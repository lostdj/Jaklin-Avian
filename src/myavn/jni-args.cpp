#include <myavn/embeddedres.h>
#include <myavn/jni-args.h>

namespace myavn
{
  //#define _jniarg(_a) jniarg jniarg::_a((void*)_cph_quoteval(_a))
  #define _jniarg(_a) jniarg jniarg::_a
  _jniarg(meths);
  _jniarg(bootcp);
  _jniarg(appcp);
} // namespace myavn

