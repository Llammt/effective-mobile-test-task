package com.effectivemobile.testtask.data.repository

import com.effectivemobile.testtask.data.network.CourseApiService
import com.effectivemobile.testtask.data.network.toDomain
import com.effectivemobile.testtask.domain.model.Course
import com.effectivemobile.testtask.domain.repository.CourseRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class CourseRepositoryImpl(
    private val apiService: CourseApiService
) : CourseRepository {

    override suspend fun getCourses(): List<Course> {
        return apiService.getCourses().courses.map { dto ->
            dto.toDomain(isLocalFavorite = false)
        }
    }

    override fun getFavoriteCourses(): Flow<List<Course>> {
        return flow { emit(emptyList()) }
    }

    override suspend fun toggleFavorite(courseId: String, isFavorite: Boolean) {
        // тут будет запись в бд
    }
}
