package org.example.delegate

/**
 * - Mẫu thiết kế ủy quyền được chứng minh là mang lại hiệu quả tốt và thường được sử dụng để triển khai kế thừa trong
 * Kotlin mà không cần sử dụng nhiều mã soạn sẵn.
 * - Ở ví dụ sau lớp Derived có thể triển khai một interface Base bằng cách ủy quyền tất cả các thành
 * phần public cho một đối tượng được chỉ định.
 */
interface Base {
    fun sayHello()
}

class BaseImpl(private val name: String) : Base {
    override fun sayHello() {
        println("Xin chào bạn $name!")
    }
}

class Derived(b: Base) : Base by b {
    // Ghi đè một thành phần của interface
    // - Ghi đè vẫn hoạt động như bạn mong muốn:
    // trình biên dịch sử dụng triển khai trong override thay vì sử dụng phiên bản gốc trong đối tượng được ủy quyền.
    // - Lưu ý rằng các thành phần được ghi đè theo cách này không thể được gọi từ các thành phần trong
    // đối tượng ủy quyền, tức đối tượng base trong ví dụ trên.
    override fun sayHello() {
        println("Hello Kotlin!!!!!")
    }
}

/**
 * - Với các thuộc tính phổ biến, tuy rằng ta có thể triển khai chúng nhiều lần mỗi khi cần,
 * nhưng tốt hơn là ta nên triển khai chúng 1 lần duy nhất và thêm chúng vào thư viện sau đó tái sử dụng chúng khi cần.
 *
 * Ví dụ:
 * - Các thuộc tính lười biếng: giá trị được tính toán chỉ 1 lần đầu tiên khi chúng được gọi.
 * - Các thuộc tính có thể quan sát được: các trình lắng nghe sẽ được thông báo mỗi khi có thay đổi xảy ra với
 * thuộc tính loại này.
 * - Lưu trữ các thuộc tính trong một đối tượng kiểu map thay vì sử dụng các trường riêng lẻ cho mỗi thuộc tính.
 * - Để giải quyết các trường hợp kể trên, Kotlin hỗ trợ thuộc tính delegate.
 * class Example {
 *     var p: String by Delegate()
 * }
 *
 * - Cú pháp:
 *      val/var propertyName: Type by expression
 * - Trong đó:
 *      + val/var là keyword chỉ định khả năng bị sửa đổi của thuộc tính.
 *      + propertyName là tên thuộc tính.
 *      + Type là kiểu của thuộc tính.
 *      + by là keyword mặc định.
 *      + expression là biểu thức delegate. Vì phương thức get()/set() tương ứng với thuộc tính sẽ được ủy quyền cho
 *      phương thức getValue()/setValue(). Mặt khác thuộc tính ủy quyền không triển khai interface nhưng phải cung
 *      cấp phương thức getValue()/setValue() cho một biến x nào đó.
 */

fun main() {
    // Mệnh đề by trong kiểu cha được liệt kê cho Derived chỉ ra rằng b sẽ được lưu trữ bên trong đối tượng của
    // kiểu Derived và trình biên dịch sẽ sinh tất cả các phương thức của Base để chuyển tiếp cho b.
    val base = BaseImpl("Mai")
    val other = BaseImpl("Nhung")
    // sử dụng đối tượng ủy quyền
    base.sayHello()
    Derived(base).sayHello()
    Derived(other).sayHello()
}