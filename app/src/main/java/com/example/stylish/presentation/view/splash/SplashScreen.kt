package com.example.stylish.presentation.view.splash

import android.annotation.SuppressLint
import android.view.animation.BounceInterpolator
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.Easing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.stylish.R
import com.example.stylish.presentation.navigation.Routes
import kotlinx.coroutines.delay

@SuppressLint("UseOfNonLambdaOffsetOverload")

@Composable
fun SplashScreen() {
    val dropAnim = remember { Animatable(-300f) }

    // Launch animation when composable is composed
//    LaunchedEffect(Unit) {
//        dropAnim.animateTo(
//            targetValue = 0f,
//            animationSpec = tween(
//                durationMillis = 1000,
//                easing = BounceInterpolatorEasing
//            )
//        )
//        delay(2000)
//        navHostController.navigate(Routes.SkipScreen1) {
//            popUpTo(Routes.SplashScreen) {
//                inclusive = true
//            }
//        }
//    }

    Surface(modifier = Modifier.fillMaxSize(), color = Color.White) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.splash_image),
                contentDescription = "logo",
                modifier = Modifier
                    .size(270.dp)
            )

            CircularProgressIndicator(
                color = Color.Red,
                modifier = Modifier.size(50.dp)
            )


        }
    }
}

