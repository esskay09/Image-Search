package com.terranullius.task.framework.presentation.composables

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.terranullius.task.framework.presentation.MainViewModel
import com.terranullius.task.framework.presentation.util.Screen
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview

/**
 * Set up Navigation
 * */

@FlowPreview
@ExperimentalCoroutinesApi
@Composable
fun Navigation(modifier: Modifier = Modifier, viewModel: MainViewModel = viewModel()) {

    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Screen.Login.route,
    ){

        composable(Screen.Login.route){
            LoginScreen(navController = navController, modifier = modifier)
        }

        composable(Screen.Main.route){
            MainScreen(navController = navController, viewModel = viewModel, modifier = modifier)
        }

        composable(Screen.ImageDetail.route){
            ImageDetailScreen(viewModel = viewModel, modifier = modifier)
        }

    }

}