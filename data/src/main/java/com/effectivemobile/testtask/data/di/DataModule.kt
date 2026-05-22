package com.effectivemobile.testtask.data.di

import androidx.room.Room
import com.effectivemobile.testtask.data.local.AppDatabase
import com.effectivemobile.testtask.data.network.CourseApiService
import com.effectivemobile.testtask.data.repository.CourseRepositoryImpl
import com.effectivemobile.testtask.domain.repository.CourseRepository
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

import com.effectivemobile.testtask.domain.usecase.GetCoursesUseCase

val dataModule = module {

    single {
        HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
    }

    single {
        OkHttpClient.Builder()
            .addInterceptor(get<HttpLoggingInterceptor>())
            .build()
    }

    single {
        Retrofit.Builder()
            .baseUrl("https://drive.usercontent.google.com/")
            .client(get())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    single<CourseApiService> {
        get<Retrofit>().create(CourseApiService::class.java)
    }

    single<CourseRepository> {
        CourseRepositoryImpl(apiService = get(), courseDao = get())
    }

    single {
        Room.databaseBuilder(
            get(), // Кoin сам подставит Context приложения, который мы передали в App.kt
            AppDatabase::class.java,
            "courses_database"
        ).build()
    }

    single { get<AppDatabase>().courseDao() }
}

val domainModule = module {
    factory { GetCoursesUseCase(repository = get()) }
}
