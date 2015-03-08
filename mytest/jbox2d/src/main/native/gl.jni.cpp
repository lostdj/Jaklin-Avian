//#include <avian/machine.h>

#include <cph/cph.h>
using namespace cph;

#include <cph/java.h>
using namespace jni;

#include <cph/debug_print_simple.h>

#include <SDL/SDL.h>

//#if !_cph_os_ems
  #include <GL/gl.h>
  #include <GL/glu.h>
//#else
//  #include <GLES2/gl2.h>
//  #include <EGL/egl.h>
//#endif

extern "C"
{
/*
 * Class:     myavn_test_jbox2d_gl
 * Method:    glEnable
 * Signature: (I)V
 */
JNIEXPORT void JNICALL Java_myavn_test_jbox2d_gl_glEnable
  (JNIEnv *, jclass, jint cap)
{
  glEnable(cap);
}

/*
 * Class:     myavn_test_jbox2d_gl
 * Method:    glBlendFunc
 * Signature: (II)V
 */
JNIEXPORT void JNICALL Java_myavn_test_jbox2d_gl_glBlendFunc
  (JNIEnv *, jclass, jint sfactor, jint dfactor)
{
  glBlendFunc(sfactor, dfactor);
}

/*
 * Class:     myavn_test_jbox2d_gl
 * Method:    glClear
 * Signature: (I)V
 */
JNIEXPORT void JNICALL Java_myavn_test_jbox2d_gl_glClear
  (JNIEnv *, jclass, jint mask)
{
  glClear(mask);
}

/*
 * Class:     myavn_test_jbox2d_gl
 * Method:    glFlush
 * Signature: ()V
 */
JNIEXPORT void JNICALL Java_myavn_test_jbox2d_gl_glFlush
  (JNIEnv *, jclass)
{
  GLenum err;
  while((err = glGetError()) != GL_NO_ERROR)
    _cph_sprinte("OpenGL error: %d\n", err);

  //  glFlush();
  SDL_GL_SwapBuffers();

}

/*
 * Class:     myavn_test_jbox2d_gl
 * Method:    glLineWidth
 * Signature: (F)V
 */
JNIEXPORT void JNICALL Java_myavn_test_jbox2d_gl_glLineWidth
  (JNIEnv *, jclass, jfloat width)
{
  glLineWidth(width);
}

/*
 * Class:     myavn_test_jbox2d_gl
 * Method:    glLoadIdentity
 * Signature: ()V
 */
JNIEXPORT void JNICALL Java_myavn_test_jbox2d_gl_glLoadIdentity
  (JNIEnv *, jclass)
{
  glLoadIdentity();
}

/*
 * Class:     myavn_test_jbox2d_gl
 * Method:    glMatrixMode
 * Signature: (I)V
 */
JNIEXPORT void JNICALL Java_myavn_test_jbox2d_gl_glMatrixMode
  (JNIEnv *, jclass, jint mode)
{
  glMatrixMode(mode);
}

/*
 * Class:     myavn_test_jbox2d_gl
 * Method:    glMultMatrixf
 * Signature: ([FI)V
 */
JNIEXPORT void JNICALL Java_myavn_test_jbox2d_gl_glMultMatrixf
  (JNIEnv* env, jclass, jfloatArray m)
{
  jfloat* m2 = env->GetFloatArrayElements(m, null);

  glMultMatrixf(m2);

  env->ReleaseFloatArrayElements(m, m2, 0);
}

/*
 * Class:     myavn_test_jbox2d_gl
 * Method:    glTranslatef
 * Signature: (FFF)V
 */
JNIEXPORT void JNICALL Java_myavn_test_jbox2d_gl_glTranslatef
  (JNIEnv *, jclass, jfloat x, jfloat y, jfloat z)
{
  glTranslatef(x, y, z);
}

#if _cph_os_ems
  void _glPointSize(GLfloat)
  {
    ;
  }
#else
  #define _glPointSize(size) glPointSize(size)
#endif

/*
 * Class:     myavn_test_jbox2d_gl
 * Method:    glPointSize
 * Signature: (F)V
 */
JNIEXPORT void JNICALL Java_myavn_test_jbox2d_gl_glPointSize
  (JNIEnv *, jclass, jfloat size)
{
  _glPointSize(size);
}

/*
 * Class:     myavn_test_jbox2d_gl
 * Method:    glBegin
 * Signature: (I)V
 */
JNIEXPORT void JNICALL Java_myavn_test_jbox2d_gl_glBegin
  (JNIEnv *, jclass, jint mode)
{
  glBegin(mode);
}

/*
 * Class:     myavn_test_jbox2d_gl
 * Method:    glEnd
 * Signature: ()V
 */
JNIEXPORT void JNICALL Java_myavn_test_jbox2d_gl_glEnd
  (JNIEnv *, jclass)
{
  glEnd();
}

/*
 * Class:     myavn_test_jbox2d_gl
 * Method:    glVertex2f
 * Signature: (FF)V
 */
JNIEXPORT void JNICALL Java_myavn_test_jbox2d_gl_glVertex2f
  (JNIEnv *, jclass, jfloat x, jfloat y)
{
  glVertex2f(x, y);
}

/*
 * Class:     myavn_test_jbox2d_gl
 * Method:    glPushMatrix
 * Signature: ()V
 */
JNIEXPORT void JNICALL Java_myavn_test_jbox2d_gl_glPushMatrix
  (JNIEnv *, jclass)
{
  glPushMatrix();
}

/*
 * Class:     myavn_test_jbox2d_gl
 * Method:    glPopMatrix
 * Signature: ()V
 */
JNIEXPORT void JNICALL Java_myavn_test_jbox2d_gl_glPopMatrix
  (JNIEnv *, jclass)
{
  glPopMatrix();
}

/*
 * Class:     myavn_test_jbox2d_gl
 * Method:    glVertex3f
 * Signature: (FFF)V
 */
JNIEXPORT void JNICALL Java_myavn_test_jbox2d_gl_glVertex3f
  (JNIEnv *, jclass, jfloat x, jfloat y, jfloat z)
{
  glVertex3f(x, y, z);
}

/*
 * Class:     myavn_test_jbox2d_gl
 * Method:    glColor3f
 * Signature: (FFF)V
 */
JNIEXPORT void JNICALL Java_myavn_test_jbox2d_gl_glColor3f
  (JNIEnv *, jclass, jfloat red, jfloat green, jfloat blue)
{
  glColor3f(red, green, blue);
}

/*
 * Class:     myavn_test_jbox2d_gl
 * Method:    glColor4f
 * Signature: (FFFF)V
 */
JNIEXPORT void JNICALL Java_myavn_test_jbox2d_gl_glColor4f
  (JNIEnv *, jclass, jfloat red, jfloat green, jfloat blue, jfloat alpha)
{
  glColor4f(red, green, blue, alpha);
}

/*
 * Class:     myavn_test_jbox2d_gl
 * Method:    glViewport
 * Signature: (IIII)V
 */
JNIEXPORT void JNICALL Java_myavn_test_jbox2d_gl_glViewport
  (JNIEnv *, jclass, jint x, jint y, jint width, jint height)
{
  glViewport(x, y, width, height);
}

/*
 * Class:     myavn_test_jbox2d_gl
 * Method:    glOrthof
 * Signature: (FFFFFF)V
 */
JNIEXPORT void JNICALL Java_myavn_test_jbox2d_gl_glOrthof
  (JNIEnv *, jclass, jfloat left, jfloat right, jfloat bottom, jfloat top, jfloat zNear, jfloat zFar)
{
  glOrtho(left, right, bottom, top, zNear, zFar);
}

//JNIEXPORT jint JNICALL Java_myavn_test_jbox2d_Main_ntest
//  (JNIEnv *e, jclass c)
//{
//  jclass cls = e->GetObjectClass(c);
//  jfieldID f = e->GetFieldID(cls, "i", "I");
//  jint i = e->GetIntField(c, f);

//  return i;
//}

/*
 * Class:     myavn_test_jbox2d_gl
 * Method:    window
 * Signature: (III)V
 */
JNIEXPORT jboolean JNICALL Java_myavn_test_jbox2d_gl_window
  (JNIEnv *, jclass, jint width, jint height, jint bpp)
{
  _cph_sprinte("Initializing.\n");
  if(SDL_Init(SDL_INIT_VIDEO) < 0)
  {
    _cph_sprinte("Can't init SDL.\n");
    return false;
  }

  SDL_WM_SetCaption("aww yiss", "aww yiss");
  SDL_GL_SetAttribute(SDL_GL_DOUBLEBUFFER, 1);

//  SDL_GL_SetAttribute(SDL_GL_RED_SIZE,            8);
//  SDL_GL_SetAttribute(SDL_GL_GREEN_SIZE,          8);
//  SDL_GL_SetAttribute(SDL_GL_BLUE_SIZE,           8);
//  SDL_GL_SetAttribute(SDL_GL_ALPHA_SIZE,          8);

//  SDL_GL_SetAttribute(SDL_GL_DEPTH_SIZE,          16);
//  SDL_GL_SetAttribute(SDL_GL_BUFFER_SIZE,            32);

//  SDL_GL_SetAttribute(SDL_GL_ACCUM_RED_SIZE,        8);
//  SDL_GL_SetAttribute(SDL_GL_ACCUM_GREEN_SIZE,    8);
//  SDL_GL_SetAttribute(SDL_GL_ACCUM_BLUE_SIZE,        8);
//  SDL_GL_SetAttribute(SDL_GL_ACCUM_ALPHA_SIZE,    8);

//  SDL_GL_SetAttribute(SDL_GL_MULTISAMPLEBUFFERS,  1);

//  SDL_GL_SetAttribute(SDL_GL_MULTISAMPLESAMPLES,  2);

  static SDL_Surface* s = null;
  if(!s
     && ((s = SDL_SetVideoMode(
            width,
            height,
            bpp,
            SDL_OPENGL/*SDL_HWSURFACE | SDL_DOUBLEBUF*/)) == NULL))
  {
    _cph_sprinte("Can't init display.\n");
    return false;
  }

  glClearColor(0, 0, 0, 0);

  return true;
}

JNIEXPORT jboolean JNICALL Java_myavn_test_jbox2d_gl_key
  (JNIEnv *, jclass)
{
  SDL_Event e;
  while(SDL_PollEvent(&e))
    if(e.type == SDL_KEYUP && e.key.keysym.sym == SDLK_SPACE)
      return true;

  return false;
}

}

