package com.example.denlauncher.ui.main

import android.content.Context
import android.util.AttributeSet
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import kotlinx.android.synthetic.main.item_installed_apps.view.*

class InstalledAppItemView(context: Context, attrs: AttributeSet?) :
    ConstraintLayout(context, attrs) {

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        app_name.setOnFocusChangeListener { v, hasFocus ->
            val size = if (hasFocus) {
                30f
            } else {
                20f
            }
            (v as TextView).textSize = size
        }
    }
}