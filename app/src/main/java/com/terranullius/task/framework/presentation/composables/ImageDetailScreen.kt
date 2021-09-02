package com.terranullius.task.framework.presentation.composables

import android.content.res.Configuration.ORIENTATION_LANDSCAPE
import android.content.res.Configuration.ORIENTATION_PORTRAIT
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import com.terranullius.task.business.domain.model.Image
import com.terranullius.task.framework.presentation.MainViewModel
import com.terranullius.task.framework.presentation.composables.components.ErrorComposable
import com.terranullius.task.framework.presentation.composables.components.ImageCard
import com.terranullius.task.framework.presentation.composables.theme.*
import kotlin.random.Random


/**
 * Calculate Screen Height for Supporting all screen sizes
 * */
@Composable
fun ImageDetailScreen(
    modifier: Modifier = Modifier.fillMaxSize(),
    viewModel: MainViewModel,
) {

    val screenHeight = LocalConfiguration.current.screenHeightDp
    val imageHeight = screenHeight.div(2.4).dp

    val selectedImage = remember {
        viewModel.selectedImage
    }

    Box(modifier = modifier, contentAlignment = Alignment.Center) {
        when (selectedImage.value) {
            null -> {
                ErrorComposable()
            }
            else -> {
                Scaffold(
                    floatingActionButton = {
                        FloatingActionButton(onClick = {
                            viewModel.onSetImage(selectedImage.value!!.largeImageUrl)
                        }) {
                            Icon(imageVector = Icons.Default.Wallpaper, contentDescription = "wallpaper")
                        }
                    }, floatingActionButtonPosition = FabPosition.End
                ) { paddingValues ->
                    ImageDetailContent(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(
                                top = paddingValues
                                    .calculateTopPadding()
                                    .plus(8.dp),
                                bottom = paddingValues.calculateBottomPadding(),
                                start = paddingValues.calculateStartPadding(LayoutDirection.Ltr),
                                end = paddingValues.calculateEndPadding(LayoutDirection.Ltr)
                            ),
                        image = selectedImage.value!!,
                        imageHeight = imageHeight
                    )
                }
            }
        }
    }
}

@Composable
fun ImageDetailContent(
    modifier: Modifier = Modifier,
    image: Image,
    imageHeight: Dp,
) {

    /**
     *  Set Different layout depending on screen orientation
     * */


    when (LocalConfiguration.current.orientation) {
        ORIENTATION_LANDSCAPE -> ImageDetailContentLandScape(
            modifier = modifier,
            image = image,
            imageHeight = imageHeight
        )
        ORIENTATION_PORTRAIT -> ImageDetailContentPortrait(
            modifier = modifier,
            image = image,
            imageHeight = imageHeight
        )
        else -> ImageDetailContentPortrait(
            modifier = modifier,
            image = image,
            imageHeight = imageHeight
        )
    }
}


@Composable
private fun ImageDetailContentPortrait(
    modifier: Modifier,
    image: Image,
    imageHeight: Dp
) {
    Column(modifier = modifier) {
        ImageCard(
            image = image,
            onClick = {},
            modifier = Modifier
                .fillMaxWidth()
                .height(imageHeight)
        ) {
        }

        ImageDetailDescription(image)
    }
}

@Composable
fun ImageDetailContentLandScape(
    modifier: Modifier = Modifier,
    image: Image,
    imageHeight: Dp
) {
    Row(modifier = modifier) {
        ImageCard(
            image = image,
            onClick = {},
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight()
        ) {
        }

        ImageDetailDescription(
            modifier = Modifier
                .fillMaxHeight()
                .weight(1f)
                .padding(horizontal = 8.dp), image = image
        )
    }

}

@Composable
private fun ImageDetailDescription(image: Image, modifier: Modifier = Modifier) {
    LazyColumn(modifier = modifier) {
        item {
            Spacer(modifier = Modifier.height(12.dp))
        }

        item {
            Row {
                Text(
                    text = image.artist,
                    style = MaterialTheme.typography.h5.copy(
                        fontWeight = FontWeight.Bold,
                        color = getHeadlineTextColor()
                    )
                )
            }
        }
        item {
            Spacer(modifier = Modifier.height(18.dp))
        }

        item {
            Column(Modifier.padding(8.dp)) {
                ImageDescriptionItem(text = image.likes.toString(), icon = Icons.Default.ThumbUp)
                Spacer(modifier = Modifier.height(12.dp))
                ImageDescriptionItem(text = image.views.toString(), icon = Icons.Default.Visibility)
                Spacer(modifier = Modifier.height(12.dp))
                ImageDescriptionItem(text = image.artist, icon = Icons.Default.Person)
                Spacer(modifier = Modifier.height(12.dp))
                ImageDescriptionItem(
                    text = Random.nextInt(50, 850).toString(),
                    icon = Icons.Default.Comment
                )
            }

        }
    }
}

@Composable
fun ImageDescriptionItem(
    modifier: Modifier = Modifier,
    text: String,
    icon: ImageVector
) {
    Row(modifier = modifier) {
        Icon(icon, contentDescription = "", tint = getTextColor())
        Spacer(modifier = Modifier.width(10.dp))
        Text(text = text, color = getTextColor())
    }
}