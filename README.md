# demo-project-skeleton
This project does not contain fully source code. It's just skeleton with some stuffs that can help you to start.

# !IMPORTANT
* Make sure you manually download chrome/gecko drivers to match you OS and browser versions
* If not, the tests will be failed
* There are 3 test failed because verification points (mismatched prices)

# Make sure you are all set
* Clone the project
* Go to root directory (or open terminal in intelliJ IDEA terminal) and type

```
mvn clean test -Dsurefire.suiteXmlFiles=src/test/resources/test-suites/Regression.xml -DbaseUrl=http://demowebshop.tricentis.com
mvn clean test -Dsurefire.suiteXmlFiles=src/test/resources/test-suites/Regression.xml -DbaseUrl=http://demowebshop.tricentis.com -Dbrowser=firefox -DseleniumHub=http://localhost:4444
```

Then make sure you can report reports:
```
allure generate --clean
allure open
```"# demo-project-skeleton" 

````
````how to start jenkins
goto folder contains .war
cd Desktop/Testing/Tools/
java -jar jenkins.war
goto : http://localhost:8080/ ttle / @Admin123456

````

```note for myself - to start a hub
java -jar /Users/tuyentle/Desktop/Testing/Project01/task01/SDETPRO_Project01/selenium-grid/selenium-server-standalone-3.141.59.jar -role hub
register nodes:
java -jar -Dwebdriver.gecko.driver=/Users/tuyentle/Desktop/Testing/Project01/task01/SDETPRO_Project01/selenium-grid/geckodriver -Dwebdriver.chrome.driver=//Users/tuyentle/Desktop/Testing/Project01/task01/SDETPRO_Project01/selenium-grid/chromedriver /Users/tuyentle/Desktop/Testing/Project01/task01/SDETPRO_Project01/selenium-grid/selenium-server-standalone-3.141.59.jar -role node -nodeConfig /Users/tuyentle/Desktop/Testing/Project01/task01/SDETPRO_Project01/selenium-grid/node_config.json
then go to 
http://localhost:4444/grid/console
