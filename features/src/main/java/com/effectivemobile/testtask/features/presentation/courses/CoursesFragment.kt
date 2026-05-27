package com.effectivemobile.testtask.features.presentation.courses

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.effectivemobile.testtask.features.R
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
                        Toast.makeText(
                            requireContext(),
                            "@string/text_label_empty_list",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        }

        binding.btnFilter.setOnClickListener { anchorView ->
            val listPopupWindow = androidx.appcompat.widget.ListPopupWindow(requireContext())

            listPopupWindow.anchorView = anchorView

            val density = requireContext().resources.displayMetrics.density
            val menuWidthDp = 200
            listPopupWindow.width = (menuWidthDp * density).toInt()
            listPopupWindow.horizontalOffset = (-160 * density).toInt()

            val options = listOf("По дате добавления", "По цене")

            val adapter = object : android.widget.ArrayAdapter<String>(
                requireContext(),
                R.layout.item_filter_menu,
                options
            ) {
                override fun getView(position: Int, convertView: android.view.View?, parent: android.view.ViewGroup): android.view.View {
                    val view = convertView ?: android.view.LayoutInflater.from(context)
                        .inflate(R.layout.item_filter_menu, parent, false)

                    val textView = view.findViewById<android.widget.TextView>(R.id.tvFilterText)
                    textView.text = getItem(position)

                    return view
                }
            }

            listPopupWindow.setAdapter(adapter)

            listPopupWindow.setOnItemClickListener { _, _, position, _ ->
                when (position) {
                    0 -> viewModel.sortCourses(byDate = true)
                    1 -> viewModel.sortCourses(byDate = false)
                }
                listPopupWindow.dismiss()
            }

            val backgroundDrawable = android.graphics.drawable.GradientDrawable().apply {
                setColor(android.graphics.Color.parseColor("#23242A"))
                cornerRadius = 12 * density
            }
            listPopupWindow.setBackgroundDrawable(backgroundDrawable)

            listPopupWindow.show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
