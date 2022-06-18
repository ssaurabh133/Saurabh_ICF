
function saveAllocation()
{	
	DisButClass.prototype.DisButMethod();
var total = 0;
var total1 = 0;
var allsum=0;
	var sourcepath=document.getElementById("contextPath").value;
	var addition=0;
	var i=0;
	var newuserval='';
	var branchs='';
	var newcheckval='';
	var table = document.getElementById("gridTable");
	var rowCount = table.rows.length;		
	var count=rowCount;
	var psize=document.getElementById("psize").value;
	if(psize==""){
		psize=rowCount;
	}

	for(i=1;i < psize;i++){
		//alert(i);
//		if(chkCases[i].checked == true){
//
//			total =total+ 1;
//		
//		}
 if((document.getElementById('chk'+i))!=undefined||(document.getElementById('chk'+i))!=null){
				
	 if((document.getElementById('allocpercentage'+i)).value==""||(document.getElementById('allocpercentage'+i)).value==null){
		 document.getElementById('allocpercentage'+i).value="0.00";
	 }
		if(((document.getElementById('allocpercentage'+i)).value != "")||((document.getElementById('allocpercentage'+i)).value != null)){
			allsum =allsum+ 1;
		
			if(newcheckval==''){
			newcheckval=(document.getElementById('allocpercentage'+i)).value ;		
			}
			else{
				newcheckval=newcheckval+"/" + (document.getElementById('allocpercentage'+i)).value ;	
			}
			
			addition=(parseFloat((document.getElementById('allocpercentage'+i)).value))+addition;
			
		
		}
		if((document.getElementById('lbxUserSearchIdd'+i) != "")||(document.getElementById('lbxUserSearchIdd'+i) != null)){
		if(((document.getElementById('lbxUserSearchIdd'+i)).value != "")||(document.getElementById('lbxUserSearchIdd'+i) != null)){
					total1 =total1+ 1;
					if(newuserval!=''){
						newuserval=newuserval+"/" + (document.getElementById('lbxUserSearchIdd'+i)).value ;	
					}else{
						newuserval=(document.getElementById('lbxUserSearchIdd'+i)).value ;		
					}
				
		}}
	if(total1>0){
	if(((document.getElementById('lbxUserSearchIdd'+i)).value !='' )||((document.getElementById('lbxUserSearchIdd'+i)).value != null )){	
		for(j=1;j<i;j++){
			if((document.getElementById('lbxUserSearchIdd'+i)!=undefined ||document.getElementById('lbxUserSearchIdd'+i)!=null)
				&& (document.getElementById('lbxUserSearchIdd'+j)!=undefined ||document.getElementById('lbxUserSearchIdd'+j)!=null)){
				if((document.getElementById('lbxUserSearchIdd'+i)).value==(document.getElementById('lbxUserSearchIdd'+j)).value){
					alert("No two users can be same");
//					document.getElementById("save").removeAttribute("disabled","true");
						     	DisButClass.prototype.EnbButMethod();
					return false;
					}	
			}
			
		}
	 }
	}
	if((total1>allsum)){
		
		alert("It is necessary to enter allocation percentage for the selected queue");
		document.getElementById('allocpercentage'+i).focus();
//		document.getElementById("save").removeAttribute("disabled","true");
		  	DisButClass.prototype.EnbButMethod();
		return false;
	}
	if(allsum>total1){
		alert("It is necessary to select a user for allocation percentage");
//		document.getElementById("save").removeAttribute("disabled","true");
		  	DisButClass.prototype.EnbButMethod();
		return false;
	}
	if((total1==0) && (allsum>0)){
		alert("It is necessary to select a user for allocation percentage");
//		document.getElementById("save").removeAttribute("disabled","true");
		  	DisButClass.prototype.EnbButMethod();
		return false;
	}
	if((total1>0) && (allsum==0)){
		
		alert("It is necessary to enter allocation percentage for the selected queue");
//		document.getElementById("save").removeAttribute("disabled","true");
		  	DisButClass.prototype.EnbButMethod();
		return false;
	}
	
	if(addition  >100.00){
		alert("Percentage cannot be more than 100");
		document.getElementById('allocpercentage'+i).focus();
//		document.getElementById("save").removeAttribute("disabled","true");
		  	DisButClass.prototype.EnbButMethod();
			return false;
	}
	
	
	 if(((document.getElementById('lbxUserSearchIdd'+i)).value=='' )||((document.getElementById('lbxUserSearchIdd'+i)).value==null )
			&& ((document.getElementById('allocpercentage'+i)).value=='')	 ||((document.getElementById('allocpercentage'+i)).value==null)){
			alert("Please fill all the values in the grid");
//			document.getElementById("save").removeAttribute("disabled","true");
			  	DisButClass.prototype.EnbButMethod();
			return false;
		}	
	}	
}
	if((document.getElementById('lbxUserSearchIdd'+i) != "")||(document.getElementById('lbxUserSearchIdd'+i) != null)){
	if(branchs!=''){
		branchs=branchs+"/" + (document.getElementById('lbxBranchIds')).value ;	
	}else{
		branchs=(document.getElementById('lbxBranchIds')).value ;		
	}
		}
	if((document.getElementById("lbxQueuesearchId").value == "")||(document.getElementById("lbxQueuesearchId").value == null)){
		alert("Please select queue");
//		document.getElementById("save").removeAttribute("disabled","true");
		  	DisButClass.prototype.EnbButMethod();
		return false;
	}

//	for(i=1;i < count;i++){
//	for(j=1;j<i;j++){
//		
//		if((document.getElementById('lbxUserSearchIdd'+i)).value==(document.getElementById('lbxUserSearchIdd'+j)).value){
//		alert("No two users can be same");
//		document.getElementById("save").removeAttribute("disabled","true");
//		return false;
//		}
//	}
//	}
//	if(total==0){
//		alert ("Please select at least one checkbox")
//		document.getElementById("save").removeAttribute("disabled","true");
//		return false;
//	}
//	
	if(total1==0){
		alert ("Please select a user for the queue");
//		document.getElementById("save").removeAttribute("disabled","true");
		  	DisButClass.prototype.EnbButMethod();
		return false;
	}
	//alert("addition:---"+addition);
	if(addition  < 100.00){
		alert("Percentage cannot be less than 100");
		
//		document.getElementById("save").removeAttribute("disabled","true");
		  	DisButClass.prototype.EnbButMethod();
			return false;
	}
//	 if(total<user.length){
//			alert ("Please select all the checkboxes")
//			document.getElementById("save").removeAttribute("disabled","true");
//			return false;
//		}
	if(total1>0 && allsum>0 && (document.getElementById("lbxQueuesearchId").value != "")){
//alert("total="+total1);
//alert("&newcheckval="+newcheckval);
//alert("newuserval="+newuserval);
		document.getElementById("AllocationForm").action=sourcepath+"/allocationmasteradd.do?method=saveQueueAllocation&total="+total1+"&newcheckval="+newcheckval+"&newuserval="+newuserval+"&branchs="+branchs;
		document.getElementById("processingImage").style.display = '';
		document.getElementById("AllocationForm").submit();

	

	}else{
		alert ("Please fill the mandatory fields");
//		document.getElementById("save").removeAttribute("disabled","true");
		  	DisButClass.prototype.EnbButMethod();
		return false;
	}
}
function totalpercent(){
	//alert("totalpercent");
	
	var addition=0;

	var newcheckval='';
	var table = document.getElementById("gridTable");
	var rowCount = table.rows.length;		
	var count=rowCount;
	//alert("in totalpercent rowCount"+rowCount);
	var psize=document.getElementById("psize").value;
	if(psize==""){
		psize=rowCount;
	}
    if(psize>1){
    for(var i=1; i<psize; i++){
	var allocpercent=document.getElementById('allocpercentage'+i);
	if(allocpercent!=undefined||allocpercent!=null){
		
	
    if((document.getElementById('allocpercentage'+i).value != "")||(document.getElementById('allocpercentage'+i).value != null)){
		
		var allocpercentagePer=document.getElementById('allocpercentage'+i).value ;
		if(allocpercentagePer==""){
			allocpercentagePer='0';
		}
		addition=parseFloat(allocpercentagePer)+addition;
		document.getElementById("percent").value=addition;
	}}
	}
}else{
	document.getElementById("percent").value='';
}
	
}


function fnChangeCase(formName,fieldName)
{
	
    document.getElementById(formName).elements[fieldName].value=(document.getElementById(formName).elements[fieldName].value).toUpperCase();
    return (document.getElementById(formName).elements[fieldName].value).toUpperCase();

}
function fnSearch(val){
	   //@SP
	DisButClass.prototype.DisButMethod();
	   var queueIdSearch=document.getElementById("queueIdSearch").value;	   
	    var user=document.getElementById("lbxUserSearchId").value;	
	   
	    if(queueIdSearch=="" && queueIdSearch==null  && user==null  && user=="" ){
	    alert("Please Select At least One Criteria");
//	    document.getElementById("search").removeAttribute("disabled","true");
	    	DisButClass.prototype.EnbButMethod();
	    		return false;
	    }
	    else{
	    var sourcepath=document.getElementById("contextPath").value;	
	    document.getElementById("AllocationForm").action=sourcepath+"/allocationMasterSearch.do?method=openSearchJsp";
	    document.getElementById("processingImage").style.display = '';
	    document.getElementById("AllocationForm").submit();
		return true;
		}
		//document.getElementById("save").removeAttribute("disabled","true");
		//return false;
	}	
function listbox_moveacross(sourceID, destID) {
	  //alert("in the function listbox_moveacross");
     var src = document.getElementById(sourceID);
   //  alert(src);
       var dest = document.getElementById(destID);
    //   alert(dest);
			for(var count=0; count < src.options.length; count++) {

	 	if(src.options[count].selected == true) {
						var option = src.options[count];

						var newOption = document.createElement("option");
						newOption.value = option.value;
						newOption.text = option.text;
						newOption.selected = true;
						try {
								 dest.add(newOption, null); //Standard
								 src.remove(count, null);
						 }catch(error) {
								 dest.add(newOption); // IE only
								 src.remove(count);
						 }
						count--;
}
}
}
function addallocationRow(){
	//alert("addRow");
	var table = document.getElementById("gridTable");
    var basePath=document.getElementById("contextPath").value;
    var newdiv = document.createElement('span');
	var rowCount = table.rows.length;
	var psize= document.getElementById("psize").value;
	if(psize==""){
		psize=rowCount;
	}
	//alert("rowCount-------"+rowCount);
	var row = table.insertRow(rowCount);
	row.setAttribute('className','white1' );
    row.setAttribute('class','white1' );
	var cell1 = row.insertCell(0);
	var element1 = document.createElement("input");
	element1.type = "checkbox";
	element1.name = "chk";
	element1.id = "chk"+psize;
	cell1.appendChild(element1);

	var cell2 = row.insertCell(1);
	var element2 = document.createElement("input");		
	element2.type = "text";
	element2.name = "userIdsearch";
	element2.id = "userIdsearch"+psize;	
	element2.setAttribute('class','text');
	element2.setAttribute('className','text');	
	element2.setAttribute('readOnly','true');
	
	var element33 = document.createElement("input");		
	element33.type = "hidden";
	element33.name = "lbxUserSearchIdd";
	element33.id = "lbxUserSearchIdd"+psize;		 
	var element333 = document.createElement("input");	
	element333.type = "hidden";
	element333.name = "lbxUserSearchId";
	element333.id = "lbxUserSearchId";	
	
	newdiv.innerHTML ='&nbsp; &nbsp;<input type="button" value="" name="userButton" id="userButton" class="lovbutton" onclick="openLOVCommon(20250,\'AllocationMasterSearchForm\',\'userIdsearch'+psize+'\',\'\',\'\',\'\',\'\',\'\',\'lbxUserSearchIdd'+psize+'\',\'userStatus'+psize+'\')" class="lovbutton"/>';
	
	cell2.appendChild(element2);
	cell2.appendChild(element33);
	cell2.appendChild(element333);
	cell2.appendChild(newdiv);
	
	var cell3 = row.insertCell(2);
	
	
	var elementUsr = document.createElement("input");
	elementUsr.type = "text";
	elementUsr.name = "userStatus";
	elementUsr.id = "userStatus"+psize;
	elementUsr.setAttribute('class','text');
	elementUsr.setAttribute('className','text');
	elementUsr.setAttribute('readOnly','true');
	cell3.appendChild(elementUsr);
	
	var cell4 = row.insertCell(3);
	var element3 = document.createElement("input");
	element3.type = "text";
	element3.name = "allocpercentage";
	element3.id = "allocpercentage"+psize;
	element3.onkeypress = function(){
		return numbersonly(event,this.id,3);totalpercent();
	};
	element3.onkeyup = function(){
		checkNumber(this.value, event, 3,this.id);totalpercent();
	};
	element3.onblur = function(){
		formatNumber(this.value,this.id);
	};
	element3.onchange = function(){
		totalpercent();
	};
	element3.setAttribute('class','text');
	element3.setAttribute('className','text' );
	//element3.setAttribute('onkeypress','return numbersonly(event,this.id,3);totalpercent();');
	//element3.setAttribute('onclick','numbersonly(event,id,3);');
	//element3.setAttribute('onblur','formatNumber(this.value,id);');
	//element3.setAttribute('onkeyup','checkNumber(this.value, event, 3,id);totalpercent();');
	//element3.setAttribute('onfocus','keyUpNumber(this.value, event, 3,id);');
	//element3.setAttribute('onchange','totalpercent();');

	cell4.appendChild(element3);
	psize++;
	document.getElementById("psize").value=psize;
}
function numbersonly1(e, san, Max){
	var dynaVal = san;
	document.getElementById(dynaVal).maxLength = Max+3;
	var unicode=e.charCode? e.charCode : e.keyCode
	alert(unicode);
	if ((unicode!=8 && unicode != 46 && e.keyCode != 37 && e.keyCode != 28 && e.keyCode != 9)){ //if the key isn't the backspace key (which we should allow)
	if ((unicode<48||unicode>57)|| unicode ==16)//if not a number
	return false //disable key press
	}
	}
function removeallocationRow() {
	//alert("removeallocationRow");
	 var count=0;
    try {
    var table = document.getElementById("gridTable");
    var rowCount = table.rows.length;
  	var psize=document.getElementById("psize").value;
	if(psize==""){
		psize=rowCount;
	}
	document.getElementById("psize").value=psize;
    //alert("rowCount:--"+rowCount);
    for(var j=1;j<rowCount;j++){
    
       var row1 = table.rows[j];
      //  var chkbox1 = row1.cells[0].childNodes[0];
       var chkbox1 = row1.getElementsByTagName("td")[0].getElementsByTagName("input")[0];
     
        if(chkbox1!=undefined ||chkbox1!=null) {
    	 if(null != chkbox1 && true == chkbox1.checked) {
		 count=count+1;
    	 }
		 }
    }
	
if(count==0){
alert("Please Select at least one row to delete");
}else{
    for(var i=1; i<rowCount; i++) {
      var row = table.rows[i];
  //  var chkbox = row.cells[0].childNodes[0];
      var chkbox = row.getElementsByTagName("td")[0].getElementsByTagName("input")[0];
        if(chkbox!=undefined ||chkbox!=null){
        if(null != chkbox && true == chkbox.checked) {
     /* if(document.getElementById("chk"+i)!=undefined || document.getElementById("chk"+i) !=null){
       if(document.getElementById("chk"+i).checked==true){*/
            table.deleteRow(i);
            rowCount--;
            i--;
        }
        }
   //   }
    }
    }}catch(e) {
        alert(e);
    }
}
//function removeallocationRow(){
//	 var table = document.getElementById("gridTable");
//	    var rowCount = table.rows.length;
//	 var chk =document.getElementsByName("chk"); 
//	 var count=0;
//	 for(var j=1;j<rowCount;j++){
//		 if(document.getElementById("chk"+j).checked==true){
//			 count=count+1;
//			 }
//	 }
//	
//if(count >0 ){
//
//for(var i=1; i<=rowCount; i++) {
//	
// 	var row = table.rows[i];
//
// 	 var chkbox = row.cells[0].childNodes[0];
//     if(null != chkbox && true == chkbox.checked) 
//    	{
//    	 table.deleteRow(i);
//        rowCount--;
//        i--;
//    }           
// }
//}else{
//	alert("Please Select at least one row to delete");
//}
//}

function allChecked()
{
	 //alert("ok");
	var table = document.getElementById("gridTable");
	var rowCount = table.rows.length;

	var c = document.getElementById("allchkd").checked;
	var ch=document.getElementsByName('chk');
 	var zx=0;
 	//alert(rowCount);
	//alert(c);
 	var psize=document.getElementById("psize").value;
	if(psize==""){
		psize=rowCount;
	}
	if(c==true)
	{
		for(i=1;i<psize;i++)
		{
			var ch1=document.getElementById('chk'+i);
			if(ch1!=undefined||ch1!=null){
			document.getElementById('chk'+i).checked=true;
			}
			
		}
	}
	else
	{
		for(i=1;i<psize;i++)
		{
			var ch2=document.getElementById('chk'+i);
			if(ch2!=undefined||ch2!=null){
			document.getElementById('chk'+i).checked=false;
		}
		}
	}
	
}
function newAdd()
{
	DisButClass.prototype.DisButMethod();
	var sourcepath=document.getElementById("contextPath").value;
	document.getElementById("AllocationForm").action=sourcepath+"/allocationmasteradd.do?method=openAddAllocation";
	document.getElementById("processingImage").style.display = '';
	document.getElementById("AllocationForm").submit();
	return true;
}

function allocationDetailsHideShow()
{
	var allocationType = document.getElementById("allocationType").value;
	if(allocationType=='OTHER')
	{
		document.getElementById("showHideAllocationDetail").style.display="";
		document.getElementById("showHideSaveButton").style.display="none";
		document.getElementById("percent").value="";
		//document.getElementById("lbxUserSearchIdd1").value="";
	}
	else
	{
		document.getElementById("showHideAllocationDetail").style.display="none";
		document.getElementById("showHideSaveButton").style.display="";
		document.getElementById("percent").value="100";
	}
}

function saveAllocationRM()
{	
	DisButClass.prototype.DisButMethod();
	var sourcepath=document.getElementById("contextPath").value;
	var queue=document.getElementById("queueIdSearch").value;
	//var branch=document.getElementById("branchId").value;
	var allocationType = document.getElementById("allocationType").value;
	var total=1;
	var newcheckval=100;
	var newuserval=allocationType;
//Rohit Changes Strats for Branch wise allocation of RM
	var branchs='';
	branchs=(document.getElementById('lbxBranchIds')).value ;
	if(queue != '')
	{
		document.getElementById("AllocationForm").action=sourcepath+"/allocationmasteradd.do?method=saveQueueAllocation&total="+total+"&newcheckval="+newcheckval+"&newuserval="+newuserval+"&branchs="+branchs;
		document.getElementById("processingImage").style.display = '';
		document.getElementById("AllocationForm").submit();
	}
	else
	{
		alert ("Please fill the mandatory fields");
	  	DisButClass.prototype.EnbButMethod();
		return false;
	}
}
function modifyAllocation(){
	DisButClass.prototype.DisButMethod();
	var alloctionGroupId=document.getElementById("alloctionGroupId").value;
	var total = 0;
	var total1 = 0;
	var allsum=0;
	var sourcepath=document.getElementById("contextPath").value;
	var addition=0;
	var i=0;
	var newuserval='';
	var branchs='';
	var newcheckval='';
	var table = document.getElementById("gridTable");
	var rowCount = table.rows.length;		
	var count=rowCount;
	var psize=document.getElementById("psize").value;
	if(psize=="" || psize>rowCount ){
		psize=rowCount;
	}
	var recStatus='X';
	if(document.getElementById('rec_status').checked){
		recStatus='A';
	}
	if(recStatus=='A'){
		for(i=1;i < psize;i++){
			var userStatus=document.getElementById('userStatus'+i).value;
			if(userStatus=='Inactive'){
				alert('In-Active user can not be allowed.');
				DisButClass.prototype.EnbButMethod();
				return false;
			}
				
			if((document.getElementById('chk'+i))!=undefined||(document.getElementById('chk'+i))!=null){
				if((document.getElementById('allocpercentage'+i)).value==""||(document.getElementById('allocpercentage'+i)).value==null){
					document.getElementById('allocpercentage'+i).value="0.00";
				}
				if(((document.getElementById('allocpercentage'+i)).value != "")||((document.getElementById('allocpercentage'+i)).value != null)){
					allsum =allsum+ 1;
					if(newcheckval==''){
						newcheckval=(document.getElementById('allocpercentage'+i)).value;
					}
					else{
						newcheckval=newcheckval+"/" + (document.getElementById('allocpercentage'+i)).value ;	
					}
					addition=(parseFloat((document.getElementById('allocpercentage'+i)).value))+addition;
				}
				if((document.getElementById('lbxUserSearchIdd'+i) != "")||(document.getElementById('lbxUserSearchIdd'+i) != null)){
					if(((document.getElementById('lbxUserSearchIdd'+i)).value != "")||(document.getElementById('lbxUserSearchIdd'+i) != null)){
						total1 =total1+ 1;
						if(newuserval!=''){
							newuserval=newuserval+"/" + (document.getElementById('lbxUserSearchIdd'+i)).value ;	
						}else{
							newuserval=(document.getElementById('lbxUserSearchIdd'+i)).value ;		
						}
					}
				}
				if(total1>0){
					if(((document.getElementById('lbxUserSearchIdd'+i)).value !='' )||((document.getElementById('lbxUserSearchIdd'+i)).value != null )){
						for(j=1;j<i;j++){
							if((document.getElementById('lbxUserSearchIdd'+i)!=undefined ||document.getElementById('lbxUserSearchIdd'+i)!=null)
								&& (document.getElementById('lbxUserSearchIdd'+j)!=undefined ||document.getElementById('lbxUserSearchIdd'+j)!=null)){
								if((document.getElementById('lbxUserSearchIdd'+i)).value==(document.getElementById('lbxUserSearchIdd'+j)).value){
									alert("No two users can be same");
									DisButClass.prototype.EnbButMethod();
									return false;
								}	
							}
						}
					}
				}
				if((total1>allsum)){
					alert("It is necessary to enter allocation percentage for the selected queue");
					document.getElementById('allocpercentage'+i).focus();
					DisButClass.prototype.EnbButMethod();
					return false;
				}
				if(allsum>total1){
					alert("It is necessary to select a user for allocation percentage");
					DisButClass.prototype.EnbButMethod();
					return false;
				}
				if((total1==0) && (allsum>0)){
					alert("It is necessary to select a user for allocation percentage");
					DisButClass.prototype.EnbButMethod();
					return false;
				}
				if((total1>0) && (allsum==0)){
					alert("It is necessary to enter allocation percentage for the selected queue");
					DisButClass.prototype.EnbButMethod();
					return false;
				}
			
				if(addition  >100.00){
					alert("Percentage cannot be more than 100");
					document.getElementById('allocpercentage'+i).focus();
					DisButClass.prototype.EnbButMethod();
					return false;
				}
				if(((document.getElementById('lbxUserSearchIdd'+i)).value=='' )||((document.getElementById('lbxUserSearchIdd'+i)).value==null )
						&& ((document.getElementById('allocpercentage'+i)).value=='')	 ||((document.getElementById('allocpercentage'+i)).value==null)){
						alert("Please fill all the values in the grid");
						DisButClass.prototype.EnbButMethod();
						return false;
				}
			}	
		}
		if((document.getElementById('lbxUserSearchIdd'+i) != "")||(document.getElementById('lbxUserSearchIdd'+i) != null)){
			if(branchs!=''){
				branchs=branchs+"/" + (document.getElementById('lbxBranchIds')).value ;	
			}else{
				branchs=(document.getElementById('lbxBranchIds')).value ;		
			}
		}
		if((document.getElementById("lbxQueuesearchId").value == "")||(document.getElementById("lbxQueuesearchId").value == null)){
			alert("Please select queue");
			DisButClass.prototype.EnbButMethod();
			return false;
		}
		if(total1==0){
			alert ("Please select a user for the queue");
			DisButClass.prototype.EnbButMethod();
			return false;
		}
		if(addition  < 100.00){
			alert("Percentage cannot be less than 100");
			DisButClass.prototype.EnbButMethod();
			return false;
		}
		if(total1>0 && allsum>0 && (document.getElementById("lbxQueuesearchId").value != "")){
			document.getElementById("AllocationForm").action=sourcepath+"/allocationmasteradd.do?method=modifyQueueAllocation&recStatus=A&total="+total1+"&newcheckval="+newcheckval+"&newuserval="+newuserval+"&branchs="+branchs+"&alloctionGroupId="+alloctionGroupId;
			document.getElementById("processingImage").style.display = '';
			document.getElementById("AllocationForm").submit();
		}else{
			alert ("Please fill the mandatory fields");
			DisButClass.prototype.EnbButMethod();
			return false;
		}
	}else{
		document.getElementById("AllocationForm").action=sourcepath+"/allocationmasteradd.do?method=modifyQueueAllocation&recStatus=X&alloctionGroupId="+alloctionGroupId;
		document.getElementById("processingImage").style.display = '';
		document.getElementById("AllocationForm").submit();
	}
}
 
 function modifyAllocationRM()
 {	
 	DisButClass.prototype.DisButMethod();
 	var sourcepath=document.getElementById("contextPath").value;
 	var queue=document.getElementById("queueIdSearch").value;
 	//var branch=document.getElementById("branchId").value;
 	var allocationType = document.getElementById("allocationType").value;
 	var total=1;
 	var newcheckval=100;
 	var newuserval=allocationType;
 	//Rohit Changes Strats for Branch wise allocation of RM
	var branchs='';
	branchs=(document.getElementById('lbxBranchIds')).value ;
 	if(queue != '')
 	{
 		document.getElementById("AllocationForm").action=sourcepath+"/allocationmasteradd.do?method=modifyQueueAllocation&total="+total+"&newcheckval="+newcheckval+"&newuserval="+newuserval+"&branchs="+branchs;
 		document.getElementById("processingImage").style.display = '';
 		document.getElementById("AllocationForm").submit();
 	}
 	else
 	{
 		alert ("Please fill the mandatory fields");
 	  	DisButClass.prototype.EnbButMethod();
 		return false;
 	}
 }

function setTotalAllocatedPercentage(){
	var totalAllocPercentage=0;
	var alloctionGroupId=document.getElementById("alloctionGroupId").value;
	var allocpercentage = document.getElementsByName('allocpercentage');
	if(alloctionGroupId!='' && allocpercentage.length>0){
		for(var i = 0; i < allocpercentage.length; i++){
			totalAllocPercentage=totalAllocPercentage + removeComma(allocpercentage[i].value);
		}
		document.getElementById("percent").value=totalAllocPercentage;
	}
 }
