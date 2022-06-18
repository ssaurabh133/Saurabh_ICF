package com.lockRecord.action;

import java.util.ArrayList;

public class LockSingleton {
	
		private static LockSingleton instance;
		public ArrayList recordAccessObject = new ArrayList(); 
		
		LockSingleton(){}
		
		public static synchronized LockSingleton getInstance()
		{
			
			if(instance==null)
			{
				instance=new LockSingleton();
			}
			return instance;
		}
		
		public Object clone() throws CloneNotSupportedException{
			
		
			 throw new CloneNotSupportedException();
	  }
}
