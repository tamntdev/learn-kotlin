package org.example.annotation

fun main() {
    val employee = Employee(
        "NV001",
        "Linh",
        "Nguyễn",
        "Thị Ngọc",
        "linhlinh@xmail.com",
        30.25f,
        "Hà Nội"
    )
    val serializer = JsonSerializer()
    val jsonData = serializer.toJson(employee)
    println(jsonData)
}