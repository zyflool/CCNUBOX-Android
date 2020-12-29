import com.muxixyz.ccnubox.build.BC.Project
import com.muxixyz.ccnubox.build.BC.Deps

plugins {
    id("com.muxixyz.ccnubox.build.module")
}

dependencies {
    implementation(project(Project.featureScheduleExport))
}