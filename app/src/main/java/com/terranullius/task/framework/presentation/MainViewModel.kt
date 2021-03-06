package com.terranullius.task.framework.presentation

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.terranullius.task.business.domain.model.Image
import com.terranullius.task.business.domain.state.Event
import com.terranullius.task.business.domain.state.StateResource
import com.terranullius.task.business.interactors.imagelist.ImageListInteractors
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import javax.inject.Inject


/**
 * ViewModel for MainActivity and Composable Screens
 * */

@ExperimentalCoroutinesApi
@FlowPreview
@HiltViewModel
class MainViewModel @Inject constructor(
    private val imageListInteractors: ImageListInteractors
) : ViewModel() {

    private val _searchQueryStateFLow: MutableStateFlow<String> = MutableStateFlow("Grand Canyon")
    val searchQueryStateFLow: StateFlow<String>
        get() = _searchQueryStateFLow

    private val _imageStateFlow: MutableStateFlow<StateResource<List<Image>>> =
        MutableStateFlow(StateResource.None)

    val imageStateFlow: StateFlow<StateResource<List<Image>>>
        get() = _imageStateFlow


    private val _selectedImage = mutableStateOf<Image?>(null)
    val selectedImage: State<Image?>
        get() = _selectedImage

    private val _onShare = MutableStateFlow<Event<String?>>(Event(null))
    val onShare: StateFlow<Event<String?>>
        get() = _onShare

    init {

        /**
         * Make Safe API requests with an interval of atleast 300ms, only for distinct query
         * */

        viewModelScope.launch {
            _searchQueryStateFLow.debounce(300)
                .filter { query ->
                    if (query.isBlank()) return@filter false else true
                }
                .distinctUntilChanged()
                .flatMapLatest { query ->
                    queryApi(query)
                }
                .collect {
                    _imageStateFlow.value = it
                }
        }
    }

    fun setSelectedImage(image: Image) {
        _selectedImage.value = image
    }

    fun onShare(imageUrl: String) {
        _onShare.value = Event(imageUrl)
    }

    fun searchImages(query: String) {
        _searchQueryStateFLow.value = query
    }

    private fun queryApi(query: String): Flow<StateResource<List<Image>>> {
        return imageListInteractors.searchImages.searchImages(query)
    }


}