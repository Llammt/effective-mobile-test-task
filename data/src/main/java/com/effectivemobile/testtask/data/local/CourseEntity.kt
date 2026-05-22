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
    val rating: String,
    val isFavorite: Boolean
)

fun CourseEntity.toDomain(): Course {
    return Course(
        id = this.id.toString(),
        title = this.title,
        description = this.description,
        rating = this.rating,
        imageUrl = "",
        isFavorite = this.isFavorite
    )
}
