package com.terranullius.task.framework.presentation.composables

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.terranullius.task.framework.presentation.MainViewModel
import com.terranullius.task.framework.presentation.composables.theme.TaskTheme
import com.terranullius.task.framework.presentation.composables.theme.mainPadding
import kotlinx.coroutines.ExperimentalCoroutinesApi

/**
 * App theme and navigation applied to and accessible by all screens
 * */

@ExperimentalCoroutinesApi
@Composable
fun MyApp(
    modifier: Modifier = Modifier
        .fillMaxSize()
        .padding(horizontal = mainPadding),
) {

    TaskTheme {
        // A surface container using the 'background' color from the theme
        Surface(color = MaterialTheme.colors.background) {

            Navigation(modifier = modifier)
        }
    }
}