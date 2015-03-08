package myavn.tools.preprocess.bin2hex;

import java.io.*;

/*
javac mytest/benchloop10k/mymain.java
(cd mytest/benchloop10k/ && jar cf mymain.jar mymain.class)
java bin2hex out/tux-x64-debug/result/cp.jar "uint8_t cparr[] = {" ", " "};" > myinc/myavn/cp.h
*/

public class Main
{
	static String[] ha = new String[256];
	static
	{
		for(int i = 0; i < 256; i++)
			ha[i] = "0x" + Integer.toHexString(i & 0xFF);
	}

	public static void main(String[] args) throws Exception
	{
		new File(args[4]).delete();
		Writer o = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(args[4])));

//		Path p = FileSystems.getDefault().getPath("", args[0]);
//		byte[] d = Files.readAllBytes(p);
		FileInputStream f = new FileInputStream(new File(args[0]));

//		System.out.print(args[1]);
		o.write(args[1]);
		int i, j = 0;
		while((i = f.read()) != -1)
//			for(int i = 0; i < d.length; i++)
		{
			if(j != 0)
//				System.out.print(args[2]);
				o.write(args[2]);
//			System.out.print(ha[i & 0xFF]);
			o.write(ha[i & 0xFF]);
			++j;
//			System.out.print("0x" + Integer.toHexString(d[i] & 0xFF));
		}
//		System.out.print(args[3]);
		o.write(args[3]);
		o.flush();
		o.close();
	}
}

