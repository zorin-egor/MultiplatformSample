package com.sample.multiplatform.android

import android.os.Bundle
import androidx.activity.ComponentActivity
import com.sample.multiplatform.navigation.setupThemedNavigation


class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupThemedNavigation()
    }
}
