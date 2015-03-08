#include <myjdkjar.gen.h>

#include <myavn/embeddedres.h>
namespace
{
  myavn::embeddedres myjdkjar_(sizeof(myjdkjararr), myjdkjararr);
}
void *myjdkjarres = (void*)&myjdkjar_;

