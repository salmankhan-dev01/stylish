package com.example.stylish.presentation.products

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.animateScrollBy
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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIos
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.ArrowForwardIos
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.FilterAlt
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.StarHalf
import androidx.compose.material.icons.filled.UnfoldMore
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.stylish.R
import com.example.stylish.domain.model.Product
import com.example.stylish.navigation.Routes
import com.example.stylish.util.Result
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.launch
import okhttp3.Route
import java.math.BigDecimal
import java.math.RoundingMode

//MAIN SCREEN
//@Preview(showBackground = true)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductScreen(navController: NavHostController, productViewModel: ProductViewModel) {


    val productState by productViewModel.productState.collectAsState()//tracking flow
    var searchQuery by remember { mutableStateOf("") }
    val firebaseUser = FirebaseAuth.getInstance().currentUser
    val username = firebaseUser?.displayName ?: "No Name"
    val email = firebaseUser?.email ?: "No Email"
    Log.d("username", username + "___" + email)
    //----------Category(Image and Text)----------
    val imageList = listOf(
        R.drawable.one,
        R.drawable.four,
        R.drawable.five,
        R.drawable.two,
        R.drawable.three,
        R.drawable.six,
        R.drawable.seven,
    )
    val nameList =
        listOf("Beauty", "Mens", "Woman", "Groceries", "Furniture", "Fragrances", "Kitchen")
    val categoryMap = mapOf(
        "Beauty" to listOf("beauty"),
        "Mens" to listOf("mens-watches", "mens-shirts", "mens-shoes"),
        "Woman" to listOf("womens-watches", "womens-dresses", "womens-shoes"),
        "Groceries" to listOf("groceries"),
        "Furniture" to listOf("furniture"),
        "Fragrances" to listOf("fragrances"),
        "Kitchen" to listOf("kitchen-accessories"),
    )


    //----------USER INTERFACE------------------
    Scaffold(
        topBar = { Top(navController) },
        bottomBar = { BottomBar(navController, Routes.ProductScreen) },
        modifier = Modifier.fillMaxSize()
    ) { paddingValues ->
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues), color = Color(0xFFF1F1F1)
        ) {

            //BOX BECAUSE WE HAVE TO SHOW LOADING IN MIDDLE
            Box(modifier = Modifier.fillMaxSize()) {

                //-----------COLUMN ONE ----------------
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState())
                ) {
                    Spacer(Modifier.height(20.dp))

                    //----SEARCH BOX----------------
                    OutlinedTextField(
                        value = searchQuery,
                        onValueChange = { searchQuery = it },
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
                                "Search any Product", fontSize = 14.sp, color = Color(0xFFBBBBBB)
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
                                elevation = 4.dp, shape = RoundedCornerShape(10.dp), clip = false
                            )

                    )
                    Spacer(Modifier.height(10.dp))


                    //----------All Feature(SORT AND FILTER)------------
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
                                    fontWeight = FontWeight.Bold,
                                    color = Color.Black
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
                    Spacer(Modifier.height(20.dp))


                    //---------Category(DISPLAY)----------------
                    LazyRow(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 20.dp),
                        horizontalArrangement = Arrangement.spacedBy(20.dp),
                        contentPadding = PaddingValues(horizontal = 8.dp)
                    ) {
                        itemsIndexed(imageList) { index, image ->
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                modifier = Modifier.clickable {
                                    val categories =
                                        categoryMap[nameList[index]]?.joinToString(",") ?: ""
                                    navController.navigate(Routes.CategoryProduct(categories))
                                }) {
                                Image(
                                    painter = painterResource(id = image),
                                    contentDescription = null,
                                    modifier = Modifier
                                        .size(56.dp)
                                        .clip(CircleShape)
                                )
                                Text(
                                    text = nameList[index],  // ✅ Safe access
                                    fontSize = 15.sp, color = Color.Black
                                )
                            }
                        }

                    }

                    Spacer(Modifier.height(20.dp))
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(260.dp)
                            .padding(horizontal = 20.dp)
                    ) {
                        Column(modifier = Modifier.fillMaxWidth()) {
                            Image(
                                painter = painterResource(R.drawable.ads),
                                contentDescription = null,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(200.dp),
                                contentScale = ContentScale.Crop  // 🟢 Key line to cover the box
                            )
                        }
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(start = 20.dp)
                        ) {
                            Spacer(Modifier.height(40.dp))
                            Text(
                                "50-40% OFF",
                                fontSize = 25.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.White
                            )
                            Spacer(Modifier.height(7.dp))
                            Text("Now in (product)", fontSize = 15.sp, color = Color.White)
                            Spacer(Modifier.height(7.dp))
                            Text("All colours", fontSize = 15.sp, color = Color.White)
                            Spacer(Modifier.height(9.dp))
                            Box(
                                modifier = Modifier
                                    .height(35.dp)
                                    .width(110.dp)
                                    .background(
                                        color = Color.Transparent, shape = RoundedCornerShape(10.dp)
                                    )
                                    .border(
                                        width = 1.dp,
                                        color = Color.White,
                                        shape = RoundedCornerShape(8.dp)
                                    )
                                    .padding(horizontal = 8.dp) // spacing inside box
                                    .clickable { navController.navigate(Routes.ViewAll) }) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.Center,
                                    modifier = Modifier.fillMaxSize()
                                ) {
                                    Text(
                                        text = "Shop Now", fontSize = 15.sp, color = Color.White
                                    )
                                    Spacer(modifier = Modifier.width(4.dp))
                                    Icon(
                                        imageVector = Icons.Default.ArrowForward,
                                        contentDescription = null,
                                        modifier = Modifier.size(16.dp),
                                        tint = Color.White
                                    )
                                }
                            }


                        }
                        Column(modifier = Modifier.fillMaxWidth()) {
                            Spacer(Modifier.height(180.dp))
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.Center
                            ) {
                                Image(
                                    painter = painterResource(R.drawable.dotthree),
                                    contentDescription = null,
                                    modifier = Modifier.size(90.dp)
                                )
                            }
                        }
                    }


                    //-------------Deal Box------------------
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(100.dp)
                            .padding(20.dp)
                            .background(color = Color(0xFF4392F9), shape = RoundedCornerShape(8.dp))
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(start = 10.dp, end = 10.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Column(
                                modifier = Modifier.weight(1f)
                            ) {
                                Text("Deal of the Day", fontSize = 15.sp, color = Color.White)
                                Text(
                                    "⏰ 22h 55m 20s remaining", fontSize = 15.sp, color = Color.White
                                )
                            }

                            Box(
                                modifier = Modifier
                                    .height(35.dp)
                                    .width(110.dp)
                                    .background(
                                        color = Color.Transparent, shape = RoundedCornerShape(10.dp)
                                    )
                                    .border(
                                        width = 1.dp,
                                        color = Color.White,
                                        shape = RoundedCornerShape(8.dp)
                                    )
                                    .padding(horizontal = 8.dp)
                                    .clickable { navController.navigate(Routes.ViewAll) }) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.Center,
                                    modifier = Modifier.fillMaxSize()
                                ) {
                                    Text(
                                        text = "View all", fontSize = 15.sp, color = Color.White
                                    )
                                    Spacer(modifier = Modifier.width(4.dp))
                                    Icon(
                                        imageVector = Icons.Default.ArrowForward,
                                        contentDescription = null,
                                        modifier = Modifier.size(16.dp),
                                        tint = Color.White
                                    )
                                }
                            }
                        }
                    }
                    Spacer(Modifier.height(10.dp))

                    //------------IF SUCCESS THEN SHOW DOWN SCREEN(PRODUCT)--------------
                    //if(state=="success")NextScreen(productlist)

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
                            NextScreen(state.data, navController)
                        }

                        is Result.Failure -> {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 20.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                    Text(
                                        text = "Error: ${state.message}", color = Color.Red
                                    )
                                    Spacer(modifier = Modifier.height(8.dp))
                                    TextButton(onClick = { productViewModel.retryLoading() }) {
                                        Text(text = "Retry")
                                    }
                                }
                            }
                        }

                        is Result.Idle -> {
                            // Initial state
                        }
                    }


                }

            }

        }
    }
}


//------TOP PAR----------
@Composable
fun Top(navController: NavHostController) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(70.dp)
            .background(color = Color(0xFFF1F1F1))
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {
            Spacer(Modifier.height(20.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(start = 20.dp, end = 20.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Menu,
                    contentDescription = null,
                    modifier = Modifier.size(35.dp),
                    tint = Color.Black
                )
                Spacer(Modifier.width(70.dp))
                Image(
                    painter = painterResource(R.drawable.icons),
                    contentDescription = null,
                    modifier = Modifier.size(120.dp)
                )
                Spacer(Modifier.width(70.dp))
                Image(
                    painter = painterResource(R.drawable.avdarlogo),
                    contentDescription = null,
                    modifier = Modifier
                        .size(120.dp)
                        .clickable {
                            navController.navigate(Routes.UserProfile)
                        })
            }
        }
    }
}


@Composable
fun BottomBar(
    navHostController: NavHostController,
    selectedTab: Routes,
) {
    val pink = Color(0xFFFD6E87)
    val black = Color.Black

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(70.dp)
            .background(Color.White),
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.CenterVertically
    ) {
        // ---- Home ----
        BottomBarItem(
            icon = Icons.Default.Home,
            label = "Home",
            isSelected = selectedTab == Routes.ProductScreen,
            selectedColor = pink,
            unselectedColor = black
        ) {
            navHostController.navigate(Routes.ProductScreen)
        }

        // ---- Wishlist ----
        BottomBarItem(
            icon = Icons.Default.FavoriteBorder,
            label = "Wishlist",
            isSelected = selectedTab == Routes.ViewAll,
            selectedColor = pink,
            unselectedColor = black
        ) {
            navHostController.navigate(Routes.ViewAll) {
                popUpTo(Routes.ViewAll) { inclusive = true }
                launchSingleTop = true
            }
        }

        // ---- Search ----
        BottomBarItem(
            icon = Icons.Default.Search,
            label = "Search",
            isSelected = false,
            selectedColor = pink,
            unselectedColor = black
        ) {
            //navHostController.navigate(Routes.Search)
        }

        // ---- Setting ----
        BottomBarItem(
            icon = Icons.Default.Settings,
            label = "Setting",
            isSelected = false,
            selectedColor = pink,
            unselectedColor = black
        ) {
            // navHostController.navigate(Routes.Setting)
        }
    }
}

@Composable
fun BottomBarItem(
    icon: ImageVector,
    label: String,
    isSelected: Boolean,
    selectedColor: Color,
    unselectedColor: Color,
    onClick: () -> Unit,
) {
    val color = if (isSelected) selectedColor else unselectedColor

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.clickable { onClick() }) {
        Icon(
            imageVector = icon,
            contentDescription = label,
            tint = color,
            modifier = Modifier.size(30.dp)
        )
        Text(
            text = label, fontSize = 12.sp, fontWeight = FontWeight.Medium, color = color
        )
    }
}


//----------------NEXT SCREEN 1---------------------------
@SuppressLint("SuspiciousIndentation", "DefaultLocale")
@Composable
fun NextScreen(productList: List<Product>, navController: NavHostController) {
    val listState = rememberLazyListState()
    val listState1 = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()
    val density = LocalDensity.current   // yaha le lo

    //----------SHOWING GIRLS PRODUCTS---------------
    Box(modifier = Modifier.fillMaxWidth()) {
        LazyRow(
            state = listState,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(start = 20.dp, end = 19.7.dp)
        ) {
            items(productList) { product ->

                //-------FILTERING THE PRODUCT BECAUSE THIS IS OUR STYLISH APP-------
                if (product.category == "womens-shoes" || product.category == "womens-dresses" || product.category == "womens-watches") {
                    //--------MAKING TMP VARIABLE FOR SENDING PARAMETER TO PRODUCT CARD-------
                    val price = product.price
                    val discount = product.discountPercentage
                    val title = product.title
                    val image = product.thumbnail
                    val description = product.description
                    val rating = product.rating
                    val stock = product.stock


                    //--------------CHECKING IF PRODUCT ITEM NOT NULL--------------
                    if (price != null && discount != null && title != null && image != null && description != null && rating != null && stock != null) {
                        val originalPrice = BigDecimal(price / (1 - discount / 100)).setScale(
                                2,
                                RoundingMode.HALF_UP
                            ).toDouble()

                        //--------------IF ALL OK THEN PASS THE PARAMETER TO THE PRODUCT CARD COMPOSABLE FUNCTION--------------
                        ProductCard(
                            title = title,
                            image = image,
                            dis = description,
                            rating = rating,
                            price = price,
                            dprice = originalPrice,
                            stock = stock.toDouble(),
                            discount = discount.toString(),
                            onClick = { navController.navigate(Routes.ProductDetailScreen(product.id)) })
                        Spacer(modifier = Modifier.width(10.dp))
                    }
                }
            }
        }


        //----Arrow button(BUT NOT IMPLEMENTED THE LOGIC)------
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(270.dp)
                .padding(horizontal = 30.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .background(color = Color(0xFFBBBBBB), shape = CircleShape)
                    .size(40.dp)
                    .clickable {
                        coroutineScope.launch {
                            val scrollAmount = with(density) { 180.dp.toPx() } // ab sahi chalega
                            listState.animateScrollBy(-scrollAmount)

                        }
                    }, contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.ArrowBackIos,
                    contentDescription = null,
                    modifier = Modifier.size(20.dp)
                )
            }
            Box(
                modifier = Modifier
                    .background(color = Color(0xFFBBBBBB), shape = CircleShape)
                    .size(40.dp)
                    .clickable {
                        coroutineScope.launch {
                            val target =
                                (listState.firstVisibleItemIndex + 1).coerceAtMost(productList.lastIndex)
                            listState.animateScrollToItem(target)
                        }
                    }, contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.ArrowForwardIos,
                    contentDescription = null,
                    modifier = Modifier.size(20.dp)
                )
            }
        }
    }
    Spacer(Modifier.height(10.dp))


    //----------------Special Offers + "joi" Text----------------------
    Box(modifier = Modifier.fillMaxWidth()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 20.dp, end = 20.dp)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(color = Color.White)
                    .height(110.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier.fillMaxSize()
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.special),
                        contentDescription = null,
                        modifier = Modifier.size(100.dp)
                    )
                    Spacer(Modifier.width(30.dp))
                    Column {
                        Text(
                            "Special Offers",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.Black
                        )
                        Spacer(Modifier.height(4.dp))
                        Text("We make sure you get the", fontSize = 15.sp, color = Color.Black)
                        Text("offer you need at best prices", fontSize = 15.sp, color = Color.Black)
                    }
                }
            }
            Spacer(Modifier.height(10.dp))


            //---------FLAT AND HEELS(JUST IMAGE)----------
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(190.dp)
                    .background(color = Color.White)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.adsmain),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )
            }
            Spacer(Modifier.height(10.dp))


            //-----------TRADING PRODUCT CARD-------------------
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(70.dp)
                    .background(color = Color(0xFFFD6E87), shape = RoundedCornerShape(8.dp))
                    .padding(horizontal = 16.dp, vertical = 12.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxSize(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = "Trending Products",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = Color.White
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "⏰Last Date 29/02/22", fontSize = 12.sp, color = Color.White
                        )
                    }

                    Box(
                        modifier = Modifier
                            .height(35.dp)
                            .width(100.dp)
                            .border(
                                width = 1.dp, color = Color.White, shape = RoundedCornerShape(8.dp)
                            )
                            .clickable { navController.navigate(Routes.ViewAll) },
                        contentAlignment = Alignment.Center
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Text(
                                text = "View all", fontSize = 14.sp, color = Color.White
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Icon(
                                imageVector = Icons.Default.ArrowForward,
                                contentDescription = null,
                                modifier = Modifier.size(16.dp),
                                tint = Color.White
                            )
                        }
                    }
                }
            }
            Spacer(Modifier.height(10.dp))


            //--------------SHOWING MEN PRODUCTS----------------------
            Box(modifier = Modifier.fillMaxWidth()) {
                LazyRow(
                    state = listState1,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(start = 2.dp, end = 2.dp)
                ) {
                    items(productList) { product ->
                        if (product.category == "mens-shirts" || product.category == "mens-shoes" || product.category == "mens-watches") {
                            val price = product.price
                            val discount = product.discountPercentage
                            val title = product.title
                            val image = product.thumbnail
                            val description = product.description
                            val rating = product.rating
                            val stock = product.stock

                            if (price != null && discount != null && title != null && image != null && description != null && rating != null && stock != null) {
                                val originalPrice =
                                    BigDecimal(price / (1 - discount / 100)).setScale(
                                            2,
                                            RoundingMode.HALF_UP
                                        ).toDouble()

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
                                                product.id
                                            )
                                        )
                                    })
                                Spacer(modifier = Modifier.width(10.dp))
                            }
                        }
                    }
                }
                //--------------Arrow button----------------
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(270.dp)
                        .padding(horizontal = 20.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .background(
                                color = Color(0xFFBBBBBB), shape = CircleShape
                            )
                            .size(40.dp)
                            .clickable {
                                coroutineScope.launch {
                                    val scrollAmount =
                                        with(density) { 180.dp.toPx() } // ab sahi chalega
                                    listState1.animateScrollBy(-scrollAmount)

                                }
                            }, contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.ArrowBackIos,
                            contentDescription = null,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                    Box(
                        modifier = Modifier
                            .background(
                                color = Color(0xFFBBBBBB), shape = CircleShape
                            )
                            .size(40.dp)
                            .clickable {
                                coroutineScope.launch {
                                    val target =
                                        (listState1.firstVisibleItemIndex + 1).coerceAtMost(
                                            productList.lastIndex
                                        )
                                    listState1.animateScrollToItem(target)
                                }
                            }, contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.ArrowForwardIos,
                            contentDescription = null,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                }

            }
            Spacer(Modifier.height(10.dp))

            //-------------ADS-------------
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
            ) {
                Image(
                    painter = painterResource(R.drawable.adssuper),
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp),
                    contentScale = ContentScale.Crop  // 🟢 Key line to cover the box
                )
            }
            //NEXT SCREEN
            Next2(
                onClick = {navController.navigate(Routes.ViewAll)}
            )

        }
    }
}


//----------------NEXT SCREEN 2---------------------------
@Composable
fun Next2(
    onClick: () -> Unit,
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = Color.White)
            .padding(15.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text(
                    text = "New Arrivals", style = TextStyle(
                        fontSize = 24.sp, fontWeight = FontWeight.Bold, color = Color.Black
                    )
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Summer’ 25 Collections", style = TextStyle(
                        fontSize = 18.sp, fontWeight = FontWeight.Normal, color = Color.Black
                    )
                )
            }

            Button(
                onClick = { onClick() },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFFD6E87), contentColor = Color.White
                ),
                shape = RoundedCornerShape(10.dp),
                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)
            ) {
                Text(
                    text = "View all", fontSize = 16.sp, fontWeight = FontWeight.SemiBold
                )
                Spacer(modifier = Modifier.width(6.dp))
                Icon(
                    imageVector = Icons.Default.ArrowForward,
                    contentDescription = null,
                    modifier = Modifier.size(18.dp)
                )
            }
        }
    }
    Spacer(Modifier.height(20.dp))
    Box(
        modifier = Modifier
            .fillMaxWidth()

            .background(color = Color.White)

            .padding(16.dp)
    ) {
        Column {
            Text(
                text = "Sponserd",
                fontSize = 20.sp,
                fontWeight = FontWeight.Medium,
                color = Color.Black
            )
            Spacer(modifier = Modifier.height(10.dp))
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp)
            ) {
                Image(
                    painter = painterResource(R.drawable.fifty),
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(300.dp),
                    contentScale = ContentScale.Crop
                )
            }
            Spacer(Modifier.height(8.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = "up to 50% Off",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color.Black
                )
                Spacer(Modifier.width(170.dp))
                Icon(
                    imageVector = Icons.Default.ArrowForwardIos,
                    contentDescription = null,
                    modifier = Modifier.size(20.dp),
                    tint = Color.Black
                )
            }

        }
    }


}


@Composable
fun ProductCard(
    title: String,
    image: String,
    dis: String,
    rating: Double,
    price: Double,
    dprice: Double,
    stock: Double,
    discount: String,
    onClick: () -> Unit,
) {
    Card(
        modifier = Modifier
            .height(300.dp)
            .width(170.dp)
            .clickable { onClick() },
        shape = RoundedCornerShape(12.dp),   // for rounded edges
        elevation = CardDefaults.cardElevation(3.dp), // <-- elevation here
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {

            //--------SHOW IMAGE WITH HELP OF COIL (URL TO IMAGE)-------------
            AsyncImage(
                model = image,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .width(170.dp)
                    .height(150.dp)
            )

            Spacer(Modifier.height(5.dp))

            //-----------PRODUCT TITLE----------------
            Text(
                title,
                color = Color.Black,
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium,
                modifier = Modifier.padding(start = 4.dp)
            )

            //----------LIMIT DESCRIPTION LENGTH TO 15 CHARACTERS------------
            val description1 = if (dis.length > 15) dis.substring(0, 15) else dis

            Text(
                description1,
                color = Color.Black,
                fontSize = 11.sp,
                modifier = Modifier.padding(start = 4.dp)
            )

            Text(
                "₹$price",
                color = Color.Black,
                fontSize = 15.sp,
                fontWeight = FontWeight.Medium,
                modifier = Modifier.padding(start = 4.dp)
            )

            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    "₹$dprice",
                    fontSize = 15.sp,
                    color = Color(0xFFA6A4A4),
                    fontWeight = FontWeight.Medium,
                    modifier = Modifier.padding(start = 4.dp),
                    style = TextStyle(textDecoration = TextDecoration.LineThrough)
                )
                Spacer(Modifier.width(7.dp))

                Text("$discount%Off", fontSize = 15.sp, color = Color.Red)
            }

            Row(verticalAlignment = Alignment.CenterVertically) {
                val ratingInt = rating.toInt()

                for (i in 1..5) {
                    if (i <= ratingInt) {
                        Icon(
                            imageVector = Icons.Default.Star,
                            contentDescription = null,
                            modifier = Modifier.size(18.dp),
                            tint = Color(0xFFEDB310)
                        )
                    } else {
                        Icon(
                            imageVector = Icons.Default.StarHalf,
                            contentDescription = null,
                            modifier = Modifier.size(18.dp),
                            tint = Color(0xFFA6A4A4)
                        )
                    }
                    Spacer(Modifier.width(2.dp))
                }

                Text(
                    stock.toString(),
                    fontSize = 15.sp,
                    color = Color(0xFFA6A4A4),
                    fontWeight = FontWeight.Medium,
                    modifier = Modifier.padding(start = 4.dp)
                )
            }
        }
    }
}
