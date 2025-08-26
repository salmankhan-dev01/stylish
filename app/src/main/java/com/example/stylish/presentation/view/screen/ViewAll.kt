package com.example.stylish.presentation.view.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FilterAlt
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.UnfoldMore
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.stylish.domain.model.Product
import com.example.stylish.presentation.navigation.Routes
import com.example.stylish.util.Result
import java.math.BigDecimal
import java.math.RoundingMode

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ViewAll(navController: NavHostController, productViewModel: ProductViewModel) {
    val productState by productViewModel.productState.collectAsState()
    var searchQuery by remember { mutableStateOf("") }

    Scaffold(
        topBar = { Top() },
        bottomBar = {
            BottomBar(
                navController,
                Routes.ViewAll
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxWidth()
        ) {
            Spacer(modifier = Modifier.height(10.dp))
            OutlinedTextField(
                value = searchQuery, onValueChange = { searchQuery = it },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = null,
                        modifier = Modifier.size(30.dp),
                        tint = Color(0xFFBBBBBB)
                    )
                },
                placeholder = {
                    Text(
                        "Search any Product",
                        fontSize = 14.sp,
                        color = Color(0xFFBBBBBB)
                    )
                },
                trailingIcon = {
                    Icon(
                        imageVector = Icons.Default.Mic,
                        contentDescription = null,
                        modifier = Modifier.size(30.dp),
                        tint = Color(0xFFBBBBBB)
                    )
                },
                shape = RoundedCornerShape(10.dp),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    containerColor = Color.White,
                    unfocusedBorderColor = Color.White, // ✅ White border
                    focusedBorderColor = Color.White,   // ✅ White border
                    disabledBorderColor = Color.White
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
                    .padding(horizontal = 15.dp)
                    .shadow(
                        elevation = 4.dp,
                        shape = RoundedCornerShape(10.dp),
                        clip = false
                    ) // ✅ Shadow
            )

            Spacer(Modifier.height(10.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 20.dp, end = 20.dp)
            ) {
                when (val state = productState) {
                    is Result.Success -> {
                        Text(
                            text = "${state.data.size} Items",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold, color = Color.Black
                        )
                    }

                    else -> {
                        Text(
                            text = "Loading...",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Medium
                        )
                    }
                }
                Spacer(modifier = Modifier.weight(1f)) // pushes the boxes to the right
                Box(
                    modifier = Modifier
                        .height(30.dp)
                        .width(70.dp)
                        .background(Color.White, shape = RoundedCornerShape(10.dp))
                ) {
                    Row(
                        modifier = Modifier.fillMaxSize(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Text(
                            "Sort",
                            fontSize = 15.sp,
                            color = Color.Black,
                            fontWeight = FontWeight.Medium
                        )
                        Spacer(Modifier.width(5.dp))
                        Icon(
                            imageVector = Icons.Default.UnfoldMore,
                            contentDescription = null,
                            modifier = Modifier.size(23.dp),
                            tint = Color.Black
                        )
                    }
                }

                Spacer(modifier = Modifier.width(8.dp)) // space between boxes
                Box(
                    modifier = Modifier
                        .height(30.dp)
                        .width(70.dp)
                        .background(Color.White, shape = RoundedCornerShape(10.dp))
                ) {
                    Row(
                        modifier = Modifier.fillMaxSize(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Text(
                            "Filter",
                            fontSize = 15.sp,
                            color = Color.Black,
                            fontWeight = FontWeight.Medium
                        )
                        Spacer(Modifier.width(5.dp))
                        Icon(
                            imageVector = Icons.Default.FilterAlt,
                            contentDescription = null,
                            modifier = Modifier.size(23.dp),
                            tint = Color.Black
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.height(20.dp))
            when (val state = productState) {
                is Result.Loading -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                }

                is Result.Success -> {
                    AllProducts(state.data, navController)
                }

                is Result.Failure -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(text = "Error: ${state.message}", color = Color.Red)
                            Spacer(modifier = Modifier.height(10.dp))
                            TextButton(onClick = { productViewModel.retryLoading() }) {
                                Text(text = "Retry")
                            }
                        }
                    }
                }

                is Result.Idle -> {

                }
            }

        }
    }

}

@Composable
fun AllProducts(
    productList: List<Product>,
    navController: NavHostController,
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(productList) { product ->
            val title = product.title
            val image = product.thumbnail
            val description = product.description
            val rating = product.rating
            val stock = product.stock
            val discount = product.discountPercentage
            val price = product.price

            if (
                price != null && discount != null && title != null &&
                image != null && description != null && rating != null && stock != null
            ) {
                val originalPrice = BigDecimal(price / (1 - discount / 100))
                    .setScale(2, RoundingMode.HALF_UP)
                    .toDouble()
                ProductCard(
                    title = title,
                    image = image,
                    dis = description,
                    rating = rating,
                    price = price,
                    dprice = originalPrice,
                    stock = stock.toDouble(),
                    discount = discount.toString(),
                    onClick = { navController.navigate(Routes.ProductDetailScreen(product.id)) }
                )
            }


        }
    }
}

