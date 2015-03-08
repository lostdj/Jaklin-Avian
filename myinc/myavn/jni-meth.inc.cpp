#ifndef _myavn_cpp_jnimeth
#define _myavn_cpp_jnimeth

#include <cph/java.h>

#include <myavn/jni-meth.h>

namespace myavn
{
  using namespace cph;

  namespace meth{namespace{

  template<typename R, typename T>
  inline
  R cast(T t)
  {
    return scast<R>(t);
  }

  template<typename R, typename T>
  inline
  R cast(T* t)
  {
    return rcast<R>(t);
  }

  inline u4 peek32(local::Thread* t, unsigned index)
  {
    #ifdef _myavn_log_eedebugstack
      fprintf(
        stderr, "peek int %" ULD " at %d\n", t->stack[(index * 2) + 1], index);
    #endif

    #ifdef _myavn_assertstack
      assertT(t, index < vm::stackSizeInWords(t) / 2);
      assertT(t, t->stack[index * 2] == vm::IntTag);
    #endif

    return t->stack[(index * 2) + 1];
  }

  inline u8 peek64(local::Thread* t, unsigned index)
  {
    #ifdef _myavn_log_eedebugstack
      fprintf(stderr,
              "peek long %" LLD " at %d\n",
              (static_cast<uint64_t>(t->stack[(index * 2) + 1]) << 32)
              | static_cast<uint64_t>(t->stack[((index + 1) * 2) + 1]),
              index);
    #endif

    return (static_cast<uint64_t>(t->stack[(index * 2) + 1]) << 32)
           | static_cast<uint64_t>(t->stack[((index+1) * 2) + 1]);
  }

  inline unsigned frameBase(local::Thread* t, int frame)
  {
    return peek32(t, frame + local::FrameBaseOffset);
  }

  inline jni::jobject peekObject(local::Thread* t, unsigned index)
  {
    #ifdef _myavn_log_eedebugstack
      fprintf(stderr,
              "peek object %p at %d\n",
              reinterpret_cast<vm::object>(t->stack[(index * 2) + 1]),
              index);
    #endif

    #ifdef _myavn_assertstack
      assertT(t, index < vm::stackSizeInWords(t) / 2);
      assertT(t, t->stack[index * 2] == vm::ObjectTag);
    #endif

    vm::object* v = reinterpret_cast<vm::object*>(t->stack + (index * 2) + 1);
    if(unlikely(!*v))
      v = null;

    return cph::rcast<jni::jobject>(v);
  }

  inline jni::jboolean peekjboolean(local::Thread* t, unsigned index)
  {
    return cph::scast<jni::jboolean>(peek32(t, index));
  }

  inline jni::jbyte peekjbyte(local::Thread* t, unsigned index)
  {
    return cph::scast<jni::jbyte>(peek32(t, index));
  }

  inline jni::jchar peekjchar(local::Thread* t, unsigned index)
  {
    return cph::scast<jni::jchar>(peek32(t, index));
  }

  inline jni::jshort peekjshort(local::Thread* t, unsigned index)
  {
    return cph::scast<jni::jshort>(peek32(t, index));
  }

  inline jni::jint peekjint(local::Thread* t, unsigned index)
  {
    return cph::scast<jni::jint>(peek32(t, index));
  }

  inline jni::jlong peekjlong(local::Thread* t, unsigned index)
  {
    return cph::scast<jni::jlong>(peek64(t, index));
  }

  inline jni::jfloat peekjfloat(local::Thread* t, unsigned index)
  {
    return cph::scast<jni::jfloat>(vm::bitsToFloat(peek32(t, index)));
  }

  inline jni::jdouble peekjdouble(local::Thread* t, unsigned index)
  {
    return cph::scast<jni::jdouble>(vm::bitsToDouble(peek64(t, index)));
  }

  inline jni::jobject peekjobject(local::Thread* t, unsigned index)
  {
    return cph::rcast<jni::jobject>(peekObject(t, index));
  }

  inline jni::jclass peekjclass(local::Thread* t, unsigned index)
  {
    return cph::rcast<jni::jclass>(peekObject(t, index));
  }

  inline jni::jstring peekjstring(local::Thread* t, unsigned index)
  {
    return cph::rcast<jni::jstring>(peekObject(t, index));
  }

  inline jni::jthrowable peekjthrowable(local::Thread* t, unsigned index)
  {
    return cph::rcast<jni::jthrowable>(peekObject(t, index));
  }

  inline jni::jbooleanArray peekjbooleanArray(local::Thread* t, unsigned index)
  {
    return cph::rcast<jni::jbooleanArray>(peekObject(t, index));
  }

  inline jni::jbyteArray peekjbyteArray(local::Thread* t, unsigned index)
  {
    return cph::rcast<jni::jbyteArray>(peekObject(t, index));
  }

  inline jni::jcharArray peekjcharArray(local::Thread* t, unsigned index)
  {
    return cph::rcast<jni::jcharArray>(peekObject(t, index));
  }

  inline jni::jshortArray peekjshortArray(local::Thread* t, unsigned index)
  {
    return cph::rcast<jni::jshortArray>(peekObject(t, index));
  }

  inline jni::jintArray peekjintArray(local::Thread* t, unsigned index)
  {
    return cph::rcast<jni::jintArray>(peekObject(t, index));
  }

  inline jni::jlongArray peekjlongArray(local::Thread* t, unsigned index)
  {
    return cph::rcast<jni::jlongArray>(peekObject(t, index));
  }

  inline jni::jfloatArray peekjfloatArray(local::Thread* t, unsigned index)
  {
    return cph::rcast<jni::jfloatArray>(peekObject(t, index));
  }

  inline jni::jdoubleArray peekjdoubleArray(local::Thread* t, unsigned index)
  {
    return cph::rcast<jni::jdoubleArray>(peekObject(t, index));
  }

  inline jni::jobjectArray peekjobjectArray(local::Thread* t, unsigned index)
  {
    return cph::rcast<jni::jobjectArray>(peekObject(t, index));
  }

  }}
} // namespace myavn

#endif // _myavn_cpp_jnimeth

