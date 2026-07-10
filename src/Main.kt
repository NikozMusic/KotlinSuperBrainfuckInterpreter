import java.io.File


//Maim Function
fun main(args: Array<String>) {

    //According to brainfuck.net/learn a tape has 30000 "boxes" exactly.
    //Boxes being what it calls bytes that are in the tape
    val tape = MutableList(30000) { 0.toUByte() }

    //Initialize the program file
    val program = if (args.isNotEmpty()) {
        initializeProgram(args[0])
    } else {
        println("Please specify a file to run")
        return
    }

    val loops = mutableMapOf<Int, Int>()
    val startStack = mutableListOf<Int>()

    //Add each loop start to the Stack
    //when encountering a loop end remove the latest loop start from the StartStack
    //once removed it sets the point the loop end should jump too to the latest loop start point
    for (i in program.indices) {
        when (program[i]) {
            '[' -> startStack.add(i)

            ']' -> {
                val start = startStack.removeLast()
                loops[start] = i
                loops[i] = start
            }
        }
    }

    //Variables can be changed later opposed to Values which cant
    var pointer = 0
    var stepper: Int = 0


    //Main Loop
    while (stepper < program.length) {


        //This is where you would add your own syntax
        when(program[stepper]) {
            '+' -> tape[pointer]++
            '-' -> tape[pointer]--
            '>' -> pointer++
            '<' -> pointer--
            '.' -> print(tape[pointer].toInt().toChar())
            ',' -> tape[pointer] = readln()[0].code.toUByte()
            '[' -> {
                if(tape[pointer] == 0.toUByte()) {
                    stepper = loops[stepper]!!
                }
            }
            ']' -> {
                if (tape[pointer] != 0.toUByte()) {
                    stepper = loops[stepper]!!
                }
            }
        }

        //Go to next program step
        stepper++
    }

}

fun initializeProgram(filepath: String): String {
    val content = File(filepath).readText()
    return content
}

//Made by NikozMusic :3