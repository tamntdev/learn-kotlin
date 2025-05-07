package org.example.lambda_expression

/**
 * - Hàm bậc cao(higher-order function) là các hàm có tham số đầu vào là các hàm khác hoặc trả về một hàm nào đó.
 * - Trong Kotlin, các hàm có thể được lưu trữ trong các biến, các cấu trúc dữ liệu.
 * - Ta có thể truyền truyền hàm vào hàm như các đối số và có thể trả về hàm như một giá trị từ các hàm bậc cao khác.
 * - Ví dụ về hàm bậc cao: hàm fold() dùng để kết hợp các phần tử vào theo một hàm cho trước và trả về kết quả
 *
 * Các kiểu hàm
 * Kotlin sử dụng các kiểu hàm ví dụ (Int) -> String để khai báo các tham số nhận vào một hàm nào đó.
 * Trong khai báo này có một dấu hiệu đặc biệt tương ứng với các dấu hiệu của các hàm, đó chính là các tham số và giá trị trả về.
 * Tất cả các kiểu hàm có một cặp ngoặc tròn liệt kê danh sách các kiểu tham số và một kiểu trả về, ví dụ: (A, B) -> C. Trong đó mô tả một hàm nhận vào hai tham số kiểu A, B và trả về một kết quả kiểu C. Danh sách tham số của kiểu hàm có thể để trống dạng () -> X. Kiểu trả về Unit không thể để trống.
 * Các kiểu hàm có thể có tùy chọn kiểu receiver, tức đối tượng có thể thực hiện lời gọi hàm. Thành phàn này được chỉ định trước dấu chấm trong ví dụ: A.(B) -> C đại diện cho hàm có thể được gọi trên đối tượng receiver A với một tham số B và trả về giá trị kiểu C. Các hàm chuỗi hằng với receiver thường được sử dụng với các kiểu như trên.
 * Các hàm suspend thuộc loại kiểu hàm đặc biệt trong đó có chứa một modifier suspend trong dấu hiệu của nó, ví dụ suspend() -> Unit hoặc suspend A.(B) -> C.
 * Dấu hiệu của kiểu hàm có thể chứa tên tùy chọn cho các tham số nhằm mục đích mô tả về ý nghĩa của các tham số.
 */

// - Trong hàm trên, tham số combine có kiểu của hàm: (R, T) -> R,
// nên nó chấp nhận một hàm nhận vào hai tham số kiểu R và T, trả về giá trị kiểu R.
// - Để gọi hàm trên, ta phải truyền vào một phiên bản của hàm có kiểu tương ứng.
// Thường ta sẽ sử dụng biểu thức lambda trong các lời gọi hàm như thế.
fun <T, R> Collection<T>.fold(
    initial: R,
    combine: (acc: R, nextElement: T) -> R
): R {
    var accumulator: R = initial
    for (element: T in this) {
        accumulator = combine(accumulator, element)
    }
    return accumulator
}

//fun main() {
//    val arr = arrayOf(1, 2, 3, 4, 5, 6, 7, 8, 9)
//
//    val actionAdd: (Long, Int) -> Long = { s, x -> s + x }
//    val sum = doSomeWork(arr, 0, actionAdd)
//
//    val actionMul: (Long, Int) -> Long = { s, x -> s * x }
//    val product = doSomeWork(arr, 1, actionMul)
//
//    val sum2 = doSomeWork(arr, 0) { s, x -> s + x * x }
//
//    println("Tổng các phần tử trong mảng: $sum")
//    println("Tích các phần tử trong mảng: $product")
//    println("Tổng bình phương các phần tử trong mảng: $sum2")
//
//    val stringPlus: (String, String) -> String = String::plus
//    val longPlus: Long.(Long) -> Long = Long::plus
//
//    // gọi kiểu hàm theo hai cách
//    println(stringPlus.invoke("Xin chào ", "Kotlin"))
//    println(stringPlus("Xin chào ", "Kotlin nhé"))
//
//    // gọi kiểu hàm có receiver theo 3 cách
//    println(longPlus.invoke(100, 235)) // gọi kiểu hàm với invoke
//    println(longPlus(101, 236)) // gọi kiểu hàm như thông thường
//    println(113L.longPlus(548)) // lời gọi giống hàm mở rộng
//}

fun doSomeWork(
    arr: Array<Int>,
    init: Long,
    action: (s: Long, x: Int) -> Long
): Long {
    var sum = init
    arr.forEach { sum = action.invoke(sum, it) }
    return sum
}

/**
 * Khởi tạo một đối tượng của kiểu hàm có 1 số cách sau:
 * 1. Sử dụng một khối code dưới dạng hàm vô danh hoặc biểu thức lambda:
 *  // biểu thức lambda
 *  {a, b -> a + b}
 *
 *  // hàm vô danh
 *  fun(s: String): Int {return s.toIntOrNull() ?: 0}
 *
 *  2. Sử dụng một tham chiếu có thể gọi được tới các khai báo đã tồn tại sẵn:
 * - Sử dụng top-level, local, member hoặc hàm mở rộng: ::isOld, String::toInt,
 * - Sử dụng top-level, member, thuộc tính mở rộng: List<Int>::size,
 * - Sử dụng một constructor: ::Regex, ::BankAccount.
 * 3. Sử dụng một phiên bản đối tương của một lớp tùy chỉnh triển khai kiểu hàm như là một interface:
 *
 * Gọi đối tượng kiểu hàm
 * - Sử dụng toán tử invoke(…) ví dụ f.invoke(x) hoặc đơn giản là f(x) để gọi đối tượng kiểu hàm.
 * Nếu có kiểu receiver, đối tượng receiver nên được truyền vào như là đối số đầu tiên.
 * Cách khác là ta có thể đặt đối tượng receiver phía trước giống như một giá trị có hàm mở rộng: 1.foo(2).
 */

// Sử dụng một phiên bản đối tương của một lớp tùy chỉnh triển khai kiểu hàm như là một interface:
class IntTransformer: (Int) -> Int {
    override operator fun invoke(x: Int): Int = TODO()
}

val intFunction: (Int) -> Int = IntTransformer()

/**
 * Biểu thức lambda và hàm vô danh
 * Biểu thức lambda và hàm vô danh là các hàm literal.
 * Tức là các hàm không được khai báo mà truyền ngay lập tức vào làm đối số của hàm bậc cao như một biểu thức.
 *
 *
 * Cú pháp của biểu thức lambda
 * - Cú pháp đầy đủ của biểu thức lambda có dạng:
 *      val sum: (Int, Int) -> Int = { x: Int, y: Int -> x + y }
 * - Trong đó:
 *      + Một biểu thức lambda luôn được bao bởi cặp ngoặc nhọn {}.
 *      + Các khai báo tham số trong cú pháp đầy đủ nằm trong cặp {} và có tùy chọn chỉ định kiểu dữ liệu.
 *      + Phần thân của biểu thức đứng sau mũi tên ->.
 *      + Nếu kiểu trả về của biểu thức(được suy luận ra) mà khác Unit,
 *      biểu thức cuối trong phần thân biểu thức lambda sẽ được coi là giá trị trả về.
 *
 * - Sau khi loại bỏ hết phần tùy chọn, ta có biểu thức lambda ngắn gọn như sau:
 *      val sum = { x: Int, y: Int -> x + y }
 *
 * - Truyền biểu thức lambda vào hàm
 *      + Nếu tham số cuối của hàm cần gọi là một hàm,
 *      biểu thức lambda có thể được truyền vào cho tham số đó bằng cách đặt ngoài ngoặc tròn của lời gọi:
 *      val product = items.fold(1) { acc, e -> acc * e }
 *      + Nếu biểu thức lambda là đối số duy nhất củ lời gọi, ta có thể bỏ qua cặp ngoặc tròn:
 *      run { println("...") }
 *
 * - Tên ngầm định của tham số duy nhất
 *  + Nếu biểu thức lambda chỉ có duy nhất 1 tham số, ta có thể loại bỏ tham số,
 *  mũi tên và chỉ cung cấp phần thân của biểu thức lambda.
 *  + Lúc này trình biên dịch Kotlin sẽ được gán ngầm cho biến tên là it:
 *      ints.filter { it > 0 }
 *      // hàm literal này tương đương với
 *      (it: Int) -> Boolean
 *
 * - Trả về một giá trị từ biểu thức lambda
 * Ta có thể trả về một giá trị từ trong biểu thức lambda theo 2 cách như sau:
 *      ints.filter {
 *          val shouldFilter = it > 0
 *          shouldFilter
 *      }
 *
 *      ints.filter {
 *          val shouldFilter = it > 0
 *          return@filter shouldFilter
 *      }
 */

fun main() {
    val firstNumber = 120
    val secondNumber = 230
    doSomething(firstNumber, secondNumber, "+") { x, y -> x + y }
    doSomething(firstNumber, secondNumber, "-") { x, y -> x - y }
    doSomething(firstNumber, secondNumber, "*") { x, y -> x * y }
    doSomething(firstNumber, secondNumber, "/") { x, y -> x / y }
    doSomething(firstNumber, secondNumber, "%") { x, y -> x % y }

    // Sử dụng dấu gạch dưới cho các biến không dùng tới
    // Nếu biểu thức lambda có tham số không sử dụng tới, ta có thể thay thế nó bằng dấu gạch dưới thay vì tên tham số cụ thể:
    val friends = listOf("Loan", "Mai", "Hương", "Quỳnh", "Tuấn")
    println("Những người bạn của tôi: ")
    friends.withIndex().forEach { (_, value) -> println(value) }

    // Ví dụ hàm vô danh
    val numbers = arrayOf(1, 2, 3, 6, 5, 4, 7, 8, 9)
    var sum = 0
    numbers.filter { it % 2 == 0 }.forEach { sum += it }
    println("Tổng các số chẵn trong mảng: $sum")
}

fun doSomething(
    a: Int = 0, b: Int = 0,
    operator: String = "+",
    action: (x: Int, y: Int) -> Int
): Int {
    val result = action(a, b)
    println("$a $operator $b = $result")
    return result
}

/**
 * Hàm vô danh
 * - Biểu thức lambda có một nhược điểm là không thể chỉ định kiểu trả về của nó một cách tường minh.
 * - Nếu ta cần chỉ rõ kiểu trả về của một hàm, ta có thể sử dụng hàm vô danh để thay cho biểu thức lambda.
 *      fun(x: Int, y: Int): Int = x + y
 *
 * - Một hàm vô danh trông giống với hàm thông thường ngoại trừ việc nó không có tên.
 * Phần thân hàm có thể có hoặc không có cặp ngoặc {} bao quanh.
 *
 *      fun(x: Int, y: Int): Int {
 *          return x + y
 *      }
 *
 * - Kiểu của các tham số và kiểu trả về được xác định giống như trong hàm thông thường.
 * Trừ trường hợp có thể suy luận từ trong ngữ cảnh, lúc này kiểu của tham số,
 * kiểu trả về có thể khuyết.
 *      ints.filter(fun(item) = item > 0)
 *
 * - Khác biệt cuối cùng của một hàm vô danh vs biểu thức lambda là:
 * lệnh return không đi kèm nhãn luôn trả về từ bên trong hàm khai báo với keyword fun.
 * Do đó trong biểu thức lambda, nếu return thì return từ hàm gần nhất chứa biểu thức lambda
 * còn hàm vô danh sẽ return từ bên trong bản thân hàm chứa lệnh return.
 */