/* 
    Document   : login
    Created on : 6/03/2013, 12:09:15 PM
    Author     : alfredo
    Description: login style
*/

root { 
    display: block;
}

body_image{
	background-image: url('<%= request.getContextPath()%>/images/bg_1.jpg');
	background-repeat: no-repeat;
	background-position:right top;
	background-color:#ffffff;				
	font-family: Arial,Helvetica,sans-serif;
}

body{
	background-color:#829498;				
	font-family: Arial,Helvetica,sans-serif;
}

.rich-panel-header{
	background-image: url('<%= request.getContextPath()%>/images/title.PNG');
}	
.login_table_panel
{
	background-color:lightgrey;
	border-collapse:collapse;
	border:1px solid black;
}
.footer-text{
	color: darkslategrey;
	font-family: Arial,Helvetica,sans-serif;
	font-size: 8;				
}
