TEMPLATE = app
CONFIG += console
CONFIG -= app_bundle
CONFIG -= qt


INCLUDEPATH += ./
INCLUDEPATH += ../../cph/common_portable_headers/native/inc
INCLUDEPATH += ../src/openjdk
INCLUDEPATH += ../../myjdktop/myjdk/src/java.base/share/native/include
INCLUDEPATH += ../include
INCLUDEPATH += ../myinc
INCLUDEPATH += ../src
INCLUDEPATH += ../out/tux-x64-debug/build/gen
INCLUDEPATH += ../out/tux-x64-debug/build/
INCLUDEPATH += ../out/tux-x64-debug/result
INCLUDEPATH += /nix/store/nln65n4i0xzll30nxpcawi8d26hbi7xa-mesa-10.2.6/include
INCLUDEPATH += /nix/store/zksbsbcnw5v0bk8y9qyci3y951h9xcvz-SDL-1.2.15/include/


DEFINES += \
AVIAN_VERSION=\"31337\" \
AVIAN_INFO=\"\" \
AVIAN_JAVA_HOME=\"\" \
AVIAN_EMBED_PREFIX=\"\" \
AVIAN_PROCESS_interpret \
AVIAN_CLASSPATH=\"[classpathJar]\" \
BOOT_CLASSPATH=\"[classpathJar]\" \
AVIAN_TARGET_FORMAT=AVIAN_FORMAT_ELF \
_JNI_IMPLEMENTATION_ \
__STDC_LIMIT_MACROS \
\
AVIAN_TARGET_ARCH=AVIAN_ARCH_X86_64 \
USE_ATOMIC_OPERATIONS \
POINTER_SIZE=8 \
TARGET_BYTES_PER_WORD=8 \
AVIAN_OPENJDK_SRC \


include(deployment.pri)
qtcAddDeployment()


SOURCES += \
		../src/tools/type-generator/main.cpp \
		../out/tux-x64-debug/build/gen/type-constructors.cpp \
		../out/tux-x64-debug/build/gen/type-declarations.cpp \
		../out/tux-x64-debug/build/gen/type-enums.cpp \
		../out/tux-x64-debug/build/gen/type-initializations.cpp \
		../out/tux-x64-debug/build/gen/type-java-initializations.cpp \
		../out/tux-x64-debug/build/gen/type-maps.cpp \
		../out/tux-x64-debug/build/gen/type-name-initializations.cpp \
		\
		../src/system/posix.cpp \
		../src/system/posix/crash.cpp \
		../src/system/posix/memory.cpp \
		../src/system/posix/signal.cpp \
		\
#    ../src/system/windows.cpp \
#    ../src/system/windows/crash.cpp \
#    ../src/system/windows/memory.cpp \
#    ../src/system/windows/signal.cpp \
		\
		../src/boot-javahome.cpp \
		../src/boot.cpp \
		../src/bootimage-fields.cpp \
		../src/bootimage-template.cpp \
		../src/debug-util.cpp \
		\
		../src/finder.cpp \
		../src/machine.cpp \
		../src/util.cpp \
		../src/heap/heap.cpp \
		../src/interpret.cpp \
		../src/jnienv.cpp \
		../src/process.cpp \
		\
		../src/myavn/jni-args.cpp \
		../myinc/myavn/jni-meth.inc.cpp \
		\
		../src/builtin.cpp \
		../src/classpath-openjdk.cpp \
		../src/openjdk/stubs.cpp \
		../src/openjdk/my_java_props_macosx.c \
		../src/openjdk/my_management.c \
		../src/openjdk/my_net_util.c \
		\
		../src/main.cpp \
		\
		../mytest/stupid/mytstupid.cpp \
		../mytest/jni/jni.cpp \
		../mytest/jni/main.cpp \
		../mytest/jbox2d/src/main/native/jbox2d.cpp \
		../mytest/jbox2d/src/main/native/gl.jni.cpp \
    ../src/myavn/jdkjar.cpp \
    ../mytest/q2/jake2/src/native/jbox2d.cpp


HEADERS += \
		../classpath/jni-util.h \
		../classpath/sockets.h \
		../include/avian/heap/heap.h \
		../include/avian/system/memory.h \
		../include/avian/system/signal.h \
		../include/avian/system/system.h \
		../include/avian/util/abort.h \
		../include/avian/util/allocator.h \
		../include/avian/util/arg-parser.h \
		../include/avian/util/assert.h \
		../include/avian/util/cpp.h \
		../include/avian/util/fixed-allocator.h \
		../include/avian/util/hash.h \
		../include/avian/util/list.h \
		../include/avian/util/math.h \
		../include/avian/util/runtime-array.h \
		../include/avian/util/slice.h \
		../include/avian/util/stream.h \
		../include/avian/util/string.h \
		../include/avian/util/tokenizer.h \
		../src/avian/alloc-vector.h \
		../src/avian/append.h \
		../src/avian/arch.h \
		../src/avian/arm.h \
		../src/avian/bootimage.h \
		../src/avian/classpath-common.h \
		../src/avian/common.h \
		../src/avian/constants.h \
		../src/avian/embed.h \
		../src/avian/ems.h \
		../src/avian/environment.h \
		../src/avian/finder.h \
		../src/avian/heapwalk.h \
		../src/avian/java-common.h \
		../src/avian/jnienv.h \
		../src/avian/lzma-util.h \
		../src/avian/lzma.h \
		../src/avian/machine.h \
		../src/avian/process.h \
		../src/avian/processor.h \
		../src/avian/target-fields.h \
		../src/avian/target.h \
		../src/avian/types.h \
		../src/avian/util.h \
		../src/avian/x86.h \
		../src/avian/zlib-custom.h \
		../src/avian/zone.h \
		../src/debug-util.h \
		\
		../src/openjdk/caseSensitive/Wincon.h \
		../src/openjdk/caseSensitive/WS2tcpip.h \
		../src/openjdk/jni_md.h \
		\
		../myinc/myavn/embeddedres.h \
		../myinc/myavn/jni-args.h \
		../myinc/myavn/jni-meth.h \
		../myinc/myavn/myavn.h \
		../myinc/myavn/ee1.h \

