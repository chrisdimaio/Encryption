:: java -cp %HOMEPATH%\Dropbox\MyProjects\DataStuff\VersionControl\classes; VersionControl %HOMEPATH%\Dropbox\MyProjects\Datastuff\Encryption java

CD "%HOMEPATH%\Dropbox\MyProjects\DataStuff\Encryption"
javac -d . -classpath libs/json.jar;libs/commons-codec-1.8.jar src/*.java
jar cvfm encrypt.jar manifest.txt com/*.class org/*

"C:\Program Files\7-Zip\7zG" a -sfx EncryptInstaller.exe encrypt.jar libs/*.jar

CD "%HOMEPATH%\Dropbox\MyProjects\DataStuff\Encryption"
java -jar encrypt.jar