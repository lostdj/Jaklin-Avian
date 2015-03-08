#ifndef _myavn_h_jnimeth
#define _myavn_h_jnimeth

#include <avian/machine.h>
#include <myavn/ee1.h>

namespace myavn
{
  using namespace cph;

  namespace meth
  {
    typedef u8(*SlowFunc)(local::Thread*, uw);
    typedef void(*SlowVoidFunc)(local::Thread*, uw);
    typedef u8(*SlowMeth)(local::Thread*);
    typedef void(*SlowVoidMeth)(local::Thread*);

    struct MethTableEntry
    {
      s4 h1;
      s4 h2;
      void* f;
    };

    struct MethTable
    {
      u4 size;
      MethTableEntry *data;
    };

    class MethLibrary : public vm::System::Library
    {
    public:
      MethLibrary(MethTable tbl)
        : tbl(tbl), next_(0)
      {
        ;
      }

      s4 hash1(char const* s)
      {
        s4 h = 0;
        while(*s)
          h = 31 * h + *s++;

        return h;
      }

      s4 hash2(char const* s)
      {
        size_t l = strlen(s);
        char c[5] = {s[6], s[l-3],s[l-2],s[l-1], '\0'};

        return hash1(c);
      }

      virtual void* resolve(const char* f)
      {
        if(!f || !*f
            || (f[0] != 'A' && f[0] != 'J')
            || (f[1] != 'v' && f[1] != 'a'))
          return 0;

        s4 h1 = hash1(f);
        s4 h2 = hash2(f);
        //TODO:
        u4 idx = 0;//scast<u4>(h1 & (tbl.size - 1));
        for(MethTableEntry* e=&tbl.data[0]; idx < tbl.size; e=&tbl.data[++idx])
          if(e->h1 == h1 && e->h2 == h2)
            return e->f;

        return 0;
      }

      virtual const char* name()
      {
        return 0;
      }

      virtual vm::System::Library* next()
      {
        return next_;
      }

      virtual void setNext(vm::System::Library* lib)
      {
        next_ = lib;
      }

      virtual void disposeAll()
      {
        ;
      }

      MethTable tbl;
      vm::System::Library* next_;
    };
  }
} // namespace myavn

#endif // _myavn_h_jnimeth

