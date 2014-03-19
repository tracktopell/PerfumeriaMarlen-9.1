


Add the tomcat server to ${maven_home}/conf/settings.xml
--------------------------------------------------------
   <server>
      <id>tomcat</id>
      <username>admin</username>
      <password>secret</password>
      <privateKey>${user.home}/.ssh/id_dsa</privateKey>
      <passphrase>some_passphrase</passphrase>
      <filePermissions>664</filePermissions>
      <directoryPermissions>775</directoryPermissions>
      <configuration></configuration>
    </server>

--------------------------------------------------------


1) Execute the ReviewDB
    mvn clean compile exec:java -Dexec.mainClass=com.pmarlen.web.operation.ReviewDB -Dexec.args="classpath:/jdbc.properties" -e

