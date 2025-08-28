package com.example.stylish.presentation.products

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.stylish.R
import com.example.stylish.navigation.Routes
import com.example.stylish.ui.theme.Pink


@Composable

fun HomeScreen(navHostController: NavHostController){
    Box(
        modifier = Modifier.fillMaxWidth()
    ){
        Image(painter = painterResource(id= R.drawable.adsscreen),
            contentDescription = null,
            contentScale = ContentScale.Crop, // fills and crops
            modifier = Modifier.fillMaxSize())
        Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally){
            Spacer(Modifier.height(500.dp))
            val list = listOf("You want ", "Authentic, here", "you go!")
            for( word in list){
                Text(
                    text = word,
                    color = Color.White,
                    fontSize = 40.sp,
                    fontWeight = FontWeight.ExtraBold
                )
            }
            Spacer(Modifier.height(20.dp))
            Text("Find it here,buy it now", fontSize = 20.sp, color = Color.White)
            Spacer(Modifier.height(49.dp))
            Box(
                modifier = Modifier
                    .fillMaxWidth().clickable {
//                        navHostController.navigate(Routes.ProductScreen) {
//                            popUpTo(Routes.HomeScreen) { inclusive=true }
//                        }
                        navHostController.navigate(Routes.UserProfile)
                    }
                    .height(55.dp)
                    .padding(horizontal = 35.dp)
                    .shadow(
                        elevation = 20.dp,
                        shape = RoundedCornerShape(9.dp),
                        ambientColor = Color.Black,
                        spotColor = Color.Black
                    )
                    .background(
                        color = Pink,
                        shape = RoundedCornerShape(9.dp)
                    )
            )
            {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        "Get Started",
                        fontSize = 25.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White,

                        )
                }

            }
        }


    }
}
