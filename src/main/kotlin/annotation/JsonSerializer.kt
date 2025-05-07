package org.example.annotation

import java.lang.reflect.Field
import java.util.Collections
import java.util.Objects
import java.util.stream.Collectors

class JsonSerializer {
    @Throws(JsonSerializationException::class)
    fun toJson(obj: Any): String {
        return try {
            checkSerializable(obj)
            getStringJson(obj)
        } catch (e: Exception) {
            throw JsonSerializationException(e.message)
        }
    }

    @Throws(JsonSerializationException::class)
    private fun getStringJson(obj: Any): String {
        val c: Class<*> = obj.javaClass
        val jsonElementMap: MutableMap<String, String> = HashMap()
        for (field in c.declaredFields) {
            field.isAccessible = true
            if (field.isAnnotationPresent(JsonElement::class.java)) {
                jsonElementMap[getKey(field)] = getValue(field, obj)
            }
        }
        val jsonStr = jsonElementMap.entries
            .stream().map { (key, value): Map.Entry<String, String> ->
                var tmp = "\"$key\":"
                tmp += if (value[0] == '{' || isNumeric(value) || isBoolean(value)) {
                    value
                } else {
                    "\"" + value + "\""
                }
                tmp
            }
            .collect(Collectors.joining(","))
        return "{$jsonStr}"
    }

    @Throws(Exception::class)
    private operator fun getValue(field: Field, obj: Any): String {
        val value = field[obj]
        return if (value is Number || value is Boolean || value is String) {
            field[obj].toString()
        } else {
            getStringJson(value)
        }
    }

    private fun getKey(field: Field): String {
        val key = field.getAnnotation(JsonElement::class.java).key
        return key.ifEmpty { field.name }
    }

    private fun checkSerializable(obj: Any) {
        if (Objects.isNull(obj)) {
            throw JsonSerializationException("Đối tượng cần chuyển đổi null")
        }
        val c: Class<*> = obj.javaClass
        if (!c.isAnnotationPresent(JsonSerializable::class.java)) {
            throw JsonSerializationException(
                "Lớp " + c.simpleName +
                        " không được đánh dấu với JsonSerializable"
            )
        }
    }

    private fun isNumeric(value: String): Boolean {
        return try {
            value.toDouble()
            true
        } catch (e: NullPointerException) {
            false
        } catch (e: NumberFormatException) {
            false
        }
    }

    private fun isBoolean(value: String): Boolean {
        return "true".compareTo(value.lowercase()) == 0 ||
                "false".compareTo(value.lowercase()) == 0
    }
}