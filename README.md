### Java server 

Server based on concurrent hash map and per connection thread.
<br/> Prerequisites are JavaVM 17, Maven 3.8.5 and current version of protobuf: https://github.com/protocolbuffers/protobuf

For running use following commands:
 
`mvn compile` <br/>
`mvn exec:java -Dexec.mainClass="cz.cvut.fel.esw.Main" -Dexec.args="<<port>>"`

where &lt;&lt;port&gt;&gt; is desired port on which the server is supposed to run. 
If no argument is given, then the port is set to 8080 by default.