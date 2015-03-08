{
	pkgs ? (import <nixos> { inherit system; }).pkgs
, system ? builtins.currentSystem
}:

with pkgs;

let

stdenv = pkgs.stdenv;

in

stdenv.mkDerivation
{
	name = "avian";

	src = ./.;

	buildInputs =
	[
		stdenv
		gdb
		pkgconfig
		#zlib
		oraclejdk8
		emscripten

		#xlibs.libxcb
		xlibs.libX11 xlibs.libXxf86vm xlibs.libXrender xlibs.libXrandr xlibs.libXcursor
		xorg.libXt xorg.libXext xorg.libXtst xorg.libXi xorg.libXinerama xorg.lndir

		#udev

		/*
		pango
		cairo
		glib
		gdk_pixbuf
		gtk2
		*/
		gtk2
		
		mesa
		#SDL
		#freetype

		alsaLib
		pulseaudio
	];

	shellHook =
	''
		export JAVA_HOME=${oraclejdk8}
		#export PATH=$PATH:/letmp/9y8nrgrmn2vvmph6vygvwhyzyy5yslmn-emscripten-1.29.10/bin
	'';

	/* # Replaced with pkgconfig.
	NIX_CFLAGS_COMPILE =
	"
		-isystem ${pango}/include/pango-1.0
		-isystem ${cairo}/include/cairo
		-isystem ${glib}/lib/glib-2.0/include
		-isystem ${glib}/include/glib-2.0
		-isystem ${glib}/include/gio-unix-2.0
		-isystem ${gtk2}/include/gtk-2.0
	";
	*/


/*
	# JDK hax.
  librarieshax =
    [stdenv.gcc.libc glib libxml2 libav_0_8 ffmpeg libxslt  xlibs.libXxf86vm alsaLib fontconfig freetype gnome.pango gnome.gtk cairo gdk_pixbuf atk xlibs.libX11 xlibs.libXext xlibs.libXtst xlibs.libXi xlibs.libXp xlibs.libXt xlibs.libXrender stdenv.gcc.gcc];

	hax =
	''
rpath= 
for i in $librarieshax; do
    rpath=$rpath''${rpath:+:}$i/lib:$i/lib64
done

jrePath=/letmp/jdk1.9.0
architecture=amd64
rpath=$rpath''${rpath:+:}$jrePath/lib/$architecture/jli
rpath=$rpath''${rpath:+:}$jrePath/lib/$architecture/server
rpath=$rpath''${rpath:+:}$jrePath/lib/$architecture/xawt
rpath=$rpath''${rpath:+:}$jrePath/lib/$architecture

find  -type f -perm +100 \
    -exec patchelf --interpreter "$(cat $NIX_GCC/nix-support/dynamic-linker)" \
    --set-rpath "$rpath" {} \;

find  -name "*.so" -exec patchelf --set-rpath "$rpath" {} \;
	'';
*/
}

