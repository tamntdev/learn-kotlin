package org.example.practices

fun main() {
    // Viết chương trình tính + - * / hai số nhập vào từ bàn phím
    while (true) {
        println("Input first number: ")
        val a: Int = readln().toInt()
        println("Input second number: ")
        val b: Int = readln().toInt()
        println("a = $a, b = $b")

        var select: Int = -1
        println("Chọn phép toán của bạn: ")
        println("1. Cộng")
        println("2. Trừ")
        println("3. Nhân")
        println("4. Chia")
        while (true) {
            select = readln().toInt()

            if (select < 1 || select > 4) {
                select = -1
            } else {
                break
            }
        }

        println(
            "Kết quả phép toán đã chọn: ${
                operatorTwoNumber(
                    a, b, when (select) {
                        1 -> {
                            ::sum
                        }

                        2 -> {
                            ::sub
                        }

                        3 -> {
                            ::multiply
                        }

                        4 -> {
                            ::div
                        }

                        else -> null
                    } as ((Int, Int) -> Int)?
                )
            }"
        )

        println("Nhấn 1 để chọn lại, 2 để thoát chương trình")

        val exit = readln().toInt()

        when (exit) {
            1-> {

            }
            2-> {
                break
            }
        }
    }
    println("Kết thúc chương trình")
}

fun operatorTwoNumber(a: Int, b: Int, operator: ((Int, Int) -> Int)?): Int {
    return operator?.invoke(a, b) ?: Int.MIN_VALUE
}

fun sum(a: Int, b: Int): Int {
    return a + b
}

fun sub(a: Int, b: Int): Int {
    return a - b
}

fun multiply(a: Int, b: Int): Int {
    return a * b
}

fun div(a: Int, b: Int): Float {
    return a * 1.0F / b
}