package com.sample.multiplatform

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.sample.multiplatform.di.Inject
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

private val usersRepository: UsersRepository get() = Inject.instance()
private val detailsRepository: DetailsRepository get() = Inject.instance()
private val state = mutableStateOf<String>("Begin")


@Composable
internal fun KmmComposeApp() {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colors.background
    ) {
        val (getter, setter) = remember { state }
        GreetingView("Result: $getter")
    }
    getUsers()
}

@Composable
fun GreetingView(text: String) {
    Text(text = text)
}

private fun getUsers() {
    CoroutineScope(context = Dispatchers.Default).launch {
        delay(2000)
        val users = withContext(Dispatchers.IO) {
            kotlin.runCatching {
                usersRepository.getUsers(0)
            }
        }

        val details = users.getOrNull()?.firstOrNull()?.url?.let {
            detailsRepository.getUserDetails(it)
        }

        withContext(Dispatchers.Main) {
            state.value = details.toString()
        }
    }
}