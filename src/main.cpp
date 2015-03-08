#include "stdlib.h"
#include "stdio.h"
#include "string.h"
#include "jni.h"

#include <avian/machine.h>
#include <avian/util/runtime-array.h>

#include <myavn/jni-args.h>
#include <myavn/embeddedres.h>

#if (defined __MINGW32__) || (defined _MSC_VER)
  #define PATH_SEPARATOR ';'
#else
  #define PATH_SEPARATOR ':'
#endif

#ifdef _MSC_VER
  #define not!
  #define or ||
  #define and &&
  #define xor ^
#endif  // not _MSC_VER

namespace {

// const char* mainClass(const char* jar)
// {
//   using namespace vm;

//   System* system = makeSystem();

//   class MyAllocator : public avian::util::Alloc {
//    public:
//     MyAllocator(System* s) : s(s)
//     {
//     }

//     virtual void* allocate(size_t size)
//     {
//       void* p = s->tryAllocate(size);
//       if (p == 0) {
//         abort(s);
//       }
//       return p;
//     }

//     virtual void free(const void* p, size_t)
//     {
//       s->free(p);
//     }

//     System* s;
//   } allocator(system);

//   Finder* finder = makeFinder(system, &allocator, jar, 0);

//   char* result = 0;

//   System::Region* region = finder->find("META-INF/MANIFEST.MF");
//   if (region) {
//     unsigned start = 0;
//     unsigned length;
//     while (readLine(region->start(), region->length(), &start, &length)) {
//       const unsigned PrefixLength = 12;
//       if (strncasecmp("Main-Class: ",
//                       reinterpret_cast<const char*>(region->start() + start),
//                       PrefixLength) == 0) {
//         result = static_cast<char*>(malloc(length + 1 - PrefixLength));
//         memcpy(result,
//                region->start() + start + PrefixLength,
//                length - PrefixLength);
//         result[length - PrefixLength] = 0;
//         break;
//       }
//       start += length;
//     }

//     region->dispose();
//   }

//   finder->dispose();

//   system->dispose();

//   return result;
// }

// void usageAndExit(const char* name)
// {
//   fprintf(
//       stderr,
//       "usage: %s\n"
//       "\t[{-cp|-classpath} <classpath>]\n"
//       "\t[-Xmx<maximum heap size>]\n"
//       "\t[-Xss<maximum stack size>]\n"
//       "\t[-Xbootclasspath/p:<classpath to prepend to bootstrap classpath>]\n"
//       "\t[-Xbootclasspath:<bootstrap classpath>]\n"
//       "\t[-Xbootclasspath/a:<classpath to append to bootstrap classpath>]\n"
//       "\t[-D<property name>=<property value> ...]\n"
//       "\t{<class name>|-jar <app jar>} [<argument> ...]\n",
//       name);
//   exit(-1);
// }

}  // namespace

#include "avian/java-common.h"
#include "avian/machine.h"

embeddedres cp(sizeof(cparr), cparr);
embeddedres hello(sizeof(helloarr), helloarr);
embeddedres simpletest(sizeof(simpletestarr), simpletestarr);
embeddedres benchloop10k(sizeof(benchloop10karr), benchloop10karr);
embeddedres benchaint(sizeof(benchaintarr), benchaintarr);
embeddedres benchafloat(sizeof(benchafloatarr), benchafloatarr);
embeddedres benchadouble(sizeof(benchadoublearr), benchadoublearr);
embeddedres benchalong(sizeof(benchalongarr), benchalongarr);

extern "C" int64_t currentTimeMillis(vm::Thread* t, vm::object, uintptr_t*)
{
  return t->m->system->now();
}

//extern "C" AVIAN_EXPORT void JNICALL
//    Avian_java_lang_System_arraycopy(vm::Thread*, vm::object, uintptr_t*);

//extern "C" JNIEXPORT jobjectArray JNICALL
//    Java_java_lang_System_getNativeProperties(JNIEnv*, jclass);
//uint64_t getNativeProperties(vm::Thread* t, vm::object, uintptr_t*)
//{
//  return cph::rcast<uint64_t>(
//    *cph::rcast<vm::object*>(myavn::meth::cp::Java_java_lang_System_getNativeProperties(
//      cph::rcast<cph::jni::JNIEnv*>(t)
//      ,0)));
//}

//extern "C" AVIAN_EXPORT int64_t JNICALL
//    Avian_avian_Classes_primitiveClass(vm::Thread*, vm::object, uintptr_t*);

//extern "C" AVIAN_EXPORT int64_t JNICALL
//    Avian_avian_SystemClassLoader_getClass(vm::Thread*,
//                                           vm::object,
//                                           uintptr_t*);

//extern "C" void myprintln(vm::Thread* t, vm::object, uintptr_t *args)
//{
//  vm::GcString *_s = vm::cast<vm::GcString>(t, cph::rcast<vm::object>(args[0]));
//  THREAD_RUNTIME_ARRAY(t, char, s, _s->length(t) + 2);
//  vm::stringChars(t, _s, RUNTIME_ARRAY_BODY(s));
//  printf("%s\n", RUNTIME_ARRAY_BODY(s));
//}

int main(int /*ac*/, const char** /*av*/)
{
  JavaVMInitArgs vmArgs;
  vmArgs.version = JNI_VERSION_1_2;
  vmArgs.nOptions = 1;
  vmArgs.ignoreUnrecognized = JNI_TRUE;

  const char* class_ = 0;
  const char* jar = 0;
  int argc = 0;
//  const char** argv = 0;
//  const char* classpath = ".";

//  for (int i = 1; i < ac; ++i) {
//    if (strcmp(av[i], "-cp") == 0 or strcmp(av[i], "-classpath") == 0) {
//      if (i + 1 == ac)
//        usageAndExit(av[0]);
//      classpath = av[++i];
//    } else if (strcmp(av[i], "-jar") == 0) {
//      if (i + 1 == ac)
//        usageAndExit(av[0]);
//      jar = av[++i];
//    } else if (strncmp(av[i], "-X", 2) == 0 or strncmp(av[i], "-D", 2) == 0) {
//      ++vmArgs.nOptions;
//    } else if (strcmp(av[i], "-client") == 0 or strcmp(av[i], "-server") == 0) {
//      // ignore
//    } else if (strcmp(av[i], "-version") == 0) {
//      fprintf(stderr, "Avian " AVIAN_VERSION "\n");
//      exit(0);
//    } else {
//      if (jar == 0) {
//        class_ = av[i++];
//      }
//      if (i < ac) {
//        argc = ac - i;
//        argv = av + i;
//        i = ac;
//      }
//    }
//  }

//  if (jar) {
//    classpath = jar;

//    class_ = mainClass(jar);

//    if (class_ == 0) {
//      fprintf(stderr, "Main-Class manifest header not found in %s\n", jar);
//      exit(-1);
//    }
//  }

//#ifdef BOOT_LIBRARY
//  ++vmArgs.nOptions;
//#endif

//#ifdef BOOT_IMAGE
//  vmArgs.nOptions += 2;
//#endif

//#ifdef BOOT_BUILTINS
//  ++vmArgs.nOptions;
//#endif

//  RUNTIME_ARRAY(JavaVMOption, options, vmArgs.nOptions);
//  vmArgs.options = RUNTIME_ARRAY_BODY(options);

//  unsigned optionIndex = 0;

//#ifdef BOOT_IMAGE
//  vmArgs.options[optionIndex++].optionString
//      = const_cast<char*>("-Davian.bootimage=bootimageBin");

//  vmArgs.options[optionIndex++].optionString
//      = const_cast<char*>("-Davian.codeimage=codeimageBin");
//#endif

//#ifdef BOOT_LIBRARY
//  vmArgs.options[optionIndex++].optionString
//      = const_cast<char*>("-Davian.bootstrap=" BOOT_LIBRARY);
//#endif

//#ifdef BOOT_BUILTINS
//  vmArgs.options[optionIndex++].optionString
//      = const_cast<char*>("-Davian.builtins=" BOOT_BUILTINS);
//#endif

//#define CLASSPATH_PROPERTY "-Djava.class.path="

//  unsigned classpathSize = strlen(classpath);
//  unsigned classpathPropertyBufferSize = sizeof(CLASSPATH_PROPERTY)
//                                         + classpathSize;

//  RUNTIME_ARRAY(char, classpathPropertyBuffer, classpathPropertyBufferSize);
//  memcpy(RUNTIME_ARRAY_BODY(classpathPropertyBuffer),
//         CLASSPATH_PROPERTY,
//         sizeof(CLASSPATH_PROPERTY) - 1);
//  memcpy(RUNTIME_ARRAY_BODY(classpathPropertyBuffer)
//         + sizeof(CLASSPATH_PROPERTY) - 1,
//         classpath,
//         classpathSize + 1);

//  vmArgs.options[optionIndex++].optionString
//      = RUNTIME_ARRAY_BODY(classpathPropertyBuffer);

//  for (int i = 1; i < ac; ++i) {
//    if (strncmp(av[i], "-X", 2) == 0 or strncmp(av[i], "-D", 2) == 0) {
//      vmArgs.options[optionIndex++].optionString = const_cast<char*>(av[i]);
//    }
//  }

  JavaVMOption opts[] =
  {
    {
      .optionString = reinterpret_cast<char*>(&myavn::jniarg::meths),
      .extraInfo = reinterpret_cast<void*>(&myavn::meth::cp::methlib),
    },
    {
      .optionString = reinterpret_cast<char*>(&myavn::jniarg::bootcp),
      .extraInfo = reinterpret_cast<void*>(&myavn::cp),
    },
    {
      .optionString = reinterpret_cast<char*>(&myavn::jniarg::appcp),
// 			.extraInfo = reinterpret_cast<void*>(&myavn::hello),
// 			.extraInfo = reinterpret_cast<void*>(&myavn::simpletest),
// 			.extraInfo = reinterpret_cast<void*>(&myavn::benchloop10k),
      .extraInfo = reinterpret_cast<void*>(&myavn::benchadouble),
    },
  };
  vmArgs.options = opts;
  vmArgs.nOptions = sizeof(opts) / sizeof(JavaVMOption);

// 	class_ = "Hello";
// 	class_ = "simpletest";
  class_ = "mymain";

// 	if (class_ == 0) {
// 		usageAndExit(av[0]);
// 	}

  JavaVM* vm;
  void* env;
  JNI_CreateJavaVM(&vm, &env, &vmArgs);
  JNIEnv* e = static_cast<JNIEnv*>(env);

  jclass java_lang_System = 0;
  if (not e->ExceptionCheck())
  {
    java_lang_System = e->FindClass("java/lang/System");

    e->SetStaticLongField(
      java_lang_System,
      e->GetStaticFieldID(java_lang_System, "NanoTimeBaseInMillis", "J"),
      currentTimeMillis(cph::rcast<vm::Thread*>(e), 0, 0));
// 		long NanoTimeBaseInMillis = e->GetStaticLongField(
// 			java_lang_System,
// 			e->GetStaticFieldID(java_lang_System, "NanoTimeBaseInMillis", "J"));
// 		printf("%" ULL "\n", NanoTimeBaseInMillis);

//    JNINativeMethod _funcs[] =
//    {
//      {
//        .name=(char*)"currentTimeMillis",
//        .signature=(char*)"()J",
//        .fnPtr=(void*)&currentTimeMillis,
//      },
//      {
//        .name=(char*)"getNativeProperties",
//        .signature=(char*)"()[Ljava/lang/String;",
//        .fnPtr=(void*)&getNativeProperties,
//      },
//      {
//        .name=(char*)"arraycopy",
//        .signature=(char*)"(Ljava/lang/Object;ILjava/lang/Object;II)V",
//        .fnPtr=(void*)&myavn::meth::cp::Avian_java_lang_System_arraycopy,
//      },
//    };
//    if (not e->ExceptionCheck())
//      e->RegisterNatives(
//        java_lang_System,
//        _funcs,
//        (sizeof(_funcs) / sizeof(_funcs[0])));
  }

//  jclass avian_Classes = 0;
//  if (not e->ExceptionCheck())
//  {
//    avian_Classes = e->FindClass("avian/Classes");

//    JNINativeMethod _funcs[] =
//    {
//      {
//        .name=(char*)"primitiveClass",
//        .signature=(char*)"(C)Lavian/VMClass;",
//        .fnPtr=(void*)&myavn::meth::cp::Avian_avian_Classes_primitiveClass,
//      },
//    };
//    if (not e->ExceptionCheck())
//      e->RegisterNatives(
//        avian_Classes,
//        _funcs,
//        (sizeof(_funcs) / sizeof(_funcs[0])));
//  }

//  jclass avian_SystemClassLoader = 0;
//  if (not e->ExceptionCheck())
//  {
//    avian_SystemClassLoader = e->FindClass("avian/SystemClassLoader");

//    JNINativeMethod _funcs[] =
//    {
//      {
//        .name=(char*)"getClass",
//        .signature=(char*)"(Lavian/VMClass;)Ljava/lang/Class;",
//        .fnPtr=(void*)&myavn::meth::cp::Avian_avian_SystemClassLoader_getClass,
//      },
//    };
//    if (not e->ExceptionCheck())
//      e->RegisterNatives(
//        avian_SystemClassLoader,
//        _funcs,
//        (sizeof(_funcs) / sizeof(_funcs[0])));
//  }

//  jclass mymainclz = 0;
//  if (not e->ExceptionCheck())
//  {
//    mymainclz = e->FindClass("mymain");

//    JNINativeMethod _funcs[] =
//    {
//      {
//        .name=(char*)"myprintln",
//        .signature=(char*)"(Ljava/lang/String;)V",
//        .fnPtr=(void*)&myprintln
//      },
//    };
//    if (not e->ExceptionCheck())
//      e->RegisterNatives(
//        mymainclz,
//        _funcs,
//        (sizeof(_funcs) / sizeof(_funcs[0])));
//  }

  jclass c = 0;
  if (not e->ExceptionCheck()) {
    c = e->FindClass(class_);
  }

  if (jar) {
    free(const_cast<char*>(class_));
  }

  if (not e->ExceptionCheck()) {
    jmethodID m = e->GetStaticMethodID(c, "main", "([Ljava/lang/String;)V");
    if (not e->ExceptionCheck()) {
      jclass stringClass = e->FindClass("java/lang/String");
      if (not e->ExceptionCheck()) {
        jobjectArray a = e->NewObjectArray(argc, stringClass, 0);
        if (not e->ExceptionCheck()) {
          for (int i = 0; i < argc; ++i) {
            e->SetObjectArrayElement(a, i, e->NewStringUTF("the arg"));
//            e->SetObjectArrayElement(a, i, e->NewStringUTF(argv[i]));
          }

          e->CallStaticVoidMethod(c, m, a);
        }
      }
    }
  }

// 	int64_t fld = e->GetStaticLongField(
// 		mymainclz,
// 		e->GetStaticFieldID(mymainclz, "_t", "J"));
// 	printf("...%" SLL "\n", fld);

//   jclass simpletest = e->FindClass("simpletest");
// 	int fld = e->GetStaticIntField(
// 		simpletest,
// 		e->GetStaticFieldID(simpletest, "fld", "I"));
// 	printf("...%d\n", fld);

  int exitCode = 0;
  if (e->ExceptionCheck()) {
    exitCode = -1;
    e->ExceptionDescribe();
  }

  vm->DestroyJavaVM();

  return exitCode;
}
