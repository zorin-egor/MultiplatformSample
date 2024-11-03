package com.sample.app.feature.repository_details

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.sample.app.core.ui.icon.AppIcons
import com.sample.app.core.ui.theme.AppTheme
import multiplatformsample.feature.repository_details.generated.resources.Res
import multiplatformsample.feature.repository_details.generated.resources.feature_repository_details_title
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun RepositoryDetailsPlaceholder(modifier: Modifier = Modifier) {
    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
        shape = RoundedCornerShape(24.dp, 24.dp, 0.dp, 0.dp),
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(
                20.dp,
                alignment = Alignment.CenterVertically,
            ),
        ) {
            Icon(
                imageVector = AppIcons.User,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary,
            )
            Text(
                text = stringResource(Res.string.feature_repository_details_title),
                style = MaterialTheme.typography.titleLarge,
            )
        }
    }
}

@Preview
@Composable
fun RepositoryDetailPlaceholderPreview() {
    AppTheme {
        RepositoryDetailsPlaceholder()
    }
}
