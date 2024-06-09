SOURCEDOC=$(wildcard ./*.adoc)
HTMLFILES=$(SOURCEDOC:%.adoc=output/%.html)
DOCXFILES=$(SOURCEDOC:%.adoc=output/%.adoc.docx)

publish: clean $(HTMLFILES)
	mkdir -p output/images
	-cp images/* output/images

doc: publish $(DOCXFILES)

install:
	brew install graphviz plantuml
	# presumes localized ruby
	gem install asciidoctor-diagram

init:

.PHONY: clean publish doc .install

clean:
	rm -rf output

output/%.html: %.adoc
	asciidoctor -r asciidoctor-diagram -d book -a toc $< -D output

# TODO fix to use the admonitions lua script and the reference doc
output/%.adoc.docx: %.adoc
	asciidoctor -r asciidoctor-diagram -d book --backend docbook --out-file  - $< | \
	pandoc -r docbook -t docx -o output/$<.docx --reference-doc custom-reference.docx --lua-filter lua/admonitions.lua
