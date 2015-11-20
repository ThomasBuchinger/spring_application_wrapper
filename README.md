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
* Spring Framework (Core, Boot, MVC)
* Apache Commons Exec
* Bootstrap (SBAdmin Template)
* editable-grid

# Stuff to do
### Services
* Embedded REDIS Datastore
* JavaSpaces (Apache River)
* Elasticsearch (or Lucene?)
* Spring Data

### Refactoring
* Making Exec fit for Interactive Process Communication
* Build a REST API to display Alerts, Navbar und User information on the Default Dashboard (SB-Admin)
* Rename this repo to something more descriptive

# Note
This project mostly an exercise for webapplications using Spring Framework.
