package org.example.control_flow

fun main() {
    println("Enter the start value:")
    val start = readLine()?.toIntOrNull()
    println("Enter the end value:")
    val end = readLine()?.toIntOrNull()

    // for loop
    if (start != null && end != null && start <= end) {
        for (i in start..end) {
            println("Current number is: $i")
        }
    } else {
        println("Invalid input. Please enter valid start and end values where start is less than or equal to end.")
    }

    // while loop
    if (start != null && end != null && start <= end) {
        var i = start
        while (i <= end) {
            println("Current number is: $i")
            i++
        }
    } else {
        println("Invalid input. Please enter valid start and end values where start is less than or equal to end.")
    }
}