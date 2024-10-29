package com.sample.app.feature.details

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material.Text
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.sample.app.widget.cycloid.ProgressWidget
import com.sample.app.widget.cycloid.ProgressWidgetConfig
import com.sample.app.widget.cycloid.models.cycloid.CycloidColors
import com.sample.app.widget.cycloid.models.cycloid.CycloidModel

@Composable
fun DetailsScreen(
    userId: Long,
    userUrl: String,
    onShowSnackbar: suspend (String, String?) -> Boolean,
    viewModel: DetailsViewModel = viewModel { DetailsViewModel() },
) {
    remember { viewModel.getUsers(userId, userUrl) }

    val scaffoldState = rememberScaffoldState()
    val users = viewModel.users.collectAsState()
    val colors = CycloidColors(
        lineDefaultColor = Color( 128, 128, 128, 200),
        lineProgressColor = Color(124, 252, 0, 200),
        particleDefaultColor = Color( 105, 105, 105, 200),
        particleProgressColor = Color( 50, 205, 50, 200),
    )

    Column(modifier = Modifier.fillMaxSize()) {
        Text(
            text = users.value.joinToString(separator = "\n") { "${it.login} - ${it.id}" },
            modifier = Modifier.fillMaxWidth()
                .align(Alignment.CenterHorizontally)
        )

        ProgressWidget(
            progress = mutableStateOf(50),
            widgetConfig = ProgressWidgetConfig(
                shapeType = CycloidModel.Two(
                    colors = colors,
                    lineWidth = 7.dp.value,
                    particleRadius = 20.dp.value
                ),
                fromProgress = 0,
                toProgress = 100
            ),
            modifier = Modifier.size(300.dp)
                .align(Alignment.CenterHorizontally)
        )
    }
}