# The name of the extension.
extension_name := feedreco

# The UUID of the extension.
extension_uuid := feedreco@codeblues.in

# The name of the profile dir where the extension can be installed.
profile_dir := 0jbej4kk.Developer

# The zip application to be used.
ZIP := zip

# The target location of the build and build files.
bin_dir := bin

# The target XPI file.
xpi_file := $(bin_dir)/$(extension_name).xpi

# The type of operating system this make command is running on.
#os_type := $(patsubst darwin%,darwin,$(shell echo $(OSTYPE)))
os_type := linux-gnu

# The location of the extension profile.
profile_location := \
      ~/.mozilla/firefox/$(profile_dir)/extensions/feedreco@codeblues.in.xpi

# The temporary location where the extension tree will be copied and built.
build_dir := $(bin_dir)

# This builds the extension XPI file.
.PHONY: all
all: $(xpi_file)
	@echo
	@echo "Build finished successfully."
	@echo

# This cleans all temporary files and directories created by 'make'.
.PHONY: clean
clean:
	#@rm -rf $(build_dir)
	@rm -f $(xpi_file)
	@echo "Cleanup is done."

# The sources for the XPI file.
xpi_built := install.rdf \
             chrome.manifest \
             $(wildcard content/*.js) \
             $(wildcard content/*.xul) \
             $(wildcard content/*.xml) \
             $(wildcard content/*.css) \
             $(wildcard skin/*.css) \
             $(wildcard skin/*.png) \
             $(wildcard locale/*/*.dtd) \
             $(wildcard locale/*/*.properties) \
	     $(wildcard modules/*.js)

# This builds everything except for the actual XPI, and then it copies it to the
# specified profile directory, allowing a quick update that requires no install.
.PHONY: install
install: $(build_dir) $(xpi_built)
	@make all
	@echo "Installing in profile folder: $(profile_location)"
	@cp -Rf $(build_dir)/* $(profile_location)
	@echo "Installing in profile folder. Done!"
	@echo


$(xpi_file): $(xpi_built)
	@echo "Creating XPI file."
	@$(ZIP) $(xpi_file) $(xpi_built)
	@echo "Creating XPI file. Done!"
