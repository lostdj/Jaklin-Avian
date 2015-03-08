#ifndef _myavn_h_embeddedres
#define _myavn_h_embeddedres

#include <cph/cph.h>

namespace myavn
{
  class embeddedres
  {
  public:
    cph::uw const size;
    void *data;

    embeddedres(cph::uw const size, void *data)
      : size(size), data(data) {}
  };
} // namespace myavn

#endif // _myavn_h_embeddedres

