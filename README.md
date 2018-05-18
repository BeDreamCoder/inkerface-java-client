### INKchain Transaction Sign SDK For JAVA

This SDK provided offline transaction signature, generate address and public key, export public key from private key interface.

### Getting started
IDEA+Maven Installation and Configuration on Mac

#### Download Maven
[Maven official download address](https://maven.apache.org/download.cgi?spm=a2c4e.11153940.blogcont277651.9.b7e11c21hdlhGe&file=download.cgi)

After downloading, unzip and put it in the library directory and rename it as apache-maven

#### Configure Maven
```
vi ~/.bash_profile
export M2_HOME=/library/apache-maven
export PATH=$PATH:$M2_HOME/bin
```

#### Maven configure to IDEA
1.Configure——>Project Defauls——>Setting

2.Search for maven in the search box

3.Select maven installation path in Maven home diretory

#### Download source code and run
```
git clone https://github.com/inklabsfoundation/inkerface-java-client.git
```
IDEA opens the project and runs

#### Available Jar Package
```
cd inkerface-java-client/target

SignTx-1.0.0-jar-with-dependencies.jar
SignTx-1.0.0.jar
```
