
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<logic:present name="productList">
<select name="product" id="product1" class="sel_width" onchange="return getSheme('s1');">
<option value="">---Select---</option>
<logic:iterate name="productList" id="subproductList">
<option value="${subproductList.id}">${subproductList.name}</option>
</logic:iterate>
</select>
$:
<select name="product" id="product2" class="sel_width" onchange="return getSheme('s2');">
<option value="">---Select---</option>
<logic:iterate name="productList" id="subproductList">
<option value="${subproductList.id}">${subproductList.name}</option>
</logic:iterate>
</select>
$:
<select name="product" id="product3" class="sel_width" onchange="return getSheme('s3');">
<option value="">---Select---</option>
<logic:iterate name="productList" id="subproductList">
<option value="${subproductList.id}">${subproductList.name}</option>
</logic:iterate>
</select>
</logic:present>