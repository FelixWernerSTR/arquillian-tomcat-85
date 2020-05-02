# Dead simple demo

 * wget http://archive.apache.org/dist/tomcat/tomcat-8/v8.5.54/bin/apache-tomcat-8.5.54.zip
 * unzip apache-tomcat-8.5.54.zip
 * add ```<user username="arquillian" password="arquillian" roles="manager-script"/>``` to apache-tomcat-8.5.54/conf/tomcat-users.xml
 * export CATALINA_HOME="/your/path/apache-tomcat-8.5.54"; 
 * or add and configure property:catalinaHome="/your/path/apache-tomcat-8.5.54":  in arquillian.xml;
 * build with: mvn clean package -Dtomcat.user=arquillian -Dtomcat.pass=arquillian
 
 * add to GlobalNamingResources in %CATALINA_HOME%\conf\server.xml:
  <Resource name="jdbc/mydatasource"
          auth="Container"
          type="javax.sql.DataSource"
          url="jdbc:oracle:thin:@SCSIS21F:1521:wesvft10" 
          driverClassName="oracle.jdbc.OracleDriver"
          username="SVIS_BL"
          password="verschlüsselt"
          factory="de.fewe.arquillian.EncryptedDataSourceFactory"
          initialSize="2"
          maxActive="75"
          maxWait="10000"
          minIdle="2"
          maxIdle="15"
          timeBetweenEvictionRunsMillis="30000"
          minEvictableIdleTimeMillis="55000"
          testWhileIdle="false"
          testOnBorrow="true"
          removeAbandoned="true"
          logAbandoned="true"
          removeAbandonedTimeout="120"
          accessToUnderlyingConnectionAllowed="true" />
          
  * place demo-web.xml to %CATALINA_HOME%\conf\Catalina\localhost
  * place EncryptedDataSourceFactory and Encryptor as jar to %CATALINA_HOME%\lib
  * place jasypt lib(s) to %CATALINA_HOME%\lib later if implemented in Encryptor
  
  *http://localhost:8080/demo-web/dsquery
 


