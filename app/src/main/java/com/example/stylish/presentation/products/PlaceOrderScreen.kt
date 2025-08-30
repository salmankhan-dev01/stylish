package com.example.stylish.presentation.products

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddBox
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Minimize
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.stylish.R

@Preview
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlaceOrderScreen() {
    Scaffold(topBar = {

    }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(paddingValues)
                .padding(20.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                IconButton(onClick = { }) {
                    Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Back")
                }
                Spacer(modifier = Modifier.width(20.dp))
                Text(text = "Checkout", fontSize = 25.sp, fontWeight = FontWeight.Medium)
            }
            HorizontalDivider()
            Spacer(modifier = Modifier.height(20.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {
                IconButton(onClick = { }) {
                    Icon(imageVector = Icons.Default.LocationOn, contentDescription = "Back")
                }
                Text(text = "Delivery Address", fontSize = 20.sp, fontWeight = FontWeight.Medium)
            }
            Row(
                modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Card(
                    modifier = Modifier
                        .height(80.dp)
                        .width(250.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = Color.White // Background color
                    ),
                    elevation = CardDefaults.cardElevation(
                        defaultElevation = 8.dp // Shadow / elevation
                    )

                    ) {
                    Column (modifier = Modifier.padding(10.dp)){
                        Text(text = "Address :")
                        Text(
                            text = "216 St Paul's Rd, London N1 2LL, UK " +
                                    "Contact :  +44-784232", fontSize = 12.sp, lineHeight = 14.sp
                        )
                    }

                }
                Card(
                    modifier = Modifier
                        .height(80.dp)
                        .width(80.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = Color.White // Background color
                    ),
                    elevation = CardDefaults.cardElevation(
                        defaultElevation = 8.dp // Shadow / elevation
                    )
                ) {
                    Box (modifier = Modifier.fillMaxWidth().height(80.dp),
                        contentAlignment = Alignment.Center){
                        Image(painterResource(id=R.drawable.add),
                            contentDescription = null,
                            modifier = Modifier.size(30.dp))
                    }
                }
            }
            Spacer(modifier = Modifier.height(20.dp))
            HorizontalDivider()
            Spacer(modifier = Modifier.height(20.dp))
            Text(text = "Shopping List", fontSize = 20.sp, fontWeight = FontWeight.Medium)
            Spacer(modifier = Modifier.height(20.dp))
            Card(
                modifier = Modifier
                    .fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = Color.White // Background color
                ),
                elevation = CardDefaults.cardElevation(
                    defaultElevation = 8.dp // Shadow / elevation
                )
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp).padding(bottom = 5.dp) // andar ka padding (4.dp se bada rakho for better look)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.profile),
                            contentDescription = null,
                            modifier = Modifier
                                .width(135.dp)
                                .height(150.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Column(
                            modifier = Modifier.weight(1f) // 👉 Column ko flexible width do
                        ) {
                            Text(
                                text = "Women’s Casual Wear",
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Medium,
                                color = Color.Black
                            )
                            Spacer(modifier = Modifier.height(7.dp))
                            Text(text = "Rating: 4.5 ⭐⭐⭐⭐⭐", fontWeight = FontWeight.Medium)
                            Spacer(modifier = Modifier.height(7.dp))
                            Row {
                                Box(
                                    modifier = Modifier
                                        .height(45.dp)
                                        .width(90.dp)
                                        .border(1.dp, Color.Gray, RoundedCornerShape(4.dp)),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(
                                        text = "\$34.00",
                                        fontSize = 20.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = Color.Black
                                    )
                                }
                                Spacer(modifier = Modifier.width(8.dp))
                                Column {
                                    Text(text = "up to 20% off", fontSize = 10.sp, color = Color.Red)
                                    Text(text = "$40.5", fontSize = 10.sp, color = Color.Gray,    textDecoration = TextDecoration.LineThrough // 👈 underline
                                    )
                                }
                            }
                            Spacer(modifier = Modifier.height(10.dp))
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.Center
                            ) {
                                Box(modifier = Modifier.size(26.dp)
                                    .border(1.dp, Color.Black, RoundedCornerShape(12.dp)), // Border lagana
                                    contentAlignment = Alignment.Center,
                                    ){
                                    Text(text = "-", fontSize = 25.sp, fontWeight = FontWeight.Bold, color = Color.Red)
                                }
                                Spacer(modifier = Modifier.width(20.dp))
                                Text(text = "2", fontSize = 25.sp, fontWeight = FontWeight.Bold)
                                Spacer(modifier = Modifier.width(20.dp))
                                Box(modifier = Modifier.size(26.dp)
                                    .border(1.dp, Color.Black, RoundedCornerShape(12.dp)), // Border lagana
                                    contentAlignment = Alignment.Center){
                                    Text(text = "+", fontSize = 25.sp, fontWeight = FontWeight.Bold, color = Color.Green)
                                }
                            }
                        }
                    }
                    Spacer(modifier = Modifier.height(10.dp))
                    HorizontalDivider()
                    Spacer(modifier = Modifier.height(20.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(text = "Items: 2")
                        Text(text = "$34.00")
                    }
                }
            }

        }
    }
}