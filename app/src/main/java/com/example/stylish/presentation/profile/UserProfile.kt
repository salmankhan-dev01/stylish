package com.example.stylish.presentation.profile

import androidx.compose.foundation.Image
import com.example.stylish.util.Result
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.stylish.R
import com.example.stylish.data.local.entity.AddressEntity
import com.example.stylish.data.local.entity.BankAccountEntity
import com.example.stylish.navigation.Routes
import com.example.stylish.ui.theme.Pink

@Composable
fun UserProfile(
    profileViewModel: ProfileViewModel,
    viewModel: AddressAccountViewModel,
    navController: NavHostController
) {
    val userFromDB by profileViewModel.user.collectAsState() // observe StateFlow
    val username = remember { mutableStateOf(userFromDB.username) }
    val scrollState = rememberScrollState()

    var addressModel by remember {
        mutableStateOf(
            AddressEntity(pinCode = "", address = "", city = "", state = "", country = "")
        )
    }

    // ---------------- Bank State ----------------
    var bankModel by remember {
        mutableStateOf(
            BankAccountEntity(accountNumber = "", accountHolder = "", ifscCode = "")
        )
    }


    LaunchedEffect(Unit) {
        profileViewModel.fetchUsername()
    }
    LaunchedEffect(userFromDB) {
        username.value = userFromDB.username
    }


    // ---------------- Observe ViewModel ----------------
    val addressResult by viewModel.addressResult.collectAsState()
    val bankResult by viewModel.bankResult.collectAsState()
    val saveAddressResult by viewModel.saveAddressResult.collectAsState()
    val saveBankResult by viewModel.saveBankResult.collectAsState()
    val logoutState by viewModel.logout.collectAsState()


    // ---------------- Fetch on screen open ----------------
    LaunchedEffect(Unit) {
        viewModel.fetchAddress()
        viewModel.fetchBankAccount()
    }

    // ---------------- Update UI when data arrives ----------------
    LaunchedEffect(addressResult) {
        if (addressResult is Result.Success) {
            addressModel = (addressResult as Result.Success<AddressEntity>).data
        }
    }

    LaunchedEffect(bankResult) {
        if (bankResult is Result.Success) {
            bankModel = (bankResult as Result.Success<BankAccountEntity>).data
        }
    }
    // Observe logout
    LaunchedEffect(logoutState) {
        when (logoutState) {
            is Result.Success -> {
                // Navigate to Login Screen after logout
//                navController.navigate("login_screen") {
//                    popUpTo("user_profile") { inclusive = true } // previous backstack clear
//                }
                navController.navigate(Routes.LoginScreen){
                    popUpTo(navController.graph.id){
                        inclusive=true
                    }
                }
            }
            is Result.Failure -> {
            }
            else -> {}
        }
    }

    Scaffold(topBar = {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .height(100.dp),
            verticalArrangement = Arrangement.Center,
        ) {
            IconButton(onClick = {}) {
                Icon(imageVector = Icons.Default.ArrowBack, contentDescription = null)
            }
        }
    }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(paddingValues)
                .padding(horizontal = 30.dp)
                .verticalScroll(scrollState)
        ) {
            Spacer(modifier = Modifier.height(10.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "Profile",
                    fontSize = 30.sp, color = Color.Black,
                    fontWeight = FontWeight.Bold
                )
            }
            Spacer(modifier = Modifier.height(10.dp))
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painterResource(id = R.drawable.profile),
                    contentDescription = null,
                    modifier = Modifier
                        .height(120.dp)
                        .width(120.dp)
                )
            }

            Spacer(modifier = Modifier.height(40.dp))
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                if (userFromDB.username.isEmpty()) {
                    CircularProgressIndicator()
                }
            }
            Spacer(modifier = Modifier.height(40.dp))
            Text(
                text = "Personal Details", fontSize = 30.sp,
                color = Color.Black,
                fontWeight = FontWeight.Bold
            )
            // Username field below profile image
            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = "Username : " + userFromDB.username,
                fontSize = 20.sp,
                color = Color.Black
            )
            Spacer(modifier = Modifier.height(10.dp))
            Row() {
                OutlinedTextField(
                    value = username.value,   // yaha aap apna state use karoge
                    modifier = Modifier
                        .width(200.dp)
                        .height(56.dp),
                    onValueChange = { username.value = it },
                    singleLine = true
                )
                Spacer(modifier = Modifier.width(10.dp))
                Button(
                    onClick = {
                        if (username.value != userFromDB.username) {
                            profileViewModel.updateUsername(username.value)
                        }
                    },
                    modifier = Modifier
                        .height(56.dp)          // same height as textfield
                        .width(100.dp),         // fixed width button
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Pink,
                        disabledContentColor = Color.Gray
                    ),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text(text = "Edit")
                }
            }

            Spacer(modifier = Modifier.height(20.dp))
            Text(
                text = "Email Address", fontSize = 20.sp,
                color = Color.Black,
            )
            Spacer(modifier = Modifier.height(10.dp))
            OutlinedTextField(
                value = userFromDB.email,
                onValueChange = {},
                modifier = Modifier.fillMaxWidth(),
                enabled = false,   // Disabled field
                singleLine = true
            )
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = "Password", fontSize = 20.sp,
                color = Color.Black,
            )
            Spacer(modifier = Modifier.height(10.dp))

            OutlinedTextField(
                value = userFromDB.password,
                onValueChange = {},
                modifier = Modifier.fillMaxWidth(),
                enabled = false,   // Disabled field
                singleLine = true
            )
            Spacer(modifier = Modifier.height(10.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                Text(text = "Change Password", color = Pink, textDecoration = TextDecoration.Underline)
            }
            Spacer(modifier = Modifier.height(15.dp))
            HorizontalDivider()
            Spacer(modifier = Modifier.height(15.dp))

            // Address Details
            Text(
                text = "Business Address Details", fontSize = 20.sp,
                color = Color.Black,
                fontWeight = FontWeight.Medium
            )
            Spacer(modifier = Modifier.height(15.dp))
            Text(
                text = "Pin code", fontSize = 20.sp,
                color = Color.Black,
            )
            Spacer(modifier = Modifier.height(10.dp))

            OutlinedTextField(
                value = addressModel.pinCode,
                onValueChange = {addressModel = addressModel.copy(pinCode = it) },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )
            Spacer(modifier = Modifier.height(15.dp))
            Text(
                text = "Address", fontSize = 20.sp,
                color = Color.Black,
            )
            Spacer(modifier = Modifier.height(10.dp))

            OutlinedTextField(
                value = addressModel.address,
                onValueChange = { addressModel = addressModel.copy(address = it) },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )
            Spacer(modifier = Modifier.height(15.dp))
            Text(
                text = "City", fontSize = 20.sp,
                color = Color.Black,
            )
            Spacer(modifier = Modifier.height(10.dp))

            OutlinedTextField(
                value = addressModel.city,
                onValueChange = { addressModel = addressModel.copy(city = it) },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )
            Spacer(modifier = Modifier.height(15.dp))
            Text(
                text = "State", fontSize = 20.sp,
                color = Color.Black,
            )
            Spacer(modifier = Modifier.height(10.dp))

            OutlinedTextField(
                value = addressModel.state,
                onValueChange = { addressModel = addressModel.copy(state = it) },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )
            Spacer(modifier = Modifier.height(15.dp))
            Text(
                text = "Country", fontSize = 20.sp,
                color = Color.Black,
            )
            Spacer(modifier = Modifier.height(10.dp))

            OutlinedTextField(
                value = addressModel.country,
                onValueChange = { addressModel = addressModel.copy(country = it) },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )
            Spacer(modifier = Modifier.height(15.dp))

            Button(
                onClick = { viewModel.saveAddress(addressModel) },
                modifier = Modifier
                    .height(56.dp)
                    .fillMaxWidth(),         // fixed width button
                colors = ButtonDefaults.buttonColors(
                    containerColor = Pink,
                    disabledContentColor = Color.Gray
                ),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text(text = "Save Address")
            }
            when (saveAddressResult) {
                is Result.Loading -> Text("Saving address...")
                is Result.Success -> Text((saveAddressResult as Result.Success<String>).data)
                is Result.Failure -> Text("Error: ${(saveAddressResult as Result.Failure).message}")
                else -> {}
            }
            Spacer(modifier = Modifier.height(20.dp))

            // account section
            HorizontalDivider()
            Spacer(modifier = Modifier.height(15.dp))

            // Account Details
            Text(
                text = "Bank Account Details", fontSize = 20.sp,
                color = Color.Black,
                fontWeight = FontWeight.Medium
            )
            Spacer(modifier = Modifier.height(15.dp))
            Text(
                text = "Account Holder’s Name", fontSize = 20.sp,
                color = Color.Black,
            )
            Spacer(modifier = Modifier.height(10.dp))

            OutlinedTextField(
                value = bankModel.accountHolder,
                onValueChange = { bankModel = bankModel.copy(accountHolder = it) },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )
            Spacer(modifier = Modifier.height(15.dp))
            Text(
                text = "Bank Account Number", fontSize = 20.sp,
                color = Color.Black,
            )
            Spacer(modifier = Modifier.height(10.dp))

            OutlinedTextField(
                value = bankModel.accountNumber,
                onValueChange = { bankModel = bankModel.copy(accountNumber = it) },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )
            Spacer(modifier = Modifier.height(15.dp))
            Text(
                text = "IFSC Code", fontSize = 20.sp,
                color = Color.Black,
            )
            Spacer(modifier = Modifier.height(10.dp))

            OutlinedTextField(
                value = bankModel.ifscCode,
                onValueChange = { bankModel = bankModel.copy(ifscCode = it) },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )
            Spacer(modifier = Modifier.height(15.dp))
            when (saveBankResult) {
                is Result.Loading -> Text("Saving bank account...")
                is Result.Success -> Text((saveBankResult as Result.Success<String>).data)
                is Result.Failure -> Text("Error: ${(saveBankResult as Result.Failure).message}")
                else -> {}
            }
            Spacer(modifier = Modifier.height(15.dp))

            Button(
                onClick = { viewModel.saveBankAccount(bankModel) },
                modifier = Modifier
                    .height(56.dp)
                    .fillMaxWidth(),         // fixed width button
                colors = ButtonDefaults.buttonColors(
                    containerColor = Pink,
                    disabledContentColor = Color.Gray
                ),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text(text = "Save Account")
            }
            Spacer(modifier = Modifier.height(30.dp))
            Button(
                onClick = { viewModel.logoutAccout() },
                modifier = Modifier
                    .height(56.dp)
                    .fillMaxWidth(),         // fixed width button
                colors = ButtonDefaults.buttonColors(
                    disabledContentColor = Color.Gray
                ),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text(text = "Log Out")
            }
            Spacer(modifier = Modifier.height(30.dp))


        }
    }
}

@Composable
fun AddressSection() {



}