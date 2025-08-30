package com.example.stylish.presentation.auth
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.navOptions
import com.example.stylish.R
import com.example.stylish.util.Result
import com.example.stylish.navigation.Routes
import com.example.stylish.presentation.auth.AuthViewModel
import kotlin.collections.forEach
import kotlin.text.isNotEmpty

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignupScreen(navHostController: NavHostController, viewModel: AuthViewModel){

    val state by viewModel.authState.collectAsState()
    var email by remember { mutableStateOf("") }
    var passwd1 by remember { mutableStateOf("") }
    var passwd2 by remember { mutableStateOf("") }
    var password1Visible by remember { mutableStateOf(false) }
    var password2Visible by remember { mutableStateOf(false) }

    val context= LocalContext.current

    Scaffold() { paddingValues ->

        Surface(
            modifier = Modifier.fillMaxSize().padding(paddingValues),
            color = Color.White
        ){


            Column(Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally) {
                Column(modifier = Modifier.fillMaxWidth().padding(20.dp).padding(start = 17.dp)) {
                    Text("Create an", fontSize = 40.sp, fontWeight = FontWeight.ExtraBold)
                    Text("account", fontSize = 40.sp, fontWeight = FontWeight.ExtraBold)
                }
                Spacer(Modifier.height(10.dp))


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
                Spacer(Modifier.height(30.dp))

                //------password------------
                OutlinedTextField(value = passwd1,
                    onValueChange = {
                        passwd1=it },
                    placeholder = {
                        Text(text = "Password", fontSize = 15.sp, fontWeight = FontWeight.Bold)
                    },
                    leadingIcon = {
                        Icon(
                            painter = painterResource(id = R.drawable.usericon),
                            contentDescription = "User Icon",
                            modifier = Modifier.size(25.dp)
                        )
                    },
                    trailingIcon = {
                        IconButton(onClick = { password1Visible = !password1Visible }) {
                            val visibilityIcon =
                                if (password1Visible) Icons.Filled.Visibility else Icons.Filled.VisibilityOff
                            Icon(imageVector = visibilityIcon, contentDescription = "Toggle Password Visibility")
                        }
                    },

                    shape = RoundedCornerShape(12.dp),
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        containerColor = Color(0xFFE7E7E7)
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 35.dp),
                    singleLine = true,
                    visualTransformation = if (password1Visible) VisualTransformation.None else PasswordVisualTransformation(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
                )
                Spacer(Modifier.height(30.dp))


                //-----confirm password
                OutlinedTextField(value = passwd2,
                    onValueChange = {
                        passwd2=it },
                    placeholder = {
                        Text(text = "Confirm Password", fontSize = 15.sp, fontWeight = FontWeight.Bold)
                    },
                    leadingIcon = {
                        Icon(
                            painter = painterResource(id = R.drawable.usericon),
                            contentDescription = "User Icon",
                            modifier = Modifier.size(25.dp)
                        )
                    },
                    trailingIcon = {
                        IconButton(onClick = { password2Visible = !password2Visible }) {
                            val visibilityIcon =
                                if (password2Visible) Icons.Filled.Visibility else Icons.Filled.VisibilityOff
                            Icon(imageVector = visibilityIcon, contentDescription = "Toggle Password Visibility")
                        }
                    },

                    shape = RoundedCornerShape(12.dp),
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        containerColor = Color(0xFFE7E7E7)
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 35.dp),
                    singleLine = true,
                    visualTransformation = if (password2Visible) VisualTransformation.None else PasswordVisualTransformation(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)

                )

                //--forgot password----
                Spacer(Modifier.height(20.dp))

                Row(Modifier.fillMaxWidth()) {
                    Spacer(Modifier.width(40.dp))
                    Text("By clicking the", fontSize = 15.sp, color = Color(
                        0xFF595959
                    ))
                    Text(" Register", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = Color(0xFFF83758))
                    Text(" button,you agree", fontSize = 15.sp, color = Color(
                        0xFF595959
                    ))
                }
                Spacer(Modifier.height(5.dp))
                Row(modifier = Modifier.fillMaxWidth()){
                    Spacer(Modifier.width(40.dp))
                    Text("to the public offer", fontSize = 15.sp, color = Color(
                        0xFF595959
                    ))
                }


                //--------LOGIN BUTTON -----------
                Spacer(Modifier.height(30.dp))
                Box(
                    modifier = Modifier.fillMaxWidth().clickable {
                        if(passwd1.length<6 && passwd2.length<6){
                            Toast.makeText(context,"Password length is less than 6", Toast.LENGTH_SHORT).show()

                        }else{
                            if (passwd1 == passwd2) {
                                viewModel.signup(email, passwd1)
                            } else{
                                Toast.makeText(context,"Please fill same password", Toast.LENGTH_SHORT).show()
                            }
                        }


                    }
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
                            "Create Account",
                            fontSize = 25.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White


                        )
                    }

                }

                // Handle state changes

                when (state) {
                    is Result.Success -> {
                        navHostController.navigate(Routes.HomeScreen) {
                            popUpTo(Routes.SignupScreen) { inclusive = true }
                        }
                    }

                    is Result.Loading -> {
                        Spacer(modifier = Modifier.height(40.dp))
                            CircularProgressIndicator()

                    }
                    is Result.Failure -> {
                        val errorMessage = (state as Result.Failure).message
                        Text(
                            text =errorMessage, // yaha ViewModel ka error message milega
                            color = Color.Red,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(top = 8.dp)
                        )

                    }

                    else -> {} // Idle or Loading - no special handling
                }


                //---or continue with-
                Spacer(Modifier.height(1.dp))
                Text(
                    text = "- OR Continue with -",
                    fontSize = 17.sp,
                    color = Color(
                        0xFF595959
                    ),
                    modifier = Modifier.padding(top = 45.dp, bottom = 20.dp)
                )

                Row(
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 32.dp).padding(start = 68.dp)
                ) {
                    listOf(
                        R.drawable.google,
                        R.drawable.apple,
                        R.drawable.facebook
                    ).forEach { icon ->
                        Box(
                            contentAlignment = Alignment.Center,
                            modifier = Modifier
                                .size(54.dp)
                                .background(
                                    color = Color(0xFFFEEFF1),
                                    shape = CircleShape
                                ) // light pink bg (optional)
                                .border(1.dp, Color.Red, shape = CircleShape)
                        ) {
                            Image(
                                painter = painterResource(id = icon),
                                contentDescription = null,
                                modifier = Modifier.size(28.dp)
                            )
                        }
                    }
                }
                Spacer(Modifier.height(20.dp))
                Row(verticalAlignment = Alignment.CenterVertically){
                    Text("I Already Have an Account", fontSize = 17.sp, color =  Color(
                        0xFF595959
                    ))
                    Text(" Login", fontSize = 17.sp, color = Color(0xFFF83758), fontWeight = FontWeight.Bold, modifier = Modifier.clickable {
                        navHostController.navigate(Routes.LoginScreen, navOptions {
                            popUpTo<Routes.SignupScreen> { inclusive = true }
                            launchSingleTop = true
                        })
                    })
                }

            }

        }
    }

}



