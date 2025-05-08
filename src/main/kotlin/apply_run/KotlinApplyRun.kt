package org.example.apply_run

/**
 * 1. apply
 * Mục đích: apply được sử dụng để khởi tạo hoặc cấu hình một đối tượng. Nó cho phép bạn thực hiện nhiều thao
 * tác trên đối tượng đó mà không cần lặp lại tên của đối tượng.
 * Cách hoạt động:
 * apply được gọi trên một đối tượng.
 * Trong khối apply, bạn có thể truy cập các thuộc tính và phương thức của đối tượng bằng cách sử
 * dụng this (hoặc bỏ qua this vì nó là ngầm định).
 * apply trả về chính đối tượng mà nó được gọi.
 * Khi nào nên dùng:
 * Khi bạn muốn khởi tạo một đối tượng và gán giá trị cho nhiều thuộc tính của nó.
 * Khi bạn muốn cấu hình một đối tượng, ví dụ: set các thuộc tính cho một view.
 * Khi bạn muốn thực hiện nhiều thao tác trên đối tượng, nhưng không cần giá trị trả về.
 * "Từ khóa" để nhớ: "Cấu hình" và "trả về chính đối tượng".
 *
 * 2. run
 * Mục đích: run có hai cách sử dụng chính:
 * Chạy một khối mã trên một đối tượng: Giống như apply, nó cho phép bạn thực hiện nhiều thao tác trên một đối tượng.
 * Chạy một khối mã độc lập: Không cần đối tượng, giống như một hàm thông thường.
 * Cách hoạt động:
 * Với đối tượng: run được gọi trên một đối tượng. Bên trong khối run, bạn có thể truy cập các thuộc tính và phương thức của đối tượng bằng cách sử dụng this (hoặc bỏ qua this).
 * Không có đối tượng: run không cần đối tượng. Bạn chỉ cần gọi run và cung cấp một khối mã.
 * run trả về kết quả của biểu thức cuối cùng trong khối mã.
 * Khi nào nên dùng:
 * Khi bạn muốn thực hiện nhiều thao tác trên một đối tượng và cần kết quả trả về.
 * Khi bạn muốn chạy một khối mã độc lập (không cần đối tượng), như một hàm thông thường.
 * "Từ khóa" để nhớ: "Thực hiện" và "trả về kết quả".
 * Ví dụ 1: Chạy một khối mã trên một đối tượng Person
 */
data class Person(var name: String = "", var age: Int = 0, var city: String = "")

data class Person1(var name: String? = null, var age: Int? = null, var city: String? = null)

fun main() {
    // Ví dụ 1: Khởi tạo một đối tượng Person
    // Giải thích:
    //*   Chúng ta tạo một đối tượng `Person` mới.
    //*   Sử dụng `apply` để gán giá trị cho các thuộc tính `name`, `age`, và `city`.
    //*   Vì `apply` trả về chính đối tượng, nên `person` chính là đối tượng `Person`
    val person = Person().apply {
        name = "Alice"
        age = 30
        city = "New York"
    }
    println(person) // Output: Person(name=Alice, age=30, city=New York)

    // Ví dụ 2: Cấu hình 1 TextView
//    val myTextView = TextView(this).apply {
//        text = "Hello, Android!"
//        textSize = 24f
//        setTextColor(Color.RED)
//        setPadding(16, 16, 16, 16)
//        setBackgroundColor(Color.YELLOW)
//    }

    // Ví dụ chạy 1 khối code trên một đối tượng Person
    val description = person.run {
        "Tên: $name, Tuổi: $age, Thành phố: $city"
    }
    println(description) // Output: Tên: Bob, Tuổi: 25, Thành phố: London

    // Ví dụ 2: Chạy một khối mã độc lập.
    val result = run {
        val number = 10
        val message = "The number is"
        "$message $number"
    }
    println(result) // output: The number is 10

    // Ví dụ 3: Kết hợp với ?. để xử lý nullable object.
    val person1: Person? = Person("Alice", 30, "New York")
    val description1 = person1?.run {
        "Tên: $name, Tuổi: $age, Thành phố: $city"
    }
    println(description) // output: Tên: Alice, Tuổi: 30, Thành phố: New York

    val nullPerson: Person? = null
    val nullDescription = nullPerson?.run {
        "Tên: $name, Tuổi: $age, Thành phố: $city"
    }
    println(nullDescription) // output: null
}