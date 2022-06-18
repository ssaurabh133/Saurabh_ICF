package com.cm.daoImplMYSQL;

import java.util.ArrayList;

import org.apache.log4j.Logger;

import com.connect.CommonFunction;
import com.connect.ConnectionUploadDAO;
import com.cm.dao.PresentationProcessDAO;
import com.cm.vo.PresentationProcessVO;

public class PresentationProcessDAOImpl implements PresentationProcessDAO {
	private static final Logger logger = Logger.getLogger(PresentationProcessDAOImpl.class.getName());
	java.util.ResourceBundle resource = java.util.ResourceBundle.getBundle("com.yourcompany.struts.utill");
	String dateFormatWithTime=resource.getString("lbl.dateWithTimeInDao");
	String dateFormat=resource.getString("lbl.dateInDao");
	String dateForDisbursal=resource.getString("lbl.dateForDisbursal");
//	DecimalFormat myFormatter = new DecimalFormat("###,##0.00######");
	int no=Integer.parseInt(resource.getString("msg.pageSizeForMaster"));
	  public String generateProcess(PresentationProcessVO presentationProcessVO) {
	   	    ArrayList<Object> in =new ArrayList<Object>();
     		ArrayList<Object> out =new ArrayList<Object>();
     		ArrayList outMessages = new ArrayList();
     		StringBuilder s1 = new StringBuilder();
//     		StringBuilder s2 = new StringBuilder();
     		StringBuilder date = new StringBuilder();
     		StringBuilder date1 = new StringBuilder();
		  logger.info("In generateProcess");
//			CallableStatement cst=null;
			  ArrayList<PresentationProcessVO> presentationProcess=new ArrayList<PresentationProcessVO>();
//			  Connection con=ConnectionUploadDAO.getConnection();	  
//		String s1 ="";
		String s2="";
			try{
				
//				String curDate= "select curdate()";
//				String now = ConnectionUploadDAO.singleReturn(curDate);
			
		       
//		        con.setAutoCommit(false);
		        logger.info("In Procedure Generate_Presentation_Batches");
//		        cst=con.prepareCall("call Generate_Presentation_Batches(?,STR_TO_DATE(?,'"+dateFormat+"'),STR_TO_DATE(?,'"+dateFormat+"'),?,?,?,?,?,?)");
	        
		        in.add(presentationProcessVO.getCompanyID() );
		        date.append(CommonFunction.changeFormat(CommonFunction.checkNull(presentationProcessVO.getMakerDate()).trim()));
       	    if(date.toString() != null)
       	    	in.add(date.toString());

//		        in.add(((CommonFunction.checkNull(presentationProcessVO.getMakerDate()).trim())));
		        date1.append(CommonFunction.changeFormat(CommonFunction.checkNull(presentationProcessVO.getPresentationDate()).trim()));
       	    if(date1.toString() != null)
       	    	in.add(date1.toString());

//		        in.add(((CommonFunction.checkNull(presentationProcessVO.getPresentationDate().trim()))));
		        in.add(presentationProcessVO.getLbxBankID());
			
		        in.add( presentationProcessVO.getLbxBranchID());
		        in.add(((CommonFunction.checkNull(presentationProcessVO.getBankAccount().trim()))));
				       
		        in.add(((CommonFunction.checkNull(presentationProcessVO.getMakerId().trim()))));
				       
//				        cst.registerOutParameter(8, Types.CHAR );
//				        cst.registerOutParameter(9, Types.CHAR );
//				        cst.executeUpdate();
//				         s1= cst.getString(8);
//			             s2 = cst.getString(9);
				        out.add(s1.toString());
				        out.add(s2);

				       outMessages=(ArrayList) ConnectionUploadDAO.callSP("Generate_Presentation_Batches",in,out);
				        s1.append(CommonFunction.checkNull(outMessages.get(0)));
				        s2 = CommonFunction.checkNull(outMessages.get(1));
			            logger.info("s1: "+s1);
			            logger.info("s2: "+s2);
//			            
//			            if(s1.equalsIgnoreCase("S")){
//			            	con.commit();
//			            }else{
//			            	con.rollback();
//			            	logger.info("Proc Exception----"+s2);
//			            }
//		        
			            logger.info("After proc call....");
			            

					}catch (Exception e) {
						
						e.printStackTrace();
					}
					finally
					{
						s1 = null;
						date = null;
						date1 = null;
					}
				 
			return s2;
	}
	  
	public String getMaxBatchId(String maker_id)
	{
		String batch_id="0";
		String query="select max(BATCH_ID) from presentation_process_upload_temp where MAKER_ID='"+maker_id.trim()+"' for update";
		logger.info("Query for gettiing max(BAtch_id)  :  "+query);
		try
		{
			batch_id=CommonFunction.checkNull(ConnectionUploadDAO.singleReturn(query));
			logger.info("batch_id  :  "+batch_id);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		return batch_id;
	}
}
