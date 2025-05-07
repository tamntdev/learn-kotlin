package org.example.mutable_immutable

class MyKotlinObject {
    val myKotlinProperty: String = "Hello, Kotlin!"
    val myNullKotlinProperty: String? = null
}

fun main() {
    // Immutable dịch ra tiếng Việt có nghĩa là Bất biến.
    // Có nghĩa là bất kỳ một biến nào trong chương trình được khai báo với kiểu Immutable đều sẽ không thay
    // đổi giá trị trong suốt vòng đời của chúng.
    //
    // Để khai báo một biến Immutable trong Kotlin ta dùng từ khóa val đứng trước tên biến.
    // Một biến Immutable sẽ chỉ được khai báo giá trị 1 lần duy nhất và không bao giờ được gán lại
    // trong suốt vòng đời của nó.
//    val myNonNullKotlinObject = MyKotlinObject()
//    val myNullKotlinObject: MyKotlinObject? = null
//    myNullKotlinObject = myNonNullKotlinObject // Compilation error

    // Ngược lại với Immutable , Kotlin cung cấp cách khai báo biến Mutable để biểu thị các biến có thể
    // được gán lại giá trị nhiều lần, trên nhiều Thread, Coroutines khác nhau trong suốt vòng đời của
    // chúng trong chương trình.
    //
    // Để khai báo một biến Mutable , ta dùng từ khóa var đứng trước tên biến.
    // Khi một biến Mutable được khai báo, chúng sẽ tự do được gán các giá trị mới phù hợp với kiểu dữ
    // liệu đã khai báo trong suốt vòng đời trên chương trình.
//    var myNonNullKotlinObject: MyKotlinObject = MyKotlinObject()
//    var myNullKotlinObject: MyKotlinObject? = null
//    myNullKotlinObject = myNonNullKotlinObject
//    myNonNullKotlinObject = MyKotlinObject()

    // Chương trình sẽ không thể biên dịch được nếu bạn cố gán một biến Mutable kiểu NonNullable cho một biến Nullable
    var myNonNullKotlinObject: MyKotlinObject = MyKotlinObject()
    var myNullKotlinObject: MyKotlinObject? = null
//    myNonNullKotlinObject = myNullKotlinObject // Compile Error
}