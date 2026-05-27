package com.effectivemobile.testtask.domain.usecase

import com.effectivemobile.testtask.domain.model.Course
import com.effectivemobile.testtask.domain.repository.CourseRepository

class ToggleFavoriteUseCase(
    private val repository: CourseRepository
) {
    suspend operator fun invoke(course: Course) {
        repository.toggleFavorite(course)
    }
}