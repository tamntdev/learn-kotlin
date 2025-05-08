package org.example.let_with_also

/**
 * 1. let
 * Mục đích: let thường được sử dụng để xử lý các giá trị có thể null (nullable) một cách an toàn hoặc để giới hạn phạm
 * vi của một biến. Nó cho phép bạn thực thi một khối mã chỉ khi đối tượng không null.
 * Cách hoạt động:
 * let nhận đối tượng mà nó được gọi là một tham số (với từ khóa it).
 * let trả về kết quả của biểu thức lambda được thực thi bên trong nó.
 * Nó thường được sử dụng với toán tử elvis ?.
 * Ví dụ:
 */
fun processName(name: String?) {
    name?.let { nonNullName ->
        println("Tên không null: $nonNullName")
        // Thực hiện các thao tác khác với nonNullName
        println("Độ dài tên: ${nonNullName.length}")
    }
}

/**
 * 2. with
 * Mục đích: with thường được sử dụng để truy cập nhiều thuộc tính hoặc gọi nhiều phương thức trên cùng một
 * đối tượng mà không cần lặp lại tên đối tượng.
 * Cách hoạt động:
 * with nhận đối tượng như một tham số đầu tiên.
 * Bên trong khối mã, bạn có thể truy cập trực tiếp các thuộc tính và phương thức của đối tượng.
 * with trả về kết quả của biểu thức lambda cuối cùng.
 * Ví dụ:
 */
class Person(var name: String, var age: Int)

/**
 * 3. also
 * Mục đích: also thường được sử dụng để thực hiện các hành động phụ (side effects) trên một đối
 * tượng mà không thay đổi đối tượng đó. Nó hữu ích khi bạn muốn làm gì đó với đối tượng (ví dụ: ghi nhật ký,
 * in ra, hoặc thay đổi trạng thái), nhưng không muốn thay đổi giá trị được trả về.
 * Cách hoạt động:
 * also nhận đối tượng mà nó được gọi là một tham số (với từ khóa it).
 * also trả về chính đối tượng mà nó được gọi.
 * Thường được sử dụng để in ra đối tượng hoặc ghi nhật ký.
 */

/**
 * Khi nào nên sử dụng mỗi hàm?
 * Sử dụng let khi bạn muốn xử lý các giá trị có thể null hoặc khi bạn muốn giới hạn phạm vi của một biến.
 * Sử dụng with khi bạn muốn thực hiện nhiều thao tác trên cùng một đối tượng mà không muốn lặp lại tên đối tượng.
 * Sử dụng also khi bạn muốn thực hiện các hành động phụ trên một đối tượng mà không thay đổi giá trị trả về.
 */

fun main() {
    // Ví dụ sử dụng let
    // Trong ví dụ này, khối mã bên trong let chỉ được thực thi nếu name không phải là null.
    // Bên trong khối let, nonNullName sẽ tham chiếu đến giá trị của name.
    processName("Alice") // output: Tên không null: Alice, Độ dài tên: 5
    processName(null) // output: Không in gì cả.

    // Ví dụ sử dụng with
    // Trong ví dụ này, chúng ta không cần viết person.name hay person.age mà có thể truy
    // cập trực tiếp thông qua name và age.
    val person = Person("Bob", 30)

    val description = with(person) {
        println("Tên: $name")
        println("Tuổi: $age")
        "Đây là $name, $age tuổi" // Giá trị trả về
    }
    println(description) // output: Tên: Bob, Tuổi: 30, Đây là Bob, 30 tuổi

    // Ví dụ sử dụng also
    // Trong ví dụ này, also cho phép chúng ta in ra danh sách ban đầu ([1, 2, 3]) và thêm một phần tử vào danh sách,
    // nhưng giá trị trả về của also vẫn là danh sách đã bị thay đổi.
    val numbers = mutableListOf(1, 2, 3)
    val result = numbers.also {
        println("Danh sách ban đầu: $it") // In ra danh sách ban đầu
        it.add(4) // Thêm một phần tử vào danh sách
    }
    println("Danh sách sau khi thêm: $result") // In ra danh sách sau khi thêm (đã thay đổi)
}