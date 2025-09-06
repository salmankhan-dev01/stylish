package com.example.stylish.presentation.products

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavHostController
import com.example.stylish.R
import com.example.stylish.navigation.Routes
import com.example.stylish.ui.theme.Pink

@Composable
fun PaymentScreen(navController: NavHostController, totalPrice: Double) {
    val paymentMethods =
        listOf("**********2105", "**********2106", "**********2107", "**********2108")
    // By default first select hoga
    var selectedMethod by remember { mutableStateOf(paymentMethods[0]) }
    var showPopup by remember { mutableStateOf(false) }

    Scaffold { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(paddingValues)
                .padding(horizontal = 20.dp)
        ) {

            Row(verticalAlignment = Alignment.CenterVertically) {
                IconButton(onClick = { navController.popBackStack() }) {
                    Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Back")
                }
                Spacer(modifier = Modifier.width(20.dp))
                Text(text = "Checkout", fontSize = 25.sp, fontWeight = FontWeight.Medium)
            }
            HorizontalDivider()

            Spacer(modifier = Modifier.height(30.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = "Order Amounts", fontSize = 22.sp, color = Color.Gray)
                Text(
                    text = "₹ ${String.format("%.2f", totalPrice)}",
                    fontSize = 22.sp,
                    color = Color.Gray,
                    fontWeight = FontWeight.Bold
                )
            }
            Spacer(modifier = Modifier.height(20.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = "Shipping", fontSize = 22.sp, color = Color.Gray)
                Text(
                    text = "+30.0",
                    fontSize = 22.sp,
                    color = Color.Gray,
                    fontWeight = FontWeight.Bold
                )
            }
            Spacer(modifier = Modifier.height(20.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = "Total Amounts", fontSize = 22.sp)
                Text(
                    text = "₹ ${String.format("%.2f", totalPrice + 30.0)}",
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold
                )
            }
            Spacer(modifier = Modifier.height(20.dp))
            HorizontalDivider()
            Spacer(modifier = Modifier.height(30.dp))
            // Payment Methods Section
            Text(text = "Payment", fontSize = 28.sp, fontWeight = FontWeight.Medium)
            Spacer(modifier = Modifier.height(20.dp))

            paymentMethods.forEach { method ->
                val logoRes = when (method) {
                    "**********2105" -> R.drawable.visa
                    "**********2106" -> R.drawable.paypal
                    "**********2107" -> R.drawable.maestro
                    "**********2108" -> R.drawable.apple
                    else -> R.drawable.visa
                }
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 6.dp)
                        .clickable { selectedMethod = method }
                        .border(
                            width = 2.dp,
                            color = if (selectedMethod == method) Pink else Color.Gray,
                            shape = RoundedCornerShape(12.dp)
                        ),
                    colors = CardDefaults.cardColors(containerColor = Color.LightGray),
                    shape = RoundedCornerShape(12.dp),
                    elevation = CardDefaults.cardElevation(4.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(10.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Image(
                            painterResource(id = logoRes),
                            contentDescription = null,
                            modifier = Modifier.size(50.dp)
                        )
                        Text(text = method, fontSize = 18.sp, fontWeight = FontWeight.Bold)
                    }
                }
            }
            Spacer(modifier = Modifier.height(20.dp))
            Button(
                onClick = { showPopup = true },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(66.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Pink),
                shape = RoundedCornerShape(12.dp)

            ) {
                Text(
                    text = "Pay ₹${String.format("%.2f", totalPrice + 30.0)}",
                    fontSize = 25.sp,
                    fontWeight = FontWeight.Medium
                )
            }
            if (showPopup) {
                Dialog(onDismissRequest = { }) {
                    Card(
                        shape = RoundedCornerShape(16.dp),
                        elevation = CardDefaults.cardElevation(8.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(32.dp)
                    ) {
                        Column(
                            modifier = Modifier
                                .padding(24.dp),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            // Success Icon
                            Box(
                                modifier = Modifier.size(100.dp), // parent box size
                                contentAlignment = Alignment.Center
                            ) {
                                // Background Icon (Success Circle)
                                Icon(
                                    painter = painterResource(id = R.drawable.sucess), // background success icon
                                    contentDescription = "Success Background",
                                    tint = Pink,
                                    modifier = Modifier.size(80.dp)
                                )

                                // Overlay Icon (Tick / Star / Any small icon)
                                Icon(
                                    imageVector = Icons.Default.Check, // tick icon
                                    contentDescription = "Overlay Icon",
                                    tint = Color.White,
                                    modifier = Modifier.size(40.dp)
                                )
                            }


                            Spacer(modifier = Modifier.height(16.dp))

                            // Success Message
                            Text(
                                text = "Payment Successful!",
                                style = MaterialTheme.typography.titleMedium,
                                color = Pink
                            )

                            Spacer(modifier = Modifier.height(24.dp))

                            Button(
                                onClick = {
                                    showPopup = false
                                    navController.navigate(Routes.ProductScreen){
                                        popUpTo(0)
                                    }
                                },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(40.dp),
                                shape = RoundedCornerShape(9.dp),
                                colors = ButtonDefaults.buttonColors(containerColor = Pink)
                            ) {
                                Text("OK")
                            }
                        }
                    }
                }
            }


        }
    }


}
//import com.example.stylish.ui.theme.Pink
