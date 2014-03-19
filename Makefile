all: pom.xml
	~/apache-tomcat-6.0.37_dev/bin/shutdown.sh
	~/apache-tomcat-6.0.37_dev/bin/startup.sh
	mvn  clean install -P dev -e -Ddevelopment_host=192.168.1.102 -Ddevelopment_port=2080 -Dmaven.test.skip
	mvn  tomcat:deploy -P dev -Ddevelopment_host=192.168.1.102 -Dmaven.test.skip

uall: pom.xml
	mvn  tomcat:undeploy -P dev
	~/apache-tomcat-6.0.37_dev/bin/shutdown.sh
	~/apache-tomcat-6.0.37_dev/bin/startup.sh
	mvn  clean install -P dev -e -Ddevelopment_host=192.168.1.102 -Ddevelopment_port=2080 -Dmaven.test.skip 
	mvn  tomcat:deploy -P dev -Ddevelopment_host=192.168.1.102 -Ddevelopment_port=2080 -Dmaven.test.skip

pall: pom.xml
	~/apache-tomcat-6.0.37/bin/shutdown.sh
	~/apache-tomcat-6.0.37/bin/startup.sh
	mvn  clean install -P dev -e -Ddevelopment_host=192.168.1.102 -Dmaven.test.skip
	mvn  tomcat:deploy -P dev -Dxroduction_host=192.168.1.102 -Dmaven.test.skip

puall: pom.xml
	mvn  tomcat:undeploy -P prod
	~/apache-tomcat-6.0.37/bin/shutdown.sh
	~/apache-tomcat-6.0.37/bin/startup.sh
	mvn  clean install -P prod -e -Dproduction_host=192.168.1.102 -Dmaven.test.skip 
	mvn  tomcat:deploy -P prod -Dproduction_host=192.168.1.102 -Dmaven.test.skip

palld: pom.xml
	~/apache-tomcat-6.0.37/bin/shutdown.sh
	~/apache-tomcat-6.0.37/bin/startup.sh
	mvn  clean install -P dev -e -Ddevelopment_host=perfumeriamerlen.dyndns.org -Ddevelopment_port=2080 -Dmaven.test.skip 
	mvn  tomcat:deploy -P dev -Ddevelopment_host=perfumeriamerlen.dyndns.org -Ddevelopment_port=2080 -Dmaven.test.skip

pualld: pom.xml
	mvn  tomcat:undeploy -P dev
	~/apache-tomcat-6.0.37/bin/shutdown.sh
	~/apache-tomcat-6.0.37/bin/startup.sh
	mvn  clean install -P dev -e -Ddevelopment_host=perfumeriamerlen.dyndns.org -Ddevelopment_port=2080 -Dmaven.test.skip 
	mvn  tomcat:deploy -P dev -Ddevelopment_host=perfumeriamerlen.dyndns.org -Ddevelopment_port=2080 -Dmaven.test.skip
