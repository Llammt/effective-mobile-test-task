package com.effectivemobile.testtask.data.repository

import com.effectivemobile.testtask.data.network.toDomain
import com.effectivemobile.testtask.data.local.CourseDao
import com.effectivemobile.testtask.data.local.CourseEntity
import com.effectivemobile.testtask.data.local.toDomain
import com.effectivemobile.testtask.data.network.CourseApiService
import com.effectivemobile.testtask.domain.model.Course
import com.effectivemobile.testtask.domain.repository.CourseRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

class CourseRepositoryImpl(
    private val apiService: CourseApiService,
    private val courseDao: CourseDao
) : CourseRepository {

    override suspend fun getCourses(): List<Course> = withContext(Dispatchers.IO) {
        try {
            val networkCourses = apiService.getCourses().courses
            val favoriteIds = courseDao.getFavoriteIds().map { it.toString() }.toSet()

            return@withContext networkCourses.map { dto ->
                val isLocalFavorite = favoriteIds.contains(dto.id.toString())
                dto.toDomain(isLocalFavorite = isLocalFavorite)
            }
        } catch (e: Exception) {
            return@withContext courseDao.getAllCourses().map { it.toDomain() }
        }
    }

    override fun getFavoriteCourses(): Flow<List<Course>> {
        return courseDao.getFavoriteCoursesFlow().map { entities ->
            entities.map { it.toDomain() }
        }
    }

    override suspend fun toggleFavorite(course: Course) = withContext(Dispatchers.IO) {
        if (course.isFavorite) {
            courseDao.deleteFromFavorites(course.id.toInt())
        } else {
            val entity = CourseEntity(
                id = course.id.toInt(),
                title = course.title,
                description = course.description,
                rating = course.rating,
                isFavorite = true
            )
            courseDao.insertFavorite(entity)
        }
    }
}
