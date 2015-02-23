rm -f -r ~/socialconnection
mkdir ~/socialconnection
cd ~/socialconnection
git clone https://github.com/pulkitmehra/socialconnection.git
mvn -DskipTests=true install tomcat:run