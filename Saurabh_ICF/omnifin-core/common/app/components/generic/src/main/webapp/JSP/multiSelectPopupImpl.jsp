<%@ page language="java"%>
<%@ page session="true"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@include file="/JSP/sessioncheck.jsp" %>
<%@include file="/JSP/commonIncludeContent.jsp" %> 

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>


<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
   <script type="text/javascript" src="<%=path%>/js/commonScript/commonMethods.js"></script>
<SCRIPT>

	var openLOVWindows; //for traking of open LOV
	
  
function closeLOVWindow()
{
	if (openLOVWindows && !openLOVWindows.closed) 
        	openLOVWindows.close();
}  

</script>
<fieldset>
  <TABLE>
    <TR>
      <TD> 
      <input type="hidden" id="contextPath" name="contextPath" value="<%=path %>"/>
  <form id="CountryStateLovForm" name="CountryStateLovForm" action="" method="post"> 
  
        <TABLE>
          <TR>  
               
            <Td>&nbsp;&nbsp;
              
			<select id="countryDesc" name="countryDesc" size="5" multiple="multiple"  style="width: 120">

			</select>
             <img SRC="<%= request.getContextPath()%>/images/lov.gif" onClick="return openMultiSelectLOVCommon(139,'CountryStateLovForm','countryDesc','','', '','','');">
            </Td>
             
            </tr>
           
        </TABLE>
        
    </form>
      </TD>
    </TR>
  </TABLE>
  
   
</fieldset>

