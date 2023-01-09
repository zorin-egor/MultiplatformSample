package com.sample.multiplatform.android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.lifecycleScope
import com.sample.multiplatform.PlatformSDK
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : ComponentActivity() {

    private val state = mutableStateOf<String>("Begin")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApplicationTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    val (getter, setter) = remember { state }
                    GreetingView("Result: $getter")
                }
            }
        }

        getUsers()
    }

    private fun getUsers() {
        lifecycleScope.launch {
            delay(2000)
            val users = withContext(Dispatchers.IO) {
                kotlin.runCatching {
                    PlatformSDK.usersRepository.getUsers(0)
                }
            }

            val result = users.getOrNull()?.joinToString(separator = ",")
                ?: users.exceptionOrNull().toString()

            state.value = result
        }
    }
}

@Composable
fun GreetingView(text: String) {
    Text(text = text)
}

@Preview
@Composable
fun DefaultPreview() {
    MyApplicationTheme {
        GreetingView("Hello, Android!")
    }
}
