package com.effectivemobile.testtask.domain.usecase

import com.effectivemobile.testtask.domain.model.Course
import com.effectivemobile.testtask.domain.repository.CourseRepository
import kotlinx.coroutines.flow.Flow

class GetFavoriteCoursesUseCase(
    private val repository: CourseRepository
) {
    operator fun invoke(): Flow<List<Course>> {
        return repository.getFavoriteCourses()
    }
}
