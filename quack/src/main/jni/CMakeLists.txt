cmake_minimum_required(VERSION 3.4.1)

file(GLOB common_SRC
    "*.h"
    "*.cpp"
)
file(GLOB_RECURSE duktape_SRC
    "duktape-jni/*.h"
    "duktape-jni/*.c"
    "duktape-jni/*.cpp"

    "duktape/*.h"
    "duktape/*.c"
    "duktape/*.cpp"
)

add_definitions(-DCONFIG_VERSION="2019-10-27")
add_definitions(-DCONFIG_DISABLE_STACK_CHECK)
add_definitions(-DCONFIG_DISABLE_WORKER)

file(GLOB quickjs_SRC
    "quickjs-jni/*.h"
    "quickjs-jni/*.c"
    "quickjs-jni/*.cpp"
    "../../../libs/quickjs/*.h"
    "../../../libs/quickjs/quickjs.c"
    "../../../libs/quickjs/libbf.c"
    "../../../libs/quickjs/quickjs-libc.c"
    "../../../libs/quickjs/quickjs-debugger.c"
    "../../../libs/quickjs/quickjs-debugger-transport-unix.c"
    "../../../libs/quickjs/libunicode.c"
    "../../../libs/quickjs/libregexp.c"
    "../../../libs/quickjs/cutils.c"
)

add_library(quack SHARED ${common_SRC} ${quickjs_SRC} ${duktape_SRC})

target_link_libraries(quack)
