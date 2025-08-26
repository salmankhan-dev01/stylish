package com.example.stylish.presentation.view.auth

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
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.stylish.R
import com.example.stylish.presentation.viewmodel.authViewModel.AuthViewModel
import com.example.stylish.util.Result
import com.example.stylish.presentation.navigation.Routes
import com.example.stylish.presentation.userPreference.UserPreferencesViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(
    navHostController: NavHostController,
    viewModel: AuthViewModel,
    userPreferenceViewModel: UserPreferencesViewModel
){
    val state by viewModel.authState.collectAsState()
    var open by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var paswd by remember { mutableStateOf("") }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color.White
    ){


        // Handle state changes

        when (state) {
            is Result.Loading -> {
                Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            }
            is Result.Failure -> {
                Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text("Something went wrong!", color = Color.Red)
                }
            }
            is Result.Success -> {
                LaunchedEffect(Unit){
//                    userPreferenceViewModel.setLoggedIn(true)
//                    userPreferenceViewModel.setFirstTimeLogin(false)
                    navHostController.navigate(Routes.HomeScreen) {
                        popUpTo(Routes.LoginScreen) { inclusive = true }
                    }
                }

            }

            else -> {} // Idle or Loading - no special handling
        }

        Column(Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally) {
            Column(modifier = Modifier.fillMaxWidth().padding(20.dp).padding(start = 17.dp)) {
                Text("Welcome", fontSize = 40.sp, fontWeight = FontWeight.ExtraBold)
                Text("Back!", fontSize = 40.sp, fontWeight = FontWeight.ExtraBold)
            }
            Spacer(Modifier.height(20.dp))



            //------user or email
            OutlinedTextField(value = email, onValueChange = {
                email=it
            }, placeholder = {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        painter = painterResource(id = R.drawable.usericon),
                        contentDescription = null,
                        modifier = Modifier.size(25.dp)
                    )
                    Spacer(Modifier.width(4.dp))
                    Text(
                        "Username or Email",
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(
                            0xFF595959
                        )
                    )
                }
            }, textStyle = TextStyle(
                color = Color(0xFF595959),
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold
            ),
                shape = RoundedCornerShape(12.dp),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    containerColor = Color(0xFFE7E7E7)
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 35.dp)
            )
            Spacer(Modifier.height(40.dp))


            //------password------------
            OutlinedTextField(value = paswd, onValueChange = {
                paswd=it
            }, placeholder = {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        painter = painterResource(id = R.drawable.lockicon),
                        contentDescription = null,
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(Modifier.width(7.dp))
                    Text(
                        "Password",
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(
                            0xFF595959
                        )
                    )
                }
            }, textStyle = TextStyle(
                color = Color(0xFF595959),
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold
            ),
                shape = RoundedCornerShape(12.dp),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    containerColor = Color(0xFFE7E7E7)
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 35.dp)
            )
            //--forgot password---
            Spacer(Modifier.height(8.dp))
            Row(Modifier.fillMaxWidth()) {
                Spacer(Modifier.width(239.dp))
                Text("Forgot Password?", fontSize = 15.sp, color = Color(0xFFF83758), modifier = Modifier.clickable { navHostController.navigate(
                    Routes.ForgotScreen) })
            }


            //--------LOGIN BUTTON -----------
            Spacer(Modifier.height(50.dp))
            Box(
                modifier = Modifier.fillMaxWidth().clickable {
                    if(email.isNotEmpty() && paswd.isNotEmpty()){
                        if (paswd.length>=6){
                            viewModel.login(email,paswd)
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
                        "Login",
                        fontSize = 25.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White,

                        )
                }

            }


            //---or continue with-
            Spacer(Modifier.height(8.dp))
            Text(
                text = "- OR Continue with -",
                fontSize = 15.sp,
                color = Color(
                    0xFF595959
                ),
                modifier = Modifier.padding(top = 80.dp, bottom = 20.dp)
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
            Spacer(Modifier.height(27.dp))
            Row(verticalAlignment = Alignment.CenterVertically){
                Text("Create An Account", fontSize = 15.sp, color =  Color(
                    0xFF595959
                ))
                Text(" Sign Up", fontSize = 15.sp, color = Color(0xFFF83758), fontWeight = FontWeight.Bold, modifier = Modifier.clickable { navHostController.navigate(
                    Routes.SignupScreen) })
            }

        }

    }

}

