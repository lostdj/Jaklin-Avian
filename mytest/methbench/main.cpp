#include <stdlib.h>
#include <stdio.h>
#include <string.h>

#include <myavn/embeddedres.h>
#include <myavn/jni-args.h>

#include <cph/cph.h>
#include <cph/java.h>
using namespace cph;

#include <myjdkjni.gen.inc.cpp>
extern void *myjdkjarres;

#include <myt_methbench/JNI.gen.inc.cpp>
namespace jbox2d_gen
{
  #include <myt_methbench/JNI.gen.h>
}
myavn::embeddedres JNI(sizeof(jbox2d_gen::JNIarr), jbox2d_gen::JNIarr);

jni::JavaVM* _jvm;
jni::JNIEnv* _env;

const char* class_ = "JNI";
jni::jclass c;
jni::jmethodID m;

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
      .extraInfo = reinterpret_cast<void*>(myavn::meth::JNI::methlib),
    },
    {
      .optionString = reinterpret_cast<char*>(&myavn::jniarg::appcp),
      .extraInfo = reinterpret_cast<void*>(&JNI),
    },
  };
  vmArgs.options = opts;
  vmArgs.nOptions = sizeof(opts) / sizeof(jni::JavaVMOption);

  void* env;
  jni::JNI_CreateJavaVM(&_jvm, &env, &vmArgs);
  _env = static_cast<jni::JNIEnv*>(env);

  if(!_env->ExceptionCheck())
    c = _env->FindClass(class_);

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
}


