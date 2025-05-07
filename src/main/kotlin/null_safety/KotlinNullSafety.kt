package org.example.null_safety

class Course(val students: List<Student?>? = null)

class Student(var first: String, var last: String, var midd: String) {
    fun doExam(subject: String) {
        println("Sinh viên $first đang làm bài kiểm tra môn $subject")
    }
}

fun getCourse(name: String): Course? {
    // tìm khóa học theo tên...
    return null
}

fun main() {
    // Trong Kotlin, các kiểu hệ thống phân biệt giữa các tham chiếu có thể chứa giá trị null và
    // các kiểu không thể chứa giá trị null.
    // Ví dụ, một biến kiểu String thông thường không thể chứa giá trị null
    // Để cho phép một kiểu nào đó có thể null, ta thêm dấu ? vào sau tên kiểu khi khai báo biến. Ví dụ:
    var name: String? = "Hoa"
    name = null // lỗi

    // Kiểm tra điều kiện null
    // Trường hợp một biến hoặc biểu thức nào đó có thể null nhưng ta vẫn muốn truy cập vào biến đó,
    // ta có thể thực hiện việc truy cập qua nhiều cách.
    // Các đầu tiên, ta sẽ kiểm tra null trước khi truy cập với câu điều kiện if:
    var fullName: String? = "Lê Công Tuấn"
    if(fullName == null) {
        println("Họ và tên null.")
    } else {
        println("Độ dài tên: ${fullName.length} kí tự")
    }

    var address: String? = null

    println("Address length: ${address?.length}")

    val course: Course? = getCourse("Lập trình Android")
    println("Tên sinh viên: ${course?.students?.get(0)?.first}")

    // Trong Kotlin có Toán tử elvis ?: tương tự như toán tử ?? trong Dart
    val nameLength = fullName?.length ?: -1

    // Toán tử not null !! trong Kotlin tương tự như toán tử ! trong Dart
    val nameLength1: Int = fullName!!.length

    val friends: List<String?> = listOf("Hoa", "Loan", null, "Tuấn", "Sơn", "Minh", null)
    val nonNullFriends = friends.filterNotNull()
    println(nonNullFriends)
}

