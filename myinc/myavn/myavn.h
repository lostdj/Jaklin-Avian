#ifndef _myavn_h_myavn
#define _myavn_h_myavn

#include <cph/cph.h>

namespace myavn
{
  #define SLL LLD
  #define ULL ULD

  #if _cph_os_ems && !defined(_myavn_unwindex)
    #define _myavn_unwindex
  #endif

  #ifdef _myavn_unwindex
    #undef _myavn_unwindex
    #define _myavn_unwindex (cph::uw)0xE1E37
  #endif

//  #if defined(_myavn_mt) && _myavn_mt
//    static bool const mt = true;
//  #else
//    static bool const mt = false;
//  #endif
} // namespace myavn

#endif // _myavn_h_myavn

