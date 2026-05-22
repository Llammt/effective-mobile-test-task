package com.effectivemobile.testtask.data.network

import com.effectivemobile.testtask.domain.model.Course
import com.google.gson.annotations.SerializedName

data class CoursesResponse(
    @SerializedName("courses") val courses: List<CourseDto>
)

data class CourseDto(
    @SerializedName("id") val id: Int,
    @SerializedName("title") val title: String,
    @SerializedName("text") val text: String,
    @SerializedName("rate") val rate: String,
    @SerializedName("hasLike") val hasLike: Boolean?
)

fun CourseDto.toDomain(isLocalFavorite: Boolean): Course {
    return Course(
        id = this.id.toString(),
        title = this.title,
        description = this.text,
        rating = this.rate,
        imageUrl = "",
        isFavorite = isLocalFavorite || (this.hasLike ?: false)
    )
}
