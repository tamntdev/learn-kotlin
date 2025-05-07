package org.example.extension_function

/**
 * Tính năng extension function cho phép mở rộng logic của 1 class và tái sử dụng chúng 1 cách hiệu quả mà
 * không phải kế thừa class đó.
 * Điểm giới hạn của extension function là nó không thể truy cập vào các thuộc tính private của class
 * Bản chất của extension function là 1 static function được viết gọn lại để dễ sử dụng và debug
 *
 * - Cú pháp tổng quát của hàm mở rộng:
 * fun <GenericTypes> ClassName<GenericTypes>.functionName(params): ReturnType {
 *     // function body
 * }
 *
 * Nếu ta có hàm thành phần và hàm mở rộng được định nghĩa giống hệt nhau thì hàm thành phần của lớp sẽ ưu tiên được gọi.
 */

fun String.countVowels(): Int {
    var count: Int = 0

    for (char in this) {
        if (char in "aeiouAEIOU") {
            count++
        }
    }

    return count
}

fun String.countWord(): Int {
    val words = this.split(" ") // tách từ tại vị trí có dấu cách
    var counter = 0 // biến đếm số từ
    for (item in words) { // chỉ xét các từ không phải khoảng trắng
        if (item.isNotBlank()) {
            counter++ // nếu từ đang xét không rỗng -> tăng biến đếm lên 1
        }
    }
    return counter // trả về kết quả
}

fun <T> Array<T>.swap(index1: Int, index2: Int) {
    val temp = this[index1]
    this[index1] = this[index2]
    this[index2] = temp
}

open class Person1
class Student : Person1()

fun Person1.getClassName() = "Person"
fun Student.getClassName() = "Student"

fun printClassName(person: Person1) {
    println(person.getClassName())
}

// Nếu ta có hàm thành phần và hàm mở rộng được định nghĩa giống hệt nhau thì hàm thành phần của lớp sẽ ưu tiên được gọi.
class Example {
    fun printFunctionInfo() {
        println("Phương thức printFunctionInfo() của lớp Example")
    }
}

fun Example.printFunctionInfo() {
    println("Hàm mở rộng printFunctionInfo() của lớp Example")
}

// nạp chồng hàm thành phần với 1 tham số
fun Example.printFunctionInfo(str: String) {
    println("Hàm mở rộng printFunctionInfo() của lớp Example")
    println("Giá trị tham số: $str")
}

// nạp chồng hàm thành phần với 2 tham số
fun Example.printFunctionInfo(str: String, value: Int) {
    println("Hàm mở rộng printFunctionInfo() của lớp Example")
    println("Giá trị tham số: $str, $value")
}

// Hàm mở rộng có thể áp dụng cho kiểu mục tiêu có thể null.
// Các hàm mở rộng lúc này có thể gọi với biến đối tượng đang null và kiểm tra null với cú pháp this == null trong phần thân hàm.
// Ví dụ: nếu đối tượng khác null, gọi phương thức toString() của nó nếu không, trả về “null”.
fun Any?.toString(): String {
    return this?.toString() ?: "null"
}

// Với các thuộc tính mở rộng, ta có cú pháp tương tự như hàm mở rộng.
// Ví dụ dưới đây trả về vị trí phần tử chính giữa của một mảng:
val <T> Array<T>.middleIndex: Int
    get() = size / 2

// Trong hầu hết các trường hợp, ta định nghĩa các thành phần mở rộng trong top level, trực tiếp dưới package.
// Do đó khi sử dụng ta chỉ cần import nó vào nơi cần sử dụng.
/**
 * Hàm mở rộng trả về từ dài nhất đầu tiên tìm được trong câu
 */
fun String.longestWord(): String {
    val words = this.split(" ")
    var result = ""
    for (item in words) {
        if (item.length > result.length) {
            result = item
        }
    }
    return result
}

/**
 * Hàm mở rộng trả về từ ngắn nhất đầu tiên tìm được trong câu
 */
fun String.shortestWord(): String {
    val words = this.split(" ")
    var result = this
    for (item in words) {
        if (item.length < result.length) {
            result = item
        }
    }
    return result
}

// - Thành phần mở rộng của một lớp có thể được khai báo từ bên trong một lớp khác.
// - Trong các hàm mở rộng loại này có thể có rất nhiều đối tượng đích ngầm định,
// cả kiểu đích sẽ thực hiện lời gọi tới hàm mở rộng và kiểu chứa hàm mở rộng đó.
// - Lúc này đối tượng chứa khai báo hàm mở rộng được gọi là dispatch receiver.
// Đối tượng của kiểu đích ta gắn hàm mở rộng vào gọi là extension receiver.
// - Nếu xảy ra xung đột tên khi gọi thành phần từ trong hàm mở rộng. Cụ thể khi tên phương thức trong hai
// lớp giống nhau chẳng hạn, ta sử dụng cú pháp this@ClassName để phân loại.
class Host(val hostname: String) {
    fun printHostname() {
        print(hostname)
    }
}

class Connection(val host: Host, val port: Int) {
    fun printPort() {
        print(port)
    }

    fun Host.printConnectionString() {
        printHostname()   // calls Host.printHostname()
        print(":")
        printPort()   // calls Connection.printPort()

        toString()         // calls Host.toString()
        this@Connection.toString()  // calls Connection.toString()
    }

    fun connect() {
        /*...*/
        host.printConnectionString()   // calls the extension function
    }
}

// Hàm mở rộng khai báo như thành phần của lớp có thể được đánh dấu với keyword open và được
// ghi đè từ trong các lớp con của nó.
open class Base {}

class Derived : Base() {}

open class BaseCaller {
    open fun Base.printFunctionInfo() {
        println("Base extension function in BaseCaller")
    }

    open fun Derived.printFunctionInfo() {
        println("Derived extension function in BaseCaller")
    }

    fun call(b: Base) {
        b.printFunctionInfo()   // call the extension function
    }
}

class DerivedCaller : BaseCaller() {
    override fun Base.printFunctionInfo() {
        println("Base extension function in DerivedCaller")
    }

    override fun Derived.printFunctionInfo() {
        println("Derived extension function in DerivedCaller")
    }
}

fun main() {
    val word = "Hello"
    println("The word '$word' has ${word.countVowels()} vowel(s).")

    val str = "Tôi đang học hàm mở rộng trong        Kotlin đây"
    val numberOfWord = str.countWord()
    println("Số từ có trong câu: $numberOfWord")

    val friends = arrayOf("Hưng", "Mạnh", "Nam", "Oanh", "Hà", "Tuấn")
    val index1 = 0
    val index2 = 5

    println("==> Trước khi tráo đổi: ")
    println(friends.contentDeepToString())

    friends.swap(index1, index2) // tráo đổi hai phần tử tại vị trí đc chỉ định
    println("==> Sau khi tráo đổi: ")
    println(friends.contentDeepToString())

    printClassName(Person1())
    printClassName(Student())

    val obj = Example()
    obj.printFunctionInfo()
    obj.printFunctionInfo("Message")
    obj.printFunctionInfo("Message", 504)

    val x = null
    println(x)

    val numbers = arrayOf(1, 2, 3, 4, 5, 6, 7)
    println("Phần tử chính giữa của mảng: ${numbers[numbers.middleIndex]}")

    Connection(Host("kotl.in"), 443).connect()
    //Host("kotl.in").printConnectionString()  // error, the extension function is unavailable outside Connection

    BaseCaller().call(Base())   // "Base extension function in BaseCaller"
    DerivedCaller().call(Base())  // "Base extension function in DerivedCaller" - dispatch receiver is resolved virtually
    DerivedCaller().call(Derived())  // "Base extension function in DerivedCaller" - extension receiver is resolved statically
}