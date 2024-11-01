package com.sample.app.core.ui.widgets

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import com.sample.app.core.ui.icon.AppIcons
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun SearchToolbar(
    searchQuery: String,
    contentDescriptionBack: String?,
    contentDescriptionSearch: String?,
    contentDescriptionClose: String?,
    onSearchQueryChanged: (String) -> Unit,
    onSearchTriggered: (String) -> Unit,
    isFocusRequest: Boolean = true,
    onBackClick: (() -> Unit)? = null,
    placeholder: String? = null,
    inputFilter: (String) -> Boolean = { "\n" !in it },
    visualTransformation: VisualTransformation = VisualTransformation.None,
    imeAction: ImeAction = ImeAction.Search,
    keyboardType: KeyboardType = KeyboardType.Text,
    modifier: Modifier = Modifier,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier.fillMaxWidth(),
    ) {
        if (onBackClick != null) {
            IconButton(onClick = onBackClick) {
                Icon(
                    imageVector = AppIcons.ArrowBack,
                    contentDescription = contentDescriptionBack,
                )
            }
        }
        SearchTextField(
            onSearchQueryChanged = onSearchQueryChanged,
            onSearchTriggered = onSearchTriggered,
            searchQuery = searchQuery,
            contentDescriptionSearch = contentDescriptionSearch,
            contentDescriptionClose = contentDescriptionClose,
            placeholder = placeholder,
            isFocusRequest = isFocusRequest,
            inputFilter = inputFilter,
            visualTransformation = visualTransformation,
            imeAction = imeAction,
            keyboardType = keyboardType
        )
    }
}

@Preview
@Composable
fun SearchToolbarPreview() {
    SearchToolbar(
        searchQuery = "query",
        contentDescriptionSearch = "contentDescriptionSearch",
        contentDescriptionClose = "contentDescriptionClose",
        contentDescriptionBack = "contentDescriptionBack",
        onSearchQueryChanged = {},
        onSearchTriggered = {},
        onBackClick = {}
    )
}