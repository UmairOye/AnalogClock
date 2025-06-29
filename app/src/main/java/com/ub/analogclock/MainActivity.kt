package com.ub.analogclock

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import com.ub.analogclock.ui.mainScreen.MainScreen
import com.ub.analogclock.ui.theme.AnalogClockTheme

class MainActivity : ComponentActivity() {

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
            setContent {
            AnalogClockTheme {
                MainScreen()
            }
        }
    }
}


