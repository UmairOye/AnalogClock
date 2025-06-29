package com.ub.analogclock

import androidx.compose.ui.graphics.Color

object Utils {
    fun colorList(): List<Color>{

        val colorList = mutableListOf<Color>()
        colorList.apply {
            add(Color.Black)
            add(Color.Yellow)
            add(Color.Magenta)
            add(Color.Red)
            add(Color.White)
            add(Color.Cyan)
            add(Color.Blue)
            add(Color.DarkGray)
            add(Color.Green)
            add(Color.LightGray)
            add(Color.Gray)
        }

        return colorList
    }
}