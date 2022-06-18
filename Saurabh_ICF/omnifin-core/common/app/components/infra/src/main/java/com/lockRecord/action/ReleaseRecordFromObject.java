package com.lockRecord.action;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.servlet.ServletContext;
import org.apache.log4j.Logger;
import com.lockRecord.Vo.LockCheckVo;


public class ReleaseRecordFromObject {
	
	private static final Logger logger = Logger.getLogger(ReleaseRecordFromObject.class.getName());	

	public static boolean releaselockedRecord(String userId,ServletContext context)
	throws SQLException, IOException {
		
		logger.info("In ReleaseRecordFromObject , releaselockedRecord() method........................");
		
	
		boolean flag = false;
		ArrayList recordAccessObject =new ArrayList();
		recordAccessObject = (ArrayList) context.getAttribute("recordAccessObject");
		
	     if(recordAccessObject!=null && recordAccessObject.size()>0)
	     {
	    		for(int i=0;i<recordAccessObject.size();i++)
				{
					LockCheckVo vo = new LockCheckVo();
					vo = (LockCheckVo) recordAccessObject.get(i);
					if((vo.getUserId().equalsIgnoreCase(userId)))
					{

						recordAccessObject.remove(i);
					}
				} 
	     }
	 
		return flag;
		
	}

}
