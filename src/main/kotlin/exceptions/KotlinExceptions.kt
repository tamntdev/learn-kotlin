package org.example.exceptions

/**
 * - Tất cả các lớp ngoại lệ trong Kotlin đều kế thừa từ lớp Throwable. Mỗi exception có một thông điệp,
 * một stack trace và một tùy chọn nguyên nhân gây ra ngoại lệ.
 * - Để ném một ngoại lệ, ta sử dụng keyword throw
 * - Để bắt ngoại lệ, ta sử dụng biểu thức try..catch
 * - Ta có thể có 1 hoặc nhiều khối catch đi sau khối try.
 * - Khối finally là tùy chọn và có thể bỏ qua. Một try luôn đi kèm với 1 catch hoặc 1 finally.
 * - try là một biểu thức, do đó ta có thể trả về từ trong khối try,
 * - throw là một biểu thức do đó ta có thể sử dụng nó trong biểu thức Elvis:
 * - Biểu thức throw có kiểu là Nothing, Kiểu này không có giá trị và được sử dụng để đánh dấu vị trí code có thể
 * không bao giờ chạy tới.
 * - Ta có thể sử dụng Nothing để đánh dấu một hàm không bao giờ trả về
 * - Ta có thể gặp kiểu Nothing hoặc Nothing? khi làm việc với interface.
 * Với kiểu Nothing?, nó chỉ có duy nhất 1 giá trị khả thi là null.
 * Nếu ta sử dụng null để khởi tạo giá trị cho một biến của kiểu tự suy luận,
 * ta sẽ không có thêm thông tin để xác định một kiểu cụ thể hơn. Trình biên dịch sẽ sử dụng kiểu Nothing?.
 */
fun raiseException() {
    val str = "123@Hello"
    try {
        val number = str.toInt()
        println("number = $number")
    } catch (e: NumberFormatException) {
        throw e
    }
}

/**
 * Hàm chuyển đổi json string sang đối tượng user.
 * @param data: json string
 * @return đối tượng user với thông tin tương ứng hoặc null nếu chuyển đổi thất bại.
 */
//private fun toUser(data: String): User? {
//    val gsonBuilder = GsonBuilder()
//    val gson = gsonBuilder.create()
//    return try {
//        gson.fromJson(data, User::class.java)
//    } catch (ignored: JsonSyntaxException) {
//        null
//    }
//}

fun fail(message: String): Nothing {
    throw IllegalArgumentException(message)
}

fun main() {
    try {
        raiseException()
    } catch (e: Exception) {
        println("==> Đã xảy ra ngoại lệ")
        println("Loại ngoại lệ: ${e.javaClass}")
        println("Thông điệp ngoại lệ: ${e.message}")
    } finally {
        println("==> Kết thúc khối xử lý ngoại lệ")
    }
    println("=======================")
    println("Các lệnh phía dưới nơi xảy ra ngoại lệ")

    val str = "123@Hello"
//    try là một biểu thức, do đó ta có thể trả về từ trong khối try,
    val number: Int? = try { str.toInt() } catch (e: NumberFormatException) { null }
    println("Giá trị của number: $number")

    val name: String? = null
    val s = name ?: throw IllegalArgumentException("Name required")

    val res = name ?: failed("Chưa cung cấp tên")
    println(s)

    val x = null           // x có kiểu là `Nothing?`
    val l = listOf(null)   // l có kiểu là `List<Nothing?>
}

fun failed(msg: String) : Nothing {
    throw IllegalArgumentException(msg)
}