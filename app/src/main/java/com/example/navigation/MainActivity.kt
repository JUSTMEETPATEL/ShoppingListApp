package com.example.navigation


import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.navigation.ui.theme.NavigationTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NavigationTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    App()
                }

            }
        }
    }
}
@Preview
@Composable
fun App(){
    val navController = rememberNavController()
    NavHost(navController = navController , startDestination = "preview") {
        composable("registration"){
         RegistrationScreen(navController)
        }
        composable("login"){
         LoginScreen(navController)
        }
        composable("main"){
         MainScreen(navController)
        }
        composable("preview"){
            PreviewScreen(navController)
        }
    }
}

@Composable
fun RegistrationScreen(navController: NavHostController) {
    Column(
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.Top
    ) {
        Button(onClick = { navController.navigate("preview") }) {
            Icon(imageVector = Icons.Outlined.ArrowBack, contentDescription = null)
        }
    }

    Column (
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxSize()
    ){
        Button(onClick = { navController.navigate("main") }) {
            Text(text = "Register")
        }
    }
}

@Composable
fun LoginScreen(navController: NavHostController) {
    Column(
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.Top
    ) {
        Button(onClick = { navController.navigate("preview") }) {
            Icon(imageVector = Icons.Outlined.ArrowBack, contentDescription = null)
        }
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        var username by remember { mutableStateOf("") }
        var password by remember { mutableStateOf("") }
        val context = LocalContext.current
        var passwordVisibility by remember { mutableStateOf(false) }
        val icon = if (passwordVisibility){
            painterResource(id = R.drawable.show)
        }
        else{
            painterResource(id = R.drawable.hide)
        }
        TextField(value = username, onValueChange = {username = it }, textStyle = LocalTextStyle.current.copy(textAlign = TextAlign.Left), label = { Text(text = "Username")}, leadingIcon = { Icon(
            imageVector = Icons.Outlined.Person,
            contentDescription = null
        )
        })
        Spacer(modifier = Modifier.padding(10.dp))
        
        TextField(value = password, onValueChange = {password = it }, textStyle = LocalTextStyle.current.copy(textAlign = TextAlign.Left), label = { Text(text = "Password")}, leadingIcon = {
            IconButton(onClick = { passwordVisibility = !passwordVisibility }) {
                Icon(painter = icon, contentDescription = null )
            }
        }, visualTransformation = if(passwordVisibility) VisualTransformation.None
            else PasswordVisualTransformation()
            )

        Spacer(modifier = Modifier.padding(30.dp))
        Button(onClick = {
            if(username=="MeetPatel"&& password=="meetpatel"){
                navController.navigate("main")
            }
            else{
                Toast.makeText(context,"Invalid Credentials",Toast.LENGTH_SHORT).show()
            }
        }) {
            Text(text = "Login")
        }
    }
}

@Composable
fun PreviewScreen(navController: NavHostController){
    Column {
        Button(onClick = { navController.navigate("registration") }) {
            Text(text = "Register")
        }
        Spacer(modifier = Modifier.padding(20.dp))
        Button(onClick = { navController.navigate("login")}) {
            Text(text = "Login")
        }
    }
}




