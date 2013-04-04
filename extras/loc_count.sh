#!/bin/sh
loc=0
dirs="../src"

for dir in $dirs; do
    files=`find $dir -type f | egrep -v "svn" | egrep -v "(DS_Store|pdf|svn|sql|png|txt|swfupload)"`
    lines=`wc -l $files | tail -1 | sed "s/total//g"`
    loc=$(($loc + $lines))
done

echo "Lines of code: $loc (not including libraries / externals)"
