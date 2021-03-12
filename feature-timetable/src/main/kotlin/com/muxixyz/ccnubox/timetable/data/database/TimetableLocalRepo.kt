package com.muxixyz.ccnubox.timetable.data.database

import android.content.Context
import com.muxixyz.android.iokit.Result
import com.muxixyz.android.iokit.preference.Preferences
import com.muxixyz.ccnubox.timetable.data.TimetableDataSource
import com.muxixyz.ccnubox.timetable.data.domain.Course
import com.muxixyz.ccnubox.timetable.data.domain.asDatabaseModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


class TimetableLocalRepo(context: Context, private val courseDao: CourseDao) : Preferences(context, "timetable"),
    TimetableDataSource {

    override suspend fun getAllCourses(): Result<List<Course>> = withContext(Dispatchers.IO) {
        return@withContext try {
            Result.Success(courseDao.getCourses().asDomainModel())
        } catch (e: Exception) {
            Result.Error(e)
        }
    }

    override suspend fun getCourseById(id: String): Result<Course> = withContext(Dispatchers.IO) {
        try {
            val course = courseDao.getCourseById(id)
            if (course != null) {
                return@withContext Result.Success(course.asDomainModel())
            } else {
                return@withContext Result.Error(Exception("Course not found!"))
            }
        } catch (e: Exception) {
            return@withContext Result.Error(e)
        }
    }

    override suspend fun deleteCourse(id: String) = withContext(Dispatchers.IO) {
        courseDao.deleteCourseById(id)
    }

    override suspend fun updateCourse(id: String, course: Course) = withContext(Dispatchers.IO) {
        courseDao.updateCourse(course.asDatabaseModel())
    }

    override suspend fun addCourse(course: Course) = withContext(Dispatchers.IO) {
        courseDao.insertCourse(course.asDatabaseModel())
    }

    override suspend fun addCourses(courses: List<Course>) = withContext(Dispatchers.IO) {
        courseDao.insertCourses(courses.asDatabaseModel())
    }

    override suspend fun deleteAllCourses() = withContext(Dispatchers.IO) {
        courseDao.deleteAllCourses()
    }
}