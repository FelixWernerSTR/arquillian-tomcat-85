# Dead simple demo

 * wget http://archive.apache.org/dist/tomcat/tomcat-8/v8.5.54/bin/apache-tomcat-8.5.54.zip
 * unzip apache-tomcat-8.5.54.zip
 * add ```<user username="arquillian" password="arquillian" roles="manager-script"/>``` to apache-tomcat-8.5.54/conf/tomcat-users.xml
 * export CATALINA_HOME="/your/path/apache-tomcat-8.5.54"; 
 * or add and configure property:catalinaHome="/your/path/apache-tomcat-8.5.54":  in arquillian.xml;
 * build with: mvn clean package -Dtomcat.user=arquillian -Dtomcat.pass=arquillian


