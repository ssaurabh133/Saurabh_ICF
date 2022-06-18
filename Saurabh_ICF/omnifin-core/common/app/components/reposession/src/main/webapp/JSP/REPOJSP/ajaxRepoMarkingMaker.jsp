<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>

<logic:present name="subloanList" >

              
       <logic:iterate name="subloanList" id="subloanList" >
          		${subloanList.branch}
			$:${subloanList.product}
            $:${subloanList.scheme}
            $:${subloanList.loanAmount}
            $:${subloanList.balancePrincipal}
            $:${subloanList.overduePrincipal}
            $:${subloanList.overdueAmount}
            $:${subloanList.dpd}
           
	</logic:iterate> 
    </logic:present>  