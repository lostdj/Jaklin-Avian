#include <avian/machine.h>


#include <cph/cph.h>
#include <cph/debug_print_simple.h>
using namespace cph;


#include <cph/java.h>
//using namespace jni;


extern "C"
{


//
AVIAN_EXPORT void JNICALL Avian_JNI_dummy_1avian
  (vm::Thread*, vm::object, uintptr_t*)
{
  ;
}

JNIEXPORT void JNICALL Java_JNI_dummy_1jni
  (jni::JNIEnv *, jni::jclass)
{
  ;
}


//
AVIAN_EXPORT void JNICALL Avian_JNI_float_1avian
  (vm::Thread*, vm::object, uintptr_t*)
{
  ;
}

JNIEXPORT void JNICALL Java_JNI_float_1jni
  (jni::JNIEnv *, jni::jclass, jni::jfloat)
{
  ;
}


//
AVIAN_EXPORT void JNICALL Avian_JNI_double_1avian
  (vm::Thread*, vm::object, uintptr_t*)
{
  ;
}

JNIEXPORT void JNICALL Java_JNI_double_1jni
  (jni::JNIEnv *, jni::jclass, jni::jdouble)
{
  ;
}


//
AVIAN_EXPORT int64_t JNICALL Avian_JNI_floor_1avian
  (vm::Thread*, vm::object, uintptr_t* arguments)
{
  #if _cph_arch_64
    return vm::doubleToBits(floor(vm::bitsToDouble(arguments[0])));
  #elif _cph_arch_32
    return vm::doubleToBits(floor(vm::bitsToDouble((u8)arguments[0] + ((u8)arguments[1] << 32))));
  #endif
}

JNIEXPORT double JNICALL Java_JNI_floor_1jni
  (jni::JNIEnv *, jni::jclass, jni::jdouble d)
{
  return floor(d);
}


//
AVIAN_EXPORT int64_t JNICALL Avian_JNI_sin_1avian
  (vm::Thread*, vm::object, uintptr_t* arguments)
{
  //_cph_sprint("--%f\n", vm::bitsToDouble((u8)arguments[0] + ((u8)arguments[1] << 32)));
  #if _cph_arch_64
    return vm::doubleToBits(sin(vm::bitsToDouble(arguments[0])));
  #elif _cph_arch_32
    return vm::doubleToBits(sin(vm::bitsToDouble((u8)arguments[0] + ((u8)arguments[1] << 32))));
  #endif
}

JNIEXPORT double JNICALL Java_JNI_sin_1jni
  (jni::JNIEnv *, jni::jclass, jni::jdouble d)
{
  return sin(d);
}


}

