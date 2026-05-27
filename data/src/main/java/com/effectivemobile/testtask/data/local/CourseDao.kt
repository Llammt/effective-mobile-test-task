package com.effectivemobile.testtask.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface CourseDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavorite(course: CourseEntity)

    @Query("SELECT * FROM courses")
    suspend fun getAllCourses(): List<CourseEntity>

    @Query("SELECT id FROM courses")
    suspend fun getFavoriteIds(): List<Int>

    @Query("DELETE FROM courses WHERE id = :courseId")
    suspend fun deleteFromFavorites(courseId: Int)

    @Query("SELECT * FROM courses")
    fun getFavoriteCoursesFlow(): Flow<List<CourseEntity>>
}
