package com.example.lab2

import android.text.InputFilter
import android.text.Spanned
import java.util.regex.Matcher
import java.util.regex.Pattern


class InputFilterMinMax : InputFilter {
    private var min: Float
    private var max: Float

    constructor(min: Float, max: Float) {
        this.min = min
        this.max = max
    }

    constructor(min: String, max: String) {
        this.min = min.toFloat()
        this.max = max.toFloat()
    }

    override fun filter(
        source: CharSequence,
        start: Int,
        end: Int,
        dest: Spanned,
        dstart: Int,
        dend: Int
    ): CharSequence? {
        

        val input = (dest.replaceRange(dstart, dend, source)).toString().toFloatOrNull() ?: 0f
        if (input in min..max) {
            return null 
        }
        
        return ""
    }
}

class DecimalDigitsInputFilter(digitsBeforeZero: Int, digitsAfterZero: Int) :
    InputFilter {
    var mPattern: Pattern
    override fun filter(
        source: CharSequence,
        start: Int,
        end: Int,
        dest: Spanned,
        dstart: Int,
        dend: Int
    ): CharSequence? {
        val matcher: Matcher = mPattern.matcher((dest.replaceRange(dstart, dend, source)))
        return if (!matcher.matches()) "" else null
    }

    init {
        mPattern =
            Pattern.compile("[0-9]{0,$digitsBeforeZero}+((\\.[0-9]{0,$digitsAfterZero})?)||(\\.)?")
    }
}