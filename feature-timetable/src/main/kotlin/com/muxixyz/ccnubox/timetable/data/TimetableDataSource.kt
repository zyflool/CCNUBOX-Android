package com.muxixyz.ccnubox.timetable.data

import com.muxixyz.android.iokit.Result
import com.muxixyz.ccnubox.timetable.data.domain.Course

interface TimetableDataSource {
    suspend fun getAllCourses(): Result<List<Course>>

    suspend fun getCourseById(id :String) : Result<Course>

    suspend fun deleteCourse(id:String)

    suspend fun updateCourse(id:String, course: Course)

    suspend fun addCourse(course:Course)

    suspend fun addCourses(courses:List<Course>)

    suspend fun deleteAllCourses()
}