import com.muxixyz.ccnubox.build.BC.Deps
import com.muxixyz.ccnubox.build.implementation

plugins {
    id("com.muxixyz.ccnubox.build.module")
}

dependencies {
    implementation(Deps.networkGroup)
}