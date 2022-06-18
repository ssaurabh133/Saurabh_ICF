package com.connect;

import com.GL.DAOImplMSSQL.MSSQLAccountPeriodDAOImpl;
import com.GL.DAOImplMSSQL.MSSQLAccountYearImpl;
import com.GL.DAOImplMSSQL.MSSQLBankUploadReconDAOImpl;
import com.GL.DAOImplMSSQL.MSSQLBanktoBankReconcileDAOImpl;
import com.cp.daoImplMYSQL.CreditProcessingDAOImpl;
import com.GL.DAOImplMSSQL.MSSQLBillCapturingDAOImpl;
import com.GL.DAOImplMSSQL.MSSQLBillReceiptDaoImpl;
import com.GL.DAOImplMSSQL.MSSQLBooktoBookReconcileDAOImpl;
import com.GL.DAOImplMSSQL.MSSQLBudgetDefinitionDAOImpl;
import com.GL.DAOImplMSSQL.MSSQLBudgetLedgetDAOImpl;
import com.GL.DAOImplMSSQL.MSSQLBudgetMasterDAOImpl;
import com.GL.DAOImplMSSQL.MSSQLBudgetVoucherMappingDAOImpl;
import com.GL.DAOImplMSSQL.MSSQLExcelSheetUploadDAOImpl;
import com.GL.DAOImplMSSQL.MSSQLFaDepreciationCalcDAOImpl;
import com.GL.DAOImplMSSQL.MSSQLFixedAssetAccountDAOImpl;
import com.GL.DAOImplMSSQL.MSSQLFixedAssetAdditionDAOImpl;
import com.GL.DAOImplMSSQL.MSSQLFixedAssetClassDAOImpl;
import com.GL.DAOImplMSSQL.MSSQLFixedAssetClassificationDAOImpl;
import com.GL.DAOImplMSSQL.MSSQLFixedAssetDepartmentDAOImpl;
import com.GL.DAOImplMSSQL.MSSQLFixedAssetInsuranceDAOImpl;
import com.GL.DAOImplMSSQL.MSSQLFixedAssetLocationImpl;
import com.GL.DAOImplMSSQL.MSSQLFixedAssetRegisterDAOImpl;
import com.GL.DAOImplMSSQL.MSSQLFixedAssetSaleDAOImpl;
import com.GL.DAOImplMSSQL.MSSQLFixedAssetTransferDAOImpl;
import com.GL.DAOImplMSSQL.MSSQLFixedAssetTransferWithInDAOImpl;
import com.GL.DAOImplMSSQL.MSSQLGLBankAutoReconcileDAOImpl;
import com.GL.DAOImplMSSQL.MSSQLGLBankManualReconcileDAOImpl;
import com.GL.DAOImplMSSQL.MSSQLGLBankStatementUploadDAOImpl;
import com.GL.DAOImplMSSQL.MSSQLGLChequeStatusUpdateDAOImpl;
import com.GL.DAOImplMSSQL.MSSQLGLKnockOffDAOImpl;
import com.GL.DAOImplMSSQL.MSSQLGLLedgerDAOImpl;
import com.GL.DAOImplMSSQL.MSSQLGLLedgerMaster1Impl;
import com.GL.DAOImplMSSQL.MSSQLGLSubLedgerDAOImpl;
import com.GL.DAOImplMSSQL.MSSQLGLVoucherDAOImpl;
import com.GL.DAOImplMSSQL.MSSQLGLVoucherEntryDAOImpl;
import com.GL.DAOImplMSSQL.MSSQLGlBankReconcilationDAOImpl;
import com.GL.DAOImplMSSQL.MSSQLGlBillCapturingViewDAOImpl;
import com.GL.DAOImplMSSQL.MSSQLGlBillGenerationDAOImpl;
import com.GL.DAOImplMSSQL.MSSQLGlBillSettlementDAOImpl;
import com.GL.DAOImplMSSQL.MSSQLGlCapitalizationDAOImpl;
import com.GL.DAOImplMSSQL.MSSQLGlChequeDetailDAOImpl;
import com.GL.DAOImplMSSQL.MSSQLGlDepreciationReportDAOImpl;
import com.GL.DAOImplMSSQL.MSSQLGlExpenseHeadDAOImpl;
import com.GL.DAOImplMSSQL.MSSQLGlGroupLedgerDAOImpl;
import com.GL.DAOImplMSSQL.MSSQLGlPartyWiseExpRptDAOImpl;
import com.GL.DAOImplMSSQL.MSSQLGlProfitLossDAOImpl;
import com.GL.DAOImplMSSQL.MSSQLGlProvisionalVoucherReverseDAOImpl;
import com.GL.DAOImplMSSQL.MSSQLGlSaleReportDAOImpl;
import com.GL.DAOImplMSSQL.MSSQLGlTrialBalanceDAOImpl;
import com.GL.DAOImplMSSQL.MSSQLGlVoucherReversalDAOImpl;
import com.GL.DAOImplMSSQL.MSSQLHoExpenseAllocationRatioDAOImpl;
import com.GL.DAOImplMSSQL.MSSQLIncomeTaxBlockMasterDAOImpl;
import com.GL.DAOImplMSSQL.MSSQLPartyCreationDAOImpl;
import com.GL.DAOImplMSSQL.MSSQLReceivPayHeadDAOImpl;
import com.GL.DAOImplMSSQL.MSSQLReconProcessDAOImpl;
import com.GL.DAOImplMSSQL.MSSQLStationaryIssuanceDAOImpl;
import com.GL.DAOImplMSSQL.MSSQLStationaryMasterAdditionDAOImpl;
import com.GL.DAOImplMSSQL.MSSQLTDSRateChartMasterDAOImpl;
import com.GL.DAOImplMSSQL.MSSQLTempletCreationPopImpl;
import com.GL.DAOImplMSSQL.MSSQLTempleteCreationImpl;
import com.GL.DAOImplMSSQL.MSSQLVoucherAuthorizationDaoImpl;
import com.GL.DAOImplMSSQL.MSSQLYearClosingDAOImpl;
import com.GL.DAOImplMYSQL.AccountPeriodDAOImpl;
import com.GL.DAOImplMYSQL.AccountYearImpl;
import com.GL.DAOImplMYSQL.BankUploadReconDAOImpl;
import com.GL.DAOImplMYSQL.BanktoBankReconcileDAOImpl;
import com.GL.DAOImplMYSQL.BillCapturingDAOImpl;
import com.GL.DAOImplMYSQL.BillReceiptDaoImpl;
import com.GL.DAOImplMYSQL.BooktoBookReconcileDAOImpl;
import com.GL.DAOImplMYSQL.BudgetDefinitionDAOImpl;
import com.GL.DAOImplMYSQL.BudgetLedgetDAOImpl;
import com.GL.DAOImplMYSQL.BudgetMasterDAOImpl;
import com.GL.DAOImplMYSQL.BudgetVoucherMappingDAOImpl;
import com.GL.DAOImplMYSQL.ExcelSheetUploadDAOImpl;
import com.GL.DAOImplMYSQL.FaDepreciationCalcDAOImpl;
import com.GL.DAOImplMYSQL.FixedAssetAccountDAOImpl;
import com.GL.DAOImplMYSQL.FixedAssetAdditionDAOImpl;
import com.GL.DAOImplMYSQL.FixedAssetClassDAOImpl;
import com.GL.DAOImplMYSQL.FixedAssetClassificationDAOImpl;
import com.GL.DAOImplMYSQL.FixedAssetDepartmentDAOImpl;
import com.GL.DAOImplMYSQL.FixedAssetInsuranceDAOImpl;
import com.GL.DAOImplMYSQL.FixedAssetLocationImpl;
import com.GL.DAOImplMYSQL.FixedAssetRegisterDAOImpl;
import com.GL.DAOImplMYSQL.FixedAssetSaleDAOImpl;
import com.GL.DAOImplMYSQL.FixedAssetTransferDAOImpl;
import com.GL.DAOImplMYSQL.FixedAssetTransferWithInDAOImpl;
import com.GL.DAOImplMYSQL.GLBankAutoReconcileDAOImpl;
import com.GL.DAOImplMYSQL.GLBankManualReconcileDAOImpl;
import com.GL.DAOImplMYSQL.GLBankStatementUploadDAOImpl;
import com.GL.DAOImplMYSQL.GLChequeStatusUpdateDAOImpl;
import com.GL.DAOImplMYSQL.GLKnockOffDAOImpl;
import com.GL.DAOImplMYSQL.GLLedgerDAOImpl;
import com.GL.DAOImplMYSQL.GLLedgerMaster1Impl;
import com.GL.DAOImplMYSQL.GLSubLedgerDAOImpl;
import com.GL.DAOImplMYSQL.GLVoucherDAOImpl;
import com.GL.DAOImplMYSQL.GLVoucherEntryDAOImpl;
import com.GL.DAOImplMYSQL.GlBankReconcilationDAOImpl;
import com.GL.DAOImplMYSQL.GlBillCapturingViewDAOImpl;
import com.GL.DAOImplMYSQL.GlBillGenerationDAOImpl;
import com.GL.DAOImplMYSQL.GlBillSettlementDAOImpl;
import com.GL.DAOImplMYSQL.GlCapitalizationDAOImpl;
import com.GL.DAOImplMYSQL.GlChequeDetailDAOImpl;
import com.GL.DAOImplMYSQL.GlDepreciationReportDAOImpl;
import com.GL.DAOImplMYSQL.GlExpenseHeadDAOImpl;
import com.GL.DAOImplMYSQL.GlGroupLedgerDAOImpl;
import com.GL.DAOImplMYSQL.GlPartyWiseExpRptDAOImpl;
import com.GL.DAOImplMYSQL.GlProfitLossDAOImpl;
import com.GL.DAOImplMYSQL.GlProvisionalVoucherReverseDAOImpl;
import com.GL.DAOImplMYSQL.GlSaleReportDAOImpl;
import com.GL.DAOImplMYSQL.GlTrialBalanceDAOImpl;
import com.GL.DAOImplMYSQL.GlVoucherReversalDAOImpl;
import com.GL.DAOImplMYSQL.HoExpenseAllocationRatioDAOImpl;
import com.GL.DAOImplMYSQL.IncomeTaxBlockMasterDAOImpl;
import com.GL.DAOImplMYSQL.PartyCreationDAOImpl;
import com.GL.DAOImplMYSQL.ReceivPayHeadDAOImpl;
import com.GL.DAOImplMYSQL.ReconProcessDAOImpl;
import com.GL.DAOImplMYSQL.StationaryIssuanceDAOImpl;
import com.GL.DAOImplMYSQL.StationaryMasterAdditionDAOImpl;
import com.GL.DAOImplMYSQL.TDSRateChartMasterDAOImpl;
import com.GL.DAOImplMYSQL.TempletCreationPopImpl;
import com.GL.DAOImplMYSQL.TempleteCreationImpl;
import com.GL.DAOImplMYSQL.VoucherAuthorizationDaoImpl;
import com.GL.DAOImplMYSQL.YearClosingDAOImpl;
import com.caps.daoImplMSSQL.MSSQLCollReportDAOImpl;
import com.caps.daoImplMSSQL.MSSQLCollUoloadDataDAOImpl;
import com.caps.daoImplMSSQL.MSSQLCollectionDumpDAOImpl;
import com.caps.daoImplMYSQL.CollReportDAOImpl;
import com.caps.daoImplMYSQL.CollUoloadDataDAOImpl;
import com.caps.daoImplMYSQL.CollectionDumpDAOImpl;
import com.cibil.daoImplMSSQL.MSSQLCibilVerificationDAOImpl;
import com.cibil.daoImplMYSQL.CibilVerificationDAOImpl;
import com.cm.daoImplMSSQL.MSSQLAdditionalDisbursalDAOImpl;
import com.cm.daoImplMSSQL.MSSQLBeginOfDayProcessDAOImpl;
import com.cm.daoImplMSSQL.MSSQLCancellationDAOImpl;
import com.cm.daoImplMSSQL.MSSQLCaseMarkingAuthorDAOImpl;
import com.cm.daoImplMSSQL.MSSQLCaseMarkingMakerDAOImpl;
import com.cm.daoImplMSSQL.MSSQLChangeRateDAOImpl;
import com.cm.daoImplMSSQL.MSSQLChequeStatusUpdateDAOImpl;
import com.cm.daoImplMSSQL.MSSQLCreditManagementDAOImpl;
//import com.cm.daoImplMSSQL.MSSQLCreditMgmtExcelSheetUploadDAOImpl;
import com.cm.daoImplMSSQL.MSSQLDeferralDAOImpl;
import com.cm.daoImplMSSQL.MSSQLDeleteInstrumentDAOImpl;
import com.cm.daoImplMSSQL.MSSQLDisbCancellationDAOImpl;
import com.cm.daoImplMSSQL.MSSQLDisbursalInitiationDAOImpl;
import com.cm.daoImplMSSQL.MSSQLDownloadDAOImpl;
import com.cm.daoImplMSSQL.MSSQLDumpDownLoadDAOImpl;
import com.cm.daoImplMSSQL.MSSQLEarlyClosureDAOImpl;
import com.cm.daoImplMSSQL.MSSQLEndOfDayProcessDAOImpl;
import com.cm.daoImplMSSQL.MSSQLFileTrackingDAOImpl;
import com.cm.daoImplMSSQL.MSSQLGenerateBankingDAOImpl;
import com.cm.daoImplMSSQL.MSSQLGenerateBatchDAOImpl;
import com.cm.daoImplMSSQL.MSSQLHandSightingDAOImpl;
import com.cm.daoImplMSSQL.MSSQLHoldInstrumentDAOImpl;
import com.cm.daoImplMSSQL.MSSQLInstrumentCapturingDAOImpl;
import com.cm.daoImplMSSQL.MSSQLInterestWorkingDAOImpl;
import com.cm.daoImplMSSQL.MSSQLKnockOffDAOImpl;
import com.cm.daoImplMSSQL.MSSQLLinkLoanDAOImpl;
import com.cm.daoImplMSSQL.MSSQLLoanDetailUploadDAOImpl;
import com.cm.daoImplMSSQL.MSSQLLoanInitiationDAOImpl;
import com.cm.daoImplMSSQL.MSSQLLoggedInUserReportDAOImpl;
import com.cm.daoImplMSSQL.MSSQLManualAdviceDAOImpl;
import com.cm.daoImplMSSQL.MSSQLManualAdviceUploadDAODAOImpl;
import com.cm.daoImplMSSQL.MSSQLManualIntCalcDAOImpl;
import com.cm.daoImplMSSQL.MSSQLManualnpaMovementDAOImpl;
import com.cm.daoImplMSSQL.MSSQLMaturityClosureDAOImpl;
import com.cm.daoImplMSSQL.MSSQLPartPrePaymentDAOImpl;
import com.cm.daoImplMSSQL.MSSQLPaymentDAOImpl;
import com.cm.daoImplMSSQL.MSSQLPdcViewerDAOImpl;
import com.cm.daoImplMSSQL.MSSQLPoolCreationDAOImpl;
import com.cm.daoImplMSSQL.MSSQLPoolIDDAOImpl;
import com.cm.daoImplMSSQL.MSSQLPoolIdAddEditDAOImpl;
import com.cm.daoImplMSSQL.MSSQLPoolReportDAOImpl;
import com.cm.daoImplMSSQL.MSSQLPresentationProcessDAOImpl;
import com.cm.daoImplMSSQL.MSSQLReceiptDAOImpl;
import com.cm.daoImplMSSQL.MSSQLReleaseInstrumentDAOImpl;
import com.cm.daoImplMSSQL.MSSQLRepayScheduleDAOImpl;
import com.cm.daoImplMSSQL.MSSQLRepoBillingApprovalAuthorDAOImpl;
import com.cm.daoImplMSSQL.MSSQLRepoBillingApprovalMakerDAOImpl;
import com.cm.daoImplMSSQL.MSSQLReportsDAOImpl;
import com.cm.daoImplMSSQL.MSSQLRepricingDAOImpl;
import com.cm.daoImplMSSQL.MSSQLSDLiquidationDAOImpl;
import com.cm.daoImplMSSQL.MSSQLUploadDocumentDAOImpl;
import com.cm.daoImplMSSQL.MSSQLWaiveOffDAOImpl;
import com.cm.daoImplMSSQL.MSSQLassetInsuranceDAOImpl;
import com.cm.daoImplMSSQL.MSSQLassetVerificationDAOImpl;
import com.cm.daoImplMYSQL.AdditionalDisbursalDAOImpl;
import com.cm.daoImplMYSQL.BeginOfDayProcessDAOImpl;
import com.cm.daoImplMYSQL.CancellationDAOImpl;
import com.cm.daoImplMYSQL.CaseMarkingAuthorDAOImpl;
import com.cm.daoImplMYSQL.CaseMarkingMakerDAOImpl;
import com.cm.daoImplMYSQL.ChangeRateDAOImpl;
import com.cm.daoImplMYSQL.ChequeStatusUpdateDAOImpl;

import com.cm.daoImplMYSQL.CreditManagementDAOImpl;
import com.cm.daoImplMYSQL.CreditMgmtExcelSheetUploadDAOImpl;
import com.cm.daoImplMYSQL.DeferralDAOImpl;
import com.cm.daoImplMYSQL.DeleteInstrumentDAOImpl;
import com.cm.daoImplMYSQL.DisbCancellationDAOImpl;
import com.cm.daoImplMYSQL.DisbursalInitiationDAOImpl;
import com.cm.daoImplMYSQL.DownloadDAOImpl;
import com.cm.daoImplMYSQL.DumpDownLoadDAOImpl;
import com.cm.daoImplMYSQL.EarlyClosureDAOImpl;
import com.cm.daoImplMYSQL.EndOfDayProcessDAOImpl;
import com.cm.daoImplMYSQL.ExcelSheetUploadDAOImplCM;
import com.cm.daoImplMYSQL.FileTrackingDAOImpl;
import com.cm.daoImplMYSQL.GenerateBankingDAOImpl;
import com.cm.daoImplMYSQL.GenerateBatchDAOImpl;
import com.cm.daoImplMYSQL.HandSightingDAOImpl;
import com.cm.daoImplMYSQL.HoldInstrumentDAOImpl;
import com.cm.daoImplMYSQL.InstrumentCapturingDAOImpl;
import com.cm.daoImplMYSQL.InterestWorkingDAOImpl;
import com.cm.daoImplMYSQL.KnockOffDAOImpl;
import com.cm.daoImplMYSQL.LinkLoanDAOImpl;
import com.cm.daoImplMYSQL.LoanDetailUploadDAOImpl;
import com.cm.daoImplMYSQL.LoanInitiationDAOImpl;
import com.cm.daoImplMYSQL.LoggedInUserReportDAOImpl;
import com.cm.daoImplMYSQL.LosUploadDAOImpl;
import com.cm.daoImplMYSQL.ManualAdviceDAOImpl;
import com.cm.daoImplMYSQL.ManualAdviceUploadDAOImpl;
import com.cm.daoImplMYSQL.ManualIntCalcDAOImpl;
import com.cm.daoImplMYSQL.ManualnpaMovementDAOImpl;
import com.cm.daoImplMYSQL.MaturityClosureDAOImpl;
import com.cm.daoImplMYSQL.PartPrePaymentDAOImpl;
import com.cm.daoImplMYSQL.PaymentDAOImpl;
import com.cm.daoImplMYSQL.PdcViewerDAOImpl;
import com.cm.daoImplMYSQL.PoolCreationDAOImpl;
import com.cm.daoImplMYSQL.PoolIDDAOImpl;
import com.cm.daoImplMYSQL.PoolIdAddEditDAOImpl;
import com.cm.daoImplMYSQL.PoolReportDAOImpl;
import com.cm.daoImplMYSQL.PresentationProcessDAOImpl;
import com.cm.daoImplMYSQL.ReceiptDAOImpl;
import com.cm.daoImplMYSQL.ReleaseInstrumentDAOImpl;
import com.cm.daoImplMYSQL.RepayScheduleDAOImpl;
import com.cm.daoImplMYSQL.RepoBillingApprovalAuthorDAOImpl;
import com.cm.daoImplMYSQL.RepoBillingApprovalMakerDAOImpl;
import com.cm.daoImplMYSQL.RepricingDAOImpl;
import com.cm.daoImplMYSQL.SDLiquidationDAOImpl;
import com.cm.daoImplMYSQL.UploadDocumentDAOImpl;
import com.cm.daoImplMYSQL.WaiveOffDAOImpl;
import com.cm.daoImplMYSQL.assetInsuranceDAOImpl;
import com.cm.daoImplMYSQL.assetVerificationDAOImpl;
import com.commonFunction.daoImplMSSQL.MSSQLcommonFunctionDaoImpl;
import com.commonFunction.daoImplMYSQL.commonFunctionDaoImpl;
import com.communication.engn.daoImplMSSQL.MSSQLEmailDAOImpl;
import com.communication.engn.daoImplMSSQL.MSSQLSmsDAOImpl;
import com.communication.engn.daoImplMySql.EmailDAOImpl;
import com.communication.engn.daoImplMySql.SmsDAOImpl;
import com.cp.daoImplMSSQL.MSSQLACHCapturingDAOImpl;
//import com.cp.daoImplMSSQL.MSSQLCreditProcessingDAOImpl;
import com.cp.daoImplMSSQL.MSSQLDealClosureDAOImpl;
import com.cp.daoImplMSSQL.MSSQLDealRateApprovalDAOImpl;
import com.cp.daoImplMSSQL.MSSQLDedupeDAOImpl;
import com.cp.daoImplMSSQL.MSSQLDedupeReferalDaoImpl;
import com.cp.daoImplMSSQL.MSSQLDeviationApprovalDAOImpl;
import com.cp.daoImplMSSQL.MSSQLEligibilityCalculationProcessDaoImpl;
import com.cp.daoImplMSSQL.MSSQLFieldVerificationDAOImpl;
import com.cp.daoImplMSSQL.MSSQLFileUploadDaoImpl;
import com.cp.daoImplMSSQL.MSSQLFileUtilityDaoImpl;
import com.cp.daoImplMSSQL.MSSQLFinancialReportDaoImpl;
import com.cp.daoImplMSSQL.MSSQLImdDAOImpl;
import com.cp.daoImplMSSQL.MSSQLIndividualFinancialAnalysisDAOImpl;
import com.cp.daoImplMSSQL.MSSQLLimitEnhancementDAOImpl;
import com.cp.daoImplMSSQL.MSSQLNhbMappingDAOImpl;
import com.cp.daoImplMSSQL.MSSQLNotepadDAOImpl;
import com.cp.daoImplMSSQL.MSSQLRmDAOImpl;
import com.cp.daoImplMSSQL.MSSQLTradeCheckDAOImpl;
import com.cp.daoImplMYSQL.ACHCapturingDAOImpl;
import com.cp.daoImplMYSQL.CovenantProposalTrackingDAOImpl;
import com.cp.daoImplMYSQL.CreditApprovalDAOImpl;
//import com.cp.daoImplMYSQL.CreditProcessingDAOImpl;
import com.cp.daoImplMYSQL.DataAuthenticationDaoImpl;
import com.cp.daoImplMYSQL.DealClosureDAOImpl;
import com.cp.daoImplMYSQL.DealRateApprovalDAOImpl;
import com.cp.daoImplMYSQL.DedupeDAOImpl;
import com.cp.daoImplMYSQL.DedupeReferalDaoImpl;
import com.cp.daoImplMYSQL.DeviationApprovalDAOImpl;
import com.cp.daoImplMYSQL.EligibilityCalculationProcessDaoImpl;
import com.cp.daoImplMYSQL.FieldVerificationDAOImpl;
import com.cp.daoImplMYSQL.FileUploadDaoImpl;
import com.cp.daoImplMYSQL.FileUtilityDaoImpl;
import com.cp.daoImplMYSQL.FinancialReportDaoImpl;
import com.cp.daoImplMYSQL.ImdDAOImpl;
import com.cp.daoImplMYSQL.IndividualFinancialAnalysisDAOImpl;
import com.cp.daoImplMYSQL.LimitEnhancementDAOImpl;
import com.cp.daoImplMYSQL.NhbMappingDAOImpl;
import com.cp.daoImplMYSQL.NotepadDAOImpl;
import com.cp.daoImplMYSQL.OcrDaoImpl;
import com.cp.daoImplMYSQL.RmDAOImpl;
import com.cp.daoImplMYSQL.TradeCheckDAOImpl;
import com.cp.dealDaoImplMSSQL.MSSQLDealProcessingDaoImpl;
import com.cp.dealDaoImplMYSQL.DealProcessingDaoImpl;
import com.cp.financialDaoImplMSSQL.MSSQLFinancialDAOImpl;
import com.cp.financialDaoImplMYSQL.FinancialDAOImpl;
import com.cp.fundFlowDaoImplMSSQL.MSSQLFundFlowAnalysisDAOImpl;
import com.cp.fundFlowDaoImplMYSQL.FundFlowAnalysisDAOImpl;
import com.cp.leadDaoImplMSSQL.MSSQLLeadDaoImp;
import com.cp.leadDaoImplMYSQL.LeadDaoImp;
import com.customerService.daoImplMSSQL.MSSQLCustomerServiceDAOImpl;
import com.customerService.daoImplMYSQL.CustomerServiceDAOImpl;
import com.gcd.daoImplMSSQL.MSSQLCorpotateDAOImpl;
import com.gcd.daoImplMYSQL.CorpotateDAOImpl;
import com.genericUpload.daoImplMSSQL.MSSQLGenericUploadDAOImpl;
import com.genericUpload.daoImplMYSQL.GenericUploadDAOImpl;
import com.legal.daoImplMSSQL.MSSQLLegalMasterDaoImpl;
import com.legal.daoImplMSSQL.MSSQLLegalTransactionDaoImpl;
import com.legal.daoImplMYSQL.LegalMasterDaoImpl;
import com.legal.daoImplMYSQL.LegalTransactionDaoImpl;
import com.login.daoImplMSSQL.MSSQLLoginDaoImpl;
import com.login.daoImplMSSQL.MSSQLUserProfileDaoImpl;
import com.login.daoImplMYSQL.LoginDaoImpl;
import com.login.daoImplMYSQL.UserProfileDaoImpl;
import com.masters.daoImplMSSQL.MSSQLHolidayMasterDAOImpl;
import com.masters.daoImplMSSQL.MSSQLMakeModelMasterDAOImpl;
import com.masters.daoImplMSSQL.MSSQLManualDeviationMasterDAOIMPL;
import com.masters.daoImplMSSQL.MSSQLManufacturerSupplierMappingMasterDAOIMPL;
import com.masters.daoImplMSSQL.MSSQLMasterDAOImpl;
import com.masters.daoImplMSSQL.MSSQLMasterParameterDAOIMPL;
import com.masters.daoImplMSSQL.MSSQLPCDMasterDAOImpl;
import com.masters.daoImplMSSQL.MSSQLReportFunctionAccessDAOImpl;
import com.masters.daoImplMSSQL.MSSQLScoreParamMasterDAOImpl;
import com.masters.daoImplMSSQL.MSSQLScoringMasterDAOImpl;
import com.masters.daoImplMSSQL.MSSQLUserApprovalMatrixDAOIMPL;
import com.masters.daoImplMSSQL.MSSQLUserProductAccessDAOImpl;
import com.masters.daoImplMSSQL.MSSQLruleMasterDAOImpl;
import com.masters.daoImplMYSQL.HolidayMasterDAOImpl;
import com.masters.daoImplMYSQL.MakeModelMasterDAOImpl;
import com.masters.daoImplMYSQL.ManualDeviationMasterDAOIMPL;
import com.masters.daoImplMYSQL.ManufacturerSupplierMappingMasterDAOIMPL;
import com.masters.daoImplMYSQL.MasterDAOImpl;
import com.masters.daoImplMYSQL.MasterParameterDAOIMPL;
import com.masters.daoImplMYSQL.PCDMasterDAOImpl;
import com.masters.daoImplMYSQL.ReportFunctionAccessDAOImpl;
import com.masters.daoImplMYSQL.ScoreParamMasterDAOImpl;
import com.masters.daoImplMYSQL.ScoringMasterDAOImpl;
import com.masters.daoImplMYSQL.UserApprovalMatrixDAOIMPL;
import com.masters.daoImplMYSQL.UserProductAccessDAOImpl;
import com.masters.daoImplMYSQL.VehicleDAOImpl;
import com.masters.daoImplMYSQL.ruleMasterDAOImpl;
import com.payout.daoImplMSSQL.MSSQLPayoutMasterDaoImpl;
import com.payout.daoImplMYSQL.PayoutMasterDaoImpl;
import com.popup.daoImplMSSQL.MSSQLpopupDaoImpl;
import com.popup.daoImplMYSQL.popupDaoImpl;
import com.repo.daoImplMSSQL.MSSQLRepoMasterDaoImpl;
import com.repo.daoImplMSSQL.MSSQLRepoTransactionDaoImpl;
import com.repo.daoImplMYSQL.RepoMasterDaoImpl;
import com.repo.daoImplMYSQL.RepoTransactionDaoImpl;
import java.util.ResourceBundle;
import org.apache.log4j.Logger;
import com.cp.leadDaoImplMYSQL.preDealDaoImp;
import com.masters.daoImplMYSQL.DumpAccessDAOImpl;
import com.cp.daoImplMYSQL.UnderwritterDaoImpl;
import com.cp.daoImplMYSQL.SalesDashBoardDAOImpl;
import com.cp.daoImplMSSQL.MSSQLSalesDashBoardDAOImpl;
import com.cm.daoImplMSSQL.MSSQLMobilePayInSlipDAOImpl;
import com.cm.daoImplMYSQL.MobilePayInSlipDAOImpl;
import com.genericUpload.daoImplMSSQL.MSSQLToDoCaseUploadDAOImpl;
import com.genericUpload.daoImplMYSQL.ToDoCaseUploadDAOImpl;

public class DaoImplInstanceFactory
{
  private static final Logger logger = Logger.getLogger(DaoImplInstanceFactory.class.getName());

  public static Object getDaoImplInstance(String daoType)
  {
    ResourceBundle resource = ResourceBundle.getBundle("com.yourcompany.struts.ApplicationResources");
    String dbType = resource.getString("lbl.dbType");
    Object ob = null;
    if (daoType.equalsIgnoreCase("CP"))
    {
  /*    if (dbType.equalsIgnoreCase("MSSQL"))
      {
        ob = new MSSQLCreditProcessingDAOImpl();
      }
      else*/
      {
     ob = new CreditProcessingDAOImpl();
      }
    }
    if (daoType.equalsIgnoreCase("TRADEDAO"))
    {
      if (dbType.equalsIgnoreCase("MSSQL"))
      {
        ob = new MSSQLTradeCheckDAOImpl();
      }
      else
      {
        ob = new TradeCheckDAOImpl();
      }
    }
    if (daoType.equalsIgnoreCase("DCL"))
    {
      if (dbType.equalsIgnoreCase("MSSQL"))
      {
        ob = new MSSQLDealClosureDAOImpl();
      }
      else
      {
        ob = new DealClosureDAOImpl();
      }
    }
    if (daoType.equalsIgnoreCase("DA"))
    {
      if (dbType.equalsIgnoreCase("MSSQL"))
      {
        ob = new MSSQLDeviationApprovalDAOImpl();
      }
      else
      {
        ob = new DeviationApprovalDAOImpl();
      }
    }
    if (daoType.equalsIgnoreCase("FV"))
    {
      if (dbType.equalsIgnoreCase("MSSQL"))
      {
        ob = new MSSQLFieldVerificationDAOImpl();
      }
      else
      {
        ob = new FieldVerificationDAOImpl();
      }
    }
    if (daoType.equalsIgnoreCase("IFA"))
    {
      if (dbType.equalsIgnoreCase("MSSQL"))
      {
        ob = new MSSQLIndividualFinancialAnalysisDAOImpl();
      }
      else
      {
        ob = new IndividualFinancialAnalysisDAOImpl();
      }
    }
    if (daoType.equalsIgnoreCase("LED"))
    {
      if (dbType.equalsIgnoreCase("MSSQL"))
      {
        ob = new MSSQLLimitEnhancementDAOImpl();
      }
      else
      {
        ob = new LimitEnhancementDAOImpl();
      }
    }
    if (daoType.equalsIgnoreCase("RMD"))
    {
      if (dbType.equalsIgnoreCase("MSSQL"))
      {
        ob = new MSSQLRmDAOImpl();
      }
      else
      {
        ob = new RmDAOImpl();
      }
    }
    if (daoType.equalsIgnoreCase("FID"))
    {
      if (dbType.equalsIgnoreCase("MSSQL"))
      {
        ob = new MSSQLFinancialDAOImpl();
      }
      else
      {
        ob = new FinancialDAOImpl();
      }
    }
    if (daoType.equalsIgnoreCase("LEAD"))
    {
      if (dbType.equalsIgnoreCase("MSSQL"))
      {
        ob = new MSSQLLeadDaoImp();
      }
      else
      {
        ob = new LeadDaoImp();
      }
    }
    if (daoType.equalsIgnoreCase("FFA"))
    {
      if (dbType.equalsIgnoreCase("MSSQL"))
      {
        ob = new MSSQLFundFlowAnalysisDAOImpl();
      }
      else
      {
        ob = new FundFlowAnalysisDAOImpl();
      }
    }
    if (daoType.equalsIgnoreCase("COLLD"))
    {
      if (dbType.equalsIgnoreCase("MSSQL"))
      {
        ob = new com.caps.daoImplMSSQL.MSSQLCollDAOImpl();
      }
      else
      {
        ob = new com.caps.daoImplMYSQL.CollDAOImpl();
      }
    }
    if (daoType.equalsIgnoreCase("COLLDUMP"))
    {
      if (dbType.equalsIgnoreCase("MSSQL"))
      {
        ob = new MSSQLCollectionDumpDAOImpl();
      }
      else
      {
        ob = new CollectionDumpDAOImpl();
      }
    }
    if (daoType.equalsIgnoreCase("COLLUPLOAD"))
    {
      if (dbType.equalsIgnoreCase("MSSQL"))
      {
        ob = new MSSQLCollUoloadDataDAOImpl();
      }
      else
      {
        ob = new CollUoloadDataDAOImpl();
      }
    }
    if (daoType.equalsIgnoreCase("HDM"))
    {
      if (dbType.equalsIgnoreCase("MSSQL"))
      {
        ob = new MSSQLHolidayMasterDAOImpl();
      }
      else
      {
        ob = new HolidayMasterDAOImpl();
      }
    }
    if (daoType.equalsIgnoreCase("MAKEMM"))
    {
      if (dbType.equalsIgnoreCase("MSSQL"))
      {
        ob = new MSSQLMakeModelMasterDAOImpl();
      }
      else
      {
        ob = new MakeModelMasterDAOImpl();
      }
    }

    if (daoType.equalsIgnoreCase("MDM"))
    {
      if (dbType.equalsIgnoreCase("MSSQL"))
      {
        ob = new MSSQLManualDeviationMasterDAOIMPL();
      }
      else
      {
        ob = new ManualDeviationMasterDAOIMPL();
      }
    }
    if (daoType.equalsIgnoreCase("MSM"))
    {
      if (dbType.equalsIgnoreCase("MSSQL"))
      {
        ob = new MSSQLManufacturerSupplierMappingMasterDAOIMPL();
      }
      else
      {
        ob = new ManufacturerSupplierMappingMasterDAOIMPL();
      }
    }

    if (daoType.equalsIgnoreCase("MD"))
    {
      if (dbType.equalsIgnoreCase("MSSQL"))
      {
        ob = new MSSQLMasterDAOImpl();
      }
      else
      {
        ob = new MasterDAOImpl();
      }
    }
    if (daoType.equalsIgnoreCase("MPD"))
    {
      if (dbType.equalsIgnoreCase("MSSQL"))
      {
        ob = new MSSQLMasterParameterDAOIMPL();
      }
      else
      {
        ob = new MasterParameterDAOIMPL();
      }
    }
    if (daoType.equalsIgnoreCase("PDM"))
    {
      if (dbType.equalsIgnoreCase("MSSQL"))
      {
        ob = new MSSQLPCDMasterDAOImpl();
      }
      else
      {
        ob = new PCDMasterDAOImpl();
      }
    }
    if (daoType.equalsIgnoreCase("RULEMASTERD"))
    {
      if (dbType.equalsIgnoreCase("MSSQL"))
      {
        ob = new MSSQLruleMasterDAOImpl();
      }
      else
      {
        ob = new ruleMasterDAOImpl();
      }
    }
    if (daoType.equalsIgnoreCase("SPM"))
    {
      if (dbType.equalsIgnoreCase("MSSQL"))
      {
        ob = new MSSQLScoreParamMasterDAOImpl();
      }
      else
      {
        ob = new ScoreParamMasterDAOImpl();
      }
    }
    if (daoType.equalsIgnoreCase("DEFERAL"))
    {
      if (dbType.equalsIgnoreCase("MSSQL"))
      {
        ob = new MSSQLDeferralDAOImpl();
      }
      else
      {
        ob = new DeferralDAOImpl();
      }
    }
    if (daoType.equalsIgnoreCase("DID"))
    {
      if (dbType.equalsIgnoreCase("MSSQL"))
      {
        ob = new MSSQLDeleteInstrumentDAOImpl();
      }
      else
      {
        ob = new DeleteInstrumentDAOImpl();
      }
    }
    if (daoType.equalsIgnoreCase("DISB"))
    {
      if (dbType.equalsIgnoreCase("MSSQL"))
      {
        ob = new MSSQLDisbursalInitiationDAOImpl();
      }
      else
      {
        ob = new DisbursalInitiationDAOImpl();
      }
    }
    if (daoType.equalsIgnoreCase("DOWNLOAD"))
    {
      if (dbType.equalsIgnoreCase("MSSQL"))
      {
        ob = new MSSQLDownloadDAOImpl();
      }
      else
      {
        ob = new DownloadDAOImpl();
      }
    }
    if (daoType.equalsIgnoreCase("DUMP"))
    {
      if (dbType.equalsIgnoreCase("MSSQL"))
      {
        ob = new MSSQLDumpDownLoadDAOImpl();
      }
      else
      {
        ob = new DumpDownLoadDAOImpl();
      }
    }
    if (daoType.equalsIgnoreCase("EARLY"))
    {
      if (dbType.equalsIgnoreCase("MSSQL"))
      {
        ob = new MSSQLEarlyClosureDAOImpl();
      }
      else
      {
        ob = new EarlyClosureDAOImpl();
      }
    }
    if (daoType.equalsIgnoreCase("EOD"))
    {
      if (dbType.equalsIgnoreCase("MSSQL"))
      {
        ob = new MSSQLEndOfDayProcessDAOImpl();
      }
      else
      {
        ob = new EndOfDayProcessDAOImpl();
      }
    }
    if (daoType.equalsIgnoreCase("GBD"))
    {
      if (dbType.equalsIgnoreCase("MSSQL"))
      {
        ob = new MSSQLGenerateBankingDAOImpl();
      }
      else
      {
        ob = new GenerateBankingDAOImpl();
      }
    }
    if (daoType.equalsIgnoreCase("GBATCH"))
    {
      if (dbType.equalsIgnoreCase("MSSQL"))
      {
        ob = new MSSQLGenerateBatchDAOImpl();
      }
      else
      {
        ob = new GenerateBatchDAOImpl();
      }
    }
    if (daoType.equalsIgnoreCase("HOLD"))
    {
      if (dbType.equalsIgnoreCase("MSSQL"))
      {
        ob = new MSSQLHoldInstrumentDAOImpl();
      }
      else
      {
        ob = new HoldInstrumentDAOImpl();
      }
    }
    if (daoType.equalsIgnoreCase("ASSETD"))
    {
      if (dbType.equalsIgnoreCase("MSSQL"))
      {
        ob = new MSSQLassetInsuranceDAOImpl();
      }
      else
      {
        ob = new assetInsuranceDAOImpl();
      }
    }
    if (daoType.equalsIgnoreCase("ASSETVD"))
    {
      if (dbType.equalsIgnoreCase("MSSQL"))
      {
        ob = new MSSQLassetVerificationDAOImpl();
      }
      else
      {
        ob = new assetVerificationDAOImpl();
      }
    }
    if (daoType.equalsIgnoreCase("BOD"))
    {
      if (dbType.equalsIgnoreCase("MSSQL"))
      {
        ob = new MSSQLBeginOfDayProcessDAOImpl();
      }
      else
      {
        ob = new BeginOfDayProcessDAOImpl();
      }
    }
    if (daoType.equalsIgnoreCase("CANCELD"))
    {
      if (dbType.equalsIgnoreCase("MSSQL"))
      {
        ob = new MSSQLCancellationDAOImpl();
      }
      else
      {
        ob = new CancellationDAOImpl();
      }
    }
    if (daoType.equalsIgnoreCase("CASEMAUTHORD"))
    {
      if (dbType.equalsIgnoreCase("MSSQL"))
      {
        ob = new MSSQLCaseMarkingAuthorDAOImpl();
      }
      else
      {
        ob = new CaseMarkingAuthorDAOImpl();
      }
    }
    if (daoType.equalsIgnoreCase("CASEMMAKERD"))
    {
      if (dbType.equalsIgnoreCase("MSSQL"))
      {
        ob = new MSSQLCaseMarkingMakerDAOImpl();
      }
      else
      {
        ob = new CaseMarkingMakerDAOImpl();
      }
    }

    if (daoType.equalsIgnoreCase("CHANGERD"))
    {
      if (dbType.equalsIgnoreCase("MSSQL"))
      {
        ob = new MSSQLChangeRateDAOImpl();
      }
      else
      {
        ob = new ChangeRateDAOImpl();
      }
    }

    if (daoType.equalsIgnoreCase("CHEQUESUD"))
    {
      if (dbType.equalsIgnoreCase("MSSQL"))
      {
        ob = new MSSQLChequeStatusUpdateDAOImpl();
      }
      else
      {
        ob = new ChequeStatusUpdateDAOImpl();
      }
    }

    if (daoType.equalsIgnoreCase("CMD"))
    {
      if (dbType.equalsIgnoreCase("MSSQL"))
      {
        ob = new MSSQLCreditManagementDAOImpl();
      }
      else
      {
        ob = new CreditManagementDAOImpl();
      }
    }
    if (daoType.equalsIgnoreCase("SCORINGMD"))
    {
      if (dbType.equalsIgnoreCase("MSSQL"))
      {
        ob = new MSSQLScoringMasterDAOImpl();
      }
      else
      {
        ob = new ScoringMasterDAOImpl();
      }
    }
    if (daoType.equalsIgnoreCase("USERAMD"))
    {
      if (dbType.equalsIgnoreCase("MSSQL"))
      {
        ob = new MSSQLUserApprovalMatrixDAOIMPL();
      }
      else
      {
        ob = new UserApprovalMatrixDAOIMPL();
      }
    }
    if (daoType.equalsIgnoreCase("USERPAD"))
    {
      if (dbType.equalsIgnoreCase("MSSQL"))
      {
        ob = new MSSQLUserProductAccessDAOImpl();
      }
      else
      {
        ob = new UserProductAccessDAOImpl();
      }
    }
    if (daoType.equalsIgnoreCase("COLLDMASTER"))
    {
      if (dbType.equalsIgnoreCase("MSSQL"))
      {
        ob = new com.masters.capsDaoImplMSSQL.MSSQLCollDAOImpl();
      }
      else
      {
        ob = new com.masters.capsDaoImplMYSQL.CollDAOImpl();
      }
    }
    if (daoType.equalsIgnoreCase("CORPORATED"))
    {
      if (dbType.equalsIgnoreCase("MSSQL"))
      {
        ob = new MSSQLCorpotateDAOImpl();
      }
      else
      {
        ob = new CorpotateDAOImpl();
      }
    }
    if (daoType.equalsIgnoreCase("ADDITIONALD"))
    {
      if (dbType.equalsIgnoreCase("MSSQL"))
      {
        ob = new MSSQLAdditionalDisbursalDAOImpl();
      }
      else
      {
        ob = new AdditionalDisbursalDAOImpl();
      }
    }
    if (daoType.equalsIgnoreCase("INSTRCD"))
    {
      if (dbType.equalsIgnoreCase("MSSQL"))
      {
        ob = new MSSQLInstrumentCapturingDAOImpl();
      }
      else
      {
        ob = new InstrumentCapturingDAOImpl();
      }
    }
    if (daoType.equalsIgnoreCase("KNOCKOFFD"))
    {
      if (dbType.equalsIgnoreCase("MSSQL"))
      {
        ob = new MSSQLKnockOffDAOImpl();
      }
      else
      {
        ob = new KnockOffDAOImpl();
      }
    }
    if (daoType.equalsIgnoreCase("LOANINITD"))
    {
      if (dbType.equalsIgnoreCase("MSSQL"))
      {
        ob = new MSSQLLoanInitiationDAOImpl();
      }
      else
      {
        ob = new LoanInitiationDAOImpl();
      }
    }
    if (daoType.equalsIgnoreCase("LOGGEDRD"))
    {
      if (dbType.equalsIgnoreCase("MSSQL"))
      {
        ob = new MSSQLLoggedInUserReportDAOImpl();
      }
      else
      {
        ob = new LoggedInUserReportDAOImpl();
      }
    }
    if (daoType.equalsIgnoreCase("MANUALAD"))
    {
      if (dbType.equalsIgnoreCase("MSSQL"))
      {
        ob = new MSSQLManualAdviceDAOImpl();
      }
      else
      {
        ob = new ManualAdviceDAOImpl();
      }
    }
    if (daoType.equalsIgnoreCase("MANUALINTCD"))
    {
      if (dbType.equalsIgnoreCase("MSSQL"))
      {
        ob = new MSSQLManualIntCalcDAOImpl();
      }
      else
      {
        ob = new ManualIntCalcDAOImpl();
      }
    }
    if (daoType.equalsIgnoreCase("MANUALMD"))
    {
      if (dbType.equalsIgnoreCase("MSSQL"))
      {
        ob = new MSSQLManualnpaMovementDAOImpl();
      }
      else
      {
        ob = new ManualnpaMovementDAOImpl();
      }
    }
    if (daoType.equalsIgnoreCase("MATURITYCD"))
    {
      if (dbType.equalsIgnoreCase("MSSQL"))
      {
        ob = new MSSQLMaturityClosureDAOImpl();
      }
      else
      {
        ob = new MaturityClosureDAOImpl();
      }
    }
    if (daoType.equalsIgnoreCase("PPD"))
    {
      if (dbType.equalsIgnoreCase("MSSQL"))
      {
        ob = new MSSQLPartPrePaymentDAOImpl();
      }
      else
      {
        ob = new PartPrePaymentDAOImpl();
      }
    }
    if (daoType.equalsIgnoreCase("PAYMENTD"))
    {
      if (dbType.equalsIgnoreCase("MSSQL"))
      {
        ob = new MSSQLPaymentDAOImpl();
      }
      else
      {
        ob = new PaymentDAOImpl();
      }
    }
    if (daoType.equalsIgnoreCase("PDCVIEWERD"))
    {
      if (dbType.equalsIgnoreCase("MSSQL"))
      {
        ob = new MSSQLPdcViewerDAOImpl();
      }
      else
      {
        ob = new PdcViewerDAOImpl();
      }
    }
    if (daoType.equalsIgnoreCase("POOLCD"))
    {
      if (dbType.equalsIgnoreCase("MSSQL"))
      {
        ob = new MSSQLPoolCreationDAOImpl();
      }
      else
      {
        ob = new PoolCreationDAOImpl();
      }
    }
    if (daoType.equalsIgnoreCase("POOLEDITD"))
    {
      if (dbType.equalsIgnoreCase("MSSQL"))
      {
        ob = new MSSQLPoolIdAddEditDAOImpl();
      }
      else
      {
        ob = new PoolIdAddEditDAOImpl();
      }
    }
    if (daoType.equalsIgnoreCase("POOLD"))
    {
      if (dbType.equalsIgnoreCase("MSSQL"))
      {
        ob = new MSSQLPoolIDDAOImpl();
      }
      else
      {
        ob = new PoolIDDAOImpl();
      }
    }
    if (daoType.equalsIgnoreCase("POOLRD"))
    {
      if (dbType.equalsIgnoreCase("MSSQL"))
      {
        ob = new MSSQLPoolReportDAOImpl();
      }
      else
      {
        ob = new PoolReportDAOImpl();
      }
    }
    if (daoType.equalsIgnoreCase("PPROCESSD"))
    {
      if (dbType.equalsIgnoreCase("MSSQL"))
      {
        ob = new MSSQLPresentationProcessDAOImpl();
      }
      else
      {
        ob = new PresentationProcessDAOImpl();
      }
    }
    if (daoType.equalsIgnoreCase("RECEIPTD"))
    {
      if (dbType.equalsIgnoreCase("MSSQL"))
      {
        ob = new MSSQLReceiptDAOImpl();
      }
      else
      {
        ob = new ReceiptDAOImpl();
      }
    }
    if (daoType.equalsIgnoreCase("RELEASEINTD"))
    {
      if (dbType.equalsIgnoreCase("MSSQL"))
      {
        ob = new MSSQLReleaseInstrumentDAOImpl();
      }
      else
      {
        ob = new ReleaseInstrumentDAOImpl();
      }
    }
    if (daoType.equalsIgnoreCase("REPAYSCHD"))
    {
      if (dbType.equalsIgnoreCase("MSSQL"))
      {
        ob = new MSSQLRepayScheduleDAOImpl();
      }
      else
      {
        ob = new RepayScheduleDAOImpl();
      }
    }
    if (daoType.equalsIgnoreCase("REPORTD"))
    {
      if (dbType.equalsIgnoreCase("MSSQL"))
      {
        ob = new MSSQLReportsDAOImpl();
      }
      else
      {
        ob = new com.cm.daoImplMYSQL.ReportsDAOImpl();
      }
    }

    if (daoType.equalsIgnoreCase("GLREPORTD"))
    {
      if (dbType.equalsIgnoreCase("MSSQL"))
      {
        ob = null;
      }
      else
      {
        ob = new com.GL.DAOImplMYSQL.ReportsDAOImpl();
      }
    }

    if (daoType.equalsIgnoreCase("REPRICINGD"))
    {
      if (dbType.equalsIgnoreCase("MSSQL"))
      {
        ob = new MSSQLRepricingDAOImpl();
      }
      else
      {
        ob = new RepricingDAOImpl();
      }
    }
    if (daoType.equalsIgnoreCase("SDLD"))
    {
      if (dbType.equalsIgnoreCase("MSSQL"))
      {
        ob = new MSSQLSDLiquidationDAOImpl();
      }
      else
      {
        ob = new SDLiquidationDAOImpl();
      }
    }
    if (daoType.equalsIgnoreCase("UPLOADDOCD"))
    {
      if (dbType.equalsIgnoreCase("MSSQL"))
      {
        ob = new MSSQLUploadDocumentDAOImpl();
      }
      else
      {
        ob = new UploadDocumentDAOImpl();
      }
    }
    if (daoType.equalsIgnoreCase("WAIVEOFFD"))
    {
      if (dbType.equalsIgnoreCase("MSSQL"))
      {
        ob = new MSSQLWaiveOffDAOImpl();
      }
      else
      {
        ob = new WaiveOffDAOImpl();
      }
    }
    if (daoType.equalsIgnoreCase("COMMONFUNCTIOND"))
    {
      if (dbType.equalsIgnoreCase("MSSQL"))
      {
        ob = new MSSQLcommonFunctionDaoImpl();
      }
      else
      {
        ob = new commonFunctionDaoImpl();
      }
    }
    if (daoType.equalsIgnoreCase("POPUPD"))
    {
      if (dbType.equalsIgnoreCase("MSSQL"))
      {
        ob = new MSSQLpopupDaoImpl();
      }
      else
      {
        ob = new popupDaoImpl();
      }
    }
    if (daoType.equalsIgnoreCase("LOGIND"))
    {
      if (dbType.equalsIgnoreCase("MSSQL"))
      {
        ob = new MSSQLLoginDaoImpl();
      }
      else
      {
        ob = new LoginDaoImpl();
      }
    }
    if (daoType.equalsIgnoreCase("USERPROFD"))
    {
      if (dbType.equalsIgnoreCase("MSSQL"))
      {
        ob = new MSSQLUserProfileDaoImpl();
      }
      else
      {
        ob = new UserProfileDaoImpl();
      }
    }

    if (daoType.equalsIgnoreCase("GLHOEAR"))
    {
      if (dbType.equalsIgnoreCase("MSSQL"))
      {
        ob = new MSSQLHoExpenseAllocationRatioDAOImpl();
      }
      else
      {
        ob = new HoExpenseAllocationRatioDAOImpl();
      }
    }

    if (daoType.equalsIgnoreCase("GLBDD"))
    {
      if (dbType.equalsIgnoreCase("MSSQL"))
      {
        ob = new MSSQLBudgetDefinitionDAOImpl();
      }
      else
      {
        ob = new BudgetDefinitionDAOImpl();
      }
    }

    if (daoType.equalsIgnoreCase("GLBMD"))
    {
      if (dbType.equalsIgnoreCase("MSSQL"))
      {
        ob = new MSSQLBudgetMasterDAOImpl();
      }
      else
      {
        ob = new BudgetMasterDAOImpl();
      }
    }

    if (daoType.equalsIgnoreCase("GLBVMD"))
    {
      if (dbType.equalsIgnoreCase("MSSQL"))
      {
        ob = new MSSQLBudgetVoucherMappingDAOImpl();
      }
      else
      {
        ob = new BudgetVoucherMappingDAOImpl();
      }
    }

    if (daoType.equalsIgnoreCase("GLGLD"))
    {
      if (dbType.equalsIgnoreCase("MSSQL"))
      {
        ob = new MSSQLGlGroupLedgerDAOImpl();
      }
      else
      {
        ob = new GlGroupLedgerDAOImpl();
      }
    }

    if (daoType.equalsIgnoreCase("GLLD"))
    {
      if (dbType.equalsIgnoreCase("MSSQL"))
      {
        ob = new MSSQLGLLedgerDAOImpl();
      }
      else
      {
        ob = new GLLedgerDAOImpl();
      }
    }

    if (daoType.equalsIgnoreCase("GLBLD"))
    {
      if (dbType.equalsIgnoreCase("MSSQL"))
      {
        ob = new MSSQLBudgetLedgetDAOImpl();
      }
      else
      {
        ob = new BudgetLedgetDAOImpl();
      }
    }

    if (daoType.equalsIgnoreCase("GLBCVD"))
    {
      if (dbType.equalsIgnoreCase("MSSQL"))
      {
        ob = new MSSQLGlBillCapturingViewDAOImpl();
      }
      else
      {
        ob = new GlBillCapturingViewDAOImpl();
      }
    }

    if (daoType.equalsIgnoreCase("GLBGD"))
    {
      if (dbType.equalsIgnoreCase("MSSQL"))
      {
        ob = new MSSQLGlBillGenerationDAOImpl();
      }
      else
      {
        ob = new GlBillGenerationDAOImpl();
      }
    }

    if (daoType.equalsIgnoreCase("GLPVRD"))
    {
      if (dbType.equalsIgnoreCase("MSSQL"))
      {
        ob = new MSSQLGlProvisionalVoucherReverseDAOImpl();
      }
      else
      {
        ob = new GlProvisionalVoucherReverseDAOImpl();
      }
    }

    if (daoType.equalsIgnoreCase("GLVD"))
    {
      if (dbType.equalsIgnoreCase("MSSQL"))
      {
        ob = new MSSQLGLVoucherDAOImpl();
      }
      else
      {
        ob = new GLVoucherDAOImpl();
      }
    }

    if (daoType.equalsIgnoreCase("GLVED"))
    {
      if (dbType.equalsIgnoreCase("MSSQL"))
      {
        ob = new MSSQLGLVoucherEntryDAOImpl();
      }
      else
      {
        ob = new GLVoucherEntryDAOImpl();
      }
    }

    if (daoType.equalsIgnoreCase("GLTRCMD"))
    {
      if (dbType.equalsIgnoreCase("MSSQL"))
      {
        ob = new MSSQLTDSRateChartMasterDAOImpl();
      }
      else
      {
        ob = new TDSRateChartMasterDAOImpl();
      }
    }

    if (daoType.equalsIgnoreCase("GLTBD"))
    {
      if (dbType.equalsIgnoreCase("MSSQL"))
      {
        ob = new MSSQLGlTrialBalanceDAOImpl();
      }
      else
      {
        ob = new GlTrialBalanceDAOImpl();
      }
    }

    if (daoType.equalsIgnoreCase("GLSRD"))
    {
      if (dbType.equalsIgnoreCase("MSSQL"))
      {
        ob = new MSSQLGlSaleReportDAOImpl();
      }
      else
      {
        ob = new GlSaleReportDAOImpl();
      }
    }

    if (daoType.equalsIgnoreCase("GLPLD"))
    {
      if (dbType.equalsIgnoreCase("MSSQL"))
      {
        ob = new MSSQLGlProfitLossDAOImpl();
      }
      else
      {
        ob = new GlProfitLossDAOImpl();
      }
    }

    if (daoType.equalsIgnoreCase("GLPWERF"))
    {
      if (dbType.equalsIgnoreCase("MSSQL"))
      {
        ob = new MSSQLGlPartyWiseExpRptDAOImpl();
      }
      else
      {
        ob = new GlPartyWiseExpRptDAOImpl();
      }
    }

    if (daoType.equalsIgnoreCase("GLEHD"))
    {
      if (dbType.equalsIgnoreCase("MSSQL"))
      {
        ob = new MSSQLGlExpenseHeadDAOImpl();
      }
      else
      {
        ob = new GlExpenseHeadDAOImpl();
      }
    }

    if (daoType.equalsIgnoreCase("GLDRD"))
    {
      if (dbType.equalsIgnoreCase("MSSQL"))
      {
        ob = new MSSQLGlDepreciationReportDAOImpl();
      }
      else
      {
        ob = new GlDepreciationReportDAOImpl();
      }
    }

    if (daoType.equalsIgnoreCase("GLCAPTD"))
    {
      if (dbType.equalsIgnoreCase("MSSQL"))
      {
        ob = new MSSQLGlCapitalizationDAOImpl();
      }
      else
      {
        ob = new GlCapitalizationDAOImpl();
      }

    }

    if (daoType.equalsIgnoreCase("GLLM"))
    {
      if (dbType.equalsIgnoreCase("MSSQL"))
      {
        ob = new MSSQLGLLedgerMaster1Impl();
      }
      else
      {
        ob = new GLLedgerMaster1Impl();
      }
    }

    if (daoType.equalsIgnoreCase("GLSL"))
    {
      if (dbType.equalsIgnoreCase("MSSQL"))
      {
        ob = new MSSQLGLSubLedgerDAOImpl();
      }
      else
      {
        ob = new GLSubLedgerDAOImpl();
      }
    }
    if (daoType.equalsIgnoreCase("GLAP"))
    {
      if (dbType.equalsIgnoreCase("MSSQL"))
      {
        ob = new MSSQLAccountPeriodDAOImpl();
      }
      else
      {
        ob = new AccountPeriodDAOImpl();
      }
    }
    if (daoType.equalsIgnoreCase("GLAY"))
    {
      if (dbType.equalsIgnoreCase("MSSQL"))
      {
        ob = new MSSQLAccountYearImpl();
      }
      else
      {
        ob = new AccountYearImpl();
      }
    }

    if (daoType.equalsIgnoreCase("GLYC"))
    {
      if (dbType.equalsIgnoreCase("MSSQL"))
      {
        ob = new MSSQLYearClosingDAOImpl();
      }
      else
      {
        ob = new YearClosingDAOImpl();
      }
    }
    if (daoType.equalsIgnoreCase("GLPC"))
    {
      if (dbType.equalsIgnoreCase("MSSQL"))
      {
        ob = new MSSQLPartyCreationDAOImpl();
      }
      else
      {
        ob = new PartyCreationDAOImpl();
      }
    }
    if (daoType.equalsIgnoreCase("GLITBM"))
    {
      if (dbType.equalsIgnoreCase("MSSQL"))
      {
        ob = new MSSQLIncomeTaxBlockMasterDAOImpl();
      }
      else
      {
        ob = new IncomeTaxBlockMasterDAOImpl();
      }
    }
    if (daoType.equalsIgnoreCase("GLVA"))
    {
      if (dbType.equalsIgnoreCase("MSSQL"))
      {
        ob = new MSSQLVoucherAuthorizationDaoImpl();
      }
      else
      {
        ob = new VoucherAuthorizationDaoImpl();
      }
    }
    if (daoType.equalsIgnoreCase("GLTC"))
    {
      if (dbType.equalsIgnoreCase("MSSQL"))
      {
        ob = new MSSQLTempleteCreationImpl();
      }
      else
      {
        ob = new TempleteCreationImpl();
      }
    }
    if (daoType.equalsIgnoreCase("GLTCP"))
    {
      if (dbType.equalsIgnoreCase("MSSQL"))
      {
        ob = new MSSQLTempletCreationPopImpl();
      }
      else
      {
        ob = new TempletCreationPopImpl();
      }
    }
    if (daoType.equalsIgnoreCase("GLVR"))
    {
      if (dbType.equalsIgnoreCase("MSSQL"))
      {
        ob = new MSSQLGlVoucherReversalDAOImpl();
      }
      else
      {
        ob = new GlVoucherReversalDAOImpl();
      }

    }

    if (daoType.equalsIgnoreCase("GLESU"))
    {
      if (dbType.equalsIgnoreCase("MSSQL"))
      {
        ob = new MSSQLExcelSheetUploadDAOImpl();
      }
      else
      {
        ob = new ExcelSheetUploadDAOImpl();
      }
    }
    if (daoType.equalsIgnoreCase("GLFDC"))
    {
      if (dbType.equalsIgnoreCase("MSSQL"))
      {
        ob = new MSSQLFaDepreciationCalcDAOImpl();
      }
      else
      {
        ob = new FaDepreciationCalcDAOImpl();
      }
    }
    if (daoType.equalsIgnoreCase("GLFAA"))
    {
      if (dbType.equalsIgnoreCase("MSSQL"))
      {
        ob = new MSSQLFixedAssetAccountDAOImpl();
      }
      else
      {
        ob = new FixedAssetAccountDAOImpl();
      }
    }
    if (daoType.equalsIgnoreCase("GLFAAD"))
    {
      if (dbType.equalsIgnoreCase("MSSQL"))
      {
        ob = new MSSQLFixedAssetAdditionDAOImpl();
      }
      else
      {
        ob = new FixedAssetAdditionDAOImpl();
      }
    }
    if (daoType.equalsIgnoreCase("GLFAC"))
    {
      if (dbType.equalsIgnoreCase("MSSQL"))
      {
        ob = new MSSQLFixedAssetClassDAOImpl();
      }
      else
      {
        ob = new FixedAssetClassDAOImpl();
      }
    }
    if (daoType.equalsIgnoreCase("GLFACL"))
    {
      if (dbType.equalsIgnoreCase("MSSQL"))
      {
        ob = new MSSQLFixedAssetClassificationDAOImpl();
      }
      else
      {
        ob = new FixedAssetClassificationDAOImpl();
      }
    }
    if (daoType.equalsIgnoreCase("GLFAD"))
    {
      if (dbType.equalsIgnoreCase("MSSQL"))
      {
        ob = new MSSQLFixedAssetDepartmentDAOImpl();
      }
      else
      {
        ob = new FixedAssetDepartmentDAOImpl();
      }
    }
    if (daoType.equalsIgnoreCase("GLFAI"))
    {
      if (dbType.equalsIgnoreCase("MSSQL"))
      {
        ob = new MSSQLFixedAssetInsuranceDAOImpl();
      }
      else
      {
        ob = new FixedAssetInsuranceDAOImpl();
      }
    }
    if (daoType.equalsIgnoreCase("GLFAL"))
    {
      if (dbType.equalsIgnoreCase("MSSQL"))
      {
        ob = new MSSQLFixedAssetLocationImpl();
      }
      else
      {
        ob = new FixedAssetLocationImpl();
      }
    }
    if (daoType.equalsIgnoreCase("GLFAS"))
    {
      if (dbType.equalsIgnoreCase("MSSQL"))
      {
        ob = new MSSQLFixedAssetSaleDAOImpl();
      }
      else
      {
        ob = new FixedAssetSaleDAOImpl();
      }
    }
    if (daoType.equalsIgnoreCase("GLFAR"))
    {
      if (dbType.equalsIgnoreCase("MSSQL"))
      {
        ob = new MSSQLFixedAssetRegisterDAOImpl();
      }
      else
      {
        ob = new FixedAssetRegisterDAOImpl();
      }
    }
    if (daoType.equalsIgnoreCase("GLFAT"))
    {
      if (dbType.equalsIgnoreCase("MSSQL"))
      {
        ob = new MSSQLFixedAssetTransferDAOImpl();
      }
      else
      {
        ob = new FixedAssetTransferDAOImpl();
      }
    }
    if (daoType.equalsIgnoreCase("GLFATW"))
    {
      if (dbType.equalsIgnoreCase("MSSQL"))
      {
        ob = new MSSQLFixedAssetTransferWithInDAOImpl();
      }
      else
      {
        ob = new FixedAssetTransferWithInDAOImpl();
      }
    }
    if (daoType.equalsIgnoreCase("GLCD"))
    {
      if (dbType.equalsIgnoreCase("MSSQL"))
      {
        ob = new MSSQLGlChequeDetailDAOImpl();
      }
      else
      {
        ob = new GlChequeDetailDAOImpl();
      }
    }
    if (daoType.equalsIgnoreCase("GLRPH"))
    {
      if (dbType.equalsIgnoreCase("MSSQL"))
      {
        ob = new MSSQLReceivPayHeadDAOImpl();
      }
      else
      {
        ob = new ReceivPayHeadDAOImpl();
      }
    }

    if (daoType.equalsIgnoreCase("DEDUPE"))
    {
      if (dbType.equalsIgnoreCase("MSSQL"))
      {
        ob = new MSSQLDedupeDAOImpl();
      }
      else
      {
        ob = new DedupeDAOImpl();
      }

    }

    if (daoType.equalsIgnoreCase("GLRPD"))
    {
      if (dbType.equalsIgnoreCase("MSSQL"))
      {
        ob = new MSSQLReconProcessDAOImpl();
      }
      else
      {
        ob = new ReconProcessDAOImpl();
      }
    }
    if (daoType.equalsIgnoreCase("COLLREPORTDAO"))
    {
      if (dbType.equalsIgnoreCase("MSSQL"))
      {
        ob = new MSSQLCollReportDAOImpl();
      }
      else
      {
        ob = new CollReportDAOImpl();
      }
    }

    if (daoType.equalsIgnoreCase("GLKOD"))
    {
      if (dbType.equalsIgnoreCase("MSSQL"))
      {
        ob = new MSSQLGLKnockOffDAOImpl();
      }
      else
      {
        ob = new GLKnockOffDAOImpl();
      }
    }

    if (daoType.equalsIgnoreCase("GLCSUD"))
    {
      if (dbType.equalsIgnoreCase("MSSQL"))
      {
        ob = new MSSQLGLChequeStatusUpdateDAOImpl();
      }
      else
      {
        ob = new GLChequeStatusUpdateDAOImpl();
      }
    }

    if (daoType.equalsIgnoreCase("GLBSD"))
    {
      if (dbType.equalsIgnoreCase("MSSQL"))
      {
        ob = new MSSQLGlBillSettlementDAOImpl();
      }
      else
      {
        ob = new GlBillSettlementDAOImpl();
      }

    }

    if (daoType.equalsIgnoreCase("GLBTBR"))
    {
      if (dbType.equalsIgnoreCase("MSSQL"))
      {
        ob = new MSSQLBanktoBankReconcileDAOImpl();
      }
      else
      {
        ob = new BanktoBankReconcileDAOImpl();
      }
    }
    if (daoType.equalsIgnoreCase("GLBC"))
    {
      if (dbType.equalsIgnoreCase("MSSQL"))
      {
        ob = new MSSQLBillCapturingDAOImpl();
      }
      else
      {
        ob = new BillCapturingDAOImpl();
      }
    }
    if (daoType.equalsIgnoreCase("GLBR"))
    {
      if (dbType.equalsIgnoreCase("MSSQL"))
      {
        ob = new MSSQLBillReceiptDaoImpl();
      }
      else
      {
        ob = new BillReceiptDaoImpl();
      }
    }
    if (daoType.equalsIgnoreCase("GLBTOBR"))
    {
      if (dbType.equalsIgnoreCase("MSSQL"))
      {
        ob = new MSSQLBooktoBookReconcileDAOImpl();
      }
      else
      {
        ob = new BooktoBookReconcileDAOImpl();
      }
    }
    if (daoType.equalsIgnoreCase("GLBAR"))
    {
      if (dbType.equalsIgnoreCase("MSSQL"))
      {
        ob = new MSSQLGLBankAutoReconcileDAOImpl();
      }
      else
      {
        ob = new GLBankAutoReconcileDAOImpl();
      }
    }
    if (daoType.equalsIgnoreCase("GLBMR"))
    {
      if (dbType.equalsIgnoreCase("MSSQL"))
      {
        ob = new MSSQLGLBankManualReconcileDAOImpl();
      }
      else
      {
        ob = new GLBankManualReconcileDAOImpl();
      }
    }
    if (daoType.equalsIgnoreCase("GLBKR"))
    {
      if (dbType.equalsIgnoreCase("MSSQL"))
      {
        ob = new MSSQLGlBankReconcilationDAOImpl();
      }
      else
      {
        ob = new GlBankReconcilationDAOImpl();
      }
    }
    if (daoType.equalsIgnoreCase("GLBSU"))
    {
      if (dbType.equalsIgnoreCase("MSSQL"))
      {
        ob = new MSSQLGLBankStatementUploadDAOImpl();
      }
      else
      {
        ob = new GLBankStatementUploadDAOImpl();
      }
    }
    if (daoType.equalsIgnoreCase("GLBKR"))
    {
      if (dbType.equalsIgnoreCase("MSSQL"))
      {
        ob = new MSSQLGlBankReconcilationDAOImpl();
      }
      else
      {
        ob = new GlBankReconcilationDAOImpl();
      }
    }
    if (daoType.equalsIgnoreCase("GLCD"))
    {
      if (dbType.equalsIgnoreCase("MSSQL"))
      {
        ob = new MSSQLGlChequeDetailDAOImpl();
      }
      else
      {
        ob = new GlChequeDetailDAOImpl();
      }
    }
    if (daoType.equalsIgnoreCase("GLSMA"))
    {
      if (dbType.equalsIgnoreCase("MSSQL"))
      {
        ob = new MSSQLStationaryMasterAdditionDAOImpl();
      }
      else
      {
        ob = new StationaryMasterAdditionDAOImpl();
      }
    }
    if (daoType.equalsIgnoreCase("GLSMI"))
    {
      if (dbType.equalsIgnoreCase("MSSQL"))
      {
        ob = new MSSQLStationaryIssuanceDAOImpl();
      }
      else
      {
        ob = new StationaryIssuanceDAOImpl();
      }
    }

    if (daoType.equalsIgnoreCase("DEALPD"))
    {
      if (dbType.equalsIgnoreCase("MSSQL"))
      {
        ob = new MSSQLDealProcessingDaoImpl();
      }
      else
      {
        ob = new DealProcessingDaoImpl();
      }
    }
    if (daoType.equalsIgnoreCase("DISBCD"))
    {
      if (dbType.equalsIgnoreCase("MSSQL"))
      {
        ob = new MSSQLDisbCancellationDAOImpl();
      }
      else
      {
        ob = new DisbCancellationDAOImpl();
      }
    }

    if (daoType.equalsIgnoreCase("PAYOUTMASTERD"))
    {
      if (dbType.equalsIgnoreCase("MSSQL"))
      {
        ob = new MSSQLPayoutMasterDaoImpl();
      }
      else
      {
        ob = new PayoutMasterDaoImpl();
      }
    }
    if (daoType.equalsIgnoreCase("REPORTFUNCTIONACCESSD"))
    {
      if (dbType.equalsIgnoreCase("MSSQL"))
      {
        ob = new MSSQLReportFunctionAccessDAOImpl();
      }
      else
      {
        ob = new ReportFunctionAccessDAOImpl();
      }
    }
    if (daoType.equalsIgnoreCase("NOTEPADINCP"))
    {
      if (dbType.equalsIgnoreCase("MSSQL"))
      {
        ob = new MSSQLNotepadDAOImpl();
      }
      else
      {
        ob = new NotepadDAOImpl();
      }
    }
    if (daoType.equalsIgnoreCase("IMD"))
    {
      if (dbType.equalsIgnoreCase("MSSQL"))
      {
        ob = new MSSQLImdDAOImpl();
      }
      else
      {
        ob = new ImdDAOImpl();
      }

    }

    if (daoType.equalsIgnoreCase("FILETRACKING"))
    {
      if (dbType.equalsIgnoreCase("MSSQL")) {
        ob = new MSSQLFileTrackingDAOImpl();
      }
      else
      {
        ob = new FileTrackingDAOImpl();
      }

    }

    if (daoType.equalsIgnoreCase("HANDSIGHTING"))
    {
      if (dbType.equalsIgnoreCase("MSSQL")) {
        ob = new MSSQLHandSightingDAOImpl();
      }
      else
      {
        ob = new HandSightingDAOImpl();
      }

    }

    if (daoType.equalsIgnoreCase("COMMEMAIL"))
    {
      if (dbType.equalsIgnoreCase("MSSQL"))
      {
        ob = new MSSQLEmailDAOImpl();
      }
      else
      {
        ob = new EmailDAOImpl();
      }
    }
    if (daoType.equalsIgnoreCase("COMMSMS"))
    {
      if (dbType.equalsIgnoreCase("MSSQL"))
      {
        ob = new MSSQLSmsDAOImpl();
      }
      else
      {
        ob = new SmsDAOImpl();
      }

    }

    if (daoType.equalsIgnoreCase("CIBILVER"))
    {
      if (dbType.equalsIgnoreCase("MSSQL"))
      {
        ob = new MSSQLCibilVerificationDAOImpl();
      }
      else
      {
        ob = new CibilVerificationDAOImpl();
      }

    }

    if (daoType.equalsIgnoreCase("LLD"))
    {
      if (dbType.equalsIgnoreCase("MSSQL")) {
        ob = new MSSQLLinkLoanDAOImpl();
      }
      else {
        ob = new LinkLoanDAOImpl();
      }
    }
    if (daoType.equalsIgnoreCase("MDU"))
    {
      if (dbType.equalsIgnoreCase("MSSQL")) {
        ob = new MSSQLManualAdviceUploadDAODAOImpl();
      }
      else {
        ob = new ManualAdviceUploadDAOImpl();
      }

    }

    if (daoType.equalsIgnoreCase("LEGALMASTERDAO"))
    {
      if (dbType.equalsIgnoreCase("MSSQL")) {
        ob = new MSSQLLegalMasterDaoImpl();
      }
      else
      {
        ob = new LegalMasterDaoImpl();
      }
    }

    if (daoType.equalsIgnoreCase("LEGALTRANSDAO"))
    {
      if (dbType.equalsIgnoreCase("MSSQL")) {
        ob = new MSSQLLegalTransactionDaoImpl();
      }
      else
      {
        ob = new LegalTransactionDaoImpl();
      }

    }

    if (daoType.equalsIgnoreCase("REPOMASTERDAO"))
    {
      if (dbType.equalsIgnoreCase("MSSQL")) {
        ob = new MSSQLRepoMasterDaoImpl();
      }
      else
      {
        ob = new RepoMasterDaoImpl();
      }
    }

    if (daoType.equalsIgnoreCase("REPOTRANSDAO"))
    {
      if (dbType.equalsIgnoreCase("MSSQL")) {
        ob = new MSSQLRepoTransactionDaoImpl();
      }
      else
      {
        ob = new RepoTransactionDaoImpl();
      }

    }

    if (daoType.equalsIgnoreCase("GLBRU"))
    {
      if (dbType.equalsIgnoreCase("MSSQL")) {
        ob = new MSSQLBankUploadReconDAOImpl();
      }
      else
      {
        ob = new BankUploadReconDAOImpl();
      }

    }

    if (daoType.equalsIgnoreCase("NHBMAPPING"))
    {
      if (dbType.equalsIgnoreCase("MSSQL"))
      {
        ob = new MSSQLNhbMappingDAOImpl();
      }
      else
      {
        ob = new NhbMappingDAOImpl();
      }
    }

    if (daoType.equalsIgnoreCase("DRA"))
    {
      if (dbType.equalsIgnoreCase("MSSQL"))
      {
        ob = new MSSQLDealRateApprovalDAOImpl();
      }
      else
      {
        ob = new DealRateApprovalDAOImpl();
      }
    }

    if (daoType.equalsIgnoreCase("CUSTOMERSERVICE"))
    {
      if (dbType.equalsIgnoreCase("MSSQL"))
      {
        ob = new MSSQLCustomerServiceDAOImpl();
      }
      else
      {
        ob = new CustomerServiceDAOImpl();
      }
    }

    if (daoType.equalsIgnoreCase("REPOAPP"))
    {
      if (dbType.equalsIgnoreCase("MSSQL"))
      {
        ob = new MSSQLRepoBillingApprovalMakerDAOImpl();
      }
      else
      {
        ob = new RepoBillingApprovalMakerDAOImpl();
      }
    }

    if (daoType.equalsIgnoreCase("REPOAUTHOR"))
    {
      if (dbType.equalsIgnoreCase("MSSQL"))
      {
        ob = new MSSQLRepoBillingApprovalAuthorDAOImpl();
      }
      else
      {
        ob = new RepoBillingApprovalAuthorDAOImpl();
      }

    }

    if (daoType.equalsIgnoreCase("LDU"))
    {
      if (dbType.equalsIgnoreCase("MSSQL"))
      {
        ob = new MSSQLLoanDetailUploadDAOImpl();
      }
      else
      {
        ob = new LoanDetailUploadDAOImpl();
      }

    }

    if (daoType.equalsIgnoreCase("FU"))
    {
      if (dbType.equalsIgnoreCase("MSSQL"))
      {
        ob = new MSSQLFileUtilityDaoImpl();
      }
      else
      {
        ob = new FileUtilityDaoImpl();
      }
    }
    if (daoType.equalsIgnoreCase("FUD"))
    {
      if (dbType.equalsIgnoreCase("MSSQL"))
      {
        ob = new MSSQLFileUploadDaoImpl();
      }
      else
      {
        ob = new FileUploadDaoImpl();
      }
    }

    if (daoType.equalsIgnoreCase("OCR"))
    {
      ob = new OcrDaoImpl();
    }

    if (daoType.equalsIgnoreCase("FU"))
    {
      ob = new FileUtilityDaoImpl();
    }

    if (daoType.equalsIgnoreCase("ECP"))
    {
      ob = new EligibilityCalculationProcessDaoImpl();
    }

    if (daoType.equalsIgnoreCase("FUD"))
    {
      ob = new FileUploadDaoImpl();
    }

    if (daoType.equalsIgnoreCase("CAD"))
    {
      ob = new CreditApprovalDAOImpl();
    }

    if (daoType.equalsIgnoreCase("DAD"))
    {
      ob = new DataAuthenticationDaoImpl();
    }

    if (daoType.equalsIgnoreCase("ECP"))
    {
      if (dbType.equalsIgnoreCase("MSSQL"))
      {
        ob = new MSSQLEligibilityCalculationProcessDaoImpl();
      }
      else
      {
        ob = new EligibilityCalculationProcessDaoImpl();
      }
    }

    if (daoType.equalsIgnoreCase("FRD"))
    {
      if (dbType.equalsIgnoreCase("MSSQL"))
      {
        ob = new MSSQLFinancialReportDaoImpl();
      }
      else
      {
        ob = new FinancialReportDaoImpl();
      }
    }

    if (daoType.equalsIgnoreCase("IWD"))
    {
      if (dbType.equalsIgnoreCase("MSSQL"))
      {
        ob = new MSSQLInterestWorkingDAOImpl();
      }
      else
      {
        ob = new InterestWorkingDAOImpl();
      }

    }

    if (daoType.equalsIgnoreCase("GUD"))
    {
      if (dbType.equalsIgnoreCase("MSSQL"))
      {
        ob = new MSSQLGenericUploadDAOImpl();
      }
      else
      {
        ob = new GenericUploadDAOImpl();
      }

    }
    if(daoType.equalsIgnoreCase("TODO"))
    {
    	if(dbType.equalsIgnoreCase("MSSQL"))
    	{							
    		ob= new  MSSQLToDoCaseUploadDAOImpl();			
    	}
    	else
    	{
    		ob= new ToDoCaseUploadDAOImpl(); 
    	}
    }

    if (daoType.equalsIgnoreCase("CMEXLUPL"))
    {
      if (dbType.equalsIgnoreCase("MSSQL"))
      {
       // ob = new MSSQLCreditMgmtExcelSheetUploadDAOImpl();
      }
      else
      {
        ob = new CreditMgmtExcelSheetUploadDAOImpl();
      }

    }

    if (daoType.equalsIgnoreCase("DD"))
    {
      if (dbType.equalsIgnoreCase("MSSQL"))
      {
        ob = new MSSQLDedupeReferalDaoImpl();
      }
      else {
        ob = new DedupeReferalDaoImpl();
      }

    }

    if (daoType.equalsIgnoreCase("VD"))
    {
      ob = new VehicleDAOImpl();
    }

    if (daoType.equalsIgnoreCase("ACHCAPDAO"))
    {
      if (dbType.equalsIgnoreCase("MSSQL"))
      {
        ob = new MSSQLACHCapturingDAOImpl();
      }
      else
      {
        ob = new ACHCapturingDAOImpl();
      }

    }

    if(daoType.equalsIgnoreCase("SDB"))
	{
		if(dbType.equalsIgnoreCase("MSSQL"))
		{
			ob=new MSSQLSalesDashBoardDAOImpl();
		}
		else
		{
			ob= new SalesDashBoardDAOImpl();
		}
	}
    if(daoType.equalsIgnoreCase("MOBILEPAYINSLIP"))
	{
		if(dbType.equalsIgnoreCase("MSSQL"))
		{
			ob= new MSSQLMobilePayInSlipDAOImpl();
		}
		else
		{
			ob= new MobilePayInSlipDAOImpl();
		}
	}

    if (daoType.equalsIgnoreCase("LOSU"))
    {
      ob = new LosUploadDAOImpl();
    }
    
    if (daoType.equalsIgnoreCase("COVENANTTRACKINGPROPOSAL"))
    {
      ob = new CovenantProposalTrackingDAOImpl();
    }
    
	if(daoType.equalsIgnoreCase("PreDeal"))
	{
		
			ob= new preDealDaoImp(); 
		
	}
	//awadhesh code start for dump access master
		if(daoType.equalsIgnoreCase("DUMPFUNCTIONACCESSD"))
		{
			
				ob= new DumpAccessDAOImpl();
			
		}
	if (daoType.equalsIgnoreCase("CMESU"))
    {
        ob = new ExcelSheetUploadDAOImplCM();
    }
	if(daoType.equalsIgnoreCase("UWA"))
	{
		ob = new UnderwritterDaoImpl();
	}
	if(daoType.equalsIgnoreCase("SMA"))
    {
	if(dbType.equalsIgnoreCase("MSSQL"))
	{
		ob= new com.cm.daoImplMSSQL.MSSQLStationaryMasterAdditionDAOImpl();
	}
	else
	{
		ob= new com.cm.daoImplMYSQL.StationaryMasterAdditionDAOImpl();
	}
}
if(daoType.equalsIgnoreCase("SMI"))
    {
	if(dbType.equalsIgnoreCase("MSSQL"))
	{
		ob= new com.cm.daoImplMSSQL.MSSQLStationaryIssuanceDAOImpl();
	}
	else
	{
		ob= new com.cm.daoImplMYSQL.StationaryIssuanceDAOImpl();
	}
}
    return ob;
  }
}