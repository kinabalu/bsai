srcarchive:
	rm -rf archive ; \
	mkdir -p archive ; \
        cd archive ; \
        pwd ; \
        git clone `git remote -v | head -n 1 | cut -f 2 | cut -f 1 -d " "` ; \
        cd bsai/src ; \
        pwd ; \
        zip -r ../../../bsai * ; \
        tar cvzf ../../../bsai.tar.gz * ; \
        cd ../../.. ; \
        rm -rf archive