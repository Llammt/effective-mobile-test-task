package com.effectivemobile.testtask.data.repository

import com.effectivemobile.testtask.data.local.CourseDao
import com.effectivemobile.testtask.data.local.CourseEntity
import com.effectivemobile.testtask.data.local.toDomain
import com.effectivemobile.testtask.data.network.CourseApiService
import com.effectivemobile.testtask.domain.model.Course
import com.effectivemobile.testtask.domain.repository.CourseRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext

class CourseRepositoryImpl(
    private val apiService: CourseApiService,
    private val courseDao: CourseDao
) : CourseRepository {

//    override suspend fun getCourses(): List<Course> {
//        return apiService.getCourses().courses.map { dto ->
//            dto.toDomain(isLocalFavorite = false)
//        }
//    }

    override suspend fun getCourses(): List<Course> = withContext(Dispatchers.IO) {
        try {
            val networkCourses = apiService.getCourses().courses

            val entities = networkCourses.map { dto ->
                CourseEntity(
                    id = dto.id,
                    title = dto.title,
                    description = dto.text,
                    rating = dto.rate,
                    isFavorite = dto.hasLike ?: false
                )
            }

            courseDao.insertCourses(entities)

        } catch (e: Exception) {
            // приложение достанет сохраненные данные из бд, если они есть
            // (а если нет, то фигово, надо вернуться к этому позже)
        }

        courseDao.getAllCourses().map { it.toDomain() }
    }

    override fun getFavoriteCourses(): Flow<List<Course>> {
        return flow { emit(emptyList()) }
    }

    override suspend fun toggleFavorite(courseId: String, isFavorite: Boolean) {
        // тут будет запись в бд
    }
}
