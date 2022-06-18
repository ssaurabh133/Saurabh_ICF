

function showBpListReport(){
	document.getElementById("bpListReport").action="payReportAction.do?method=showBpListReport";
	//document.getElementById("processingImage").style.display = '';
	document.getElementById("bpListReport").submit();
}

function  showTransactionSummaryReport(){
	document.getElementById("transacttionReport").action="payReportAction.do?method=showTransacttionSumReport";
	//document.getElementById("processingImage").style.display = '';
	document.getElementById("transacttionReport").submit();
}

function  showPaymentSummaryReport(){
	document.getElementById("transacttionReport").action="payReportAction.do?method=showPaymentSumReport";
	//document.getElementById("processingImage").style.display = '';
	document.getElementById("transacttionReport").submit();
}