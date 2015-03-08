package name.ltp.jviolet;

public class M
{
	public static final float PI = (float)java.lang.Math.PI;

	public static double max(double a, double b)
	{
		return (a < b ? b : a);
	}

	public static double min(double a, double b)
	{
		return (a > b ? b : a);
	}

	public static float max(float a, float b)
	{
		return (a < b ? b : a);
	}

	public static float min(float a, float b)
	{
		return (a > b ? b : a);
	}

	public static long max(long a, long b)
	{
		return (a < b ? b : a);
	}

	public static long min(long a, long b)
	{
		return (a > b ? b : a);
	}

	public static int max(int a, int b)
	{
		return (a < b ? b : a);
	}

	public static int min(int a, int b)
	{
		return (a > b ? b : a);
	}

	public static int abs(int v)
	{
		return (v < 0 ? -v : v);
	}

	public static long abs(long v)
	{
		return (v < 0 ? -v : v);
	}

	public static float abs(float v)
	{
		return (v < 0 ? -v : v);
	}

	public static double abs(double v)
	{
		return (v < 0 ? -v : v);
	}

	public static long round(double v)
	{
		return (long)java.lang.Math.floor(v + 0.5);
	}

	public static int round(float v)
	{
		return (int)java.lang.Math.floor(v + 0.5);
	}

	public static float sin(float v)
	{
		return (float)java.lang.Math.sin(v);
	}

	public static float cos(float v)
	{
		return (float)java.lang.Math.cos(v);
	}

	public static float atan2(float y, float x)
	{
		return (float)java.lang.Math.atan2(y, x);
	}

	public static float pow(float v, float e)
	{
		return (float)java.lang.Math.pow(v, e);
	}

	public static float sqrt(float v)
	{
		return (float)java.lang.Math.sqrt(v);
	}

	public static float hypot(float x, float y)
	{
		return 123; // FIXME:
	}

	public static float random()
	{
		return (float)java.lang.Math.random();
	}
}

