package com.ub.analogclock.ui.mainScreen

 import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
 import androidx.compose.foundation.lazy.LazyColumn
 import androidx.compose.foundation.lazy.LazyRow
 import androidx.compose.foundation.lazy.items
 import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
 import androidx.compose.ui.graphics.Color
 import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import com.ub.analogclock.AnalogClock
import com.ub.analogclock.R
 import com.ub.analogclock.Utils
 import com.ub.analogclock.ui.components.ColorItems

@RequiresApi(Build.VERSION_CODES.O)
@Composable
@Preview(showBackground = true, showSystemUi = true)
fun MainScreen() {
    var checked by remember { mutableStateOf(false) }
    var showDigits by remember { mutableStateOf(true) }
    var showSecondDots by remember { mutableStateOf(true) }
    var clockColors by remember { mutableStateOf(Color.Black) }

    ConstraintLayout(
        modifier = Modifier
            .statusBarsPadding()
            .fillMaxSize()
    ) {


        val (analogClock, smoothSecondText, smoothSecondToggle,
            digitText, digitToggle, showSecondText, showSecondToggle,
            colorText, colorList) = createRefs()

        AnalogClock(
            clockColor = clockColors,
            smoothSecondHand = checked,
            showDigits = showDigits,
            showSecondsDot = showSecondDots,
            modifier = Modifier
                .size(200.dp)
                .constrainAs(analogClock) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                })


        ClockText(
            text = stringResource(R.string.smooth_seconds),
            modifier = Modifier
                .padding(start = 15.dp)
                .constrainAs(smoothSecondText) {
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    bottom.linkTo(smoothSecondToggle.bottom)
                    top.linkTo(smoothSecondToggle.top)
                    horizontalBias = 0f
                    verticalBias = 0.7f
                })

        ClockSwitch(
            checked = checked,
            onCheckedChange = {
                checked = it
            },
            modifier = Modifier
                .padding(top = 25.dp, end = 15.dp)
                .scale(.8f)
                .constrainAs(smoothSecondToggle) {
                    top.linkTo(analogClock.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    horizontalBias = 1f
                })

        ClockText(
            text = stringResource(R.string.show_hours),
            modifier = Modifier
                .padding(start = 15.dp)
                .constrainAs(digitText) {
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    bottom.linkTo(digitToggle.bottom)
                    top.linkTo(digitToggle.top)
                    horizontalBias = 0f
                    verticalBias = 0.5f
                })

        ClockSwitch(checked = showDigits, onCheckedChange = {
            showDigits = it
        }, modifier = Modifier
            .padding(end = 15.dp)
            .scale(.8f)
            .constrainAs(digitToggle) {
                top.linkTo(smoothSecondToggle.bottom)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
                horizontalBias = 1f
            })

        ClockText(
            text = stringResource(R.string.show_seconds),
            modifier = Modifier
                .padding(start = 15.dp)
                .constrainAs(showSecondText) {
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    bottom.linkTo(showSecondToggle.bottom)
                    top.linkTo(showSecondToggle.top)
                    horizontalBias = 0f
                    verticalBias = 0.5f
                })

        ClockSwitch(checked = showSecondDots, onCheckedChange = {
            showSecondDots = it
        }, modifier = Modifier
            .padding(end = 15.dp)
            .scale(.8f)
            .constrainAs(showSecondToggle) {
                top.linkTo(digitToggle.bottom)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
                horizontalBias = 1f
            })
        
        ClockText(
            text = stringResource(R.string.clock_colors),
            modifier = Modifier
                .padding(start = 15.dp)
                .constrainAs(colorText) {
                    top.linkTo(showSecondToggle.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    horizontalBias = 0f
                }
        )

        LazyRow(
            modifier = Modifier
                .padding(
                    start = 15.dp,
                    end = 15.dp, top = 15.dp
                )
                .constrainAs(colorList) {
                    top.linkTo(colorText.bottom)
                    end.linkTo(parent.end)
                    start.linkTo(parent.start)
                }
        ) {
            items(Utils.colorList()){
                ColorItems(color = it, onClicked = {
                    clockColors = it
                })
            }
        }
    }
}

@Composable
fun ClockSwitch(
    checked: Boolean, onCheckedChange: (Boolean) -> Unit, modifier: Modifier = Modifier
) {

    Switch(
        checked = checked, onCheckedChange = {
            onCheckedChange(it)
        }, colors = SwitchDefaults.colors(
            checkedThumbColor = colorResource(R.color.white),
            checkedTrackColor = colorResource(R.color.teal_200),
            uncheckedThumbColor = colorResource(R.color.black),
            uncheckedTrackColor = colorResource(R.color.light_gray)
        ), modifier = modifier
    )
}

@Composable
fun ClockText(
    text: String, modifier: Modifier
) {
    Text(
        text = text, fontSize = 15.sp, color = colorResource(R.color.black), modifier = modifier
    )
}
