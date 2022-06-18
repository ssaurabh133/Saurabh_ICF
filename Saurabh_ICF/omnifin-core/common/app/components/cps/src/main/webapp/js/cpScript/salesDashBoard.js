function manageQueue(queue){
	var basePath=document.getElementById("contextPath").value;
	var source=document.getElementById("source").value;
	var loginUserId=document.getElementById("loginUserId").value;
	document.getElementById("salesDashBoard").action = basePath+"/salesDashBoardAction.do?method=showDefaultView&loginUserId="+loginUserId+"&source="+source+"&queue="+queue;
	document.getElementById("processingImage").style.display = '';
	document.getElementById("salesDashBoard").submit();
}
function cleanDashboard(){
	document.getElementById('LD_PD_SB').innerHTML='?';
	document.getElementById('PRE_LGN_DL').innerHTML='?';
	document.getElementById('PRE_LGM_QR_DL').innerHTML='?';
	document.getElementById('OPEN_DEAL').innerHTML='?';
	document.getElementById('LD_LGM_MNTH').innerHTML='?';
	document.getElementById('LGD_QRY_DL').innerHTML='?';
	document.getElementById('LD_RJCT_CNCLD').innerHTML='?';
	document.getElementById('DL_IN_CP').innerHTML='?';
	document.getElementById('LD_SNCD').innerHTML='?';
	document.getElementById('DL_SNCD_UN_DSBSD').innerHTML='?';
	document.getElementById('LD_DSBSD').innerHTML='?';
}
function replaceAllOccurrences(str,replace,with_this)
{
	str=str.replace(replace,with_this);
	if (str.indexOf(replace)>= 0){
		str=replaceAllOccurrences(str,replace,with_this);
	}
    return str;
}
function manageSelectedBranch(checked,branchId){
	cleanDashboard();
	var selectedBranchIds=document.getElementById('selectedBranchIds').value;
	if(checked){
		selectedBranchIds=selectedBranchIds+'|'+branchId+'|';
	}else{
		selectedBranchIds=replaceAllOccurrences(selectedBranchIds,'|'+branchId+'|','');
	}
	document.getElementById('selectedBranchIds').value=selectedBranchIds;
	refreshSelectedBranch(selectedBranchIds);
}
function manageSelecteAllBranch(checked){
	cleanDashboard();
	var selectedBranchIds='';
	var branchCBList=document.getElementsByName('branchCB');
	for(i=0;i<branchCBList.length;i++){
		if(checked){
			branchCBList[i].checked=true;
			selectedBranchIds=selectedBranchIds+'|'+branchCBList[i].value+'|';
		}else{
			branchCBList[i].checked=false;
		}
	}
	document.getElementById('selectedBranchIds').value=selectedBranchIds;
	refreshSelectedBranch(selectedBranchIds);
}
function refreshSelectedBranch(selectedBranchIds){
	var source=document.getElementById('source').value;
	var userId=document.getElementById('userId').value;
	var source=document.getElementById('source').value;
	
	document.getElementById("processingImage").style.display = '';
	$.ajax({
		url : 'salesDashBoardAction.do?method=refreshSelectedBranch',
		datatype : 'json',
		async : false,
		type: "POST",
		data:{
			'source':source,
			'userId':userId,
			'selectedBranchIds':selectedBranchIds
		},
		success : function(resp) {
			document.getElementById("processingImage").style.display = 'none';
			var jsonData = $.parseJSON(resp);
			var OPERATION_STATUS=jsonData.OPERATION_STATUS;
			var OPERATION_MESSAGE=jsonData.OPERATION_MESSAGE;
			var USER_HIERARCHY=jsonData.USER_HIERARCHY;
			refreshUserTree(USER_HIERARCHY);
		},
		error : function(jqXHR, textStatus, errorThrown) {
			document.getElementById("processingImage").style.display = 'none';
			alert('Some error occurred.');
		}
	});
}
function salesDashBoardItemDtl(dashBoard)
{
	var showAll="N";
	var path = document.getElementById("contextPath").value;
	var source = document.getElementById("source").value;
	var userId = document.getElementById("userId").value;
	var queue = document.getElementById("queue").value;
	var selectedUserIds = document.getElementById("selectedUserIds").value;
	var selectedBranchIds = document.getElementById("selectedBranchIds").value;
	if(queue=='R' && selectedUserIds=='' && selectedBranchIds==''){
		showAll="Y";
	}
	var url=path+"//salesDashBoardAction.do?method=salesDashBoardItemDtl&dashBoard="+dashBoard+"&source="+source+"&userId="+userId+"&showAll="+showAll;
	popupWin=window.open(url,'Sales Dashboard','height=900,width=1100,top=0,left=0, scrollbars=yes ').focus();
	if(window.focus){
		popupWin.focus();
	}
}
function onButtonClick()
{
		window.close();
}
function callUserHierarchyWebservice(){
	var userId=document.getElementById('userId').value;
	document.getElementById("processingImage").style.display = '';
	var source=document.getElementById('source').value;
	$.ajax({
		url : 'salesDashBoardAction.do?method=callUserHierarchyWebservice',
		datatype : 'json',
		async : false,
		type: "POST",
		data : {
			'source':source,
			'userId' : userId
		},
		success : function(resp) {
			document.getElementById("processingImage").style.display = 'none';
			var jsonData = $.parseJSON(resp);
			var OPERATION_STATUS=jsonData.OPERATION_STATUS;
			var OPERATION_MESSAGE=jsonData.OPERATION_MESSAGE;
			var USER_HIERARCHY=jsonData.USER_HIERARCHY;
			createUserTree(USER_HIERARCHY);
		},
		error : function(jqXHR, textStatus, errorThrown) {
			document.getElementById("processingImage").style.display = 'none';
			alert('Some error occurred.');
		}
	});
}
function refreshUserTree(treeChildren){
	document.getElementById('selectedUserIds').value='';
	$("#tree").dynatree("option", "children", null);
	$("#tree").dynatree("option", "children", treeChildren);
    $("#tree").dynatree("getTree").reload();
	generateSalesDashboard();
}
function createUserTree(treeChildren){
	document.getElementById('selectedUserIds').value='';
	var treeBody={
		checkbox: true,
		selectMode: 3,
		children: treeChildren,
		minExpandLevel: 2,
		onSelect: function(select, node) {
			cleanDashboard();
			var selectedUserIds='';
			var selNodes = node.tree.getSelectedNodes();
			var selKeys = $.map(selNodes, function(node1){
				selectedUserIds=selectedUserIds+'|'+node1.data.key+'|';
			});
			document.getElementById('selectedUserIds').value=selectedUserIds;
			generateSalesDashboard();
		}
	};
	$("#tree").dynatree(treeBody);
	generateSalesDashboard();
}
function generateSalesDashboard(){
	//alert("generateSalesDashboard");
	var selectedUserIds=document.getElementById('selectedUserIds').value;
	var selectedBranchIds=document.getElementById('selectedBranchIds').value;
	var source=document.getElementById('source').value;
	var userId=document.getElementById('userId').value;
	var queue=document.getElementById('queue').value;
	document.getElementById("processingImage").style.display = '';
	$.ajax({
		url : 'salesDashBoardAction.do?method=fetchSalesDashboardData',
		datatype : 'json',
		async : false,
		type: "POST",
		data:{
			'queue':queue,
			'source':source,
			'userId':userId,
			'selectedBranchIds':selectedBranchIds,
			'selectedUserIds':selectedUserIds
		},
		success : function(resp) {
			document.getElementById("processingImage").style.display = 'none';
			var jsonData = $.parseJSON(resp);
			var OPERATION_STATUS=jsonData.OPERATION_STATUS;
			var OPERATION_MESSAGE=jsonData.OPERATION_MESSAGE;
			if(parseFloat(OPERATION_STATUS)==1){
				document.getElementById('LD_PD_SB').innerHTML=jsonData.LD_PD_SB;
				document.getElementById('PRE_LGN_DL').innerHTML=jsonData.PRE_LGN_DL;
				document.getElementById('PRE_LGM_QR_DL').innerHTML=jsonData.PRE_LGM_QR_DL;
				document.getElementById('OPEN_DEAL').innerHTML=jsonData.OPEN_DEAL;
				document.getElementById('LD_LGM_MNTH').innerHTML=jsonData.LD_LGM_MNTH;
				document.getElementById('LGD_QRY_DL').innerHTML=jsonData.LGD_QRY_DL;
				document.getElementById('LD_RJCT_CNCLD').innerHTML=jsonData.LD_RJCT_CNCLD;
				document.getElementById('DL_IN_CP').innerHTML=jsonData.DL_IN_CP;
				document.getElementById('LD_SNCD').innerHTML=jsonData.LD_SNCD;
				document.getElementById('DL_SNCD_UN_DSBSD').innerHTML=jsonData.DL_SNCD_UN_DSBSD;
				document.getElementById('LD_DSBSD').innerHTML=jsonData.LD_DSBSD;
			}else{
				document.getElementById('LD_PD_SB').innerHTML='0';
				document.getElementById('PRE_LGN_DL').innerHTML='0';
				document.getElementById('PRE_LGM_QR_DL').innerHTML='0';
				document.getElementById('OPEN_DEAL').innerHTML='0';
				document.getElementById('LD_LGM_MNTH').innerHTML='0';
				document.getElementById('LGD_QRY_DL').innerHTML='0';
				document.getElementById('LD_RJCT_CNCLD').innerHTML='0';
				document.getElementById('DL_IN_CP').innerHTML='0';
				document.getElementById('LD_SNCD').innerHTML='0';
				document.getElementById('DL_SNCD_UN_DSBSD').innerHTML='0';
				document.getElementById('LD_DSBSD').innerHTML='0';
				alert(OPERATION_MESSAGE);
			}
		},
		error : function(jqXHR, textStatus, errorThrown) {
			document.getElementById("processingImage").style.display = 'none';
			document.getElementById('LD_PD_SB').innerHTML='0';
			document.getElementById('PRE_LGN_DL').innerHTML='0';
			document.getElementById('PRE_LGM_QR_DL').innerHTML='0';
			document.getElementById('OPEN_DEAL').innerHTML='0';
			document.getElementById('LD_LGM_MNTH').innerHTML='0';
			document.getElementById('LGD_QRY_DL').innerHTML='0';
			document.getElementById('LD_RJCT_CNCLD').innerHTML='0';
			document.getElementById('DL_IN_CP').innerHTML='0';
			document.getElementById('LD_SNCD').innerHTML='0';
			document.getElementById('DL_SNCD_UN_DSBSD').innerHTML='0';
			document.getElementById('LD_DSBSD').innerHTML='0';
			alert('Some error occurred.');
		}
	});
}
function openDashboardQueryDtl(dealId)
{
	var path = document.getElementById("contextPath").value;
	var url=path+"//salesDashBoardAction.do?method=openDashboardQueryDtl&dealId="+dealId;
	popupWin=window.open(url,'Sales_Dashboard_Query_Dtl','height=400,width=1000,top=200,left=400, scrollbars=yes ').focus();
	if(window.focus){
		popupWin.focus();
	}
}