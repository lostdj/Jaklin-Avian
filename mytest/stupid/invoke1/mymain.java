public class mymain
{
	public static void invk(mymain m, Integer i)
	{
		System.out.println("hello");
	}

	public static void main(String[] args) throws Exception
	{
		mymain.class.getMethod("invk", new Class[]{mymain.class, Integer.class}).invoke(null, new Object[]{null, null});
	}
}

