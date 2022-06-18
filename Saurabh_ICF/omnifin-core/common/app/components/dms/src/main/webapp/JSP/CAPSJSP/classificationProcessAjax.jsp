<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@include file="/JSP/sessioncheck.jsp" %> <%@include file="/JSP/commonIncludeContent.jsp" %> 

<div id="revisedcontainer">	
	<html:form action="/classificationProcessAction" styleId="classificationForm"> 
  <fieldset>
  <legend><bean:message key="lbl.classificationProcess" /></legend>
	<input type="hidden" name="contextPath" id="contextPath" value="<%=request.getContextPath()%>" />
	<table width="100%" border="0" cellspacing="1" cellpadding="1">
		  <tr>
      <td class="white2" style="width:220px;"><strong><input type="checkbox" name="chk" id="chk" disabled="disabled" checked="checked" /></strong></td>
      <td class="white2" style="width:220px;"><strong><bean:message key="lbl.processName"/> </strong></td>
      <%--<td class="white2" style="width:220px;" align="center"><strong> <bean:message key="lbl.imagetimer"/> </strong></td>--%>
      <td class="white2" style="width:220px;"><strong><bean:message key="lbl.laststartTime"/></strong></td>
      <td class="white2" style="width:220px;"><strong><bean:message key="lbl.lastendTime"/> </strong></td>
      <td class="white2" style="width:220px;"><strong><bean:message key="lbl.beginingOfDaystatus"/></strong></td>
 
    </tr>
    <logic:present name="classificationData">
    <logic:notEmpty name="classificationData">
    <logic:iterate id="classificationDataObj" name="classificationData" indexId="count">
	<tr>
	 <td class="white2" style="width:220px;"><input type="checkbox" name="chk" id="chk" disabled="disabled" checked="checked" /></td>
	 <td class="white2" style="width:220px;"><bean:write name="classificationDataObj" property="processName"/></td>
     <td class="white2" style="width:220px;"><bean:write name="classificationDataObj" property="lastStartTime"/></td>
     <td class="white2" style="width:220px;"><bean:write name="classificationDataObj" property="lastEndStartTime"/></td>
     <td class="white2" style="width:220px;">
     <input type="hidden" name="statusFlag" id="statusFlag<%=count.intValue()+1%>" value="<bean:write name='classificationDataObj' property='status'/>" />
     <logic:notPresent name="processflag">
      <div id="process " style="display: block">
     &nbsp;&nbsp;&nbsp;<strong><font color="Black"><bean:message key="lbl.noProcess"/></font></strong>
      </div>
      </logic:notPresent>
      <logic:present name="processflag">
    <logic:equal value="NI" name="classificationDataObj" property="status">
    <div id="process " style="display:block">
    <img align="middle"  src="<%=request.getContextPath()%>/images/theme1/blue.gif" width="20" height="20" ></img>
 	&nbsp;&nbsp;&nbsp;<strong><bean:message key="lbl.noProcess"/></strong>
    </div>
    </logic:equal>
     <logic:equal value="P" name="classificationDataObj" property="status">
    <div id="process " style="display:block">
    <img align="middle"  src="<%=request.getContextPath()%>/images/theme1/loder-blue.gif" width="20" height="20" ></img>
 	&nbsp;&nbsp;&nbsp;<strong><bean:message key="lbl.process"/></strong>
    </div>
    </logic:equal>
      <logic:equal value="F" name="classificationDataObj" property="status">
     <div id="processFinish " style="display: block">
   <img align="middle" src="<%=request.getContextPath()%>/images/theme1/green.gif"  width="20" height="20" ></img>
  	 &nbsp;&nbsp;&nbsp;<strong><font color="black"><bean:message key="lbl.processFinish"/></font></strong>
    </div>
    </logic:equal>
     <logic:equal value="E" name="classificationDataObj" property="status">
     <div id="errorProcess " style="display: block">
   <img align="middle"  src="<%=request.getContextPath()%>/images/theme1/red.gif" width="20" height="20" ></img>
    &nbsp;&nbsp;&nbsp;<strong><font color="Red"><bean:message key="lbl.ErrorInProcess"/></font></strong>
    </div>
    </logic:equal>
    </logic:present>
     </td>
 
	</tr>
	</logic:iterate>
	</logic:notEmpty>
	</logic:present>
	
 </table>
 </fieldset>

  <table width="30%" border="0" cellspacing="1" cellpadding="1">
   <tr>
	   <td><html:button property="startProcess" styleId="startProcess" styleClass="topformbutton2" value="Start Process" onclick="classificationStartprocess();"></html:button></td>
	   <td><b><bean:message key="lbl.autoRefresh" /></b></td>
	    <td>
		   <html:select property="refreshFlag" styleId="refreshFlag" value="${refreshFlag}">
		    	<html:option value="Y">YES</html:option>
		    	<html:option value="N">NO</html:option>
		    </html:select>
	    </td>
   </tr>
  </table>
  </html:form>
</div>