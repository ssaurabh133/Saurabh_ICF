package com.cp.dao;

import java.util.ArrayList;
import javax.servlet.http.HttpServletRequest;
import com.cp.vo.CommonNotepadVo;

public interface NotepadDAO {
	String IDENTITY="NOTEPADINCP";
	ArrayList getDealListForNotePad(CommonNotepadVo vo,HttpServletRequest request);
	ArrayList getDealHeader(String dealId);
	ArrayList getLeadListForNotePad(CommonNotepadVo vo,HttpServletRequest request) ;
	ArrayList getLeadHeader(String leadId);
}
