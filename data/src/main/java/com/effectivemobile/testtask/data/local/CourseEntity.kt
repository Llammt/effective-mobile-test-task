package com.effectivemobile.testtask.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.effectivemobile.testtask.domain.model.Course

@Entity(tableName = "courses")
data class CourseEntity(
    @PrimaryKey
    val id: Int,
    val title: String,
    val description: String,
    val price: String,
    val rating: String,
    val startDate: String,
    val isFavorite: Boolean,
    val publishDate: String
)

fun CourseEntity.toDomain(): Course {
    return Course(
        id = this.id.toString(),
        title = this.title,
        description = this.description,
        price = this.price,
        rating = this.rating,
        startDate = this.startDate,
        isFavorite = this.isFavorite,
        publishDate = this.publishDate
    )
}
