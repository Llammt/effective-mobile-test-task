package com.effectivemobile.testtask.features.presentation.favorites

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.effectivemobile.testtask.domain.model.Course
import com.effectivemobile.testtask.domain.usecase.GetFavoriteCoursesUseCase
import com.effectivemobile.testtask.domain.usecase.ToggleFavoriteUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class FavoriteCoursesViewModel(
    private val getFavoriteCoursesUseCase: GetFavoriteCoursesUseCase,
    private val toggleFavoriteUseCase: ToggleFavoriteUseCase
) : ViewModel() {

    private val _state = MutableStateFlow<FavoriteState>(FavoriteState.Loading)
    val state: StateFlow<FavoriteState> = _state.asStateFlow()

    init {
        observeFavoriteCourses()
    }

    private fun observeFavoriteCourses() {
        viewModelScope.launch {
            try {
                getFavoriteCoursesUseCase().collect { favoriteCourses ->
                    if (favoriteCourses.isEmpty()) {
                        _state.value = FavoriteState.Empty
                    } else {
                        _state.value = FavoriteState.Success(favoriteCourses)
                    }
                }
            } catch (e: Exception) {
                _state.value = FavoriteState.Error(e.localizedMessage ?: "Unknown database error")
            }
        }
    }

    fun removeFromFavorite(course: Course) {
        viewModelScope.launch {
            try {
                toggleFavoriteUseCase(course)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}

sealed interface FavoriteState {
    object Loading : FavoriteState
    object Empty : FavoriteState
    data class Success(val courses: List<Course>) : FavoriteState
    data class Error(val message: String) : FavoriteState
}