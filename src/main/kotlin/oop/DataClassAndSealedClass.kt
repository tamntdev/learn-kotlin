package org.example.oop

import java.text.SimpleDateFormat
import java.util.Date

/**
*  Lớp data là các lớp được tạo ra với mục đích chính là chứa dữ liệu.
 * Để tạo data class, ta bắt đầu khai báo lớp với keyword data
 *
 * - Trình biên dịch sẽ tự động tạo các thành phần sau từ tất cả các thuộc tính khai báo trong hàm khởi tạo chính:
 *      + Cặp phương thức equals()/hashCode().
 *      + toString() trả về dạng “Student(studentId=B25DCCN100, fullName=Le Cong Toan, gpa=3.25)”
 *      + componentN() – hàm tương ứng với các thuộc tính theo thứ tự mà nó được khai báo.
 *      + Hàm copy() để sao chép đối tượng.
 *
 * - Lớp data có đầy đủ các tính chất thỏa mãn các yêu cầu sau nhằm tạo sự nhất quán và sinh các code có ý nghĩa:
 *      + Hàm khởi tạo chính có ít nhất 1 tham số.
 *      + Mọi tham số của hàm khởi tạo chính cần được đánh dấu với val hoặc var.
 *      + Lớp data không thể là lớp abstract, open, sealed hay inner.
 *
 * - Các thành phần được sinh ra trong lớp data tuân theo các quy tắc sau dựa trên quan hệ kế thừa:
 *      + Nếu đã có triển khai tường minh của các phương thức như equals(), hashCode(), toString() trong thân lớp data hoặc triển khai của final trong một lớp cha, thì các hàm đó sẽ không được sinh ra nữa. Lúc này những hàm/phương thức đã tồn tại sẽ được sử dụng.
 *      + Nếu trong một kiểu cha có hàm componentN() gắn nhãn open và trả về kiểu có thể tương thích, các hàm tương ứng sẽ được sinh ra trong lớp data và ghi đè lại các hàm trong các kiểu cha. Nếu các hàm của kiểu cha không thể ghi đè do tính tương thích, hoặc do chúng là final, một lỗi sẽ xuất hiện.
 *      + Cung cấp các triển khai chi tiết cho các hàm componentN() và copy() là không được phép. Tức là hai hàm này được sinh tự động.
 * - Lớp data có thể kế thừa các lớp khác.
 */
data class Student(val studentId: String, val fullName: String) {
    var gpa: Float = 0f
    lateinit var birthDate: Date
}

fun main() {
    val student1 = Student("B25DCCN100", "Lê Công Tuấn")
    val student2 = Student("B25DCCN101", "Lê Công Đặng Duy")
    // gọi các thuộc tính của đối tượng student
    student1.gpa = 3.36f
    student2.birthDate = SimpleDateFormat("dd/MM/yyyy")
        .parse("12/10/2007")

    println(student1) // in ra toString() của student1
    println(student2) // in ra toString() của student2
}
