package com.ub.analogclock

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Canvas
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import java.time.LocalTime
import kotlin.math.sin

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AnalogClock(
    modifier: Modifier = Modifier,
    smoothSecondHand: Boolean = true,
    showDigits: Boolean = true,
    showSecondsDot: Boolean = true,
    clockColor: Color = Color.White
) {
    val currentTime = remember { mutableStateOf(LocalTime.now()) }

    LaunchedEffect(smoothSecondHand) {
        while (true) {
            currentTime.value = LocalTime.now()
            delay(if (smoothSecondHand) 16L else 1000L)
        }
    }

    Canvas(modifier = modifier) {
        val radius = size.minDimension / 2
        val center = Offset(size.width / 2, size.height / 2)

        // Clock circle
        drawCircle(
            brush = Brush.radialGradient(
                colors = listOf(Color.Black, clockColor,Color.Black),
                center = center,
                radius = radius
            ),
            radius = radius,
            center = center
        )

        // Paint for numbers
        if (showDigits) {
            val textPaint = android.graphics.Paint().apply {
                color = android.graphics.Color.WHITE
                textSize = 18.sp.toPx()
                textAlign = android.graphics.Paint.Align.CENTER
                isAntiAlias = true
            }

            for (i in 1..12) {
                val angle = Math.toRadians((i * 30 - 90).toDouble())
                val numberRadius = radius - 40
                val x = (center.x + kotlin.math.cos(angle) * numberRadius).toFloat()
                val y = (center.y + sin(angle) * numberRadius).toFloat() + 12

                drawContext.canvas.nativeCanvas.drawText(
                    i.toString(),
                    x,
                    y,
                    textPaint
                )
            }
        }

        // Tick marks
        if (showSecondsDot) {
            for (i in 0 until 60) {
                val angle = Math.toRadians((i * 6 - 90).toDouble())
                val tickLength = if (i % 5 == 0) 15f else 7f
                val start = Offset(
                    x = (center.x + kotlin.math.cos(angle) * (radius - tickLength - 10)).toFloat(),
                    y = (center.y + sin(angle) * (radius - tickLength - 10)).toFloat()
                )
                val end = Offset(
                    x = (center.x + kotlin.math.cos(angle) * (radius - 10)).toFloat(),
                    y = (center.y + sin(angle) * (radius - 10)).toFloat()
                )
                drawLine(Color.White, start, end, strokeWidth = if (i % 5 == 0) 3f else 1.5f)
            }
        }

        val time = currentTime.value

        // Hands
        val hourAngle = ((time.hour % 12) + time.minute / 60f) * 30f - 90f
        drawHand(center, radius * 0.5f, hourAngle, Color.White, 6f)

        val minuteAngle = (time.minute + time.second / 60f) * 6f - 90f
        drawHand(center, radius * 0.7f, minuteAngle, Color.LightGray, 4f)

        val second =
            if (smoothSecondHand) time.nano / 1_000_000_000f + time.second else time.second.toFloat()
        val secondAngle = second * 6f - 90f
        drawHand(center, radius * 0.9f, secondAngle, Color.Red, 2f)

        // Center dot

        drawCircle(Color.Red, radius = 6f, center = center)
    }
}

fun DrawScope.drawHand(
    center: Offset,
    length: Float,
    angleDegrees: Float,
    color: Color,
    strokeWidth: Float
) {
    val angleRad = Math.toRadians(angleDegrees.toDouble())
    val end = Offset(
        x = (center.x + kotlin.math.cos(angleRad) * length).toFloat(),
        y = (center.y + sin(angleRad) * length).toFloat()
    )

    drawLine(color, center, end, strokeWidth = strokeWidth)
}
