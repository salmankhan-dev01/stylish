package com.example.stylish.presentation.order

import android.util.Log
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.stylish.domain.model.Product
import com.example.stylish.presentation.products.ProductViewModel
import com.example.stylish.presentation.products.StarRating
import com.example.stylish.presentation.products.getCartStateValue
import com.example.stylish.util.Result

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OrderScreen(
    navController: NavHostController,
    productViewModel: ProductViewModel,
    orderScreenViewModel: OrderScreenViewModel,
) {
    val productState by productViewModel.productState.collectAsState()
    val getOrderState by orderScreenViewModel.getOrderState.collectAsState()

    LaunchedEffect(Unit) {
        orderScreenViewModel.getOrderProduct()
    }
    var cartProducts = when (val state = getOrderState) {
        is Result.Success -> state.data
        else -> emptyList()
    }
    cartProducts=cartProducts.reversed()

    val products = when (val state = productState) {
        is Result.Success -> state.data
        else -> emptyList()
    }



    Scaffold(topBar = {

    }
    ) { paddingValues ->
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
            LazyColumn(
                modifier = Modifier.fillMaxSize()
            ) {
                item {
                    Spacer(modifier = Modifier.height(20.dp))

                    when (getOrderState) {
                        is Result.Loading -> {
                            // 🔄 Loading Indicator
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(400.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                androidx.compose.material3.CircularProgressIndicator()
                            }
                        }

                        is Result.Failure -> {
                            // ❌ Error State
                            val errorMsg = (getOrderState as Result.Failure).message
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(400.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(text = errorMsg, fontSize = 20.sp, color = Color.Red)
                            }
                        }

                        is Result.Success -> {
                            if (cartProducts.isEmpty()) {
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(400.dp),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(text = "No order found", fontSize = 30.sp)
                                }
                            }
                        }

                        else -> {}
                    }
                }
                items(cartProducts) { order ->
                    val product=products.firstOrNull{it.id==order.productId.toInt()}
                    if(product!=null){
                        OrderProductItem(
                            product = product,
                            cartCount = order.quantity,
                        )
                    }


                }
            }

        }
    }
}

@Composable
fun OrderProductItem(
    product: Product,
    cartCount: Int,

) {
    if (cartCount > 0)
        Card(
            modifier = Modifier
                .fillMaxWidth().clickable{
                    Log.d("checker111",product.id.toString())
                },
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
                    .padding(8.dp)
                    .padding(bottom = 5.dp) // andar ka padding (4.dp se bada rakho for better look)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    AsyncImage(
                        model = product.thumbnail,
                        contentDescription = product.title,
                        modifier = Modifier
                            .width(135.dp)
                            .height(150.dp),
                        contentScale = ContentScale.Crop
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Column(
                        modifier = Modifier.weight(1f) // 👉 Column ko flexible width do
                    ) {
                        Text(
                            text = product.title,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Medium,
                            color = Color.Black
                        )
                        Spacer(modifier = Modifier.height(7.dp))
                        Text(
                            text = "Rating: ${String.format("%.1f", product.rating)}",
                            fontWeight = FontWeight.Medium
                        )
                        StarRating(product.rating, starSize = 15.dp)
                        Spacer(modifier = Modifier.height(7.dp))
                        Row {
                            Box(
                                modifier = Modifier
                                    .height(45.dp)
                                    .width(95.dp)
                                    .border(1.dp, Color.Gray, RoundedCornerShape(4.dp)),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = "₹${String.format("%.2f", product.price)}",
                                    fontSize = 20.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color.Black
                                )
                            }
                            Spacer(modifier = Modifier.width(8.dp))
                            if (product.discountPercentage > 0) {
                                val originalPrice =
                                    product.price / (1 - product.discountPercentage / 100)

                                Column {
                                    Text(
                                        text = "up to ${
                                            String.format(
                                                "%.2f",
                                                product.discountPercentage
                                            )
                                        }% off",
                                        fontSize = 10.sp,
                                        color = Color.Red
                                    )
                                    Text(
                                        text = "₹ ${String.format("%.2f", originalPrice)}",
                                        fontSize = 10.sp,
                                        color = Color.Gray,
                                        textDecoration = TextDecoration.LineThrough // 👈 underline
                                    )
                                }
                            }
                        }
                        Spacer(modifier = Modifier.height(10.dp))
                    }
                }
                Spacer(modifier = Modifier.height(10.dp))
                HorizontalDivider()
                Spacer(modifier = Modifier.height(20.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(text = "Items: $cartCount")
                    Text(text = "₹ ${String.format("%.2f", product.price * cartCount)}")
                }
                Spacer(modifier = Modifier.height(10.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(text = "Status")
                    Text(text = "Pending", color = Color.Yellow)
                }
            }
        }
     Spacer(modifier = Modifier.height(20.dp))
}