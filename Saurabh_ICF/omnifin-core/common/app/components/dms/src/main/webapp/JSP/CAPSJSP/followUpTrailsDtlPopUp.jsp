<!--Author Name : Kanika Maheshwari -->
<!--Date of Creation :11 Nov 2011 -->
<!--Purpose  : Follow up trails Page -->
<!--Documentation : -->

<%@ page language="java" %>
<%@ page session="true"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@include file="/JSP/sessioncheck.jsp" %> <%@include file="/JSP/commonIncludeContent.jsp" %> 
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display" %>
<%@ page language="java" import="java.util.ResourceBundle.*" %>
<%@ page language="java" import="java.util.*" %>
<html:html>
<head>
<%	ResourceBundle resource = ResourceBundle.getBundle("com.yourcompany.struts.ApplicationResources");
int no=Integer.parseInt(resource.getString("msg.pageSizeForMaster"));
request.setAttribute("no",no); %>
<title><bean:message key="a3s.noida" /></title>

		<logic:present name="css">
			<link type="text/css" rel="stylesheet" href="<%=request.getContextPath() %>/${css }/displaytable.css"/>
		</logic:present>
		<logic:notPresent name="css">
			<link type="text/css" rel="stylesheet" href="<%=request.getContextPath() %>/css/theme1/displaytable.css"/>
		</logic:notPresent>
<script type="text/javascript" src="<%=request.getContextPath()%>/development-bundle/jquery-1.4.2.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/commonMethods.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/capsScript/queueCodeScript.js"></script>
<link type="text/css" href="<%=request.getContextPath()%>/development-bundle/demos/demos.css" rel="stylesheet" />
<script type="text/javascript" src="<%=request.getContextPath()%>/js/commonScript/calendar.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/capsScript/contactRecordingScript.js"></script>
<script type="text/javascript">
function fntab()
{
     if(document.getElementById('modifyRecord').value =='M')	
     {
    	 document.getElementById('queueCodeMasterForm').queueDesc.focus();
     }
     else if(document.getElementById('modifyRecord').value =='I')
     {
    	document.getElementById('queueCodeMasterForm').queueCode.focus();
     }
     return true;
}

	</script>
	<!--[if IE]>
	<style type="text/css">
		.opacity{
			 position:fixed;
  			 _position:absolute;
  			 top:0;
 			 _top:expression(eval(document.body.scrollTop));
		}
	</style>
	<! [endif] -->	

</head>

<body>
 
 		
 	
<html:form styleId="followUpTrailForm"  method="post"  action="/collAddressAction" >

       <input type="hidden" name="contextPath" value="<%=request.getContextPath() %>" id="contextPath"/>
       <logic:present name="image">
    	   		<input type="hidden" id="CalImage" value='<%=request.getContextPath() %>/${image}/calendar.gif' />
            </logic:present>
    		<logic:notPresent name="image">
    			<input type="hidden" id="CalImage" value='<%=request.getContextPath() %>/images/theme1/calendar.gif'/>
    		</logic:notPresent>
    		<input type="hidden" id="businessdate" value='${userobject.businessdate}'/>
		
	<input type="hidden" name="formatD" id="formatD" value="<bean:message key="lbl.dateFormat"/>" />
       
       <br />
<fieldset>

<legend><bean:message key="lbl.followUpTrailGrid" /></legend>

<fieldset>


    <logic:present name="followUpTrailDetail">
 
 
    <display:table  id="followUpTrailDetail"  name="followUpTrailDetail" style="width: 100%" class="dataTable" pagesize="${requestScope.no}" cellspacing="1" partialList="true" size="${followUpTrailDetail[0].totalRecordSize}" requestURI="/collButtonAction.do" >
    <display:setProperty name="paging.banner.placement"  value="bottom"/>
    <display:setProperty name="locale.resolver" value="org.displaytag.localization.I18nStrutsAdapter"></display:setProperty>
    <display:setProperty name="locale.provider" value="org.displaytag.localization.I18nStrutsAdapter"></display:setProperty>
    <display:column property="actionDate" titleKey="lbl.actionDate"  sortable="true"  />
	<display:column property="actionCode" titleKey="lbl.actionCode"  sortable="true"  />
	<display:column property="contactMode" titleKey="lbl.contactMode"  sortable="true"  />
	<display:column property="contactPlace" titleKey="lbl.placeContacted"  sortable="true"  />
	<display:column property="actionAmt" titleKey="lbl.actionAmount"  sortable="true"  />
	<display:column property="nxtActionDate" titleKey="lbl.nxtActionDate"  sortable="true"  />
	<display:column property="nxtActionTime" titleKey="lbl.nxtActionTime"  sortable="true"  />
	<display:column property="actionTime" titleKey="lbl.ActionTime"  sortable="true"  />
	<display:column property="applicantType" titleKey="lbl.applicantType"  sortable="true"  />
	<display:column property="contactTo" titleKey="lbl.ContactTo"  sortable="true"  />
	<display:column property="relationshipS" titleKey="lbl.nomineeRelationship"  sortable="true"  />
	<display:column property="contactBy" titleKey="lbl.ContactBy"  sortable="true"  />
	<display:column property="remarks" titleKey="lbl.FollowUpRemark"  sortable="true"  />
	
</display:table>
</logic:present>



	</fieldset>
<logic:notPresent name="followUpTrailDetail">



				<table width="100%" cellspacing="0" cellpadding="0" border="0">
					<tbody>
						<tr>
							<td class="gridtd">
								<table width="100%" cellspacing="1" cellpadding="1" border="0">
									<tbody>
										<tr align="center" class="white2">
											<td>
												<b><bean:message key="lbl.actionDate" />
												</b>
											</td>
											<td>
												<b><bean:message key="lbl.actionCode" />
												</b>
											</td>
											<td>
												<b><bean:message key="lbl.contactMode" />
												</b>
											</td>
												<td>
												<b><bean:message key="lbl.placeContacted" />
												</b>
											</td>
												<td>
												<b><bean:message key="lbl.actionAmount" />
												</b>
											</td>
												<td>
												<b><bean:message key="lbl.nxtActionDate" />
												</b>
											</td>
												<td>
												<b><bean:message key="lbl.nxtActionTime" />
												</b>
											</td>
												<td>
												<b><bean:message key="lbl.ActionTime" />
												</b>
											</td>
											<td>
												<b><bean:message key="lbl.applicantType" />
												</b>
											</td>
												<td>
												<b><bean:message key="lbl.ContactTo" />
												</b>
											</td>
											<!-- Rohit Changes Starts for relationShip -->
												<td>
												<b><bean:message key="lbl.nomineeRelationship" />
												</b>
											</td>
											<!-- Rohit Changes end for relationShip -->
											<td>
												<b><bean:message key="lbl.ContactBy" />
												</b>
											</td>
												<td>
												<b><bean:message key="lbl.FollowUpRemark" />
												</b>
											</td>
											
										</tr>
										<tr class="white2" >
	                                  			<td colspan="8"><bean:message key="lbl.noDataFound" /></td>
                                    	</tr>
										
									</tbody>
								</table>
							</td>
						</tr>
					</tbody>
				</table>

			</logic:notPresent>
</fieldset>
</fieldset>
	</html:form>
	
		<logic:present name="sms">
     <script type="text/javascript">

    
	
	if('<%=request.getAttribute("sms").toString()%>'=='N')
	{
		alert("<bean:message key="lbl.noDataFound" />");
		self.close();
	}
	
	

</script>
</logic:present>
<div  align="center" class="opacity" style="display: none;"  id="processingImage" ></div>
  </body>
		</html:html>