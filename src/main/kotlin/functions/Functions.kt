package org.example.functions

fun main() {
    customPrint("Hello World! Custom Print")
    val result = sumTwoNumber(2, 3)

    println(result)

    unitFunction()

    println("------------------")
    customPrint(unitFunction().toString())
    println("------------------")
    println(sumTwoNumber(a = 10))

    // lambda có thể được dùng khi muốn sử dụng 1 hàm mà không muốn khai báo nó ra bên ngoài phạm vi của hàm cần sử dụng
    println("------------------")
    // Khai báo lambda function
    val sum = { a: Int, b: Int ->
        Int
        a + b
    }
    println(sum.invoke(10, 2))

    val multiply = { a: Int, b: Int ->
        Int
        a * b
    }

    println("--------------------")
    println("Sum two number: ${operatorTwoNumber(2, 3, sum)}")
    println("Multiply two number: ${operatorTwoNumber(2, 3, multiply)}")

    // Nếu truyền hàm thuần vào higher order function thì phải dùng toán tử ::
    println("Higher order function with normal function: ${operatorTwoNumber(2, 3, ::sumTwoNumber)}")
}

// Higher order function là khi 1 hàm được truyền vào 1 hàm khác theo dạng parameter
fun operatorTwoNumber(a: Int, b: Int, operator: (Int, Int) -> Int): Int {
    return operator.invoke(a, b)
}

// Khai báo lambda function
val sum = { a: Int, b: Int ->
    Int
    a + b
}

// Default argument
fun sumTwoNumber(a: Int = 0, b: Int = 0): Int {
    return a + b
}

fun customPrint(message: String) {
    println("Custom print: $message")
}

fun unitFunction(): Unit {
    println("Call Unit function")
    return Unit
}