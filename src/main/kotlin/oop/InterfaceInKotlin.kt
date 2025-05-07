package org.example.oop

/**
 * - Interface là một kiểu đặc biệt chỉ sử dụng để mô tả các hành động, các thao tác, hằng số.
 * - Interface thể hiện tính trừu tượng trong lập trình hướng đối tượng.
 * - Interface hoàn toàn trừu tượng và do đó không chứa trạng thái, tức chỉ chứa các hành động của đối tượng.
 * - Interface có thể chứa các phương thức abstract, phương thức chứa triển khai mặc định và có thể chứa các thuộc
 * tính nhưng chúng phải abstract hoặc cung cấp sẵn triển khai của accessor.
 * - Sử dụng interface để nêu lên các hành động chung nhưng không chỉ rõ cách thức thực hiện.
 * Ta thường sử dụng chúng trong các callback.
 * - Mặc định các thành phần trong interface là public, abstract.
 *
 * - Một lớp hoặc một đối tượng có thể triển khai một hoặc nhiều interface.
 * - Khi triển khai interface, lớp con phải triển khai tất cả các phương thức abstract trong interface,
 * nếu không lớp con đó phải là abstract class.
 *
 * - Một lớp có thể vừa kế thừa lớp khác vừa triển khai các interface.
 * - Trong quan hệ kế thừa, một lớp chỉ có thể có duy nhất 1 lớp cha trực tiếp trên nó nhưng có thể có
 * nhiều interface cha.
 * - Như vậy muốn triển khai đa kế thừa ta sẽ ưu tiên sử dụng interface.
 * - Sử dụng dấu phẩy để liệt kê các lớp cha, interface cha của một lớp khi kế thừa.
 * - Thường ta sẽ liệt kê lớp cha trực tiếp của lớp đang kế thừa đầu tiên, sau đó sẽ tới các interface.
 *
 * - Ta có thể khai báo các thuộc tính trong interface.
 * - Thuộc tính này có thể được triển khai với accessor mặc định hoặc là thuộc tính abstract.
 * - Thuộc tính khai báo trong interface không thể chứa các trường phụ trợ và do đó các accessor khai báo trong
 * interface không thể tham chiếu tới chúng
 *
 * - Một interface có thể kế thừa từ các interface cha khác.
 * - Interface con và cha đều có thể cung cấp các phương thức đã triển khai và các phương thức abstract.
 * - Các interface con có thể bổ sung thêm các phương thức, thuộc tính của riêng bản thân nó.
 * - Các lớp triển khai interface con ở trên chỉ phải định nghĩa chi tiết phần thân các phương thức và
 * thuộc tính abstract.
 *
 * - Khi các interface cha, lớp cha có chung phương thức trùng tên nhau sẽ xảy ra xung đột ghi đè.
 * - Lúc này ta không biết phương thức đang cần triển khai là của interface nào.
 * - Theo mặc định, các phương thức abstract bắt buộc phải được triển khai.
 * - Trường hợp có 2 hoặc nhiều interface cha có chứa các phương thức cùng tên,
 * ta cần chỉ rõ chính xác cách thức cần phải vận hành của phương thức đó trong lớp con.
 */

// interface mô tả các hành động có thể vẽ được
interface Drawable {
    val lineStyle: String // thuộc tính abstract
    val lineColor: String // thuộc tính có triển khai accessor
        get() = "RED"

    fun draw() // phương thức trừu tượng
    fun ringBell() {
        // phần thân tùy chọn
        println("Reee.eee...eeeng...")
    }
}

// interface mô tả các hành động kết nối, kiểm tra kết nối và ngắt kết nối
// tới một database(giả định)
interface DBConnection {
    fun connect(connString: String)
    fun disconnect()
    fun checkConnection()
}

// interface dùng để so sánh hai đối tượng kiểu bất kì
interface Comparable {
    fun compare(obj: Any, other: Any)
}

// lớp triển khai interface DBConnection
//class DBConnectionImp : DBConnection {
//    // ...
//    override fun connect(connString: String) {
//        TODO("Not yet implemented")
//    }
//
//    override fun disconnect() {
//        TODO("Not yet implemented")
//    }
//
//    override fun checkConnection() {
//        TODO("Not yet implemented")
//    }
//}

// lớp triển khai không đầy đủ interface DBConnection
// do đó nó phải là abstract class
abstract class DBConnectionImp : DBConnection {
    // ...
    override fun connect(connString: String) {
        TODO("Not yet implemented")
    }

    override fun disconnect() {
        TODO("Not yet implemented")
    }
}

class RectangleClass(private val width: Int, private val height: Int) : Drawable {
    override val lineStyle: String // triển khai thuộc tính abstract c
        get() = "GREEN"

    override fun draw() {
        println("Vẽ hình chữ nhật với kích thước $width x $height")
        println("Quá trình vẽ hình chữ nhật đã hoàn tất.")
        ringBell()
    }

    override fun ringBell() {
        super.ringBell()
        println("Chuông reo leng keng...")
    }
}

open class Shape1(protected val x: Int, protected val y: Int) {
    open fun showInfo() {
        println("Tọa độ tâm ($x, $y)")
    }
}

// lớp kế thừa Shape, triển khai interface Drawable
class Rectangle1(x: Int, y: Int, private val width: Int, private val height: Int, override val lineStyle: String)
    : Shape1(x, y), Drawable {
    override fun draw() {
        println("Vẽ hình chữ nhật với kích thước $width x $height")
        println("Quá trình vẽ hình chữ nhật đã hoàn tất.")
        ringBell()
    }

    override fun ringBell() {
        super.ringBell()
        println("Chuông reo leng keng...")
    }

    override fun showInfo() { // ghi đè phương thức showInfo của Shape
        println("Hình chữ nhật có kích thước $width x $height")
        super.showInfo()
    }
}

// interface thể hiện hành động tô màu
interface Colorable {
    var color: String // thuộc tính riêng của interface Colorable
    fun paint(color: String) // đổ màu cho hình khối
    fun mixColor(color: String, other: String) // trộn màu
}

// interface thể hiện khả năng co kéo
interface Scalable {
    var scaleAmount: Float // thuộc tính riêng của interface Colorable
    fun scale(amount: Float) // co kéo hình khối
}

// interface thể hiện khả năng có thể vẽ được
interface Drawable1 : Colorable, Scalable {
    val lineStyle: String // thuộc tính abstract
    val lineColor: String // thuộc tính có triển khai accessor
        get() = "RED"

    fun draw() // phương thức trừu tượng

    fun ringBell() {
        // phần thân tùy chọn
        println("Reee.eee...eeeng...")
    }
}
// lớp kế thừa Shape, triển khai interface Drawable và các interface cha liên quan
class Rectangle2(x: Int, y: Int, private val width: Int, private val height: Int)
    : Shape1(x, y), Drawable1 {
    override val lineStyle: String // triển khai thuộc tính abstract c
        get() = "GREEN"

    override fun draw() {
        println("Vẽ hình chữ nhật với kích thước $width x $height")
        println("Quá trình vẽ hình chữ nhật đã hoàn tất.")
        ringBell()
    }

    override var color: String
        get() = TODO("Not yet implemented")
        set(value) {}

    override fun paint(color: String) {
        // ...
    }

    override fun mixColor(color: String, other: String) {
        // ...
    }

    override var scaleAmount: Float
        get() = TODO("Not yet implemented")
        set(value) {}

    override fun scale(amount: Float) {
        // ...
    }
    // ...
}

interface AnimalMove {
    fun move(by: String)
}

interface VehicleMove {
    fun move(by: String)
}

class Person : AnimalMove, VehicleMove {
    override fun move(by: String) {
        println("Con người di chuyển bằng $by")
    }
}

interface BirdMove {
    fun move(by: String) {
        println("Các loại chim di chuyển bằng $by")
    }
}

interface CarMove {
    fun move(by: String) {
        println("Xe ô tô con di chuyển bằng $by")
    }
}

class Superman : BirdMove, CarMove {
    override fun move(by: String) {
        println("Siêu nhân di chuyển bằng $by")
        super<BirdMove>.move("Chân và cánh")
        super<CarMove>.move("4 bánh")
    }
}

/**
 * - Một interface chứa duy nhất 1 phương thức abstract được gọi là functional interface hay
 * còn có tên khác là SAM(Sing Abstract Method interface).
 * - Loại interface này có thể chứa vài thành phần không trừu tượng nhưng chỉ được phép chứa duy nhất 1 thành
 * phần trừu tượng.
 * - Ta có thể tạo functional interface với keyword fun đặt trước interface
 */
fun interface MyRunnable {
    fun run()
}

fun interface GpaValidator {
    fun check(gpa: Float): Boolean
}

// Nếu ta không sử dụng cú pháp chuyển đổi SAM ta có thể phải tạo lớp hoặc đối tượng triển khai interface trên:
// tạo lớp triển khai interface
class ValidatorImp : GpaValidator {
    override fun check(gpa: Float): Boolean {
        if (gpa in 0.0..4.0) {
            return true
        }
        return false
    }
}

// tạo đối tượng triển khai interface trên
val isValid = object : GpaValidator {
    override fun check(gpa: Float): Boolean {
        return gpa in 0.0..4.0
    }
}

// Áp dụng chuyển đổi SAM, tất cả cú pháp trở nên ngắn gọn:
val isValidGpa = GpaValidator { gpa -> gpa in 0.0..4.0 }

// hàm main tạo các đối tượng và khởi chạy chúng
fun main() {
//    val rectangle = RectangleClass(120, 360)
//    rectangle.draw()
//
//    val rectangle1 = Rectangle1(
//        0, 0, 120, 360,
//        lineStyle = ""
//    )
//    rectangle1.draw()
//    rectangle1.showInfo()

    val person = Person()
    person.move("Đi bộ và sử dụng các phương tiện giao thông.")

    val superman = Superman()
    superman.move("Siêu năng lực của chim và máy móc")
}