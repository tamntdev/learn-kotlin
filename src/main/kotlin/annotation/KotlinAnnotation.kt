package org.example.annotation

import kotlin.reflect.KClass

/**
 * - Annotation hay chú thích có nghĩa là gắn kèm siêu dữ liệu vào code.
 * - Để khai báo một annotation, đặt keyword annotation trước tên một class. Ví dụ:
 * annotation class Serializable
 * - Các thuộc tính bổ sung của annotation có thể được chỉ định bằng cách sử dụng lớp meta-annotation:
 *      + @Target chỉ định các kiểu phần tử có thể được đánh dấu với annotation như các lớp, hàm, thuộc tính, biểu thức.
 *      + @Retention chỉ định liệu rằng một annotation được lưu trữ trong file đã biên dịch của lớp và
 *      liệu rằng nó có thể được nhìn thấy qua reflection tại runtime. Theo mặc định cả hai là true.
 *      + @Repeatable cho phép sử dụng cùng annotation trên một phần tử đơn nhiều lần.
 *      + @MustBeDocumented chỉ định rằng annotation là một phần của API được công bố và nên được mô tả trong
 *      dấu hiệu của lớp hoặc phương thức hiển thị trong tài liệu API được sinh ra.
 * Ví dụ:
 */
@Target(
    AnnotationTarget.CLASS,
    AnnotationTarget.FUNCTION,
    AnnotationTarget.TYPE_PARAMETER,
    AnnotationTarget.VALUE_PARAMETER,
    AnnotationTarget.EXPRESSION
)
@Retention(AnnotationRetention.SOURCE)
@MustBeDocumented
annotation class Fancy

/**
 * Cách sử dụng
 * Sau khi có annotation, ta có thể sử dụng nó để đánh dấu các thành phần: class, interface, các hàm, trường,
 * tham số, giá trị trả về…
 * Khi sử dụng ta cần đặt @ trước tên annotation và đặt chúng trước tên thành phần muốn sử dụng.
 * Ví dụ: @Fancy là gọi tới một annotation có tên Fancy đã định nghĩa trước đó.
 */
@Fancy class Foo {
    @Fancy fun baz(@Fancy foo: Int): Int {
        return (@Fancy 1)
    }
}

/**
 * Nếu cần đánh dấu một constructor chính với annotation, ta cần bổ sung keyword constructor vào và đặt
 * @annotation trước keyword constructor:
 * class Foo @Inject constructor(dependency: MyDependency) { ... }
 *
 * Ta thậm chí có thể chú thích hàm set của thuộc tính với annotation:
 * class Foo {
 *     var x: MyDependency? = null
 *         @Inject set
 * }
 */

// Ví dụ khai báo và sử dụng annotation
// khai báo annotation
//@Retention(AnnotationRetention.RUNTIME)
//@Target(AnnotationTarget.FIELD)
//annotation class JsonElement(val key: String = "")
//
// sử dụng:
//@JsonSerializable
//class Employee(
//    @field:JsonElement("id") private val empId: String,
//    first: String?, last: String?, midd: String?,
//    @field:JsonElement("email") private val email: String,
//    @field:JsonElement("salary") private val salary: Float,
//    @field:JsonElement("address") private val address: String
//) {
//    @JsonElement("full_name")
//    private val fullName: FullName = FullName(first, last, midd)
//
//    internal class FullName(
//        @field:JsonElement("first") private val firstName: String?,
//        @field:JsonElement("last") private val lastName: String?,
//        @field:JsonElement("midd") private val middName: String?
//    )
//}

/**
 * Các constructor
 * Annotation có thể chứa constructor nhận vào nhiều tham số:
 * @Retention(AnnotationRetention.RUNTIME)
 * @Target(AnnotationTarget.FIELD)
 * annotation class JsonElement(val key: String = "")
 *
 * Mục đích của các tham số là cung cấp các giá trị dữ liệu bổ sung cho annotation. Ví dụ ta muốn đặt lại tên trường tham chiếu để lấy giá trị tương ứng từ key được chỉ định ra và gán cho thuộc tính của lớp trong cặp key-value của JSON như ví dụ trên.
 * Các kiểu tham số được phép sử dụng trong constructor của annotation:
 * Các kiểu nguyên thủy: Int, Long, Boolean…
 * Các String.
 * Các lớp như Employee::class.
 * Các enum.
 * Các annotation khác.
 * Mảng của các kiểu được liệt kê ở trên.
 * Tham số của annotation không thể nhận giá trị có thể null vì JVM không hỗ trợ lưu trữ giá trị null làm thuộc tính của annotation.
 * Nếu một annotation được sử dụng làm tham số của annotation khác, tên của annotation dùng làm tham số không chứa @ ở trước.
 */

annotation class ReplaceWith(val expression: String)

annotation class Deprecated(
    val message: String,
    val replaceWith: ReplaceWith = ReplaceWith(""))

@Deprecated("This function is deprecated, use === instead", ReplaceWith("this === other"))

//Nếu ta cần chỉ định một lớp làm tham số trong annotation, sử dụng kiểu KClass:
annotation class Ann(val arg1: KClass<*>, val arg2: KClass<out Any>)

@Ann(String::class, Int::class) class MyClass

// Khởi tạo annotation
// Ta có thể tạo đối tượng của annotation sau đó sử dụng đối tượng đó:
annotation class InfoMarker(val info: String)

fun processInfo(marker: InfoMarker): Unit = TODO()

// Annotation có thể được sử dụng trong biểu thức lambda.
// Loại này hữu ích với các thư viện như Quasar trong đó sử dụng annotation để kiểm soát đa luồng song song.
//annotation class Suspendable
//
//val f = @Suspendable { Fiber.sleep(10) }

/**
 * Những nơi sử dụng annotation
 * Khi ta chú thích một thuộc tính hoặc một tham số trong constructor chính, có rất nhiều phần tử Java được sinh ra từ các phần tử Kotlin tương ứng.
 * Các annotation thường được sử dụng nhắm mục tiêu vào:
 * file
 * property (thuộc tính)
 * field
 * get/set(getter/setter của thuộc tính)
 * receiver
 * parameter(tham số của constructor)
 * setparam
 * delegate.
 * Để chú thích tham số receiver của hàm mở rộng, sử dụng cú pháp:
 * fun @receiver:Fancy String.myExtension() { ... }
 * Nếu ta không chỉ định target cụ thể, mục tiêu sẽ nhắm vào thành phần được chỉ định trong @Target annotation của annotation đang được sử dụng.
 * Nếu có nhiều target, target đầu tiên trong danh sách sau sẽ được sử dụng:
 * param
 * property
 * field
 *
 *
 *
 * Các Java annotation
 * - Toàn bộ các annotation trong Java đều tương thích hoàn toàn trong Kotlin:
 * - Trong khi thứ tự của các tham số trong annotation của Java không được chỉ định, ta cần sử dụng cú pháp tên đối số.
 * // Kotlin
 * @Ann(intValue = 1, stringValue = "abc") class C
 *
 * - Trường hợp đặc biệt của tham số value có thể bỏ qua tên tường minh:
 * // Kotlin
 * @Ann(intValue = 1, stringValue = "abc") class C
 *
 * // Kotlin
 * @AnnWithValue("abc") class C
 *
 * - Với tham số kiểu mảng trong annotation, trong Kotlin sẽ trở thành tham số vararg:
 * // Java
 * public @interface AnnWithArrayValue {
 *     String[] value();
 * }
 *
 * // Kotlin
 * @AnnWithArrayValue("abc", "foo", "bar") class C
 *
 * - Với các tham số khác có kiểu mảng, ta cần sử dụng cú pháp chuỗi kí tự hoặc arrayOf(…):
 * // Java
 * public @interface AnnWithArrayMethod {
 *     String[] names();
 * }
 *
 * // Kotlin
 * @AnnWithArrayMethod(names = ["abc", "foo", "bar"])
 * class C
 *
 * - Truy cập vào các thuộc tính từ một đối tượng annotation:
 * // Java
 * public @interface Ann {
 *     int value();
 * }
 *
 * // Kotlin
 * fun foo(ann: Ann) {
 *     val i = ann.value
 * }
 */

/**
 * Các annotation có thể lặp lại
 * Giống như Java, Kotlin cũng hỗ trợ các annotation lặp đi lặp lại, thứ có thể áp dụng vào một phần tử đơn lặp lại nhiều lần.
 * Để cho phép một annotation có thể lặp lại, ta gắn @Repeatable cho annotation đó.
 * Làm vậy sẽ cho phép nó lặp lại ở cả Kotlin và Java. Java annotation lặp lại cũng được hỗ trợ trong Kotlin.
 */
@Repeatable
annotation class Tag(val name: String)