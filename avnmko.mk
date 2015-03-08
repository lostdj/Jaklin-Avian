avnmkf := $(avnp)/makefile
avnmko := \
	v=$(v) \
	mf_rootp=$(avnp) \
	tmpout=$(tmpout) \
	_mf_tmpout=$(_mf_tmpout) \
	lto=$(lto) \
	arch=$(arch) \
	mode=$(mode)
avndomk = (cd $(avnp) && make -f $(avnmkf) $(avnmko) $(1))
avndomks = $(shell $(call avndomk,$(1)))
avnmkfdb := $(mf_outp)/avnmkfdb
ifneq ($(noavn),t)
$(eval $(shell \
	if   [ ! -f $(avnmkfdb) ] \
		|| [ $(avnmkf) -nt $(avnmkfdb) ] \
		|| [ $(libmkf) -nt $(avnmkfdb) ] \
		; then \
			$(call avndomk,-p$(space)>$(space)$(avnmkfdb)); \
		fi))
endif
avnmkfdbprint = $(call mf_mkfdbfind,$(1),$(avnmkfdb))

jaklinextp := $(call avnmkfdbprint,jaklinextp)

