package com.effectivemobile.testtask.features.presentation.courses

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.effectivemobile.testtask.features.R
import com.effectivemobile.testtask.features.databinding.ItemCourseBinding
import com.effectivemobile.testtask.domain.model.Course

class CoursesAdapter(
    private val onFavoriteClick: (Course) -> Unit
) : ListAdapter<Course, CoursesAdapter.CourseViewHolder>(CourseDiffCallback()) {

    private val placeholderColors = listOf(
        android.R.color.holo_blue_dark,
        android.R.color.holo_green_dark,
        android.R.color.holo_purple,
        android.R.color.holo_orange_dark,
        android.R.color.holo_red_dark
    )

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CourseViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemCourseBinding.inflate(layoutInflater, parent, false)
        return CourseViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CourseViewHolder, position: Int) {
        holder.bind(getItem(position), position)
    }

    inner class CourseViewHolder(
        private val binding: ItemCourseBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(course: Course, position: Int) {
            with(binding) {
                tvCourseTitle.text = course.title
                tvCourseDescription.text = course.description
                tvRating.text = course.rating
                tvCoursePrice.text = course.price
                tvPublishDate.text = course.publishDate

                val colorRes = placeholderColors[position % placeholderColors.size]
                ivCourseCover.setImageResource(colorRes)

                if (course.isFavorite) {
                    ivFavorite.setImageResource(android.R.drawable.btn_star_big_on)
                    ivFavorite.setColorFilter(root.context.getColor(R.color.brand_green))
                } else {
                    ivFavorite.setImageResource(android.R.drawable.btn_star_big_off)
                    ivFavorite.clearColorFilter()
                }

                ivFavorite.setOnClickListener {
                    onFavoriteClick(course)
                }
            }
        }
    }


    class CourseDiffCallback : DiffUtil.ItemCallback<Course>() {
        override fun areItemsTheSame(oldItem: Course, newItem: Course): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Course, newItem: Course): Boolean {
            return oldItem == newItem
        }
    }
}