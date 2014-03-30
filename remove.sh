#!/bin/bash
#script that removes all .DS_Store files
find . -name .DS_Store -print0 | xargs -0 git rm --ignore-unmatch
