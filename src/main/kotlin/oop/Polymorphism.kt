package org.example.oop

/**
 * Tính đa hình – Polymorphism trong OOP thể hiện qua việc các Class khác nhau,
 * kế thừa chung 1 Class, Interface có thể cùng gọi đến 1 hàm
 * (thuật ngữ gọi là cùng giao diện/Interface) nhưng lại có thể thực hiện các Logic khác nhau
 * tùy vào từng nơi khai báo.
 * Hiểu đơn giản là Đa hình – cùng 1 hình thức nhưng nội dung bên trong khác nhau.
 *
 * Overload
 * Overload là việc định nghĩa các phương thức (hàm) trong Class có cùng tên nhưng khác nhau về các input params.
 * Các kiểu dữ liệu trả về của các phương thức này có thể giống hoặc khác nhau tùy ý.
 *
 *
 * Override
 * Override: Định nghĩa các phương thức (hàm) trong Class con được kế thừa từ Class cha,
 * và thay đổi hoàn toàn logic bên trong nó.
 *
 * Trong Kotlin để có thể override lại các function hoặc các biến của Class cha.
 * Bạn phải thêm từ khóa open trước khai báo các nội dung đó.
 */

// Ví dụ overload các function trong class
class Calculator {
    fun add(a: Int, b: Int): Int {
        return a + b
    }

    fun add(a: Double, b: Double): Double {
        return a + b
    }

    fun add(vararg numbers: Int): Int {
        var sum = 0
        for (number in numbers) {
            sum += number
        }
        return sum
    }
}

// Overload trong interface
interface Encryptor {
    fun encrypt(input: String): String
    fun encrypt(input: Int): Int
    fun encrypt(input: Char): Char
}

// Override
open class Animal {
    open fun makeSound() {
        println("Tiếng động")
    }
}

class Dog : Animal() {
    override fun makeSound() {
        println("Gâu gâu")
    }
}

class Cat : Animal() {
    override fun makeSound() {
        println("Meow meow")
    }
}
// Ví dụ về Override trong Kotlin
open class Shape {
    open fun calculateArea(): Double {
        return 0.0
    }
}

// Ví dụ vê Override trong Kotlin
class Circle(val radius: Double) : Shape() {
    override fun calculateArea(): Double {
        return Math.PI * radius * radius
    }
}

class Rectangle(val width: Double, val height: Double) : Shape() {
    override fun calculateArea(): Double {
        return width * height
    }
}

fun main() {
    val animals = listOf(Dog(), Cat())

    animals.forEach { animal ->
        animal.makeSound()
    }
}

open class BaseClass {
    open var name: String = "BaseClass"
    open fun print() {
        println("Hello from BaseClass1")
    }
}

// Override với open class
class SubClass1 : BaseClass() {
    override var name: String = "SubClass" // kotlin cho phép bạn override properties
        get() = super.name
        set(value) {
            field = value
        }

    override fun print() {
        super.print() // -> call hàm của lớp BaseClass
        println("Hello from SubClass")
    }
}

// Ngoài ra bạn cũng có thể sử dụng override cho abstract class, hoặc interface
abstract class MyAbstractClass {
    open val name: String = "Name"
    abstract val age: Int
    abstract fun myFunction()
    open fun myOpenFunction() {
        println("Hello from myEmptyFunction")
    }

    fun myNormalFunction() {
        println("Hello from myNormalFunction")
    }
}

class MySubClass : MyAbstractClass() {
    override val age: Int = 10
    override fun myFunction() {
        println("Hello from myFunction")
    }

    override fun myOpenFunction() {
        super.myOpenFunction()
    }

    override val name: String
        get() = super.name
}

// Override với Interface
interface MyInterface1 {
    val name: String // interface của Kotlin có thể chứa member variable
    fun myFunction() {
        // interface của Kotlin có thể chứa hàm đã được định nghĩa sẵn
        println("Hello from MyInterface")
    }

    // interface của Kotlin có thể chứa hàm chưa được định nghĩa
    fun myEmptyFunction()
}

class MyClass1 : MyInterface1 {
    // cách override trong Implementation của Interface
    override val name: String = "MyClass"
    override fun myFunction() {
        super.myFunction()
        println("Hello from MyClass")
    }

    override fun myEmptyFunction() {
        println("Hello from myEmptyFunction")
    }
}

/**
 * Điều khác biệt giữa kế thừa từ Class và Interface là gì?
 * -> 1 Class chỉ có thể kế thừa từ 1 Class, nhưng có thể implement nhiều Interface.
 *
 * Khi không muốn cho các module ngoài kế thừa Class, Interface bạn cần làm gì?
 * -> Sử dụng sealed Class, Interface
 *
 * Trong Kotlin abstract class và class khác biệt gì?
 * -> class có thể được dùng để tạo object trực tiếp, abstract class thì không được.
 * Lưu ý: Interface, abstract Class vẫn có thể tạo được object thông qua object expression.
 * Cách này không nên áp dụng cho các inteface, abstract class lớn. Vì sẽ làm code trở nên phức tạp, khó đọc, khó debug.
 */