# Makefile instalcaion de ambiente de Base de Datos del Sistema Perfumeria Marlen

# make init_DB_TEST MYSQL_ROOT_PASSWORD=xxxxxx

#IMGS_DIR=../../Fotos_Perfumeria_Marlen
#PREFIX_IMG=0000.JPG

prapre_DB_TEST : ../../pmarlen-logic/src/test/resources/initScenarioForTest.sql
	mysql --default-character-set=utf8 -u PMARLEN_TEST -pPMARLEN_TEST_PASSWORD PMARLEN_DB_TEST < ../../pmarlen-logic/src/test/resources/initScenarioForTest.sql

init_DB_TEST : create_users_TEST
	mysql --default-character-set=utf8 -u root         -p$(MYSQL_ROOT_PASSWORD) PMARLEN_DB_TEST < import/0_Import_SEPOMEX_dump.sql	
	mysql --default-character-set=utf8 -u PMARLEN_TEST -pPMARLEN_TEST_PASSWORD     PMARLEN_DB_TEST < import/1_Intrinsec_Data.sql
	mysql --default-character-set=utf8 -u PMARLEN_TEST -pPMARLEN_TEST_PASSWORD     PMARLEN_DB_TEST < import/2_Import_First_Users.sql

init_DB_PROD : create_users_PROD 
	mysql --default-character-set=utf8 -u root         -p$(MYSQL_ROOT_PASSWORD) PMARLEN_DB_PROD < import/0_Import_SEPOMEX_dump.sql
	mysql --default-character-set=utf8 -u PMARLEN_PROD -pPMARLEN_PROD_PASSWORD     PMARLEN_DB_PROD < import/1_Intrinsec_Data.sql
	mysql --default-character-set=utf8 -u PMARLEN_PROD -pPMARLEN_PROD_PASSWORD     PMARLEN_DB_PROD < import/2_Import_First_Users.sql	
#	mysql --default-character-set=utf8 -u PMARLEN_PROD -pPMARLEN_PROD_PASSWORD  PMARLEN_DB_PROD < import/4_Import_Proveedores_Almacenes.sql
#	mysql --default-character-set=utf8 -u PMARLEN_PROD -pPMARLEN_PROD_PASSWORD  PMARLEN_DB_PROD < import/5_Import_Productos_Proveedores.sql
#	mysql --default-character-set=utf8 -u PMARLEN_PROD -pPMARLEN_PROD_PASSWORD  PMARLEN_DB_PROD < import/6_Import_Clientes.sql
#	java -jar "import/ImagesImporter/ImagesImporter.jar" $(IMGS_DIR) $(PREFIX_IMG) PROD 2> importImgsOut.log

create_users_TEST : create_DB_TEST
	mysql --default-character-set=utf8 -u root -p$(MYSQL_ROOT_PASSWORD) < sql/CREATE_DBUSER_TEST.MySQL.SQL

create_users_PROD : create_DB_PROD
	mysql --default-character-set=utf8 -u root -p$(MYSQL_ROOT_PASSWORD) < sql/CREATE_DBUSER_PROD.MySQL.SQL

create_DB_TEST : sql/CREATE_SCHEMA_PROD.MySQL.SQL sql/CREATE_SCHEMA_TEST.MySQL.SQL
	mysql --default-character-set=utf8 -u root -p$(MYSQL_ROOT_PASSWORD) < sql/CREATE_SCHEMA_TEST.MySQL.SQL
	
create_DB_PROD : sql/CREATE_SCHEMA_PROD.MySQL.SQL sql/CREATE_SCHEMA_TEST.MySQL.SQL
	mysql --default-character-set=utf8 -u root -p$(MYSQL_ROOT_PASSWORD) < sql/CREATE_SCHEMA_PROD.MySQL.SQL
	
regenerate_scripts_DB : run_regenerate_scripts_DB clean_results
	cp sql/CREATE_SCHEMA.DERBY.SQL ../../pmarlen-desktop-client/src/main/resources/scripts
	
run_regenerate_scripts_DB : model/exported/project.xml model/jpa-builder-0.9.5.jar
	java -cp model/jpa-builder-0.9.5.jar com.tracktopell.dao.builder.dbschema.VPModel2SQL model/exported/project.xml mysql PMARLEN_DB_PROD sql/CREATE_SCHEMA_PROD.MySQL.SQL  "{all}" > resultsgen_mysql.txt 2> debug_resultsgen_mysql.txt
	java -cp model/jpa-builder-0.9.5.jar com.tracktopell.dao.builder.dbschema.VPModel2SQL model/exported/project.xml mysql PMARLEN_DB_TEST sql/CREATE_SCHEMA_TEST.MySQL.SQL  "{all}" > resultsgen_mysql.txt 2> debug_resultsgen_mysql.txt
	java -cp model/jpa-builder-0.9.5.jar com.tracktopell.dao.builder.dbschema.VPModel2SQL model/exported/project.xml derby PMARLEN_DB      sql/CREATE_SCHEMA.DERBY.SQL       "{all}" > resultsgen_mysql.txt 2> debug_resultsgen_mysql.txt

run_regenerate_JPABeans : model/exported/project.xml model/jpa-builder-0.9.5.jar
	java -cp model/jpa-builder-0.9.5.jar com.tracktopell.dao.builder.jpa.VPModel2JPA model/exported/project.xml PMARLEN_DB com.pmarlen.model.beans ../src/main/java "{all}"
	java -cp model/jpa-builder-0.9.5.jar com.tracktopell.dao.builder.jpa.VPModel2DTO model/exported/project.xml PMARLEN_DB com.pmarlen.wscommons.services.dto ../src/main/java "{all}"
	java -cp model/jpa-builder-0.9.5.jar com.tracktopell.dao.builder.jpa.UpdatePersistenceXML model/exported/project.xml model/persistence.xml com.pmarlen.model.beans "{all}" > ../src/main/resources/META-INF/persistence.xml

# sudo aptitude install librsvg2-bin
create_png: /usr/bin/rsvg model/exported/ER1.svg
	rsvg -f png model/exported/ER1.svg model/exported/ER1.png

clean_results :
	rm -f debug*
	rm -f results*
