# SuperBrainfuck

## Click [Here](https://nikozmusic.github.io/visualsuperbrainfuck.github.io/) for a Visual Webapp interpreter

SuperBrainfuck asks "what if brainfuck had 16 instructions instead of 8".
Using this simple change I was able to formulate a complete expansion of the language while staying within its original design goals.

A few of the changes are as follows:
- There are now 16 Tapes stacked in a "Roll" effectively turning the bit plane into a grid
- Each tape shares the same pointer allowing you to jump to a location on another tape
- Tapes are now exactly 2048 Bytes long (2KiB per tape totaling 32KiB of memory)

## What?
The easiest way to visualize the new memory layout is to think of it as all 16 tapes being stacked on top of each-other into one large "Roll" that spins along its top and bottom faces.

When the pointer is moved left or right think of it as spinning the roll to the left or right as when it goes off one end it loops to the other.

Selecting which tape you want to use can be seen as moving your selection up or down the roll.

This system ends up turning the 1D line of bits from the original Brainfuck into a 2D Bitplane of sorts allowing more complicated programs to be made much easier while still having very minimal syntax.

## How?
SuperBrainfuck's syntax is expanded off Brainfuck's syntax as the following:

### Brainfuck Syntax

| Command | Description                                  |
|---------|----------------------------------------------|
| `+`     | Increase the current cell                    |
| `-`     | Decrease the current cell                    |
| `<`     | Move pointer left                            |
| `>`     | Move pointer right                           |
| `[`     | Begin loop                                   |
| `]`     | End loop                                     |
| `.`     | Print the current cell as ASCII format       |
| `,`     | Read character from terminal as ASCII format |

### SuperBrainfuck Syntax additions

| Command | Description                             |
|---------|-----------------------------------------|
| `/`     | Select the tape above                   |
| `\`     | Select the tape below                   |
| `$`     | Select the bottom tape                  |
| `?`     | Randomize the current cell              |
| `@`     | Reset the pointer and tape selection    |
| `^`     | Copy the current cell to the tape above |
| `_`     | Copy the current cell to the tape below |
| `:`     | Print the current cell as a raw byte    |

This interpreter specifically loads SuperBrainfuck files as ".sbf (Super Brain Fuck" and will reject any other file format

#### Made by NikozMusic based on the work of Urban Müller
