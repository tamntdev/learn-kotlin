package org.example.oop

/**
 * Tính đóng gói - Encapsulation
 * Tính đóng gói là khả năng kiểm soát mức độ truy cập của các thuộc tính nằm trong 1 Class.
 * Nhằm đảm bảo sự trọng vẹn dữ liệu, logic của 1 Class để phục vụ cho tính năng của Thư viện, hoặc chương trình.
 *
 * Access Modifiers	                        Ý nghĩa
 *      public	                Có thể được truy cập ở mọi nơi trong Source code
 *      internal	            Chỉ có thể truy cập được trong cùng module (khác package name nhưng cùng module vẫn truy cập được)
 *      protected	            Chỉ có thể truy cập được trong phạm vi bên trong Class. Được kế thừa cho Sub Class.
 *      private	                Chỉ có thể truy cập được trong phạm vi bên trong Class. Không được kế thừa cho Sub Class.
 */

// Khi khai báo mà không kèm cụ thể Access modifier, Kotlin sẽ xem đó mặc định là 1 public access modifier.
class DefineKotlinAccessModifier {
    //    Khi khai báo không kèm modifier cụ thể, Kotlin compiler sẽ mặc định hiểu đó là public modifier
    val publicModifier: String = "Public Modifier"
    internal val internalModifier: String = "Internal Modifier"
    protected val protectedModifier: String = "Protected Modifier"
    private val privateModifier: String = "Private Modifier"
}

/**
 * Kotlin còn cung cấp thêm tính năng Properties Getter, Setter để Lập trình viên được tùy biến nhiều hơn trong việc quản lý giá trị của các biến.
 */
class EncapsulationKotlin {
    var dynamicString: String = "Initialized Value"
        private set // giới hạn phạm vi của hàm set, có thể truy cập dynamicString từ bên ngoài, nhưng việc set phải được thực hiện bên trong Class
    var anotherDynamicString: String = "Another Initialized Value"
        set(value) {
            field = "Another $value"
        } // customize lại logic của hàm set cho anotherDynamicString
}

/**
 * Cách chọn Access Modifier phù hợp
 * Nguyên tắc chung trong việc thiết kế Class là phải giới hạn việc tự ý thay đổi property của 1 object.
 * Việc giới hạn này giúp cho logic của Class được tập trung, chỉ có những behavior được yêu cầu mới cần public.
 *
 * Nguyên tắc 1: Nếu 1 biến không cần thiết được truy cập từ bên ngoài, hãy dùng protected hoặc private
 * Nguyên tắc 2: Nếu 1 biến cần thiết được truy cập từ bên ngoài để đọc giá trị, hãy dùng protected hoặc private cho setter
 * Nguyên tắc 3: Nếu một biến cần thiết được truy cập và set giá trị từ bên ngoài.
 * Không dùng cách set giá trị trực tiếp cho biến, hãy dùng setter để kiếm soát code được tốt hơn
 *
 *
 */
//class DatabaseService {
//    var dbName: String = ""
//    // db logics
//}
// Đây là một bad pratice khi thiết kế lớp DatabaseService.
//biến dbName để lưu trữ bị mark là public và ở dạng mutable -> có thể bị thay đổi trong runtime.
// Cách cải thiện là
//class DatabaseService(private val dbName: String) {
//    // Db logics
//}
// Lúc này biến dbName lưu duy nhất 1 giá trị cần thiết để khởi tạo database, và không thay đổi được theo thời gian.
// Từ bên ngoài nhìn vào cũng không thể truy cập đến dbName để kiểm soát giá trị của biến này

//Nguyên tắc 2: Nếu 1 biến cần thiết được truy cập từ bên ngoài để đọc giá trị, hãy dung protected hoặc private cho setter
class DatabaseService(dbName: String) {
    var databaseName: String
        private set

    init {
        databaseName = dbName
    }

    // Db logics
}

// Nguyên tắc 3: Nếu một biến cần thiết được truy cập và set giá trị từ bên ngoài.
// Không dùng cách set giá trị trực tiếp cho biến, hãy dùng setter để kiếm soát code được tốt hơn
class MyWifiController() {
    var currentDeviceCount: Int = 0
        set(value) {
            if (value > maxDeviceCount) {
                println("Device count is too high")
                return
            }
            field = value
        }
    var maxDeviceCount: Int = 10
        set(value) {
            if (currentDeviceCount > value) {
                println("Current device count is greater than max device count")
                return
            }
            field = value
        }
}
// Một ví dụ trong trường hợp Lập trình viên cần thiết buộc phải public setter ra ngoài

class BankAccount {
    private var balance: Double = 0.0

    fun deposit(amount: Double) {
        if (amount > 0) {
            balance += amount
            println("Gửi tiền thành công. Số dư hiện tại: $balance")
        } else {
            println("Số tiền gửi phải lớn hơn 0.")
        }
    }

    fun withdraw(amount: Double) {
        if (amount > 0 && amount <= balance) {
            balance -= amount
            println("Rút tiền thành công. Số dư hiện tại: $balance")
        } else {
            println("Số tiền rút không hợp lệ hoặc số dư không đủ.")
        }
    }

    fun getBalance(): Double {
        return balance
    }
} // Ví dụ về tính đóng gói
/*
- Không thể trực tiếp thay đổi số dư - balance được
- Phải thực thi qua hàm deposit, withdraw để kiểm soát logic
*/