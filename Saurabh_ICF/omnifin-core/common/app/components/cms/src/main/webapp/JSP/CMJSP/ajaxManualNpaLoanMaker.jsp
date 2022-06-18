<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>

<logic:present name="list" >

 
       <logic:iterate name="list" id="obj" >

          
            ${obj.scheme}
            $:${obj.product}
            
          
	</logic:iterate> 
    </logic:present>  
    
   