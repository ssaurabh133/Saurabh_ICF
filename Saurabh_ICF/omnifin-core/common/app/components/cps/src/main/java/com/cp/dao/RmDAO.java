package com.cp.dao;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import com.cp.vo.RmChangeVo;

public interface RmDAO {
	String IDENTITY="RMD"; 
	ArrayList<RmChangeVo> searchRmData(Object ob);
	ArrayList<RmChangeVo> searchRmMakerAuthor(Object ob, String recStatus);
	boolean insertRmChangeData(Object ob, String[] checkbox);
	boolean forwardRM(Object ob, String[] checkbox);
	boolean saveRmChangeAuthor(Object ob, String[] checkbox);
}
