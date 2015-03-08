avnp ?= ./../../..
override avnp := $(shell realpath -m $(avnp))

libmkf ?= $(avnp)/lib.mk
include $(libmkf)

buildp = $(mf_buildp)/jake2
$(eval $(call mf_mkout))
$(eval $(shell mkdir -p $(buildp)))

include $(avnp)/avnmko.mk

t_b2h_outo := $(call avnmkfdbprint,t_b2h_outo)
$(eval t_b2h_run_raw = $(call avnmkfdbprint,t_b2h_run_raw))
$(eval t_b2h_run = $(call avnmkfdbprint,t_b2h_run))

t_nama_flags := $(call avnmkfdbprint,t_nama_flags)
t_nama_outo := $(call avnmkfdbprint,t_nama_outo)
$(eval t_nama_run = $(call avnmkfdbprint,t_nama_run))

jdkmkfdb = $(call avnmkfdbprint,jdkmkfdb)
jdkmkfdbprint = $(call mf_mkfdbfind,$(1),$(jdkmkfdb))


#----------------------------------------


# g++ -shared -Wall -fpic \
# -std=c++11 \
# -I$JAVA_HOME/include/ -I$JAVA_HOME/include/linux/ \
# -Iext/cph/common_portable_headers/native/inc \
# -lSDL -lGL \
# mytest/jbox2d/src/main/native/gl.cpp \
# -o libjbox2d.so

t_jb2d_outp = $(mf_resultp)/jake2
t_jb2d_buildp = $(mf_buildp)/jake2
t_jb2d_buildp_j = $(t_jb2d_buildp)/j
t_jb2d_buildp_cpp = $(t_jb2d_buildp)/cpp
t_jb2d_outo = $(t_jb2d_outp)/$(call mf_binname_exe,$(call mf_binname_plat,$(mf_target_platform)),jb2d)
t_jb2d_outjar = $(t_jb2d_outp)/jbox2d.jar
t_jb2d_outh = $(t_jb2d_outp)/jbox2djar.gen.h
t_jb2d_outcpp = $(t_jb2d_outp)/gl.gen.inc.cpp

t_jb2d_srcp = $(mf_rootp)/src
t_jb2d_srcp_j = $(t_jb2d_srcp)/java
t_jb2d_srcp_cpp = $(t_jb2d_srcp)/native

t_jb2d_javac = $(mf_default_javac)
t_jb2d_jar = $(mf_default_jar)

t_jb2d_src_j = $(shell find $(t_jb2d_srcp_j) -name '*.java')

t_jb2d_objs_j = $(call mf_javaobjs,$(t_jb2d_src_j),$(t_jb2d_srcp_j),$(t_jb2d_buildp_j))

t_jb2d_flags_libs := $(call avnmkfdbprint,t_vm_libs) -lSDL -lGL

t_jb2d_flags_opts := $(call avnmkfdbprint,t_vm_flags_opts)

t_jb2d_flags_defs := $(call avnmkfdbprint,t_vm_flags_defs)

t_jb2d_flags_inc := $(call avnmkfdbprint,t_vm_flags_inc) -I$(t_jb2d_outp)/..

t_jb2d_flags_etc := $(call avnmkfdbprint,t_vm_flags_etc)

ifeq ($(mf_target_ems),t)
	t_jb2d_flags_etc += -s GL_FFP_ONLY=1 -s LEGACY_GL_EMULATION=1 -s GL_UNSAFE_OPTS=0
endif

t_jb2d_flags := $(t_jb2d_flags_libs) $(t_jb2d_flags_opts) $(t_jb2d_flags_defs) $(t_jb2d_flags_inc) $(t_jb2d_flags_etc)

t_jb2d_cxx := $(call avnmkfdbprint,t_vm_cxx)
t_jb2d_cxx_flags := $(mf_default_cxx_flags) $(t_jb2d_flags)

t_jb2d_ld := $(call avnmkfdbprint,t_vm_ld)
t_jb2d_ld_flags := $(mf_ld_cxx_gcc_flags) $(call avnmkfdbprint,t_vm_ld_flags) $(t_jb2d_flags)

t_jb2d_src_cpp := $(shell find $(t_jb2d_srcp_cpp) -name '*.cpp')
t_jb2d_src_cpp_jni := $(shell find $(t_jb2d_srcp_cpp) -name '*.jni.cpp')

t_jb2d_objs_cpp = $(call mf_cppobjs,$(t_jb2d_src_cpp),$(t_jb2d_srcp_cpp),$(t_jb2d_buildp_cpp),mf_binname_obj,$(mf_target_platform))

# Workaround on automatic dep gen.
# Ugh.
ifeq ($(mf_target_ems),t)
cp.inc.cpp: ;
cp.h: ;
%.gen.inc.cpp: ;
%.gen.h: ;
endif

$(t_jb2d_outjar): $(t_jb2d_src_j)
	@mkdir -p $(t_jb2d_outp)
	@mkdir -p $(t_jb2d_buildp_j)
	$(t_jb2d_javac) -d $(t_jb2d_buildp_j) -bootclasspath $(call jdkmkfdbprint,t_img_r_jar) $(t_jb2d_src_j)
	@echo ""
	@echo "Creating $(@)"
	(cd $(t_jb2d_buildp_j) && $(t_jb2d_jar) cf0 $(shell realpath -m $(@)) .)

$(t_jb2d_outh): $(t_b2h_outo) $(t_jb2d_outjar)
	@echo ""
	@echo "Generating $(@)"
	@mkdir -p $(t_jb2d_outp)
	rm -f $(@)
	rm -f $(@).tmp
	$(call t_b2h_run,$(t_jb2d_outjar),jbox2djar,$(@).tmp)
	mv $(@).tmp $(@)

# $(t_jb2d_outcpp): $(t_nama_outo) $(t_jb2d_outjar) $(t_jb2d_src_cpp_jni)
# 	@mkdir -p $(t_jb2d_outp)
# 	@echo ""
# 	@echo "Generating $(@)"
# 	$(call t_nama_run,gl,$(t_jb2d_outjar),$(t_jb2d_flags_defs)$(space)$(t_jb2d_flags_inc),$(t_jb2d_src_cpp_jni)) > $(@)

$(t_jb2d_objs_cpp): $(t_jb2d_buildp_cpp)/$(call mf_binname_obj,$(call mf_binname_plat,$(mf_target_platform)),%): $$(call mf_srcdepends,$(t_jb2d_cxx),$(t_jb2d_flags_defs)$(space)$(t_jb2d_flags_inc),$(t_jb2d_srcp_cpp)/%.cpp)
	@mkdir -p $(t_jb2d_buildp_cpp)
	$(call mf_compileobj,$(t_jb2d_cxx),$(t_jb2d_cxx_flags))

# ?
$(call avnmkfdbprint,t_vm_commondep): %: ;

$(t_jb2d_outo): $(t_jb2d_objs_cpp) $(call avnmkfdbprint,vmobjs)
	$(call mf_linkobj,$(t_jb2d_ld),$(t_jb2d_ld_flags)) $(if $(mf_target_ems),--preload-file$(space)/letmp/baseq2@/baseq2)

.PHONY: t_vm
t_vm:
	$(call avndomk,all)

$(eval $(call mf_echot,jb2d,"Building t_jb2d..."))

.PHONY: t_jb2d
t_jb2d: | $(if $(noavn),,t_vm) $(call mf_echot_dep,jb2d) $(t_jb2d_outh) $(t_jb2d_outo)


#----------------------------------------


.PHONY: targets
targets: | t_jb2d ;

.PHONY: all
all: | $(mf_rootp)/makefile $(mf_outp) targets ;

.PHONY: f
f: | clean all ;

define HELP
	`make all`
	`make f`        -- forced rebuild (broken)
	`make clean`    -- delete current build
	`make cleanall` -- delete all the builds
	v={t}           -- be verbose
	tmpout={t,f}
	lto={t}
	vm_unwindex={t}
	platform={tux,ems} 
	arch={x86,x64,arm32,ems}
	mode={debug,profile,fast}
	vm_assert_stack|vm_log_eeins|vm_log_eestack={t}

	noavn={t}
endef
export HELP
.PHONY: help
help:
	@echo "$$HELP"

