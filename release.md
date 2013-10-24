

git checkout master
git checkout -b branch-0.0.x
git push -u origin branch-0.0.x

mvn release:prepare
git tag

git checkout tags/remote-system-connector-parent-0.0.1
mvn clean install

git checkout master
mvn release:update-versions
