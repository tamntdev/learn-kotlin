package org.example.generics

// Hàm generic
fun <T> printArray(arr: Array<T>) {
    for (item in arr) {
        print("$item ")
    }
    println("\n============================")
}

fun <T> getTValue(list: List<T>, index: Int): T {
    if (index < list.size) {
        return list.get(index)
    }
    throw IndexOutOfBoundsException("Chỉ số vượt biên của danh sách.")
}

fun <K, V> getValueByKey(map: Map<K, V>, key: K): V {
    // ...
    return map.getValue(key)
}

// Trong trường hợp nếu ta chỉ muốn gán biến có tham số generic kiểu con cho biến có
// tham số generic kiểu cha hoặc tương đương, ta phải sử dụng keyword out:
// tức là có thể gán biến cho một biến khác vượt ngoài phạm vi kế thừa theo hướng đi lên.
interface Source<out T> {
    fun nextT(): T
}

fun demo(strs: Source<String>) {
    val objects: Source<Any> = strs // gán sang biến có tham số kiểu cha
    // ...
}

// Nếu ta chỉ muốn cho phép gán ngược lại, tức là gán biến có tham số generic kiểu cha cho biến có tham số generic của
// kiểu con hoặc kiểu tương đương, ta sử dụng keyword in:
// Nói cách khác, ta dùng keyword in để ám chỉ có thể gán biến tham số kiểu cha cho
// các biến có tham số kiểu con, trong nội bộ kế thừa từ thằng cha xuống.
interface Comparable<in T> {
    operator fun compareTo(other: T): Int
}

fun demo(x: Comparable<Number>) {
    x.compareTo(1.0) // 1.0 có kiểu là Double, là kiểu con của kiểu Number
    // do đó ta có thể gán x cho biến y kiểu Comparable<Double>
    val y: Comparable<Double> = x // OK
    // ...
}

// Sử dụng keyword out để cấm ghi dữ liệu vào một mảng:
fun fill(dest: Array<out String>, index: Int, value: String) {
//    dest[index] = value // lỗi
}

// Sử dụng keyword in để cho phép truyền dữ liệu có tham số kiểu >= kiểu được chỉ định vào hàm:
fun fill1(dest: Array<in String>, index: Int, value: String) {
    dest[index] = value
}

/**
 * Sử dụng dấu sao(*)
 * - Nếu ta không biết gì về kiểu của tham số nhưng vẫn muốn sử dụng các tham số kiểu một cách an toàn.
 * An toàn ở đây tức là định nghĩa một phép chiếu của kiểu generic,
 * trong đó mọi khởi tạo tường minh của kiểu generic đó sẽ là một kiểu con của phép chiếu.
 *
 * - Kotlin cung cấp cú pháp gọi là phép chiếu sao cho mục đích này.
 *  + Với cú pháp Foo<out T : TUpper>, trong đó TUpper là biên trên của T, Foo<*> tương đương Foo<out TUpper>.
 *  Điều này tức là khi chưa biết rõ T, ta có thể đọc giá trị của TUpper một cách an toàn từ Foo<*>.
 *
 *  + Với Foo<int T>, Foo<*> tương đương với Foo<in Nothing>.
 *  Điều này nghĩa là ta không thể ghi bất kì thứ gì vào Foo<*> một cách an toàn khi kiểu của T chưa được làm rõ.
 *  + Với Foo<T : TUpper>, Foo<*> tương đương với Foo<out TUpper> cho việc đọc giá trị và Foo<in Nothing> cho việc
 *  ghi các giá trị.
 *
 * - Nếu có nhiều tham số kiểu, một tham số sẽ có thể được chiếu một cách độc lập. Ví dụ:
 *    + Function<*, String> có nghĩa là Function<in Nothing, String>
 *    + Function<Int, *> có nghĩa là Function<Int, out Any?>
 *    + Function<*, *> có nghĩa là Function<in Nothing, out Any?>
 */

fun printArray1(arr: Array<*>) {
    for (item in arr) {
        print("$item ")
    }
    println("\n============================")
}

/**
 * Ràng buộc generic
 * Ràng buộc biên trên bởi keyword extends trong Java. Trong Kotlin, ràng buộc này thể hiện bằng dấu hai chấm.
 */
// chỉ các kiểu <= kiểu con của Number như
// Number, Int, Float, Double, Long... mới được chấp nhận
fun <T : Number> printArray2(arr: Array<T>) {
    for (item in arr) {
        print("$item ")
    }
    println("\n============================")
}

// Ví dụ khác: chỉ những kiểu triển khai interface Comparable<T> mới được chấp nhận truyền vào hàm sau:
fun <T : Comparable<T>> sort1(list: List<T>) {
    // ...
}

// Ví dụ: hàm sau chỉ đưa ra các phần tử lớn hơn một ngưỡng nào đó đã chỉ định trước.
// Kiểu truyền vào các hàm có nhiều ràng buộc về biên trên(upper bound) phải thỏa mãn tất cả các ràng buộc đã đưa ra.
// Trong ví dụ ngay phía trên, kiểu T phải thỏa mãn vừa triển khai Charsequence vừa triển khai Comparable.
fun <T> copyWhenGreater(list: List<T>, threshold: T): List<String>
        where T : CharSequence,
              T : Comparable<T> {
    return list.filter { it > threshold }.map { it.toString() }
}

/**
 * Loại bỏ kiểu
 * Việc kiểm tra an toàn kiểu trong Kotlin được thực hiện cho các khai báo generic được hoàn tất tại thời gian biên dịch.
 * Tại runtime, các phiên bản cụ thể của kiểu generic không chứa bất kì thông tin nào về tham số kiểu hiện thời của nó.
 * Lúc này, thông tin về kiểu được gọi là bị xóa, bị loại bỏ. Do đó các phiên bản đối tượng của Foo<Bar> hay Foo<Bar?> sẽ được xóa bỏ thành Foo<*>.
 * Do các tham số kiểu bị xóa tại runtime nên ta không có cách thông thường nào để kiểm tra xem một đối tượng của kiểu generic nào đó có được tạo ra hay không.
 * Trình biên dịch cấm các thao tác kiểm tra với toán tử is như ints is List<Int> hay list is T.
 * Tuy nhiên ta có thể thực hiện thao tác kiểm tra với toán tử chiếu sao:
 *
 * Các tham số kiểu của lời gọi hàm generic cũng chỉ được kiểm tra tại thời điểm biên dịch. Bên trong thân hàm, các tham số kiểu không thể được sử dụng cho việc kiểu tra kiểu và việc ép kiểu sang các tham số kiểu không được kiểm tra.
 * Trong phần thân của hàm generic, ta không thể truy cập tới kiểu T vì nó chỉ tồn tại khi biên dịch và sẽ bị xóa bỏ khi vào runtime. Do đó nếu ta muốn sử dụng kiểu generic như một lớp thông thường khác trong phần thân hàm, ta cần truyền tên cụ thể của class vào hàm generic.
 * Nếu ta tạo một hàm inline với một kiểu reified T, kiểu T có thể được truy cập tại runtime và do đó ta không cần truyền Class<T> vào làm tham số của hàm nữa.
 * Cú pháp:
 * inline fun <reified T> myGenericFun()
 *
 * Ví dụ:
 * inline fun <reified T: Any> String.toKotlinObject(): T {
 *     val mapper = jacksonObjectMapper()
 *     return mapper.readValue(this, T::class.java)
 * }
 *
 * Nếu không dùng keyword reified:
 *
 * fun <T> String.toKotlinObject(): T {
 *     val mapper = jacksonObjectMapper()
 *     // không biên dịch
 *     return mapper.readValue(this, T::class.java)
 * }
 */

/**
 * Toán tử gạch dưới
 * Toán tử gạch dưới _ có thể được sử dụng để thay thế cho các tham số kiểu. Sử dụng nó trong trường hợp cần tự động suy luận 1 kiểu khi những kiểu khác đã được chỉ định rõ ràng.
 * Ví dụ:
 */
abstract class SomeClass<T> {
    abstract fun execute() : T
}

class SomeImplementation : SomeClass<String>() {
    override fun execute(): String = "Test"
}

class OtherImplementation : SomeClass<Int>() {
    override fun execute(): Int = 42
}

object Runner {
    inline fun <reified S: SomeClass<T>, T> run() : T {
        return S::class.java.getDeclaredConstructor().newInstance().execute()
    }
}

fun main() {
    val intArr = arrayOf(1, 2, 3, 4, 5, 6, 8, 7, 9)
    val floatArr = arrayOf(1.25f, 2.36f, 3.11f, 4.25f, 5.88f, 9.1f)
    val stringArr = arrayOf("Lan", "Quang", "Hoa", "Phong", "Tú")

    // gọi hàm hiển thị mảng int
    printArray(intArr)
    // gọi hàm hiển thị mảng float
    printArray(floatArr)
    // gọi hàm hiển thị mảng string
    printArray(stringArr)

    val any = Array<String>(3) { "" }
    fill1(any, 0, "Hello")
    println(any.contentDeepToString())

    // khi sử dụng
    printArray1(intArr)
    printArray1(stringArr)
    printArray1(floatArr)

    // Để sử dụng các hàm generic, ta phải chỉ định rõ kiểu của tham số kiểu.
    // Trường hợp tham số kiểu có thể tự suy luận được, ta có thể bỏ qua việc chỉ định tường minh.
    val friends = listOf("Lan", "Quang", "Hoa", "Phong", "Tú")
    val bestFriend: String = getTValue<String>(friends, 1)
    println("Bạn thân của Mai: $bestFriend")

    val map: MutableMap<String, String> = HashMap()
    map["Hello"] = "Xin chào"
    map["Excellent"] = "Xuất sắc"
    map["Nice"] = "Tuyệt vời"
    map["Stupid"] = "Khờ khạo"
    val key = "Hello"
    val mean = getValueByKey<String, String>(map, key)
    println("$key: $mean")

//    sort1(listOf(1, 2, 3)) // Ok
//    sort1(listOf(HashMap<Int, String>())) // Error
// HashMap<Int, String> không phải kiểu con của Comparable<T>

//    val numbers = listOf("One", "Two", "Three", "Four", "Five", "Six", "Seven")
//    val afterOne = copyWhenGreater(numbers, "One")
//    println(afterOne) // Prints: [Seven, Six, Three, Two]

    // T is inferred as String because SomeImplementation derives from SomeClass<String>
    val s = Runner.run<SomeImplementation, _>()
    assert(s == "Test")

    // T is inferred as Int because OtherImplementation derives from SomeClass<Int>
    val n = Runner.run<OtherImplementation, _>()
    assert(n == 42)
}