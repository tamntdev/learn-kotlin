package org.example.lateinit_kotlin

/**
 * by lazy là một từ khóa khác trong Kotlin. Cụm từ khóa này thông báo cho trình biên dịch hiểu rằng biến được khai báo
 * với by lazy chưa được khởi tạo giá trị lúc chương trình khởi chạy, thay vào đó giá trị của biến sẽ được khởi tạo ở
 * lần truy cập đầu tiên đến biến đó.
 *
 * Biến được khai báo bởi by lazy có thể là NonNullable, hoặc cũng có thể là Nullable
 *
 * Tuy nhiên, by lazy phải là một Immutable , điều đó đồng nghĩa suốt vòng đời của biến chỉ mang 1 giá trị duy nhất,
 * chính là giá trị được khởi tạo ở lần truy cập đầu tiên.
 */
class Person {
    val name: String by lazy {
        println("Initializing name...")
        "Dan Tech 0xFF"
    }
}

fun main() {
    val person = Person()
    println(person.name) // This will print "Initializing name..." then "Dan Tech 0xFF"
}