package com.effectivemobile.testtask.features.presentation.courses

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.effectivemobile.testtask.features.databinding.FragmentCoursesBinding
import com.effectivemobile.testtask.features.presentation.courses.CoursesAdapter

class CoursesFragment : Fragment() {

    private var _binding: FragmentCoursesBinding? = null
    private val binding get() = _binding!!

    private lateinit var coursesAdapter: CoursesAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCoursesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        coursesAdapter = CoursesAdapter { clickedCourse ->
            Toast.makeText(
                requireContext(),
                "Кликнули на избранное у курса: ${clickedCourse.title}",
                Toast.LENGTH_SHORT
            ).show()
        }

        binding.rvCourses.adapter = coursesAdapter

        val fakeCourses = listOf(
            com.effectivemobile.testtask.domain.model.Course(
                id = "1",
                title = "Java-разработчик с нуля",
                description = "Освойте backend-разработку и программирование на Java, фреймворки Spring и Hibernate с практикой на реальных проектах.",
                rating = "4.9",
                imageUrl = "",
                isFavorite = true
            ),
            com.effectivemobile.testtask.domain.model.Course(
                id = "2",
                title = "Разработка на Kotlin",
                description = "Изучите основы синтаксиса, ООП, корутины и создание мобильных приложений под Android.",
                rating = "4.8",
                imageUrl = "",
                isFavorite = false
            ),
            com.effectivemobile.testtask.domain.model.Course(
                id = "3",
                title = "Тестировщик ПО (QA)",
                description = "Научитесь искать баги, писать тест-кейсы и автоматизировать проверки веб-интерфейсов.",
                rating = "4.5",
                imageUrl = "",
                isFavorite = false
            )
        )

        coursesAdapter.submitList(fakeCourses)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}