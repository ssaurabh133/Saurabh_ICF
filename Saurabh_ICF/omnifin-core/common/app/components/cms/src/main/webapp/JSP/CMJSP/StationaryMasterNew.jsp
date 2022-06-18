
<!--Author Name :- Mradul Agarwal-->
<!--Date of Creation : 19_Jan_2013-->
<!--Purpose :-  Stationary Addition Screen-->

<%@ page language="java" pageEncoding="ISO-8859-1"%>
<%@page import="com.logger.LoggerMsg"%>
<%@include file="/JSP/sessioncheck.jsp" %> <%@include file="/JSP/commonIncludeContent.jsp" %> 

<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display" %>
<%@ page language="java" import="java.util.ResourceBundle.*" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<HTML>
 <head>
 
 		 <!-- css for Datepicker -->
		<link type="text/css" href="<%=request.getContextPath() %>/development-bundle/demos/demos.css" rel="stylesheet" />
		<link type="text/css" href="<%=request.getContextPath() %>/development-bundle/themes/base/jquery.ui.all.css" rel="stylesheet" />
	      <!-- jQuery for Datepicker -->
		
		<script type="text/javascript" src="<%=request.getContextPath() %>/development-bundle/jquery-1.4.2.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath() %>/development-bundle/ui/jquery.ui.core.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath() %>/development-bundle/ui/jquery.ui.widget.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath() %>/development-bundle/ui/jquery.ui.datepicker.js"></script>
		<script type="text/javascript" 	src=" <%=request.getContextPath() %>/js/formatnumber.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/js/cmScript/stationaryMasterScript.js"></script>
<meta http-equiv="content-type" content="text/html; charset=iso-8859-1" />
<meta name="author" content="" />
<logic:present name="css">
			<link type="text/css" rel="stylesheet" href="<%=request.getContextPath() %>/${css }/displaytable.css"/>
		</logic:present>
		<logic:notPresent name="css">
			<link type="text/css" rel="stylesheet" href="<%=request.getContextPath() %>/css/theme1/displaytable.css"/>
		</logic:notPresent>
</head>

	

<script type="text/javascript">
			$(function() {
				$("#additionDate").datepicker({
						changeMonth: true,
					changeYear: true,
		            yearRange: '1900:+10',
		            showOn: 'both',
						<logic:present name="image">
    	   		buttonImage: '<%=request.getContextPath()%>/${image }/calendar.gif',
            </logic:present>
    		<logic:notPresent name="image">
    			buttonImage: '<%=request.getContextPath()%>/images/theme1/calendar.gif',
    		</logic:notPresent>
					buttonImageOnly: true,
					dateFormat:"<bean:message key="lbl.dateFormat"/>",
					defaultDate:'${userobject.businessdate }'
			})
			});
			$(function() {
					$("#allocdate").datepicker({
							changeMonth: true,
						changeYear: true,
			            yearRange: '1900:+10',
			            showOn: 'both',
							<logic:present name="image">
	    	   		buttonImage: '<%=request.getContextPath()%>/${image }/calendar.gif',
	            </logic:present>
	    		<logic:notPresent name="image">
	    			buttonImage: '<%=request.getContextPath()%>/images/theme1/calendar.gif',
	    		</logic:notPresent>
						buttonImageOnly: true,
						dateFormat:"<bean:message key="lbl.dateFormat"/>",
						defaultDate:'${userobject.businessdate }'
			})
			});
		
function rowCal(abc){
	
	var str="";
	if(abc!=null){
		str=abc;
	}else{	
	 	str= document.getElementById("psize").value;
		str = str - 1;
	}	 
	var recievedDate = 'allocdate'+str;

		$(function() {
			var contextPath =document.getElementById('contextPath').value;
			$("#allocdate"+str).datepicker({
			format: "%Y-%m-%d %H:%i:%s %E %#",
			formatUtcOffset: "%: (%@)",
			placement: "inline",
 			changeMonth: true,
 			changeYear: true,
 			yearRange: '1900:+10',
 			showOn: 'both',
 			buttonImage: contextPath+'/images/theme1/calendar.gif',
			buttonImageOnly: true,
	        dateFormat: document.getElementById("formatD").value,
			defaultDate: document.getElementById("bDate").value
			});
		});
	}
</script>

<body onload="enableAnchor();">
<html:form action="/StationaryAdditionMasterDispatchActionAtCM" styleId="StationaryMasterForm" >
<html:hidden property="contextPath" styleId="contextPath" value="<%=request.getContextPath()%>"  />
<input type="hidden" id="bDate" value='${userobject.businessdate}'/>
<input type="hidden" name="formatD" id="formatD" value="<bean:message key="lbl.dateFormat"/>" />


			<logic:present name="image">
    		   		<input type="hidden" id="CalImage" value='<%=request.getContextPath() %>/${image}/calendar.gif' />
            </logic:present>
    		<logic:notPresent name="image">
    			<input type="hidden" id="CalImage" value='<%=request.getContextPath() %>/images/theme1/calendar.gif'/>
    		</logic:notPresent>
    
    
	<fieldset> <legend><bean:message key="lbl.StatCre" /></legend>
			<table width="100%" border="0" cellspacing="2" cellpadding="1">
			<logic:present name="dataList">
			
			<input type="hidden" id="bookNoU" name="bookNoU" value="U"/>
					<tr><td><bean:message key="lbl.InsAdd" /></td>
					<td><html:text property="additionDate" styleClass="text" styleId="additionDate12" maxlength="10" readonly="true" value="${dataList[0].additionDate }"/></td>
				</tr>
					
				<tr>
					<td><bean:message key="lbl.InsType" /></td>
					<td colspan="2">
					<logic:present name="dataList">
						   <logic:iterate name="dataList" id="subDataList">
						  <logic:notPresent name="viewList">
						     <logic:equal name="subDataList" property="checkTypeValue" value="C">
						       <input type="radio" id="typeC" name="checkType" value="C" onclick="changeAdvance();" checked="checked"/>Cheque Book
						       <input type="radio" id="typeR" name="checkType" value="R" onclick="changeAdvance();"/>Receipt Book
						     </logic:equal>
						     <logic:equal name="subDataList" property="checkTypeValue" value="R">
						       <input type="radio" id="typeC" name="checkType" value="C" onclick="changeAdvance();" />Cheque Book
						       <input type="radio" id="typeR" name="checkType" value="R" onclick="changeAdvance();" checked="checked"/>Receipt Book
						     </logic:equal>
						   </logic:notPresent>
							 <logic:present name="viewList">
						       <logic:iterate name="viewList" id="subDataList">
						 
							     <logic:equal name="subDataList" property="checkTypeValue" value="C">
							       <input type="radio" id="typeC" name="checkType" value="C" onclick="changeAdvance();" checked="checked"/>Cheque Book
							       <input type="radio" id="typeR" name="checkType" value="R" disabled="disabled" onclick="changeAdvance();"/>Receipt Book
							     </logic:equal>
						   		  <logic:equal name="subDataList" property="checkTypeValue" value="R">
							       <input type="radio" id="typeC" name="checkType" value="C" onclick="changeAdvance();" disabled="disabled" />Cheque Book
							       <input type="radio" id="typeR" name="checkType" value="R" onclick="changeAdvance();"  checked="checked"/>Receipt Book
						         </logic:equal>	
						      </logic:iterate>	
					        </logic:present>	
						  </logic:iterate>	
					  </logic:present>	
					 			
					</td><td></td>
					<td></td>
				</tr>		
				<tr>  <td></td>
					<td></td>
				</tr>
				<logic:present name="dataList">
				
				      <logic:notPresent name="viewList">
				        <logic:iterate name="dataList" id="subDataList">
				      		<logic:equal name="subDataList" property="checkTypeValue" value="C">
						  	 <tr>
						  
						  	   	  <td><div id="bankShow"><bean:message key="lbl.Bank" /><span><font color="red">*</font></span></div></td>
							      <td>
								      <div id="bankShow1">
								        <html:select property="bankName" styleId="bankList" value="${subDataList.lbxBranchId}" >	
											   <html:option value="">--Select--</html:option>
												<logic:present name="bankList">
													<html:optionsCollection name="bankList" label="bankName" value="bankId"/>
												</logic:present>
										</html:select>
								     </div>
							     </td>
							   
							 </tr>
						   </logic:equal>
					   </logic:iterate>
					   
				      </logic:notPresent>
				      
					<logic:present name="viewList">
					  <logic:iterate name="viewList" id="subDataList">
					      <logic:equal name="subDataList" property="checkTypeValue" value="C">
						  	 <tr>
						  
						  	   	  <td><div id="bankShow"><bean:message key="lbl.Bank" /><span><font color="red">*</font></span></div></td>
							      <td>
								      <div id="bankShow1">
								        <html:select property="bankName" styleId="bankList" value="${subDataList.lbxBranchId}" disabled="true">	
											   <html:option value="">--Select--</html:option>
												<logic:present name="bankList">
													<html:optionsCollection name="bankList" label="bankName" value="bankId"/>
												</logic:present>
										</html:select>
								     </div>
							     </td>
							   
							 </tr>
						   </logic:equal>
					   </logic:iterate>
					   </logic:present>
				   </logic:present>
				   
				   <logic:present name="dataList">
				      <logic:notPresent name="viewList">
					   <tr>
						<td><bean:message key="lbl.Status"/></td>
			        		<logic:present name="check">
						<td><input type="checkbox" id="status" name="status" checked="checked"/></td>
						</logic:present>
						<logic:notPresent name="check">
						<td><input type="checkbox" id="status" name="status" /></td>
						</logic:notPresent>
						</tr>	
						</logic:notPresent>
						<logic:present name="viewList">
						   <tr>
							<td><bean:message key="lbl.Status"/></td>
				        		<logic:present name="check">
							<td><input type="checkbox" id="status" name="status" disabled="disabled" checked="checked"/></td>
							</logic:present>
							<logic:notPresent name="check">
							<td><input type="checkbox" id="status" disabled="disabled" name="status" /></td>
							</logic:notPresent>
							</tr>	
					   </logic:present>	
				  </logic:present>	
				  
				        </logic:present>	
				    
		 <logic:notPresent name="dataList">
		 <input type="hidden" id="bookNoU" name="bookNoU" value="A"/>
		<tr><td><bean:message key="lbl.InsAdd" /><span><font color="red">*</font></span></td>
					<td><html:text property="additionDate" styleClass="text" styleId="additionDate"  onchange="return checkDate('additionDate');" maxlength="10" value="${userobject.businessdate }"/></td>
				</tr>	
				<tr>
					<td><bean:message key="lbl.InsType" /><span><font color="red">*</font></span></td>
					<td colspan="2">
					    <input type="radio" id="typeC" name="checkType" value="C" onclick="changeAdvance();" checked="checked"/>Cheque Book
						<input type="radio" id="typeR" name="checkType" value="R" onclick="changeAdvance();"/>Receipt Book						
					</td><td></td>
					<td></td>
				</tr>		
				<tr>  <td></td>
					<td></td>
				</tr>
			  	  <tr>	<td><div id="bankShow"><bean:message key="lbl.Bank" /><span><font color="red">*</font></span></div></td>
						<td><div id="bankShow1"><html:select property="bankName" styleId="bankList">	
								<html:option value="">--Select--</html:option>
									<logic:present name="bankList">
										<html:optionsCollection name="bankList" label="bankName" value="bankId"/>
										</logic:present>
								</html:select>
						  </div>
						</td>
				    </tr>		
				    <tr>
					<td><bean:message key="lbl.Status"/></td>
		       
					<td><input type="checkbox" id="status" name="status" checked="checked"/></td>
				
					
					</tr>		    
				    </logic:notPresent>						
		</table>				  
	    </fieldset>
	    <br/>	    
		<fieldset><legend><bean:message key="lbl.StatDet" /></legend>
			<table border="0" cellspacing="0" cellpadding="0" style="width: 100%;">
			
			<tr>
				<td>
					<input type="hidden" name="psize" id="psize" value="6"/>
					<input type="hidden" name="pcheck" id="pcheck" />
					<table id="gridtable" style="width: 100%;" border="1" cellspacing="1" cellpadding="4">
						<logic:notPresent name="dataList">
						<tr class="white2">   
							 <td ><input type="checkbox" name="chkd" id="allchkd" onclick="allChecked();" /></td>
							 <td ><b><bean:message key="lbl.BookNo" /></b></td>							 
							 <td ><b><bean:message key="lbl.InsNo" /></b></td>
							 <td ><b><bean:message key="lbl.InsFrom" /></b></td>
							 <td ><b><bean:message key="lbl.InsTo" /></b></td>
        		 		</tr>
				
						<tr class="white1">
							<td><input type="checkbox" name="chkd" id="chk1" /></td>
							<td><input type="text" name="bookNo" id="bookNo1" class="text"  maxlength="13" value="${subList.bookNo1}" /></td>						   
							<td><input type="text" name="instruNo" id="instruNo1" class="text3" maxlength="10" value="${subList.instruNo1}"  onkeypress="return isNumberKey(event);" onchange="removeInstruFrm();"/></td>
							<td><input type="text" name="instruFrom" id="instruFrom1" class="text3" maxlength="10" value="${subList.instruFrom1}" onkeypress="return isNumberKey(event);" onkeypress="return checkInstruNo(this.id);" onchange="autoCalc(this.id)" /></td>
							<td><input type="text" name="instruTo" id="instruTo1" class="text3" maxlength="10" value="${subList.instruTo1}" readonly/></td>
	          			</tr>	
	          			<tr class="white1">
							<td><input type="checkbox" name="chkd" id="chk2" /></td>
							<td><input type="text" name="bookNo" id="bookNo2" class="text"  maxlength="13" value="${subList.bookNo1}" /></td>						   
							<td><input type="text" name="instruNo" id="instruNo2" class="text3" maxlength="10" value="${subList.instruNo1}"  onkeypress="return isNumberKey(event);" onchange="removeInstruFrm();"/></td>
							<td><input type="text" name="instruFrom" id="instruFrom2" class="text3" maxlength="10" value="${subList.instruFrom1}" onkeypress="return isNumberKey(event);" onkeypress="return checkInstruNo(this.id);" onchange="autoCalc(this.id)" /></td>
							<td><input type="text" name="instruTo" id="instruTo2" class="text3" maxlength="10" value="${subList.instruTo1}" readonly/></td>
	          			</tr>				
	          			<tr class="white1">
							<td><input type="checkbox" name="chkd" id="chk3" /></td>
							<td><input type="text" name="bookNo" id="bookNo3" class="text"  maxlength="13" value="${subList.bookNo1}" /></td>						   
							<td><input type="text" name="instruNo" id="instruNo3" class="text3" maxlength="10" value="${subList.instruNo1}" onkeypress="return isNumberKey(event);" onchange="removeInstruFrm();"/></td>
							<td><input type="text" name="instruFrom" id="instruFrom3" class="text3" maxlength="10" value="${subList.instruFrom1}" onkeypress="return isNumberKey(event);" onkeypress="return checkInstruNo(this.id);" onchange="autoCalc(this.id)" /></td>
							<td><input type="text" name="instruTo" id="instruTo3" class="text3" maxlength="10" value="${subList.instruTo1}" readonly/></td>
	          			</tr>				
	          			<tr class="white1">
							<td><input type="checkbox" name="chkd" id="chk4" /></td>
							<td><input type="text" name="bookNo" id="bookNo4" class="text"  maxlength="13" value="${subList.bookNo1}" /></td>						   
							<td><input type="text" name="instruNo" id="instruNo4" class="text3" maxlength="10" value="${subList.instruNo1}" onkeypress="return isNumberKey(event);" onchange="removeInstruFrm();"/></td>
							<td><input type="text" name="instruFrom" id="instruFrom4" class="text3" maxlength="10" value="${subList.instruFrom1}" onkeypress="return isNumberKey(event);" onkeypress="return checkInstruNo(this.id);" onchange="autoCalc(this.id)" /></td>
							<td><input type="text" name="instruTo" id="instruTo4" class="text3" maxlength="10" value="${subList.instruTo1}" readonly/></td>
	          			</tr>				
	          			<tr class="white1">
							<td><input type="checkbox" name="chkd" id="chk5" /></td>
							<td><input type="text" name="bookNo" id="bookNo5" class="text"  maxlength="13" value="${subList.bookNo1}" /></td>						   
							<td><input type="text" name="instruNo" id="instruNo5" class="text3" maxlength="10" value="${subList.instruNo1}" onkeypress="return isNumberKey(event);" onchange="removeInstruFrm();"/></td>
							<td><input type="text" name="instruFrom" id="instruFrom5" class="text3" maxlength="10" value="${subList.instruFrom1}" onkeypress="return isNumberKey(event);" onkeypress="return checkInstruNo(this.id);" onchange="autoCalc(this.id)" /></td>
							<td><input type="text" name="instruTo" id="instruTo5" class="text3" maxlength="10" value="${subList.instruTo1}" readonly/></td>
	          			</tr>									
					</logic:notPresent>
					<logic:present name="dataList">
					<logic:iterate name="dataList" id="subList" indexId="counter">
					<input type="hidden" id="stationaryId" name="stationaryArrayId" value='${subList.stationaryId}'/>
					<tr class="white2">   							
							 <td ><b><bean:message key="lbl.BookNo" /></b></td>	
							 <td ><b><bean:message key="lbl.InsType" /></b></td>							 
							 <td ><b><bean:message key="lbl.InsNo" /></b></td>
							 <td ><b><bean:message key="lbl.InsFrom" /></b></td>
							 <td ><b><bean:message key="lbl.InsTo" /></b></td>        		 
						</tr>	
					<tr class="white1">
				    	<logic:notPresent name="viewList">
				    	    <input type="hidden" id="bookNoL" name="bookNoL" value="L"/>			    	    	
							<td><input type="text" name="bookNo" id="bookNo${counter+1 }" onkeyup="return upperMe('bookNo');" maxlength="13" class="text" value="${subList.bookNo1}" readonly/></td>								
							<td><input type="text" name="checkType" class="text3" maxlength="10" value="${subList.checkType}"/></td>								
							<td><input type="text" name="instruNo" id="instruNo${counter+1 }" maxlength="10" class="text3" value="${subList.instruNo1}" onchange="removeInstruFrm();" onkeypress="return isNumberKey(event);"/></td>
							<td><input type="text" name="instruFrom" id="instruFrom${counter+1 }" maxlength="10" class="text3" value="${subList.instruFrom1}" onkeypress="return isNumberKey(event);" onkeypress="return checkInstruNo(this.id);" onblur="autoCalc(this.id)" /></td>
							<td><input type="text" name="instruTo" id="instruTo${counter+1 }" maxlength="10" class="text3" value="${subList.instruTo1}" readonly/></td>
							
					   </logic:notPresent>					   
					
					  <logic:present name="viewList">
					  	    	<input type="hidden" id="bookNoL" name="bookNoL" value="X"/>
							<td><input type="text" name="bookNo" id="bookNo" onkeyup="return upperMe('bookNo');" class="text"  value="${subList.bookNo1}" readonly/></td>							
							<td><input type="text" name="checkType" class="text3"  value="${subList.checkType}" readonly/></td>								
							<td><input type="text" name="instruNo" id="instruNo" class="text3" value="${subList.instruNo1}" readonly/></td>
							<td><input type="text" name="instruFrom" id="instruFrom" class="text3"value="${subList.instruFrom1}"  readonly/></td>
							<td><input type="text" name="instruTo" id="instruTo" class="text3" value="${subList.instruTo1}" readonly/></td>
							
					 
					   </logic:present>
					  
					   </tr>
					    </logic:iterate>
						</logic:present>
					</table>
				</td>
			</tr>
			<logic:notPresent name="dataList">
			<tr class="white1">
			<td colspan="5"><button type="button" onclick="addRow();rowCal();" class="topformbutton2" title="Alt+A" accesskey="A"><bean:message key="button.addrow"/></button>&nbsp;&nbsp;&nbsp; 
				<button type="button" value="Delete Row" class="topformbutton2" onclick="removeRow();" title="Alt+D" accesskey="D"><bean:message key="button.deleterow"/></button>
			</td>
		 </tr>
		 </logic:notPresent>
		 <logic:notPresent name="viewList">
         <tr><td>
         
         <button type="button" name="Save" id="Save" class="topformbutton2" title="Alt+V" accesskey="V" onclick="return saveDataDtl();clearUseReceipt();">
				 	<bean:message key="button.save" /></button>	
                  
         </td>
        </tr>
        </logic:notPresent>
	
			</table>
		</fieldset>
</html:form>
	
<logic:present name="saveData">
<script type="text/javascript">

   if('<%=request.getAttribute("saveData").toString()%>'=='saveData')
	{
		alert('Data Saved Succesfully');
		document.getElementById("StationaryMasterForm").action="stationaryAdditionMasterAtCM.do";
	   	document.getElementById("StationaryMasterForm").submit();
	}
</script>
</logic:present>
<br>
<logic:present name="exist">
<script type="text/javascript">

   if('<%=request.getAttribute("exist").toString()%>'=='exist')
	{
		alert('Book No already exists');
		document.getElementById("StationaryMasterForm").action="stationaryAdditionMasterAtCM.do";
	   	document.getElementById("StationaryMasterForm").submit();
	}
</script>
</logic:present>
<logic:present name="existDublicateReceipt">
<script type="text/javascript">

   if('<%=request.getAttribute("existDublicateReceipt")%>'=='existDublicateReceipt')
	{
		alert('Instrument/Receipt No is already existing in range of Instrument From/Receipt From and Instrument To/Receipt To');
		document.getElementById("StationaryMasterForm").action="stationaryAdditionMasterAtCM.do";
	   	document.getElementById("StationaryMasterForm").submit();
		
	}
</script>
</logic:present>

</body>
</HTML>
