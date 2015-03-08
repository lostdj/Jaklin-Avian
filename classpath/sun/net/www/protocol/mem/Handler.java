package sun.net.www.protocol.mem;

import sun.misc.Unsafe;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLStreamHandler;

public class Handler extends URLStreamHandler
{
	@Override
	protected URLConnection openConnection(URL url) throws IOException
	{
		return new MemURLConnection(url);
	}

	protected void parseURL(URL url, String s, int start, int end)
	{
		// skip "mem:"
		final String orig = s;
		s = s.substring(s.indexOf("mem:") + 4, s.length());

		if(!Character.isDigit(s.charAt(0)))
			throw new RuntimeException(new MalformedURLException());
		int i = 0;
		for(; i < s.length(); i++)
			if(!Character.isDigit(s.charAt(i)))
			{
				--i;
				break;
			}

		setURL(url, "mem", s.substring(0, i), -1, s, "", orig, "", "");
	}

	private static class MemURLConnection extends URLConnection
	{
		long addr;
		long i = 0;

		public MemURLConnection(URL url)
		{
			super(url);

			addr = Long.parseLong(url.getHost());
		}

		public InputStream getInputStream() throws IOException
		{
			return new InputStream()
			{
				@Override
				public int read() throws IOException
				{
					return unsafe.getByte(addr + i++);
				}
			};
		}

		public void connect()
		{
			// ignore
		}

		static Unsafe unsafe;
		static
		{
			try
			{
				Field singleoneInstanceField = Unsafe.class.getDeclaredField("theUnsafe");
				singleoneInstanceField.setAccessible(true);
				unsafe = (Unsafe)singleoneInstanceField.get(null);
			}
			catch(Exception ignored) { System.err.println("Can't get ahold of Unsafe."); }
		}
	}
}

