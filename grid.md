# What is it
. Generally speaking, cross-browser testing is technique that you need to handle to 
    have your test methods able to run on different browser types, environments in parallel.

. You CAN'T combine dataPovider and Parameters annotation from TestNG same time!

. You need to design your test suite to have important test scripts to run cross-browser testing.
    It's not necessary to have all test methods to run cross-browser testing.
    
* Select main browser: Chrome
* VERY IMPORTANT scenarios: cross-browser testing
    
## Command to start Hub
```
java -jar /path/to/selenium-server-file -role hub
```

## Command to start Node
This node will register:
* Chrome
* Firefox
* Safari

Note: IF you are on Windows OS, please remove part related to safari and add for Edge
```
java -jar -Dwebdriver.<type>.<name>s path/to/selenium/server.jsr -role node -nodeCOnfig /path/to/nodeConfig.json
java -jar -Dwebdriver.gecko.driver=/Users/tuhuynh/SOURCE_CODE/maven-start-project/selenium-grid/geckodriver -Dwebdriver.chrome.driver=/Users/tuhuynh/SOURCE_CODE/maven-start-project/selenium-grid/chromedriver /Users/tuhuynh/SOURCE_CODE/maven-start-project/selenium-grid/selenium-server-standalone-3.141.59.jar -role node -nodeConfig /Users/tuhuynh/SOURCE_CODE/maven-start-project/selenium-grid/node_config.json

```

NOTE: on Windows need to specify extension like gecko.exe, chrome.exe
``
to start jenkins:
cd to folder containing jenkins.war
run command: java -jar jenkins.war
go to: http://localhost:8080/
``

```note for myself - to start a hub
java -jar /Users/tuyentle/Desktop/Testing/Project01/task01/SDETPRO_Project01/selenium-grid/selenium-server-standalone-3.141.59.jar -role hub
register nodes:
java -jar -Dwebdriver.gecko.driver=/Users/tuyentle/Desktop/Testing/Project01/task01/SDETPRO_Project01/selenium-grid/geckodriver -Dwebdriver.chrome.driver=//Users/tuyentle/Desktop/Testing/Project01/task01/SDETPRO_Project01/selenium-grid/chromedriver /Users/tuyentle/Desktop/Testing/Project01/task01/SDETPRO_Project01/selenium-grid/selenium-server-standalone-3.141.59.jar -role node -nodeConfig /Users/tuyentle/Desktop/Testing/Project01/task01/SDETPRO_Project01/selenium-grid/node_config.json
then go to 
http://localhost:4444/grid/console
```

