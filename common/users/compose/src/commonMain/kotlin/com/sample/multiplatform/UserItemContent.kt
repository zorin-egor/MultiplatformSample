package com.sample.multiplatform

import androidx.compose.foundation.clickable
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.sample.multiplatform.models.User
import com.sample.multiplatform.models.UsersEvent

@Composable
fun UsersItemContent(user: User, action: (UsersEvent)-> Unit) {
    Text(
        text = user.login.toString(),
        modifier = Modifier.clickable(onClick = { action(UsersEvent.OnUserClick(user))} )
    )
}