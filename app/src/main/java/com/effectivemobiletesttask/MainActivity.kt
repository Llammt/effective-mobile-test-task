package com.effectivemobiletesttask

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.effectivemobile.testtask.domain.usecase.GetCoursesUseCase
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject

class MainActivity : AppCompatActivity() {
    private val getCoursesUseCase: GetCoursesUseCase by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycleScope.launch {
            Log.w("TEST_LOG", "Отправляем запрос в сеть...")
            try {
                val courses = getCoursesUseCase()

                Log.w("TEST_LOG", "Успех! Найдено курсов: ${courses.size}")

                courses.forEach { course ->
                    Log.w("TEST_LOG", "Курс: ${course.title} | Рейтинг: ${course.rating}")
                }
            } catch (e: Exception) {
                Log.w("TEST_LOG", "Ошибка при получении данных: ${e.localizedMessage}", e)
            }
        }
    }
}
