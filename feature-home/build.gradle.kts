import com.muxixyz.ccnubox.build.BC.Project
import com.muxixyz.ccnubox.build.BC.Deps
import com.muxixyz.ccnubox.build.implementation

plugins {
    id("com.muxixyz.ccnubox.build.module")
}

dependencies {
    implementation(project(Project.featureHomeExport))
    implementation(project(Project.featureUIKit))
    implementation(project(Project.featureIOKit))
    implementation(project(Project.infrastructureIOKit))

    implementation(Deps.koinRuntimeGroup)
    implementation(Deps.jetpackUIGroup)
    implementation(Deps.networkGroup)
    implementation(Deps.jetpackLifeCycleRuntimeGroup)
}