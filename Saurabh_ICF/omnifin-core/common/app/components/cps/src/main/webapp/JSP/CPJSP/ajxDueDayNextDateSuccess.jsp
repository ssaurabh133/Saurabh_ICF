<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>

<logic:present name="dueDayList" >

   
	      <logic:iterate name="dueDayList" id="subdueDayList">
	
				${subdueDayList.cycleDate}
				$:${subdueDayList.nextDueDate}
			
		</logic:iterate>

	
 </logic:present>     
         