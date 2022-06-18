<%@ page import="com.login.dao.UserPermissionVo" %>
<%@ page language="java"%>
<%@ page session="true"%>
<%@ page import="java.util.ArrayList" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@include file="/JSP/sessioncheck.jsp" %>
<%@include file="/JSP/commonIncludeContent.jsp" %> 

	<html>
	
	<head>
		<meta http-equiv="content-type" content="text/html; charset=iso-8859-1" />
	
		    <meta name="author" content="" />
				
		   <script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/commonMethods.js"></script>
	    <style type="text/css">
<!--
.style1 {font-size: 18px}
-->
        </style>
      
    </head>
<body oncontextmenu="return false" onclick="parent_disable();" >

              
  <table height="280" border="1" align="center" width="70%" style="margin-top:2%;" >      
     
 <logic:present name="headermenulist">
 
  <% 
        java.util.ResourceBundle resource = java.util.ResourceBundle.getBundle("com.yourcompany.struts.utill");
	    String moduleColumnsInRow=resource.getString("count.moduleColumnsInRow");
        ArrayList<UserPermissionVo> arr = (ArrayList<UserPermissionVo>)session.getAttribute("headermenulist");
		int moduleColumns =Integer.parseInt(moduleColumnsInRow);
  		for(int i=0;i<arr.size();i=i+moduleColumns)
  		{
  			  			
  %>
   
  <tr>

     <%
          for(int j=0;j<moduleColumns;j++)
		  {
        	
        	  UserPermissionVo user=user=(UserPermissionVo)arr.get(j+i);
           	  request.setAttribute("obHeadermenulist", user);
     %>
     <logic:notEqual name="obHeadermenulist" property="roleId" value="0"> 
        <logic:notEqual name="obHeadermenulist" property="roleId" value="-1">
	    <td width="350" height="140" valign="top">
		     <table align="center" >
			    <tr>
			    <td>
			       <logic:present name="image">
			    	    <img ondragstart="return false" onselectstart="return false" src="<%=request.getContextPath() %>/${image }/OmniFin.png"></img>
			      </logic:present>
			    	<logic:notPresent name="image">
			    		<img ondragstart="return false" onselectstart="return false" src="<%=request.getContextPath() %>/images/theme1/OmniFin.png"></img>
			    	</logic:notPresent>
			    </td>
			    
			    <td>
				    <div align="center" class="style1">
						
						     <a ondragstart="return false" onselectstart="return false" href="<%=request.getContextPath() %>/LeftMenuLoadAction.do?modid=${obHeadermenulist.moduleid}&roleid=${obHeadermenulist.roleId}" target="_parent">
						     ${obHeadermenulist.moduledesc}</a> 
					
				    </div>
			    </td>
			    
			    </tr>
		    </table>
		    <p align="justify">${obHeadermenulist.moduleremarks}</p>
	   
	    </td>
	    </logic:notEqual>
     </logic:notEqual> 
      <logic:equal name="obHeadermenulist" property="roleId" value="0"> 
	    <td width="350" height="140" valign="top">
		     <table align="center" >
			    <tr>
			    <td>
			       <logic:present name="image">
			    	    <img ondragstart="return false" onselectstart="return false" src="<%=request.getContextPath() %>/${image }/OmniFin.png"></img>
			      </logic:present>
			    	<logic:notPresent name="image">
			    		<img ondragstart="return false" onselectstart="return false" src="<%=request.getContextPath() %>/images/theme1/OmniFin.png"></img>
			    	</logic:notPresent>
			    </td>
			    
			    <td>
				    <div align="center" class="style1">
				
						 <font style="color:#0000FF;"> ${obHeadermenulist.moduledesc}</font>
				
				    </div>
			    </td>
			    
			    </tr>
		    </table>
		    <p align="justify">${obHeadermenulist.moduleremarks}</p>
	   
	    </td>
     </logic:equal> 
   <%
		  }
   %>
     
  </tr>
 <%
  		}
 %>
  </logic:present>
</table>

</body>

</html>
