package com.example.stylish.presentation.view.onboarding

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.stylish.R
import com.example.stylish.presentation.navigation.Routes


@Composable
fun SkipScreen2(navHostController: NavHostController) {
    Scaffold(
        topBar = {
            Top2(navHostController)
        },
        bottomBar = {
            Down2(navHostController)
        }
    ) {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = Color.White
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(it),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(Modifier.height(90.dp))
                Image(
                    painter = painterResource(id = R.drawable.skipimage2),
                    contentDescription = null,
                    modifier = Modifier.size(340.dp)
                )
                Spacer(Modifier.height(1.dp))

                Text(
                    "Make Payment",
                    fontSize = 30.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = Color.Black
                )
                Spacer(Modifier.height(4.dp))
                Text(
                    "Your money is in good hands",
                    fontSize = 20.sp,
                    color = Color.Gray,
                    fontWeight = FontWeight.Bold
                )
                Spacer(Modifier.height(2.dp))
                Text(
                    "Pay quickly and safely",
                    fontSize = 20.sp,
                    color = Color.Gray,
                    fontWeight = FontWeight.Bold
                )
                Spacer(Modifier.height(2.dp))
                Text(
                    "Just one tap to finish",
                    fontSize = 20.sp,
                    color = Color.Gray,
                    fontWeight = FontWeight.Bold
                )


            }
        }
    }
}

@Composable
fun Top2(navHostController: NavHostController) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .height(80.dp)
            .padding(20.dp), color = Color.White

    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text("2", fontSize = 30.sp, color = Color.Black, fontWeight = FontWeight.Bold)
            Text("/3", fontSize = 30.sp, color = Color.Gray, fontWeight = FontWeight.Bold)
            Spacer(Modifier.width(240.dp))
            Text(
                "Skip", fontSize = 30.sp, color = Color.Black, fontWeight = FontWeight.Bold,
                modifier = Modifier.clickable {
                    navHostController.navigate(
                        Routes.LoginScreen
                    ){
                        popUpTo(0){
                            inclusive=true
                        }
                        launchSingleTop = true

                    }
                })
        }
    }

}

@Composable
fun Down2(navHostController: NavHostController) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .height(80.dp), color = Color.White
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(start = 310.dp)
        ) {
            Text(
                "Next",
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Red,
                modifier = Modifier.clickable {
                    navHostController.navigate(
                        Routes.SkipScreen3
                    )
                })
        }
    }
}
//
//@Preview(showBackground = true)
//@Composable
//fun SkipPreview2(){
//    SkipScreen2()
//}