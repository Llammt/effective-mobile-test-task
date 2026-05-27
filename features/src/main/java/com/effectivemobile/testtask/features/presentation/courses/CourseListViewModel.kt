package com.effectivemobile.testtask.features.presentation.courses

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.effectivemobile.testtask.domain.model.Course
import com.effectivemobile.testtask.domain.usecase.GetCoursesUseCase
import com.effectivemobile.testtask.domain.usecase.ToggleFavoriteUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class CourseListViewModel(
    private val getCoursesUseCase: GetCoursesUseCase,
    private val toggleFavoriteUseCase: ToggleFavoriteUseCase
) : ViewModel() {

    private val _state = MutableStateFlow<CourseListState>(CourseListState.Loading)
    val state: StateFlow<CourseListState> = _state.asStateFlow()

    init {
        loadCourses()
    }

    fun loadCourses() {
        viewModelScope.launch {
            _state.value = CourseListState.Loading
            try {
                val courses = getCoursesUseCase()
                if (courses.isEmpty()) {
                    _state.value = CourseListState.Empty
                } else {
                    _state.value = CourseListState.Success(courses)
                }
            } catch (e: Exception) {
                _state.value = CourseListState.Error(e.localizedMessage ?: "Unknown error")
            }
        }
    }

    fun toggleFavorite(course: Course) {
        viewModelScope.launch {
            try {
                toggleFavoriteUseCase(course)

                val currentState = _state.value
                if (currentState is CourseListState.Success) {
                    val updatedList = currentState.courses.map {
                        if (it.id == course.id) it.copy(isFavorite = !it.isFavorite) else it
                    }
                    _state.value = CourseListState.Success(updatedList)
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun sortCourses(byDate: Boolean) {
        val currentState = _state.value
        if (currentState is CourseListState.Success) {
            val currentList = currentState.courses

            val sortedList = if (byDate) {
                currentList.sortedByDescending { it.publishDate }
            } else {
                currentList.sortedBy { course ->
                    // Очищаем строку цены от грязи (пробелы, ₽, символы) перед кастом в Int
                    val numericPrice = course.price.replace(Regex("[^0-9]"), "")
                    numericPrice.toIntOrNull() ?: 0
                }
            }

            // Пересоздаем стейт с новым отсортированным списком (toMutableList() или новый Success)
            _state.value = CourseListState.Success(sortedList.toList())
        }
    }
}

sealed interface CourseListState {
    object Loading : CourseListState
    object Empty : CourseListState
    data class Success(val courses: List<Course>) : CourseListState
    data class Error(val message: String) : CourseListState
}