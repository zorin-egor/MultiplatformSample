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
import com.sample.app.feature.repository_details.resources.Res
import com.sample.app.feature.repository_details.resources.feature_repository_details_created_at
import com.sample.app.feature.repository_details.resources.feature_repository_details_default_branch
import com.sample.app.feature.repository_details.resources.feature_repository_details_description
import com.sample.app.feature.repository_details.resources.feature_repository_details_forks
import com.sample.app.feature.repository_details.resources.feature_repository_details_license
import com.sample.app.feature.repository_details.resources.feature_repository_details_pushed_at
import com.sample.app.feature.repository_details.resources.feature_repository_details_repository_name
import com.sample.app.feature.repository_details.resources.feature_repository_details_stargazers_count
import com.sample.app.feature.repository_details.resources.feature_repository_details_topics
import com.sample.app.feature.repository_details.resources.feature_repository_details_updated_at
import com.sample.app.feature.repository_details.resources.feature_repository_details_watchers
import org.jetbrains.compose.resources.stringResource

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
                    header = stringResource(Res.string.feature_repository_details_repository_name),
                    title = it
                )
            }

            repositoryDetails.forks.toAnnotatedString()?.let {
                TwoSeparatedTextWidget(
                    header = stringResource(Res.string.feature_repository_details_forks),
                    title = it
                )
            }

            repositoryDetails.watchersCount.toAnnotatedString()?.let {
                TwoSeparatedTextWidget(
                    header = stringResource(Res.string.feature_repository_details_watchers),
                    title = it
                )
            }

            repositoryDetails.createdAt.toFormatterDateTime?.toAnnotatedString()?.let {
                TwoSeparatedTextWidget(
                    header = stringResource(Res.string.feature_repository_details_created_at),
                    title = it
                )
            }

            repositoryDetails.updatedAt?.toFormatterDateTime?.toAnnotatedString()?.let {
                TwoSeparatedTextWidget(
                    header = stringResource(Res.string.feature_repository_details_updated_at),
                    title = it
                )
            }

            repositoryDetails.pushedAt?.toFormatterDateTime?.toAnnotatedString()?.let {
                TwoSeparatedTextWidget(
                    header = stringResource(Res.string.feature_repository_details_pushed_at),
                    title = it
                )
            }

            repositoryDetails.defaultBranch.toAnnotatedString()?.let {
                TwoSeparatedTextWidget(
                    header = stringResource(Res.string.feature_repository_details_default_branch),
                    title = it
                )
            }

            repositoryDetails.stargazersCount.toAnnotatedString()?.let {
                TwoSeparatedTextWidget(
                    header = stringResource(Res.string.feature_repository_details_stargazers_count),
                    title = it
                )
            }

            repositoryDetails.topics.joinToString(separator = "-") { it }.toAnnotatedString()?.let {
                TwoSeparatedTextWidget(
                    header = stringResource(Res.string.feature_repository_details_topics),
                    title = it
                )
            }

            repositoryDetails.description?.toAnnotatedString()?.let {
                TwoSeparatedTextWidget(
                    header = stringResource(Res.string.feature_repository_details_description),
                    title = it
                )
            }

            getHyperLink(repositoryDetails.license?.url)?.let {
                TwoSeparatedTextWidget(
                    header = stringResource(Res.string.feature_repository_details_license),
                    title = it
                )
            }
        }
    }
}