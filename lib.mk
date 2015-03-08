void :=
space :=$(void) $(void)
comma :=,
hash :=\#
leftparen :=(
rightparen :=)
squote :='
dquote :="
define newline


endef

tmpout ?= t
mode ?= debug

MAKEFLAGS = -s
ifeq ($(v),t)
	override MAKEFLAGS =
endif

.SUFFIXES:
SUFFIXES :=
%.out:
%.a:
%.ln:
%.o:
%: %.o
%.c:
%: %.c
%.ln: %.c
%.o: %.c
%.cc:
%: %.cc
%.o: %.cc
%.C:
%: %.C
%.o: %.C
%.cpp:
%: %.cpp
%.o: %.cpp
%.p:
%: %.p
%.o: %.p
%.f:
%: %.f
%.o: %.f
%.F:
%: %.F
%.o: %.F
%.f: %.F
%.r:
%: %.r
%.o: %.r
%.f: %.r
%.y:
%.ln: %.y
%.c: %.y
%.l:
%.ln: %.l
%.c: %.l
%.r: %.l
%.s:
%: %.s
%.o: %.s
%.S:
%: %.S
%.o: %.S
%.s: %.S
%.mod:
%: %.mod
%.o: %.mod
%.sym:
%.def:
%.sym: %.def
%.h:
%.info:
%.dvi:
%.tex:
%.dvi: %.tex
%.texinfo:
%.info: %.texinfo
%.dvi: %.texinfo
%.texi:
%.info: %.texi
%.dvi: %.texi
%.txinfo:
%.info: %.txinfo
%.dvi: %.txinfo
%.w:
%.c: %.w
%.tex: %.w
%.ch:
%.web:
%.p: %.web
%.tex: %.web
%.sh:
%: %.sh
%.elc:
%.el:
(%): %
%.out: %
%.c: %.w %.ch
%.tex: %.w %.ch
%: %,v
%: RCS/%,v
%: RCS/%
%: s.%
%: SCCS/s.%
.web.p:
.l.r:
.dvi:
.F.o:
.l:
.y.ln:
.o:
.y:
.def.sym:
.p.o:
.p:
.txinfo.dvi:
.a:
.l.ln:
.w.c:
.texi.dvi:
.sh:
.cc:
.cc.o:
.def:
.c.o:
.r.o:
.r:
.info:
.elc:
.l.c:
.out:
.C:
.r.f:
.S:
.texinfo.info:
.c:
.w.tex:
.c.ln:
.s.o:
.s:
.texinfo.dvi:
.el:
.texinfo:
.y.c:
.web.tex:
.texi.info:
.DEFAULT:
.h:
.tex.dvi:
.cpp.o:
.cpp:
.C.o:
.ln:
.texi:
.txinfo:
.tex:
.txinfo.info:
.ch:
.S.s:
.mod:
.mod.o:
.F.f:
.w:
.S.o:
.F:
.web:
.sym:
.f:
.f.o:

mf_host_platform := \
	$(shell uname -s | tr [:upper:] [:lower:] \
		| sed \
			-e 's/linux/tux/' \
		  -e 's/^mingw32.*$$/mingw/' \
			-e 's/^cygwin.*$$/cygwin/' \
			-e 's/^darwin.*$$/osx/' \
			-e 's/macosx/osx/')

mf_host_arch := \
	  $(shell uname -m \
	| sed 's/^i.86$$/x86/' \
	| sed 's/^x86pc$$/x86/' \
	| sed 's/amd64/x64/' \
	| sed 's/x86_64/x64/' \
	| sed 's/x64/x64/' \
	| sed 's/^arm.*$$/arm32/')

ifeq ($(platform),ems)
	override arch = ems
else ifeq ($(arch),ems)
	override platform = ems
else
	platform ?= $(mf_host_platform)
	arch ?= $(mf_host_arch)
endif
mf_target_platform = $(platform)
mf_target_arch = $(arch)

mf_host_tux = $(if $(filter tux,$(mf_host_platform)),t)
mf_host_osx = $(if $(filter osx,$(mf_host_platform)),t)
mf_host_mingw = $(if $(filter mingw,$(mf_host_platform)),t)
mf_host_cygwin = $(if $(filter cygwin,$(mf_host_platform)),t)
mf_host_win = $(if $(mf_host_mingw)$(mf_host_cygwin),t)

mf_host_x86 = $(if $(filter x86,$(mf_host_arch)),t)
mf_host_x64 = $(if $(filter x64,$(mf_host_arch)),t)
mf_host_arm32 = $(if $(filter arm32,$(mf_host_arch)),t)

mf_host_bo_le = $(if $(filter x86 x64 arm32,$(mf_host_arch)),t)
mf_host_bo_be = 

mf_target_tux = $(if $(filter tux,$(mf_target_platform)),t)
mf_target_osx = $(if $(filter osx,$(mf_target_platform)),t)
mf_target_ios = $(if $(filter ios,$(mf_target_platform)),t)
mf_target_rob =
mf_target_mingw = $(if $(filter mingw,$(mf_target_platform)),t)
mf_target_cygwin = $(if $(filter cygwin,$(mf_target_platform)),t)
mf_target_win = $(if $(mf_target_mingw)$(mf_target_cygwin),t)
mf_target_ems = $(if $(filter ems,$(mf_target_platform)),t)

mf_target_x86 = $(if $(filter x86,$(mf_target_arch)),t)
mf_target_x64 = $(if $(filter x64,$(mf_target_arch)),t)
mf_target_arm32 = $(if $(filter arm32,$(mf_target_arch)),t)
mf_target_ems = $(if $(filter ems,$(mf_target_arch)),t)

mf_target_bo_le = $(if $(filter x86 x64 arm32 ems,$(mf_host_arch)),t)
mf_target_bo_be = 

ifneq ($(filter x86 arm32,$(mf_host_arch)),)
	mf_host_ptrsz = 4
else
	mf_host_ptrsz = 8
endif

ifneq ($(filter x86 arm32 ems,$(mf_target_arch)),)
	mf_target_ptrsz = 4
else
	mf_target_ptrsz = 8
endif

#
mf_rootp ?= $(shell pwd)
# mf_rootp ?= .
mf_rootout = $(mf_rootp)/out
mf_outp = $(mf_rootout)/$(mf_target_platform)-$(mf_target_arch)-$(mode)
mf_buildp = $(mf_outp)/build
mf_resultp = $(mf_outp)/result

#
mf_cc_gcc_nm = gcc
mf_cxx_gcc_nm = gcc
mf_ld_cc_gcc_nm = gcc
mf_ld_cxx_gcc_nm = gcc

mf_cc_gcc_inco = -I
mf_cxx_gcc_inco = -I

mf_cc_gcc = gcc
mf_cc_gcc_flags =
mf_cxx_gcc = g++
mf_cxx_gcc_flags = -std=c++11

mf_ld_cc_gcc = $(mf_cc_gcc)
mf_ld_cc_gcc_flags =
mf_ld_cxx_gcc = $(mf_cxx_gcc)
mf_ld_cxx_gcc_flags =

#
mf_cc_clang_nm = clang
mf_cxx_clang_nm = clang
mf_ld_cc_clang_nm = clang
mf_ld_cxx_clang_nm = clang

mf_cc_clang_inco = -I
mf_cxx_clang_inco = -I

mf_cc_clang = clang
mf_cc_clang_flags =
mf_cxx_clang = clang++
mf_cxx_clang_flags = -std=c++11

mf_ld_cc_clang = $(mf_cc_clang)
mf_ld_cc_clang_flags =
mf_ld_cxx_clang = $(mf_cxx_clang)
mf_ld_cxx_clang_flags =

#
mf_cc_ems_nm = ems
mf_cxx_ems_nm = ems
mf_ld_cc_ems_nm = ems
mf_ld_cxx_ems_nm = ems

mf_cc_ems_inco = -I
mf_cxx_ems_inco = -I

mf_cc_ems = emcc
mf_cc_ems_flags =
mf_cxx_ems = em++
mf_cxx_ems_flags = -std=c++11

mf_ld_cxx_ems = $(mf_cxx_ems)
mf_ld_cxx_ems_flags =
mf_ld_cc_ems = $(mf_cc_ems)
mf_ld_cc_ems_flags =

#
mf_java_rtjar = "$(JAVA_HOME)/jre/lib/rt.jar"
mf_java = "$(JAVA_HOME)/bin/java"
mf_javac = "$(JAVA_HOME)/bin/javac"
mf_jar = "$(JAVA_HOME)/bin/jar"

#
ifneq ($(mf_target_ems),t)
	mf_default_cc_nm = $(mf_cc_gcc_nm)
	mf_default_cxx_nm = $(mf_cxx_gcc_nm)
	mf_default_ld_cc_nm = $(mf_ld_cc_gcc_nm)
	mf_default_ld_cxx_nm = $(mf_ld_cxx_gcc_nm)

	mf_default_cc_inco = $(mf_cc_gcc_inco)
	mf_default_cxx_inco = $(mf_cxx_gcc_inco)

	mf_default_cc = $(mf_cc_gcc)
	mf_default_cc_flags = $(mc_cc_gcc_flags)

	mf_default_cxx = $(mf_cxx_gcc)
	mf_default_cxx_flags = $(mf_cxx_gcc_flags)

	mf_default_ld_cc = $(mf_ld_cc_gcc)
	mf_default_ld_cc_flags = $(mf_ld_cc_gcc_flags)

	mf_default_ld_cxx = $(mf_ld_cxx_gcc)
	mf_default_ld_cxx_flags = $(mf_ld_cxx_gcc_flags)
else
	mf_default_cc_nm = $(mf_cc_ems_nm)
	mf_default_cxx_nm = $(mf_cxx_ems_nm)
	mf_default_ld_cc_nm = $(mf_ld_cc_ems_nm)
	mf_default_ld_cxx_nm = $(mf_ld_cxx_ems_nm)

	mf_default_cc_inco = $(mf_cc_ems_inco)
	mf_default_cxx_inco = $(mf_cxx_ems_inco)

	mf_default_cc = $(mf_cc_ems)
	mf_default_cc_flags = $(mc_cc_ems_flags)

	mf_default_cxx = $(mf_cxx_ems)
	mf_default_cxx_flags = $(mf_cxx_ems_flags)

	mf_default_ld_cc = $(mf_ld_cc_ems)
	mf_default_ld_cc_flags = $(mf_ld_cc_ems_flags)

	mf_default_ld_cxx = $(mf_ld_cxx_ems)
	mf_default_ld_cxx_flags = $(mf_ld_cxx_ems_flags)
endif

#
mf_default_java = $(mf_java)
mf_default_javac = $(mf_javac)
mf_default_jar = $(mf_jar)

#
mf_cc_inco =$(foreach _p,$(2),$(mf_cc_$(1)_inco)$(_p))

#
_mf_binname_obj_prefix_nix =
_mf_binname_obj_suffix_nix = .o
_mf_binname_static_prefix_nix = lib
_mf_binname_static_suffix_nix = .a
_mf_binname_dll_prefix_nix = lib
_mf_binname_dll_suffix_nix = .so
_mf_binname_exe_prefix_nix =
_mf_binname_exe_suffix_nix =

_mf_binname_obj_prefix_ems =
_mf_binname_obj_suffix_ems = .bc
_mf_binname_static_prefix_ems = lib
_mf_binname_static_suffix_ems = .bc
_mf_binname_dll_prefix_ems = lib
_mf_binname_dll_suffix_ems = .bc
_mf_binname_exe_prefix_ems =
_mf_binname_exe_suffix_ems = .html
# _mf_binname_obj_prefix_ems =
# _mf_binname_obj_suffix_ems = .o
# _mf_binname_static_prefix_ems = lib
# _mf_binname_static_suffix_ems = .a
# _mf_binname_dll_prefix_ems = lib
# _mf_binname_dll_suffix_ems = .so
# _mf_binname_exe_prefix_ems =
# _mf_binname_exe_suffix_ems = .html

_mf_binname_helper = $(_mf_binname_$(1)_prefix_$(2))$(3)$(_mf_binname_$(1)_suffix_$(2))

mf_binname_obj = $(call _mf_binname_helper,obj,$(1),$(2))
mf_binname_static = $(call _mf_binname_helper,static,$(1),$(2))
mf_binname_dll = $(call _mf_binname_helper,dll,$(1),$(2))
mf_binname_exe = $(call _mf_binname_helper,exe,$(1),$(2))

mf_binname_plat = $(if $(filter tux osx,$(1)),nix,$(1))

mf_anyobjs = $(foreach x,$(1),$(patsubst $(2)/%.$(3),$(4)/%.$(5),$(x)))

mf_cobjs = $(foreach x,$(1),$(patsubst $(2)/%.c,$(3)/$(call $(4),$(call mf_binname_plat,$(5)),%),$(x)))

mf_cppobjs = $(foreach x,$(1),$(patsubst $(2)/%.cpp,$(3)/$(call $(4),$(call mf_binname_plat,$(5)),%),$(x)))

mf_asmext = S

mf_asmobjs = $(foreach x,$(1),$(patsubst $(2)/%.$(mf_asmext),$(3)/$(call $(4),$(call mf_binname_plat,$(5)),%),$(x)))

mf_javaobjs = $(foreach x,$(1),$(patsubst $(2)/%.java,$(3)/%.class,$(x)))

mf_srcdepends = $(shell $(1) -MM -MG $(2) $(3) | perl -pe 's/.*\.o\: //' | perl -pe 's/\\\n//g' | perl -pe 's/  / /g')
#mf_srcdepends = $(shell echo "$(1) -MM -MG $(2) $(3)">>tmp | perl -pe 's/.*\.o\: //' | perl -pe 's/\\\n//g' | perl -pe 's/  / /g')
#mf_srcdepends = $()
#mf_srcdepends = $(shell echo "$(1) $(2)" 1>&2 && g++ -MM $(1) $(2) | perl -pe 's/.*\.o\: //')
#mf_srcdepends = $(shell echo $(2) 1>&2)
#mf_srcdepends2 = $(shell echo $(2) 1>&2)

mf_mkfdbfind = $(shell perl -ne '/^$(1) (=|:=) (.*)\n/ && print $$2' $(2))

define mf_compileobj =
	@echo "Compiling $(@)"
	@mkdir -p $(@D)
	$(1) $(2) -c $(<) -o $(@)
endef

define mf_linkobj =
	@echo "Linking $(@)"
	@mkdir -p $(@D)
	$(1) $(2) $(^) -o $(@)
endef

mf_echot_dep = $(mf_buildp)/__t_$(1)_echot

.SECONDEXPANSION:

define mf_echot =
.PHONY: $(call mf_echot_dep,$(1))_del $(call mf_echot_dep,$(1))_doecho
$(call mf_echot_dep,$(1))_del:
	rm -f $(mf_buildp)/$(call mf_echot_dep,$(1))
$(call mf_echot_dep,$(1))_doecho:
	@echo ""
	@echo $(2)
$(call mf_echot_dep,$(1)): $(call mf_echot_dep,$(1))_doecho
endef
	#touch $(call mf_echot_dep,$(1))

.PHONY: mf_help
mf_help:
	@echo "Type 'make help'."

# :>
ifeq ($(shell hostname),lews)
_mf_tmpout ?= /letmp/$(shell basename $$(pwd))out
else
_mf_tmpout ?= /tmp/$(shell basename $$(pwd))out
endif
ifeq ($(tmpout),t)
mf_mkout = $(shell \
	if [ ! -f $(_mf_tmpout) ] ; then \
		mkdir -p $(_mf_tmpout); \
	fi ; \
	if [ ! -h $(mf_rootout) ] ; then \
		ln -s $(_mf_tmpout) $(mf_rootout); \
	fi ;)

$(_mf_tmpout):
	if [ ! -f $(_mf_tmpout) ] ; then \
		mkdir -p $(_mf_tmpout); \
	fi ;
$(mf_rootout): $(_mf_tmpout)
	if [ ! -h $(mf_rootout) ] ; then \
		ln -s $(_mf_tmpout) $(mf_rootout); \
	fi ;
else
mf_mkout = $(shell \
	if [ ! -f $(mf_rootout) ] ; then \
		mkdir -p $(mf_rootout); \
	fi ;)

$(mf_rootout):
	if [ ! -f $(mf_rootout) ] ; then \
		mkdir -p $(mf_rootout); \
	fi ;
endif

$(mf_outp): $(mf_rootout)
	mkdir -p $(mf_buildp)
	mkdir -p $(mf_resultp)

.PHONY: clean cleanall
clean:
	rm -rf $(mf_outp)/*
	rm -rf $(mf_outp)
cleanall:
	rm -rf $(mf_rootout)/*
	rm -rf $(mf_rootout)

print-%:
	@echo '$($*)'

printvar-%:
	@echo '$(value $*)'

