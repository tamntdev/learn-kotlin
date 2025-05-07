package org.example.asynchronous_programming

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * Tạo một tác vụ dài hơi
 * Coroutine được xây dựng dựa trên các hàm thông thường bằng cách bổ sung thêm 2 thao tác để xử lý các tác
 * vụ dài hơi(long-running task):
 * suspend dừng việc thực thi của coroutine hiện tại, lưu giữ lại tất cả các biến cục bộ.
 * resume tiếp tục việc thực thi của một coroutine đã bị trì hoãn từ nơi nói đã bị trì hoãn.
 * Ta chỉ có thể gọi hàm suspend từ trong các hàm suspend khác hoặc bằng cách sử dụng các coroutine builder như
 * launch để start một coroutine mới.
 * Ví dụ sau mô tả một triển khai của coroutine đơn giản cho một tác vụ dài hơi giả định:
 * suspend fun fetchDocs() {                             // Dispatchers.Main
 *     val result = get("https://developer.android.com") // Dispatchers.IO for `get`
 *     show(result)                                      // Dispatchers.Main
 * }
 *
 * suspend fun get(url: String) = withContext(Dispatchers.IO) { /* ... */ }
 *
 * - Trong ví dụ này, hàm get() vẫn chạy trên main thread nhưng nó hoãn coroutine trước khi start yêu cầu kết nối mạng.
 * Khi kết nối mạng hoàn tất, hàm get() sẽ khôi phục coroutine đã tạm dừng trước đó thay vì sử dụng một callback để
 * thông báo cho main thread.
 * - Kotlin sử dụng một stack frame để quản lý hàm nào là hàm chạy dài hơi với bất kì các biến cục bộ nào.
 * Khi tạm dừng một coroutine, stack frame hiện tại được sao chép lại và lưu lại cho việc sử dụng về sau.
 * Khi khôi phục, stack frame được sao chép ngược trở lại từ nơi nó đã được lưu trữ và hàm sẽ hoạt động trở lại.
 * Ngay cả như vậy thì mã nguồn có vẻ trông giống như một khối lệnh yêu cầu tuần tự thông thường.
 * Coroutine đảm bảo rằng yêu cầu kết nối mạng không làm phong tỏa main thread.
 *
 *
 * CoroutineContext, Dispatchers
 * CoroutineContext
 * Context: Bối cảnh
 *
 * CoroutineContext được hiểu là bối cảnh, môi trường để thực thi 1 Coroutine.
 *
 * Dispatchers
 * Là kẻ quyết định Coroutine sẽ được thực thi trên Thread Pool nào. Bạn lưu ý đây là Thread Pool chứ không
 * phải Thread nhé.
 *
 *
 * Sử dụng coroutine cho mục đích an toàn luồng chính
 * - Coroutine sử dụng các đối tượng điều phối(dispatchers) để xác định các thread nào được sử dụng cho việc thực thi coroutine.
 * - Để chạy code bên ngoài luồng chính, ta có thể bảo coroutine trong Kotlin thực hiện công việc trên đối
 * tượng điều phối Default hoặc IO.
 * - Trong Kotlin, tất cả các coroutine phải chạy trong một đối tượng điều phối, ngay cả khi chúng đang chạy
 * trên main thread. Các coroutine có thể tạm dừng bản thân chúng và đối tượng điều phối chịu trách nhiệm khôi
 * phục hoạt động của chúng.
 *
 * - Kotlin cung cấp cho ứng dụng một số Dispatchers mặc định mà ta có thể lựa chọn.
 *      + Dispatchers.Default: Là Dispatcher dùng để tạo các Coroutine Job thực thi trên Background Thread.
 *      Các task này được khuyến khích, quy định là các tác vụ tính toán, tốn nhiều tài nguyên CPU – (CPU-bound opertaion):
 *      tính toán đệ quy, tiền lãi ngân hàng, biến động thị trường, …
 *      + Dispatchers.IO: Là Dispatcher dùng để tạo các Coroutine Job thực thi trên môi trường Background Thread.
 *      Các task được chạy trên IO được khuyến nghị liên quan đến IO operations (thực thi ghi / đọc file, network, …)
 *      + Dispatchers.Main: Đại diện cho main thread trong ứng dụng. Các Coroutine được thực thi bởi Dispatchers.
 *      Main luôn chạy trên 1 Main Thread duy nhất.
 *      + Dispatchers.Unconfined: Đặc biệt, các Coroutine Job được tạo ra bởi Unconfined sẽ không xác định được môi
 *      trường chúng sẽ được thực thi sau khi resume từ 1 suspend. Điều này có nghĩa, tại thời điểm resume hệ
 *      thống quản lý Kotlin Coroutines sẽ tìm kiếm một Thread được free gần nhất để tiếp tục thực thi,
 *      không giới hạn là Dispatchers.IO Dispatchers.Main hay Dispatchers.Default.
 *
 * - Ta có thể sửa đổi ví dụ ở phần trên của bài học bằng cách sử dụng các đối tượng điều phối phù hợp.
 * Trong thân hàm get(), gọi withContext(Dispatchers.IO) để tạo một khối hoạt động trong IO thread pool.
 * Bất kì code nào ta đặt trong khối này luôn thực thi thông qua đối tượng điều phối IO.
 * Bản thân withContext là một hàm suspend nên hàm get() cũng là một hàm suspend.
 * - Với các coroutine, ta có thể điều phối các luồng bằng các quyền kiểm soát chi tiết.
 * Vì withContext() cho phép ta kiểm soát thread pool của bất kì dòng code nào mà không tạo callback,
 * ta có thể áp dụng nó cho các hàm rất nhỏ như đọc dữ liệu từ database hoặc thực hiện các thao tác kết nối mạng.
 * - Một thói quen được khuyến nghị là sử dụng withContext() để đảm bảo mọi hàm đều là an toàn với main thread,
 * tức là ta có thể gọi hàm đó từ trong main thread.
 * - Bằng cách này, nơi thực hiện lời gọi không cần suy nghĩ về việc thread nào nên được sử dụng để thực thi một hàm.
 * - Trong ví dụ trước, hàm fetchDocs() thực thi trên luồng chính tuy nhiên nó có thể gọi hàm get() một cách an
 * toàn để thực hiện một yêu cầu kết nối mạng trong background. Bởi vì các coroutine hỗ trợ suspend và resume,
 * coroutine trên main thread được khôi phục với kết quả của hàm get() ngay khi khối withContext hoàn tất công việc.
 *
 *
 * Hiệu năng của withContext()
 * - withContext() không tạo thêm các chi phí bổ sung so với các triển khai dựa trên callback.
 * Hơn nữa ta có thể tối ưu hóa lời gọi withContext() so với các callback triển khai tương đương trong một số trường hợp.
 * - Ví dụ: nếu một hàm thực hiện 10 lời gọi tới một kết nối mạng, ta có thể bảo Kotlin chuyển qua lại giữa các
 * thread chỉ 1 lần bằng cách sử dụng một outer withContext(). Sau đó, mặc dù thư viện network sử dụng withContext()
 * nhiều lần, nó vẫn nằm trong cùng đối tượng điều phối và tránh chuyển đổi sang các luồng khác.
 * Kotlin cũng tối ưu hóa việc chuyển đổi từ đối tượng điều phối Default và IO để tránh chuyển đổi luồng bất cứ khi có thể.
 *
 *
 *
 * Start một coroutine
 * - Ta có thể start một coroutine bằng một trong hai cách:
 *      + Sử dụng launch để start một coroutine mới và không trả về kết quả cho nơi thực hiện lời gọi.
 *      Bất kì công việc nào được cân nhắc là kích hoạt lên và quên nó đi thì ta có thể start nó với launch.
 *      + Sử dụng async để start một coroutine mới và cho phép ta trả về một kết quả với một hàm suspend được gọi là await.
 * - Thông thường ta nên sử dụng launch để chạy một coroutine mới từ hàm thông thường vì hàm thông thường không
 * thể gọi tới await. Sử dụng async chỉ khi ta đang bên trong một coroutine khác hoặc khi đang ở trong một
 * hàm suspend và thực hiện một thao tác phân tách song song rời rạc(parallel decompposition).
 *
 *
 * Phân tách song song rời rạc
 * - Tất cả các coroutine được start bên trong một hàm suspend cần phải bị stop khi hàm đó trả về,
 * do đó ta cần đảm bảo rằng các coroutine đó kết thúc trước khi trả về.
 * - Với các chương trình lập trình đồng thời có cấu trúc trong Kotlin,
 * ta có thể định nghĩa một coroutineScope khởi chạy một hoặc nhiều coroutine.
 * - Sau đó sử dụng await() cho một coroutine đơn hoặc awaitAll() cho nhiều coroutine,
 * ta có thể đảm bảo rằng các coroutine đó kết thúc trước khi hàm trả về.
 *
 * suspend fun fetchTwoDocs() =
 *     coroutineScope { // nạp hai tài liệu bất đồng bộ
 *         val deferredOne = async { fetchDoc(1) } // nạp tài liệu 1
 *         val deferredTwo = async { fetchDoc(2) } // nạp tài liệu 2
 *         // chờ cho cả hai kết thúc sau đó trả về trả về một giá trị
 *         deferredOne.await()
 *         deferredTwo.await()
 *     }
 *
 * - Ta có thể sử dụng awaitAll() trên tập hợp:
 *
 * suspend fun fetchTwoDocs() =        // được gọi trên bất kì Dispatcher của bất kì luồng nào
 *     coroutineScope {
 *         val deferreds = listOf(     // nạp cùng lúc 2 tài liệu
 *             async { fetchDoc(1) },  // async returns 1 kết quả cho tài liệu 1
 *             async { fetchDoc(2) }   // async returns 1 kết quả cho tài liệu 2
 *         )
 *         deferreds.awaitAll()        // sử dụng để chờ cho cả hai yêu cầu kết thúc
 *     }
 *
 * - Ngay cả khi fetchTwoDocs() khởi chạy các coroutine mới với async, hàm sử dụng awaitAll() để chờ cho các
 * coroutine đã khởi chạy kết thúc trước khi trả về. Ngay cả khi không gọi awaitAll(), coroutineScope builder sẽ
 * không khôi phục coroutine đã thực hiện lời gọi tới fetchTwoDocs() cho tới khi tất cả các coroutine mới hoàn tất.
 * - Ngoài ra, coroutineScope cũng bắt bất kì ngoại lệ nào mà các coroutine ném ra và điều hướng ngoại lệ đó tới
 * nơi thực hiện lời gọi tới đoạn code chứa coroutineScope.
 *
 *
 *
 * - Các khai niệm trong coroutine:
 *      + CoroutineScope.
 *      + Job.
 *      + CoroutineContext.
 *
 *
 * CoroutineScope
 * - CoroutineScope lưu vết bất kì coroutine nào nó tạo ra với launch hoặc async.
 * Các coroutine đang hoạt động có thể bị hủy bỏi bằng lời gọi scope.cancel() tại bất kì thời điểm nào.
 * - Trong Android, một số thư viện cung cấp CoroutineScope của riêng nó cho các lớp vòng đời nhất định.
 * Ví dụ:
 *      + ViewModel có viewModelScope.
 *      + Lifecycle có lifecycleScope.
 * - Không giống như các đối tượng điều phối, CoroutineScope không chạy các coroutine.
 * - Ta có thể tự tạo CoroutineScope của riêng ta để quản lý vòng đời của các coroutine trong một tầng cụ
 * thể nào đó trong ứng dụng
 * - Một phạm vi đã bị hủy không thể tạo nhiều coroutine. Do đó ta chỉ nên gọi scope.cancel() khi lớp kiểm
 * soát vòng đời của nó đã bị hủy. Khi sử dụng viewModelScope, ViewModel tự hủy phạm vi cho ta trong phương
 * thức onCleared().
 *
 *
 * Job
 * - Một Job là một trình xử lý của một coroutine. Mỗi coroutine ta tạo ra với launch hoặc async trả về một
 * đối tượng Job xác định duy nhất coroutine và quản lý vòng đời của coroutine đó.
 * - Ta có thể truyền một Job vào một CoroutineScope để quản lý vòng đời của nó tốt hơn.
 *
 *
 * CoroutineContext
 * - Một CoroutineContext định nghĩa các hành vi của một coroutine sử dụng các phương các phần tử sau:
 *      + Job: kiểm soát vòng đời của coroutine.
 *      + CoroutineDispatcher: điều phối công việc tới một thread phù hợp.
 *      + CoroutineName: tên của coroutine, hữu ích khi ta debug.
 *      + CoroutineExceptionHandler: xử lý các ngoại lệ chưa được bắt.
 * - Với các coroutine mới được tạo bên trong một scope, một Job mới sẽ được gán cho coroutine mới đó,
 * còn các phần tử CoroutineContext khác sẽ kế thừa từ scope chứa coroutine đó.
 * - Ta có thể ghi đè các phần tử được kế thừa bằng cách truyền một CoroutineContext mới vào hàm launch hoặc async.
 * - Lưu ý, truyền Job vào launch hoặc async không gây thay đổi gì cả vì một đối tượng mới của Job luôn được dùng để
 * gán cho coroutine mới.
 */

//class ExampleClass {
//
//    // Job and Dispatcher are combined into a CoroutineContext which
//    // will be discussed shortly
//    val scope = CoroutineScope(Job() + Dispatchers.Main)
//
////    fun exampleMethod() {
////        // Starts a new coroutine within the scope
////        scope.launch {
////            // New coroutine that can call suspend functions
////            fetchDocs()
////        }
////    }
//
//    fun cleanUp() {
//        // Cancel the scope to cancel ongoing coroutines work
//        scope.cancel()
//    }
//
//    fun exampleMethod() {
//        // trình xử lý của coroutine, ta có thể kiểm soát vòng đời của nó
//        val job = scope.launch {
//            // coroutine mới
//        }
//
//        if (...) {
//            // hủy coroutine đã khởi chạy ở trên, không ảnh hưởng tới scope.
//            job.cancel()
//        }
//    }
//}

suspend fun fetchDocs() {                      // Dispatchers.Main
    val result = get("developer.android.com")  // Dispatchers.Main
    show(result)                               // Dispatchers.Main
}

fun show(value: Any) {}

suspend fun get(url: String) =                 // Dispatchers.Main
    withContext(Dispatchers.IO) {              // Dispatchers.IO (main-safety block)
        /* perform network IO here */          // Dispatchers.IO (main-safety block)
    }                                          // Dispatchers.Main
