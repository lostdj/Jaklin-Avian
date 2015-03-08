#ifndef _myavn_h_jniargs
#define _myavn_h_jniargs

namespace myavn
{
  class jniarg
  {
  public:
    static jniarg meths;
    static jniarg bootcp;
    static jniarg appcp;

    //jniarg(void* dummy) : _dummy(dummy) {}
    jniarg() : _dummy((void*)this) {}

    template<typename T>
    bool eq(T const * p) const
    {
      return static_cast<void const *>(p) == static_cast<void const *>(this)
        && (/*derp*/ _dummy != 0);
    }

  private:
    void *_dummy;
  };
} // namespace myavn

#endif // _myavn_h_jniargs

