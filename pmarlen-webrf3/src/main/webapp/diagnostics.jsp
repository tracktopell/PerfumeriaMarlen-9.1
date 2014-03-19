<%@ page import="java.util.Date" %>
<%@ page import="java.util.Hashtable" %>
<%@ page import="java.util.Enumeration" %>
<%@ page import="java.util.Properties" %>
<%@ page import="javax.naming.Context" %>
<%@ page import="javax.naming.InitialContext" %>
<%@ page import="javax.servlet.http.HttpSession" %>
<%@ page import="org.apache.tomcat.jdbc.pool.jmx.ConnectionPoolMBean" %>
<%@ page import="com.pmarlen.web.servlet.SessionInfo" %>


<%
	ConnectionPoolMBean cpMBean = null;
	Hashtable<String,String> cpMBeanDescHT = new Hashtable<String,String>();
	try {
		InitialContext envContext = new InitialContext();
		Context initContext = (Context) envContext.lookup("java:/comp/env");
		Object dsObj = initContext.lookup("jdbc/PMARLEN_DB_TEST_DS");

		cpMBean = (org.apache.tomcat.jdbc.pool.jmx.ConnectionPoolMBean) dsObj;		
		
		String infocpBean = cpMBean.toString();
		infocpBean = infocpBean.substring(infocpBean.indexOf("{")+1,infocpBean.indexOf("}"));
		String infoBenaArr[] = infocpBean.split(";");
		for(String ia:infoBenaArr){
			String kv[] = ia.split("=");
			cpMBeanDescHT.put(kv[0].trim(), kv[1].trim());
		}
		
	} catch (Throwable t) {

	}
	
%>

<html>
	<head>
		<title> DIAGNOSTICS </title>
	</head>

	<body>	
		<h3>PMarlen Web-RF3 >> DIAGNOSTICS</h3>
		<a href="pages/home.jsp">Application HOME</a> <br/>
<%
if(cpMBean != null){
%>
		<h3>ConnectionPoolMBean (Tomcat)</h3>		
		
		<table border="1" width="70%" align="center">
<%
			Enumeration<String> keysCPMBeanDescHT = cpMBeanDescHT.keys();
			
			while(keysCPMBeanDescHT.hasMoreElements()){
				String keyCPDEsc   = keysCPMBeanDescHT.nextElement();
				String valueCPDEsc = cpMBeanDescHT.get(keyCPDEsc);
%>
			<tr>
				<td>
					<%=keyCPDEsc%>
				</td>
				<td>
					<%=valueCPDEsc%>
				</td>
			</tr>
<%
			}
%>
			<tr>
				<td>
					Time UP:
				</td>
				<td>
					<%=SessionInfo.getLifeTimeUpDiff()%>
				</td>
			</tr>

			<tr>
				<td>
					CP Size
				</td>
				<td>
					<%=cpMBean.getSize()%>
				</td>
			</tr>
			<tr>
				<td>
					CP Active
				</td>
				<td>
					<%=cpMBean.getActive()%>
				</td>
			</tr>
			<tr>
				<td>
					CP Idle
				</td>
				<td>
					<%=cpMBean.getIdle()%>
				</td>
			</tr>
			<tr>
				<td>
					CP Min Idle
				</td>
				<td>
					<%=cpMBean.getMinIdle()%>
				</td>
			</tr>
			<tr>
				<td>
					CP Num Idle
				</td>
				<td>
					<%=cpMBean.getNumIdle()%>
				</td>
			</tr>
			<tr>
				<td>
					CP Num Active
				</td>
				<td>
					<%=cpMBean.getNumActive()%>
				</td>
			</tr>
			<tr>
				<td>
					CP Max Age
				</td>
				<td>
					<%=new Date(cpMBean.getMaxAge())%>
				</td>
			</tr>
<%
			Properties dbPoperties = cpMBean.getDbProperties();
			Enumeration propertiesKeysEnum = dbPoperties.keys();
			while(propertiesKeysEnum.hasMoreElements()){
				String dbPropertyKey   = propertiesKeysEnum.nextElement().toString();
				String dbPropertyValue = dbPoperties.getProperty(dbPropertyKey);
%>
			<tr>
				<td>
					DS PROPERTY: <%=dbPropertyKey%>
				</td>
				<td>
					<%=dbPropertyValue%>
				</td>
			</tr>
<%
			}
%>			
		</table>
<%
}
%>				
	</body>	
</html>