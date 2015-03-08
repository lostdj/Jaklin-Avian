#include <stdlib.h>
#include <stdio.h>
#include <string.h>

//#include <avian/machine.h>

#include <myavn/embeddedres.h>
#include <myavn/jni-args.h>

#include <cph/cph.h>
#include <cph/java.h>
using namespace cph;

#if _cph_os_ems
  #include <emscripten/emscripten.h>
#endif

#include <myjdkjni.gen.inc.cpp>
extern void *myjdkjarres;

#include <jbox2d/gl.gen.inc.cpp>
namespace jbox2d_gen
{
  #include <jbox2d/jbox2djar.gen.h>
}
myavn::embeddedres jbox2djar(sizeof(jbox2d_gen::jbox2djararr), jbox2d_gen::jbox2djararr);

jni::JavaVM* _jvm;
jni::JNIEnv* _env;

const char* class_ = "myavn.test.jbox2d.Main";
jni::jclass c;
jni::jmethodID m;

void tick()
{
  _env->CallStaticVoidMethod(c, m);
}

int main(int /*ac*/, const char** /*av*/)
{
  jni::JavaVMInitArgs vmArgs;
  vmArgs.version = JNI_VERSION_1_2;
  vmArgs.nOptions = 1;
  vmArgs.ignoreUnrecognized = JNI_TRUE;

  jni::JavaVMOption opts[] =
  {
    {
      .optionString = reinterpret_cast<char*>(&myavn::jniarg::meths),
      .extraInfo = reinterpret_cast<void*>(myavn::meth::myjdkjni::methlib),
    },
    {
      .optionString = reinterpret_cast<char*>(&myavn::jniarg::bootcp),
      .extraInfo = reinterpret_cast<void*>(myjdkjarres),
    },

    {
      .optionString = reinterpret_cast<char*>(&myavn::jniarg::meths),
      .extraInfo = reinterpret_cast<void*>(myavn::meth::gl::methlib),
    },
    {
      .optionString = reinterpret_cast<char*>(&myavn::jniarg::appcp),
      .extraInfo = reinterpret_cast<void*>(&jbox2djar),
    },
  };
  vmArgs.options = opts;
  vmArgs.nOptions = sizeof(opts) / sizeof(jni::JavaVMOption);

  void* env;
  jni::JNI_CreateJavaVM(&_jvm, &env, &vmArgs);
  _env = static_cast<jni::JNIEnv*>(env);

  if(!_env->ExceptionCheck())
    c = _env->FindClass(class_);

  #if !_cph_os_ems
//    if(!_env->ExceptionCheck())
//      m = _env->GetStaticMethodID(c, "tick", "()V");
//    forever
//      tick();

    if(!_env->ExceptionCheck())
    {
      m = _env->GetStaticMethodID(c, "main", "([Ljava/lang/String;)V");
      if(!_env->ExceptionCheck())
      {
        jni::jclass stringClass = _env->FindClass("java/lang/String");
        if(!_env->ExceptionCheck())
        {
          jni::jobjectArray a = _env->NewObjectArray(/*argc*/0, stringClass, 0);
          if(!_env->ExceptionCheck())
          {
            for(int i = 0; i < /*argc*/0; ++i)
            {
              _env->SetObjectArrayElement(a, i, _env->NewStringUTF("the arg"));
  //            _env->SetObjectArrayElement(a, i, _env->NewStringUTF(argv[i]));
            }

            _env->CallStaticVoidMethod(c, m, a);
          }
        }
      }
    }

    int exitCode = 0;
    if(_env->ExceptionCheck())
    {
      exitCode = -1;
      _env->ExceptionDescribe();
    }

    _jvm->DestroyJavaVM();

    return exitCode;
  #else
    if(!_env->ExceptionCheck())
      m = _env->GetStaticMethodID(c, "tick", "()V");

    int exitCode = 0;
    if(_env->ExceptionCheck())
    {
      exitCode = -1;
      _env->ExceptionDescribe();

      _jvm->DestroyJavaVM();
    }
    else
      emscripten_set_main_loop(tick, 0, true);

    return exitCode;
  #endif
}

