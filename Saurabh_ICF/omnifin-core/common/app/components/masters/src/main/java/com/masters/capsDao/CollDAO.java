package com.masters.capsDao;

import java.sql.SQLException;
import java.util.ArrayList;
import javax.servlet.http.HttpServletRequest;
import com.masters.capsVO.AllocationMasterVo;
import com.masters.capsVO.QueueCodeMasterVo;
import com.masters.capsVO.ActionCodeMasterVo;

public interface CollDAO {
	String IDENTITY="COLLDMASTER";
	//--------------Action Code Master-----------------By Nishant Rai
	ArrayList <ActionCodeMasterVo> searchActionCodeData(ActionCodeMasterVo VO);
	boolean insertActionCodeMaster(ActionCodeMasterVo VO);
	boolean updateActionCodeData(ActionCodeMasterVo VO);
	
	//--------------Classification Master-----------------By Nishant Rai
	ArrayList <QueueCodeMasterVo> searchQueueCodeData(QueueCodeMasterVo VO);
	ArrayList<QueueCodeMasterVo> NPAStageList();
	ArrayList<QueueCodeMasterVo> CustCategoryList();
	ArrayList <QueueCodeMasterVo> editQueueCodeData(String queue);
	boolean insertQueueCodeMaster(QueueCodeMasterVo VO);
	boolean updateQueueCodeData(QueueCodeMasterVo VO);
	public ArrayList<QueueCodeMasterVo> ProductList();
	public String checkQueueCodeMaster(QueueCodeMasterVo queueCodeMasterVo);
	public String checkPriority(QueueCodeMasterVo queueCodeMasterVo);
	
	//--------------Allocation Master-----------------By Nishant Rai
	public ArrayList<AllocationMasterVo> getQueueList();
    public String saveQueueAllocation(AllocationMasterVo collVo, String[] percentage,int total,String[] queueuser,String[] branchList) throws SQLException;
    public ArrayList<AllocationMasterVo> searchQueueAllocationData(AllocationMasterVo allocationVo);
    public ArrayList<AllocationMasterVo> allocatedCollQueueData(AllocationMasterVo allocationVo);
    public ArrayList<AllocationMasterVo> searchQueueAllocationEdit(String userId);
    public ArrayList<AllocationMasterVo> searchQueueEdit(String userId);
    public String modifyqueueAllocation(AllocationMasterVo collVo, String[] checkbox,String[] percentagebox,int total) throws SQLException;
	public String calcpercentage(String[] checkbox) throws SQLException;	
	public ArrayList <AllocationMasterVo> searchAllocationData(Object ob);
	ArrayList getresultForBranchAllcation(String allocId);
	public String modifyQueueAllocationDtl(AllocationMasterVo collVo, String[] percentage,int total,String[] queueuser,String[] branchList) throws SQLException;
}
