rootProject.name = "ccnubox-android"

include(":app")

//首页模块
include(":feature-home")
include(":feature-home-export")

//个人主页模块
include(":feature-profile")
include(":feature-profile-export")

//图书馆模块
include(":feature-library")
include(":feature-library-export")

//日程模块
include(":feature-schedule")
include(":feature-schedule-export")

//课程表模块
include(":feature-timetable")
include(":feature-timetable-export")

//huashi相关工具模块
include(":feature-ccnutoolbox")
include(":feature-ccnutoolbox-export")

//通用工具箱模块
include(":feature-unitoolbox")
include(":feature-unitoolbox-export")

include(":feature-uikit")
include(":feature-iokit")

include(":feature-common")

include(":infras-iokit")
