package com.muxixyz.ccnubox.timetable.data.repository

import com.muxixyz.android.iokit.Result
import com.muxixyz.ccnubox.timetable.data.database.TimetableLocalRepo
import com.muxixyz.ccnubox.timetable.data.domain.Course
import com.muxixyz.ccnubox.timetable.data.network.TimetableRemoteRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.ConcurrentMap

class TimetableRepository(
    private val courseLocal: TimetableLocalRepo,
    private val courseRemote: TimetableRemoteRepo
) {
    private var cachedCourses: ConcurrentMap<String, Course>? = null

    suspend fun getCourses(forceUpdate: Boolean): Result<List<Course>> {
        return withContext(Dispatchers.IO) {
            // Respond immediately with cache if available and not dirty
            if (!forceUpdate) {
                cachedCourses?.let { cachedCourses ->
                    return@withContext Result.Success(cachedCourses.values.sortedBy { it.id })
                }
            }

            val newCourses = fetchCoursesFromRemoteOrLocal(forceUpdate)

            // Refresh the cache with the new courses
            (newCourses as? Result.Success)?.let { refreshCache(it.data) }

            cachedCourses?.values?.let { courses ->
                return@withContext Result.Success(courses.sortedBy { it.id })
            }

            (newCourses as? Result.Success)?.let {
                if (it.data.isEmpty()) {
                    return@withContext Result.Success(it.data)
                }
            }

            return@withContext Result.Error(Exception("Illegal state"))
        }
    }

    suspend fun fetchCoursesFromRemoteOrLocal(forceUpdate: Boolean): Result<List<Course>> {
        // Remote first
        val remoteCourses = courseRemote.getAllCourses()
        when (remoteCourses) {
            is Result.Error -> ("Remote data source fetch failed")
            is Result.Success -> {
                refreshLocalDataSource(remoteCourses.data)
                return remoteCourses
            }
            else -> throw IllegalStateException()
        }

        // Don't read from local if it's forced
        if (forceUpdate) {
            return Result.Error(Exception("Can't force refresh: remote data source is unavailable"))
        }

        // Local if remote fails
        val localCourses = courseLocal.getAllCourses()
        if (localCourses is Result.Success) return localCourses
        return Result.Error(Exception("Error fetching from remote and local"))
    }

    suspend fun refreshLocalDataSource(courses: List<Course>) {
        courseLocal.deleteAllCourses()
        courseLocal.addCourses(courses)
    }

    fun refreshCache(courses: List<Course>) {
        cachedCourses?.clear()
        courses.sortedBy { it.id }.forEach {
            cacheAndPerform(it) {}
        }
    }

    private fun cacheCourse(course: Course): Course {
        val cachedCourse = Course(
            course.id, course.name, course.place, course.teacher, course.dayOfWeek,
            course.courseTime, course.weeks, course.courseTableId
        )
        // Create if it doesn't exist.
        if (cachedCourses == null) {
            cachedCourses = ConcurrentHashMap()
        }
        cachedCourses?.put(cachedCourse.id, cachedCourse)
        return cachedCourse
    }

    private inline fun cacheAndPerform(course: Course, perform: (Course) -> Unit) {
        val cachedCourse = cacheCourse(course)
        perform(cachedCourse)
    }

    suspend fun addCourse(course: Course) {
        // Do in memory cache update to keep the app UI up to date
        cacheAndPerform(course) {
            coroutineScope {
                launch { courseRemote.addCourse(it) }
                launch { courseLocal.addCourse(it) }
            }
        }
    }

    suspend fun deleteAllCourses() {
        withContext(Dispatchers.IO) {
            coroutineScope {
                launch { courseRemote.deleteAllCourses() }
                launch { courseLocal.deleteAllCourses() }
            }
        }
        cachedCourses?.clear()
    }

    suspend fun deleteCourse(courseId: String) {
        coroutineScope {
            launch { courseRemote.deleteCourse(courseId) }
            launch { courseLocal.deleteCourse(courseId) }
        }

        cachedCourses?.remove(courseId)
    }

    private fun getCourseById(id: String) = cachedCourses?.get(id)
}