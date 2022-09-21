package com.example.lab3

import android.graphics.Color
import androidx.lifecycle.ViewModel

class LayoutViewModel(var baseFormActivity: BaseFormActivity) {

   fun getCurrentStepRectColor(step: Int): Int {

       val currentStep = baseFormActivity.getCurrentStep()
       
       if (currentStep == step) {
        return Color.CYAN
       }
       
       return Color.WHITE

   }
    
}