package com.effectivemobile.testtask.features.presentation.courses

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.effectivemobile.testtask.features.databinding.FragmentCoursesBinding
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class CoursesFragment : Fragment() {

    private var _binding: FragmentCoursesBinding? = null
    private val binding get() = _binding!!

    private val viewModel: CourseListViewModel by viewModel()

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
            viewModel.toggleFavorite(clickedCourse)
        }

        binding.rvCourses.adapter = coursesAdapter

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.state.collect { state ->
                when (state) {
                    is CourseListState.Loading -> {
                        // binding.progressBar.isVisible = true
                    }
                    is CourseListState.Success -> {
                        // binding.progressBar.isVisible = false
                        binding.rvCourses.isVisible = true
                        coursesAdapter.submitList(state.courses)
                    }
                    is CourseListState.Error -> {
                        // binding.progressBar.isVisible = false
                        Toast.makeText(requireContext(), state.message, Toast.LENGTH_LONG).show()
                    }
                    is CourseListState.Empty -> {
                        // binding.progressBar.isVisible = false
                        Toast.makeText(requireContext(), "@string/text_label_empty_list", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
