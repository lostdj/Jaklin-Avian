public class JNI
{
	// avian
	/*
dummy_java      t: 33, i: 119077
dummy_avian     t: 33, i: 124141
dummy_jni       t: 33, i: 108883

float_java      t: 33, i: 118382
float_avian     t: 33, i: 120788
float_jni       t: 33, i: 107228

double_java     t: 33, i: 116940
double_avian    t: 33, i: 118180
double_jni      t: 33, i: 109926

floor_java      t: 33, i: 108888
floor_avian     t: 33, i: 114660
floor_jni       t: 33, i: 95443

sin_java        t: 33, i: 41032
sin_avian       t: 33, i: 103928
sin_jni         t: 33, i: 87071
	*/


	// ff -s PRECISE_F32=1
	/*
dummy_java 	t: 33, i: 39308
dummy_avian 	t: 33, i: 19525
dummy_jni 	t: 33, i: 28482

float_java 	t: 33, i: 36308
float_avian 	t: 33, i: 18014
float_jni 	t: 33, i: 28647

double_java 	t: 33, i: 36478
double_avian 	t: 33, i: 17850
double_jni 	t: 33, i: 28643

floor_java 	t: 33, i: 27051
floor_avian 	t: 33, i: 17239
floor_jni 	t: 33, i: 27499

sin_java 	t: 33, i: 13164
sin_avian 	t: 33, i: 16837
sin_jni 	t: 33, i: 26757
	*/


	// ff -s PRECISE_F32=1 --llvm-lto 3
	/*
dummy_java 	t: 33, i: 42225
dummy_avian 	t: 33, i: 19981
dummy_jni 	t: 33, i: 29144

float_java 	t: 33, i: 39684
float_avian 	t: 33, i: 19212
float_jni 	t: 33, i: 30428

double_java 	t: 33, i: 38938
double_avian 	t: 33, i: 19221
double_jni 	t: 33, i: 30027

floor_java 	t: 33, i: 30119
floor_avian 	t: 33, i: 18520
floor_jni 	t: 33, i: 29513

sin_java 	t: 33, i: 13867
sin_avian 	t: 33, i: 18015
sin_jni 	t: 33, i: 27822
	*/


	// ch -s PRECISE_F32=1
	/*
dummy_java 	t: 33, i: 3970
dummy_avian 	t: 33, i: 2614
dummy_jni 	t: 33, i: 3755

float_java 	t: 33, i: 3441
float_avian 	t: 33, i: 3386
float_jni 	t: 33, i: 3615

double_java 	t: 33, i: 3517
double_avian 	t: 33, i: 3383
double_jni 	t: 33, i: 3684

floor_java 	t: 33, i: 2381
floor_avian 	t: 33, i: 3188
floor_jni 	t: 33, i: 3426

sin_java 	t: 33, i: 1086
sin_avian 	t: 33, i: 2802
sin_jni 	t: 33, i: 2992
	*/


	// ch 39.0.2171.19 (64-bit) beta -s PRECISE_F32=1
	/*
dummy_java 	t: 33, i: 1617
dummy_avian 	t: 33, i: 1454
dummy_jni 	t: 33, i: 4123

float_java 	t: 33, i: 3867
float_avian 	t: 33, i: 3526
float_jni 	t: 33, i: 3772

double_java 	t: 33, i: 3884
double_avian 	t: 33, i: 3436
double_jni 	t: 33, i: 3778

floor_java 	t: 33, i: 2330
floor_avian 	t: 33, i: 3212
floor_jni 	t: 33, i: 3291

sin_java 	t: 33, i: 1067
sin_avian 	t: 33, i: 3091
sin_jni 	t: 33, i: 3356
	*/


	// ch 39.0.2171.19 (64-bit) beta -s PRECISE_F32=1 --llvm-lto 3
	/*
dummy_java 	t: 33, i: 4172
dummy_avian 	t: 33, i: 3614
dummy_jni 	t: 33, i: 4079

float_java 	t: 33, i: 3784
float_avian 	t: 33, i: 3364
float_jni 	t: 33, i: 3811

double_java 	t: 33, i: 2164
double_avian 	t: 33, i: 3403
double_jni 	t: 33, i: 3761

floor_java 	t: 33, i: 2011
floor_avian 	t: 33, i: 3231
floor_jni 	t: 33, i: 3469

sin_java 	t: 33, i: 1092
sin_avian 	t: 33, i: 3198
sin_jni 	t: 33, i: 3538
	*/


	public static void dummy_java()
	{
		;
	}
	public static native void dummy_avian();
	public static native void dummy_jni();


	//


	public static void float_java(float f)
	{
		;
	}
	public static native void float_avian(float f);
	public static native void float_jni(float f);


	//


	public static void double_java(double f)
	{
		;
	}
	public static native void double_avian(double f);
	public static native void double_jni(double f);


	//


	public static double floor_java(double d)
	{
		int y = (int)d;
		if (d < y)
			return y - 1;

		return y;
	}
	public static native double floor_avian(double d);
	public static native double floor_jni(double d);


	//


	public static boolean SINCOS_LUT_LERP = false;
	public static final double SINCOS_LUT_PRECISION = .00011;
	public static final int SINCOS_LUT_LENGTH = (int)Math.ceil(Math.PI * 2 / SINCOS_LUT_PRECISION);
	public static final double TWOPI = (Math.PI * 2);
	public static final double[] sinLUT = new double[SINCOS_LUT_LENGTH];
	public static int round(double d)
	{
		return (int)floor_java(d + .5);
	}
	public static final double sinLUT(double x)
	{
		x %= TWOPI;

		if (x < 0) {
			x += TWOPI;
		}

		if (SINCOS_LUT_LERP) {

			x /= SINCOS_LUT_PRECISION;

			final int index = (int) x;

			if (index != 0) {
				x %= index;
			}

			// the next index is 0
			if (index == SINCOS_LUT_LENGTH - 1) {
				return ((1 - x) * sinLUT[index] + x * sinLUT[0]);
			} else {
				return ((1 - x) * sinLUT[index] + x * sinLUT[index + 1]);
			}

		} else {
			return sinLUT[round(x / SINCOS_LUT_PRECISION) % SINCOS_LUT_LENGTH];
		}
	}

	public static double sin_java(double d)
	{
		return sinLUT(d);
	}
	public static native double sin_avian(double d);
	public static native double sin_jni(double d);


	//


	static long tstop = 33;

	public static long time()
	{
			return System.currentTimeMillis();
	}

	public static void print(String s, long t, int i)
	{
		System.out.println(s + " \t" + "t: " + t + ", i: " + i);
	}


	//


	public static void bench(boolean print)
	{
		long t = 0;
		int i = 0;


		//
		{
			dummy_java(); dummy_avian(); dummy_jni();

			t = time(); i = 0; do{ dummy_java(); ++i; } while((time() - t) < tstop);
			if(print) print("dummy_java", time() - t, i);

			t = time(); i = 0; do{ dummy_avian(); ++i; } while((time() - t) < tstop);
			if(print) print("dummy_avian", time() - t, i);

			t = time(); i = 0; do{ dummy_jni(); ++i; } while((time() - t) < tstop);
			if(print) print("dummy_jni", time() - t, i);
			
			if(print) System.out.println();
		}


		//
		{
			float_java(123.456f); float_avian(123.456f); float_jni(123.456f);

			t = time(); i = 0; do{ float_java(123.456f); ++i; } while((time() - t) < tstop);
			if(print) print("float_java", time() - t, i);

			t = time(); i = 0; do{ float_avian(123.456f); ++i; } while((time() - t) < tstop);
			if(print) print("float_avian", time() - t, i);

			t = time(); i = 0; do{ float_jni(123.456f); ++i; } while((time() - t) < tstop);
			if(print) print("float_jni", time() - t, i);

			if(print) System.out.println();
		}


		//
		{
			double_java(123.456d); double_avian(123.456d); double_jni(123.456d);

			t = time(); i = 0; do{ double_java(123.456d); ++i; } while((time() - t) < tstop);
			if(print) print("double_java", time() - t, i);

			t = time(); i = 0; do{ double_avian(123.456d); ++i; } while((time() - t) < tstop);
			if(print) print("double_avian", time() - t, i);

			t = time(); i = 0; do{ double_jni(123.456d); ++i; } while((time() - t) < tstop);
			if(print) print("double_jni", time() - t, i);

			if(print) System.out.println();
		}


		//
		{
			floor_java(123.456d); floor_avian(123.456d); floor_jni(123.456d);

			t = time(); i = 0; do{ floor_java(123.456d); ++i; } while((time() - t) < tstop);
			if(print) print("floor_java", time() - t, i);

			t = time(); i = 0; do{ floor_avian(123.456d); ++i; } while((time() - t) < tstop);
			if(print) print("floor_avian", time() - t, i);

			t = time(); i = 0; do{ floor_jni(123.456d); ++i; } while((time() - t) < tstop);
			if(print) print("floor_jni", time() - t, i);

			if(print) System.out.println();
		}


		//
		{
			sin_java(42d); sin_avian(42d); sin_jni(42d);

			t = time(); i = 0; do{ sin_java(42d); ++i; } while((time() - t) < tstop);
			if(print) print("sin_java", time() - t, i);

			t = time(); i = 0; do{ sin_avian(42d); ++i; } while((time() - t) < tstop);
			if(print) print("sin_avian", time() - t, i);

			t = time(); i = 0; do{ sin_jni(42d); ++i; } while((time() - t) < tstop);
			if(print) print("sin_jni", time() - t, i);

			if(print) System.out.println();
		}
	}


	//


	public static void main(String[] args) throws Exception
	{
		bench(false);
		bench(true);

		System.out.println((sin_avian(42)==sin_jni(42))+"");
		System.out.println(sin_avian(42)+"");
		System.out.println(sin_jni(42)+"");
	}
}

