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
    @SerializedName("price") val price: String,
    @SerializedName("rate") val rate: String,
    @SerializedName("startDate") val startDate: String,
    @SerializedName("hasLike") val hasLike: Boolean?,
    @SerializedName("publishDate") val publishDate: String
)

fun CourseDto.toDomain(isLocalFavorite: Boolean): Course {
    return Course(
        id = this.id.toString(),
        title = this.title,
        description = this.text,
        price = this.price,
        rating = this.rate,
        startDate = this.startDate,
        isFavorite = isLocalFavorite || (this.hasLike ?: false),
        publishDate = this.publishDate
    )
}
