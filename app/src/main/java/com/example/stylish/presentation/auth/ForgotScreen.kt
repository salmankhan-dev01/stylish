package com.example.stylish.presentation.auth
import androidx.compose.foundation.background
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
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
import androidx.navigation.NavHostController
import com.example.stylish.R
import com.example.stylish.navigation.Routes

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ForgotScreen(navHostController: NavHostController){
    var email by remember { mutableStateOf("") }
    Scaffold { paddingValues ->
        Surface(
            modifier = Modifier.fillMaxSize().padding(paddingValues),
            color = Color.White
        ){
            Column(Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally) {
                Column(modifier = Modifier.fillMaxWidth().padding(20.dp).padding(start = 17.dp)) {
                    Text("Forgot", fontSize = 40.sp, fontWeight = FontWeight.ExtraBold)
                    Text("password?", fontSize = 40.sp, fontWeight = FontWeight.ExtraBold)
                }
                Spacer(Modifier.height(20.dp))


                //------user or email
                OutlinedTextField(value = email,
                    onValueChange = {
                        email=it },
                    placeholder = {
                        Text(text = "Enter your email", fontSize = 15.sp, fontWeight = FontWeight.Bold)
                    },
                    leadingIcon = {
                        Icon(
                            painter = painterResource(id = R.drawable.usericon),
                            contentDescription = "User Icon",
                            modifier = Modifier.size(25.dp)
                        )
                    },

                    shape = RoundedCornerShape(12.dp),
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        containerColor = Color(0xFFE7E7E7)
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 35.dp),
                    singleLine = true
                )
                Spacer(Modifier.height(20.dp))

                Row(Modifier.fillMaxWidth()) {
                    Spacer(Modifier.width(40.dp))
                    Text("*", fontSize = 15.sp, color = Color(0xFFF83758))
                    Text(
                        " We will send you a message to set or rest",
                        fontSize = 16.sp,
                        color = Color(
                            0xFF595959
                        )
                    )

                }
                Spacer(Modifier.height(5.dp))
                Row(modifier = Modifier.fillMaxWidth()) {
                    Spacer(Modifier.width(43.dp))
                    Text(
                        "your new password", fontSize = 15.sp, color = Color(
                            0xFF595959
                        )
                    )

                }


                //--------LOGIN BUTTON -----------
                Spacer(Modifier.height(50.dp))
                Box(
                    modifier = Modifier.fillMaxWidth().clickable { navHostController.navigate(Routes.LoginScreen) }
                        .height(55.dp).padding(horizontal = 35.dp)
                        .background(
                            color = Color(0xFFF83758), shape = RoundedCornerShape(9.dp)
                        )
                ) {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text(
                            "Submit",
                            fontSize = 25.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                    }

                }
            }
        }
    }

}

//
//@Preview(showBackground = true)
//@Composable
//fun ForgotScreenPreview(){
//    ForgotScreen()
//}