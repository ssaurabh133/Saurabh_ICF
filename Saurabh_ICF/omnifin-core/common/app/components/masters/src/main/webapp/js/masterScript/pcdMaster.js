function addRow(val)
	{
	  var approvalpmstSize=document.getElementById('approvalpmstSize');
	  var apppmstSize='';
	  var ptable = document.getElementById('gridtable');
	  var lastElement = ptable.rows.length;	  
	  var index='';
	  if(val=="Save" || val=="Edit")
	  {
		var count=document.getElementById('rowCounter');
		if(count!=null || count!=undefined)
		{
			if(count.value=="")
			{
				document.getElementById('rowCounter').value=0;
				index=0;
			}
			else
			{
				document.getElementById('rowCounter').value=parseInt(count.value,10)+1;
				index=count.value;
			}
		}
	}
	  document.getElementById("psize").value=1;
      var pcheck = document.getElementById("pcheck").value; 
      if(approvalpmstSize.value!=''){
      apppmstSize=approvalpmstSize.value;
      }

	 var row = ptable.insertRow(lastElement);
	 row.setAttribute('className','white1' );
     row.setAttribute('class','white1' );

      var cellCheck = row.insertCell(0);
  	  var element = document.createElement('input');
  	  element.type = 'checkbox';
  	  element.name = 'chk';
  	  element.id = 'chk'+index;	 
	  if(val=="Edit"){
	  var ele=document.createElement('input');
	   ele.type = 'hidden';
	   ele.name = 'pcdCheckId';
	   ele.id = 'pcdCheckId'+index;
	  }

      var ruleCodeSelect = row.insertCell(1);
      var newdiv = document.createElement('span');
      var basePath='<%= request.getContextPath()%>';
      var ruleCode = document.createElement('input');
	  ruleCode.type = 'text';
	  ruleCode.setAttribute('readonly','readonly');
	  ruleCode.name = 'ruleCode';
	  ruleCode.id = 'ruleCode'+index;
	  ruleCode.setAttribute('className','text');
	  ruleCode.setAttribute('class','text');

	  	newdiv.innerHTML ='<input type="button" value="" name="loanAccountButton" id="loanAccountButton"  onclick="openLOVCommon(250,\'policyChecklistDef\',\'ruleCode'+index+'\',\'\',\'\',\'\',\'\',\'\',\'ruleDesc'+index+'\')" Class="lovbutton" />';
		ruleCodeSelect.appendChild(ruleCode);
	 	ruleCodeSelect.appendChild(newdiv);

      var ruleDescSelect = row.insertCell(2);
	  var ruleDesc = document.createElement('input');
	  ruleDesc.type = 'text';
	  ruleDesc.readOnly=true;
	  ruleDesc.name = 'ruleDesc';
	  ruleDesc.id = 'ruleDesc'+index;
	  ruleDesc.setAttribute('className','text');
	  ruleDesc.setAttribute('class','text');
	  var lbxPCD = document.createElement('input');
	  lbxPCD.type = 'hidden';
	  lbxPCD.name = 'lbxPCD';
	  lbxPCD.id = 'lbxPCD';
	  ruleDescSelect.appendChild(lbxPCD);

      var actionSelect = row.insertCell(3);
	  var action = document.createElement('select');
	  action.type = 'option';
	  //action.options[0] = new Option('---Select---', '');
	  action.options[0] = new Option('STOP', 'S');
      action.options[1] = new Option('DEVIATION', 'D');
	  action.name = 'action';
	  action.id = 'action'+index;

	  var appLevelSlect = row.insertCell(4);
	  var appLevel = document.createElement('select');
	  appLevel.type = 'option';
	  //appLevel.options[0] = new Option('---Select---', '');
	  for (var i=0;i<=apppmstSize;i++){
		  appLevel.options[i] = new Option(i, i);
	  }
//	  appLevel.options[0] = new Option('1', '1');
//      appLevel.options[1] = new Option('2', '2');
//      appLevel.options[2] = new Option('3', '3');
	  appLevel.name = 'appLevel';
	  appLevel.id = 'appLevel'+index;


	cellCheck.appendChild(element);	
	if(val=="Edit"){	
	cellCheck.appendChild(ele);	
	}
	ruleDescSelect.appendChild(ruleDesc);
	actionSelect.appendChild(action);	
	appLevelSlect.appendChild(appLevel);



	  index++;
	  pcheck++;
	  document.getElementById("psize").value=index;
	  document.getElementById("pcheck").value=pcheck;

	  }

function allChecked()
{
	// alert("ok");
	var c = document.getElementById("allchkd").checked;
	var ch=document.getElementsByName('chk');
 	var zx=0;
	// alert(c);
	if(c==true)
	{
		for(i=0;i<ch.length;i++)
		{
			ch[zx].checked=true;
			zx++;
		}
	}
	else
	{
		for(i=0;i<ch.length;i++)
		{
			ch[zx].checked=false;
			zx++;
		}
	}
	
}

function removeRow(val) {
      var table = document.getElementById("gridtable");
         var rowCount = table.rows.length;
         //alert("Row Count"+rowCount)
		var count=0;
         for(var i=1; i<rowCount; i++) {
//        	 alert("i: "+i);
//        	 alert("Row Count"+rowCount);
             var row = table.rows[i];
             var chkbox = row.cells[0].childNodes[0];
//             alert("chkbox.checked: "+chkbox.checked);
             if(null != chkbox && true == chkbox.checked) 
             {
             	 table.deleteRow(i);
                 //rowCount--;
                 count++;
                 i--;
              }        

            }        

      if(count==0){
           alert(val);
         return false;
         }

      }

function removeSearchRow(val) {
      var table = document.getElementById("gridtable");
        var rowCount = table.rows.length;
		var count=rowCount;

         for(var i=1; i<rowCount; i++) {
             var row = table.rows[i];
             var chkbox = row.cells[0].childNodes[0];
             if(null != chkbox && true == chkbox.checked) 
             {
             	 table.deleteRow(i);
                 rowCount--;
                 i--;
              return true;
              }        

            }        

      if(count>1){
           alert(val);
         return false;
         }

      }


function pcdSave(val,id){
	DisButClass.prototype.DisButMethod();
	var sourcepath=document.getElementById("path").value;

	var flag=0;
	var ptable = document.getElementById('gridtable');
	var lastElement = ptable.rows.length;
	
	var ruleCodeList="";
	var ruleDescList="";
	var actionList="";
	var appLevelList ="";
	var pcdCheckIdList="";

	if((document.getElementById("product").value == "" || document.getElementById("scheme").value == "" || document.getElementById("stage").value == "" )){
		var a = "";
		if(document.getElementById("product").value == "")
			var a= "* Product is required \n";
		if(document.getElementById("scheme").value == "")
			a += "* Scheme is required \n";
		if(document.getElementById("stage").value == "")
			a +="* Stage is required \n";
		alert(a);
		DisButClass.prototype.EnbButMethod();
		return false;
	}else {
		var rowCounter = document.getElementById('rowCounter');
		if(rowCounter!=null || rowCounter!=undefined)
		{
			if(rowCounter.value=="")
			{
				rowCounter.value=0;
			}

		}	
		for(i=1;i<=rowCounter.value;i++)
		{
			if(document.getElementById("chk"+i)==null || document.getElementById("chk"+i)==undefined)
			{
				continue;
			}
			else
			{
			if(document.getElementById("chk"+i).checked==true){
				
				if(document.getElementById("ruleCode"+i).value==""){
//					alert(document.getElementById("ruleCode"+i).value);
					flag=0;
					alert('Please select Rule Code & Description.');
					DisButClass.prototype.EnbButMethod();
					return false;
				}
				//Ritu Code Start
				if(document.getElementById("action"+i).value=="D"){
//					alert(document.getElementById("ruleCode"+i).value);
					{
						if(document.getElementById("appLevel"+i).value=="0"){
						alert('Please select Approval Level as 1, 2 or 3.');
						DisButClass.prototype.EnbButMethod();
						return false;
						}
					}
				}
				//Ritu Code End
				ruleCodeList=ruleCodeList +document.getElementById("ruleCode"+i).value + "/";
				
				
				if(id=="Update"){
					if((document.getElementById("pcdCheckId"+i).value) == null || (document.getElementById("pcdCheckId"+i).value) == ""){
						 pcdCheckIdList = pcdCheckIdList + "a" + "/";					
					 }else{
						 pcdCheckIdList=pcdCheckIdList +document.getElementById("pcdCheckId"+i).value + "/";
					 }
				}else if(id=="Save"){
					if((document.getElementById("chk"+i).checked)){
						pcdCheckIdList = pcdCheckIdList + document.getElementById("chk"+i).value + "/";					
					 }else{
						 pcdCheckIdList=pcdCheckIdList;
					 }
				}

				 if((document.getElementById("appLevel"+i).value) == 0){
					 appLevelList=appLevelList + "0" + "/";
				 }else if((document.getElementById("appLevel"+i).value) == 1){
						 appLevelList=appLevelList + "1" + "/";
				 }else if((document.getElementById("appLevel"+i).value) == 2){
					 appLevelList=appLevelList + "2" + "/";
				 }else if((document.getElementById("appLevel"+i).value) == 3){
					 appLevelList=appLevelList + "3" + "/";
				 }else if((document.getElementById("appLevel"+i).value) == 4){
					 appLevelList=appLevelList + "4" + "/";
				 }else if((document.getElementById("appLevel"+i).value) == 5){
					 appLevelList=appLevelList + "5" + "/";
				 }else if((document.getElementById("appLevel"+i).value) == 6){
					 appLevelList=appLevelList + "6" + "/";
				 }else if((document.getElementById("appLevel"+i).value) == 7){
					 appLevelList=appLevelList + "7" + "/";
				 }else if((document.getElementById("appLevel"+i).value) == 8){
					 appLevelList=appLevelList + "8" + "/";
				 }else if((document.getElementById("appLevel"+i).value) == 9){
					 appLevelList=appLevelList + "9" + "/";
				 }
				 
				 else {
					 appLevelList=appLevelList + "" + "/";
				 }


				 if((document.getElementById("action"+i).value)== "S"){
					 actionList = actionList + "S" + "/";
				 }else if((document.getElementById("action"+i).value)== "D"){
					 actionList = actionList + "D" + "/";
				 }else{
					 actionList = actionList + "" + "/";
				 }

//				 if((document.getElementById("ruleDesc"+i).value)){
//					 ruleDescList = ruleDescList + "Y" + "/";
//				 }else{
//					 	alert("...........BYE");
//					 	return false;
//				 }
				 flag=1;		
				}
				}	
			 }
	 if(flag==0)
   	 {
   		 alert(val);
   		 DisButClass.prototype.EnbButMethod();
   		 return false;
   	 }
   	var confrm = confirm("Do you want to continue?");
   	
   	if(confrm){
		
			document.getElementById("policyChecklistAdd").action=sourcepath+"/policyChecklistEdit.do?method=savePolicyChekDetails&pcdCheckIdList="+pcdCheckIdList+"&ruleCodeList="+ruleCodeList+"&appLevelList="+appLevelList+"&actionList="+actionList+"&ruleDescList="+ruleDescList+"&function="+id;
			document.getElementById("processingImage").style.display = '';
			document.getElementById("policyChecklistAdd").submit();
			return true;

   	 }else{
   		 DisButClass.prototype.EnbButMethod();
  		return false;
  	 }
	}
 }


function policyAdd(){
		DisButClass.prototype.DisButMethod();
		var sourcepath=document.getElementById("path").value;
		
			document.getElementById("policyChecklistDef").action=sourcepath+"/policyChecklistMasterAdd.do";
			document.getElementById("processingImage").style.display = '';
			document.getElementById("policyChecklistDef").submit();
			return true;	
			
 }


function fnSearch(){
	
		DisButClass.prototype.DisButMethod();	
		var product = document.getElementById("lbxProductID").value;
		var scheme = document.getElementById("lbxSchemeID").value;
		var stage = document.getElementById("stage").value;

		if(product == "" && scheme == "" && stage == ""){
			var a = "*  Please select at least one field.";
			alert(a);
			DisButClass.prototype.EnbButMethod();
			return false;
		}else{
				var sourcepath=document.getElementById("path").value;
				document.getElementById("policyChecklistDef").action=sourcepath+"/policyChecklistMasterSearchExisting.do?method=searchPolicyChekDetails";
				document.getElementById("processingImage").style.display = '';
				document.getElementById("policyChecklistDef").submit();
				return true;		
	}
}
//Ritu Code Start
function levelDis(){
	
	var ptable = document.getElementById('gridtable');
	for(i=1;i<=ptable.rows.length-1;i++){
	var action=document.getElementById("action"+i).value;
	
	if(action=='S')
	{
		document.getElementById("appLevel"+i).value='0';
		document.getElementById("appLevel"+i).disabled='true';
		
		
	}else{
		document.getElementById("appLevel"+i).disabled='false';
		document.getElementById("appLevel"+i).removeAttribute("disabled","true");
	}
	}
} 
//Ritu Code End