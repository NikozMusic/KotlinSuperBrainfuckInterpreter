import java.io.File

/*
    This program is an Interpreter for the SuperBrainFuck esolang made by NikozMusic based on the original
    Brainfuck esolang by Urban Müller written in Kotlin.
 */

//Main Function
fun main(args: Array<String>) {

    /*
        This is where the file is first initialized and checked, right at the top so we don't waste any time processing
        and invalid or nonexistent file.
     */
    val file = if (args.isNotEmpty()) File(args[0]) else null

    val program = if ( //Check if the file...
        file != null && //Is not null... (e.g. if they actually put anything)
        file.exists() && //Exists...
        file.isFile && //Is a file and not a directory...
        file.extension.equals("sbf", ignoreCase = true) //And if it's a .sbf file
    ) {
        initializeProgram(file.path)
    } else { //If the input fails any of these checks tell the user it's not valid then return to terminal
        println("Please specify a valid .sbf (SuperBrainFuck) file to run.")
        return
    }

    val TAPES = 16 //Default 16
    val TAPE_SIZE = 2048 //Default 2048

    /*
        Here we set up the roll, this contains 16 tapes of 2048 bytes totaling 32KiB of usable memory by default exactly,
        Though these values are arbitrary and can be changed between interpreters this is the recommended setup.
     */
    val roll = MutableList(TAPES) { MutableList(TAPE_SIZE) { 0.toUByte()} }

    val loops = mutableMapOf<Int, Int>()
    val startStack = mutableListOf<Int>()

    //Add each loop start to the Stack
    //when encountering a loop end remove the latest loop start from the StartStack
    //once removed it sets the point the loop end should jump too to the latest loop start point
    for (i in program.indices) {
        when (program[i]) {
            '[' -> startStack.add(i)

            ']' -> {
                if (startStack.isEmpty()) {
                    println("Error: unmatched ] at position $i")
                    return
                }

                val start = startStack.removeLast()
                loops[start] = i
                loops[i] = start
            }
        }
    }
    //cleanup
    if (startStack.isNotEmpty()) {
        println("Error: unmatched [ at position ${startStack.last()}")
        return
    }

    //Unlike the other statements these are Variables instead of Values meaning they can have their value changed
    var pointer = 0
    var tape = 0
    var stepper = 0


    //Main Loop
    while (stepper < program.length) {

        /*
            This part of the code is the meat and potatoes of the entire script as this is where each syntax character has
            its functions defined in one big "When" statement.
         */
        when(program[stepper]) {
            //Increase byte at selected box
            '+' -> roll[tape][pointer]++

            //Decrease byte at selected box
            '-' -> roll[tape][pointer]--

            //Move pointer
            '<' -> pointer = (pointer + TAPE_SIZE - 1) % TAPE_SIZE //Move pointer left
            '>' -> pointer = (pointer + 1) % TAPE_SIZE //Move pointer right

            //Print character at selected box
            '.' -> print(roll[tape][pointer].toInt().toChar()) //Print ASCII
            ':' -> print(roll[tape][pointer]) //Print Raw

            //Read single character input from terminal
            ',' -> roll[tape][pointer] = readln()[0].code.toUByte()

            //Loop instructions
            '[' -> {
                if (roll[tape][pointer] == 0.toUByte()) {
                    stepper = loops[stepper]!!
                }
            }
            ']' -> {
                if (roll[tape][pointer] != 0.toUByte()) {
                    stepper = loops[stepper]!!
                }
            }

            //Navigate selected tapes
            '/' -> tape = (tape + 1) % TAPES  //Up
            '\\' -> tape = (tape + TAPES - 1) % TAPES //Down
            '$' -> tape = 0 //Floor

            //Randomize the selected byte
            '?' -> roll[tape][pointer] = (0..255).random().toUByte()

            //Reset tape and pointer positions
            '@' -> {
                tape = 0
                pointer = 0
            }

            //Copy to another tape
            '^' -> roll[(tape + 1) % TAPES][pointer] = roll[tape][pointer]
            '_' -> roll[(tape + (TAPES-1)) % TAPES][pointer] = roll[tape][pointer]

        }

        //Advance to the next character in the program file
        stepper++
    }

}


//This simple function pulls the text out of the .sbf file and turns it into a string the program can use later
fun initializeProgram(filepath: String): String {
    val content = File(filepath).readText()
    return content
}

//Made by NikozMusic :3