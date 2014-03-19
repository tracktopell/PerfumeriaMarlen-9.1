<%@ page language="java" import="com.pmarlen.web.common	.view.messages.Messages" %>
<%@ page language="java" import="java.util.Locale" %>
<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<jsp:useBean id="versionInfo" class="com.pmarlen.web.common.view.messages.VersionInfo"  scope="session"/>

<c:set var="language" value="${not empty param.language ? param.language : not empty language ? language : pageContext.request.locale}" scope="session" />

<fmt:setLocale value="${language}" />
<fmt:setBundle basename="com.pmarlen.web.common.view.properties.UIMessages" />

<%
	//Messages messages = new Messages("com.pmarlen.web.common.view.properties.UIMessages");
	String acceptLanguage = request.getHeader("Accept-Language");
	System.err.println("Header[Accept-Language]:" + acceptLanguage);
	Locale reqLocale = request.getLocale();
	System.err.println("Request Locale : " + reqLocale);
	String userAgent = request.getHeader("User-Agent");
	System.err.println("Header[User-Agent]:" + userAgent);

	if (userAgent.toLowerCase().contains("android")) {
		request.getSession().setAttribute("viewingFromMobile", 1);
		System.err.println("\t-->>>viewingFromMobile=1");
	} else {
		System.err.println("\t-->>>viewingFromMobile=0, es normal");
	}
%>
<html>
    <head>
		<title><fmt:message key="MAIN_SHORT_TITLE" /> : <fmt:message key="MAIN_SYSTEM_ACCESS" /></title>

		<link   href="<%= request.getContextPath()%>/css/login.jsp"				rel="stylesheet"     type="text/css" />
        <link 	href="<%= request.getContextPath()%>/images/Logo_icono_2.gif" 	rel="shortcut icon"  type="image/x-icon"/>
        <link	href="<%= request.getContextPath()%>/images/Logo_icono_2.gif"   rel="icon"           type="image/x-icon"/>

		<script >
			function init() {
				console.log("-->>init()");
				timedMsg();
				document.forms[0].j_username.focus();
			}
			function timedMsg() {
				console.log("-->>timedMsg()");
				var t = setTimeout("returnToHome();",<%=((request.getSession().getMaxInactiveInterval() * 1000 - 10))%>);
			}
			function returnToHome() {
				console.log("-->>returnToHome()");
				window.location = "<%=request.getContextPath()%>";
			}

		</script>

    </head>

    <body onload="init();">	
        <form>
			<div style="text-align: right">				
			<!--	
			<input type="radio" name="language" value="en" ${language == 'en' ? 'checked' : ''} onclick="submit();"> <img src="<%= request.getContextPath()%>/images/flag-en.gif"/></input>
			<input type="radio" name="language" value="es" ${language == 'es' ? 'checked' : ''} onclick="submit();"> <img src="<%= request.getContextPath()%>/images/flag-es_MX.gif"/></input>
			-->
			</div>
        </form>
		<br/>
		<br/>		
        <form action="j_security_check" method="post" id="loginForm" onsubmit="return sendAjaxBeforeSubmit();">

			<table cellpadding="0" cellspacing="0" border="0" width="800" align="center">
				<tr>
					<td align="center"  valign="middle" width="200" >
						<img src="<%= request.getContextPath()%>/images/LogoOriginal_5_Q25.jpg"/>
					</td>
					<td align="center"  valign="middle">

						<h1> <fmt:message key="MAIN_LEGEND" /></h1>
						<h2> <fmt:message key="MAIN_SUBLEGEND" /></h2>
						<h3> <fmt:message key="MAIN_SYSTEM_NAME" /></h3>

					</td>
				</tr>
			</table>
			<br/>
			<br/>
			<table cellpadding="0" cellspacing="0" border="0" width="700" align="center">

				<tr>
					<td align="center"  valign="middle" width="300" >
						<img src="<%= request.getContextPath()%>/images/almacen_rack_Q25.jpg"/>
					</td>
					<td>
						<table class="login_table_panel" border="0">
							<tr>
								<td colspan="3" valign="middle" align="center" style="background-color:gray;">
									<fmt:message key="MAIN_SYSTEM_ACCESS" />
								</td>
							</tr>
							<tr>
								<td width="20%" rowspan="6" valign="middle" align="center" >
									<img src="<%= request.getContextPath()%>/images/secure_Q25.jpg" />
								</td>
							</tr>
							<tr><td colspan="2">&nbsp;</td></tr>
							<tr><td colspan="2">&nbsp;</td></tr>
							<tr>
								<td width="40%" align="right">
									<span style="font-size : 10px;"><fmt:message key="LOGIN_USER" /> :</span>
								</td>
								<td align="left">
									<input type="text" name="j_username" value="" size="8"/>
								</td>
							</tr>
							<tr>
								<td width="40%" align="right">
									<span style="font-size : 10px;"><fmt:message key="LOGIN_PASSWORD" /> :</span>
								</td>
								<td align="left">
									<input type="password" name="j_password" value="" size="8"/>
								</td>
							</tr>

							<tr>
								<td colspan="2">
									<%
										if (request.getParameter("error") != null) {
									%>									
									<div style="color: red;vertical-align: middle;">
										<br/>
										<img src="<%= request.getContextPath()%>/images/error16x16.gif" />&nbsp; 
										<span style="font-size : 10px;"><fmt:message key="LOGIN_ERROR_AUTHENTICATE" /></span>
										<br/>
										<br/>
									</div>
									<%
									} else {
									%>
									<br/>
									<br/>
									<%    }
									%>									
								</td>
							</tr>												

							<tr>
								<td colspan="3" width="100%" align="center">										
									<input type="submit" value="<fmt:message key="COMMON_OK" />"/>
								</td>
							</tr>
						</table>
					</td>
				</tr>
			</table>

		</td>
	</tr>
</table>
<br/>
<br/>
<table cellpadding="0" cellspacing="0" border="0" align="center">
	<tr>
		<td align="center" height="20">
			<a href="http://perfumeriamarlen.com.mx" style="color:#000; display:block" class="footer-text">[<fmt:message key="ENTERPRISE_INFO" />]</a>
		</td>
	</tr>
	<!--
	<tr>
		<td align="center">
			<div style="color:#A0A0A0; display:block" class="footer-text">
				<a href="http://xpressosystems.com">XPRESSO SYSTEMS</a>
			</div>
		</td>
	</tr>
	-->
	<tr>
		<td align="center">
			<div style="color:#A0A0A0; display:block" class="footer-text">
				<fmt:message key="APP_BUILD_LABEL" /> : <jsp:getProperty name="versionInfo" property="maven_build_timestamp" /> |
				<fmt:message key="APP_VERSION_LABEL" /> : <jsp:getProperty name="versionInfo" property="version" />
			</div>
		</td>
	</tr>
</table>

</form>

</body>	
</html>
