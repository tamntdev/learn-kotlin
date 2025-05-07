package org.example.functions

/**
 * - Trong Kotlin, hàm được khai báo bắt đầu với keyword fun. Theo sau đó là tên hàm và danh sách tham số,
 * kiểu trả về, cuối cùng là thân hàm.
 * - Cấu trúc tổng quát:
 *          fun functionName(parameter: Type, parameter2: Type, ...): Type {
 *              // statements
 *          } // end function
 *
 * - Nếu một hàm không trả về giá trị hữu ích nào đó thì kiểu trả về của nó là Unit.
 * - Unit là một kiểu với duy nhất 1 giá trị Unit. Giá trị này không cần return một cách tường minh.
 */

fun drawRect(width: Int, height: Int) {
    for (i in 1..height) {
        for (j in 1..width) {
            if (i == 1 || i == height || j == 1 || j == width) {
                print(" * ") // vẽ ra dấu *
            } else {
                print("   ") // vẽ ra dấu cách
            }
        }
        println() // in xuống dòng
    }
}

// Khai báo hàm đơn giản nhất trong Kotlin
fun addNumbers(num1: Int, num2: Int): Int {
    return num1 + num2
}

// inner function
fun outerFunction() {
    fun innerFunction() {
        println("This is the inner function.")
    }

    println("This is the outer function.")
    innerFunction()
}

// Khai báo hàm là 1 lambda expression
val multiply = { num1: Int, num2: Int -> num1 * num2 }

/**
 * Đệ quy đuôi
 * - Đệ quy là kĩ thuật được sử dụng để giải quyết các vấn đề có tính lặp đi lặp lại.
 * Các vấn đề được giải quyết bằng vòng lặp có thể được giải quyết bằng đệ quy.
 * - Với Kotlin, kĩ thuật đệ quy đuôi được tối ưu do đó ta không lo lắng việc tràn stack.
 * - Hàm đệ quy đuôi được gắn modifier tailrec phía trước và phải thỏa mãn các điều kiện cần thiết.
 * - Điều kiện để một hàm trở thành đệ quy đuôi là lời gọi đệ quy phải là lời gọi sau cùng của hàm.
 * - Trình biên dịch sẽ tối ưu hóa đệ quy, tạo ra một phiên bản vòng lặp hiệu quả đằng sau việc đệ quy.
 */

// Tính tổng các chữ số của n
tailrec fun sumDigits(n: Int, sum: Int): Int = if (n == 0) sum else sumDigits(n / 10, sum + n % 10)

// Tìm n!
tailrec fun factorial(n: ULong, result: ULong): ULong {
    var tmp = result
    if (n >= 1u) {
        tmp = n * result
    }
    return if (n <= 1u) {
        tmp
    } else {
        factorial(n - 1u, tmp)
    }
}

fun main() {
//    val sum = addNumbers(5, 3)
//
//    println("The sum is: $sum")
//
//    outerFunction()
//
//    val res = multiply(3, 5)
//
//    println("The result is: $res")

    drawRect(3, 5)
}