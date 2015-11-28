# spring_webapplication_wrapper
A microservice collection providing some (hopefully) useful services out-of-the-box

# Services
### ApplicationWrapper
A Webservice wrapping native Applications on the Server and making them available on the Network. It executes a command (via Apache Commons Exec) on the Server and returns the content of STDOUT and STDERR stream as BASE64 encoded String.
### Dashboard-Template (work-in-progress)
A Webapplication providing a pretty UI using Spring MVC and REST endpoints for modifying navigation, alerts, user information. Other webservices can use this template and focus on implementing the main section of the page 
### QuickIP Webapplication (work-in-progress)
A simple webapplication to change the servers (usually my laptop) IP address. Goal is to have something actual useable and I have to change my IP address far to often to use the OS way...


# Included Projects
|Project|Note|License|URL|
|---|---|---|---|
| Spring Framework | Core, Boot, MVC | Apache License 2.0 | https://spring.io/ |
| Apache Commons Exec |  | Apache License 2.0 | https://commons.apache.org/proper/commons-exec/index.html |
| Bootstrap | | MIT | http://getbootstrap.com/ |
| SB Admin | Bootstrab template | Apache 2.0 | http://startbootstrap.com/template-overviews/sb-admin/ |
| editable-grid | JS table rendering lib | Dual licensed under the MIT or GPL Version 2 licenses.| http://www.editablegrid.net/en/ |
| XOM | Java XML processing lib | LGPLv2.1 | http://www.xom.nu/ |

# Stuff to do
### Services
* Embedded REDIS Datastore
* JavaSpaces (Apache River, Gigaspace)
* Elasticsearch (or Lucene?)
* Spring Data
* Configuration Management
* Script Interpreter embedded oin Java (Perl, Python?, Bash, Powershell/cmd)
* Out-of-The-Box Memory Cache (Apache Commons JCS - https://commons.apache.org/proper/commons-jcs/)

### MISC
* Making Exec fit for Interactive Process Communication
* Build a REST API to display Alerts, Navbar und User information on the Default Dashboard (SB-Admin)
* Rename this repo to something more descriptive
* Make a lot more use of Spring boot within the framework
* Add logging
* Refactor Project (framework) to only start services really in use (Spring-Boot?)


# Note
This project mostly an exercise for webapplications using Spring Framework.
