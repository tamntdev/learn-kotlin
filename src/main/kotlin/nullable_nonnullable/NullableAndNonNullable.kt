package org.example.nullable_nonnullable

class MyKotlinObject {
    val myKotlinProperty: String = "Hello, Kotlin!"
    val myNullKotlinProperty: String? = null
}

fun main() {
    val myNonNullKotlinObject = MyKotlinObject()
    println("myNonNullKotlinObject.myKotlinProperty: ${myNonNullKotlinObject.myKotlinProperty}")
    println("myNonNullKotlinObject.myNullKotlinProperty: ${myNonNullKotlinObject.myNullKotlinProperty}")
    val myNullKotlinObject: MyKotlinObject? = null
    println("myNullKotlinObject?.myKotlinProperty: ${myNullKotlinObject?.myKotlinProperty}")
    println("myNullKotlinObject?.myNullKotlinProperty: ${myNullKotlinObject?.myNullKotlinProperty}")
}