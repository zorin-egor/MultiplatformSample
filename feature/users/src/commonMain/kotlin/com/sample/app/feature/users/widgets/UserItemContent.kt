package com.sample.app.feature.users.widgets

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImagePainter
import coil3.compose.rememberAsyncImagePainter
import com.sample.app.core.model.UserModel
import com.sample.app.core.ui.icon.AppIcons
import com.sample.app.core.ui.widgets.ImageLoadingWidget
import com.sample.app.feature.users.models.UsersEvents

@Composable
fun UsersItemContent(
    user: UserModel,
    onEventAction: (UsersEvents) -> Unit,
    modifier: Modifier
) {
    var isLoading by remember { mutableStateOf(true) }
    var isError by remember { mutableStateOf(false) }
    val imageLoader = rememberAsyncImagePainter(
        model = user.avatarUrl,
        onState = { state ->
            isLoading = state is AsyncImagePainter.State.Loading
            isError = state is AsyncImagePainter.State.Error
        },
    )

    val onUserClick = remember {{ onEventAction(UsersEvents.OnUserClick(user)) }}

    Card(
        modifier = modifier.then(Modifier.height(100.dp)),
        shape = RoundedCornerShape(8.dp),
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .clickable(onClick = onUserClick)
        ) {

            ImageLoadingWidget(
                isError = isError,
                isLoading = isLoading,
                painter = imageLoader,
                placeHolder = AppIcons.Search,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.CenterHorizontally)
                    .weight(weight = 1.0f)
            )
            
            Spacer(modifier = Modifier.height(4.dp))
            
            Text(
                text = user.login,
                fontSize = 18.sp,
                fontWeight = FontWeight.Medium,
                letterSpacing = TextUnit(2.0f, TextUnitType.Sp),
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(start = 8.dp, end = 8.dp, bottom = 8.dp)
            )
        }
    }
}