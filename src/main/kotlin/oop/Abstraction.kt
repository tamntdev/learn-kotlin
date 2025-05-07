package org.example.oop

/**
 * Tính Trừu tượng – Abstraction trong Kotlin
 * Tính trừu tượng – 1 đặc tính thú vị trong cách tiếp cận của lập trình hướng đối tượng.
 *
 * Tính trừu tượng – abstraction được thể hiện thông qua việc khi thiết kế các tính năng của phần mềm các Lập trình
 * viên sẽ tập trung vào việc thiết kế luồng làm việc của các hành vi (behaviour / method) rồi từ đó phân chia
 * ra các Class mà chưa đi sâu vào các chi tiết cụ thể. Các Class được thiết kế ở giai đoạn này gọi là Abstract Class,
 * hoặc cũng có thể gọi là Interface trong 1 vài trường hợp.
 *
 * Trong Kotlin, tính đa hình thể hiện qua Interface, Abstract Class
 */
interface MyInterface {
    val name: String // interface của Kotlin có thể chứa member variable
    fun myFunction() {
        // interface của Kotlin có thể chứa hàm đã được định nghĩa sẵn
        println("Hello from MyInterface")
    }
    // interface của Kotlin có thể chứa hàm chưa được định nghĩa
    fun myEmptyFunction()
}

abstract class MyAbstractClass1 {
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

// Các lợi ích của việc tận dung Abstraction trong Kotlin
// Việc thành thạo abstraction và tận dụng nó trong quá trình phát triển phần mềm là yêu cầu bắt buộc cho Lập trình viên.
// Mục đích của abstraction là giúp các luồng logic sẽ dễ mở rộng, dễ triển khai trong quá trình phát triển phần mềm.
// Điều này thể hiện chặt chẽ trong các nguyên tắc của SOLID Principles

// Ứng dụng thực tế của Tính Trừu Tượng – Abstraction trong quá trình phát triển phần mềm.
// Ví dụ: Thiết kế giải pháp lưu trữ và đọc file nhạc cho ứng dụng Spotify.
// Yêu cầu phải mã hóa để đảm bảo tính an toàn dữ liệu.
//
// Trong trường hợp này chúng ta chưa tìm ra một giải pháp cụ thể cho việc sẽ sử dụng thuật toán mã hóa nào,
// nếu cứ tiếp tục tìm kiếm thuật toán mã hóa thì việc development sẽ tiếp tục bị treo. Giải pháp lúc này là Abstraction.

// Bước 1: Thiết kế ra Interface chung cho việc mã hóa, giải mã file âm nhạc
interface ICryptography {
    fun encrypt(arr: ByteArray): ByteArray
    fun decrypt(arr: ByteArray): ByteArray
}

// Bước 2: Implement một giải pháp đơn giản và tích hợp nó vào mã nguồn
class DummyCryptography : ICryptography {
    override fun encrypt(arr: ByteArray): ByteArray {
        return arr
    }
    override fun decrypt(arr: ByteArray): ByteArray {
        return arr
    }
}

// Bước 3: Phát triển các phương án nâng cao, chi tiết
class CryptographySolution1 : ICryptography {
    override fun encrypt(arr: ByteArray): ByteArray {
        // implement logic for the Solution1
        return arr
    }
    override fun decrypt(arr: ByteArray): ByteArray {
        // implement logic for the Solution1
        return arr
    }
}

class CryptographySolution2 : ICryptography {
    override fun encrypt(arr: ByteArray): ByteArray {
        // implement logic for the Solution 2
        return arr
    }
    override fun decrypt(arr: ByteArray): ByteArray {
        // implement logic for the Solution 2
        return arr
    }
}

// Bước 4: Test các giải pháp độc lập
class CryptographySolution1Test {
    // write unit test for CryptographySolution1
}
class CryptographySolution2Test {
    // write unit test for CryptographySolution2
}

// Bước 5: Lựa chọn và Release sản phẩm

// Nhờ tính chất Trừu Tượng (Abstraction) và Đa Hình (Polymorphism)
// chúng ta có thể dễ dàng thay thế được DummyCryptography bằng các CryptographySolution1 hoặc
// CryptographySolution2.
// Đây chính là điểm mạnh của OOP. Ngoài ra trong suốt quá trình phát triển, các Solution đều diễn ra độc lập với nhau
// nên việc Testing cũng trở nên dễ dàng hơn, điều đó khiến cho tính năng được tin cậy cao khi Release.