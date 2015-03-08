/* Copyright (c) 2008-2014, Avian Contributors

   Permission to use, copy, modify, and/or distribute this software
   for any purpose with or without fee is hereby granted, provided
   that the above copyright notice and this permission notice appear
   in all copies.

   There is NO WARRANTY for this software.  See license.txt for
   details. */

#ifndef JNI_MD_H
#define JNI_MD_H

//mymod
//#include "stdint.h"
#include <cph/cph.h>

#if (defined __MINGW32__) || (defined _MSC_VER)
#define JNIEXPORT __declspec(dllexport)
#define JNICALL __stdcall
#else  // not (defined __MINGW32__) || (defined _MSC_VER)
#define JNIEXPORT __attribute__((visibility("default"))) __attribute__((used))
#define JNICALL
#endif  // not (defined __MINGW32__) || (defined _MSC_VER)

#define JNIIMPORT

//mymod
//typedef int32_t jint;
//typedef int64_t jlong;
//typedef int8_t jbyte;
typedef signed char jbyte;
#if _cph_cc_msc
  typedef signed   __int32 jint;
  typedef signed   __int64 jlong;
#elif _cph_cc_clang || _cph_cc_gnu
  #if _cph_arch_32
    typedef signed int         jint;
    typedef signed long long   jlong;
  #elif _cph_arch_64
    typedef signed int         jint;
    typedef signed long int    jlong;
  #endif
#endif

#endif  // JNI_MD_H
