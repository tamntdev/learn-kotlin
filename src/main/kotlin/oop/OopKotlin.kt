package org.example.oop

/**
 * Kotlin hỗ trợ lập trình Hướng đối tượng với đầy đủ các tính năng:
 * Khai báo Class, Interface, Kế thừa, Đa hình.
 * Ngoài ra Kotlin còn hỗ trợ các tính năng riêng độc đáo của mình như data class,
 * sealed class, Object , Enum class
 */
enum class Direction1(val direction: String) {
    LEFT("LEFT"),
    RIGHT("RIGHT"),
    FORWARD("FORWARD"),
    BACKWARD("BACKWARD"),
    STOP("STOP")
}

interface Drivable {
    fun drive(direction: Direction)
}

interface Movable {
    fun speedUp()
    fun speedDown()
}

class Car : Drivable, Movable {
    private var speed: Int = 0

    companion object {
        const val MAX_SPEED = 60
        const val MIN_SPEED = 0
    }

    override fun drive(direction: Direction) {
        println("Driving the car turn $direction")
    }

    override fun speedUp() {
        speed++
        if (speed > MAX_SPEED) {
            speed = MAX_SPEED
        }
        println("Speeding up the car $speed")
    }

    override fun speedDown() {
        speed--
        if (speed < MIN_SPEED) {
            speed = MIN_SPEED
        }
        println("Speeding down the car $speed")
    }
}

data class User(val name: String, val age: Int)

sealed class Expr {
    data class Const(val number: Double) : Expr()
    data class Sum(val e1: Expr, val e2: Expr) : Expr()
    object NotANumber : Expr()
}

fun main() {
    // Using data class
    val user = User("Tom", 25)
    println(user)

    // Using sealed class
    val expr1: Expr = Expr.Const(10.0)
    val expr2: Expr = Expr.Sum(expr1, Expr.Const(20.0))
    val expr3: Expr = Expr.NotANumber

    val result = when (expr2) {
        is Expr.Const -> "Constant: ${expr2.number}"
        is Expr.Sum -> "Sum: ${expr2.e1} + ${expr2.e2}"
        Expr.NotANumber -> "Not a number"
    }

    println(result)
}