package com.terranullius.task.framework.presentation.composables

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.terranullius.task.framework.presentation.MainViewModel
import com.terranullius.task.framework.presentation.util.Screen

@Composable
fun Navigation(modifier: Modifier = Modifier, viewModel: MainViewModel) {

    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Screen.Main.route){

        composable(Screen.Main.route){
            MainScreen(navController = navController, viewModel = viewModel)
        }

        composable(Screen.ImageDetail.route){
            ImageDetailScreen()
        }

    }

}