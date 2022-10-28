package lab11

class A (val a : Int) {
    private lateinit var prop: String
    fun setUp() {
        prop = "100 + 200 "
    }
    fun display() {
        println(prop)
    }
    
    operator fun plus(other: A): A {
        return A(a + other.a)
    }

    override fun equals(other: Any?): Boolean {
        return other is A && a == other.a
    }
}