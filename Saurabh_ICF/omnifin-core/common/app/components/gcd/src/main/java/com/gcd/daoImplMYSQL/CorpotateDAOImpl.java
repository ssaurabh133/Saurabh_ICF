package com.gcd.daoImplMYSQL;

import com.VO.ConstitutionVO;
import com.VO.CorporateBusinessActivityVO;
import com.VO.CreditRatingGridVo;
import com.VO.CreditRatingVo;
import com.VO.CustomerCategoryVO;
import com.VO.CustomerSaveVo;
import com.VO.DeatilOfCustomerAddress;
import com.VO.IndustryVO;
import com.VO.InstitutionNameVo;
import com.VO.RegistrationTypeVO;
import com.VO.StakeHolderTypeVo;
import com.VO.StakeHolderVo;
import com.VO.StakePositionVo;
import com.connect.CommonFunction;
import com.connect.ConnectionDAO;
import com.connect.PrepStmtObject;
import com.cp.vo.CpInsuranceVo;
import com.cp.vo.CreditProcessingCustomerEntryVo;
import com.cp.vo.LeaddetailDealVo;
import com.gcd.VO.CorporateDetailsVO;
import com.gcd.VO.ShowCustomerDetailVo;
import com.gcd.dao.CorporateDAO;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.ResourceBundle;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.log4j.Logger;

public class CorpotateDAOImpl
  implements CorporateDAO
{
  private static final Logger logger = Logger.getLogger(CorpotateDAOImpl.class.getName());
  ResourceBundle resource = ResourceBundle.getBundle("com.yourcompany.struts.ApplicationResources");
  String dateFormatWithTime = this.resource.getString("lbl.dateWithTimeInDao");
  String dateFormat = this.resource.getString("lbl.dateInDao");
  DecimalFormat myFormatter = new DecimalFormat("###,##0.00######");
  int no = Integer.parseInt(this.resource.getString("msg.pageSizeForMaster"));

  Statement stmt = null;
  PreparedStatement pstmt = null;
  CallableStatement cstm = null;
  ResultSet rs = null;

  public ArrayList<Object> getCastCategoryList()
  {
    logger.info("in getCastCategoryList() of CorpotateDAOImpl. ");
    ArrayList list = new ArrayList();
    try
    {
      String query = "SELECT VALUE,DESCRIPTION FROM  generic_master WHERE GENERIC_KEY='CAST_CATEGORY' and rec_status ='A'";
      logger.info(new StringBuilder().append("in getCastCategoryList() of CorpotateDAOImpl Query :  ").append(query).toString());
      CustomerCategoryVO vo = null;
      ArrayList source = ConnectionDAO.sqlSelect(query);
      logger.info(new StringBuilder().append(" in getCastCategoryList() size  : ").append(source.size()).toString());
      int size = source.size();
      for (int i = 0; i < size; i++)
      {
        ArrayList subCategory = (ArrayList)source.get(i);
        if (subCategory.size() > 0)
        {
          vo = new CustomerCategoryVO();
          vo.setCastCategoryCode(CommonFunction.checkNull(subCategory.get(0)).toString());
          vo.setCastCategoryDesc(CommonFunction.checkNull(subCategory.get(1)).toString());
          list.add(vo);
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    return list;
  }

  public ArrayList<Object> getGenderList()
  {
    logger.info("in getGenderList() method***********************");
    ArrayList list = new ArrayList();
    try
    {
      String query = "select VALUE,DESCRIPTION  from GENERIC_MASTER WHERE GENERIC_KEY='GENDER_INDIVIDUAL' and REC_STATUS ='A' ";
      logger.info(new StringBuilder().append("query for gener:::::::::").append(query).toString());
      ArrayList gender = ConnectionDAO.sqlSelect(query);
      CustomerCategoryVO vo = null;
      logger.info(new StringBuilder().append("sizew of gender list:::::::").append(gender.size()).toString());
      int size = gender.size();
      for (int i = 0; i < size; i++)
      {
        ArrayList gender1 = (ArrayList)gender.get(i);
        if (gender1.size() > 0)
        {
          vo = new CustomerCategoryVO();
          vo.setGenderCode(CommonFunction.checkNull(gender1.get(0)).toString());
          vo.setGenderDesc(CommonFunction.checkNull(gender1.get(1)).toString());
          list.add(vo);
        }
      }
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }

    return list;
  }

  public ArrayList<Object> getCustomerCategoryList()
  {
    logger.info("in getCustomerCategoryList() of CorpotateDAOImpl. ");
    ArrayList list = new ArrayList();
    try
    {
      String query = "SELECT VALUE,DESCRIPTION FROM  generic_master WHERE GENERIC_KEY='CUST_CATEGORY' and rec_status ='A'";
      logger.info(new StringBuilder().append("in getCustomerCategoryList() of CorpotateDAOImpl Query :  ").append(query).toString());
      CustomerCategoryVO vo = null;
      ArrayList source = ConnectionDAO.sqlSelect(query);
      logger.info(new StringBuilder().append("getCustomerCategoryList").append(source.size()).toString());
      int size = source.size();
      for (int i = 0; i < size; i++)
      {
        ArrayList subCategory = (ArrayList)source.get(i);
        if (subCategory.size() > 0)
        {
          vo = new CustomerCategoryVO();
          vo.setCustomerCategoryCode(CommonFunction.checkNull(subCategory.get(0)).toString());
          vo.setCustomerCategoryDesc(CommonFunction.checkNull(subCategory.get(1)).toString());
          list.add(vo);
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    return list;
  }

  public ArrayList<Object> getEduDetail()
  {
    logger.info("in getEduDetail() of CorpotateDAOImpl. ");
    ArrayList list = new ArrayList();
    try
    {
      String query = "select value,description from generic_master where GENERIC_KEY='EDU_DETAIL' and rec_status ='A'";
      logger.info(new StringBuilder().append("in getEduDetail() of CorpotateDAOImpl Query :  ").append(query).toString());
      CustomerCategoryVO vo = null;
      ArrayList source = ConnectionDAO.sqlSelect(query);
      logger.info(new StringBuilder().append("getEduDetail").append(source.size()).toString());
      int size = source.size();
      for (int i = 0; i < size; i++)
      {
        ArrayList subEduDetail = (ArrayList)source.get(i);
        if (subEduDetail.size() > 0)
        {
          vo = new CustomerCategoryVO();
          vo.setEduDetailCode(CommonFunction.checkNull(subEduDetail.get(0)).toString());
          vo.setEduDetailDesc(CommonFunction.checkNull(subEduDetail.get(1)).toString());
          list.add(vo);
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    return list;
  }

  public ArrayList<Object> getBusinessSegmentList()
  {
    ArrayList list = new ArrayList();
    try
    {
      String query = "select value,description from generic_master where GENERIC_KEY='CUST_BUS_SEGMENT' and rec_status ='A'";
      logger.info(new StringBuilder().append("in getBusinessSegmentList() of CorpotateDAOImpl Query :  ").append(query).toString());
      RegistrationTypeVO vo = null;
      ArrayList source = ConnectionDAO.sqlSelect(query);
      logger.info(new StringBuilder().append("getBusinessSegmentList").append(source.size()).toString());
      for (int i = 0; i < source.size(); i++)
      {
        ArrayList subRegistration = (ArrayList)source.get(i);
        if (subRegistration.size() > 0)
        {
          vo = new RegistrationTypeVO();
          vo.setBusinessSegmentCode(CommonFunction.checkNull(subRegistration.get(0)).toString());
          vo.setBusinessSegmentDesc(CommonFunction.checkNull(subRegistration.get(1)).toString());
          list.add(vo);
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    return list;
  }

  public ArrayList<Object> getNatureOfBusinessList()
  {
    ArrayList list = new ArrayList();
    try
    {
      String query = "select value,description from generic_master where GENERIC_KEY='NATURE_OF_BUSINESS' and rec_status ='A'";
      logger.info(new StringBuilder().append("in getNatureOfBusinessList() of CorpotateDAOImpl Query :  ").append(query).toString());
      RegistrationTypeVO vo = null;
      ArrayList source = ConnectionDAO.sqlSelect(query);
      logger.info(new StringBuilder().append("getNatureOfBusinessList ").append(source.size()).toString());
      for (int i = 0; i < source.size(); i++)
      {
        ArrayList subRegistration = (ArrayList)source.get(i);
        if (subRegistration.size() > 0)
        {
          vo = new RegistrationTypeVO();
          vo.setNatureOfBusCode(CommonFunction.checkNull(subRegistration.get(0)).toString());
          vo.setNatureOfBusDesc(CommonFunction.checkNull(subRegistration.get(1)).toString());
          list.add(vo);
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    return list;
  }

  public ArrayList<Object> getContitutionList()
  {
    ArrayList list = new ArrayList();
    try
    {
      String query = "SELECT VALUE,DESCRIPTION FROM  generic_master WHERE GENERIC_KEY='CUST_CONSTITUTION'  and PARENT_VALUE='CORP' and rec_status ='A'";
      logger.info(new StringBuilder().append("in getContitutionList() of CorpotateDAOImpl Query :  ").append(query).toString());
      ConstitutionVO vo = null;
      ArrayList source = ConnectionDAO.sqlSelect(query);
      logger.info(new StringBuilder().append("getCustomerCategoryList").append(source.size()).toString());
      for (int i = 0; i < source.size(); i++)
      {
        ArrayList subConstitution = (ArrayList)source.get(i);
        if (subConstitution.size() > 0)
        {
          vo = new ConstitutionVO();
          vo.setContitutionCode(CommonFunction.checkNull(subConstitution.get(0)).toString());
          vo.setConstitution(CommonFunction.checkNull(subConstitution.get(1)).toString());
          list.add(vo);
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    return list;
  }

  public ArrayList<Object> getRegistrationTypeList()
  {
    ArrayList list = new ArrayList();
    try
    {
      String query = "SELECT VALUE,DESCRIPTION FROM  generic_master WHERE GENERIC_KEY='CUST_REG_TYPE' and rec_status ='A'";
      logger.info(new StringBuilder().append("in getRegistrationTypeList() of CorpotateDAOImpl Query :  ").append(query).toString());
      RegistrationTypeVO vo = null;
      ArrayList source = ConnectionDAO.sqlSelect(query);
      logger.info(new StringBuilder().append("getRegistrationTypeList").append(source.size()).toString());
      for (int i = 0; i < source.size(); i++)
      {
        ArrayList subRegistration = (ArrayList)source.get(i);
        if (subRegistration.size() > 0)
        {
          vo = new RegistrationTypeVO();
          vo.setRegistrationTypeCode(CommonFunction.checkNull(subRegistration.get(0)).toString());
          vo.setRegistrationTypeDesc(CommonFunction.checkNull(subRegistration.get(1)).toString());
          list.add(vo);
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    return list;
  }

  public ArrayList<Object> getAddressList()
  {
    ArrayList list = new ArrayList();
    try
    {
      String query = "select  VALUE,DESCRIPTION from generic_master where GENERIC_KEY='ADDRESS_TYPE' and rec_status ='A'";
      logger.info(new StringBuilder().append("getAddressList").append(query).toString());
      RegistrationTypeVO vo = null;
      ArrayList source = ConnectionDAO.sqlSelect(query);
      logger.info(new StringBuilder().append("getAddressList").append(source.size()).toString());
      for (int i = 0; i < source.size(); i++)
      {
        ArrayList subAddress = (ArrayList)source.get(i);
        if (subAddress.size() > 0)
        {
          vo = new RegistrationTypeVO();
          vo.setRegistrationTypeCode(CommonFunction.checkNull(subAddress.get(0)).toString());
          vo.setRegistrationTypeDesc(CommonFunction.checkNull(subAddress.get(1)).toString());
          list.add(vo);
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    return list;
  }

  public ArrayList<Object> getCountryList()
  {
    ArrayList list = new ArrayList();
    try
    {
      String query = "select COUNTRY_ID,COUNTRY_DESC from com_country_m";
      logger.info(new StringBuilder().append("getCountryList").append(query).toString());
      RegistrationTypeVO vo = null;
      ArrayList source = ConnectionDAO.sqlSelect(query);
      logger.info(new StringBuilder().append("getCountryList").append(source.size()).toString());
      for (int i = 0; i < source.size(); i++)
      {
        ArrayList subCountry = (ArrayList)source.get(i);
        if (subCountry.size() > 0)
        {
          vo = new RegistrationTypeVO();
          vo.setRegistrationTypeCode(CommonFunction.checkNull(subCountry.get(0)).toString());
          vo.setRegistrationTypeDesc(CommonFunction.checkNull(subCountry.get(1)).toString());
          list.add(vo);
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    return list;
  }

  public ArrayList<Object> getDetailAddressList(String addr)
  {
    ArrayList list = new ArrayList();
    try
    {
      String query = "select  ADDRESS_LINE1,ADDRESS_LINE2,ADDRESS_LINE3 from com_address_m where ADDRESS_TYPE=?";
      logger.info(new StringBuilder().append("getDetailAddressList").append(query).toString());
      DeatilOfCustomerAddress vo = null;
      ArrayList source = ConnectionDAO.sqlSelect(query);
      logger.info(new StringBuilder().append("getDetailAddressList").append(source.size()).toString());
      for (int i = 0; i < source.size(); i++)
      {
        ArrayList subDetailAddress = (ArrayList)source.get(i);
        if (subDetailAddress.size() > 0)
        {
          vo = new DeatilOfCustomerAddress();
          vo.setAddr1(CommonFunction.checkNull(subDetailAddress.get(0)).toString());
          vo.setAddr2(CommonFunction.checkNull(subDetailAddress.get(1)).toString());
          vo.setAddr3(CommonFunction.checkNull(subDetailAddress.get(2)).toString());
          list.add(vo);
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    return list;
  }

  public ArrayList<Object> getCountryDetail(String country)
  {
    ArrayList list = new ArrayList();
    try
    {
      String query = new StringBuilder().append("select STATE_ID,STATE_DESC from com_state_m where COUNTRY_ID=").append(country).toString();
      logger.info(new StringBuilder().append("getCountryList  ...  ").append(query).toString());
      DeatilOfCustomerAddress vo = null;
      ArrayList source = ConnectionDAO.sqlSelect(query);
      logger.info(new StringBuilder().append("getCountryList").append(source.size()).toString());
      for (int i = 0; i < source.size(); i++)
      {
        ArrayList subCountry = (ArrayList)source.get(i);
        if (subCountry.size() > 0)
        {
          vo = new DeatilOfCustomerAddress();
          vo.setState_code(CommonFunction.checkNull(subCountry.get(0)).toString());
          vo.setState_name(CommonFunction.checkNull(subCountry.get(1)).toString());
          list.add(vo);
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    return list;
  }

  public ArrayList<Object> getCityDetail(String country, String state)
  {
    ArrayList list = new ArrayList();
    try
    {
      String query = new StringBuilder().append("select DISTRICT_ID,DISTRICT_DESC from com_district_m where STATE_ID=").append(state).toString();
      logger.info(new StringBuilder().append("getCityDetail").append(query).toString());
      DeatilOfCustomerAddress vo = null;
      ArrayList source = ConnectionDAO.sqlSelect(query);
      logger.info(new StringBuilder().append("getCityDetail").append(source.size()).toString());
      for (int i = 0; i < source.size(); i++)
      {
        ArrayList subCity = (ArrayList)source.get(i);
        if (subCity.size() > 0)
        {
          vo = new DeatilOfCustomerAddress();
          vo.setDist_code(CommonFunction.checkNull(subCity.get(0)).toString());
          vo.setDist_name(CommonFunction.checkNull(subCity.get(1)).toString());
          list.add(vo);
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    return list;
  }

  public boolean saveCustomerAddress(Object ob)
  {
    logger.info("In saveCustomerAddress() of CorpotateDAOImpl.");
    CustomerSaveVo vo = (CustomerSaveVo)ob;
    boolean status = false;
    ArrayList qryList = new ArrayList();
    PrepStmtObject insertPrepStmtObject = new PrepStmtObject();
    String source = vo.getSource();
    String customerId = "";
    if (!CommonFunction.checkNull(vo.getBp_id()).trim().equalsIgnoreCase(""))
      customerId = vo.getBp_id();
    if (!CommonFunction.checkNull(vo.getBp_id1()).trim().equalsIgnoreCase("")) {
      customerId = vo.getBp_id1();
    }
    try
    {
      if (((vo.getPageStatus() != null) && (vo.getPageStatus().equals("fromLeads"))) || ((vo.getUpdateFlag() != null) && (vo.getUpdateFlag().length() > 0)))
      {
        StringBuffer bufInsSql = new StringBuffer();
        bufInsSql.append("insert into cr_deal_address_m(ADDRESS_TYPE,BPTYPE,BPID,ADDRESS_LINE1,ADDRESS_LINE2,ADDRESS_LINE3,COUNTRY,STATE,DISTRICT,PINCODE,PRIMARY_PHONE,ALTERNATE_PHONE,TOLLFREE_NUMBER,FAX,LANDMARK,NO_OF_YEARS,COMMUNICATION_ADDRESS,ADDRESS_DETAIL,NO_OF_MONTHS,REC_STATUS,MAKER_ID,MAKER_DATE,AUTHOR_ID,AUTHOR_DATE,TAHSIL,BRANCH_DISTANCE,GST_NO,STD_NO,RELATIONSHIP_FLAG)");
        bufInsSql.append(" values ( ");
        bufInsSql.append(" ?,");
        bufInsSql.append(" ?,");
        bufInsSql.append(" ?,");
        bufInsSql.append(" ?,");
        bufInsSql.append(" ?,");
        bufInsSql.append(" ?,");
        bufInsSql.append(" ?,");
        bufInsSql.append(" ?,");
        bufInsSql.append(" ?,");
        bufInsSql.append(" ?,");
        bufInsSql.append(" ?,");
        bufInsSql.append(" ?,");
        bufInsSql.append(" ?,");
        bufInsSql.append(" ?,");
        bufInsSql.append(" ?,");
        bufInsSql.append(" ?,");
        bufInsSql.append(" ?,");
        bufInsSql.append(" ?,");
        bufInsSql.append(" ?,");
        bufInsSql.append(" ?,");
        bufInsSql.append(" ?,");
        bufInsSql.append(new StringBuilder().append(" DATE_ADD(STR_TO_DATE(?, '").append(this.dateFormatWithTime).append("'),INTERVAL CURTIME() HOUR_SECOND), ").toString());
        bufInsSql.append(" ?,");
        bufInsSql.append(new StringBuilder().append(" DATE_ADD(STR_TO_DATE(?, '").append(this.dateFormatWithTime).append("'),INTERVAL CURTIME() HOUR_SECOND),").toString());
        bufInsSql.append(" ?,");
        bufInsSql.append(" ? ,");
        bufInsSql.append(" ? ,");
        bufInsSql.append(" ? ,");
        bufInsSql.append(" ? )");

        if (CommonFunction.checkNull(vo.getAddr_type()).trim().equalsIgnoreCase(""))
          insertPrepStmtObject.addNull();
        else {
          insertPrepStmtObject.addString(vo.getAddr_type().trim());
        }
        if (CommonFunction.checkNull(vo.getBp_type()).trim().equalsIgnoreCase(""))
          insertPrepStmtObject.addNull();
        else {
          insertPrepStmtObject.addString(vo.getBp_type().trim());
        }
        if (CommonFunction.checkNull(vo.getBp_id()).trim().equalsIgnoreCase(""))
          insertPrepStmtObject.addString(vo.getBp_id1().trim());
        else {
          insertPrepStmtObject.addString(vo.getBp_id().trim());
        }
        if (CommonFunction.checkNull(vo.getAddr1()).trim().equalsIgnoreCase(""))
          insertPrepStmtObject.addNull();
        else {
          insertPrepStmtObject.addString(vo.getAddr1().trim());
        }
        if (CommonFunction.checkNull(vo.getAddr2()).trim().equalsIgnoreCase(""))
          insertPrepStmtObject.addNull();
        else {
          insertPrepStmtObject.addString(vo.getAddr2().trim());
        }
        if (CommonFunction.checkNull(vo.getAddr3()).trim().equalsIgnoreCase(""))
          insertPrepStmtObject.addNull();
        else {
          insertPrepStmtObject.addString(vo.getAddr3().trim());
        }
        if (CommonFunction.checkNull(vo.getTxtCountryCode()).trim().equalsIgnoreCase(""))
          insertPrepStmtObject.addNull();
        else {
          insertPrepStmtObject.addString(vo.getTxtCountryCode().trim());
        }
        if (CommonFunction.checkNull(vo.getTxtStateCode()).trim().equalsIgnoreCase(""))
          insertPrepStmtObject.addNull();
        else {
          insertPrepStmtObject.addString(vo.getTxtStateCode().trim());
        }
        if (CommonFunction.checkNull(vo.getTxtDistCode()).trim().equalsIgnoreCase(""))
          insertPrepStmtObject.addNull();
        else {
          insertPrepStmtObject.addString(vo.getTxtDistCode().trim());
        }
        if (CommonFunction.checkNull(vo.getPincode()).trim().equalsIgnoreCase(""))
          insertPrepStmtObject.addNull();
        else {
          insertPrepStmtObject.addString(vo.getPincode().trim());
        }
        if (CommonFunction.checkNull(vo.getPrimaryPhoneNo()).trim().equalsIgnoreCase(""))
          insertPrepStmtObject.addNull();
        else {
          insertPrepStmtObject.addString(vo.getPrimaryPhoneNo().trim());
        }
        if (CommonFunction.checkNull(vo.getAlternatePhoneNo()).trim().equalsIgnoreCase(""))
          insertPrepStmtObject.addNull();
        else {
          insertPrepStmtObject.addString(vo.getAlternatePhoneNo().trim());
        }
        if (CommonFunction.checkNull(vo.getTollfreeNo()).trim().equalsIgnoreCase(""))
          insertPrepStmtObject.addNull();
        else {
          insertPrepStmtObject.addString(vo.getTollfreeNo().trim());
        }
        if (CommonFunction.checkNull(vo.getFaxNo()).trim().equalsIgnoreCase(""))
          insertPrepStmtObject.addNull();
        else {
          insertPrepStmtObject.addString(vo.getFaxNo().trim());
        }
        if (CommonFunction.checkNull(vo.getLandMark()).trim().equalsIgnoreCase(""))
          insertPrepStmtObject.addNull();
        else {
          insertPrepStmtObject.addString(vo.getLandMark().trim());
        }
        if (CommonFunction.checkNull(vo.getNoYears()).trim().equalsIgnoreCase(""))
          insertPrepStmtObject.addNull();
        else {
          insertPrepStmtObject.addString(vo.getNoYears().trim());
        }
        if (CommonFunction.checkNull(vo.getCommunicationAddress()).trim().equalsIgnoreCase(""))
          insertPrepStmtObject.addNull();
        else {
          insertPrepStmtObject.addString(vo.getCommunicationAddress().trim());
        }
        if (CommonFunction.checkNull(vo.getAddDetails()).trim().equalsIgnoreCase(""))
          insertPrepStmtObject.addNull();
        else {
          insertPrepStmtObject.addString(vo.getAddDetails().trim());
        }
        if (CommonFunction.checkNull(vo.getNoMonths()).trim().equalsIgnoreCase(""))
          insertPrepStmtObject.addNull();
        else {
          insertPrepStmtObject.addString(vo.getNoMonths().trim());
        }
        insertPrepStmtObject.addString("A");

        if (CommonFunction.checkNull(vo.getMakerId()).trim().equalsIgnoreCase(""))
          insertPrepStmtObject.addNull();
        else
          insertPrepStmtObject.addString(vo.getMakerId().trim());
        if (CommonFunction.checkNull(vo.getMakerDate()).trim().equalsIgnoreCase(""))
          insertPrepStmtObject.addNull();
        else {
          insertPrepStmtObject.addString(CommonFunction.checkNull(vo.getMakerDate()).trim());
        }
        if (CommonFunction.checkNull(vo.getMakerId()).trim().equalsIgnoreCase(""))
          insertPrepStmtObject.addNull();
        else
          insertPrepStmtObject.addString(vo.getMakerId().trim());
        if (CommonFunction.checkNull(vo.getMakerDate()).trim().equalsIgnoreCase(""))
          insertPrepStmtObject.addNull();
        else {
          insertPrepStmtObject.addString(CommonFunction.checkNull(vo.getMakerDate()).trim());
        }
        if (CommonFunction.checkNull(vo.getTahsil()).trim().equalsIgnoreCase(""))
          insertPrepStmtObject.addNull();
        else {
          insertPrepStmtObject.addString(CommonFunction.checkNull(vo.getTahsil()).trim());
        }
        if (CommonFunction.checkNull(vo.getDistanceBranch()).trim().equalsIgnoreCase(""))
          insertPrepStmtObject.addNull();
        else
          insertPrepStmtObject.addString(new StringBuilder().append("").append(this.myFormatter.parse(CommonFunction.checkNull(vo.getDistanceBranch()).trim())).toString());
        if (CommonFunction.checkNull(vo.getGstIn()).trim().equalsIgnoreCase(""))
          insertPrepStmtObject.addNull();
        else {
          insertPrepStmtObject.addString(CommonFunction.checkNull(vo.getGstIn()).trim());
        }
        if (CommonFunction.checkNull(vo.getStdNo()).trim().equalsIgnoreCase(""))
            insertPrepStmtObject.addNull();
          else {
            insertPrepStmtObject.addString(CommonFunction.checkNull(vo.getStdNo()).trim());
          }
        if (CommonFunction.checkNull(vo.getRelation()).trim().equalsIgnoreCase(""))
            insertPrepStmtObject.addNull();
          else {
            insertPrepStmtObject.addString(CommonFunction.checkNull(vo.getRelation()).trim());
          }
        insertPrepStmtObject.setSql(bufInsSql.toString());
        logger.info(new StringBuilder().append("IN saveCustomerAddress() of CorpotateDAOImpl  insert query1 ### ").append(insertPrepStmtObject.printQuery()).toString());
        qryList.add(insertPrepStmtObject);
        status = ConnectionDAO.sqlInsUpdDeletePrepStmt(qryList);
        logger.info(new StringBuilder().append("In saveCustomer......................").append(status).toString());

        String countQuery = new StringBuilder().append("select count(1) from cr_deal_movement_dtl where deal_id=").append(CommonFunction.checkNull(vo.getDealId())).append(" and DEAL_STAGE_ID='CBL' and rec_status<>'X'").toString();
        logger.info(new StringBuilder().append("IN saveCorporateDetails() of CorpotateDAOImpl  countQuery  :").append(countQuery).toString());
        String countQueryFlag = ConnectionDAO.singleReturn(countQuery);

        if ((CommonFunction.checkNull(countQueryFlag).trim().equalsIgnoreCase("0")) && (status))
        {
          ArrayList qryListMovement = new ArrayList();
          StringBuffer bufInsSqlMovement = new StringBuffer();
          PrepStmtObject insertPrepStmtObject3 = new PrepStmtObject();
          bufInsSqlMovement.append("insert into cr_deal_movement_dtl (DEAL_ID,DEAL_STAGE_ID,DEAL_ACTION,DEAL_RECEIVED,DEAL_FORWARDED,DEAL_RECEIVED_USER  ,DEAL_FORWARD_USER , REC_STATUS)values");
          bufInsSqlMovement.append("(?,'CBL','I',");
          bufInsSqlMovement.append(new StringBuilder().append(" DATE_ADD(STR_TO_DATE(?, '").append(this.dateFormatWithTime).append("'),INTERVAL CURTIME() HOUR_SECOND) , ").toString());
          bufInsSqlMovement.append(" '0000-00-00 00:00:00' , ");
          bufInsSqlMovement.append(" ?,'','A')");
          if (CommonFunction.checkNull(vo.getDealId()).trim().equalsIgnoreCase(""))
            insertPrepStmtObject3.addNull();
          else
            insertPrepStmtObject3.addString(vo.getDealId().trim());
          if (CommonFunction.checkNull(vo.getMakerDate()).trim().equalsIgnoreCase(""))
            insertPrepStmtObject3.addNull();
          else {
            insertPrepStmtObject3.addString(vo.getMakerDate().trim());
          }
          if (CommonFunction.checkNull(vo.getMakerId()).trim().equalsIgnoreCase(""))
            insertPrepStmtObject3.addNull();
          else {
            insertPrepStmtObject3.addString(vo.getMakerId().trim());
          }

          insertPrepStmtObject3.setSql(bufInsSqlMovement.toString());
          logger.info(new StringBuilder().append("IN Applicant insert query1 : ").append(insertPrepStmtObject3.printQuery()).toString());
          qryListMovement.add(insertPrepStmtObject3);
          status = ConnectionDAO.sqlInsUpdDeletePrepStmt(qryListMovement);
        }

        saveCustomerAddress1(ob);
      }
      else
      {
        int maxId = 0;
        StringBuffer bufInsSql = new StringBuffer();
        int checkTable = 0;
        if (!vo.getStatusCase().equals(""))
        {
          String query3 = "Select max(ADDRESS_ID) from com_address_m_temp for update";
          String id = ConnectionDAO.singleReturn(query3);
          logger.info(new StringBuilder().append("id: ").append(id).toString());
          if (CommonFunction.checkNull(id).equalsIgnoreCase(""))
            maxId = 1;
          else
            maxId = Integer.parseInt(id) + 1;
          checkTable++;
          bufInsSql.append("insert into com_address_m_temp(ADDRESS_ID,ADDRESS_TYPE,BPTYPE,BPID,ADDRESS_LINE1,ADDRESS_LINE2,ADDRESS_LINE3,COUNTRY,STATE,DISTRICT,PINCODE,PRIMARY_PHONE,ALTERNATE_PHONE,TOLLFREE_NUMBER,FAX,LANDMARK,NO_OF_YEARS,COMMUNICATION_ADDRESS,ADDRESS_DETAIL,NO_OF_MONTHS,TAHSIL,BRANCH_DISTANCE,GST_NO,STD_NO,RELATIONSHIP_FLAG)");
          bufInsSql.append(" values ( ");
          bufInsSql.append(" ?,");
        }
        else
        {
          bufInsSql.append("insert into com_address_m(ADDRESS_TYPE,BPTYPE,BPID,ADDRESS_LINE1,ADDRESS_LINE2,ADDRESS_LINE3,COUNTRY,STATE,DISTRICT,PINCODE,PRIMARY_PHONE,ALTERNATE_PHONE,TOLLFREE_NUMBER,FAX,LANDMARK,NO_OF_YEARS,COMMUNICATION_ADDRESS,ADDRESS_DETAIL,NO_OF_MONTHS,MAKER_ID,MAKER_DATE,AUTHOR_ID,AUTHOR_DATE,TAHSIL,BRANCH_DISTANCE,GST_NO,STD_NO,RELATIONSHIP_FLAG)");
          bufInsSql.append(" values ( ");
        }
        bufInsSql.append(" ?,");
        bufInsSql.append(" ?,");
        bufInsSql.append(" ?,");
        bufInsSql.append(" ?,");
        bufInsSql.append(" ?,");
        bufInsSql.append(" ?,");
        bufInsSql.append(" ?,");
        bufInsSql.append(" ?,");
        bufInsSql.append(" ?,");
        bufInsSql.append(" ?,");
        bufInsSql.append(" ?,");
        bufInsSql.append(" ?,");
        bufInsSql.append(" ?,");
        bufInsSql.append(" ?,");
        bufInsSql.append(" ?,");
        bufInsSql.append(" ?,");
        bufInsSql.append(" ?,");
        bufInsSql.append(" ?,");

        if (checkTable == 0)
        {
          bufInsSql.append(" ?,");
          bufInsSql.append(" ?,");
          bufInsSql.append(new StringBuilder().append(" DATE_ADD(STR_TO_DATE(?, '").append(this.dateFormatWithTime).append("'),INTERVAL CURTIME() HOUR_SECOND), ").toString());
          bufInsSql.append(" ?,");
          bufInsSql.append(new StringBuilder().append(" DATE_ADD(STR_TO_DATE(?, '").append(this.dateFormatWithTime).append("'),INTERVAL CURTIME() HOUR_SECOND),").toString());
        }
        else
        {
          bufInsSql.append(" ? ,");
        }
        bufInsSql.append(" ?,");
        bufInsSql.append(" ?,");
        bufInsSql.append(" ?,");
        bufInsSql.append(" ?,");
        bufInsSql.append(" ? )");

        if (!vo.getStatusCase().equals(""))
        {
          insertPrepStmtObject.addInt(maxId);
        }

        if (CommonFunction.checkNull(vo.getAddr_type()).trim().equalsIgnoreCase(""))
          insertPrepStmtObject.addNull();
        else {
          insertPrepStmtObject.addString(vo.getAddr_type().trim());
        }
        if (CommonFunction.checkNull(vo.getBp_type()).trim().equalsIgnoreCase(""))
          insertPrepStmtObject.addNull();
        else {
          insertPrepStmtObject.addString(vo.getBp_type().trim());
        }
        insertPrepStmtObject.addString(customerId);

        if (CommonFunction.checkNull(vo.getAddr1()).trim().equalsIgnoreCase(""))
          insertPrepStmtObject.addNull();
        else {
          insertPrepStmtObject.addString(vo.getAddr1().trim());
        }
        if (CommonFunction.checkNull(vo.getAddr2()).trim().equalsIgnoreCase(""))
          insertPrepStmtObject.addNull();
        else {
          insertPrepStmtObject.addString(vo.getAddr2().trim());
        }
        if (CommonFunction.checkNull(vo.getAddr3()).trim().equalsIgnoreCase(""))
          insertPrepStmtObject.addNull();
        else {
          insertPrepStmtObject.addString(vo.getAddr3().trim());
        }
        if (CommonFunction.checkNull(vo.getTxtCountryCode()).trim().equalsIgnoreCase(""))
          insertPrepStmtObject.addNull();
        else {
          insertPrepStmtObject.addString(vo.getTxtCountryCode().trim());
        }
        if (CommonFunction.checkNull(vo.getTxtStateCode()).trim().equalsIgnoreCase(""))
          insertPrepStmtObject.addNull();
        else {
          insertPrepStmtObject.addString(vo.getTxtStateCode().trim());
        }
        if (CommonFunction.checkNull(vo.getTxtDistCode()).trim().equalsIgnoreCase(""))
          insertPrepStmtObject.addNull();
        else {
          insertPrepStmtObject.addString(vo.getTxtDistCode().trim());
        }
        if (CommonFunction.checkNull(vo.getPincode()).trim().equalsIgnoreCase(""))
          insertPrepStmtObject.addNull();
        else {
          insertPrepStmtObject.addString(vo.getPincode().trim());
        }
        if (CommonFunction.checkNull(vo.getPrimaryPhoneNo().trim()).equalsIgnoreCase(""))
          insertPrepStmtObject.addNull();
        else {
          insertPrepStmtObject.addString(vo.getPrimaryPhoneNo().trim());
        }
        if (CommonFunction.checkNull(vo.getAlternatePhoneNo().trim()).equalsIgnoreCase(""))
          insertPrepStmtObject.addNull();
        else {
          insertPrepStmtObject.addString(vo.getAlternatePhoneNo().trim());
        }
        if (CommonFunction.checkNull(vo.getTollfreeNo()).trim().equalsIgnoreCase(""))
          insertPrepStmtObject.addNull();
        else {
          insertPrepStmtObject.addString(vo.getTollfreeNo().trim());
        }
        if (CommonFunction.checkNull(vo.getFaxNo()).trim().equalsIgnoreCase(""))
          insertPrepStmtObject.addNull();
        else {
          insertPrepStmtObject.addString(vo.getFaxNo().trim());
        }
        if (CommonFunction.checkNull(vo.getLandMark()).trim().equalsIgnoreCase(""))
          insertPrepStmtObject.addNull();
        else {
          insertPrepStmtObject.addString(vo.getLandMark().trim());
        }
        if (CommonFunction.checkNull(vo.getNoYears()).trim().equalsIgnoreCase(""))
          insertPrepStmtObject.addNull();
        else {
          insertPrepStmtObject.addString(vo.getNoYears().trim());
        }
        if (CommonFunction.checkNull(vo.getCommunicationAddress()).trim().equalsIgnoreCase(""))
          insertPrepStmtObject.addNull();
        else {
          insertPrepStmtObject.addString(vo.getCommunicationAddress().trim());
        }
        if (CommonFunction.checkNull(vo.getAddDetails()).trim().equalsIgnoreCase(""))
          insertPrepStmtObject.addNull();
        else {
          insertPrepStmtObject.addString(vo.getAddDetails().trim());
        }
        if (CommonFunction.checkNull(vo.getNoMonths()).trim().equalsIgnoreCase(""))
          insertPrepStmtObject.addNull();
        else {
          insertPrepStmtObject.addString(vo.getNoMonths().trim());
        }

        if (checkTable == 0)
        {
          if (CommonFunction.checkNull(vo.getMakerId()).trim().equalsIgnoreCase(""))
            insertPrepStmtObject.addNull();
          else {
            insertPrepStmtObject.addString(vo.getMakerId().trim());
          }
          if (CommonFunction.checkNull(vo.getMakerDate()).trim().equalsIgnoreCase(""))
            insertPrepStmtObject.addNull();
          else {
            insertPrepStmtObject.addString(CommonFunction.checkNull(vo.getMakerDate()).trim());
          }
          if (CommonFunction.checkNull(vo.getMakerId()).trim().equalsIgnoreCase(""))
            insertPrepStmtObject.addNull();
          else {
            insertPrepStmtObject.addString(vo.getMakerId().trim());
          }
          if (CommonFunction.checkNull(vo.getMakerDate()).trim().equalsIgnoreCase(""))
            insertPrepStmtObject.addNull();
          else
            insertPrepStmtObject.addString(CommonFunction.checkNull(vo.getMakerDate()).trim());
        }
        if (CommonFunction.checkNull(vo.getTahsil()).trim().equalsIgnoreCase(""))
          insertPrepStmtObject.addNull();
        else {
          insertPrepStmtObject.addString(CommonFunction.checkNull(vo.getTahsil()).trim());
        }
        if (CommonFunction.checkNull(vo.getDistanceBranch()).trim().equalsIgnoreCase(""))
          insertPrepStmtObject.addNull();
        else
          insertPrepStmtObject.addString(CommonFunction.checkNull(vo.getDistanceBranch()).trim());
        if (CommonFunction.checkNull(vo.getGstIn()).trim().equalsIgnoreCase(""))
          insertPrepStmtObject.addNull();
        else {
          insertPrepStmtObject.addString(vo.getGstIn().trim());
        }
        if (CommonFunction.checkNull(vo.getStdNo()).trim().equalsIgnoreCase(""))
            insertPrepStmtObject.addNull();
          else {
            insertPrepStmtObject.addString(vo.getStdNo().trim());
          }
        if (CommonFunction.checkNull(vo.getRelation()).trim().equalsIgnoreCase(""))
            insertPrepStmtObject.addNull();
          else {
            insertPrepStmtObject.addString(vo.getRelation().trim());
          }
        insertPrepStmtObject.setSql(bufInsSql.toString());
        logger.info(new StringBuilder().append("IN saveCustomerAddress() insert query1 ### ").append(insertPrepStmtObject.printQuery()).toString());
        qryList.add(insertPrepStmtObject);

        status = ConnectionDAO.sqlInsUpdDeletePrepStmt(qryList);
        logger.info(new StringBuilder().append("In saveCustomer......................").append(status).toString());
        if ((CommonFunction.checkNull(source).trim().equalsIgnoreCase("ED")) && (status))
        {
          ArrayList list = new ArrayList();
          ArrayList list2 = new ArrayList();
          String q1 = null; String q2 = null; String q3 = null;
          PrepStmtObject stmt = new PrepStmtObject();
          PrepStmtObject stmt2 = new PrepStmtObject();
          q1 = new StringBuilder().append("select max(ADDRESS_ID) from com_address_m where BPID='").append(customerId).append("' for update").toString();
          String addressId = ConnectionDAO.singleReturn(q1);
          q2 = new StringBuilder().append("INSERT INTO com_address_m_edit select * from com_address_m WHERE ADDRESS_ID=").append(addressId).toString();
          stmt.setSql(q2);
          logger.info(new StringBuilder().append("IN saveCustomerAddress() of CorpotateDAOImpl  insert query1 for edit loan :  ").append(q2).toString());
          list.add(stmt);
          status = ConnectionDAO.sqlInsUpdDeletePrepStmt(list);

          q3 = new StringBuilder().append("delete from com_address_m WHERE ADDRESS_ID=").append(addressId).toString();
          stmt2.setSql(q3);
          logger.info(new StringBuilder().append("IN saveCustomerAddress() of CorpotateDAOImpl  Delete query :  ").append(q3).toString());
          list2.add(stmt2);
          status = ConnectionDAO.sqlInsUpdDeletePrepStmt(list2);
          list.clear();
          list = null;
          list2.clear();
          list2 = null;
          q1 = null;
          q2 = null;
          q3 = null;
          stmt = null;
          stmt2 = null;
        }
      }
    }
    catch (Exception e) {
      e.printStackTrace();
    }return status;
  }

  public boolean saveCustomerReference(Object ob)
  {
    logger.info("In saveCustomerAddress() of CorpotateDAOImpl.");
    CustomerSaveVo vo = (CustomerSaveVo)ob;
    boolean status = false;
    String source = vo.getSource();
    ArrayList qryList = new ArrayList();
    PrepStmtObject insertPrepStmtObject = new PrepStmtObject();
    String customer = "";
    if ((!CommonFunction.checkNull(vo.getBp_id()).trim().equalsIgnoreCase("")) && (CommonFunction.checkNull(vo.getBp_id1()).trim().equalsIgnoreCase("")))
      customer = vo.getBp_id();
    if ((!CommonFunction.checkNull(vo.getBp_id1()).trim().equalsIgnoreCase("")) && (CommonFunction.checkNull(vo.getBp_id()).trim().equalsIgnoreCase("")))
      customer = vo.getBp_id1();
    try
    {
      if (((vo.getPageStatus() != null) && (vo.getPageStatus().equals("fromLeads"))) || ((vo.getUpdateFlag() != null) && (vo.getUpdateFlag().length() > 0)))
      {
        StringBuffer bufInsSql = new StringBuffer();
        bufInsSql.append("insert into cr_deal_reference_m(BPID, BPTYPE, F_NAME, M_NAME, L_NAME, RELATIONSHIP, KNOWING_SINCE, MOBILE_NUMBER, LANDLINE_NUMBER,REF_ADDRESS,REC_STATUS,EMAIL_ID,MAKER_ID,MAKER_DATE,AUTHOR_ID,AUTHOR_DATE)");
        bufInsSql.append(" values ( ");
        bufInsSql.append(" ?,");
        bufInsSql.append(" ?,");
        bufInsSql.append(" ?,");
        bufInsSql.append(" ?,");
        bufInsSql.append(" ?,");
        bufInsSql.append(" ?,");
        bufInsSql.append(" ?,");
        bufInsSql.append(" ?,");
        bufInsSql.append(" ?,");
        bufInsSql.append(" ?,");
        bufInsSql.append(" ?,");
        bufInsSql.append(" ?,");
        bufInsSql.append(" ?,");
        bufInsSql.append(new StringBuilder().append(" DATE_ADD(STR_TO_DATE(?, '").append(this.dateFormatWithTime).append("'),INTERVAL CURTIME() HOUR_SECOND), ").toString());
        bufInsSql.append(" ?,");
        bufInsSql.append(new StringBuilder().append(" DATE_ADD(STR_TO_DATE(?, '").append(this.dateFormatWithTime).append("'),INTERVAL CURTIME() HOUR_SECOND)").toString());
        bufInsSql.append(" )");

        if (CommonFunction.checkNull(vo.getBp_id()).trim().equalsIgnoreCase(""))
          insertPrepStmtObject.addString(vo.getBp_id1().trim());
        else {
          insertPrepStmtObject.addString(vo.getBp_id().trim());
        }
        if (CommonFunction.checkNull(vo.getBp_type()).trim().equalsIgnoreCase(""))
          insertPrepStmtObject.addNull();
        else {
          insertPrepStmtObject.addString(vo.getBp_type().trim());
        }
        if (CommonFunction.checkNull(vo.getFirstName()).trim().equalsIgnoreCase(""))
          insertPrepStmtObject.addNull();
        else {
          insertPrepStmtObject.addString(vo.getFirstName().trim());
        }
        if (CommonFunction.checkNull(vo.getMiddleName()).trim().equalsIgnoreCase(""))
          insertPrepStmtObject.addNull();
        else {
          insertPrepStmtObject.addString(vo.getMiddleName().trim());
        }
        if (CommonFunction.checkNull(vo.getLastName()).trim().equalsIgnoreCase(""))
          insertPrepStmtObject.addNull();
        else {
          insertPrepStmtObject.addString(vo.getLastName().trim());
        }
        if (CommonFunction.checkNull(vo.getRelationshipS()).trim().equalsIgnoreCase(""))
          insertPrepStmtObject.addNull();
        else {
          insertPrepStmtObject.addString(vo.getRelationshipS().trim());
        }
        if (CommonFunction.checkNull(vo.getKnowingSince()).trim().equalsIgnoreCase(""))
          insertPrepStmtObject.addNull();
        else {
          insertPrepStmtObject.addString(vo.getKnowingSince().trim());
        }
        if (CommonFunction.checkNull(vo.getPrimaryRefMbNo()).trim().equalsIgnoreCase(""))
          insertPrepStmtObject.addNull();
        else {
          insertPrepStmtObject.addString(vo.getPrimaryRefMbNo().trim());
        }
        if (CommonFunction.checkNull(vo.getAlternateRefPhNo()).trim().equalsIgnoreCase(""))
          insertPrepStmtObject.addNull();
        else {
          insertPrepStmtObject.addString(vo.getAlternateRefPhNo().trim());
        }
        if (CommonFunction.checkNull(vo.getAddRef()).trim().equalsIgnoreCase(""))
          insertPrepStmtObject.addNull();
        else {
          insertPrepStmtObject.addString(vo.getAddRef().trim());
        }
        insertPrepStmtObject.addString("A");

        if (CommonFunction.checkNull(vo.getInstitutionEmail()).trim().equalsIgnoreCase(""))
          insertPrepStmtObject.addNull();
        else {
          insertPrepStmtObject.addString(vo.getInstitutionEmail().trim());
        }

        if (CommonFunction.checkNull(vo.getMakerId()).trim().equalsIgnoreCase(""))
          insertPrepStmtObject.addNull();
        else
          insertPrepStmtObject.addString(vo.getMakerId().trim());
        if (CommonFunction.checkNull(vo.getMakerDate()).trim().equalsIgnoreCase(""))
          insertPrepStmtObject.addNull();
        else {
          insertPrepStmtObject.addString(CommonFunction.checkNull(vo.getMakerDate()).trim());
        }
        if (CommonFunction.checkNull(vo.getMakerId()).trim().equalsIgnoreCase(""))
          insertPrepStmtObject.addNull();
        else
          insertPrepStmtObject.addString(vo.getMakerId().trim());
        if (CommonFunction.checkNull(vo.getMakerDate()).trim().equalsIgnoreCase(""))
          insertPrepStmtObject.addNull();
        else {
          insertPrepStmtObject.addString(CommonFunction.checkNull(vo.getMakerDate()).trim());
        }

        insertPrepStmtObject.setSql(bufInsSql.toString());
        logger.info(new StringBuilder().append("IN saveCustomerReference() of CorpotateDAOImpl  insert query1 ### ").append(insertPrepStmtObject.printQuery()).toString());
        qryList.add(insertPrepStmtObject);
        status = ConnectionDAO.sqlInsUpdDeletePrepStmt(qryList);
        logger.info(new StringBuilder().append("In saveCustomerReference......................").append(status).toString());
      }
      else
      {
        int maxId = 0;
        StringBuffer bufInsSql = new StringBuffer();
        int checkTable = 0;
        if (!vo.getStatusCase().equals(""))
        {
          String query3 = "Select max(REF_ID) from com_reference_m_temp for update";
          String id = ConnectionDAO.singleReturn(query3);
          logger.info(new StringBuilder().append("id: ").append(id).toString());
          if (CommonFunction.checkNull(id).equalsIgnoreCase(""))
            maxId = 1;
          else
            maxId = Integer.parseInt(id) + 1;
          checkTable++;
          bufInsSql.append("insert into com_reference_m_temp(REF_ID,BPID,BPTYPE, F_NAME, M_NAME, L_NAME, RELATIONSHIP, KNOWING_SINCE, MOBILE_NUMBER, LANDLINE_NUMBER, REF_ADDRESS,EMAIL_ID)");
          bufInsSql.append(" values ( ");
          bufInsSql.append(" ?,");
        }
        else
        {
          bufInsSql.append("insert into com_reference_m(BPID,BPTYPE, F_NAME, M_NAME, L_NAME, RELATIONSHIP, KNOWING_SINCE, MOBILE_NUMBER, LANDLINE_NUMBER, REF_ADDRESS,EMAIL_ID, REC_STATUS,MAKER_ID,MAKER_DATE,AUTHOR_ID,AUTHOR_DATE)");
          bufInsSql.append(" values ( ");
        }

        bufInsSql.append(" ?,");
        bufInsSql.append(" ?,");
        bufInsSql.append(" ?,");
        bufInsSql.append(" ?,");
        bufInsSql.append(" ?,");
        bufInsSql.append(" ?,");
        bufInsSql.append(" ?,");
        bufInsSql.append(" ?,");
        bufInsSql.append(" ?,");
        bufInsSql.append(" ?, ");
        bufInsSql.append(" ? ");
        if (checkTable == 0)
        {
          bufInsSql.append(" , ?,");
          bufInsSql.append(" ?,");
          bufInsSql.append(new StringBuilder().append(" DATE_ADD(STR_TO_DATE(?, '").append(this.dateFormatWithTime).append("'),INTERVAL CURTIME() HOUR_SECOND), ").toString());
          bufInsSql.append(" ?,");
          bufInsSql.append(new StringBuilder().append(" DATE_ADD(STR_TO_DATE(?, '").append(this.dateFormatWithTime).append("'),INTERVAL CURTIME() HOUR_SECOND)").toString());
          bufInsSql.append(" )");
        }
        else {
          bufInsSql.append(" )");
        }

        if (!vo.getStatusCase().equals(""))
        {
          insertPrepStmtObject.addInt(maxId);
        }

        insertPrepStmtObject.addString(customer);

        if (CommonFunction.checkNull(vo.getBp_type()).trim().equalsIgnoreCase(""))
          insertPrepStmtObject.addNull();
        else {
          insertPrepStmtObject.addString(vo.getBp_type().trim());
        }
        if (CommonFunction.checkNull(vo.getFirstName()).trim().equalsIgnoreCase(""))
          insertPrepStmtObject.addNull();
        else {
          insertPrepStmtObject.addString(vo.getFirstName().trim());
        }
        if (CommonFunction.checkNull(vo.getMiddleName()).trim().equalsIgnoreCase(""))
          insertPrepStmtObject.addNull();
        else {
          insertPrepStmtObject.addString(vo.getMiddleName().trim());
        }
        if (CommonFunction.checkNull(vo.getLastName()).trim().equalsIgnoreCase(""))
          insertPrepStmtObject.addNull();
        else {
          insertPrepStmtObject.addString(vo.getLastName().trim());
        }
        if (CommonFunction.checkNull(vo.getRelationshipS()).trim().equalsIgnoreCase(""))
          insertPrepStmtObject.addNull();
        else {
          insertPrepStmtObject.addString(vo.getRelationshipS().trim());
        }
        if (CommonFunction.checkNull(vo.getKnowingSince()).trim().equalsIgnoreCase(""))
          insertPrepStmtObject.addNull();
        else {
          insertPrepStmtObject.addString(vo.getKnowingSince().trim());
        }
        if (CommonFunction.checkNull(vo.getPrimaryRefMbNo()).trim().equalsIgnoreCase(""))
          insertPrepStmtObject.addNull();
        else {
          insertPrepStmtObject.addString(vo.getPrimaryRefMbNo().trim());
        }
        if (CommonFunction.checkNull(vo.getAlternateRefPhNo()).trim().equalsIgnoreCase(""))
          insertPrepStmtObject.addNull();
        else {
          insertPrepStmtObject.addString(vo.getAlternateRefPhNo().trim());
        }
        if (CommonFunction.checkNull(vo.getAddRef()).trim().equalsIgnoreCase(""))
          insertPrepStmtObject.addNull();
        else {
          insertPrepStmtObject.addString(vo.getAddRef().trim());
        }
        if (CommonFunction.checkNull(vo.getInstitutionEmail()).trim().equalsIgnoreCase(""))
          insertPrepStmtObject.addNull();
        else {
          insertPrepStmtObject.addString(vo.getInstitutionEmail().trim());
        }
        if (checkTable == 0)
        {
          insertPrepStmtObject.addString("A");

          if (CommonFunction.checkNull(vo.getMakerId()).trim().equalsIgnoreCase(""))
            insertPrepStmtObject.addNull();
          else {
            insertPrepStmtObject.addString(vo.getMakerId().trim());
          }
          if (CommonFunction.checkNull(vo.getMakerDate()).trim().equalsIgnoreCase(""))
            insertPrepStmtObject.addNull();
          else {
            insertPrepStmtObject.addString(CommonFunction.checkNull(vo.getMakerDate()).trim());
          }
          if (CommonFunction.checkNull(vo.getMakerId()).trim().equalsIgnoreCase(""))
            insertPrepStmtObject.addNull();
          else {
            insertPrepStmtObject.addString(vo.getMakerId().trim());
          }
          if (CommonFunction.checkNull(vo.getMakerDate()).trim().equalsIgnoreCase(""))
            insertPrepStmtObject.addNull();
          else {
            insertPrepStmtObject.addString(CommonFunction.checkNull(vo.getMakerDate()).trim());
          }
        }
        insertPrepStmtObject.setSql(bufInsSql.toString());
        logger.info(new StringBuilder().append("IN saveCustomerReference() insert query1 ### ").append(insertPrepStmtObject.printQuery()).toString());
        qryList.add(insertPrepStmtObject);

        status = ConnectionDAO.sqlInsUpdDeletePrepStmt(qryList);
        logger.info(new StringBuilder().append("In saveCustomerReference......................").append(status).toString());
        if ((CommonFunction.checkNull(source).trim().equalsIgnoreCase("ED")) && (status))
        {
          String q1 = new StringBuilder().append("select max(ref_id) from com_reference_m where BPID='").append(customer).append("' for update").toString();
          String ref_id = ConnectionDAO.singleReturn(q1);
          String q2 = new StringBuilder().append("INSERT INTO com_reference_m_edit select * from com_reference_m WHERE ref_id=").append(ref_id).toString();
          PrepStmtObject stmt = new PrepStmtObject();
          stmt.setSql(q2);
          ArrayList list = new ArrayList();
          list.add(stmt);
          status = ConnectionDAO.sqlInsUpdDeletePrepStmt(list);
          logger.info(new StringBuilder().append("In saveCustomerReference......................").append(status).toString());
          String q3 = new StringBuilder().append("delete from com_reference_m WHERE ref_id=").append(ref_id).toString();
          PrepStmtObject stmt2 = new PrepStmtObject();
          stmt2.setSql(q3);
          ArrayList list2 = new ArrayList();
          list2.add(stmt2);
          status = ConnectionDAO.sqlInsUpdDeletePrepStmt(list2);
        }
      }
    }
    catch (Exception e) {
      e.printStackTrace();
    }return status;
  }

  public boolean savesavaSuraksha(Object ob)
  {
    logger.info("In savesavaSuraksha() of CorpotateDAOImpl.");
    CustomerSaveVo vo = (CustomerSaveVo)ob;
    boolean status = false;
    String source = vo.getSource();
    ArrayList qryList = new ArrayList();
    PrepStmtObject insertPrepStmtObject = new PrepStmtObject();
    String customer = "";
    if ((!CommonFunction.checkNull(vo.getBp_id()).trim().equalsIgnoreCase("")) && (CommonFunction.checkNull(vo.getBp_id1()).trim().equalsIgnoreCase("")))
      customer = vo.getBp_id();
    if ((!CommonFunction.checkNull(vo.getBp_id1()).trim().equalsIgnoreCase("")) && (CommonFunction.checkNull(vo.getBp_id()).trim().equalsIgnoreCase("")))
      customer = vo.getBp_id1();
    try
    {
      if (((vo.getPageStatus() != null) && (vo.getPageStatus().equals("fromLeads"))) || ((vo.getUpdateFlag() != null) && (vo.getUpdateFlag().length() > 0)))
      {
        StringBuffer bufInsSql = new StringBuffer();
        bufInsSql.append("insert into cr_deal_sarva_Suraksha_m(BPID, BPTYPE,Nominee_NAME, RELATIONSHIP,NomineeDOB,REC_STATUS,MAKER_ID,MAKER_DATE,AUTHOR_ID,AUTHOR_DATE)");
        bufInsSql.append(" values ( ");
        bufInsSql.append(" ?,");
        bufInsSql.append(" ?,");
        bufInsSql.append(" ?,");
        bufInsSql.append(" ?,");
        bufInsSql.append(new StringBuilder().append(" STR_TO_DATE(?,'").append(this.dateFormat).append("'), ").toString());
        bufInsSql.append(" ?,");
        bufInsSql.append(" ?,");
        bufInsSql.append(new StringBuilder().append(" DATE_ADD(STR_TO_DATE(?, '").append(this.dateFormatWithTime).append("'),INTERVAL CURTIME() HOUR_SECOND), ").toString());
        bufInsSql.append(" ?,");
        bufInsSql.append(new StringBuilder().append(" DATE_ADD(STR_TO_DATE(?, '").append(this.dateFormatWithTime).append("'),INTERVAL CURTIME() HOUR_SECOND)").toString());
        bufInsSql.append(" )");

        if (CommonFunction.checkNull(vo.getBp_id()).trim().equalsIgnoreCase(""))
          insertPrepStmtObject.addString(vo.getBp_id1().trim());
        else {
          insertPrepStmtObject.addString(vo.getBp_id().trim());
        }
        if (CommonFunction.checkNull(vo.getBp_type()).trim().equalsIgnoreCase(""))
          insertPrepStmtObject.addNull();
        else {
          insertPrepStmtObject.addString(vo.getBp_type().trim());
        }
        if (CommonFunction.checkNull(vo.getNominee()).trim().equalsIgnoreCase(""))
          insertPrepStmtObject.addNull();
        else {
          insertPrepStmtObject.addString(vo.getNominee().trim());
        }
        if (CommonFunction.checkNull(vo.getRelationshipS()).trim().equalsIgnoreCase(""))
          insertPrepStmtObject.addNull();
        else {
          insertPrepStmtObject.addString(vo.getRelationshipS().trim());
        }
        if (CommonFunction.checkNull(vo.getNomineeDOB()).trim().equalsIgnoreCase(""))
          insertPrepStmtObject.addNull();
        else {
          insertPrepStmtObject.addString(vo.getNomineeDOB().trim());
        }
        insertPrepStmtObject.addString("A");

        if (CommonFunction.checkNull(vo.getMakerId()).trim().equalsIgnoreCase(""))
          insertPrepStmtObject.addNull();
        else
          insertPrepStmtObject.addString(vo.getMakerId().trim());
        if (CommonFunction.checkNull(vo.getMakerDate()).trim().equalsIgnoreCase(""))
          insertPrepStmtObject.addNull();
        else {
          insertPrepStmtObject.addString(CommonFunction.checkNull(vo.getMakerDate()).trim());
        }
        if (CommonFunction.checkNull(vo.getMakerId()).trim().equalsIgnoreCase(""))
          insertPrepStmtObject.addNull();
        else
          insertPrepStmtObject.addString(vo.getMakerId().trim());
        if (CommonFunction.checkNull(vo.getMakerDate()).trim().equalsIgnoreCase(""))
          insertPrepStmtObject.addNull();
        else {
          insertPrepStmtObject.addString(CommonFunction.checkNull(vo.getMakerDate()).trim());
        }

        insertPrepStmtObject.setSql(bufInsSql.toString());
        logger.info(new StringBuilder().append("IN savesavaSuraksha() of CorpotateDAOImpl  insert query1 ### ").append(insertPrepStmtObject.printQuery()).toString());
        qryList.add(insertPrepStmtObject);
        status = ConnectionDAO.sqlInsUpdDeletePrepStmt(qryList);
        logger.info(new StringBuilder().append("In savesavaSuraksha......................").append(status).toString());
      }
      else
      {
        int maxId = 0;
        StringBuffer bufInsSql = new StringBuffer();
        int checkTable = 0;
        if (!vo.getStatusCase().equals(""))
        {
          String query3 = "Select max(REF_ID) from com_sarva_Suraksha_m_temp for update";
          String id = ConnectionDAO.singleReturn(query3);
          logger.info(new StringBuilder().append("id: ").append(id).toString());
          if (CommonFunction.checkNull(id).equalsIgnoreCase(""))
            maxId = 1;
          else
            maxId = Integer.parseInt(id) + 1;
          checkTable++;
          bufInsSql.append("insert into com_sarva_Suraksha_m_temp(REF_ID,BPID,BPTYPE,Nominee_NAME, RELATIONSHIP,NomineeDOB)");
          bufInsSql.append(" values ( ");
          bufInsSql.append(" ?,");
        }
        else
        {
          bufInsSql.append("insert into com_sarva_Suraksha_m(BPID,BPTYPE,Nominee_NAME, RELATIONSHIP,NomineeDOB,REC_STATUS,MAKER_ID,MAKER_DATE,AUTHOR_ID,AUTHOR_DATE)");
          bufInsSql.append(" values ( ");
        }

        bufInsSql.append(" ?,");
        bufInsSql.append(" ?,");
        bufInsSql.append(" ?,");
        bufInsSql.append(" ?,");

        bufInsSql.append(new StringBuilder().append(" STR_TO_DATE(?,'").append(this.dateFormat).append("') ").toString());

        if (checkTable == 0)
        {
          bufInsSql.append(" , ?,");
          bufInsSql.append(" ?,");
          bufInsSql.append(new StringBuilder().append(" DATE_ADD(STR_TO_DATE(?, '").append(this.dateFormatWithTime).append("'),INTERVAL CURTIME() HOUR_SECOND), ").toString());
          bufInsSql.append(" ?,");
          bufInsSql.append(new StringBuilder().append(" DATE_ADD(STR_TO_DATE(?, '").append(this.dateFormatWithTime).append("'),INTERVAL CURTIME() HOUR_SECOND)").toString());
          bufInsSql.append(" )");
        }
        else {
          bufInsSql.append(" )");
        }

        if (!vo.getStatusCase().equals(""))
        {
          insertPrepStmtObject.addInt(maxId);
        }

        insertPrepStmtObject.addString(customer);

        if (CommonFunction.checkNull(vo.getBp_type()).trim().equalsIgnoreCase(""))
          insertPrepStmtObject.addNull();
        else {
          insertPrepStmtObject.addString(vo.getBp_type().trim());
        }
        if (CommonFunction.checkNull(vo.getNominee()).trim().equalsIgnoreCase(""))
          insertPrepStmtObject.addNull();
        else {
          insertPrepStmtObject.addString(vo.getNominee().trim());
        }
        if (CommonFunction.checkNull(vo.getRelationshipS()).trim().equalsIgnoreCase(""))
          insertPrepStmtObject.addNull();
        else {
          insertPrepStmtObject.addString(vo.getRelationshipS().trim());
        }
        if (CommonFunction.checkNull(vo.getNomineeDOB()).trim().equalsIgnoreCase(""))
          insertPrepStmtObject.addNull();
        else {
          insertPrepStmtObject.addString(vo.getNomineeDOB().trim());
        }
        if (checkTable == 0)
        {
          insertPrepStmtObject.addString("A");

          if (CommonFunction.checkNull(vo.getMakerId()).trim().equalsIgnoreCase(""))
            insertPrepStmtObject.addNull();
          else {
            insertPrepStmtObject.addString(vo.getMakerId().trim());
          }
          if (CommonFunction.checkNull(vo.getMakerDate()).trim().equalsIgnoreCase(""))
            insertPrepStmtObject.addNull();
          else {
            insertPrepStmtObject.addString(CommonFunction.checkNull(vo.getMakerDate()).trim());
          }
          if (CommonFunction.checkNull(vo.getMakerId()).trim().equalsIgnoreCase(""))
            insertPrepStmtObject.addNull();
          else {
            insertPrepStmtObject.addString(vo.getMakerId().trim());
          }
          if (CommonFunction.checkNull(vo.getMakerDate()).trim().equalsIgnoreCase(""))
            insertPrepStmtObject.addNull();
          else {
            insertPrepStmtObject.addString(CommonFunction.checkNull(vo.getMakerDate()).trim());
          }
        }
        insertPrepStmtObject.setSql(bufInsSql.toString());
        logger.info(new StringBuilder().append("IN saveCustomerReference() insert query1 ### ").append(insertPrepStmtObject.printQuery()).toString());
        qryList.add(insertPrepStmtObject);

        status = ConnectionDAO.sqlInsUpdDeletePrepStmt(qryList);
        logger.info(new StringBuilder().append("In saveCustomerReference......................").append(status).toString());
        if ((CommonFunction.checkNull(source).trim().equalsIgnoreCase("ED")) && (status))
        {
          String q1 = new StringBuilder().append("select max(ref_id) from com_sarva_Suraksha_m where BPID='").append(customer).append("' for update").toString();
          String ref_id = ConnectionDAO.singleReturn(q1);
          String q2 = new StringBuilder().append("INSERT INTO com_sarva_Suraksha_m_edit select * from com_sarva_Suraksha_m WHERE ref_id=").append(ref_id).toString();
          PrepStmtObject stmt = new PrepStmtObject();
          stmt.setSql(q2);
          ArrayList list = new ArrayList();
          list.add(stmt);
          status = ConnectionDAO.sqlInsUpdDeletePrepStmt(list);
          logger.info(new StringBuilder().append("In saveCustomerReference......................").append(status).toString());
          String q3 = new StringBuilder().append("delete from com_sarva_Suraksha_m WHERE ref_id=").append(ref_id).toString();
          PrepStmtObject stmt2 = new PrepStmtObject();
          stmt2.setSql(q3);
          ArrayList list2 = new ArrayList();
          list2.add(stmt2);
          status = ConnectionDAO.sqlInsUpdDeletePrepStmt(list2);
        }
      }
    }
    catch (Exception e) {
      e.printStackTrace();
    }return status;
  }

  public boolean saveStakeHolder(Object ob, int id, String source)
  {
    StakeHolderVo vo = (StakeHolderVo)ob;

    boolean status = false;
    ArrayList qryList = new ArrayList();
    PrepStmtObject insertPrepStmtObject = new PrepStmtObject();
    try {
      if (((vo.getPageStat() != null) && (vo.getPageStat().equals("fromLeads"))) || ((vo.getUpdateFlag() != null) && (vo.getUpdateFlag().length() > 0)))
      {
        logger.info("In Credit Processing , Customer Entry.,saveStakeHolder().......");

        logger.info("In insert saveStakeHolder");
        StringBuffer bufInsSql = new StringBuffer();
        bufInsSql.append("insert into cr_deal_customer_stakeholder_m(STAKEHOLDER_SALUTATION,STAKEHOLDER_NAME,STAKEHOLDER_TYPE,STAKEHOLDER_PERCENTAGE,STAKEHOLDER_DOB,STAKEHOLDER_EXPERIENCE,STAKEHOLDER_DIN,STAKEHOLDER_IDENTIFICATION_NO,STAKEHOLDER_POSITION,STAKEHOLDER_JOINING_DATE,ELIGIBLE_FOR_COMPUTATION,STAKEHOLDER_PRIMARY_PHONE,STAKEHOLDER_ALTERNATE_PHONE,STAKEHOLDER_PRIMARY_EMAIL,STAKEHOLDER_ALTERNATE_EMAIL,STAKEHOLDER_WEBSITE,REC_STATUS,MAKER_ID,MAKER_DATE,CUSTOMER_ID,AUTHOR_ID,AUTHOR_DATE,STAKEHOLDER_PAN,ADDITIONAL_ID1,ADDITIONAL_ID2)");
        bufInsSql.append(" values ( ");
        bufInsSql.append(" ?,");
        bufInsSql.append(" ?,");
        bufInsSql.append(" ?,");
        bufInsSql.append(" ?,");
        bufInsSql.append(new StringBuilder().append(" STR_TO_DATE(?,'").append(this.dateFormat).append("'),").toString());
        bufInsSql.append(" ?,");
        bufInsSql.append(" ?,");
        bufInsSql.append(" ?,");
        bufInsSql.append(" ?,");
        bufInsSql.append(new StringBuilder().append(" STR_TO_DATE(?,'").append(this.dateFormat).append("'),").toString());
        bufInsSql.append(" ?,");
        bufInsSql.append(" ?,");
        bufInsSql.append(" ?,");
        bufInsSql.append(" ?,");
        bufInsSql.append(" ?,");
        bufInsSql.append(" ?,");
        bufInsSql.append(" ?,");
        bufInsSql.append(" ?,");
        bufInsSql.append(new StringBuilder().append(" DATE_ADD(STR_TO_DATE(?, '").append(this.dateFormatWithTime).append("'),INTERVAL CURTIME() HOUR_SECOND),").toString());
        bufInsSql.append(" ?,");
        bufInsSql.append(" ?,");
        bufInsSql.append(new StringBuilder().append(" DATE_ADD(STR_TO_DATE(?, '").append(this.dateFormatWithTime).append("'),INTERVAL CURTIME() HOUR_SECOND),").toString());
        bufInsSql.append("?,");
        bufInsSql.append("?,");
        bufInsSql.append("? )");

        if (CommonFunction.checkNull(vo.getSex()).trim().equalsIgnoreCase(""))
          insertPrepStmtObject.addNull();
        else {
          insertPrepStmtObject.addString(vo.getSex().trim());
        }
        if (CommonFunction.checkNull(vo.getHolderName()).trim().equalsIgnoreCase(""))
          insertPrepStmtObject.addNull();
        else {
          insertPrepStmtObject.addString(vo.getHolderName().trim());
        }
        if (CommonFunction.checkNull(vo.getHolderType()).trim().equalsIgnoreCase(""))
          insertPrepStmtObject.addNull();
        else {
          insertPrepStmtObject.addString(vo.getHolderType().trim());
        }
        if (CommonFunction.checkNull(vo.getHoldingPerc()).trim().equalsIgnoreCase(""))
          insertPrepStmtObject.addNull();
        else {
          insertPrepStmtObject.addString(vo.getHoldingPerc().trim());
        }
        if (CommonFunction.checkNull(vo.getDob()).trim().equalsIgnoreCase(""))
          insertPrepStmtObject.addNull();
        else {
          insertPrepStmtObject.addString(vo.getDob().trim());
        }
        if (CommonFunction.checkNull(vo.getTotalExp()).trim().equalsIgnoreCase(""))
          insertPrepStmtObject.addNull();
        else {
          insertPrepStmtObject.addString(vo.getTotalExp().trim());
        }
        if (CommonFunction.checkNull(vo.getDinNo()).trim().equalsIgnoreCase(""))
          insertPrepStmtObject.addNull();
        else {
          insertPrepStmtObject.addString(vo.getDinNo().trim());
        }
        if (CommonFunction.checkNull(vo.getIdNo()).trim().equalsIgnoreCase(""))
          insertPrepStmtObject.addNull();
        else {
          insertPrepStmtObject.addString(vo.getIdNo().trim());
        }
        if (CommonFunction.checkNull(vo.getPosition()).trim().equalsIgnoreCase(""))
          insertPrepStmtObject.addNull();
        else {
          insertPrepStmtObject.addString(vo.getPosition().trim());
        }
        if (CommonFunction.checkNull(vo.getDoj()).trim().equalsIgnoreCase(""))
          insertPrepStmtObject.addNull();
        else {
          insertPrepStmtObject.addString(vo.getDoj().trim());
        }
        if (CommonFunction.checkNull(vo.getCompute()).trim().equalsIgnoreCase("on"))
          insertPrepStmtObject.addString("Y");
        else {
          insertPrepStmtObject.addString("N");
        }
        if (CommonFunction.checkNull(vo.getPrimaryPhone()).trim().equalsIgnoreCase(""))
          insertPrepStmtObject.addNull();
        else {
          insertPrepStmtObject.addString(vo.getPrimaryPhone().trim());
        }
        if (CommonFunction.checkNull(vo.getAlternatePhone()).trim().equalsIgnoreCase(""))
          insertPrepStmtObject.addNull();
        else {
          insertPrepStmtObject.addString(vo.getAlternatePhone().trim());
        }
        if (CommonFunction.checkNull(vo.getPrimaryEmail()).trim().equalsIgnoreCase(""))
          insertPrepStmtObject.addNull();
        else {
          insertPrepStmtObject.addString(vo.getPrimaryEmail().trim());
        }
        if (CommonFunction.checkNull(vo.getAlternateEmail()).equalsIgnoreCase(""))
          insertPrepStmtObject.addNull();
        else {
          insertPrepStmtObject.addString(vo.getAlternateEmail());
        }
        if (CommonFunction.checkNull(vo.getWebsite()).trim().equalsIgnoreCase(""))
          insertPrepStmtObject.addNull();
        else {
          insertPrepStmtObject.addString(vo.getWebsite().trim());
        }
        insertPrepStmtObject.addString("A");

        if (CommonFunction.checkNull(vo.getMakerId()).trim().equalsIgnoreCase(""))
          insertPrepStmtObject.addNull();
        else
          insertPrepStmtObject.addString(vo.getMakerId().trim());
        if (CommonFunction.checkNull(vo.getMakerDate()).trim().equalsIgnoreCase(""))
          insertPrepStmtObject.addNull();
        else {
          insertPrepStmtObject.addString(CommonFunction.checkNull(vo.getMakerDate()).trim());
        }
        insertPrepStmtObject.addInt(id);

        if (CommonFunction.checkNull(vo.getMakerId()).trim().equalsIgnoreCase(""))
          insertPrepStmtObject.addNull();
        else {
          insertPrepStmtObject.addString(vo.getMakerId().trim());
        }

        if (CommonFunction.checkNull(vo.getMakerDate()).trim().equalsIgnoreCase(""))
          insertPrepStmtObject.addNull();
        else {
          insertPrepStmtObject.addString(CommonFunction.checkNull(vo.getMakerDate()).trim());
        }
        if (CommonFunction.checkNull(vo.getMgmtPAN()).trim().equalsIgnoreCase(""))
          insertPrepStmtObject.addNull();
        else {
          insertPrepStmtObject.addString(CommonFunction.checkNull(vo.getMgmtPAN()).trim());
        }
        if (CommonFunction.checkNull(vo.getAddId1()).trim().equalsIgnoreCase(""))
          insertPrepStmtObject.addNull();
        else {
          insertPrepStmtObject.addString(CommonFunction.checkNull(vo.getAddId1()).trim());
        }
        if (CommonFunction.checkNull(vo.getAddId2()).trim().equalsIgnoreCase(""))
          insertPrepStmtObject.addNull();
        else {
          insertPrepStmtObject.addString(CommonFunction.checkNull(vo.getAddId2()).trim());
        }
        insertPrepStmtObject.setSql(bufInsSql.toString());
        logger.info(new StringBuilder().append("IN saveCustomerAddress() insert query1 ### ").append(insertPrepStmtObject.printQuery()).toString());
        qryList.add(insertPrepStmtObject);
        status = ConnectionDAO.sqlInsUpdDeletePrepStmt(qryList);
        logger.info(new StringBuilder().append("In saveCustomer......................").append(status).toString());
        saveStakeHolderDedupe(ob, id, source);
      }
      else
      {
        logger.info("In GCD.,saveStakeHolder().......");
        int maxId = 0;

        StringBuffer bufInsSql = new StringBuffer();
        int checkTable = 0;

        if (!vo.getStatusCase().equals(""))
        {
          String query3 = "Select max(STAKEHOLDER_ID) from customer_stakeholder_m_temp for update";
          String maxid = ConnectionDAO.singleReturn(query3);
          if (maxid == null)
          {
            maxId = 1;
          }
          else
          {
            maxId = Integer.parseInt(maxid) + 1;
          }

          logger.info(new StringBuilder().append("maxId : ").append(maxId).toString());
          checkTable++;
          bufInsSql.append("insert into customer_stakeholder_m_temp(STAKEHOLDER_ID,STAKEHOLDER_SALUTATION,STAKEHOLDER_NAME,STAKEHOLDER_TYPE,STAKEHOLDER_PERCENTAGE,STAKEHOLDER_DOB,STAKEHOLDER_EXPERIENCE,STAKEHOLDER_DIN,STAKEHOLDER_IDENTIFICATION_NO,STAKEHOLDER_POSITION,STAKEHOLDER_JOINING_DATE,ELIGIBLE_FOR_COMPUTATION,STAKEHOLDER_PRIMARY_PHONE,STAKEHOLDER_ALTERNATE_PHONE,STAKEHOLDER_PRIMARY_EMAIL,STAKEHOLDER_ALTERNATE_EMAIL,STAKEHOLDER_WEBSITE,CUSTOMER_ID)");
          bufInsSql.append(" values ( ");
          bufInsSql.append(" ?,");
        }
        else
        {
          bufInsSql.append("insert into customer_stakeholder_m(STAKEHOLDER_SALUTATION,STAKEHOLDER_NAME,STAKEHOLDER_TYPE,STAKEHOLDER_PERCENTAGE,STAKEHOLDER_DOB,STAKEHOLDER_EXPERIENCE,STAKEHOLDER_DIN,STAKEHOLDER_IDENTIFICATION_NO,STAKEHOLDER_POSITION,STAKEHOLDER_JOINING_DATE,ELIGIBLE_FOR_COMPUTATION,STAKEHOLDER_PRIMARY_PHONE,STAKEHOLDER_ALTERNATE_PHONE,STAKEHOLDER_PRIMARY_EMAIL,STAKEHOLDER_ALTERNATE_EMAIL,STAKEHOLDER_WEBSITE,CUSTOMER_ID,MAKER_ID,MAKER_DATE,AUTHOR_ID,AUTHOR_DATE,STAKEHOLDER_PAN,ADDITIONAL_ID1,ADDITIONAL_ID2)");
          bufInsSql.append(" values ( ");
        }

        bufInsSql.append(" ?,");
        bufInsSql.append(" ?,");
        bufInsSql.append(" ?,");
        bufInsSql.append(" ?,");
        bufInsSql.append(new StringBuilder().append(" STR_TO_DATE(?,'").append(this.dateFormat).append("'),").toString());
        bufInsSql.append(" ?,");
        bufInsSql.append(" ?,");
        bufInsSql.append(" ?,");
        bufInsSql.append(" ?,");
        bufInsSql.append(new StringBuilder().append(" STR_TO_DATE(?,'").append(this.dateFormat).append("'),").toString());
        bufInsSql.append(" ?,");
        bufInsSql.append(" ?,");
        bufInsSql.append(" ?,");
        bufInsSql.append(" ?,");
        bufInsSql.append(" ?,");
        bufInsSql.append(" ?,");

        if (checkTable == 0) {
          bufInsSql.append(" ?,");
          bufInsSql.append(" ?,");
          bufInsSql.append(new StringBuilder().append(" DATE_ADD(STR_TO_DATE(?, '").append(this.dateFormatWithTime).append("'),INTERVAL CURTIME() HOUR_SECOND),").toString());
          bufInsSql.append(" ?,");
          bufInsSql.append(new StringBuilder().append(" DATE_ADD(STR_TO_DATE(?, '").append(this.dateFormatWithTime).append("'),INTERVAL CURTIME() HOUR_SECOND),").toString());
          bufInsSql.append("?,");
          bufInsSql.append("?,");
          bufInsSql.append("? )");
        }
        else {
          bufInsSql.append(" ? )");
        }
        if (!vo.getStatusCase().equals(""))
        {
          insertPrepStmtObject.addInt(maxId);
        }

        if (CommonFunction.checkNull(vo.getSex()).trim().equalsIgnoreCase(""))
          insertPrepStmtObject.addNull();
        else {
          insertPrepStmtObject.addString(vo.getSex().trim());
        }
        if (CommonFunction.checkNull(vo.getHolderName()).trim().equalsIgnoreCase(""))
          insertPrepStmtObject.addNull();
        else {
          insertPrepStmtObject.addString(vo.getHolderName().trim());
        }
        if (CommonFunction.checkNull(vo.getHolderType()).trim().equalsIgnoreCase(""))
          insertPrepStmtObject.addNull();
        else {
          insertPrepStmtObject.addString(vo.getHolderType().trim());
        }
        if (CommonFunction.checkNull(vo.getHoldingPerc()).trim().equalsIgnoreCase(""))
          insertPrepStmtObject.addNull();
        else {
          insertPrepStmtObject.addString(vo.getHoldingPerc().trim());
        }
        if (CommonFunction.checkNull(vo.getDob()).trim().equalsIgnoreCase(""))
          insertPrepStmtObject.addNull();
        else {
          insertPrepStmtObject.addString(vo.getDob().trim());
        }
        if (CommonFunction.checkNull(vo.getTotalExp()).trim().equalsIgnoreCase(""))
          insertPrepStmtObject.addNull();
        else {
          insertPrepStmtObject.addString(vo.getTotalExp().trim());
        }
        if (CommonFunction.checkNull(vo.getDinNo()).trim().equalsIgnoreCase(""))
          insertPrepStmtObject.addNull();
        else {
          insertPrepStmtObject.addString(vo.getDinNo().trim());
        }
        if (CommonFunction.checkNull(vo.getIdNo()).trim().equalsIgnoreCase(""))
          insertPrepStmtObject.addNull();
        else {
          insertPrepStmtObject.addString(vo.getIdNo().trim());
        }
        if (CommonFunction.checkNull(vo.getPosition()).trim().equalsIgnoreCase(""))
          insertPrepStmtObject.addNull();
        else {
          insertPrepStmtObject.addString(vo.getPosition().trim());
        }
        if (CommonFunction.checkNull(vo.getDoj()).trim().equalsIgnoreCase(""))
          insertPrepStmtObject.addNull();
        else {
          insertPrepStmtObject.addString(vo.getDoj().trim());
        }
        if (CommonFunction.checkNull(vo.getCompute()).trim().equalsIgnoreCase("on"))
          insertPrepStmtObject.addString("Y");
        else {
          insertPrepStmtObject.addString("N");
        }
        if (CommonFunction.checkNull(vo.getPrimaryPhone()).trim().equalsIgnoreCase(""))
          insertPrepStmtObject.addNull();
        else {
          insertPrepStmtObject.addString(vo.getPrimaryPhone().trim());
        }
        if (CommonFunction.checkNull(vo.getAlternatePhone()).trim().equalsIgnoreCase(""))
          insertPrepStmtObject.addNull();
        else {
          insertPrepStmtObject.addString(vo.getAlternatePhone().trim());
        }
        if (CommonFunction.checkNull(vo.getPrimaryEmail()).trim().equalsIgnoreCase(""))
          insertPrepStmtObject.addNull();
        else {
          insertPrepStmtObject.addString(vo.getPrimaryEmail().trim());
        }
        if (CommonFunction.checkNull(vo.getAlternateEmail()).trim().equalsIgnoreCase(""))
          insertPrepStmtObject.addNull();
        else {
          insertPrepStmtObject.addString(vo.getAlternateEmail().trim());
        }
        if (CommonFunction.checkNull(vo.getWebsite()).trim().equalsIgnoreCase(""))
          insertPrepStmtObject.addNull();
        else {
          insertPrepStmtObject.addString(vo.getWebsite().trim());
        }
        insertPrepStmtObject.addInt(id);

        if (checkTable == 0)
        {
          if (CommonFunction.checkNull(vo.getMakerId()).trim().equalsIgnoreCase(""))
            insertPrepStmtObject.addNull();
          else
            insertPrepStmtObject.addString(vo.getMakerId().trim());
          if (CommonFunction.checkNull(vo.getMakerDate()).trim().equalsIgnoreCase(""))
            insertPrepStmtObject.addNull();
          else {
            insertPrepStmtObject.addString(CommonFunction.checkNull(vo.getMakerDate()).trim());
          }
          if (CommonFunction.checkNull(vo.getMakerId()).trim().equalsIgnoreCase(""))
            insertPrepStmtObject.addNull();
          else
            insertPrepStmtObject.addString(vo.getMakerId().trim());
          if (CommonFunction.checkNull(vo.getMakerDate()).trim().equalsIgnoreCase(""))
            insertPrepStmtObject.addNull();
          else {
            insertPrepStmtObject.addString(CommonFunction.checkNull(vo.getMakerDate()).trim());
          }
          if (CommonFunction.checkNull(vo.getMgmtPAN()).trim().equalsIgnoreCase(""))
            insertPrepStmtObject.addNull();
          else {
            insertPrepStmtObject.addString(CommonFunction.checkNull(vo.getMgmtPAN()).trim());
          }
          if (CommonFunction.checkNull(vo.getAddId1()).trim().equalsIgnoreCase(""))
            insertPrepStmtObject.addNull();
          else {
            insertPrepStmtObject.addString(CommonFunction.checkNull(vo.getAddId1()).trim());
          }
          if (CommonFunction.checkNull(vo.getAddId2()).trim().equalsIgnoreCase(""))
            insertPrepStmtObject.addNull();
          else {
            insertPrepStmtObject.addString(CommonFunction.checkNull(vo.getAddId2()).trim());
          }
        }
        insertPrepStmtObject.setSql(bufInsSql.toString());
        logger.info(new StringBuilder().append("IN saveCustomerAddress() insert query1 ### ").append(insertPrepStmtObject.printQuery()).toString());
        qryList.add(insertPrepStmtObject);

        status = ConnectionDAO.sqlInsUpdDeletePrepStmt(qryList);
        logger.info(new StringBuilder().append("In saveCustomer......................").append(status).toString());
        if ((CommonFunction.checkNull(source).trim().equalsIgnoreCase("ED")) && (status))
        {
          String q1 = new StringBuilder().append("select max(stakeholder_id) from customer_stakeholder_m where CUSTOMER_ID='").append(id).append("' for update ").toString();
          String stakID = ConnectionDAO.singleReturn(q1);
          String q2 = new StringBuilder().append("INSERT INTO customer_stakeholder_m_edit select *  from customer_stakeholder_m WHERE STAKEHOLDER_ID=").append(stakID).toString();
          PrepStmtObject stmt = new PrepStmtObject();
          stmt.setSql(q2);
          ArrayList list = new ArrayList();
          list.add(stmt);
          status = ConnectionDAO.sqlInsUpdDeletePrepStmt(list);
          logger.info(new StringBuilder().append("In Edit Insert Status  :   ").append(status).toString());

          String q3 = new StringBuilder().append("delete from customer_stakeholder_m WHERE STAKEHOLDER_ID=").append(stakID).toString();
          PrepStmtObject stmt2 = new PrepStmtObject();
          stmt2.setSql(q3);
          ArrayList list2 = new ArrayList();
          list2.add(stmt2);
          status = ConnectionDAO.sqlInsUpdDeletePrepStmt(list2);
        }
      }
    }
    catch (Exception e) {
      e.printStackTrace();
    }

    return status;
  }

  private java.sql.Date getSqlDate(String ddMMyyyy)
  {
    SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
    java.sql.Date sqlDate = null;
    try {
      sqlDate = new java.sql.Date(dateFormat.parse(ddMMyyyy).getTime());
    } catch (Exception e) {
      logger.info(new StringBuilder().append("getSqlDate ").append(e.getMessage()).toString());
    }
    return sqlDate;
  }

  public boolean saveCreditRating(Object ob, int id, String source)
  {
    CreditRatingVo vo = (CreditRatingVo)ob;

    boolean status = false;
    ArrayList qryList = new ArrayList();
    PrepStmtObject insertPrepStmtObject = new PrepStmtObject();
    try
    {
      if (((vo.getPageStat() != null) && (vo.getPageStat().equals("fromLeads"))) || ((vo.getUpdateFlag() != null) && (vo.getUpdateFlag().length() > 0)))
      {
        logger.info("In Credit Processing , Customer Entry..,saveCreditRating()......");

        logger.info("In insert saveCreditRating");
        StringBuffer bufInsSql = new StringBuffer();
        bufInsSql.append("insert into cr_deal_customer_rating(CUSTOMER_ID,RATING_AGENCY,CUSTOMER_RATING,RATING_DATE,REC_STATUS,MAKER_ID,MAKER_DATE,AUTHOR_ID,AUTHOR_DATE)");
        bufInsSql.append(" values ( ");
        bufInsSql.append(" ?,");
        bufInsSql.append(" ?,");
        bufInsSql.append(" ?,");
        bufInsSql.append(new StringBuilder().append(" STR_TO_DATE(?,'").append(this.dateFormat).append("'),").toString());
        bufInsSql.append(" ?,");
        bufInsSql.append(" ?,");
        bufInsSql.append(new StringBuilder().append(" DATE_ADD(STR_TO_DATE(?, '").append(this.dateFormatWithTime).append("'),INTERVAL CURTIME() HOUR_SECOND),").toString());
        bufInsSql.append(" ?,");
        bufInsSql.append(new StringBuilder().append(" DATE_ADD(STR_TO_DATE(?, '").append(this.dateFormatWithTime).append("'),INTERVAL CURTIME() HOUR_SECOND))").toString());

        insertPrepStmtObject.addInt(id);

        if (CommonFunction.checkNull(vo.getInstitute()).trim().equalsIgnoreCase(""))
          insertPrepStmtObject.addNull();
        else {
          insertPrepStmtObject.addString(vo.getInstitute().trim());
        }
        if (CommonFunction.checkNull(vo.getRating()).trim().equalsIgnoreCase(""))
          insertPrepStmtObject.addNull();
        else {
          insertPrepStmtObject.addString(vo.getRating().trim());
        }
        if (CommonFunction.checkNull(vo.getCreditDate()).trim().equalsIgnoreCase(""))
          insertPrepStmtObject.addNull();
        else {
          insertPrepStmtObject.addString(vo.getCreditDate().trim());
        }
        insertPrepStmtObject.addString("A");

        if (CommonFunction.checkNull(vo.getMakerId()).trim().equalsIgnoreCase(""))
          insertPrepStmtObject.addNull();
        else
          insertPrepStmtObject.addString(vo.getMakerId().trim());
        if (CommonFunction.checkNull(vo.getMakerDate()).trim().equalsIgnoreCase(""))
          insertPrepStmtObject.addNull();
        else {
          insertPrepStmtObject.addString(CommonFunction.checkNull(vo.getMakerDate()).trim());
        }
        if (CommonFunction.checkNull(vo.getMakerId()).trim().equalsIgnoreCase(""))
          insertPrepStmtObject.addNull();
        else
          insertPrepStmtObject.addString(vo.getMakerId().trim());
        if (CommonFunction.checkNull(vo.getMakerDate()).trim().equalsIgnoreCase(""))
          insertPrepStmtObject.addNull();
        else {
          insertPrepStmtObject.addString(CommonFunction.checkNull(vo.getMakerDate()).trim());
        }

        insertPrepStmtObject.setSql(bufInsSql.toString());
        logger.info(new StringBuilder().append("IN saveCustomerAddress() insert query1 ### ").append(insertPrepStmtObject.printQuery()).toString());
        qryList.add(insertPrepStmtObject);
        status = ConnectionDAO.sqlInsUpdDeletePrepStmt(qryList);
        logger.info(new StringBuilder().append("In saveCreditRating......................").append(status).toString());
      }
      else
      {
        logger.info("In GCD..,saveCreditRating()......&&&&788999999999999999888");
        int maxId = 0;

        StringBuffer bufInsSql = new StringBuffer();
        int checkTable = 0;
        if (!vo.getStatusCase().equals(""))
        {
          String query3 = "Select max(CUSTOMER_RATING_ID) from customer_rating_m_temp for update";
          String maxid = ConnectionDAO.singleReturn(query3);
          logger.info(new StringBuilder().append("maxId : ").append(maxId).toString());
          if (CommonFunction.checkNull(maxid).equalsIgnoreCase(""))
          {
            maxId = 1;
          }
          else
          {
            maxId = Integer.parseInt(maxid) + 1;
          }

          checkTable++;
          bufInsSql.append("insert into customer_rating_m_temp(CUSTOMER_RATING_ID,CUSTOMER_ID,RATING_AGENCY,CUSTOMER_RATING,RATING_DATE)");
          bufInsSql.append(" values ( ");
          bufInsSql.append(" ?,");
        }
        else
        {
          bufInsSql.append("insert into customer_rating_m(CUSTOMER_ID,RATING_AGENCY,CUSTOMER_RATING,RATING_DATE,MAKER_ID,MAKER_DATE,AUTHOR_ID,AUTHOR_DATE)");
          bufInsSql.append(" values ( ");
        }

        bufInsSql.append(" ?,");
        bufInsSql.append(" ?,");
        bufInsSql.append(" ?,");
        if (checkTable == 0)
        {
          bufInsSql.append(new StringBuilder().append(" STR_TO_DATE(?,'").append(this.dateFormat).append("'),").toString());
          bufInsSql.append(" ?,");
          bufInsSql.append(new StringBuilder().append(" DATE_ADD(STR_TO_DATE(?, '").append(this.dateFormatWithTime).append("'),INTERVAL CURTIME() HOUR_SECOND),").toString());
          bufInsSql.append(" ?,");
          bufInsSql.append(new StringBuilder().append(" DATE_ADD(STR_TO_DATE(?, '").append(this.dateFormatWithTime).append("'),INTERVAL CURTIME() HOUR_SECOND))").toString());
        }
        else {
          bufInsSql.append(new StringBuilder().append(" STR_TO_DATE(?,'").append(this.dateFormat).append("') )").toString());
        }

        if (!vo.getStatusCase().equals(""))
        {
          insertPrepStmtObject.addInt(maxId);
        }

        insertPrepStmtObject.addInt(id);

        if (CommonFunction.checkNull(vo.getInstitute()).trim().equalsIgnoreCase(""))
          insertPrepStmtObject.addNull();
        else {
          insertPrepStmtObject.addString(vo.getInstitute().trim());
        }
        if (CommonFunction.checkNull(vo.getRating()).trim().equalsIgnoreCase(""))
          insertPrepStmtObject.addNull();
        else {
          insertPrepStmtObject.addString(vo.getRating().trim());
        }
        if (CommonFunction.checkNull(vo.getCreditDate()).trim().equalsIgnoreCase(""))
          insertPrepStmtObject.addNull();
        else {
          insertPrepStmtObject.addString(vo.getCreditDate().trim());
        }
        if (checkTable == 0)
        {
          if (CommonFunction.checkNull(vo.getMakerId()).trim().equalsIgnoreCase(""))
            insertPrepStmtObject.addNull();
          else
            insertPrepStmtObject.addString(vo.getMakerId().trim());
          if (CommonFunction.checkNull(vo.getMakerDate()).trim().equalsIgnoreCase(""))
            insertPrepStmtObject.addNull();
          else {
            insertPrepStmtObject.addString(CommonFunction.checkNull(vo.getMakerDate()).trim());
          }
          if (CommonFunction.checkNull(vo.getMakerId()).trim().equalsIgnoreCase(""))
            insertPrepStmtObject.addNull();
          else
            insertPrepStmtObject.addString(vo.getMakerId().trim());
          if (CommonFunction.checkNull(vo.getMakerDate()).trim().equalsIgnoreCase(""))
            insertPrepStmtObject.addNull();
          else {
            insertPrepStmtObject.addString(CommonFunction.checkNull(vo.getMakerDate()).trim());
          }
        }

        insertPrepStmtObject.setSql(bufInsSql.toString());
        logger.info(new StringBuilder().append("IN saveCustomerAddress() insert query1 ### ").append(insertPrepStmtObject.printQuery()).toString());
        qryList.add(insertPrepStmtObject);

        status = ConnectionDAO.sqlInsUpdDeletePrepStmt(qryList);
        logger.info(new StringBuilder().append("In saveCreditRating......................").append(status).toString());
        if ((CommonFunction.checkNull(source).trim().equalsIgnoreCase("ED")) && (status))
        {
          String q1 = new StringBuilder().append("select max(customer_rating_id) from customer_rating_m where CUSTOMER_ID='").append(id).append("' for update").toString();
          String ratingID = ConnectionDAO.singleReturn(q1);
          String query = new StringBuilder().append("INSERT INTO customer_rating_m_edit select * from customer_rating_m WHERE CUSTOMER_RATING_ID=").append(ratingID).toString();
          PrepStmtObject stmt = new PrepStmtObject();
          stmt.setSql(query);
          ArrayList list = new ArrayList();
          list.add(stmt);
          status = ConnectionDAO.sqlInsUpdDeletePrepStmt(list);
          logger.info(new StringBuilder().append("Edit Insert Status  :  ").append(status).toString());
          String query2 = new StringBuilder().append("delete from customer_rating_m WHERE CUSTOMER_RATING_ID=").append(ratingID).toString();
          PrepStmtObject stmt2 = new PrepStmtObject();
          stmt2.setSql(query2);
          ArrayList list2 = new ArrayList();
          list2.add(stmt2);
          status = ConnectionDAO.sqlInsUpdDeletePrepStmt(list2);
        }
      }

    }
    catch (Exception e)
    {
      e.printStackTrace();
    }

    return status;
  }

  public ArrayList<Object> getPositionList()
  {
    ArrayList list = new ArrayList();
    try
    {
      String query = "select value,description from generic_master where generic_key='DESIGNATION' and rec_status ='A'";
      logger.info(new StringBuilder().append("getPositionList").append(query).toString());
      StakePositionVo vo = null;
      ArrayList source = ConnectionDAO.sqlSelect(query);
      logger.info(new StringBuilder().append("getPositionList").append(source.size()).toString());
      for (int i = 0; i < source.size(); i++)
      {
        ArrayList subPosition = (ArrayList)source.get(i);
        if (subPosition.size() > 0)
        {
          vo = new StakePositionVo();
          vo.setPosition_code(CommonFunction.checkNull(subPosition.get(0)).toString());
          vo.setPosition_name(CommonFunction.checkNull(subPosition.get(1)).toString());
          list.add(vo);
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    return list;
  }

  public ArrayList<Object> getHolderTypeList()
  {
    ArrayList list = new ArrayList();
    try
    {
      String query = "select value,description from generic_master where generic_key='STAKEHOLDER_TYPE' and rec_status ='A'";
      logger.info(new StringBuilder().append("getHolderList").append(query).toString());
      StakeHolderTypeVo vo = null;
      ArrayList source = ConnectionDAO.sqlSelect(query);
      logger.info(new StringBuilder().append("getHolderList").append(source.size()).toString());
      for (int i = 0; i < source.size(); i++)
      {
        ArrayList subHolder = (ArrayList)source.get(i);
        if (subHolder.size() > 0)
        {
          vo = new StakeHolderTypeVo();
          vo.setSTAKEHOLDER_ID(CommonFunction.checkNull(subHolder.get(0)).toString());
          vo.setSTAKEHOLDER_DESC(CommonFunction.checkNull(subHolder.get(1)).toString());
          list.add(vo);
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    return list;
  }

  public ArrayList<Object> getSalutation()
  {
    ArrayList list = new ArrayList();
    try
    {
      String query = "select value,description from generic_master where GENERIC_KEY='SALUTATION' and rec_status ='A'";
      logger.info(new StringBuilder().append("getSalutation").append(query).toString());
      StakeHolderTypeVo vo = null;
      ArrayList source = ConnectionDAO.sqlSelect(query);
      logger.info(new StringBuilder().append("getSalutation").append(source.size()).toString());
      for (int i = 0; i < source.size(); i++)
      {
        ArrayList subHolder = (ArrayList)source.get(i);
        if (subHolder.size() > 0)
        {
          vo = new StakeHolderTypeVo();
          vo.setSALUTATION_ID(CommonFunction.checkNull(subHolder.get(0)).toString());
          vo.setSALUTATION_DESC(CommonFunction.checkNull(subHolder.get(1)).toString());
          list.add(vo);
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    return list;
  }

  public int saveCorporateDetails(Object ob, String st, String dealId, String source)
  {
    logger.info("In saveCorporateDetails()");
    CorporateDetailsVO corporateDetailVo = (CorporateDetailsVO)ob;
    String fatherName = corporateDetailVo.getFatherHusband();
    int rowcount = 0;
    int maxId = 0;
    boolean checkApp = false;
    PrepStmtObject insertPrepStmtObject = null;
    try
    {
      if ((corporateDetailVo.getPagestatus() != null) && (corporateDetailVo.getPagestatus().equals("fromLeads")))
      {
        if ((corporateDetailVo.getApplType() != null) && (corporateDetailVo.getApplType().equalsIgnoreCase("PRAPPL")))
        {
          String checkQ = new StringBuilder().append("select DEAL_CUSTOMER_ID from cr_deal_dtl where DEAL_ID=").append(dealId).append(" and DEAL_CUSTOMER_ID is not NULL").toString();
          logger.info(new StringBuilder().append("In Cr_deal_dtl. query  :  ").append(checkQ).toString());
          checkApp = ConnectionDAO.checkStatus(checkQ);
        }
        if (!checkApp)
        {
          StringBuffer bufInsSql1 = new StringBuffer();
          PrepStmtObject insPrepStmtObject2 = new PrepStmtObject();
          ArrayList queryList = new ArrayList();
          bufInsSql1.append("Insert into cr_deal_customer_m (CUSTOMER_NAME,CUSTOMER_FNAME,CUSTOMER_MNAME,CUSTOMER_LNAME,");
          bufInsSql1.append("CUSTOMER_TYPE,CUSTOMER_GROUP_ID,CUSTOMER_DOB,CUSTMER_PAN,CUSTOMER_REGISTRATION_TYPE,");
          bufInsSql1.append("CUSTOMER_REGISTRATION_NO,CUSTOMER_VAT_NO,CUSTOMER_CATEGORY,CUSTOMER_CONSTITUTION,");
          bufInsSql1.append("CUSTOMER_BUSINESS_SEGMENT,CUSTOMER_INDUSTRY,CUSTOMER_SUB_INDUSTRY,CUSTOMER_EMAIL,CUSTOMER_WEBSITE,");
          bufInsSql1.append("CUSTOMER_REFERENCE,CUSTOMER_BLACKLIST,CUSTOMER_BLACKLIST_REASON,REC_STATUS,MAKER_ID,MAKER_DATE,AUTHOR_ID,AUTHOR_DATE,FATHER_HUSBAND_NAME,CASTE_CATEGORY,MARITAL_STATUS,DRIVING_LICENSE,VOTER_ID,PASSPORT_NUMBER,UID_NO,OTHER_NO,EDU_DETAIL,CUSTOMER_GROUP_TYPE,CUSTOMER_GROUP_DESC,RELATIONSHIP,GENDER,OTHOR_RELATIONSHIP_TYPE,OTHER_RELATIONSHIP_ID,NATURE_OF_BUSINESS,YEAR_OF_ESTBLISHMENT,SHOP_ESTABLISHMENT_NO,SALES_TAX_TIN_NO,DGFT_NO,NO_BV_YEARS,NO_BV_MONTHS,industry_percent");

          bufInsSql1.append(" ,MOTHER_FIRST_NAME,MOTHER_MIDDLE_NAME,MOTHER_LAST_NAME,CITIZENSHIP,RESIDENTIAL_STATUS,OCCPATION,UIDAI,NRI,PROOF_IDENTITY_DOCUMENT_NO, PROOF_IDENTITY_DOCUMENT_EXP_DATE,PROOF_ADDRESS_DOCUMENT_NO,PROOF_ADDRESS_DOCUMENT_EXP_DATE,RELATED_PERSON_TYPE,RELATED_PERSON_PRIFIX,RELATED_PERSON_FIRST_NAME, RELATED_PERSON_MIDDLE_NAME,RELATED_PERSON_LAST_NAME,RELATED_PERSON_CKYC_NUMBER,RELATED_PERSON_PROOF_IDENTITY_DOCUMENT, RELATED_PERSON_PROOF_OF_IDENTITY_DOCUMENT_NO,RELATED_PERSON_PROOF_IDENTITY_DOCUMENT_EXP_DATE,CKYC,KYC_ACCOUNT_TYPE,CUSTOMER_NAME_PRIFX, PROOF_OF_IDENTITY_DOCUMENT,PROOF_OF_IDENTITY_DOCUMENT_NO,PROOF_OF_IDENTITY_DOCUMENT_EXP_DATE,PROOF_OF_ADDRESS_DOCUMENT,PROOF_OF_ADDRESS_DOCUMENT_NO,PROOF_OF_ADDRESS_DOCUMENT_EXP_DATE,  PROJECTED_SALES,PROJECTED_EXPORTS,MINORITY_COMMUNITY,HANDICAPPED,RISK_CATEGORY,CGTMSE_INDUSTRY )");

          bufInsSql1.append(" values (");
          bufInsSql1.append("?, ");
          bufInsSql1.append("?, ");
          bufInsSql1.append("?, ");
          bufInsSql1.append("?, ");
          bufInsSql1.append("?, ");
          bufInsSql1.append("?, ");
          bufInsSql1.append(new StringBuilder().append("STR_TO_DATE(?,'").append(this.dateFormat).append("'), ").toString());
          bufInsSql1.append("?, ");
          bufInsSql1.append("?, ");
          bufInsSql1.append("?, ");
          bufInsSql1.append("?, ");
          bufInsSql1.append("?, ");
          bufInsSql1.append("?, ");
          bufInsSql1.append("?, ");
          bufInsSql1.append("?, ");
          bufInsSql1.append("?, ");
          bufInsSql1.append("?, ");
          bufInsSql1.append("?, ");
          bufInsSql1.append("?, ");
          bufInsSql1.append("?, ");
          bufInsSql1.append("?, ");
          bufInsSql1.append("?, ");
          bufInsSql1.append("?, ");
          bufInsSql1.append(new StringBuilder().append("DATE_ADD(STR_TO_DATE(?, '").append(this.dateFormatWithTime).append("'),INTERVAL CURTIME() HOUR_SECOND), ").toString());
          bufInsSql1.append("?, ");
          bufInsSql1.append(new StringBuilder().append("DATE_ADD(STR_TO_DATE(?, '").append(this.dateFormatWithTime).append("'),INTERVAL CURTIME() HOUR_SECOND), ").toString());
          bufInsSql1.append("?, ");
          bufInsSql1.append("?, ");
          bufInsSql1.append("?, ");
          bufInsSql1.append("?, ");
          bufInsSql1.append("?, ");
          bufInsSql1.append("?, ");
          bufInsSql1.append("?, ");
          bufInsSql1.append("?, ");
          bufInsSql1.append("?, ");
          bufInsSql1.append("?, ");
          bufInsSql1.append("?,");
          bufInsSql1.append("?, ");
          bufInsSql1.append("?, ");
          bufInsSql1.append("?, ");
          bufInsSql1.append("?, ");
          bufInsSql1.append("?, ");
          bufInsSql1.append("?, ");
          bufInsSql1.append("?, ");
          bufInsSql1.append("?, ");
          bufInsSql1.append("?, ");
          bufInsSql1.append("?, ");
          bufInsSql1.append("?, ");
          bufInsSql1.append("?, ");
          bufInsSql1.append("?, ");
          bufInsSql1.append("?, ");
          bufInsSql1.append("?, ");
          bufInsSql1.append("?, ");
          bufInsSql1.append("?, ");
          bufInsSql1.append("?, ");
          bufInsSql1.append("?, ");
          bufInsSql1.append("?, ");
          bufInsSql1.append("?, ");
          bufInsSql1.append(new StringBuilder().append("DATE_ADD(STR_TO_DATE(?, '").append(this.dateFormatWithTime).append("'),INTERVAL CURTIME() HOUR_SECOND), ").toString());
          bufInsSql1.append("?, ");
          bufInsSql1.append(new StringBuilder().append("DATE_ADD(STR_TO_DATE(?, '").append(this.dateFormatWithTime).append("'),INTERVAL CURTIME() HOUR_SECOND), ").toString());
          bufInsSql1.append("?, ");
          bufInsSql1.append("?, ");
          bufInsSql1.append("?, ");
          bufInsSql1.append("?, ");
          bufInsSql1.append("?, ");
          bufInsSql1.append("?, ");
          bufInsSql1.append("?, ");
          bufInsSql1.append("?, ");
          bufInsSql1.append(new StringBuilder().append("DATE_ADD(STR_TO_DATE(?, '").append(this.dateFormatWithTime).append("'),INTERVAL CURTIME() HOUR_SECOND), ").toString());
          bufInsSql1.append("?, ");
          bufInsSql1.append("?, ");
          bufInsSql1.append("?, ");
          bufInsSql1.append("?, ");
          bufInsSql1.append("?, ");
          bufInsSql1.append("?, ");
          bufInsSql1.append("?, ");
          bufInsSql1.append("?, ");
          bufInsSql1.append("?, ");
          bufInsSql1.append("?, ");
          bufInsSql1.append("?, ");
          bufInsSql1.append("?, ");
          bufInsSql1.append("?, ");
          bufInsSql1.append("?, ");
          bufInsSql1.append("? ) ");

          if (CommonFunction.checkNull(corporateDetailVo.getCorporateName()).trim().equalsIgnoreCase(""))
            insPrepStmtObject2.addNull();
          else {
            insPrepStmtObject2.addString(corporateDetailVo.getCorporateName().trim());
          }
          if (CommonFunction.checkNull(corporateDetailVo.getFirstName()).trim().equalsIgnoreCase(""))
            insPrepStmtObject2.addNull();
          else {
            insPrepStmtObject2.addString(corporateDetailVo.getFirstName().trim());
          }
          if (CommonFunction.checkNull(corporateDetailVo.getMiddleName()).trim().equalsIgnoreCase(""))
            insPrepStmtObject2.addNull();
          else {
            insPrepStmtObject2.addString(corporateDetailVo.getMiddleName().trim());
          }
          if (CommonFunction.checkNull(corporateDetailVo.getLastName()).trim().equalsIgnoreCase(""))
            insPrepStmtObject2.addNull();
          else {
            insPrepStmtObject2.addString(corporateDetailVo.getLastName().trim());
          }
          if (st.equalsIgnoreCase("C"))
          {
            insPrepStmtObject2.addString("C");
          }
          else if (st.equalsIgnoreCase("I"))
          {
            insPrepStmtObject2.addString("I");
          }

          if (CommonFunction.checkNull(corporateDetailVo.gethGroupId()).trim().equalsIgnoreCase(""))
            insPrepStmtObject2.addNull();
          else {
            insPrepStmtObject2.addString(corporateDetailVo.gethGroupId().trim());
          }
          if (CommonFunction.checkNull(corporateDetailVo.getIncorporationDate()).trim().equalsIgnoreCase(""))
            insPrepStmtObject2.addNull();
          else {
            insPrepStmtObject2.addString(corporateDetailVo.getIncorporationDate().trim());
          }
          if (CommonFunction.checkNull(corporateDetailVo.getPan()).trim().equalsIgnoreCase(""))
            insPrepStmtObject2.addNull();
          else {
            insPrepStmtObject2.addString(corporateDetailVo.getPan().trim());
          }
          if (CommonFunction.checkNull(corporateDetailVo.getRegistrationType()).trim().equalsIgnoreCase(""))
            insPrepStmtObject2.addNull();
          else {
            insPrepStmtObject2.addString(corporateDetailVo.getRegistrationType().trim());
          }
          if (CommonFunction.checkNull(corporateDetailVo.getRegistrationNo()).trim().equalsIgnoreCase(""))
            insPrepStmtObject2.addNull();
          else {
            insPrepStmtObject2.addString(corporateDetailVo.getRegistrationNo().trim());
          }
          if (CommonFunction.checkNull(corporateDetailVo.getVatRegNo()).trim().equalsIgnoreCase(""))
            insPrepStmtObject2.addNull();
          else {
            insPrepStmtObject2.addString(corporateDetailVo.getVatRegNo().trim());
          }
          if (CommonFunction.checkNull(corporateDetailVo.getCorporateCategory()).trim().equalsIgnoreCase(""))
            insPrepStmtObject2.addNull();
          else {
            insPrepStmtObject2.addString(corporateDetailVo.getCorporateCategory().trim());
          }
          if (CommonFunction.checkNull(corporateDetailVo.getConstitution()).trim().equalsIgnoreCase(""))
            insPrepStmtObject2.addNull();
          else {
            insPrepStmtObject2.addString(corporateDetailVo.getConstitution().trim());
          }
          if (CommonFunction.checkNull(corporateDetailVo.getBusinessSegment()).trim().equalsIgnoreCase(""))
            insPrepStmtObject2.addNull();
          else {
            insPrepStmtObject2.addString(corporateDetailVo.getBusinessSegment().trim());
          }
          if (CommonFunction.checkNull(corporateDetailVo.getLbxIndustry()).trim().equalsIgnoreCase(""))
            insPrepStmtObject2.addNull();
          else {
            insPrepStmtObject2.addString(corporateDetailVo.getLbxIndustry().trim());
          }
          if (CommonFunction.checkNull(corporateDetailVo.getLbxSubIndustry()).trim().equalsIgnoreCase(""))
            insPrepStmtObject2.addNull();
          else {
            insPrepStmtObject2.addString(corporateDetailVo.getLbxSubIndustry().trim());
          }
          if (CommonFunction.checkNull(corporateDetailVo.getInstitutionEmail()).trim().equalsIgnoreCase(""))
            insPrepStmtObject2.addNull();
          else {
            insPrepStmtObject2.addString(corporateDetailVo.getInstitutionEmail().trim());
          }
          if (CommonFunction.checkNull(corporateDetailVo.getWebAddress()).trim().equalsIgnoreCase(""))
            insPrepStmtObject2.addNull();
          else {
            insPrepStmtObject2.addString(corporateDetailVo.getWebAddress().trim());
          }
          if (CommonFunction.checkNull(corporateDetailVo.getReferredBy()).trim().equalsIgnoreCase(""))
            insPrepStmtObject2.addNull();
          else {
            insPrepStmtObject2.addString(corporateDetailVo.getReferredBy().trim());
          }
          if (corporateDetailVo.getBlackListed().equals("on"))
          {
            insPrepStmtObject2.addString("Y");
          }
          else
          {
            insPrepStmtObject2.addString("N");
          }

          if (CommonFunction.checkNull(corporateDetailVo.getReasonForBlackListed()).trim().equalsIgnoreCase(""))
            insPrepStmtObject2.addNull();
          else {
            insPrepStmtObject2.addString(corporateDetailVo.getReasonForBlackListed().trim());
          }
          insPrepStmtObject2.addString("A");

          if (CommonFunction.checkNull(corporateDetailVo.getMakerId()).trim().equalsIgnoreCase(""))
            insPrepStmtObject2.addNull();
          else {
            insPrepStmtObject2.addString(corporateDetailVo.getMakerId().trim());
          }
          if (CommonFunction.checkNull(corporateDetailVo.getMakerDate()).trim().equalsIgnoreCase(""))
            insPrepStmtObject2.addNull();
          else {
            insPrepStmtObject2.addString(corporateDetailVo.getMakerDate().trim());
          }

          if (CommonFunction.checkNull(corporateDetailVo.getMakerId()).trim().equalsIgnoreCase(""))
            insPrepStmtObject2.addNull();
          else {
            insPrepStmtObject2.addString(corporateDetailVo.getMakerId().trim());
          }
          if (CommonFunction.checkNull(corporateDetailVo.getMakerDate()).trim().equalsIgnoreCase(""))
            insPrepStmtObject2.addNull();
          else {
            insPrepStmtObject2.addString(corporateDetailVo.getMakerDate().trim());
          }
          if (CommonFunction.checkNull(corporateDetailVo.getFatherHusband()).trim().equalsIgnoreCase(""))
            insPrepStmtObject2.addNull();
          else {
            insPrepStmtObject2.addString(corporateDetailVo.getFatherHusband().trim());
          }
          if (CommonFunction.checkNull(corporateDetailVo.getCasteCategory()).trim().equalsIgnoreCase(""))
            insPrepStmtObject2.addNull();
          else {
            insPrepStmtObject2.addString(corporateDetailVo.getCasteCategory().trim());
          }
          if (CommonFunction.checkNull(corporateDetailVo.getMaritalStatus()).trim().equalsIgnoreCase(""))
            insPrepStmtObject2.addNull();
          else {
            insPrepStmtObject2.addString(corporateDetailVo.getMaritalStatus().trim());
          }
          if (CommonFunction.checkNull(corporateDetailVo.getDrivingLicense()).trim().equalsIgnoreCase(""))
            insPrepStmtObject2.addNull();
          else {
            insPrepStmtObject2.addString(corporateDetailVo.getDrivingLicense().trim());
          }
          if (CommonFunction.checkNull(corporateDetailVo.getVoterId()).trim().equalsIgnoreCase(""))
            insPrepStmtObject2.addNull();
          else {
            insPrepStmtObject2.addString(corporateDetailVo.getVoterId().trim());
          }
          if (CommonFunction.checkNull(corporateDetailVo.getPassport()).trim().equalsIgnoreCase(""))
            insPrepStmtObject2.addNull();
          else {
            insPrepStmtObject2.addString(corporateDetailVo.getPassport().trim());
          }
          if (CommonFunction.checkNull(corporateDetailVo.getAadhaar()).trim().equalsIgnoreCase(""))
            insPrepStmtObject2.addNull();
          else {
            insPrepStmtObject2.addString(corporateDetailVo.getAadhaar().trim());
          }
          if (CommonFunction.checkNull(corporateDetailVo.getOther()).trim().equalsIgnoreCase(""))
            insPrepStmtObject2.addNull();
          else {
            insPrepStmtObject2.addString(corporateDetailVo.getOther().trim());
          }
          if (CommonFunction.checkNull(corporateDetailVo.getEduDetailInd()).trim().equalsIgnoreCase(""))
            insPrepStmtObject2.addNull();
          else {
            insPrepStmtObject2.addString(corporateDetailVo.getEduDetailInd().trim());
          }
          if (CommonFunction.checkNull(corporateDetailVo.getGroupType()).trim().equalsIgnoreCase(""))
            insPrepStmtObject2.addNull();
          else {
            insPrepStmtObject2.addString(corporateDetailVo.getGroupType().trim());
          }
          if ((CommonFunction.checkNull(st).trim().equalsIgnoreCase("C")) && (CommonFunction.checkNull(corporateDetailVo.getGroupType()).trim().equalsIgnoreCase("N")))
          {
            if (CommonFunction.checkNull(corporateDetailVo.getGroupNameText()).trim().equalsIgnoreCase(""))
              insPrepStmtObject2.addNull();
            else {
              insPrepStmtObject2.addString(corporateDetailVo.getGroupNameText().trim());
            }

          }
          else if (CommonFunction.checkNull(corporateDetailVo.getGroupName()).trim().equalsIgnoreCase(""))
            insPrepStmtObject2.addNull();
          else {
            insPrepStmtObject2.addString(corporateDetailVo.getGroupName().trim());
          }
          if (CommonFunction.checkNull(corporateDetailVo.getRelationShip()).trim().equalsIgnoreCase(""))
            insPrepStmtObject2.addNull();
          else {
            insPrepStmtObject2.addString(corporateDetailVo.getRelationShip().trim());
          }
          if (CommonFunction.checkNull(corporateDetailVo.getGenderIndividual()).trim().equalsIgnoreCase(""))
            insPrepStmtObject2.addNull();
          else {
            insPrepStmtObject2.addString(corporateDetailVo.getGenderIndividual().trim());
          }
          if (CommonFunction.checkNull(corporateDetailVo.getOtherRelationShip()).trim().equalsIgnoreCase(""))
            insPrepStmtObject2.addNull();
          else {
            insPrepStmtObject2.addString(corporateDetailVo.getOtherRelationShip().trim());
          }
          if (CommonFunction.checkNull(corporateDetailVo.getLbxBusinessPartnearHID()).trim().equalsIgnoreCase(""))
            insPrepStmtObject2.addNull();
          else {
            insPrepStmtObject2.addString(corporateDetailVo.getLbxBusinessPartnearHID().trim());
          }
          if (CommonFunction.checkNull(corporateDetailVo.getNatureOfBus()).trim().equalsIgnoreCase(""))
            insPrepStmtObject2.addNull();
          else
            insPrepStmtObject2.addString(corporateDetailVo.getNatureOfBus().trim());
          if (CommonFunction.checkNull(corporateDetailVo.getYearOfEstb()).trim().equalsIgnoreCase(""))
            insPrepStmtObject2.addNull();
          else
            insPrepStmtObject2.addString(corporateDetailVo.getYearOfEstb().trim());
          if (CommonFunction.checkNull(corporateDetailVo.getShopEstbNo()).trim().equalsIgnoreCase(""))
            insPrepStmtObject2.addNull();
          else
            insPrepStmtObject2.addString(corporateDetailVo.getShopEstbNo().trim());
          if (CommonFunction.checkNull(corporateDetailVo.getSalesTax()).trim().equalsIgnoreCase(""))
            insPrepStmtObject2.addNull();
          else
            insPrepStmtObject2.addString(corporateDetailVo.getSalesTax().trim());
          if (CommonFunction.checkNull(corporateDetailVo.getDgftNo()).trim().equalsIgnoreCase(""))
            insPrepStmtObject2.addNull();
          else {
            insPrepStmtObject2.addString(corporateDetailVo.getDgftNo().trim());
          }
          if (CommonFunction.checkNull(corporateDetailVo.getNoBVYears()).trim().equalsIgnoreCase(""))
            insPrepStmtObject2.addNull();
          else
            insPrepStmtObject2.addString(corporateDetailVo.getNoBVYears().trim());
          if (CommonFunction.checkNull(corporateDetailVo.getNoBVMonths()).trim().equalsIgnoreCase(""))
            insPrepStmtObject2.addNull();
          else {
            insPrepStmtObject2.addString(corporateDetailVo.getNoBVMonths().trim());
          }
          if (CommonFunction.checkNull(corporateDetailVo.getIndustryPercent()).trim().equalsIgnoreCase(""))
            insPrepStmtObject2.addNull();
          else {
            insPrepStmtObject2.addString(corporateDetailVo.getIndustryPercent().trim());
          }

          if (CommonFunction.checkNull(corporateDetailVo.getMotherFName()).trim().equalsIgnoreCase(""))
            insPrepStmtObject2.addNull();
          else {
            insPrepStmtObject2.addString(corporateDetailVo.getMotherFName().trim());
          }
          if (CommonFunction.checkNull(corporateDetailVo.getMotherMName()).trim().equalsIgnoreCase(""))
            insPrepStmtObject2.addNull();
          else {
            insPrepStmtObject2.addString(corporateDetailVo.getMotherMName().trim());
          }
          if (CommonFunction.checkNull(corporateDetailVo.getMotherLName()).trim().equalsIgnoreCase(""))
            insPrepStmtObject2.addNull();
          else {
            insPrepStmtObject2.addString(corporateDetailVo.getMotherLName().trim());
          }
          if (CommonFunction.checkNull(corporateDetailVo.getCitizenship()).trim().equalsIgnoreCase(""))
            insPrepStmtObject2.addNull();
          else {
            insPrepStmtObject2.addString(corporateDetailVo.getCitizenship().trim());
          }
          if (CommonFunction.checkNull(corporateDetailVo.getResidentialStatus()).trim().equalsIgnoreCase(""))
            insPrepStmtObject2.addNull();
          else {
            insPrepStmtObject2.addString(corporateDetailVo.getResidentialStatus().trim());
          }
          if (CommonFunction.checkNull(corporateDetailVo.getOccpation()).trim().equalsIgnoreCase(""))
            insPrepStmtObject2.addNull();
          else {
            insPrepStmtObject2.addString(corporateDetailVo.getOccpation().trim());
          }
          if (CommonFunction.checkNull(corporateDetailVo.getuIDAI()).trim().equalsIgnoreCase(""))
            insPrepStmtObject2.addNull();
          else {
            insPrepStmtObject2.addString(corporateDetailVo.getuIDAI().trim());
          }
          if (CommonFunction.checkNull(corporateDetailVo.getPesTaxPrsJuriOutSideInd()).trim().equalsIgnoreCase(""))
            insPrepStmtObject2.addNull();
          else {
            insPrepStmtObject2.addString(corporateDetailVo.getPesTaxPrsJuriOutSideInd().trim());
          }
          if (CommonFunction.checkNull(corporateDetailVo.getPrfIdentityDocNo()).trim().equalsIgnoreCase(""))
            insPrepStmtObject2.addNull();
          else {
            insPrepStmtObject2.addString(corporateDetailVo.getPrfIdentityDocNo().trim());
          }
          if (CommonFunction.checkNull(corporateDetailVo.getPrfIdentityDocExpDate()).trim().equalsIgnoreCase(""))
            insPrepStmtObject2.addNull();
          else {
            insPrepStmtObject2.addString(corporateDetailVo.getPrfIdentityDocExpDate().trim());
          }
          if (CommonFunction.checkNull(corporateDetailVo.getPrfAddressDocNo()).trim().equalsIgnoreCase(""))
            insPrepStmtObject2.addNull();
          else {
            insPrepStmtObject2.addString(corporateDetailVo.getPrfAddressDocNo().trim());
          }
          if (CommonFunction.checkNull(corporateDetailVo.getPrfAddressDocExpDate()).trim().equalsIgnoreCase(""))
            insPrepStmtObject2.addNull();
          else {
            insPrepStmtObject2.addString(corporateDetailVo.getPrfAddressDocExpDate().trim());
          }
          if (CommonFunction.checkNull(corporateDetailVo.getRelatedPersonType()).trim().equalsIgnoreCase(""))
            insPrepStmtObject2.addNull();
          else {
            insPrepStmtObject2.addString(corporateDetailVo.getRelatedPersonType().trim());
          }
          if (CommonFunction.checkNull(corporateDetailVo.getRelatedPersonPrifix()).trim().equalsIgnoreCase(""))
            insPrepStmtObject2.addNull();
          else {
            insPrepStmtObject2.addString(corporateDetailVo.getRelatedPersonPrifix().trim());
          }
          if (CommonFunction.checkNull(corporateDetailVo.getRelatedPersonFName()).trim().equalsIgnoreCase(""))
            insPrepStmtObject2.addNull();
          else {
            insPrepStmtObject2.addString(corporateDetailVo.getRelatedPersonFName().trim());
          }
          if (CommonFunction.checkNull(corporateDetailVo.getRelatedPersonMName()).trim().equalsIgnoreCase(""))
            insPrepStmtObject2.addNull();
          else {
            insPrepStmtObject2.addString(corporateDetailVo.getRelatedPersonMName().trim());
          }
          if (CommonFunction.checkNull(corporateDetailVo.getRelatedPersonLName()).trim().equalsIgnoreCase(""))
            insPrepStmtObject2.addNull();
          else {
            insPrepStmtObject2.addString(corporateDetailVo.getRelatedPersonLName().trim());
          }
          if (CommonFunction.checkNull(corporateDetailVo.getRelatedPersonCkycNo()).trim().equalsIgnoreCase(""))
            insPrepStmtObject2.addNull();
          else {
            insPrepStmtObject2.addString(corporateDetailVo.getRelatedPersonCkycNo().trim());
          }
          if (CommonFunction.checkNull(corporateDetailVo.getRelatedPersonPrfIdntyDoc()).trim().equalsIgnoreCase(""))
            insPrepStmtObject2.addNull();
          else {
            insPrepStmtObject2.addString(corporateDetailVo.getRelatedPersonPrfIdntyDoc().trim());
          }
          if (CommonFunction.checkNull(corporateDetailVo.getRelatedPersonPrfIdntyDocNo()).trim().equalsIgnoreCase(""))
            insPrepStmtObject2.addNull();
          else {
            insPrepStmtObject2.addString(corporateDetailVo.getRelatedPersonPrfIdntyDocNo().trim());
          }
          if (CommonFunction.checkNull(corporateDetailVo.getRelatedPersonPrfIdntyDocExpDate()).trim().equalsIgnoreCase(""))
            insPrepStmtObject2.addNull();
          else {
            insPrepStmtObject2.addString(corporateDetailVo.getRelatedPersonPrfIdntyDocExpDate().trim());
          }
          if (CommonFunction.checkNull(corporateDetailVo.getcKYC()).trim().equalsIgnoreCase(""))
            insPrepStmtObject2.addNull();
          else {
            insPrepStmtObject2.addString(corporateDetailVo.getcKYC().trim());
          }
          if (CommonFunction.checkNull(corporateDetailVo.getAccountType()).trim().equalsIgnoreCase(""))
            insPrepStmtObject2.addNull();
          else {
            insPrepStmtObject2.addString(corporateDetailVo.getAccountType().trim());
          }
          if (CommonFunction.checkNull(corporateDetailVo.getCustomerNamePrifx()).trim().equalsIgnoreCase(""))
            insPrepStmtObject2.addNull();
          else {
            insPrepStmtObject2.addString(corporateDetailVo.getCustomerNamePrifx().trim());
          }
          if (CommonFunction.checkNull(corporateDetailVo.getProofOfIdentityDocument()).trim().equalsIgnoreCase(""))
            insPrepStmtObject2.addNull();
          else {
            insPrepStmtObject2.addString(corporateDetailVo.getProofOfIdentityDocument().trim());
          }
          if (CommonFunction.checkNull(corporateDetailVo.getProofOfIdentityDocumentNo()).trim().equalsIgnoreCase(""))
            insPrepStmtObject2.addNull();
          else {
            insPrepStmtObject2.addString(corporateDetailVo.getProofOfIdentityDocumentNo().trim());
          }
          if (CommonFunction.checkNull(corporateDetailVo.getProofOfIdentityDocumentExpDate()).trim().equalsIgnoreCase(""))
            insPrepStmtObject2.addNull();
          else {
            insPrepStmtObject2.addString(corporateDetailVo.getProofOfIdentityDocumentExpDate().trim());
          }
          if (CommonFunction.checkNull(corporateDetailVo.getProofOfAddressDocument()).trim().equalsIgnoreCase(""))
            insPrepStmtObject2.addNull();
          else {
            insPrepStmtObject2.addString(corporateDetailVo.getProofOfAddressDocument().trim());
          }
          if (CommonFunction.checkNull(corporateDetailVo.getProofOfAddressDocumentNo()).trim().equalsIgnoreCase(""))
            insPrepStmtObject2.addNull();
          else {
            insPrepStmtObject2.addString(corporateDetailVo.getProofOfAddressDocumentNo().trim());
          }
          if (CommonFunction.checkNull(corporateDetailVo.getProofOfAddressDocumentExpDate()).trim().equalsIgnoreCase(""))
            insPrepStmtObject2.addNull();
          else {
            insPrepStmtObject2.addString(corporateDetailVo.getProofOfAddressDocumentExpDate().trim());
          }

          if (CommonFunction.checkNull(corporateDetailVo.getProjectedSales()).trim().equalsIgnoreCase(""))
            insPrepStmtObject2.addNull();
          else {
            insPrepStmtObject2.addString(corporateDetailVo.getProjectedSales().trim());
          }
          if (CommonFunction.checkNull(corporateDetailVo.getProjectedExports()).trim().equalsIgnoreCase(""))
            insPrepStmtObject2.addNull();
          else {
            insPrepStmtObject2.addString(corporateDetailVo.getProjectedExports().trim());
          }
          if (CommonFunction.checkNull(corporateDetailVo.getMinorityCommunity()).trim().equalsIgnoreCase(""))
            insPrepStmtObject2.addNull();
          else {
            insPrepStmtObject2.addString(corporateDetailVo.getMinorityCommunity().trim());
          }
          if (CommonFunction.checkNull(corporateDetailVo.getHandiCapped()).trim().equalsIgnoreCase(""))
            insPrepStmtObject2.addNull();
          else {
            insPrepStmtObject2.addString(corporateDetailVo.getHandiCapped().trim());
          }

          if (CommonFunction.checkNull(corporateDetailVo.getRiskCategory()).trim().equalsIgnoreCase(""))
            insPrepStmtObject2.addNull();
          else {
            insPrepStmtObject2.addString(corporateDetailVo.getRiskCategory().trim());
          }
          if (CommonFunction.checkNull(corporateDetailVo.getLbxcgtmseIndustry()).trim().equalsIgnoreCase(""))
              insPrepStmtObject2.addNull();
            else {
              insPrepStmtObject2.addString(corporateDetailVo.getLbxcgtmseIndustry().trim());
            }

          insPrepStmtObject2.setSql(bufInsSql1.toString());
          logger.info(new StringBuilder().append("IN saveCorporateDetails() insert query1  : ").append(insPrepStmtObject2.printQuery()).toString());
          queryList.add(insPrepStmtObject2);
          boolean status1 = ConnectionDAO.sqlInsUpdDeletePrepStmt(queryList);
          int s = 0;
          if (status1) {
            s = 1;
          }
          if (s > 0)
          {
            String query1 = "Select max(CUSTOMER_ID) from cr_deal_customer_m for update";
            String strMax = ConnectionDAO.singleReturn(query1);
            maxId = Integer.parseInt(strMax);
            saveCorporateDetails1(ob, st, dealId, maxId);
            CorporateDetailsVO cv = (CorporateDetailsVO)ob;
            ArrayList qryList = new ArrayList();
            StringBuffer bufInsSql = new StringBuffer();
            PrepStmtObject insertPrepStmtObject1 = new PrepStmtObject();
            boolean status = false;
            String stat = "";
            try
            {
              if (!CommonFunction.checkNull(cv.getHiApplType()).trim().equalsIgnoreCase("PRAPPL"))
              {
                bufInsSql.append("Insert into cr_deal_customer_role(DEAL_ID,DEAL_CUSTOMER_ROLE_TYPE,DEAL_CUSTOMER_TYPE,DEAL_EXISTING_CUSTOMER,DEAL_CUSTOMER_ID,STATUS,MAKER_ID,MAKER_DATE)");
                bufInsSql.append(" values ( ");
                bufInsSql.append(" ?,");
                bufInsSql.append(" ?,");
                bufInsSql.append(" ?,");
                bufInsSql.append(" ?,");
                bufInsSql.append(" ?,");
                bufInsSql.append(" ?,");
                bufInsSql.append(" ?,");
                bufInsSql.append(new StringBuilder().append(" DATE_ADD(STR_TO_DATE(?, '").append(this.dateFormatWithTime).append("'),INTERVAL CURTIME() HOUR_SECOND) )").toString());

                if (CommonFunction.checkNull(dealId).trim().equalsIgnoreCase(""))
                  insertPrepStmtObject1.addNull();
                else {
                  insertPrepStmtObject1.addString(dealId.trim());
                }
                if (CommonFunction.checkNull(cv.getHiApplType()).trim().equalsIgnoreCase(""))
                  insertPrepStmtObject1.addNull();
                else {
                  insertPrepStmtObject1.addString(cv.getHiApplType().trim());
                }
                if (st.equalsIgnoreCase("c"))
                {
                  insertPrepStmtObject1.addString("C");
                  stat = "C";
                }
                else if (st.equalsIgnoreCase("i"))
                {
                  insertPrepStmtObject1.addString("I");
                  stat = "I";
                }
                insertPrepStmtObject1.addString("N");
                insertPrepStmtObject1.addString(new StringBuilder().append("").append(maxId).toString());
                insertPrepStmtObject1.addString("P");

                if (CommonFunction.checkNull(corporateDetailVo.getMakerId()).trim().equalsIgnoreCase(""))
                  insertPrepStmtObject1.addNull();
                else {
                  insertPrepStmtObject1.addString(corporateDetailVo.getMakerId().trim());
                }
                if (CommonFunction.checkNull(corporateDetailVo.getMakerDate()).trim().equalsIgnoreCase(""))
                  insertPrepStmtObject1.addNull();
                else {
                  insertPrepStmtObject1.addString(corporateDetailVo.getMakerDate().trim());
                }
                insertPrepStmtObject1.setSql(bufInsSql.toString());
                qryList.add(insertPrepStmtObject1);
                status = ConnectionDAO.sqlInsUpdDeletePrepStmt(qryList);
              }
              else if (CommonFunction.checkNull(cv.getHiApplType()).trim().equalsIgnoreCase("PRAPPL"))
              {
                PrepStmtObject insertPrepStmtObject3 = new PrepStmtObject();
                ArrayList qryList2 = new ArrayList();
                String queryUpdate = "update cr_deal_dtl set DEAL_CUSTOMER_ID=?, DEAL_CUSTOMER_TYPE=?, DEAL_EXISTING_CUSTOMER=? where DEAL_ID=?";

                if (CommonFunction.checkNull(Integer.valueOf(maxId)).trim().equalsIgnoreCase(""))
                  insertPrepStmtObject3.addNull();
                else {
                  insertPrepStmtObject3.addInt(maxId);
                }
                if (CommonFunction.checkNull(st.trim()).trim().equalsIgnoreCase("")) {
                  insertPrepStmtObject3.addNull();
                }
                else {
                  if (st.equalsIgnoreCase("c"))
                  {
                    stat = "C";
                  }
                  else if (st.equalsIgnoreCase("i"))
                  {
                    stat = "I";
                  }
                  insertPrepStmtObject3.addString(stat.trim().trim());
                }

                insertPrepStmtObject3.addString("N");

                if (CommonFunction.checkNull(dealId).trim().equalsIgnoreCase(""))
                  insertPrepStmtObject3.addNull();
                else
                  insertPrepStmtObject3.addString(dealId.trim());
                insertPrepStmtObject3.setSql(queryUpdate.toString());
                qryList2.add(insertPrepStmtObject3);
                status = ConnectionDAO.sqlInsUpdDeletePrepStmt(qryList2);
                if (status)
                {
                  ArrayList qryList3 = new ArrayList();
                  StringBuffer bufInsSql2 = new StringBuffer();
                  PrepStmtObject insertPrepStmtObject2 = new PrepStmtObject();
                  if (st.equals("c"))
                  {
                    st = "C";
                  }
                  else if (st.equals("I"))
                  {
                    st = "I";
                  }
                  bufInsSql2.append("Insert into cr_deal_customer_role(DEAL_ID,DEAL_CUSTOMER_ROLE_TYPE,DEAL_CUSTOMER_TYPE,DEAL_EXISTING_CUSTOMER,DEAL_CUSTOMER_ID,STATUS,MAKER_ID,MAKER_DATE)");
                  bufInsSql2.append(" values ( ");
                  bufInsSql2.append(" ?,");
                  bufInsSql2.append(" ?,");
                  bufInsSql2.append(" ?,");
                  bufInsSql2.append(" ?,");
                  bufInsSql2.append(" ?,");
                  bufInsSql2.append(" ?,");
                  bufInsSql2.append(" ?,");
                  bufInsSql2.append(new StringBuilder().append(" DATE_ADD(STR_TO_DATE(?, '").append(this.dateFormatWithTime).append("'),INTERVAL CURTIME() HOUR_SECOND) )").toString());

                  if (CommonFunction.checkNull(dealId).trim().equalsIgnoreCase(""))
                    insertPrepStmtObject2.addNull();
                  else {
                    insertPrepStmtObject2.addString(dealId.trim());
                  }
                  if (CommonFunction.checkNull(cv.getHiApplType()).trim().equalsIgnoreCase(""))
                    insertPrepStmtObject2.addNull();
                  else {
                    insertPrepStmtObject2.addString(cv.getHiApplType().trim());
                  }
                  if (st.equalsIgnoreCase("c"))
                  {
                    insertPrepStmtObject2.addString("C");
                  }
                  else if (st.equalsIgnoreCase("I"))
                  {
                    insertPrepStmtObject2.addString("I");
                  }

                  insertPrepStmtObject2.addString("N");
                  insertPrepStmtObject2.addString(new StringBuilder().append("").append(maxId).toString());
                  insertPrepStmtObject2.addString("P");

                  if (CommonFunction.checkNull(corporateDetailVo.getMakerId()).trim().equalsIgnoreCase(""))
                    insertPrepStmtObject2.addNull();
                  else {
                    insertPrepStmtObject2.addString(corporateDetailVo.getMakerId().trim());
                  }
                  if (CommonFunction.checkNull(corporateDetailVo.getMakerDate()).trim().equalsIgnoreCase(""))
                    insertPrepStmtObject2.addNull();
                  else {
                    insertPrepStmtObject2.addString(corporateDetailVo.getMakerDate().trim());
                  }

                  insertPrepStmtObject2.setSql(bufInsSql2.toString());
                  logger.info(new StringBuilder().append("IN Applicant insert query1 : ").append(insertPrepStmtObject2.printQuery()).toString());
                  qryList3.add(insertPrepStmtObject2);
                  status = ConnectionDAO.sqlInsUpdDeletePrepStmt(qryList3);
                }
              }
            }
            catch (Exception e) {
              e.printStackTrace();
            }
          }
        }
        else
        {
          maxId = 0;
        }
      }
      else
      {
        logger.info("In GCD..,saveCorporateDetails()......");
        ArrayList qryList = new ArrayList();
        StringBuffer bufInsSql = new StringBuffer();
        insertPrepStmtObject = new PrepStmtObject();
        bufInsSql.append("insert into gcd_customer_m(CUSTOMER_NAME,CUSTOMER_FNAME,CUSTOMER_MNAME,CUSTOMER_LNAME,CUSTOMER_TYPE,CUSTOMER_DOB,CUSTMER_PAN,CUSTOMER_REGISTRATION_TYPE,CUSTOMER_REGISTRATION_NO,CUSTOMER_VAT_NO,CUSTOMER_CATEGORY,CUSTOMER_CONSTITUTION,CUSTOMER_BUSINESS_SEGMENT,CUSTOMER_INDUSTRY,CUSTOMER_SUB_INDUSTRY,CUSTOMER_EMAIL,CUSTOMER_WEBSITE,CUSTOMER_REFERENCE,CUSTOMER_BLACKLIST,CUSTOMER_BLACKLIST_REASON,CUSTOMER_STATUS,GROUP_ID,MAKER_ID,MAKER_DATE,AUTHOR_ID,AUTHOR_DATE,FATHER_HUSBAND_NAME,CASTE_CATEGORY,MARITAL_STATUS,DRIVING_LICENSE,VOTER_ID,PASSPORT_NUMBER,UID_NO,OTHER_NO,EDU_DETAIL,GENDER,OTHOR_RELATIONSHIP_TYPE,OTHER_RELATIONSHIP_ID,NATURE_OF_BUSINESS,YEAR_OF_ESTBLISHMENT,SHOP_ESTABLISHMENT_NO,SALES_TAX_TIN_NO,DGFT_NO,NO_BV_YEARS,NO_BV_MONTHS,industry_percent");

        bufInsSql.append(" ,MOTHER_FIRST_NAME,MOTHER_MIDDLE_NAME,MOTHER_LAST_NAME,CITIZENSHIP,RESIDENTIAL_STATUS,OCCPATION,UIDAI,NRI,PROOF_IDENTITY_DOCUMENT_NO, PROOF_IDENTITY_DOCUMENT_EXP_DATE,PROOF_ADDRESS_DOCUMENT_NO,PROOF_ADDRESS_DOCUMENT_EXP_DATE,RELATED_PERSON_TYPE,RELATED_PERSON_PRIFIX,RELATED_PERSON_FIRST_NAME, RELATED_PERSON_MIDDLE_NAME,RELATED_PERSON_LAST_NAME,RELATED_PERSON_CKYC_NUMBER,RELATED_PERSON_PROOF_IDENTITY_DOCUMENT, RELATED_PERSON_PROOF_OF_IDENTITY_DOCUMENT_NO,RELATED_PERSON_PROOF_IDENTITY_DOCUMENT_EXP_DATE,CKYC,KYC_ACCOUNT_TYPE,CUSTOMER_NAME_PRIFX,  PROOF_OF_IDENTITY_DOCUMENT,PROOF_OF_IDENTITY_DOCUMENT_NO,PROOF_OF_IDENTITY_DOCUMENT_EXP_DATE,PROOF_OF_ADDRESS_DOCUMENT,PROOF_OF_ADDRESS_DOCUMENT_NO,PROOF_OF_ADDRESS_DOCUMENT_EXP_DATE,  PROJECTED_SALES,PROJECTED_EXPORTS,MINORITY_COMMUNITY,HANDICAPPED,RISK_CATEGORY,CGTMSE_INDUSTRY )");

        bufInsSql.append(" values ( ");
        bufInsSql.append(" ?,");
        bufInsSql.append(" ?,");
        bufInsSql.append(" ?,");
        bufInsSql.append(" ?,");
        bufInsSql.append(" ?,");
        bufInsSql.append(new StringBuilder().append(" STR_TO_DATE(?,'").append(this.dateFormat).append("'),").toString());
        bufInsSql.append(" ?,");
        bufInsSql.append(" ?,");
        bufInsSql.append(" ?,");
        bufInsSql.append(" ?,");
        bufInsSql.append(" ?,");
        bufInsSql.append(" ?,");
        bufInsSql.append(" ?,");
        bufInsSql.append(" ?,");
        bufInsSql.append(" ?,");
        bufInsSql.append(" ?,");
        bufInsSql.append(" ?,");
        bufInsSql.append(" ?,");
        bufInsSql.append(" ?,");
        bufInsSql.append(" ?,");
        bufInsSql.append(" ?,");
        bufInsSql.append(" ?,");
        bufInsSql.append(" ?,");
        bufInsSql.append(new StringBuilder().append(" DATE_ADD(STR_TO_DATE(?, '").append(this.dateFormatWithTime).append("'),INTERVAL CURTIME() HOUR_SECOND),").toString());
        bufInsSql.append(" ?,");
        bufInsSql.append(new StringBuilder().append(" DATE_ADD(STR_TO_DATE(?, '").append(this.dateFormatWithTime).append("'),INTERVAL CURTIME() HOUR_SECOND), ").toString());
        bufInsSql.append("?, ");
        bufInsSql.append("?, ");
        bufInsSql.append("?, ");
        bufInsSql.append("?, ");
        bufInsSql.append("?, ");
        bufInsSql.append("?, ");
        bufInsSql.append("?, ");
        bufInsSql.append("?, ");
        bufInsSql.append("?, ");
        bufInsSql.append("?, ");
        bufInsSql.append("?, ");
        bufInsSql.append("?, ");
        bufInsSql.append("?, ");
        bufInsSql.append("?, ");
        bufInsSql.append("?, ");
        bufInsSql.append("?, ");
        bufInsSql.append("?, ");
        bufInsSql.append("?, ");
        bufInsSql.append("?, ");
        bufInsSql.append("?, ");
        bufInsSql.append("?, ");
        bufInsSql.append("?, ");
        bufInsSql.append("?, ");
        bufInsSql.append("?, ");
        bufInsSql.append("?, ");
        bufInsSql.append("?, ");
        bufInsSql.append("?, ");
        bufInsSql.append("?, ");
        bufInsSql.append("?, ");
        bufInsSql.append("?, ");
        bufInsSql.append("?, ");
        bufInsSql.append("?, ");
        bufInsSql.append("?, ");
        bufInsSql.append("?, ");
        bufInsSql.append("?, ");
        bufInsSql.append("?, ");
        bufInsSql.append("?, ");
        bufInsSql.append("?, ");
        bufInsSql.append("?, ");
        bufInsSql.append("?, ");
        bufInsSql.append("?, ");
        bufInsSql.append("?, ");
        bufInsSql.append("?, ");
        bufInsSql.append("?, ");
        bufInsSql.append("?, ");
        bufInsSql.append("?, ");
        bufInsSql.append("?, ");
        bufInsSql.append("?, ");
        bufInsSql.append("?, ");
        bufInsSql.append("?, ");
        bufInsSql.append("?, ");
        bufInsSql.append("?, ");
        bufInsSql.append("?, ");
        bufInsSql.append("?, ");
        bufInsSql.append("?, ");
        bufInsSql.append("? ) ");

        if (CommonFunction.checkNull(corporateDetailVo.getCorporateName()).trim().equalsIgnoreCase(""))
          insertPrepStmtObject.addNull();
        else {
          insertPrepStmtObject.addString(corporateDetailVo.getCorporateName().trim());
        }
        if (CommonFunction.checkNull(corporateDetailVo.getFirstName()).trim().equalsIgnoreCase(""))
          insertPrepStmtObject.addNull();
        else {
          insertPrepStmtObject.addString(corporateDetailVo.getFirstName().trim());
        }
        if (CommonFunction.checkNull(corporateDetailVo.getMiddleName()).trim().equalsIgnoreCase(""))
          insertPrepStmtObject.addNull();
        else {
          insertPrepStmtObject.addString(corporateDetailVo.getMiddleName().trim());
        }
        if (CommonFunction.checkNull(corporateDetailVo.getLastName()).trim().equalsIgnoreCase(""))
          insertPrepStmtObject.addNull();
        else {
          insertPrepStmtObject.addString(corporateDetailVo.getLastName().trim());
        }
        if (CommonFunction.checkNull(st).trim().equalsIgnoreCase("c"))
          insertPrepStmtObject.addString("C");
        else {
          insertPrepStmtObject.addString("I");
        }
        if (CommonFunction.checkNull(corporateDetailVo.getIncorporationDate()).trim().equalsIgnoreCase(""))
          insertPrepStmtObject.addNull();
        else {
          insertPrepStmtObject.addString(corporateDetailVo.getIncorporationDate().trim());
        }
        if (CommonFunction.checkNull(corporateDetailVo.getPan()).trim().equalsIgnoreCase(""))
          insertPrepStmtObject.addNull();
        else {
          insertPrepStmtObject.addString(corporateDetailVo.getPan().trim());
        }
        if (CommonFunction.checkNull(corporateDetailVo.getRegistrationType()).trim().equalsIgnoreCase(""))
          insertPrepStmtObject.addNull();
        else {
          insertPrepStmtObject.addString(corporateDetailVo.getRegistrationType().trim());
        }
        if (CommonFunction.checkNull(corporateDetailVo.getRegistrationNo()).trim().equalsIgnoreCase(""))
          insertPrepStmtObject.addNull();
        else {
          insertPrepStmtObject.addString(corporateDetailVo.getRegistrationNo().trim());
        }
        if (CommonFunction.checkNull(corporateDetailVo.getVatRegNo()).trim().equalsIgnoreCase(""))
          insertPrepStmtObject.addNull();
        else {
          insertPrepStmtObject.addString(corporateDetailVo.getVatRegNo().trim());
        }
        if (CommonFunction.checkNull(corporateDetailVo.getCorporateCategory()).trim().equalsIgnoreCase(""))
          insertPrepStmtObject.addNull();
        else {
          insertPrepStmtObject.addString(corporateDetailVo.getCorporateCategory().trim());
        }
        if (CommonFunction.checkNull(corporateDetailVo.getConstitution()).trim().equalsIgnoreCase(""))
          insertPrepStmtObject.addNull();
        else {
          insertPrepStmtObject.addString(corporateDetailVo.getConstitution().trim());
        }
        if (CommonFunction.checkNull(corporateDetailVo.getBusinessSegment()).trim().equalsIgnoreCase(""))
          insertPrepStmtObject.addNull();
        else {
          insertPrepStmtObject.addString(corporateDetailVo.getBusinessSegment().trim());
        }
        if (CommonFunction.checkNull(corporateDetailVo.getLbxIndustry()).trim().equalsIgnoreCase(""))
          insertPrepStmtObject.addNull();
        else {
          insertPrepStmtObject.addString(corporateDetailVo.getLbxIndustry().trim());
        }
        if (CommonFunction.checkNull(corporateDetailVo.getLbxSubIndustry()).trim().equalsIgnoreCase(""))
          insertPrepStmtObject.addNull();
        else {
          insertPrepStmtObject.addString(corporateDetailVo.getLbxSubIndustry().trim());
        }
        if (CommonFunction.checkNull(corporateDetailVo.getInstitutionEmail()).trim().equalsIgnoreCase(""))
          insertPrepStmtObject.addNull();
        else {
          insertPrepStmtObject.addString(corporateDetailVo.getInstitutionEmail().trim());
        }
        if (CommonFunction.checkNull(corporateDetailVo.getWebAddress()).trim().equalsIgnoreCase(""))
          insertPrepStmtObject.addNull();
        else {
          insertPrepStmtObject.addString(corporateDetailVo.getWebAddress().trim());
        }
        if (CommonFunction.checkNull(corporateDetailVo.getReferredBy()).trim().equalsIgnoreCase(""))
          insertPrepStmtObject.addNull();
        else {
          insertPrepStmtObject.addString(corporateDetailVo.getReferredBy().trim());
        }
        if (CommonFunction.checkNull(corporateDetailVo.getBlackListed()).trim().equalsIgnoreCase("on"))
          insertPrepStmtObject.addString("Y");
        else {
          insertPrepStmtObject.addString("N");
        }
        if (CommonFunction.checkNull(corporateDetailVo.getReasonForBlackListed()).trim().equalsIgnoreCase(""))
          insertPrepStmtObject.addNull();
        else {
          insertPrepStmtObject.addString(corporateDetailVo.getReasonForBlackListed().trim());
        }
        insertPrepStmtObject.addString("P");

        if (CommonFunction.checkNull(corporateDetailVo.gethGroupId()).trim().equalsIgnoreCase(""))
          insertPrepStmtObject.addNull();
        else {
          insertPrepStmtObject.addString(corporateDetailVo.gethGroupId().trim());
        }
        if (CommonFunction.checkNull(corporateDetailVo.getMakerId()).trim().equalsIgnoreCase(""))
          insertPrepStmtObject.addNull();
        else {
          insertPrepStmtObject.addString(corporateDetailVo.getMakerId().trim());
        }
        if (CommonFunction.checkNull(corporateDetailVo.getMakerDate()).trim().equalsIgnoreCase(""))
          insertPrepStmtObject.addNull();
        else {
          insertPrepStmtObject.addString(corporateDetailVo.getMakerDate().trim());
        }
        if (CommonFunction.checkNull(corporateDetailVo.getMakerId()).trim().equalsIgnoreCase(""))
          insertPrepStmtObject.addNull();
        else {
          insertPrepStmtObject.addString(corporateDetailVo.getMakerId().trim());
        }
        if (CommonFunction.checkNull(corporateDetailVo.getMakerDate()).trim().equalsIgnoreCase(""))
          insertPrepStmtObject.addNull();
        else {
          insertPrepStmtObject.addString(corporateDetailVo.getMakerDate().trim());
        }
        if (CommonFunction.checkNull(corporateDetailVo.getFatherHusband()).trim().equalsIgnoreCase(""))
          insertPrepStmtObject.addNull();
        else {
          insertPrepStmtObject.addString(corporateDetailVo.getFatherHusband().trim());
        }
        if (CommonFunction.checkNull(corporateDetailVo.getCasteCategory()).trim().equalsIgnoreCase(""))
          insertPrepStmtObject.addNull();
        else {
          insertPrepStmtObject.addString(corporateDetailVo.getCasteCategory().trim());
        }
        if (CommonFunction.checkNull(corporateDetailVo.getMaritalStatus()).trim().equalsIgnoreCase(""))
          insertPrepStmtObject.addNull();
        else {
          insertPrepStmtObject.addString(corporateDetailVo.getMaritalStatus().trim());
        }
        if (CommonFunction.checkNull(corporateDetailVo.getDrivingLicense()).trim().equalsIgnoreCase(""))
          insertPrepStmtObject.addNull();
        else {
          insertPrepStmtObject.addString(corporateDetailVo.getDrivingLicense().trim());
        }
        if (CommonFunction.checkNull(corporateDetailVo.getVoterId()).trim().equalsIgnoreCase(""))
          insertPrepStmtObject.addNull();
        else {
          insertPrepStmtObject.addString(corporateDetailVo.getVoterId().trim());
        }
        if (CommonFunction.checkNull(corporateDetailVo.getPassport()).trim().equalsIgnoreCase(""))
          insertPrepStmtObject.addNull();
        else {
          insertPrepStmtObject.addString(corporateDetailVo.getPassport().trim());
        }
        if (CommonFunction.checkNull(corporateDetailVo.getAadhaar()).trim().equalsIgnoreCase(""))
          insertPrepStmtObject.addNull();
        else {
          insertPrepStmtObject.addString(corporateDetailVo.getAadhaar().trim());
        }
        if (CommonFunction.checkNull(corporateDetailVo.getOther()).trim().equalsIgnoreCase(""))
          insertPrepStmtObject.addNull();
        else {
          insertPrepStmtObject.addString(corporateDetailVo.getOther().trim());
        }
        if (CommonFunction.checkNull(corporateDetailVo.getEduDetailInd()).trim().equalsIgnoreCase(""))
          insertPrepStmtObject.addNull();
        else {
          insertPrepStmtObject.addString(corporateDetailVo.getEduDetailInd().trim());
        }
        if (CommonFunction.checkNull(corporateDetailVo.getGenderIndividual()).trim().equalsIgnoreCase(""))
          insertPrepStmtObject.addNull();
        else {
          insertPrepStmtObject.addString(corporateDetailVo.getGenderIndividual().trim());
        }
        if (CommonFunction.checkNull(corporateDetailVo.getOtherRelationShip()).trim().equalsIgnoreCase(""))
          insertPrepStmtObject.addNull();
        else {
          insertPrepStmtObject.addString(corporateDetailVo.getOtherRelationShip().trim());
        }
        if (CommonFunction.checkNull(corporateDetailVo.getLbxBusinessPartnearHID()).trim().equalsIgnoreCase(""))
          insertPrepStmtObject.addNull();
        else {
          insertPrepStmtObject.addString(corporateDetailVo.getLbxBusinessPartnearHID().trim());
        }
        if (CommonFunction.checkNull(corporateDetailVo.getNatureOfBus()).trim().equalsIgnoreCase(""))
          insertPrepStmtObject.addNull();
        else
          insertPrepStmtObject.addString(corporateDetailVo.getNatureOfBus().trim());
        if (CommonFunction.checkNull(corporateDetailVo.getYearOfEstb()).trim().equalsIgnoreCase(""))
          insertPrepStmtObject.addNull();
        else
          insertPrepStmtObject.addString(corporateDetailVo.getYearOfEstb().trim());
        if (CommonFunction.checkNull(corporateDetailVo.getShopEstbNo()).trim().equalsIgnoreCase(""))
          insertPrepStmtObject.addNull();
        else
          insertPrepStmtObject.addString(corporateDetailVo.getShopEstbNo().trim());
        if (CommonFunction.checkNull(corporateDetailVo.getSalesTax()).trim().equalsIgnoreCase(""))
          insertPrepStmtObject.addNull();
        else
          insertPrepStmtObject.addString(corporateDetailVo.getSalesTax().trim());
        if (CommonFunction.checkNull(corporateDetailVo.getDgftNo()).trim().equalsIgnoreCase(""))
          insertPrepStmtObject.addNull();
        else {
          insertPrepStmtObject.addString(corporateDetailVo.getDgftNo().trim());
        }
        if (CommonFunction.checkNull(corporateDetailVo.getNoBVYears()).trim().equalsIgnoreCase(""))
          insertPrepStmtObject.addNull();
        else
          insertPrepStmtObject.addString(corporateDetailVo.getNoBVYears().trim());
        if (CommonFunction.checkNull(corporateDetailVo.getNoBVMonths()).trim().equalsIgnoreCase(""))
          insertPrepStmtObject.addNull();
        else {
          insertPrepStmtObject.addString(corporateDetailVo.getNoBVMonths().trim());
        }
        if (CommonFunction.checkNull(corporateDetailVo.getIndustryPercent()).trim().equalsIgnoreCase(""))
          insertPrepStmtObject.addNull();
        else {
          insertPrepStmtObject.addString(corporateDetailVo.getIndustryPercent().trim());
        }

        if (CommonFunction.checkNull(corporateDetailVo.getMotherFName()).trim().equalsIgnoreCase(""))
          insertPrepStmtObject.addNull();
        else {
          insertPrepStmtObject.addString(corporateDetailVo.getMotherFName().trim());
        }
        if (CommonFunction.checkNull(corporateDetailVo.getMotherMName()).trim().equalsIgnoreCase(""))
          insertPrepStmtObject.addNull();
        else {
          insertPrepStmtObject.addString(corporateDetailVo.getMotherMName().trim());
        }
        if (CommonFunction.checkNull(corporateDetailVo.getMotherLName()).trim().equalsIgnoreCase(""))
          insertPrepStmtObject.addNull();
        else {
          insertPrepStmtObject.addString(corporateDetailVo.getMotherLName().trim());
        }
        if (CommonFunction.checkNull(corporateDetailVo.getCitizenship()).trim().equalsIgnoreCase(""))
          insertPrepStmtObject.addNull();
        else {
          insertPrepStmtObject.addString(corporateDetailVo.getCitizenship().trim());
        }
        if (CommonFunction.checkNull(corporateDetailVo.getResidentialStatus()).trim().equalsIgnoreCase(""))
          insertPrepStmtObject.addNull();
        else {
          insertPrepStmtObject.addString(corporateDetailVo.getResidentialStatus().trim());
        }
        if (CommonFunction.checkNull(corporateDetailVo.getOccpation()).trim().equalsIgnoreCase(""))
          insertPrepStmtObject.addNull();
        else {
          insertPrepStmtObject.addString(corporateDetailVo.getOccpation().trim());
        }
        if (CommonFunction.checkNull(corporateDetailVo.getuIDAI()).trim().equalsIgnoreCase(""))
          insertPrepStmtObject.addNull();
        else {
          insertPrepStmtObject.addString(corporateDetailVo.getuIDAI().trim());
        }
        if (CommonFunction.checkNull(corporateDetailVo.getPesTaxPrsJuriOutSideInd()).trim().equalsIgnoreCase(""))
          insertPrepStmtObject.addNull();
        else {
          insertPrepStmtObject.addString(corporateDetailVo.getPesTaxPrsJuriOutSideInd().trim());
        }
        if (CommonFunction.checkNull(corporateDetailVo.getPrfIdentityDocNo()).trim().equalsIgnoreCase(""))
          insertPrepStmtObject.addNull();
        else {
          insertPrepStmtObject.addString(corporateDetailVo.getPrfIdentityDocNo().trim());
        }
        if (CommonFunction.checkNull(corporateDetailVo.getPrfIdentityDocExpDate()).trim().equalsIgnoreCase(""))
          insertPrepStmtObject.addNull();
        else {
          insertPrepStmtObject.addString(corporateDetailVo.getPrfIdentityDocExpDate().trim());
        }
        if (CommonFunction.checkNull(corporateDetailVo.getPrfAddressDocNo()).trim().equalsIgnoreCase(""))
          insertPrepStmtObject.addNull();
        else {
          insertPrepStmtObject.addString(corporateDetailVo.getPrfAddressDocNo().trim());
        }
        if (CommonFunction.checkNull(corporateDetailVo.getPrfAddressDocExpDate()).trim().equalsIgnoreCase(""))
          insertPrepStmtObject.addNull();
        else {
          insertPrepStmtObject.addString(corporateDetailVo.getPrfAddressDocExpDate().trim());
        }
        if (CommonFunction.checkNull(corporateDetailVo.getRelatedPersonType()).trim().equalsIgnoreCase(""))
          insertPrepStmtObject.addNull();
        else {
          insertPrepStmtObject.addString(corporateDetailVo.getRelatedPersonType().trim());
        }
        if (CommonFunction.checkNull(corporateDetailVo.getRelatedPersonPrifix()).trim().equalsIgnoreCase(""))
          insertPrepStmtObject.addNull();
        else {
          insertPrepStmtObject.addString(corporateDetailVo.getRelatedPersonPrifix().trim());
        }
        if (CommonFunction.checkNull(corporateDetailVo.getRelatedPersonFName()).trim().equalsIgnoreCase(""))
          insertPrepStmtObject.addNull();
        else {
          insertPrepStmtObject.addString(corporateDetailVo.getRelatedPersonFName().trim());
        }
        if (CommonFunction.checkNull(corporateDetailVo.getRelatedPersonMName()).trim().equalsIgnoreCase(""))
          insertPrepStmtObject.addNull();
        else {
          insertPrepStmtObject.addString(corporateDetailVo.getRelatedPersonMName().trim());
        }
        if (CommonFunction.checkNull(corporateDetailVo.getRelatedPersonLName()).trim().equalsIgnoreCase(""))
          insertPrepStmtObject.addNull();
        else {
          insertPrepStmtObject.addString(corporateDetailVo.getRelatedPersonLName().trim());
        }
        if (CommonFunction.checkNull(corporateDetailVo.getRelatedPersonCkycNo()).trim().equalsIgnoreCase(""))
          insertPrepStmtObject.addNull();
        else {
          insertPrepStmtObject.addString(corporateDetailVo.getRelatedPersonCkycNo().trim());
        }
        if (CommonFunction.checkNull(corporateDetailVo.getRelatedPersonPrfIdntyDoc()).trim().equalsIgnoreCase(""))
          insertPrepStmtObject.addNull();
        else {
          insertPrepStmtObject.addString(corporateDetailVo.getRelatedPersonPrfIdntyDoc().trim());
        }
        if (CommonFunction.checkNull(corporateDetailVo.getRelatedPersonPrfIdntyDocNo()).trim().equalsIgnoreCase(""))
          insertPrepStmtObject.addNull();
        else {
          insertPrepStmtObject.addString(corporateDetailVo.getRelatedPersonPrfIdntyDocNo().trim());
        }
        if (CommonFunction.checkNull(corporateDetailVo.getRelatedPersonPrfIdntyDocExpDate()).trim().equalsIgnoreCase(""))
          insertPrepStmtObject.addNull();
        else {
          insertPrepStmtObject.addString(corporateDetailVo.getRelatedPersonPrfIdntyDocExpDate().trim());
        }
        if (CommonFunction.checkNull(corporateDetailVo.getcKYC()).trim().equalsIgnoreCase(""))
          insertPrepStmtObject.addNull();
        else {
          insertPrepStmtObject.addString(corporateDetailVo.getcKYC().trim());
        }
        if (CommonFunction.checkNull(corporateDetailVo.getAccountType()).trim().equalsIgnoreCase(""))
          insertPrepStmtObject.addNull();
        else {
          insertPrepStmtObject.addString(corporateDetailVo.getAccountType().trim());
        }
        if (CommonFunction.checkNull(corporateDetailVo.getCustomerNamePrifx()).trim().equalsIgnoreCase(""))
          insertPrepStmtObject.addNull();
        else {
          insertPrepStmtObject.addString(corporateDetailVo.getCustomerNamePrifx().trim());
        }
        if (CommonFunction.checkNull(corporateDetailVo.getProofOfIdentityDocument()).trim().equalsIgnoreCase(""))
          insertPrepStmtObject.addNull();
        else {
          insertPrepStmtObject.addString(corporateDetailVo.getProofOfIdentityDocument().trim());
        }
        if (CommonFunction.checkNull(corporateDetailVo.getProofOfIdentityDocumentNo()).trim().equalsIgnoreCase(""))
          insertPrepStmtObject.addNull();
        else {
          insertPrepStmtObject.addString(corporateDetailVo.getProofOfIdentityDocumentNo().trim());
        }
        if (CommonFunction.checkNull(corporateDetailVo.getProofOfIdentityDocumentExpDate()).trim().equalsIgnoreCase(""))
          insertPrepStmtObject.addNull();
        else {
          insertPrepStmtObject.addString(corporateDetailVo.getProofOfIdentityDocumentExpDate().trim());
        }
        if (CommonFunction.checkNull(corporateDetailVo.getProofOfAddressDocument()).trim().equalsIgnoreCase(""))
          insertPrepStmtObject.addNull();
        else {
          insertPrepStmtObject.addString(corporateDetailVo.getProofOfAddressDocument().trim());
        }
        if (CommonFunction.checkNull(corporateDetailVo.getProofOfAddressDocumentNo()).trim().equalsIgnoreCase(""))
          insertPrepStmtObject.addNull();
        else {
          insertPrepStmtObject.addString(corporateDetailVo.getProofOfAddressDocumentNo().trim());
        }
        if (CommonFunction.checkNull(corporateDetailVo.getProofOfAddressDocumentExpDate()).trim().equalsIgnoreCase(""))
          insertPrepStmtObject.addNull();
        else {
          insertPrepStmtObject.addString(corporateDetailVo.getProofOfAddressDocumentExpDate().trim());
        }

        if (CommonFunction.checkNull(corporateDetailVo.getProjectedSales()).trim().equalsIgnoreCase(""))
          insertPrepStmtObject.addNull();
        else {
          insertPrepStmtObject.addString(corporateDetailVo.getProjectedSales().trim());
        }
        if (CommonFunction.checkNull(corporateDetailVo.getProjectedExports()).trim().equalsIgnoreCase(""))
          insertPrepStmtObject.addNull();
        else {
          insertPrepStmtObject.addString(corporateDetailVo.getProjectedExports().trim());
        }
        if (CommonFunction.checkNull(corporateDetailVo.getMinorityCommunity()).trim().equalsIgnoreCase(""))
          insertPrepStmtObject.addNull();
        else {
          insertPrepStmtObject.addString(corporateDetailVo.getMinorityCommunity().trim());
        }
        if (CommonFunction.checkNull(corporateDetailVo.getHandiCapped()).trim().equalsIgnoreCase(""))
          insertPrepStmtObject.addNull();
        else {
          insertPrepStmtObject.addString(corporateDetailVo.getHandiCapped().trim());
        }
        if (CommonFunction.checkNull(corporateDetailVo.getRiskCategory()).trim().equalsIgnoreCase(""))
          insertPrepStmtObject.addNull();
        else {
          insertPrepStmtObject.addString(corporateDetailVo.getRiskCategory().trim());
        }
        if (CommonFunction.checkNull(corporateDetailVo.getLbxcgtmseIndustry()).trim().equalsIgnoreCase(""))
            insertPrepStmtObject.addNull();
          else {
            insertPrepStmtObject.addString(corporateDetailVo.getLbxcgtmseIndustry().trim());
          }

        insertPrepStmtObject.setSql(bufInsSql.toString());
        logger.info(new StringBuilder().append("IN saveCorporateDetails() insert query1   :  ").append(insertPrepStmtObject.printQuery()).toString());
        qryList.add(insertPrepStmtObject);
        boolean status = ConnectionDAO.sqlInsUpdDeletePrepStmt(qryList);
        if (status)
        {
          String query3 = "Select max(CUSTOMER_ID) from gcd_customer_m for update";
          String id = ConnectionDAO.singleReturn(query3);
          maxId = Integer.parseInt(id);
          logger.info(new StringBuilder().append("maxId : ").append(maxId).toString());
        }
        String gcdID = new StringBuilder().append(maxId).append("").toString();

        StringBuffer bufInsSqlLoan = new StringBuffer();
        PrepStmtObject insPrepStmtObjectLoan = new PrepStmtObject();
        ArrayList queryListLoan = new ArrayList();
        bufInsSqlLoan.append("Insert into cr_loan_customer_role(LOAN_ID,LOAN_CUSTOMER_ROLE_TYPE,LOAN_CUSTOMER_TYPE,LOAN_EXISTING_CUSTOMER,GCD_ID,REC_STATUS,MAKER_ID,MAKER_DATE)");
        bufInsSqlLoan.append(" values ( ");
        bufInsSqlLoan.append(" ?,");
        bufInsSqlLoan.append(" ?,");
        bufInsSqlLoan.append(" ?,");
        bufInsSqlLoan.append(" ?,");
        bufInsSqlLoan.append(" ?,");
        bufInsSqlLoan.append(" ?,");
        bufInsSqlLoan.append(" ?,");
        bufInsSqlLoan.append(new StringBuilder().append(" DATE_ADD(STR_TO_DATE(?, '").append(this.dateFormatWithTime).append("'),INTERVAL CURTIME() HOUR_SECOND) )").toString());

        if (CommonFunction.checkNull(dealId).trim().equalsIgnoreCase(""))
          insPrepStmtObjectLoan.addNull();
        else {
          insPrepStmtObjectLoan.addString(dealId.trim());
        }
        if (CommonFunction.checkNull(corporateDetailVo.getHiApplType()).trim().equalsIgnoreCase(""))
          insPrepStmtObjectLoan.addNull();
        else {
          insPrepStmtObjectLoan.addString(corporateDetailVo.getHiApplType().trim());
        }
        if (st.equalsIgnoreCase("c"))
        {
          insPrepStmtObjectLoan.addString("C");
        }
        else if (st.equalsIgnoreCase("I"))
        {
          insPrepStmtObjectLoan.addString("I");
        }
        insPrepStmtObjectLoan.addString("N");
        insPrepStmtObjectLoan.addString(gcdID);
        insPrepStmtObjectLoan.addString("P");

        if (CommonFunction.checkNull(corporateDetailVo.getMakerId()).trim().equalsIgnoreCase(""))
          insPrepStmtObjectLoan.addNull();
        else {
          insPrepStmtObjectLoan.addString(corporateDetailVo.getMakerId().trim());
        }
        if (CommonFunction.checkNull(corporateDetailVo.getMakerDate()).trim().equalsIgnoreCase(""))
          insPrepStmtObjectLoan.addNull();
        else {
          insPrepStmtObjectLoan.addString(corporateDetailVo.getMakerDate().trim());
        }
        insPrepStmtObjectLoan.setSql(bufInsSqlLoan.toString());
        logger.info(new StringBuilder().append("IN customer insert in CR_LOAN_CUTOMER_ROLE Query  :  ").append(insPrepStmtObjectLoan.printQuery()).toString());
        queryListLoan.add(insPrepStmtObjectLoan);
        status = ConnectionDAO.sqlInsUpdDeletePrepStmt(queryListLoan);

        logger.info(new StringBuilder().append("In saveCorporateDetails......................").append(status).toString());
        if (status)
        {
          String query3 = "Select max(CUSTOMER_ID) from gcd_customer_m for update";
          String id = ConnectionDAO.singleReturn(query3);
          maxId = Integer.parseInt(id);
          logger.info(new StringBuilder().append("maxId : ").append(maxId).toString());
        }

        if ((status) && (CommonFunction.checkNull(source).trim().equalsIgnoreCase("ED")))
        {
          ArrayList queryList = new ArrayList();
          ArrayList queryList2 = new ArrayList();
          String q1 = null; String q5 = null; String q6 = null; String d1 = null; String d2 = null; String query = null;
          PrepStmtObject istmt = new PrepStmtObject();
          PrepStmtObject stmt = new PrepStmtObject();
          PrepStmtObject istmt2 = new PrepStmtObject();
          PrepStmtObject istmt3 = new PrepStmtObject();

          String roleID = "";
          q5 = new StringBuilder().append("SELECT MAX(LOAN_CUSTOMER_ROLE_ID) FROM cr_loan_customer_role WHERE LOAN_ID=").append(dealId).toString();
          roleID = ConnectionDAO.singleReturn(q5);
          q6 = new StringBuilder().append("Insert Into cr_loan_customer_role_edit(LOAN_CUSTOMER_ROLE_ID,LOAN_ID,GCD_ID,LOAN_CUSTOMER_ROLE_TYPE,LOAN_CUSTOMER_TYPE,LOAN_EXISTING_CUSTOMER,GUARANTEE_AMOUNT,REC_STATUS,MAKER_ID,MAKER_DATE,AUTHOR_ID,AUTHOR_DATE)SELECT LOAN_CUSTOMER_ROLE_ID,LOAN_ID,GCD_ID,LOAN_CUSTOMER_ROLE_TYPE,LOAN_CUSTOMER_TYPE,LOAN_EXISTING_CUSTOMER,GUARANTEE_AMOUNT,'P',MAKER_ID,MAKER_DATE,AUTHOR_ID,AUTHOR_DATE FROM cr_loan_customer_role WHERE LOAN_CUSTOMER_ROLE_ID=").append(roleID).toString();

          istmt.setSql(q6);
          queryList.add(istmt);
          q1 = new StringBuilder().append("SELECT EDIT_REFRESH_FLAG FROM cr_loan_dtl WHERE LOAN_ID=").append(dealId).toString();
          logger.info(new StringBuilder().append("IN saveCorporateDetails() of CorpotateDAOImpl  q1  :").append(q1).toString());
          String refreshFlag = ConnectionDAO.singleReturn(q1);
          if (CommonFunction.checkNull(refreshFlag).trim().equalsIgnoreCase(""))
            refreshFlag = " ";
          char fchar = refreshFlag.charAt(0);
          String refreshFlagNew = new StringBuilder().append(fchar).append("NY").toString();
          query = new StringBuilder().append("INSERT INTO gcd_customer_m_edit(CUSTOMER_ID,CUSTOMER_NAME,CUSTOMER_FNAME,CUSTOMER_MNAME,CUSTOMER_LNAME,CUSTOMER_TYPE,CUSTOMER_DOB,CUSTMER_PAN,CUSTOMER_REGISTRATION_TYPE,CUSTOMER_REGISTRATION_NO,CUSTOMER_VAT_NO,CUSTOMER_CATEGORY,CUSTOMER_CONSTITUTION,CUSTOMER_BUSINESS_SEGMENT,CUSTOMER_INDUSTRY,CUSTOMER_SUB_INDUSTRY,CUSTOMER_EMAIL,CUSTOMER_WEBSITE,CUSTOMER_REFERENCE,CUSTOMER_BLACKLIST,CUSTOMER_BLACKLIST_REASON,CUSTOMER_STATUS,GROUP_ID,FATHER_HUSBAND_NAME,CASTE_CATEGORY,MARITAL_STATUS,DRIVING_LICENSE,VOTER_ID,PASSPORT_NUMBER,UID_NO,OTHER_NO,EDU_DETAIL,GENDER,NATURE_OF_BUSINESS,YEAR_OF_ESTBLISHMENT,SHOP_ESTABLISHMENT_NO,SALES_TAX_TIN_NO,DGFT_NO,NO_BV_YEARS,NO_BV_MONTHS,MAKER_ID,MAKER_DATE,AUTHOR_ID,AUTHOR_DATE,customer_profile,OTHOR_RELATIONSHIP_TYPE,OTHER_RELATIONSHIP_ID,BUSINESS_DESC,AGE, CO,NO_OF_VEH,INCOME_SLAB,RELIGION,DOCUMENT_PATH,DOCUMENT_NAME,CUSTOMER_GROUP_DESC,CUSTOMER_GROUP_ID,RELATIONSHIP,DATE_OF_RETIREMENT,deal_customer_id,CUSTOMER_GROUP_TYPE,CUSTOMER_GROUP_EXPOSURE_LIMIT,BUSINESS_DESCRIPTION,BUS_ADDRESS_TYPE,BUS_ADDRESS_LINE1,BUS_ADDRESS_LINE2,BUS_ADDRESS_LINE3,BUS_COUNTRY,BUS_STATE,BUS_DISTRICT,BUS_PINCODE,TAN_NO,CIBIL_DONE,CIBIL_DATE,cibil_id,EXPERIAN_DONE,EXPERIAN_DATE,EXPERIAN_ID,EXPERIAN_SCORE,NUMBER_OF_DEPENDENT,WEAKER_SECTION,EDIT_LOAN_ID,MASTER_CUSTOMER_ID,CUSTOMER_RISK_CATEGORY,CIN_NO,TIN_NO,DATE_OF_INCORPORATION,industry_percent,MOTHER_FIRST_NAME,MOTHER_MIDDLE_NAME,MOTHER_LAST_NAME,CITIZENSHIP,RESIDENTIAL_STATUS,MOTHER_NAME,DEPENDENT_LIST,PHOTO_STREAM,OCCPATION,UIDAI, NRI, PROOF_IDENTITY_DOCUMENT_NO,PROOF_IDENTITY_DOCUMENT_EXP_DATE,PROOF_ADDRESS_DOCUMENT_NO,PROOF_ADDRESS_DOCUMENT_EXP_DATE,RELATED_PERSON_TYPE,RELATED_PERSON_PRIFIX,RELATED_PERSON_FIRST_NAME,RELATED_PERSON_MIDDLE_NAME,RELATED_PERSON_LAST_NAME,RELATED_PERSON_CKYC_NUMBER,RELATED_PERSON_PROOF_IDENTITY_DOCUMENT,RELATED_PERSON_PROOF_OF_IDENTITY_DOCUMENT_NO,RELATED_PERSON_PROOF_IDENTITY_DOCUMENT_EXP_DATE,CKYC,KYC_ACCOUNT_TYPE,CUSTOMER_NAME_PRIFX,PROOF_OF_IDENTITY_DOCUMENT,PROOF_OF_IDENTITY_DOCUMENT_NO,PROOF_OF_IDENTITY_DOCUMENT_EXP_DATE,PROOF_OF_ADDRESS_DOCUMENT,PROOF_OF_ADDRESS_DOCUMENT_NO,PROOF_OF_ADDRESS_DOCUMENT_EXP_DATE,PROJECTED_SALES,PROJECTED_EXPORTS,MINORITY_COMMUNITY,HANDICAPPED,RISK_CATEGORY,DMS_DOC_NUMBER,DMS_OPERATION_DATE,PAN_ID,IS_PAN_VALIDATE,LINKED_CUSTOMER_ID,LINK_CUSTOMER_ID,CKYC_NUMBER,miscotId,JURIDICTION_OUTSIDE_INDIA,APPLICATION_TYPE,CITY_PLACE_OF_BIRTH,OPT_FATHER_HUSBAND,CRIF_ID,NAREGA_NO,PASSPORT_EXPIRY_DATE,DL_EXPIRY_DATE,HUSBAND_NAME,COMMUNITY,CRIF_DONE,CHK_KYC_DTL,Grad_Percentage,CRIF_DATE,LAST_BUREAU,CIBIL_DOCUMENT_ID,CRIF_DOCUMENT_ID,EXPERIAN_DOCUMENT_ID,CIBIL_SCORE,CRIF_SCORE,EMPLOYMENT_STATUS,LATITUDE,LONGITUDE,ADDRESS,EXPERIAN_MATCH,PRIMARY_CONTACT,WEIGHT,FEET,INCHES,MEDICAL_HISTORY,SALUTATION,LEAD_CUSTOMER_ID,UCIC_NO,UCIC_REJECT_REASON,MATCH_TYPE,UCIC_OUTPUT_FILE_NAME,CGTMSE_INDUSTRY,EmailVerificationFlag) select CUSTOMER_ID,CUSTOMER_NAME,CUSTOMER_FNAME,CUSTOMER_MNAME,CUSTOMER_LNAME,CUSTOMER_TYPE,CUSTOMER_DOB,CUSTMER_PAN,CUSTOMER_REGISTRATION_TYPE,CUSTOMER_REGISTRATION_NO,CUSTOMER_VAT_NO,CUSTOMER_CATEGORY,CUSTOMER_CONSTITUTION,CUSTOMER_BUSINESS_SEGMENT,CUSTOMER_INDUSTRY,CUSTOMER_SUB_INDUSTRY,CUSTOMER_EMAIL,CUSTOMER_WEBSITE,CUSTOMER_REFERENCE,CUSTOMER_BLACKLIST,CUSTOMER_BLACKLIST_REASON,CUSTOMER_STATUS,GROUP_ID,FATHER_HUSBAND_NAME,CASTE_CATEGORY,MARITAL_STATUS,DRIVING_LICENSE,VOTER_ID,PASSPORT_NUMBER,UID_NO,OTHER_NO,EDU_DETAIL,GENDER,NATURE_OF_BUSINESS,YEAR_OF_ESTBLISHMENT,SHOP_ESTABLISHMENT_NO,SALES_TAX_TIN_NO,DGFT_NO,NO_BV_YEARS,NO_BV_MONTHS,MAKER_ID,MAKER_DATE,AUTHOR_ID,AUTHOR_DATE,customer_profile,OTHOR_RELATIONSHIP_TYPE,OTHER_RELATIONSHIP_ID,BUSINESS_DESC,AGE, CO,NO_OF_VEH,INCOME_SLAB,RELIGION,DOCUMENT_PATH,DOCUMENT_NAME,CUSTOMER_GROUP_DESC,CUSTOMER_GROUP_ID,RELATIONSHIP,DATE_OF_RETIREMENT,deal_customer_id,CUSTOMER_GROUP_TYPE,CUSTOMER_GROUP_EXPOSURE_LIMIT,BUSINESS_DESCRIPTION,BUS_ADDRESS_TYPE,BUS_ADDRESS_LINE1,BUS_ADDRESS_LINE2,BUS_ADDRESS_LINE3,BUS_COUNTRY,BUS_STATE,BUS_DISTRICT,BUS_PINCODE,TAN_NO,CIBIL_DONE,CIBIL_DATE,cibil_id,EXPERIAN_DONE,EXPERIAN_DATE,EXPERIAN_ID,EXPERIAN_SCORE,NUMBER_OF_DEPENDENT,WEAKER_SECTION,EDIT_LOAN_ID,MASTER_CUSTOMER_ID,CUSTOMER_RISK_CATEGORY,CIN_NO,TIN_NO,DATE_OF_INCORPORATION,industry_percent,MOTHER_FIRST_NAME,MOTHER_MIDDLE_NAME,MOTHER_LAST_NAME,CITIZENSHIP,RESIDENTIAL_STATUS,MOTHER_NAME,DEPENDENT_LIST,PHOTO_STREAM,OCCPATION,UIDAI, NRI, PROOF_IDENTITY_DOCUMENT_NO,PROOF_IDENTITY_DOCUMENT_EXP_DATE,PROOF_ADDRESS_DOCUMENT_NO,PROOF_ADDRESS_DOCUMENT_EXP_DATE,RELATED_PERSON_TYPE,RELATED_PERSON_PRIFIX,RELATED_PERSON_FIRST_NAME,RELATED_PERSON_MIDDLE_NAME,RELATED_PERSON_LAST_NAME,RELATED_PERSON_CKYC_NUMBER,RELATED_PERSON_PROOF_IDENTITY_DOCUMENT,RELATED_PERSON_PROOF_OF_IDENTITY_DOCUMENT_NO,RELATED_PERSON_PROOF_IDENTITY_DOCUMENT_EXP_DATE,CKYC,KYC_ACCOUNT_TYPE,CUSTOMER_NAME_PRIFX,PROOF_OF_IDENTITY_DOCUMENT,PROOF_OF_IDENTITY_DOCUMENT_NO,PROOF_OF_IDENTITY_DOCUMENT_EXP_DATE,PROOF_OF_ADDRESS_DOCUMENT,PROOF_OF_ADDRESS_DOCUMENT_NO,PROOF_OF_ADDRESS_DOCUMENT_EXP_DATE,PROJECTED_SALES,PROJECTED_EXPORTS,MINORITY_COMMUNITY,HANDICAPPED,RISK_CATEGORY,DMS_DOC_NUMBER,DMS_OPERATION_DATE,PAN_ID,IS_PAN_VALIDATE,LINKED_CUSTOMER_ID,LINK_CUSTOMER_ID,CKYC_NUMBER,miscotId,JURIDICTION_OUTSIDE_INDIA,APPLICATION_TYPE,CITY_PLACE_OF_BIRTH,OPT_FATHER_HUSBAND,CRIF_ID,NAREGA_NO,PASSPORT_EXPIRY_DATE,DL_EXPIRY_DATE,HUSBAND_NAME,COMMUNITY,CRIF_DONE,CHK_KYC_DTL,Grad_Percentage,CRIF_DATE,LAST_BUREAU,CIBIL_DOCUMENT_ID,CRIF_DOCUMENT_ID,EXPERIAN_DOCUMENT_ID,CIBIL_SCORE,CRIF_SCORE,EMPLOYMENT_STATUS,LATITUDE,LONGITUDE,ADDRESS,EXPERIAN_MATCH,PRIMARY_CONTACT,WEIGHT,FEET,INCHES,MEDICAL_HISTORY,SALUTATION,LEAD_CUSTOMER_ID,UCIC_NO,UCIC_REJECT_REASON,MATCH_TYPE,UCIC_OUTPUT_FILE_NAME,CGTMSE_INDUSTRY,EmailVerificationFlag from gcd_customer_m WHERE CUSTOMER_ID=").append(gcdID).toString();
          logger.info(new StringBuilder().append("IN saveCorporateDetails() of CorpotateDAOImpl  query  : ").append(query).toString());
          stmt.setSql(query);
          queryList.add(stmt);
          StringBuffer updatLoan = new StringBuffer();
          updatLoan.append(new StringBuilder().append("update cr_loan_dtl set EDIT_REC_STATUS='P',EDIT_REFRESH_FLAG='").append(refreshFlagNew).append("',EDIT_MAKER_ID=?,EDIT_MAKER_DATE=DATE_ADD(STR_TO_DATE(?,'").append(this.dateFormatWithTime).append("'),INTERVAL CURTIME() HOUR_SECOND) where loan_id=").append(dealId).toString());
          PrepStmtObject prepStmt = new PrepStmtObject();
          if (CommonFunction.checkNull(corporateDetailVo.getMakerId()).trim().equalsIgnoreCase(""))
            prepStmt.addNull();
          else {
            prepStmt.addString(corporateDetailVo.getMakerId().trim());
          }
          if (CommonFunction.checkNull(corporateDetailVo.getMakerDate()).trim().equalsIgnoreCase(""))
            prepStmt.addNull();
          else {
            prepStmt.addString(corporateDetailVo.getMakerDate().trim());
          }
          prepStmt.setSql(updatLoan.toString());
          logger.info(new StringBuilder().append("IN saveCorporateDetails() of CorpotateDAOImpl  update query :  ").append(prepStmt.printQuery()).toString());
          queryList.add(prepStmt);
          status = ConnectionDAO.sqlInsUpdDeletePrepStmt(queryList);
          d1 = new StringBuilder().append("delete from gcd_customer_m WHERE CUSTOMER_ID=").append(gcdID).toString();
          istmt2.setSql(d1);
          queryList2.add(istmt2);
          d2 = new StringBuilder().append("delete from gcd_customer_m WHERE CUSTOMER_ID=").append(gcdID).toString();
          istmt3.setSql(d2);
          queryList2.add(istmt3);
          status = ConnectionDAO.sqlInsUpdDeletePrepStmt(queryList2);
          queryList.clear();
          queryList = null;
          queryList2.clear();
          queryList2 = null;
          q1 = null; q5 = null; q6 = null; d1 = null; d2 = null; query = null;
          istmt = null;
          stmt = null;
          istmt2 = null;
          istmt3 = null;
        }
      }
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }

    return maxId;
  }

  public int setApproveStatus(int cId, String statusCase, String userId, String businessDate)
  {
    int status = 0;
    boolean qrystatus = false;
    try {
      logger.info("CorporateDAOImpl setApproveStatus()");
      String query = "";
      if ((statusCase.equalsIgnoreCase("Approved")) || (statusCase.equalsIgnoreCase("UnApproved")))
      {
        query = "update gcd_customer_m_temp set CUSTOMER_STATUS='F' where CUSTOMER_ID=?";
      }
      else
      {
        query = new StringBuilder().append("update gcd_customer_m set CUSTOMER_STATUS='A',MAKER_ID='").append(CommonFunction.checkNull(userId)).append("',").append("MAKER_DATE=DATE_ADD(STR_TO_DATE('").append(CommonFunction.checkNull(businessDate)).append("','").append(this.dateFormatWithTime).append("'),INTERVAL CURTIME() HOUR_SECOND) where CUSTOMER_ID=?").toString();
      }

      PrepStmtObject prepStmtObject = new PrepStmtObject();
      ArrayList qryList = new ArrayList();
      if (CommonFunction.checkNull(Integer.valueOf(cId)).equalsIgnoreCase(""))
        prepStmtObject.addNull();
      else {
        prepStmtObject.addInt(cId);
      }
      prepStmtObject.setSql(query.toString());
      qryList.add(prepStmtObject);
      logger.info(new StringBuilder().append("query").append(query).toString());
      qrystatus = ConnectionDAO.sqlInsUpdDeletePrepStmt(qryList);
    } catch (Exception e) {
      e.printStackTrace();
    }
    if (qrystatus)
    {
      status = 1;
    }
    return status;
  }

  public ArrayList<Object> getIndustryList()
  {
    ArrayList list = new ArrayList();
    try
    {
      String query = "SELECT INDUSTRY_ID,INDUSTRY_DESC from com_industry_m";
      logger.info(new StringBuilder().append("in getIndustryList() of CorpotateDAOImpl Query :  ").append(query).toString());
      IndustryVO vo = null;
      ArrayList source = ConnectionDAO.sqlSelect(query);
      logger.info(new StringBuilder().append("getIndustryList").append(source.size()).toString());
      for (int i = 0; i < source.size(); i++)
      {
        ArrayList subIndustry = (ArrayList)source.get(i);
        if (subIndustry.size() > 0)
        {
          vo = new IndustryVO();
          vo.setIndustryCode(CommonFunction.checkNull(subIndustry.get(0)).toString());
          vo.setIndustryName(CommonFunction.checkNull(subIndustry.get(1)).toString());
          list.add(vo);
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    return list;
  }

  public ArrayList<Object> getSubIndustryList(String industry)
  {
    ArrayList list = new ArrayList();
    try
    {
      String query = new StringBuilder().append("SELECT SUB_INDUSTRY_ID,SUB_INDUSTRY_DESC from com_sub_industry_m where INDUSTRY_ID=").append(industry).toString();

      logger.info(new StringBuilder().append("getSubIndustryList").append(query).toString());
      IndustryVO vo = null;
      ArrayList source = ConnectionDAO.sqlSelect(query);
      logger.info(new StringBuilder().append("getSubIndustryList").append(source.size()).toString());
      for (int i = 0; i < source.size(); i++)
      {
        ArrayList subIndustry = (ArrayList)source.get(i);
        if (subIndustry.size() > 0)
        {
          vo = new IndustryVO();
          vo.setSubIndustryCode(CommonFunction.checkNull(subIndustry.get(0)).toString());
          vo.setSubIndustryName(CommonFunction.checkNull(subIndustry.get(1)).toString());
          list.add(vo);
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    return list;
  }

  public ArrayList<Object> getCustomerDetailList(String code, String name, String pageStatus, String recStatus, String statusCase, int currentPageLink)
  {
    logger.info("in getCustomerDetailList() of CorpotateDAOImpl");
    ArrayList list = new ArrayList();
    String cust_tableName = "";
    StringBuffer bufInsSql = new StringBuffer();
    StringBuffer bufInsSqlTempCount = new StringBuffer();
    String query = "";
    int count = 0;
    int startRecordIndex = 0;
    int endRecordIndex = this.no;

    logger.info(new StringBuilder().append("code id ................................ ").append(code).toString());
    logger.info(new StringBuilder().append("name ................................ ").append(name).toString());
    logger.info(new StringBuilder().append("pageStatus... ").append(pageStatus).append(" recStatus: ").append(recStatus).append(" statusCase: ").append(statusCase).toString());

    if (((pageStatus != null) && (pageStatus.equals("approve"))) || ((statusCase != null) && (statusCase.equals("UnApproved"))))
      cust_tableName = "gcd_customer_m_temp";
    else {
      cust_tableName = "gcd_customer_m";
    }
    String custStatus = "";
    if ((recStatus.equals("PC")) || (recStatus.equals("PI"))) {
      custStatus = "P";
    }
    else if (recStatus.equals("A"))
    {
      if (statusCase.equals("Approved"))
        custStatus = "A";
      if (statusCase.equals("UnApproved"))
        custStatus = "P";
      if (statusCase.equals("")) {
        custStatus = "F";
      }

    }

    try
    {
      bufInsSql.append(new StringBuilder().append("select CUSTOMER_ID,CUSTOMER_NAME,CUSTOMER_TYPE,CUSTOMER_CONSTITUTION,CUSTOMER_BUSINESS_SEGMENT from ").append(cust_tableName).append(" where 1=1 ").toString());
      bufInsSqlTempCount.append(new StringBuilder().append("select count(1) from (select CUSTOMER_ID,CUSTOMER_NAME,CUSTOMER_TYPE,CUSTOMER_CONSTITUTION,CUSTOMER_BUSINESS_SEGMENT from ").append(cust_tableName).append(" where 1=1 ").toString());
      if ((recStatus.equals("A")) && (statusCase.equals("Approved")))
      {
        bufInsSql.append(" and CUSTOMER_ID not in(select CUSTOMER_ID from gcd_customer_m_temp )");
        bufInsSqlTempCount.append(" and CUSTOMER_ID not in(select CUSTOMER_ID from gcd_customer_m_temp )");
      }
      if (!code.equalsIgnoreCase(""))
      {
        bufInsSql.append(new StringBuilder().append(" and CUSTOMER_ID=").append(code).toString());
        bufInsSqlTempCount.append(new StringBuilder().append(" and CUSTOMER_ID=").append(code).toString());
      }
      if (!name.equalsIgnoreCase(""))
      {
        bufInsSql.append(new StringBuilder().append(" and CUSTOMER_NAME like '%").append(name).append("%'").toString());
        bufInsSqlTempCount.append(new StringBuilder().append(" and CUSTOMER_NAME like '%").append(name).append("%'").toString());
      }
      if (recStatus.equals("PC"))
      {
        bufInsSql.append(" and CUSTOMER_TYPE='C'");
        bufInsSqlTempCount.append(" and CUSTOMER_TYPE='C'");
      }
      if (recStatus.equals("PI"))
      {
        bufInsSql.append(" and CUSTOMER_TYPE='I'");
        bufInsSqlTempCount.append(" and CUSTOMER_TYPE='I'");
      }
      bufInsSqlTempCount.append(" ) as b ");
      logger.info(new StringBuilder().append("current PAge Link no .................... ").append(currentPageLink).toString());
      if (currentPageLink > 1)
      {
        startRecordIndex = (currentPageLink - 1) * this.no;
        endRecordIndex = this.no;
        logger.info(new StringBuilder().append("startRecordIndex .................... ").append(startRecordIndex).toString());
        logger.info(new StringBuilder().append("endRecordIndex .................... ").append(endRecordIndex).toString());
      }
      bufInsSql.append(new StringBuilder().append(" limit ").append(startRecordIndex).append(",").append(endRecordIndex).toString());
      logger.info(new StringBuilder().append("in getCustomerDetailList() of CorpotateDAOImpl  search query : ").append(bufInsSql.toString()).toString());
      count = Integer.parseInt(ConnectionDAO.singleReturn(bufInsSqlTempCount.toString()));
      logger.info(new StringBuilder().append("count   :      ").append(count).toString());
      ArrayList customerDetailList = ConnectionDAO.sqlSelect(bufInsSql.toString());
      int size = customerDetailList.size();
      logger.info(new StringBuilder().append("Size  :  ").append(size).toString());
      for (int i = 0; i < size; i++)
      {
        ArrayList data = (ArrayList)customerDetailList.get(i);
        if (data.size() > 0)
        {
          ShowCustomerDetailVo show = new ShowCustomerDetailVo();
          if (recStatus.equals("PC"))
            show.setId(new StringBuilder().append("<a href=corporateEntryForm.do?method=displayCorporateForms&operation=update&hideId=").append(CommonFunction.checkNull(data.get(0)).trim()).append("&cType=C>").append(CommonFunction.checkNull(data.get(0)).trim()).append("</a>").toString());
          if (recStatus.equals("PI"))
            show.setId(new StringBuilder().append("<a href=corporateEntryForm.do?method=displayCorporateForms&operation=update&hideId=").append(CommonFunction.checkNull(data.get(0)).trim()).append("&cType=I>").append(CommonFunction.checkNull(data.get(0)).trim()).append("</a>").toString());
          if (recStatus.equals("A")) {
            show.setId(new StringBuilder().append("<a href=corporateEntryForm.do?method=displayCorporateForms&operation=update&hideId=").append(CommonFunction.checkNull(data.get(0)).trim()).append("&cType=").append(CommonFunction.checkNull(data.get(2)).trim()).append("&recStatus=A>").append(CommonFunction.checkNull(data.get(0)).trim()).append("</a>").toString());
          }
          if (CommonFunction.checkNull(data.get(2)).toString().equals("C"))
          {
            show.setfCustType("CORPORATE");
            show.setName(CommonFunction.checkNull(data.get(1)).toString());
          }
          else
          {
            show.setfCustType("INDIVIDUAL");
            show.setName(CommonFunction.checkNull(data.get(1)).toString());
          }
          show.setType(CommonFunction.checkNull(data.get(2)).toString());
          show.setCustContitution(CommonFunction.checkNull(data.get(3)).toString());
          show.setBusinessSegment(CommonFunction.checkNull(data.get(4)).toString());
          show.setTotalRecordSize(count);
          list.add(show);
        }
      }
    }
    catch (Exception e) {
      e.printStackTrace();
    }return list;
  }

  public ArrayList<ShowCustomerDetailVo> getDefaultIndividualList(int currentPageLink)
  {
    logger.info("in getDefaultIndividualList() of CorpotateDAOImpl().");
    ArrayList resultList = new ArrayList();
    ArrayList list = new ArrayList();
    StringBuffer bufInsSql = new StringBuffer();
    StringBuffer bufInsSqlTempCount = new StringBuffer();
    int count = 0;
    int startRecordIndex = 0;
    int endRecordIndex = this.no;
    try
    {
      bufInsSql.append("select CUSTOMER_ID,CUSTOMER_NAME,CUSTOMER_TYPE,CUSTOMER_CONSTITUTION from gcd_customer_m where CUSTOMER_STATUS='P' and CUSTOMER_TYPE='I'");
      bufInsSqlTempCount.append("select count(1) from (select CUSTOMER_ID,CUSTOMER_NAME,CUSTOMER_TYPE,CUSTOMER_CONSTITUTION from gcd_customer_m where CUSTOMER_STATUS='P' and CUSTOMER_TYPE='I' ) as b");

      logger.info(new StringBuilder().append("current PAge Link no .................... ").append(currentPageLink).toString());
      if (currentPageLink > 1)
      {
        startRecordIndex = (currentPageLink - 1) * this.no;
        endRecordIndex = this.no;
        logger.info(new StringBuilder().append("startRecordIndex .................... ").append(startRecordIndex).toString());
        logger.info(new StringBuilder().append("endRecordIndex .................... ").append(endRecordIndex).toString());
      }
      bufInsSql.append(new StringBuilder().append(" limit ").append(startRecordIndex).append(",").append(endRecordIndex).toString());
      logger.info(new StringBuilder().append("in getDefaultIndividualList() of CorpotateDAOImpl  Query : ").append(bufInsSql.toString()).toString());
      count = Integer.parseInt(ConnectionDAO.singleReturn(bufInsSqlTempCount.toString()));
      list = ConnectionDAO.sqlSelect(bufInsSql.toString());
      int size = list.size();
      logger.info(new StringBuilder().append("size  :  ").append(size).toString());
      for (int i = 0; i < size; i++)
      {
        ArrayList data = (ArrayList)list.get(i);
        if (data.size() > 0)
        {
          ShowCustomerDetailVo show = new ShowCustomerDetailVo();
          show.setId(new StringBuilder().append("<a href=corporateEntryForm.do?method=displayCorporateForms&operation=update&hideId=").append(CommonFunction.checkNull(data.get(0)).trim()).append("&cType=I&recStatus=PI>").append(CommonFunction.checkNull(data.get(0)).trim()).append("</a>").toString());
          show.setName(CommonFunction.checkNull(data.get(1)).toString());
          if (CommonFunction.checkNull(data.get(2)).toString().equals("I"))
            show.setfCustType("INDIVIDUAL");
          show.setCustContitution(CommonFunction.checkNull(data.get(3)).toString());
          show.setTotalRecordSize(count);
          resultList.add(show);
        }
      }
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }return resultList;
  }

  public ArrayList<ShowCustomerDetailVo> getDefaultUpdateCustomerAuthorList(int currentPageLink, String userId)
  {
    logger.info("in getDefaultUpdateCustomerAuthorList() of CorpotateDAOImpl().");
    ArrayList resultList = new ArrayList();
    ArrayList list = new ArrayList();
    StringBuffer bufInsSql = new StringBuffer();
    StringBuffer bufInsSqlTempCount = new StringBuffer();
    int count = 0;
    int startRecordIndex = 0;
    int endRecordIndex = this.no;
    try
    {
      bufInsSql.append(new StringBuilder().append("select CUSTOMER_ID,CUSTOMER_NAME,CUSTOMER_TYPE,CUSTOMER_CONSTITUTION,CUSTOMER_BUSINESS_SEGMENT from gcd_customer_m_temp where CUSTOMER_STATUS='F' AND IFNULL(MAKER_ID,'')!='").append(CommonFunction.checkNull(userId)).append("'").toString());
      bufInsSqlTempCount.append(new StringBuilder().append("select count(1) from (select CUSTOMER_ID,CUSTOMER_NAME,CUSTOMER_TYPE,CUSTOMER_CONSTITUTION,CUSTOMER_BUSINESS_SEGMENT from gcd_customer_m_temp where CUSTOMER_STATUS='F' AND IFNULL(MAKER_ID,'')!='").append(CommonFunction.checkNull(userId)).append("') as b").toString());

      logger.info(new StringBuilder().append("current PAge Link no .................... ").append(currentPageLink).toString());
      if (currentPageLink > 1)
      {
        startRecordIndex = (currentPageLink - 1) * this.no;
        endRecordIndex = this.no;
        logger.info(new StringBuilder().append("startRecordIndex .................... ").append(startRecordIndex).toString());
        logger.info(new StringBuilder().append("endRecordIndex .................... ").append(endRecordIndex).toString());
      }
      bufInsSql.append(new StringBuilder().append(" limit ").append(startRecordIndex).append(",").append(endRecordIndex).toString());
      logger.info(new StringBuilder().append("in getDefaultUpdateCustomerAuthorList() of CorpotateDAOImpl  Query : ").append(bufInsSql.toString()).toString());
      count = Integer.parseInt(ConnectionDAO.singleReturn(bufInsSqlTempCount.toString()));
      list = ConnectionDAO.sqlSelect(bufInsSql.toString());
      int size = list.size();
      logger.info(new StringBuilder().append("size  :  ").append(size).toString());
      for (int i = 0; i < size; i++)
      {
        ArrayList data = (ArrayList)list.get(i);
        if (data.size() > 0)
        {
          ShowCustomerDetailVo show = new ShowCustomerDetailVo();
          show.setId(new StringBuilder().append("<a href=corporateEntryForm.do?method=displayCorporateForms&operation=update&hideId=").append(CommonFunction.checkNull(data.get(0)).trim()).append("&cType=").append(CommonFunction.checkNull(data.get(2)).trim()).append("&recStatus=A>").append(CommonFunction.checkNull(data.get(0)).trim()).append("</a>").toString());
          show.setName(CommonFunction.checkNull(data.get(1)).toString());
          if (CommonFunction.checkNull(data.get(2)).toString().equals("C"))
            show.setfCustType("CORPORATE");
          if (CommonFunction.checkNull(data.get(2)).toString().equals("I"))
            show.setfCustType("INDIVIDUAL");
          show.setCustContitution(CommonFunction.checkNull(data.get(3)).toString());
          show.setBusinessSegment(CommonFunction.checkNull(data.get(4)).toString());
          show.setTotalRecordSize(count);
          resultList.add(show);
        }
      }
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }return resultList;
  }

  public ArrayList<ShowCustomerDetailVo> getDefaultUpdateCustomerMakerList(int currentPageLink) {
    logger.info("in getDefaultUpdateCustomerMakerList() of CorpotateDAOImpl().");
    ArrayList resultList = new ArrayList();
    ArrayList list = new ArrayList();
    StringBuffer bufInsSql = new StringBuffer();
    StringBuffer bufInsSqlTempCount = new StringBuffer();
    int count = 0;
    int startRecordIndex = 0;
    int endRecordIndex = this.no;
    try
    {
      bufInsSql.append("select CUSTOMER_ID,CUSTOMER_NAME,CUSTOMER_TYPE,CUSTOMER_CONSTITUTION,CUSTOMER_BUSINESS_SEGMENT from gcd_customer_m where  CUSTOMER_STATUS='A' and CUSTOMER_ID not in(select CUSTOMER_ID from gcd_customer_m_temp )");
      bufInsSqlTempCount.append("select count(1) from (select CUSTOMER_ID,CUSTOMER_NAME,CUSTOMER_TYPE,CUSTOMER_CONSTITUTION,CUSTOMER_BUSINESS_SEGMENT from gcd_customer_m where  CUSTOMER_STATUS='A' and CUSTOMER_ID not in(select CUSTOMER_ID from gcd_customer_m_temp )) as b");

      logger.info(new StringBuilder().append("current PAge Link no .................... ").append(currentPageLink).toString());
      if (currentPageLink > 1)
      {
        startRecordIndex = (currentPageLink - 1) * this.no;
        endRecordIndex = this.no;
        logger.info(new StringBuilder().append("startRecordIndex .................... ").append(startRecordIndex).toString());
        logger.info(new StringBuilder().append("endRecordIndex .................... ").append(endRecordIndex).toString());
      }
      bufInsSql.append(new StringBuilder().append(" limit ").append(startRecordIndex).append(",").append(endRecordIndex).toString());
      logger.info(new StringBuilder().append("in getDefaultUpdateCustomerMakerList() of CorpotateDAOImpl  Query : ").append(bufInsSql.toString()).toString());
      count = Integer.parseInt(ConnectionDAO.singleReturn(bufInsSqlTempCount.toString()));
      list = ConnectionDAO.sqlSelect(bufInsSql.toString());
      int size = list.size();
      logger.info(new StringBuilder().append("size  :  ").append(size).toString());
      for (int i = 0; i < size; i++)
      {
        ArrayList data = (ArrayList)list.get(i);
        if (data.size() > 0)
        {
          ShowCustomerDetailVo show = new ShowCustomerDetailVo();
          show.setId(new StringBuilder().append("<a href=corporateEntryForm.do?method=displayCorporateForms&operation=update&hideId=").append(CommonFunction.checkNull(data.get(0)).trim()).append("&cType=").append(CommonFunction.checkNull(data.get(2)).trim()).append("&recStatus=A>").append(CommonFunction.checkNull(data.get(0)).trim()).append("</a>").toString());
          show.setName(CommonFunction.checkNull(data.get(1)).toString());
          if (CommonFunction.checkNull(data.get(2)).toString().equals("C"))
            show.setfCustType("CORPORATE");
          if (CommonFunction.checkNull(data.get(2)).toString().equals("I"))
            show.setfCustType("INDIVIDUAL");
          show.setCustContitution(CommonFunction.checkNull(data.get(3)).toString());
          show.setBusinessSegment(CommonFunction.checkNull(data.get(4)).toString());
          show.setTotalRecordSize(count);
          resultList.add(show);
        }
      }
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }return resultList;
  }

  public ArrayList<ShowCustomerDetailVo> getDefaultCorporateList(int currentPageLink) {
    logger.info("in getDefaultIndividualList() of CorpotateDAOImpl().");
    ArrayList resultList = new ArrayList();
    ArrayList list = new ArrayList();
    StringBuffer bufInsSql = new StringBuffer();
    StringBuffer bufInsSqlTempCount = new StringBuffer();
    int count = 0;
    int startRecordIndex = 0;
    int endRecordIndex = this.no;
    try
    {
      bufInsSql.append("select CUSTOMER_ID,CUSTOMER_NAME,CUSTOMER_TYPE,CUSTOMER_CONSTITUTION,CUSTOMER_BUSINESS_SEGMENT from gcd_customer_m where  CUSTOMER_STATUS='P' and CUSTOMER_TYPE='C'");
      bufInsSqlTempCount.append("select count(1) from (select CUSTOMER_ID,CUSTOMER_NAME,CUSTOMER_TYPE,CUSTOMER_CONSTITUTION,CUSTOMER_BUSINESS_SEGMENT from gcd_customer_m where  CUSTOMER_STATUS='P' and CUSTOMER_TYPE='C') as b");

      logger.info(new StringBuilder().append("current PAge Link no .................... ").append(currentPageLink).toString());
      if (currentPageLink > 1)
      {
        startRecordIndex = (currentPageLink - 1) * this.no;
        endRecordIndex = this.no;
        logger.info(new StringBuilder().append("startRecordIndex .................... ").append(startRecordIndex).toString());
        logger.info(new StringBuilder().append("endRecordIndex .................... ").append(endRecordIndex).toString());
      }
      bufInsSql.append(new StringBuilder().append(" limit ").append(startRecordIndex).append(",").append(endRecordIndex).toString());
      logger.info(new StringBuilder().append("in getDefaultCorporateList() of CorpotateDAOImpl  Query : ").append(bufInsSql.toString()).toString());
      count = Integer.parseInt(ConnectionDAO.singleReturn(bufInsSqlTempCount.toString()));
      list = ConnectionDAO.sqlSelect(bufInsSql.toString());
      int size = list.size();
      logger.info(new StringBuilder().append("size  :  ").append(size).toString());
      for (int i = 0; i < size; i++)
      {
        ArrayList data = (ArrayList)list.get(i);
        if (data.size() > 0)
        {
          ShowCustomerDetailVo show = new ShowCustomerDetailVo();
          show.setId(new StringBuilder().append("<a href=corporateEntryForm.do?method=displayCorporateForms&operation=update&hideId=").append(CommonFunction.checkNull(data.get(0)).trim()).append("&cType=C&recStatus=PC>").append(CommonFunction.checkNull(data.get(0)).trim()).append("</a>").toString());
          show.setName(CommonFunction.checkNull(data.get(1)).toString());
          if (CommonFunction.checkNull(data.get(2)).toString().equals("C"))
            show.setfCustType("CORPORATE");
          show.setCustContitution(CommonFunction.checkNull(data.get(3)).toString());
          show.setBusinessSegment(CommonFunction.checkNull(data.get(4)).toString());
          show.setTotalRecordSize(count);
          resultList.add(show);
        }
      }
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }return resultList;
  }

  public ArrayList<Object> getIndividualDetails(String code, Object pageStatus, String updateInMaker, String statusCase, String updateFlag, String pageStatuss, String source)
  {
    logger.info("In getIndividualDetails() of CortateDAOImpl.");
    ArrayList list = new ArrayList();
    String tableName = "";
    String query = "";
    StringBuilder str = new StringBuilder();
    if (((pageStatuss != null) && (pageStatuss.equals("fromLeads"))) || ((updateFlag != null) && (updateFlag.equals("updateFlag"))) || ((updateFlag != null) && (updateFlag.equals("notEdit"))))
    {
      tableName = "cr_deal_customer_m";
      try
      {
        String custStatus = "";
        String OthRelType = "";
        String cusGroupType = "";
        custStatus = ConnectionDAO.singleReturn(new StringBuilder().append("SELECT DEAL_EXISTING_CUSTOMER from cr_deal_customer_role where DEAL_CUSTOMER_ID=").append(code).toString());
        OthRelType = ConnectionDAO.singleReturn(new StringBuilder().append("SELECT OTHOR_RELATIONSHIP_TYPE FROM cr_deal_customer_m WHERE CUSTOMER_ID=").append(code).append("").toString());
        cusGroupType = ConnectionDAO.singleReturn(new StringBuilder().append("SELECT customer_group_type FROM cr_deal_customer_m WHERE CUSTOMER_ID=").append(code).append("").toString());

        if (OthRelType.equalsIgnoreCase("CS"))
        {
          str.append(new StringBuilder().append("select a.CUSTOMER_ID, a.CUSTOMER_FNAME,a.CUSTOMER_MNAME,a.CUSTOMER_LNAME,a.CUSTOMER_EMAIL,DATE_FORMAT(a.CUSTOMER_DOB,'").append(this.dateFormat).append("'),a.CUSTMER_PAN,a.CUSTOMER_REFERENCE,a.CUSTOMER_BLACKLIST,a.CUSTOMER_BLACKLIST_REASON,a.CUSTOMER_CATEGORY,a.CUSTOMER_TYPE,a.CUSTOMER_CONSTITUTION,a.FATHER_HUSBAND_NAME,a.CASTE_CATEGORY,a.MARITAL_STATUS,a.DRIVING_LICENSE,a.VOTER_ID,").append(" a.PASSPORT_NUMBER,a.UID_NO,a.OTHER_NO,a.EDU_DETAIL,a.RELATIONSHIP,a.GENDER,a.OTHOR_RELATIONSHIP_TYPE,a.OTHER_RELATIONSHIP_ID,a.CUSTOMER_INDUSTRY,a.CUSTOMER_SUB_INDUSTRY,c.INDUSTRY_DESC ,s.SUB_INDUSTRY_DESC,A.customer_group_id,case customer_group_type when 'E' then B.group_desc when 'N' then A.customer_group_desc end as customer_group_desc,A.customer_group_type,d.dealer_desc, ").append(" a.MOTHER_FIRST_NAME,a.MOTHER_MIDDLE_NAME,a.MOTHER_LAST_NAME,a.CITIZENSHIP,a.RESIDENTIAL_STATUS,a.OCCPATION,a.UIDAI,a.NRI,a.PROOF_IDENTITY_DOCUMENT_NO,").append(" DATE_FORMAT(a.PROOF_IDENTITY_DOCUMENT_EXP_DATE,'").append(this.dateFormat).append("'),a.PROOF_ADDRESS_DOCUMENT_NO,DATE_FORMAT(a.PROOF_ADDRESS_DOCUMENT_EXP_DATE,'").append(this.dateFormat).append("'),a.RELATED_PERSON_TYPE,a.RELATED_PERSON_PRIFIX,").append(" a.RELATED_PERSON_FIRST_NAME,a.RELATED_PERSON_MIDDLE_NAME,a.RELATED_PERSON_LAST_NAME,a.RELATED_PERSON_CKYC_NUMBER,a.RELATED_PERSON_PROOF_IDENTITY_DOCUMENT,").append(" a.RELATED_PERSON_PROOF_OF_IDENTITY_DOCUMENT_NO,DATE_FORMAT(a.RELATED_PERSON_PROOF_IDENTITY_DOCUMENT_EXP_DATE,'").append(this.dateFormat).append("'),a.CKYC,a.KYC_ACCOUNT_TYPE,a.CUSTOMER_NAME_PRIFX,").append(" a.PROOF_OF_IDENTITY_DOCUMENT,a.PROOF_OF_IDENTITY_DOCUMENT_NO,a.PROOF_OF_IDENTITY_DOCUMENT_EXP_DATE,a.PROOF_OF_ADDRESS_DOCUMENT,a.PROOF_OF_ADDRESS_DOCUMENT_NO,a.PROOF_OF_ADDRESS_DOCUMENT_EXP_DATE,").append(" a.PROJECTED_SALES,a.PROJECTED_EXPORTS,a.MINORITY_COMMUNITY,a.HANDICAPPED,a.RISK_CATEGORY,a.UCIC_NO,a.CGTMSE_INDUSTRY,gr.DESCRIPTION ").append("from ").append(tableName).append(" a left join cr_dsa_dealer_m d on a.OTHOR_RELATIONSHIP_TYPE=d.bp_type and a.OTHER_RELATIONSHIP_ID=d.dealer_id  left join com_industry_m c on a.CUSTOMER_INDUSTRY=c.INDUSTRY_ID left join com_sub_industry_m s on a.CUSTOMER_SUB_INDUSTRY=s.SUB_INDUSTRY_ID left join generic_master gr on gr.generic_key='CGTMSE_INDUSTRY' and gr.value=a.CGTMSE_INDUSTRY  ").append(" left join gcd_group_m B on B.group_id=A.customer_group_id where CUSTOMER_ID=").append(code).append(" ").toString());
        }
        else
        {
          str.append(new StringBuilder().append("select a.CUSTOMER_ID, a.CUSTOMER_FNAME,a.CUSTOMER_MNAME,a.CUSTOMER_LNAME,a.CUSTOMER_EMAIL,DATE_FORMAT(a.CUSTOMER_DOB,'").append(this.dateFormat).append("'),a.CUSTMER_PAN,a.CUSTOMER_REFERENCE,a.CUSTOMER_BLACKLIST,a.CUSTOMER_BLACKLIST_REASON,a.CUSTOMER_CATEGORY,a.CUSTOMER_TYPE,a.CUSTOMER_CONSTITUTION,a.FATHER_HUSBAND_NAME,a.CASTE_CATEGORY,a.MARITAL_STATUS,a.DRIVING_LICENSE,a.VOTER_ID,").append(" a.PASSPORT_NUMBER,a.UID_NO,a.OTHER_NO,a.EDU_DETAIL,a.RELATIONSHIP,a.GENDER,a.OTHOR_RELATIONSHIP_TYPE,a.OTHER_RELATIONSHIP_ID,a.CUSTOMER_INDUSTRY,a.CUSTOMER_SUB_INDUSTRY,c.INDUSTRY_DESC ,s.SUB_INDUSTRY_DESC, A.customer_group_id,case customer_group_type when 'E' then B.group_desc when 'N' then A.customer_group_desc end as customer_group_desc,a.customer_group_type,d.dealer_desc, ").append(" a.MOTHER_FIRST_NAME,a.MOTHER_MIDDLE_NAME,a.MOTHER_LAST_NAME,a.CITIZENSHIP,a.RESIDENTIAL_STATUS,a.OCCPATION,a.UIDAI,a.NRI,a.PROOF_IDENTITY_DOCUMENT_NO,").append(" DATE_FORMAT(a.PROOF_IDENTITY_DOCUMENT_EXP_DATE,'").append(this.dateFormat).append("'),a.PROOF_ADDRESS_DOCUMENT_NO,DATE_FORMAT(a.PROOF_ADDRESS_DOCUMENT_EXP_DATE,'").append(this.dateFormat).append("'),a.RELATED_PERSON_TYPE,a.RELATED_PERSON_PRIFIX,").append(" a.RELATED_PERSON_FIRST_NAME,a.RELATED_PERSON_MIDDLE_NAME,a.RELATED_PERSON_LAST_NAME,a.RELATED_PERSON_CKYC_NUMBER,a.RELATED_PERSON_PROOF_IDENTITY_DOCUMENT,").append(" a.RELATED_PERSON_PROOF_OF_IDENTITY_DOCUMENT_NO,DATE_FORMAT(a.RELATED_PERSON_PROOF_IDENTITY_DOCUMENT_EXP_DATE,'").append(this.dateFormat).append("'),a.CKYC,a.KYC_ACCOUNT_TYPE,a.CUSTOMER_NAME_PRIFX, ").append(" a.PROOF_OF_IDENTITY_DOCUMENT,a.PROOF_OF_IDENTITY_DOCUMENT_NO,a.PROOF_OF_IDENTITY_DOCUMENT_EXP_DATE,a.PROOF_OF_ADDRESS_DOCUMENT,a.PROOF_OF_ADDRESS_DOCUMENT_NO,a.PROOF_OF_ADDRESS_DOCUMENT_EXP_DATE,").append(" a.PROJECTED_SALES,a.PROJECTED_EXPORTS,a.MINORITY_COMMUNITY,a.HANDICAPPED,a.RISK_CATEGORY,a.UCIC_NO,a.CGTMSE_INDUSTRY,gr.DESCRIPTION ").append(" from ").append(tableName).append(" a left join cr_dsa_dealer_m d on a.OTHOR_RELATIONSHIP_TYPE=d.bp_type and a.OTHER_RELATIONSHIP_ID=d.dealer_id  left join com_industry_m c on a.CUSTOMER_INDUSTRY=c.INDUSTRY_ID left join com_sub_industry_m s on a.CUSTOMER_SUB_INDUSTRY=s.SUB_INDUSTRY_ID left join generic_master gr on gr.generic_key='CGTMSE_INDUSTRY' and gr.value=a.CGTMSE_INDUSTRY  left join gcd_group_m B on B.group_id=a.customer_group_id  where CUSTOMER_ID=").append(code).append("").toString());
        }

        logger.info(new StringBuilder().append("customer status ................................................. ").append(custStatus).toString());

        logger.info(new StringBuilder().append("In getIndividualDetails() of CortateDAOImpl.  Query : ").append(str.toString()).toString());
        ArrayList individualDetails = ConnectionDAO.sqlSelect(str.toString());
        logger.info(new StringBuilder().append("getIndividualDetails ").append(individualDetails.size()).toString());
        int size = individualDetails.size();
        for (int i = 0; i < size; i++)
        {
          ArrayList data = (ArrayList)individualDetails.get(i);
          if (data.size() > 0)
          {
            ShowCustomerDetailVo show = new ShowCustomerDetailVo();
            show.setCorporateCode(CommonFunction.checkNull(data.get(0)).toString());
            show.setFirstName(CommonFunction.checkNull(data.get(1)).toString());
            show.setMiddleName(CommonFunction.checkNull(data.get(2)).toString());
            show.setLastName(CommonFunction.checkNull(data.get(3)).toString());
            show.setInstitutionEmail(CommonFunction.checkNull(data.get(4)).toString());
            show.setIncorporationDate(CommonFunction.checkNull(data.get(5)).toString());
            show.setPan(CommonFunction.checkNull(data.get(6)).toString());
            show.setReferredBy(CommonFunction.checkNull(data.get(7)).toString());
            show.setBlackListed(CommonFunction.checkNull(data.get(8)).toString());
            show.setReasonForBlackListed(CommonFunction.checkNull(data.get(9)).toString());
            show.setCorporateCategory(CommonFunction.checkNull(data.get(10)).toString());
            show.setCusType(CommonFunction.checkNull(data.get(11)).toString());
            show.setConstitution(CommonFunction.checkNull(data.get(12)).toString());
            show.setFatherHusband(CommonFunction.checkNull(data.get(13)).toString());
            show.setCasteCategory(CommonFunction.checkNull(data.get(14)).toString());
            show.setMaritalStatus(CommonFunction.checkNull(data.get(15)).toString());
            show.setDrivingLicense(CommonFunction.checkNull(data.get(16)).toString());
            show.setVoterId(CommonFunction.checkNull(data.get(17)).toString());
            show.setPassport(CommonFunction.checkNull(data.get(18)).toString());
            show.setAadhaar(CommonFunction.checkNull(data.get(19)).toString());
            show.setOther(CommonFunction.checkNull(data.get(20)).toString());
            show.setEduDetailInd(CommonFunction.checkNull(data.get(21)).toString());
            show.setRelationShip(CommonFunction.checkNull(data.get(22)).toString());
            show.setGenderIndividual(CommonFunction.checkNull(data.get(23)).toString());
            show.setOtherRelationShip(CommonFunction.checkNull(data.get(24)).toString());
            show.setLbxBusinessPartnearHID(CommonFunction.checkNull(data.get(25)).toString());
            show.setLbxIndustry(CommonFunction.checkNull(data.get(26)).toString());
            show.setLbxSubIndustry(CommonFunction.checkNull(data.get(27)).toString());
            show.setIndustry(CommonFunction.checkNull(data.get(28)).toString());
            show.setSubIndustry(CommonFunction.checkNull(data.get(29)).toString());
            show.sethGroupId(CommonFunction.checkNull(data.get(30)).toString());

            show.setGroupName(CommonFunction.checkNull(data.get(31)).toString());
            logger.info(new StringBuilder().append("groupppp").append(show.getGroupName()).toString());

            show.setGroupType(CommonFunction.checkNull(data.get(32)).toString());

            logger.info(new StringBuilder().append("group_id::").append(show.gethGroupId()).toString());
            logger.info(new StringBuilder().append("group_name::").append(show.getGroupName()).toString());
            logger.info(new StringBuilder().append("groupNaemText::").append(show.getGroupNameText()).toString());
            if (OthRelType.equalsIgnoreCase("CS"))
            {
              show.setBusinessPartnerTypeDesc("");
            }
            else
            {
              show.setBusinessPartnerTypeDesc(CommonFunction.checkNull(data.get(33)).toString());
            }

            show.setMotherFName(CommonFunction.checkNull(data.get(34)).toString());
            show.setMotherMName(CommonFunction.checkNull(data.get(35)).toString());
            show.setMotherLName(CommonFunction.checkNull(data.get(36)).toString());
            show.setCitizenship(CommonFunction.checkNull(data.get(37)).toString());
            show.setResidentialStatus(CommonFunction.checkNull(data.get(38)).toString());
            show.setOccpation(CommonFunction.checkNull(data.get(39)).toString());
            show.setuIDAI(CommonFunction.checkNull(data.get(40)).toString());
            show.setPesTaxPrsJuriOutSideInd(CommonFunction.checkNull(data.get(41)).toString());
            show.setPrfIdentityDocNo(CommonFunction.checkNull(data.get(42)).toString());
            show.setPrfIdentityDocExpDate(CommonFunction.checkNull(data.get(43)).toString());
            show.setPrfAddressDocNo(CommonFunction.checkNull(data.get(44)).toString());
            show.setPrfAddressDocExpDate(CommonFunction.checkNull(data.get(45)).toString());
            show.setRelatedPersonType(CommonFunction.checkNull(data.get(46)).toString());
            show.setRelatedPersonPrifix(CommonFunction.checkNull(data.get(47)).toString());
            show.setRelatedPersonFName(CommonFunction.checkNull(data.get(48)).toString());
            show.setRelatedPersonMName(CommonFunction.checkNull(data.get(49)).toString());
            show.setRelatedPersonLName(CommonFunction.checkNull(data.get(50)).toString());
            show.setRelatedPersonCkycNo(CommonFunction.checkNull(data.get(51)).toString());
            show.setRelatedPersonPrfIdntyDoc(CommonFunction.checkNull(data.get(52)).toString());
            show.setRelatedPersonPrfIdntyDocNo(CommonFunction.checkNull(data.get(53)).toString());
            show.setRelatedPersonPrfIdntyDocExpDate(CommonFunction.checkNull(data.get(54)).toString());
            show.setcKYC(CommonFunction.checkNull(data.get(55)).toString());
            show.setAccountType(CommonFunction.checkNull(data.get(56)).toString());
            show.setCustomerNamePrifx(CommonFunction.checkNull(data.get(57)).toString());
            show.setProofOfIdentityDocument(CommonFunction.checkNull(data.get(58)).toString());
            show.setProofOfIdentityDocumentNo(CommonFunction.checkNull(data.get(59)).toString());
            show.setProofOfIdentityDocumentExpDate(CommonFunction.checkNull(data.get(60)).toString());
            show.setProofOfAddressDocument(CommonFunction.checkNull(data.get(61)).toString());
            show.setProofOfAddressDocumentNo(CommonFunction.checkNull(data.get(62)).toString());
            show.setProofOfAddressDocumentExpDate(CommonFunction.checkNull(data.get(63)).toString());
            show.setProjectedSales(CommonFunction.checkNull(data.get(64)).toString());
            show.setProjectedExports(CommonFunction.checkNull(data.get(65)).toString());
            show.setMinorityCommunity(CommonFunction.checkNull(data.get(66)).toString());
            show.setHandiCapped(CommonFunction.checkNull(data.get(67)).toString());
            show.setRiskCategory(CommonFunction.checkNull(data.get(68)).toString());
            show.setUcic(CommonFunction.checkNull(data.get(69)).toString());
            show.setLbxcgtmseIndustry(CommonFunction.checkNull(data.get(70)).toString());
            show.setCgtmseIndustry(CommonFunction.checkNull(data.get(71)).toString());
            list.add(show);
          }
        }
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
    else
    {
      if (((pageStatus != null) && (pageStatus.equals("approve"))) || ((updateInMaker != null) && (updateInMaker.equals("updateInMaker"))) || (statusCase.equalsIgnoreCase("UnApproved"))) {
        tableName = "gcd_customer_m_temp";
      }
      else {
        tableName = "gcd_customer_m";
        if (CommonFunction.checkNull(source).trim().equalsIgnoreCase("ED")) {
          tableName = "gcd_customer_m_edit";
        }
      }
      try
      {
        String OthRelType = "";
        OthRelType = ConnectionDAO.singleReturn(new StringBuilder().append("SELECT OTHOR_RELATIONSHIP_TYPE FROM ").append(tableName).append(" WHERE CUSTOMER_ID=").append(code).append("").toString());

        if ((!OthRelType.equalsIgnoreCase("")) && (OthRelType.equalsIgnoreCase("CS")))
        {
          query = new StringBuilder().append("select a.CUSTOMER_ID, a.CUSTOMER_FNAME,a.CUSTOMER_MNAME,a.CUSTOMER_LNAME,a.CUSTOMER_EMAIL,DATE_FORMAT(a.CUSTOMER_DOB,'").append(this.dateFormat).append("'),a.CUSTMER_PAN,a.CUSTOMER_REFERENCE,a.CUSTOMER_BLACKLIST,a.CUSTOMER_BLACKLIST_REASON,a.CUSTOMER_CATEGORY,a.CUSTOMER_TYPE,a.CUSTOMER_STATUS,a.CUSTOMER_CONSTITUTION,a.FATHER_HUSBAND_NAME,a.CASTE_CATEGORY,a.MARITAL_STATUS,a.DRIVING_LICENSE,a.VOTER_ID ").append(" ,a.PASSPORT_NUMBER,a.UID_NO,a.OTHER_NO,a.EDU_DETAIL,a.GENDER,a.OTHOR_RELATIONSHIP_TYPE,a.OTHER_RELATIONSHIP_ID,a.CUSTOMER_INDUSTRY,a.CUSTOMER_SUB_INDUSTRY,c.INDUSTRY_DESC ,s.SUB_INDUSTRY_DESC,a.group_id,B.group_desc , ").append(" a.BUSINESS_DESC,a.MOTHER_FIRST_NAME,a.MOTHER_MIDDLE_NAME,a.MOTHER_LAST_NAME,a.CITIZENSHIP,a.RESIDENTIAL_STATUS,a.OCCPATION,a.UIDAI,a.NRI,a.PROOF_IDENTITY_DOCUMENT_NO,").append(" DATE_FORMAT(a.PROOF_IDENTITY_DOCUMENT_EXP_DATE,'").append(this.dateFormat).append("'),a.PROOF_ADDRESS_DOCUMENT_NO,DATE_FORMAT(a.PROOF_ADDRESS_DOCUMENT_EXP_DATE,'").append(this.dateFormat).append("'),a.RELATED_PERSON_TYPE,a.RELATED_PERSON_PRIFIX,").append(" a.RELATED_PERSON_FIRST_NAME,a.RELATED_PERSON_MIDDLE_NAME,a.RELATED_PERSON_LAST_NAME,a.RELATED_PERSON_CKYC_NUMBER,a.RELATED_PERSON_PROOF_IDENTITY_DOCUMENT,").append(" a.RELATED_PERSON_PROOF_OF_IDENTITY_DOCUMENT_NO,DATE_FORMAT(a.RELATED_PERSON_PROOF_IDENTITY_DOCUMENT_EXP_DATE,'").append(this.dateFormat).append("'),a.CKYC,a.KYC_ACCOUNT_TYPE,a.CUSTOMER_NAME_PRIFX, ").append(" a.PROOF_OF_IDENTITY_DOCUMENT,a.PROOF_OF_IDENTITY_DOCUMENT_NO,a.PROOF_OF_IDENTITY_DOCUMENT_EXP_DATE,a.PROOF_OF_ADDRESS_DOCUMENT,a.PROOF_OF_ADDRESS_DOCUMENT_NO,a.PROOF_OF_ADDRESS_DOCUMENT_EXP_DATE,").append(" a.PROJECTED_SALES,a.PROJECTED_EXPORTS,a.MINORITY_COMMUNITY,a.HANDICAPPED,a.RISK_CATEGORY,a.UCIC_NO,a.CGTMSE_INDUSTRY,gr.DESCRIPTION ").append("from ").append(tableName).append(" a left join com_industry_m c on a.CUSTOMER_INDUSTRY=c.INDUSTRY_ID left join com_sub_industry_m s on a.CUSTOMER_SUB_INDUSTRY=s.SUB_INDUSTRY_ID left join generic_master gr on gr.generic_key='CGTMSE_INDUSTRY' and gr.value=a.CGTMSE_INDUSTRY left join gcd_group_m B on B.group_id=a.group_id where CUSTOMER_ID=").append(code).append("").toString();
        }
        else
        {
          query = new StringBuilder().append("select A.CUSTOMER_ID, A.CUSTOMER_FNAME,A.CUSTOMER_MNAME,A.CUSTOMER_LNAME,A.CUSTOMER_EMAIL,DATE_FORMAT(A.CUSTOMER_DOB,'").append(this.dateFormat).append("'),A.CUSTMER_PAN,A.CUSTOMER_REFERENCE,A.CUSTOMER_BLACKLIST,A.CUSTOMER_BLACKLIST_REASON,A.CUSTOMER_CATEGORY,A.CUSTOMER_TYPE,A.CUSTOMER_STATUS,A.CUSTOMER_CONSTITUTION,A.FATHER_HUSBAND_NAME,A.CASTE_CATEGORY,A.MARITAL_STATUS,A.DRIVING_LICENSE,A.VOTER_ID ").append(" ,A.PASSPORT_NUMBER,A.UID_NO,A.OTHER_NO,A.EDU_DETAIL,A.GENDER,A.OTHOR_RELATIONSHIP_TYPE,A.OTHER_RELATIONSHIP_ID,a.CUSTOMER_INDUSTRY,a.CUSTOMER_SUB_INDUSTRY,c.INDUSTRY_DESC ,s.SUB_INDUSTRY_DESC ,a.group_id,B.group_desc,d.dealer_desc ,").append(" a.MOTHER_FIRST_NAME,a.MOTHER_MIDDLE_NAME,a.MOTHER_LAST_NAME,a.CITIZENSHIP,a.RESIDENTIAL_STATUS,a.OCCPATION,a.UIDAI,a.NRI,a.PROOF_IDENTITY_DOCUMENT_NO,").append(" DATE_FORMAT(a.PROOF_IDENTITY_DOCUMENT_EXP_DATE,'").append(this.dateFormat).append("'),a.PROOF_ADDRESS_DOCUMENT_NO,DATE_FORMAT(a.PROOF_ADDRESS_DOCUMENT_EXP_DATE,'").append(this.dateFormat).append("'),a.RELATED_PERSON_TYPE,a.RELATED_PERSON_PRIFIX,").append(" a.RELATED_PERSON_FIRST_NAME,a.RELATED_PERSON_MIDDLE_NAME,a.RELATED_PERSON_LAST_NAME,a.RELATED_PERSON_CKYC_NUMBER,a.RELATED_PERSON_PROOF_IDENTITY_DOCUMENT,").append(" a.RELATED_PERSON_PROOF_OF_IDENTITY_DOCUMENT_NO,DATE_FORMAT(a.RELATED_PERSON_PROOF_IDENTITY_DOCUMENT_EXP_DATE,'").append(this.dateFormat).append("'),a.CKYC,a.KYC_ACCOUNT_TYPE,a.CUSTOMER_NAME_PRIFX, ").append(" a.PROOF_OF_IDENTITY_DOCUMENT,a.PROOF_OF_IDENTITY_DOCUMENT_NO,a.PROOF_OF_IDENTITY_DOCUMENT_EXP_DATE,a.PROOF_OF_ADDRESS_DOCUMENT,a.PROOF_OF_ADDRESS_DOCUMENT_NO,a.PROOF_OF_ADDRESS_DOCUMENT_EXP_DATE, ").append(" a.PROJECTED_SALES,a.PROJECTED_EXPORTS,a.MINORITY_COMMUNITY,a.HANDICAPPED,a.RISK_CATEGORY,a.UCIC_NO,a.CGTMSE_INDUSTRY,gr.DESCRIPTION  ").append(" from ").append(tableName).append(" A left join com_industry_m c on a.CUSTOMER_INDUSTRY=c.INDUSTRY_ID left join com_sub_industry_m s on a.CUSTOMER_SUB_INDUSTRY=s.SUB_INDUSTRY_ID left join generic_master gr on gr.generic_key='CGTMSE_INDUSTRY' and gr.value=a.CGTMSE_INDUSTRY left join gcd_group_m B on B.group_id=A.group_id left join cr_dsa_dealer_m d on A.OTHOR_RELATIONSHIP_TYPE= d.bp_type and OTHER_RELATIONSHIP_ID=d.dealer_id where CUSTOMER_ID=").append(code).append(" ").toString();
        }

        logger.info(new StringBuilder().append("In getIndividualDetails() of CortateDAOImpl.  Query next: ").append(query).toString());
        ArrayList individualDetails = ConnectionDAO.sqlSelect(query);
        logger.info(new StringBuilder().append("getIndividualDetails ").append(individualDetails.size()).toString());
        int size = individualDetails.size();
        for (int i = 0; i < size; i++)
        {
          ArrayList data = (ArrayList)individualDetails.get(i);
          if (data.size() > 0)
          {
            ShowCustomerDetailVo show = new ShowCustomerDetailVo();

            show.setCorporateCode(CommonFunction.checkNull(data.get(0)).toString());
            show.setFirstName(CommonFunction.checkNull(data.get(1)).toString());
            show.setMiddleName(CommonFunction.checkNull(data.get(2)).toString());
            show.setLastName(CommonFunction.checkNull(data.get(3)).toString());
            show.setInstitutionEmail(CommonFunction.checkNull(data.get(4)).toString());
            show.setIncorporationDate(CommonFunction.checkNull(data.get(5)).toString());
            show.setPan(CommonFunction.checkNull(data.get(6)).toString());
            show.setReferredBy(CommonFunction.checkNull(data.get(7)).toString());
            show.setBlackListed(CommonFunction.checkNull(data.get(8)).toString());
            show.setReasonForBlackListed(CommonFunction.checkNull(data.get(9)).toString());
            show.setCorporateCategory(CommonFunction.checkNull(data.get(10)).toString());
            show.setCusType(CommonFunction.checkNull(data.get(11)).toString());
            show.setPagestatus(CommonFunction.checkNull(data.get(12)).toString());
            show.setConstitution(CommonFunction.checkNull(data.get(13)).toString());
            show.setFatherHusband(CommonFunction.checkNull(data.get(14)).toString());
            show.setCasteCategory(CommonFunction.checkNull(data.get(15)).toString());
            show.setMaritalStatus(CommonFunction.checkNull(data.get(16)).toString());
            show.setDrivingLicense(CommonFunction.checkNull(data.get(17)).toString());
            show.setVoterId(CommonFunction.checkNull(data.get(18)).toString());
            show.setPassport(CommonFunction.checkNull(data.get(19)).toString());
            show.setAadhaar(CommonFunction.checkNull(data.get(20)).toString());
            show.setOther(CommonFunction.checkNull(data.get(21)).toString());
            show.setEduDetailInd(CommonFunction.checkNull(data.get(22)).toString());
            show.setGenderIndividual(CommonFunction.checkNull(data.get(23)).toString());
            show.setOtherRelationShip(CommonFunction.checkNull(data.get(24)).toString());
            show.setLbxBusinessPartnearHID(CommonFunction.checkNull(data.get(25)).toString());
            show.setLbxIndustry(CommonFunction.checkNull(data.get(26)).toString());
            show.setLbxSubIndustry(CommonFunction.checkNull(data.get(27)).toString());
            show.setIndustry(CommonFunction.checkNull(data.get(28)).toString());
            show.setSubIndustry(CommonFunction.checkNull(data.get(29)).toString());

            show.sethGroupId(CommonFunction.checkNull(data.get(30)).toString());
            show.setGroupName(CommonFunction.checkNull(data.get(31)).toString());
            if (OthRelType.equalsIgnoreCase("CS"))
            {
              show.setBusinessPartnerTypeDesc("");
            }
            else
            {
              show.setBusinessPartnerTypeDesc(CommonFunction.checkNull(data.get(32)).toString());
            }

            show.setMotherFName(CommonFunction.checkNull(data.get(33)).toString());
            show.setMotherMName(CommonFunction.checkNull(data.get(34)).toString());
            show.setMotherLName(CommonFunction.checkNull(data.get(35)).toString());
            show.setCitizenship(CommonFunction.checkNull(data.get(36)).toString());
            show.setResidentialStatus(CommonFunction.checkNull(data.get(37)).toString());
            show.setOccpation(CommonFunction.checkNull(data.get(38)).toString());
            show.setuIDAI(CommonFunction.checkNull(data.get(39)).toString());
            show.setPesTaxPrsJuriOutSideInd(CommonFunction.checkNull(data.get(40)).toString());
            show.setPrfIdentityDocNo(CommonFunction.checkNull(data.get(41)).toString());
            show.setPrfIdentityDocExpDate(CommonFunction.checkNull(data.get(42)).toString());
            show.setPrfAddressDocNo(CommonFunction.checkNull(data.get(43)).toString());
            show.setPrfAddressDocExpDate(CommonFunction.checkNull(data.get(44)).toString());
            show.setRelatedPersonType(CommonFunction.checkNull(data.get(45)).toString());
            show.setRelatedPersonPrifix(CommonFunction.checkNull(data.get(46)).toString());
            show.setRelatedPersonFName(CommonFunction.checkNull(data.get(47)).toString());
            show.setRelatedPersonMName(CommonFunction.checkNull(data.get(48)).toString());
            show.setRelatedPersonLName(CommonFunction.checkNull(data.get(49)).toString());
            show.setRelatedPersonCkycNo(CommonFunction.checkNull(data.get(50)).toString());
            show.setRelatedPersonPrfIdntyDoc(CommonFunction.checkNull(data.get(51)).toString());
            show.setRelatedPersonPrfIdntyDocNo(CommonFunction.checkNull(data.get(52)).toString());
            show.setRelatedPersonPrfIdntyDocExpDate(CommonFunction.checkNull(data.get(53)).toString());
            show.setcKYC(CommonFunction.checkNull(data.get(54)).toString());
            show.setAccountType(CommonFunction.checkNull(data.get(55)).toString());
            show.setCustomerNamePrifx(CommonFunction.checkNull(data.get(56)).toString());
            show.setProofOfIdentityDocument(CommonFunction.checkNull(data.get(57)).toString());
            show.setProofOfIdentityDocumentNo(CommonFunction.checkNull(data.get(58)).toString());
            show.setProofOfIdentityDocumentExpDate(CommonFunction.checkNull(data.get(59)).toString());
            show.setProofOfAddressDocument(CommonFunction.checkNull(data.get(60)).toString());
            show.setProofOfAddressDocumentNo(CommonFunction.checkNull(data.get(61)).toString());
            show.setProofOfAddressDocumentExpDate(CommonFunction.checkNull(data.get(62)).toString());
            show.setProjectedSales(CommonFunction.checkNull(data.get(63)).toString());
            show.setProjectedExports(CommonFunction.checkNull(data.get(64)).toString());
            show.setMinorityCommunity(CommonFunction.checkNull(data.get(65)).toString());
            show.setHandiCapped(CommonFunction.checkNull(data.get(66)).toString());
            show.setRiskCategory(CommonFunction.checkNull(data.get(67)).toString());
            show.setUcic(CommonFunction.checkNull(data.get(68)).toString());
            show.setLbxcgtmseIndustry(CommonFunction.checkNull(data.get(69)).toString());
            show.setCgtmseIndustry(CommonFunction.checkNull(data.get(70)).toString());
            list.add(show);
          }
        }
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
    return list;
  }

  public ArrayList<Object> getReferenceAll(String code, Object pageStatus, String updateFlag, String source)
  {
    ArrayList list = new ArrayList();
    String tableName = "";
    if (((updateFlag != null) && (updateFlag.equals("updateFlag"))) || ((updateFlag != null) && (updateFlag.equals("notEdit"))))
    {
      logger.info("In Credit Processing , Customer Entry..,getReferenceAll()......");
      logger.info(new StringBuilder().append("from deal info in update flag>>>>>>>>>>>>> ").append(updateFlag).toString());
      tableName = "cr_deal_reference_m";
    }
    else
    {
      logger.info("In GCD..,getReferenceAll()......");
      tableName = "com_reference_m";
      if (CommonFunction.checkNull(source).trim().equalsIgnoreCase("ED"))
        tableName = "com_reference_m_edit";
    }
    try
    {
      String query = new StringBuilder().append("select REF_ID, concat(F_NAME,' ',IFNULL(M_NAME,''),' ',L_NAME)NAME, RELATIONSHIP, KNOWING_SINCE, MOBILE_NUMBER, LANDLINE_NUMBER from ").append(tableName).append("  where BPID=").append(code).toString();
      logger.info(new StringBuilder().append("................. from ").append(tableName).append("  table............................query ").append(query).toString());
      ArrayList addressAll = ConnectionDAO.sqlSelect(query);
      logger.info(new StringBuilder().append("getReferenceAll : : :  ").append(addressAll.size()).toString());
      for (int i = 0; i < addressAll.size(); i++)
      {
        ArrayList data = (ArrayList)addressAll.get(i);
        if (data.size() > 0)
        {
          CustomerSaveVo addr = new CustomerSaveVo();
          addr.setRefId(CommonFunction.checkNull(data.get(0)).toString());
          addr.setRefName(CommonFunction.checkNull(data.get(1)).toString());
          addr.setRelationshipS(CommonFunction.checkNull(data.get(2)).toString());
          addr.setKnowingSince(CommonFunction.checkNull(data.get(3)).toString());
          addr.setPrimaryRefMbNo(CommonFunction.checkNull(data.get(4)).toString());
          addr.setAlternateRefPhNo(CommonFunction.checkNull(data.get(5)).toString());
          list.add(addr);
        }
      }
    }
    catch (Exception e) {
      e.printStackTrace();
    }

    return list;
  }

  public ArrayList<Object> getSarvSurakshaAll(String code, Object pageStatus, String updateFlag, String source) {
    ArrayList list = new ArrayList();
    String tableName = "";
    if (((updateFlag != null) && (updateFlag.equals("updateFlag"))) || ((updateFlag != null) && (updateFlag.equals("notEdit"))))
    {
      logger.info("In Credit Processing , Customer Entry..,getReferenceAll()......");
      logger.info(new StringBuilder().append("from deal info in update flag>>>>>>>>>>>>> ").append(updateFlag).toString());
      tableName = "cr_deal_sarva_Suraksha_m";
    }
    else
    {
      logger.info("In GCD..,getReferenceAll()......");
      tableName = "com_sarva_Suraksha_m";
      if (CommonFunction.checkNull(source).trim().equalsIgnoreCase("ED"))
        tableName = "com_sarva_Suraksha_m_edit";
    }
    try
    {
      String query = new StringBuilder().append("select REF_ID, Nominee_Name, RELATIONSHIP, date_FORMAT(NomineeDOB  ,'%d-%m-%Y') from ").append(tableName).append("  where BPID=").append(code).toString();
      logger.info(new StringBuilder().append("................. from ").append(tableName).append("  table............................query ").append(query).toString());
      ArrayList addressAll = ConnectionDAO.sqlSelect(query);
      logger.info(new StringBuilder().append("getReferenceAll : : :  ").append(addressAll.size()).toString());
      for (int i = 0; i < addressAll.size(); i++)
      {
        ArrayList data = (ArrayList)addressAll.get(i);
        if (data.size() > 0)
        {
          CustomerSaveVo addr = new CustomerSaveVo();
          addr.setRefId(CommonFunction.checkNull(data.get(0)).toString());
          addr.setNominee(CommonFunction.checkNull(data.get(1)).toString());
          addr.setRelationshipS(CommonFunction.checkNull(data.get(2)).toString());
          addr.setNomineeDOB(CommonFunction.checkNull(data.get(3)).toString());
          list.add(addr);
        }
      }
    }
    catch (Exception e) {
      e.printStackTrace();
    }

    return list;
  }

  public ArrayList<Object> getAdressAll(String code, Object pageStatus, String updateFlag, String source)
  {
    ArrayList list = new ArrayList();
    String tableName = "";
    if (((updateFlag != null) && (updateFlag.equals("updateFlag"))) || ((updateFlag != null) && (updateFlag.equals("notEdit"))))
    {
      logger.info("In Credit Processing , Customer Entry..,getAdressAll()......");
      logger.info(new StringBuilder().append("from deal info in update flag>>>>>>>>>>>>> ").append(updateFlag).toString());
      tableName = "cr_deal_address_m";
      try {
        String query = new StringBuilder().append("SELECT c.ADDRESS_ID,m.DESCRIPTION,c.ADDRESS_LINE1,d.DISTRICT_DESC,s.STATE_DESC,p.COUNTRY_DESC from ").append(tableName).append(" c,generic_master m,com_country_m p,com_district_m d,com_state_m s ").append("where m.VALUE=c.ADDRESS_TYPE AND m.GENERIC_KEY='ADDRESS_TYPE' and P.COUNTRY_id=c.COUNTRY AND s.STATE_id=c.STATE AND d.DISTRICT_id=c.DISTRICT and c.BPID=").append(code).toString();

        logger.info(new StringBuilder().append("................. from deal table..........................................query ").append(query).toString());
        ArrayList addressAll = ConnectionDAO.sqlSelect(query);
        logger.info(new StringBuilder().append("getAdressAll ").append(addressAll.size()).toString());
        for (int i = 0; i < addressAll.size(); i++)
        {
          ArrayList data = (ArrayList)addressAll.get(i);
          if (data.size() > 0)
          {
            CustomerSaveVo addr = new CustomerSaveVo();
            addr.setAddId(CommonFunction.checkNull(data.get(0)).toString());
            addr.setAddr_type(CommonFunction.checkNull(data.get(1)).toString());
            addr.setAddr1(CommonFunction.checkNull(data.get(2)).toString());
            addr.setDist(CommonFunction.checkNull(data.get(3)).toString());
            addr.setState(CommonFunction.checkNull(data.get(4)).toString());
            addr.setCountry(CommonFunction.checkNull(data.get(5)).toString());
            list.add(addr);
          }
        }
      }
      catch (Exception e) {
        e.printStackTrace();
      }
    }
    else
    {
      logger.info("In GCD..,getAdressAll()......");
      if ((pageStatus != null) && (pageStatus.equals("approve")))
      {
        tableName = "com_address_m_temp";
      }
      else
      {
        tableName = "com_address_m";
        if (CommonFunction.checkNull(source).trim().equalsIgnoreCase("ED"))
          tableName = "com_address_m_edit";
      }
      try
      {
        String query = new StringBuilder().append("SELECT c.ADDRESS_ID,m.DESCRIPTION,c.ADDRESS_LINE1,d.DISTRICT_DESC,s.STATE_DESC,p.COUNTRY_DESC from ").append(tableName).append(" c,generic_master m,com_country_m p,com_district_m d,com_state_m s").append(" where m.VALUE=c.ADDRESS_TYPE AND m.GENERIC_KEY='ADDRESS_TYPE' and P.COUNTRY_id=c.COUNTRY AND s.STATE_id=c.STATE AND d.DISTRICT_id=c.DISTRICT and c.BPID=").append(code).toString();

        logger.info(new StringBuilder().append("...........................................................query ").append(query).toString());
        ArrayList addressAll = ConnectionDAO.sqlSelect(query);
        logger.info(new StringBuilder().append("getAdressAll ").append(addressAll.size()).toString());
        for (int i = 0; i < addressAll.size(); i++) {
          ArrayList data = (ArrayList)addressAll.get(i);
          if (data.size() > 0)
          {
            CustomerSaveVo addr = new CustomerSaveVo();
            addr.setAddId(CommonFunction.checkNull(data.get(0)).toString());
            addr.setAddr_type(CommonFunction.checkNull(data.get(1)).toString());
            addr.setAddr1(CommonFunction.checkNull(data.get(2)).toString());
            addr.setDist(CommonFunction.checkNull(data.get(3)).toString());
            addr.setState(CommonFunction.checkNull(data.get(4)).toString());
            addr.setCountry(CommonFunction.checkNull(data.get(5)).toString());
            list.add(addr);
          }
        }
      }
      catch (Exception e) {
        e.printStackTrace();
      }
    }
    return list;
  }

  public boolean saveCorporateUpdate(Object ob, int id, String recStatus, String statusCase, String updateFlag, String pageStatuss, String source)
  {
    logger.info("in saveCorporateUpdate() of CorpotateDAOImpl");

    CorporateDetailsVO corporateDetailVo = (CorporateDetailsVO)ob;
    boolean status = false;
    String tableName = "";
    String customerType = "";
    try
    {
      if (((pageStatuss != null) && (pageStatuss.equals("fromLeads"))) || ((updateFlag != null) && (updateFlag.equals("updateFlag"))))
      {
        tableName = "cr_deal_customer_m";
        String custStatus = "";
        custStatus = ConnectionDAO.singleReturn(new StringBuilder().append("SELECT DEAL_EXISTING_CUSTOMER from cr_deal_customer_role where DEAL_CUSTOMER_ID=").append(id).toString());
        logger.info(new StringBuilder().append("corporateDetailVo.getCorporateName(): ").append(StringEscapeUtils.escapeSql(corporateDetailVo.getCorporateName())).toString());
        String queryUpdate = "";
        customerType = ConnectionDAO.singleReturn(new StringBuilder().append("select CUSTOMER_TYPE from cr_deal_customer_m where CUSTOMER_ID=").append(id).toString());
        PrepStmtObject insertPrepStmtObject3 = new PrepStmtObject();
        ArrayList qryList2 = new ArrayList();
        logger.info("CorporateDAOImpl saveCorporateUpdate().............fromLeads..........................");
        String emailQuery="select ifnull(customer_Email,'') from "+tableName+" where customer_id='"+id+"' ";
        String previousEmailId=ConnectionDAO.singleReturn(emailQuery);
        if(!CommonFunction.checkNull(corporateDetailVo.getInstitutionEmail()).equalsIgnoreCase(previousEmailId)){
        String query="update "+tableName+" set EmailVerificationFlag='N' where customer_id='"+id+"' ";
        ArrayList upList=new ArrayList();
        upList.add(query);
        boolean st=ConnectionDAO.sqlInsUpdDelete(upList);
        }
        queryUpdate = new StringBuilder().append("update ").append(tableName).append(" set CUSTOMER_NAME =?,CUSTOMER_FNAME=?,CUSTOMER_MNAME=?,CUSTOMER_LNAME=?,CUSTOMER_CATEGORY=?,CUSTOMER_DOB=STR_TO_DATE(?,'").append(this.dateFormat).append("'),").append(" CUSTOMER_CONSTITUTION=?,CUSTOMER_REGISTRATION_TYPE=?,CUSTOMER_REGISTRATION_NO=?,CUSTMER_PAN=?,CUSTOMER_VAT_NO=?,CUSTOMER_INDUSTRY=?,").append(" CUSTOMER_SUB_INDUSTRY=?,CUSTOMER_BUSINESS_SEGMENT=?,CUSTOMER_EMAIL=?,CUSTOMER_WEBSITE=?,CUSTOMER_REFERENCE=?,CUSTOMER_BLACKLIST=?,").append(" CUSTOMER_BLACKLIST_REASON=?,CUSTOMER_TYPE=?,MAKER_ID=?,MAKER_DATE=DATE_ADD(STR_TO_DATE(?, '").append(this.dateFormatWithTime).append("'),INTERVAL CURTIME() HOUR_SECOND),CUSTOMER_GROUP_ID=?,AUTHOR_ID=?,AUTHOR_DATE=DATE_ADD(STR_TO_DATE(?, '").append(this.dateFormatWithTime).append("'),INTERVAL CURTIME() HOUR_SECOND),").append(" FATHER_HUSBAND_NAME=?,CASTE_CATEGORY=?,MARITAL_STATUS=?,DRIVING_LICENSE=?,VOTER_ID=?,PASSPORT_NUMBER=?,UID_NO=?,OTHER_NO=?,EDU_DETAIL=?,CUSTOMER_GROUP_TYPE=?,CUSTOMER_GROUP_DESC=?,RELATIONSHIP=?,GENDER=?,OTHOR_RELATIONSHIP_TYPE=?,OTHER_RELATIONSHIP_ID=?,NATURE_OF_BUSINESS=?,YEAR_OF_ESTBLISHMENT=?,").append(" SHOP_ESTABLISHMENT_NO=?,SALES_TAX_TIN_NO=?,DGFT_NO=?,NO_BV_YEARS=?,NO_BV_MONTHS=?,industry_percent=? ").append(" ,MOTHER_FIRST_NAME=?,MOTHER_MIDDLE_NAME=?,MOTHER_LAST_NAME=?,CITIZENSHIP=?,RESIDENTIAL_STATUS=?,OCCPATION=?,UIDAI=?,NRI=?,PROOF_IDENTITY_DOCUMENT_NO=?, ").append(" PROOF_IDENTITY_DOCUMENT_EXP_DATE = DATE_ADD(STR_TO_DATE(?, '").append(this.dateFormatWithTime).append("'),INTERVAL CURTIME() HOUR_SECOND),PROOF_ADDRESS_DOCUMENT_NO=?,PROOF_ADDRESS_DOCUMENT_EXP_DATE  = DATE_ADD(STR_TO_DATE(?, '").append(this.dateFormatWithTime).append("'),INTERVAL CURTIME() HOUR_SECOND) ,RELATED_PERSON_TYPE=?,RELATED_PERSON_PRIFIX=?,RELATED_PERSON_FIRST_NAME=?, ").append(" RELATED_PERSON_MIDDLE_NAME=?,RELATED_PERSON_LAST_NAME=?,RELATED_PERSON_CKYC_NUMBER=?,RELATED_PERSON_PROOF_IDENTITY_DOCUMENT=?, ").append(" RELATED_PERSON_PROOF_OF_IDENTITY_DOCUMENT_NO=?,RELATED_PERSON_PROOF_IDENTITY_DOCUMENT_EXP_DATE = DATE_ADD(STR_TO_DATE(?, '").append(this.dateFormatWithTime).append("'),INTERVAL CURTIME() HOUR_SECOND) ,CKYC=?, KYC_ACCOUNT_TYPE=?,CUSTOMER_NAME_PRIFX=?, ").append(" PROOF_OF_IDENTITY_DOCUMENT=?,PROOF_OF_IDENTITY_DOCUMENT_NO=?,PROOF_OF_IDENTITY_DOCUMENT_EXP_DATE=?,PROOF_OF_ADDRESS_DOCUMENT=?,PROOF_OF_ADDRESS_DOCUMENT_NO=?,PROOF_OF_ADDRESS_DOCUMENT_EXP_DATE=?,").append(" PROJECTED_SALES=?,PROJECTED_EXPORTS=?,MINORITY_COMMUNITY=?,HANDICAPPED=?,RISK_CATEGORY=?,CGTMSE_INDUSTRY=? ").append("where CUSTOMER_ID=?").toString();

        if (CommonFunction.checkNull(corporateDetailVo.getCorporateName()).trim().equalsIgnoreCase(""))
          insertPrepStmtObject3.addNull();
        else {
          insertPrepStmtObject3.addString(CommonFunction.checkNull(corporateDetailVo.getCorporateName()).trim());
        }
        if (CommonFunction.checkNull(corporateDetailVo.getFirstName()).trim().equalsIgnoreCase(""))
          insertPrepStmtObject3.addNull();
        else {
          insertPrepStmtObject3.addString(CommonFunction.checkNull(corporateDetailVo.getFirstName()).trim());
        }
        if (CommonFunction.checkNull(corporateDetailVo.getMiddleName()).trim().equalsIgnoreCase(""))
          insertPrepStmtObject3.addNull();
        else {
          insertPrepStmtObject3.addString(CommonFunction.checkNull(corporateDetailVo.getMiddleName()).trim());
        }
        if (CommonFunction.checkNull(corporateDetailVo.getLastName()).trim().equalsIgnoreCase(""))
          insertPrepStmtObject3.addNull();
        else {
          insertPrepStmtObject3.addString(CommonFunction.checkNull(corporateDetailVo.getLastName()).trim());
        }
        if (CommonFunction.checkNull(corporateDetailVo.getCorporateCategory()).trim().equalsIgnoreCase(""))
          insertPrepStmtObject3.addNull();
        else {
          insertPrepStmtObject3.addString(CommonFunction.checkNull(corporateDetailVo.getCorporateCategory()).trim());
        }
        if (CommonFunction.checkNull(corporateDetailVo.getIncorporationDate()).trim().equalsIgnoreCase(""))
          insertPrepStmtObject3.addNull();
        else {
          insertPrepStmtObject3.addString(CommonFunction.checkNull(corporateDetailVo.getIncorporationDate()).trim());
        }
        if (CommonFunction.checkNull(corporateDetailVo.getConstitution()).trim().equalsIgnoreCase(""))
          insertPrepStmtObject3.addNull();
        else {
          insertPrepStmtObject3.addString(CommonFunction.checkNull(corporateDetailVo.getConstitution()).trim());
        }
        if (CommonFunction.checkNull(corporateDetailVo.getRegistrationType()).trim().equalsIgnoreCase(""))
          insertPrepStmtObject3.addNull();
        else {
          insertPrepStmtObject3.addString(CommonFunction.checkNull(corporateDetailVo.getRegistrationType()).trim());
        }
        if (CommonFunction.checkNull(corporateDetailVo.getRegistrationNo()).trim().equalsIgnoreCase(""))
          insertPrepStmtObject3.addNull();
        else {
          insertPrepStmtObject3.addString(CommonFunction.checkNull(corporateDetailVo.getRegistrationNo()).trim());
        }
        if (CommonFunction.checkNull(corporateDetailVo.getPan()).trim().equalsIgnoreCase(""))
          insertPrepStmtObject3.addNull();
        else {
          insertPrepStmtObject3.addString(CommonFunction.checkNull(corporateDetailVo.getPan()).trim());
        }
        if (CommonFunction.checkNull(corporateDetailVo.getVatRegNo()).trim().equalsIgnoreCase(""))
          insertPrepStmtObject3.addNull();
        else {
          insertPrepStmtObject3.addString(CommonFunction.checkNull(corporateDetailVo.getVatRegNo()).trim());
        }
        if (CommonFunction.checkNull(corporateDetailVo.getLbxIndustry()).trim().equalsIgnoreCase(""))
          insertPrepStmtObject3.addNull();
        else {
          insertPrepStmtObject3.addString(CommonFunction.checkNull(corporateDetailVo.getLbxIndustry()).trim());
        }
        if (CommonFunction.checkNull(corporateDetailVo.getLbxSubIndustry()).trim().equalsIgnoreCase(""))
          insertPrepStmtObject3.addNull();
        else {
          insertPrepStmtObject3.addString(CommonFunction.checkNull(corporateDetailVo.getLbxSubIndustry()).trim());
        }
        if (CommonFunction.checkNull(corporateDetailVo.getBusinessSegment()).trim().equalsIgnoreCase(""))
          insertPrepStmtObject3.addNull();
        else {
          insertPrepStmtObject3.addString(CommonFunction.checkNull(corporateDetailVo.getBusinessSegment()).trim());
        }
        if (CommonFunction.checkNull(corporateDetailVo.getInstitutionEmail()).trim().equalsIgnoreCase(""))
          insertPrepStmtObject3.addNull();
        else {
          insertPrepStmtObject3.addString(CommonFunction.checkNull(corporateDetailVo.getInstitutionEmail()).trim());
        }
        if (CommonFunction.checkNull(corporateDetailVo.getWebAddress()).trim().equalsIgnoreCase(""))
          insertPrepStmtObject3.addNull();
        else {
          insertPrepStmtObject3.addString(CommonFunction.checkNull(corporateDetailVo.getWebAddress()).trim());
        }
        if (CommonFunction.checkNull(corporateDetailVo.getReferredBy()).trim().equalsIgnoreCase(""))
          insertPrepStmtObject3.addNull();
        else {
          insertPrepStmtObject3.addString(CommonFunction.checkNull(corporateDetailVo.getReferredBy()).trim());
        }
        if (CommonFunction.checkNull(corporateDetailVo.getBlackListed()).trim().equalsIgnoreCase("on"))
          insertPrepStmtObject3.addString("Y");
        else {
          insertPrepStmtObject3.addString("N");
        }
        if (CommonFunction.checkNull(corporateDetailVo.getReasonForBlackListed()).trim().equalsIgnoreCase(""))
          insertPrepStmtObject3.addNull();
        else {
          insertPrepStmtObject3.addString(CommonFunction.checkNull(corporateDetailVo.getReasonForBlackListed()).trim());
        }
        if (CommonFunction.checkNull(customerType).trim().equalsIgnoreCase(""))
          insertPrepStmtObject3.addNull();
        else {
          insertPrepStmtObject3.addString(CommonFunction.checkNull(customerType).trim());
        }
        if (CommonFunction.checkNull(corporateDetailVo.getMakerId()).trim().equalsIgnoreCase(""))
          insertPrepStmtObject3.addNull();
        else {
          insertPrepStmtObject3.addString(corporateDetailVo.getMakerId().trim());
        }
        if (CommonFunction.checkNull(corporateDetailVo.getMakerDate()).trim().equalsIgnoreCase(""))
          insertPrepStmtObject3.addNull();
        else {
          insertPrepStmtObject3.addString(corporateDetailVo.getMakerDate().trim());
        }
        if (CommonFunction.checkNull(corporateDetailVo.gethGroupId()).trim().equalsIgnoreCase(""))
          insertPrepStmtObject3.addNull();
        else {
          insertPrepStmtObject3.addString(CommonFunction.checkNull(corporateDetailVo.gethGroupId()).trim());
        }
        if (CommonFunction.checkNull(corporateDetailVo.getMakerId()).trim().equalsIgnoreCase(""))
          insertPrepStmtObject3.addNull();
        else {
          insertPrepStmtObject3.addString(corporateDetailVo.getMakerId().trim());
        }
        if (CommonFunction.checkNull(corporateDetailVo.getMakerDate()).trim().equalsIgnoreCase(""))
          insertPrepStmtObject3.addNull();
        else {
          insertPrepStmtObject3.addString(corporateDetailVo.getMakerDate().trim());
        }
        if (CommonFunction.checkNull(corporateDetailVo.getFatherHusband()).trim().equalsIgnoreCase(""))
          insertPrepStmtObject3.addNull();
        else {
          insertPrepStmtObject3.addString(corporateDetailVo.getFatherHusband().trim());
        }
        if (CommonFunction.checkNull(corporateDetailVo.getCasteCategory()).trim().equalsIgnoreCase(""))
          insertPrepStmtObject3.addNull();
        else {
          insertPrepStmtObject3.addString(corporateDetailVo.getCasteCategory().trim());
        }
        if (CommonFunction.checkNull(corporateDetailVo.getMaritalStatus()).trim().equalsIgnoreCase(""))
          insertPrepStmtObject3.addNull();
        else {
          insertPrepStmtObject3.addString(corporateDetailVo.getMaritalStatus().trim());
        }
        if (CommonFunction.checkNull(corporateDetailVo.getDrivingLicense()).trim().equalsIgnoreCase(""))
          insertPrepStmtObject3.addNull();
        else {
          insertPrepStmtObject3.addString(corporateDetailVo.getDrivingLicense().trim());
        }
        if (CommonFunction.checkNull(corporateDetailVo.getVoterId()).trim().equalsIgnoreCase(""))
          insertPrepStmtObject3.addNull();
        else {
          insertPrepStmtObject3.addString(corporateDetailVo.getVoterId().trim());
        }
        if (CommonFunction.checkNull(corporateDetailVo.getPassport()).trim().equalsIgnoreCase(""))
          insertPrepStmtObject3.addNull();
        else {
          insertPrepStmtObject3.addString(corporateDetailVo.getPassport().trim());
        }

        if (CommonFunction.checkNull(corporateDetailVo.getAadhaar()).trim().equalsIgnoreCase(""))
          insertPrepStmtObject3.addNull();
        else {
          insertPrepStmtObject3.addString(corporateDetailVo.getAadhaar().trim());
        }
        if (CommonFunction.checkNull(corporateDetailVo.getOther()).trim().equalsIgnoreCase(""))
          insertPrepStmtObject3.addNull();
        else {
          insertPrepStmtObject3.addString(corporateDetailVo.getOther().trim());
        }
        if (CommonFunction.checkNull(corporateDetailVo.getEduDetailInd()).trim().equalsIgnoreCase(""))
          insertPrepStmtObject3.addNull();
        else {
          insertPrepStmtObject3.addString(corporateDetailVo.getEduDetailInd().trim());
        }

        if (CommonFunction.checkNull(corporateDetailVo.getGroupType()).trim().equalsIgnoreCase(""))
          insertPrepStmtObject3.addNull();
        else {
          insertPrepStmtObject3.addString(corporateDetailVo.getGroupType().trim());
        }

        if ((CommonFunction.checkNull(customerType).trim().equalsIgnoreCase("C")) || (CommonFunction.checkNull(corporateDetailVo.getGroupType()).trim().equalsIgnoreCase("N")))
        {
          if (CommonFunction.checkNull(corporateDetailVo.getGroupNameText()).trim().equalsIgnoreCase(""))
            insertPrepStmtObject3.addNull();
          else {
            insertPrepStmtObject3.addString(corporateDetailVo.getGroupNameText().trim());
          }

        }
        else if (CommonFunction.checkNull(corporateDetailVo.getGroupName()).trim().equalsIgnoreCase(""))
          insertPrepStmtObject3.addNull();
        else {
          insertPrepStmtObject3.addString(corporateDetailVo.getGroupName().trim());
        }

        if (CommonFunction.checkNull(corporateDetailVo.getRelationShip()).trim().equalsIgnoreCase(""))
          insertPrepStmtObject3.addNull();
        else {
          insertPrepStmtObject3.addString(corporateDetailVo.getRelationShip().trim());
        }

        if (CommonFunction.checkNull(corporateDetailVo.getGenderIndividual()).trim().equalsIgnoreCase(""))
          insertPrepStmtObject3.addNull();
        else {
          insertPrepStmtObject3.addString(corporateDetailVo.getGenderIndividual().trim());
        }

        if (CommonFunction.checkNull(corporateDetailVo.getOtherRelationShip()).trim().equalsIgnoreCase(""))
          insertPrepStmtObject3.addNull();
        else {
          insertPrepStmtObject3.addString(corporateDetailVo.getOtherRelationShip().trim());
        }
        if (CommonFunction.checkNull(corporateDetailVo.getOtherRelationShip()).trim().equalsIgnoreCase("CS"))
        {
          insertPrepStmtObject3.addNull();
        }
        else if (CommonFunction.checkNull(corporateDetailVo.getLbxBusinessPartnearHID()).trim().equalsIgnoreCase(""))
          insertPrepStmtObject3.addNull();
        else {
          insertPrepStmtObject3.addString(corporateDetailVo.getLbxBusinessPartnearHID().trim());
        }

        if (CommonFunction.checkNull(corporateDetailVo.getNatureOfBus()).trim().equalsIgnoreCase(""))
          insertPrepStmtObject3.addNull();
        else
          insertPrepStmtObject3.addString(corporateDetailVo.getNatureOfBus().trim());
        if (CommonFunction.checkNull(corporateDetailVo.getYearOfEstb()).trim().equalsIgnoreCase(""))
          insertPrepStmtObject3.addNull();
        else
          insertPrepStmtObject3.addString(corporateDetailVo.getYearOfEstb().trim());
        if (CommonFunction.checkNull(corporateDetailVo.getShopEstbNo()).trim().equalsIgnoreCase(""))
          insertPrepStmtObject3.addNull();
        else
          insertPrepStmtObject3.addString(corporateDetailVo.getShopEstbNo().trim());
        if (CommonFunction.checkNull(corporateDetailVo.getSalesTax()).trim().equalsIgnoreCase(""))
          insertPrepStmtObject3.addNull();
        else
          insertPrepStmtObject3.addString(corporateDetailVo.getSalesTax().trim());
        if (CommonFunction.checkNull(corporateDetailVo.getDgftNo()).trim().equalsIgnoreCase(""))
          insertPrepStmtObject3.addNull();
        else {
          insertPrepStmtObject3.addString(corporateDetailVo.getDgftNo().trim());
        }

        if (CommonFunction.checkNull(corporateDetailVo.getNoBVYears()).trim().equalsIgnoreCase(""))
          insertPrepStmtObject3.addNull();
        else
          insertPrepStmtObject3.addString(corporateDetailVo.getNoBVYears().trim());
        if (CommonFunction.checkNull(corporateDetailVo.getNoBVMonths()).trim().equalsIgnoreCase(""))
          insertPrepStmtObject3.addNull();
        else {
          insertPrepStmtObject3.addString(corporateDetailVo.getNoBVMonths().trim());
        }
        if (CommonFunction.checkNull(corporateDetailVo.getIndustryPercent()).trim().equalsIgnoreCase(""))
          insertPrepStmtObject3.addNull();
        else {
          insertPrepStmtObject3.addString(corporateDetailVo.getIndustryPercent().trim());
        }

        if (CommonFunction.checkNull(corporateDetailVo.getMotherFName()).trim().equalsIgnoreCase(""))
          insertPrepStmtObject3.addNull();
        else {
          insertPrepStmtObject3.addString(corporateDetailVo.getMotherFName().trim());
        }

        if (CommonFunction.checkNull(corporateDetailVo.getMotherMName()).trim().equalsIgnoreCase(""))
          insertPrepStmtObject3.addNull();
        else {
          insertPrepStmtObject3.addString(corporateDetailVo.getMotherMName().trim());
        }
        if (CommonFunction.checkNull(corporateDetailVo.getMotherLName()).trim().equalsIgnoreCase(""))
          insertPrepStmtObject3.addNull();
        else {
          insertPrepStmtObject3.addString(corporateDetailVo.getMotherLName().trim());
        }
        if (CommonFunction.checkNull(corporateDetailVo.getCitizenship()).trim().equalsIgnoreCase(""))
          insertPrepStmtObject3.addNull();
        else {
          insertPrepStmtObject3.addString(corporateDetailVo.getCitizenship().trim());
        }
        if (CommonFunction.checkNull(corporateDetailVo.getResidentialStatus()).trim().equalsIgnoreCase(""))
          insertPrepStmtObject3.addNull();
        else {
          insertPrepStmtObject3.addString(corporateDetailVo.getResidentialStatus().trim());
        }
        if (CommonFunction.checkNull(corporateDetailVo.getOccpation()).trim().equalsIgnoreCase(""))
          insertPrepStmtObject3.addNull();
        else {
          insertPrepStmtObject3.addString(corporateDetailVo.getOccpation().trim());
        }
        if (CommonFunction.checkNull(corporateDetailVo.getuIDAI()).trim().equalsIgnoreCase(""))
          insertPrepStmtObject3.addNull();
        else {
          insertPrepStmtObject3.addString(corporateDetailVo.getuIDAI().trim());
        }
        if (CommonFunction.checkNull(corporateDetailVo.getPesTaxPrsJuriOutSideInd()).trim().equalsIgnoreCase(""))
          insertPrepStmtObject3.addNull();
        else {
          insertPrepStmtObject3.addString(corporateDetailVo.getPesTaxPrsJuriOutSideInd().trim());
        }
        if (CommonFunction.checkNull(corporateDetailVo.getPrfIdentityDocNo()).trim().equalsIgnoreCase(""))
          insertPrepStmtObject3.addNull();
        else {
          insertPrepStmtObject3.addString(corporateDetailVo.getPrfIdentityDocNo().trim());
        }
        if (CommonFunction.checkNull(corporateDetailVo.getPrfIdentityDocExpDate()).trim().equalsIgnoreCase(""))
          insertPrepStmtObject3.addNull();
        else {
          insertPrepStmtObject3.addString(corporateDetailVo.getPrfIdentityDocExpDate().trim());
        }
        if (CommonFunction.checkNull(corporateDetailVo.getPrfAddressDocNo()).trim().equalsIgnoreCase(""))
          insertPrepStmtObject3.addNull();
        else {
          insertPrepStmtObject3.addString(corporateDetailVo.getPrfAddressDocNo().trim());
        }
        if (CommonFunction.checkNull(corporateDetailVo.getPrfAddressDocExpDate()).trim().equalsIgnoreCase(""))
          insertPrepStmtObject3.addNull();
        else {
          insertPrepStmtObject3.addString(corporateDetailVo.getPrfAddressDocExpDate().trim());
        }
        if (CommonFunction.checkNull(corporateDetailVo.getRelatedPersonType()).trim().equalsIgnoreCase(""))
          insertPrepStmtObject3.addNull();
        else {
          insertPrepStmtObject3.addString(corporateDetailVo.getRelatedPersonType().trim());
        }
        if (CommonFunction.checkNull(corporateDetailVo.getRelatedPersonPrifix()).trim().equalsIgnoreCase(""))
          insertPrepStmtObject3.addNull();
        else {
          insertPrepStmtObject3.addString(corporateDetailVo.getRelatedPersonPrifix().trim());
        }
        if (CommonFunction.checkNull(corporateDetailVo.getRelatedPersonFName()).trim().equalsIgnoreCase(""))
          insertPrepStmtObject3.addNull();
        else {
          insertPrepStmtObject3.addString(corporateDetailVo.getRelatedPersonFName().trim());
        }
        if (CommonFunction.checkNull(corporateDetailVo.getRelatedPersonMName()).trim().equalsIgnoreCase(""))
          insertPrepStmtObject3.addNull();
        else {
          insertPrepStmtObject3.addString(corporateDetailVo.getRelatedPersonMName().trim());
        }
        if (CommonFunction.checkNull(corporateDetailVo.getRelatedPersonLName()).trim().equalsIgnoreCase(""))
          insertPrepStmtObject3.addNull();
        else {
          insertPrepStmtObject3.addString(corporateDetailVo.getRelatedPersonLName().trim());
        }
        if (CommonFunction.checkNull(corporateDetailVo.getRelatedPersonCkycNo()).trim().equalsIgnoreCase(""))
          insertPrepStmtObject3.addNull();
        else {
          insertPrepStmtObject3.addString(corporateDetailVo.getRelatedPersonCkycNo().trim());
        }
        if (CommonFunction.checkNull(corporateDetailVo.getRelatedPersonPrfIdntyDoc()).trim().equalsIgnoreCase(""))
          insertPrepStmtObject3.addNull();
        else {
          insertPrepStmtObject3.addString(corporateDetailVo.getRelatedPersonPrfIdntyDoc().trim());
        }
        if (CommonFunction.checkNull(corporateDetailVo.getRelatedPersonPrfIdntyDocNo()).trim().equalsIgnoreCase(""))
          insertPrepStmtObject3.addNull();
        else {
          insertPrepStmtObject3.addString(corporateDetailVo.getRelatedPersonPrfIdntyDocNo().trim());
        }
        if (CommonFunction.checkNull(corporateDetailVo.getRelatedPersonPrfIdntyDocExpDate()).trim().equalsIgnoreCase(""))
          insertPrepStmtObject3.addNull();
        else {
          insertPrepStmtObject3.addString(corporateDetailVo.getRelatedPersonPrfIdntyDocExpDate().trim());
        }
        if (CommonFunction.checkNull(corporateDetailVo.getcKYC()).trim().equalsIgnoreCase(""))
          insertPrepStmtObject3.addNull();
        else {
          insertPrepStmtObject3.addString(corporateDetailVo.getcKYC().trim());
        }
        if (CommonFunction.checkNull(corporateDetailVo.getAccountType()).trim().equalsIgnoreCase(""))
          insertPrepStmtObject3.addNull();
        else {
          insertPrepStmtObject3.addString(corporateDetailVo.getAccountType().trim());
        }
        if (CommonFunction.checkNull(corporateDetailVo.getCustomerNamePrifx()).trim().equalsIgnoreCase(""))
          insertPrepStmtObject3.addNull();
        else {
          insertPrepStmtObject3.addString(corporateDetailVo.getCustomerNamePrifx().trim());
        }
        if (CommonFunction.checkNull(corporateDetailVo.getProofOfIdentityDocument()).trim().equalsIgnoreCase(""))
          insertPrepStmtObject3.addNull();
        else {
          insertPrepStmtObject3.addString(corporateDetailVo.getProofOfIdentityDocument().trim());
        }
        if (CommonFunction.checkNull(corporateDetailVo.getProofOfIdentityDocumentNo()).trim().equalsIgnoreCase(""))
          insertPrepStmtObject3.addNull();
        else {
          insertPrepStmtObject3.addString(corporateDetailVo.getProofOfIdentityDocumentNo().trim());
        }
        if (CommonFunction.checkNull(corporateDetailVo.getProofOfIdentityDocumentExpDate()).trim().equalsIgnoreCase(""))
          insertPrepStmtObject3.addNull();
        else {
          insertPrepStmtObject3.addString(corporateDetailVo.getProofOfIdentityDocumentExpDate().trim());
        }
        if (CommonFunction.checkNull(corporateDetailVo.getProofOfAddressDocument()).trim().equalsIgnoreCase(""))
          insertPrepStmtObject3.addNull();
        else {
          insertPrepStmtObject3.addString(corporateDetailVo.getProofOfAddressDocument().trim());
        }
        if (CommonFunction.checkNull(corporateDetailVo.getProofOfAddressDocumentNo()).trim().equalsIgnoreCase(""))
          insertPrepStmtObject3.addNull();
        else {
          insertPrepStmtObject3.addString(corporateDetailVo.getProofOfAddressDocumentNo().trim());
        }
        if (CommonFunction.checkNull(corporateDetailVo.getProofOfAddressDocumentExpDate()).trim().equalsIgnoreCase(""))
          insertPrepStmtObject3.addNull();
        else {
          insertPrepStmtObject3.addString(corporateDetailVo.getProofOfAddressDocumentExpDate().trim());
        }
        if (CommonFunction.checkNull(corporateDetailVo.getProjectedSales()).trim().equalsIgnoreCase(""))
          insertPrepStmtObject3.addNull();
        else {
          insertPrepStmtObject3.addString(corporateDetailVo.getProjectedSales().trim());
        }
        if (CommonFunction.checkNull(corporateDetailVo.getProjectedExports()).trim().equalsIgnoreCase(""))
          insertPrepStmtObject3.addNull();
        else {
          insertPrepStmtObject3.addString(corporateDetailVo.getProjectedExports().trim());
        }
        if (CommonFunction.checkNull(corporateDetailVo.getMinorityCommunity()).trim().equalsIgnoreCase(""))
          insertPrepStmtObject3.addNull();
        else {
          insertPrepStmtObject3.addString(corporateDetailVo.getMinorityCommunity().trim());
        }
        if (CommonFunction.checkNull(corporateDetailVo.getHandiCapped()).trim().equalsIgnoreCase(""))
          insertPrepStmtObject3.addNull();
        else {
          insertPrepStmtObject3.addString(corporateDetailVo.getHandiCapped().trim());
        }
        if (CommonFunction.checkNull(corporateDetailVo.getRiskCategory()).trim().equalsIgnoreCase(""))
          insertPrepStmtObject3.addNull();
        else
          insertPrepStmtObject3.addString(corporateDetailVo.getRiskCategory().trim());
        
        if (CommonFunction.checkNull(corporateDetailVo.getLbxcgtmseIndustry()).trim().equalsIgnoreCase(""))
            insertPrepStmtObject3.addNull();
          else
            insertPrepStmtObject3.addString(corporateDetailVo.getLbxcgtmseIndustry().trim());
        
        insertPrepStmtObject3.addInt(id);

        logger.info(new StringBuilder().append("value of gender:::::::::::::").append(CommonFunction.checkNull(corporateDetailVo.getGenderIndividual())).toString());

        insertPrepStmtObject3.setSql(queryUpdate);
        logger.info(new StringBuilder().append("in saveCorporateUpdate() of CorpotateDAOImpl  Query  :").append(insertPrepStmtObject3.printQuery()).toString());
        qryList2.add(insertPrepStmtObject3);
        status = ConnectionDAO.sqlInsUpdDeletePrepStmt(qryList2);
        saveCorporateUpdate1(ob, id, recStatus, statusCase, updateFlag, pageStatuss, source);
      }
      else
      {
        if ((statusCase != null) && (statusCase.length() > 0)) {
          tableName = "gcd_customer_m_temp";
        }
        else {
          tableName = "gcd_customer_m";
          if (CommonFunction.checkNull(source).trim().equalsIgnoreCase("ED")) {
            tableName = "gcd_customer_m_edit";
          }
        }
        String queryUpdate = "";
        customerType = ConnectionDAO.singleReturn(new StringBuilder().append("select CUSTOMER_TYPE from ").append(tableName).append(" where CUSTOMER_ID=").append(id).toString());

        StringBuilder bufInsUpdSql = new StringBuilder();
        PrepStmtObject insertPrepStmtObject3 = new PrepStmtObject();

        ArrayList qryList2 = new ArrayList();
        ArrayList insertUpdate = new ArrayList();
        boolean status1;
        if (!CommonFunction.checkNull(source).trim().equalsIgnoreCase("ED")) {
          bufInsUpdSql.append(" INSERT INTO GCD_CUSTOMER_HST ");
          bufInsUpdSql.append(" ( ");

          bufInsUpdSql.append(" CUSTOMER_ID,CUSTOMER_NAME,CUSTOMER_FNAME,CUSTOMER_MNAME,CUSTOMER_LNAME,CUSTOMER_CATEGORY,");
          bufInsUpdSql.append("CUSTOMER_DOB, CUSTOMER_CONSTITUTION,CUSTOMER_REGISTRATION_TYPE,");
          bufInsUpdSql.append("CUSTOMER_REGISTRATION_NO,CUSTMER_PAN,CUSTOMER_VAT_NO,CUSTOMER_INDUSTRY, CUSTOMER_SUB_INDUSTRY,CUSTOMER_BUSINESS_SEGMENT,");
          bufInsUpdSql.append("CUSTOMER_EMAIL,CUSTOMER_WEBSITE,CUSTOMER_REFERENCE,CUSTOMER_BLACKLIST, CUSTOMER_BLACKLIST_REASON,CUSTOMER_TYPE,MAKER_ID,");
          bufInsUpdSql.append("MAKER_DATE,GROUP_ID,AUTHOR_ID,");
          bufInsUpdSql.append("AUTHOR_DATE,FATHER_HUSBAND_NAME,CASTE_CATEGORY,");
          bufInsUpdSql.append("MARITAL_STATUS,DRIVING_LICENSE,VOTER_ID,PASSPORT_NUMBER,UID_NO,OTHER_NO,EDU_DETAIL,MVMT_DATE,MVMT_BY,GENDER,OTHOR_RELATIONSHIP_TYPE,OTHER_RELATIONSHIP_ID,");

          bufInsUpdSql.append("NATURE_OF_BUSINESS,YEAR_OF_ESTBLISHMENT,SHOP_ESTABLISHMENT_NO,SALES_TAX_TIN_NO,DGFT_NO,NO_BV_YEARS,NO_BV_MONTHS,industry_percent, MOTHER_FIRST_NAME,MOTHER_MIDDLE_NAME,MOTHER_LAST_NAME,CITIZENSHIP,RESIDENTIAL_STATUS,OCCPATION,UIDAI,NRI,PROOF_IDENTITY_DOCUMENT_NO, PROOF_IDENTITY_DOCUMENT_EXP_DATE,PROOF_ADDRESS_DOCUMENT_NO,PROOF_ADDRESS_DOCUMENT_EXP_DATE,RELATED_PERSON_TYPE,RELATED_PERSON_PRIFIX,RELATED_PERSON_FIRST_NAME, RELATED_PERSON_MIDDLE_NAME,RELATED_PERSON_LAST_NAME,RELATED_PERSON_CKYC_NUMBER,RELATED_PERSON_PROOF_IDENTITY_DOCUMENT,  RELATED_PERSON_PROOF_OF_IDENTITY_DOCUMENT_NO,RELATED_PERSON_PROOF_IDENTITY_DOCUMENT_EXP_DATE,CKYC,KYC_ACCOUNT_TYPE,CUSTOMER_NAME_PRIFX,   PROOF_OF_IDENTITY_DOCUMENT,PROOF_OF_IDENTITY_DOCUMENT_NO,PROOF_OF_IDENTITY_DOCUMENT_EXP_DATE,PROOF_OF_ADDRESS_DOCUMENT,PROOF_OF_ADDRESS_DOCUMENT_NO,PROOF_OF_ADDRESS_DOCUMENT_EXP_DATE, PROJECTED_SALES,PROJECTED_EXPORTS,MINORITY_COMMUNITY,HANDICAPPED,RISK_CATEGORY,UCIC_NO,UCIC_REJECT_REASON,MATCH_TYPE,UCIC_OUTPUT_FILE_NAME,CGTMSE_INDUSTRY ) ");

          bufInsUpdSql.append(" SELECT CUSTOMER_ID,CUSTOMER_NAME,CUSTOMER_FNAME,CUSTOMER_MNAME,CUSTOMER_LNAME,CUSTOMER_CATEGORY,");
          bufInsUpdSql.append("CUSTOMER_DOB, CUSTOMER_CONSTITUTION,CUSTOMER_REGISTRATION_TYPE,");
          bufInsUpdSql.append("CUSTOMER_REGISTRATION_NO,CUSTMER_PAN,CUSTOMER_VAT_NO,CUSTOMER_INDUSTRY, CUSTOMER_SUB_INDUSTRY,CUSTOMER_BUSINESS_SEGMENT,");
          bufInsUpdSql.append("CUSTOMER_EMAIL,CUSTOMER_WEBSITE,CUSTOMER_REFERENCE,CUSTOMER_BLACKLIST, CUSTOMER_BLACKLIST_REASON,CUSTOMER_TYPE,MAKER_ID,");
          bufInsUpdSql.append("MAKER_DATE,GROUP_ID,AUTHOR_ID,");
          bufInsUpdSql.append("AUTHOR_DATE,FATHER_HUSBAND_NAME,CASTE_CATEGORY,");
          bufInsUpdSql.append(new StringBuilder().append("MARITAL_STATUS,DRIVING_LICENSE,VOTER_ID,PASSPORT_NUMBER,UID_NO,OTHER_NO,EDU_DETAIL,DATE_ADD(STR_TO_DATE('").append(CommonFunction.checkNull(corporateDetailVo.getMakerDate())).append("','").append(this.dateFormatWithTime).append("'),INTERVAL CURTIME() HOUR_SECOND),'").append(CommonFunction.checkNull(corporateDetailVo.getMakerId())).append("',").toString());
          bufInsUpdSql.append("GENDER,OTHOR_RELATIONSHIP_TYPE,OTHER_RELATIONSHIP_ID,NATURE_OF_BUSINESS,YEAR_OF_ESTBLISHMENT,SHOP_ESTABLISHMENT_NO,SALES_TAX_TIN_NO,DGFT_NO,NO_BV_YEARS,NO_BV_MONTHS,industry_percent  ,MOTHER_FIRST_NAME,MOTHER_MIDDLE_NAME,MOTHER_LAST_NAME,CITIZENSHIP,RESIDENTIAL_STATUS,OCCPATION,UIDAI,NRI,PROOF_IDENTITY_DOCUMENT_NO,  PROOF_IDENTITY_DOCUMENT_EXP_DATE,PROOF_ADDRESS_DOCUMENT_NO,PROOF_ADDRESS_DOCUMENT_EXP_DATE,RELATED_PERSON_TYPE,RELATED_PERSON_PRIFIX,RELATED_PERSON_FIRST_NAME,  RELATED_PERSON_MIDDLE_NAME,RELATED_PERSON_LAST_NAME,RELATED_PERSON_CKYC_NUMBER,RELATED_PERSON_PROOF_IDENTITY_DOCUMENT,  RELATED_PERSON_PROOF_OF_IDENTITY_DOCUMENT_NO,RELATED_PERSON_PROOF_IDENTITY_DOCUMENT_EXP_DATE,CKYC,KYC_ACCOUNT_TYPE,CUSTOMER_NAME_PRIFX,  PROOF_OF_IDENTITY_DOCUMENT,PROOF_OF_IDENTITY_DOCUMENT_NO,PROOF_OF_IDENTITY_DOCUMENT_EXP_DATE,PROOF_OF_ADDRESS_DOCUMENT,PROOF_OF_ADDRESS_DOCUMENT_NO,PROOF_OF_ADDRESS_DOCUMENT_EXP_DATE, PROJECTED_SALES,PROJECTED_EXPORTS,MINORITY_COMMUNITY,HANDICAPPED,RISK_CATEGORY,UCIC_NO,UCIC_REJECT_REASON,MATCH_TYPE,UCIC_OUTPUT_FILE_NAME,CGTMSE_INDUSTRY ");

          bufInsUpdSql.append(new StringBuilder().append(" FROM GCD_CUSTOMER_M WHERE CUSTOMER_ID ='").append(id).append("' ").toString());

          insertPrepStmtObject3.setSql(bufInsUpdSql.toString());
          logger.info(new StringBuilder().append("insert history :").append(insertPrepStmtObject3.printQuery()).toString());
          insertUpdate.add(insertPrepStmtObject3);

          status1 = ConnectionDAO.sqlInsUpdDeletePrepStmt(insertUpdate);
        }
        logger.info(new StringBuilder().append("CorporateDAOImpl saveCorporateUpdate().............recStatus..........................").append(recStatus).toString());
        
        String emailQuery="select ifnull(customer_Email,'') from "+tableName+" where customer_id='"+id+"' ";
        String previousEmailId=ConnectionDAO.singleReturn(emailQuery);
        if(!CommonFunction.checkNull(corporateDetailVo.getInstitutionEmail()).equalsIgnoreCase(previousEmailId)){
        String query="update "+tableName+" set EmailVerificationFlag='N' where customer_id='"+id+"' ";
        ArrayList upList=new ArrayList();
        upList.add(query);
        boolean st=ConnectionDAO.sqlInsUpdDelete(upList);
        }
        
        if ((tableName.equalsIgnoreCase("gcd_customer_m")) || (tableName.equalsIgnoreCase("gcd_customer_m_edit")))
        {
          queryUpdate = new StringBuilder().append("update ").append(tableName).append(" set CUSTOMER_NAME =?,CUSTOMER_FNAME=?,CUSTOMER_MNAME=?,CUSTOMER_LNAME=?,CUSTOMER_CATEGORY=?,CUSTOMER_DOB=STR_TO_DATE(?,'").append(this.dateFormat).append("'),").append(" CUSTOMER_CONSTITUTION=?,CUSTOMER_REGISTRATION_TYPE=?,CUSTOMER_REGISTRATION_NO=?,CUSTMER_PAN=?,CUSTOMER_VAT_NO=?,CUSTOMER_INDUSTRY=?,").append(" CUSTOMER_SUB_INDUSTRY=?,CUSTOMER_BUSINESS_SEGMENT=?,CUSTOMER_EMAIL=?,CUSTOMER_WEBSITE=?,CUSTOMER_REFERENCE=?,CUSTOMER_BLACKLIST=?,").append(" CUSTOMER_BLACKLIST_REASON=?,CUSTOMER_TYPE=?,MAKER_ID=?,MAKER_DATE=DATE_ADD(STR_TO_DATE(?, '").append(this.dateFormatWithTime).append("'),INTERVAL CURTIME() HOUR_SECOND),AUTHOR_ID=?,AUTHOR_DATE=DATE_ADD(STR_TO_DATE(?, '").append(this.dateFormatWithTime).append("'),INTERVAL CURTIME() HOUR_SECOND),FATHER_HUSBAND_NAME=?,CASTE_CATEGORY=?,MARITAL_STATUS=?,DRIVING_LICENSE=?,VOTER_ID=?,PASSPORT_NUMBER=? , UID_NO=?, OTHER_NO=? ,EDU_DETAIL=?, GROUP_ID=? ,GENDER=?,OTHOR_RELATIONSHIP_TYPE=?,OTHER_RELATIONSHIP_ID=?").append(" ,NATURE_OF_BUSINESS=?,YEAR_OF_ESTBLISHMENT=?,SHOP_ESTABLISHMENT_NO=?,SALES_TAX_TIN_NO=?,DGFT_NO=?,NO_BV_YEARS=?,NO_BV_MONTHS=?,industry_percent=? ").append(" ,MOTHER_FIRST_NAME=?,MOTHER_MIDDLE_NAME=?,MOTHER_LAST_NAME=?,CITIZENSHIP=?,RESIDENTIAL_STATUS=?,OCCPATION=?,UIDAI=?,NRI=?,PROOF_IDENTITY_DOCUMENT_NO=?, ").append(" PROOF_IDENTITY_DOCUMENT_EXP_DATE=?,PROOF_ADDRESS_DOCUMENT_NO=?,PROOF_ADDRESS_DOCUMENT_EXP_DATE=?,RELATED_PERSON_TYPE=?,RELATED_PERSON_PRIFIX=?,RELATED_PERSON_FIRST_NAME=?, ").append(" RELATED_PERSON_MIDDLE_NAME=?,RELATED_PERSON_LAST_NAME=?,RELATED_PERSON_CKYC_NUMBER=?,RELATED_PERSON_PROOF_IDENTITY_DOCUMENT=?, ").append(" RELATED_PERSON_PROOF_OF_IDENTITY_DOCUMENT_NO=?,RELATED_PERSON_PROOF_IDENTITY_DOCUMENT_EXP_DATE=DATE_ADD(STR_TO_DATE(?, '").append(this.dateFormatWithTime).append("'),INTERVAL CURTIME() HOUR_SECOND),CKYC=?,KYC_ACCOUNT_TYPE=?,CUSTOMER_NAME_PRIFX=?, ").append(" PROOF_OF_IDENTITY_DOCUMENT=?,PROOF_OF_IDENTITY_DOCUMENT_NO=?,PROOF_OF_IDENTITY_DOCUMENT_EXP_DATE=?,PROOF_OF_ADDRESS_DOCUMENT=?,PROOF_OF_ADDRESS_DOCUMENT_NO=?,PROOF_OF_ADDRESS_DOCUMENT_EXP_DATE=?,").append(" PROJECTED_SALES=?,PROJECTED_EXPORTS=?,MINORITY_COMMUNITY=?,HANDICAPPED=?,RISK_CATEGORY=?,CGTMSE_INDUSTRY=? ").append(" where CUSTOMER_ID=?").toString();
        }
        else
        {
          queryUpdate = new StringBuilder().append("update ").append(tableName).append(" set CUSTOMER_NAME =?,CUSTOMER_FNAME=?,CUSTOMER_MNAME=?,CUSTOMER_LNAME=?,CUSTOMER_CATEGORY=?,CUSTOMER_DOB=STR_TO_DATE(?,'").append(this.dateFormat).append("'),").append(" CUSTOMER_CONSTITUTION=?,CUSTOMER_REGISTRATION_TYPE=?,CUSTOMER_REGISTRATION_NO=?,CUSTMER_PAN=?,CUSTOMER_VAT_NO=?,CUSTOMER_INDUSTRY=?,").append(" CUSTOMER_SUB_INDUSTRY=?,CUSTOMER_BUSINESS_SEGMENT=?,CUSTOMER_EMAIL=?,CUSTOMER_WEBSITE=?,CUSTOMER_REFERENCE=?,CUSTOMER_BLACKLIST=?,").append(" CUSTOMER_BLACKLIST_REASON=?,CUSTOMER_TYPE=?,FATHER_HUSBAND_NAME=?,CASTE_CATEGORY=?,MARITAL_STATUS=?,DRIVING_LICENSE=?,VOTER_ID=?,PASSPORT_NUMBER=? , UID_NO=?, OTHER_NO=?, EDU_DETAIL=?, GROUP_ID=?,GENDER=?,OTHOR_RELATIONSHIP_TYPE=?,OTHER_RELATIONSHIP_ID=?,NATURE_OF_BUSINESS=?,YEAR_OF_ESTBLISHMENT=?,SHOP_ESTABLISHMENT_NO=?,SALES_TAX_TIN_NO=?,DGFT_NO=?,NO_BV_YEARS=?,NO_BV_MONTHS=?,MAKER_ID=?,industry_percent=? ").append(" ,MOTHER_FIRST_NAME=?,MOTHER_MIDDLE_NAME=?,MOTHER_LAST_NAME=?,CITIZENSHIP=?,RESIDENTIAL_STATUS=?,OCCPATION=?,UIDAI=?,NRI=?,PROOF_IDENTITY_DOCUMENT_NO=?, ").append(" PROOF_IDENTITY_DOCUMENT_EXP_DATE=?,PROOF_ADDRESS_DOCUMENT_NO=?,PROOF_ADDRESS_DOCUMENT_EXP_DATE=?,RELATED_PERSON_TYPE=?,RELATED_PERSON_PRIFIX=?,RELATED_PERSON_FIRST_NAME=?, ").append(" RELATED_PERSON_MIDDLE_NAME=?,RELATED_PERSON_LAST_NAME=?,RELATED_PERSON_CKYC_NUMBER=?,RELATED_PERSON_PROOF_IDENTITY_DOCUMENT=?, ").append(" RELATED_PERSON_PROOF_OF_IDENTITY_DOCUMENT_NO=?,RELATED_PERSON_PROOF_IDENTITY_DOCUMENT_EXP_DATE=?,CKYC=?,KYC_ACCOUNT_TYPE=?,CUSTOMER_NAME_PRIFX=?, ").append(" PROOF_OF_IDENTITY_DOCUMENT=?,PROOF_OF_IDENTITY_DOCUMENT_NO=?,PROOF_OF_IDENTITY_DOCUMENT_EXP_DATE=?,PROOF_OF_ADDRESS_DOCUMENT=?,PROOF_OF_ADDRESS_DOCUMENT_NO=?,PROOF_OF_ADDRESS_DOCUMENT_EXP_DATE=?, ").append(" PROJECTED_SALES=?,PROJECTED_EXPORTS=?,MINORITY_COMMUNITY=?,HANDICAPPED=?,RISK_CATEGORY=?,CGTMSE_INDUSTRY=? ").append(" where CUSTOMER_ID=?").toString();
        }

        logger.info(new StringBuilder().append("in saveCorporateUpdate() of CorpotateDAOImpl update query : ").append(queryUpdate).toString());

        if (CommonFunction.checkNull(corporateDetailVo.getCorporateName()).trim().equalsIgnoreCase(""))
          insertPrepStmtObject3.addNull();
        else {
          insertPrepStmtObject3.addString(CommonFunction.checkNull(corporateDetailVo.getCorporateName()).trim());
        }
        if (CommonFunction.checkNull(corporateDetailVo.getFirstName()).trim().equalsIgnoreCase(""))
          insertPrepStmtObject3.addNull();
        else {
          insertPrepStmtObject3.addString(CommonFunction.checkNull(corporateDetailVo.getFirstName()).trim());
        }
        if (CommonFunction.checkNull(corporateDetailVo.getMiddleName()).trim().equalsIgnoreCase(""))
          insertPrepStmtObject3.addNull();
        else {
          insertPrepStmtObject3.addString(CommonFunction.checkNull(corporateDetailVo.getMiddleName()).trim());
        }
        if (CommonFunction.checkNull(corporateDetailVo.getLastName()).trim().equalsIgnoreCase(""))
          insertPrepStmtObject3.addNull();
        else {
          insertPrepStmtObject3.addString(CommonFunction.checkNull(corporateDetailVo.getLastName()).trim());
        }
        if (CommonFunction.checkNull(corporateDetailVo.getCorporateCategory()).trim().equalsIgnoreCase(""))
          insertPrepStmtObject3.addNull();
        else {
          insertPrepStmtObject3.addString(CommonFunction.checkNull(corporateDetailVo.getCorporateCategory()).trim());
        }
        if (CommonFunction.checkNull(corporateDetailVo.getIncorporationDate()).trim().equalsIgnoreCase(""))
          insertPrepStmtObject3.addNull();
        else {
          insertPrepStmtObject3.addString(CommonFunction.checkNull(corporateDetailVo.getIncorporationDate()).trim());
        }
        if (CommonFunction.checkNull(corporateDetailVo.getConstitution()).trim().equalsIgnoreCase(""))
          insertPrepStmtObject3.addNull();
        else {
          insertPrepStmtObject3.addString(CommonFunction.checkNull(corporateDetailVo.getConstitution()).trim());
        }
        if (CommonFunction.checkNull(corporateDetailVo.getRegistrationType()).trim().equalsIgnoreCase(""))
          insertPrepStmtObject3.addNull();
        else {
          insertPrepStmtObject3.addString(CommonFunction.checkNull(corporateDetailVo.getRegistrationType()).trim());
        }
        if (CommonFunction.checkNull(corporateDetailVo.getRegistrationNo()).trim().equalsIgnoreCase(""))
          insertPrepStmtObject3.addNull();
        else {
          insertPrepStmtObject3.addString(CommonFunction.checkNull(corporateDetailVo.getRegistrationNo()).trim());
        }
        if (CommonFunction.checkNull(corporateDetailVo.getPan()).trim().equalsIgnoreCase(""))
          insertPrepStmtObject3.addNull();
        else {
          insertPrepStmtObject3.addString(CommonFunction.checkNull(corporateDetailVo.getPan()).trim());
        }
        if (CommonFunction.checkNull(corporateDetailVo.getVatRegNo()).trim().equalsIgnoreCase(""))
          insertPrepStmtObject3.addNull();
        else {
          insertPrepStmtObject3.addString(CommonFunction.checkNull(corporateDetailVo.getVatRegNo()).trim());
        }
        if (CommonFunction.checkNull(corporateDetailVo.getLbxIndustry()).trim().equalsIgnoreCase(""))
          insertPrepStmtObject3.addNull();
        else {
          insertPrepStmtObject3.addString(CommonFunction.checkNull(corporateDetailVo.getLbxIndustry()).trim());
        }
        if (CommonFunction.checkNull(corporateDetailVo.getLbxSubIndustry()).trim().equalsIgnoreCase(""))
          insertPrepStmtObject3.addNull();
        else {
          insertPrepStmtObject3.addString(CommonFunction.checkNull(corporateDetailVo.getLbxSubIndustry()).trim());
        }
        if (CommonFunction.checkNull(corporateDetailVo.getBusinessSegment()).trim().equalsIgnoreCase(""))
          insertPrepStmtObject3.addNull();
        else {
          insertPrepStmtObject3.addString(CommonFunction.checkNull(corporateDetailVo.getBusinessSegment()).trim());
        }
        if (CommonFunction.checkNull(corporateDetailVo.getInstitutionEmail()).trim().equalsIgnoreCase(""))
          insertPrepStmtObject3.addNull();
        else {
          insertPrepStmtObject3.addString(CommonFunction.checkNull(corporateDetailVo.getInstitutionEmail()).trim());
        }
        if (CommonFunction.checkNull(corporateDetailVo.getWebAddress()).trim().equalsIgnoreCase(""))
          insertPrepStmtObject3.addNull();
        else {
          insertPrepStmtObject3.addString(CommonFunction.checkNull(corporateDetailVo.getWebAddress()).trim());
        }
        if (CommonFunction.checkNull(corporateDetailVo.getReferredBy()).trim().equalsIgnoreCase(""))
          insertPrepStmtObject3.addNull();
        else {
          insertPrepStmtObject3.addString(CommonFunction.checkNull(corporateDetailVo.getReferredBy()).trim());
        }
        if (CommonFunction.checkNull(corporateDetailVo.getBlackListed()).trim().equalsIgnoreCase("on"))
          insertPrepStmtObject3.addString("Y");
        else {
          insertPrepStmtObject3.addString("N");
        }
        if (CommonFunction.checkNull(corporateDetailVo.getReasonForBlackListed()).trim().equalsIgnoreCase(""))
          insertPrepStmtObject3.addNull();
        else {
          insertPrepStmtObject3.addString(CommonFunction.checkNull(corporateDetailVo.getReasonForBlackListed()).trim());
        }
        if (CommonFunction.checkNull(customerType).trim().equalsIgnoreCase(""))
          insertPrepStmtObject3.addNull();
        else {
          insertPrepStmtObject3.addString(CommonFunction.checkNull(customerType).trim());
        }
        if ((tableName.equalsIgnoreCase("gcd_customer_m")) || (tableName.equalsIgnoreCase("gcd_customer_m_edit")))
        {
          if (CommonFunction.checkNull(corporateDetailVo.getMakerId()).trim().equalsIgnoreCase(""))
            insertPrepStmtObject3.addNull();
          else {
            insertPrepStmtObject3.addString(corporateDetailVo.getMakerId().trim());
          }
          if (CommonFunction.checkNull(corporateDetailVo.getMakerDate()).trim().equalsIgnoreCase(""))
            insertPrepStmtObject3.addNull();
          else {
            insertPrepStmtObject3.addString(corporateDetailVo.getMakerDate().trim());
          }
          if (CommonFunction.checkNull(corporateDetailVo.getMakerId()).trim().equalsIgnoreCase(""))
            insertPrepStmtObject3.addNull();
          else {
            insertPrepStmtObject3.addString(corporateDetailVo.getMakerId().trim());
          }
          if (CommonFunction.checkNull(corporateDetailVo.getMakerDate()).trim().equalsIgnoreCase(""))
            insertPrepStmtObject3.addNull();
          else
            insertPrepStmtObject3.addString(corporateDetailVo.getMakerDate().trim());
        }
        if (CommonFunction.checkNull(corporateDetailVo.getFatherHusband()).trim().equalsIgnoreCase(""))
          insertPrepStmtObject3.addNull();
        else {
          insertPrepStmtObject3.addString(corporateDetailVo.getFatherHusband().trim());
        }
        if (CommonFunction.checkNull(corporateDetailVo.getCasteCategory()).trim().equalsIgnoreCase(""))
          insertPrepStmtObject3.addNull();
        else {
          insertPrepStmtObject3.addString(corporateDetailVo.getCasteCategory().trim());
        }
        if (CommonFunction.checkNull(corporateDetailVo.getMaritalStatus()).trim().equalsIgnoreCase(""))
          insertPrepStmtObject3.addNull();
        else {
          insertPrepStmtObject3.addString(corporateDetailVo.getMaritalStatus().trim());
        }
        if (CommonFunction.checkNull(corporateDetailVo.getDrivingLicense()).trim().equalsIgnoreCase(""))
          insertPrepStmtObject3.addNull();
        else {
          insertPrepStmtObject3.addString(corporateDetailVo.getDrivingLicense().trim());
        }
        if (CommonFunction.checkNull(corporateDetailVo.getVoterId()).trim().equalsIgnoreCase(""))
          insertPrepStmtObject3.addNull();
        else {
          insertPrepStmtObject3.addString(corporateDetailVo.getVoterId().trim());
        }
        if (CommonFunction.checkNull(corporateDetailVo.getPassport()).trim().equalsIgnoreCase(""))
          insertPrepStmtObject3.addNull();
        else {
          insertPrepStmtObject3.addString(corporateDetailVo.getPassport().trim());
        }

        if (CommonFunction.checkNull(corporateDetailVo.getAadhaar()).trim().equalsIgnoreCase(""))
          insertPrepStmtObject3.addNull();
        else {
          insertPrepStmtObject3.addString(corporateDetailVo.getAadhaar().trim());
        }
        if (CommonFunction.checkNull(corporateDetailVo.getOther()).trim().equalsIgnoreCase(""))
          insertPrepStmtObject3.addNull();
        else {
          insertPrepStmtObject3.addString(corporateDetailVo.getOther().trim());
        }
        if (CommonFunction.checkNull(corporateDetailVo.getEduDetailInd()).trim().equalsIgnoreCase(""))
          insertPrepStmtObject3.addNull();
        else {
          insertPrepStmtObject3.addString(corporateDetailVo.getEduDetailInd().trim());
        }

        if (CommonFunction.checkNull(corporateDetailVo.gethGroupId()).trim().equalsIgnoreCase(""))
          insertPrepStmtObject3.addNull();
        else {
          insertPrepStmtObject3.addString(corporateDetailVo.gethGroupId().trim());
        }

        if (CommonFunction.checkNull(corporateDetailVo.getGenderIndividual()).trim().equalsIgnoreCase(""))
          insertPrepStmtObject3.addNull();
        else {
          insertPrepStmtObject3.addString(corporateDetailVo.getGenderIndividual().trim());
        }

        if (CommonFunction.checkNull(corporateDetailVo.getOtherRelationShip()).trim().equalsIgnoreCase(""))
          insertPrepStmtObject3.addNull();
        else {
          insertPrepStmtObject3.addString(corporateDetailVo.getOtherRelationShip().trim());
        }
        if (CommonFunction.checkNull(corporateDetailVo.getOtherRelationShip()).trim().equalsIgnoreCase("CS"))
        {
          insertPrepStmtObject3.addNull();
        }
        else if (CommonFunction.checkNull(corporateDetailVo.getLbxBusinessPartnearHID()).trim().equalsIgnoreCase(""))
          insertPrepStmtObject3.addNull();
        else {
          insertPrepStmtObject3.addString(corporateDetailVo.getLbxBusinessPartnearHID().trim());
        }

        if (CommonFunction.checkNull(corporateDetailVo.getNatureOfBus()).trim().equalsIgnoreCase(""))
          insertPrepStmtObject3.addNull();
        else
          insertPrepStmtObject3.addString(corporateDetailVo.getNatureOfBus().trim());
        if (CommonFunction.checkNull(corporateDetailVo.getYearOfEstb()).trim().equalsIgnoreCase(""))
          insertPrepStmtObject3.addNull();
        else
          insertPrepStmtObject3.addString(corporateDetailVo.getYearOfEstb().trim());
        if (CommonFunction.checkNull(corporateDetailVo.getShopEstbNo()).trim().equalsIgnoreCase(""))
          insertPrepStmtObject3.addNull();
        else
          insertPrepStmtObject3.addString(corporateDetailVo.getShopEstbNo().trim());
        if (CommonFunction.checkNull(corporateDetailVo.getSalesTax()).trim().equalsIgnoreCase(""))
          insertPrepStmtObject3.addNull();
        else
          insertPrepStmtObject3.addString(corporateDetailVo.getSalesTax().trim());
        if (CommonFunction.checkNull(corporateDetailVo.getDgftNo()).trim().equalsIgnoreCase(""))
          insertPrepStmtObject3.addNull();
        else {
          insertPrepStmtObject3.addString(corporateDetailVo.getDgftNo().trim());
        }

        if (CommonFunction.checkNull(corporateDetailVo.getNoBVYears()).trim().equalsIgnoreCase(""))
          insertPrepStmtObject3.addNull();
        else
          insertPrepStmtObject3.addString(corporateDetailVo.getNoBVYears().trim());
        if (CommonFunction.checkNull(corporateDetailVo.getNoBVMonths()).trim().equalsIgnoreCase(""))
          insertPrepStmtObject3.addNull();
        else
          insertPrepStmtObject3.addString(corporateDetailVo.getNoBVMonths().trim());
        if (tableName.equalsIgnoreCase("gcd_customer_m_temp"))
        {
          if (CommonFunction.checkNull(corporateDetailVo.getMakerId()).trim().equalsIgnoreCase(""))
            insertPrepStmtObject3.addNull();
          else {
            insertPrepStmtObject3.addString(corporateDetailVo.getMakerId().trim());
          }
        }
        if (CommonFunction.checkNull(corporateDetailVo.getIndustryPercent()).trim().equalsIgnoreCase(""))
          insertPrepStmtObject3.addNull();
        else {
          insertPrepStmtObject3.addString(corporateDetailVo.getIndustryPercent().trim());
        }
        if (CommonFunction.checkNull(corporateDetailVo.getMotherFName()).trim().equalsIgnoreCase(""))
          insertPrepStmtObject3.addNull();
        else {
          insertPrepStmtObject3.addString(corporateDetailVo.getMotherFName().trim());
        }
        if (CommonFunction.checkNull(corporateDetailVo.getMotherMName()).trim().equalsIgnoreCase(""))
          insertPrepStmtObject3.addNull();
        else {
          insertPrepStmtObject3.addString(corporateDetailVo.getMotherMName().trim());
        }
        if (CommonFunction.checkNull(corporateDetailVo.getMotherLName()).trim().equalsIgnoreCase(""))
          insertPrepStmtObject3.addNull();
        else {
          insertPrepStmtObject3.addString(corporateDetailVo.getMotherLName().trim());
        }
        if (CommonFunction.checkNull(corporateDetailVo.getCitizenship()).trim().equalsIgnoreCase(""))
          insertPrepStmtObject3.addNull();
        else {
          insertPrepStmtObject3.addString(corporateDetailVo.getCitizenship().trim());
        }
        if (CommonFunction.checkNull(corporateDetailVo.getResidentialStatus()).trim().equalsIgnoreCase(""))
          insertPrepStmtObject3.addNull();
        else {
          insertPrepStmtObject3.addString(corporateDetailVo.getResidentialStatus().trim());
        }
        if (CommonFunction.checkNull(corporateDetailVo.getOccpation()).trim().equalsIgnoreCase(""))
          insertPrepStmtObject3.addNull();
        else {
          insertPrepStmtObject3.addString(corporateDetailVo.getOccpation().trim());
        }
        if (CommonFunction.checkNull(corporateDetailVo.getuIDAI()).trim().equalsIgnoreCase(""))
          insertPrepStmtObject3.addNull();
        else {
          insertPrepStmtObject3.addString(corporateDetailVo.getuIDAI().trim());
        }
        if (CommonFunction.checkNull(corporateDetailVo.getPesTaxPrsJuriOutSideInd()).trim().equalsIgnoreCase(""))
          insertPrepStmtObject3.addNull();
        else {
          insertPrepStmtObject3.addString(corporateDetailVo.getPesTaxPrsJuriOutSideInd().trim());
        }
        if (CommonFunction.checkNull(corporateDetailVo.getPrfIdentityDocNo()).trim().equalsIgnoreCase(""))
          insertPrepStmtObject3.addNull();
        else {
          insertPrepStmtObject3.addString(corporateDetailVo.getPrfIdentityDocNo().trim());
        }
        if (CommonFunction.checkNull(corporateDetailVo.getPrfIdentityDocExpDate()).trim().equalsIgnoreCase(""))
          insertPrepStmtObject3.addNull();
        else {
          insertPrepStmtObject3.addString(corporateDetailVo.getPrfIdentityDocExpDate().trim());
        }
        if (CommonFunction.checkNull(corporateDetailVo.getPrfAddressDocNo()).trim().equalsIgnoreCase(""))
          insertPrepStmtObject3.addNull();
        else {
          insertPrepStmtObject3.addString(corporateDetailVo.getPrfAddressDocNo().trim());
        }
        if (CommonFunction.checkNull(corporateDetailVo.getPrfAddressDocExpDate()).trim().equalsIgnoreCase(""))
          insertPrepStmtObject3.addNull();
        else {
          insertPrepStmtObject3.addString(corporateDetailVo.getPrfAddressDocExpDate().trim());
        }
        if (CommonFunction.checkNull(corporateDetailVo.getRelatedPersonType()).trim().equalsIgnoreCase(""))
          insertPrepStmtObject3.addNull();
        else {
          insertPrepStmtObject3.addString(corporateDetailVo.getRelatedPersonType().trim());
        }
        if (CommonFunction.checkNull(corporateDetailVo.getRelatedPersonPrifix()).trim().equalsIgnoreCase(""))
          insertPrepStmtObject3.addNull();
        else {
          insertPrepStmtObject3.addString(corporateDetailVo.getRelatedPersonPrifix().trim());
        }
        if (CommonFunction.checkNull(corporateDetailVo.getRelatedPersonFName()).trim().equalsIgnoreCase(""))
          insertPrepStmtObject3.addNull();
        else {
          insertPrepStmtObject3.addString(corporateDetailVo.getRelatedPersonFName().trim());
        }
        if (CommonFunction.checkNull(corporateDetailVo.getRelatedPersonMName()).trim().equalsIgnoreCase(""))
          insertPrepStmtObject3.addNull();
        else {
          insertPrepStmtObject3.addString(corporateDetailVo.getRelatedPersonMName().trim());
        }
        if (CommonFunction.checkNull(corporateDetailVo.getRelatedPersonLName()).trim().equalsIgnoreCase(""))
          insertPrepStmtObject3.addNull();
        else {
          insertPrepStmtObject3.addString(corporateDetailVo.getRelatedPersonLName().trim());
        }
        if (CommonFunction.checkNull(corporateDetailVo.getRelatedPersonCkycNo()).trim().equalsIgnoreCase(""))
          insertPrepStmtObject3.addNull();
        else {
          insertPrepStmtObject3.addString(corporateDetailVo.getRelatedPersonCkycNo().trim());
        }
        if (CommonFunction.checkNull(corporateDetailVo.getRelatedPersonPrfIdntyDoc()).trim().equalsIgnoreCase(""))
          insertPrepStmtObject3.addNull();
        else {
          insertPrepStmtObject3.addString(corporateDetailVo.getRelatedPersonPrfIdntyDoc().trim());
        }
        if (CommonFunction.checkNull(corporateDetailVo.getRelatedPersonPrfIdntyDocNo()).trim().equalsIgnoreCase(""))
          insertPrepStmtObject3.addNull();
        else {
          insertPrepStmtObject3.addString(corporateDetailVo.getRelatedPersonPrfIdntyDocNo().trim());
        }
        if (CommonFunction.checkNull(corporateDetailVo.getRelatedPersonPrfIdntyDocExpDate()).trim().equalsIgnoreCase(""))
          insertPrepStmtObject3.addNull();
        else {
          insertPrepStmtObject3.addString(corporateDetailVo.getRelatedPersonPrfIdntyDocExpDate().trim());
        }
        if (CommonFunction.checkNull(corporateDetailVo.getcKYC()).trim().equalsIgnoreCase(""))
          insertPrepStmtObject3.addNull();
        else {
          insertPrepStmtObject3.addString(corporateDetailVo.getcKYC().trim());
        }
        if (CommonFunction.checkNull(corporateDetailVo.getAccountType()).trim().equalsIgnoreCase(""))
          insertPrepStmtObject3.addNull();
        else {
          insertPrepStmtObject3.addString(corporateDetailVo.getAccountType().trim());
        }
        if (CommonFunction.checkNull(corporateDetailVo.getCustomerNamePrifx()).trim().equalsIgnoreCase(""))
          insertPrepStmtObject3.addNull();
        else {
          insertPrepStmtObject3.addString(corporateDetailVo.getCustomerNamePrifx().trim());
        }
        if (CommonFunction.checkNull(corporateDetailVo.getProofOfIdentityDocument()).trim().equalsIgnoreCase(""))
          insertPrepStmtObject3.addNull();
        else {
          insertPrepStmtObject3.addString(corporateDetailVo.getProofOfIdentityDocument().trim());
        }
        if (CommonFunction.checkNull(corporateDetailVo.getProofOfIdentityDocumentNo()).trim().equalsIgnoreCase(""))
          insertPrepStmtObject3.addNull();
        else {
          insertPrepStmtObject3.addString(corporateDetailVo.getProofOfIdentityDocumentNo().trim());
        }
        if (CommonFunction.checkNull(corporateDetailVo.getProofOfIdentityDocumentExpDate()).trim().equalsIgnoreCase(""))
          insertPrepStmtObject3.addNull();
        else {
          insertPrepStmtObject3.addString(corporateDetailVo.getProofOfIdentityDocumentExpDate().trim());
        }
        if (CommonFunction.checkNull(corporateDetailVo.getProofOfAddressDocument()).trim().equalsIgnoreCase(""))
          insertPrepStmtObject3.addNull();
        else {
          insertPrepStmtObject3.addString(corporateDetailVo.getProofOfAddressDocument().trim());
        }
        if (CommonFunction.checkNull(corporateDetailVo.getProofOfAddressDocumentNo()).trim().equalsIgnoreCase(""))
          insertPrepStmtObject3.addNull();
        else {
          insertPrepStmtObject3.addString(corporateDetailVo.getProofOfAddressDocumentNo().trim());
        }
        if (CommonFunction.checkNull(corporateDetailVo.getProofOfAddressDocumentExpDate()).trim().equalsIgnoreCase(""))
          insertPrepStmtObject3.addNull();
        else {
          insertPrepStmtObject3.addString(corporateDetailVo.getProofOfAddressDocumentExpDate().trim());
        }
        if (CommonFunction.checkNull(corporateDetailVo.getProjectedSales()).trim().equalsIgnoreCase(""))
          insertPrepStmtObject3.addNull();
        else {
          insertPrepStmtObject3.addString(corporateDetailVo.getProjectedSales().trim());
        }
        if (CommonFunction.checkNull(corporateDetailVo.getProjectedExports()).trim().equalsIgnoreCase(""))
          insertPrepStmtObject3.addNull();
        else {
          insertPrepStmtObject3.addString(corporateDetailVo.getProjectedExports().trim());
        }
        if (CommonFunction.checkNull(corporateDetailVo.getMinorityCommunity()).trim().equalsIgnoreCase(""))
          insertPrepStmtObject3.addNull();
        else {
          insertPrepStmtObject3.addString(corporateDetailVo.getMinorityCommunity().trim());
        }
        if (CommonFunction.checkNull(corporateDetailVo.getHandiCapped()).trim().equalsIgnoreCase(""))
          insertPrepStmtObject3.addNull();
        else {
          insertPrepStmtObject3.addString(corporateDetailVo.getHandiCapped().trim());
        }
        if (CommonFunction.checkNull(corporateDetailVo.getRiskCategory()).trim().equalsIgnoreCase(""))
          insertPrepStmtObject3.addNull();
        else {
          insertPrepStmtObject3.addString(corporateDetailVo.getRiskCategory().trim());
        }
        if (CommonFunction.checkNull(corporateDetailVo.getLbxcgtmseIndustry()).trim().equalsIgnoreCase(""))
            insertPrepStmtObject3.addNull();
          else {
            insertPrepStmtObject3.addString(corporateDetailVo.getLbxcgtmseIndustry().trim());
          }

        insertPrepStmtObject3.addInt(id);
        insertPrepStmtObject3.setSql(queryUpdate);
        logger.info(new StringBuilder().append("customer Update Query    :   ").append(insertPrepStmtObject3.printQuery()).toString());
        qryList2.add(insertPrepStmtObject3);
        logger.info("Ritu..............");
        status = ConnectionDAO.sqlInsUpdDeletePrepStmt(qryList2);
      }
    } catch (Exception e) {
      e.printStackTrace();
    }return status;
  }

  private boolean checkStatus(int id)
  {
    boolean flag = true;
    try {
      String query = new StringBuilder().append("select CUSTOMER_ID from gcd_customer_m_temp where CUSTOMER_ID=").append(id).toString();
      flag = ConnectionDAO.checkStatus(query);
      if (flag)
        flag = false;
    }
    catch (Exception e) {
      e.printStackTrace();
    }
    return flag;
  }

  public ArrayList<Object> getStates(String country)
  {
    ArrayList list = new ArrayList();
    try
    {
      String query = new StringBuilder().append("select distinct b.STATE_ID,b.STATE_DESC from com_address_m a left join com_state_m b on a.COUNTRY=b.COUNTRY_ID where a.BPID=").append(country).toString();
      logger.info(new StringBuilder().append("getStates: ").append(query).toString());
      DeatilOfCustomerAddress vo = null;
      ArrayList source = ConnectionDAO.sqlSelect(query);
      logger.info(new StringBuilder().append("getStates").append(source.size()).toString());
      for (int i = 0; i < source.size(); i++)
      {
        ArrayList subStates = (ArrayList)source.get(i);
        if (subStates.size() > 0)
        {
          vo = new DeatilOfCustomerAddress();
          vo.setState_code(CommonFunction.checkNull(subStates.get(0)).toString());
          vo.setState_name(CommonFunction.checkNull(subStates.get(1)).toString());
          list.add(vo);
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    return list;
  }

  public ArrayList<Object> getCities(String country)
  {
    ArrayList list = new ArrayList();
    try
    {
      String query = new StringBuilder().append("select distinct c.DISTRICT_ID,c.DISTRICT_DESC from com_address_m a left join com_state_m b on a.country=b.country_ID left join com_district_m c on a.STATE=c.STATE_ID where a.BPID=").append(country).toString();

      logger.info(new StringBuilder().append("getCities").append(query).toString());
      DeatilOfCustomerAddress vo = null;
      ArrayList source = ConnectionDAO.sqlSelect(query);
      logger.info(new StringBuilder().append("getCities").append(source.size()).toString());
      for (int i = 0; i < source.size(); i++)
      {
        ArrayList subCities = (ArrayList)source.get(i);
        if (subCities.size() > 0)
        {
          vo = new DeatilOfCustomerAddress();
          vo.setDist_code(CommonFunction.checkNull(subCities.get(0)).toString());
          vo.setDist_name(CommonFunction.checkNull(subCities.get(1)).toString());
          list.add(vo);
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    return list;
  }

  public int updateCustomerAddress(Object ob, int id, int addId, String recStatus, String statusCase, String updateFlag, String pageStatuss, String source)
  {
    logger.info("in updateCustomerAddress() of CorpotateDAOImpl");
    CustomerSaveVo vo = (CustomerSaveVo)ob;
    int status = 0;
    boolean qryStatus = false;
    String tableName = "";
    ArrayList qryList = new ArrayList();
    String subQuery = null;
    try
    {
      if (((pageStatuss != null) && (pageStatuss.equals("fromLeads"))) || ((updateFlag != null) && (updateFlag.equals("updateFlag"))))
      {
        tableName = "cr_deal_address_m";
        String mobileQuery="select PRIMARY_PHONE from "+tableName+" where address_id='"+addId+"' ";
        String previousMobile=ConnectionDAO.singleReturn(mobileQuery);
        if(!CommonFunction.checkNull(vo.getPrimaryPhoneNo()).equalsIgnoreCase(previousMobile)){
        String query="update "+tableName+" set MobileVerificationFlag='N' where address_id='"+addId+"' ";
        ArrayList upList=new ArrayList();
        upList.add(query);
        boolean st=ConnectionDAO.sqlInsUpdDelete(upList);
        }
        String query = new StringBuilder().append("update ").append(tableName).append(" set ADDRESS_TYPE=?,BPTYPE=?,BPID=?,ADDRESS_LINE1=?, ").append("ADDRESS_LINE2=?, ADDRESS_LINE3=?, COUNTRY=?, STATE=?, ").append("DISTRICT=?, PINCODE=?,PRIMARY_PHONE=?,ALTERNATE_PHONE=?,TOLLFREE_NUMBER=?,FAX=?, ").append("LANDMARK=?,NO_OF_YEARS=?,NO_OF_MONTHS=?,ADDRESS_DETAIL=?,MAKER_ID=?,").append("MAKER_DATE=DATE_ADD(STR_TO_DATE(?,'").append(this.dateFormatWithTime).append("'),INTERVAL CURTIME() HOUR_SECOND),AUTHOR_ID=?,AUTHOR_DATE=DATE_ADD(STR_TO_DATE(?,'").append(this.dateFormatWithTime).append("'),INTERVAL CURTIME() HOUR_SECOND),TAHSIL=?,BRANCH_DISTANCE=?,COMMUNICATION_ADDRESS=?,GST_NO=?,STD_NO=?,RELATIONSHIP_FLAG=? where ADDRESS_ID=?").toString();

        PrepStmtObject insertPrepStmtObject = new PrepStmtObject();
        if (CommonFunction.checkNull(vo.getAddr_type()).trim().equalsIgnoreCase(""))
          insertPrepStmtObject.addNull();
        else {
          insertPrepStmtObject.addString(vo.getAddr_type().trim());
        }
        if (CommonFunction.checkNull(vo.getBp_type()).trim().equalsIgnoreCase(""))
          insertPrepStmtObject.addNull();
        else {
          insertPrepStmtObject.addString(vo.getBp_type().trim());
        }
        if (vo.getBp_id() != "")
          insertPrepStmtObject.addString(vo.getBp_id().trim());
        else {
          insertPrepStmtObject.addString(vo.getBp_id1().trim());
        }
        if (CommonFunction.checkNull(vo.getAddr1()).trim().equalsIgnoreCase(""))
          insertPrepStmtObject.addNull();
        else {
          insertPrepStmtObject.addString(vo.getAddr1().trim());
        }
        if (CommonFunction.checkNull(vo.getAddr2()).trim().equalsIgnoreCase(""))
          insertPrepStmtObject.addNull();
        else {
          insertPrepStmtObject.addString(vo.getAddr2().trim());
        }
        if (CommonFunction.checkNull(vo.getAddr3()).trim().equalsIgnoreCase(""))
          insertPrepStmtObject.addNull();
        else {
          insertPrepStmtObject.addString(vo.getAddr3().trim());
        }
        if (CommonFunction.checkNull(vo.getTxtCountryCode()).trim().equalsIgnoreCase(""))
          insertPrepStmtObject.addNull();
        else {
          insertPrepStmtObject.addString(vo.getTxtCountryCode().trim());
        }
        if (CommonFunction.checkNull(vo.getTxtStateCode()).trim().equalsIgnoreCase(""))
          insertPrepStmtObject.addNull();
        else {
          insertPrepStmtObject.addString(vo.getTxtStateCode().trim());
        }
        if (CommonFunction.checkNull(vo.getTxtDistCode()).trim().equalsIgnoreCase(""))
          insertPrepStmtObject.addNull();
        else {
          insertPrepStmtObject.addString(vo.getTxtDistCode().trim());
        }
        if (CommonFunction.checkNull(vo.getPincode()).trim().equalsIgnoreCase(""))
          insertPrepStmtObject.addNull();
        else {
          insertPrepStmtObject.addString(vo.getPincode().trim());
        }
        if (CommonFunction.checkNull(vo.getPrimaryPhoneNo()).trim().equalsIgnoreCase(""))
          insertPrepStmtObject.addNull();
        else {
          insertPrepStmtObject.addString(vo.getPrimaryPhoneNo().trim());
        }
        if (CommonFunction.checkNull(vo.getAlternatePhoneNo()).trim().equalsIgnoreCase(""))
          insertPrepStmtObject.addNull();
        else {
          insertPrepStmtObject.addString(vo.getAlternatePhoneNo().trim());
        }
        if (CommonFunction.checkNull(vo.getTollfreeNo()).trim().equalsIgnoreCase(""))
          insertPrepStmtObject.addNull();
        else {
          insertPrepStmtObject.addString(vo.getTollfreeNo().trim());
        }
        if (CommonFunction.checkNull(vo.getFaxNo()).trim().equalsIgnoreCase(""))
          insertPrepStmtObject.addNull();
        else {
          insertPrepStmtObject.addString(vo.getFaxNo().trim());
        }
        if (CommonFunction.checkNull(vo.getLandMark()).trim().equalsIgnoreCase(""))
          insertPrepStmtObject.addNull();
        else {
          insertPrepStmtObject.addString(vo.getLandMark().trim());
        }
        if (CommonFunction.checkNull(vo.getNoYears()).trim().equalsIgnoreCase(""))
          insertPrepStmtObject.addNull();
        else {
          insertPrepStmtObject.addString(vo.getNoYears().trim());
        }
        if (CommonFunction.checkNull(vo.getNoMonths()).trim().equalsIgnoreCase(""))
          insertPrepStmtObject.addNull();
        else {
          insertPrepStmtObject.addString(vo.getNoMonths().trim());
        }
        if (CommonFunction.checkNull(vo.getAddDetails()).trim().equalsIgnoreCase(""))
          insertPrepStmtObject.addNull();
        else {
          insertPrepStmtObject.addString(vo.getAddDetails().trim());
        }

        if (StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getMakerId()).trim()).equalsIgnoreCase(""))
          insertPrepStmtObject.addNull();
        else {
          insertPrepStmtObject.addString(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getMakerId()).trim()));
        }
        if (StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getMakerDate()).trim()).equalsIgnoreCase(""))
          insertPrepStmtObject.addNull();
        else {
          insertPrepStmtObject.addString(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getMakerDate()).trim()));
        }

        if (StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getMakerId()).trim()).equalsIgnoreCase(""))
          insertPrepStmtObject.addNull();
        else {
          insertPrepStmtObject.addString(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getMakerId()).trim()));
        }
        if (StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getMakerDate()).trim()).equalsIgnoreCase(""))
          insertPrepStmtObject.addNull();
        else {
          insertPrepStmtObject.addString(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getMakerDate()).trim()));
        }

        if (StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getTahsil()).trim()).equalsIgnoreCase(""))
          insertPrepStmtObject.addNull();
        else {
          insertPrepStmtObject.addString(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getTahsil()).trim()));
        }
        if (StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getDistanceBranch()).trim()).equalsIgnoreCase(""))
          insertPrepStmtObject.addNull();
        else {
          insertPrepStmtObject.addString(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getDistanceBranch()).trim()));
        }
        if (CommonFunction.checkNull(vo.getCommunicationAddress()).trim().equalsIgnoreCase(""))
          insertPrepStmtObject.addNull();
        else
          insertPrepStmtObject.addString(vo.getCommunicationAddress().trim());
        if (CommonFunction.checkNull(vo.getGstIn()).trim().equalsIgnoreCase(""))
          insertPrepStmtObject.addNull();
        else {
          insertPrepStmtObject.addString(CommonFunction.checkNull(vo.getGstIn()).trim());
        }
        if (CommonFunction.checkNull(vo.getStdNo()).trim().equalsIgnoreCase(""))
            insertPrepStmtObject.addNull();
          else {
            insertPrepStmtObject.addString(CommonFunction.checkNull(vo.getStdNo()).trim());
          }
        if (CommonFunction.checkNull(vo.getRelation()).trim().equalsIgnoreCase(""))
            insertPrepStmtObject.addNull();
          else {
            insertPrepStmtObject.addString(CommonFunction.checkNull(vo.getRelation()).trim());
          }
        if (CommonFunction.checkNull(Integer.valueOf(addId)).trim().equalsIgnoreCase(""))
          insertPrepStmtObject.addNull();
        else
          insertPrepStmtObject.addInt(addId);
        insertPrepStmtObject.setSql(query.toString());
        logger.info(new StringBuilder().append("in updateCustomerAddress() of CorpotateDAOImpl  Query  :  ").append(insertPrepStmtObject.printQuery()).toString());
        qryList.add(insertPrepStmtObject);
        qryStatus = ConnectionDAO.sqlInsUpdDeletePrepStmt(qryList);
      }
      else
      {
        if ((statusCase != null) && (statusCase.length() > 0))
        {
          tableName = "com_address_m_temp";
        }
        else
        {
          tableName = "com_address_m";
          if (CommonFunction.checkNull(source).trim().equalsIgnoreCase("ED")) {
            tableName = "com_address_m_edit";
          }
        }
        String mobileQuery="select PRIMARY_PHONE from "+tableName+" where address_id='"+addId+"' ";
        String previousMobile=ConnectionDAO.singleReturn(mobileQuery);
        if(!CommonFunction.checkNull(vo.getPrimaryPhoneNo()).equalsIgnoreCase(previousMobile)){
        String query="update "+tableName+" set MobileVerificationFlag='N' where address_id='"+addId+"' ";
        ArrayList upList=new ArrayList();
        upList.add(query);
        boolean st=ConnectionDAO.sqlInsUpdDelete(upList);
        }
        PrepStmtObject insertPrepStmtObject = new PrepStmtObject();
        StringBuilder bufInsUpdSql = new StringBuilder();
        ArrayList qryList2 = new ArrayList();
        ArrayList insertUpdate = new ArrayList();
        boolean status1;
        if (!CommonFunction.checkNull(source).trim().equalsIgnoreCase("ED")) {
          bufInsUpdSql.append(" INSERT INTO com_address_hst ");
          bufInsUpdSql.append(" ( ");

          bufInsUpdSql.append(" ADDRESS_TYPE,BPTYPE,BPID,ADDRESS_LINE1, ADDRESS_LINE2, ADDRESS_LINE3, COUNTRY, STATE, DISTRICT,");
          bufInsUpdSql.append(" PINCODE,PRIMARY_PHONE,ALTERNATE_PHONE,TOLLFREE_NUMBER,FAX, LANDMARK,NO_OF_YEARS,NO_OF_MONTHS,");
          bufInsUpdSql.append("ADDRESS_DETAIL,COMMUNICATION_ADDRESS ,MAKER_ID,MAKER_DATE,AUTHOR_ID,AUTHOR_DATE ,TAHSIL,BRANCH_DISTANCE ,ADDRESS_ID,GST_NO,STD_NO,RELATIONSHIP_FLAG,MVMT_DATE,MVMT_BY");
          bufInsUpdSql.append(" ) ");

          bufInsUpdSql.append(" Select ADDRESS_TYPE,BPTYPE,BPID,ADDRESS_LINE1, ADDRESS_LINE2, ADDRESS_LINE3, COUNTRY, STATE, DISTRICT,");
          bufInsUpdSql.append(" PINCODE,PRIMARY_PHONE,ALTERNATE_PHONE,TOLLFREE_NUMBER,FAX, LANDMARK,NO_OF_YEARS,NO_OF_MONTHS,");
          bufInsUpdSql.append(new StringBuilder().append("ADDRESS_DETAIL,COMMUNICATION_ADDRESS ,MAKER_ID,MAKER_DATE,AUTHOR_ID,AUTHOR_DATE ,TAHSIL,BRANCH_DISTANCE,ADDRESS_ID,GST_NO,STD_NO,RELATIONSHIP_FLAG,DATE_ADD(STR_TO_DATE('").append(CommonFunction.checkNull(vo.getMakerDate())).append("','").append(this.dateFormatWithTime).append("'),INTERVAL CURTIME() HOUR_SECOND),'").append(CommonFunction.checkNull(vo.getMakerId())).append("' ").toString());

          bufInsUpdSql.append(new StringBuilder().append(" FROM com_address_m WHERE ADDRESS_ID ='").append(addId).append("' ").toString());

          insertPrepStmtObject.setSql(bufInsUpdSql.toString());
          logger.info(new StringBuilder().append("insert history :").append(insertPrepStmtObject.printQuery()).toString());
          insertUpdate.add(insertPrepStmtObject);

          status1 = ConnectionDAO.sqlInsUpdDeletePrepStmt(insertUpdate);
          
        }if ((tableName.equalsIgnoreCase("com_address_m")) || (tableName.equalsIgnoreCase("com_address_m_edit")))
        {
          subQuery = new StringBuilder().append(",MAKER_ID='").append(CommonFunction.checkNull(vo.getMakerId())).append("',").append("MAKER_DATE=DATE_ADD(STR_TO_DATE('").append(CommonFunction.checkNull(vo.getMakerDate())).append("','").append(this.dateFormatWithTime).append("'),INTERVAL CURTIME() HOUR_SECOND)").append(",AUTHOR_ID='").append(CommonFunction.checkNull(vo.getMakerId())).append("',AUTHOR_DATE=DATE_ADD(STR_TO_DATE('").append(CommonFunction.checkNull(vo.getMakerDate())).append("','").append(this.dateFormatWithTime).append("'),INTERVAL CURTIME() HOUR_SECOND) ").toString();
        }
        else
        {
          subQuery = "";
        }String query = new StringBuilder().append("update ").append(tableName).append(" set ADDRESS_TYPE=?,BPTYPE=?,BPID=?,ADDRESS_LINE1=?, ").append("ADDRESS_LINE2=?, ADDRESS_LINE3=?, COUNTRY=?, STATE=?, ").append("DISTRICT=?, PINCODE=?,PRIMARY_PHONE=?,ALTERNATE_PHONE=?,TOLLFREE_NUMBER=?,FAX=?, ").append("LANDMARK=?,NO_OF_YEARS=?,NO_OF_MONTHS=?,ADDRESS_DETAIL=?,COMMUNICATION_ADDRESS=? ").append(subQuery).append(",TAHSIL=?,BRANCH_DISTANCE=?,GST_NO=?,STD_NO=?,RELATIONSHIP_FLAG=?  where ADDRESS_ID=?").toString();

        if (CommonFunction.checkNull(vo.getAddr_type()).trim().equalsIgnoreCase(""))
          insertPrepStmtObject.addNull();
        else {
          insertPrepStmtObject.addString(vo.getAddr_type().trim());
        }
        if (CommonFunction.checkNull(vo.getBp_type()).trim().equalsIgnoreCase(""))
          insertPrepStmtObject.addNull();
        else {
          insertPrepStmtObject.addString(vo.getBp_type().trim());
        }
        if (vo.getBp_id() != "")
        {
          insertPrepStmtObject.addString(vo.getBp_id().trim());
        }
        else
        {
          insertPrepStmtObject.addString(vo.getBp_id1().trim());
        }

        if (CommonFunction.checkNull(vo.getAddr1()).trim().equalsIgnoreCase(""))
          insertPrepStmtObject.addNull();
        else {
          insertPrepStmtObject.addString(vo.getAddr1().trim());
        }
        if (CommonFunction.checkNull(vo.getAddr2()).trim().equalsIgnoreCase(""))
          insertPrepStmtObject.addNull();
        else {
          insertPrepStmtObject.addString(vo.getAddr2().trim());
        }
        if (CommonFunction.checkNull(vo.getAddr3()).trim().equalsIgnoreCase(""))
          insertPrepStmtObject.addNull();
        else {
          insertPrepStmtObject.addString(vo.getAddr3().trim());
        }
        if (CommonFunction.checkNull(vo.getTxtCountryCode()).trim().equalsIgnoreCase(""))
          insertPrepStmtObject.addNull();
        else {
          insertPrepStmtObject.addString(vo.getTxtCountryCode().trim());
        }
        if (CommonFunction.checkNull(vo.getTxtStateCode()).trim().equalsIgnoreCase(""))
          insertPrepStmtObject.addNull();
        else {
          insertPrepStmtObject.addString(vo.getTxtStateCode().trim());
        }
        if (CommonFunction.checkNull(vo.getTxtDistCode()).trim().equalsIgnoreCase(""))
          insertPrepStmtObject.addNull();
        else {
          insertPrepStmtObject.addString(vo.getTxtDistCode().trim());
        }
        if (CommonFunction.checkNull(vo.getPincode()).trim().equalsIgnoreCase(""))
          insertPrepStmtObject.addNull();
        else {
          insertPrepStmtObject.addString(vo.getPincode().trim());
        }
        if (CommonFunction.checkNull(vo.getPrimaryPhoneNo()).trim().equalsIgnoreCase(""))
          insertPrepStmtObject.addNull();
        else {
          insertPrepStmtObject.addString(vo.getPrimaryPhoneNo().trim());
        }
        if (CommonFunction.checkNull(vo.getAlternatePhoneNo()).trim().equalsIgnoreCase(""))
          insertPrepStmtObject.addNull();
        else {
          insertPrepStmtObject.addString(vo.getAlternatePhoneNo().trim());
        }
        if (CommonFunction.checkNull(vo.getTollfreeNo()).trim().equalsIgnoreCase(""))
          insertPrepStmtObject.addNull();
        else {
          insertPrepStmtObject.addString(vo.getTollfreeNo().trim());
        }
        if (CommonFunction.checkNull(vo.getFaxNo()).trim().equalsIgnoreCase(""))
          insertPrepStmtObject.addNull();
        else {
          insertPrepStmtObject.addString(vo.getFaxNo().trim());
        }
        if (CommonFunction.checkNull(vo.getLandMark()).trim().equalsIgnoreCase(""))
          insertPrepStmtObject.addNull();
        else {
          insertPrepStmtObject.addString(vo.getLandMark().trim());
        }
        if (CommonFunction.checkNull(vo.getNoYears()).trim().equalsIgnoreCase(""))
          insertPrepStmtObject.addNull();
        else {
          insertPrepStmtObject.addString(vo.getNoYears().trim());
        }
        if (CommonFunction.checkNull(vo.getNoMonths()).trim().equalsIgnoreCase(""))
          insertPrepStmtObject.addNull();
        else {
          insertPrepStmtObject.addString(vo.getNoMonths().trim());
        }
        if (CommonFunction.checkNull(vo.getAddDetails()).trim().equalsIgnoreCase(""))
          insertPrepStmtObject.addNull();
        else {
          insertPrepStmtObject.addString(vo.getAddDetails().trim());
        }
        if (CommonFunction.checkNull(vo.getCommunicationAddress()).trim().equalsIgnoreCase(""))
          insertPrepStmtObject.addNull();
        else {
          insertPrepStmtObject.addString(vo.getCommunicationAddress().trim());
        }
        if (StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getTahsil()).trim()).equalsIgnoreCase(""))
          insertPrepStmtObject.addNull();
        else {
          insertPrepStmtObject.addString(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getTahsil()).trim()));
        }
        if (StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getDistanceBranch()).trim()).equalsIgnoreCase(""))
          insertPrepStmtObject.addNull();
        else
          insertPrepStmtObject.addString(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getDistanceBranch()).trim()));
        if (StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getGstIn()).trim()).equalsIgnoreCase(""))
          insertPrepStmtObject.addNull();
        else {
          insertPrepStmtObject.addString(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getGstIn()).trim()));
        }
        if (StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getStdNo()).trim()).equalsIgnoreCase(""))
            insertPrepStmtObject.addNull();
          else {
            insertPrepStmtObject.addString(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getStdNo()).trim()));
          }
        if (StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getRelation()).trim()).equalsIgnoreCase(""))
            insertPrepStmtObject.addNull();
          else {
            insertPrepStmtObject.addString(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getRelation()).trim()));
          }
        if (CommonFunction.checkNull(Integer.valueOf(addId)).trim().equalsIgnoreCase(""))
          insertPrepStmtObject.addNull();
        else {
          insertPrepStmtObject.addInt(addId);
        }

        insertPrepStmtObject.setSql(query.toString());
        logger.info(new StringBuilder().append("in updateCustomerAddress() of CorpotateDAOImpl  Query  :  ").append(insertPrepStmtObject.printQuery()).toString());
        qryList.add(insertPrepStmtObject);

        qryStatus = ConnectionDAO.sqlInsUpdDeletePrepStmt(qryList);
      }
    }
    catch (Exception e) {
      e.printStackTrace();
    } finally {
      subQuery = null;
    }
    if (qryStatus)
    {
      status = 1;
    }
    return status;
  }

  public int updateIndReference(Object ob, int id, int refId, String recStatus, String statusCase, String updateFlag, String pageStatuss, String source) {
    logger.info("in updateCustomerAddress() of CorpotateDAOImpl");
    CustomerSaveVo vo = (CustomerSaveVo)ob;
    int status = 0;
    boolean qryStatus = false;
    String tableName = "";
    ArrayList qryList = new ArrayList();
    String subQuery = null;
    try
    {
      if (((pageStatuss != null) && (pageStatuss.equals("fromLeads"))) || ((updateFlag != null) && (updateFlag.equals("updateFlag"))))
      {
        tableName = "cr_deal_reference_m";
        String query = new StringBuilder().append("update ").append(tableName).append(" set BPID=?,BPTYPE=?,F_NAME=?,M_NAME=?,L_NAME=?, RELATIONSHIP=?, KNOWING_SINCE=?, MOBILE_NUMBER=?,LANDLINE_NUMBER=?, REF_ADDRESS=?,EMAIL_ID=?,").append("MAKER_ID=?,MAKER_DATE=DATE_ADD(STR_TO_DATE(?,'").append(this.dateFormatWithTime).append("'),INTERVAL CURTIME() HOUR_SECOND),AUTHOR_ID=?,AUTHOR_DATE=DATE_ADD(STR_TO_DATE(?,'").append(this.dateFormatWithTime).append("'),INTERVAL CURTIME() HOUR_SECOND) where REF_ID=?").toString();

        PrepStmtObject insertPrepStmtObject = new PrepStmtObject();

        if (CommonFunction.checkNull(vo.getBp_id()).trim().equalsIgnoreCase(""))
          insertPrepStmtObject.addString(vo.getBp_id1().trim());
        else {
          insertPrepStmtObject.addString(vo.getBp_id().trim());
        }
        if (CommonFunction.checkNull(vo.getBp_type()).trim().equalsIgnoreCase(""))
          insertPrepStmtObject.addNull();
        else {
          insertPrepStmtObject.addString(vo.getBp_type().trim());
        }
        if (CommonFunction.checkNull(vo.getFirstName()).trim().equalsIgnoreCase(""))
          insertPrepStmtObject.addNull();
        else {
          insertPrepStmtObject.addString(vo.getFirstName().trim());
        }
        if (CommonFunction.checkNull(vo.getMiddleName()).trim().equalsIgnoreCase(""))
          insertPrepStmtObject.addNull();
        else {
          insertPrepStmtObject.addString(vo.getMiddleName().trim());
        }
        if (CommonFunction.checkNull(vo.getLastName()).trim().equalsIgnoreCase(""))
          insertPrepStmtObject.addNull();
        else {
          insertPrepStmtObject.addString(vo.getLastName().trim());
        }
        if (CommonFunction.checkNull(vo.getRelationshipS()).trim().equalsIgnoreCase(""))
          insertPrepStmtObject.addNull();
        else {
          insertPrepStmtObject.addString(vo.getRelationshipS().trim());
        }
        if (CommonFunction.checkNull(vo.getKnowingSince()).trim().equalsIgnoreCase(""))
          insertPrepStmtObject.addNull();
        else {
          insertPrepStmtObject.addString(vo.getKnowingSince().trim());
        }
        if (CommonFunction.checkNull(vo.getPrimaryRefMbNo()).trim().equalsIgnoreCase(""))
          insertPrepStmtObject.addNull();
        else {
          insertPrepStmtObject.addString(vo.getPrimaryRefMbNo().trim());
        }
        if (CommonFunction.checkNull(vo.getAlternateRefPhNo()).trim().equalsIgnoreCase(""))
          insertPrepStmtObject.addNull();
        else {
          insertPrepStmtObject.addString(vo.getAlternateRefPhNo().trim());
        }
        if (CommonFunction.checkNull(vo.getAddRef()).trim().equalsIgnoreCase(""))
          insertPrepStmtObject.addNull();
        else {
          insertPrepStmtObject.addString(vo.getAddRef().trim());
        }
        if (CommonFunction.checkNull(vo.getInstitutionEmail()).trim().equalsIgnoreCase(""))
          insertPrepStmtObject.addNull();
        else {
          insertPrepStmtObject.addString(vo.getInstitutionEmail().trim());
        }

        if (StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getMakerId()).trim()).equalsIgnoreCase(""))
          insertPrepStmtObject.addNull();
        else {
          insertPrepStmtObject.addString(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getMakerId()).trim()));
        }
        if (StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getMakerDate()).trim()).equalsIgnoreCase(""))
          insertPrepStmtObject.addNull();
        else {
          insertPrepStmtObject.addString(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getMakerDate()).trim()));
        }

        if (StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getMakerId()).trim()).equalsIgnoreCase(""))
          insertPrepStmtObject.addNull();
        else {
          insertPrepStmtObject.addString(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getMakerId()).trim()));
        }
        if (StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getMakerDate()).trim()).equalsIgnoreCase(""))
          insertPrepStmtObject.addNull();
        else {
          insertPrepStmtObject.addString(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getMakerDate()).trim()));
        }

        if (CommonFunction.checkNull(Integer.valueOf(refId)).trim().equalsIgnoreCase(""))
          insertPrepStmtObject.addNull();
        else {
          insertPrepStmtObject.addInt(refId);
        }
        insertPrepStmtObject.setSql(query.toString());
        logger.info(new StringBuilder().append("in saveCustomerReference() of CorpotateDAOImpl  Query  :  ").append(insertPrepStmtObject.printQuery()).toString());
        qryList.add(insertPrepStmtObject);
        qryStatus = ConnectionDAO.sqlInsUpdDeletePrepStmt(qryList);
      }
      else
      {
        if ((statusCase != null) && (statusCase.length() > 0)) {
          tableName = "com_reference_m_temp";
        }
        else {
          tableName = "com_reference_m";
          if (CommonFunction.checkNull(source).trim().equalsIgnoreCase("ED")) {
            tableName = "com_reference_m_edit";
          }
        }
        if ((tableName.equalsIgnoreCase("com_reference_m")) || (tableName.equalsIgnoreCase("com_reference_m_edit")))
        {
          subQuery = new StringBuilder().append(",MAKER_ID='").append(CommonFunction.checkNull(vo.getMakerId())).append("',").append("MAKER_DATE=DATE_ADD(STR_TO_DATE('").append(CommonFunction.checkNull(vo.getMakerDate())).append("','").append(this.dateFormatWithTime).append("'),INTERVAL CURTIME() HOUR_SECOND),").append("AUTHOR_ID='").append(CommonFunction.checkNull(vo.getMakerId())).append("',AUTHOR_DATE=DATE_ADD(STR_TO_DATE('").append(CommonFunction.checkNull(vo.getMakerDate())).append("','").append(this.dateFormatWithTime).append("'),INTERVAL CURTIME() HOUR_SECOND) ").toString();
        }
        else
        {
          subQuery = "";
        }String query = new StringBuilder().append("update ").append(tableName).append(" set BPID=?,BPTYPE=?,F_NAME=?,M_NAME=?,L_NAME=?, RELATIONSHIP=?, KNOWING_SINCE=?, MOBILE_NUMBER=?,LANDLINE_NUMBER=?,REF_ADDRESS=?,EMAIL_ID=? ").append(subQuery).append(" where REF_ID=?").toString();

        PrepStmtObject insertPrepStmtObject = new PrepStmtObject();

        if (CommonFunction.checkNull(vo.getBp_id()).trim().equalsIgnoreCase(""))
          insertPrepStmtObject.addString(vo.getBp_id1().trim());
        else {
          insertPrepStmtObject.addString(vo.getBp_id().trim());
        }
        if (CommonFunction.checkNull(vo.getBp_type()).trim().equalsIgnoreCase(""))
          insertPrepStmtObject.addNull();
        else {
          insertPrepStmtObject.addString(vo.getBp_type().trim());
        }
        if (CommonFunction.checkNull(vo.getFirstName()).trim().equalsIgnoreCase(""))
          insertPrepStmtObject.addNull();
        else {
          insertPrepStmtObject.addString(vo.getFirstName().trim());
        }
        if (CommonFunction.checkNull(vo.getMiddleName()).trim().equalsIgnoreCase(""))
          insertPrepStmtObject.addNull();
        else {
          insertPrepStmtObject.addString(vo.getMiddleName().trim());
        }
        if (CommonFunction.checkNull(vo.getLastName()).trim().equalsIgnoreCase(""))
          insertPrepStmtObject.addNull();
        else {
          insertPrepStmtObject.addString(vo.getLastName().trim());
        }
        if (CommonFunction.checkNull(vo.getRelationshipS()).trim().equalsIgnoreCase(""))
          insertPrepStmtObject.addNull();
        else {
          insertPrepStmtObject.addString(vo.getRelationshipS().trim());
        }
        if (CommonFunction.checkNull(vo.getKnowingSince()).trim().equalsIgnoreCase(""))
          insertPrepStmtObject.addNull();
        else {
          insertPrepStmtObject.addString(vo.getKnowingSince().trim());
        }
        if (CommonFunction.checkNull(vo.getPrimaryRefMbNo()).trim().equalsIgnoreCase(""))
          insertPrepStmtObject.addNull();
        else {
          insertPrepStmtObject.addString(vo.getPrimaryRefMbNo().trim());
        }
        if (CommonFunction.checkNull(vo.getAlternateRefPhNo()).trim().equalsIgnoreCase(""))
          insertPrepStmtObject.addNull();
        else {
          insertPrepStmtObject.addString(vo.getAlternateRefPhNo().trim());
        }
        if (CommonFunction.checkNull(vo.getAddRef()).trim().equalsIgnoreCase(""))
          insertPrepStmtObject.addNull();
        else {
          insertPrepStmtObject.addString(vo.getAddRef().trim());
        }
        if (CommonFunction.checkNull(vo.getInstitutionEmail()).trim().equalsIgnoreCase(""))
          insertPrepStmtObject.addNull();
        else {
          insertPrepStmtObject.addString(vo.getInstitutionEmail().trim());
        }
        if (CommonFunction.checkNull(Integer.valueOf(refId)).trim().equalsIgnoreCase(""))
          insertPrepStmtObject.addNull();
        else {
          insertPrepStmtObject.addInt(refId);
        }
        insertPrepStmtObject.setSql(query.toString());
        logger.info(new StringBuilder().append("in saveCustomerReference() of CorpotateDAOImpl  Query  :  ").append(insertPrepStmtObject.printQuery()).toString());
        qryList.add(insertPrepStmtObject);

        qryStatus = ConnectionDAO.sqlInsUpdDeletePrepStmt(qryList);
      }
    }
    catch (Exception e) {
      e.printStackTrace();
    } finally {
      subQuery = null;
    }
    if (qryStatus)
    {
      status = 1;
    }
    return status;
  }

  public int updateSarvaSuraksha(Object ob, int id, int refId, String recStatus, String statusCase, String updateFlag, String pageStatuss, String source)
  {
    logger.info("in updateSarvaSuraksha() of CorpotateDAOImpl");
    CustomerSaveVo vo = (CustomerSaveVo)ob;
    int status = 0;
    boolean qryStatus = false;
    String tableName = "";
    ArrayList qryList = new ArrayList();
    String subQuery = null;
    try
    {
      if (((pageStatuss != null) && (pageStatuss.equals("fromLeads"))) || ((updateFlag != null) && (updateFlag.equals("updateFlag"))))
      {
        tableName = "cr_deal_sarva_Suraksha_m";
        String query = new StringBuilder().append("update ").append(tableName).append(" set BPID=?,BPTYPE=?,Nominee_NAME=?, RELATIONSHIP=?,NomineeDOB=STR_TO_DATE(?,'").append(this.dateFormat).append("') ,").append("MAKER_ID=?,MAKER_DATE=DATE_ADD(STR_TO_DATE(?,'").append(this.dateFormatWithTime).append("'),INTERVAL CURTIME() HOUR_SECOND),AUTHOR_ID=?,AUTHOR_DATE=DATE_ADD(STR_TO_DATE(?,'").append(this.dateFormatWithTime).append("'),INTERVAL CURTIME() HOUR_SECOND) where REF_ID=?").toString();

        PrepStmtObject insertPrepStmtObject = new PrepStmtObject();

        if (CommonFunction.checkNull(vo.getBp_id()).trim().equalsIgnoreCase(""))
          insertPrepStmtObject.addString(vo.getBp_id1().trim());
        else {
          insertPrepStmtObject.addString(vo.getBp_id().trim());
        }
        if (CommonFunction.checkNull(vo.getBp_type()).trim().equalsIgnoreCase(""))
          insertPrepStmtObject.addNull();
        else {
          insertPrepStmtObject.addString(vo.getBp_type().trim());
        }
        if (CommonFunction.checkNull(vo.getNominee()).trim().equalsIgnoreCase(""))
          insertPrepStmtObject.addNull();
        else {
          insertPrepStmtObject.addString(vo.getNominee().trim());
        }
        if (CommonFunction.checkNull(vo.getRelationshipS()).trim().equalsIgnoreCase(""))
          insertPrepStmtObject.addNull();
        else {
          insertPrepStmtObject.addString(vo.getRelationshipS().trim());
        }

        if (CommonFunction.checkNull(vo.getNomineeDOB()).trim().equalsIgnoreCase(""))
          insertPrepStmtObject.addNull();
        else {
          insertPrepStmtObject.addString(vo.getNomineeDOB().trim());
        }

        if (StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getMakerId()).trim()).equalsIgnoreCase(""))
          insertPrepStmtObject.addNull();
        else {
          insertPrepStmtObject.addString(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getMakerId()).trim()));
        }
        if (StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getMakerDate()).trim()).equalsIgnoreCase(""))
          insertPrepStmtObject.addNull();
        else {
          insertPrepStmtObject.addString(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getMakerDate()).trim()));
        }

        if (StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getMakerId()).trim()).equalsIgnoreCase(""))
          insertPrepStmtObject.addNull();
        else {
          insertPrepStmtObject.addString(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getMakerId()).trim()));
        }
        if (StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getMakerDate()).trim()).equalsIgnoreCase(""))
          insertPrepStmtObject.addNull();
        else {
          insertPrepStmtObject.addString(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getMakerDate()).trim()));
        }

        if (CommonFunction.checkNull(Integer.valueOf(refId)).trim().equalsIgnoreCase(""))
          insertPrepStmtObject.addNull();
        else {
          insertPrepStmtObject.addInt(refId);
        }
        insertPrepStmtObject.setSql(query.toString());
        logger.info(new StringBuilder().append("in saveCustomerReference() of CorpotateDAOImpl  Query  :  ").append(insertPrepStmtObject.printQuery()).toString());
        qryList.add(insertPrepStmtObject);
        qryStatus = ConnectionDAO.sqlInsUpdDeletePrepStmt(qryList);
      }
      else
      {
        if ((statusCase != null) && (statusCase.length() > 0)) {
          tableName = "com_sarva_Suraksha_m_temp";
        }
        else {
          tableName = "com_sarva_Suraksha_m";
          if (CommonFunction.checkNull(source).trim().equalsIgnoreCase("ED")) {
            tableName = "com_sarva_Suraksha_m_edit";
          }
        }
        if ((tableName.equalsIgnoreCase("com_sarva_Suraksha_m")) || (tableName.equalsIgnoreCase("com_sarva_Suraksha_m_edit")))
        {
          subQuery = new StringBuilder().append("MAKER_ID='").append(CommonFunction.checkNull(vo.getMakerId())).append("',").append("MAKER_DATE=DATE_ADD(STR_TO_DATE('").append(CommonFunction.checkNull(vo.getMakerDate())).append("','").append(this.dateFormatWithTime).append("'),INTERVAL CURTIME() HOUR_SECOND),").append("AUTHOR_ID='").append(CommonFunction.checkNull(vo.getMakerId())).append("',AUTHOR_DATE=DATE_ADD(STR_TO_DATE('").append(CommonFunction.checkNull(vo.getMakerDate())).append("','").append(this.dateFormatWithTime).append("'),INTERVAL CURTIME() HOUR_SECOND) ").toString();
        }
        else
        {
          subQuery = "";
        }String query = new StringBuilder().append("update ").append(tableName).append(" set BPID=?,BPTYPE=?,Nominee_Name=?, RELATIONSHIP=?,NomineeDOB=STR_TO_DATE(?,'").append(this.dateFormat).append("'),  ").append(subQuery).append(" where REF_ID=?").toString();

        PrepStmtObject insertPrepStmtObject = new PrepStmtObject();

        if (CommonFunction.checkNull(vo.getBp_id()).trim().equalsIgnoreCase(""))
          insertPrepStmtObject.addString(vo.getBp_id1().trim());
        else {
          insertPrepStmtObject.addString(vo.getBp_id().trim());
        }
        if (CommonFunction.checkNull(vo.getBp_type()).trim().equalsIgnoreCase(""))
          insertPrepStmtObject.addNull();
        else {
          insertPrepStmtObject.addString(vo.getBp_type().trim());
        }
        if (CommonFunction.checkNull(vo.getNominee()).trim().equalsIgnoreCase(""))
          insertPrepStmtObject.addNull();
        else {
          insertPrepStmtObject.addString(vo.getNominee().trim());
        }

        if (CommonFunction.checkNull(vo.getRelationshipS()).trim().equalsIgnoreCase(""))
          insertPrepStmtObject.addNull();
        else {
          insertPrepStmtObject.addString(vo.getRelationshipS().trim());
        }
        if (CommonFunction.checkNull(vo.getNomineeDOB()).trim().equalsIgnoreCase(""))
          insertPrepStmtObject.addNull();
        else {
          insertPrepStmtObject.addString(vo.getNomineeDOB().trim());
        }

        if (CommonFunction.checkNull(Integer.valueOf(refId)).trim().equalsIgnoreCase(""))
          insertPrepStmtObject.addNull();
        else {
          insertPrepStmtObject.addInt(refId);
        }
        insertPrepStmtObject.setSql(query.toString());
        logger.info(new StringBuilder().append("in updateSarvaSuraksha() of CorpotateDAOImpl  Query  :  ").append(insertPrepStmtObject.printQuery()).toString());
        qryList.add(insertPrepStmtObject);

        qryStatus = ConnectionDAO.sqlInsUpdDeletePrepStmt(qryList);
      }
    }
    catch (Exception e) {
      e.printStackTrace();
    } finally {
      subQuery = null;
    }
    if (qryStatus)
    {
      status = 1;
    }
    return status;
  }

  private boolean checkAddressStatus(int addId)
  {
    boolean flag = true;
    try
    {
      String query = new StringBuilder().append("select ADDRESS_ID from com_address_m_temp where ADDRESS_ID=").append(addId).toString();
      flag = ConnectionDAO.checkStatus(query);
      logger.info(new StringBuilder().append("query of address id check ....................................................... ").append(query).toString());
      if (flag)
        flag = false;
    }
    catch (Exception e) {
      e.printStackTrace();
    }
    return flag;
  }

  private boolean checkAddressMaster(int id)
  {
    boolean flag = false;
    try
    {
      String query = new StringBuilder().append("select CUSTOMER_ID from com_address_m where CUSTOMER_ID=").append(id).toString();
      flag = ConnectionDAO.checkStatus(query);
    } catch (Exception e) {
      e.printStackTrace();
    }
    return flag;
  }

  public ArrayList<IndustryVO> getSubIndustryUpdateList(String codeId)
  {
    ArrayList list = new ArrayList();
    try {
      String query = new StringBuilder().append("select distinct b.SUB_INDUSTRY_ID,b.SUB_INDUSTRY_DESC from gcd_customer_m a left join com_sub_industry_m b on a.CUSTOMER_INDUSTRY=b.INDUSTRY_ID where a.CUSTOMER_ID=").append(codeId).append("").toString();
      ArrayList subIndustryUpdateList = ConnectionDAO.sqlSelect(query);
      logger.info(new StringBuilder().append("getSubIndustryUpdateList ").append(subIndustryUpdateList.size()).toString());
      for (int i = 0; i < subIndustryUpdateList.size(); i++) {
        logger.info(new StringBuilder().append("getSubIndustryUpdateList...FOR loop ").append(CommonFunction.checkNull(subIndustryUpdateList.get(i)).toString()).toString());
        ArrayList data = (ArrayList)subIndustryUpdateList.get(i);
        if (data.size() > 0)
        {
          IndustryVO industryndVO = new IndustryVO();
          industryndVO.setSubIndustryCode(CommonFunction.checkNull(data.get(0)).toString());
          industryndVO.setSubIndustryName(CommonFunction.checkNull(data.get(1)).toString());
          list.add(industryndVO);
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    return list;
  }

  public ArrayList<Object> getCorporateDetailAll(int code, Object pageStatus, String statusCase, String updateInMaker, String updateFlag, String pageStatuss, String source)
  {
    logger.info("In getCorporateDetailAll() of CorpotateDAOImpl.");
    ArrayList list = new ArrayList();
    String tableName = "";
    logger.info(new StringBuilder().append("pageStatuss: ").append(pageStatuss).append(" updateFlag: ").append(updateFlag).toString());
    if (((pageStatuss != null) && (pageStatuss.equals("fromLeads"))) || ((updateFlag != null) && (updateFlag.equals("updateFlag"))) || ((updateFlag != null) && (updateFlag.equals("notEdit"))))
    {
      logger.info("In Credit Processing , Customer Entry..,getCorporateDetailAll()......");
      tableName = "cr_deal_customer_m";
      try
      {
        StringBuilder str = new StringBuilder();
        String custStatus = "";
        custStatus = ConnectionDAO.singleReturn(new StringBuilder().append("SELECT DEAL_EXISTING_CUSTOMER from cr_deal_customer_role where DEAL_CUSTOMER_ID=").append(code).toString());

        String OthRelType = "";
        OthRelType = ConnectionDAO.singleReturn(new StringBuilder().append("SELECT OTHOR_RELATIONSHIP_TYPE FROM cr_deal_customer_m WHERE CUSTOMER_ID=").append(code).append("").toString());

        if (CommonFunction.checkNull(OthRelType).equalsIgnoreCase("CS"))
        {
          str.append(new StringBuilder().append("select c.CUSTOMER_ID,c.CUSTOMER_NAME,c.CUSTOMER_FNAME,c.CUSTOMER_MNAME,c.CUSTOMER_LNAME,c.CUSTOMER_TYPE,DATE_FORMAT(c.CUSTOMER_DOB,'").append(this.dateFormat).append("'),c.CUSTMER_PAN,c.CUSTOMER_REGISTRATION_TYPE,c.CUSTOMER_REGISTRATION_NO,c.CUSTOMER_VAT_NO,c.CUSTOMER_CATEGORY,c.CUSTOMER_CONSTITUTION,c.CUSTOMER_BUSINESS_SEGMENT,c.CUSTOMER_INDUSTRY,c.CUSTOMER_SUB_INDUSTRY,c.CUSTOMER_EMAIL,c.CUSTOMER_WEBSITE,c.CUSTOMER_REFERENCE,c.CUSTOMER_BLACKLIST,c.CUSTOMER_BLACKLIST_REASON,").append(" g.GROUP_ID,g.GROUP_DESC,i.INDUSTRY_DESC,si.SUB_INDUSTRY_DESC,CUSTOMER_GROUP_TYPE,CUSTOMER_GROUP_DESC,c.RELATIONSHIP,c.OTHOR_RELATIONSHIP_TYPE,c.OTHER_RELATIONSHIP_ID,c.NATURE_OF_BUSINESS,c.YEAR_OF_ESTBLISHMENT,c.SHOP_ESTABLISHMENT_NO,c.SALES_TAX_TIN_NO,c.DGFT_NO,c.NO_BV_YEARS,c.NO_BV_MONTHS,c.industry_percent, ").append(" c.PROJECTED_SALES,c.PROJECTED_EXPORTS,c.RISK_CATEGORY,c.ucic_no,c.CGTMSE_INDUSTRY,gr.DESCRIPTION  from ").append(tableName).append(" c left join gcd_group_m g on c.CUSTOMER_GROUP_ID=g.GROUP_ID left join com_industry_m i on c.CUSTOMER_INDUSTRY=i.INDUSTRY_ID left join com_sub_industry_m si on si.sub_industry_id=c.CUSTOMER_SUB_INDUSTRY left join generic_master gr on gr.generic_key='CGTMSE_INDUSTRY' and gr.value=c.CGTMSE_INDUSTRY  where customer_id=").append(code).append(" ").toString());
        }
        else
        {
          str.append(new StringBuilder().append("select c.CUSTOMER_ID,c.CUSTOMER_NAME,c.CUSTOMER_FNAME,c.CUSTOMER_MNAME,c.CUSTOMER_LNAME,c.CUSTOMER_TYPE,DATE_FORMAT(c.CUSTOMER_DOB,'").append(this.dateFormat).append("'),c.CUSTMER_PAN,c.CUSTOMER_REGISTRATION_TYPE,c.CUSTOMER_REGISTRATION_NO,c.CUSTOMER_VAT_NO,c.CUSTOMER_CATEGORY,c.CUSTOMER_CONSTITUTION,c.CUSTOMER_BUSINESS_SEGMENT,c.CUSTOMER_INDUSTRY,c.CUSTOMER_SUB_INDUSTRY,c.CUSTOMER_EMAIL,c.CUSTOMER_WEBSITE,c.CUSTOMER_REFERENCE,c.CUSTOMER_BLACKLIST,c.CUSTOMER_BLACKLIST_REASON,").append(" g.GROUP_ID,g.GROUP_DESC,i.INDUSTRY_DESC,si.SUB_INDUSTRY_DESC,CUSTOMER_GROUP_TYPE,CUSTOMER_GROUP_DESC,c.RELATIONSHIP,c.OTHOR_RELATIONSHIP_TYPE,c.OTHER_RELATIONSHIP_ID,d.dealer_desc,c.NATURE_OF_BUSINESS,c.YEAR_OF_ESTBLISHMENT,c.SHOP_ESTABLISHMENT_NO,c.SALES_TAX_TIN_NO,c.DGFT_NO,c.NO_BV_YEARS,c.NO_BV_MONTHS,c.industry_percent,c.PROJECTED_SALES,c.PROJECTED_EXPORTS,c.RISK_CATEGORY,c.ucic_no,c.CGTMSE_INDUSTRY,gr.DESCRIPTION  from ").append(tableName).append(" c left join gcd_group_m g on c.CUSTOMER_GROUP_ID=g.GROUP_ID left join com_industry_m i on c.CUSTOMER_INDUSTRY=i.INDUSTRY_ID left join com_sub_industry_m si on si.sub_industry_id=c.CUSTOMER_SUB_INDUSTRY left join generic_master gr on gr.generic_key='CGTMSE_INDUSTRY' and gr.value=c.CGTMSE_INDUSTRY ,cr_dsa_dealer_m d  where customer_id=").append(code).append(" and c.OTHOR_RELATIONSHIP_TYPE=d.bp_type and c.OTHER_RELATIONSHIP_ID=d.dealer_id").toString());
        }

        logger.info(new StringBuilder().append("in getCorporateDetailAll() of CorpotateDAOImpl Qiery : ").append(str.toString()).toString());
        ArrayList corporateDetailAll = ConnectionDAO.sqlSelect(str.toString());
        logger.info(new StringBuilder().append("getCorporateDetailAll ").append(corporateDetailAll.size()).toString());
        int size = corporateDetailAll.size();
        for (int i = 0; i < size; i++)
        {
          ArrayList data = (ArrayList)corporateDetailAll.get(i);
          if (data.size() > 0)
          {
            CorporateDetailsVO show = new CorporateDetailsVO();
            show.setCorporateCode(CommonFunction.checkNull(data.get(0)).toString());
            if (!CommonFunction.checkNull(data.get(1).toString()).equalsIgnoreCase(""))
              show.setCorporateName(CommonFunction.checkNull(data.get(1)).toString());
            else
              show.setCorporateName(CommonFunction.checkNull(data.get(2)).toString());
            show.setCorporateCategory(CommonFunction.checkNull(data.get(11)).toString());
            show.setIncorporationDate(CommonFunction.checkNull(data.get(6)));
            show.setConstitution(CommonFunction.checkNull(data.get(12)).toString());
            show.setRegistrationType(CommonFunction.checkNull(data.get(8)).toString());
            show.setRegistrationNo(CommonFunction.checkNull(data.get(9)).toString());
            show.setPan(CommonFunction.checkNull(data.get(7)).toString());
            show.setVatRegNo(CommonFunction.checkNull(data.get(10)).toString());
            show.setLbxIndustry(CommonFunction.checkNull(data.get(14)).toString());
            show.setLbxSubIndustry(CommonFunction.checkNull(data.get(15)).toString());
            show.setBusinessSegment(CommonFunction.checkNull(data.get(13)).toString());
            show.setInstitutionEmail(CommonFunction.checkNull(data.get(16)).toString());
            show.setWebAddress(CommonFunction.checkNull(data.get(17)).toString());
            show.setReferredBy(CommonFunction.checkNull(data.get(18)).toString());
            show.setBlackListed(CommonFunction.checkNull(data.get(19)).toString());
            show.setReasonForBlackListed(CommonFunction.checkNull(data.get(20)).toString());
            show.sethGroupId(CommonFunction.checkNull(data.get(21)).toString());
            show.setGroupName(CommonFunction.checkNull(data.get(22)).toString());
            show.setIndustry(CommonFunction.checkNull(data.get(23)).toString());
            show.setSubIndustry(CommonFunction.checkNull(data.get(24)).toString());
            show.setGroupType(CommonFunction.checkNull(data.get(25)));
            show.setGroupNameText(CommonFunction.checkNull(data.get(26)));
            show.setRelationShip(CommonFunction.checkNull(data.get(27)));
            show.setOtherRelationShip(CommonFunction.checkNull(data.get(28)).toString());
            show.setLbxBusinessPartnearHID(CommonFunction.checkNull(data.get(29)).toString());
            if (OthRelType.equalsIgnoreCase("CS"))
            {
              show.setBusinessPartnerTypeDesc("");

              show.setNatureOfBus(CommonFunction.checkNull(data.get(30)).toString());
              show.setYearOfEstb(CommonFunction.checkNull(data.get(31)).toString());
              show.setShopEstbNo(CommonFunction.checkNull(data.get(32)).toString());
              show.setSalesTax(CommonFunction.checkNull(data.get(33)).toString());
              show.setDgftNo(CommonFunction.checkNull(data.get(34)).toString());
              show.setNoBVYears(CommonFunction.checkNull(data.get(35)).toString());
              show.setNoBVMonths(CommonFunction.checkNull(data.get(36)).toString());
              show.setIndustryPercent(CommonFunction.checkNull(data.get(37)).toString());

              show.setProjectedSales(CommonFunction.checkNull(data.get(38)).toString());
              show.setProjectedExports(CommonFunction.checkNull(data.get(39)).toString());
              show.setRiskCategory(CommonFunction.checkNull(data.get(40)).toString());
              show.setUcic(CommonFunction.checkNull(data.get(41)).toString());
              show.setLbxcgtmseIndustry(CommonFunction.checkNull(data.get(42)).toString());
              show.setCgtmseIndustry(CommonFunction.checkNull(data.get(43)).toString());
            }
            else
            {
              show.setBusinessPartnerTypeDesc(CommonFunction.checkNull(data.get(30)).toString());

              show.setNatureOfBus(CommonFunction.checkNull(data.get(31)).toString());
              show.setYearOfEstb(CommonFunction.checkNull(data.get(32)).toString());
              show.setShopEstbNo(CommonFunction.checkNull(data.get(33)).toString());
              show.setSalesTax(CommonFunction.checkNull(data.get(34)).toString());
              show.setDgftNo(CommonFunction.checkNull(data.get(35)).toString());
              show.setNoBVYears(CommonFunction.checkNull(data.get(36)).toString());
              show.setNoBVMonths(CommonFunction.checkNull(data.get(37)).toString());
              show.setIndustryPercent(CommonFunction.checkNull(data.get(38)).toString());
              show.setProjectedSales(CommonFunction.checkNull(data.get(39)).toString());
              show.setProjectedExports(CommonFunction.checkNull(data.get(40)).toString());
              show.setRiskCategory(CommonFunction.checkNull(data.get(41)).toString());
              show.setUcic(CommonFunction.checkNull(data.get(42)).toString());
              show.setLbxcgtmseIndustry(CommonFunction.checkNull(data.get(43)).toString());
              show.setCgtmseIndustry(CommonFunction.checkNull(data.get(44)).toString());
            }

            logger.info("**** : ");
            list.add(show);
          }
        }
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
    else {
      if (((pageStatus != null) && (pageStatus.equals("approve"))) || ((updateInMaker != null) && (updateInMaker.equals("updateInMaker"))) || (statusCase.equalsIgnoreCase("UnApproved"))) {
        tableName = "gcd_customer_m_temp";
      }
      else {
        tableName = "gcd_customer_m";
        if (CommonFunction.checkNull(source).trim().equalsIgnoreCase("ED"))
          tableName = "gcd_customer_m_edit";
      }
      try
      {
        String OthRelType = "";
        String query1 = "";
        OthRelType = ConnectionDAO.singleReturn(new StringBuilder().append("SELECT OTHOR_RELATIONSHIP_TYPE FROM ").append(tableName).append(" WHERE CUSTOMER_ID=").append(code).append("").toString());

        if (OthRelType.equalsIgnoreCase("CS"))
        {
         query1 = "select  c.CUSTOMER_ID,c.CUSTOMER_NAME,c.CUSTOMER_FNAME,c.CUSTOMER_MNAME,c.CUSTOMER_LNAME,c.CUSTOMER_TYPE,DATE_FORMAT(c.CUSTOMER_DOB,'"+dateFormat+"'),c.CUSTMER_PAN,c.CUSTOMER_REGISTRATION_TYPE,c.CUSTOMER_REGISTRATION_NO,c.CUSTOMER_VAT_NO,c.CUSTOMER_CATEGORY,c.CUSTOMER_CONSTITUTION,c.CUSTOMER_BUSINESS_SEGMENT,c.CUSTOMER_INDUSTRY,c.CUSTOMER_SUB_INDUSTRY,c.CUSTOMER_EMAIL,c.CUSTOMER_WEBSITE,c.CUSTOMER_REFERENCE,c.CUSTOMER_BLACKLIST,c.CUSTOMER_BLACKLIST_REASON,c.CUSTOMER_STATUS,"+" g.GROUP_ID,g.GROUP_DESC,i.INDUSTRY_DESC,si.SUB_INDUSTRY_DESC,c.OTHOR_RELATIONSHIP_TYPE,c.OTHER_RELATIONSHIP_ID,c.NATURE_OF_BUSINESS,c.YEAR_OF_ESTBLISHMENT,c.SHOP_ESTABLISHMENT_NO,c.SALES_TAX_TIN_NO,c.DGFT_NO,c.NO_BV_YEARS,c.NO_BV_MONTHS,c.industry_percent,c.PROJECTED_SALES,c.PROJECTED_EXPORTS,c.RISK_CATEGORY,c.UCIC_NO,c.CGTMSE_INDUSTRY,gr.DESCRIPTION from "+tableName+" c left join gcd_group_m g on c.GROUP_ID=g.GROUP_ID left join com_industry_m i on c.CUSTOMER_INDUSTRY=i.INDUSTRY_ID left join com_sub_industry_m si on c.CUSTOMER_SUB_INDUSTRY=si.sub_industry_id left join generic_master gr on gr.generic_key='CGTMSE_INDUSTRY' and gr.value=c.CGTMSE_INDUSTRY  where customer_id="+code;	
        }
        else
        {
         query1 ="select  c.CUSTOMER_ID,c.CUSTOMER_NAME,c.CUSTOMER_FNAME,c.CUSTOMER_MNAME,c.CUSTOMER_LNAME,c.CUSTOMER_TYPE,DATE_FORMAT(c.CUSTOMER_DOB,'"+dateFormat+"'),c.CUSTMER_PAN,c.CUSTOMER_REGISTRATION_TYPE,c.CUSTOMER_REGISTRATION_NO,c.CUSTOMER_VAT_NO,c.CUSTOMER_CATEGORY,c.CUSTOMER_CONSTITUTION,c.CUSTOMER_BUSINESS_SEGMENT,c.CUSTOMER_INDUSTRY,c.CUSTOMER_SUB_INDUSTRY,c.CUSTOMER_EMAIL,c.CUSTOMER_WEBSITE,c.CUSTOMER_REFERENCE,c.CUSTOMER_BLACKLIST,c.CUSTOMER_BLACKLIST_REASON,c.CUSTOMER_STATUS,"+" g.GROUP_ID,g.GROUP_DESC,i.INDUSTRY_DESC,si.SUB_INDUSTRY_DESC,c.OTHOR_RELATIONSHIP_TYPE,c.OTHER_RELATIONSHIP_ID,d.dealer_desc,c.NATURE_OF_BUSINESS,c.YEAR_OF_ESTBLISHMENT,c.SHOP_ESTABLISHMENT_NO,c.SALES_TAX_TIN_NO,c.DGFT_NO,c.NO_BV_YEARS,c.NO_BV_MONTHS,c.industry_percent,c.PROJECTED_SALES,c.PROJECTED_EXPORTS,c.RISK_CATEGORY,c.UCIC_NO,c.CGTMSE_INDUSTRY,gr.DESCRIPTION from "+tableName+" c left join gcd_group_m g on c.GROUP_ID=g.GROUP_ID left join com_industry_m i on c.CUSTOMER_INDUSTRY=i.INDUSTRY_ID left join com_sub_industry_m si on c.CUSTOMER_SUB_INDUSTRY=si.sub_industry_id left join generic_master gr on gr.generic_key='CGTMSE_INDUSTRY' and gr.value=c.CGTMSE_INDUSTRY  left join cr_dsa_dealer_m d on c.OTHOR_RELATIONSHIP_TYPE=d.bp_type and c.OTHER_RELATIONSHIP_ID=d.dealer_id where customer_id="+code;
        }

        logger.info(new StringBuilder().append("in getCorporateDetailAll() of CorpotateDAOImpl Qiery : ").append(query1).toString());
        ArrayList corporateDetailAll = ConnectionDAO.sqlSelect(query1);
        logger.info(new StringBuilder().append("getCorporateDetailAll ").append(corporateDetailAll.size()).toString());
        int size = corporateDetailAll.size();
        for (int i = 0; i < size; i++)
        {
          ArrayList data = (ArrayList)corporateDetailAll.get(i);
          if (data.size() > 0)
          {
            CorporateDetailsVO show = new CorporateDetailsVO();
            show.setCorporateCode(CommonFunction.checkNull(data.get(0)));
            show.setCorporateName(CommonFunction.checkNull(data.get(1)));
            show.setCorporateCategory(CommonFunction.checkNull(data.get(11)));
            show.setIncorporationDate(CommonFunction.checkNull(data.get(6)));
            show.setConstitution(CommonFunction.checkNull(data.get(12)));
            show.setRegistrationType(CommonFunction.checkNull(data.get(8)));
            show.setRegistrationNo(CommonFunction.checkNull(data.get(9)));
            show.setPan(CommonFunction.checkNull(data.get(7)));
            show.setVatRegNo(CommonFunction.checkNull(data.get(10)));
            show.setLbxIndustry(CommonFunction.checkNull(data.get(14)));
            show.setLbxSubIndustry(CommonFunction.checkNull(data.get(15)));
            show.setBusinessSegment(CommonFunction.checkNull(data.get(13)));
            show.setInstitutionEmail(CommonFunction.checkNull(data.get(16)));
            show.setWebAddress(CommonFunction.checkNull(data.get(17)));
            show.setReferredBy(CommonFunction.checkNull(data.get(18)));
            show.setBlackListed(CommonFunction.checkNull(data.get(19)));
            show.setReasonForBlackListed(CommonFunction.checkNull(data.get(20)));
            show.setPagestatus(CommonFunction.checkNull(data.get(21)));
            show.sethGroupId(CommonFunction.checkNull(data.get(22)));
            show.setGroupName(CommonFunction.checkNull(data.get(23)));
            show.setIndustry(CommonFunction.checkNull(data.get(24)));
            show.setSubIndustry(CommonFunction.checkNull(data.get(25)));
            show.setOtherRelationShip(CommonFunction.checkNull(data.get(26)).toString());
            show.setLbxBusinessPartnearHID(CommonFunction.checkNull(data.get(27)).toString());
            if (OthRelType.equalsIgnoreCase("CS"))
            {
              show.setBusinessPartnerTypeDesc("");
              show.setNatureOfBus(CommonFunction.checkNull(data.get(28)).toString());
              show.setYearOfEstb(CommonFunction.checkNull(data.get(29)).toString());
              show.setShopEstbNo(CommonFunction.checkNull(data.get(30)).toString());
              show.setSalesTax(CommonFunction.checkNull(data.get(31)).toString());
              show.setDgftNo(CommonFunction.checkNull(data.get(32)).toString());
              show.setNoBVYears(CommonFunction.checkNull(data.get(33)).toString());
              show.setNoBVMonths(CommonFunction.checkNull(data.get(34)).toString());
              show.setIndustryPercent(CommonFunction.checkNull(data.get(35)).toString());
              show.setProjectedSales(CommonFunction.checkNull(data.get(36)).toString());
              show.setProjectedExports(CommonFunction.checkNull(data.get(37)).toString());
              show.setRiskCategory(CommonFunction.checkNull(data.get(38)).toString());
              show.setUcic(CommonFunction.checkNull(data.get(39)).toString());
              show.setLbxcgtmseIndustry(CommonFunction.checkNull(data.get(40)).toString());
              show.setCgtmseIndustry(CommonFunction.checkNull(data.get(41)).toString());
            }
            else
            {
              show.setBusinessPartnerTypeDesc(CommonFunction.checkNull(data.get(28)).toString());
              show.setNatureOfBus(CommonFunction.checkNull(data.get(29)).toString());
              show.setYearOfEstb(CommonFunction.checkNull(data.get(30)).toString());
              show.setShopEstbNo(CommonFunction.checkNull(data.get(31)).toString());
              show.setSalesTax(CommonFunction.checkNull(data.get(32)).toString());
              show.setDgftNo(CommonFunction.checkNull(data.get(33)).toString());
              show.setNoBVYears(CommonFunction.checkNull(data.get(34)).toString());
              show.setNoBVMonths(CommonFunction.checkNull(data.get(35)).toString());
              show.setIndustryPercent(CommonFunction.checkNull(data.get(36)).toString());
              show.setProjectedSales(CommonFunction.checkNull(data.get(37)).toString());
              show.setProjectedExports(CommonFunction.checkNull(data.get(38)).toString());
              show.setRiskCategory(CommonFunction.checkNull(data.get(39)).toString());
              show.setUcic(CommonFunction.checkNull(data.get(40)).toString());
              show.setLbxcgtmseIndustry(CommonFunction.checkNull(data.get(41)).toString());
              show.setCgtmseIndustry(CommonFunction.checkNull(data.get(42)).toString());
            }
            list.add(show);
          }
        }
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
    return list;
  }

  public ArrayList<Object> getStakeDetails(int code, Object pageStatus, String statusCase, String updateFlag, String updateInMaker, String source) {
    ArrayList list = new ArrayList();
    String tableName = "";

    if (((updateFlag != null) && (updateFlag.equals("updateFlag"))) || ((updateFlag != null) && (updateFlag.equals("notEdit"))))
    {
      logger.info("In Credit Processing , Customer Entry.,getStakeDetails().......");
      tableName = "cr_deal_customer_stakeholder_m";
      try {
        String query = new StringBuilder().append("SELECT distinct cs.STAKEHOLDER_ID, cs.STAKEHOLDER_SALUTATION, cs.STAKEHOLDER_NAME, s.STAKEHOLDER_ID, cs.STAKEHOLDER_PERCENTAGE, DATE_FORMAT(cs.STAKEHOLDER_DOB,'").append(this.dateFormat).append("'), cs.STAKEHOLDER_EXPERIENCE, cs.STAKEHOLDER_DIN, ").append("cs.STAKEHOLDER_IDENTIFICATION_NO, p.DESCRIPTION,DATE_FORMAT(cs.STAKEHOLDER_JOINING_DATE,'").append(this.dateFormat).append("') , cs.ELIGIBLE_FOR_COMPUTATION,").append(" cs.STAKEHOLDER_PRIMARY_PHONE, cs.STAKEHOLDER_ALTERNATE_PHONE, cs.STAKEHOLDER_PRIMARY_EMAIL,").append(" cs.STAKEHOLDER_ALTERNATE_EMAIL, cs.STAKEHOLDER_WEBSITE").append(" FROM ").append(tableName).append(" cs, com_stakeholder_m s, generic_master p").append(" WHERE cs.STAKEHOLDER_TYPE=s.STAKEHOLDER_ID and cs.STAKEHOLDER_POSITION=p.VALUE ").append("and cs.CUSTOMER_ID=").append(code).toString();

        logger.info(new StringBuilder().append("query ").append(query).toString());
        ArrayList stakeDetails = ConnectionDAO.sqlSelect(query);

        logger.info(new StringBuilder().append("getStakeDetails ").append(stakeDetails.size()).toString());
        for (int i = 0; i < stakeDetails.size(); i++)
        {
          ArrayList data = (ArrayList)stakeDetails.get(i);
          if (data.size() > 0)
          {
            StakeHolderVo stakeHolderVo = new StakeHolderVo();
            stakeHolderVo.setHolderid(CommonFunction.checkNull(data.get(0)).toString());
            stakeHolderVo.setSex(CommonFunction.checkNull(data.get(1)).toString());
            stakeHolderVo.setHolderName(CommonFunction.checkNull(data.get(2)).toString());
            stakeHolderVo.setHolderType(CommonFunction.checkNull(data.get(3)).toString());
            stakeHolderVo.setHoldingPerc(CommonFunction.checkNull(data.get(4)).toString());
            stakeHolderVo.setDob(CommonFunction.checkNull(data.get(5)).toString());
            stakeHolderVo.setTotalExp(CommonFunction.checkNull(data.get(6)).toString());
            stakeHolderVo.setDinNo(CommonFunction.checkNull(data.get(7)).toString());
            stakeHolderVo.setIdNo(CommonFunction.checkNull(data.get(8)).toString());
            stakeHolderVo.setPosition(CommonFunction.checkNull(data.get(9)).toString());
            stakeHolderVo.setDoj(CommonFunction.checkNull(data.get(10)).toString());
            stakeHolderVo.setCompute(CommonFunction.checkNull(data.get(11)).toString());
            stakeHolderVo.setPrimaryPhone(CommonFunction.checkNull(data.get(12)).toString());
            stakeHolderVo.setAlternatePhone(CommonFunction.checkNull(data.get(13)).toString());
            stakeHolderVo.setPrimaryEmail(CommonFunction.checkNull(data.get(14)).toString());
            stakeHolderVo.setAlternateEmail(CommonFunction.checkNull(data.get(15)).toString());
            stakeHolderVo.setWebsite(CommonFunction.checkNull(data.get(16)).toString());
            list.add(stakeHolderVo);
          }
        }
      } catch (Exception e) {
        e.printStackTrace();
      }

    }
    else
    {
      logger.info("In GCD.,getStakeDetails().......");
      if ((pageStatus != null) || ((updateInMaker != null) && (updateInMaker.equals("updateInMaker"))) || ((statusCase != null) && (!statusCase.equals(""))))
      {
        if ((statusCase != null) && (statusCase.equalsIgnoreCase("Approved")) && ((updateInMaker == null) || (updateInMaker.equals(""))))
        {
          tableName = "customer_stakeholder_m";
          if (CommonFunction.checkNull(source).trim().equalsIgnoreCase("ED"))
            tableName = "customer_stakeholder_m_edit";
        }
        else
        {
          tableName = "customer_stakeholder_m_temp";
        }

      }
      else
      {
        tableName = "customer_stakeholder_m";
        if (CommonFunction.checkNull(source).trim().equalsIgnoreCase("ED"))
          tableName = "customer_stakeholder_m_edit";
      }
      try
      {
        String query = new StringBuilder().append("SELECT cs.STAKEHOLDER_ID, cs.STAKEHOLDER_SALUTATION, cs.STAKEHOLDER_NAME, p.DESCRIPTION, cs.STAKEHOLDER_PERCENTAGE, cs.STAKEHOLDER_DOB, cs.STAKEHOLDER_EXPERIENCE, cs.STAKEHOLDER_DIN, cs.STAKEHOLDER_IDENTIFICATION_NO, p.DESCRIPTION, cs.STAKEHOLDER_JOINING_DATE, cs.ELIGIBLE_FOR_COMPUTATION, cs.STAKEHOLDER_PRIMARY_PHONE, cs.STAKEHOLDER_ALTERNATE_PHONE, cs.STAKEHOLDER_PRIMARY_EMAIL, cs.STAKEHOLDER_ALTERNATE_EMAIL, cs.STAKEHOLDER_WEBSITE FROM ").append(tableName).append(" cs, generic_master p").append(" WHERE p.GENERIC_KEY='DESIGNATION' and cs.STAKEHOLDER_POSITION=p.VALUE ").append("and cs.CUSTOMER_ID=").append(code).toString();

        ArrayList stakeDetails = ConnectionDAO.sqlSelect(query);
        logger.info(new StringBuilder().append("getStakeDetails ").append(stakeDetails.size()).toString());
        for (int i = 0; i < stakeDetails.size(); i++)
        {
          ArrayList data = (ArrayList)stakeDetails.get(i);
          if (data.size() > 0)
          {
            StakeHolderVo stakeHolderVo = new StakeHolderVo();
            stakeHolderVo.setHolderid(CommonFunction.checkNull(data.get(0)).toString());
            stakeHolderVo.setSex(CommonFunction.checkNull(data.get(1)).toString());
            stakeHolderVo.setHolderName(CommonFunction.checkNull(data.get(2)).toString());
            stakeHolderVo.setHolderType(CommonFunction.checkNull(data.get(3)).toString());
            stakeHolderVo.setHoldingPerc(CommonFunction.checkNull(data.get(4)).toString());
            stakeHolderVo.setDob(CommonFunction.checkNull(data.get(5)).toString());
            stakeHolderVo.setTotalExp(CommonFunction.checkNull(data.get(6)).toString());
            stakeHolderVo.setDinNo(CommonFunction.checkNull(data.get(7)).toString());
            stakeHolderVo.setIdNo(CommonFunction.checkNull(data.get(8)).toString());
            stakeHolderVo.setPosition(CommonFunction.checkNull(data.get(9)).toString());
            stakeHolderVo.setDoj(CommonFunction.checkNull(data.get(10)).toString());
            stakeHolderVo.setCompute(CommonFunction.checkNull(data.get(11)).toString());
            stakeHolderVo.setPrimaryPhone(CommonFunction.checkNull(data.get(12)).toString());
            stakeHolderVo.setAlternatePhone(CommonFunction.checkNull(data.get(13)).toString());
            stakeHolderVo.setPrimaryEmail(CommonFunction.checkNull(data.get(14)).toString());
            stakeHolderVo.setAlternateEmail(CommonFunction.checkNull(data.get(15)).toString());
            stakeHolderVo.setWebsite(CommonFunction.checkNull(data.get(16)).toString());
            list.add(stakeHolderVo);
          }
        }
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
    return list;
  }

  public int updateStakeHolder(Object ob, int id, int stackId, String recStatus, String statusCase, String updateFlag, String pageStatuss, String source)
  {
    StakeHolderVo vo = (StakeHolderVo)ob;

    int status = 0;
    boolean qryStatus = false;
    String tableName = "";
    String subQuery = null;
    ArrayList qryList = new ArrayList();
    PrepStmtObject insertPrepStmtObject = new PrepStmtObject();
    try {
      logger.info(new StringBuilder().append("updateStakeHolder :").append(id).toString());
      if (((pageStatuss != null) && (pageStatuss.equals("fromLeads"))) || ((updateFlag != null) && (updateFlag.equals("updateFlag"))))
      {
        logger.info("In Credit Processing , Customer Entry.,updateStakeHolder().......");
        tableName = "cr_deal_customer_stakeholder_m";
        String query = new StringBuilder().append("update ").append(tableName).append(" set STAKEHOLDER_SALUTATION=?,").append(" STAKEHOLDER_NAME=?, STAKEHOLDER_TYPE=?, STAKEHOLDER_PERCENTAGE=?, STAKEHOLDER_DOB=STR_TO_DATE(?,'").append(this.dateFormat).append("'), ").append("STAKEHOLDER_EXPERIENCE=?, STAKEHOLDER_DIN=?, STAKEHOLDER_IDENTIFICATION_NO=?, ").append("STAKEHOLDER_PRIMARY_PHONE=? ,STAKEHOLDER_ALTERNATE_PHONE=?, STAKEHOLDER_PRIMARY_EMAIL=?,").append(" STAKEHOLDER_ALTERNATE_EMAIL=?, STAKEHOLDER_WEBSITE=?, STAKEHOLDER_POSITION=?, ").append("STAKEHOLDER_JOINING_DATE=STR_TO_DATE(?,'").append(this.dateFormat).append("'), ELIGIBLE_FOR_COMPUTATION=?,").append("MAKER_ID=?,MAKER_DATE=DATE_ADD(STR_TO_DATE(?,'").append(this.dateFormatWithTime).append("'),INTERVAL CURTIME() HOUR_SECOND),AUTHOR_ID=?,AUTHOR_DATE=DATE_ADD(STR_TO_DATE(?,'").append(this.dateFormatWithTime).append("'),INTERVAL CURTIME() HOUR_SECOND),STAKEHOLDER_PAN=?,ADDITIONAL_ID1=?,ADDITIONAL_ID2=?  where STAKEHOLDER_ID=?").toString();

        if (CommonFunction.checkNull(vo.getSex()).trim().equalsIgnoreCase(""))
          insertPrepStmtObject.addNull();
        else {
          insertPrepStmtObject.addString(vo.getSex().trim());
        }
        if (CommonFunction.checkNull(vo.getHolderName()).trim().equalsIgnoreCase(""))
          insertPrepStmtObject.addNull();
        else {
          insertPrepStmtObject.addString(vo.getHolderName().trim());
        }
        if (CommonFunction.checkNull(vo.getHolderType()).trim().equalsIgnoreCase(""))
          insertPrepStmtObject.addNull();
        else {
          insertPrepStmtObject.addString(vo.getHolderType().trim());
        }
        if (CommonFunction.checkNull(vo.getHoldingPerc()).trim().equalsIgnoreCase(""))
          insertPrepStmtObject.addNull();
        else {
          insertPrepStmtObject.addString(vo.getHoldingPerc().trim());
        }
        if (CommonFunction.checkNull(vo.getDob()).trim().equalsIgnoreCase(""))
          insertPrepStmtObject.addNull();
        else {
          insertPrepStmtObject.addString(vo.getDob().trim());
        }
        if (CommonFunction.checkNull(vo.getTotalExp()).trim().equalsIgnoreCase(""))
          insertPrepStmtObject.addNull();
        else {
          insertPrepStmtObject.addString(vo.getTotalExp().trim());
        }
        if (CommonFunction.checkNull(vo.getDinNo()).trim().equalsIgnoreCase(""))
          insertPrepStmtObject.addNull();
        else {
          insertPrepStmtObject.addString(vo.getDinNo().trim());
        }
        if (CommonFunction.checkNull(vo.getIdNo()).trim().equalsIgnoreCase(""))
          insertPrepStmtObject.addNull();
        else {
          insertPrepStmtObject.addString(vo.getIdNo().trim());
        }
        if (CommonFunction.checkNull(vo.getPrimaryPhone()).trim().equalsIgnoreCase(""))
          insertPrepStmtObject.addNull();
        else {
          insertPrepStmtObject.addString(vo.getPrimaryPhone().trim());
        }
        if (CommonFunction.checkNull(vo.getAlternatePhone()).trim().equalsIgnoreCase(""))
          insertPrepStmtObject.addNull();
        else {
          insertPrepStmtObject.addString(vo.getAlternatePhone().trim());
        }
        if (CommonFunction.checkNull(vo.getPrimaryEmail()).trim().equalsIgnoreCase(""))
          insertPrepStmtObject.addNull();
        else {
          insertPrepStmtObject.addString(vo.getPrimaryEmail().trim());
        }
        if (CommonFunction.checkNull(vo.getAlternateEmail()).trim().equalsIgnoreCase(""))
          insertPrepStmtObject.addNull();
        else {
          insertPrepStmtObject.addString(vo.getAlternateEmail().trim());
        }
        if (CommonFunction.checkNull(vo.getWebsite()).trim().equalsIgnoreCase(""))
          insertPrepStmtObject.addNull();
        else {
          insertPrepStmtObject.addString(vo.getWebsite().trim());
        }
        if (CommonFunction.checkNull(vo.getPosition()).trim().equalsIgnoreCase(""))
          insertPrepStmtObject.addNull();
        else {
          insertPrepStmtObject.addString(vo.getPosition().trim());
        }
        if (CommonFunction.checkNull(vo.getDoj()).trim().equalsIgnoreCase(""))
          insertPrepStmtObject.addNull();
        else {
          insertPrepStmtObject.addString(vo.getDoj().trim());
        }
        if (vo.getCompute().equals("on"))
        {
          insertPrepStmtObject.addString("Y");
        }
        else
        {
          insertPrepStmtObject.addString("N");
        }

        if (StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getMakerId()).trim()).equalsIgnoreCase(""))
          insertPrepStmtObject.addNull();
        else {
          insertPrepStmtObject.addString(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getMakerId()).trim()));
        }

        if (StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getMakerDate()).trim()).equalsIgnoreCase(""))
          insertPrepStmtObject.addNull();
        else {
          insertPrepStmtObject.addString(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getMakerDate()).trim()));
        }

        if (StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getMakerId()).trim()).equalsIgnoreCase(""))
          insertPrepStmtObject.addNull();
        else {
          insertPrepStmtObject.addString(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getMakerId()).trim()));
        }

        if (StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getMakerDate()).trim()).equalsIgnoreCase(""))
          insertPrepStmtObject.addNull();
        else {
          insertPrepStmtObject.addString(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getMakerDate()).trim()));
        }

        if (StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getMgmtPAN()).trim()).equalsIgnoreCase(""))
          insertPrepStmtObject.addNull();
        else {
          insertPrepStmtObject.addString(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getMgmtPAN()).trim()));
        }
        if (StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getAddId1()).trim()).equalsIgnoreCase(""))
          insertPrepStmtObject.addNull();
        else {
          insertPrepStmtObject.addString(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getAddId1()).trim()));
        }
        if (StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getAddId2()).trim()).equalsIgnoreCase(""))
          insertPrepStmtObject.addNull();
        else {
          insertPrepStmtObject.addString(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getAddId2()).trim()));
        }
        if (CommonFunction.checkNull(Integer.valueOf(stackId)).trim().equalsIgnoreCase(""))
          insertPrepStmtObject.addNull();
        else {
          insertPrepStmtObject.addInt(stackId);
        }
        insertPrepStmtObject.setSql(query.toString());
        logger.info(new StringBuilder().append("IN updateStakeHolder() update query1 ### ").append(insertPrepStmtObject.printQuery()).toString());
        qryList.add(insertPrepStmtObject);
        qryStatus = ConnectionDAO.sqlInsUpdDeletePrepStmt(qryList);
        saveStakeHolderDedupe(ob, id, source);
      }
      else
      {
        logger.info("In GCD.,updateStakeHolder().......");
        if ((statusCase != null) && (statusCase.length() > 0))
        {
          tableName = "customer_stakeholder_m_temp";
        }
        else
        {
          tableName = "customer_stakeholder_m";
          if (CommonFunction.checkNull(source).trim().equalsIgnoreCase("ED")) {
            tableName = "customer_stakeholder_m_edit";
          }
        }

        if ((tableName.equalsIgnoreCase("customer_stakeholder_m")) || (tableName.equalsIgnoreCase("customer_stakeholder_m_edit")))
        {
          subQuery = new StringBuilder().append(",MAKER_ID='").append(CommonFunction.checkNull(vo.getMakerId())).append("',").append("MAKER_DATE=DATE_ADD(STR_TO_DATE('").append(CommonFunction.checkNull(vo.getMakerDate())).append("','").append(this.dateFormatWithTime).append("'),INTERVAL CURTIME() HOUR_SECOND)").append(",AUTHOR_ID='").append(CommonFunction.checkNull(vo.getMakerId())).append("',AUTHOR_DATE=DATE_ADD(STR_TO_DATE('").append(CommonFunction.checkNull(vo.getMakerDate())).append("','").append(this.dateFormatWithTime).append("'),INTERVAL CURTIME() HOUR_SECOND),STAKEHOLDER_PAN='").append(CommonFunction.checkNull(vo.getMgmtPAN())).append("',ADDITIONAL_ID1='").append(CommonFunction.checkNull(vo.getAddId1())).append("',ADDITIONAL_ID2='").append(CommonFunction.checkNull(vo.getAddId2())).append("'").toString();
        }
        else
        {
          subQuery = "";
        }
        String query = new StringBuilder().append("update ").append(tableName).append(" set STAKEHOLDER_SALUTATION=?,").append(" STAKEHOLDER_NAME=?, STAKEHOLDER_TYPE=?, STAKEHOLDER_PERCENTAGE=?, STAKEHOLDER_DOB=STR_TO_DATE(?,'").append(this.dateFormat).append("'), ").append("STAKEHOLDER_EXPERIENCE=?, STAKEHOLDER_DIN=?, STAKEHOLDER_IDENTIFICATION_NO=?, ").append("STAKEHOLDER_PRIMARY_PHONE=? ,STAKEHOLDER_ALTERNATE_PHONE=?, STAKEHOLDER_PRIMARY_EMAIL=?,").append(" STAKEHOLDER_ALTERNATE_EMAIL=?, STAKEHOLDER_WEBSITE=?, STAKEHOLDER_POSITION=?, ").append("STAKEHOLDER_JOINING_DATE=STR_TO_DATE(?,'").append(this.dateFormat).append("'), ELIGIBLE_FOR_COMPUTATION=?,STAKEHOLDER_PAN=?,ADDITIONAL_ID1=?,ADDITIONAL_ID2=? ").append(subQuery).append("  where STAKEHOLDER_ID=?").toString();

        if (CommonFunction.checkNull(vo.getSex()).trim().equalsIgnoreCase(""))
          insertPrepStmtObject.addNull();
        else {
          insertPrepStmtObject.addString(vo.getSex().trim());
        }
        if (CommonFunction.checkNull(vo.getHolderName()).trim().equalsIgnoreCase(""))
          insertPrepStmtObject.addNull();
        else {
          insertPrepStmtObject.addString(vo.getHolderName().trim());
        }
        if (CommonFunction.checkNull(vo.getHolderType()).trim().equalsIgnoreCase(""))
          insertPrepStmtObject.addNull();
        else {
          insertPrepStmtObject.addString(vo.getHolderType().trim());
        }
        if (CommonFunction.checkNull(vo.getHoldingPerc()).trim().equalsIgnoreCase(""))
          insertPrepStmtObject.addNull();
        else {
          insertPrepStmtObject.addString(vo.getHoldingPerc().trim());
        }
        if (CommonFunction.checkNull(vo.getDob()).trim().equalsIgnoreCase(""))
          insertPrepStmtObject.addNull();
        else {
          insertPrepStmtObject.addString(vo.getDob().trim());
        }
        if (CommonFunction.checkNull(vo.getTotalExp()).trim().equalsIgnoreCase(""))
          insertPrepStmtObject.addNull();
        else {
          insertPrepStmtObject.addString(vo.getTotalExp().trim());
        }
        if (vo.getDinNo() == null)
        {
          insertPrepStmtObject.addNull();
        }
        else
        {
          insertPrepStmtObject.addString(vo.getDinNo().trim());
        }

        if (vo.getIdNo() == null)
        {
          insertPrepStmtObject.addNull();
        }
        else
        {
          insertPrepStmtObject.addString(vo.getIdNo().trim());
        }

        if (CommonFunction.checkNull(vo.getPrimaryPhone()).trim().equalsIgnoreCase(""))
          insertPrepStmtObject.addNull();
        else {
          insertPrepStmtObject.addString(vo.getPrimaryPhone().trim());
        }
        if (CommonFunction.checkNull(vo.getAlternatePhone()).trim().equalsIgnoreCase(""))
          insertPrepStmtObject.addNull();
        else {
          insertPrepStmtObject.addString(vo.getAlternatePhone().trim());
        }
        if (CommonFunction.checkNull(vo.getPrimaryEmail()).trim().equalsIgnoreCase(""))
          insertPrepStmtObject.addNull();
        else {
          insertPrepStmtObject.addString(vo.getPrimaryEmail().trim());
        }
        if (CommonFunction.checkNull(vo.getAlternateEmail()).trim().equalsIgnoreCase(""))
          insertPrepStmtObject.addNull();
        else {
          insertPrepStmtObject.addString(vo.getAlternateEmail().trim());
        }
        if (CommonFunction.checkNull(vo.getWebsite()).trim().equalsIgnoreCase(""))
          insertPrepStmtObject.addNull();
        else {
          insertPrepStmtObject.addString(vo.getWebsite().trim());
        }
        if (CommonFunction.checkNull(vo.getPosition()).trim().equalsIgnoreCase(""))
          insertPrepStmtObject.addNull();
        else {
          insertPrepStmtObject.addString(vo.getPosition().trim());
        }
        if (CommonFunction.checkNull(vo.getDoj()).trim().equalsIgnoreCase(""))
          insertPrepStmtObject.addNull();
        else {
          insertPrepStmtObject.addString(vo.getDoj().trim());
        }
        if (vo.getCompute().equals("on"))
        {
          insertPrepStmtObject.addString("Y");
        }
        else
        {
          insertPrepStmtObject.addString("N");
        }

        if (StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getMgmtPAN()).trim()).equalsIgnoreCase(""))
          insertPrepStmtObject.addNull();
        else {
          insertPrepStmtObject.addString(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getMgmtPAN()).trim()));
        }
        if (StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getAddId1()).trim()).equalsIgnoreCase(""))
          insertPrepStmtObject.addNull();
        else {
          insertPrepStmtObject.addString(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getAddId1()).trim()));
        }
        if (StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getAddId2()).trim()).equalsIgnoreCase(""))
          insertPrepStmtObject.addNull();
        else {
          insertPrepStmtObject.addString(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getAddId2()).trim()));
        }
        if (CommonFunction.checkNull(Integer.valueOf(stackId)).trim().equalsIgnoreCase(""))
          insertPrepStmtObject.addNull();
        else {
          insertPrepStmtObject.addInt(stackId);
        }
        insertPrepStmtObject.setSql(query.toString());
        logger.info(new StringBuilder().append("IN updateStakeHolder() update query1 ### ").append(insertPrepStmtObject.printQuery()).toString());
        qryList.add(insertPrepStmtObject);

        qryStatus = ConnectionDAO.sqlInsUpdDeletePrepStmt(qryList);
      }
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      subQuery = "";
    }
    if (qryStatus)
    {
      status = 1;
    }
    return status;
  }

  private boolean checkStake(int stackId)
  {
    logger.info(new StringBuilder().append("stack id....................................................................... ").append(stackId).toString());
    boolean flag = true;
    try {
      String query = new StringBuilder().append("select STAKEHOLDER_ID from customer_stakeholder_m_temp where STAKEHOLDER_ID=").append(stackId).toString();
      flag = ConnectionDAO.checkStatus(query);
      if (flag)
        flag = false;
    }
    catch (Exception e) {
      e.printStackTrace();
    }
    return flag;
  }

  public ArrayList<Object> getRatingDetails(int code, Object pageStatus, String statusCase, String updateFlag, String updateInMaker, String cuaStatus, String source) {
    ArrayList list = new ArrayList();
    String tableName = "";
    if (((updateFlag != null) && (updateFlag.equals("updateFlag"))) || ((updateFlag != null) && (updateFlag.equals("notEdit"))))
    {
      logger.info("In Credit Processing , Customer Entry..,getRatingDetails()......");
      tableName = "cr_deal_customer_rating";
      try {
        String query = new StringBuilder().append("SELECT r.CUSTOMER_RATING_ID,a.AGENCY_NAME,r.RATING_AGENCY,r.CUSTOMER_RATING,r.RATING_DATE from ").append(tableName).append(" r, com_agency_m a ").append("where a.AGENCY_CODE=r.RATING_AGENCY and r.CUSTOMER_ID=").append(code).toString();

        logger.info(new StringBuilder().append("In getRatingDetails()  Query  :  ").append(query).toString());
        ArrayList ratingDetails = ConnectionDAO.sqlSelect(query);
        logger.info(new StringBuilder().append("getRatingDetails ").append(ratingDetails.size()).toString());
        for (int i = 0; i < ratingDetails.size(); i++)
        {
          ArrayList data = (ArrayList)ratingDetails.get(i);
          if (data.size() > 0)
          {
            CreditRatingGridVo creditRatingGridVo = new CreditRatingGridVo();
            creditRatingGridVo.setRatingId(CommonFunction.checkNull(data.get(0)).toString());
            creditRatingGridVo.setInstitute(CommonFunction.checkNull(data.get(1)).toString());
            creditRatingGridVo.setAgencyCode(CommonFunction.checkNull(data.get(2)).toString());
            creditRatingGridVo.setRating(CommonFunction.checkNull(data.get(3)).toString());
            creditRatingGridVo.setCreditDate(CommonFunction.checkNull(data.get(4)).toString());
            list.add(creditRatingGridVo);
          }
        }
      } catch (Exception e) {
        e.printStackTrace();
      }

    }
    else
    {
      if ((pageStatus != null) || ((updateInMaker != null) && (updateInMaker.equals("updateInMaker"))) || ((statusCase != null) && (!statusCase.equals(""))))
      {
        if ((statusCase != null) && (statusCase.equalsIgnoreCase("Approved")) && ((updateInMaker == null) || (updateInMaker.equals(""))))
        {
          tableName = "customer_rating_m";
          if (CommonFunction.checkNull(source).trim().equalsIgnoreCase("ED"))
            tableName = "customer_rating_m_edit";
        }
        else
        {
          tableName = "customer_rating_m_temp";
        }

      }
      else
      {
        tableName = "customer_rating_m";
        if (CommonFunction.checkNull(source).trim().equalsIgnoreCase("ED")) {
          tableName = "customer_rating_m_edit";
        }
      }
      if (CommonFunction.checkNull(cuaStatus).equalsIgnoreCase("CUA"))
      {
        tableName = "customer_rating_m_temp";
      }

      try
      {
        String query = new StringBuilder().append("SELECT r.CUSTOMER_RATING_ID,a.AGENCY_NAME,r.RATING_AGENCY,r.CUSTOMER_RATING,r.RATING_DATE from ").append(tableName).append(" r, com_agency_m a ").append("where a.AGENCY_CODE=r.RATING_AGENCY and r.CUSTOMER_ID=").append(code).toString();

        logger.info(new StringBuilder().append("In getRatingDetails()  Query  :  ").append(query).toString());
        ArrayList ratingDetails = ConnectionDAO.sqlSelect(query);
        logger.info(new StringBuilder().append("getRatingDetails ").append(ratingDetails.size()).toString());
        for (int i = 0; i < ratingDetails.size(); i++) {
          ArrayList data = (ArrayList)ratingDetails.get(i);
          if (data.size() > 0)
          {
            CreditRatingGridVo creditRatingGridVo = new CreditRatingGridVo();
            creditRatingGridVo.setRatingId(CommonFunction.checkNull(data.get(0)).toString());
            creditRatingGridVo.setInstitute(CommonFunction.checkNull(data.get(1)).toString());
            creditRatingGridVo.setAgencyCode(CommonFunction.checkNull(data.get(2)).toString());
            creditRatingGridVo.setRating(CommonFunction.checkNull(data.get(3)).toString());
            creditRatingGridVo.setCreditDate(CommonFunction.checkNull(data.get(4)).toString());
            list.add(creditRatingGridVo);
          }
        }
      }
      catch (Exception e) {
        e.printStackTrace();
      }
    }
    return list;
  }

  public int updateCreditRating(Object ob, int id, int crId, String recStatus, String statusCase, String updateFlag, String pageStatuss, String source)
  {
    CreditRatingVo vo = (CreditRatingVo)ob;
    int status = 0;
    boolean qryStatus = false;
    String tableName = "";
    String subQuery = null;
    PrepStmtObject insertPrepStmtObject = new PrepStmtObject();
    ArrayList qryList = new ArrayList();
    try
    {
      logger.info(new StringBuilder().append("updateCreditRating :").append(id).toString());
      if (((pageStatuss != null) && (pageStatuss.equals("fromLeads"))) || ((updateFlag != null) && (updateFlag.equals("updateFlag"))))
      {
        logger.info("In Credit Processing , Customer Entry..,updateCreditRating()......");
        tableName = "cr_deal_customer_rating";
        String query = new StringBuilder().append("update ").append(tableName).append(" set RATING_AGENCY=?, CUSTOMER_RATING=?, ").append("RATING_DATE=STR_TO_DATE(?,'").append(this.dateFormat).append("'),MAKER_ID=?,MAKER_DATE=DATE_ADD(STR_TO_DATE(?,'").append(this.dateFormatWithTime).append("'),INTERVAL CURTIME() HOUR_SECOND),AUTHOR_ID=?,AUTHOR_DATE=DATE_ADD(STR_TO_DATE(?,'").append(this.dateFormatWithTime).append("'),INTERVAL CURTIME() HOUR_SECOND) where CUSTOMER_RATING_ID=?").toString();

        if (CommonFunction.checkNull(vo.getInstitute()).trim().equalsIgnoreCase(""))
          insertPrepStmtObject.addNull();
        else {
          insertPrepStmtObject.addString(vo.getInstitute().trim());
        }
        if (CommonFunction.checkNull(vo.getRating()).trim().equalsIgnoreCase(""))
          insertPrepStmtObject.addNull();
        else {
          insertPrepStmtObject.addString(vo.getRating().trim());
        }
        if (CommonFunction.checkNull(vo.getCreditDate()).trim().equalsIgnoreCase(""))
          insertPrepStmtObject.addNull();
        else {
          insertPrepStmtObject.addString(vo.getCreditDate().trim());
        }

        if (StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getMakerId()).trim()).equalsIgnoreCase(""))
          insertPrepStmtObject.addNull();
        else {
          insertPrepStmtObject.addString(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getMakerId()).trim()));
        }

        if (StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getMakerDate()).trim()).equalsIgnoreCase(""))
          insertPrepStmtObject.addNull();
        else {
          insertPrepStmtObject.addString(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getMakerDate()).trim()));
        }

        if (StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getMakerId()).trim()).equalsIgnoreCase(""))
          insertPrepStmtObject.addNull();
        else {
          insertPrepStmtObject.addString(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getMakerId()).trim()));
        }

        if (StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getMakerDate()).trim()).equalsIgnoreCase(""))
          insertPrepStmtObject.addNull();
        else {
          insertPrepStmtObject.addString(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getMakerDate()).trim()));
        }

        if (CommonFunction.checkNull(Integer.valueOf(crId)).trim().equalsIgnoreCase(""))
          insertPrepStmtObject.addNull();
        else {
          insertPrepStmtObject.addInt(crId);
        }
        insertPrepStmtObject.setSql(query.toString());
        logger.info(new StringBuilder().append("IN updateCreditRating() update query1 ### ").append(insertPrepStmtObject.printQuery()).toString());
        qryList.add(insertPrepStmtObject);
        qryStatus = ConnectionDAO.sqlInsUpdDeletePrepStmt(qryList);
      }
      else
      {
        logger.info("In GCD..,saveCreditRating()......method ");
        if ((statusCase != null) && (statusCase.length() > 0))
        {
          tableName = "customer_rating_m_temp";
        }
        else
        {
          tableName = "customer_rating_m";
          if (CommonFunction.checkNull(source).trim().equalsIgnoreCase("ED"))
            tableName = "customer_rating_m_edit";
        }
        if ((tableName.equalsIgnoreCase("customer_rating_m")) || (tableName.equalsIgnoreCase("customer_rating_m_edit"))) {
          subQuery = new StringBuilder().append(",MAKER_ID='").append(CommonFunction.checkNull(vo.getMakerId())).append("',").append("MAKER_DATE=DATE_ADD(STR_TO_DATE('").append(CommonFunction.checkNull(vo.getMakerDate())).append("','").append(this.dateFormatWithTime).append("'),INTERVAL CURTIME() HOUR_SECOND) ").append(",AUTHOR_ID='").append(CommonFunction.checkNull(vo.getMakerId())).append("',AUTHOR_DATE=DATE_ADD(STR_TO_DATE('").append(CommonFunction.checkNull(vo.getMakerDate())).append("','").append(this.dateFormatWithTime).append("'),INTERVAL CURTIME() HOUR_SECOND)").toString();
        }
        else
        {
          subQuery = "";
        }
        String query = new StringBuilder().append("update ").append(tableName).append(" set RATING_AGENCY=?, CUSTOMER_RATING=?, ").append("RATING_DATE=STR_TO_DATE(?,'").append(this.dateFormat).append("') ").append(subQuery).append(" where CUSTOMER_RATING_ID=?").toString();

        if (CommonFunction.checkNull(vo.getInstitute()).trim().equalsIgnoreCase(""))
          insertPrepStmtObject.addNull();
        else {
          insertPrepStmtObject.addString(vo.getInstitute().trim());
        }
        if (CommonFunction.checkNull(vo.getRating()).trim().equalsIgnoreCase(""))
          insertPrepStmtObject.addNull();
        else {
          insertPrepStmtObject.addString(vo.getRating().trim());
        }
        if (CommonFunction.checkNull(vo.getCreditDate()).trim().equalsIgnoreCase(""))
          insertPrepStmtObject.addNull();
        else {
          insertPrepStmtObject.addString(vo.getCreditDate().trim());
        }

        if (CommonFunction.checkNull(Integer.valueOf(crId)).trim().equalsIgnoreCase(""))
          insertPrepStmtObject.addNull();
        else {
          insertPrepStmtObject.addInt(crId);
        }

        insertPrepStmtObject.setSql(query.toString());
        logger.info(new StringBuilder().append("IN updateCreditRating() update query1 ### ").append(insertPrepStmtObject.printQuery()).toString());
        qryList.add(insertPrepStmtObject);

        qryStatus = ConnectionDAO.sqlInsUpdDeletePrepStmt(qryList);
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    if (qryStatus)
    {
      status = 1;
    }
    return status;
  }

  private boolean checkRating(int crId)
  {
    boolean flag = true;
    try {
      String query = new StringBuilder().append("select CUSTOMER_RATING_ID from customer_rating_m_temp where CUSTOMER_RATING_ID=").append(crId).toString();
      flag = ConnectionDAO.checkStatus(query);
      if (flag)
        flag = false;
    }
    catch (Exception e) {
      e.printStackTrace();
    }
    return flag;
  }

  private boolean checkRatingMaster(int id)
  {
    boolean flag = false;
    try {
      String query = new StringBuilder().append("select customer_id from gcd_credit_rating_detail_m where customer_id=").append(id).toString();
      flag = ConnectionDAO.checkStatus(query);
    } catch (Exception e) {
      e.printStackTrace();
    }

    return flag;
  }

  public String moveFromGCD(String customerId, String applType, String dealId, String tableStatus, String userId, String bDate, String pan, String aadhaar)
  {
    logger.info("In moveFromGCD");

    int statusProc = 0;
    String appQ = "";
    String procval = "";
    boolean checkApp = false;
    if ((applType != null) && (applType.equalsIgnoreCase("PRAPPL")))
    {
      appQ = new StringBuilder().append("select DEAL_CUSTOMER_ID from cr_deal_dtl where DEAL_ID=").append(dealId).append(" and DEAL_CUSTOMER_ID is not NULL").toString();
      logger.info(new StringBuilder().append("In Cr_deal_dtl. query.....................").append(appQ).toString());
      checkApp = ConnectionDAO.checkStatus(appQ);
      if (checkApp)
        return "More than one applicant can not be possible.";
    }
    String q = new StringBuilder().append("select DEAL_CUSTOMER_ID from cr_deal_customer_role where DEAL_CUSTOMER_ID=").append(customerId).append(" and DEAL_ID=").append(dealId).toString();
    logger.info(new StringBuilder().append("Query: ").append(q).toString());
    boolean exist = ConnectionDAO.checkStatus(q);
    if (exist)
    {
      return "Customer being added already exist in this deal.";
    }

    logger.info(new StringBuilder().append("pan------->").append(pan).append(",customerId------->").append(customerId).toString());
    if (!CommonFunction.checkNull(pan).trim().equalsIgnoreCase("")) {
      String custID = new StringBuilder().append("select max(CUSTOMER_ID) from gcd_customer_m where CUSTOMER_STATUS='A' and custmer_pan='").append(pan).append("' ").toString();
      String custID1 = ConnectionDAO.singleReturn(custID);
      logger.info(new StringBuilder().append("custID------->").append(custID).append(",custID1-------->>").append(custID1).append(",customerId--->").append(customerId).toString());
      if (!CommonFunction.checkNull(custID1).trim().equalsIgnoreCase(customerId))
      {
        return "Please select latest customer.";
      }
    }
    if (!CommonFunction.checkNull(aadhaar).trim().equalsIgnoreCase("")) {
      String uCustID = new StringBuilder().append("select max(CUSTOMER_ID) from gcd_customer_m where CUSTOMER_STATUS='A' and UID_NO='").append(aadhaar).append("' ").toString();
      String uCustID1 = ConnectionDAO.singleReturn(uCustID);
      logger.info(new StringBuilder().append("uCustID------->").append(uCustID).append("uCustID1--------->").append(uCustID1).append(",customerId--->").append(customerId).toString());

      if (!CommonFunction.checkNull(uCustID1).trim().equalsIgnoreCase(customerId))
      {
        return "Please select latest customer.";
      }

    }

    ArrayList in = new ArrayList();
    ArrayList out = new ArrayList();
    ArrayList outMessages = new ArrayList();
    String s1 = "";
    String s2 = "";
    try
    {
      logger.info(new StringBuilder().append("Applicant Type in moveFromGCD:== ").append(applType).append(" customerId:  ").append(customerId).append(" tableStatus:  ").append(tableStatus).append(" userId: ").append(userId).append(" bDate:  ").append(bDate).toString());
      logger.info("Gcd_Customer_Link Procedure------>");
      in.add(dealId);
      in.add(customerId);
      in.add(applType);
      in.add(tableStatus);
      in.add(userId);
      String date = CommonFunction.changeFormat(bDate);
      if (date != null) {
        in.add(date);
      }
      in.add("LIM");

      out.add(s1);
      out.add(s2);
      logger.info(new StringBuilder().append("Gcd_Customer_Link(").append(in.toString()).append(",").append(out.toString()).append(")").toString());
      outMessages = (ArrayList)ConnectionDAO.callSP("Gcd_Customer_Link", in, out);
      s1 = CommonFunction.checkNull(outMessages.get(0));
      s2 = CommonFunction.checkNull(outMessages.get(1));
      logger.info(new StringBuilder().append("s1: ").append(s1).toString());
      logger.info(new StringBuilder().append("s2: ").append(s2).toString());
      procval = s2;

      String countQuery = new StringBuilder().append("select count(1) from cr_deal_movement_dtl where deal_id=").append(CommonFunction.checkNull(dealId)).append(" and DEAL_STAGE_ID='CBL' and rec_status<>'X'").toString();
      logger.info(new StringBuilder().append("IN moveFromGCD() of CorpotateDAOImpl  countQuery  :").append(countQuery).toString());
      String countQueryFlag = ConnectionDAO.singleReturn(countQuery);

      if ((CommonFunction.checkNull(countQueryFlag).trim().equalsIgnoreCase("0")) && (CommonFunction.checkNull(s1).trim().equalsIgnoreCase("S")))
      {
        ArrayList qryListMovement = new ArrayList();
        StringBuffer bufInsSqlMovement = new StringBuffer();
        PrepStmtObject insertPrepStmtObject3 = new PrepStmtObject();
        bufInsSqlMovement.append("insert into cr_deal_movement_dtl (DEAL_ID,DEAL_STAGE_ID,DEAL_ACTION,DEAL_RECEIVED,DEAL_FORWARDED,DEAL_RECEIVED_USER  ,DEAL_FORWARD_USER , REC_STATUS)values");
        bufInsSqlMovement.append("(?,'CBL','I',");
        bufInsSqlMovement.append(new StringBuilder().append(" DATE_ADD(STR_TO_DATE(?, '").append(this.dateFormatWithTime).append("'),INTERVAL CURTIME() HOUR_SECOND) , ").toString());
        bufInsSqlMovement.append(" '0000-00-00 00:00:00' , ");
        bufInsSqlMovement.append(" ?,'','A')");
        if (CommonFunction.checkNull(dealId).trim().equalsIgnoreCase(""))
          insertPrepStmtObject3.addNull();
        else
          insertPrepStmtObject3.addString(dealId.trim());
        if (CommonFunction.checkNull(bDate).trim().equalsIgnoreCase(""))
          insertPrepStmtObject3.addNull();
        else {
          insertPrepStmtObject3.addString(bDate.trim());
        }
        if (CommonFunction.checkNull(userId).trim().equalsIgnoreCase(""))
          insertPrepStmtObject3.addNull();
        else {
          insertPrepStmtObject3.addString(userId.trim());
        }

        insertPrepStmtObject3.setSql(bufInsSqlMovement.toString());
        logger.info(new StringBuilder().append("IN moveFromGCD insert query1 : ").append(insertPrepStmtObject3.printQuery()).toString());
        qryListMovement.add(insertPrepStmtObject3);
        boolean status = ConnectionDAO.sqlInsUpdDeletePrepStmt(qryListMovement);
        if (!status) {
          procval = "ERROR DUE TO SQL EXECPTION";
        }
      }

    }
    catch (Exception e)
    {
      logger.info(new StringBuilder().append("Exception In approve: ").append(e).toString());
    }
    finally
    {
      in = null;
      out = null;
      outMessages = null;
    }

    return procval;
  }

  public ArrayList<Object> getStakeDetailsAll(int id, String statusCase, String updateInMaker, String pageStatuss, String updateFlag, String cuaStatus, String source)
  {
    ArrayList list = new ArrayList();
    String tableName = "";

    if (((pageStatuss != null) && (pageStatuss.equals("fromLeads"))) || ((updateFlag != null) && (updateFlag.equals("updateFlag"))) || ((updateFlag != null) && (updateFlag.equals("notEdit"))))
    {
      logger.info("In Credit Processing , Customer Entry.,getStakeDetailsAll().......");
      tableName = "cr_deal_customer_stakeholder_m";
      try
      {
        String query = new StringBuilder().append("SELECT c.STAKEHOLDER_ID,d.DESCRIPTION, c.STAKEHOLDER_NAME, c.STAKEHOLDER_PERCENTAGE,  DATE_FORMAT(c.STAKEHOLDER_JOINING_DATE,'").append(this.dateFormat).append("')").append(" FROM ").append(tableName).append(" c left join generic_master d on c.stakeholder_position=d.VALUE and d.GENERIC_KEY='DESIGNATION' ").append(" where c.CUSTOMER_ID=").append(id).toString();

        ArrayList stakeDetailsAll = ConnectionDAO.sqlSelect(query);
        logger.info(new StringBuilder().append("query ").append(query).toString());

        String perQuery = new StringBuilder().append("select sum(ifnull(STAKEHOLDER_PERCENTAGE,0)) from cr_deal_customer_stakeholder_m where CUSTOMER_ID=").append(id).toString();
        logger.info(new StringBuilder().append("Query for getting total percentage : ").append(perQuery).toString());
        String percentage = ConnectionDAO.singleReturn(perQuery);

        logger.info(new StringBuilder().append("getStakeDetailsAll ").append(stakeDetailsAll.size()).toString());
        for (int i = 0; i < stakeDetailsAll.size(); i++)
        {
          ArrayList data = (ArrayList)stakeDetailsAll.get(i);
          if (data.size() > 0)
          {
            StakeHolderVo addr = new StakeHolderVo();
            addr.setHolderid(CommonFunction.checkNull(data.get(0)).toString());
            addr.setPosition(CommonFunction.checkNull(data.get(1)).toString());
            addr.setHolderName(CommonFunction.checkNull(data.get(2)).toString());

            if (!CommonFunction.checkNull(data.get(3)).toString().equalsIgnoreCase(""))
            {
              Number reconNum = this.myFormatter.parse(CommonFunction.checkNull(data.get(3)).toString());
              addr.setHoldingPerc(this.myFormatter.format(reconNum));
            }

            addr.setDoj(CommonFunction.checkNull(data.get(4)).toString());
            addr.setPercentage(percentage);
            list.add(addr);
          }
        }
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
    else
    {
      logger.info("In GCD.,getStakeDetailsAll().......");
      if (((updateInMaker != null) && (updateInMaker.equals("updateInMaker"))) || ((statusCase != null) && (!statusCase.equals(""))))
      {
        if ((statusCase != null) && (statusCase.equalsIgnoreCase("Approved")) && ((updateInMaker == null) || (updateInMaker.equals(""))))
        {
          tableName = "customer_stakeholder_m";
          if (CommonFunction.checkNull(source).trim().equalsIgnoreCase("ED"))
            tableName = "customer_stakeholder_m_edit";
        }
        else
        {
          tableName = "customer_stakeholder_m_temp";
        }
      }
      else
      {
        tableName = "customer_stakeholder_m";
        if (CommonFunction.checkNull(source).trim().equalsIgnoreCase("ED")) {
          tableName = "customer_stakeholder_m_edit";
        }
      }

      if (CommonFunction.checkNull(cuaStatus).equalsIgnoreCase("CUA"))
      {
        tableName = "customer_stakeholder_m_temp";
      }

      try
      {
        String query = new StringBuilder().append("SELECT c.STAKEHOLDER_ID,d.DESCRIPTION, c.STAKEHOLDER_NAME, c.STAKEHOLDER_PERCENTAGE,  DATE_FORMAT(c.STAKEHOLDER_JOINING_DATE,'").append(this.dateFormat).append("')").append(" FROM ").append(tableName).append(" c left join generic_master d on c.stakeholder_position=d.VALUE and d.GENERIC_KEY='DESIGNATION' ").append(" where c.CUSTOMER_ID=").append(id).toString();

        logger.info(new StringBuilder().append("Query for getting stack holder list  : ").append(query).toString());
        ArrayList stakeDetailsAll = ConnectionDAO.sqlSelect(query);
        logger.info(new StringBuilder().append("getStakeDetailsAll ").append(stakeDetailsAll.size()).toString());

        String perQuery = new StringBuilder().append("select sum(ifnull(STAKEHOLDER_PERCENTAGE,0)) from ").append(tableName).append(" where CUSTOMER_ID=").append(id).toString();
        logger.info(new StringBuilder().append("Query for getting total percentage : ").append(perQuery).toString());
        String percentage = ConnectionDAO.singleReturn(perQuery);

        for (int i = 0; i < stakeDetailsAll.size(); i++)
        {
          ArrayList data = (ArrayList)stakeDetailsAll.get(i);
          if (data.size() > 0)
          {
            StakeHolderVo addr = new StakeHolderVo();
            addr.setHolderid(CommonFunction.checkNull(data.get(0)).toString());
            addr.setPosition(CommonFunction.checkNull(data.get(1)).toString());
            addr.setHolderName(CommonFunction.checkNull(data.get(2)).toString());

            if (!CommonFunction.checkNull(data.get(3)).toString().equalsIgnoreCase(""))
            {
              Number reconNum = this.myFormatter.parse(CommonFunction.checkNull(data.get(3)).toString());
              addr.setHoldingPerc(this.myFormatter.format(reconNum));
            }

            addr.setDoj(CommonFunction.checkNull(data.get(4)).toString());
            addr.setPercentage(percentage);
            list.add(addr);
          }
        }
      }
      catch (Exception e) {
        e.printStackTrace();
      }
    }
    return list;
  }

  public int deleteStakeHolderDetails(String holderid, String updateInMaker, String statusCase, Object pageStatus, String pageStatuss, String updateFlag, String source)
  {
    int status = 0;
    boolean qryStatus = false;
    String tableName = "";
    ArrayList qryList = new ArrayList();
    try {
      if ((pageStatus != null) || ((updateInMaker != null) && (updateInMaker.equals("updateInMaker"))) || ((statusCase != null) && (!statusCase.equals(""))))
      {
        if ((statusCase != null) && (statusCase.equalsIgnoreCase("Approved")) && ((updateInMaker == null) || (updateInMaker.equals(""))))
        {
          tableName = "customer_stakeholder_m";
          if (CommonFunction.checkNull(source).trim().equalsIgnoreCase("ED"))
            tableName = "customer_stakeholder_m_edit";
        }
        else
        {
          tableName = "customer_stakeholder_m_temp";
        }

      }
      else if (((pageStatuss != null) && (pageStatuss.equals("fromLeads"))) || ((updateFlag != null) && (updateFlag.equals("updateFlag"))) || ((updateFlag != null) && (updateFlag.equals("notEdit"))))
      {
        tableName = "cr_deal_customer_stakeholder_m";
      }
      else
      {
        tableName = "customer_stakeholder_m";
        if (CommonFunction.checkNull(source).trim().equalsIgnoreCase("ED"))
          tableName = "customer_stakeholder_m_edit";
      }
      String query = new StringBuilder().append("delete from ").append(tableName).append(" where STAKEHOLDER_ID=?").toString();
      PrepStmtObject insPrepStmtObject = new PrepStmtObject();
      if (CommonFunction.checkNull(holderid).trim().equalsIgnoreCase(""))
        insPrepStmtObject.addNull();
      else {
        insPrepStmtObject.addString(CommonFunction.checkNull(holderid).trim());
      }
      insPrepStmtObject.setSql(query.toString());
      logger.info(new StringBuilder().append("IN deleteStakeHolderDetails() update query1 ### ").append(insPrepStmtObject.printQuery()).toString());
      qryList.add(insPrepStmtObject);
      qryStatus = ConnectionDAO.sqlInsUpdDeletePrepStmt(qryList);
      logger.info(new StringBuilder().append("Deletion Status :.").append(qryStatus).toString());
    } catch (Exception e) {
      e.printStackTrace();
    }
    if (qryStatus)
    {
      status = 1;
    }
    return status;
  }

  public ArrayList<InstitutionNameVo> getInstitutionName()
  {
    ArrayList list = new ArrayList();
    try {
      String query = "select AGENCY_CODE,AGENCY_NAME from com_agency_m where AGENCY_TYPE='CR'";
      ArrayList institutionName = ConnectionDAO.sqlSelect(query);
      logger.info(new StringBuilder().append("getInstitutionName ").append(institutionName.size()).toString());
      for (int i = 0; i < institutionName.size(); i++)
      {
        ArrayList data = (ArrayList)institutionName.get(i);
        if (data.size() > 0)
        {
          InstitutionNameVo institutionNameVo = new InstitutionNameVo();
          institutionNameVo.setAgencyCode(CommonFunction.checkNull(data.get(0)).toString());
          institutionNameVo.setAgencyDesc(CommonFunction.checkNull(data.get(1)).toString());
          list.add(institutionNameVo);
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    return list;
  }

  public int deleteSelectedCreditRating(String ratingId, String updateInMaker, String statusCase, Object pageStatus, String pageStatuss, String updateFlag, String source)
  {
    int status = 0;
    boolean qryStatus = false;
    String tableName = "";
    ArrayList qryList = new ArrayList();
    try {
      if ((pageStatus != null) || ((updateInMaker != null) && (updateInMaker.equals("updateInMaker"))) || ((statusCase != null) && (!statusCase.equals(""))))
      {
        if ((statusCase != null) && (statusCase.equalsIgnoreCase("Approved")) && ((updateInMaker == null) || (updateInMaker.equals(""))))
        {
          tableName = "customer_rating_m";
          if (CommonFunction.checkNull(source).trim().equalsIgnoreCase("ED"))
            tableName = "customer_rating_m_edit";
        }
        else
        {
          tableName = "customer_rating_m_temp";
        }

      }
      else if (((pageStatuss != null) && (pageStatuss.equals("fromLeads"))) || ((updateFlag != null) && (updateFlag.equals("updateFlag"))) || ((updateFlag != null) && (updateFlag.equals("notEdit"))))
      {
        tableName = "cr_deal_customer_rating";
      }
      else
      {
        tableName = "customer_rating_m";
        if (CommonFunction.checkNull(source).trim().equalsIgnoreCase("ED")) {
          tableName = "customer_rating_m_edit";
        }
      }
      String query = new StringBuilder().append("delete from ").append(tableName).append(" where CUSTOMER_RATING_ID=?").toString();
      PrepStmtObject insPrepStmtObject = new PrepStmtObject();
      if (CommonFunction.checkNull(ratingId).trim().equalsIgnoreCase(""))
        insPrepStmtObject.addNull();
      else {
        insPrepStmtObject.addString(CommonFunction.checkNull(ratingId).trim());
      }
      insPrepStmtObject.setSql(query.toString());
      logger.info(new StringBuilder().append("IN deleteSelectedCreditRating() update query1 ### ").append(insPrepStmtObject.printQuery()).toString());
      qryList.add(insPrepStmtObject);
      qryStatus = ConnectionDAO.sqlInsUpdDeletePrepStmt(qryList);
      logger.info(new StringBuilder().append("Deletion Status :.").append(qryStatus).toString());
    } catch (Exception e) {
      e.printStackTrace();
    }
    if (qryStatus)
    {
      status = 1;
    }
    return status;
  }

  public ArrayList<Object> getAddressDetails(String code, String statusCase, String updateInMaker, String pageStatuss, String updateFlag, String cuaStatus, String source)
  {
    String tableName = "";
    logger.info("In getAddressDetails() of CorpotateDAOImpl");
    ArrayList list = new ArrayList();
    if (((pageStatuss != null) && (pageStatuss.equals("fromLeads"))) || ((updateFlag != null) && (updateFlag.equals("updateFlag"))) || ((updateFlag != null) && (updateFlag.equals("notEdit"))))
    {
      tableName = "cr_deal_address_m";
      try
      {
        String query = new StringBuilder().append("SELECT c.ADDRESS_ID,m.DESCRIPTION,c.ADDRESS_LINE1,d.DISTRICT_DESC,s.STATE_DESC,p.COUNTRY_DESC from ").append(tableName).append(" c,generic_master m,com_country_m p,com_district_m d,com_state_m s ").append("where  m.VALUE=c.ADDRESS_TYPE AND GENERIC_KEY='ADDRESS_TYPE' AND P.COUNTRY_id=c.COUNTRY AND s.STATE_id=c.STATE AND d.DISTRICT_id=c.DISTRICT and c.BPID=").append(code).toString();

        logger.info(new StringBuilder().append("in getAddressDetails() of CorpotateDAOImpl  Query : ").append(query).toString());
        ArrayList addressDetails = ConnectionDAO.sqlSelect(query);

        for (int i = 0; i < addressDetails.size(); i++)
        {
          ArrayList data = (ArrayList)addressDetails.get(i);
          if (data.size() > 0)
          {
            CustomerSaveVo addr = new CustomerSaveVo();
            addr.setBp_id(CommonFunction.checkNull(data.get(0)).toString());
            addr.setAddr_type(CommonFunction.checkNull(data.get(1)).toString());
            addr.setAddr1(CommonFunction.checkNull(data.get(2)).toString());
            addr.setDist(CommonFunction.checkNull(data.get(3)).toString());
            addr.setState(CommonFunction.checkNull(data.get(4)).toString());
            addr.setCountry(CommonFunction.checkNull(data.get(5)).toString());
            list.add(addr);
          }
        }
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
    else
    {
      if (((updateInMaker != null) && (updateInMaker.equals("updateInMaker"))) || ((statusCase != null) && (!statusCase.equals(""))))
      {
        if ((statusCase != null) && (statusCase.equalsIgnoreCase("Approved")) && ((updateInMaker == null) || (updateInMaker.equals(""))))
        {
          tableName = "com_address_m";
          if (CommonFunction.checkNull(source).trim().equalsIgnoreCase("ED"))
            tableName = "com_address_m_edit";
        }
        else
        {
          tableName = "com_address_m_temp";
        }
      }
      else
      {
        tableName = "com_address_m";
        if (CommonFunction.checkNull(source).trim().equalsIgnoreCase("ED")) {
          tableName = "com_address_m_edit";
        }

      }

      if (CommonFunction.checkNull(cuaStatus).equalsIgnoreCase("CUA"))
      {
        tableName = "com_address_m_temp";
      }

      try
      {
        String query = new StringBuilder().append("SELECT c.ADDRESS_ID,m.DESCRIPTION,c.ADDRESS_LINE1,d.DISTRICT_DESC,s.STATE_DESC,p.COUNTRY_DESC  from ").append(tableName).append(" c,generic_master m,com_country_m p,com_district_m d,com_state_m s ").append(" where  m.VALUE=c.ADDRESS_TYPE AND GENERIC_KEY='ADDRESS_TYPE' AND P.COUNTRY_id=c.COUNTRY AND s.STATE_id=c.STATE AND d.DISTRICT_id=c.DISTRICT").append(" and c.BPID=").append(code).toString();

        logger.info(new StringBuilder().append("in getAddressDetails() of CorpotateDAOImpl  Query :   ").append(query).toString());
        ArrayList addressDetails = ConnectionDAO.sqlSelect(query);

        logger.info(new StringBuilder().append("getAddressDetails ").append(addressDetails.size()).toString());
        for (int i = 0; i < addressDetails.size(); i++)
        {
          ArrayList data = (ArrayList)addressDetails.get(i);
          if (data.size() > 0)
          {
            CustomerSaveVo addr = new CustomerSaveVo();
            addr.setBp_id(CommonFunction.checkNull(data.get(0)).toString());
            addr.setAddr_type(CommonFunction.checkNull(data.get(1)).toString());
            addr.setAddr1(CommonFunction.checkNull(data.get(2)).toString());
            addr.setDist(CommonFunction.checkNull(data.get(3)).toString());
            addr.setState(CommonFunction.checkNull(data.get(4)).toString());
            addr.setCountry(CommonFunction.checkNull(data.get(5)).toString());
            list.add(addr);
          }
        }
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
    return list;
  }

  public ArrayList<Object> getRefrenceDetails(String code, String statusCase, String updateInMaker, String pageStatuss, String updateFlag, String cuaStatus, String source)
  {
    String tableName = "";
    logger.info("In getRefrenceDetails() of CorpotateDAOImpl");
    ArrayList list = new ArrayList();
    if (((pageStatuss != null) && (pageStatuss.equals("fromLeads"))) || ((updateFlag != null) && (updateFlag.equals("updateFlag"))) || ((updateFlag != null) && (updateFlag.equals("notEdit"))))
    {
      tableName = "cr_deal_reference_m";
      try
      {
        String query = new StringBuilder().append("select REF_ID, concat(F_NAME,' ',IFNULL(M_NAME,''),' ',L_NAME)NAME, RELATIONSHIP,(select DESCRIPTION from generic_master\twhere generic_key='Relation_type'  and value=RELATIONSHIP) RELATIONSHIP_DESC, KNOWING_SINCE, MOBILE_NUMBER, LANDLINE_NUMBER, REF_ADDRESS,EMAIL_ID from ").append(tableName).append(" where BPID=").append(code).toString();
        logger.info(new StringBuilder().append("getRefrenceDetails query : : : : : : :  ").append(query).toString());
        ArrayList addressDetails = ConnectionDAO.sqlSelect(query);
        logger.info(new StringBuilder().append("getRefrenceDetails ").append(addressDetails.size()).toString());
        for (int i = 0; i < addressDetails.size(); i++) {
          ArrayList data = (ArrayList)addressDetails.get(i);
          if (data.size() > 0)
          {
            CustomerSaveVo addr = new CustomerSaveVo();
            addr.setRefId(CommonFunction.checkNull(data.get(0)).toString());
            addr.setRefName(CommonFunction.checkNull(data.get(1)).toString());
            addr.setRelationshipS(CommonFunction.checkNull(data.get(2)).toString());
            addr.setRelationShipDesc(CommonFunction.checkNull(data.get(3)).toString());
            addr.setKnowingSince(CommonFunction.checkNull(data.get(4)).toString());
            addr.setPrimaryRefMbNo(CommonFunction.checkNull(data.get(5)).toString());
            addr.setAlternateRefPhNo(CommonFunction.checkNull(data.get(6)).toString());
            addr.setAddRef(CommonFunction.checkNull(data.get(7)).toString());
            addr.setInstitutionEmail(CommonFunction.checkNull(data.get(8)).toString());
            list.add(addr);
          }
        }
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
    else
    {
      if (((updateInMaker != null) && (updateInMaker.equals("updateInMaker"))) || ((statusCase != null) && (!statusCase.equals(""))))
      {
        if ((statusCase != null) && (statusCase.equalsIgnoreCase("Approved")) && ((updateInMaker == null) || (updateInMaker.equals(""))))
        {
          tableName = "com_reference_m";
          if (CommonFunction.checkNull(source).trim().equalsIgnoreCase("ED"))
            tableName = "com_reference_m_edit";
        }
        else
        {
          tableName = "com_reference_m_temp";
        }
      }
      else
      {
        tableName = "com_reference_m";
        if (CommonFunction.checkNull(source).trim().equalsIgnoreCase("ED")) {
          tableName = "com_reference_m_edit";
        }
      }

      if (CommonFunction.checkNull(cuaStatus).equalsIgnoreCase("CUA"))
      {
        tableName = "com_reference_m_temp";
      }

      try
      {
        String query = new StringBuilder().append("select REF_ID, concat(F_NAME,' ',IFNULL(M_NAME,''),' ',L_NAME)NAME, RELATIONSHIP,(select DESCRIPTION from generic_master\twhere generic_key='Relation_type'  and value=RELATIONSHIP) RELATIONSHIP_DESC, KNOWING_SINCE, MOBILE_NUMBER, LANDLINE_NUMBER, REF_ADDRESS,EMAIL_ID from ").append(tableName).append(" where BPID=").append(code).toString();
        logger.info(new StringBuilder().append("getRefrenceDetails query : : : : : : :  ").append(query).toString());
        ArrayList addressDetails = ConnectionDAO.sqlSelect(query);
        logger.info(new StringBuilder().append("getRefrenceDetails ").append(addressDetails.size()).toString());
        for (int i = 0; i < addressDetails.size(); i++)
        {
          ArrayList data = (ArrayList)addressDetails.get(i);
          if (data.size() > 0)
          {
            CustomerSaveVo addr = new CustomerSaveVo();
            addr.setRefId(CommonFunction.checkNull(data.get(0)).toString());
            addr.setRefName(CommonFunction.checkNull(data.get(1)).toString());
            addr.setRelationshipS(CommonFunction.checkNull(data.get(2)).toString());
            addr.setRelationShipDesc(CommonFunction.checkNull(data.get(3)).toString());
            addr.setKnowingSince(CommonFunction.checkNull(data.get(4)).toString());
            addr.setPrimaryRefMbNo(CommonFunction.checkNull(data.get(5)).toString());
            addr.setAlternateRefPhNo(CommonFunction.checkNull(data.get(6)).toString());
            addr.setAddRef(CommonFunction.checkNull(data.get(7)).toString());
            addr.setInstitutionEmail(CommonFunction.checkNull(data.get(8)).toString());
            list.add(addr);
          }
        }
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
    return list;
  }

  public ArrayList<Object> getSarvSurkshaDetails(String code, String statusCase, String updateInMaker, String pageStatuss, String updateFlag, String cuaStatus, String source)
  {
    String tableName = "";
    logger.info("In getRefrenceDetails() of CorpotateDAOImpl");
    ArrayList list = new ArrayList();
    if (((pageStatuss != null) && (pageStatuss.equals("fromLeads"))) || ((updateFlag != null) && (updateFlag.equals("updateFlag"))) || ((updateFlag != null) && (updateFlag.equals("notEdit"))))
    {
      tableName = "cr_deal_sarva_Suraksha_m";
      try
      {
        String query = new StringBuilder().append("select REF_ID,Nominee_Name, RELATIONSHIP,(select DESCRIPTION from generic_master\twhere generic_key='Relation_type'  and value=RELATIONSHIP) RELATIONSHIP_DESC,date_FORMAT(NomineeDOB  ,'%d-%m-%Y')   from ").append(tableName).append(" where BPID=").append(code).toString();
        logger.info(new StringBuilder().append("getRefrenceDetails query : : : : : : :  ").append(query).toString());
        ArrayList addressDetails = ConnectionDAO.sqlSelect(query);
        logger.info(new StringBuilder().append("getRefrenceDetails ").append(addressDetails.size()).toString());
        for (int i = 0; i < addressDetails.size(); i++) {
          ArrayList data = (ArrayList)addressDetails.get(i);
          if (data.size() > 0)
          {
            CustomerSaveVo addr = new CustomerSaveVo();
            addr.setRefId(CommonFunction.checkNull(data.get(0)).toString());
            addr.setNominee(CommonFunction.checkNull(data.get(1)).toString());
            addr.setRelationshipS(CommonFunction.checkNull(data.get(2)).toString());
            addr.setRelationShipDesc(CommonFunction.checkNull(data.get(3)).toString());
            addr.setNomineeDOB(CommonFunction.checkNull(data.get(4)).toString());

            list.add(addr);
          }
        }
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
    else
    {
      if (((updateInMaker != null) && (updateInMaker.equals("updateInMaker"))) || ((statusCase != null) && (!statusCase.equals(""))))
      {
        if ((statusCase != null) && (statusCase.equalsIgnoreCase("Approved")) && ((updateInMaker == null) || (updateInMaker.equals(""))))
        {
          tableName = "com_sarva_Suraksha_m";
          if (CommonFunction.checkNull(source).trim().equalsIgnoreCase("ED"))
            tableName = "com_sarva_Suraksha_m_edit";
        }
        else
        {
          tableName = "com_sarva_Suraksha_m_temp";
        }
      }
      else
      {
        tableName = "com_sarva_Suraksha_m";
        if (CommonFunction.checkNull(source).trim().equalsIgnoreCase("ED")) {
          tableName = "com_sarva_Suraksha_m_edit";
        }
      }

      if (CommonFunction.checkNull(cuaStatus).equalsIgnoreCase("CUA"))
      {
        tableName = "com_sarva_Suraksha_m_temp";
      }

      try
      {
        String query = new StringBuilder().append("select REF_ID,Nominee_Name, RELATIONSHIP,(select DESCRIPTION from generic_master\twhere generic_key='Relation_type'  and value=RELATIONSHIP) RELATIONSHIP_DESC,date_FORMAT(NomineeDOB  ,'%d-%m-%Y')   from ").append(tableName).append(" where BPID=").append(code).toString();
        logger.info(new StringBuilder().append("getRefrenceDetails query : : : : : : :  ").append(query).toString());
        ArrayList addressDetails = ConnectionDAO.sqlSelect(query);
        logger.info(new StringBuilder().append("getRefrenceDetails ").append(addressDetails.size()).toString());
        for (int i = 0; i < addressDetails.size(); i++)
        {
          ArrayList data = (ArrayList)addressDetails.get(i);
          if (data.size() > 0)
          {
            CustomerSaveVo addr = new CustomerSaveVo();
            addr.setRefId(CommonFunction.checkNull(data.get(0)).toString());
            addr.setNominee(CommonFunction.checkNull(data.get(1)).toString());
            addr.setRelationshipS(CommonFunction.checkNull(data.get(2)).toString());
            addr.setRelationShipDesc(CommonFunction.checkNull(data.get(3)).toString());
            addr.setNomineeDOB(CommonFunction.checkNull(data.get(4)).toString());

            list.add(addr);
          }
        }
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
    return list;
  }

  public int deleteCustomerAddress(String addr_id, String updateInMaker, String statusCase, Object pageStatus, String pageStatuss, String updateFlag, String source)
  {
    int status = 0;
    boolean qryStatus = false;
    String tableName = "";
    ArrayList qryList = new ArrayList();
    try
    {
      if ((pageStatus != null) || ((updateInMaker != null) && (updateInMaker.equals("updateInMaker"))) || ((statusCase != null) && (!statusCase.equals(""))))
      {
        logger.info("In Credit Processing, Deal Capturing , deleteCustomerAddress().............. ");
        if ((statusCase != null) && (statusCase.equalsIgnoreCase("Approved")) && ((updateInMaker == null) || (updateInMaker.equals(""))))
        {
          tableName = "com_address_m";
          if (CommonFunction.checkNull(source).trim().equalsIgnoreCase("ED")) {
            tableName = "com_address_m_edit";
          }
        }
        else
        {
          tableName = "com_address_m_temp";
        }

      }
      else if (((pageStatuss != null) && (pageStatuss.equals("fromLeads"))) || ((updateFlag != null) && (updateFlag.equals("updateFlag"))) || ((updateFlag != null) && (updateFlag.equals("notEdit"))))
      {
        tableName = "cr_deal_address_m";
      }
      else
      {
        tableName = "com_address_m";
        if (CommonFunction.checkNull(source).trim().equalsIgnoreCase("ED"))
          tableName = "com_address_m_edit";
      }
      String query = new StringBuilder().append("delete from ").append(tableName).append(" where ADDRESS_ID=?").toString();
      PrepStmtObject insPrepStmtObject = new PrepStmtObject();
      if (CommonFunction.checkNull(addr_id).trim().equalsIgnoreCase(""))
        insPrepStmtObject.addNull();
      else {
        insPrepStmtObject.addString(CommonFunction.checkNull(addr_id).trim());
      }
      insPrepStmtObject.setSql(query.toString());
      logger.info(new StringBuilder().append("IN deleteSelectedCreditRating() update query1 ### ").append(insPrepStmtObject.printQuery()).toString());
      qryList.add(insPrepStmtObject);
      qryStatus = ConnectionDAO.sqlInsUpdDeletePrepStmt(qryList);
      logger.info(new StringBuilder().append("Deletion Status :.").append(qryStatus).toString());
    } catch (Exception e) {
      e.printStackTrace();
    }
    if (qryStatus)
    {
      status = 1;
    }
    return status;
  }

  public int deleteCustomerReference(String addr_id, String updateInMaker, String statusCase, Object pageStatus, String pageStatuss, String updateFlag, String source)
  {
    int status = 0;
    boolean qryStatus = false;
    String tableName = "";
    ArrayList qryList = new ArrayList();
    try
    {
      if ((pageStatus != null) || ((updateInMaker != null) && (updateInMaker.equals("updateInMaker"))) || ((statusCase != null) && (!statusCase.equals(""))))
      {
        logger.info("In Credit Processing, Deal Capturing , deleteCustomerReference().............. ");
        if ((statusCase != null) && (statusCase.equalsIgnoreCase("Approved")) && ((updateInMaker == null) || (updateInMaker.equals(""))))
        {
          tableName = "com_reference_m";
          if (CommonFunction.checkNull(source).trim().equalsIgnoreCase("ED")) {
            tableName = "com_reference_m_edit";
          }
        }
        else
        {
          tableName = "com_reference_m_temp";
        }

      }
      else if (((pageStatuss != null) && (pageStatuss.equals("fromLeads"))) || ((updateFlag != null) && (updateFlag.equals("updateFlag"))) || ((updateFlag != null) && (updateFlag.equals("notEdit"))))
      {
        tableName = "cr_deal_reference_m";
      }
      else
      {
        tableName = "com_reference_m";
        if (CommonFunction.checkNull(source).trim().equalsIgnoreCase("ED"))
          tableName = "com_reference_m_edit";
      }
      String query = new StringBuilder().append("delete from ").append(tableName).append(" where REF_ID=?").toString();
      PrepStmtObject insPrepStmtObject = new PrepStmtObject();
      if (CommonFunction.checkNull(addr_id).trim().equalsIgnoreCase(""))
        insPrepStmtObject.addNull();
      else {
        insPrepStmtObject.addString(CommonFunction.checkNull(addr_id).trim());
      }
      insPrepStmtObject.setSql(query.toString());
      logger.info(new StringBuilder().append("IN deleteCustomerReference() update query1 ### ").append(insPrepStmtObject.printQuery()).toString());
      qryList.add(insPrepStmtObject);
      qryStatus = ConnectionDAO.sqlInsUpdDeletePrepStmt(qryList);
      logger.info(new StringBuilder().append("Deletion Status :.").append(qryStatus).toString());
    } catch (Exception e) {
      e.printStackTrace();
    }
    if (qryStatus)
    {
      status = 1;
    }
    return status;
  }

  public int deletesarvaSuraksha(String addr_id, String updateInMaker, String statusCase, Object pageStatus, String pageStatuss, String updateFlag, String source)
  {
    int status = 0;
    boolean qryStatus = false;
    String tableName = "";
    ArrayList qryList = new ArrayList();
    try
    {
      if ((pageStatus != null) || ((updateInMaker != null) && (updateInMaker.equals("updateInMaker"))) || ((statusCase != null) && (!statusCase.equals(""))))
      {
        logger.info("In Credit Processing, Deal Capturing , deletesarvaSuraksha().............. ");
        if ((statusCase != null) && (statusCase.equalsIgnoreCase("Approved")) && ((updateInMaker == null) || (updateInMaker.equals(""))))
        {
          tableName = "com_sarva_Suraksha_m";
          if (CommonFunction.checkNull(source).trim().equalsIgnoreCase("ED")) {
            tableName = "com_sarva_Suraksha_m_edit";
          }
        }
        else
        {
          tableName = "com_sarva_Suraksha_m_temp";
        }

      }
      else if (((pageStatuss != null) && (pageStatuss.equals("fromLeads"))) || ((updateFlag != null) && (updateFlag.equals("updateFlag"))) || ((updateFlag != null) && (updateFlag.equals("notEdit"))))
      {
        tableName = "cr_deal_sarva_Suraksha_m";
      }
      else
      {
        tableName = "com_sarva_Suraksha_m";
        if (CommonFunction.checkNull(source).trim().equalsIgnoreCase("ED"))
          tableName = "com_sarva_Suraksha_m_edit";
      }
      String query = new StringBuilder().append("delete from ").append(tableName).append(" where REF_ID=?").toString();
      PrepStmtObject insPrepStmtObject = new PrepStmtObject();
      if (CommonFunction.checkNull(addr_id).trim().equalsIgnoreCase(""))
        insPrepStmtObject.addNull();
      else {
        insPrepStmtObject.addString(CommonFunction.checkNull(addr_id).trim());
      }
      insPrepStmtObject.setSql(query.toString());
      logger.info(new StringBuilder().append("IN deletesarvaSuraksha update query1 ### ").append(insPrepStmtObject.printQuery()).toString());
      qryList.add(insPrepStmtObject);
      qryStatus = ConnectionDAO.sqlInsUpdDeletePrepStmt(qryList);
      logger.info(new StringBuilder().append("Deletion Status :.").append(qryStatus).toString());
    } catch (Exception e) {
      e.printStackTrace();
    }
    if (qryStatus)
    {
      status = 1;
    }
    return status;
  }

  public ArrayList<Object> getcreditRatingDetails(int cid, String statusCase, String updateInMaker, String pageStatuss, String updateFlag, String cuaStatus, String source) {
    ArrayList list = new ArrayList();
    String tableName = "";

    if (((pageStatuss != null) && (pageStatuss.equals("fromLeads"))) || ((updateFlag != null) && (updateFlag.equals("updateFlag"))) || ((updateFlag != null) && (updateFlag.equals("notEdit"))))
    {
      logger.info("In Credit Processing , Customer Entry..,getcreditRatingDetails()......");
      tableName = "cr_deal_customer_rating";
      try
      {
        String query = new StringBuilder().append("SELECT r.CUSTOMER_RATING_ID,a.AGENCY_NAME,r.RATING_AGENCY,r.CUSTOMER_RATING,DATE_FORMAT(r.RATING_DATE,'").append(this.dateFormat).append("') from com_agency_m a,").append(tableName).append(" r").append(" where a.AGENCY_CODE=r.RATING_AGENCY and r.CUSTOMER_ID=").append(cid).toString();

        logger.info(new StringBuilder().append("In getcreditRatingDetails()  Query  :  ").append(query).toString());

        ArrayList creditRatingDetails = ConnectionDAO.sqlSelect(query);
        logger.info(new StringBuilder().append("getcreditRatingDetails ").append(creditRatingDetails.size()).toString());
        for (int i = 0; i < creditRatingDetails.size(); i++)
        {
          ArrayList data = (ArrayList)creditRatingDetails.get(i);
          if (data.size() > 0)
          {
            CreditRatingGridVo creditRatingGridVo = new CreditRatingGridVo();
            creditRatingGridVo.setRatingId(CommonFunction.checkNull(data.get(0)).toString());
            creditRatingGridVo.setInstitute(CommonFunction.checkNull(data.get(1)).toString());
            creditRatingGridVo.setAgencyCode(CommonFunction.checkNull(data.get(2)).toString());
            creditRatingGridVo.setRating(CommonFunction.checkNull(data.get(3)).toString());
            creditRatingGridVo.setCreditDate(CommonFunction.checkNull(data.get(4)).toString());
            list.add(creditRatingGridVo);
          }
        }
      } catch (Exception e) {
        e.printStackTrace();
      }

    }
    else
    {
      if (((updateInMaker != null) && (updateInMaker.equals("updateInMaker"))) || ((statusCase != null) && (!statusCase.equals(""))))
      {
        if ((statusCase != null) && (statusCase.equalsIgnoreCase("Approved")) && ((updateInMaker == null) || (updateInMaker.equals(""))))
        {
          tableName = "customer_rating_m";
          if (CommonFunction.checkNull(source).trim().equalsIgnoreCase("ED"))
            tableName = "customer_rating_m_edit";
        }
        else
        {
          tableName = "customer_rating_m_temp";
        }

      }
      else
      {
        tableName = "customer_rating_m";
        if (CommonFunction.checkNull(source).trim().equalsIgnoreCase("ED")) {
          tableName = "customer_rating_m_edit";
        }
      }
      if (CommonFunction.checkNull(cuaStatus).equalsIgnoreCase("CUA"))
      {
        tableName = "customer_rating_m_temp";
      }

      try
      {
        String query = new StringBuilder().append("SELECT r.CUSTOMER_RATING_ID,a.AGENCY_NAME,r.RATING_AGENCY,r.CUSTOMER_RATING,DATE_FORMAT(r.RATING_DATE,'").append(this.dateFormat).append("') from com_agency_m a,").append(tableName).append(" r").append(" where a.AGENCY_CODE=r.RATING_AGENCY and r.CUSTOMER_ID=").append(cid).toString();

        logger.info(new StringBuilder().append("In getcreditRatingDetails()  Query  :  ").append(query).toString());

        ArrayList creditRatingDetails = ConnectionDAO.sqlSelect(query);
        logger.info(new StringBuilder().append("getcreditRatingDetails ").append(creditRatingDetails.size()).toString());
        for (int i = 0; i < creditRatingDetails.size(); i++) {
          ArrayList data = (ArrayList)creditRatingDetails.get(i);
          if (data.size() > 0)
          {
            CreditRatingGridVo creditRatingGridVo = new CreditRatingGridVo();
            creditRatingGridVo.setRatingId(CommonFunction.checkNull(data.get(0)).toString());
            creditRatingGridVo.setInstitute(CommonFunction.checkNull(data.get(1)).toString());
            creditRatingGridVo.setAgencyCode(CommonFunction.checkNull(data.get(2)).toString());
            creditRatingGridVo.setRating(CommonFunction.checkNull(data.get(3)).toString());
            creditRatingGridVo.setCreditDate(CommonFunction.checkNull(data.get(4)).toString());
            list.add(creditRatingGridVo);
          }
        }
      }
      catch (Exception e) {
        e.printStackTrace();
      }
    }
    return list;
  }

  public ArrayList<CustomerSaveVo> getCustomerAddressDetail(String addId, Object pageStatus, String statusCase, String updateInMaker, String updateFlag, String pageStatuss, String cuaStatus, String source)
  {
    logger.info("In getCustomerAddressDetail() of CorpotateDAOImpl.");
    ArrayList list = new ArrayList();
    String tableName = "";
    if (((pageStatuss != null) && (pageStatuss.equals("fromLeads"))) || ((updateFlag != null) && (updateFlag.equals("updateFlag"))) || ((updateFlag != null) && (updateFlag.equals("notEdit"))))
    {
      tableName = "cr_deal_address_m";
      try
      {
        String query = "";
        query = new StringBuilder().append("SELECT distinct a.ADDRESS_TYPE VALUE, a.BPTYPE, a.BPID, a.ADDRESS_LINE1, a.ADDRESS_LINE2, a.ADDRESS_LINE3, c.COUNTRY_ID,s.STATE_ID,d.DISTRICT_ID, a.PINCODE, a.PRIMARY_PHONE, a.ALTERNATE_PHONE, a.TOLLFREE_NUMBER, a.FAX, a.LANDMARK,a.NO_OF_YEARS, a.NO_OF_MONTHS,  a.ADDRESS_ID,a.COMMUNICATION_ADDRESS,c.COUNTRY_DESC,s.STATE_DESC,d.DISTRICT_DESC,a.ADDRESS_DETAIL,a.TAHSIL,a.BRANCH_DISTANCE,a.GST_NO,a.STD_NO,a.RELATIONSHIP_FLAG FROM ").append(tableName).append(" a ").append("join com_country_m c on(a.COUNTRY=c.COUNTRY_ID and a.ADDRESS_ID=").append(addId).append(") ").append("join com_state_m s on(a.STATE=s.STATE_ID) ").append("join com_district_m d on(a.DISTRICT=d.DISTRICT_ID)").toString();

        logger.info(new StringBuilder().append("In getCustomerAddressDetail() of CorpotateDAOImpl. Select Query  :  ").append(query).toString());
        ArrayList customerAddressDetail = ConnectionDAO.sqlSelect(query);
        logger.info(new StringBuilder().append("getCustomerAddressDetail ").append(customerAddressDetail.size()).toString());
        int size = customerAddressDetail.size();
        for (int i = 0; i < size; i++)
        {
          ArrayList data = (ArrayList)customerAddressDetail.get(i);
          if (data.size() > 0)
          {
            CustomerSaveVo customerSaveVo = new CustomerSaveVo();
            customerSaveVo.setAddr_type(CommonFunction.checkNull(data.get(0)).toString());
            customerSaveVo.setBp_type(CommonFunction.checkNull(data.get(1)).toString());
            customerSaveVo.setBp_id(CommonFunction.checkNull(data.get(2)).toString());
            customerSaveVo.setAddr1(CommonFunction.checkNull(data.get(3)).toString());
            customerSaveVo.setAddr2(CommonFunction.checkNull(data.get(4)).toString());
            customerSaveVo.setAddr3(CommonFunction.checkNull(data.get(5)).toString());
            customerSaveVo.setCountry(CommonFunction.checkNull(data.get(19)).toString());
            customerSaveVo.setState(CommonFunction.checkNull(data.get(20)).toString());
            customerSaveVo.setDist(CommonFunction.checkNull(data.get(21)).toString());
            customerSaveVo.setPincode(CommonFunction.checkNull(data.get(9)).toString());
            customerSaveVo.setPrimaryPhoneNo(CommonFunction.checkNull(data.get(10)).toString());
            customerSaveVo.setAlternatePhoneNo(CommonFunction.checkNull(data.get(11)).toString());
            customerSaveVo.setTollfreeNo(CommonFunction.checkNull(data.get(12)).toString());
            customerSaveVo.setFaxNo(CommonFunction.checkNull(data.get(13)).toString());
            customerSaveVo.setLandMark(CommonFunction.checkNull(data.get(14)).toString());
            customerSaveVo.setNoYears(CommonFunction.checkNull(data.get(15)).toString());
            customerSaveVo.setNoMonths(CommonFunction.checkNull(data.get(16)).toString());
            customerSaveVo.setAddId(CommonFunction.checkNull(data.get(17)).toString());
            customerSaveVo.setCommunicationAddress(CommonFunction.checkNull(data.get(18)).toString());
            customerSaveVo.setTxtCountryCode(CommonFunction.checkNull(data.get(6)).toString());
            customerSaveVo.setTxtStateCode(CommonFunction.checkNull(data.get(7)).toString());
            customerSaveVo.setTxtDistCode(CommonFunction.checkNull(data.get(8)).toString());
            customerSaveVo.setAddDetails(CommonFunction.checkNull(data.get(22)).toString());
            customerSaveVo.setTahsil(CommonFunction.checkNull(data.get(23)).toString());
            customerSaveVo.setDistanceBranch(CommonFunction.checkNull(data.get(24)).toString());
            customerSaveVo.setGstIn(CommonFunction.checkNull(data.get(25)).toString());
            customerSaveVo.setStdNo(CommonFunction.checkNull(data.get(26)).toString());
            customerSaveVo.setRelation(CommonFunction.checkNull(data.get(27)).toString());
            list.add(customerSaveVo);
          }
        }
      }
      catch (Exception e) {
        e.printStackTrace();
      }
    }
    else {
      if (((pageStatus != null) && (pageStatus.equals("approve"))) || ((updateInMaker != null) && (updateInMaker.equals("updateInMaker"))) || ((statusCase != null) && (!statusCase.equals(""))))
      {
        if ((statusCase != null) && (statusCase.equalsIgnoreCase("Approved")) && ((updateInMaker == null) || (updateInMaker.equals(""))))
        {
          tableName = "com_address_m";
          if (CommonFunction.checkNull(source).trim().equalsIgnoreCase("ED"))
            tableName = "com_address_m_edit";
        }
        else
        {
          tableName = "com_address_m_temp";
        }
      }
      else
      {
        tableName = "com_address_m";
        if (CommonFunction.checkNull(source).trim().equalsIgnoreCase("ED")) {
          tableName = "com_address_m_edit";
        }
      }
      if (CommonFunction.checkNull(cuaStatus).equalsIgnoreCase("CUA"))
      {
        tableName = "com_address_m_temp";
      }

      try
      {
        String query = new StringBuilder().append("SELECT distinct a.ADDRESS_TYPE VALUE, a.BPTYPE, a.BPID, a.ADDRESS_LINE1, a.ADDRESS_LINE2, a.ADDRESS_LINE3, c.COUNTRY_ID,s.STATE_ID,d.DISTRICT_ID, a.PINCODE, a.PRIMARY_PHONE, a.ALTERNATE_PHONE, a.TOLLFREE_NUMBER, a.FAX, a.LANDMARK,a.NO_OF_YEARS, a.NO_OF_MONTHS,  a.ADDRESS_ID,a.COMMUNICATION_ADDRESS,c.COUNTRY_DESC,s.STATE_DESC,d.DISTRICT_DESC,a.ADDRESS_DETAIL,a.TAHSIL,a.BRANCH_DISTANCE,a.GST_NO,a.STD_NO,a.RELATIONSHIP_FLAG FROM ").append(tableName).append(" a ").append("join com_country_m c on(a.COUNTRY=c.COUNTRY_ID and a.ADDRESS_ID=").append(addId).append(") ").append("join com_state_m s on(a.STATE=s.STATE_ID) ").append("join com_district_m d on(a.DISTRICT=d.DISTRICT_ID)").toString();

        logger.info(new StringBuilder().append("In getCustomerAddressDetail() of CorpotateDAOImpl. Select Query  :  ").append(query).toString());
        ArrayList customerAddressDetail = ConnectionDAO.sqlSelect(query);
        logger.info(new StringBuilder().append("getCustomerAddressDetail ").append(customerAddressDetail.size()).toString());
        int size = customerAddressDetail.size();
        for (int i = 0; i < size; i++)
        {
          ArrayList data = (ArrayList)customerAddressDetail.get(i);
          if (data.size() > 0)
          {
            CustomerSaveVo customerSaveVo = new CustomerSaveVo();
            customerSaveVo.setAddr_type(CommonFunction.checkNull(data.get(0)).toString());
            customerSaveVo.setBp_type(CommonFunction.checkNull(data.get(1)).toString());
            customerSaveVo.setBp_id(CommonFunction.checkNull(data.get(2)).toString());
            customerSaveVo.setAddr1(CommonFunction.checkNull(data.get(3)).toString());
            customerSaveVo.setAddr2(CommonFunction.checkNull(data.get(4)).toString());
            customerSaveVo.setAddr3(CommonFunction.checkNull(data.get(5)).toString());
            customerSaveVo.setCountry(CommonFunction.checkNull(data.get(19)).toString());
            customerSaveVo.setState(CommonFunction.checkNull(data.get(20)).toString());
            customerSaveVo.setDist(CommonFunction.checkNull(data.get(21)).toString());
            customerSaveVo.setPincode(CommonFunction.checkNull(data.get(9)).toString());
            customerSaveVo.setPrimaryPhoneNo(CommonFunction.checkNull(data.get(10)).toString());
            customerSaveVo.setAlternatePhoneNo(CommonFunction.checkNull(data.get(11)).toString());
            customerSaveVo.setTollfreeNo(CommonFunction.checkNull(data.get(12)).toString());
            customerSaveVo.setFaxNo(CommonFunction.checkNull(data.get(13)).toString());
            customerSaveVo.setLandMark(CommonFunction.checkNull(data.get(14)).toString());
            customerSaveVo.setNoYears(CommonFunction.checkNull(data.get(15)).toString());
            customerSaveVo.setNoMonths(CommonFunction.checkNull(data.get(16)).toString());
            customerSaveVo.setAddId(CommonFunction.checkNull(data.get(17)).toString());
            if (CommonFunction.checkNull(data.get(18)).toString().equals(""))
              customerSaveVo.setCommunicationAddress("N");
            else
              customerSaveVo.setCommunicationAddress(CommonFunction.checkNull(data.get(18)).toString());
            customerSaveVo.setTxtCountryCode(CommonFunction.checkNull(data.get(6)).toString());
            customerSaveVo.setTxtStateCode(CommonFunction.checkNull(data.get(7)).toString());
            customerSaveVo.setTxtDistCode(CommonFunction.checkNull(data.get(8)).toString());
            customerSaveVo.setAddDetails(CommonFunction.checkNull(data.get(22)).toString());
            customerSaveVo.setTahsil(CommonFunction.checkNull(data.get(23)).toString());
            customerSaveVo.setDistanceBranch(CommonFunction.checkNull(data.get(24)).toString());
            customerSaveVo.setGstIn(CommonFunction.checkNull(data.get(25)).toString());
            customerSaveVo.setStdNo(CommonFunction.checkNull(data.get(26)).toString());
            customerSaveVo.setRelation(CommonFunction.checkNull(data.get(27)).toString());
            list.add(customerSaveVo);
          }
        }
      }
      catch (Exception e) {
        e.printStackTrace();
      }
    }
    return list;
  }

  public ArrayList<CustomerSaveVo> getIndReferenceDetail(String addId, Object pageStatus, String statusCase, String updateInMaker, String updateFlag, String pageStatuss, String source)
  {
    ArrayList list = new ArrayList();
    String tableName = "";

    if (((pageStatuss != null) && (pageStatuss.equals("fromLeads"))) || ((updateFlag != null) && (updateFlag.equals("updateFlag"))) || ((updateFlag != null) && (updateFlag.equals("notEdit"))))
    {
      logger.info("In Credit Processing , Customer Entry., getIndReferenceDetail()......");
      tableName = "cr_deal_reference_m";
    }
    else
    {
      logger.info("In GCD., getIndReferenceDetail()......");
      tableName = "com_reference_m";
      if (CommonFunction.checkNull(source).trim().equalsIgnoreCase("ED"))
        tableName = "com_reference_m_edit";
    }
    try {
      String query = "";
      query = new StringBuilder().append("select REF_ID, BPID, F_NAME, M_NAME, L_NAME, RELATIONSHIP,(select DESCRIPTION from generic_master\twhere generic_key='Relation_type'  and value=RELATIONSHIP) RELATIONSHIP_DESC, KNOWING_SINCE, MOBILE_NUMBER, LANDLINE_NUMBER,REF_ADDRESS,EMAIL_ID FROM ").append(tableName).append(" WHERE  REF_ID=").append(addId).toString();
      logger.info(new StringBuilder().append("getIndReferenceDetail query................. ").append(query).toString());
      ArrayList customerAddressDetail = ConnectionDAO.sqlSelect(query);
      logger.info(new StringBuilder().append("getIndReferenceDetail ").append(customerAddressDetail.size()).toString());
      for (int i = 0; i < customerAddressDetail.size(); i++)
      {
        ArrayList data = (ArrayList)customerAddressDetail.get(i);
        if (data.size() > 0)
        {
          CustomerSaveVo customerSaveVo = new CustomerSaveVo();
          customerSaveVo.setRefId(CommonFunction.checkNull(data.get(0)).toString());
          customerSaveVo.setBpId(CommonFunction.checkNull(data.get(1)).toString());
          customerSaveVo.setFirstName(CommonFunction.checkNull(data.get(2)).toString());
          customerSaveVo.setMiddleName(CommonFunction.checkNull(data.get(3)).toString());
          customerSaveVo.setLastName(CommonFunction.checkNull(data.get(4)).toString());
          customerSaveVo.setRelationshipS(CommonFunction.checkNull(data.get(5)).toString());
          customerSaveVo.setRelationShipDesc(CommonFunction.checkNull(data.get(6)).toString());
          customerSaveVo.setKnowingSince(CommonFunction.checkNull(data.get(7)).toString());
          customerSaveVo.setPrimaryRefMbNo(CommonFunction.checkNull(data.get(8)).toString());
          customerSaveVo.setAlternateRefPhNo(CommonFunction.checkNull(data.get(9)).toString());
          customerSaveVo.setAddRef(CommonFunction.checkNull(data.get(10)).toString());
          customerSaveVo.setInstitutionEmail(CommonFunction.checkNull(data.get(11)).toString());
          list.add(customerSaveVo);
        }
      }
    }
    catch (Exception e) {
      e.printStackTrace();
    }
    return list;
  }

  public ArrayList<CustomerSaveVo> getSarvaSurakshaDetail(String addId, Object pageStatus, String statusCase, String updateInMaker, String updateFlag, String pageStatuss, String source)
  {
    ArrayList list = new ArrayList();
    String tableName = "";

    if (((pageStatuss != null) && (pageStatuss.equals("fromLeads"))) || ((updateFlag != null) && (updateFlag.equals("updateFlag"))) || ((updateFlag != null) && (updateFlag.equals("notEdit"))))
    {
      logger.info("In Credit Processing , Customer Entry., getSarvaSurakshaDetail()......");
      tableName = "cr_deal_sarva_Suraksha_m";
    }
    else
    {
      logger.info("In GCD., getIndReferenceDetail()......");
      tableName = "com_sarva_Suraksha_m";
      if (CommonFunction.checkNull(source).trim().equalsIgnoreCase("ED"))
        tableName = "com_sarva_Suraksha_m_edit";
    }
    try {
      String query = "";
      query = new StringBuilder().append("select REF_ID, BPID,Nominee_NAME, RELATIONSHIP,(select DESCRIPTION from generic_master\twhere generic_key='Relation_type'  and value=RELATIONSHIP) RELATIONSHIP_DESC,date_FORMAT(NomineeDOB  ,'%d-%m-%Y') FROM ").append(tableName).append(" WHERE  REF_ID=").append(addId).toString();
      logger.info(new StringBuilder().append("getSarvaSurakshaDetail query................. ").append(query).toString());
      ArrayList customerAddressDetail = ConnectionDAO.sqlSelect(query);
      logger.info(new StringBuilder().append("getSarvaSurakshaDetail ").append(customerAddressDetail.size()).toString());
      for (int i = 0; i < customerAddressDetail.size(); i++)
      {
        ArrayList data = (ArrayList)customerAddressDetail.get(i);
        if (data.size() > 0)
        {
          CustomerSaveVo customerSaveVo = new CustomerSaveVo();
          customerSaveVo.setRefId(CommonFunction.checkNull(data.get(0)).toString());
          customerSaveVo.setBpId(CommonFunction.checkNull(data.get(1)).toString());
          customerSaveVo.setNominee(CommonFunction.checkNull(data.get(2)).toString());
          customerSaveVo.setRelationshipS(CommonFunction.checkNull(data.get(3)).toString());
          customerSaveVo.setRelationShipDesc(CommonFunction.checkNull(data.get(4)).toString());
          customerSaveVo.setNomineeDOB(CommonFunction.checkNull(data.get(5)).toString());
          list.add(customerSaveVo);
        }
      }
    }
    catch (Exception e) {
      e.printStackTrace();
    }
    return list;
  }

  public ArrayList<StakeHolderVo> getStackHolderDetail(String stackHolderId, Object pageStatus, String statusCase, String updateInMaker, String updateFlag, String pageStatuss, String cuaStatus, String source)
  {
    ArrayList list = new ArrayList();
    String tableName = "";

    if (((pageStatuss != null) && (pageStatuss.equals("fromLeads"))) || ((updateFlag != null) && (updateFlag.equals("updateFlag"))) || ((updateFlag != null) && (updateFlag.equals("notEdit"))))
    {
      logger.info("In Credit Processing , Customer Entry..,getStackHolderDetail()......");
      tableName = "cr_deal_customer_stakeholder_m";
      try {
        String query = new StringBuilder().append("SELECT cs.STAKEHOLDER_ID, cs.STAKEHOLDER_SALUTATION, cs.STAKEHOLDER_NAME, cs.STAKEHOLDER_TYPE, cs.STAKEHOLDER_PERCENTAGE, DATE_FORMAT(cs.STAKEHOLDER_DOB,'").append(this.dateFormat).append("'), cs.STAKEHOLDER_EXPERIENCE, ").append("cs.STAKEHOLDER_DIN, cs.STAKEHOLDER_IDENTIFICATION_NO,cs.STAKEHOLDER_POSITION, ").append("DATE_FORMAT(cs.STAKEHOLDER_JOINING_DATE,'").append(this.dateFormat).append("'), cs.ELIGIBLE_FOR_COMPUTATION,").append(" cs.STAKEHOLDER_PRIMARY_PHONE, cs.STAKEHOLDER_ALTERNATE_PHONE, cs.STAKEHOLDER_PRIMARY_EMAIL,").append(" cs.STAKEHOLDER_ALTERNATE_EMAIL, cs.STAKEHOLDER_WEBSITE,cs.STAKEHOLDER_PAN,cs.ADDITIONAL_ID1,cs.ADDITIONAL_ID2 FROM cr_deal_customer_stakeholder_m cs").append(" WHERE cs.STAKEHOLDER_ID=").append(stackHolderId).append("").toString();

        logger.info(new StringBuilder().append("Query for getting stack holder list  :   ").append(query).toString());
        ArrayList stackHolderDetail = ConnectionDAO.sqlSelect(query);
        logger.info(new StringBuilder().append("getStackHolderDetail ").append(stackHolderDetail.size()).toString());

        String custQuery = new StringBuilder().append("select CUSTOMER_ID from cr_deal_customer_stakeholder_m where STAKEHOLDER_ID=").append(stackHolderId).toString();
        logger.info(new StringBuilder().append("Query for getting customerId : ").append(custQuery).toString());
        String cid = ConnectionDAO.singleReturn(custQuery);
        String perQuery = new StringBuilder().append("select sum(ifnull(STAKEHOLDER_PERCENTAGE,0)) from cr_deal_customer_stakeholder_m where CUSTOMER_ID=").append(cid).toString();
        logger.info(new StringBuilder().append("Query for getting total percentage : ").append(perQuery).toString());
        String percentage = ConnectionDAO.singleReturn(perQuery);

        for (int i = 0; i < stackHolderDetail.size(); i++)
        {
          ArrayList data = (ArrayList)stackHolderDetail.get(i);
          if (data.size() > 0)
          {
            StakeHolderVo stakeHolderVo = new StakeHolderVo();
            stakeHolderVo.setHolderid(CommonFunction.checkNull(data.get(0)).toString());
            stakeHolderVo.setSex(CommonFunction.checkNull(data.get(1)).toString());
            stakeHolderVo.setHolderName(CommonFunction.checkNull(data.get(2)).toString());
            stakeHolderVo.setHolderType(CommonFunction.checkNull(data.get(3)).toString());

            if (!CommonFunction.checkNull(data.get(4)).toString().equalsIgnoreCase(""))
            {
              Number reconNum = this.myFormatter.parse(CommonFunction.checkNull(data.get(4)).toString());
              stakeHolderVo.setHoldingPerc(this.myFormatter.format(reconNum));
            }

            stakeHolderVo.setDob(CommonFunction.checkNull(data.get(5)).toString());
            stakeHolderVo.setTotalExp(CommonFunction.checkNull(data.get(6)).toString());
            stakeHolderVo.setDinNo(CommonFunction.checkNull(data.get(7)).toString());

            stakeHolderVo.setIdNo(CommonFunction.checkNull(data.get(8)).toString());
            stakeHolderVo.setPosition(CommonFunction.checkNull(data.get(9)).toString());
            stakeHolderVo.setDoj(CommonFunction.checkNull(data.get(10)).toString());
            stakeHolderVo.setCompute(CommonFunction.checkNull(data.get(11)).toString());
            stakeHolderVo.setPrimaryPhone(CommonFunction.checkNull(data.get(12)).toString());
            stakeHolderVo.setAlternatePhone(CommonFunction.checkNull(data.get(13)).toString());
            stakeHolderVo.setPrimaryEmail(CommonFunction.checkNull(data.get(14)).toString());
            stakeHolderVo.setAlternateEmail(CommonFunction.checkNull(data.get(15)).toString());
            stakeHolderVo.setWebsite(CommonFunction.checkNull(data.get(16)).toString());
            stakeHolderVo.setMgmtPAN(CommonFunction.checkNull(data.get(17)).toString());
            stakeHolderVo.setAddId1(CommonFunction.checkNull(data.get(18)).toString());
            stakeHolderVo.setAddId2(CommonFunction.checkNull(data.get(19)).toString());
            stakeHolderVo.setPercentage(percentage);
            list.add(stakeHolderVo);
          }
        }
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
    else
    {
      logger.info("In GCD.,getStackHolderDetail()......");
      if (((pageStatus != null) && (pageStatus.equals("approve"))) || ((updateInMaker != null) && (updateInMaker.equals("updateInMaker"))) || ((statusCase != null) && (!statusCase.equals(""))))
      {
        if ((statusCase != null) && (statusCase.equalsIgnoreCase("Approved")) && ((updateInMaker == null) || (updateInMaker.equals(""))))
        {
          tableName = "customer_stakeholder_m";
          if (CommonFunction.checkNull(source).trim().equalsIgnoreCase("ED"))
            tableName = "customer_stakeholder_m_edit";
        }
        else
        {
          tableName = "customer_stakeholder_m_temp";
        }

      }
      else
      {
        tableName = "customer_stakeholder_m";
        if (CommonFunction.checkNull(source).trim().equalsIgnoreCase("ED")) {
          tableName = "customer_stakeholder_m_edit";
        }
      }
      if (CommonFunction.checkNull(cuaStatus).equalsIgnoreCase("CUA"))
      {
        tableName = "customer_stakeholder_m_temp";
      }

      try
      {
        String query = new StringBuilder().append("SELECT cs.STAKEHOLDER_ID, cs.STAKEHOLDER_SALUTATION, cs.STAKEHOLDER_NAME, cs.STAKEHOLDER_TYPE, cs.STAKEHOLDER_PERCENTAGE, DATE_FORMAT(cs.STAKEHOLDER_DOB,'").append(this.dateFormat).append("'), cs.STAKEHOLDER_EXPERIENCE, cs.STAKEHOLDER_DIN, cs.STAKEHOLDER_IDENTIFICATION_NO, cs.STAKEHOLDER_POSITION, DATE_FORMAT(cs.STAKEHOLDER_JOINING_DATE,'").append(this.dateFormat).append("'), cs.ELIGIBLE_FOR_COMPUTATION, cs.STAKEHOLDER_PRIMARY_PHONE, cs.STAKEHOLDER_ALTERNATE_PHONE, cs.STAKEHOLDER_PRIMARY_EMAIL, cs.STAKEHOLDER_ALTERNATE_EMAIL, cs.STAKEHOLDER_WEBSITE,cs.STAKEHOLDER_PAN,cs.ADDITIONAL_ID1,cs.ADDITIONAL_ID2 FROM ").append(tableName).append(" cs WHERE cs.STAKEHOLDER_ID=").append(stackHolderId).toString();

        logger.info(new StringBuilder().append("Query for getting stack holder list  :   ").append(query).toString());
        ArrayList stackHolderDetail = ConnectionDAO.sqlSelect(query);

        String custQuery = new StringBuilder().append("select CUSTOMER_ID from ").append(tableName).append("  where STAKEHOLDER_ID=").append(stackHolderId).toString();
        logger.info(new StringBuilder().append("Query for getting customerId : ").append(custQuery).toString());
        String cid = ConnectionDAO.singleReturn(custQuery);
        String perQuery = new StringBuilder().append("select sum(ifnull(STAKEHOLDER_PERCENTAGE,0)) from ").append(tableName).append(" where CUSTOMER_ID=").append(cid).toString();
        logger.info(new StringBuilder().append("Query for getting total percentage : ").append(perQuery).toString());
        String percentage = ConnectionDAO.singleReturn(perQuery);

        logger.info(new StringBuilder().append("getStackHolderDetail ").append(stackHolderDetail.size()).toString());
        for (int i = 0; i < stackHolderDetail.size(); i++) {
          ArrayList data = (ArrayList)stackHolderDetail.get(i);
          if (data.size() > 0)
          {
            StakeHolderVo stakeHolderVo = new StakeHolderVo();
            stakeHolderVo.setHolderid(CommonFunction.checkNull(data.get(0)).toString());
            stakeHolderVo.setSex(CommonFunction.checkNull(data.get(1)).toString());
            stakeHolderVo.setHolderName(CommonFunction.checkNull(data.get(2)).toString());
            stakeHolderVo.setHolderType(CommonFunction.checkNull(data.get(3)).toString());

            if (!CommonFunction.checkNull(data.get(4)).toString().equalsIgnoreCase(""))
            {
              Number reconNum = this.myFormatter.parse(CommonFunction.checkNull(data.get(4)).toString());
              stakeHolderVo.setHoldingPerc(this.myFormatter.format(reconNum));
            }

            stakeHolderVo.setDob(CommonFunction.checkNull(data.get(5)).toString());
            stakeHolderVo.setTotalExp(CommonFunction.checkNull(data.get(6)).toString());
            stakeHolderVo.setDinNo(CommonFunction.checkNull(data.get(7)).toString());
            stakeHolderVo.setIdNo(CommonFunction.checkNull(data.get(8)).toString());
            stakeHolderVo.setPosition(CommonFunction.checkNull(data.get(9)).toString());
            stakeHolderVo.setDoj(CommonFunction.checkNull(data.get(10)).toString());
            stakeHolderVo.setCompute(CommonFunction.checkNull(data.get(11)).toString());
            stakeHolderVo.setPrimaryPhone(CommonFunction.checkNull(data.get(12)).toString());
            stakeHolderVo.setAlternatePhone(CommonFunction.checkNull(data.get(13)).toString());
            stakeHolderVo.setPrimaryEmail(CommonFunction.checkNull(data.get(14)).toString());
            stakeHolderVo.setAlternateEmail(CommonFunction.checkNull(data.get(15)).toString());
            stakeHolderVo.setWebsite(CommonFunction.checkNull(data.get(16)).toString());
            stakeHolderVo.setMgmtPAN(CommonFunction.checkNull(data.get(17)).toString());
            stakeHolderVo.setAddId1(CommonFunction.checkNull(data.get(18)).toString());
            stakeHolderVo.setAddId2(CommonFunction.checkNull(data.get(19)).toString());
            stakeHolderVo.setPercentage(percentage);
            list.add(stakeHolderVo);
          }
        }
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
    return list;
  }

  public ArrayList<CreditRatingVo> getCreditRatingDetail(String creditRatingId, Object pageStatus, String statusCase, String updateInMaker, String updateFlag, String pageStatuss, String source)
  {
    ArrayList list = new ArrayList();
    String tableName = "";

    if (((pageStatuss != null) && (pageStatuss.equals("fromLeads"))) || ((updateFlag != null) && (updateFlag.equals("updateFlag"))) || ((updateFlag != null) && (updateFlag.equals("notEdit"))))
    {
      logger.info("In Credit Processing , Customer Entry..,getCreditRatingDetail()......");
      tableName = "cr_deal_customer_rating";
      try {
        String query = new StringBuilder().append("SELECT a.AGENCY_CODE, DATE_FORMAT(r.RATING_DATE,'").append(this.dateFormat).append("'), r.CUSTOMER_RATING, r.CUSTOMER_RATING_ID ").append("from ").append(tableName).append(" r, com_agency_m a ").append("where r.RATING_AGENCY=a.AGENCY_CODE and r.CUSTOMER_RATING_ID=").append(creditRatingId).toString();

        logger.info(new StringBuilder().append("In getCreditRatingDetail() Query :  ").append(query).toString());
        ArrayList creditRatingDetail = ConnectionDAO.sqlSelect(query);
        logger.info(new StringBuilder().append("getCreditRatingDetail ").append(creditRatingDetail.size()).toString());
        for (int i = 0; i < creditRatingDetail.size(); i++) {
          ArrayList data = (ArrayList)creditRatingDetail.get(i);
          if (data.size() > 0)
          {
            CreditRatingVo creditRatingVo = new CreditRatingVo();
            creditRatingVo.setInstitute(CommonFunction.checkNull(data.get(0)).toString());
            creditRatingVo.setCreditDate(CommonFunction.checkNull(data.get(1)).toString());
            creditRatingVo.setRating(CommonFunction.checkNull(data.get(2)).toString());
            creditRatingVo.setcRatingId(CommonFunction.checkNull(data.get(3)).toString());
            list.add(creditRatingVo);
          }
        }
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
    else
    {
      if (((pageStatus != null) && (pageStatus.equals("approve"))) || ((updateInMaker != null) && (updateInMaker.equals("updateInMaker"))) || ((statusCase != null) && (!statusCase.equals(""))))
      {
        if ((statusCase != null) && (statusCase.equalsIgnoreCase("Approved")) && ((updateInMaker == null) || (updateInMaker.equals(""))))
        {
          tableName = "customer_rating_m";
          if (CommonFunction.checkNull(source).trim().equalsIgnoreCase("ED"))
            tableName = "customer_rating_m_edit";
        }
        else
        {
          tableName = "customer_rating_m_temp";
        }

      }
      else
      {
        tableName = "customer_rating_m";
        if (CommonFunction.checkNull(source).trim().equalsIgnoreCase("ED"))
          tableName = "customer_rating_m_edit";
      }
      try
      {
        String query = new StringBuilder().append("SELECT a.AGENCY_CODE, DATE_FORMAT(r.RATING_DATE,'").append(this.dateFormat).append("'), r.CUSTOMER_RATING, r.CUSTOMER_RATING_ID ").append("from ").append(tableName).append(" r, com_agency_m a ").append("where r.RATING_AGENCY=a.AGENCY_CODE and r.CUSTOMER_RATING_ID=").append(creditRatingId).toString();

        logger.info(new StringBuilder().append("In getCreditRatingDetail() Query :  ").append(query).toString());
        ArrayList creditRatingDetail = ConnectionDAO.sqlSelect(query);
        logger.info(new StringBuilder().append("getCreditRatingDetail ").append(creditRatingDetail.size()).toString());
        for (int i = 0; i < creditRatingDetail.size(); i++) {
          ArrayList data = (ArrayList)creditRatingDetail.get(i);
          if (data.size() > 0)
          {
            CreditRatingVo creditRatingVo = new CreditRatingVo();
            creditRatingVo.setInstitute(CommonFunction.checkNull(data.get(0)).toString());
            creditRatingVo.setCreditDate(CommonFunction.checkNull(data.get(1)).toString());
            creditRatingVo.setRating(CommonFunction.checkNull(data.get(2)).toString());
            creditRatingVo.setcRatingId(CommonFunction.checkNull(data.get(3)).toString());
            list.add(creditRatingVo);
          }
        }
      } catch (Exception e) {
        e.printStackTrace();
      }

    }

    return list;
  }

  public boolean checkGoForApproval(String cId)
  {
    boolean flag = true;
    try
    {
      String query = new StringBuilder().append("Select CUSTOMER_ID from gcd_customer_m_temp where customer_id=").append(cId).toString();
      logger.info("in checkGoForApproval() of CorpotateDAOImpl.");
      String custId = ConnectionDAO.singleReturn(query);
      if ((custId != null) && 
        (custId.length() > 0))
      {
        logger.info("record present in temp table.....................................");
        flag = false;
      }
    } catch (Exception e) {
      e.printStackTrace();
    }logger.info(new StringBuilder().append("checkGoForApproval.............................................. ").append(flag).toString());
    return flag;
  }

  public String insertAllIntoTempFromMainTable(String id, String cusType)
  {
    logger.info("in insertAllIntoTempFromMainTable() of CorpotateDAOImpl");
    int statusProc = 0;
    int custID = Integer.parseInt(id.trim());
    String type = cusType.trim();
    String s1 = "";
    String s2 = "";
    String status = "";
    ArrayList in = new ArrayList();
    ArrayList out = new ArrayList();
    ArrayList outMessages = new ArrayList();
    try
    {
      logger.info("in procedure Gcd_Tmp_Insert------>");
      in.add(Integer.valueOf(custID));
      in.add(type);
      out.add(s1);
      out.add(s2);
      logger.info(new StringBuilder().append("Gcd_Tmp_Insert(").append(in.toString()).append(",").append(out.toString()).append(")").toString());
      outMessages = (ArrayList)ConnectionDAO.callSP("Gcd_Tmp_Insert", in, out);
      s1 = CommonFunction.checkNull(outMessages.get(0));
      s2 = CommonFunction.checkNull(outMessages.get(1));
      if (s1.equalsIgnoreCase("S"))
      {
        status = s1;
      }
      else
      {
        status = s2;
      }
      logger.info(new StringBuilder().append("s1::::::::::::: ").append(s1).toString());
      logger.info(new StringBuilder().append("s2::::::::::::::::: ").append(s2).toString());
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }return status;
  }

  public ArrayList<Object> getRoleListCorp(String dealId)
  {
    logger.info("id in actiongetRoleList");
    ArrayList list = new ArrayList();
    logger.info(new StringBuilder().append("In , credit processing , getRoleList() *************************************    :").append(dealId).toString());
    try
    {
      String query = new StringBuilder().append(" SELECT DEAL_CUSTOMER_ROLE_ID, DEAL_CUSTOMER_TYPE,DEAL_CUSTOMER_ID,DEAL_EXISTING_CUSTOMER, CUSTOMER_NAME,m.description,c.GUARANTEE_AMOUNT from cr_deal_customer_role c  left join generic_master m on c.DEAL_CUSTOMER_ROLE_TYPE=m.value and GENERIC_KEY='CUST_ROLE' left join cr_deal_customer_m d on c.DEAL_CUSTOMER_ID=CUSTOMER_ID where DEAL_ID='").append(dealId).append("'and DEAL_CUSTOMER_TYPE='C' ").toString();

      logger.info(new StringBuilder().append("getRoleListCorp:Query ").append(query).toString());
      CreditProcessingCustomerEntryVo vo = null;
      ArrayList roleList = ConnectionDAO.sqlSelect(query);
      logger.info(new StringBuilder().append("getRoleListCorp ").append(roleList.size()).toString());
      for (int i = 0; i < roleList.size(); i++)
      {
        ArrayList data = (ArrayList)roleList.get(i);
        if (data.size() > 0)
        {
          vo = new CreditProcessingCustomerEntryVo();
          vo.setRoleId(CommonFunction.checkNull(data.get(0)).toString());
          vo.setApplicantType(CommonFunction.checkNull(data.get(5)).toString());
          if (CommonFunction.checkNull(data.get(1)).toString().equalsIgnoreCase("C"))
          {
            vo.setApplicantCategory("CORPORATE");
          }
          else if (CommonFunction.checkNull(data.get(1)).toString().equalsIgnoreCase("I"))
          {
            vo.setApplicantCategory("INDIVIDUAL");
          }

          if (CommonFunction.checkNull(data.get(3)).toString().equalsIgnoreCase("N"))
          {
            vo.setExistingCustomer("NO");
          }
          else if (CommonFunction.checkNull(data.get(3)).toString().equalsIgnoreCase("Y"))
          {
            vo.setExistingCustomer("YES");
          }

          vo.setCustomerName(CommonFunction.checkNull(data.get(4)).toString());
          vo.setCustomerId(CommonFunction.checkNull(data.get(2)).toString());

          if (!CommonFunction.checkNull(data.get(6)).toString().equalsIgnoreCase(""))
          {
            Number reconNum = this.myFormatter.parse(CommonFunction.checkNull(data.get(6)).trim());
            vo.setGuaranteeAmount(this.myFormatter.format(reconNum));
          }

          vo.setFlagForUpdate("updateFlag");
          list.add(vo);
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    return list;
  }

  public ArrayList<Object> getRoleList(String dealId) {

		logger.info("id in actiongetRoleList");
		ArrayList<Object> list=new ArrayList<Object>();
		logger.info("In , credit processing , getRoleList() *************************************    :"+dealId);
		try{
//		String query=" SELECT DEAL_CUSTOMER_ROLE_ID,DEAL_CUSTOMER_ROLE_TYPE, DEAL_CUSTOMER_TYPE,DEAL_CUSTOMER_ID,DEAL_EXISTING_CUSTOMER,"+
//					" CUSTOMER_FNAME,m.description from cr_deal_customer_role c "+
//					" left join generic_master m on c.DEAL_CUSTOMER_ROLE_TYPE=m.value and GENERIC_KEY='CUST_ROLE'"+
//					" left join cr_deal_customer_m d on c.DEAL_CUSTOMER_ID=CUSTOMER_ID"+
//					" where DEAL_ID="+dealId;
			String scoreCardName =ConnectionDAO.singleReturn("select parameter_value from Parameter_mst where parameter_key ='CRIF_SCORE_TYPE'");
		
		String query=" SELECT DEAL_CUSTOMER_ROLE_ID, DEAL_CUSTOMER_TYPE,DEAL_CUSTOMER_ID,DEAL_EXISTING_CUSTOMER,"+
		//" CUSTOMER_NAME,m.description,c.GUARANTEE_AMOUNT, d.cibil_done,d.cibil_date, d.cibil_id,case d.cibil_done when 'Y' then 'CIBIL DONE' else 'CIBIL NOT DONE' end as CIBIL_STATUS,DEAL_ID  from cr_deal_customer_role c "+
		" CUSTOMER_NAME,m.description,c.GUARANTEE_AMOUNT, d.cibil_done,d.cibil_date, d.cibil_id,case d.cibil_done when 'Y' then 'BUREAU DONE' else 'BUREAU NOT DONE' end as CIBIL_STATUS,DEAL_ID,IFNULL(CM.SCORE,''),ifnull(cm.CRIF_HIGH_MARK_SCORE,''),ifnull(cm.EXPERIAN_SCORE,'')    from cr_deal_customer_role c "+			
		" left join generic_master m on c.DEAL_CUSTOMER_ROLE_TYPE=m.value and GENERIC_KEY='CUST_ROLE'"+
		" left join cr_deal_customer_m d on c.DEAL_CUSTOMER_ID=CUSTOMER_ID"+
		" LEFT JOIN CR_CIBIL_SCORE_SEGMENT CM ON CM.CIBIL_ID=D.CIBIL_ID "+
		" where DEAL_ID='"+dealId+"' and ( SCORE_CARD_NAME ='"+scoreCardName+"' or SCORE_CARD_NAME is null)  ";
		
		logger.info("getRoleList:Query "+query);	
		
		CreditProcessingCustomerEntryVo vo=null;
		ArrayList roleList = ConnectionDAO.sqlSelect(query);
		logger.info("getRoleList "+roleList.size());
      for (int i = 0; i < roleList.size(); i++)
      {
        ArrayList data = (ArrayList)roleList.get(i);
        if (data.size() > 0)
        {
          vo = new CreditProcessingCustomerEntryVo();
          vo.setRoleId(CommonFunction.checkNull(data.get(0)).toString());
          vo.setApplicantType(CommonFunction.checkNull(data.get(5)).toString());
          if (CommonFunction.checkNull(data.get(1)).toString().equalsIgnoreCase("C"))
          {
            vo.setApplicantCategory("CORPORATE");
          }
          else if (CommonFunction.checkNull(data.get(1)).toString().equalsIgnoreCase("I"))
          {
            vo.setApplicantCategory("INDIVIDUAL");
          }

          if (CommonFunction.checkNull(data.get(3)).toString().equalsIgnoreCase("N"))
          {
            vo.setExistingCustomer("NO");
          }
          else if (CommonFunction.checkNull(data.get(3)).toString().equalsIgnoreCase("Y"))
          {
            vo.setExistingCustomer("YES");
          }

          vo.setCustomerName(CommonFunction.checkNull(data.get(4)).toString());
          vo.setCustomerId(CommonFunction.checkNull(data.get(2)).toString());

          if (!CommonFunction.checkNull(data.get(6)).toString().equalsIgnoreCase(""))
          {
            Number reconNum = this.myFormatter.parse(CommonFunction.checkNull(data.get(6)).trim());
            vo.setGuaranteeAmount(this.myFormatter.format(reconNum));
          }

          vo.setFlagForUpdate("updateFlag");
          vo.setCibilDone(CommonFunction.checkNull(data.get(7)).toString());
          vo.setCibilDate(CommonFunction.checkNull(data.get(8)).toString());
          vo.setLbxCibilId(CommonFunction.checkNull(data.get(9)).toString());
          vo.setCibilResponse(CommonFunction.checkNull(data.get(10)).toString());
          vo.setDealId(CommonFunction.checkNull(data.get(11)).toString());
		  vo.setCibilScore((CommonFunction.checkNull(data.get(12)).toString()));
		  vo.setCrifHighMarkScore((CommonFunction.checkNull(data.get(13)).toString()));
		  vo.setExperianScore((CommonFunction.checkNull(data.get(14)).toString()));
          list.add(vo);
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    return list;
  }

  public ArrayList<Object> getRoleList(String dealId, String leadId)
  {
    logger.info("id in actiongetRoleList");
    ArrayList list = new ArrayList();
    logger.info(new StringBuilder().append("In , credit processing , getRoleList() *************************************    :").append(dealId).toString());
    try
    {
      String query = new StringBuilder().append(" SELECT DEAL_CUSTOMER_ROLE_ID, DEAL_CUSTOMER_TYPE,DEAL_CUSTOMER_ID,DEAL_EXISTING_CUSTOMER, CUSTOMER_NAME,m.description,c.GUARANTEE_AMOUNT from cr_deal_customer_role c  left join generic_master m on c.DEAL_CUSTOMER_ROLE_TYPE=m.value and GENERIC_KEY='CUST_ROLE' left join cr_deal_customer_m d on c.DEAL_CUSTOMER_ID=CUSTOMER_ID where DEAL_ID='").append(dealId).append("'").toString();

      logger.info(new StringBuilder().append("getRoleList:Query ").append(query).toString());
      CreditProcessingCustomerEntryVo vo = null;
      ArrayList roleList = ConnectionDAO.sqlSelect(query);
      logger.info(new StringBuilder().append("getRoleList ").append(roleList.size()).toString());
      for (int i = 0; i < roleList.size(); i++)
      {
        ArrayList data = (ArrayList)roleList.get(i);
        if (data.size() > 0)
        {
          vo = new CreditProcessingCustomerEntryVo();
          vo.setRoleId(CommonFunction.checkNull(data.get(0)).toString());
          vo.setApplicantType(CommonFunction.checkNull(data.get(5)).toString());
          if (CommonFunction.checkNull(data.get(1)).toString().equalsIgnoreCase("C"))
          {
            vo.setApplicantCategory("CORPORATE");
          }
          else if (CommonFunction.checkNull(data.get(1)).toString().equalsIgnoreCase("I"))
          {
            vo.setApplicantCategory("INDIVIDUAL");
          }

          if (CommonFunction.checkNull(data.get(3)).toString().equalsIgnoreCase("N"))
          {
            vo.setExistingCustomer("NO");
          }
          else if (CommonFunction.checkNull(data.get(3)).toString().equalsIgnoreCase("Y"))
          {
            vo.setExistingCustomer("YES");
          }

          vo.setCustomerName(CommonFunction.checkNull(data.get(4)).toString());
          vo.setCustomerId(CommonFunction.checkNull(data.get(2)).toString());

          if (!CommonFunction.checkNull(data.get(6)).toString().equalsIgnoreCase(""))
          {
            Number reconNum = this.myFormatter.parse(CommonFunction.checkNull(data.get(6)).trim());
            vo.setGuaranteeAmount(this.myFormatter.format(reconNum));
          }

          vo.setFlagForUpdate("updateFlag");
          list.add(vo);
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    return list;
  }

  public boolean deleteroleList(String[] roleId, String[] applType, String dealId)
  {
    logger.info("deleteroleList() OF CorpotateDAOImpl");
    boolean status = false;
    boolean status1 = false;
    try
    {
      ArrayList qryList = new ArrayList();
      ArrayList qryList1 = new ArrayList();
      for (int k = 0; k < roleId.length; k++)
      {
        String query2 = "delete from cr_deal_customer_role where DEAL_CUSTOMER_ROLE_ID=?";
        PrepStmtObject insertPrepStmtObject = new PrepStmtObject();
        if (CommonFunction.checkNull(roleId[k]).trim().equalsIgnoreCase(""))
          insertPrepStmtObject.addNull();
        else
          insertPrepStmtObject.addString(CommonFunction.checkNull(roleId[k]).trim());
        insertPrepStmtObject.setSql(query2.toString());

        qryList.add(insertPrepStmtObject);

        String q1 = new StringBuilder().append("select DEAL_CUSTOMER_ID from cr_deal_customer_role where DEAL_CUSTOMER_ROLE_TYPE='PRAPPL' and DEAL_CUSTOMER_ROLE_ID=").append(StringEscapeUtils.escapeSql(CommonFunction.checkNull(roleId[k])).trim()).toString();

        String id = CommonFunction.checkNull(ConnectionDAO.singleReturn(q1));

        if (!id.equalsIgnoreCase(""))
        {
          String q2 = new StringBuilder().append("select DEAL_CUSTOMER_ID from cr_deal_dtl where DEAL_CUSTOMER_ID=").append(id).toString();

          String idInDeal = CommonFunction.checkNull(ConnectionDAO.singleReturn(q2));

          if ((idInDeal != null) && (!idInDeal.equalsIgnoreCase("")))
          {
            String query1 = "update cr_deal_dtl set DEAL_CUSTOMER_ID=?,DEAL_CUSTOMER_TYPE=?,DEAL_EXISTING_CUSTOMER=? WHERE DEAL_ID=?";
            PrepStmtObject insertPrepStmtObject2 = new PrepStmtObject();
            insertPrepStmtObject2.addNull();
            insertPrepStmtObject2.addNull();
            insertPrepStmtObject2.addNull();
            if (CommonFunction.checkNull(dealId).trim().equalsIgnoreCase(""))
              insertPrepStmtObject2.addNull();
            else
              insertPrepStmtObject2.addString(CommonFunction.checkNull(dealId).trim());
            insertPrepStmtObject2.setSql(query1.toString());
            logger.info(new StringBuilder().append("update this query  :  ").append(insertPrepStmtObject2.printQuery()).toString());
            qryList.add(insertPrepStmtObject2);
          }
        }

        String dedupeqry = new StringBuilder().append("select DEAL_CUSTOMER_ID from cr_deal_customer_role where  DEAL_CUSTOMER_ROLE_ID=").append(StringEscapeUtils.escapeSql(CommonFunction.checkNull(roleId[k])).trim()).toString();
        String custid = CommonFunction.checkNull(ConnectionDAO.singleReturn(dedupeqry));

        String dedupeqry2 = "delete from cr_customer_Search_dtl where DEAL_ID=? and customer_id=?";
        PrepStmtObject insertPrepStmtObject1 = new PrepStmtObject();
        if (CommonFunction.checkNull(dealId).trim().equalsIgnoreCase(""))
          insertPrepStmtObject1.addNull();
        else {
          insertPrepStmtObject1.addString(CommonFunction.checkNull(dealId).trim());
        }
        if (CommonFunction.checkNull(custid).trim().equalsIgnoreCase(""))
          insertPrepStmtObject1.addNull();
        else
          insertPrepStmtObject1.addString(CommonFunction.checkNull(custid).trim());
        insertPrepStmtObject1.setSql(dedupeqry2.toString());

        qryList1.add(insertPrepStmtObject1);
        status1 = ConnectionDAO.sqlInsUpdDeletePrepStmt(qryList1);

        logger.info(new StringBuilder().append("query for dedlete record from cr_customer_seacrh_dtl is   ::  ").append(insertPrepStmtObject1.printQuery()).toString());
        logger.info(new StringBuilder().append("update this query  :  ").append(status1).toString());
      }

      status = ConnectionDAO.sqlInsUpdDeletePrepStmt(qryList);
      logger.info(new StringBuilder().append("Status of Updation is   ::  ").append(status).toString());
    }
    catch (Exception e) {
      e.printStackTrace();
    }return status;
  }

  public String approve(String id, String cusType, String mvmtBy, String pageInfo, String dealId)
  {
    logger.info("in approve() of CorpotateDAOImpl");
    String s1 = "";
    String s2 = "";
    ArrayList in = new ArrayList();
    ArrayList out = new ArrayList();
    ArrayList outMessages = new ArrayList();
    try
    {
      in.add(id);
      in.add(cusType);
      in.add(mvmtBy);
      out.add(s1);
      out.add(s2);
      logger.info(new StringBuilder().append("Gcd_Approve(").append(in.toString()).append("+").append(out.toString()).append(")").toString());
      outMessages = (ArrayList)ConnectionDAO.callSP("Gcd_Approve", in, out);
      s1 = CommonFunction.checkNull(outMessages.get(0));
      s2 = CommonFunction.checkNull(outMessages.get(1));
      logger.info(new StringBuilder().append("out parameter in Apporve S1  :  ").append(s1).toString());
      logger.info(new StringBuilder().append("out parameter in Apporve S2  :  ").append(s2).toString());
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }

    return s2;
  }

  public String checkCustomerStatus(int Id, String custType)
  {
    String addCheck = "";
    String stackCheck = "";
    String custStatus = "";
    logger.info(new StringBuilder().append("deal Id in checkCustomerStatus()................. ").append(Id).toString());
    logger.info(new StringBuilder().append("custType in checkCustomerStatus()................. ").append(custType).toString());
    addCheck = ConnectionDAO.singleReturnFromMultipleRecords(new StringBuilder().append("Select COMMUNICATION_ADDRESS from cr_deal_address_m where BPID=").append(Id).toString());
    if ((custType != null) && (custType.equalsIgnoreCase("C")))
    {
      logger.info("corporate...................................... ");
      stackCheck = ConnectionDAO.singleReturn(new StringBuilder().append("Select STAKEHOLDER_ID from cr_deal_customer_stakeholder_m where CUSTOMER_ID=").append(Id).toString());
      if ((addCheck != null) && (stackCheck != null))
      {
        if ((addCheck.equals("Y")) && (stackCheck != null))
        {
          custStatus = "Approved";
        }
        else
        {
          custStatus = "UnApproved";
        }

      }
      else
      {
        custStatus = "UnApproved";
      }
    }
    else
    {
      logger.info("Individual...................................... ");
      if (addCheck != null)
      {
        if (addCheck.equals("Y"))
        {
          custStatus = "Approved";
        }
        else
        {
          custStatus = "UnApproved";
        }
      }
      else
      {
        custStatus = "UnApproved";
      }
    }
    logger.info(new StringBuilder().append("custStatus............................... ").append(custStatus).toString());
    return custStatus;
  }

  public ArrayList<Object> getIndividualContitutionList()
  {
    logger.info("in getIndividualContitutionList() of CorpotateDAOImpl");
    ArrayList list = new ArrayList();
    try
    {
      String query = "SELECT VALUE,DESCRIPTION FROM  generic_master WHERE GENERIC_KEY='CUST_CONSTITUTION' and PARENT_VALUE='INDV' and rec_status ='A'";
      logger.info(new StringBuilder().append("in getIndividualContitutionList() of CorpotateDAOImpl  Query : ").append(query).toString());
      ConstitutionVO vo = null;
      ArrayList source = ConnectionDAO.sqlSelect(query);
      logger.info(new StringBuilder().append("Size : ").append(source.size()).toString());
      int size = source.size();
      for (int i = 0; i < size; i++)
      {
        ArrayList subConstitution = (ArrayList)source.get(i);
        if (subConstitution.size() > 0)
        {
          vo = new ConstitutionVO();
          vo.setContitutionCode(CommonFunction.checkNull(subConstitution.get(0)).toString());
          vo.setConstitution(CommonFunction.checkNull(subConstitution.get(1)).toString());
          list.add(vo);
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    return list;
  }

  public boolean deleteCustomerDocs(String[] roleId, String[] applType, String dealId)
  {
    logger.info(new StringBuilder().append("deleteApplDocs for deleteApplDocs....roleId..").append(roleId).append(" dealId ").append(dealId).toString());
    boolean status = false;
    ArrayList qryList = null;
    StringBuffer bufInsUpdSql = null;
    try
    {
      qryList = new ArrayList();
      bufInsUpdSql = new StringBuffer();

      for (int k = 0; k < roleId.length; k++)
      {
        String deleteChatge = new StringBuilder().append("delete from cr_document_dtl where TXN_TYPE='DC' AND STAGE_ID='PRS' AND DOC_TYPE=(select DEAL_CUSTOMER_ROLE_TYPE from cr_deal_customer_role where DEAL_CUSTOMER_ROLE_ID=").append(StringEscapeUtils.escapeSql(CommonFunction.checkNull(roleId[k])).trim()).append(" ) AND  TXNID=").append(StringEscapeUtils.escapeSql(CommonFunction.checkNull(dealId)).trim()).append(" and ENTITY_ID=(select DEAL_CUSTOMER_ID from cr_deal_customer_role where DEAL_CUSTOMER_ROLE_ID=").append(StringEscapeUtils.escapeSql(CommonFunction.checkNull(roleId[k])).trim()).append(" )").toString();
        logger.info(new StringBuilder().append("delete query:deletedocs ").append(deleteChatge).toString());
        qryList.add(deleteChatge);
      }

      status = ConnectionDAO.sqlInsUpdDelete(qryList);
      logger.info(new StringBuilder().append("Status of Deletion is =").append(status).toString());
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
    return status;
  }
  public ArrayList<CustomerSaveVo> defaultcountry() {
    ArrayList list = new ArrayList();
    try {
      logger.info("In defaultcountry()..........................DAOImpl");
      StringBuilder query = new StringBuilder();

      query.append("SELECT PARAMETER_VALUE,PARAMETER_DESC FROM parameter_mst WHERE PARAMETER_KEY='DEFAULT_COUNTRY'");
      logger.info(new StringBuilder().append("In defaultcountry...............query...........DAOImpl").append(query).toString());
      CustomerSaveVo vo = null;
      ArrayList country = ConnectionDAO.sqlSelect(query.toString());

      query = null;

      logger.info(new StringBuilder().append("defaultcountry() ").append(country.size()).toString());
      for (int i = 0; i < country.size(); i++) {
        logger.info(new StringBuilder().append("defaultcountry()...Outer FOR loop ").append(CommonFunction.checkNull(country.get(i)).toString()).toString());
        ArrayList data = (ArrayList)country.get(i);
        if (data.size() > 0) {
          vo = new CustomerSaveVo();
          vo.setDefaultcountryid(CommonFunction.checkNull(data.get(0)).trim());
          vo.setDefaultcountryname(CommonFunction.checkNull(data.get(1)).trim());
          list.add(vo);
        }
      }
    }
    catch (Exception e) {
      e.printStackTrace();
    }

    return list;
  }

  public ArrayList<CustomerSaveVo> copyAddress(String addressId, String source)
  {
    logger.info("In copyAddress()");
    ArrayList list = new ArrayList();
    String table = "cr_deal_address_m";
    if (CommonFunction.checkNull(source).trim().equalsIgnoreCase("GCD"))
      table = "com_address_m";
    try
    {
      StringBuilder query = new StringBuilder();
      query.append(new StringBuilder().append(" select distinct a.ADDRESS_TYPE,a.ADDRESS_LINE1,a.ADDRESS_LINE2,a.ADDRESS_LINE3,a.COUNTRY, b.COUNTRY_DESC,a.STATE,c.STATE_DESC,a.TAHSIL,a.DISTRICT,d.DISTRICT_DESC,a.PINCODE,a.PRIMARY_PHONE,a.ALTERNATE_PHONE, a.TOLLFREE_NUMBER,a.FAX,a.LANDMARK,a.NO_OF_YEARS,a.NO_OF_MONTHS,a.BRANCH_DISTANCE,a.COMMUNICATION_ADDRESS,a.ADDRESS_DETAIL  from ").append(table).append(" a join com_country_m b on(b.COUNTRY_ID=a.COUNTRY) join com_state_m c on(c.STATE_ID=a.STATE) ").append(" join com_district_m d on(d.DISTRICT_ID=a.DISTRICT) where a.ADDRESS_ID='").append(addressId.trim()).append("'").toString());

      logger.info(new StringBuilder().append("In copyAddress()   query  :  ").append(query).toString());
      CustomerSaveVo vo = null;
      ArrayList mainList = ConnectionDAO.sqlSelect(query.toString());
      query = null;
      logger.info(new StringBuilder().append("defaultcountry() ").append(mainList.size()).toString());
      int size = mainList.size();
      for (int i = 0; i < size; i++)
      {
        ArrayList data = (ArrayList)mainList.get(i);
        if (data.size() > 0)
        {
          vo = new CustomerSaveVo();
          vo.setAddr_type(CommonFunction.checkNull(data.get(0)).trim());
          vo.setAddr1(CommonFunction.checkNull(data.get(1)).trim());
          vo.setAddr2(CommonFunction.checkNull(data.get(2)).trim());
          vo.setAddr3(CommonFunction.checkNull(data.get(3)).trim());
          vo.setTxtCountryCode(CommonFunction.checkNull(data.get(4)).trim());
          vo.setCountry(CommonFunction.checkNull(data.get(5)).trim());
          vo.setTxtStateCode(CommonFunction.checkNull(data.get(6)).trim());
          vo.setState(CommonFunction.checkNull(data.get(7)).trim());
          vo.setTahsil(CommonFunction.checkNull(data.get(8)).trim());
          vo.setTxtDistCode(CommonFunction.checkNull(data.get(9)).trim());
          vo.setDist(CommonFunction.checkNull(data.get(10)).trim());
          vo.setPincode(CommonFunction.checkNull(data.get(11)).trim());
          vo.setPrimaryPhoneNo(CommonFunction.checkNull(data.get(12)).trim());
          vo.setAlternatePhoneNo(CommonFunction.checkNull(data.get(13)).trim());
          vo.setTollfreeNo(CommonFunction.checkNull(data.get(14)).trim());
          vo.setFaxNo(CommonFunction.checkNull(data.get(15)).trim());
          vo.setLandMark(CommonFunction.checkNull(data.get(16)).trim());
          vo.setNoYears(CommonFunction.checkNull(data.get(17)).trim());
          vo.setNoMonths(CommonFunction.checkNull(data.get(18)).trim());
          vo.setDistanceBranch(CommonFunction.checkNull(data.get(19)).trim());
          vo.setCommunicationAddress(CommonFunction.checkNull(data.get(20)).trim());
          vo.setAddDetails(CommonFunction.checkNull(data.get(21)).trim());
          list.add(vo);
        }
      }
    }
    catch (Exception e) {
      e.printStackTrace();
    }return list;
  }

  public boolean saveCustomerProfile(Object ob, String id, String source)
  {
    logger.info("In saveCustomerProfile() of CorpotateDAOImpl.");
    CustomerSaveVo vo = (CustomerSaveVo)ob;
    boolean status = false;
    ArrayList qryList = new ArrayList();
    PrepStmtObject insertPrepStmtObject = new PrepStmtObject();
    try
    {
      if (((vo.getPageStatus() != null) && (vo.getPageStatus().equals("fromLeads"))) || ((vo.getUpdateFlag() != null) && (vo.getUpdateFlag().length() > 0)))
      {
        StringBuffer bufInsSql = new StringBuffer();
        bufInsSql.append("UPDATE cr_deal_customer_m SET CUSTOMER_PROFILE=? WHERE customer_id=?");

        if (CommonFunction.checkNull(vo.getCustomerProfile()).trim().equalsIgnoreCase(""))
          insertPrepStmtObject.addNull();
        else
          insertPrepStmtObject.addString(CommonFunction.checkNull(vo.getCustomerProfile()).trim());
        logger.info(new StringBuilder().append("id.......").append(id).toString());

        if (CommonFunction.checkNull(id).equalsIgnoreCase(""))
          insertPrepStmtObject.addNull();
        else {
          insertPrepStmtObject.addString(CommonFunction.checkNull(id));
        }

        insertPrepStmtObject.setSql(bufInsSql.toString());
        logger.info(new StringBuilder().append("IN saveCustomerAddress() of CorpotateDAOImpl  insert query1 ### ").append(insertPrepStmtObject.printQuery()).toString());
        qryList.add(insertPrepStmtObject);
        status = ConnectionDAO.sqlInsUpdDeletePrepStmt(qryList);
        logger.info(new StringBuilder().append("In saveCustomer......................").append(status).toString());
      }
      else
      {
        StringBuffer bufInsSql = new StringBuffer();

        if (!vo.getStatusCase().equals(""))
        {
          bufInsSql.append("UPDATE gcd_customer_m_temp SET CUSTOMER_PROFILE=? WHERE customer_id=?");
        }
        else
        {
          String table = "gcd_customer_m";
          if (CommonFunction.checkNull(source).trim().equalsIgnoreCase("ED"))
            table = "gcd_customer_m_edit";
          bufInsSql.append(new StringBuilder().append("UPDATE ").append(table).append(" SET CUSTOMER_PROFILE=? WHERE customer_id=?").toString());
        }

        if (CommonFunction.checkNull(vo.getCustomerProfile()).trim().equalsIgnoreCase(""))
          insertPrepStmtObject.addNull();
        else {
          insertPrepStmtObject.addString(vo.getCustomerProfile().trim());
        }
        if (CommonFunction.checkNull(id).equalsIgnoreCase(""))
          insertPrepStmtObject.addString(id);
        else {
          insertPrepStmtObject.addString(id);
        }

        insertPrepStmtObject.setSql(bufInsSql.toString());
        logger.info(new StringBuilder().append("IN saveCustomerAddress() insert query1 ### ").append(insertPrepStmtObject.printQuery()).toString());
        qryList.add(insertPrepStmtObject);

        status = ConnectionDAO.sqlInsUpdDeletePrepStmt(qryList);
        logger.info(new StringBuilder().append("In saveCustomer......................").append(status).toString());
      }
    }
    catch (Exception e) {
      e.printStackTrace();
    }return status;
  }

  public ArrayList<Object> getProfileDetails(String code, String statusCase, String updateInMaker, String pageStatuss, String updateFlag, String cuaStatus, String source)
  {
    String tableName = "";
    logger.info("In getProfileDetails() of CorpotateDAOImpl");
    ArrayList list = new ArrayList();
    if (((pageStatuss != null) && (pageStatuss.equals("fromLeads"))) || ((updateFlag != null) && (updateFlag.equals("updateFlag"))) || ((updateFlag != null) && (updateFlag.equals("notEdit"))))
    {
      tableName = "cr_deal_customer_m";
      try
      {
        String query = new StringBuilder().append("SELECT c.customer_id,c.customer_profile,CUSTOMER_NAME from ").append(tableName).append(" c ").append("where c.customer_id=").append(code).toString();

        logger.info(new StringBuilder().append("in getAddressDetails() of CorpotateDAOImpl  Query : ").append(query).toString());
        ArrayList addressDetails = ConnectionDAO.sqlSelect(query);

        for (int i = 0; i < addressDetails.size(); i++)
        {
          ArrayList data = (ArrayList)addressDetails.get(i);
          if (data.size() > 0)
          {
            CustomerSaveVo addr = new CustomerSaveVo();
            addr.setBp_id1(CommonFunction.checkNull(data.get(0)).toString());
            addr.setCustomerProfile(CommonFunction.checkNull(data.get(1)).toString());
            addr.setCustomerName(CommonFunction.checkNull(data.get(2)).toString());
            list.add(addr);
          }
        }
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
    else
    {
      if (((updateInMaker != null) && (updateInMaker.equals("updateInMaker"))) || ((statusCase != null) && (!statusCase.equals(""))))
      {
        if ((statusCase != null) && (statusCase.equalsIgnoreCase("Approved")) && ((updateInMaker == null) || (updateInMaker.equals(""))))
        {
          tableName = "gcd_customer_m";
          if (CommonFunction.checkNull(source).trim().equalsIgnoreCase("ED"))
            tableName = "gcd_customer_m_edit";
        }
        else
        {
          tableName = "gcd_customer_m_temp";
        }
      }
      else
      {
        tableName = "gcd_customer_m";
        if (CommonFunction.checkNull(source).trim().equalsIgnoreCase("ED")) {
          tableName = "gcd_customer_m_edit";
        }
      }

      if (CommonFunction.checkNull(cuaStatus).equalsIgnoreCase("CUA"))
      {
        tableName = "gcd_customer_m_temp";
      }

      try
      {
        String query = new StringBuilder().append("SELECT c.customer_id,c.customer_profile,c.CUSTOMER_NAME  from ").append(tableName).append(" c where  c.customer_id=").append(code).toString();

        logger.info(new StringBuilder().append("in getAddressDetails() of CorpotateDAOImpl  Query :   ").append(query).toString());
        ArrayList addressDetails = ConnectionDAO.sqlSelect(query);

        logger.info(new StringBuilder().append("getAddressDetails ").append(addressDetails.size()).toString());
        for (int i = 0; i < addressDetails.size(); i++)
        {
          ArrayList data = (ArrayList)addressDetails.get(i);
          if (data.size() > 0)
          {
            CustomerSaveVo addr = new CustomerSaveVo();
            addr.setBp_id1(CommonFunction.checkNull(data.get(0)).toString());

            addr.setCustomerProfile(CommonFunction.checkNull(data.get(1)).toString());
            addr.setCustomerName(CommonFunction.checkNull(data.get(2)).toString());
            list.add(addr);
          }
        }
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
    return list;
  }

  public ArrayList<Object> getProfileAll(String code, Object pageStatus, String updateFlag, String source)
  {
    ArrayList list = new ArrayList();
    String tableName = "";
    if (((updateFlag != null) && (updateFlag.equals("updateFlag"))) || ((updateFlag != null) && (updateFlag.equals("notEdit"))))
    {
      logger.info("In Credit Processing , Customer Entry..,getProfileAll()......");
      logger.info(new StringBuilder().append("from deal info in update flag>>>>>>>>>>>>> ").append(updateFlag).toString());
      tableName = "cr_deal_customer_m";
    }
    else
    {
      logger.info("In GCD..,getProfileAll()......");
      tableName = "gcd_customer_m";
      if (CommonFunction.checkNull(source).trim().equalsIgnoreCase("ED"))
        tableName = "gcd_customer_m_edit";
    }
    try
    {
      String query = new StringBuilder().append("select customer_id,customer_profile from ").append(tableName).append("  where customer_id=").append(code).toString();
      logger.info(new StringBuilder().append("................. from ").append(tableName).append("  table............................query ").append(query).toString());
      ArrayList addressAll = ConnectionDAO.sqlSelect(query);
      logger.info(new StringBuilder().append("getReferenceAll : : :  ").append(addressAll.size()).toString());
      for (int i = 0; i < addressAll.size(); i++) {
        logger.info(new StringBuilder().append("getProfileAll...Outer FOR loop ").append(CommonFunction.checkNull(addressAll.get(i)).toString()).toString());
        ArrayList data = (ArrayList)addressAll.get(i);
        if (data.size() > 0)
        {
          CustomerSaveVo addr = new CustomerSaveVo();
          addr.setBp_id1(CommonFunction.checkNull(data.get(0)).toString());

          addr.setCustomerProfile(CommonFunction.checkNull(data.get(1)).toString());

          list.add(addr);
        }
      }
    }
    catch (Exception e) {
      e.printStackTrace();
    }

    return list;
  }

  public ArrayList<Object> getRelationDeatil()
  {
    ArrayList mainList = new ArrayList();
    try
    {
      String query = "select VALUE,DESCRIPTION FROM generic_master  WHERE GENERIC_KEY='RELATION_TYPE'  AND REC_STATUS='A'";
      logger.info(new StringBuilder().append("query for the relation *****").append(query).toString());
      ArrayList list = ConnectionDAO.sqlSelect(query);

      logger.info(new StringBuilder().append("size of tyhe list********").append(list.size()).toString());

      for (int i = 0; i < list.size(); i++) {
        ArrayList data = (ArrayList)list.get(i);
        if (data.size() > 0) {
          CustomerSaveVo relatVO = new CustomerSaveVo();
          relatVO.setRelationCode(CommonFunction.checkNull(data.get(0)));
          relatVO.setRelationshipS(CommonFunction.checkNull(data.get(1)));
          mainList.add(relatVO);
        }
      }
    }
    catch (Exception e) {
      e.printStackTrace();
    }

    return mainList;
  }

  public ArrayList<CorporateBusinessActivityVO> getBusinessActivity(int code, Object pageStatus, String statusCase, String updateFlag, String updateInMaker, String pageStatuss, String cuaStatus, String source)
  {
    ArrayList list = new ArrayList();
    String tableName = "";
    logger.info(new StringBuilder().append("Value of Flag cid: ").append(code).toString());
    logger.info(new StringBuilder().append("Value of Flag pageStatus: ").append(pageStatus).toString());
    logger.info(new StringBuilder().append("Value of Flag statusCase: ").append(statusCase).toString());
    logger.info(new StringBuilder().append("Value of Flag updateFlag: ").append(updateFlag).toString());
    logger.info(new StringBuilder().append("Value of Flag updateInMaker: ").append(updateInMaker).toString());
    logger.info(new StringBuilder().append("Value of Flag cuaStatus: ").append(cuaStatus).toString());
    StringBuffer query = null;
    if (((updateFlag != null) && (updateFlag.equals("updateFlag"))) || ((updateFlag != null) && (updateFlag.equals("notEdit"))) || (pageStatuss.equalsIgnoreCase("fromLeads")))
    {
      logger.info("In CorporateDAOImpl getBusinessActivity() for CP-->Cust Entry.......");
      tableName = "cr_deal_customer_business_activity_m";
      try
      {
        query = new StringBuffer();
        query.append(new StringBuilder().append("SELECT CUSTOMER_ID, BRANDS, PRODUCT_SERVICES, NO_OF_EMPLOYEES, AUDITORS, CERTIFICATIONS, AWARDS, ASSOCIATION_NAME, REC_STATUS FROM ").append(tableName).append(" WHERE CUSTOMER_ID=").append(code).toString());

        logger.info(new StringBuilder().append("query: ").append(query.toString()).toString());
        ArrayList businessActivityDetails = ConnectionDAO.sqlSelect(query.toString());
        query = null;
        logger.info(new StringBuilder().append("getBusinessActivity ").append(businessActivityDetails.size()).toString());
        for (int i = 0; i < businessActivityDetails.size(); i++)
        {
          ArrayList data = (ArrayList)businessActivityDetails.get(i);
          if (data.size() > 0)
          {
            CorporateBusinessActivityVO vo = new CorporateBusinessActivityVO();
            vo.setCustomerId(CommonFunction.checkNull(data.get(0)).toString());
            vo.setBrands(CommonFunction.checkNull(data.get(1)).toString());
            vo.setProductServices(CommonFunction.checkNull(data.get(2)).toString());
            vo.setNoOfEmployees(CommonFunction.checkNull(data.get(3)).toString());
            vo.setAuditors(CommonFunction.checkNull(data.get(4)).toString());
            vo.setCertifications(CommonFunction.checkNull(data.get(5)).toString());
            vo.setAwards(CommonFunction.checkNull(data.get(6)).toString());
            vo.setAssocoationMembershipName(CommonFunction.checkNull(data.get(7)).toString());
            vo.setRecStatus(CommonFunction.checkNull(data.get(8)).toString());
            list.add(vo);
          }
        }
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
    else
    {
      logger.info("In GCD.,getBusinessActivity().......");
      if ((pageStatus != null) || ((updateInMaker != null) && (updateInMaker.equals("updateInMaker"))) || ((statusCase != null) && (!statusCase.equals(""))))
      {
        if ((statusCase != null) && (statusCase.equalsIgnoreCase("Approved")) && ((updateInMaker == null) || (updateInMaker.equals(""))))
        {
          tableName = "customer_business_activity_m";
          if (CommonFunction.checkNull(source).trim().equalsIgnoreCase("ED"))
            tableName = "customer_business_activity_m_edit";
        }
        else
        {
          tableName = "customer_business_activity_temp";
        }
      }
      else
      {
        tableName = "customer_business_activity_m";
        if (CommonFunction.checkNull(source).trim().equalsIgnoreCase("ED"))
          tableName = "customer_business_activity_m_edit";
      }
      if (CommonFunction.checkNull(cuaStatus).equalsIgnoreCase("CUA"))
      {
        tableName = "customer_business_activity_temp";
      }
      try
      {
        query = new StringBuffer();
        query.append(new StringBuilder().append("SELECT CUSTOMER_ID, BRANDS, PRODUCT_SERVICES, NO_OF_EMPLOYEES, AUDITORS, CERTIFICATIONS, AWARDS, ASSOCIATION_NAME, REC_STATUS FROM ").append(tableName).append(" WHERE CUSTOMER_ID=").append(code).toString());

        logger.info(new StringBuilder().append("query: ").append(query.toString()).toString());
        ArrayList businessActivityDetails = ConnectionDAO.sqlSelect(query.toString());
        query = null;
        logger.info(new StringBuilder().append("getBusinessActivity ").append(businessActivityDetails.size()).toString());
        for (int i = 0; i < businessActivityDetails.size(); i++)
        {
          ArrayList data = (ArrayList)businessActivityDetails.get(i);
          if (data.size() > 0)
          {
            CorporateBusinessActivityVO vo = new CorporateBusinessActivityVO();
            vo.setCustomerId(CommonFunction.checkNull(data.get(0)).toString());
            vo.setBrands(CommonFunction.checkNull(data.get(1)).toString());
            vo.setProductServices(CommonFunction.checkNull(data.get(2)).toString());
            vo.setNoOfEmployees(CommonFunction.checkNull(data.get(3)).toString());
            vo.setAuditors(CommonFunction.checkNull(data.get(4)).toString());
            vo.setCertifications(CommonFunction.checkNull(data.get(5)).toString());
            vo.setAwards(CommonFunction.checkNull(data.get(6)).toString());
            vo.setAssocoationMembershipName(CommonFunction.checkNull(data.get(7)).toString());
            vo.setRecStatus(CommonFunction.checkNull(data.get(8)).toString());
            list.add(vo);
          }
        }
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
    return list;
  }

  public boolean saveBusinessActivity(CorporateBusinessActivityVO vo, int id, String source)
  {
    boolean status = false;
    ArrayList qryList = new ArrayList();
    PrepStmtObject insertPrepStmtObject = new PrepStmtObject();
    try {
      if (((vo.getPageStat() != null) && (vo.getPageStat().equals("fromLeads"))) || ((vo.getUpdateFlag() != null) && (vo.getUpdateFlag().length() > 0)))
      {
        logger.info("In Credit Processing , Customer Entry.,saveBusinessActivity().......");

        logger.info("In insert saveBusinessActivity");
        StringBuffer bufInsSql = new StringBuffer();
        bufInsSql.append("insert into cr_deal_customer_business_activity_m(CUSTOMER_ID,BRANDS,PRODUCT_SERVICES,NO_OF_EMPLOYEES,AUDITORS,CERTIFICATIONS,AWARDS,ASSOCIATION_NAME,REC_STATUS,MAKER_ID,MAKER_DATE)");
        bufInsSql.append(" values ( ");
        bufInsSql.append(" ?,");
        bufInsSql.append(" ?,");
        bufInsSql.append(" ?,");
        bufInsSql.append(" ?,");
        bufInsSql.append(" ?,");
        bufInsSql.append(" ?,");
        bufInsSql.append(" ?,");
        bufInsSql.append(" ?,");
        bufInsSql.append(" ?,");
        bufInsSql.append(" ?,");
        bufInsSql.append(new StringBuilder().append(" DATE_ADD(STR_TO_DATE(?, '").append(this.dateFormatWithTime).append("'),INTERVAL CURTIME() HOUR_SECOND))").toString());

        if (CommonFunction.checkNull(Integer.valueOf(id)).trim().equalsIgnoreCase(""))
          insertPrepStmtObject.addNull();
        else {
          insertPrepStmtObject.addInt(id);
        }
        if (CommonFunction.checkNull(vo.getBrands()).trim().equalsIgnoreCase(""))
          insertPrepStmtObject.addNull();
        else {
          insertPrepStmtObject.addString(vo.getBrands().trim());
        }
        if (CommonFunction.checkNull(vo.getProductServices()).trim().equalsIgnoreCase(""))
          insertPrepStmtObject.addNull();
        else {
          insertPrepStmtObject.addString(vo.getProductServices().trim());
        }
        if (CommonFunction.checkNull(vo.getNoOfEmployees()).trim().equalsIgnoreCase(""))
          insertPrepStmtObject.addNull();
        else {
          insertPrepStmtObject.addInt(Integer.parseInt(vo.getNoOfEmployees()));
        }
        if (CommonFunction.checkNull(vo.getAuditors()).trim().equalsIgnoreCase(""))
          insertPrepStmtObject.addNull();
        else {
          insertPrepStmtObject.addString(vo.getAuditors().trim());
        }
        if (CommonFunction.checkNull(vo.getCertifications()).trim().equalsIgnoreCase(""))
          insertPrepStmtObject.addNull();
        else {
          insertPrepStmtObject.addString(vo.getCertifications().trim());
        }
        if (CommonFunction.checkNull(vo.getAwards()).trim().equalsIgnoreCase(""))
          insertPrepStmtObject.addNull();
        else {
          insertPrepStmtObject.addString(vo.getAwards().trim());
        }
        if (CommonFunction.checkNull(vo.getAssocoationMembershipName()).trim().equalsIgnoreCase(""))
          insertPrepStmtObject.addNull();
        else {
          insertPrepStmtObject.addString(vo.getAssocoationMembershipName().trim());
        }
        insertPrepStmtObject.addString("A");

        if (CommonFunction.checkNull(vo.getMakerId()).trim().equalsIgnoreCase(""))
          insertPrepStmtObject.addNull();
        else {
          insertPrepStmtObject.addString(vo.getMakerId().trim());
        }
        if (CommonFunction.checkNull(vo.getMakerDate()).trim().equalsIgnoreCase(""))
          insertPrepStmtObject.addNull();
        else {
          insertPrepStmtObject.addString(CommonFunction.checkNull(vo.getMakerDate()).trim());
        }

        insertPrepStmtObject.setSql(bufInsSql.toString());
        logger.info(new StringBuilder().append("IN saveBusinessActivity() insert query1 ### ").append(insertPrepStmtObject.printQuery()).toString());
        qryList.add(insertPrepStmtObject);
        status = ConnectionDAO.sqlInsUpdDeletePrepStmt(qryList);
        logger.info(new StringBuilder().append("In saveBusinessActivity......................").append(status).toString());
      }
      else
      {
        logger.info("In GCD.,saveBusinessActivity().......");
        logger.info(new StringBuilder().append("Ivo.getStatusCase()......").append(vo.getStatusCase()).toString());
        int maxId = 0;
        StringBuffer bufInsSql = new StringBuffer();
        int checkTable = 0;
        if (!vo.getStatusCase().equals(""))
        {
          String query3 = "Select max(BUSINESS_ACTIVITY_ID) from customer_business_activity_temp for update";
          String maxid = ConnectionDAO.singleReturn(query3);
          if ((maxid == null) || (maxid == ""))
          {
            maxId = 1;
          }
          else
          {
            maxId = Integer.parseInt(maxid) + 1;
          }
          logger.info(new StringBuilder().append("maxId : ").append(maxId).toString());
          checkTable++;
          bufInsSql.append("insert into customer_business_activity_temp(BUSINESS_ACTIVITY_ID,CUSTOMER_ID,BRANDS,PRODUCT_SERVICES,NO_OF_EMPLOYEES,AUDITORS,CERTIFICATIONS,AWARDS,ASSOCIATION_NAME,REC_STATUS)");
          bufInsSql.append(" values ( ");
          bufInsSql.append(" ?,");
        }
        else
        {
          bufInsSql.append("insert into customer_business_activity_m(CUSTOMER_ID,BRANDS,PRODUCT_SERVICES,NO_OF_EMPLOYEES,AUDITORS,CERTIFICATIONS,AWARDS,ASSOCIATION_NAME,REC_STATUS,MAKER_ID,MAKER_DATE)");
          bufInsSql.append(" values ( ");
        }

        if (checkTable != 0)
        {
          bufInsSql.append(" ?,");
          bufInsSql.append(" ?,");
          bufInsSql.append(" ?,");
          bufInsSql.append(" ?,");
          bufInsSql.append(" ?,");
          bufInsSql.append(" ?,");
          bufInsSql.append(" ?,");
          bufInsSql.append(" ?,");
          bufInsSql.append(" ?)");
        }
        if (checkTable == 0)
        {
          bufInsSql.append(" ?,");
          bufInsSql.append(" ?,");
          bufInsSql.append(" ?,");
          bufInsSql.append(" ?,");
          bufInsSql.append(" ?,");
          bufInsSql.append(" ?,");
          bufInsSql.append(" ?,");
          bufInsSql.append(" ?,");
          bufInsSql.append(" ?,");
          bufInsSql.append(" ?,");
          bufInsSql.append(new StringBuilder().append(" DATE_ADD(STR_TO_DATE(?, '").append(this.dateFormatWithTime).append("'),INTERVAL CURTIME() HOUR_SECOND))").toString());
        }
        if (!vo.getStatusCase().equals(""))
        {
          insertPrepStmtObject.addInt(maxId);
        }

        if (CommonFunction.checkNull(Integer.valueOf(id)).trim().equalsIgnoreCase(""))
          insertPrepStmtObject.addNull();
        else {
          insertPrepStmtObject.addInt(id);
        }
        if (CommonFunction.checkNull(vo.getBrands()).trim().equalsIgnoreCase(""))
          insertPrepStmtObject.addNull();
        else {
          insertPrepStmtObject.addString(vo.getBrands().trim());
        }
        if (CommonFunction.checkNull(vo.getProductServices()).trim().equalsIgnoreCase(""))
          insertPrepStmtObject.addNull();
        else {
          insertPrepStmtObject.addString(vo.getProductServices().trim());
        }
        if (CommonFunction.checkNull(vo.getNoOfEmployees()).trim().equalsIgnoreCase(""))
          insertPrepStmtObject.addNull();
        else {
          insertPrepStmtObject.addInt(Integer.parseInt(vo.getNoOfEmployees()));
        }
        if (CommonFunction.checkNull(vo.getAuditors()).trim().equalsIgnoreCase(""))
          insertPrepStmtObject.addNull();
        else {
          insertPrepStmtObject.addString(vo.getAuditors().trim());
        }
        if (CommonFunction.checkNull(vo.getCertifications()).trim().equalsIgnoreCase(""))
          insertPrepStmtObject.addNull();
        else {
          insertPrepStmtObject.addString(vo.getCertifications().trim());
        }
        if (CommonFunction.checkNull(vo.getAwards()).trim().equalsIgnoreCase(""))
          insertPrepStmtObject.addNull();
        else {
          insertPrepStmtObject.addString(vo.getAwards().trim());
        }
        if (CommonFunction.checkNull(vo.getAssocoationMembershipName()).trim().equalsIgnoreCase(""))
          insertPrepStmtObject.addNull();
        else {
          insertPrepStmtObject.addString(vo.getAssocoationMembershipName().trim());
        }
        insertPrepStmtObject.addString("P");

        if (checkTable == 0)
        {
          if (CommonFunction.checkNull(vo.getMakerId()).trim().equalsIgnoreCase(""))
            insertPrepStmtObject.addNull();
          else
            insertPrepStmtObject.addString(vo.getMakerId().trim());
          if (CommonFunction.checkNull(vo.getMakerDate()).trim().equalsIgnoreCase(""))
            insertPrepStmtObject.addNull();
          else
            insertPrepStmtObject.addString(CommonFunction.checkNull(vo.getMakerDate()).trim());
        }
        insertPrepStmtObject.setSql(bufInsSql.toString());
        logger.info(new StringBuilder().append("IN saveBusinessActivity() insert query1 ### ").append(insertPrepStmtObject.printQuery()).toString());
        qryList.add(insertPrepStmtObject);

        status = ConnectionDAO.sqlInsUpdDeletePrepStmt(qryList);
        logger.info(new StringBuilder().append("In saveBusinessActivity......................").append(status).toString());
        if ((CommonFunction.checkNull(source).trim().equalsIgnoreCase("ED")) && (status))
        {
          String q1 = new StringBuilder().append("select max(BUSINESS_ACTIVITY_ID) from customer_business_activity_m where CUSTOMER_ID='").append(id).append("' for update").toString();
          String businessID = ConnectionDAO.singleReturn(q1);
          String query = new StringBuilder().append("INSERT INTO customer_business_activity_m_edit select * from customer_business_activity_m where  BUSINESS_ACTIVITY_ID =").append(businessID).toString();
          PrepStmtObject stmt = new PrepStmtObject();
          stmt.setSql(query);
          ArrayList list = new ArrayList();
          list.add(stmt);
          status = ConnectionDAO.sqlInsUpdDeletePrepStmt(list);
          logger.info(new StringBuilder().append("Edit Insert Status  :  ").append(status).toString());
          String query2 = new StringBuilder().append("delete from customer_business_activity_m where  BUSINESS_ACTIVITY_ID =").append(businessID).toString();
          PrepStmtObject stmt2 = new PrepStmtObject();
          stmt2.setSql(query2);
          ArrayList list2 = new ArrayList();
          list2.add(stmt2);
          status = ConnectionDAO.sqlInsUpdDeletePrepStmt(list2);
        }
      }
    }
    catch (Exception e) {
      e.printStackTrace();
    }
    return status;
  }

  public int updateBusinessActivity(CorporateBusinessActivityVO vo, int id, String recStatus, String statusCase, String updateFlag, String pageStatuss, String source)
  {
    int status = 0;
    boolean qryStatus = false;
    String tableName = "";
    String subQuery = null;
    ArrayList qryList = new ArrayList();
    PrepStmtObject insertPrepStmtObject = new PrepStmtObject();
    try {
      logger.info(new StringBuilder().append("updateBusinessActivity :").append(id).toString());
      if (((pageStatuss != null) && (pageStatuss.equals("fromLeads"))) || ((updateFlag != null) && (updateFlag.equals("updateFlag"))))
      {
        logger.info("In Credit Processing , Customer Entry.,updateBusinessActivity().......");
        String query = new StringBuilder().append("update cr_deal_customer_business_activity_m set BRANDS=?, PRODUCT_SERVICES=?, NO_OF_EMPLOYEES=?, AUDITORS=?, CERTIFICATIONS=?, AWARDS=?, ASSOCIATION_NAME=?, REC_STATUS=?, MAKER_ID=?,MAKER_DATE=DATE_ADD(STR_TO_DATE(?,'").append(this.dateFormatWithTime).append("'),INTERVAL CURTIME() HOUR_SECOND) ").append("where customer_id=?").toString();

        if (CommonFunction.checkNull(vo.getBrands()).trim().equalsIgnoreCase(""))
          insertPrepStmtObject.addNull();
        else {
          insertPrepStmtObject.addString(vo.getBrands().trim());
        }
        if (CommonFunction.checkNull(vo.getProductServices()).trim().equalsIgnoreCase(""))
          insertPrepStmtObject.addNull();
        else {
          insertPrepStmtObject.addString(vo.getProductServices().trim());
        }
        if (CommonFunction.checkNull(vo.getNoOfEmployees()).trim().equalsIgnoreCase(""))
          insertPrepStmtObject.addNull();
        else {
          insertPrepStmtObject.addInt(Integer.parseInt(vo.getNoOfEmployees()));
        }
        if (CommonFunction.checkNull(vo.getAuditors()).trim().equalsIgnoreCase(""))
          insertPrepStmtObject.addNull();
        else {
          insertPrepStmtObject.addString(vo.getAuditors().trim());
        }
        if (CommonFunction.checkNull(vo.getCertifications()).trim().equalsIgnoreCase(""))
          insertPrepStmtObject.addNull();
        else {
          insertPrepStmtObject.addString(vo.getCertifications().trim());
        }
        if (CommonFunction.checkNull(vo.getAwards()).trim().equalsIgnoreCase(""))
          insertPrepStmtObject.addNull();
        else {
          insertPrepStmtObject.addString(vo.getAwards().trim());
        }
        if (CommonFunction.checkNull(vo.getAssocoationMembershipName()).trim().equalsIgnoreCase(""))
          insertPrepStmtObject.addNull();
        else {
          insertPrepStmtObject.addString(vo.getAssocoationMembershipName().trim());
        }
        if (CommonFunction.checkNull(vo.getRecStatus()).trim().equalsIgnoreCase(""))
          insertPrepStmtObject.addNull();
        else {
          insertPrepStmtObject.addString(vo.getRecStatus().trim());
        }
        if (CommonFunction.checkNull(vo.getMakerId()).trim().equalsIgnoreCase(""))
          insertPrepStmtObject.addNull();
        else {
          insertPrepStmtObject.addString(vo.getMakerId().trim());
        }
        if (CommonFunction.checkNull(vo.getMakerDate()).trim().equalsIgnoreCase(""))
          insertPrepStmtObject.addNull();
        else {
          insertPrepStmtObject.addString(vo.getMakerDate().trim());
        }
        if (CommonFunction.checkNull(Integer.valueOf(id)).trim().equalsIgnoreCase(""))
          insertPrepStmtObject.addNull();
        else {
          insertPrepStmtObject.addInt(id);
        }
        insertPrepStmtObject.setSql(query);
        logger.info(new StringBuilder().append("IN updateBusinessActivity() update query1 ### ").append(insertPrepStmtObject.printQuery()).toString());
        qryList.add(insertPrepStmtObject);
        qryStatus = ConnectionDAO.sqlInsUpdDeletePrepStmt(qryList);
      }
      else
      {
        logger.info("In GCD.,updateBusinessActivity().......");
        if ((statusCase != null) && (statusCase.length() > 0))
        {
          tableName = "customer_business_activity_temp";
        }
        else
        {
          tableName = "customer_business_activity_m";
          if (CommonFunction.checkNull(source).trim().equalsIgnoreCase("ED")) {
            tableName = "customer_business_activity_m_edit";
          }
        }

        if ((tableName.equalsIgnoreCase("customer_business_activity_m")) || (tableName.equalsIgnoreCase("customer_business_activity_m_edit")))
        {
          subQuery = new StringBuilder().append(",MAKER_ID='").append(CommonFunction.checkNull(vo.getMakerId())).append("',").append("MAKER_DATE=DATE_ADD(STR_TO_DATE('").append(CommonFunction.checkNull(vo.getMakerDate())).append("','").append(this.dateFormatWithTime).append("'),INTERVAL CURTIME() HOUR_SECOND)").toString();
        }
        else {
          subQuery = "";
        }
        String query = new StringBuilder().append("update ").append(tableName).append(" set BRANDS=?, PRODUCT_SERVICES=?, NO_OF_EMPLOYEES=?, AUDITORS=?, ").append(" CERTIFICATIONS=?, AWARDS=?, ASSOCIATION_NAME=?, REC_STATUS=? ").append(subQuery).append("  where CUSTOMER_ID=?").toString();

        if (CommonFunction.checkNull(vo.getBrands()).trim().equalsIgnoreCase(""))
          insertPrepStmtObject.addNull();
        else {
          insertPrepStmtObject.addString(vo.getBrands().trim());
        }
        if (CommonFunction.checkNull(vo.getProductServices()).trim().equalsIgnoreCase(""))
          insertPrepStmtObject.addNull();
        else {
          insertPrepStmtObject.addString(vo.getProductServices().trim());
        }
        if (CommonFunction.checkNull(vo.getNoOfEmployees()).trim().equalsIgnoreCase(""))
          insertPrepStmtObject.addNull();
        else {
          insertPrepStmtObject.addInt(Integer.parseInt(vo.getNoOfEmployees()));
        }
        if (CommonFunction.checkNull(vo.getAuditors()).trim().equalsIgnoreCase(""))
          insertPrepStmtObject.addNull();
        else {
          insertPrepStmtObject.addString(vo.getAuditors().trim());
        }
        if (CommonFunction.checkNull(vo.getCertifications()).trim().equalsIgnoreCase(""))
          insertPrepStmtObject.addNull();
        else {
          insertPrepStmtObject.addString(vo.getCertifications().trim());
        }
        if (CommonFunction.checkNull(vo.getAwards()).trim().equalsIgnoreCase(""))
          insertPrepStmtObject.addNull();
        else {
          insertPrepStmtObject.addString(vo.getAwards().trim());
        }
        if (CommonFunction.checkNull(vo.getAssocoationMembershipName()).trim().equalsIgnoreCase(""))
          insertPrepStmtObject.addNull();
        else {
          insertPrepStmtObject.addString(vo.getAssocoationMembershipName().trim());
        }
        insertPrepStmtObject.addString("P");

        if (CommonFunction.checkNull(Integer.valueOf(id)).trim().equalsIgnoreCase(""))
          insertPrepStmtObject.addNull();
        else {
          insertPrepStmtObject.addInt(id);
        }
        insertPrepStmtObject.setSql(query.toString());
        logger.info(new StringBuilder().append("IN updateBusinessActivity() update query1 ### ").append(insertPrepStmtObject.printQuery()).toString());
        qryList.add(insertPrepStmtObject);

        qryStatus = ConnectionDAO.sqlInsUpdDeletePrepStmt(qryList);
      }
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      subQuery = "";
    }
    if (qryStatus)
    {
      status = 1;
    }
    return status;
  }

  public ArrayList<Object> getBusinessDescription(String code, String statusCase, String updateInMaker, String pageStatuss, String updateFlag, String cuaStatus, String source)
  {
    String tableName = "";
    logger.info("In getBusinessDescription() of CorpotateDAOImpl");
    ArrayList list = new ArrayList();
    if (((pageStatuss != null) && (pageStatuss.equals("fromLeads"))) || ((updateFlag != null) && (updateFlag.equals("updateFlag"))) || ((updateFlag != null) && (updateFlag.equals("notEdit"))))
    {
      tableName = "cr_deal_customer_m";
      try
      {
        String query = new StringBuilder().append("SELECT c.customer_id,c.BUSINESS_DESC,CUSTOMER_NAME from ").append(tableName).append(" c ").append("where c.customer_id=").append(code).toString();

        logger.info(new StringBuilder().append("in getBusinessDescription() of CorpotateDAOImpl  Query : ").append(query).toString());
        ArrayList addressDetails = ConnectionDAO.sqlSelect(query);

        for (int i = 0; i < addressDetails.size(); i++)
        {
          ArrayList data = (ArrayList)addressDetails.get(i);
          if (data.size() > 0)
          {
            CustomerSaveVo vo = new CustomerSaveVo();
            vo.setBp_id1(CommonFunction.checkNull(data.get(0)).toString());
            vo.setBusinessDesc(CommonFunction.checkNull(data.get(1)).toString());
            vo.setCustomerName(CommonFunction.checkNull(data.get(2)).toString());
            list.add(vo);
          }
        }
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
    else
    {
      if (((updateInMaker != null) && (updateInMaker.equals("updateInMaker"))) || ((statusCase != null) && (!statusCase.equals(""))))
      {
        if ((statusCase != null) && (statusCase.equalsIgnoreCase("Approved")) && ((updateInMaker == null) || (updateInMaker.equals(""))))
        {
          tableName = "gcd_customer_m";
          if (CommonFunction.checkNull(source).trim().equalsIgnoreCase("ED"))
            tableName = "gcd_customer_m_edit";
        }
        else
        {
          tableName = "gcd_customer_m_temp";
        }
      }
      else
      {
        tableName = "gcd_customer_m";
        if (CommonFunction.checkNull(source).trim().equalsIgnoreCase("ED")) {
          tableName = "gcd_customer_m_edit";
        }
      }

      if (CommonFunction.checkNull(cuaStatus).equalsIgnoreCase("CUA"))
      {
        tableName = "gcd_customer_m_temp";
      }
      try
      {
        String query = new StringBuilder().append("SELECT c.customer_id,c.BUSINESS_DESC,c.CUSTOMER_NAME  from ").append(tableName).append(" c where  c.customer_id=").append(code).toString();

        logger.info(new StringBuilder().append("in getBusinessDescription() of CorpotateDAOImpl  Query :   ").append(query).toString());
        ArrayList addressDetails = ConnectionDAO.sqlSelect(query);

        logger.info(new StringBuilder().append("getBusinessDescription ").append(addressDetails.size()).toString());
        for (int i = 0; i < addressDetails.size(); i++)
        {
          ArrayList data = (ArrayList)addressDetails.get(i);
          if (data.size() > 0)
          {
            CustomerSaveVo vo = new CustomerSaveVo();
            vo.setBp_id1(CommonFunction.checkNull(data.get(0)).toString());
            vo.setBusinessDesc(CommonFunction.checkNull(data.get(1)).toString());
            vo.setCustomerName(CommonFunction.checkNull(data.get(2)).toString());
            list.add(vo);
          }
        }
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
    return list;
  }

  public boolean saveBusinessDescription(Object ob, String id, String source)
  {
    logger.info("In saveBusinessDescription() of CorpotateDAOImpl.");
    CustomerSaveVo vo = (CustomerSaveVo)ob;
    boolean status = false;
    ArrayList qryList = new ArrayList();
    PrepStmtObject insertPrepStmtObject = new PrepStmtObject();
    try
    {
      if (((vo.getPageStatus() != null) && (vo.getPageStatus().equals("fromLeads"))) || ((vo.getUpdateFlag() != null) && (vo.getUpdateFlag().length() > 0)))
      {
        StringBuffer bufInsSql = new StringBuffer();
        bufInsSql.append("UPDATE cr_deal_customer_m SET BUSINESS_DESC=? WHERE customer_id=?");

        if (CommonFunction.checkNull(vo.getBusinessDesc()).trim().equalsIgnoreCase(""))
          insertPrepStmtObject.addNull();
        else
          insertPrepStmtObject.addString(CommonFunction.checkNull(vo.getBusinessDesc()).trim());
        logger.info(new StringBuilder().append("id.......").append(id).toString());

        if (CommonFunction.checkNull(id).equalsIgnoreCase(""))
          insertPrepStmtObject.addNull();
        else {
          insertPrepStmtObject.addString(CommonFunction.checkNull(id));
        }

        insertPrepStmtObject.setSql(bufInsSql.toString());
        logger.info(new StringBuilder().append("IN saveCustomerAddress() of CorpotateDAOImpl  insert query1 ### ").append(insertPrepStmtObject.printQuery()).toString());
        qryList.add(insertPrepStmtObject);
        status = ConnectionDAO.sqlInsUpdDeletePrepStmt(qryList);
        logger.info(new StringBuilder().append("In saveCustomer......................").append(status).toString());
      }
      else
      {
        StringBuffer bufInsSql = new StringBuffer();

        if (!vo.getStatusCase().equals(""))
        {
          bufInsSql.append("UPDATE gcd_customer_m_temp SET BUSINESS_DESC=? WHERE customer_id=?");
        }
        else
        {
          String table = "gcd_customer_m";
          if (CommonFunction.checkNull(source).trim().equalsIgnoreCase("ED"))
            table = "gcd_customer_m_edit";
          bufInsSql.append(new StringBuilder().append("UPDATE ").append(table).append(" SET BUSINESS_DESC=? WHERE customer_id=?").toString());
        }

        if (CommonFunction.checkNull(vo.getBusinessDesc()).trim().equalsIgnoreCase(""))
          insertPrepStmtObject.addNull();
        else {
          insertPrepStmtObject.addString(vo.getBusinessDesc().trim());
        }
        if (CommonFunction.checkNull(id).equalsIgnoreCase(""))
          insertPrepStmtObject.addString(id);
        else {
          insertPrepStmtObject.addString(id);
        }
        insertPrepStmtObject.setSql(bufInsSql.toString());
        logger.info(new StringBuilder().append("IN saveBusinessDescription() insert query1 ### ").append(insertPrepStmtObject.printQuery()).toString());
        qryList.add(insertPrepStmtObject);

        status = ConnectionDAO.sqlInsUpdDeletePrepStmt(qryList);
        logger.info(new StringBuilder().append("In saveBusinessDescription......................").append(status).toString());
      }
    }
    catch (Exception e) {
      e.printStackTrace();
    }return status;
  }

  public String checkCustomerVerifInit(String[] roleId, String[] applType, String dealId)
  {
    logger.info(new StringBuilder().append("checkCustomerVerifInit for checkCustomerVerifInit....roleId..").append(roleId).append(" dealId ").append(dealId).toString());
    String status = "";
    String status1 = "";
    try
    {
      for (int k = 0; k < roleId.length; k++)
      {
        String checkVerifInitCountQuery = new StringBuilder().append("SELECT R.DEAL_CUSTOMER_ID FROM cr_deal_customer_role R  INNER JOIN cr_deal_verification_dtl  V ON V.DEAL_ID=R.DEAL_ID AND V.ENTITY_TYPE=R.DEAL_CUSTOMER_ROLE_TYPE  INNER JOIN  cr_deal_address_m A ON A.BPID=R.DEAL_CUSTOMER_ID AND A.BPTYPE='CS' AND A.address_id=V.ENTITY_ID WHERE R.DEAL_CUSTOMER_ROLE_ID='").append(StringEscapeUtils.escapeSql(CommonFunction.checkNull(roleId[k])).trim()).append("' AND  R.DEAL_ID='").append(StringEscapeUtils.escapeSql(CommonFunction.checkNull(dealId)).trim()).append("' ").toString();

        logger.info(new StringBuilder().append("checkVerifInitCountQuery: ").append(checkVerifInitCountQuery).toString());
        status = ConnectionDAO.singleReturn(checkVerifInitCountQuery);
        logger.info(new StringBuilder().append("checkVerifInitCount ").append(status).toString());

        String checkVerifInitCustomerQuery = new StringBuilder().append("SELECT R.DEAL_CUSTOMER_ID FROM cr_deal_customer_role R  INNER JOIN cr_deal_verification_dtl  V ON V.DEAL_ID=R.DEAL_ID   WHERE R.DEAL_CUSTOMER_ROLE_ID='").append(StringEscapeUtils.escapeSql(CommonFunction.checkNull(roleId[k])).trim()).append("' AND  R.DEAL_ID='").append(StringEscapeUtils.escapeSql(CommonFunction.checkNull(dealId)).trim()).append("' ").toString();

        logger.info(new StringBuilder().append("checkVerifInitCustomerQuery: ").append(checkVerifInitCustomerQuery).toString());
        status1 = ConnectionDAO.singleReturn(checkVerifInitCustomerQuery);
        logger.info(new StringBuilder().append("checkVerifInitCount ").append(status1).toString());

        if ((!CommonFunction.checkNull(status).equalsIgnoreCase("")) || (!CommonFunction.checkNull(status1).equalsIgnoreCase("")))
        {
          String customerNameQuery = new StringBuilder().append("SELECT C.CUSTOMER_NAME FROM cr_deal_customer_m C WHERE CUSTOMER_ID='").append(StringEscapeUtils.escapeSql(CommonFunction.checkNull(status)).trim()).append("' ").toString();
          if (CommonFunction.checkNull(status).equalsIgnoreCase(""))
          {
            customerNameQuery = new StringBuilder().append("SELECT C.CUSTOMER_NAME FROM cr_deal_customer_m C WHERE CUSTOMER_ID='").append(StringEscapeUtils.escapeSql(CommonFunction.checkNull(status1)).trim()).append("' ").toString();
          }

          logger.info(new StringBuilder().append("customerNameQuery: ").append(customerNameQuery).toString());
          status = ConnectionDAO.singleReturn(customerNameQuery);
          logger.info(new StringBuilder().append("customerName ").append(status).toString());
          return status;
        }

      }

    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
    return status;
  }

  public boolean deleteVerificationInitCustomer(String[] roleId, String[] applType, String dealId)
  {
    logger.info(new StringBuilder().append("deleteVerificationInitCustomer for deleteVerificationInitCustomer....roleId..").append(roleId).append(" dealId ").append(dealId).toString());
    boolean status = false;
    ArrayList qryList = null;
    StringBuffer bufInsUpdSql = null;
    try
    {
      qryList = new ArrayList();
      bufInsUpdSql = new StringBuffer();

      for (int k = 0; k < roleId.length; k++)
      {
        String updateFVIMovementQuery = new StringBuilder().append("update cr_deal_movement_dtl m set DEAL_FORWARDED='0000-00-00 00:00:00',DEAL_FORWARD_USER='' where m.DEAL_STAGE_ID='FVI' AND M.REC_STATUS='A' and m.DEAL_ID='").append(StringEscapeUtils.escapeSql(CommonFunction.checkNull(dealId)).trim()).append("'").toString();
        logger.info(new StringBuilder().append("updateFVIMovementQuery q1 ").append(updateFVIMovementQuery).toString());
        qryList.add(updateFVIMovementQuery);

        String verifIdQuery = new StringBuilder().append("select VERIFICATION_ID from cr_deal_verification_dtl WHERE ENTITY_TYPE=(select DEAL_CUSTOMER_ROLE_TYPE from cr_deal_customer_role where DEAL_CUSTOMER_ROLE_ID=").append(StringEscapeUtils.escapeSql(CommonFunction.checkNull(roleId[k])).trim()).append(" ) AND  DEAL_ID=").append(StringEscapeUtils.escapeSql(CommonFunction.checkNull(dealId)).trim()).append(" and ENTITY_ID IN (SELECT address_id FROM cr_deal_address_m WHERE BPTYPE='CS' AND BPID=(select DEAL_CUSTOMER_ID from cr_deal_customer_role where DEAL_CUSTOMER_ROLE_ID=").append(StringEscapeUtils.escapeSql(CommonFunction.checkNull(roleId[k])).trim()).append(" ))").toString();
        logger.info(new StringBuilder().append("verifIdQuery  ").append(verifIdQuery).toString());
        String verifId = ConnectionDAO.singleReturn(verifIdQuery);
        logger.info(new StringBuilder().append("verifId  ").append(verifId).toString());

        String deleteVerifCapturing = new StringBuilder().append("delete from cr_field_verification_dtl  where VERIFICATION_ID='").append(StringEscapeUtils.escapeSql(CommonFunction.checkNull(verifId).trim())).append("' ").toString();
        logger.info(new StringBuilder().append("delete deleteVerifCapturing q2 ").append(deleteVerifCapturing).toString());
        qryList.add(deleteVerifCapturing);

        String deleteVerification = new StringBuilder().append("delete from cr_deal_verification_dtl where ENTITY_TYPE=(select DEAL_CUSTOMER_ROLE_TYPE from cr_deal_customer_role where DEAL_CUSTOMER_ROLE_ID=").append(StringEscapeUtils.escapeSql(CommonFunction.checkNull(roleId[k])).trim()).append(" ) AND  DEAL_ID=").append(StringEscapeUtils.escapeSql(CommonFunction.checkNull(dealId)).trim()).append(" and ENTITY_ID IN (SELECT address_id FROM cr_deal_address_m WHERE BPTYPE='CS' AND BPID=(select DEAL_CUSTOMER_ID from cr_deal_customer_role where DEAL_CUSTOMER_ROLE_ID=").append(StringEscapeUtils.escapeSql(CommonFunction.checkNull(roleId[k])).trim()).append(" ))").toString();
        logger.info(new StringBuilder().append("delete deleteVerificationInitCustomer q3 ").append(deleteVerification).toString());
        qryList.add(deleteVerification);

        String verifIdForCustQuery = new StringBuilder().append("select VERIFICATION_ID from cr_deal_verification_dtl WHERE DEAL_ID='").append(StringEscapeUtils.escapeSql(CommonFunction.checkNull(dealId)).trim()).append("' and ENTITY_ID IN (select DEAL_CUSTOMER_ID from cr_deal_customer_role where DEAL_CUSTOMER_ROLE_ID='").append(StringEscapeUtils.escapeSql(CommonFunction.checkNull(roleId[k])).trim()).append("' )").toString();
        logger.info(new StringBuilder().append("verifIdForCustQuery  ").append(verifIdForCustQuery).toString());
        String verifIdForCust = ConnectionDAO.singleReturn(verifIdForCustQuery);
        logger.info(new StringBuilder().append("verifIdForCust  ").append(verifIdForCust).toString());

        String deleteVerifCapturingForCust = new StringBuilder().append("delete from cr_field_verification_dtl  where VERIFICATION_ID='").append(StringEscapeUtils.escapeSql(CommonFunction.checkNull(verifIdForCust).trim())).append("' ").toString();
        logger.info(new StringBuilder().append("delete deleteVerifCapturingForCust q2 ").append(deleteVerifCapturingForCust).toString());
        qryList.add(deleteVerifCapturingForCust);

        String deleteVerificationForCust = new StringBuilder().append("delete from cr_deal_verification_dtl where VERIFICATION_ID='").append(StringEscapeUtils.escapeSql(CommonFunction.checkNull(verifIdForCust).trim())).append("' ").toString();
        logger.info(new StringBuilder().append("delete deleteVerificationForCust q3 ").append(deleteVerificationForCust).toString());
        qryList.add(deleteVerificationForCust);
      }

      status = ConnectionDAO.sqlInsUpdDelete(qryList);
      logger.info(new StringBuilder().append("Status of Deletion is =").append(status).toString());
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
    return status;
  }

  public ArrayList<Object> getCustomerRoleList(String loanId, String source)
  {
    logger.info("id in getCustomerRoleList");
    ArrayList list = new ArrayList();
    logger.info(new StringBuilder().append("In , credit management , getCustomerRoleList *************************************    :").append(loanId).toString());
    try {
      String roleTable = "cr_loan_customer_role";
      String custTable = "gcd_customer_m";
      if (CommonFunction.checkNull(source).trim().equalsIgnoreCase("ED"))
      {
        roleTable = "cr_loan_customer_role_edit";
        custTable = "gcd_customer_m_edit";
      }

      String query = new StringBuilder().append(" SELECT LOAN_CUSTOMER_ROLE_ID, LOAN_CUSTOMER_TYPE,GCD_ID,LOAN_EXISTING_CUSTOMER, CUSTOMER_NAME,m.description,c.GUARANTEE_AMOUNT  from ").append(roleTable).append(" c ").append(" left join generic_master m on c.LOAN_CUSTOMER_ROLE_TYPE=m.value and GENERIC_KEY='CUST_ROLE'").append(" left join ").append(custTable).append(" d on c.GCD_ID=CUSTOMER_ID").append(" where LOAN_ID='").append(loanId).append("'").toString();

      logger.info(new StringBuilder().append("getCustomerRoleList:Query ").append(query).toString());
      CreditProcessingCustomerEntryVo vo = null;
      ArrayList roleList = ConnectionDAO.sqlSelect(query);
      logger.info(new StringBuilder().append("getCustomerRoleList ").append(roleList.size()).toString());
      for (int i = 0; i < roleList.size(); i++)
      {
        ArrayList data = (ArrayList)roleList.get(i);
        if (data.size() > 0)
        {
          vo = new CreditProcessingCustomerEntryVo();
          vo.setRoleId(CommonFunction.checkNull(data.get(0)).toString());

          if (CommonFunction.checkNull(data.get(1)).toString().equalsIgnoreCase("C"))
          {
            vo.setApplicantCategory("CORPORATE");
          }
          else if (CommonFunction.checkNull(data.get(1)).toString().equalsIgnoreCase("I"))
          {
            vo.setApplicantCategory("INDIVIDUAL");
          }
          vo.setCustomerId(CommonFunction.checkNull(data.get(2)).toString());
          if (CommonFunction.checkNull(data.get(3)).toString().equalsIgnoreCase("N"))
          {
            vo.setExistingCustomer("NO");
          }
          else if (CommonFunction.checkNull(data.get(3)).toString().equalsIgnoreCase("Y"))
          {
            vo.setExistingCustomer("YES");
          }

          vo.setCustomerName(CommonFunction.checkNull(data.get(4)).toString());

          vo.setApplicantType(CommonFunction.checkNull(data.get(5)).trim().toString());

          if (!CommonFunction.checkNull(data.get(6)).toString().equalsIgnoreCase(""))
          {
            Number reconNum = this.myFormatter.parse(CommonFunction.checkNull(data.get(6)).trim());
            vo.setGuaranteeAmount(this.myFormatter.format(reconNum));
          }
          else {
            vo.setGuaranteeAmount("0.00");
          }

          vo.setFlagForUpdate("updateFlag");
          list.add(vo);
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    return list;
  }

  public String moveFromGCDATCM(String customerId, String applType, String loanId, String tableStatus, String userId, String bDate, String source, String pan, String aadhaar)
  {
    logger.info("In moveFromGCDATCM");
    int statusProc = 0;
    String appQ = "";
    String procval = "";
    boolean checkApp = false;
    if ((applType != null) && (applType.equalsIgnoreCase("PRAPPL")))
    {
      appQ = new StringBuilder().append("select LOAN_CUSTOMER_ID from cr_loan_dtl where LOAN_ID=").append(loanId).append(" and LOAN_CUSTOMER_ID is not NULL").toString();
      logger.info(new StringBuilder().append("In Cr_deal_dtl. query.....................").append(appQ).toString());
      checkApp = ConnectionDAO.checkStatus(appQ);
      if (checkApp)
        return "More than one applicant can not be possible.";
    }
    String table = "cr_loan_customer_role";
    if (CommonFunction.checkNull(source).trim().equalsIgnoreCase("ED")) {
      table = "cr_loan_customer_role_edit";
    }
    String q = new StringBuilder().append("select GCD_ID from ").append(table).append(" where GCD_ID=").append(customerId).append(" and LOAN_ID=").append(loanId).toString();
    logger.info(new StringBuilder().append("Query: ").append(q).toString());
    boolean exist = ConnectionDAO.checkStatus(q);
    if (exist)
    {
      return "Customer being added already exist in this deal.";
    }

    logger.info(new StringBuilder().append("pan------->").append(pan).append(",customerId------->").append(customerId).toString());
    if (!CommonFunction.checkNull(pan).trim().equalsIgnoreCase("")) {
      String custID = new StringBuilder().append("select max(CUSTOMER_ID) from gcd_customer_m where CUSTOMER_STATUS='A' and custmer_pan='").append(pan).append("' ").toString();
      String custID1 = ConnectionDAO.singleReturn(custID);
      logger.info(new StringBuilder().append("custID------->").append(custID).append(",custID1-------->>").append(custID1).append(",customerId--->").append(customerId).toString());
      if (!CommonFunction.checkNull(custID1).trim().equalsIgnoreCase(customerId))
      {
        return "Please select latest customer.";
      }
    }
    if (!CommonFunction.checkNull(aadhaar).trim().equalsIgnoreCase("")) {
      String uCustID = new StringBuilder().append("select max(CUSTOMER_ID) from gcd_customer_m where CUSTOMER_STATUS='A' and UID_NO='").append(aadhaar).append("' ").toString();
      String uCustID1 = ConnectionDAO.singleReturn(uCustID);
      logger.info(new StringBuilder().append("uCustID------->").append(uCustID).append("uCustID1--------->").append(uCustID1).append(",customerId--->").append(customerId).toString());

      if (!CommonFunction.checkNull(uCustID1).trim().equalsIgnoreCase(customerId))
      {
        return "Please select latest customer.";
      }

    }

    ArrayList in = new ArrayList();
    ArrayList out = new ArrayList();
    ArrayList outMessages = new ArrayList();
    String s1 = "";
    String s2 = "";
    try
    {
      in.add(CommonFunction.checkNull(source).trim());
      in.add(loanId);
      in.add(customerId);
      in.add(applType);
      in.add(tableStatus);
      in.add(userId);
      String date = CommonFunction.changeFormat(bDate);
      if (date != null)
        in.add(date);
      out.add(s1);
      out.add(s2);
      logger.info(new StringBuilder().append("Gcd_Customer_Link_AT_CM(").append(in.toString()).append(",").append(out.toString()).append(")").toString());
      outMessages = (ArrayList)ConnectionDAO.callSP("Gcd_Customer_Link_AT_CM", in, out);
      s1 = CommonFunction.checkNull(outMessages.get(0));
      s2 = CommonFunction.checkNull(outMessages.get(1));
      logger.info(new StringBuilder().append("s1: ").append(s1).toString());
      logger.info(new StringBuilder().append("s2: ").append(s2).toString());
      procval = s2;
    }
    catch (Exception e)
    {
      logger.info(new StringBuilder().append("Exception In approve: ").append(e).toString());
    }
    finally
    {
      in = null;
      out = null;
      outMessages = null;
    }

    return procval;
  }

  public boolean deleteCustomerRoleAtCM(String[] roleId, String[] applType, String loanId, String source)
  {
    logger.info("deleteCustomerRoleAtCM() OF CorpotateDAOImpl");
    boolean status = false;
    try
    {
      ArrayList qryList = new ArrayList();
      String table = "cr_loan_customer_role";
      if (CommonFunction.checkNull(source).trim().equalsIgnoreCase("ED"))
        table = "cr_loan_customer_role_edit";
      for (int k = 0; k < roleId.length; k++)
      {
        String query2 = new StringBuilder().append("delete from ").append(table).append(" where LOAN_CUSTOMER_ROLE_ID=?").toString();
        PrepStmtObject insertPrepStmtObject = new PrepStmtObject();
        if (CommonFunction.checkNull(roleId[k]).trim().equalsIgnoreCase(""))
          insertPrepStmtObject.addNull();
        else
          insertPrepStmtObject.addString(CommonFunction.checkNull(roleId[k]).trim());
        insertPrepStmtObject.setSql(query2.toString());
        logger.info(new StringBuilder().append("deleteroleList() OF CorpotateDAOImpl query   :   ").append(insertPrepStmtObject.printQuery()).toString());
        qryList.add(insertPrepStmtObject);
      }

      status = ConnectionDAO.sqlInsUpdDeletePrepStmt(qryList);
      logger.info(new StringBuilder().append("Status of Updation is   ::  ").append(status).toString());
    }
    catch (Exception e) {
      e.printStackTrace();
    }return status;
  }

  public boolean saveCustomerFromDeal(String loanId, String source)
  {
    logger.info(new StringBuilder().append("In saveCustomerFromDeal mod").append(loanId).toString());

    ArrayList qryList = new ArrayList();
    ArrayList qryList1 = new ArrayList();
    boolean status = false;
    try {
      StringBuilder bufInsSql = null;

      StringBuilder checkQ = null;
      StringBuilder count = new StringBuilder();

      checkQ = new StringBuilder();
      PrepStmtObject insertPrepStmtObject = new PrepStmtObject();

      String table = "cr_loan_customer_role";
      if (CommonFunction.checkNull(source).trim().equalsIgnoreCase("ED"))
        table = "cr_loan_customer_role_edit";
      StringBuilder qry = new StringBuilder();
      qry.append(new StringBuilder().append("DELETE FROM ").append(table).append(" WHERE LOAN_ID='").append(CommonFunction.checkNull(loanId).trim()).append("'").toString());
      qryList1.add(qry);
      ConnectionDAO.sqlInsUpdDelete(qryList1);
      qry = null;

      bufInsSql = new StringBuilder();
      bufInsSql.append(new StringBuilder().append("insert into ").append(table).append("(LOAN_ID,GCD_ID,LOAN_CUSTOMER_ROLE_TYPE,LOAN_CUSTOMER_TYPE,LOAN_EXISTING_CUSTOMER,GUARANTEE_AMOUNT,REC_STATUS,MAKER_ID,MAKER_DATE)").toString());
      bufInsSql.append(new StringBuilder().append("SELECT ").append(loanId).append(",GCD_ID,DEAL_CUSTOMER_ROLE_TYPE,DEAL_CUSTOMER_TYPE,DEAL_EXISTING_CUSTOMER,GUARANTEE_AMOUNT,STATUS,MAKER_ID,MAKER_DATE FROM cr_deal_customer_role WHERE DEAL_ID=(SELECT LOAN_DEAL_ID FROM CR_LOAN_DTL WHERE LOAN_ID='").append(CommonFunction.checkNull(loanId).trim()).append("')").toString());

      insertPrepStmtObject.setSql(bufInsSql.toString());
      logger.info(new StringBuilder().append("IN saveCustomerFromDeal()del insert query1 ### ").append(insertPrepStmtObject.printQuery()).toString());
      qryList.add(insertPrepStmtObject);
      checkQ = null;
      bufInsSql = null;

      status = ConnectionDAO.sqlInsUpdDeletePrepStmt(qryList);

      checkQ = null;
      bufInsSql = null;
      count = null;
    }
    catch (Exception e) {
      e.printStackTrace();
    }
    logger.info(new StringBuilder().append("In saveCustomerFromDeal......................").append(status).toString());
    return status;
  }

  public ArrayList<CustomerSaveVo> getCustBankDetails(String code, String statusCase, String updateInMaker, String pageStatuss, String updateFlag, String cuaStatus, String source)
  {
    String tableName = "";
    logger.info("In getCustBankDetails() of CorpotateDAOImpl");
    ArrayList list = new ArrayList();
    if (((pageStatuss != null) && (pageStatuss.equals("fromLeads"))) || ((updateFlag != null) && (updateFlag.equals("updateFlag"))) || ((updateFlag != null) && (updateFlag.equals("notEdit"))))
    {
      tableName = "cr_deal_cust_bank_details_m";
      try
      {
        String query = new StringBuilder().append("SELECT b.CUSTOMER_ID,b.BANK_ID,b.BANK_BRANCH_ID,c.BANK_NAME ,d.BANK_BRANCH_NAME,b.BANK_ACCOUNT,d.BRANCH_MICR_CODE,d.BRANCH_IFCS_CODE   from ").append(tableName).append(" b ").append("left join COM_BANK_M c on b.BANK_ID=c.BANK_ID left join COM_BANKBRANCH_M d on d.BANK_BRANCH_ID=b.BANK_BRANCH_ID ").append("where b.CUSTOMER_ID=").append(code).toString();

        logger.info(new StringBuilder().append("in getCustBankDetails Query : ").append(query).toString());
        ArrayList custBankDetails = ConnectionDAO.sqlSelect(query);

        for (int i = 0; i < custBankDetails.size(); i++)
        {
          ArrayList data = (ArrayList)custBankDetails.get(i);
          if (data.size() > 0)
          {
            CustomerSaveVo vo = new CustomerSaveVo();
            vo.setCustomerId(CommonFunction.checkNull(data.get(0)).toString());
            vo.setLbxBankID(CommonFunction.checkNull(data.get(1)).toString());
            vo.setLbxBranchID(CommonFunction.checkNull(data.get(2)).toString());
            vo.setBankCode(CommonFunction.checkNull(data.get(3)).toString());
            vo.setBankBranchName(CommonFunction.checkNull(data.get(4)).toString());
            vo.setAccountNo(CommonFunction.checkNull(data.get(5)).toString());
            vo.setMicrCode(CommonFunction.checkNull(data.get(6)).toString());
            vo.setIfscCode(CommonFunction.checkNull(data.get(7)).toString());

            list.add(vo);
          }
        }
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
    else
    {
      if (((updateInMaker != null) && (updateInMaker.equals("updateInMaker"))) || ((statusCase != null) && (!statusCase.equals(""))))
      {
        if ((statusCase != null) && (statusCase.equalsIgnoreCase("Approved")) && ((updateInMaker == null) || (updateInMaker.equals(""))))
        {
          tableName = "cust_bank_details_m";
          if (CommonFunction.checkNull(source).trim().equalsIgnoreCase("ED"))
            tableName = "cust_bank_details_m_edit";
        }
        else {
          tableName = "cust_bank_details_tmp";
        }
      }
      else {
        tableName = "cust_bank_details_m";
        if (CommonFunction.checkNull(source).trim().equalsIgnoreCase("ED")) {
          tableName = "cust_bank_details_m_edit";
        }
      }
      if (CommonFunction.checkNull(cuaStatus).equalsIgnoreCase("CUA"))
      {
        tableName = "cust_bank_details_tmp";
      }

      try
      {
        logger.info(new StringBuilder().append("in Else getCustBankDetails tableName ").append(tableName).toString());
        String query = new StringBuilder().append("SELECT b.CUSTOMER_ID,b.BANK_ID,b.BANK_BRANCH_ID,c.BANK_NAME ,d.BANK_BRANCH_NAME,b.BANK_ACCOUNT,d.BRANCH_MICR_CODE,d.BRANCH_IFCS_CODE   from ").append(tableName).append(" b ").append("left join COM_BANK_M c on b.BANK_ID=c.BANK_ID left join COM_BANKBRANCH_M d on d.BANK_BRANCH_ID=b.BANK_BRANCH_ID ").append("where b.CUSTOMER_ID=").append(code).toString();

        logger.info(new StringBuilder().append("in getCustBankDetails()Else Part :   ").append(query).toString());
        ArrayList addressDetails = ConnectionDAO.sqlSelect(query);

        logger.info(new StringBuilder().append("getAddressDetails ").append(addressDetails.size()).toString());
        for (int i = 0; i < addressDetails.size(); i++)
        {
          ArrayList data = (ArrayList)addressDetails.get(i);
          if (data.size() > 0)
          {
            CustomerSaveVo vo = new CustomerSaveVo();
            vo.setCustomerId(CommonFunction.checkNull(data.get(0)).toString());
            vo.setLbxBankID(CommonFunction.checkNull(data.get(1)).toString());
            vo.setLbxBranchID(CommonFunction.checkNull(data.get(2)).toString());
            vo.setBankCode(CommonFunction.checkNull(data.get(3)).toString());
            vo.setBankBranchName(CommonFunction.checkNull(data.get(4)).toString());
            vo.setAccountNo(CommonFunction.checkNull(data.get(5)).toString());
            vo.setMicrCode(CommonFunction.checkNull(data.get(6)).toString());
            vo.setIfscCode(CommonFunction.checkNull(data.get(7)).toString());
            list.add(vo);
          }
        }
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
    return list;
  }

  public boolean saveCustBankDetails(CustomerSaveVo vo, int id, String source)
  {
    logger.info("DaoIMPL saveCustBankDetails");
    boolean status = false;
    ArrayList qryList = new ArrayList();
    PrepStmtObject insertPrepStmtObject = new PrepStmtObject();
    try {
      if (((vo.getPageStat() != null) && (vo.getPageStat().equals("fromLeads"))) || ((vo.getUpdateFlag() != null) && (vo.getUpdateFlag().length() > 0)))
      {
        logger.info("At the CP-DC saveCustBankDetails");

        logger.info("In insert saveCustBankDetails");
        StringBuffer bufInsSql = new StringBuffer();
        bufInsSql.append("insert into cr_deal_cust_bank_details_m(CUSTOMER_ID,BANK_ID,BANK_BRANCH_ID,BANK_ACCOUNT,REC_STATUS,MAKER_ID,MAKER_DATE)");
        bufInsSql.append(" values ( ");
        bufInsSql.append(" ?,");
        bufInsSql.append(" ?,");
        bufInsSql.append(" ?,");
        bufInsSql.append(" ?,");
        bufInsSql.append(" ?,");
        bufInsSql.append(" ?,");
        bufInsSql.append(new StringBuilder().append(" DATE_ADD(STR_TO_DATE(?, '").append(this.dateFormatWithTime).append("'),INTERVAL CURTIME() HOUR_SECOND))").toString());

        if (CommonFunction.checkNull(Integer.valueOf(id)).trim().equalsIgnoreCase(""))
          insertPrepStmtObject.addNull();
        else {
          insertPrepStmtObject.addInt(id);
        }
        if (CommonFunction.checkNull(vo.getLbxBankID()).trim().equalsIgnoreCase(""))
          insertPrepStmtObject.addInt(0L);
        else {
          insertPrepStmtObject.addInt(Integer.parseInt(vo.getLbxBankID()));
        }
        if (CommonFunction.checkNull(vo.getLbxBranchID()).trim().equalsIgnoreCase(""))
          insertPrepStmtObject.addInt(0L);
        else {
          insertPrepStmtObject.addInt(Integer.parseInt(vo.getLbxBranchID()));
        }
        if (CommonFunction.checkNull(vo.getAccountNo()).trim().equalsIgnoreCase(""))
          insertPrepStmtObject.addNull();
        else {
          insertPrepStmtObject.addString(vo.getAccountNo());
        }

        insertPrepStmtObject.addString("A");

        if (CommonFunction.checkNull(vo.getMakerId()).trim().equalsIgnoreCase(""))
          insertPrepStmtObject.addNull();
        else {
          insertPrepStmtObject.addString(vo.getMakerId().trim());
        }
        if (CommonFunction.checkNull(vo.getMakerDate()).trim().equalsIgnoreCase(""))
          insertPrepStmtObject.addNull();
        else {
          insertPrepStmtObject.addString(CommonFunction.checkNull(vo.getMakerDate()).trim());
        }

        insertPrepStmtObject.setSql(bufInsSql.toString());
        logger.info(new StringBuilder().append("IN saveCustBankDetails() insert query1 ### ").append(insertPrepStmtObject.printQuery()).toString());
        qryList.add(insertPrepStmtObject);
        status = ConnectionDAO.sqlInsUpdDeletePrepStmt(qryList);
        logger.info(new StringBuilder().append("In saveCustBankDetails......................").append(status).toString());
      }
      else
      {
        logger.info("In GCD.,saveCustBankDetails().......");
        int maxId = 0;
        StringBuffer bufInsSql = new StringBuffer();
        int checkTable = 0;
        logger.info(new StringBuilder().append("vo.getStatusCase()").append(vo.getStatusCase()).toString());
        if (!vo.getStatusCase().equals(""))
        {
          String query3 = "Select max(CUST_BANK_ID) from cust_bank_details_tmp for update";
          String maxid = ConnectionDAO.singleReturn(query3);
          if ((maxid == null) || (maxid == ""))
          {
            maxId = 1;
          }
          else
          {
            maxId = Integer.parseInt(maxid) + 1;
          }
          logger.info(new StringBuilder().append("maxId : ").append(maxId).toString());
          checkTable++;
          bufInsSql.append("insert into cust_bank_details_tmp(CUST_BANK_ID,CUSTOMER_ID,BANK_ID,BANK_BRANCH_ID,BANK_ACCOUNT,REC_STATUS)");
          bufInsSql.append(" values ( ");
        }
        else
        {
          bufInsSql.append("insert into cust_bank_details_m(CUSTOMER_ID,BANK_ID,BANK_BRANCH_ID,BANK_ACCOUNT,REC_STATUS,MAKER_ID,MAKER_DATE)");
          bufInsSql.append(" values ( ");
        }

        if (checkTable != 0)
        {
          logger.info(new StringBuilder().append("checkTable!=0").append(checkTable).toString());
          bufInsSql.append(" ?,");
          bufInsSql.append(" ?,");
          bufInsSql.append(" ?,");
          bufInsSql.append(" ?,");
          bufInsSql.append(" ?,");
          bufInsSql.append(" ?)");
        }
        if (checkTable == 0)
        {
          logger.info(new StringBuilder().append("checkTable==0").append(checkTable).toString());
          bufInsSql.append(" ?,");
          bufInsSql.append(" ?,");
          bufInsSql.append(" ?,");
          bufInsSql.append(" ?,");
          bufInsSql.append(" ?,");
          bufInsSql.append(" ?,");
          bufInsSql.append(new StringBuilder().append(" DATE_ADD(STR_TO_DATE(?, '").append(this.dateFormatWithTime).append("'),INTERVAL CURTIME() HOUR_SECOND))").toString());
        }
        if (!vo.getStatusCase().equals(""))
        {
          insertPrepStmtObject.addInt(maxId);
        }

        if (CommonFunction.checkNull(Integer.valueOf(id)).trim().equalsIgnoreCase(""))
          insertPrepStmtObject.addNull();
        else {
          insertPrepStmtObject.addInt(id);
        }
        if (CommonFunction.checkNull(vo.getLbxBankID()).trim().equalsIgnoreCase(""))
          insertPrepStmtObject.addInt(0L);
        else {
          insertPrepStmtObject.addInt(Integer.parseInt(vo.getLbxBankID()));
        }
        if (CommonFunction.checkNull(vo.getLbxBranchID()).trim().equalsIgnoreCase(""))
          insertPrepStmtObject.addInt(0L);
        else {
          insertPrepStmtObject.addInt(Integer.parseInt(vo.getLbxBranchID()));
        }
        if (CommonFunction.checkNull(vo.getAccountNo()).trim().equalsIgnoreCase(""))
          insertPrepStmtObject.addNull();
        else {
          insertPrepStmtObject.addString(vo.getAccountNo());
        }
        insertPrepStmtObject.addString("A");

        if (checkTable == 0)
        {
          if (CommonFunction.checkNull(vo.getMakerId()).trim().equalsIgnoreCase(""))
            insertPrepStmtObject.addNull();
          else
            insertPrepStmtObject.addString(vo.getMakerId().trim());
          if (CommonFunction.checkNull(vo.getMakerDate()).trim().equalsIgnoreCase(""))
            insertPrepStmtObject.addNull();
          else
            insertPrepStmtObject.addString(CommonFunction.checkNull(vo.getMakerDate()).trim());
        }
        insertPrepStmtObject.setSql(bufInsSql.toString());
        logger.info(new StringBuilder().append("IN CustBankDeatil Save() insert query1 ### ").append(insertPrepStmtObject.printQuery()).toString());
        qryList.add(insertPrepStmtObject);

        status = ConnectionDAO.sqlInsUpdDeletePrepStmt(qryList);
        if ((CommonFunction.checkNull(source).trim().equalsIgnoreCase("ED")) && (status))
        {
          String q1 = new StringBuilder().append("select max(CUST_BANK_ID) from cust_bank_details_m where CUSTOMER_ID='").append(id).append("' for update ").toString();
          String bank_id = ConnectionDAO.singleReturn(q1);
          String q2 = new StringBuilder().append("INSERT INTO cust_bank_details_m_edit select *  from cust_bank_details_m where CUST_BANK_ID=").append(bank_id).toString();
          PrepStmtObject stmt = new PrepStmtObject();
          stmt.setSql(q2);
          ArrayList list = new ArrayList();
          list.add(stmt);
          status = ConnectionDAO.sqlInsUpdDeletePrepStmt(list);
          logger.info(new StringBuilder().append("In saveCustomerReference......................").append(status).toString());

          String q3 = new StringBuilder().append("delete from cust_bank_details_m where CUST_BANK_ID=").append(bank_id).toString();
          PrepStmtObject stmt2 = new PrepStmtObject();
          stmt2.setSql(q3);
          ArrayList list2 = new ArrayList();
          list2.add(stmt2);
          status = ConnectionDAO.sqlInsUpdDeletePrepStmt(list2);
        }

        logger.info(new StringBuilder().append("In saveBusinessActivity......................").append(status).toString());
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    return status;
  }

  public int updateCustBankDetails(CustomerSaveVo vo, int id, String recStatus, String statusCase, String updateFlag, String pageStatuss, String source)
  {
    int status = 0;
    boolean qryStatus = false;
    String tableName = "";
    String subQuery = null;
    ArrayList qryList = new ArrayList();
    PrepStmtObject insertPrepStmtObject = new PrepStmtObject();
    try {
      logger.info(new StringBuilder().append("updateCustBankDetails id :").append(id).toString());
      if (((pageStatuss != null) && (pageStatuss.equals("fromLeads"))) || ((updateFlag != null) && (updateFlag.equals("updateFlag"))))
      {
        logger.info("In Credit Processing , Customer Entry.,updateCustBankDetails().......");
        String query = new StringBuilder().append("update cr_deal_cust_bank_details_m set BANK_ID=?, BANK_BRANCH_ID=?, BANK_ACCOUNT=?, REC_STATUS=?, MAKER_ID=?,MAKER_DATE=DATE_ADD(STR_TO_DATE(?,'").append(this.dateFormatWithTime).append("'),INTERVAL CURTIME() HOUR_SECOND) ").append("where customer_id=?").toString();

        if (CommonFunction.checkNull(vo.getLbxBankID()).trim().equalsIgnoreCase(""))
          insertPrepStmtObject.addInt(0L);
        else {
          insertPrepStmtObject.addInt(Integer.parseInt(vo.getLbxBankID()));
        }
        if (CommonFunction.checkNull(vo.getLbxBranchID()).trim().equalsIgnoreCase(""))
          insertPrepStmtObject.addInt(0L);
        else {
          insertPrepStmtObject.addInt(Integer.parseInt(vo.getLbxBranchID()));
        }
        if (CommonFunction.checkNull(vo.getAccountNo()).trim().equalsIgnoreCase(""))
          insertPrepStmtObject.addNull();
        else {
          insertPrepStmtObject.addString(vo.getAccountNo());
        }
        if (CommonFunction.checkNull(vo.getRecStatus()).trim().equalsIgnoreCase(""))
          insertPrepStmtObject.addNull();
        else {
          insertPrepStmtObject.addString(vo.getRecStatus().trim());
        }
        if (CommonFunction.checkNull(vo.getMakerId()).trim().equalsIgnoreCase(""))
          insertPrepStmtObject.addNull();
        else {
          insertPrepStmtObject.addString(vo.getMakerId().trim());
        }
        if (CommonFunction.checkNull(vo.getMakerDate()).trim().equalsIgnoreCase(""))
          insertPrepStmtObject.addNull();
        else {
          insertPrepStmtObject.addString(vo.getMakerDate().trim());
        }
        if (CommonFunction.checkNull(Integer.valueOf(id)).trim().equalsIgnoreCase(""))
          insertPrepStmtObject.addNull();
        else {
          insertPrepStmtObject.addInt(id);
        }
        insertPrepStmtObject.setSql(query);
        logger.info(new StringBuilder().append("IN updateCustBankDetails() update query1 ### ").append(insertPrepStmtObject.printQuery()).toString());
        qryList.add(insertPrepStmtObject);
        qryStatus = ConnectionDAO.sqlInsUpdDeletePrepStmt(qryList);
      }
      else
      {
        logger.info("In GCD.,updateCustBankDetails().......");
        if ((statusCase != null) && (statusCase.length() > 0))
        {
          tableName = "cust_bank_details_tmp";
        }
        else
        {
          tableName = "cust_bank_details_m";
          if (CommonFunction.checkNull(source).trim().equalsIgnoreCase("ED")) {
            tableName = "cust_bank_details_m_edit";
          }
        }

        if ((tableName.equalsIgnoreCase("cust_bank_details_m")) || (tableName.equalsIgnoreCase("cust_bank_details_m_edit")))
        {
          subQuery = new StringBuilder().append(",MAKER_ID='").append(CommonFunction.checkNull(vo.getMakerId())).append("',").append("MAKER_DATE=DATE_ADD(STR_TO_DATE('").append(CommonFunction.checkNull(vo.getMakerDate())).append("','").append(this.dateFormatWithTime).append("'),INTERVAL CURTIME() HOUR_SECOND)").toString();
        }
        else {
          subQuery = "";
        }
        String query = new StringBuilder().append("update ").append(tableName).append(" set BANK_ID=?, BANK_BRANCH_ID=?, BANK_ACCOUNT=?,REC_STATUS=? ").append(subQuery).append("  where CUSTOMER_ID=?").toString();

        if (CommonFunction.checkNull(vo.getLbxBankID()).trim().equalsIgnoreCase(""))
          insertPrepStmtObject.addInt(0L);
        else {
          insertPrepStmtObject.addInt(Integer.parseInt(vo.getLbxBankID()));
        }
        if (CommonFunction.checkNull(vo.getLbxBranchID()).trim().equalsIgnoreCase(""))
          insertPrepStmtObject.addInt(0L);
        else {
          insertPrepStmtObject.addInt(Integer.parseInt(vo.getLbxBranchID()));
        }
        if (CommonFunction.checkNull(vo.getAccountNo()).trim().equalsIgnoreCase(""))
          insertPrepStmtObject.addNull();
        else {
          insertPrepStmtObject.addString(vo.getAccountNo());
        }
        insertPrepStmtObject.addString("A");

        if (CommonFunction.checkNull(Integer.valueOf(id)).trim().equalsIgnoreCase(""))
          insertPrepStmtObject.addNull();
        else {
          insertPrepStmtObject.addInt(id);
        }
        insertPrepStmtObject.setSql(query.toString());
        logger.info(new StringBuilder().append("IN updateCustBankDetails() update query1 ### insertPrepStmtObject ").append(insertPrepStmtObject.printQuery()).toString());
        qryList.add(insertPrepStmtObject);

        qryStatus = ConnectionDAO.sqlInsUpdDeletePrepStmt(qryList);
      }
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      subQuery = "";
    }
    if (qryStatus)
    {
      status = 1;
    }
    return status;
  }

  public boolean deleteCustomerDocsAtCM(String[] roleId, String[] applType, String loanId, String source)
  {
    logger.info(new StringBuilder().append("deleteCustomerDocsAtCM for deleteCustomerDocsAtCM....roleId..").append(roleId).append(" loanId ").append(loanId).toString());
    boolean status = false;
    ArrayList qryList = null;
    StringBuffer bufInsUpdSql = null;
    try
    {
      qryList = new ArrayList();
      bufInsUpdSql = new StringBuffer();

      String table = "cr_document_dtl";
      if (CommonFunction.checkNull(source).trim().equalsIgnoreCase("ED"))
        table = "cr_document_dtl_edit";
      for (int k = 0; k < roleId.length; k++)
      {
        String deleteChatge = new StringBuilder().append("delete from ").append(table).append(" where TXN_TYPE='LIM' AND STAGE_ID='PRD' AND DOC_TYPE=(select LOAN_CUSTOMER_ROLE_TYPE from cr_loan_customer_role where LOAN_CUSTOMER_ROLE_ID=").append(StringEscapeUtils.escapeSql(CommonFunction.checkNull(roleId[k])).trim()).append(" ) AND  TXNID=").append(StringEscapeUtils.escapeSql(CommonFunction.checkNull(loanId)).trim()).append(" and ENTITY_ID=(select GCD_ID from cr_loan_customer_role where LOAN_CUSTOMER_ROLE_ID=").append(StringEscapeUtils.escapeSql(CommonFunction.checkNull(roleId[k])).trim()).append(" )").toString();
        logger.info(new StringBuilder().append("delete query:deletedocs ").append(deleteChatge).toString());
        qryList.add(deleteChatge);
      }

      status = ConnectionDAO.sqlInsUpdDelete(qryList);
      logger.info(new StringBuilder().append("Status of Deletion is =").append(status).toString());
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
    return status;
  }

  public boolean checkApplExistance(String[] roleId, String dealId)
  {
    boolean status = false;
    int count = 0;
    String role = "";
    for (int i = 0; i < roleId.length; i++)
      role = new StringBuilder().append("'").append(roleId[i]).append("',").toString();
    role = role.substring(0, role.length() - 1);
    String query = new StringBuilder().append("select count(1) from cr_deal_customer_role where deal_customer_role_id in(").append(role).append(") and deal_id='").append(dealId.trim()).append("' and DEAL_CUSTOMER_ROLE_TYPE='PRAPPL'").toString();
    count = Integer.parseInt(ConnectionDAO.singleReturn(query));
    if (count > 0)
      status = true;
    return status;
  }

  public boolean updateCustomerPRSDocsAtCM(String[] roleId, String[] applType, String loanId)
  {
    logger.info(new StringBuilder().append("updateCustomerPRSDocsAtCM for updateCustomerPRSDocsAtCM....roleId..").append(roleId).append(" loanId ").append(loanId).toString());
    boolean status = false;
    ArrayList qryList = null;
    StringBuffer bufInsUpdSql = null;
    try
    {
      qryList = new ArrayList();
      bufInsUpdSql = new StringBuffer();

      for (int k = 0; k < roleId.length; k++)
      {
        String deleteChatge = new StringBuilder().append("update cr_document_dtl set REC_STATUS='X' where TXN_TYPE='DC' AND STAGE_ID='PRS' AND  TXNID=(select DISTINCT LOAN_DEAL_ID from cr_loan_dtl where loan_id='").append(StringEscapeUtils.escapeSql(CommonFunction.checkNull(loanId)).trim()).append("') and ENTITY_ID=(SELECT DISTINCT DEAL_CUSTOMER_ID FROM cr_deal_customer_role WHERE GCD_ID=(select DISTINCT GCD_ID from cr_loan_customer_role where LOAN_CUSTOMER_ROLE_ID=").append(StringEscapeUtils.escapeSql(CommonFunction.checkNull(roleId[k])).trim()).append(") )").toString();
        logger.info(new StringBuilder().append("delete query:deletedocs ").append(deleteChatge).toString());
        qryList.add(deleteChatge);
      }

      status = ConnectionDAO.sqlInsUpdDelete(qryList);
      logger.info(new StringBuilder().append("Status of updation docs prs is =").append(status).toString());
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
    return status;
  }

  public ArrayList<CustomerSaveVo> copyAddressAtCM(String addressId)
  {
    logger.info("In copyAddressAtCM()");
    ArrayList list = new ArrayList();
    try
    {
      StringBuilder query = new StringBuilder();
      query.append(new StringBuilder().append(" select distinct a.ADDRESS_TYPE,a.ADDRESS_LINE1,a.ADDRESS_LINE2,a.ADDRESS_LINE3,a.COUNTRY, b.COUNTRY_DESC,a.STATE,c.STATE_DESC,a.TAHSIL,a.DISTRICT,d.DISTRICT_DESC,a.PINCODE,a.PRIMARY_PHONE,a.ALTERNATE_PHONE, a.TOLLFREE_NUMBER,a.FAX,a.LANDMARK,a.NO_OF_YEARS,a.NO_OF_MONTHS,a.BRANCH_DISTANCE,a.COMMUNICATION_ADDRESS,a.ADDRESS_DETAIL  from com_address_m a join com_country_m b on(b.COUNTRY_ID=a.COUNTRY) join com_state_m c on(c.STATE_ID=a.STATE)  join com_district_m d on(d.DISTRICT_ID=a.DISTRICT) where a.ADDRESS_ID='").append(addressId.trim()).append("'").toString());

      logger.info(new StringBuilder().append("In copyAddress()   query  :  ").append(query).toString());
      CustomerSaveVo vo = null;
      ArrayList mainList = ConnectionDAO.sqlSelect(query.toString());
      query = null;
      logger.info(new StringBuilder().append("defaultcountry() ").append(mainList.size()).toString());
      int size = mainList.size();
      for (int i = 0; i < size; i++)
      {
        ArrayList data = (ArrayList)mainList.get(i);
        if (data.size() > 0)
        {
          vo = new CustomerSaveVo();
          vo.setAddr_type(CommonFunction.checkNull(data.get(0)).trim());
          vo.setAddr1(CommonFunction.checkNull(data.get(1)).trim());
          vo.setAddr2(CommonFunction.checkNull(data.get(2)).trim());
          vo.setAddr3(CommonFunction.checkNull(data.get(3)).trim());
          vo.setTxtCountryCode(CommonFunction.checkNull(data.get(4)).trim());
          vo.setCountry(CommonFunction.checkNull(data.get(5)).trim());
          vo.setTxtStateCode(CommonFunction.checkNull(data.get(6)).trim());
          vo.setState(CommonFunction.checkNull(data.get(7)).trim());
          vo.setTahsil(CommonFunction.checkNull(data.get(8)).trim());
          vo.setTxtDistCode(CommonFunction.checkNull(data.get(9)).trim());
          vo.setDist(CommonFunction.checkNull(data.get(10)).trim());
          vo.setPincode(CommonFunction.checkNull(data.get(11)).trim());
          vo.setPrimaryPhoneNo(CommonFunction.checkNull(data.get(12)).trim());
          vo.setAlternatePhoneNo(CommonFunction.checkNull(data.get(13)).trim());
          vo.setTollfreeNo(CommonFunction.checkNull(data.get(14)).trim());
          vo.setFaxNo(CommonFunction.checkNull(data.get(15)).trim());
          vo.setLandMark(CommonFunction.checkNull(data.get(16)).trim());
          vo.setNoYears(CommonFunction.checkNull(data.get(17)).trim());
          vo.setNoMonths(CommonFunction.checkNull(data.get(18)).trim());
          vo.setDistanceBranch(CommonFunction.checkNull(data.get(19)).trim());
          vo.setCommunicationAddress(CommonFunction.checkNull(data.get(20)).trim());
          vo.setAddDetails(CommonFunction.checkNull(data.get(21)).trim());
          list.add(vo);
        }
      }
    }
    catch (Exception e) {
      e.printStackTrace();
    }return list;
  }

  public boolean getFirstNameStatus(CustomerSaveVo vo)
  {
    logger.info("In getFirstNameStatus..");
    boolean checkApp = false;
    String bpId = null;
    if (CommonFunction.checkNull(vo.getBp_id()).trim().equalsIgnoreCase(""))
      bpId = vo.getBp_id1().trim();
    else {
      bpId = vo.getBp_id().trim();
    }
    if (((vo.getPageStatus() != null) && (vo.getPageStatus().equals("fromLeads"))) || ((vo.getUpdateFlag() != null) && (vo.getUpdateFlag().length() > 0)))
    {
      String appQ = new StringBuilder().append("select F_NAME from cr_deal_reference_m where BPID='").append(bpId).append("' and BPTYPE = '").append(vo.getBp_type()).append("' AND F_NAME='").append(vo.getFirstName()).append("' ").toString();
      logger.info(new StringBuilder().append("In getFirstNameStatus(cr_deal_reference_m) query.....................").append(appQ).toString());
      checkApp = ConnectionDAO.checkStatus(appQ);
      appQ = null;
      bpId = null;
    }
    else if (!vo.getStatusCase().equals(""))
    {
      String appQ = new StringBuilder().append("select F_NAME from com_reference_m_temp where BPID='").append(bpId).append("' and BPTYPE = '").append(vo.getBp_type()).append("' AND F_NAME='").append(vo.getFirstName()).append("' ").toString();
      logger.info(new StringBuilder().append("In getFirstNameStatus(com_reference_m_temp) query.....................").append(appQ).toString());
      checkApp = ConnectionDAO.checkStatus(appQ);
      appQ = null;
      bpId = null;
    }
    else
    {
      String appQ = new StringBuilder().append("select F_NAME from com_reference_m where BPID='").append(bpId).append("' and BPTYPE = '").append(vo.getBp_type()).append("' AND F_NAME='").append(vo.getFirstName()).append("' ").toString();
      logger.info(new StringBuilder().append("In getFirstNameStatus(com_reference_m) query.....................").append(appQ).toString());
      checkApp = ConnectionDAO.checkStatus(appQ);
      appQ = null;
      bpId = null;
    }

    return checkApp;
  }

  public String getPanCondition()
  {
    logger.info("in getPanCondition() ");
    StringBuilder bufInsSql = new StringBuilder();
    bufInsSql.append(" SELECT PARAMETER_VALUE FROM parameter_mst WHERE PARAMETER_KEY='PAN_NO_FLAG' ");
    logger.info(new StringBuilder().append("Query  to panCondition    : ").append(bufInsSql.toString()).toString());
    String panCondition = "";
    try
    {
      panCondition = ConnectionDAO.singleReturn(bufInsSql.toString());
    }
    catch (Exception e) {
      e.printStackTrace();
    }logger.info(new StringBuilder().append("panCondition   :   ").append(panCondition).toString());
    return panCondition;
  }

  public String checkCustomerVerifInitAtCM(String[] roleId, String[] applType, String loanId)
  {
    logger.info(new StringBuilder().append("checkCustomerVerifInitAtCM for ..roleId..").append(roleId).append(" loanId ").append(loanId).toString());
    String status = "";
    String status1 = "";
    try
    {
      for (int k = 0; k < roleId.length; k++)
      {
        String checkVerifInitCountQuery = new StringBuilder().append("SELECT R.GCD_ID FROM cr_loan_customer_role R  INNER JOIN cr_deal_verification_dtl  V ON V.LOAN_ID=R.LOAN_ID AND V.ENTITY_TYPE=R.LOAN_CUSTOMER_ROLE_TYPE  INNER JOIN  com_address_m A ON A.BPID=R.GCD_ID AND A.BPTYPE='CS' AND A.address_id=V.ENTITY_ID WHERE R.LOAN_CUSTOMER_ROLE_ID='").append(StringEscapeUtils.escapeSql(CommonFunction.checkNull(roleId[k])).trim()).append("' AND  R.LOAN_ID='").append(StringEscapeUtils.escapeSql(CommonFunction.checkNull(loanId)).trim()).append("' ").toString();

        logger.info(new StringBuilder().append("checkVerifInitCountQuery: ").append(checkVerifInitCountQuery).toString());
        status = ConnectionDAO.singleReturn(checkVerifInitCountQuery);
        logger.info(new StringBuilder().append("checkVerifInitCount ").append(status).toString());

        String checkVerifInitCustomerQuery = new StringBuilder().append("SELECT R.GCD_ID FROM cr_loan_customer_role R  INNER JOIN cr_deal_verification_dtl  V ON V.LOAN_ID=R.LOAN_ID   WHERE R.LOAN_CUSTOMER_ROLE_ID='").append(StringEscapeUtils.escapeSql(CommonFunction.checkNull(roleId[k])).trim()).append("' AND  R.LOAN_ID='").append(StringEscapeUtils.escapeSql(CommonFunction.checkNull(loanId)).trim()).append("' ").toString();

        logger.info(new StringBuilder().append("checkVerifInitCustomerQuery: ").append(checkVerifInitCustomerQuery).toString());
        status1 = ConnectionDAO.singleReturn(checkVerifInitCustomerQuery);
        logger.info(new StringBuilder().append("checkVerifInitCount ").append(status1).toString());

        if ((!CommonFunction.checkNull(status).equalsIgnoreCase("")) || (!CommonFunction.checkNull(status1).equalsIgnoreCase("")))
        {
          String customerNameQuery = new StringBuilder().append("SELECT C.CUSTOMER_NAME FROM gcd_customer_m C WHERE CUSTOMER_ID='").append(StringEscapeUtils.escapeSql(CommonFunction.checkNull(status)).trim()).append("' ").toString();
          if (CommonFunction.checkNull(status).equalsIgnoreCase(""))
          {
            customerNameQuery = new StringBuilder().append("SELECT C.CUSTOMER_NAME FROM gcd_customer_m C WHERE CUSTOMER_ID='").append(StringEscapeUtils.escapeSql(CommonFunction.checkNull(status1)).trim()).append("' ").toString();
          }

          logger.info(new StringBuilder().append("customerNameQuery: ").append(customerNameQuery).toString());
          status = ConnectionDAO.singleReturn(customerNameQuery);
          logger.info(new StringBuilder().append("customerName ").append(status).toString());
          return status;
        }

      }

    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
    return status;
  }

  public boolean deleteVerificationInitCustomerAtCM(String[] roleId, String[] applType, String loanId)
  {
    logger.info(new StringBuilder().append("deleteVerificationInitCustomerAtCM for deleteVerificationInitCustomer....roleId..").append(roleId).append(" loanId ").append(loanId).toString());
    boolean status = false;
    ArrayList qryList = null;
    StringBuffer bufInsUpdSql = null;
    try
    {
      qryList = new ArrayList();
      bufInsUpdSql = new StringBuffer();

      for (int k = 0; k < roleId.length; k++)
      {
        String verifIdQuery = new StringBuilder().append("select VERIFICATION_ID from cr_deal_verification_dtl WHERE ENTITY_TYPE=(select LOAN_CUSTOMER_ROLE_TYPE from cr_loan_customer_role where LOAN_CUSTOMER_ROLE_ID=").append(StringEscapeUtils.escapeSql(CommonFunction.checkNull(roleId[k])).trim()).append(" ) AND  LOAN_ID=").append(StringEscapeUtils.escapeSql(CommonFunction.checkNull(loanId)).trim()).append(" and ENTITY_ID IN (SELECT address_id FROM com_address_m WHERE BPTYPE='CS' AND BPID=(select GCD_ID from cr_loan_customer_role where LOAN_CUSTOMER_ROLE_ID=").append(StringEscapeUtils.escapeSql(CommonFunction.checkNull(roleId[k])).trim()).append(" ))").toString();
        logger.info(new StringBuilder().append("verifIdQuery  ").append(verifIdQuery).toString());
        String verifId = ConnectionDAO.singleReturn(verifIdQuery);
        logger.info(new StringBuilder().append("verifId  ").append(verifId).toString());

        String deleteVerifCapturing = new StringBuilder().append("delete from cr_field_verification_dtl  where VERIFICATION_ID='").append(StringEscapeUtils.escapeSql(CommonFunction.checkNull(verifId).trim())).append("' ").toString();
        logger.info(new StringBuilder().append("delete deleteVerifCapturing q2 ").append(deleteVerifCapturing).toString());
        qryList.add(deleteVerifCapturing);

        String deleteVerification = new StringBuilder().append("delete from cr_deal_verification_dtl where ENTITY_TYPE=(select LOAN_CUSTOMER_ROLE_TYPE from cr_loan_customer_role where LOAN_CUSTOMER_ROLE_ID=").append(StringEscapeUtils.escapeSql(CommonFunction.checkNull(roleId[k])).trim()).append(" ) AND  LOAN_ID=").append(StringEscapeUtils.escapeSql(CommonFunction.checkNull(loanId)).trim()).append(" and ENTITY_ID IN (SELECT address_id FROM com_address_m WHERE BPTYPE='CS' AND BPID=(select GCD_ID from cr_loan_customer_role where LOAN_CUSTOMER_ROLE_ID=").append(StringEscapeUtils.escapeSql(CommonFunction.checkNull(roleId[k])).trim()).append(" ))").toString();
        logger.info(new StringBuilder().append("delete deleteVerificationInitCustomer q3 ").append(deleteVerification).toString());
        qryList.add(deleteVerification);

        String verifIdForCustQuery = new StringBuilder().append("select VERIFICATION_ID from cr_deal_verification_dtl WHERE LOAN_ID='").append(StringEscapeUtils.escapeSql(CommonFunction.checkNull(loanId)).trim()).append("' and ENTITY_ID IN (select GCD_ID from cr_loan_customer_role where LOAN_CUSTOMER_ROLE_ID='").append(StringEscapeUtils.escapeSql(CommonFunction.checkNull(roleId[k])).trim()).append("' )").toString();
        logger.info(new StringBuilder().append("verifIdForCustQuery  ").append(verifIdForCustQuery).toString());
        String verifIdForCust = ConnectionDAO.singleReturn(verifIdForCustQuery);
        logger.info(new StringBuilder().append("verifIdForCust  ").append(verifIdForCust).toString());

        String deleteVerifCapturingForCust = new StringBuilder().append("delete from cr_field_verification_dtl  where VERIFICATION_ID='").append(StringEscapeUtils.escapeSql(CommonFunction.checkNull(verifIdForCust).trim())).append("' ").toString();
        logger.info(new StringBuilder().append("delete deleteVerifCapturingForCust q2 ").append(deleteVerifCapturingForCust).toString());
        qryList.add(deleteVerifCapturingForCust);

        String deleteVerificationForCust = new StringBuilder().append("delete from cr_deal_verification_dtl where VERIFICATION_ID='").append(StringEscapeUtils.escapeSql(CommonFunction.checkNull(verifIdForCust).trim())).append("' ").toString();
        logger.info(new StringBuilder().append("delete deleteVerificationForCust q3 ").append(deleteVerificationForCust).toString());
        qryList.add(deleteVerificationForCust);
      }

      status = ConnectionDAO.sqlInsUpdDelete(qryList);
      logger.info(new StringBuilder().append("Status of Deletion is =").append(status).toString());
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
    return status;
  }

  public String getEmailMandatoryFlag()
  {
    String flag = "";
    StringBuilder query = new StringBuilder();
    query.append("select PARAMETER_VALUE from parameter_mst where PARAMETER_KEY='EMAIL_MANDATORY_FLAG'");
    logger.info(new StringBuilder().append("Query for getting EMAIL_MANDATORY_FLAG from parameter_mst  : ").append(query.toString()).toString());
    try
    {
      flag = ConnectionDAO.singleReturn(query.toString());
    } catch (Exception e) {
      e.printStackTrace();
    }
    finally {
      query = null;
    }
    return flag;
  }

  public String getMobileNoStatus(String tableName, String mobileNo, String addresId,String bpId)
  {
    String queryCheckMobileNo = null;
    if (CommonFunction.checkNull(bpId).equalsIgnoreCase(""))
    {
      queryCheckMobileNo = new StringBuilder().append("Select count(1) from ").append(tableName).append("  where PRIMARY_PHONE='").append(CommonFunction.checkNull(mobileNo)).append("' AND IFNULL(REC_STATUS,'A')<>'X'").toString();
    }
    else
    {
      queryCheckMobileNo = new StringBuilder().append("Select count(1) from ").append(tableName).append("  where PRIMARY_PHONE='").append(CommonFunction.checkNull(mobileNo)).append("' AND BPID<>'").append(CommonFunction.checkNull(bpId)).append("' AND BPTYPE='CS' AND IFNULL(REC_STATUS,'A')<>'X'").toString();
    }
    logger.info(new StringBuilder().append("In getMobileNoStatus queryCheckMobileNo  :  ").append(queryCheckMobileNo).toString());
    String countCheckMobileNo = ConnectionDAO.singleReturn(queryCheckMobileNo);
    queryCheckMobileNo = null;
    return countCheckMobileNo;
  }

  public boolean getExistingIMD(String dealId, String[] roleId) {
    logger.info("In getExistingIMD ");
    String query = new StringBuilder().append("select count(1) from cr_instrument_dtl where txnid=").append(CommonFunction.checkNull(dealId)).append(" and txn_type = 'DC' and rec_status in('P','F','A','D','R','B')").toString();
    logger.info(new StringBuilder().append("In getExistingIMD query  :  ").append(query).toString());
    String countExistingIMD = ConnectionDAO.singleReturn(query);
    String q1 = "";
    boolean status = false;
    int applCount = 0;
    for (int k = 0; k < roleId.length; k++)
    {
      q1 = new StringBuilder().append("select DEAL_CUSTOMER_ROLE_TYPE from cr_deal_customer_role where DEAL_CUSTOMER_ROLE_TYPE='PRAPPL' and DEAL_CUSTOMER_ROLE_ID=").append(StringEscapeUtils.escapeSql(CommonFunction.checkNull(roleId[k])).trim()).toString();
      String applType = ConnectionDAO.singleReturn(q1);
      if (CommonFunction.checkNull(applType).equalsIgnoreCase("PRAPPL"))
        applCount++;
      logger.info(new StringBuilder().append("applType: ").append(applType).toString());
      logger.info(new StringBuilder().append("applcount: ").append(applCount).toString());
    }
    query = null;
    if ((Integer.parseInt(countExistingIMD) > 0) && (applCount > 0))
    {
      status = true;
    }
    return status;
  }

  public boolean saveCustomerAddress1(Object ob)
  {
    logger.info("In saveCustomerAddress() of CorpotateDAOImpl.");
    CustomerSaveVo vo = (CustomerSaveVo)ob;
    boolean status = false;
    ArrayList qryList = new ArrayList();
    PrepStmtObject insertPrepStmtObject = new PrepStmtObject();
    String source = vo.getSource();
    String customerId = "";
    if (!CommonFunction.checkNull(vo.getBp_id()).trim().equalsIgnoreCase(""))
      customerId = vo.getBp_id();
    if (!CommonFunction.checkNull(vo.getBp_id1()).trim().equalsIgnoreCase("")) {
      customerId = vo.getBp_id1();
    }
    try
    {
      String ADDRESS_LINE1 = vo.getAddr1().replaceAll("[^a-zA-Z0-9]", "");
      String ADDRESS_LINE2 = vo.getAddr2().replaceAll("[^a-zA-Z0-9]", "");
      String ADDRESS_LINE3 = vo.getAddr3().replaceAll("[^a-zA-Z0-9]", "");

      StringBuffer bufInsSql = new StringBuffer();
      bufInsSql.append("UPDATE cr_customer_search_dtl SET BPID=?,");
      bufInsSql.append("ADDRESS_LINE1=? , ");
      bufInsSql.append("ADDRESS_LINE2=? , ");
      bufInsSql.append("ADDRESS_LINE3=? , ");
      bufInsSql.append("ADDRESS_STRING= (concat(?,?,?)) , ");
      bufInsSql.append(new StringBuilder().append("DISTRICT=?,PRIMARY_PHONE=?,ALTERNATE_PHONE=?,PINCODE=?,STATE=?,MAKER_DATE=DATE_ADD(STR_TO_DATE(?,'").append(this.dateFormatWithTime).append("'),INTERVAL CURTIME() HOUR_SECOND)").toString());

      bufInsSql.append(" where CUSTOMER_ID=? and deal_id=?; ");

      if (CommonFunction.checkNull(vo.getBp_id()).trim().equalsIgnoreCase(""))
        insertPrepStmtObject.addString(vo.getBp_id1().trim());
      else {
        insertPrepStmtObject.addString(vo.getBp_id().trim());
      }
      if (CommonFunction.checkNull(vo.getAddr1()).trim().equalsIgnoreCase(""))
        insertPrepStmtObject.addNull();
      else {
        insertPrepStmtObject.addString(ADDRESS_LINE1);
      }
      if (CommonFunction.checkNull(vo.getAddr2()).trim().equalsIgnoreCase(""))
        insertPrepStmtObject.addNull();
      else {
        insertPrepStmtObject.addString(ADDRESS_LINE2);
      }
      if (CommonFunction.checkNull(vo.getAddr3()).trim().equalsIgnoreCase(""))
        insertPrepStmtObject.addNull();
      else {
        insertPrepStmtObject.addString(ADDRESS_LINE3);
      }
      if (CommonFunction.checkNull(vo.getAddr1()).trim().equalsIgnoreCase(""))
        insertPrepStmtObject.addString(ADDRESS_LINE1);
      else {
        insertPrepStmtObject.addString(ADDRESS_LINE1);
      }
      if (CommonFunction.checkNull(vo.getAddr2()).trim().equalsIgnoreCase(""))
        insertPrepStmtObject.addString(ADDRESS_LINE2);
      else {
        insertPrepStmtObject.addString(ADDRESS_LINE2);
      }
      if (CommonFunction.checkNull(vo.getAddr3()).trim().equalsIgnoreCase(""))
        insertPrepStmtObject.addString(ADDRESS_LINE3);
      else {
        insertPrepStmtObject.addString(ADDRESS_LINE3);
      }
      if (CommonFunction.checkNull(vo.getTxtDistCode()).trim().equalsIgnoreCase(""))
        insertPrepStmtObject.addNull();
      else {
        insertPrepStmtObject.addString(vo.getTxtDistCode().trim());
      }

      if (CommonFunction.checkNull(vo.getPrimaryPhoneNo()).trim().equalsIgnoreCase(""))
        insertPrepStmtObject.addNull();
      else {
        insertPrepStmtObject.addString(vo.getPrimaryPhoneNo().trim());
      }
      if (CommonFunction.checkNull(vo.getAlternatePhoneNo()).trim().equalsIgnoreCase(""))
        insertPrepStmtObject.addNull();
      else {
        insertPrepStmtObject.addString(vo.getAlternatePhoneNo().trim());
      }
      if (CommonFunction.checkNull(vo.getPincode()).trim().equalsIgnoreCase(""))
        insertPrepStmtObject.addNull();
      else {
        insertPrepStmtObject.addString(vo.getPincode().trim());
      }
      if (CommonFunction.checkNull(vo.getTxtStateCode()).trim().equalsIgnoreCase(""))
        insertPrepStmtObject.addNull();
      else {
        insertPrepStmtObject.addString(vo.getTxtStateCode().trim());
      }
      if (CommonFunction.checkNull(vo.getMakerDate()).trim().equalsIgnoreCase(""))
        insertPrepStmtObject.addNull();
      else
        insertPrepStmtObject.addString(vo.getMakerDate().trim());
      if (CommonFunction.checkNull(vo.getBp_id()).trim().equalsIgnoreCase(""))
        insertPrepStmtObject.addString(vo.getBp_id1().trim());
      else
        insertPrepStmtObject.addString(vo.getBp_id().trim());
      if (CommonFunction.checkNull(vo.getDealId()).trim().equalsIgnoreCase(""))
        insertPrepStmtObject.addNull();
      else {
        insertPrepStmtObject.addString(vo.getDealId().trim());
      }

      insertPrepStmtObject.setSql(bufInsSql.toString());
      logger.info(new StringBuilder().append("IN saveCustomerAddress() of cr_customer_search_dtl  insert query1 ### ").append(insertPrepStmtObject.printQuery()).toString());
      qryList.add(insertPrepStmtObject);
      status = ConnectionDAO.sqlInsUpdDeletePrepStmt(qryList);
      logger.info(new StringBuilder().append("In saveCustomer......................").append(status).toString());

      insertPrepStmtObject = null;
      qryList = null;
      bufInsSql = null;
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
    return status;
  }

  public int saveCorporateDetails1(Object ob, String st, String dealId, int maxId)
  {
    ArrayList searchlist = new ArrayList();
    logger.info("In saveCorporateDetails1()");
    CorporateDetailsVO corporateDetailVo = (CorporateDetailsVO)ob;
    String fatherName = corporateDetailVo.getFatherHusband();
    StringBuffer bufInsSql = new StringBuffer();

    boolean checkApp = false;
    try
    {
      if (!CommonFunction.checkNull(dealId).equalsIgnoreCase(""))
      {
        bufInsSql.append(new StringBuilder().append(" select deal_no,rec_status from cr_deal_dtl where deal_id='").append(dealId).append("' ").toString());
        logger.info(new StringBuilder().append("deal number ").append(bufInsSql.toString()).toString());
        searchlist = ConnectionDAO.sqlSelect(bufInsSql.toString());
        logger.info(new StringBuilder().append("searchDedupeData search Data size is....").append(searchlist.size()).toString());

        for (int i = 0; i < searchlist.size(); i++) {
          ArrayList data = (ArrayList)searchlist.get(i);

          if (data.size() > 0)
          {
            corporateDetailVo.setDealNo(CommonFunction.checkNull(data.get(0)).trim());
            corporateDetailVo.setStatus(CommonFunction.checkNull(data.get(1)).trim());
          }
        }
      }
      StringBuffer bufInsSql1 = new StringBuffer();

      PrepStmtObject insPrepStmtObject2 = new PrepStmtObject();
      ArrayList queryList = new ArrayList();
      bufInsSql1.append("\tINSERT INTO cr_customer_search_dtl(CUSTOMER_ID,CUSTOMER_NAME,CUSTOMER_TYPE,CUSTOMER_DOB,CUSTMER_PAN,CUSTOMER_CONSTITUTION,CUSTOMER_EMAIL,FATHER_HUSBAND_NAME,DRIVING_LICENSE,VOTER_ID,PASSPORT_NUMBER,SOURCE_SYSTEM,FIRST_NAME,MIDDLE_NAME,LAST_NAME,DATE_OF_INCORPORATION, REGISTRATION_NUMBER,UID,VAT_REGISTRATION,TIN_NUMBER,REFERENCE_NO,DEAL_NO,DEAL_STATUS,DEAL_ID) ");

      bufInsSql1.append(" values (");
      bufInsSql1.append("?, ");
      bufInsSql1.append("?, ");
      bufInsSql1.append("?, ");

      bufInsSql1.append(new StringBuilder().append("STR_TO_DATE(?,'").append(this.dateFormat).append("'), ").toString());

      bufInsSql1.append("?, ");
      bufInsSql1.append("?, ");

      bufInsSql1.append("?, ");
      bufInsSql1.append("?, ");
      bufInsSql1.append("?, ");
      bufInsSql1.append("?, ");
      bufInsSql1.append("?, ");
      bufInsSql1.append("?, ");
      bufInsSql1.append("?, ");
      bufInsSql1.append("?, ");
      bufInsSql1.append("?, ");

      bufInsSql1.append(new StringBuilder().append("STR_TO_DATE(?,'").append(this.dateFormat).append("'), ").toString());

      bufInsSql1.append("?, ");
      bufInsSql1.append("?, ");
      bufInsSql1.append("?, ");
      bufInsSql1.append("?, ");
      bufInsSql1.append("?, ");
      bufInsSql1.append("?, ");
      bufInsSql1.append("?, ");
      bufInsSql1.append("? ) ");

      if (CommonFunction.checkNull(corporateDetailVo.getCorporateCode()).trim().equalsIgnoreCase(""))
        insPrepStmtObject2.addInt(maxId);
      else {
        insPrepStmtObject2.addString(corporateDetailVo.getCorporateCode().trim());
      }
      if (CommonFunction.checkNull(corporateDetailVo.getCorporateName()).trim().equalsIgnoreCase(""))
        insPrepStmtObject2.addNull();
      else {
        insPrepStmtObject2.addString(corporateDetailVo.getCorporateName().trim());
      }
      if (st.equalsIgnoreCase("C"))
      {
        insPrepStmtObject2.addString("C");
      }
      else if (st.equalsIgnoreCase("I"))
      {
        insPrepStmtObject2.addString("I");
      }
      if (st.equalsIgnoreCase("I"))
      {
        if (CommonFunction.checkNull(corporateDetailVo.getIncorporationDate()).trim().equalsIgnoreCase(""))
          insPrepStmtObject2.addNull();
        else
          insPrepStmtObject2.addString(corporateDetailVo.getIncorporationDate().trim());
      }
      else {
        insPrepStmtObject2.addNull();
      }
      if (CommonFunction.checkNull(corporateDetailVo.getPan()).trim().equalsIgnoreCase(""))
        insPrepStmtObject2.addNull();
      else {
        insPrepStmtObject2.addString(corporateDetailVo.getPan().trim());
      }
      if (CommonFunction.checkNull(corporateDetailVo.getConstitution()).trim().equalsIgnoreCase(""))
        insPrepStmtObject2.addNull();
      else {
        insPrepStmtObject2.addString(corporateDetailVo.getConstitution().trim());
      }
      if (CommonFunction.checkNull(corporateDetailVo.getInstitutionEmail()).trim().equalsIgnoreCase(""))
        insPrepStmtObject2.addNull();
      else {
        insPrepStmtObject2.addString(corporateDetailVo.getInstitutionEmail().trim());
      }
      if (CommonFunction.checkNull(corporateDetailVo.getFatherHusband()).trim().equalsIgnoreCase(""))
        insPrepStmtObject2.addNull();
      else {
        insPrepStmtObject2.addString(corporateDetailVo.getFatherHusband().trim());
      }
      if (CommonFunction.checkNull(corporateDetailVo.getDrivingLicense()).trim().equalsIgnoreCase(""))
        insPrepStmtObject2.addNull();
      else {
        insPrepStmtObject2.addString(corporateDetailVo.getDrivingLicense().trim());
      }
      if (CommonFunction.checkNull(corporateDetailVo.getVoterId()).trim().equalsIgnoreCase(""))
        insPrepStmtObject2.addNull();
      else {
        insPrepStmtObject2.addString(corporateDetailVo.getVoterId().trim());
      }
      if (CommonFunction.checkNull(corporateDetailVo.getPassport()).trim().equalsIgnoreCase(""))
        insPrepStmtObject2.addNull();
      else {
        insPrepStmtObject2.addString(corporateDetailVo.getPassport().trim());
      }
      if (CommonFunction.checkNull(corporateDetailVo.getSource()).trim().equalsIgnoreCase(""))
        insPrepStmtObject2.addString("I");
      else {
        insPrepStmtObject2.addString("I");
      }
      if (CommonFunction.checkNull(corporateDetailVo.getFirstName()).trim().equalsIgnoreCase(""))
        insPrepStmtObject2.addNull();
      else {
        insPrepStmtObject2.addString(corporateDetailVo.getFirstName().trim());
      }
      if (CommonFunction.checkNull(corporateDetailVo.getMiddleName()).trim().equalsIgnoreCase(""))
        insPrepStmtObject2.addNull();
      else {
        insPrepStmtObject2.addString(corporateDetailVo.getMiddleName().trim());
      }
      if (CommonFunction.checkNull(corporateDetailVo.getLastName()).trim().equalsIgnoreCase(""))
        insPrepStmtObject2.addNull();
      else {
        insPrepStmtObject2.addString(corporateDetailVo.getLastName().trim());
      }
      if (st.equalsIgnoreCase("C"))
      {
        if (CommonFunction.checkNull(corporateDetailVo.getIncorporationDate()).trim().equalsIgnoreCase(""))
          insPrepStmtObject2.addNull();
        else
          insPrepStmtObject2.addString(corporateDetailVo.getIncorporationDate().trim());
      }
      else {
        insPrepStmtObject2.addNull();
      }
      if (CommonFunction.checkNull(corporateDetailVo.getRegistrationNo()).trim().equalsIgnoreCase(""))
        insPrepStmtObject2.addNull();
      else {
        insPrepStmtObject2.addString(corporateDetailVo.getRegistrationNo().trim());
      }
      if (CommonFunction.checkNull(corporateDetailVo.getAadhaar()).trim().equalsIgnoreCase(""))
        insPrepStmtObject2.addNull();
      else {
        insPrepStmtObject2.addString(corporateDetailVo.getAadhaar().trim());
      }
      if (CommonFunction.checkNull(corporateDetailVo.getVatRegNo()).trim().equalsIgnoreCase(""))
        insPrepStmtObject2.addNull();
      else {
        insPrepStmtObject2.addString(corporateDetailVo.getVatRegNo().trim());
      }
      if (CommonFunction.checkNull(corporateDetailVo.getSalesTax()).trim().equalsIgnoreCase(""))
        insPrepStmtObject2.addNull();
      else {
        insPrepStmtObject2.addString(corporateDetailVo.getSalesTax().trim());
      }
      if (CommonFunction.checkNull(corporateDetailVo.getReferredBy()).trim().equalsIgnoreCase(""))
        insPrepStmtObject2.addNull();
      else {
        insPrepStmtObject2.addString(corporateDetailVo.getReferredBy().trim());
      }
      if (CommonFunction.checkNull(corporateDetailVo.getDealNo()).trim().equalsIgnoreCase(""))
        insPrepStmtObject2.addNull();
      else {
        insPrepStmtObject2.addString(corporateDetailVo.getDealNo().trim());
      }
      if ((CommonFunction.checkNull(corporateDetailVo.getStatus()).trim().equalsIgnoreCase("P")) || (CommonFunction.checkNull(corporateDetailVo.getStatus()).trim().equalsIgnoreCase("")))
        insPrepStmtObject2.addString("F");
      else {
        insPrepStmtObject2.addString(corporateDetailVo.getStatus().trim());
      }
      if (CommonFunction.checkNull(dealId).trim().equalsIgnoreCase(""))
        insPrepStmtObject2.addNull();
      else {
        insPrepStmtObject2.addString(dealId.trim());
      }

      insPrepStmtObject2.setSql(bufInsSql1.toString());
      logger.info(new StringBuilder().append("IN saveCorporateDetails() insert query1  : ").append(insPrepStmtObject2.printQuery()).toString());
      queryList.add(insPrepStmtObject2);
      boolean status1 = ConnectionDAO.sqlInsUpdDeletePrepStmt(queryList);
      queryList = null;

      insPrepStmtObject2 = null;
      bufInsSql1 = null;
		//Saurabh Code start here for cibil initiation
		 logger.info("Saurabh Code start for Cibil Initiation");
		//String dealId = "";					
 			boolean cibilStatus=false;
 			ArrayList qryListC = new ArrayList();
 			PrepStmtObject insertPrepStmtObject=new PrepStmtObject();
 			String qryUpdate = "UPDATE CR_DEAL_MOVEMENT_DTL SET DEAL_FORWARDED = ?, DEAL_FORWARD_USER = ? WHERE DEAL_STAGE_ID = ? AND DEAL_ID = ?";	
 			
 			if(StringEscapeUtils.escapeSql(CommonFunction.checkNull("0000-00-00 00:00:00")).trim().equalsIgnoreCase(""))
 				insertPrepStmtObject.addNull();
 			else
 				insertPrepStmtObject.addString(StringEscapeUtils.escapeSql("0000-00-00 00:00:00").trim());
 			
 			if(StringEscapeUtils.escapeSql(CommonFunction.checkNull("")).equalsIgnoreCase(""))
 				insertPrepStmtObject.addString(StringEscapeUtils.escapeSql("").trim());
 			else
 				insertPrepStmtObject.addString(StringEscapeUtils.escapeSql("").trim());
 			
 			if(StringEscapeUtils.escapeSql(CommonFunction.checkNull("CBL")).trim().equalsIgnoreCase(""))
 				insertPrepStmtObject.addNull();
 			else
 				insertPrepStmtObject.addString(StringEscapeUtils.escapeSql("CBL").trim());
 			
 			if((CommonFunction.checkNull(corporateDetailVo.getDealId()).trim().equalsIgnoreCase("")))
 				insertPrepStmtObject.addNull();
 			else
 				insertPrepStmtObject.addString(StringEscapeUtils.escapeSql(corporateDetailVo.getDealId()).trim());
 			
 			insertPrepStmtObject.setSql(qryUpdate);
 			qryListC.add(insertPrepStmtObject);
 			/*boolean status2 = ConnectionDAO.sqlInsUpdDelete(qryListC);
 			qryUpdate=null;*/
 			try
 			{
 				logger.info("Query to initiate CIBIL: "+ insertPrepStmtObject.printQuery());
 				cibilStatus = ConnectionDAO.sqlInsUpdDeletePrepStmt(qryListC);
 			}
 			catch(Exception e){
 				e.printStackTrace();
 			}
		 //Saurabh Code Ends here for cibil initiation 
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
    return maxId;
  }

  public boolean saveCorporateUpdate1(Object ob, int id, String recStatus, String statusCase, String updateFlag, String pageStatuss, String source)
  {
    logger.info("in saveCorporateUpdate() of CorpotateDAOImpl");
    StringBuffer bufInsSql = new StringBuffer();
    ArrayList searchlist = new ArrayList();
    CorporateDetailsVO corporateDetailVo = (CorporateDetailsVO)ob;
    logger.info(new StringBuilder().append("DealId is: ").append(corporateDetailVo.getDealId()).toString());
    boolean status = false;
    String tableName = "";
    String customerType = "";
    String st = "";
    try
    {
      customerType = ConnectionDAO.singleReturn(new StringBuilder().append("select CUSTOMER_TYPE from cr_deal_customer_m where CUSTOMER_ID=").append(id).toString());
      if (!CommonFunction.checkNull(corporateDetailVo.getDealId()).trim().equalsIgnoreCase(""))
      {
        bufInsSql.append(new StringBuilder().append(" select deal_ID from CR_CUSTOMER_SEARCH_DTL where CUSTOMER_ID='").append(id).append("' AND DEAL_ID='").append(corporateDetailVo.getDealId()).append("' ").toString());
        logger.info(new StringBuilder().append("deal No ").append(bufInsSql.toString()).toString());
        searchlist = ConnectionDAO.sqlSelect(bufInsSql.toString());
        logger.info(new StringBuilder().append("searchDedupeData search Data size is....").append(searchlist.size()).toString());
        if (searchlist.size() == 0)
        {
          saveCorporateDetails1(ob, customerType, corporateDetailVo.getDealId(), id);
        }
        else
        {
          StringBuffer bufInsSql4 = new StringBuffer();

          PrepStmtObject insPrepStmtObject2 = new PrepStmtObject();
          ArrayList qryList4 = new ArrayList();
          logger.info("CorporateDAOImpl saveCorporateUpdate().............fromLeads..........................");
          bufInsSql4.append("UPDATE cr_customer_search_dtl SET CUSTOMER_NAME=?,CUSTOMER_TYPE=?,CUSTOMER_DOB= ");

          bufInsSql4.append(new StringBuilder().append("STR_TO_DATE(?,'").append(this.dateFormat).append("'),").toString());
          bufInsSql4.append("CUSTMER_PAN=?,CUSTOMER_CONSTITUTION=?,CUSTOMER_EMAIL=?,FATHER_HUSBAND_NAME=?,DRIVING_LICENSE=?,VOTER_ID=?,PASSPORT_NUMBER=?,FIRST_NAME=?,MIDDLE_NAME=?,LAST_NAME=?,DATE_OF_INCORPORATION= ");

          bufInsSql4.append(new StringBuilder().append("STR_TO_DATE(?,'").append(this.dateFormat).append("'),").toString());
          bufInsSql4.append("REGISTRATION_NUMBER=?,UID=?,VAT_REGISTRATION=?,TIN_NUMBER=?,REFERENCE_NO=? WHERE CUSTOMER_ID=? and Deal_id=? ; ");
          if (CommonFunction.checkNull(corporateDetailVo.getCorporateName()).trim().equalsIgnoreCase(""))
            insPrepStmtObject2.addNull();
          else {
            insPrepStmtObject2.addString(corporateDetailVo.getCorporateName().trim());
          }
          if (CommonFunction.checkNull(customerType).trim().equalsIgnoreCase(""))
            insPrepStmtObject2.addNull();
          else
            insPrepStmtObject2.addString(CommonFunction.checkNull(customerType).trim());
          if (customerType.equalsIgnoreCase("I"))
          {
            if (CommonFunction.checkNull(corporateDetailVo.getIncorporationDate()).trim().equalsIgnoreCase(""))
              insPrepStmtObject2.addNull();
            else
              insPrepStmtObject2.addString(corporateDetailVo.getIncorporationDate().trim());
          }
          else
            insPrepStmtObject2.addNull();
          if (CommonFunction.checkNull(corporateDetailVo.getPan()).trim().equalsIgnoreCase(""))
            insPrepStmtObject2.addNull();
          else {
            insPrepStmtObject2.addString(corporateDetailVo.getPan().trim());
          }
          if (CommonFunction.checkNull(corporateDetailVo.getConstitution()).trim().equalsIgnoreCase(""))
            insPrepStmtObject2.addNull();
          else {
            insPrepStmtObject2.addString(corporateDetailVo.getConstitution().trim());
          }
          if (CommonFunction.checkNull(corporateDetailVo.getInstitutionEmail()).trim().equalsIgnoreCase(""))
            insPrepStmtObject2.addNull();
          else {
            insPrepStmtObject2.addString(corporateDetailVo.getInstitutionEmail().trim());
          }
          if (CommonFunction.checkNull(corporateDetailVo.getFatherHusband()).trim().equalsIgnoreCase(""))
            insPrepStmtObject2.addNull();
          else {
            insPrepStmtObject2.addString(corporateDetailVo.getFatherHusband().trim());
          }
          if (CommonFunction.checkNull(corporateDetailVo.getDrivingLicense()).trim().equalsIgnoreCase(""))
            insPrepStmtObject2.addNull();
          else {
            insPrepStmtObject2.addString(corporateDetailVo.getDrivingLicense().trim());
          }
          if (CommonFunction.checkNull(corporateDetailVo.getVoterId()).trim().equalsIgnoreCase(""))
            insPrepStmtObject2.addNull();
          else {
            insPrepStmtObject2.addString(corporateDetailVo.getVoterId().trim());
          }
          if (CommonFunction.checkNull(corporateDetailVo.getPassport()).trim().equalsIgnoreCase(""))
            insPrepStmtObject2.addNull();
          else {
            insPrepStmtObject2.addString(corporateDetailVo.getPassport().trim());
          }
          if (CommonFunction.checkNull(corporateDetailVo.getFirstName()).trim().equalsIgnoreCase(""))
            insPrepStmtObject2.addNull();
          else {
            insPrepStmtObject2.addString(corporateDetailVo.getFirstName().trim());
          }
          if (CommonFunction.checkNull(corporateDetailVo.getMiddleName()).trim().equalsIgnoreCase(""))
            insPrepStmtObject2.addNull();
          else {
            insPrepStmtObject2.addString(corporateDetailVo.getMiddleName().trim());
          }
          if (CommonFunction.checkNull(corporateDetailVo.getLastName()).trim().equalsIgnoreCase(""))
            insPrepStmtObject2.addNull();
          else
            insPrepStmtObject2.addString(corporateDetailVo.getLastName().trim());
          if (customerType.equalsIgnoreCase("C"))
          {
            if (CommonFunction.checkNull(corporateDetailVo.getIncorporationDate()).trim().equalsIgnoreCase(""))
              insPrepStmtObject2.addNull();
            else
              insPrepStmtObject2.addString(corporateDetailVo.getIncorporationDate().trim());
          }
          else
            insPrepStmtObject2.addNull();
          if (CommonFunction.checkNull(corporateDetailVo.getRegistrationNo()).trim().equalsIgnoreCase(""))
            insPrepStmtObject2.addNull();
          else {
            insPrepStmtObject2.addString(corporateDetailVo.getRegistrationNo().trim());
          }
          if (CommonFunction.checkNull(corporateDetailVo.getAadhaar()).trim().equalsIgnoreCase(""))
            insPrepStmtObject2.addNull();
          else {
            insPrepStmtObject2.addString(corporateDetailVo.getAadhaar().trim());
          }
          if (CommonFunction.checkNull(corporateDetailVo.getVatRegNo()).trim().equalsIgnoreCase(""))
            insPrepStmtObject2.addNull();
          else {
            insPrepStmtObject2.addString(corporateDetailVo.getVatRegNo().trim());
          }
          if (CommonFunction.checkNull(corporateDetailVo.getSalesTax()).trim().equalsIgnoreCase(""))
            insPrepStmtObject2.addNull();
          else {
            insPrepStmtObject2.addString(corporateDetailVo.getSalesTax().trim());
          }
          if (CommonFunction.checkNull(corporateDetailVo.getReferredBy()).trim().equalsIgnoreCase(""))
            insPrepStmtObject2.addNull();
          else
            insPrepStmtObject2.addString(corporateDetailVo.getReferredBy().trim());
          if (CommonFunction.checkNull(corporateDetailVo.getCorporateCode()).trim().equalsIgnoreCase(""))
            insPrepStmtObject2.addNull();
          else {
            insPrepStmtObject2.addString(corporateDetailVo.getCorporateCode().trim());
          }
          if (CommonFunction.checkNull(corporateDetailVo.getDealId()).trim().equalsIgnoreCase(""))
            insPrepStmtObject2.addNull();
          else {
            insPrepStmtObject2.addString(corporateDetailVo.getDealId().trim());
          }
          insPrepStmtObject2.setSql(bufInsSql4.toString());
          logger.info(new StringBuilder().append("IN saveCorporateUpdate() insert query1  : ").append(insPrepStmtObject2.printQuery()).toString());
          qryList4.add(insPrepStmtObject2);
          boolean status1 = ConnectionDAO.sqlInsUpdDeletePrepStmt(qryList4);
          qryList4 = null;

          insPrepStmtObject2 = null;
          bufInsSql4 = null;
        }

      }
      else
      {
        StringBuffer bufInsSql4 = new StringBuffer();

        PrepStmtObject insPrepStmtObject2 = new PrepStmtObject();
        ArrayList qryList4 = new ArrayList();
        logger.info("CorporateDAOImpl saveCorporateUpdate().............fromLeads..........................");
        bufInsSql4.append("UPDATE cr_customer_search_dtl SET CUSTOMER_NAME=?,CUSTOMER_TYPE=?,CUSTOMER_DOB= ");

        bufInsSql4.append(new StringBuilder().append("STR_TO_DATE(?,'").append(this.dateFormat).append("'),").toString());
        bufInsSql4.append("CUSTMER_PAN=?,CUSTOMER_CONSTITUTION=?,CUSTOMER_EMAIL=?,FATHER_HUSBAND_NAME=?,DRIVING_LICENSE=?,VOTER_ID=?,PASSPORT_NUMBER=?,FIRST_NAME=?,MIDDLE_NAME=?,LAST_NAME=?,DATE_OF_INCORPORATION= ");

        bufInsSql4.append(new StringBuilder().append("STR_TO_DATE(?,'").append(this.dateFormat).append("'),").toString());
        bufInsSql4.append("REGISTRATION_NUMBER=?,UID=?,VAT_REGISTRATION=?,TIN_NUMBER=?,REFERENCE_NO=? WHERE CUSTOMER_ID=? ; ");
        if (CommonFunction.checkNull(corporateDetailVo.getCorporateName()).trim().equalsIgnoreCase(""))
          insPrepStmtObject2.addNull();
        else {
          insPrepStmtObject2.addString(corporateDetailVo.getCorporateName().trim());
        }
        if (CommonFunction.checkNull(customerType).trim().equalsIgnoreCase(""))
          insPrepStmtObject2.addNull();
        else
          insPrepStmtObject2.addString(CommonFunction.checkNull(customerType).trim());
        if (customerType.equalsIgnoreCase("I"))
        {
          if (CommonFunction.checkNull(corporateDetailVo.getIncorporationDate()).trim().equalsIgnoreCase(""))
            insPrepStmtObject2.addNull();
          else
            insPrepStmtObject2.addString(corporateDetailVo.getIncorporationDate().trim());
        }
        else
          insPrepStmtObject2.addNull();
        if (CommonFunction.checkNull(corporateDetailVo.getPan()).trim().equalsIgnoreCase(""))
          insPrepStmtObject2.addNull();
        else {
          insPrepStmtObject2.addString(corporateDetailVo.getPan().trim());
        }
        if (CommonFunction.checkNull(corporateDetailVo.getConstitution()).trim().equalsIgnoreCase(""))
          insPrepStmtObject2.addNull();
        else {
          insPrepStmtObject2.addString(corporateDetailVo.getConstitution().trim());
        }
        if (CommonFunction.checkNull(corporateDetailVo.getInstitutionEmail()).trim().equalsIgnoreCase(""))
          insPrepStmtObject2.addNull();
        else {
          insPrepStmtObject2.addString(corporateDetailVo.getInstitutionEmail().trim());
        }
        if (CommonFunction.checkNull(corporateDetailVo.getFatherHusband()).trim().equalsIgnoreCase(""))
          insPrepStmtObject2.addNull();
        else {
          insPrepStmtObject2.addString(corporateDetailVo.getFatherHusband().trim());
        }
        if (CommonFunction.checkNull(corporateDetailVo.getDrivingLicense()).trim().equalsIgnoreCase(""))
          insPrepStmtObject2.addNull();
        else {
          insPrepStmtObject2.addString(corporateDetailVo.getDrivingLicense().trim());
        }
        if (CommonFunction.checkNull(corporateDetailVo.getVoterId()).trim().equalsIgnoreCase(""))
          insPrepStmtObject2.addNull();
        else {
          insPrepStmtObject2.addString(corporateDetailVo.getVoterId().trim());
        }
        if (CommonFunction.checkNull(corporateDetailVo.getPassport()).trim().equalsIgnoreCase(""))
          insPrepStmtObject2.addNull();
        else {
          insPrepStmtObject2.addString(corporateDetailVo.getPassport().trim());
        }
        if (CommonFunction.checkNull(corporateDetailVo.getFirstName()).trim().equalsIgnoreCase(""))
          insPrepStmtObject2.addNull();
        else {
          insPrepStmtObject2.addString(corporateDetailVo.getFirstName().trim());
        }
        if (CommonFunction.checkNull(corporateDetailVo.getMiddleName()).trim().equalsIgnoreCase(""))
          insPrepStmtObject2.addNull();
        else {
          insPrepStmtObject2.addString(corporateDetailVo.getMiddleName().trim());
        }
        if (CommonFunction.checkNull(corporateDetailVo.getLastName()).trim().equalsIgnoreCase(""))
          insPrepStmtObject2.addNull();
        else
          insPrepStmtObject2.addString(corporateDetailVo.getLastName().trim());
        if (customerType.equalsIgnoreCase("C"))
        {
          if (CommonFunction.checkNull(corporateDetailVo.getIncorporationDate()).trim().equalsIgnoreCase(""))
            insPrepStmtObject2.addNull();
          else
            insPrepStmtObject2.addString(corporateDetailVo.getIncorporationDate().trim());
        }
        else
          insPrepStmtObject2.addNull();
        if (CommonFunction.checkNull(corporateDetailVo.getRegistrationNo()).trim().equalsIgnoreCase(""))
          insPrepStmtObject2.addNull();
        else {
          insPrepStmtObject2.addString(corporateDetailVo.getRegistrationNo().trim());
        }
        if (CommonFunction.checkNull(corporateDetailVo.getAadhaar()).trim().equalsIgnoreCase(""))
          insPrepStmtObject2.addNull();
        else {
          insPrepStmtObject2.addString(corporateDetailVo.getAadhaar().trim());
        }
        if (CommonFunction.checkNull(corporateDetailVo.getVatRegNo()).trim().equalsIgnoreCase(""))
          insPrepStmtObject2.addNull();
        else {
          insPrepStmtObject2.addString(corporateDetailVo.getVatRegNo().trim());
        }
        if (CommonFunction.checkNull(corporateDetailVo.getSalesTax()).trim().equalsIgnoreCase(""))
          insPrepStmtObject2.addNull();
        else {
          insPrepStmtObject2.addString(corporateDetailVo.getSalesTax().trim());
        }
        if (CommonFunction.checkNull(corporateDetailVo.getReferredBy()).trim().equalsIgnoreCase(""))
          insPrepStmtObject2.addNull();
        else
          insPrepStmtObject2.addString(corporateDetailVo.getReferredBy().trim());
        if (CommonFunction.checkNull(corporateDetailVo.getCorporateCode()).trim().equalsIgnoreCase(""))
          insPrepStmtObject2.addNull();
        else {
          insPrepStmtObject2.addString(corporateDetailVo.getCorporateCode().trim());
        }

        insPrepStmtObject2.setSql(bufInsSql4.toString());
        logger.info(new StringBuilder().append("IN saveCorporateUpdate() insert query1  : ").append(insPrepStmtObject2.printQuery()).toString());
        qryList4.add(insPrepStmtObject2);
        boolean status1 = ConnectionDAO.sqlInsUpdDeletePrepStmt(qryList4);
        qryList4 = null;

        insPrepStmtObject2 = null;
        bufInsSql4 = null;
      }

    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
    return status;
  }
  public boolean saveStakeHolderDedupe(Object ob, int id, String source) {
    ArrayList searchlist = new ArrayList();
    StakeHolderVo vo = (StakeHolderVo)ob;
    boolean status = false;
    ArrayList qryList = new ArrayList();
    PrepStmtObject insertPrepStmtObject = new PrepStmtObject();
    StringBuffer bufInsSql = new StringBuffer();
    StringBuffer bufInsSql1 = new StringBuffer();
    try {
      bufInsSql1.append("(SELECT  GROUP_CONCAT(DISTINCT STAKEHOLDER_NAME ORDER BY STAKEHOLDER_NAME)as partner FROM CR_DEAL_CUSTOMER_STAKEHOLDER_M  ");
      bufInsSql1.append(new StringBuilder().append(" where customer_id ='").append(id).append("'  GROUP BY customer_id) ").toString());
      searchlist = ConnectionDAO.sqlSelect(bufInsSql1.toString());
      logger.info(new StringBuilder().append("searchDedupeData search Data size is....").append(searchlist.size()).toString());

      for (int i = 0; i < searchlist.size(); i++) {
        ArrayList data = (ArrayList)searchlist.get(i);

        if (data.size() > 0)
        {
          vo.setPartner(CommonFunction.checkNull(data.get(0)).trim());
        }
      }

      bufInsSql.append("update cr_customer_search_dtl set partner=? where customer_id=? and deal_id=?");
      if (CommonFunction.checkNull(vo.getPartner()).trim().equalsIgnoreCase(""))
        insertPrepStmtObject.addNull();
      else {
        insertPrepStmtObject.addString(vo.getPartner().trim());
      }
      insertPrepStmtObject.addInt(id);

      if (CommonFunction.checkNull(vo.getDealId()).trim().equalsIgnoreCase(""))
        insertPrepStmtObject.addNull();
      else {
        insertPrepStmtObject.addString(vo.getDealId().trim());
      }
      insertPrepStmtObject.setSql(bufInsSql.toString());
      logger.info(new StringBuilder().append("IN saveStakeholderName insert query1 ### ").append(insertPrepStmtObject.printQuery()).toString());
      qryList.add(insertPrepStmtObject);
      status = ConnectionDAO.sqlInsUpdDeletePrepStmt(qryList);
      logger.info(new StringBuilder().append("In saveCustomer......................").append(status).toString());
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
    finally
    {
      vo = null;
      bufInsSql = null;
      bufInsSql1 = null;
      insertPrepStmtObject = null;
    }

    return status;
  }

  public boolean saveLeadCustomer(LeaddetailDealVo vo, String str)
  {
    boolean status = false;
    String dealId = vo.getDealId();
    PrepStmtObject insertPrepStmtObject = new PrepStmtObject();
    ArrayList qryList = new ArrayList();
    ArrayList searchlist = new ArrayList();
    StringBuffer bufInsSql1 = new StringBuffer();
    try
    {
      if (!CommonFunction.checkNull(dealId).equalsIgnoreCase(""))
      {
        bufInsSql1.append(new StringBuilder().append(" select deal_no from cr_deal_dtl where deal_id='").append(dealId).append("' ").toString());
        logger.info(new StringBuilder().append("deal number ").append(bufInsSql1.toString()).toString());
        searchlist = ConnectionDAO.sqlSelect(bufInsSql1.toString());
        logger.info(new StringBuilder().append("searchDedupeData search Data size is....").append(searchlist.size()).toString());

        for (int i = 0; i < searchlist.size(); i++) {
          ArrayList data = (ArrayList)searchlist.get(i);

          if (data.size() > 0)
          {
            vo.setDealNo(CommonFunction.checkNull(data.get(0)).trim());
          }
        }

      }

      logger.info("In insert saveLeadCustomerDetails");
      StringBuffer bufInsSql = new StringBuffer();
      bufInsSql.append("insert into cr_customer_search_dtl(CUSTOMER_ID,CUSTOMER_NAME,FIRST_NAME,LAST_NAME,CUSTOMER_TYPE,CUSTOMER_DOB,CUSTMER_PAN,REGISTRATION_NUMBER,CUSTOMER_CONSTITUTION,CUSTOMER_EMAIL,FATHER_HUSBAND_NAME,DRIVING_LICENSE,VOTER_ID,PASSPORT_NUMBER,DEAL_STATUS,SOURCE_SYSTEM,DEAL_NO,deal_id)");

      bufInsSql.append(" values ( ");
      bufInsSql.append(" ?,");
      bufInsSql.append(" ?,");
      bufInsSql.append(" ?,");
      bufInsSql.append(" ?,");
      bufInsSql.append(" ?,");

      bufInsSql.append(new StringBuilder().append("STR_TO_DATE(?,'").append(this.dateFormat).append("'),").toString());
      bufInsSql.append(" ?,");
      bufInsSql.append(" ?,");
      bufInsSql.append(" ?,");
      bufInsSql.append(" ?,");
      bufInsSql.append(" ?,");
      bufInsSql.append(" ?,");
      bufInsSql.append(" ?,");
      bufInsSql.append(" ?,");
      bufInsSql.append(" ?,");
      bufInsSql.append(" ?,");
      bufInsSql.append(" ?,");
      bufInsSql.append(" ? )");

      if (CommonFunction.checkNull(str).trim().equals(""))
        insertPrepStmtObject.addNull();
      else {
        insertPrepStmtObject.addString(str.trim());
      }
      if (CommonFunction.checkNull(vo.getCustomerName()).trim().equals(""))
        insertPrepStmtObject.addNull();
      else {
        insertPrepStmtObject.addString(vo.getCustomerName().trim());
      }
      if (CommonFunction.checkNull(vo.getFirstName()).trim().equals(""))
        insertPrepStmtObject.addNull();
      else {
        insertPrepStmtObject.addString(vo.getFirstName().trim());
      }
      if (CommonFunction.checkNull(vo.getLastName()).trim().equals(""))
        insertPrepStmtObject.addNull();
      else {
        insertPrepStmtObject.addString(vo.getLastName().trim());
      }
      if (CommonFunction.checkNull(vo.getCustomerType()).trim().equals(""))
        insertPrepStmtObject.addNull();
      else {
        insertPrepStmtObject.addString(vo.getCustomerType().trim());
      }
      if (CommonFunction.checkNull(vo.getCustDOB()).trim().equals(""))
        insertPrepStmtObject.addNull();
      else {
        insertPrepStmtObject.addString(vo.getCustDOB().trim());
      }
      if (CommonFunction.checkNull(vo.getPan()).trim().equals(""))
        insertPrepStmtObject.addNull();
      else {
        insertPrepStmtObject.addString(vo.getPan().trim());
      }

      if (CommonFunction.checkNull(vo.getRegistrationNo()).trim().equals(""))
        insertPrepStmtObject.addNull();
      else {
        insertPrepStmtObject.addString(vo.getRegistrationNo().trim());
      }

      if (CommonFunction.checkNull(vo.getConstitution()).trim().equals(""))
        insertPrepStmtObject.addNull();
      else {
        insertPrepStmtObject.addString(vo.getConstitution().trim());
      }
      if (CommonFunction.checkNull(vo.getEmail()).trim().equals(""))
        insertPrepStmtObject.addNull();
      else {
        insertPrepStmtObject.addString(vo.getEmail().trim());
      }

      if (CommonFunction.checkNull(vo.getFatherHusbandName()).trim().equals(""))
        insertPrepStmtObject.addNull();
      else {
        insertPrepStmtObject.addString(vo.getFatherHusbandName().trim());
      }

      if (CommonFunction.checkNull(vo.getDrivingLicence()).trim().equals(""))
        insertPrepStmtObject.addNull();
      else {
        insertPrepStmtObject.addString(vo.getDrivingLicence().trim());
      }
      if (CommonFunction.checkNull(vo.getVoterId()).trim().equals(""))
        insertPrepStmtObject.addNull();
      else
        insertPrepStmtObject.addString(vo.getVoterId().trim());
      if (CommonFunction.checkNull(vo.getPassportNo()).trim().equals(""))
        insertPrepStmtObject.addNull();
      else
        insertPrepStmtObject.addString(vo.getPassportNo().trim());
      insertPrepStmtObject.addString("F");
      if (CommonFunction.checkNull(vo.getSource()).trim().equalsIgnoreCase(""))
        insertPrepStmtObject.addString("I");
      else
        insertPrepStmtObject.addString("I");
      if (CommonFunction.checkNull(vo.getDealNo()).trim().equalsIgnoreCase(""))
        insertPrepStmtObject.addNull();
      else
        insertPrepStmtObject.addString(vo.getDealNo().trim());
      if (CommonFunction.checkNull(dealId).trim().equalsIgnoreCase(""))
        insertPrepStmtObject.addNull();
      else {
        insertPrepStmtObject.addString(dealId.trim());
      }
      insertPrepStmtObject.setSql(bufInsSql.toString());
      logger.info(new StringBuilder().append("IN saveLeadCustomerDetails() insert query1 ### ").append(insertPrepStmtObject.printQuery()).toString());

      qryList.add(insertPrepStmtObject);
      status = ConnectionDAO.sqlInsUpdDeletePrepStmt(qryList);

      logger.info(new StringBuilder().append("In saveLeadCustomer..........status............").append(status).toString());
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
    finally
    {
      vo = null;
    }
    return status;
  }

  public boolean saveLeadCustomerAddress(LeaddetailDealVo vo)
  {
    boolean status = false;
    PrepStmtObject insertPrepStmtObject = new PrepStmtObject();
    ArrayList qryList = new ArrayList();
    try
    {
      logger.info("In insert saveLeadCustomerAddressDetails");
      StringBuffer bufInsSql = new StringBuffer();
      bufInsSql.append("UPDATE cr_customer_search_dtl SET BPID=?,");
      bufInsSql.append("ADDRESS_LINE1=? , ");
      bufInsSql.append("ADDRESS_LINE2=? , ");
      bufInsSql.append("ADDRESS_LINE3=? , ");

      bufInsSql.append(new StringBuilder().append("DISTRICT=?,PRIMARY_PHONE=?,ALTERNATE_PHONE=?,PINCODE=?,STATE=?,MAKER_DATE=DATE_ADD(STR_TO_DATE(?,'").append(this.dateFormatWithTime).append("'),INTERVAL CURTIME() HOUR_SECOND) ").toString());

      bufInsSql.append("  where CUSTOMER_ID=? ; ");

      if (CommonFunction.checkNull(vo.getBpId()).trim().equals(""))
        insertPrepStmtObject.addNull();
      else {
        insertPrepStmtObject.addString(vo.getBpId().trim());
      }
      if (CommonFunction.checkNull(vo.getAddressLine1()).trim().equals(""))
        insertPrepStmtObject.addNull();
      else {
        insertPrepStmtObject.addString(vo.getAddressLine1().trim());
      }
      if (CommonFunction.checkNull(vo.getAddressLine2()).trim().equals(""))
        insertPrepStmtObject.addNull();
      else {
        insertPrepStmtObject.addString(vo.getAddressLine2().trim());
      }
      if (CommonFunction.checkNull(vo.getAddressLine3()).trim().equals(""))
        insertPrepStmtObject.addNull();
      else
        insertPrepStmtObject.addString(vo.getAddressLine3().trim());
      if (CommonFunction.checkNull(vo.getLbxDistrict()).trim().equals(""))
        insertPrepStmtObject.addNull();
      else
        insertPrepStmtObject.addString(vo.getLbxDistrict().trim());
      if (CommonFunction.checkNull(vo.getPhone()).trim().equals(""))
        insertPrepStmtObject.addNull();
      else {
        insertPrepStmtObject.addString(vo.getPhone().trim());
      }
      if (CommonFunction.checkNull(vo.getAltPhone()).trim().equals(""))
        insertPrepStmtObject.addNull();
      else {
        insertPrepStmtObject.addString(vo.getAltPhone().trim());
      }
      if (CommonFunction.checkNull(vo.getPinCode()).trim().equals(""))
        insertPrepStmtObject.addNull();
      else {
        insertPrepStmtObject.addString(vo.getPinCode().trim());
      }

      if (CommonFunction.checkNull(vo.getLbxState()).trim().equals(""))
        insertPrepStmtObject.addNull();
      else {
        insertPrepStmtObject.addString(vo.getLbxState().trim());
      }
      if (CommonFunction.checkNull(vo.getMakerDate()).equalsIgnoreCase(""))
        insertPrepStmtObject.addNull();
      else {
        insertPrepStmtObject.addString(vo.getMakerDate());
      }
      if (CommonFunction.checkNull(vo.getBpId()).trim().equals(""))
        insertPrepStmtObject.addNull();
      else {
        insertPrepStmtObject.addString(vo.getBpId().trim());
      }
      insertPrepStmtObject.setSql(bufInsSql.toString());
      logger.info(new StringBuilder().append("IN saveLeadCustomerAddressDetails() insert query1 ### ").append(insertPrepStmtObject.printQuery()).toString());
      qryList.add(insertPrepStmtObject);

      status = ConnectionDAO.sqlInsUpdDeletePrepStmt(qryList);
      logger.info(new StringBuilder().append("In saveLeadCustomerAddress..........status............").append(status).toString());
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
    finally
    {
      vo = null;
    }
    return status;
  }

  public ArrayList getriskCategoryList()
  {
    ArrayList list = new ArrayList();
    try
    {
      String query = " select VALUE, DESCRIPTION from generic_master where generic_key = 'RISK_CATEGORY' AND REC_STATUS = 'A' ";
      logger.info(new StringBuilder().append("RISK_CATEGORY Generic master value query :   ").append(query).toString());
      ArrayList riskCategoryList = ConnectionDAO.sqlSelect(query);

      logger.info(new StringBuilder().append("riskCategoryList ").append(riskCategoryList.size()).toString());
      for (int i = 0; i < riskCategoryList.size(); i++)
      {
        ArrayList data = (ArrayList)riskCategoryList.get(i);
        if (data.size() > 0)
        {
          ShowCustomerDetailVo addr = new ShowCustomerDetailVo();
          addr.setRiskCategoryCode(CommonFunction.checkNull(data.get(0)).toString());
          addr.setRiskCategoryDesc(CommonFunction.checkNull(data.get(1)).toString());
          list.add(addr);
        }
      }
    }
    catch (Exception e) {
      e.printStackTrace();
    }
    return list;
  }
  
  //Shashank Starts Here For UCIC
  public ArrayList<Object> getRelationShipFlagIndividual() {
	    ArrayList mainList = new ArrayList();
	    try
	    {
	      String query = "select VALUE,DESCRIPTION FROM generic_master  WHERE GENERIC_KEY='RELATION_FLAG' AND PARENT_VALUE='I' AND REC_STATUS='A'";
	      logger.info(new StringBuilder().append("getRelationShipFlagIndividual query for the relation *****").append(query).toString());
	      ArrayList list = ConnectionDAO.sqlSelect(query);

	      logger.info(new StringBuilder().append("size of tyhe list********").append(list.size()).toString());

	      for (int i = 0; i < list.size(); i++) {
	          ArrayList data = (ArrayList)list.get(i);
	          if (data.size() > 0) {
	            CustomerSaveVo relatVO = new CustomerSaveVo();
	            relatVO.setRelationCode(CommonFunction.checkNull(data.get(0)));
	            relatVO.setRelationshipS(CommonFunction.checkNull(data.get(1)));
	            mainList.add(relatVO);
	          }
	        }
	    }
	    catch (Exception e) {
	      e.printStackTrace();
	    }

	    return mainList;
	  }
  
  public ArrayList<Object> getRelationShipFlagCorporate() {
	    ArrayList mainList = new ArrayList();
	    try
	    {
	      String query = "select VALUE,DESCRIPTION FROM generic_master  WHERE GENERIC_KEY='RELATION_FLAG' AND PARENT_VALUE='C' AND REC_STATUS='A'";
	      logger.info(new StringBuilder().append(" getRelationShipFlagCorporate query for the relation *****").append(query).toString());
	      ArrayList list = ConnectionDAO.sqlSelect(query);

	      logger.info(new StringBuilder().append("size of tyhe list********").append(list.size()).toString());

	      for (int i = 0; i < list.size(); i++) {
	          ArrayList data = (ArrayList)list.get(i);
	          if (data.size() > 0) {
	            CustomerSaveVo relatVO = new CustomerSaveVo();
	            relatVO.setRelationCode(CommonFunction.checkNull(data.get(0)));
	            relatVO.setRelationshipS(CommonFunction.checkNull(data.get(1)));
	            mainList.add(relatVO);
	          }
	        }
	    }
	    catch (Exception e) {
	      e.printStackTrace();
	    }

	    return mainList;
	  }
  //Shashank Starts Here For UCIC

@Override
public ArrayList<Object> getCustomerDetails(String addId, String customerId,String tableName) {
    ArrayList mainList = new ArrayList();
    try
    {
    	String PrimaryPhoneQuery="select primary_phone from "+tableName+" where address_id='"+addId+"' and bpid='"+customerId+"'";
    	String primaryPhone=ConnectionDAO.singleReturn(PrimaryPhoneQuery);
      String query = "SELECT a.ADDRESS_ID,a.BPID,CUSTOMER_NAME,PRIMARY_PHONE,c.RELATIONSHIP,customer_type FROM COM_ADDRESS_M A JOIN GCD_CUSTOMER_M B ON A.BPID=B.CUSTOMER_ID left join COM_MOBILE_RELATION_DTL c on c.address_id=a.address_id and c.bpid=a.bpid and c.linked_bpid='"+customerId+"' WHERE BPTYPE='CS' AND PRIMARY_PHONE='"+primaryPhone+"'  and a.bpid<>'"+customerId+"'";
      logger.info(new StringBuilder().append(" getCustomerDetails query for the relation *****").append(query).toString());
      ArrayList list = ConnectionDAO.sqlSelect(query);

      logger.info(new StringBuilder().append("size of tyhe list********").append(list.size()).toString());

      for (int i = 0; i < list.size(); i++) {
          ArrayList data = (ArrayList)list.get(i);
          if (data.size() > 0) {
            CustomerSaveVo relatVO = new CustomerSaveVo();
            relatVO.setRelationAddressId(CommonFunction.checkNull(data.get(0)));
            relatVO.setRelationCustomerId(CommonFunction.checkNull(data.get(1)));
            relatVO.setRelationCustomerName(CommonFunction.checkNull(data.get(2)));
            relatVO.setRelationPhone(CommonFunction.checkNull(data.get(3)));
            relatVO.setRelation(CommonFunction.checkNull(data.get(4)));
            relatVO.setRelationCustomerType(CommonFunction.checkNull(data.get(5)));
            mainList.add(relatVO);
          }
        }
    }
    catch (Exception e) {
      e.printStackTrace();
    }

    return mainList;
  }

@Override
public boolean saveRelationShipDetails(String addressId, String customerId, String relationship,
		String relationAddressId, String relationCustomerId,String tableName) {
	boolean status=false;
	String relation[]=relationship.split(",");
	String addrId[]=relationAddressId.split(",");
	String custId[]=relationCustomerId.split(",");
	ArrayList delList=new ArrayList();
	boolean st1=false;
	ArrayList qryList=new ArrayList();
	try{
		String deleteQuery="delete from COM_MOBILE_RELATION_DTL where LINKED_ADDRESS_ID='"+addressId+"' and LINKED_BPID='"+customerId+"' ";
		delList.add(deleteQuery);
		boolean st=ConnectionDAO.sqlInsUpdDelete(delList);
		for (int i=0;i<relation.length;i++){
	        PrepStmtObject insertPrepStmtObject = new PrepStmtObject();
	        StringBuffer bufInsSql = new StringBuffer();

	        bufInsSql.append("insert into COM_MOBILE_RELATION_DTL (ADDRESS_ID,BPID,LINKED_BPID,LINKED_ADDRESS_ID,RELATIONSHIP)");

	        bufInsSql.append(" values ( ");
	        bufInsSql.append(" ?,");
	        bufInsSql.append(" ?,");
	        bufInsSql.append(" ?,");
	        bufInsSql.append(" ?,");
	        bufInsSql.append(" ?)");
	        
	        if (CommonFunction.checkNull(addrId[i].trim()).equalsIgnoreCase(""))
	          insertPrepStmtObject.addNull();
	        else {
	          insertPrepStmtObject.addString(addrId[i].trim());
	        }
	        if (CommonFunction.checkNull(custId[i].trim()).equalsIgnoreCase(""))
	          insertPrepStmtObject.addNull();
	        else {
	          insertPrepStmtObject.addString(custId[i].trim());
	        }
	        if (CommonFunction.checkNull(customerId.trim()).equalsIgnoreCase(""))
	          insertPrepStmtObject.addNull();
	        else {
	          insertPrepStmtObject.addString(customerId.trim());
	        }
	        if (CommonFunction.checkNull(addressId.trim()).equalsIgnoreCase(""))
	          insertPrepStmtObject.addNull();
	        else {
	          insertPrepStmtObject.addString(addressId.trim());
	        }
	        if (CommonFunction.checkNull(relation[i].trim()).equalsIgnoreCase(""))
		          insertPrepStmtObject.addNull();
		        else {
		          insertPrepStmtObject.addString(relation[i].trim());
		        }

	         insertPrepStmtObject.setSql(bufInsSql.toString());
	        logger.info(new StringBuilder().append("IN saveRelationShipDetails() insert query1 ### ").append(insertPrepStmtObject.printQuery()).toString());
	        qryList.add(insertPrepStmtObject);
	        status = ConnectionDAO.sqlInsUpdDeletePrepStmt(qryList);
	      }
		/*if(status){*/
			String updateQuery="update "+tableName+" set RELATIONSHIP_FLAG='Y' where address_id='"+addressId+"'";
			logger.info("Relation updation in address:"+updateQuery);
			ArrayList updatelist=new ArrayList();
			updatelist.add(updateQuery);
			 st1=ConnectionDAO.sqlInsUpdDelete(updatelist);
			 logger.info("Relation updation in address :"+updateQuery);
			 logger.info("Status Relation updation in address :"+st1);
			 
				 String updateQuery1="update com_address_m set RELATIONSHIP_FLAG='Y' where address_id='"+addressId+"'";
					ArrayList updatelist1=new ArrayList();
					updatelist1.add(updateQuery1);
					boolean st2=ConnectionDAO.sqlInsUpdDelete(updatelist1);
					logger.info("Relation updation in address 1 :"+updateQuery1);
					 logger.info("Status Relation updation in address 1 :"+st2);
					 
					 String updateQuery2="update com_address_m_edit set RELATIONSHIP_FLAG='Y' where address_id='"+addressId+"'";
						ArrayList updatelist2=new ArrayList();
						updatelist2.add(updateQuery2);
						boolean st3=ConnectionDAO.sqlInsUpdDelete(updatelist2);
						logger.info("Relation updation in address 2 :"+updateQuery2);
						 logger.info("Status Relation updation in address 2 :"+st3);
						 
						 String updateQuery3="update com_address_m_temp set RELATIONSHIP_FLAG='Y' where address_id='"+addressId+"'";
							ArrayList updatelist3=new ArrayList();
							updatelist3.add(updateQuery3);
							boolean st4=ConnectionDAO.sqlInsUpdDelete(updatelist3);
							logger.info("Relation updation in address 3 :"+updateQuery3);
							 logger.info("Status Relation updation in address 3 :"+st4);
							 
							 String updateQuery4="update com_address_hst set RELATIONSHIP_FLAG='Y' where address_id='"+addressId+"'";
								ArrayList updatelist4=new ArrayList();
								updatelist4.add(updateQuery4);
								boolean st5=ConnectionDAO.sqlInsUpdDelete(updatelist4);
			
								logger.info("Relation updation in address 4 :"+updateQuery4);
								 logger.info("Status Relation updation in address 4 :"+st5);
		/*}*/
	}catch(Exception e){
		e.printStackTrace();
	}
	return st1;
}

public String getValidation(String code, Object pageStatus, String updateInMaker, String statusCase, String updateFlag, String pageStatuss, String source){
	
	 	String list = ""; 
	    String tableName = "";
	logger.info(new StringBuilder().append("pageStatuss: ").append(pageStatuss).append(" updateFlag: ").append(updateFlag).toString());
    if (((pageStatuss != null) && (pageStatuss.equals("fromLeads"))) || ((updateFlag != null) && (updateFlag.equals("updateFlag"))) || ((updateFlag != null) && (updateFlag.equals("notEdit"))))
    {
      logger.info("In Credit Processing , Customer Entry..,getValidation()......");
      tableName = "cr_deal_customer_m";
      try
      {
    	  	String query = "Select EmailVerificationFlag from "+tableName+" where customer_id="+code;
			logger.info("Query for checking record is verified or not  :  "+query);
			 list = ConnectionDAO.singleReturn(query);
			logger.info("list *?*************************** "+list);  
      }
      catch (Exception e) {
          e.printStackTrace();
        }
    }
    else
    {
        if (((pageStatus != null) && (pageStatus.equals("approve"))) || ((updateInMaker != null) && (updateInMaker.equals("updateInMaker"))) || (statusCase.equalsIgnoreCase("UnApproved"))) {
          tableName = "gcd_customer_m_temp";
        }
        else {
          tableName = "gcd_customer_m";
          if (CommonFunction.checkNull(source).trim().equalsIgnoreCase("ED"))
            tableName = "gcd_customer_m_edit";
        }
        try
        {
      	  	String query = "Select EmailVerificationFlag from "+tableName+" where customer_id="+code;
  			logger.info("Query for checking record is verified or not  :  "+query);
  			list = ConnectionDAO.singleReturn(query);
  			logger.info("list *?*************************** "+list);  
        }
        catch (Exception e) {
            e.printStackTrace();
          }
    }
    return list;
}
}