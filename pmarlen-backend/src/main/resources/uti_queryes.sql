SQuirreL SQL Client Session Dump Wed Feb 12 09:21:02 CST 2014


===================================================
Session Properties
===================================================
<Beans>
    <Bean Class="net.sourceforge.squirrel_sql.client.session.properties.SessionProperties">
        <autoCommit>true</autoCommit>
        <catalogFilterExclude/>
        <catalogFilterInclude/>
        <commitOnClosingConnection>false</commitOnClosingConnection>
        <contentsLimitRows>true</contentsLimitRows>
        <contentsNbrOfRowsToShow>100</contentsNbrOfRowsToShow>
        <fontInfo Class="net.sourceforge.squirrel_sql.fw.gui.FontInfo">
            <family>Monospaced</family>
            <isBold>false</isBold>
            <isItalic>false</isItalic>
            <size>10</size>
        </fontInfo>
        <keepTableLayoutOnRerun>false</keepTableLayoutOnRerun>
        <limitSqlEntryHistorySize>true</limitSqlEntryHistorySize>
        <limitSqlResultTabs>true</limitSqlResultTabs>
        <loadCatalogsSchemas>true</loadCatalogsSchemas>
        <loadColumnsInBackground>false</loadColumnsInBackground>
        <mainTabPlacement>1</mainTabPlacement>
        <metaDataOutputClassName>net.sourceforge.squirrel_sql.fw.datasetviewer.DataSetViewerTablePanel</metaDataOutputClassName>
        <objectFilterExclude/>
        <objectFilterInclude/>
        <objectTabPlacement>1</objectTabPlacement>
        <removeMultiLineComment>false</removeMultiLineComment>
        <schemaFilterExclude/>
        <schemaFilterInclude/>
        <showResultsMetaData>true</showResultsMetaData>
        <showRowCount>false</showRowCount>
        <showSQLErrorsInTab>true</showSQLErrorsInTab>
        <showToolBar>true</showToolBar>
        <sqlEntryHistorySize>500</sqlEntryHistorySize>
        <sqlExecutionTabPlacement>1</sqlExecutionTabPlacement>
        <sqlFetchSize>false</sqlFetchSize>
        <sqlLimitRows>true</sqlLimitRows>
        <sqlNbrOfRowsToShow>100</sqlNbrOfRowsToShow>
        <sqlPanelOrientation>0</sqlPanelOrientation>
        <sqlResultTabLimit>15</sqlResultTabLimit>
        <sqlResultsOutputClassName>net.sourceforge.squirrel_sql.fw.datasetviewer.DataSetViewerTablePanel</sqlResultsOutputClassName>
        <sqlResultsTabPlacement>1</sqlResultsTabPlacement>
        <sqlShareHistory>true</sqlShareHistory>
        <sqlStartOfLineComment>--</sqlStartOfLineComment>
        <sqlStatementSeparatorString>;</sqlStatementSeparatorString>
        <sqlUseFetchSize>50</sqlUseFetchSize>
        <tableContentsOutputClassName>net.sourceforge.squirrel_sql.fw.datasetviewer.DataSetViewerTablePanel</tableContentsOutputClassName>
    </Bean>
</Beans>


===================================================
Driver
===================================================
<Beans>
    <Bean Class="net.sourceforge.squirrel_sql.fw.sql.SQLDriver">
        <driverClassName>com.mysql.jdbc.Driver</driverClassName>
        <identifier Class="net.sourceforge.squirrel_sql.fw.id.UidIdentifier">
            <string>-27</string>
        </identifier>
        <jarFileName/>
        <jarFileNames Indexed="true">
            <Bean Class="net.sourceforge.squirrel_sql.fw.util.beanwrapper.StringWrapper">
                <string>/usr/share/java/mysql.jar</string>
            </Bean>
        </jarFileNames>
        <name>MySQL Driver</name>
        <url>jdbc:mysql://&lt;hostname&gt;[,&lt;failoverhost&gt;][&lt;:3306&gt;]/&lt;dbname&gt;[?&lt;param1&gt;=&lt;value1&gt;][&amp;&lt;param2&gt;=&lt;value2&gt;]</url>
        <websiteUrl>http://dev.mysql.com</websiteUrl>
    </Bean>
</Beans>


===================================================
Alias
===================================================
<Beans>
    <Bean Class="net.sourceforge.squirrel_sql.client.gui.db.SQLAlias">
        <autoLogon>true</autoLogon>
        <colorProperties Class="net.sourceforge.squirrel_sql.client.gui.db.SQLAliasColorProperties">
            <objectTreeBackgroundColorRgbValue>0</objectTreeBackgroundColorRgbValue>
            <overrideObjectTreeBackgroundColor>false</overrideObjectTreeBackgroundColor>
            <overrideStatusBarBackgroundColor>false</overrideStatusBarBackgroundColor>
            <overrideToolbarBackgroundColor>false</overrideToolbarBackgroundColor>
            <statusBarBackgroundColorRgbValue>0</statusBarBackgroundColorRgbValue>
            <toolbarBackgroundColorRgbValue>0</toolbarBackgroundColorRgbValue>
        </colorProperties>
        <connectAtStartup>false</connectAtStartup>
        <connectionProperties Class="net.sourceforge.squirrel_sql.client.gui.db.SQLAliasConnectionProperties">
            <enableConnectionKeepAlive>false</enableConnectionKeepAlive>
            <keepAliveSleepTimeSeconds>120</keepAliveSleepTimeSeconds>
            <keepAliveSqlStatement/>
        </connectionProperties>
        <driverIdentifier Class="net.sourceforge.squirrel_sql.fw.id.UidIdentifier">
            <string>-27</string>
        </driverIdentifier>
        <driverProperties Class="net.sourceforge.squirrel_sql.fw.sql.SQLDriverPropertyCollection">
            <driverProperties Indexed="true"/>
        </driverProperties>
        <identifier Class="net.sourceforge.squirrel_sql.fw.id.UidIdentifier">
            <string>19ea6e19:14426a96afd:-7f7b</string>
        </identifier>
        <name>jdbc/PMARLEN_DB_TEST_DS</name>
        <password>PMARLEN_TEST_PASSWORD</password>
        <schemaProperties Class="net.sourceforge.squirrel_sql.client.gui.db.SQLAliasSchemaProperties">
            <allSchemaProceduresNotToBeCached Indexed="true"/>
            <cacheSchemaIndependentMetaData>false</cacheSchemaIndependentMetaData>
            <expectsSomeCachedData>false</expectsSomeCachedData>
            <globalState>0</globalState>
            <schemaDetails Indexed="true"/>
        </schemaProperties>
        <url>jdbc:mysql://localhost/PMARLEN_DB_TEST?characterEncoding=UTF-8</url>
        <useDriverProperties>false</useDriverProperties>
        <userName>PMARLEN_TEST</userName>
    </Bean>
</Beans>


===================================================
Connection - General
===================================================
transIsolation: 4
readonly: false


===================================================
Metadata
===================================================
"getUserName","PMARLEN_TEST@localhost"
"getDatabaseProductName","MySQL"
"getDatabaseProductVersion","5.5.32-0ubuntu0.13.04.1"
"getDatabaseMajorVersion","5"
"supportsSchemasInDataManipulation","false"
"supportsCatalogsInDataManipulation","true"
"supportsSchemasInTableDefinitions","false"
"getCatalogSeparator","."
"getIdentifierQuoteString","`"
"supportsStoredProcedures","true"
"getDriverName","MySQL-AB JDBC Driver"
"getDriverVersion","mysql-connector-java-5.1.16 ( Revision: ${bzr.revision-id} )"
"supportsCatalogsInTableDefinitions","true"
"storesMixedCaseIdentifiers","true"
"storesUpperCaseIdentifiers","false"
"getSchemaTerm",""
"getProcedureTerm","PROCEDURE"
"supportsCatalogsInProcedureCalls","true"
"supportsSavepoints","true"
"getCatalogTerm","database"
"supportsMultipleResultSets","true"
"allProceduresAreCallable","false"
"allTablesAreSelectable","false"
"nullsAreSortedHigh","false"
"nullsAreSortedLow","true"
"nullsAreSortedAtStart","false"
"nullsAreSortedAtEnd","false"
"getDriverMajorVersion","5"
"getDriverMinorVersion","1"
"usesLocalFiles","false"
"usesLocalFilePerTable","false"
"supportsMixedCaseIdentifiers","true"
"storesLowerCaseIdentifiers","false"
"supportsMixedCaseQuotedIdentifiers","true"
"storesUpperCaseQuotedIdentifiers","true"
"storesLowerCaseQuotedIdentifiers","false"
"storesMixedCaseQuotedIdentifiers","true"
"getSearchStringEscape","\"
"getExtraNameCharacters","#@"
"supportsAlterTableWithAddColumn","true"
"supportsAlterTableWithDropColumn","true"
"supportsColumnAliasing","true"
"nullPlusNonNullIsNull","true"
"supportsConvert","false"
"supportsTableCorrelationNames","true"
"supportsDifferentTableCorrelationNames","true"
"supportsExpressionsInOrderBy","true"
"supportsOrderByUnrelated","false"
"supportsGroupBy","true"
"supportsGroupByUnrelated","true"
"supportsGroupByBeyondSelect","true"
"supportsLikeEscapeClause","true"
"supportsMultipleTransactions","true"
"supportsNonNullableColumns","true"
"supportsMinimumSQLGrammar","true"
"supportsCoreSQLGrammar","true"
"supportsExtendedSQLGrammar","false"
"supportsANSI92EntryLevelSQL","true"
"supportsANSI92IntermediateSQL","false"
"supportsANSI92FullSQL","false"
"supportsIntegrityEnhancementFacility","false"
"supportsOuterJoins","true"
"supportsFullOuterJoins","false"
"supportsLimitedOuterJoins","true"
"isCatalogAtStart","true"
"supportsSchemasInProcedureCalls","false"
"supportsSchemasInIndexDefinitions","false"
"supportsSchemasInPrivilegeDefinitions","false"
"supportsCatalogsInIndexDefinitions","true"
"supportsCatalogsInPrivilegeDefinitions","true"
"supportsPositionedDelete","false"
"supportsPositionedUpdate","false"
"supportsSelectForUpdate","true"
"supportsSubqueriesInComparisons","true"
"supportsSubqueriesInExists","true"
"supportsSubqueriesInIns","true"
"supportsSubqueriesInQuantifieds","true"
"supportsCorrelatedSubqueries","true"
"supportsUnion","true"
"supportsUnionAll","true"
"supportsOpenCursorsAcrossCommit","false"
"supportsOpenCursorsAcrossRollback","false"
"supportsOpenStatementsAcrossCommit","false"
"supportsOpenStatementsAcrossRollback","false"
"getMaxBinaryLiteralLength","16777208"
"getMaxCharLiteralLength","16777208"
"getMaxColumnNameLength","64"
"getMaxColumnsInGroupBy","64"
"getMaxColumnsInIndex","16"
"getMaxColumnsInOrderBy","64"
"getMaxColumnsInSelect","256"
"getMaxColumnsInTable","512"
"getMaxConnections","0"
"getMaxCursorNameLength","64"
"getMaxIndexLength","256"
"getMaxSchemaNameLength","0"
"getMaxProcedureNameLength","0"
"getMaxCatalogNameLength","32"
"getMaxRowSize","2147483639"
"doesMaxRowSizeIncludeBlobs","true"
"getMaxStatementLength","65531"
"getMaxStatements","0"
"getMaxTableNameLength","64"
"getMaxTablesInSelect","256"
"getMaxUserNameLength","16"
"getDefaultTransactionIsolation","TRANSACTION_READ_COMMITTED"
"supportsTransactions","true"
"supportsDataDefinitionAndDataManipulationTransactions","false"
"supportsDataManipulationTransactionsOnly","false"
"dataDefinitionCausesTransactionCommit","true"
"dataDefinitionIgnoredInTransactions","false"
"supportsBatchUpdates","true"
"supportsNamedParameters","false"
"supportsMultipleOpenResults","true"
"supportsGetGeneratedKeys","true"
"getResultSetHoldability","1"
"getDatabaseMinorVersion","5"
"getJDBCMajorVersion","4"
"getJDBCMinorVersion","0"
"getSQLStateType","2"
"locatorsUpdateCopy","true"
"supportsStatementPooling","false"
"getRowIdLifetime","ROWID_UNSUPPORTED"
"supportsStoredFunctionsUsingCallSyntax","true"
"autoCommitFailureClosesAllResultSets","false"
"getClientInfoProperties",""
"generatedKeyAlwaysReturned","<Unsupported>"
"getURL","jdbc:mysql://localhost/PMARLEN_DB_TEST?characterEncoding=UTF-8"
"isReadOnly","false"


===================================================
Catalogs
===================================================
"information_schema"
"PMARLEN_DB_TEST"
"test"


===================================================
Schemas
===================================================


===================================================
Data Types
===================================================
"BIT","-7 [BIT]","1","","","","true","true","supports all WHERE","false","false","false","BIT","0","0","null","null","10"
"BOOL","-7 [BIT]","1","","","","true","true","supports all WHERE","false","false","false","BOOL","0","0","null","null","10"
"TINYINT","-6 [TINYINT]","3","","","[(M)] [UNSIGNED] [ZEROFILL]","true","false","supports all WHERE","true","false","true","TINYINT","0","0","null","null","10"
"TINYINT UNSIGNED","-6 [TINYINT]","3","","","[(M)] [UNSIGNED] [ZEROFILL]","true","false","supports all WHERE","true","false","true","TINYINT UNSIGNED","0","0","null","null","10"
"BIGINT","-5 [BIGINT]","19","","","[(M)] [UNSIGNED] [ZEROFILL]","true","false","supports all WHERE","true","false","true","BIGINT","0","0","null","null","10"
"BIGINT UNSIGNED","-5 [BIGINT]","20","","","[(M)] [ZEROFILL]","true","false","supports all WHERE","true","false","true","BIGINT UNSIGNED","0","0","null","null","10"
"LONG VARBINARY","-4 [LONGVARBINARY]","16777215","'","'","","true","true","supports all WHERE","false","false","false","LONG VARBINARY","0","0","null","null","10"
"MEDIUMBLOB","-4 [LONGVARBINARY]","16777215","'","'","","true","true","supports all WHERE","false","false","false","MEDIUMBLOB","0","0","null","null","10"
"LONGBLOB","-4 [LONGVARBINARY]","2147483647","'","'","","true","true","supports all WHERE","false","false","false","LONGBLOB","0","0","null","null","10"
"BLOB","-4 [LONGVARBINARY]","65535","'","'","","true","true","supports all WHERE","false","false","false","BLOB","0","0","null","null","10"
"TINYBLOB","-4 [LONGVARBINARY]","255","'","'","","true","true","supports all WHERE","false","false","false","TINYBLOB","0","0","null","null","10"
"VARBINARY","-3 [VARBINARY]","255","'","'","(M)","true","true","supports all WHERE","false","false","false","VARBINARY","0","0","null","null","10"
"BINARY","-2 [BINARY]","255","'","'","(M)","true","true","supports all WHERE","false","false","false","BINARY","0","0","null","null","10"
"LONG VARCHAR","-1 [LONGVARCHAR]","16777215","'","'","","true","false","supports all WHERE","false","false","false","LONG VARCHAR","0","0","null","null","10"
"MEDIUMTEXT","-1 [LONGVARCHAR]","16777215","'","'","","true","false","supports all WHERE","false","false","false","MEDIUMTEXT","0","0","null","null","10"
"LONGTEXT","-1 [LONGVARCHAR]","2147483647","'","'","","true","false","supports all WHERE","false","false","false","LONGTEXT","0","0","null","null","10"
"TEXT","-1 [LONGVARCHAR]","65535","'","'","","true","false","supports all WHERE","false","false","false","TEXT","0","0","null","null","10"
"TINYTEXT","-1 [LONGVARCHAR]","255","'","'","","true","false","supports all WHERE","false","false","false","TINYTEXT","0","0","null","null","10"
"CHAR","1 [CHAR]","255","'","'","(M)","true","false","supports all WHERE","false","false","false","CHAR","0","0","null","null","10"
"NUMERIC","2 [NUMERIC]","65","","","[(M[,D])] [ZEROFILL]","true","false","supports all WHERE","false","false","true","NUMERIC","-308","308","null","null","10"
"DECIMAL","3 [DECIMAL]","65","","","[(M[,D])] [ZEROFILL]","true","false","supports all WHERE","false","false","true","DECIMAL","-308","308","null","null","10"
"INTEGER","4 [INTEGER]","10","","","[(M)] [UNSIGNED] [ZEROFILL]","true","false","supports all WHERE","true","false","true","INTEGER","0","0","null","null","10"
"INTEGER UNSIGNED","4 [INTEGER]","10","","","[(M)] [ZEROFILL]","true","false","supports all WHERE","true","false","true","INTEGER UNSIGNED","0","0","null","null","10"
"INT","4 [INTEGER]","10","","","[(M)] [UNSIGNED] [ZEROFILL]","true","false","supports all WHERE","true","false","true","INT","0","0","null","null","10"
"INT UNSIGNED","4 [INTEGER]","10","","","[(M)] [ZEROFILL]","true","false","supports all WHERE","true","false","true","INT UNSIGNED","0","0","null","null","10"
"MEDIUMINT","4 [INTEGER]","7","","","[(M)] [UNSIGNED] [ZEROFILL]","true","false","supports all WHERE","true","false","true","MEDIUMINT","0","0","null","null","10"
"MEDIUMINT UNSIGNED","4 [INTEGER]","8","","","[(M)] [ZEROFILL]","true","false","supports all WHERE","true","false","true","MEDIUMINT UNSIGNED","0","0","null","null","10"
"SMALLINT","5 [SMALLINT]","5","","","[(M)] [UNSIGNED] [ZEROFILL]","true","false","supports all WHERE","true","false","true","SMALLINT","0","0","null","null","10"
"SMALLINT UNSIGNED","5 [SMALLINT]","5","","","[(M)] [ZEROFILL]","true","false","supports all WHERE","true","false","true","SMALLINT UNSIGNED","0","0","null","null","10"
"FLOAT","7 [REAL]","10","","","[(M,D)] [ZEROFILL]","true","false","supports all WHERE","false","false","true","FLOAT","-38","38","null","null","10"
"DOUBLE","8 [DOUBLE]","17","","","[(M,D)] [ZEROFILL]","true","false","supports all WHERE","false","false","true","DOUBLE","-308","308","null","null","10"
"DOUBLE PRECISION","8 [DOUBLE]","17","","","[(M,D)] [ZEROFILL]","true","false","supports all WHERE","false","false","true","DOUBLE PRECISION","-308","308","null","null","10"
"REAL","8 [DOUBLE]","17","","","[(M,D)] [ZEROFILL]","true","false","supports all WHERE","false","false","true","REAL","-308","308","null","null","10"
"VARCHAR","12 [VARCHAR]","255","'","'","(M)","true","false","supports all WHERE","false","false","false","VARCHAR","0","0","null","null","10"
"ENUM","12 [VARCHAR]","65535","'","'","","true","false","supports all WHERE","false","false","false","ENUM","0","0","null","null","10"
"SET","12 [VARCHAR]","64","'","'","","true","false","supports all WHERE","false","false","false","SET","0","0","null","null","10"
"DATE","91 [DATE]","0","'","'","","true","false","supports all WHERE","false","false","false","DATE","0","0","null","null","10"
"TIME","92 [TIME]","0","'","'","","true","false","supports all WHERE","false","false","false","TIME","0","0","null","null","10"
"DATETIME","93 [TIMESTAMP]","0","'","'","","true","false","supports all WHERE","false","false","false","DATETIME","0","0","null","null","10"
"TIMESTAMP","93 [TIMESTAMP]","0","'","'","[(M)]","true","false","supports all WHERE","false","false","false","TIMESTAMP","0","0","null","null","10"


===================================================
Table Types
===================================================
"LOCAL TEMPORARY"
"TABLE"
"VIEW"
