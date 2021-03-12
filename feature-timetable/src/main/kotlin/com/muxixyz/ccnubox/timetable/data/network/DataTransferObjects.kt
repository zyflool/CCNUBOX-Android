package com.muxixyz.ccnubox.timetable.data.network

import com.muxixyz.ccnubox.timetable.data.database.DatabaseCourse
import com.muxixyz.ccnubox.timetable.data.domain.Course

data class NetworkCourseContainer(val courses: List<NetworkCourse>) {
    fun asDatabaseModel(): List<DatabaseCourse> {
        return courses.map{
            DatabaseCourse(
                id = it.id,
                name = it.name,
                place = it.place,
                teacher = it.teacher,
                dayOfWeek = it.dayOfWeek,
                courseTime = it.courseTime,
                weeks = it.weeks,
                courseTableId = it.courseTableId)
        }
    }
}

data class NetworkCourse(
    var id: String,
    var name: String,
    var place: String,
    var teacher: String,
    var dayOfWeek: Int,  // 一周的第几天，0 是周一
    var courseTime: String, // 比如：1-2，表示第一节到第二节
    var weeks: String, // 1,2,3,4,5 哪几周上课
    var courseTableId: String // 课表 id
)

/**
 * Convert Network results to database objects
 */
fun NetworkCourseContainer.asDomainModel(): List<Course> {
    return courses.map {
        Course(
            id = it.id,
            name = it.name,
            place = it.place,
            teacher = it.teacher,
            dayOfWeek = it.dayOfWeek,
            courseTime = it.courseTime,
            weeks = it.weeks,
            courseTableId = it.courseTableId)
    }
}
