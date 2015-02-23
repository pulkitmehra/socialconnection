#Social-Connection
A graph based social connection web application. This application contains graph of persons on which user can query the following
<ol>
  <li> List of persons in graph </li>
  <li> Path between two person.(Like linked-In, when you visit someone's profile, Linked-In shows you a path(people/freinds) who can introduce you with this person i.e friends of friends) </li>
  <li> Get above path count. If you wanted to know how far two person are.</li>
</ol>  

<p>
  This Graph is acheived via two ways 
    <ul>
      <li> <b>Neo4j</b>, Graph Database </li>
      <li> Home grown Graph Implementation using <b>Adjacency list</b> </li>
    </ul>  
  </p>
  <p>
    For detailed design overview see wiki,
    <a href="https://github.com/pulkitmehra/socialconnection/wiki/Design-Details">Wiki-Design</a>
  </p>
  
  
#Technology Stack

<p>
  Below is the technology stack
    
    <ul>
      <li>  <b> Language: Java 8, Groovy</b> </li>
      <li>  <b> Framework: Spring, SpringMVC </b> </li>
      <li>  <b> Database: Neo4j </b> </li>
      <li>  <b> Testing: JUnit, Mockito </b> </li>
      <li>  <b> Functional Testing: Groovy Spock </b> </li>
      <li>  <b> UAT Testing: Cucumber </b> </li>
      <li>  <b> Build Tools: Maven, Gradle </b> </li>
      <li>  <b> Dev ops: Vagrant </b> </li>
    </ul>  
</p>

# Quick Run Instruction


<b>Pre-requisite</b>
<br/>
Following should be installed
<p>
  <ul>
      <li>  <a href="http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html">JDK 8</a> </li>
      <li>  <a href="http://maven.apache.org/">Maven</a> </li>
      <li>  <a href="https://gradle.org/downloads">Gradle</a> </li>
  </ul>
</p>

<p>
  <b>For running web app</b>
  <ul>
      <li>  Git clone this repo and CD to root of that foder </li>
      <li>  Run command <i>'mvn install tomcat:run'</i> </li>
      <li>  Application will run and will be initialize with small graph data set, Below is the data set </li>
  </ul>
  

  
  <img src="http://s21.postimg.org/p689tmf6f/Social_Connection.png" alt="Social connection" style="padding:1px;border:thin solid black;" height="300" width="500"/>
  
</p>
<p>
  <b>Verify REST calls (Use Accept header for JSON or XML response)</b>
   <ul>
      <li>  http://localhost:8080/socialconnection/api/social/1.0/person </li>
      <li>  http://localhost:8080/socialconnection/api/social/1.0/person?fromName=Foo&toName=Tim </li>
      <li>  http://localhost:8080/socialconnection/api/social/1.0/person/count?fromName=Foo&toName=Tim </li>
  </ul>
  
  <b>For running FT and UAT test case (Make sure the tomcat server is up at localhost:8080)</b>
  <ul>
      <li>  Run command at root of the clone folder <b>'gradle build'</b> </li>
  </ul>
</p>

<p>
  For detailed deployment or deployment using vagrant see wiki
<a href="https://github.com/pulkitmehra/socialconnection/wiki/Deployment-Instructions">Wiki-Deployment</a>
</p>

