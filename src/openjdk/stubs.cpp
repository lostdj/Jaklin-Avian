#include "avian/machine.h"

#include <cph/java.h>
#include <cph/debug_print_simple.h>

using namespace vm;

// extern "C" AVIAN_EXPORT jint JNICALL net_JNI_OnLoad(JavaVM*, void*)
// {
//   return 0;
// }

extern "C" AVIAN_EXPORT jint JNICALL management_JNI_OnLoad(JavaVM*, void*)
{
  return 0;
}

//mymod
// extern "C" char* findJavaTZ_md(const char*, const char*)
// {
//   return 0;
// }

