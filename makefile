#
avnp ?= ./../myavn
override avnp := $(shell realpath -m $(avnp))

#
libmkf ?= $(avnp)/lib.mk
include $(libmkf)

#
buildp ?= $(mf_buildp)/vm
$(eval $(call mf_mkout))
$(eval $(shell mkdir -p $(buildp)))

#
jdkp ?= $(shell realpath -m $(avnp)/../myjdktop/myjdk)
jdkmkf ?= $(jdkp)/makefile
jdkmko ?= \
	noavn=t \
	v=$(v) \
	mf_rootp=$(jdkp) \
	tmpout=$(tmpout) \
	_mf_tmpout=$(_mf_tmpout) \
	arch=$(arch) \
	mode=$(mode)
jdkdomk = (cd $(jdkp) && make -f $(jdkmkf) $(jdkmko) $(1))
jdkdomks = $(shell $(call jdkdomk,$(1)))
jdkmkfdb := $(mf_outp)/jdkmkfdb
$(eval $(shell \
	if   [ ! -f $(jdkmkfdb) ] \
		|| [ $(jdkmkf) -nt $(jdkmkfdb) ] \
		|| [ $(libmkf) -nt $(jdkmkfdb) ] \
		; then \
			$(call jdkdomk,-p$(space)>$(space)$(jdkmkfdb)); \
		fi))
jdkmkfdbprint = $(call mf_mkfdbfind,$(1),$(jdkmkfdb))

#
jaklinextp := $(shell realpath -m $(avnp)/../ext)

#
# env JAVA8_HOME=/nix/store/x7895d271myyax1awkpk25hg5ay3znqg-oraclejdk-8u25 JAVA7_HOME=/nix/store/jjjljshqaamkcwci8w942x4w2mn2f9f2-oraclejdk-7u71 mvn clean verify
jretrolambda = \
	$(mf_java) \
	-Dretrolambda.inputDir=$(1) \
	-Dretrolambda.classpath=$(2) \
	-Dretrolambda.bytecodeVersion=50 \
	-Djava.security.manager -Djava.security.policy=$(jaklinextp)/retrolambda/sec.policy \
	-Xbootclasspath/p:$(jaklinextp)/retrolambda \
	-jar $(jaklinextp)/retrolambda/retrolambda-1.8.1.jar

# TODO: Query from jdkmkfdb.
libzp ?= $(jdkp)/src/java.base/share/native/libzip/zlib-1.2.8

#
cphp := $(mf_rootp)/../cph
cphp_incp := $(cphp)/common_portable_headers/native/inc

#
extp = $(mf_rootp)/../ext
ext_asm = $(extp)/asm

#
src = $(mf_rootp)/src
inc = $(mf_rootp)/include
myinc = $(mf_rootp)/myinc

#
mytools = $(src)/mytools
mytest = $(mf_rootp)/mytest

cpsrc := $(mf_rootp)/classpath

#
jdkincp := $(src)/openjdk $(jdkp)/src/java.base/share/native/include
#jdkincs := $(call mf_cc_inco,$(mf_default_cc_nm),$(jdkincp))

#
commonincp :=
commonincs :=
define _commoninc_add =
commonincp += $(1)
commonincs += $(call mf_cc_inco,$(mf_default_cc_nm),$(1))
endef
commoninc_add = $(eval $(call _commoninc_add,$(1)))

$(call commoninc_add,$(cphp_incp))
$(call commoninc_add,$(jdkincp))
$(call commoninc_add,$(src))
$(call commoninc_add,$(inc))
$(call commoninc_add,$(myinc))

# OPENJDK_TARGET_OS := $(call jdkmkfdbprint,OPENJDK_TARGET_OS)
# OPENJDK_TARGET_OS_API_DIR := $(call jdkmkfdbprint,OPENJDK_TARGET_OS_API_DIR)
# $(call commoninc_add,$(jdkp)/src/java.base/share/native/include)
# $(call commoninc_add,$(jdkp)/src/java.base/$(OPENJDK_TARGET_OS)/native/include)
# $(call commoninc_add,$(jdkp)/src/java.base/$(OPENJDK_TARGET_OS_API_DIR)/native/include)

# $(call commoninc_add,$(jdkp)/src/java.base/share/native/libjava)
# $(call commoninc_add,$(jdkp)/src/java.base/$(OPENJDK_TARGET_OS)/native/libjava)
# $(call commoninc_add,$(jdkp)/src/java.base/$(OPENJDK_TARGET_OS_API_DIR)/native/libjava)

# $(call commoninc_add,$(jdkp)/src/java.base/share/native/libnet)
# $(call commoninc_add,$(jdkp)/src/java.base/$(OPENJDK_TARGET_OS)/native/libnet)
# $(call commoninc_add,$(jdkp)/src/java.base/$(OPENJDK_TARGET_OS_API_DIR)/native/libnet)

#
version := $(shell grep version $(mf_rootp)/gradle.properties | cut -d'=' -f2)

#
host_system := posix
target_system := posix


#----------------------------------------


t_gen_outp = $(mf_resultp)
t_gen_buildp := $(mf_buildp)/gen
$(call commoninc_add,$(t_gen_buildp))
t_gen_outo = $(t_gen_buildp)/$(call mf_binname_exe,$(call mf_binname_plat,$(mf_host_platform)),typegen)

t_gen_ptrsz = $(mf_target_ptrsz)

t_gen_libs += -pthread -lz -ldl

t_gen_flags_opts += -O0 -g3

t_gen_flags_defs += -DPOINTER_SIZE=$(t_gen_ptrsz) -D__STDC_LIMIT_MACROS -DAVIAN_VERSION=\"$(version)\" -DAVIAN_INFO="\"\"" -DUSE_ATOMIC_OPERATIONS -DTARGET_BYTES_PER_WORD=$(mf_target_ptrsz)

t_gen_flags_etc += -Wall -Wextra -Werror -Wunused-parameter -Winit-self -Wno-non-virtual-dtor 
t_gen_flags_etc += -fno-rtti -fno-exceptions -fPIC -fvisibility=hidden

t_gen_flags_inc += $(commonincs) -I$(src)/tools/type-generator

t_gen_flags = $(t_gen_libs) $(t_gen_flags_opts) $(t_gen_flags_defs) $(t_gen_flags_etc) $(t_gen_flags_inc)

t_gen_cxx = $(mf_cxx_gcc)
t_gen_cxx_flags = $(mf_cxx_gcc_flags) $(t_gen_flags)

t_gen_ld = $(mf_ld_cxx_gcc)
t_gen_ld_flags = $(mf_ld_cxx_gcc_flags) $(t_gen_libs)

t_gen_src = \
	$(src)/tools/type-generator/main.cpp \
	$(src)/system/$(host_system).cpp \
	$(wildcard $(src)/system/$(host_system)/*.cpp) \
	$(src)/finder.cpp \
	$(src)/util/arg-parser.cpp

t_gen_objs = $(call mf_cppobjs,$(t_gen_src),$(src),$(t_gen_buildp),mf_binname_obj,$(mf_host_platform))

t_gen_result = \
	$(t_gen_buildp)/type-enums.cpp \
	$(t_gen_buildp)/type-declarations.cpp \
	$(t_gen_buildp)/type-constructors.cpp \
	$(t_gen_buildp)/type-initializations.cpp \
	$(t_gen_buildp)/type-java-initializations.cpp \
	$(t_gen_buildp)/type-name-initializations.cpp \
	$(t_gen_buildp)/type-maps.cpp

$(t_gen_objs): $(t_gen_buildp)/$(call mf_binname_obj,$(call mf_binname_plat,$(mf_host_platform)),%): $$(call mf_srcdepends,$(t_gen_cxx),$(t_gen_cxx_flags),$(src)/%.cpp)
	$(call mf_compileobj,$(t_gen_cxx),$(t_gen_cxx_flags))

$(t_gen_outo): $(t_gen_objs)
	@mkdir -p $(t_gen_buildp)
	$(call mf_linkobj,$(t_gen_ld),$(t_gen_ld_flags))

t_gen_genarg = $(shell echo $(1) | sed -e 's:$(t_gen_buildp)/type-\(.*\)\.cpp:\1:')
$(t_gen_result): %.cpp: $(src)/types.def | $(t_gen_outo)
	@echo "Generating $(@)"
	@mkdir -p $(dir $(@))
	$(t_gen_outo) -cp $(call jdkmkfdbprint,jbasebp) -i $(<) -o $(@) -t $(call t_gen_genarg,$(@))

$(eval $(call mf_echot,gen,"Building t_gen..."))

.PHONY: t_gen
t_gen: | $(call mf_echot_dep,gen) $(t_gen_result)


#----------------------------------------


t_b2h_outp = $(mf_resultp)
t_b2h_outo := $(t_b2h_outp)/bin2hex.jar
t_b2h_buildp = $(mf_buildp)/b2h

# <file> <prefix> <delim> <suffix> <outfile>
t_b2h_run_raw = $(mf_java) -jar $(t_b2h_outo) $(1) $(2) $(3) $(4) $(5)

# <file> <array name> <outfile>
t_b2h_run = $(call t_b2h_run_raw,$(1),"unsigned char $(2)arr[] = {","$(comma)$(space)","};",$(3))

t_b2h_flags = 

t_b2h_javac = $(mf_default_javac)
t_b2h_jar = $(mf_default_jar)

t_b2h_src = $(shell find $(mytools)/bin2hex/src/main/java -name '*.java')

t_b2h_objs = $(call mf_javaobjs,$(t_b2h_src),$(mytools)/bin2hex/src/main/java,$(t_b2h_buildp))

$(t_b2h_objs): $(t_b2h_src)
	@mkdir -p $(t_b2h_buildp)
	$(t_b2h_javac) $(t_b2h_flags) -d $(t_b2h_buildp) $(t_b2h_src)

$(t_b2h_outo): $(t_b2h_objs)
	@echo ""
	@echo "Creating $(@)"
	@mkdir -p $(@D)
	(cd $(t_b2h_buildp) && $(t_b2h_jar) cf0e $(shell realpath -m $(@)) myavn.tools.preprocess.bin2hex.Main .)

$(eval $(call mf_echot,b2h,"Building t_b2h..."))

.PHONY: t_b2h
t_b2h: | $(call mf_echot_dep,b2h) $(t_b2h_outo)

#t_b2h_any: t_b2h
#	$(call t_b2h_run,$(t_b2h_any-file),$(t_b2h_any-name),$(t_b2h_any-out))


#----------------------------------------


t_nama_outp = $(mf_resultp)
t_nama_outo := $(t_nama_outp)/nativemapper.jar
t_nama_buildp = $(mf_buildp)/nama

# <name> <jar> <flags> <cpp files> <stubs>
t_nama_run = $(mf_java) $(t_nama_flags):$(t_nama_outo) -Davn.nama.stubs="$(5)" myavn.tools.preprocess.nativemapper.Main $(1) $(2) "$(3)" "$(4)"

t_nama_flags := -cp $(ext_asm)/asm-all-5.0.3.jar

t_nama_javac = $(mf_default_javac)
t_nama_jar = $(mf_default_jar)

t_nama_src = $(shell find $(mytools)/nativemapper/src/main/java -name '*.java')

t_nama_objs = $(call mf_javaobjs,$(t_nama_src),$(mytools)/nativemapper/src/main/java,$(t_nama_buildp))

$(t_nama_objs): $(t_nama_src)
	@mkdir -p $(t_nama_buildp)
	$(t_nama_javac) $(t_nama_flags) -d $(t_nama_buildp) $(t_nama_src)

$(t_nama_outo): $(t_nama_objs)
	@echo ""
	@echo "Creating $(@)"
	@mkdir -p $(@D)
	(cd $(t_nama_buildp) && $(t_nama_jar) cf0e $(shell realpath -m $(@)) myavn.tools.preprocess.nativemapper.Main .)

$(eval $(call mf_echot,nama,"Building t_nama..."))

.PHONY: t_nama
t_nama: | $(call mf_echot_dep,nama) $(t_nama_outo)

#t_nama_any: t_nama
#	$(call t_nama_run,$(t_nama_any-name),$(t_nama_any-jar),$(t_nama_any-flags),$(t_nama_any-cppfiles)) > $(t_nama_any-out)


#----------------------------------------


t_vm_outp = $(mf_resultp)
$(call commoninc_add,$(t_vm_outp))
#t_vm_outo_static = $(t_vm_outp)/$(call mf_binname_static,$(call mf_binname_plat,$(mf_target_platform)),avian)
#t_vm_outo_dll = $(t_vm_outp)/$(call mf_binname_dll,$(call mf_binname_plat,$(mf_target_platform)),jvm)
#t_vm_outo_exe_static = $(t_vm_outp)/$(call mf_binname_exe,$(call mf_binname_plat,$(mf_target_platform)),avian)
#t_vm_outo_exe_dynamic = $(t_vm_outp)/$(call mf_binname_exe,$(call mf_binname_plat,$(mf_target_platform)),avian-dynamic)
t_vm_buildp = $(mf_buildp)/vm

t_vm_ptrsz = $(mf_target_ptrsz)

t_vm_libs :=

t_vm_flags_opts :=
#t_vm_flags_opts += -fno-rtti -fno-exceptions -fno-omit-frame-pointer -fPIC -fvisibility=hidden
t_vm_flags_opts += -fno-rtti -fno-omit-frame-pointer -fPIC -fvisibility=hidden

t_vm_flags_defs :=
t_vm_flags_defs += -DAVIAN_OPENJDK_SRC -D__STDC_LIMIT_MACROS -D_JNI_IMPLEMENTATION_ -DAVIAN_VERSION=\"$(version)\" -DAVIAN_INFO="\"\"" -DAVIAN_JAVA_HOME=\"\" -DAVIAN_EMBED_PREFIX=\"\" -DTARGET_BYTES_PER_WORD=$(mf_target_ptrsz) -DAVIAN_PROCESS_interpret -DAVIAN_CLASSPATH=\"[classpathJar]\" -DBOOT_CLASSPATH=\"[classpathJar]\" -DAVIAN_TARGET_FORMAT=AVIAN_FORMAT_ELF

t_vm_flags_etc :=
# t_vm_flags_etc += -Wall -Wextra -Werror -Wunused-parameter -Winit-self -Wno-non-virtual-dtor -Wno-unused-function
t_vm_flags_etc += -Wall -Wextra -Wunused-parameter -Winit-self -Wno-non-virtual-dtor -Wno-unused-function -fpermissive

t_vm_flags_inc :=
t_vm_flags_inc += \
	$(commonincs) \
	$(call mf_cc_inco,$(mf_default_cc_nm),$(jdkp)/src/java.base/share/native/libzip/zlib-1.2.8) \
	$(call mf_cc_inco,$(mf_default_cc_nm),$(jdkp)/src/java.base/share/native/libnet) \
	$(call mf_cc_inco,$(mf_default_cc_nm),$(jdkp)/src/java.management/share/native/libmanagement) \
	#

t_vm_flags_defs += -D_myavn_disablejvminitargs

ifneq ($(mf_target_ems),t)
	t_vm_libs += -ldl -pthread
endif

ifeq ($(mf_target_ems),t)
	# Nmib
	t_vm_flags_opts += -s TOTAL_MEMORY=$(shell expr 432 \* 1024 \* 1024)
	#t_vm_flags_opts += -s PRECISE_F32=1
	#t_vm_flags_opts += -s PRECISE_F32=2

	# TODO: DISABLE_EXCEPTION_CATCHING, EXCEPTION_CATCHING_WHITELIST.
endif

ifneq ($(mf_target_ems),t)
	t_vm_flags_defs += -DUSE_ATOMIC_OPERATIONS 
endif

ifeq ($(mf_target_ems),t)
	override vm_unwindex = t
	override vm_mt = f
endif

ifeq ($(mf_target_x64),t)
	t_vm_flags_defs += -DAVIAN_TARGET_ARCH=AVIAN_ARCH_X86_64
	t_vm_flags_opts += -m64
else ifeq ($(mf_target_x86),t)
	t_vm_flags_defs += -DAVIAN_TARGET_ARCH=AVIAN_ARCH_X86
	t_vm_flags_opts += -m32
else ifeq ($(mf_target_arm32),t)
	t_vm_flags_defs += -DAVIAN_TARGET_ARCH=AVIAN_ARCH_ARM
else ifeq ($(mf_target_ems),t)
	t_vm_flags_defs += -DAVIAN_EMS -DAVIAN_TARGET_ARCH=AVIAN_ARCH_EMS
	t_vm_flags_etc += -Wno-warn-absolute-paths
	# t_vm_flags_opts += -s DLOPEN_SUPPORT=1 -s DISABLE_EXCEPTION_CATCHING=1
	# t_vm_flags_opts += -s ERROR_ON_UNDEFINED_SYMBOLS=1
endif

ifeq ($(mode),debug)
	#t_vm_flags_defs += -D_myavn_log_finder
	#t_vm_flags_defs += -D_myavn_log_sysverbose
	#t_vm_flags_defs += -D_myavn_log_methresolve
	#t_vm_flags_defs += -D_myavn_log_eenativeinvoke

	vm_assert_stack ?= t
	vm_log_finder ?= t
	vm_log_sysverbose ?= t

	ifeq ($(mf_target_ems),t)
		t_vm_flags_opts += -O0
		t_vm_flags_opts += --js-opts 0
		t_vm_flags_opts += -g
		t_vm_flags_opts += -g4
		t_vm_flags_opts += -s ASSERTIONS=2
		t_vm_flags_opts += -s DEMANGLE_SUPPORT=1
		#t_vm_flags_opts += -s SAFE_HEAP=1
	else
		t_vm_flags_opts += -O0
		t_vm_flags_opts += -g3
	endif
else ifeq ($(mode),profile)
	ifeq ($(mf_target_ems),t)
		t_vm_flags_opts += --profiling -O0 -g -g4
		t_vm_flags_opts += -s DEMANGLE_SUPPORT=1
	else
		t_vm_flags_opts += -O0 -g3
	endif
else ifeq ($(mode),light)
	vm_assert_stack ?= t

	ifeq ($(mf_target_ems),t)
		#t_vm_flags_opts += -O0
		t_vm_flags_opts += -O2
		#t_vm_flags_opts += --js-opts 0
		#t_vm_flags_opts += -g
		# Var and func names.
		#t_vm_flags_opts += -g2
		t_vm_flags_opts += --emit-symbol-map
		t_vm_flags_opts += -s ASSERTIONS=2
		t_vm_flags_opts += -s DEMANGLE_SUPPORT=1
		#t_vm_flags_opts += -s SAFE_HEAP=1
		#t_vm_flags_opts += -s SAFE_DYNCALLS=1
	endif
else ifeq ($(mode),fast)
	t_vm_flags_opts += -DNDEBUG
	t_vm_flags_defs += -DNDEBUG

	ifeq ($(mf_target_ems),t)
		t_vm_flags_opts += -O3
		t_vm_flags_opts += -g0
		t_vm_flags_opts += --emit-symbol-map
		t_vm_flags_opts += -s ASSERTIONS=0
		t_vm_flags_opts += -s DEMANGLE_SUPPORT=0
		t_vm_flags_opts += -s SAFE_HEAP=0
	else
		t_vm_flags_opts += -O3
		t_vm_flags_opts += -g0
	endif
endif

ifeq ($(lto),t)
	ifeq ($(mf_target_ems),t)
		t_vm_flags_opts += --llvm-lto 3
	else
		t_vm_flags_opts += -flto
	endif
endif
ifeq ($(vm_unwindex),t)
	t_vm_flags_defs += -D_myavn_unwindex
	t_vm_flags_asm += -D_myavn_unwindex

	ifeq ($(mf_target_ems),t)
		t_vm_flags_opts += -s DISABLE_EXCEPTION_CATCHING=0
	endif
else
	t_vm_flags_opts += -fno-exceptions
endif
ifeq ($(vm_mt),f)
	t_vm_flags_defs += -D_myavn_mt=0
else
	t_vm_flags_defs += -D_myavn_mt=1
endif
ifeq ($(vm_assert_stack),t)
	t_vm_flags_defs += -D_myavn_assertstack
endif
ifeq ($(vm_log_eeins),t)
	t_vm_flags_defs += -D_myavn_log_eeins
endif
ifeq ($(vm_log_eestack),t)
	t_vm_flags_defs += -D_myavn_log_eedebugstack
endif
ifeq ($(vm_log_eenativeinvoke),t)
	t_vm_flags_defs += -D_myavn_log_eenativeinvoke
endif
ifeq ($(vm_log_finder),t)
	t_vm_flags_defs += -D_myavn_log_finder
endif
ifeq ($(vm_log_sysverbose),t)
	t_vm_flags_defs += -D_myavn_log_sysverbose
endif
ifeq ($(vm_log_methresolve),t)
	t_vm_flags_defs += -D_myavn_log_methresolve
endif

t_vm_flags = $(t_vm_libs) $(t_vm_flags_opts) $(t_vm_flags_defs) $(t_vm_flags_etc) $(t_vm_flags_inc)

t_vm_flags_asm += -DTARGET_BYTES_PER_WORD=$(mf_target_ptrsz) -I$(src)

ifeq ($(mode),debug)
	t_vm_flags_asm += -O0 -g3
endif

ifeq ($(mf_target_x64),t)
	t_vm_flags_asm += -m64
endif

t_vm_cc := $(mf_default_cc)
t_vm_cc_flags := $(mf_default_cc_flags) $(t_vm_flags)

t_vm_cxx := $(mf_default_cxx)
t_vm_cxx_flags := $(mf_default_cxx_flags) $(t_vm_flags)

t_vm_asm := $(mf_default_cc)
t_vm_asm_flags := $(t_vm_flags_asm)

t_vm_ld := $(mf_default_ld_cxx)
t_vm_ld_flags := $(mf_ld_cxx_gcc_flags) $(t_vm_flags)
#t_vm_ld_flags = $(mf_ld_cxx_gcc_flags) $(t_vm_flags) -rdynamic

ifeq ($(mf_target_x64),t)
	t_vm_ld_flags += -m64
else ifeq ($(mf_target_ems),t)
	t_vm_ld_flags += $(t_vm_flags)
endif

t_vm_src := \
	$(src)/system/$(target_system).cpp \
	$(wildcard $(src)/system/$(target_system)/*.cpp) \
	\
	$(src)/finder.cpp \
	$(src)/machine.cpp \
	$(src)/util.cpp \
	$(src)/heap/heap.cpp \
	$(src)/interpret.cpp \
	$(src)/jnienv.cpp \
	$(src)/process.cpp \
	\
	$(wildcard $(src)/myavn/*.cpp) \
	#
t_vm_src_asm :=
t_vm_src_asm := $(if $(mf_target_x86),$(src)/i386.$(mf_asmext))
t_vm_src_asm := $(if $(mf_target_x64),$(src)/x86_64.$(mf_asmext))
t_vm_src_cp1 := \
	$(src)/builtin.cpp \
	$(src)/classpath-openjdk.cpp \
	\
	$(src)/openjdk/stubs.cpp \
	#
t_vm_src_cp2 := \
	#$(src)/openjdk/my_net_util.c \
	#$(src)/openjdk/my_management.c \
	#
# ifeq ($(mf_target_ios),t)
# 	t_vm_src_cp2 += $(src)/openjdk/my_java_props_macosx.c
# endif
#t_vm_src_cp3 := \
#	$(call jdkmkfdbprint,t_img_r_h) \
#	$(call jdkmkfdbprint,t_img_r_m) \
#	#
#t_vm_src_cpall := $(t_vm_src_cp1)

t_vm_src_main := $(src)/main.cpp

t_vm_objs_cpp := $(call mf_cppobjs,$(t_vm_src),$(src),$(t_vm_buildp),mf_binname_obj,$(mf_target_platform))
t_vm_objs_asm := $(call mf_asmobjs,$(t_vm_src_asm),$(src),$(t_vm_buildp),mf_binname_obj,$(mf_target_platform))
t_vm_objs_cp1 := $(call mf_cppobjs,$(t_vm_src_cp1),$(src),$(t_vm_buildp),mf_binname_obj,$(mf_target_platform))
# t_vm_objs_cp2 := $(call mf_cobjs,$(t_vm_src_cp2),$(src)/openjdk,$(t_vm_buildp),mf_binname_obj,$(mf_target_platform))
# t_vm_objs_cp3 := \
# 	$(call mf_cppobjs,$(src)/myavn/jdkjar.cpp,$(src)/myavn,$(t_vm_buildp),mf_binname_obj,$(mf_target_platform)) \
	#
t_vm_objs_all := $(t_vm_objs_cpp) $(if $(mf_target_ems),,$(t_vm_objs_asm)) $(t_vm_objs_gen) $(t_vm_objs_cp1) $(t_vm_objs_cp2) $(t_vm_objs_cp3)

t_vm_objs_main := $(call mf_cppobjs,$(t_vm_src_main),$(src),$(t_vm_buildp),mf_binname_obj,$(mf_target_platform))

t_vm_commondep := $(t_vm_objs_all)
t_vm_cpdep := $(t_vm_objs_cp3)
vmobjs := $(call jdkmkfdbprint,jdkobjs) $(t_vm_commondep)
cpobjs := $(t_vm_cpdep)

# Workaround on automatic dep gen.
type-%.cpp: ;

$(t_vm_objs_cpp): $(t_vm_buildp)/$(call mf_binname_obj,$(call mf_binname_plat,$(mf_target_platform)),%): $$(call mf_srcdepends,$(t_vm_cxx),$(t_vm_flags_defs)$(space)$(t_vm_flags_inc),$(src)/%.cpp)
	$(call mf_compileobj,$(t_vm_cxx),$(t_vm_cxx_flags))

$(t_vm_objs_asm): $(t_vm_buildp)/$(call mf_binname_obj,$(call mf_binname_plat,$(mf_target_platform)),%): $$(call mf_srcdepends,$(t_vm_cxx),$(t_vm_asm_flags),$(src)/%.$(mf_asmext))
	$(call mf_compileobj,$(t_vm_asm),$(t_vm_asm_flags))

$(t_vm_objs_cp1): $(t_vm_buildp)/$(call mf_binname_obj,$(call mf_binname_plat,$(mf_target_platform)),%): $$(call mf_srcdepends,$(t_vm_cxx),$(t_vm_flags_defs)$(space)$(t_vm_flags_inc),$(src)/%.cpp)
	$(call mf_compileobj,$(t_vm_cxx),$(t_vm_cxx_flags))

# $(t_vm_objs_cp2): $(t_vm_buildp)/$(call mf_binname_obj,$(call mf_binname_plat,$(mf_target_platform)),%): $$(call mf_srcdepends,$(t_vm_cc),$(t_vm_flags_defs)$(space)$(t_vm_flags_inc),$(src)/openjdk/%.c)
# 	$(call mf_compileobj,$(t_vm_cc),$(t_vm_cc_flags))

# $(t_vm_objs_cp3): $(t_vm_buildp)/$(call mf_binname_obj,$(call mf_binname_plat,$(mf_target_platform)),%): $$(call mf_srcdepends,$(t_vm_cxx),$(t_vm_flags_defs)$(space)$(t_vm_flags_inc),$(src)/myavn/%.cpp)
# 	$(call mf_compileobj,$(t_vm_cxx),$(t_vm_cxx_flags))

# $(t_vm_outo_static): $(t_vm_commondep)
# 	ar cru $(@) $(t_vm_objs_all)
# 	ranlib $(@)

# $(t_vm_outo_dll): $(t_vm_commondep)
# 	$(ld) $(^) -shared -o $(@)
# 	strip --strip-all $(@)

# $(t_vm_outo_exe_dynamic): $(t_vm_outo_dll)
# 	;

# $(t_vm_objs_main): $(t_vm_buildp)/$(call mf_binname_obj,$(call mf_binname_plat,$(mf_target_platform)),%): $$(call mf_srcdepends,$(t_vm_cxx_flags),$(src)/%.cpp)
# 	$(call mf_compileobj,$(t_vm_cxx),$(t_vm_cxx_flags))

# $(t_vm_outo_exe_static): $(if $(mf_target_ems),$(t_z_objs)) $(t_vm_commondep) $(t_vm_objs_main)
# 	$(call mf_linkobj,$(t_vm_ld),$(t_vm_ld_flags))

$(eval $(call mf_echot,vm,"Building t_vm..."))

.PHONY: t_vm
t_vm: | $(call mf_echot_dep,vm) t_gen $(t_vm_commondep) $(t_vm_cpdep)
# t_vm: | $(if $(mf_target_ems),t_z) $(call mf_echot_dep,vm) $(t_vm_outo_exe_static) $(if $(t_vm_outo_exe_dynamic),)


#----------------------------------------


ifeq ($(mytests),t)


t_myt_stupid_outp = $(mf_resultp)
t_myt_stupid_buildp = $(mf_buildp)/myt_stupid
t_myt_stupid_outo = $(t_myt_stupid_outp)/$(call mf_binname_exe,$(call mf_binname_plat,$(mf_target_platform)),myt_stupid)

_t_myt_stupid_bd = $(mytest)/stupid
_t_myt_stupid_nm = mymain

t_myt_stupid_tests = \
	assignableprimitive \
	benchadouble \
	benchafloat \
	benchaint \
	benchalong \
	benchloop10k \
	lambda \
	invoke1 \
	exvtbl \
	#

t_myt_stupid_javac = $(mf_default_javac)
t_myt_stupid_jar = $(mf_default_jar)

t_myt_stupid_src = $(foreach dir,$(t_myt_stupid_tests),$(_t_myt_stupid_bd)/$(dir)/$(_t_myt_stupid_nm).java)

t_myt_stupid_h = $(call mf_anyobjs,$(t_myt_stupid_src),$(_t_myt_stupid_bd),java,$(t_myt_stupid_buildp),h)

t_myt_stupid_flags_inc += -I$(t_myt_stupid_buildp)/..

t_myt_stupid_cxx = $(t_vm_cxx)
t_myt_stupid_cxx_flags = $(t_vm_cxx_flags) $(t_myt_stupid_flags_inc)

t_myt_stupid_ld = $(t_vm_ld)
t_myt_stupid_ld_flags = $(t_vm_ld_flags)

t_myt_stupid_main_src = $(_t_myt_stupid_bd)/mytstupid.cpp

t_myt_stupid_main_objs = $(call mf_cppobjs,$(t_myt_stupid_main_src),$(_t_myt_stupid_bd),$(t_myt_stupid_buildp),mf_binname_obj,$(mf_target_platform))

define _t_my_stupid_tgen =
$(t_myt_stupid_buildp)/$(1)/$(_t_myt_stupid_nm).class: $(_t_myt_stupid_bd)/$(1)/$(_t_myt_stupid_nm).java
	@mkdir -p $(t_myt_stupid_buildp)/$(1)
	$(t_myt_stupid_javac) -d $(t_myt_stupid_buildp)/$(1) -cp $(call jdkmkfdbprint,jbasebp) $$(<)
	$(call jretrolambda,$(t_myt_stupid_buildp)/$(1),$(t_myt_stupid_buildp)/$(1):$(call jdkmkfdbprint,jbasebp))

$(t_myt_stupid_buildp)/$(1)/$(_t_myt_stupid_nm).jar: $(t_myt_stupid_buildp)/$(1)/$(_t_myt_stupid_nm).class
	rm -f $$(@)
	(cd $$(dir $$(@)) && $(t_myt_stupid_jar) cf0 $$(shell realpath -m $$(@)) `find -type f -and -not -name '*.jar' -and -not -name '*.h'`)

$(t_myt_stupid_buildp)/$(1)/$(_t_myt_stupid_nm).h: $(t_myt_stupid_buildp)/$(1)/$(_t_myt_stupid_nm).jar
	rm -f $$(@)
	rm -f $$(@).tmp
	$$(call t_b2h_run,$$(<),$(1),$$(@).tmp)
	mv $$(@).tmp $$(@)
endef

$(foreach t,$(t_myt_stupid_tests),$(eval $(call _t_my_stupid_tgen,$(t))))

# Workaround on automatic dep gen.
# Ugh.
#ifeq ($(mf_target_ems),t)
cp.h: ;
cp.inc.cpp: ;
$(t_myt_stupid_buildp)/../myt_stupid/%.h: ;
myt_stupid/%.h: ;
#endif

$(t_myt_stupid_main_objs): $(t_myt_stupid_buildp)/$(call mf_binname_obj,$(call mf_binname_plat,$(mf_target_platform)),%): $$(call mf_srcdepends,$(t_myt_stupid_cxx),$(t_vm_flags_defs)$(space)$(t_vm_flags_inc)$(space)$(t_myt_stupid_flags_inc),$(_t_myt_stupid_bd)/%.cpp) $(t_myt_stupid_h)
	$(call mf_compileobj,$(t_myt_stupid_cxx),$(t_myt_stupid_cxx_flags))

$(t_myt_stupid_outo): $(t_myt_stupid_main_objs) $(vmobjs) $(cpobjs)
	$(call mf_linkobj,$(t_myt_stupid_ld),$(t_myt_stupid_ld_flags))

$(eval $(call mf_echot,myt_stupid,"Building t_myt_stupid..."))

.PHONY: t_myt_stupid
t_myt_stupid: | t_vm $(call mf_echot_dep,myt_stupid) $(t_myt_stupid_outo)


#----------------------------------------


t_myt_jni_outp = $(mf_resultp)
t_myt_jni_buildp = $(mf_buildp)/myt_jni
t_myt_jni_outo = $(t_myt_jni_outp)/$(call mf_binname_exe,$(call mf_binname_plat,$(mf_target_platform)),myt_jni)

_t_myt_jni_bd = $(mytest)/jni

t_myt_jni_javac = $(mf_default_javac)
t_myt_jni_jar = $(mf_default_jar)

t_myt_jni_src = $(_t_myt_jni_bd)/JNI.java

t_myt_jni_h = $(t_myt_jni_buildp)/JNI.gen.h

t_myt_jni_cpp = $(t_myt_jni_buildp)/JNI.gen.inc.cpp

t_myt_jni_flags_inc += -I$(t_myt_jni_buildp)/..

t_myt_jni_cxx = $(t_vm_cxx)
t_myt_jni_cxx_flags = $(t_vm_cxx_flags) $(t_myt_jni_flags_inc)

t_myt_jni_ld = $(t_vm_ld)
t_myt_jni_ld_flags = $(t_vm_ld_flags)

t_myt_jni_main_src = $(_t_myt_jni_bd)/jni.cpp $(_t_myt_jni_bd)/main.cpp

t_myt_jni_main_objs = $(call mf_cppobjs,$(t_myt_jni_main_src),$(_t_myt_jni_bd),$(t_myt_jni_buildp),mf_binname_obj,$(mf_target_platform))

$(t_myt_jni_buildp)/JNI.class: $(t_myt_jni_src)
	@mkdir -p $(t_myt_jni_buildp)/
	$(t_myt_jni_javac) -d $(t_myt_jni_buildp)/ $(<)

$(t_myt_jni_buildp)/JNI.jar: $(t_myt_jni_buildp)/JNI.class
	@mkdir -p $(t_myt_jni_buildp)/
	(cd $(dir $(@)) && $(t_myt_jni_jar) cf0 $(shell realpath -m $(@)) JNI.class)

$(t_myt_jni_h): $(t_myt_jni_buildp)/JNI.jar $(t_b2h_outo)
	@mkdir -p $(t_myt_jni_buildp)/
	@rm -f $(@)
	@rm -f $(@).tmp
	$(call t_b2h_run,$(<),JNI,$(@).tmp)
	mv $(@).tmp $(@)

$(t_myt_jni_cpp): $(t_myt_jni_buildp)/JNI.jar $(t_myt_jni_main_src) $(t_nama_outo)
	@mkdir -p $(t_myt_jni_buildp)/
	@rm -f $(@)
	@rm -f $(@).tmp
	$(call t_nama_run,JNI,$(t_myt_jni_buildp)/JNI.jar,$(t_vm_flags_defs)$(space)$(t_vm_flags_inc)$(space)$(t_myt_jni_flags_inc),$(_t_myt_jni_bd)/jni.cpp) > $(@).tmp
	mv $(@).tmp $(@)

# Workaround on automatic dep gen.
# Ugh.
#ifeq ($(mf_target_ems),t)
myt_jni/%.gen.h: ;
myt_jni/%.gen.inc.cpp: ;
#endif

$(t_myt_jni_main_objs): $(t_myt_jni_buildp)/$(call mf_binname_obj,$(call mf_binname_plat,$(mf_target_platform)),%): $$(call mf_srcdepends,$(t_myt_jni_cxx),$(t_vm_flags_defs)$(space)$(t_vm_flags_inc)$(space)$(t_myt_jni_flags_inc),$(_t_myt_jni_bd)/%.cpp) $(t_myt_jni_h) $(t_myt_jni_cpp)
	@mkdir -p $(t_myt_jni_buildp)
	$(call mf_compileobj,$(t_myt_jni_cxx),$(t_myt_jni_cxx_flags))

$(t_myt_jni_outo): $(t_myt_jni_main_objs) $(vmobjs) $(cpobjs)
	$(call mf_linkobj,$(t_myt_jni_ld),$(t_myt_jni_ld_flags))

$(eval $(call mf_echot,myt_jni,"Building t_myt_jni..."))

.PHONY: t_myt_jni
t_myt_jni: | t_vm $(call mf_echot_dep,myt_jni) $(t_myt_jni_outo)


#----------------------------------------


t_myt_methbench_outp = $(mf_resultp)
t_myt_methbench_buildp = $(mf_buildp)/myt_methbench
t_myt_methbench_outo = $(t_myt_methbench_outp)/$(call mf_binname_exe,$(call mf_binname_plat,$(mf_target_platform)),myt_methbench)

_t_myt_methbench_bd = $(mytest)/methbench

t_myt_methbench_javac = $(mf_default_javac)
t_myt_methbench_jar = $(mf_default_jar)

t_myt_methbench_src = $(_t_myt_methbench_bd)/JNI.java

t_myt_methbench_h = $(t_myt_methbench_buildp)/JNI.gen.h

t_myt_methbench_cpp = $(t_myt_methbench_buildp)/JNI.gen.inc.cpp

t_myt_methbench_flags_inc += -I$(_t_myt_methbench_bd) -I$(t_myt_methbench_buildp)/..

t_myt_methbench_cxx = $(t_vm_cxx)
t_myt_methbench_cxx_flags = $(t_vm_cxx_flags) $(t_myt_methbench_flags_inc)

t_myt_methbench_ld = $(t_vm_ld)
t_myt_methbench_ld_flags = $(t_vm_ld_flags)

t_myt_methbench_main_src = $(_t_myt_methbench_bd)/jni.cpp $(_t_myt_methbench_bd)/main.cpp

t_myt_methbench_main_objs = $(call mf_cppobjs,$(t_myt_methbench_main_src),$(_t_myt_methbench_bd),$(t_myt_methbench_buildp),mf_binname_obj,$(mf_target_platform))

$(t_myt_methbench_buildp)/JNI.class: $(t_myt_methbench_src)
	@mkdir -p $(t_myt_methbench_buildp)/
	$(t_myt_methbench_javac) -d $(t_myt_methbench_buildp)/ $(<)

$(t_myt_methbench_buildp)/JNI.jar: $(t_myt_methbench_buildp)/JNI.class
	@mkdir -p $(t_myt_methbench_buildp)/
	(cd $(dir $(@)) && $(t_myt_methbench_jar) cf0 $(shell realpath -m $(@)) JNI.class)

$(t_myt_methbench_h): $(t_myt_methbench_buildp)/JNI.jar $(t_b2h_outo)
	@mkdir -p $(t_myt_methbench_buildp)/
	@rm -f $(@)
	@rm -f $(@).tmp
	$(call t_b2h_run,$(<),JNI,$(@).tmp)
	mv $(@).tmp $(@)

$(t_myt_methbench_cpp): $(t_myt_methbench_buildp)/JNI.jar $(t_myt_methbench_main_src) $(t_nama_outo)
	@mkdir -p $(t_myt_methbench_buildp)/
	@rm -f $(@)
	@rm -f $(@).tmp
	$(call t_nama_run,JNI,$(t_myt_methbench_buildp)/JNI.jar,$(t_vm_flags_defs)$(space)$(t_vm_flags_inc)$(space)$(t_myt_methbench_flags_inc),$(_t_myt_methbench_bd)/jni.cpp) > $(@).tmp
	mv $(@).tmp $(@)

# Workaround on automatic dep gen.
# Ugh.
#ifeq ($(mf_target_ems),t)
myt_methbench/%.gen.h: ;
myt_methbench/%.gen.inc.cpp: ;
#endif

$(t_myt_methbench_main_objs): $(t_myt_methbench_buildp)/$(call mf_binname_obj,$(call mf_binname_plat,$(mf_target_platform)),%): $$(call mf_srcdepends,$(t_myt_methbench_cxx),$(t_vm_flags_defs)$(space)$(t_vm_flags_inc)$(space)$(t_myt_methbench_flags_inc),$(_t_myt_methbench_bd)/%.cpp) $(t_myt_methbench_h) $(t_myt_methbench_cpp)
	@mkdir -p $(t_myt_methbench_buildp)/
	$(call mf_compileobj,$(t_myt_methbench_cxx),$(t_myt_methbench_cxx_flags))

$(t_myt_methbench_outo): $(t_myt_methbench_main_objs) $(vmobjs) $(cpobjs)
	$(call mf_linkobj,$(t_myt_methbench_ld),$(t_myt_methbench_ld_flags))

$(eval $(call mf_echot,myt_methbench,"Building t_myt_methbench..."))

.PHONY: t_myt_methbench
t_myt_methbench: | t_vm $(call mf_echot_dep,myt_methbench) $(t_myt_methbench_outo)


endif #ifeq ($(mytests),t)


#----------------------------------------


.PHONY: targets
targets: | \
	t_vm \
	$(if $(mytests),t_myt_stupid) \
	$(if $(mytests),t_myt_jni) \
	$(if $(mytests),t_myt_methbench) \
	;

.PHONY: all
all: | $(mf_rootp)/makefile $(mf_outp) targets ;

.PHONY: f
f: | clean all ;

define HELP
	`make all`
	`make f`        -- forced rebuild
	`make clean`    -- delete current build
	`make cleanall` -- delete all the builds
	v={t}           -- be verbose
	tmpout={t}
	lto={t}
	vm_unwindex={t}
	vm_mt={f}
	platform={tux,ems} 
	arch={x86,x64,arm32,ems}
	mode={debug,profile,fast}
	mytests={t}
	vm_assert_stack|vm_log_eeins|vm_log_eestack={t}
	vm_log_eenativeinvoke|vm_log_finder|vm_log_sysverbose={t}
	vm_log_methresolve={t}
endef
export HELP
.PHONY: help
help:
	@echo "$$HELP"

