package org.example.asynchronous_programming

/**
 * Threading
 * Thread có thể được coi là phương pháp được biết đến rộng rãi nhất để tránh ứng dụng bị block.
 * Ví dụ đoạn chương trình sau sẽ có thể gây phong tỏa giao diện người dùng:
 *
 * fun postItem(item: Item) {
 *     val token = preparePost()
 *     val post = submitPost(token, item)
 *     processPost(post)
 * }
 *
 * fun preparePost(): Token {
 *     // makes a request and consequently blocks the main thread
 *     return token
 * }
 *
 * - Giả sử phương thức preparePost() là một tiến trình chạy dài hơi và hệ quả có thể phong tỏa giao diện người dùng.
 * - Những gì ta có thể làm là khởi chạy hành động đó ở một thread độc lập, từ đó tránh được việc phong tỏa giao
 * diện người dùng.
 * - Đây là một kĩ thuật rất phổ biến tuy nhiên nó có vài nhược điểm:
 *      + Thread có chi phí cao, nó yêu cầu chuyển đổi ngữ cảnh, điều này gây ra nhiều chi phí.
 *      + Thread không vô hạn. Số lượng các luồng có thể khởi chạy bị giới hạn bởi hệ điều hành chạy phía dưới.
 *      Trong các ứng dụng phía server, điều này có thể gây ra các điểm tắc nghẽn trong ứng dụng.
 *      + Thread không phải lúc nào cũng sẵn dùng. Một số nền tảng như JavaScript không hỗ trợ thread.
 *      + Thread không dễ dùng. Việc debug, tránh các điều kiện ngặt nghèo là các vấn đề phổ biến ta hay gặp phải trong
 *      lập trình đa luồng.
 *
 * Callback
 * - Với callback, ý tưởng là ta truyền một hàm vào một lời gọi tới hàm khác như một tham số và sau đó thực thi vào một lúc nào đó phù hợp, ví dụ khi tiến trình kết thúc.
 * - Ví dụ:
 * fun postItem(item: Item) {
 *     preparePostAsync { token ->
 *         submitPostAsync(token, item) { post ->
 *             processPost(post)
 *         }
 *     }
 * }
 *
 * fun preparePostAsync(callback: (Token) -> Unit) {
 *     // make request and return immediately
 *     // arrange callback to be invoked later
 * }
 *
 * - Cách này có vẻ là một giải pháp ổn hơn nhưng nó cũng có vài vấn đề:
 *      + Khó khăn với các callback lồng nhau. Thông thường, một hàm được sử dụng làm callback thường kết thúc bằng
 *      lời gọi của chính nó. Điều này dẫn tới một chuỗi các callback lồng nhau và cuối cùng làm cho code khó hiểu.
 *      + Việc xử lý lỗi là phức tạp. Các mô hình lồng nhau làm cho việc xử lý lỗi và sự lan truyền của chúng trở nên
 *      phức tạp.
 * - Callback khá phổ biến trong kiến trúc lặp sự kiện như trong JavaScript, ngay cả trong các công nghệ đó,
 * người dùng có xu hướng rời đi và sử dụng một phương pháp khác như promise hoặc các reactive extension.
 *
 *
 * Futures, Promises, và others
 * - Ý tưởng đằng sau future hoặc promises đó là: khi ta thực hiện một lời gọi, ta được hứa hẹn rằng tại một thời điểm nào đó nó sẽ trả về với một đối tượng gọi là Promise. Sau đó ta có thể sử dụng đối tượng đó để thực hiện các tác vụ khác.
 * - Xem ví dụ sau để dễ hình dung hơn
 * fun postItem(item: Item) {
 *     preparePostAsync()
 *         .thenCompose { token ->
 *             submitPostAsync(token, item)
 *         }
 *         .thenAccept { post ->
 *             processPost(post)
 *         }
 *
 * }
 *
 * fun preparePostAsync(): Promise<Token> {
 *     // makes request and returns a promise that is completed later
 *     return promise
 * }
 *
 * - Hướng tiếp cận này yêu cầu một chuỗi các thay đổi trong cách ta lập trình. Điển hình là:
 *      + Mô hình lập trình khác. Giống như callback, mô hình lập trình chuyển từ cách tiếp cận mệnh lệnh từ
 *      top-down(trên xuống) sang mô hình thành phần tổng hợp với một chuỗi các lời gọi. Các cấu trúc chương
 *      trình truyền thống như vòng lặp, ngoại lệ… thường không còn hợp lệ trong mô hình này.
 *      + Khác biệt về API. Thường ta sẽ càn phải học một API hoàn toàn mới ví dụ thenCompose() hoặc thenAccept(),
 *      mà những thứ đó có thể khác nhau tùy nền tảng.
 *      + Kiểu trả về đặc thù. Thay vì trả về một giá trị dữ liệu ta đang cần, nó trả về một kiểu dữ liệu mới:
 *      Promise – thứ mà ta cần phải xem xét kĩ lưỡng.
 *      + Việc xử lý lỗi có thể trở nên phức tạp. Việc lan truyền và xâu chuỗi các lỗi không phải là đơn giản.
 *
 *
 *  Reactive extensions
 * - Reactive Extension (Rx) được giới thiệu trong C# bởi Erik Meijer và được áp dụng chính thống vào Java bởi
 * Netflix dưới tên RxJava. Kể từ đó Rx trở nên phổ biến với nhiều nền tảng khác nhau.
 * - Ý tưởng đằng sau Rx là chuyển hướng sang cái được gọi là luồng có thể quan sát được,
 * theo đó giờ ta coi dữ liệu là một luồng(một lượng dữ liệu vô hạn) và luồng này có thể được quan sát.
 * Trong thực hành, Rx đơn giản là mẫu thiết kế Observer với một chuỗi các extension trong đó cho phép ta
 * vận hành trên dữ liệu.
 * - Trong hướng tiếp cận nó khá tương đồng với Future nhưng Future trả về một phần tử rời rạc còn Rx trả về một luồng.
 * Nó cũng giới thiệu một cách nghĩ mới về mô hình lập trình, có thể phát biểu kiểu: “Mọi thứ là một luồng và
 * có thể quan sát được”.
 * - Rx giới thiệu một hướng tiếp cận tốt hơn với việc xử lý lỗi.
 *
 *
 * Coroutines
 * - Phương pháp tiếp cận của Kotlin khi làm việc với bất đồng bộ là sử dụng coroutine,
 * một ý tưởng của việc tính toán có thể trì hoãn, ví dụ: một hàm có thể tạm hoãn việc thực thi của nó tại một
 * thời điểm nào đó, và sau đó có thể khôi phục lại và tiếp tục hoạt động.
 * - Một lợi thế của coroutine là mô hình lập trình không bị thay đổi.
 * Việc viết code gây phong tỏa tương tự như viết code không gây phong tỏa giao diện người dùng.
 * - Xem ví dụ dưới đây để hiểu rõ hơn:
 * fun postItem(item: Item) {
 *     launch {
 *         val token = preparePost()
 *         val post = submitPost(token, item)
 *         processPost(post)
 *     }
 * }
 *
 * suspend fun preparePost(): Token {
 *     // makes a request and suspends the coroutine
 *     return suspendCoroutine { /* ... */ }
 * }
 *
 * - Đoạn code trên có thể thực hiện một hành động dài hơi nào đó mà không làm phong tỏa main thread(UI thread).
 * Hàm preparePost() được gọi là một hàm có thể trì hoãn và ta đặt keyword suspend trước tên hàm.
 * Hàm này có thể tạm hoãn và khôi phục lại tại một thời điểm nào đó trong khi ứng dụng hoạt động.
 *      + Dấu hiệu của hàm vẫn chính xác như cũ, chỉ thêm keyword suspend. Kiểu trả về của hàm cũng là kiểu
 *      ta muốn được trả về.
 *      + Code vẫn được viết như thể ta đang viết code chạy đồng bộ theo hướng top-down mà không cần bất
 *      kì cú pháp đặc biệt nào ngoài việc gọi launch để khởi động coroutine.
 *      + Mô hình lập trình và API vẫn như cũ. Ta có thể tiếp tục sử dụng vòng lặp, xử lý ngoại lệ..
 *      và do đó không cần học những tập API hoàn toàn mới.
 *      + Nó độc lập nền tảng. Cho dù ta nhắm mục tiêu JVM, JavaScript hay bất kì nền tảng nào khác thì code cũng
 *      được viết tương tự nhau. Ở phía dưới, trình biên dịch sẽ nhận nhiệm vụ xử lý tương thích chúng với từng nền tảng.
 * - Coroutine không phải khái niệm mới hay được phát minh ra trong Kotlin. Nó đã tồn tại ở đó hàng thập kỉ và
 * phổ biến trong các ngôn ngữ lập trình khác như Go. Điều quan trọng nằm ở cách nó được triển khai trong Kotlin.
 * Tức là hầu hết các chức năng được ủy quyền cho thư viện. Ngoài suspend, không có thêm keyword nào được thêm vào ngôn ngữ. Với C# chẳng hạn, ta có async, await, trong Kotlin, chỉ có các hàm của thư viện.
 * - Chi tiết về Coroutine ta sẽ tìm hiểu trong các bài học kế tiếp và trong khi thực hành làm ứng dụng Android.
 *
 */
