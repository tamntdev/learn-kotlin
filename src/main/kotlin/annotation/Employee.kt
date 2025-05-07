package org.example.annotation

@JsonSerializable
class Employee(
    @JsonElement("id") private val empId: String,
    first: String?,
    last: String?,
    midd: String?,
    @JsonElement("email") private val email: String,
    @JsonElement("salary") private val salary: Float,
    @JsonElement("address") private val address: String
) {
    @JsonElement("full_name")
    private val fullName: FullName

    init {
        fullName = FullName(first, last, midd)
    }

    internal data class FullName(
        @JsonElement("first") private val firtName: String?,
        @JsonElement("last") private val lastName: String?,
        @JsonElement("midd") private val middName: String?
    )
}