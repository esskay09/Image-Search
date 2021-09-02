package com.terranullius.task.framework.presentation

import android.app.WallpaperManager
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Bitmap.CompressFormat.PNG
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import coil.ImageLoader
import coil.request.ImageRequest
import coil.request.SuccessResult
import com.terranullius.task.framework.presentation.composables.MyApp
import com.yalantis.ucrop.UCrop
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream


@ExperimentalCoroutinesApi
@FlowPreview
@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private lateinit var wallpaperManager: WallpaperManager
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        wallpaperManager = WallpaperManager.getInstance(this)

        lifecycleScope.launchWhenCreated {
            viewModel.onImageSet.collect {
                it.getContentIfNotHandled()?.let { imgUrl ->
                    startCrop(imgUrl)
                }
            }
        }

        setContent {
            MyApp()
        }
    }

    private fun startCrop(url: String) {
        val file = File(filesDir.path, "image.png")
        val file2 = File(filesDir.path, "image1.png")

        lifecycleScope.launchWhenCreated {
            val bitmap = url.toBitmap()
            withContext(Dispatchers.IO) {
                bitmap?.let {
                    it.saveToFile(file)
                    UCrop.of(Uri.fromFile(file), Uri.fromFile(file2))
                        .start(this@MainActivity);
                }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == RESULT_OK && requestCode == UCrop.REQUEST_CROP) {
            val resultUri = data?.let {
                UCrop.getOutput(it)
            }
            resultUri?.let {
                setWallpaper(it.toString())
            }

        } else if (resultCode == UCrop.RESULT_ERROR) {
            val cropError = UCrop.getError(data!!)
        }
    }

    private fun setWallpaper(url: String) {
        lifecycleScope.launchWhenCreated {
            val bitmap = url.toBitmap()
            kotlin.runCatching {
                wallpaperManager.setBitmap(bitmap)
                Toast.makeText(this@MainActivity, "Wallpaper changed", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private suspend fun String.toBitmap(): Bitmap? {
        val loader = ImageLoader(this@MainActivity)
        val req = ImageRequest.Builder(this@MainActivity)
            .data(this)
            .build()
        val result = (loader.execute(req) as SuccessResult).drawable
        val bitmap = (result as BitmapDrawable).bitmap
        return bitmap
    }

    private fun Bitmap.saveToFile(file: File) {
        kotlin.runCatching {
            val outputStream = FileOutputStream(file, false)
            compress(PNG, 100, outputStream)
            outputStream.close()
        }
    }

}