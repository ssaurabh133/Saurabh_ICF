package com.lockRecord.action;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.servlet.ServletContext;
import org.apache.log4j.Logger;
import com.connect.CommonFunction;
import com.lockRecord.Vo.*;



public class LockRecordCheck {

	private static final Logger logger = Logger.getLogger(LockRecordCheck.class.getName());	
	public static boolean lockCheck(String userId, String functionId, String recordId,ServletContext context)
	throws SQLException, IOException {
		
		logger.info(" In LockCheck Action, LockCheck() method userId  ...................................... "+userId);
		logger.info(" In LockCheck Action, LockCheck() method functionId  ...................................... "+functionId);
		logger.info("In LockCheck Action, LockCheck() method recordId   ...................................... "+recordId);
		//LockSingleton lockObj;
		boolean flag = false;
		ArrayList recordAccessObject = (ArrayList) context.getAttribute("recordAccessObject");
	   
	    if(recordAccessObject.size()>0)
	    {
	    	for(int i=0;i<recordAccessObject.size();i++)
			{
				logger.info("In LockCheck Action, LockCheck() method  user id exist already...............................");
				LockCheckVo vo = new LockCheckVo();
				vo = (LockCheckVo) recordAccessObject.get(i);
				if((vo.getUserId().equalsIgnoreCase(userId)))
				{
					logger.info("In LockCheck Action, LockCheck() method user id exist...............................");
					recordAccessObject.remove(i);
				}
			}
			for(int i=0;i<recordAccessObject.size();i++)
				{
				logger.info("In LockCheck Action, LockCheck() method search function id and record id should same and user id should different");
				
					LockCheckVo vo = new LockCheckVo();
					vo = (LockCheckVo) recordAccessObject.get(i);
					logger.info(" In LockCheck Action, LockCheck() methodvo.getUserId() *********************** "+vo.getUserId());
					logger.info(" In LockCheck Action, LockCheck() method vo.getFunctionId() *********************** "+vo.getFunctionId());
					logger.info("In LockCheck Action, LockCheck() method vo.getRecordId() *********************** "+vo.getRecordId());
					if(!(vo.getUserId()!=null && CommonFunction.checkNull(vo.getUserId()).equalsIgnoreCase(userId)) && (vo.getFunctionId()!=null && CommonFunction.checkNull(vo.getFunctionId()).equalsIgnoreCase(functionId)) && (vo.getRecordId()!=null && CommonFunction.checkNull(vo.getRecordId()).equalsIgnoreCase(recordId)))
					{
						flag = false;
					}
					else
					{
						flag =true;
					}
				}
	    }
		
		
		if(flag || recordAccessObject.size()<=0)
		{
			logger.info("In LockCheck Action, LockCheck() method User Not Exist ........ "+flag);
			LockCheckVo vo = new LockCheckVo();
			vo.setUserId(userId);
			vo.setFunctionId(functionId);
			vo.setRecordId(recordId);
			recordAccessObject.add(vo);
			flag=true;
		}
		logger.info(" In LockCheck Action, LockCheck() method recordAccessObject size id ........................ "+recordAccessObject.size());
		return flag;
		
	}

	
}
