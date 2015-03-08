package myavn.tools.preprocess.nativemapper;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.MethodNode;

import java.io.*;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.util.*;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.lang.System.err;
import static java.lang.System.out;

public class Main
{
	{out.println(); err.println();}

	static int error = 0;
	static String name = "";
	static String jar = "";
	static String flags = "";
	static String cppfiles = "";
	static String stubs = System.getProperty("avn.nama.stubs");
	static {if(stubs == null) stubs = "";}

	static final List<HashSet> cppprocessed = new LinkedList<>();
/*	{
		"/mnt/zfs/levault/dev/s/myavian/classpath/java-io.cpp",
		"/mnt/zfs/levault/dev/s/myavian/classpath/java-lang.cpp",
		"/mnt/zfs/levault/dev/s/myavian/classpath/java-net.cpp",
		"/mnt/zfs/levault/dev/s/myavian/classpath/java-nio.cpp",
		"/mnt/zfs/levault/dev/s/myavian/classpath/java-util-zip.cpp",
		"/mnt/zfs/levault/dev/s/myavian/classpath/java-util.cpp",
		"/mnt/zfs/levault/dev/s/myavian/src/builtin.cpp",
		"/mnt/zfs/levault/dev/s/myavian/src/classpath-avian.cpp",
	};*/

	static final Map<String, String> cppsrcfuncs = new HashMap<>();

	static final String REMOVED = "REMOVED";
	static final Set<String> removedfuncs = new HashSet<>();

	static String mangle(String s)
	{
		return s
			.replaceAll("\\_", "_1")
			.replaceAll("\\;", "_2")
			.replaceAll("\\[", "_3")
			.replaceAll("\\/", "_")
			.replaceAll("\\$", "_00024")
			;
	}

	static String mangle(String prefix, String nm)
	{
		return prefix + "_" + mangle(nm);
	}

	static String jniMeth(String prefix, String clazz, String meth, String methSpec, boolean decorate)
	{
		String nm = mangle(prefix, clazz) + "_" + mangle(meth);

		if(decorate)
			nm = nm + "__" + mangle(methSpec.substring(1, methSpec.lastIndexOf(')')));

		return nm;
	}

	static String jniObjType(String type, int i)
	{
		String t = type.substring(i, type.indexOf(';', i));
		// TODO:
		if(t.equals("java/lang/Class"))
			return "jclass";
		if(t.equals("java/lang/String"))
			return "jstring";
		if(t.equals("java/lang/Throwable"))
			return "jthrowable";

		return "jobject";
	}

	static String jniBasicType(char c, boolean jniNs)
	{
		switch(c)
		{
			case 'V':
				return "void";
			case 'Z':
				return (jniNs?"jni::":"")+"jboolean";
			case 'B':
				return (jniNs?"jni::":"")+"jbyte";
			case 'C':
				return (jniNs?"jni::":"")+"jchar";
			case 'S':
				return (jniNs?"jni::":"")+"jshort";
			case 'I':
				return (jniNs?"jni::":"")+"jint";
			case 'J':
				return (jniNs?"jni::":"")+"jlong";
			case 'F':
				return (jniNs?"jni::":"")+"jfloat";
			case 'D':
				return (jniNs?"jni::":"")+"jdouble";
			default:
				return null;
		}
	}

	static String jniTypes(String methSig, boolean params, boolean jniNs)
	{
		if(params)
			methSig = methSig.substring(methSig.indexOf('('), methSig.indexOf(')'));
		else
			methSig = methSig.substring(methSig.indexOf(')'), methSig.length());

		char[] s = methSig.toCharArray();
		List<String> t = new LinkedList<>();
		for(int i = 0; i < methSig.length(); i++)
		{
			String type = jniBasicType(s[i], jniNs);
			if(type != null)
			{
				t.add(type);

				continue;
			}

			if(s[i] == 'L')
			{
				t.add((jniNs?"jni::":"")+jniObjType(methSig, i+1));
				i = methSig.indexOf(';', i);
			}
			else if(s[i] == '[' && s[i+1] != '[')
			{
				if(s[i+1] == 'L')
				{
					t.add((jniNs?"jni::":"")+"jobjectArray");
					i = methSig.indexOf(';', i);
				}
				else
					t.add(jniBasicType(s[++i], jniNs) + "Array");
			}
			else if(s[i] == '[' && s[i+1] == '[')
			{
				t.add((jniNs?"jni::":"")+"jobjectArray");
				while(s[i+1] == '[') ++i;
				if(s[i+1] == 'L')
					i = methSig.indexOf(';', i);
				else
					++i;
			}
		}

		String r = "";
		for(int i = 0; i < t.size(); i++)
		{
			r += t.get(i);
			if(i+1 < t.size())
				r += ',';
		}

		return r;
	}

	static String jniTypes(String methSig, boolean params)
	{
		return jniTypes(methSig, params, false);
	}

	static String find(ClassReader cr, MethodNode mn)
	{
		String t = null;
		String m = null;
		Map<String, String> h = cppsrcfuncs;

		String a=jniMeth("Avian", cr.getClassName(), mn.name, mn.desc, false);
		String b=jniMeth("Avian", cr.getClassName(), mn.name, mn.desc, true);
		String c=jniMeth("Java", cr.getClassName(), mn.name, mn.desc, false);
		String d=jniMeth("Java", cr.getClassName(), mn.name, mn.desc, true);

		m=m==null?h.containsKey(t=a) ? t : null : m;
		m=m==null?h.containsKey(t=b) ? t : null : m;
		m=m==null?h.containsKey(t=c) ? t : null : m;
		m=m==null?h.containsKey(t=d) ? t : null : m;

		if(m != null)
			return m;

		m=m==null?removedfuncs.contains(t = a) ? t : null : m;
		m=m==null?removedfuncs.contains(t = b) ? t : null : m;
		m=m==null?removedfuncs.contains(t = c) ? t : null : m;
		m=m==null?removedfuncs.contains(t = d) ? t : null : m;

		if(m != null)
			m = REMOVED;

		return m;
	}

	public static int hash1(String s)
	{
		int h = 0;
		char[] a = s.toCharArray();
		if(a.length > 0)
			for(int i = 0; i < a.length; i++)
				h = 31 * h + a[i];

		return h;
	}

	public static int hash2(String s)
	{
		char[] a = {s.charAt(6), s.charAt(s.length()-3),s.charAt(s.length()-2),s.charAt(s.length()-1)};

		return hash1(new String (a));
	}

	@SuppressWarnings("unchecked")
	public static void main(String[] args) throws Exception
	{
		name = args[0];
		jar = args[1];
		flags = args[2];
		cppfiles = args[3].trim();
//		out.println(name);
//		out.println();
//		out.println(jar);
//		out.println();
//		out.println(flags);
//		out.println();
//		out.println(cppfiles);
//		out.println();

		Arrays.asList(cppfiles.split(" ")).parallelStream().forEach(s ->
			{
				s = s.trim();
				if(s.isEmpty() || new File(s).isDirectory())
					return;

				try
				{
					String src = new String(Files.readAllBytes(FileSystems.getDefault().getPath("", s)));
					Matcher m = Pattern
						.compile(
							"(?:JNIEXPORT|AVIAN_EXPORT)\\s*([^ \\r\\n\\t]*)\\s*(?:JNICALL|NO_RETURN JNICALL|)\\s*((?:Java|Avian)_\\w*)\\s*\\(\\s*(?:.*[^()]*)\\s*\\)\\s*\\{"
							, Pattern.MULTILINE)
						.matcher(src);

					while(m.find())
						synchronized(cppsrcfuncs)
						{
							cppsrcfuncs.put(m.group(2).trim(), m.group(1).trim());
						}
				}
				catch(Exception e) {System.err.println(e);}
			});

		{
			Pattern r = Pattern.compile(
				"([^ \\r\\n\\t]*)\\s*((?:Java|Avian)_\\w*)\\s*\\(\\s*(?:.*[^()]*)\\s*\\)\\s*\\{"
				, Pattern.MULTILINE);
			Arrays.asList(cppfiles.split(" ")).parallelStream().forEach(
				s ->
				{
					try
					{
						List<String> cmd = new ArrayList<>();
						cmd.add("sh");
						cmd.add("-c");
						cmd.add("gcc" + flags.replaceAll("\"", "") + " -E " + s);
						ProcessBuilder pb = new ProcessBuilder(cmd);
						pb.redirectErrorStream(false);
						Process p = pb.start();
						p.getOutputStream().close();

						StringBuilder sb = new StringBuilder(1024 * 20);
						StringBuilder esb = new StringBuilder(1024 * 20);
						BufferedReader std = new BufferedReader(new InputStreamReader(p.getInputStream()));
						BufferedReader err = new BufferedReader(new InputStreamReader(p.getErrorStream()));
						String line = null;
						String eline = null;
						while(((line = std.readLine()) != null) || ((eline = err.readLine()) != null))
						{
							if(line != null)
								sb.append(line).append('\n');
							if(eline != null)
								esb.append(eline).append('\n');
							//if(s.contains("util-zip"))out.println(line);out.flush();
//				out.println(line);out.flush();
						}
						p.waitFor();

						if(eline != null)
						{
							System.err.println(esb.toString());
							error = 1;
						}

						HashSet h = new HashSet();
						Matcher m = r.matcher(sb.toString());
						sb = new StringBuilder(1024 * 20);
						while(m.find())
							h.add(m.group(2).trim());

						synchronized(cppprocessed)
						{
							cppprocessed.add(h);
						}
					}
					catch(Exception e)
					{
						System.err.println(e);
					}
				});
		}

		StringBuilder o = new StringBuilder(1024 * 10);

		{
			new ArrayList<>(cppsrcfuncs.keySet()).parallelStream().forEach(
				f ->
				{
					class found
					{
						boolean found;
					}
					final found found = new found();

					cppprocessed./*parallelStream().*/forEach(
						p ->
						{
							if(found.found)
								return;

							if(p.contains(f))
							{
								found.found = true;
								return;
							}
						});

					if(!found.found)
					{
						o.append("//Skipping " + f + "\n");
						err.println("//Skipping " + f);
						synchronized(removedfuncs)
						{
							removedfuncs.add(f);
							cppsrcfuncs.remove(f);
						}
					}
				});
		}

		o.append(
			""
				+ "#ifndef _myavn_cpp_jnimeth__" + name + "\n"
				+ "#define _myavn_cpp_jnimeth__" + name + "\n"
				+ "\n"
				+ "#include <stdlib.h>\n#include <stdio.h>\n#include <string.h>"
				+ "\n"
				+ "#include <avian/machine.h>"
				+ "\n"
				+ "#include <myavn/jni-meth.inc.cpp>" + "\n"
				+ "#include <cph/debug_print_simple.h>" + "\n"
				+ "" + "\n"
				+ "namespace myavn{namespace meth{namespace{namespace " + name + "{" + "\n"
				+ "using namespace cph;" + "\n"
				+ "" + "\n"
		);

		JarFile jarf = new JarFile(jar);
		Enumeration<JarEntry> jarfe = jarf.entries();
		while(jarfe.hasMoreElements())
		{
			JarEntry je = jarfe.nextElement();
			if(!je.getName().endsWith(".class"))
				continue;

			byte[] cb;
			{
				ByteArrayOutputStream baos = new ByteArrayOutputStream();
				InputStream is = jarf.getInputStream(je);
				for(int b = is.read(); b != -1; b = is.read())
					baos.write(b);
				cb = baos.toByteArray();
			}

			ClassReader cr = new ClassReader(cb);
			ClassNode cn = new ClassNode();
			cr.accept(cn, 0);
			for(MethodNode mn : (List<MethodNode>)cn.methods)
			{
				if((mn.access & Opcodes.ACC_NATIVE) == 0)
					continue;

				boolean stub = false;
				String m = find(cr, mn);
				if(m == REMOVED || stubs.contains(cr.getClassName() + "." + mn.name + ": " + mn.desc))
				{
					stub = true;
					cppsrcfuncs.put(m = jniMeth("Java", cr.getClassName(), mn.name, mn.desc, true), stubs);
				}

				String r = jniTypes(mn.desc, false, true);
				String p = jniTypes(mn.desc, true, true);
				if(m == null && !stub)
				{
					err.println("Not found " + cr.getClassName() + "." + mn.name + ": " + mn.desc);

					error = 1;
					continue;
				}
				else if(!stub &&
					/*m.startsWith("Avian_")
					&& */(mn.desc.charAt(mn.desc.length()-1) == 'V' && mn.desc.charAt(mn.desc.length()-2) == ')')
					&& !(cppsrcfuncs.get(m)).equals("void"))
				{
					err.println("Ret type mismatch " + cr.getClassName() + "." + mn.name + ": " + mn.desc);

					error = 1;
					continue;
				}
//				else
//					out.println(mn.desc+":"+jniTypes(mn.desc, false)+" "+m+"("+jniTypes(mn.desc, true)+")");

				if(!stub && m.startsWith("Avian_"))
				{
					o.append(
						""
							+ "extern \"C\" AVIAN_EXPORT "+ (r.equals("void") ? "void" : "int64_t") +" JNICALL" + "\n"
							+ "	" + m + "\n"
							+ "	(vm::Thread*,vm::object,uintptr_t*);" + "\n"
							+ "\n"
					);
				}
				else
				{
					if(stub)
					{
						o.append("//Stub " + m +"\n");
						err.println("//Stub " + m);
						o.append(
							""
//							+ "void NO_RETURN"
							+ "extern \"C\" JNIEXPORT "+ r +" JNICALL" + "\n"
							+ "	" + m+"_myavnwrapper"+name +"(local::Thread*"
								+ ((mn.access & Opcodes.ACC_STATIC) != 0?", uw)":")") +"\n"
							+ "{_cph_sprinte(\"ABORTION!11\" \" \" \"NOT IMPLEMENTED: %s\",_cph_funcname);abort();"
							+ (r.equals("void")?"":"return 0;") + "}" +"\n\n"
						);
						continue;
					}

					o.append(
						""
							+ "extern \"C\" JNIEXPORT "+ r +" JNICALL" + "\n"
							+ "	" + m + "\n"
							+ "	(jni::JNIEnv*,jni::jclass"+ (!p.isEmpty() ? ","+p : "") +");" +"\n"
					);

					o.append(""
						+ (r.equals("void")?r:"u8") +" "+ m+"_myavnwrapper"+name +"(local::Thread* t"
							+((mn.access & Opcodes.ACC_STATIC) != 0?", uw thiz)":")") +"\n"
						+"{"+"\n"
					);

					if(!p.isEmpty() || (mn.access & Opcodes.ACC_STATIC) == 0)
						o.append("	unsigned sp=frameBase(t, t->frame);"+"\n");

					if(!r.equals("void"))
						o.append("	return cast<u8>("+"\n");
					if(r.endsWith("float"))
						o.append("	vm::floatToBits("+"\n");
					else if(r.endsWith("double"))
						o.append("	vm::doubleToBits("+"\n");

					o.append("	"+m+"("+"\n");
					{
						int idx = 0;

						o.append("		rcast<jni::JNIEnv*>(t)"+"\n");
						if((mn.access & Opcodes.ACC_STATIC) != 0)
							o.append("		,rcast<jni::jclass>(thiz)"+"\n");
						else
							o.append("		,peekjclass(t,"+ "sp+"+idx++ +")"+"\n");

						if(!p.isEmpty())
							for(String s : p.split(","))
							{
								s = s.replace("jni::", "");
								o.append("		,"+ "peek"+s+"(t,"+ "sp+"+idx++ +")" +"\n");
								if(s.endsWith("long") || s.endsWith("double"))
									++idx;
							}
					}
					if(r.endsWith("float") || r.endsWith("double"))
						o.append("	"+")));"+"\n");
					else if(!r.equals("void"))
						o.append("	"+"));"+"\n");
					else
						o.append("	"+");"+"\n");

					o.append(""
						+"}"+"\n"
					);

					o.append("\n");
				}
			}
		}
		o.append("\n");

		o.append("MethTableEntry entries[]="+"\n");
		o.append("{"+"\n");
		jarfe = jarf.entries();
		while(jarfe.hasMoreElements())
//		for(String m : cppsrcfuncs.keySet())
		{
			JarEntry je = jarfe.nextElement();
			if(!je.getName().endsWith(".class"))
				continue;

			byte[] cb;
			{
				ByteArrayOutputStream baos = new ByteArrayOutputStream();
				InputStream is = jarf.getInputStream(je);
				for(int b = is.read(); b != -1; b = is.read())
					baos.write(b);
				cb = baos.toByteArray();
			}

			ClassReader cr = new ClassReader(cb);
			ClassNode cn = new ClassNode();
			cr.accept(cn, 0);
			for(MethodNode mn : (List<MethodNode>)cn.methods)
			{
				if((mn.access & Opcodes.ACC_NATIVE) == 0)
					continue;

				boolean stub = false;
				String m = find(cr, mn);
				// Should not happen, but...
				if(m == null)
					continue;

				o.append("	{" + "\n");
				o.append("		.h1=" + hash1(m) + "," + "\n");
				o.append("		.h2=" + hash2(m) + "," + "\n");
				o.append("		.f=(void*)&" + (m.startsWith("Avian_") ? m : m + "_myavnwrapper" + name) + "," + "\n");
				o.append("	}," + "\n");
			}
		}
		o.append("};"+"\n");
		o.append("\n");

		o.append("MethLibrary methlib_ = MethLibrary"+"\n");
		o.append("("+"\n");
		o.append("	{"+"\n");
		o.append("		"+cppsrcfuncs.size()+"," +"\n");
		o.append("		"+(cppsrcfuncs.isEmpty()?"(void*)0":"entries,") +"\n");
		o.append("	}"+"\n");
		o.append(");"+"\n");
		o.append("\n");
		o.append("void *methlib = (void*)&methlib_;"+"\n");
		o.append("\n");

		o.append(""
				+ "}}}}" + "\n"
				+ "" + "\n"
				+ "#endif" + "\n"
				+ ""//
		);

		out.println(o.toString());

		System.exit(error);
	}
}

