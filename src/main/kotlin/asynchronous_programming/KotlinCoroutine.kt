package org.example.asynchronous_programming

import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

/**
 * - Kotlin Coroutines là một bộ thư viện được viết bằng ngôn ngữ Kotlin, giúp Lập trình viên sử dụng,
 * quản lý các luồng logic (lịch trình) bất đồng bộ với nhau một cách hiệu quả.
 * - Một coroutine là một phiên bản của đối tượng tính toán có thể trì hoãn.
 * Nó giống với thread ở chỗ thực thi đoạn code song song với phần còn lại.
 * - Tuy vậy coroutine không liên kết với bất kì thread nào. Nó có thể trì hoãn việc thực thi của
 * bản thân trong một thread và và khôi phục hoạt động trong một thread khác.
 *
 *
 * Các khái niệm khi sử dụng Kotlin Coroutines
 * - CoroutineScope
 * Là nguồn gốc, động cơ để kích hoạt việc khởi chạy 1 Coroutine.
 * Hãy tưởng tượng CoroutineScope giống như một factory nơi khởi tạo ra các Coroutine và quản lý vòng đời của chúng.
 *
 * Trong một ứng dụng Kotlin bạn có thể tự tạo CoroutineScope cho riêng mình hoặc
 * sử dụng GlobalScope – Một CoroutineScope với tầm hoạt động trên toàn app, khi sử dụng đến GlobalScope cần cẩn
 * thận để tránh chiếm tài nguyên và gây ra Memory Leaks.
 *
 * Trong một ứng dụng Android, bạn có thể tự tạo CoroutineScope cho riêng từng trường hợp để sử dụng,
 * sử dụng GlobalScope hoặc là sử dụng các built-in CoroutineScope mà Android đã cung cấp sắn:
 * lifecycleScope của Activity, Fragment; viewModelScope của ViewModel. Các built-in CoroutineScope này sẽ có
 * vòng đời riêng, gắn liền với các Component của chúng (Activity, Fragment, ViewModel) giúp bạn thuận tiện hơn trong
 * việc quản lý bộ nhớ và tránh phí phạm tài nguyên dễ gây Memory Leaks.
 *
 * Coroutine là một thành phần gọn nhẹ
 * Coroutine ít gây tốn tài nguyên như JVM thread.Code sử dụng thread có thể làm cạn kiệt bộ nhớ nhưng với coroutine,
 * ta có thể thoải mái sử dụng mà không gây tiêu tốn nhiều tài nguyên.
 */

// runBlocking là một coroutine builder khác kết nối phần code không coroutine của hàm main() với phần code có
// chứa coroutine bên trong. Khu vực code coroutine được đánh dấu với this:CoroutineScope trong IDE.
// Nếu ta thiếu runBlocking, ta sẽ bị lỗi khi gọi launch vì launch chỉ được khai báo trong phạm vi CoroutineScope.
// runBlocking có nghĩa là main thread sẽ bị blocked trong khoảng thời gian diễn ra lời gọi,
// khi nào tất cả coroutine bên trong runBlocking{…} hoàn tất việc thực thi, main thread sẽ được khôi phục.
// Trong code thực tế ta ít thấy khối runBlocking vì thread rất tốn kém tài nguyên và việc phong tỏa chúng không mang
// lại hiệu quả, cũng không phải điều mà ta mong muốn.
fun main() = runBlocking {
    // launch là một coroutine builder sử dụng để khởi chạy một coroutine mới song song với các đoạn code còn lại và
    // nó chạy độc lập với các đoạn code khác.
//    launch {
    // delay() là một hàm trì hoãn đặc biệt dùng để hoãn coroutine trong một khoảng thời gian cụ thể.
    // Trì hoãn một coroutine không gây phong tỏa thread phía dưới nhưng cho phép các coroutine khác chạy và
    // sử dụng thread nằm phía dưới nó.
//        printMessage()
//    }

    //    Ví dụ sau tạo ra 100k dấu chấm bởi 100k coroutine mỗi coroutine chờ trong 3 giây sau đó in ra dấu chấm mà
    //    không làm cạn tài nguyên bộ nhớ:

    repeat(100000) {
        launch{
            print(".")
        }
    }

    showMessage()
}

/**
 * Tiến trình song song có cấu trúc
 * Coroutine tuân thủ một quy tắc của tiến trình song song có cấu trúc, tức là một coroutine mới chỉ có thể được khởi
 * chạy trong một CoroutineScope cụ thể, thứ dùng để phân định thời gian tồn tại của coroutine.
 * Đoạn code trên cho thấy rằng khối runBlocking thiết lập phạm vi tương ứng và tại sao ứng dụng chờ cho tới khi World!
 * được in ra sau 1 giây trì hoãn rồi sau đó mới kết thúc.
 * Trong ứng dụng thực tế ta sẽ khởi chạy rất nhiều coroutine. Cấu trúc tiến trình song song đảm bảo rằng chúng không
 * bị mất mát hay rò rỉ. Khối phía ngoài sẽ không thể hoàn tất cho tới khi tất cả các coroutine chạy trong nó hoàn tất.
 * Cấu trúc này cũng đảm bảo rằng bất kì lỗi nào trong code đều được báo cáo chính xác, không bị mất mát.
 */

/**
 * Trích xuất các hàm
 * Ta sẽ trích xuất các hàm từ trong khối launch ra thành các hàm nhỏ hơn. Khi ta tái cấu trúc đoạn code,
 * ta sẽ tạo các hàm mới với modifier suspend ở đầu.
 * Hàm suspend có thể dược sử dụng bên trong coroutine như các hàm thông thường khác nhưng tính năng bổ sung của nó là
 * có thể sử dụng các hàm suspend khác để hoãn thực thi của một coroutine.
 */
suspend fun printMessage() {
    delay(1000L)
    println("World!")
}

/**
 * Phạm vi của builder
 * - Ta có thể khai báo pham vi coroutine của riêng ta bằng cách sử dụng coroutineScope builder.
 * - Nó sẽ tạo một pham vi coroutine và không kết thúc cho tới khi tất cả các thành phần con được
 * khởi chạy trong nó kết thúc.
 * - Sự giống nhau của runBlocking và coroutineScope là chúng đều chờ đợi phần thân và tất cả các thành phần con của
 * nó hoàn tất để kết thúc.
 * - Sự khác nhau là runBlocking phong tỏa luồng hiện tại để chờ đợi còn coroutineScope chỉ trì hoãn,
 * giải phóng luồng chứa nó cho các mục đích sử dụng khác.
 * - Do sự biệt trên mà coroutineScope là một hàm suspend còn runBlocking là một hàm thông thường.
 * - Ta có thể sử dụng coroutineScope từ bất kì hàm suspend nào.
 *
 * Scope builder và lập trình song song
 * Một coroutineScope có thê được sử dụng trong bất kì hàm suspend nào để thực hiện nhiều thao tác song song.
 *
 *
 * Job
 * Job trong Kotlin Coroutines là đại diện cho 1 Coroutine được tạo ra từ CoroutineScope.
 *
 * Có 2 loại Job thường được dùng mặc định trong Kotlin Coroutines: Job và Deferred
 *
 * Điểm giống nhau: Cả 2 đều dùng để mô tả 1 Coroutine tạo bởi CoroutineScope. Chứa dữ liệu về trạng thái của Coroutine
 * đang chạy. Có thể được cancel khi cần thiết.
 *
 * Điểm khác biệt lớn nhất giữa Job và Deferred : Job mô tả một tác vụ được thực thi nhưng không quan trọng đến
 * kết quả trả về (fire and forget), Deferred là một tác vụ được thực thi, và chờ đợi kết quả trả
 * về (fire and wait for result)
 *
 * Job tường minh
 * Một launch coroutine builder trả về một đối tượng Job dùng như một trình xử lý cho coroutine đã khởi chạy.
 * Ta có thể chờ cho đối tượng job này kết thúc bằng lời gọi tường minh:
 */
suspend fun showMessage() = coroutineScope {
    // Cả hai khối launch { … } được thực thi song song nhau.
    // Kotlin! được in ra trước sau 1 giây khởi chạy còn Hello Android!!! được in ra sau khởi chạy 2 giây.
    // Khối coroutineScope của hàm printMessage() chỉ kết thúc khi cả hai khối launch{} bên trong nó kết thúc.
    // Do đó thông điệp “Load Done!” chỉ được in ra sau khi khối coroutineScope hoàn tất.
    val job1 = launch {
        delay(1000L)
        println("Kotlin!")
    }

    val job2 = launch {
        delay(2000L)
        println("Hello Android!!!")
    }

    println("Hello ")

//    job1.join()
//    job2.join()

//    Nếu không muốn chờ và cho phép chạy song song, ta có thể start các job:
    job1.start()
    job2.start()

    println("Load Done!")
}