package com.muxixyz.ccnubox.timetable.data.network

import com.muxixyz.android.iokit.Result
import com.muxixyz.ccnubox.iokit.network.RetrofitClients
import com.muxixyz.ccnubox.timetable.data.TimetableDataSource
import com.muxixyz.ccnubox.timetable.data.domain.Course
import retrofit2.http.GET

class TimetableRemoteRepo(retrofitClients: RetrofitClients) : TimetableDataSource {

    private val courseApi = retrofitClients.generalClient.create(ICourseApi::class.java)

    interface ICourseApi {
        @GET("course/")
        suspend fun getCourses(): List<NetworkCourse>
    }

    override suspend fun getAllCourses(): Result<List<Course>> {
        return Result.Success(arrayListOf())
    }

    override suspend fun getCourseById(id: String): Result<Course> {
        return Result.Success(Course("","", "", "", 0, "", "", ""))
    }

    override suspend fun deleteCourse(id: String) {
        TODO("Not yet implemented")
    }

    override suspend fun updateCourse(id: String, course: Course) {
        TODO("Not yet implemented")
    }
    override suspend fun addCourse(course: Course) {
        TODO("Not yet implemented")
    }

    override suspend fun addCourses(courses: List<Course>) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteAllCourses() {
        TODO("Not yet implemented")
    }
}