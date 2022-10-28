package lab11

fun converter (which: Char): (Double, Double) -> Double {
    return when (which) {
        '+' -> {a, b -> a + b}
        '-' -> {a, b -> a - b}
        '*' -> {a, b -> a * b}
        '/' -> {a, b -> a / b}
        else -> {_, _ -> 0.0}
    }
}