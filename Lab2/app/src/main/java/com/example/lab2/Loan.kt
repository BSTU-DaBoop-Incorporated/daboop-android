package com.example.lab2

enum class PayType {
    Annual,
    Differential
}

enum class DwellingType {
    New,
    Resell,
    Building
}

data class Loan(
    val LoanSum: Float,
    val PayType: PayType,
    val DwellingType: DwellingType,
    val LoanTerm: Int,
    val LoanPercentage: Float,
)