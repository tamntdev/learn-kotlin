package org.example.operators

fun main() {
    // Toán tử số học
    val integerVariable: Int = 28283
    val integerVariable2: Int = 168
    println("Integer Variable: $integerVariable")
    println("Integer Variable 2: $integerVariable2")
    val addedIntegerVariable: Int = integerVariable + integerVariable2
    val subtractedIntegerVariable: Int = integerVariable - integerVariable2
    val multipliedIntegerVariable: Int = integerVariable * integerVariable2
    val dividedIntegerVariable: Int = integerVariable / integerVariable2
    val remainderIntegerVariable: Int = integerVariable % integerVariable2
    println("Added: $addedIntegerVariable")
    println("Subtracted: $subtractedIntegerVariable")
    println("Multiplied: $multipliedIntegerVariable")
    println("Divided: $dividedIntegerVariable")
    println("Remainder: $remainderIntegerVariable")

    // Toán tử trên bit
    val number1 = 12
    val number2 = 25

    // Bitwise AND
    val andResult = number1 and number2
    println("Result of Bitwise AND is $andResult")

    // Bitwise OR
    val orResult = number1 or number2
    println("Result of Bitwise OR is $orResult")

    // Bitwise XOR
    val xorResult = number1 xor number2
    println("Result of Bitwise XOR is $xorResult")

    // Bitwise Inversion (NOT)
    val invResult = number1.inv()
    println("Result of Bitwise Inversion (NOT) is $invResult")

    // Bitwise Left Shift
    val leftShiftResult = number1 shl 2
    println("Result of Bitwise Left Shift is $leftShiftResult")

    // Bitwise Right Shift
    val rightShiftResult = number1 shr 2
    println("Result of Bitwise Right Shift is $rightShiftResult")
}