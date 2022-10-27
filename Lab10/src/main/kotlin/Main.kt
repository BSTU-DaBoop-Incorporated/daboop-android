import java.awt.font.NumericShaper.Range
import java.lang.Integer.min
import java.time.LocalDate
import kotlin.math.sqrt

var intVar: Int = 0
val doubleVar = 0.0
var stringVar: String = "Hello World"
val nullableInt: Int? = null

enum class Holidays(val date: LocalDate) {
    NEW_YEAR(LocalDate.of(2022, 1, 1)),
    CHRISTMAS(LocalDate.of(2022, 12, 25)),
    EASTER(LocalDate.of(2022, 4, 21));
}

fun main(args: Array<String>) {
    println("Hello World!")

    println("Program arguments: ${args.joinToString()}")
    StringDemo.run();


    println("${intVar.toDouble()} $doubleVar $stringVar $nullableInt")

    fun notNull(obj: Any?) = obj != null

    println("${notNull(intVar)} ${notNull(nullableInt)}")
    println("${isValid("admin@test.sa", "admina")} ${isValid("adsdfsdfs.sd", "admin")}")
    println("${getHoliday(LocalDate.of(2022, 1, 1))} ${getHoliday(LocalDate.of(2022, 1, 2))}")
    try {
        println(
            "${doOperation(1, 2, '-')} ${doOperation(1, 2, '+')}" +
                    " ${doOperation(1, 2, '*')} ${doOperation(1, 2, 'a')}"
        )
    } catch (e: Exception) {
        println(e.message)
    }

    val arr1 = intArrayOf(1, 2, 3, 4, 5)
    val arr2 = intArrayOf()
    println("${arr1.indexOfMax()} ${arr2.indexOfMax()}")
    println("string".coincidence("straight"))
    println("${factorialCycle(5)} ${factorialRecursion(5)} ${factorialRange(5)}")
    var primeList = mutableListOf<Int>()
    var primeArray = arrayOf<Int>()
    var count = 0
    for (i in 1..10000) {
        if (isPrime(i)) {
            count++
            if (count <= 20)
                primeList.add(i)
            if (count in 21..30)
                primeArray += i
        }
    }

    println("Prime numbers from 1 to 10000: $count")
    println("${primeList.joinToString()} || ${primeArray.joinToString()}")

    println("${primeList.any { el -> el == 3 }} ${primeList.all { el -> el < 2 }} ${primeList.containsIn(7)}")


    var numbers = mutableListOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)
    numbers.add(11)
    numbers += 1
    numbers = numbers.distinct().toMutableList()
    numbers = numbers.filter { it % 2 == 1 }.toMutableList()
    numbers = numbers.filter(::isPrime).toMutableList()
    for (i in numbers) {
        print("$i ")
    }
    println()

    numbers.find { it == 3 }?.let { println(it) }
    numbers.groupBy { it % 2 == 0 }.forEach { println(it) }
    numbers.all { it < 10 }.let { println(it) }
    numbers.any { it > 10 }.let { println(it) }
    val (first, second) = numbers
    println("$first $second")

    val scoreMap = mutableMapOf(
        40 to 10,
        39 to 9,
        38 to 8
    )
    for (i in 0..18) scoreMap[i] = 1
    for (i in 19..21) scoreMap[i] = 2
    for (i in 22..24) scoreMap[i] = 3
    for (i in 25..28) scoreMap[i] = 4
    for (i in 29..31) scoreMap[i] = 5
    for (i in 32..34) scoreMap[i] = 6
    for (i in 35..37) scoreMap[i] = 7
    
    
    val answerCounts = listOf(3, 6, 10, 15, 20 , 31, 34, 35, 40)
    answerCounts.map { scoreMap[it] }.forEach{ print("$it ") }
    println()
    answerCounts.map { scoreMap[it] }.groupingBy { it }.eachCount().forEach {print("$it ")}
    println()
    answerCounts.count { (scoreMap[it] ?: 0) < 4 }.let { println("<4 count: $it") }
    
    println()


}

fun isValid(login: String, password: String): Boolean {
    val emailRegex = Regex("^[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+\$")
    val passwordRegex = Regex("^[a-zA-Z0-9]+\$")
    return emailRegex.matches(login) && passwordRegex.matches(password) && password.length >= 6 && password.length <= 12
}

fun getHoliday(date: LocalDate?): Holidays? {
    return when (date) {
        Holidays.NEW_YEAR.date -> Holidays.NEW_YEAR
        Holidays.CHRISTMAS.date -> Holidays.CHRISTMAS
        Holidays.EASTER.date -> Holidays.EASTER
        else -> null
    }
}

fun doOperation(a: Int, b: Int, operation: Char) {
    when (operation) {
        '+' -> println(a + b)
        '-' -> println(a - b)
        '*' -> println(a * b)
        '/' -> println(a / b)
        '%' -> println(a % b)
        else -> throw IllegalArgumentException("Unknown operation")
    }
}

fun IntArray.indexOfMax(): Int? {
    if (isEmpty())
        return null

    val arr = this
    var max = arr[0]
    var index = 0
    for (i in arr.indices) {
        if (arr[i] > max) {
            max = arr[i]
            index = i
        }
    }
    return index
}

fun String.coincidence(str: String): Int {
    var count = 0
    for (i in 0 until min(this.length, str.length)) {
        if (this[i] == str[i])
            count++
    }
    return count
}

fun factorialRange(n: Int): Int {
    if (n < 0)
        throw IllegalArgumentException("n must be >= 0")
    var result = 1
    for (i in 1..n)
        result *= i
    return result
}

fun factorialCycle(n: Int): Int {
    if (n < 0)
        throw IllegalArgumentException("n must be >= 0")
    var result = 1
    var i = 1
    while (i <= n) {
        result *= i
        i++
    }
    return result
}

fun factorialRecursion(n: Int): Int {
    if (n < 0)
        throw IllegalArgumentException("n must be >= 0")
    return if (n == 0)
        1
    else
        n * factorialRecursion(n - 1)
}

fun isPrime(n: Int): Boolean {
    if (n <= 1)
        return false
    if (n == 2)
        return true
    if (n % 2 == 0)
        return false
    val root = sqrt(n.toDouble()).toInt()
    for (i in 3..root step 2) {
        if ((n % i) == 0) {
            return false
        }
    }

    return true
}

fun <T> Collection<T>.containsIn(element: T): Boolean {
    return this.any { it == element }
}

object StringDemo {
    @JvmStatic
    fun run() {
        val palindrome = "Dot saw I was Tod"
        val len = palindrome.length
        val tempCharArray = CharArray(len)
        val charArray = CharArray(len)
        for (i in 0 until len) {
            tempCharArray[i] = palindrome[i]
        }
        for (j in 0 until len) {
            charArray[j] = tempCharArray[len - 1 - j]
        }
        val reversePalindrome = String(charArray)
        println(reversePalindrome)
    }
}