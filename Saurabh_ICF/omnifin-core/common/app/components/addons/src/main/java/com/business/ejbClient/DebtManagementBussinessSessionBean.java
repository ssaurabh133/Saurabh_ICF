package com.business.ejbClient;

import java.sql.SQLException;
import java.util.ArrayList;
import org.apache.log4j.Logger;
import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.transaction.UserTransaction;
import com.connect.CommonFunction;
import com.connect.DaoImplInstanceFactory;
import com.masters.capsDao.CollDAO;
import com.masters.capsVO.ActionCodeMasterVo;
import com.masters.capsVO.AllocationMasterVo;
import com.masters.capsVO.QueueCodeMasterVo;

@Stateless
@TransactionManagement(TransactionManagementType.BEAN)
public class DebtManagementBussinessSessionBean implements
		DebtManagementBussinessSessionBeanRemote {
	private static final Logger logger = Logger.getLogger(DebtManagementBussinessSessionBean.class.getName());
	CollDAO newObj=(CollDAO)DaoImplInstanceFactory.getDaoImplInstance(CollDAO.IDENTITY); 
	//CollDAO newObj = new CollDAOImpl();				// changed by asesh for mssql
	// Injects UserTransaction
	  @Resource
	  private UserTransaction userTransaction;
	
	//--------------Action Code Master-----------------By Nishant Rai
	public ArrayList <ActionCodeMasterVo> searchActionCodeData(ActionCodeMasterVo VO){
		logger.info("..In DebtManagementBussinessSessionBean..............searchActionCodeData");
		logger.info("Implementation class: "+newObj.getClass());
		ArrayList list= newObj.searchActionCodeData(VO);
		return list;
	}
	public boolean insertActionCodeMaster(ActionCodeMasterVo VO){
		logger.info("..In DebtManagementBussinessSessionBean..............insertActionCodeMaster");
		boolean st =false;
		try
		{
			userTransaction.begin();
			st = newObj.insertActionCodeMaster(VO);
			if(st)
			{
				logger.info("before commit");
				userTransaction.commit();
				
			}
			else
			{
				logger.info("before rollback");
				userTransaction.rollback();
			}
		}
		catch(Exception e)
		{
			logger.info("Exception: "+e);
		}
	
		return st;
}
	public boolean updateActionCodeData(ActionCodeMasterVo VO){
		logger.info("..In DebtManagementBussinessSessionBean..............updateActionCodeData");
		boolean st =false;
		try
		{
			userTransaction.begin();
			st = newObj.updateActionCodeData(VO);
			if(st)
			{
				logger.info("before commit");
				userTransaction.commit();
				
			}
			else
			{
				logger.info("before rollback");
				userTransaction.rollback();
			}
		}
		catch(Exception e)
		{
			logger.info("Exception: "+e);
		}
		return st;
}
	
	//--------------Classification Master-----------------By Nishant Rai
	public ArrayList <QueueCodeMasterVo> searchQueueCodeData(QueueCodeMasterVo VO){
		logger.info("..In DebtManagementBussinessSessionBean..............searchQueueCodeData");
		logger.info("Implementation class: "+newObj.getClass());
		ArrayList<QueueCodeMasterVo> list= newObj.searchQueueCodeData(VO);
		return list;
	}
	public ArrayList<QueueCodeMasterVo> NPAStageList(){
		logger.info("..In DebtManagementBussinessSessionBean..............NPAStageList");
		logger.info("Implementation class: "+newObj.getClass());
		ArrayList<QueueCodeMasterVo> list= newObj.NPAStageList();
		return list;
	}
	public ArrayList<QueueCodeMasterVo> CustCategoryList(){
		logger.info("..In DebtManagementBussinessSessionBean..............CustCategoryList");
		logger.info("Implementation class: "+newObj.getClass());
		ArrayList<QueueCodeMasterVo> list= newObj.CustCategoryList();
		return list;
	}
	public ArrayList <QueueCodeMasterVo> editQueueCodeData(String queue){
		logger.info("..In DebtManagementBussinessSessionBean..............editQueueCodeData");
		logger.info("Implementation class: "+newObj.getClass());
		ArrayList<QueueCodeMasterVo> list= newObj.editQueueCodeData(queue);
		return list;
	}
	public boolean insertQueueCodeMaster(QueueCodeMasterVo VO){
		logger.info("..In DebtManagementBussinessSessionBean..............insertQueueCodeMaster");
		boolean st =false;
		try
		{
			userTransaction.begin();
			st = newObj.insertQueueCodeMaster(VO);
			if(st)
			{
				logger.info("before commit");
				userTransaction.commit();
				
			}
			else
			{
				logger.info("before rollback");
				userTransaction.rollback();
			}
		}
		catch(Exception e)
		{
			logger.info("Exception: "+e);
		}
		return st;
}
	public boolean updateQueueCodeData(QueueCodeMasterVo VO){
		logger.info("..In DebtManagementBussinessSessionBean..............updateQueueCodeData");
		boolean st =false;
		try
		{
			userTransaction.begin();
			st = newObj.updateQueueCodeData(VO);
			if(st)
			{
				logger.info("before commit");
				userTransaction.commit();
				
			}
			else
			{
				logger.info("before rollback");
				userTransaction.rollback();
			}
		}
		catch(Exception e)
		{
			logger.info("Exception: "+e);
		}
		return st;
}
	public ArrayList<QueueCodeMasterVo> ProductList(){
		logger.info("..In DebtManagementBussinessSessionBean..............ProductList");
		logger.info("Implementation class: "+newObj.getClass());
		ArrayList<QueueCodeMasterVo> list= newObj.ProductList();
		return list;
	}
	public String checkQueueCodeMaster(QueueCodeMasterVo queueCodeMasterVo){
		logger.info("..In DebtManagementBussinessSessionBean..............checkQueueCodeMaster");
		logger.info("Implementation class: "+newObj.getClass());
		String st = newObj.checkQueueCodeMaster(queueCodeMasterVo);
		return st;
}
	public String checkPriority(QueueCodeMasterVo queueCodeMasterVo){
		logger.info("..In DebtManagementBussinessSessionBean..............checkPriority");
		logger.info("Implementation class: "+newObj.getClass());
		String st = newObj.checkPriority(queueCodeMasterVo);
		return st;
}
	
	//--------------Allocation Master-----------------By Nishant Rai
	public ArrayList<AllocationMasterVo> getQueueList(){
		logger.info("..In DebtManagementBussinessSessionBean..............getQueueList");
		logger.info("Implementation class: "+newObj.getClass());
		ArrayList<AllocationMasterVo> list= newObj.getQueueList();
		return list;
	}
    public String saveQueueAllocation(AllocationMasterVo collVo, String[] percentage,int total,String[] queueuser,String[] branchList) throws SQLException{
		logger.info("..In DebtManagementBussinessSessionBean..............saveQueueAllocation");
		String st = "";
		try
		{
			userTransaction.begin();
			logger.info("Implementation class: "+newObj.getClass());
			st = newObj.saveQueueAllocation(collVo, percentage, total, queueuser,branchList);
			if(CommonFunction.checkNull(st).equalsIgnoreCase("SAVE"))
			{
				logger.info("before commit");
				userTransaction.commit();				
			}
			else
			{
				logger.info("before rollback");
				userTransaction.rollback();
			}
		}
		catch(Exception e)
		{
			logger.info("Exception: "+e);
		}
		return st;
}
    public ArrayList<AllocationMasterVo> searchQueueAllocationData(AllocationMasterVo allocationVo){
		logger.info("..In DebtManagementBussinessSessionBean..............searchQueueAllocationData");
		logger.info("Implementation class: "+newObj.getClass());
		ArrayList<AllocationMasterVo> list= newObj.searchQueueAllocationData(allocationVo);
		return list;
	}
    public ArrayList<AllocationMasterVo> searchQueueAllocationEdit(String userId){
		logger.info("..In DebtManagementBussinessSessionBean..............searchQueueAllocationEdit");
		logger.info("Implementation class: "+newObj.getClass());
		ArrayList<AllocationMasterVo> list= newObj.searchQueueAllocationEdit(userId);
		return list;
	}
    public ArrayList<AllocationMasterVo> searchQueueEdit(String userId){
		logger.info("..In DebtManagementBussinessSessionBean..............searchQueueEdit");
		logger.info("Implementation class: "+newObj.getClass());
		ArrayList<AllocationMasterVo> list= newObj.searchQueueEdit(userId);
		return list;
	}
    public String modifyqueueAllocation(AllocationMasterVo collVo, String[] checkbox,String[] percentagebox,int total) throws SQLException{
		logger.info("..In DebtManagementBussinessSessionBean..............modifyqueueAllocation");
		String st = "";

		try
		{
			userTransaction.begin();
			logger.info("Implementation class: "+newObj.getClass());
			st = newObj.modifyqueueAllocation(collVo, checkbox, percentagebox, total);
			if(CommonFunction.checkNull(st).equalsIgnoreCase("Modify"))
			{
				logger.info("before commit");
				userTransaction.commit();				
			}
			else
			{
				logger.info("before rollback");
				userTransaction.rollback();
			}
		}
		catch(Exception e)
		{
			logger.info("Exception: "+e);
		}
		return st;
}
	public String calcpercentage(String[] checkbox) throws SQLException{
		logger.info("..In DebtManagementBussinessSessionBean..............calcpercentage");
		logger.info("Implementation class: "+newObj.getClass());
		String st = newObj.calcpercentage(checkbox);
		return st;
}	
	public ArrayList <AllocationMasterVo> searchAllocationData(Object ob){
		logger.info("..In DebtManagementBussinessSessionBean..............searchAllocationData");
		logger.info("Implementation class: "+newObj.getClass());
		ArrayList<AllocationMasterVo> list= newObj.searchAllocationData(ob);
		return list;
	}
	
	public ArrayList<AllocationMasterVo> getresultForBranchAllcation(String queue_code){
		logger.info("CreditProcessingMasterBussinessSessionBean...........get vehicle record on the basis of MODEL ID.");
		 logger.info("Implementation class: "+newObj.getClass()); 
		ArrayList<AllocationMasterVo> list = newObj.getresultForBranchAllcation(queue_code);
		
		return list;
	} 
    public String modifyQueueAllocationDtl(AllocationMasterVo collVo, String[] percentage,int total,String[] queueuser,String[] branchList) {
		logger.info("..In DebtManagementBussinessSessionBean..............saveQueueAllocation");
		String st = "";
		try
		{
			userTransaction.begin();
			logger.info("Implementation class: "+newObj.getClass());
			st = newObj.modifyQueueAllocationDtl(collVo, percentage, total, queueuser,branchList);
			if(CommonFunction.checkNull(st).equalsIgnoreCase("SAVE"))
			{
				logger.info("before commit");
				userTransaction.commit();				
			}
			else
			{
				logger.info("before rollback");
				userTransaction.rollback();
			}
		}
		catch(Exception e)
		{
			logger.error("Exception: "+e);
		}
		return st;
}
	@Override
	public ArrayList<AllocationMasterVo> allocatedCollQueueData(AllocationMasterVo allocationVo) {
		logger.info("..In DebtManagementBussinessSessionBean..............searchQueueAllocationData");
		logger.info("Implementation class: "+newObj.getClass());
		ArrayList<AllocationMasterVo> list= newObj.allocatedCollQueueData(allocationVo);
		return list;
	}
			
}
