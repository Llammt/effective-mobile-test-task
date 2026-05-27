package com.effectivemobile.testtask.domain.repository
import com.effectivemobile.testtask.domain.model.Course
import kotlinx.coroutines.flow.Flow

interface CourseRepository {
    suspend fun getCourses(): List<Course>

    fun getFavoriteCourses(): Flow<List<Course>>

    suspend fun toggleFavorite(course: Course)
}