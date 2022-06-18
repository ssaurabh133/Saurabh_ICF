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
              
               Country Code : <input type="text" name="lovCountryCode" id="lovCountryCode" size="20" value="${sessionScope.pParentGroup}" class="txtBackColor" readonly tabindex="-1">&nbsp;
              <input type="hidden" name="txtCountryCode" id="txtCountryCode" size="20" value="${sessionScope.strParentOption}"/>
             <img SRC="<%= request.getContextPath()%>/images/lov.gif" onClick="return openLOVCommon(4,'CountryStateLovForm','lovCountryCode','','', '','','');">
              State Code : <input type="text" name="lovStateCode" id="lovStateCode" size="20" class="txtBackColor" readonly>
               <input type="hidden" name="txtStateCode" id="txtStateCode" size="20"/>
              <button class="clsLovButton" name="PopupOpenBtn" value="" onClick="return openLOVCommon(5,'CountryStateLovForm','lovStateCode','lovCountryCode','txtStateCode', 'txtCountryCode','Parent Code Not present','');"><img SRC="<%= request.getContextPath()%>/images/lov.gif"></button>
<!--           																						 (lovID,strFormName,strFieldName,strParentGroup,strChildGroup,strParentOption,strMessages)-->
            </Td>
             
            </tr>
           
        </TABLE>
        
    </form>
      </TD>
    </TR>
  </TABLE>
  
   
</fieldset>

