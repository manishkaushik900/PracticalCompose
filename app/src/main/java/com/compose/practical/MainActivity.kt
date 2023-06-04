package com.compose.practical

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.compose.practical.ui.theme.PracticalComposeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PracticalComposeTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    var name by remember{ mutableStateOf("Manish") }
                    Greeting(name, Modifier.padding(10.dp),
                        handleButtonClicked =  { name = it})
                }
            }
        }
    }


    @Composable
    fun Greeting(
        name: String,
        modifier: Modifier = Modifier,
        handleButtonClicked: (String) -> Unit
    ) {

//        var myTextValue by remember { mutableStateOf(name) }

        Text(
            text = "Hello $name!",
            modifier = modifier.clickable {
                handleButtonClicked("Ashish Kaushik")
//            myTextValue = "Ashish"
            }
        )
    }

    @Preview(showBackground = true)
    @Composable
    fun GreetingPreview() {
        PracticalComposeTheme {
//            Greeting("Android Developer", Modifier.padding(10.dp)) { onButtonClicked() }
        }
    }

}
