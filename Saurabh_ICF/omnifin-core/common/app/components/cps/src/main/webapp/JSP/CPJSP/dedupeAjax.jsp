<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>

<logic:present name="list" >
	 ${list[0].panNo}
	 $:${list[0].regNo}
     $:${list[0].dlNo}
     $:${list[0].passportNo}
     $:${list[0].voterID}
     $:${list[0].phNo}   
     $:${list[0].groupName}     
</logic:present>
    
     
    