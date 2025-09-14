package com.example.stylish.presentation.products

import android.widget.Toast
import androidx.compose.foundation.Image
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.stylish.R
import com.example.stylish.data.local.entity.AddressEntity
import com.example.stylish.domain.model.CartItem
import com.example.stylish.domain.model.Product
import com.example.stylish.navigation.Routes
import com.example.stylish.presentation.profile.AddressAccountViewModel
import com.example.stylish.util.Result
import com.example.stylish.ui.theme.Pink

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlaceOrderScreen(
    navController: NavHostController,
    productViewModel: ProductViewModel,
    addressAccountViewModel: AddressAccountViewModel,
) {
    val productState by productViewModel.productState.collectAsState()
    val addCartState by productViewModel.addCart.collectAsState()
    val getCartState by productViewModel.getCart.collectAsState()
    val addressResult by addressAccountViewModel.addressResult.collectAsState()


    val address = remember { mutableStateOf("") }
    LaunchedEffect(Unit) {
        addressAccountViewModel.fetchAddress()
    }

    LaunchedEffect(addressResult) {
        if (addressResult is Result.Success) {
            val add = (addressResult as Result.Success<AddressEntity>).data
            // pin add city state coun
            address.value =
                add.address + " " + add.city + " " + add.state + " " + add.country + " (" + add.pinCode + ")"
        }
    }
    val cartProducts = when (val state = getCartState) {
        is Result.Success -> state.data
        else -> emptyList()
    }

    val products = when (val state = productState) {
        is Result.Success -> state.data
        else -> emptyList()
    }

    val filteredProducts = if (cartProducts.isNotEmpty() && products.isNotEmpty()) {
        val cartIds = cartProducts.map { it.productId.toInt() }
        products.filter { it.id in cartIds }
    } else emptyList()
    val count = filteredProducts.sumOf { product ->
        getCartStateValue(product.id, getCartState)
    }

    val finalAmount = filteredProducts.sumOf { product ->
        val cartCount = getCartStateValue(product.id, getCartState)
        cartCount * product.price
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
            Spacer(modifier = Modifier.height(20.dp))
            LazyColumn(
                modifier = Modifier.fillMaxSize()
            ) {
                item {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        IconButton(onClick = { }) {
                            Icon(
                                imageVector = Icons.Default.LocationOn,
                                contentDescription = "Back"
                            )
                        }
                        Text(
                            text = "Delivery Address",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Medium
                        )
                    }
                }
                item {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
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
                            Column(modifier = Modifier.padding(10.dp)) {
                                Text(text = "Address :")
                                Text(
                                    text = address.value, fontSize = 12.sp, lineHeight = 14.sp
                                )
                            }

                        }
                        Card(
                            modifier = Modifier
                                .height(80.dp)
                                .width(80.dp)
                                .clickable {
                                    navController.navigate(Routes.UserProfile)
                                },
                            colors = CardDefaults.cardColors(
                                containerColor = Color.White // Background color
                            ),
                            elevation = CardDefaults.cardElevation(
                                defaultElevation = 8.dp // Shadow / elevation
                            )
                        ) {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(80.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Image(
                                    painterResource(id = R.drawable.add),
                                    contentDescription = null,
                                    modifier = Modifier.size(30.dp)
                                )
                            }
                        }
                    }
                }
                item {
                    Spacer(modifier = Modifier.height(20.dp))
                    HorizontalDivider()
                    Spacer(modifier = Modifier.height(20.dp))
                    Text(text = "Shopping List", fontSize = 20.sp, fontWeight = FontWeight.Medium)
                    Spacer(modifier = Modifier.height(20.dp))
                    HorizontalDivider()
                    Spacer(modifier = Modifier.height(20.dp))
                }
                item {
                    when (getCartState) {
                        is Result.Loading -> {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(400.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                androidx.compose.material3.CircularProgressIndicator(color = Pink)
                            }
                        }

                        is Result.Failure -> {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(400.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = "Failed to load cart",
                                    color = Color.Red,
                                    fontSize = 18.sp
                                )
                            }
                        }

                        is Result.Success -> {
                            if (filteredProducts.isEmpty()) {
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(400.dp),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(text = "Product cart is empty", fontSize = 20.sp)
                                }
                            }
                        }

                        else -> {}
                    }
                }

                items(filteredProducts, key = { it.id }) { product ->
                    val cartCount by remember(product.id, getCartState) {
                        derivedStateOf {
                            getCartStateValue(product.id, getCartState)
                        }
                    }
                    CartProductItem(
                        product = product,
                        cartCount = cartCount,
                        onAdd = { productViewModel.addToCart(product.id.toString(), 1) },
                        onRemove = { productViewModel.addToCart(product.id.toString(), -1) },
                    )

                }
                item {
                    Spacer(modifier = Modifier.height(20.dp))
                    HorizontalDivider()
                    Spacer(modifier = Modifier.height(20.dp))

                }
                if (filteredProducts.size > 0) {
                    item {
                        PaymentSection(finalAmount, count, navController, address)
                    }
                }
            }

        }
    }
}

@Composable
fun PaymentSection(
    totalPrice: Double,
    totalItems: Int,
    navController: NavHostController,
    address: MutableState<String>,
) {
    val context= LocalContext.current
    Spacer(modifier = Modifier.height(20.dp))
    Text(
        text = "Order Payment Details",
        fontSize = 20.sp,
        color = Color.Black,
        fontWeight = FontWeight.Medium
    )
    Spacer(modifier = Modifier.height(30.dp))
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = "Order Amounts", fontSize = 18.sp)
        Text(
            text = "₹ ${String.format("%.2f", totalPrice)}",
            fontSize = 18.sp,
            color = Color.Black,
            fontWeight = FontWeight.Bold
        )
    }
    Spacer(modifier = Modifier.height(20.dp))
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = "Total Items", fontSize = 18.sp)
        Text(
            text = totalItems.toString(),
            fontSize = 18.sp,
            color = Color.Black,
            fontWeight = FontWeight.Bold
        )
    }
    Spacer(modifier = Modifier.height(20.dp))
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = "Convenience", fontSize = 18.sp)
        Text(text = "Apply Coupon", color = Pink)
    }
    Spacer(modifier = Modifier.height(20.dp))
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = "Delivery Fee")
        Text(text = "Free", color = Color.Red, fontWeight = FontWeight.Bold)
    }
    Spacer(modifier = Modifier.height(30.dp))
    HorizontalDivider()
    Spacer(modifier = Modifier.height(30.dp))
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = "Order Total",
            fontSize = 20.sp,
            color = Color.Black,
            fontWeight = FontWeight.Medium
        )
        Text(
            text = "₹ ${String.format("%.2f", totalPrice)}",
            fontSize = 20.sp,
            color = Color.Black,
            fontWeight = FontWeight.Bold
        )
    }
    Spacer(modifier = Modifier.height(30.dp))
    HorizontalDivider()
    Spacer(modifier = Modifier.height(30.dp))
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(110.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "₹ ${String.format("%.2f", totalPrice)}",
            fontSize = 30.sp,
            color = Color.Black,
            fontWeight = FontWeight.Bold
        )

        Button(
            onClick = {
                if (address.value.isNotBlank()) {
                    navController.navigate(Routes.PaymentScreen(totalPrice))
                }else{
                    Toast.makeText(context,"Please fill address first", Toast.LENGTH_SHORT).show()
                }
            },
            modifier = Modifier
                .width(220.dp)
                .height(56.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Pink),
            shape = RoundedCornerShape(12.dp)
        ) {
            Text(
                text = "Proceed to Payment", fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
        }

    }

    Spacer(modifier = Modifier.height(30.dp))


}

@Composable
fun CartProductItem(
    product: Product,
    cartCount: Int,
    onAdd: () -> Unit,
    onRemove: () -> Unit,
) {
    if (cartCount > 0)
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
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(26.dp)
                                    .border(
                                        1.dp,
                                        Color.Black,
                                        RoundedCornerShape(12.dp)
                                    )
                                    .clickable {
                                        onRemove()
                                    },
                                contentAlignment = Alignment.Center,
                            ) {
                                Text(
                                    text = "-",
                                    fontSize = 25.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color.Red
                                )
                            }
                            Spacer(modifier = Modifier.width(20.dp))
                            Text(
                                text = cartCount.toString(),
                                fontSize = 25.sp,
                                fontWeight = FontWeight.Bold
                            )
                            Spacer(modifier = Modifier.width(20.dp))
                            Box(
                                modifier = Modifier
                                    .size(26.dp)
                                    .border(
                                        1.dp,
                                        Color.Black,
                                        RoundedCornerShape(12.dp)
                                    )
                                    .clickable {
                                        onAdd()
                                    },
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = "+",
                                    fontSize = 25.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color.Green
                                )
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
                    Text(text = "Items: $cartCount")
                    Text(text = "₹ ${String.format("%.2f", product.price * cartCount)}")
                }
            }
        }
    Spacer(modifier = Modifier.height(20.dp))
}

fun getCartStateValue(id: Int, getCartState: Result<List<CartItem>>): Int {
    return when (getCartState) {
        is Result.Success -> getCartState.data.firstOrNull { it.productId.toInt() == id }?.quantity
            ?: 0

        else -> 0
    }

}


