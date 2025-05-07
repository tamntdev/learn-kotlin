package org.example.oop

import java.io.File

/**
 * Lập trình Hướng đối tượng (OOP) là phương pháp tiếp cận vấn đề dựa trên khái niệm “Đối tượng”.
 * OOP sẽ mô hình hóa phần mềm thành các thực thể (có thể là hữu hình, hoặc vô hình);
 * các thực thể sẽ luôn có 2 dạng tài nguyên cơ bản là đặc tính – attribute và hành vi – method.
 *
 * Đặc tính – attribute: Là các giá trị mà đối tượng đó nắm giữ, có thể cập nhật thay đổi theo thời gian.
 * Hành vi – method: Là các hành vi mà đối tượng đó có thể thực hiện.
 * Việc thực hiện các method có thể thay đổi giá trị của các attribute.
 *
 * Lớp – Class trong Lập trình Hướng đối tượng là 1 bản thiết kế khuôn mẫu cho 1 Thực thể.
 * Bạn hãy hình dung Class chính là bản thiết kế của một ngôi nhà, trong đó bao gồm đầy đủ các chỉ sổ: vị trí cửa,
 * màu sắc bàn ghế, chiều cao nha, hướng gió, phong thủy … tất cả gói bọn trong 1 bản thiết kế.
 * Tương tự như vậy, trong lập trình khi ta muốn gom nhóm những đặc tính,
 * hành vi cụ thể nào đó về một thực tể ta cần tạo ra Class.
 */

// Ví dụ về Object hóa một logic Copy File
class FileCopier {
    fun copy(sourcePath: String, destinationPath: String) {
        File(sourcePath).inputStream().use { input ->
            File(destinationPath).outputStream().use { output ->
                input.copyTo(output)
            }
        }
    }
}

fun main() {
    // Create an object of FileCopier class
    val fileCopier = FileCopier()

    // Define source and destination paths
    val source = "source.txt"
    val destination = "destination.txt"

    // Call the copy function with object reference
    fileCopier.copy(source, destination)

    println("File copied successfully!")
}