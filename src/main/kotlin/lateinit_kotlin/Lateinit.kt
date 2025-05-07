package org.example.lateinit_kotlin

/**
 * lateinit là từ khóa của Kotlin để thông báo với compiler rằng biến được khai báo với tiền tố lateinit sẽ được
 * khởi tạo giá trị vào 1 thời điểm khác trong chương trình.
 *
 * Các biến được khởi tạo bởi lateinit sẽ có dạng mutable (tức là phải được khai báo với từ khóa var ) và là 1
 * biến NonNullable .
 *
 * Trường hợp Lập trình viên cố tình khai báo biến lateinit với khai báo Immutable hoặc với một Nullable type,
 * trình biên dịch sẽ văng lỗi.
 *
 * Trường hợp logic truy cập vào 1 biến lateinit chưa được khởi tạo, chương trình lúc này cũng sẽ văng lỗi.
 * Chính vì điều này chúng ta cần thực sự hiểu luồng chạy của chương trình và sử dụng lateinit một cách thông minh,
 * hiệu quả.
 */
class User {
    lateinit var address: String

    fun setAddressValue(address: String) {
        this.address = address
    }

    fun printAddress() {
        if(::address.isInitialized) {
            println(address)
        } else {
            println("Address is not initialized yet.")
        }
    }
}

fun main() {
    val user = User()
    user.printAddress() // Prints: Address is not initialized yet.
    user.setAddressValue("1 Le Duan St")
    user.printAddress() // Prints: 123 Main St
}