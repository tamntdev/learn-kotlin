package org.example.operators

/**
 * Để nạp chồng một toán tử, đánh dấu hàm tương ứng với modifier operator:
 */

interface IndexedContainer {
    operator fun get(index: Int)
}

// Khi ta ghi đè toán tử nạp chồng, ta có thể để khuyến keyword operator
class OrdersList: IndexedContainer {
    override fun get(index: Int) { /*...*/ }
}

fun main() {
    val a = Fraction(1, 2)
    val b = Fraction(18, 8)
    val sum = minimize(a + b)
    val diff = minimize(a - b)
    val prod = minimize(a * b)
    println("${a.a}/${a.b} + ${b.a}/${b.b} = ${sum.a}/${sum.b}")
    println("${a.a}/${a.b} - ${b.a}/${b.b} = ${diff.a}/${diff.b}")
    println("${a.a}/${a.b} * ${b.a}/${b.b} = ${prod.a}/${prod.b}")
}

// tìm ước chung lớn nhất của hai số
fun gcd(a: Int, b: Int): Int {
    return if (b == 0) {
        a
    } else if (a < b) {
        gcd(a, b % a)
    } else {
        gcd(b, a % b)
    }
}

data class Fraction(val a: Int, val b: Int)

// cộng hai phân số
operator fun Fraction.plus(other: Fraction): Fraction {
    val lcm = this.b * other.b / gcd(this.b, other.b)
    return Fraction(this.a * (lcm / this.b) + (lcm / other.b) * other.a, lcm)
}

// trừ phân số p1 cho phân số p2
operator fun Fraction.minus(other: Fraction): Fraction {
    val lcm = this.b * other.b / gcd(this.b, other.b)
    return Fraction(this.a * (lcm / this.b) - (lcm / other.b) * other.a, lcm)
}

// nhân phân số p1 với phân số p2
operator fun Fraction.times(other: Fraction): Fraction {
    val lcm = this.b * other.b / gcd(this.b, other.b)
    return Fraction(this.a * other.a, this.b * other.b)
}

// rút gọn phân số
fun minimize(f: Fraction): Fraction {
    val currentGcd = if (f.a > 0 && f.b > 0) {
        gcd(f.a, f.b)
    } else if (f.a < 0 && f.b > 0) {
        gcd(-f.a, f.b)
    } else if (f.a > 0 && f.b < 0) {
        gcd(f.a, -f.b)
    } else {
        gcd(-f.a, -f.b)
    }
    return Fraction(f.a / currentGcd, f.b / currentGcd)
}