package com.effectivemobile.testtask.domain.usecase
import com.effectivemobile.testtask.domain.model.Course
import com.effectivemobile.testtask.domain.repository.CourseRepository

class GetCoursesUseCase(private val repository: CourseRepository) {

    suspend operator fun invoke(): List<Course> {
        return repository.getCourses()
    }
}