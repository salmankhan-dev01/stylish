package com.example.stylish.presentation.products

import android.content.Context
import android.content.Intent
import android.util.Log
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.FilterAlt
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.UnfoldMore
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.clipRect
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.stylish.domain.model.Product
import com.example.stylish.navigation.Routes
import com.example.stylish.util.Result
import java.math.BigDecimal
import java.math.RoundingMode

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductDetailScreen(
    navController: NavHostController,
    productViewModel: ProductViewModel,
    productId: Int,
) {
    val productState by productViewModel.productState.collectAsState()
    var selectedImageIndex by remember { mutableStateOf(0) }
    var isFavorite by remember { mutableStateOf(false) }
    val context = LocalContext.current

    val product = when (val state = productState) {
        is Result.Success -> {
            state.data.find { it.id == productId }
        }

        else -> null
    }
    Scaffold(topBar = {
        TopAppBar(
            title = { Text(text = "Product Details") },
            navigationIcon = {
                IconButton(onClick = { navController.popBackStack() }) {
                    Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Back")
                }
            },
            actions = {
                IconButton(onClick = {
                    product?.let { shareProduct(it,context) }
                }) {
                    Icon(imageVector = Icons.Default.Share, contentDescription = "Share")
                }
                IconButton(onClick = { isFavorite = !isFavorite }) {
                    Icon(
                        imageVector = if (isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                        contentDescription = "favorite",
                        tint = if (isFavorite) Color.Red else Color.Gray
                    )
                }
            },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = Color.White
            )
        )
    }) { paddingValues ->
        when {
            productState is Result.Loading -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }

            product == null -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = "Product not found!")
                }
            }

            else -> {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues)
                ) {
                    // Image section
                    item {
                        ProductImageSection(
                            product = product,
                            selectedImageIndex = selectedImageIndex,
                            onImageSelected = { selectedImageIndex = it }

                        )
                    }
                    // Details section of product
                    item { ProductDetailsSection(product = product) }

                    // Add to card button
                    item {
                        AddToCartSection(
                            product = product,
                            onAddToCart = {}
                        )
                    }

                    // for similar products
                    item {
                        val similarProducts = (productState as? Result.Success)?.data?.filter {
                            it.category == product.category
                        } ?: emptyList()
                        if(similarProducts.size>0){
                            Text(text = "Similar To", fontSize = 24.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.Black,
                                modifier = Modifier.padding(horizontal = 20.dp)
                                )
                            Spacer(modifier = Modifier.height(8.dp))
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(start = 20.dp, end = 20.dp)
                            ) {
                                Text(
                                    text = "${similarProducts.size} Items",
                                    fontSize = 20.sp,
                                    fontWeight = FontWeight.Bold, color = Color.Black
                                )
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
                            Spacer(modifier = Modifier.height(10.dp))
                        }

                        LazyVerticalGrid(
                            columns = GridCells.Fixed(2),
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(400.dp),
                            contentPadding = PaddingValues(16.dp),
                            horizontalArrangement = Arrangement.spacedBy(12.dp),
                            verticalArrangement = Arrangement.spacedBy(16.dp)
                        ) {
                            items(similarProducts) { product1 ->
                                val title = product1.title
                                val image = product1.thumbnail
                                val description = product1.description
                                val rating = product1.rating
                                val stock = product1.stock
                                val discount = product1.discountPercentage
                                val price = product1.price

                                if ((price != null && discount != null && title != null && image != null && description != null && rating != null && stock != null) && product1.category == product.category
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
                                        onClick = {
                                            navController.navigate(
                                                Routes.ProductDetailScreen(
                                                    product1.id
                                                )
                                            )
                                        }
                                    )
                                }


                            }

                        }
                    }
                }
            }
        }
    }

}


@Composable
fun ProductImageSection(
    product: Product,
    selectedImageIndex: Int,
    onImageSelected: (Int) -> Unit,
) {
    val images = if (product.images.isNotEmpty()) product.images else listOf(product.thumbnail)
    Column {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp)
                .padding(16.dp),
            shape = RoundedCornerShape(16.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
        ) {
            AsyncImage(
                model = images.getOrNull(selectedImageIndex) ?: product.thumbnail,
                contentDescription = product.title,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
        }
        if (images.size > 1) {
            LazyRow(
                modifier = Modifier.padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(images.size) { index ->
                    Card(
                        modifier = Modifier
                            .size(60.dp)
                            .clip(RoundedCornerShape(8.dp)),
                        colors = CardDefaults.cardColors(
                            containerColor = if (index == selectedImageIndex)
                                Color(0xFF4285F4).copy(alpha = 0.1f) else Color.White
                        ),
                        onClick = { onImageSelected(index) }
                    ) {
                        AsyncImage(
                            model = images[index],
                            contentDescription = null,
                            modifier = Modifier.fillMaxSize(),
                            contentScale = ContentScale.Crop
                        )
                    }
                }
            }
        }
    }
}


@Composable
fun ProductDetailsSection(product: Product) {
    Column(modifier = Modifier.padding(16.dp)) {
        // Brand
        if (product.brand.isNotEmpty()) {
            Text(
                text = product.brand,
                fontSize = 14.sp,
                color = Color(0xFF4285F4),
                fontWeight = FontWeight.Medium
            )
            Spacer(modifier = Modifier.height(8.dp))
        }
        // Title
        Text(
            text = product.title,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )

        Spacer(modifier = Modifier.height(8.dp))
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            StarRating(rating = product.rating, starSize = 20.dp)
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "${
                    String.format(
                        "%.1f",
                        product.rating
                    )
                } (${(product.rating * 100).toInt()} reviews)",
                fontSize = 14.sp,
                color = Color.Gray
            )
        }
        Spacer(modifier = Modifier.height(16.dp))

        // price section
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "₹${String.format("%.2f", product.price )}",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
            if (product.discountPercentage > 0) {
                Spacer(modifier = Modifier.width(12.dp))
                val originalPrice = product.price / (1 - product.discountPercentage / 100)
                Text(
                    text = "₹${String.format("%.2f", originalPrice )}",
                    fontSize = 18.sp,
                    color = Color.Gray,
                    textDecoration = TextDecoration.LineThrough
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "${String.format("%.2f", product.discountPercentage)}% OFF",
                    fontSize = 14.sp,
                    color = Color(0xFF4CAF50),
                    fontWeight = FontWeight.Bold
                )
            }

        }
        Spacer(modifier = Modifier.height(16.dp))
        // Category
        if (product.category.isNotEmpty()) {
            Text(
                text = "Category: ${product.category.replaceFirstChar { it.uppercase() }}",
                fontSize = 14.sp,
                color = Color.Gray
            )
            Spacer(modifier = Modifier.height(8.dp))
        }
        // Stock status
        Text(
            text = if (product.stock > 0) "In Stock (${product.stock} available)" else " Out of Stock",
            fontSize = 14.sp,
            color = if (product.stock > 0) Color(0xFF4CAF50) else Color.Red,
            fontWeight = FontWeight.Medium,
        )
        Spacer(modifier = Modifier.height(16.dp))
        // Description
        Text(
            text = "Description",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = product.description,
            fontSize = 16.sp,
            color = Color.Gray,
            lineHeight = 24.sp
        )
    }
}

@Composable
fun StarRating(rating: Double, starSize: Dp = 16.dp, marStars: Int = 5) {
    Row {
        repeat(marStars) { index ->
            val starRating = when {
                rating >= index + 1 -> 1.0
                rating > index -> rating - index
                else -> 0.0
            }
            Box {
                Icon(
                    Icons.Default.Star,
                    contentDescription = "Rating",
                    tint = Color(0xFFE0E0E0),// gray color
                    modifier = Modifier.size(starSize)
                )
                if (starRating > 0) {
                    Icon(
                        Icons.Default.Star,
                        contentDescription = "Rating",
                        tint = Color(0xFFFFA726),
                        modifier = Modifier
                            .size(starSize)
                            .clipToBounds()
                            .drawWithContent {
                                clipRect(
                                    right = size.width * starRating.toFloat()
                                ) {
                                    this@drawWithContent.drawContent()
                                }
                            }
                    )
                }
            }
        }
    }
}

@Composable
fun AddToCartSection(
    product: Product,
    onAddToCart: () -> Unit,
) {
    Column(modifier = Modifier.padding(16.dp)) {
        Button(
            onClick = onAddToCart,
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            enabled = product.stock > 0,
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF4285F4),
                disabledContentColor = Color.Gray
            ),
            shape = RoundedCornerShape(12.dp)

        ) {
            Text(
                text = if (product.stock > 0) "Add to Cart" else "Out of Stock",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
        }
        Spacer(modifier = Modifier.height(12.dp))
        Button(
            onClick = { Log.d("ProductC", product.category) },
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            enabled = product.stock > 0,
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFFFF6B35),
                disabledContentColor = Color.Gray
            ),
            shape = RoundedCornerShape(12.dp)

        ) {
            Text(
                text = if (product.stock > 0) "Buy Now" else "Out of Stock",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
        }

    }
}

// share function of product

fun shareProduct(product: Product,context: Context){
    val shareText="Check out this amazing product: ${product.title} \n\n" +
            "${product.description}\n\n" +
            "Price: ₹${String.format("%.2f", product.price)}\n" +
            "Rating: ${String.format("%.1f", product.rating)} stars\n\n" +
            "Get it now on Stylish!"
    val shareIntent= Intent().apply {
        action =Intent.ACTION_SEND
        type ="text/plain"
        putExtra(Intent.EXTRA_TEXT,shareText)
        putExtra(Intent.EXTRA_SUBJECT,"Check out ${product.title}")
    }
    val chooserIntent= Intent.createChooser(shareIntent,"Share Product")
    context.startActivity(chooserIntent)
}