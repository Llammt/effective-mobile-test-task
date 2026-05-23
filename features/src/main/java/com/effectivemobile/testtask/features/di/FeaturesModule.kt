package com.effectivemobile.testtask.features.di

import com.effectivemobile.testtask.features.presentation.courses.CourseListViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val featuresModule = module {
    viewModel { CourseListViewModel(getCoursesUseCase = get()) }
}
