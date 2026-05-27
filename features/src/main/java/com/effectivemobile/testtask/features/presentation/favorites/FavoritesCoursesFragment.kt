package com.effectivemobile.testtask.features.presentation.favorites

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.effectivemobile.testtask.features.databinding.FragmentFavoritesBinding
import com.effectivemobile.testtask.features.presentation.courses.CoursesAdapter
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class FavoritesCoursesFragment : Fragment() {

    private var _binding: FragmentFavoritesBinding? = null
    private val binding get() = _binding!!

    private val viewModel: FavoriteCoursesViewModel by viewModel()
    private lateinit var coursesAdapter: CoursesAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavoritesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        coursesAdapter = CoursesAdapter { clickedCourse ->
            viewModel.removeFromFavorite(clickedCourse)
        }

        binding.rvFavoriteCourses.adapter = coursesAdapter

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.state.collect { state ->
                when (state) {
                    is FavoriteState.Loading -> {
                        // binding.progressBar.isVisible = true
                    }
                    is FavoriteState.Success -> {
                        binding.rvFavoriteCourses.isVisible = true
                        // binding.emptyView.isVisible = false
                        coursesAdapter.submitList(state.courses)
                    }
                    is FavoriteState.Error -> {
                        Toast.makeText(requireContext(), state.message, Toast.LENGTH_LONG).show()
                    }
                    is FavoriteState.Empty -> {
                        binding.rvFavoriteCourses.isVisible = false
                        // binding.emptyView.isVisible = true
                        coursesAdapter.submitList(emptyList())
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