package name.ltp.jviolet.system.utility;

public class UidGenerator
{
	private static UidGenerator i;
	public static UidGenerator i()
	{
		if(i == null)
			i = new UidGenerator();

		return i;
	}

	long m_val;

	private UidGenerator()
	{
		;
	}

	public long getId()
	{
		return m_val++;
	}
}

