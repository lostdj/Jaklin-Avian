package dalvik.system;

public class BaseDexClassLoader extends ClassLoader {
	//mymod not implemented in native
  // public native String getLdLibraryPath();
  public String getLdLibraryPath() {return "";};
}
