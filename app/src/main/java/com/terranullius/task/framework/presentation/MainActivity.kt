package com.terranullius.task.framework.presentation

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.terranullius.task.framework.presentation.composables.MyApp
import com.terranullius.task.framework.presentation.util.showToast
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @ExperimentalCoroutinesApi
    private val viewModel: MainViewModel by viewModels()

    @ExperimentalCoroutinesApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        /**
         * Collect and start share intent
         * */
        lifecycleScope.launchWhenCreated {
            viewModel.onShare.collect {
                it.getContentIfNotHandled()?.let {
                    shareImage(it)
                }
            }
        }

        setContent {
            MyApp(viewModel = viewModel)
        }
    }

    private fun shareImage(url: String) {

        try {
            val intent = Intent(Intent.ACTION_SEND).apply {
                type = "text/plain"
                putExtra(Intent.EXTRA_SUBJECT, "Sharing photo")
                putExtra(Intent.EXTRA_TEXT, url)
            }
            with(Intent.createChooser(intent, "Share photo")){
                startActivity(this)
            }
        } catch (e: Exception) {
            showToast("No app found to share")
        }
    }
}