package com.example.stylish.presentation.profile

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.stylish.R
import androidx.compose.ui.res.painterResource
import com.google.firebase.auth.FirebaseAuth

@Composable
fun UserProfile(profileViewModel: ProfileViewModel) {
    val userFromDB by profileViewModel.user.collectAsState() // observe StateFlow
    val state by profileViewModel.updateResult.collectAsState()
    val username= remember{mutableStateOf(userFromDB.username)}

    val currentUser = FirebaseAuth.getInstance().currentUser
    if (currentUser != null) {
        val uid = currentUser.uid
        val email = currentUser.email
        val username = currentUser.displayName
        Log.d("111111",username.toString())
    }

    LaunchedEffect(Unit) {
        profileViewModel.fetchUsername()
    }
    LaunchedEffect(userFromDB) {
        username.value=userFromDB.username
    }
    Scaffold(topBar = {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .height(100.dp),
            verticalArrangement = Arrangement.Center
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
            Box(modifier = Modifier.fillMaxWidth().height(120.dp),
                contentAlignment = Alignment.Center
            ){
                Image(painterResource(id=R.drawable.profile),
                    contentDescription = null,
                    modifier = Modifier
                        .height(120.dp)
                        .width(120.dp)
                    )
            }

            Spacer(modifier = Modifier.height(40.dp))
            Box(modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ){
                if(userFromDB.username.isEmpty()){
                    CircularProgressIndicator()
                }
            }
            Spacer(modifier = Modifier.height(40.dp))
            Text(text = "Personal Details", fontSize =30.sp,
                color = Color.Black,
                fontWeight = FontWeight.Bold
            )
            // Username field below profile image
            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = "Username : "+userFromDB.username,
                fontSize = 20.sp,
                color = Color.Black
            )
            Spacer(modifier = Modifier.height(10.dp))
            Row() {
                OutlinedTextField(
                    value = username.value,   // yaha aap apna state use karoge
                    modifier = Modifier.width(200.dp).height(56.dp),
                    onValueChange = { username.value=it },
                    singleLine = true
                )
                Spacer(modifier = Modifier.width(10.dp))
                Button(
                    onClick = {
                        if(username.value!=userFromDB.username){
                        profileViewModel.updateUsername(username.value)
                        }
                    },
                    modifier = Modifier
                        .height(56.dp)          // same height as textfield
                        .width(100.dp) ,         // fixed width button
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFFFF6B35),
                        disabledContentColor = Color.Gray
                    ),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text(text = "Edit")
                }
            }

            Spacer(modifier = Modifier.height(20.dp))
            Text(text = "Email Address", fontSize =20.sp,
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
            Text(text = "Password", fontSize =20.sp,
                color = Color.Black,
            )
            OutlinedTextField(
                value = userFromDB.password,
                onValueChange = {},
                modifier = Modifier.fillMaxWidth(),
                enabled = false,   // Disabled field
                singleLine = true
            )
        }
    }
}