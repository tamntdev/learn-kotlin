package org.example.oop

/**
 * Tính kế thừa – Inheritance trong Kotlin
 * - Muốn kế thừa được phải có từ khóa open,
 * - Có thể dùng open cho class, variable, fun, lambdas
 * - super dùng để call parent
 * Kế thừa (Inheritance) là khả năng giúp 1 Class sử dụng lại các thuộc tính (properties) và hàm (method, behavior)
 * từ 1 Class khác.
 *
 * Khác với C++, Kotlin đơn giản hóa việc kế thừa bằng cách không phân chia ra các loại kế thừa khác nhau như public,
 * hay private. Trong Kotlin, khi nhắc đến kế thừa, chỉ có kế thừa – nothing more.
 *
 * Access Modifier Base Class	                    Access Modifier Sub Class
 *          private	                                    NOT INHERITANCE
 *          protected	                                protected
 *          internal	                                internal (Nếu Sub Class nằm khác module thì NOT INHERITANCE)
 *          public	                                    public
 */
// Để một Class có thể kế thừa được Lập trình viên phải thêm từ khóa open trước định nghĩa Class
open class SampleClass(internal: Int) {
    // thuộc tính có thể được kế thừa
    open var publicAttr: String = "public attr"
    internal var internalAttr: String = "internal attr"
    protected var protectedAttr: String = "protected attr"
    private var privateAttr: String = "private attr"
    var internal: Int
        private  set

    init {
        this.internal = internal
    }

    open val lambdaOperator: (String, String) -> String = { s1, s2 ->
        "$s1 $s2"
    }

    open fun print() {
        println("Call from sample class")
    }

    fun encodeStringWithKey(key: String, input: String): String {
        return lambdaOperator.invoke(key, input)
    }
}

class SampleSubClass : SampleClass(internal = 0) {
    fun printAttrs() {
        println(publicAttr) // Có thể truy cập
        println(internalAttr) // Có thể truy cập trong cùng module
        println(protectedAttr) // Có thể truy cập trong lớp con
        // println(privateAttr) // Lỗi: không thể truy cập privateAttr
    }

    override fun print() {
        println("---------------------------")
        super.print()
        println("Call from sample subclass")
    }

    override var publicAttr: String
        get() = super.publicAttr
        set(value) {}

    override val lambdaOperator: (String, String) -> String
        get() = {s1, s2 ->
            "$s2 $s1 $s2"
        }
}

// Ngoài ra còn có thể kế thừa từ sealed class hoặc abstract class
sealed class SampleSealedClass
abstract class SampleAbstractClass

class SubSealedClass : SampleSealedClass()
class SubAbstractClass : SampleAbstractClass()

// Cú pháp kế thừa
// Kotlin chặt chẽ trong việc xác định một Class có kế thừa được hay không.
// Để khai báo một class có khả năng kế thừa bạn cần đặt thêm keyword open trước khai báo class.
// Để một function của class cha có thể được override ở class con thì function đó cũng phải được khai báo với keyword open
// Ngoài việc override function thì trong Kotlin cũng có thể override property của class cha bằng cách sử dụng từ khóa
// open trước thuộc tính cần được override
open class OpenBaseClass {
    private val privateVar = 1
    protected val protectedVar = 2
    internal val internalVar = 3
    val publicVar = 4

    private fun privateFunction() {
        println("Private Function")
    }

    protected fun protectedFunction() {
        println("Protected Function")
    }

    internal fun internalFunction() {
        println("Internal Function")
    }

    fun publicFunction() {
        println("Public Function")
    }
}
class SubClass : OpenBaseClass() {
    fun callPrivateVar() {
        // Not able to access privateVar
    }
    fun callPublicVar() {
        println(publicVar)
    }
    fun callProtectedVar() {
        println(protectedVar)
    }
    fun callInternalVar() {
        println(internalVar)
    }
    fun callPrivateFunction() {
        // Not able to access privateFunction
    }
    fun callPublicFunction() {
        publicFunction()
    }
    fun callProtectedFunction() {
        protectedFunction()
    }
    fun callInternalFunction() {
        internalFunction()
    }
}

fun main() {
    val sample = SampleClass(internal = 10)
    println(sample.publicAttr) // Prints: public attr
    println(sample.internalAttr) // Prints: internal attr
    // println(sample.protectedAttr) // Lỗi: không thể truy cập từ ngoài lớp
    // println(sample.privateAttr) // Lỗi: không thể truy cập từ ngoài lớp
//    sample.printPrivateAttr() // Prints: private attr

    val subSample = SampleSubClass()
    subSample.printAttrs()
    // Prints:
    // public attr
    // internal attr
    // protected attr
}