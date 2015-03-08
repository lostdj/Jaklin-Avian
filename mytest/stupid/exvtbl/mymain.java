public class mymain
{
	public static void main(String[] args) throws Exception
	{
		try
		{
			((Object)null).getClass();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
}

