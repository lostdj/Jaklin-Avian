#ifndef _myavn_h_ee1
#define _myavn_h_ee1

#include <avian/machine.h>
#include <avian/util/list.h>

namespace local
{
  using namespace vm;
  using namespace avian::system;

  const unsigned FrameBaseOffset = 0;

  class Thread : public vm::Thread {
   public:
    Thread(Machine* m, GcThread* javaThread, vm::Thread* parent)
        : vm::Thread(m, javaThread, parent),
          ip(0),
          sp(0),
          frame(-1),
          code(0),
          stackPointers(0)
    {
    }

    unsigned ip;
    unsigned sp;
    int frame;
    GcCode* code;
    List<unsigned>* stackPointers;
    uintptr_t stack[0];
  };
}

#endif // _myavn_h_ee1

