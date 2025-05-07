package org.example.control_flow

fun main() {
    // readLine() is used to read input from the user
    val number: Int = readlnOrNull()?.toIntOrNull() ?: 0

    println("Simple if statement")
    if (number > 5) {
        println("$number is greater than 5")
    } else {
        println("$number is not greater than 5")
    }

    println("Advanced if statement")
    val checkNumberString = if (number > 5) {
        "$number is greater than 5"
    } else {
        "$number is not greater than 5"
    }

    // out standing if
    println(
        if (number > 5) {
            "$number is greater than 5"
        } else {
            "$number is not greater than 5"
        }
    )

    val obj: Any = "Hello"

    // Simple when
    when (obj) {
        is String -> println("The object is a String. Value: $obj")
        is Int -> println("The object is an Integer. Value: $obj")
        else -> println("The object is of an unknown type.")
    }

    // Advanced when
    val ret = when (obj) {
        is String -> "The object is a String. Value: $obj"
        is Int -> "The object is an Integer. Value: $obj"
        else -> "The object is of an unknown type."
    }
}