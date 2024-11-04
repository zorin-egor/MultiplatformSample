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
import androidx.compose.foundation.verticalScroll
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
import com.sample.app.feature.user_details.resources.Res
import com.sample.app.feature.user_details.resources.feature_user_details_user_bio
import com.sample.app.feature.user_details.resources.feature_user_details_user_blog
import com.sample.app.feature.user_details.resources.feature_user_details_user_company
import com.sample.app.feature.user_details.resources.feature_user_details_user_created
import com.sample.app.feature.user_details.resources.feature_user_details_user_email
import com.sample.app.feature.user_details.resources.feature_user_details_user_followers
import com.sample.app.feature.user_details.resources.feature_user_details_user_id
import com.sample.app.feature.user_details.resources.feature_user_details_user_location
import com.sample.app.feature.user_details.resources.feature_user_details_user_name
import com.sample.app.feature.user_details.resources.feature_user_details_user_repos
import com.sample.app.feature.user_details.resources.feature_user_details_user_url
import org.jetbrains.compose.resources.stringResource

@Composable
fun UserDetailsContent(
    isTopBarVisible: Boolean,
    userDetails: UserDetailsModel,
    onEventAction: (UserDetailsEvent) -> Unit,
    modifier: Modifier = Modifier
) {
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
                .verticalScroll(scroll)
                .padding(bottom = 16.dp)
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
                    header = stringResource(Res.string.feature_user_details_user_id),
                    title = it
                )
            }

            getHyperLink(userDetails.url)?.let {
                TwoSeparatedTextWidget(
                    header = stringResource(Res.string.feature_user_details_user_url),
                    title = it
                )
            }

            userDetails.name?.toAnnotatedString()?.let {
                TwoSeparatedTextWidget(
                    header = stringResource(Res.string.feature_user_details_user_name),
                    title = it
                )
            }

            userDetails.company?.toAnnotatedString()?.let {
                TwoSeparatedTextWidget(
                    header = stringResource(Res.string.feature_user_details_user_company),
                    title = it
                )
            }

            getHyperLink(userDetails.blog)?.let {
                TwoSeparatedTextWidget(
                    header = stringResource(Res.string.feature_user_details_user_blog),
                    title = it
                )
            }

            userDetails.location?.toAnnotatedString()?.let {
                TwoSeparatedTextWidget(
                    header = stringResource(Res.string.feature_user_details_user_location),
                    title = it
                )
            }

            getEmailLink(userDetails.email, userDetails.email)?.let {
                TwoSeparatedTextWidget(
                    header = stringResource(Res.string.feature_user_details_user_email),
                    title = it
                )
            }

            userDetails.bio?.toAnnotatedString()?.let {
                TwoSeparatedTextWidget(
                    header = stringResource(Res.string.feature_user_details_user_bio),
                    title = it
                )
            }

            userDetails.followers?.toAnnotatedString()?.let {
                TwoSeparatedTextWidget(
                    header = stringResource(Res.string.feature_user_details_user_followers),
                    title = it
                )
            }

            userDetails.createdAt?.toFormatterDateTime?.toAnnotatedString()?.let {
                TwoSeparatedTextWidget(
                    header = stringResource(Res.string.feature_user_details_user_created),
                    title = it
                )
            }

            getHyperLink(userDetails.reposUrl)?.let {
                TwoSeparatedTextWidget(
                    header = stringResource(Res.string.feature_user_details_user_repos),
                    title = it
                )
            }
        }
    }
}