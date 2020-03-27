[![Codacy Badge](https://api.codacy.com/project/badge/Grade/6f45b1103d2845708238cd5ca2cad53f)](https://app.codacy.com/gh/inf112-v20/factoryRobots?utm_source=github.com&utm_medium=referral&utm_content=inf112-v20/factoryRobots&utm_campaign=Badge_Grade_Settings)
[![Build Status](https://travis-ci.com/inf112-v20/factoryRobots.svg?branch=master)](https://travis-ci.com/inf112-v20/factoryRobots)
# INF112 Robo Rally
This project uses:
*   Java 8
*   JUnit 4
*   Maven
 
## Install project

*   Clone project
*   Maven install
    *   mvn clean install
    *   mvn clean package (without tests)   
    
*   Run Jar: ```java -cp target/factory-robots-1.0-SNAPSHOT.jar inf112.app.Main```


## Known bugs
- Currently throws "WARNING: An illegal reflective access operation has occurred", 
when the java version used is >8. This has no effect on function or performance, and is just a warning.

- Exception when using Mac OS: Please run the JVM with -XstartOnFirstThread. For example:<br>
```java -XstartOnFirstThread -cp target/factory-robots-1.0-SNAPSHOT.jar inf112.app.Main```
