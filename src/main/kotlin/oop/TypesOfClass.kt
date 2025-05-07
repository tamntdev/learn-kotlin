package org.example.oop

// Khai báo class
//class MyClass {
//    // Define các member của bạn
//}

class Empty // khai báo một class không có gì bên trong

// Lưu ý: Default Access Modifier trong Kotlin là Public
// Constructor
class MyClass(val intProp: Int) {
    fun samplePublicFunction() {
        println("Hello world!")
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || other !is MyClass) return false

        return intProp == other.intProp
    }

    override fun hashCode(): Int {
        return intProp.hashCode()
    }
}

class MyAnotherClass constructor(val intProp: Int) {
    fun anotherPublicFunction() {
        println("Hello world 2!")
    }
}

class MyClassWithMultipleConstructor {
    var stringProp: String = ""
    var intProp: Int = 0

    constructor(intProp: Int) {
        this.intProp = intProp
    }

    constructor(stringProp: String) {
        this.stringProp = stringProp
    }
}

/**
 * Destructor – Hàm hủy
 * Cơ chế cấp phát và quản lý memory của Kotlin sử dụng GC (Garbage Collector) khác biệt với ngôn ngữ C++
 *
 * Chính vì sử dụng GC nên trong Kotlin ta không cần phải chủ động delete các đối tượng đã được tạo ra.
 * GC sẽ tự phát hiện và delete các object đã được tạo ra khi chúng không còn được sử dụng nữa.
 *
 * So sánh object
 * Koltin có hỗ trợ sẵn 2 toán tử so sánh là == và ===
 *
 * == So sánh giá trị, so sánh content
 * === So sánh địa chỉ (reference) mà biến đó trỏ đến
 * Các Class trong kotlin nếu được khai báo bằng cú pháp mặc định class Myclass sẽ không bằng nhau trong các
 * phép so sánh sử dụng toán tử == hoặc === cho dù các element bên trong chúng bằng nhau.
 *
 * Làm cách nào để chúng bằng nhau trong trường hợp các properties bên trong bằng nhau?
 *
 * Giải pháp là override lại hàm equals và hàm hashCode để dễ dàng so sánh.
 * Chính vì sự bất tiện này mà Kotlin đã sinh ra data class
 *
 *
 */

/**
 * Kotlin interface
 * Điều đặc biệt ở Interface trong Kotlin là nó có thể chứa được cả các function được định nghĩa sẵn, hoặc không,
 * và chứa được cả các biến. Interface trong Kotlin đa dụng và tiện lợi hơn C++ rất nhiều.
 */
interface MyUserInterface {
    var name: String
    var age: Int
    fun myFunction() {
        println("Hello From Interface User")
    }
}

class ProUser(
    override var name: String,
    override var age: Int
) : MyUserInterface {
    override fun myFunction() {
        println("Hello")
    }
}

/**
 * Kotlin Data Class
 * Kotlin có khái niệm Data Class dùng để chỉ các Class các tác dụng chứa dữ liệu – only Data & no Logic.
 *
 * Các đặc trưng của data class trong Kotlin
 *
 * Hàm println sẽ in ra nội dung của object được tạo bởi data class thay vì địa chỉ của nó.
 * So sánh == giữa 2 object khác nhau được tạo bởi data class sẽ so sánh nội dung của mỗi object.
 * Nếu chúng có chung giá trị sẽ được return TRUE
 * So sánh === giữa 2 object khác nhau được tạo bởi data class sẽ return FALSE. Vì chúng thuộc 2 vùng nhớ khác nhau.
 */
data class MyUserDataClass(val name: String, var age: Int)
// Data class có thể chứa các biến thành viên là var hoặc val

/**
 * Kotlin Enum Class
 * Enum là 1 khái niệm không mới trong ngôn ngữ lập trình hiện đại.
 * Enum được dùng trong các logic để phân loại vì tính tiện dụng của nó.
 *
 * Trong Kotlin, enum không có cách khai báo riêng biệt. Muốn khai báo một enum ta phải khai báo thông qua enum class
 */
enum class Direction {
    NORTH, SOUTH, WEST, EAST
}
enum class UserTier {
    FREE, PREMIUM
}
enum class MessageState(val value: String) {
    FAILED("failed"),
    SENDING("sending"),
    RECEIVED("received"),
    READ("read")
}
enum class MyUserEnum(val role: String) {
    ADMIN("admin"),
    USER("user"),
    GUEST("guest")
}

/**
 * Kotlin Sealed Class, Interface
 * Sealed Class, Interface trong Kotlin được tạo ra với mục đích ngăn chặn sự kế thừa từ Class, Interface sau khi
 * code đã được compile.
 */

/**
 * Kotlin Object
 * Kotlin Object là giải pháp cho Singleton pattern trong ngôn ngữ Kotlin.
 *
 * Trong ứng dụng của bạn sẽ có lúc cần những dữ liệu chỉ có 1 Instance duy nhất trên phạm vi toàn ứng dụng.
 * Kotlin Object sẽ giúp làm việc đó trở nên đơn giản hơn.
 * Kotlin object cung cấp giải pháp Thread-safe cho Singleton pattern.
 * Điều này có nghĩa là trong mọi trường hợp chương trình luôn tạo 1 và chỉ 1 Instance của 1 Kotlin object.
 */
object FilePathProvider {
    val cachePath: String = "file://cache"
    val diskPath: String = "file://disk"
}

fun main() {
    // Khởi tạo đối tượng
    val a = MyClass(1)
    a.samplePublicFunction()
    var b = MyAnotherClass(0)
    b.anotherPublicFunction()

    val myClass1 = MyClass(1)
    val myClass2 = MyClass(1)
    println(myClass2 == myClass1) // false
    println(myClass2 === myClass1) // false

    println(myClass2 == myClass1) // false
    println(myClass2 === myClass1) // false
    println(myClass1.hashCode() == myClass2.hashCode()) // false

    val user: MyUserInterface = ProUser("Pro", 20)
    println("User Info: ${user.name} ${user.age}")
    println("User Info: $user") // chỉ in ra địa chỉ của biến, không phải giá trị của nội dung

    val myUserDataObject = MyUserDataClass("John", 25)
    val myUserDataObject2 = MyUserDataClass("John", 25)
    println("User Name: $myUserDataObject")
    println("myUserDataObject == myUserDataObject2: ${myUserDataObject == myUserDataObject2}")
    println("myUserDataObject.hashCode == myUserDataObject2.hashCode: ${myUserDataObject.hashCode() == myUserDataObject2.hashCode()}")
    println("myUserDataObject === myUserDataObject2: ${myUserDataObject === myUserDataObject2}")

    MyUserEnum.entries.forEach {
        println("string value: $it")
        println("role of enum:" + it.role)
        println("name of enum" + it.name)
        println("ordinal of enum: " + it.ordinal)
        println("---")
    }
}
