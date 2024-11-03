package com.sample.app.feature.user_details.widgets

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImagePainter
import coil3.compose.rememberAsyncImagePainter
import com.sample.app.core.domain.ext.toFormatterDateTime
import com.sample.app.core.model.UserDetailsModel
import com.sample.app.core.ui.ext.getEmailLink
import com.sample.app.core.ui.ext.getHyperLink
import com.sample.app.core.ui.ext.toAnnotatedString
import com.sample.app.core.ui.icon.AppIcons
import com.sample.app.core.ui.widgets.TwoSeparatedTextWidget
import com.sample.app.feature.user_details.models.UserDetailsEvent

@Composable
fun UserDetailsContent(
    isTopBarVisible: Boolean,
    userDetails: UserDetailsModel,
    onEventAction: (UserDetailsEvent) -> Unit,
    modifier: Modifier = Modifier
) {

    /*
    Vertically scrollable component was measured with an infinity maximum height constraints,
    which is disallowed. One of the common reasons is nesting layouts like LazyColumn and
    Column(Modifier.verticalScroll()). If you want to add a header before the list of items please
    add a header as a separate item() before the main items() inside the LazyColumn scope.
    There are could be other reasons for this to happen: your ComposeView was added into a
    LinearLayout with some weight, you applied Modifier.wrapContentSize(unbounded = true) or wrote
    a custom layout. Please try to remove the source of infinite constraints in the hierarchy above
    the scrolling container.
    * */

    var isLoading by remember { mutableStateOf(true) }
    var isError by remember { mutableStateOf(false) }
    val imageLoader = rememberAsyncImagePainter(
        model = userDetails.avatarUrl,
        onState = { state ->
            isLoading = state is AsyncImagePainter.State.Loading
            isError = state is AsyncImagePainter.State.Error
        },
    )

    val onShareProfileClick = remember {{ onEventAction(UserDetailsEvent.ShareProfile) }}
    val scroll = rememberScrollState()

    Card(
        shape = RoundedCornerShape(8.dp),
        modifier = modifier
            .fillMaxWidth()
            .fillMaxHeight()
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
//                .verticalScroll(scroll)
        ) {
            Box(modifier = Modifier.wrapContentSize()) {
                Image(
                    painter = imageLoader,
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.Center)
                        .height(300.dp)
                        .clip(shape = RoundedCornerShape(8.dp))
                )
                if (!isTopBarVisible) {
                    Icon(
                        imageVector = AppIcons.Share,
                        contentDescription = null,
                        modifier = Modifier.wrapContentSize()
                            .padding(16.dp)
                            .align(Alignment.TopEnd)
                            .clickable(onClick = onShareProfileClick)
                    )
                }
            }

            userDetails.id.toAnnotatedString()?.let {
                TwoSeparatedTextWidget(
                    header = "header",
                    title = it
                )
            }

            getHyperLink(userDetails.url)?.let {
                TwoSeparatedTextWidget(
                    header = "header",
                    title = it
                )
            }

            userDetails.name?.toAnnotatedString()?.let {
                TwoSeparatedTextWidget(
                    header = "Header",
                    title = it
                )
            }

            userDetails.company?.toAnnotatedString()?.let {
                TwoSeparatedTextWidget(
                    header = "Header",
                    title = it
                )
            }

            getHyperLink(userDetails.blog)?.let {
                TwoSeparatedTextWidget(
                    header = "Header",
                    title = it
                )
            }

            userDetails.location?.toAnnotatedString()?.let {
                TwoSeparatedTextWidget(
                    header = "Header",
                    title = it
                )
            }

            getEmailLink(userDetails.email, userDetails.email)?.let {
                TwoSeparatedTextWidget(
                    header = "Header",
                    title = it
                )
            }

            userDetails.bio?.toAnnotatedString()?.let {
                TwoSeparatedTextWidget(
                    header = "Header",
                    title = it
                )
            }

            userDetails.bio?.toAnnotatedString()?.let {
                TwoSeparatedTextWidget(
                    header = "Header",
                    title = it
                )
            }

            userDetails.followers?.toAnnotatedString()?.let {
                TwoSeparatedTextWidget(
                    header = "Header",
                    title = it
                )
            }

            userDetails.createdAt?.toFormatterDateTime?.toAnnotatedString()?.let {
                TwoSeparatedTextWidget(
                    header = "Header",
                    title = it
                )
            }

            getHyperLink(userDetails.reposUrl)?.let {
                TwoSeparatedTextWidget(
                    header = "Header",
                    title = it
                )
            }
        }
    }
}