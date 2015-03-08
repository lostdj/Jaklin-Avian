#include <stdlib.h>
#include <stdio.h>
#include <string.h>

#include <avian/machine.h>

#include <myavn/embeddedres.h>
#include <myavn/jni-args.h>

#include <myjdkjni.gen.inc.cpp>
extern void *myjdkjarres;

namespace myt_stupid_arr
{
  #include <myt_stupid/assignableprimitive/mymain.h>
  #include <myt_stupid/benchadouble/mymain.h>
  #include <myt_stupid/benchafloat/mymain.h>
  #include <myt_stupid/benchaint/mymain.h>
  #include <myt_stupid/benchalong/mymain.h>
  #include <myt_stupid/benchloop10k/mymain.h>
  #include <myt_stupid/lambda/mymain.h>
  #include <myt_stupid/invoke1/mymain.h>
  #include <myt_stupid/exvtbl/mymain.h>
}
myavn::embeddedres assignableprimitive(sizeof(myt_stupid_arr::assignableprimitivearr), myt_stupid_arr::assignableprimitivearr);
myavn::embeddedres benchadouble(sizeof(myt_stupid_arr::benchadoublearr), myt_stupid_arr::benchadoublearr);
myavn::embeddedres benchafloat(sizeof(myt_stupid_arr::benchafloatarr), myt_stupid_arr::benchafloatarr);
myavn::embeddedres benchaint(sizeof(myt_stupid_arr::benchaintarr), myt_stupid_arr::benchaintarr);
myavn::embeddedres benchalong(sizeof(myt_stupid_arr::benchalongarr), myt_stupid_arr::benchalongarr);
myavn::embeddedres benchloop10k(sizeof(myt_stupid_arr::benchloop10karr), myt_stupid_arr::benchloop10karr);
myavn::embeddedres lambda(sizeof(myt_stupid_arr::lambdaarr), myt_stupid_arr::lambdaarr);
myavn::embeddedres invoke1(sizeof(myt_stupid_arr::invoke1arr), myt_stupid_arr::invoke1arr);
myavn::embeddedres exvtbl(sizeof(myt_stupid_arr::exvtblarr), myt_stupid_arr::exvtblarr);

#include <cph/java.h>
using namespace cph;

extern "C" int64_t currentTimeMillis(vm::Thread* t, vm::object, uintptr_t*)
{
  return t->m->system->now();
}

int main(int /*ac*/, const char** /*av*/)
{
  jni::JavaVMInitArgs vmArgs;
  vmArgs.version = JNI_VERSION_1_2;
  vmArgs.nOptions = 1;
  vmArgs.ignoreUnrecognized = JNI_TRUE;

  const char* class_ = 0;
  int argc = 0;

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
      .optionString = reinterpret_cast<char*>(&myavn::jniarg::appcp),

      .extraInfo = reinterpret_cast<void*>(&assignableprimitive),
      // .extraInfo = reinterpret_cast<void*>(&benchadouble),
//      .extraInfo = reinterpret_cast<void*>(&benchafloat),
//      .extraInfo = reinterpret_cast<void*>(&benchaint),
//      .extraInfo = reinterpret_cast<void*>(&benchalong),
//      .extraInfo = reinterpret_cast<void*>(&benchloop10k),
      // .extraInfo = reinterpret_cast<void*>(&lambda),
      // .extraInfo = reinterpret_cast<void*>(&invoke1),
			// .extraInfo = reinterpret_cast<void*>(&exvtbl),
    },
  };
  vmArgs.options = opts;
  vmArgs.nOptions = sizeof(opts) / sizeof(jni::JavaVMOption);

  class_ = "mymain";

  jni::JavaVM* vm;
  void* env;
  jni::JNI_CreateJavaVM(&vm, &env, &vmArgs);
  jni::JNIEnv* e = static_cast<jni::JNIEnv*>(env);

//  if(!e->ExceptionCheck())
//  {
//    jni::jclass java_lang_System = 0;
//    java_lang_System = e->FindClass("java/lang/System");

//    e->SetStaticLongField(
//      java_lang_System,
//      e->GetStaticFieldID(java_lang_System, "NanoTimeBaseInMillis", "J"),
//      currentTimeMillis(cph::rcast<vm::Thread*>(e), 0, 0));
//  }

  jni::jclass c = 0;
  if(!e->ExceptionCheck())
    c = e->FindClass(class_);

  if(!e->ExceptionCheck())
  {
    jni::jmethodID m = e->GetStaticMethodID(c, "main", "([Ljava/lang/String;)V");
    if(!e->ExceptionCheck())
    {
      jni::jclass stringClass = e->FindClass("java/lang/String");
      if(!e->ExceptionCheck())
      {
        jni::jobjectArray a = e->NewObjectArray(argc, stringClass, 0);
        if(!e->ExceptionCheck())
        {
          for(int i = 0; i < argc; ++i)
          {
            e->SetObjectArrayElement(a, i, e->NewStringUTF("the arg"));
//            e->SetObjectArrayElement(a, i, e->NewStringUTF(argv[i]));
          }

          e->CallStaticVoidMethod(c, m, a);
        }
      }
    }
  }

  int exitCode = 0;
  if(e->ExceptionCheck())
  {
    exitCode = -1;
    e->ExceptionDescribe();
  }

  vm->DestroyJavaVM();

  return exitCode;
}

