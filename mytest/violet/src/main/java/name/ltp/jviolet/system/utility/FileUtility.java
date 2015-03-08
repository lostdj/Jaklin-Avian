package name.ltp.jviolet.system.utility;

import java.io.File;
import java.util.LinkedList;
import java.util.List;

public class FileUtility
{
	public static enum PathType
	{
		common, image, anima, sound, music, monsters, weapon, user
	}

	;

	public String m_appPath, m_resPath, m_usrPath;

	public int getFilesCountFromDir(String dir)
	{
		int i = 0;
		for(File f : new File(dir).listFiles())
			if(!f.isDirectory())
				++i;

		return i;
	}

	public List<File> getFilesFromDir(String dir)
	{
		List<File> l = new LinkedList<>();
		for(File f : new File(dir).listFiles())
			if(!f.isDirectory())
				l.add(f);

		return l;
	}

	public List<File> getSubDirsFromDir(String dir)
	{
		List<File> l = new LinkedList<>();
		for(File f : new File(dir).listFiles())
			if(f.isDirectory() && f.getName().charAt(0) != '.')
				l.add(f);

		return l;
	}

	public int getSubDirsCountFromDir(String dir)
	{
		int i = 0;
		for(File f : new File(dir).listFiles())
			if(f.isDirectory() && f.getName().charAt(0) != '.')
				++i;

		return i;
	}

	public void traceResPath()
	{
		System.out.println("Path to resources is set to:");
		System.out.println("\t" + m_resPath);
//	std::cout << "Path to resources is set to:" << std::endl;
//	std::cout << '\t' << m_resPath << std::endl;
//	std::cout << "To change the path use -r <path> key" << std::endl;
	}

	public FileUtility(String argPath)
	{
		m_appPath = ".";
		m_resPath = ".";
		m_usrPath = ".";

//	#ifdef _WIN32
//	m_appPath = argPath;
//	m_appPath = m_appPath.parent_path();
//	m_resPath = m_appPath;
//	m_usrPath = m_resPath;
//	#endif //_WIN32
//	#if defined linux || defined __FreeBSD__
//	#ifndef INSTALL_PREFIX
//	#define INSTALL_PREFIX "/usr/local";
//	#endif //INSTALL_PREFIX
//		m_appPath = INSTALL_PREFIX;
//	m_appPath /= "bin";
//	#ifndef DATA_INSTALL_DIR
//	m_resPath = m_appPath;
//	m_resPath /= "../share/violetland";
//	#else //DATA_INSTALL_DIR
//	m_resPath = DATA_INSTALL_DIR;
//	#endif //DATA_INSTALL_DIR
//		m_usrPath = getenv("HOME");
//	m_usrPath /= ".config";
//	mkdir(m_usrPath.string().c_str(), S_IRWXU | S_IRGRP | S_IROTH);
//	m_usrPath /= "violetland";
//	mkdir(m_usrPath.string().c_str(), S_IRWXU | S_IRGRP | S_IROTH);
//	#endif //linux || __FreeBSD__
		traceResPath();
	}

	public void setFullResPath(String path)
	{
		m_resPath = path;
		traceResPath();
	}

	public String getFullPath(PathType type, String resource)
	{
		String path = m_resPath;
		String usrPath = m_usrPath;

		switch(type)
		{
			case image:
				path += "/images";
				return path += "/" + resource;
			case anima:
				path += "/images";
				path += "/anima";
				return path += "/" + resource;
			case sound:
				path += "/sounds";
				return path += "/" + resource;
			case music:
				path += "/music";
				return path += "/" + resource;
			case monsters:
				path += "/monsters";
				return path += "/" + resource;
			case weapon:
				path += "/weapon";
				return path += "/" + resource;
			case user:
				return usrPath += "/" + resource;
			default:
				return path += "/" + resource;
		}
	}
}

