package com.sample.app.feature.users.widgets


import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.sample.app.feature.users.models.UsersEvent


private const val PIC_PLACEHOLDER = "https://www.seekpng.com/png/detail/110-1100707_person-avatar-placeholder.png"

@Composable
fun UsersItemContent(user: String, modifier: Modifier, action: (UsersEvent)-> Unit) {
    Card(
        modifier = modifier,
        elevation = 4.dp,
        shape = RoundedCornerShape(8.dp),
        backgroundColor = MaterialTheme.colors.surface
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth().fillMaxHeight()
                .clickable(onClick = { action(UsersEvent.OnUserClick(user)) })
        ) {
//            Image(
//                painter = painter,
//                contentDescription = null,
//                contentScale = ContentScale.Crop,
//                modifier = Modifier.fillMaxWidth().align(Alignment.CenterHorizontally).weight(weight = 1.0f)
//            )
            Text(
                text = user,
                modifier = Modifier.align(Alignment.CenterHorizontally).padding(all = 8.dp)
            )
        }
    }
}