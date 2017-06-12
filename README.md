# load-generator

First build this gradle project. After successful build change your directory to `build/distributions`.
Unzip `loadgenerator-1.0-SNAPSHOT.zip` and go to `loadgenerator-1.0-SNAPSHOT/bin` folder
Now run the following command with your argument

```sh
##### ./loadgenerator <website name with http/https> <number of concurrent request> #####
$ ./loadgenerator http://test.com 10
```