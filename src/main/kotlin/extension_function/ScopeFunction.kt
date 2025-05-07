package org.example.extension_function

/**
 * Scope Function là 1 dạng của Extension function, hỗ trợ các phương thức rút gọn khi thao tác lên 1 Kotlin Object.
 */

data class Person(var name: String, var age: Int = 0, var city: String = "")

fun main() {
    // Cách sử dụng apply
    val person = Person("John")
    println("Before update: Hello, $person")
    person.apply {
        age = 30
        city = "New York"
    }.apply {
        name = "Alice"
    } // lưu ý: apply trả về chính receiver object
    println("After update: Hello, $person")

    // Cách sử dụng also
    val numbers = mutableListOf("one", "two", "three")
    numbers.also {
        it.add("four")
        println("The list elements before adding new one: $it")
    }.add("five")
    // Lưu ý: also() nhận vào chính receiver và trả về receiver object
    // Lưu ý: add() trả về Unit
    println("The list elements after adding new one: $numbers")

    // Cách sử dụng let
    val listWithNulls: List<String?> = listOf("Kotlin", null)
    for (item in listWithNulls) {
        val res = item?.let {
            println(it)
            "Printed item"
        } ?: "Null item"
        // Lưu ý: let trả về statement cuối cùng trong block
        println("Result: $res")
    }
}