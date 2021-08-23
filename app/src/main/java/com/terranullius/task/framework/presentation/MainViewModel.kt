package com.terranullius.task.framework.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.terranullius.task.business.domain.model.Image
import com.terranullius.task.business.domain.state.StateResource
import com.terranullius.task.business.interactors.imagelist.ImageListInteractors
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val imageListInteractors: ImageListInteractors
): ViewModel() {

    val imageStateFlow: StateFlow<StateResource<List<Image>>> =
        imageListInteractors.getAllImages.getAllImages().stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = StateResource.Loading
        )

}