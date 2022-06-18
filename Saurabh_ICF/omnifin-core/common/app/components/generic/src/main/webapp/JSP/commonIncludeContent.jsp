<%@ page language="java"  pageEncoding="ISO-8859-1"%>
<%@ page session="true" %>


<head>
<%
        String userIdAutoLogout=null;
	 	com.login.roleManager.UserObject userobjAutoLogout=(com.login.roleManager.UserObject)session.getAttribute("userobject");
	 	if(userobjAutoLogout!=null)
		userIdAutoLogout=userobjAutoLogout.getUserId();

%>

	    <meta http-equiv="refresh" content="<%= session.getMaxInactiveInterval()%>;url= <%=request.getContextPath()%>/logoff.do?userIdAutoLogout=<%=userIdAutoLogout %>" />
	    <meta http-equiv="content-type" content="text/html; charset=iso-8859-1" />
	   <meta http-equiv="pragma" content="no-cache, no-store, must-revalidate" /><!-- Rohit Chnages for security -->
	    <meta http-equiv="cache-control" content="no-cache, no-store, must-revalidate" /><!-- Rohit Chanegs for security -->
	    <meta http-equiv="expires" content="0" />
	    
<script type="text/javascript" > 
 function enableAnchor(){

	//alert("enableAnchor ");
	var upperMaxSize=parent.parent.menu.document.commonFormMenu.upperMaxSize;
	
	var maxNum=parseInt(upperMaxSize.value);

	 for (var j=0;j<maxNum;j++ ) {
	     
	    
	     for ( var i=0;i<100;i++ ) {
	          	
	          
	     	     var anchorId="hrefIda"+j+"b"+i;
	     	
	     	 
	     	     var ab=parent.parent.menu.document.getElementById(anchorId);
	     	    
	     	     if(ab!=null && ab!='undefined')
	     	     {
	     	         if(ab.attributes['href_bak']!=null && ab.attributes['href_bak']!='undefined')
	     	         {
			     		// alert("enableAnchor in atribute: "+ab.attributes['href_bak'].nodeValue);
			     		 if(ab.attributes['href_bak'].nodeValue!=null && ab.attributes['href_bak'].nodeValue!='undefined')
			     		 {
				     		 ab.setAttribute('href', ab.attributes['href_bak'].nodeValue);
				     		 ab.removeAttribute('href_bak');
				             ab.style.color="white";
	     	            }
		             }
		           
	             }
	     	 }
	   }


	}
	</script> 
</head>