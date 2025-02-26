cmake_minimum_required(VERSION 3.4.1)
project(RealmKotlin)
set(CAPI "${CMAKE_SOURCE_DIR}/../../../external/core")
set(CAPI_BUILD "${CAPI}/build-android-${ANDROID_ABI}-${CMAKE_BUILD_TYPE}")
set(SWIG_JNI_GENERATED "${CMAKE_SOURCE_DIR}/../../../jni-swig-stub/build/generated/sources/jni")
set(SWIG_JNI_HELPERS "${CMAKE_SOURCE_DIR}/../../../jni-swig-stub/src/main/jni")

# Build Realm Core
# Set option flags for Core.
# See https://github.com/realm/realm-core/blob/master/CMakeLists.txt#L174 for the full list.
set(REALM_ENABLE_SYNC ON)
set(REALM_BUILD_LIB_ONLY ON)
add_subdirectory("realm-core" EXCLUDE_FROM_ALL)

set(CMAKE_CXX_FLAGS "${CMAKE_CXX_FLAGS} -std=c++17")

include_directories(${CAPI_BUILD}/src jni ${SWIG_JNI_GENERATED} ${SWIG_JNI_HELPERS})

file(GLOB jni_SRC
        "jni/env_utils.cpp"
        "jni/utils.cpp"
        "jni/java_global_ref_by_move.cpp"
        "jni/java_class_global_def.cpp"
        "jni/java_class.cpp"
        "jni/java_method.cpp"
        )

file(GLOB swig_SRC
        ${SWIG_JNI_GENERATED}/realmc.cpp
        ${SWIG_JNI_HELPERS}/realm_api_helpers.cpp
        )

# Create shared FFI library that is consumed by the C-Interop layer.
add_library(realmc SHARED
        ${swig_SRC}
        ${jni_SRC}
        )

target_link_libraries(realmc log android RealmFFIStatic Realm::ObjectStore)
