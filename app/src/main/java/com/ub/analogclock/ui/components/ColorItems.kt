package com.ub.analogclock.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun ColorItems(modifier: Modifier = Modifier,
               color: Color = Color.Black,
               onClicked:()-> Unit
){
    Canvas(modifier = modifier
        .clickable{
            onClicked()
        }.padding(all = 5.dp).size(25.dp)){
        drawRoundRect(
            color = color,
            cornerRadius = CornerRadius(x = 5f, y = 5f)
        )
    }
}