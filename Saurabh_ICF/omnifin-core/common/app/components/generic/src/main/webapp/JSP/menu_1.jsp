<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java"%>
<%@ page session="true"%>
<%@include file="/JSP/sessioncheck.jsp" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ page import="java.util.ArrayList" %>

<html>
	<head>
	<title><bean:message key="title.name"/></title>

	<logic:present name="css">
			 <link type="text/css" rel="stylesheet" href="<%=request.getContextPath() %>/${css }/menustyle.css"/>
		</logic:present>
		<logic:notPresent name="css">
			 <link type="text/css" rel="stylesheet" href="<%=request.getContextPath() %>/css/theme1/menustyle.css"/>
		</logic:notPresent>
 
 <script type="text/javascript" src="<%=request.getContextPath() %>/js/commonScript/commonMethods.js"></script>
 
		<style type="text/css">
/* A few IE bug fixes */
* {
	margin: 0;
	padding: 0;
}

* html ul ul li a {
	height: 100%;
}

* html ul li a {
	height: 100%;
}

* html ul ul li {
	margin-bottom: -1px;
}

body {
	padding-left: 0px;
	font-family:Arial, Helvetica, sans-serif;
	<logic:present name="image">
   		 background:url("../${image }/nav-bg.jpg") repeat-y ;
    </logic:present>
    <logic:notPresent name="image">
    	background:url("../images/theme1/nav-bg.jpg") repeat-y ;
    </logic:notPresent>
}

#theMenu {
	width: 237px;
	height: auto;
	margin: 5px 0;
	padding: 0;
}

/* Some list and link styling */
ul li {
	width: 237px;
}

ul ul li {
    list-style:square inside none;
	width: 206px;
	margin-bottom: 0;
	padding: 4px 4px 4px 9px;
}

ul ul li a {
	color: #fff;
	font-size: 12px;
	text-decoration:none;
}

ul ul li a:hover {
	color: #ffe1ae;
	font-size: 12px;
	text-decoration:none;
}
ul ul li a:active {
	color: #f4d092;
	font-size: 12px;
	text-decoration:none;
}

/* For the xtra menu */
ul ul ul li {
	border-left: none;
	border-bottom: 1px solid #eee;
	padding: 0;
	width: 165px;
	margin-bottom: 0;
	font-size: 10px;
}

ul ul ul li a {
	display: block;
	color: #678B3F;
	padding: 3px 6px;
	font-size: 10px;
}

ul ul ul li a:hover {
	display: block;
	color: #369;
	background-color: #E9F4DF;
	padding: 3px 6px;
	font-size: 10px;
}

li {
	list-style-type: none;
}

h2 {
	margin-top: 1.5em;
}

/* Header links styling */
h3.head a{
	color: #fff;
	display: block;
	font-weight:normal;
	font-size: 12px;
	text-decoration: none;
	<logic:present name="image">
   		background: url(<%=request.getContextPath()%>/${image }/nav-menu.jpg) no-repeat;
    </logic:present>
    <logic:notPresent name="image">
    	background: url(<%=request.getContextPath()%>/images/theme1/nav-menu.jpg) no-repeat;
    </logic:notPresent>
	
	height: 31px;
    line-height: 31px;
    margin-top: 1px;
    padding: 0 0 0 8px;
}

h3.head2 a {
	color: #006699;
	display: block;
	font-size: 12px;
	font-weight: bold;
	text-decoration: none;
	border-bottom: 1px solid #ECECEC;
	background-position: 98% 50%;
	padding: 3px 1px;
}

h3.head a:hover {
	color: #ffe1ae;
	<logic:present name="image">
   		background: url(<%=request.getContextPath()%>/${image }/nav-menu.jpg) no-repeat;
    </logic:present>
    <logic:notPresent name="image">
    	background: url(<%=request.getContextPath()%>/images/theme1/nav-menu.jpg) no-repeat;
    </logic:notPresent>
	
	height: 31px;
    line-height: 31px;
    margin-top: 1px;
    padding: 0 0 0 8px;
}

h3.head2 a:hover {
	color: #006699;
	background: #DBEFFB;
}

h3.selected a {

	<logic:present name="image">
   		background: url(<%=request.getContextPath()%>/${image }/nav-menu.jpg) no-repeat;
    </logic:present>
    <logic:notPresent name="image">
    	background: url(<%=request.getContextPath()%>/images/theme1/nav-menu.jpg) no-repeat;
    </logic:notPresent>
	
	color: #fff;
	height: 31px;
    line-height: 31px;
    margin-top: 1px;
    padding: 0 0 0 8px;
}

h3.selected a:hover {
<logic:present name="image">
   		background: url(<%=request.getContextPath()%>/${image }/nav-menu.jpg) no-repeat;
    </logic:present>
    <logic:notPresent name="image">
    	background: url(<%=request.getContextPath()%>/images/theme1/nav-menu.jpg) no-repeat;
    </logic:notPresent>
	
	color: #fff;
    height: 31px;
    line-height: 31px;
    margin-top: 1px;
    padding: 0 0 0 8px;
}

/* Xtra Header links styling */
h4.head a {
	color: #449805;
	display: block;
	font-size: 12px;
	font-weight: bold;
	text-decoration: none;
	border-bottom: 1px solid #ECECEC;
	<logic:present name="image">
   		background: url(<%=request.getContextPath()%>/${image }/down.gif) no-repeat;
    </logic:present>
    <logic:notPresent name="image">
    	background: url(<%=request.getContextPath()%>/images/theme1/down.gif) no-repeat;
    </logic:notPresent>
	
	background-position: 98% 50%;
	padding: 3px 1px;
}

h4.head2 a {
	color: #449805;
	display: block;
	font-size: 12px;
	font-weight: bold;
	text-decoration: none;
	border-bottom: 1px solid #ECECEC;
	background: ;
	background-position: 98% 50%;
	padding: 3px 1px;
}

h4.head a:hover {
	color: #36a;
	<logic:present name="image">
   		background: #EBF5E2 url(<%=request.getContextPath()%>/${image }/down.gif) no-repeat;
    </logic:present>
    <logic:notPresent name="image">
    	background: #EBF5E2 url(<%=request.getContextPath()%>/images/theme1/down.gif) no-repeat;
    </logic:notPresent>
	
	background-position: 98% 50%;
	font-size: 12px;
	font-weight: bold;
}

h4.head2 a:hover {
	color: #36a;
	background: #EBF5E2;
	background-position: 98% 50%;
}

h4.selected a {
	<logic:present name="image">
   		background: #EBF5E2 url(<%=request.getContextPath()%>/${image }/up.gif) no-repeat;
    </logic:present>
    <logic:notPresent name="image">
    	background: #EBF5E2 url(<%=request.getContextPath()%>/images/theme1/up.gif) no-repeat;
    </logic:notPresent>

	background-position: 98% 50%;
	color: #36a;
	padding: 3px 6px;
}

h4.selected a:hover {
<logic:present name="image">
   		background: #EBF5E2 url(<%=request.getContextPath()%>/${image }/up.gif) no-repeat;
    </logic:present>
    <logic:notPresent name="image">
    	background: #EBF5E2 url(<%=request.getContextPath()%>/images/theme1/up.gif) no-repeat;
    </logic:notPresent>

	
	background-position: 98% 50%;
	color: #36a;
}
</style>

	</head>
	<body oncontextmenu="return false;">
	
	<form action="" method="post" id="commonFormMenu" name="commonFormMenu">
		<script type="text/javascript"
			src="<%=request.getContextPath()%>/js/jquery.js"></script>
		<script type="text/javascript"
			src="<%=request.getContextPath()%>/js/accordion.js"></script>
			<script type="text/javascript"
		src="<%=request.getContextPath()%>/js/popup.js"></script>
		<script type="text/javascript">
jQuery().ready(function(){	
	// applying the settings
	jQuery('#theMenu').Accordion({
		active: 'h3.selected',
		header: 'h3.head',
		alwaysOpen: false,
		animated: true,
		showSpeed: 400,
		hideSpeed: 800
	});

});	
 
</script>
<script language="Javascript">
function disableAnchor(key,id,max){


var maxNum=parseInt(max);
var anchorNames=document.getElementsByName("hrefId");
//alert("disableAnchor in anchorNames.length "+anchorNames.length);
 for (var j=0;j<maxNum;j++ ) {
     
    
     for ( var i=0;i<anchorNames.length;i++ ) {
          	
          
     	var anchorId="hrefIda"+j+"b"+i;
     	
     	if(id!=anchorId)
     	{
     	
     	     var ab=document.getElementById(anchorId);
     	     //alert("disableAnchor in ab "+ab+" anchorId: "+anchorId);
     	     if(ab!=null && ab!='undefined')
     	     {
	     		 var href = ab.getAttribute('href');
	     		  if(href!=null && href!='undefined')
     	         {
     	      
		     		 ab.setAttribute('href_bak', href);
		     		 ab.removeAttribute('href');
		     	     ab.style.color="gray";
		            
	             }
	           
             }
     	}
     	else
     	{
     	   
     		var ab=document.getElementById(anchorId);
     	     //alert("disableAnchor in ab "+ab+" anchorId: "+anchorId);
     	     if(ab!=null && ab!='undefined')
     	     {
	     		 var href = ab.getAttribute('href');
	     		  if(href!=null && href!='undefined')
     	         {
     	           
     	             top.top.frames['content'].location.href="<%=request.getContextPath() %>/PageLevelAuthorization.do?parameter="+key;
     	          	 ab.setAttribute('href_bak', href);
		     		 ab.removeAttribute('href');
		     	     ab.style.color="gray";
		            
	             }
	           
             }
     		
     	}
     	
     }
   }

}

</script>
<%	 	
        
         ArrayList arr= new ArrayList();
         if(session.getAttribute("leftsidemenulist")!= null){
         arr=(ArrayList)session.getAttribute("leftsidemenulist"); 
         }
     
     
 %>
		<table width="100%" border="0" cellspacing="1" cellpadding="4">
		 
			<tr>
			
				<td>
					<div class="headers">
				${sessionScope.modulename}
		
					</div>
					
					<% if(arr!=null)      {%>
					<ul id="theMenu">
							
						<% 
   						int max=arr.size(); %>
   						<input name="upperMaxSize" type="hidden" id="upperMaxSize" value='<%=max %>'/>
   						<% 
						for(int i=0;i<max;i++){ %>
											
						
						<% if(i==0||i%2==0){
						 com.login.roleManager.Menu menu=(com.login.roleManager.Menu)arr.get(i);%>
									<li><h3 class="head">
									
											<div>
													<a href="javascript:;">
										<%=menu.getLeftHeaderMenu() %>
											</a>
											</div>
									
										
									</h3>
										<%	}else{ 
					 com.login.dao.UserPermissionVo userPerVo=(com.login.dao.UserPermissionVo)arr.get(i);
					  ArrayList<com.login.roleManager.Menu> list=new ArrayList<com.login.roleManager.Menu>();
                      list=(ArrayList<com.login.roleManager.Menu>)userPerVo.getLeftSubMenuList();%>
    
										
									
												
											
						 <%  if(list != null){
						 
						  int max1=list.size(); 
						 
						 %>
						  
						 <ul>	
						 <%   for(int j=0;j<max1;j++){
						 
						        com.login.roleManager.Menu subMenu=(com.login.roleManager.Menu)list.get(j);
                                  %>                	<li>
                                  
												 <a name="hrefId" id="hrefIda<%=i %>b<%=j %>" href="<%=request.getContextPath() %>/PageLevelAuthorization.do?parameter=<%=subMenu.getLeftSubMenuID() %>" onclick="javascript:disableAnchor('<%=subMenu.getLeftSubMenuID() %>','hrefIda<%=i %>b<%=j %>','<%=max %>');" target="content">
																<%=subMenu.getLeftHeaderMenu() %></a>
												</li>
									<% }%></ul><%}%></li>
									<%}%>   	
											
						 <%}
						 }
						  %>
							
					</ul>



				</td>


			</tr>
		</table>
		<div id="leftcolumn"></div>

		<!-- Left Nav for non script pages -->
<div id="modal" style=" background-color:none; padding:25px; font-size:150%; display:none;">
	
</div>

 
</form>

	</body>
</html>