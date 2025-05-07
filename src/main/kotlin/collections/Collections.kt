package org.example.collections

// Function dùng generic
// Collection<T> là kiểu cha của phân cấp tập hợp.
// Interface này thể hiện các hành vi chung của một tập hợp chỉ đọc: truy cập kích thước, lấy phần tử cụ thể nào đó,…
fun <T> printCollection(data: Collection<T>) {
    for (item in data) {
        print("$item ")
    }
    println("\n========================")
}

// MutableCollection<T> là một Collection<T> được bổ sung khả năng ghi dữ liệu vào với các
// thao tác như add(), addAll(), remove().
fun <E> MutableList<E>.addSomeFriends(vararg items: E) {
    for (item in items) {
        add(item)
    }
}

fun main() {
//    val sampleList: MutableList<Int> = mutableListOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)
//    println("Before: $sampleList")
//    for( i in 0 until sampleList.size) {
//        println(sampleList[i])
//    }
//
//    sampleList.add(11)
//    sampleList.add(12)
//    sampleList.remove(10)
//    println("After: $sampleList")

//    val sampleSet: MutableSet<String> = mutableSetOf("A", "B", "A")
//    println(sampleSet.size)
//    sampleSet.forEach { println(it) }
//
//    println("Check if C is in sampleSet: " + sampleSet.contains("C"))
//    sampleSet.add("C")
//
//    for(s in sampleSet) {
//        println("$s ")
//    }

    val sampleMap: MutableMap<String, String> = mutableMapOf("a" to "A", "b" to "B", "c" to "C")

    for (e in sampleMap) {
        println("key: ${e.key} value: ${e.value}")
    }

    println(sampleMap.size)
    sampleMap.put("c", "C")

    println(sampleMap)

    val myFriends = mutableListOf("Loan", "Mai", "Nam", "Linh")
    val friends = listOf("Nhung", "Khang", "Duy")
    val set = setOf("One", "Two", "Three")
    val numbers = listOf(100, 500, 900, 789)
    printCollection(myFriends)
    printCollection(set)
    printCollection(friends)
    printCollection(numbers)

    // Cách đơn giản nhất để tạo một collection là sử dụng các hàm của thư viện chuẩn như listOf<T>(), setOf<T>(),
// mutableListOf<T>(), mutableSetOf<T>(), mapOf<K, V>()…
    // Nếu ta cung cấp các phần tử trong đối số truyền vào các hàm trên, trình biên dịch sẽ tự động xác định kiểu(tự suy luận kiểu).
    // Khi ta tạo một tập hợp rỗng, ta cần chỉ định kiểu cụ thể của các phần tử trong tập hợp một cách tường minh.
    val numbersSet = setOf("one", "two", "three", "four")
    val emptySet = mutableSetOf<String>()
    // Toán tử to dùng để tạo một đối tượng Pair tồn tại trong thời gian ngắn, nên ta chỉ sử dụng trong trường hợp nếu hiệu năng là không quan trọng.
    // Để tránh việc sử dụng quá nhiều bộ nhớ, sử dụng các cách tương đương khác như hàm apply():
//    val numbersMap = mapOf("key1" to 1, "key2" to 2, "key3" to 3, "key4" to 1)
    val numbersMap = mutableMapOf<String, String>().apply { this["one"] = "1"; this["two"] = "2" }

    // - Tạo lập collection với các hàm collection builder
    // - Một cách khác để tạo một collection là gọi hàm builder như builderSet(), builderMap(), muilderList().
    // - Chúng tạo một mutable collection mới sau đó thực hiện các thao tác thêm các phần tử vào collection.
    // Cuối cùng trả về một collection read-only có cùng số lượng phần tử.

    val myList = buildList {
        add(Student("SV1001", "Lê Công Tuấn", 3.25f))
        add(Student("SV1002", "Mai Thanh Trúc", 3.75f))
        add(Student("SV1003", "Huỳnh Công Thắng", 3.55f))
        add(Student("SV1004", "Nguyễn Mỹ Hạnh", 3.36f))
        add(Student("SV1005", "Trần Văn Hùng", 3.29f))
    }

    printList(myList)

    val myMap = buildMap {
        put("SV1001", Student("SV1001", "Lê Công Tuấn", 3.25f))
        put("SV1002", Student("SV1002", "Mai Thanh Trúc", 3.75f))
        put("SV1003", Student("SV1003", "Huỳnh Công Thắng", 3.55f))
        put("SV1004", Student("SV1004", "Nguyễn Mỹ Hạnh", 3.36f))
        put("SV1005", Student("SV1005", "Trần Văn Hùng", 3.29f))
    }

    printList(myMap)

    // Có các hàm có sẵn cho phép ta tạo các collection không chứa phần tử nào: emptyList(), emptySet(), emptyMap().
    // Khi tạo các tập hợp rỗng, ta nên chỉ định kiểu các phần tử sẽ được lưu trong tập hợp đó

    // Sao chép
    // Để tạo một tập hợp với cùng các phần tử như các tập hợp có sẵn, ta có thể sử dụng các hàm sao chép.
    // Các hàm này tạo một bản sao tham chiếu tới các phần tử của tập hợp đã có.
    // Khi có thay đổi xảy ra với phần tử trong một tập hợp, các tập hợp khác liên quan sẽ tự động nhận được sự thay đổi này.
    // Các hàm sao chép như toList(), toMutableList(), toSet(), toMutableSet()… tạo một bản sao của một tập hợp tại thời điểm hiện tại.
    // Kết quả là một tập hợp mới được tạo ra với cùng các phần tử đang có. Nếu ta thêm hoặc xóa các phần tử khỏi tập hợp gốc, nó không ảnh hưởng tới các tập hợp bản sao vừa tạo ra.
    // Các bản sao cũng có thể thay đổi độc lập không ảnh hưởng tới tập hợp gốc.
}

data class Student(val id: String, val fullName: String, val gpa: Float)

fun <T> printList(data: List<T>) {
    for (item in data) {
        println(item)
    }
}

fun <K, V> printList(map: Map<K, V>) {
    for (entry in map.entries) {
        println("${entry.key} : ${entry.value}")
    }
}