package name.ltp.jviolet.system;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;

public class ConfigFile
{
	String filename;
	public String myDelimiter, myComment, mySentry;
	public Map<String, String> myContents = new HashMap<>();

	public ConfigFile()
	{
		myDelimiter = "=";
		myComment = "#";
	}

	public ConfigFile(String filename) throws FileNotFoundException
	{
		this(filename, "=");
	}

	public ConfigFile(String filename, String delimiter) throws FileNotFoundException
	{
		this(filename, delimiter, "#");
	}

	public ConfigFile(String filename, String delimiter, String comment) throws FileNotFoundException
	{
		this(filename, delimiter, comment, "EndConfigFile");
	}

	public ConfigFile(String filename, String delimiter, String comment, String sentry) throws FileNotFoundException
	{
		File f = new File(this.filename = filename);
		if(!f.exists() || !f.isFile() || !f.canRead())
			throw new FileNotFoundException(filename);

		;
	}

	public void remove(String key)
	{
		// Remove key and its value
		myContents.remove(key);
	}

	public boolean keyExists(String key)
	{
		// Indicate whether key is found
		return myContents.containsKey(key);
	}

	/* static */
	public String trim(String s)
	{
		// Remove leading and trailing whitespace
		return s.trim();
	}

	public String getDelimiter()
	{
		return myDelimiter;
	}
	public String getComment()
	{
		return myComment;
	}
	public String getSentry()
	{
		return mySentry;
	}

	public String setDelimiter(String s)
	{
		String old = myDelimiter;
		myDelimiter = s;
		return old;
	}
	public String setComment(String s)
	{
		String old = myComment;
		myComment = s;
		return old;
	}

	public void read() throws Exception
	{
		String delim = myDelimiter;
		String comm = myComment;
		String sentry = mySentry;
		int skip = delim.length();

		String nextline = "";

		BufferedReader br = new BufferedReader(new FileReader(new File(filename)));
		while(nextline != null && !nextline.isEmpty())
		{
//		for(String line; (line = br.readLine()) != null; ) {
			String line;
			if(!nextline.isEmpty())
			{
				line = nextline;
				nextline = "";
			}
			else
				line = br.readLine();

			line = line.substring(0, line.indexOf(comm));

			if(!sentry.isEmpty() && line.contains(sentry))
				return;

			int delimPos = line.indexOf(delim);
			if(delimPos != -1)
			{
				String key = line.substring(0, delimPos);
				line = line.substring(0, delimPos + skip);

				boolean terminate = false;
				while(!terminate && (nextline = br.readLine()) != null)
				{
					terminate = true;

					String nlcopy = nextline.trim();
					if(nlcopy.isEmpty())
						continue;

					nextline = nextline.substring(0, nextline.indexOf(comm));
					if(!nextline.contains(delim))
						continue;
					if(!nextline.isEmpty() && nextline.contains(sentry))
						continue;

					nlcopy = nextline.trim();
					if(!nlcopy.isEmpty())
						line += "\n";
					line += nextline;
					terminate = false;
				}

				myContents.put(key.trim(), line.trim());
			}
		}
		br.close();
	}

//	public boolean readInto(String key)
//	{
//		;
//	}
}

