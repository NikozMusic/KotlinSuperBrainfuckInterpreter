Superbrainfuck is a project I made for fun after creating my own Brainfuck interpreter and realizing how simple it would be to add more syntax

A few of the changes are as follows:
- There are now 8 Tapes stacked in a "Roll" effectively turning the bit plane into a grid
- The roll spins with the pointer and the selected tale can be selected via code (see syntax changes)
- Tapes are now exactly 1024 Bytes long (Making one KiB per tape totaling 8KiB of usable memory)

New Syntax:
"/" - Select the tape above, loops to bottom at the top
"\" - Select the tape below, loops to the top at the bottom
"@" - Sets all bytes in the current collum across all tapes to the value at the selected byte
