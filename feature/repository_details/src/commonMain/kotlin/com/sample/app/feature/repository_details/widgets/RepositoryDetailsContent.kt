package com.sample.app.feature.repository_details.widgets

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
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
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
import com.sample.app.core.model.RepositoryDetailsModel
import com.sample.app.core.ui.ext.getHyperLink
import com.sample.app.core.ui.ext.toAnnotatedString
import com.sample.app.core.ui.icon.AppIcons
import com.sample.app.core.ui.widgets.TwoSeparatedTextWidget
import com.sample.app.feature.repository_details.models.RepositoryDetailsEvents

@Composable
fun RepositoryDetailsContent(
    isTopBarVisible: Boolean,
    repositoryDetails: RepositoryDetailsModel,
    onEventAction: (RepositoryDetailsEvents) -> Unit,
    modifier: Modifier = Modifier
) {
    var isLoading by remember { mutableStateOf(true) }
    var isError by remember { mutableStateOf(false) }
    val imageLoader = rememberAsyncImagePainter(
        model = repositoryDetails.avatarUrl,
        onState = { state ->
            isLoading = state is AsyncImagePainter.State.Loading
            isError = state is AsyncImagePainter.State.Error
        },
    )

    val onShareProfileClick = remember {{ onEventAction(RepositoryDetailsEvents.ShareProfile) }}
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
                        modifier = Modifier
                            .wrapContentSize()
                            .padding(16.dp)
                            .align(Alignment.TopEnd)
                            .clickable(onClick = onShareProfileClick)
                    )
                }
            }

            repositoryDetails.name.toAnnotatedString()?.let {
                TwoSeparatedTextWidget(
                    header = "Header",
                    title = it
                )
            }

            repositoryDetails.forks.toAnnotatedString()?.let {
                TwoSeparatedTextWidget(
                    header = "Header",
                    title = it
                )
            }

            repositoryDetails.watchersCount.toAnnotatedString()?.let {
                TwoSeparatedTextWidget(
                    header = "Header",
                    title = it
                )
            }

            repositoryDetails.createdAt.toFormatterDateTime?.toAnnotatedString()?.let {
                TwoSeparatedTextWidget(
                    header = "Header",
                    title = it
                )
            }

            repositoryDetails.updatedAt?.toFormatterDateTime?.toAnnotatedString()?.let {
                TwoSeparatedTextWidget(
                    header = "Header",
                    title = it
                )
            }

            repositoryDetails.pushedAt?.toFormatterDateTime?.toAnnotatedString()?.let {
                TwoSeparatedTextWidget(
                    header = "Header",
                    title = it
                )
            }

            repositoryDetails.defaultBranch.toAnnotatedString()?.let {
                TwoSeparatedTextWidget(
                    header = "Header",
                    title = it
                )
            }

            repositoryDetails.stargazersCount.toAnnotatedString()?.let {
                TwoSeparatedTextWidget(
                    header = "Header",
                    title = it
                )
            }

            repositoryDetails.stargazersCount.toAnnotatedString()?.let {
                TwoSeparatedTextWidget(
                    header = "Header",
                    title = it
                )
            }

            repositoryDetails.description?.toAnnotatedString()?.let {
                TwoSeparatedTextWidget(
                    header = "Header",
                    title = it
                )
            }

            repositoryDetails.topics.joinToString(separator = "-") { it }.toAnnotatedString()?.let {
                TwoSeparatedTextWidget(
                    header = "Header",
                    title = it
                )
            }

            getHyperLink(repositoryDetails.license?.url)?.let {
                TwoSeparatedTextWidget(
                    header = "Header",
                    title = it
                )
            }

        }
    }
}