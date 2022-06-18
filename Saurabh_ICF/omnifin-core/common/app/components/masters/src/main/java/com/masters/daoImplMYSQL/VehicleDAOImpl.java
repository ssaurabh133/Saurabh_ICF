package com.masters.daoImplMYSQL;

import com.connect.CommonFunction;
import com.connect.ConnectionDAO;
import com.connect.ConnectionDAOforEJB;
import com.connect.PrepStmtObject;
import com.masters.dao.VehicleDAO;
import com.masters.vo.VehicleApprovalGridVo;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Logger;
import org.apache.commons.lang.StringEscapeUtils;

public class VehicleDAOImpl
  implements VehicleDAO
{
  private static final Logger logger = Logger.getLogger(VehicleDAOImpl.class.getName());
  ResourceBundle resource = ResourceBundle.getBundle("com.yourcompany.struts.ApplicationResources");
  String dateFormatWithTime = this.resource.getString("lbl.dateWithTimeInDao");
  String dateFormat = this.resource.getString("lbl.dateInDao");

  DecimalFormat myFormatter = new DecimalFormat("###,##0.00######");
  int no = Integer.parseInt(this.resource.getString("msg.pageSizeForMaster"));

  public boolean insertVehicleApprovalGrid(Object ob)
  {
    VehicleApprovalGridVo vo = (VehicleApprovalGridVo)ob;
    boolean status = false;
    boolean status1 = false;

    logger.info(new StringBuilder().append("In insert Vehicle Approval Grid..........").append(vo.getMakerId()).toString());
    ArrayList qryList = new ArrayList();
    PrepStmtObject insertPrepStmtObject = new PrepStmtObject();

    String[] vehicleApprovalYearArr = vo.getVehicleApprovalYearArr();
    String[] vehicleApprovalBranchArr = vo.getVehicleApprovalBranchArr();
    String[] vehicleApprovalNationalArr = vo.getVehicleApprovalNationalArr();
    String[] vehicleApprovalHOArr = vo.getVehicleApprovalHOArr();

    logger.info(new StringBuilder().append("makerId.......").append(vo.getMakerId()).toString());
    logger.info(new StringBuilder().append("makerDate.......").append(vo.getMakerDate()).toString());
    try
    {
      for (int j = 0; j < vehicleApprovalYearArr.length; j++)
      {
        String sqlquery1 = new StringBuilder().append("select PRODUCT_ID from com_vehicle_approval_grid cvag LEFT JOIN com_old_vehicle_approval_grid covag ON covag.vehicle_approval_id = cvag.vehicle_approval_id where PRODUCT_ID='").append(StringEscapeUtils.escapeSql(vo.getLbxProductID().trim())).append("'AND MANUFACTURING_YEAR='").append(StringEscapeUtils.escapeSql(vehicleApprovalYearArr[j])).append("'AND (SCHEME_ID='").append(StringEscapeUtils.escapeSql(vo.getLbxScheme().trim())).append("' AND MODEL='").append(StringEscapeUtils.escapeSql(vo.getModelDescId().trim())).append("')").toString();
        logger.info(new StringBuilder().append("qry for used vehicle is  :: ").append(sqlquery1).toString());
        status1 = ConnectionDAO.checkStatus(sqlquery1);
      }
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }

    if (status1 == true) {
      status1 = false;
      return status1;
    }

    logger.info("In insertVehicleApprovalGrid.........inside ejb server file...........Dao Impl");

    String query = new StringBuilder().append("select PRODUCT_ID from com_vehicle_approval_grid where PRODUCT_ID='").append(StringEscapeUtils.escapeSql(vo.getLbxProductID().trim())).append("'").toString();
    logger.info(new StringBuilder().append("In insertVehicleApprovalGrid.......inside ejb server file..........Dao Impl").append(query).toString());

    boolean st = ConnectionDAOforEJB.checkStatus(query);
    try {
      logger.info("In insert Vehicle Approval Grid");
      StringBuffer bufInsSql = new StringBuffer();
      bufInsSql.append("insert into com_vehicle_approval_grid(PRODUCT_ID,SCHEME_ID,VEHICLE_TYPE,MANUFACTURER,MODEL,BRANCH_AMT,NATIONAL_HEAD_AMT,HEAD_OFFICE_AMT,REC_STATUS,MAKER_ID,MAKER_DATE)");
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
      bufInsSql.append(new StringBuilder().append(" DATE_ADD(STR_TO_DATE(?, '").append(this.dateFormatWithTime).append("'),INTERVAL CURTIME() HOUR_SECOND) )").toString());

      if (CommonFunction.checkNull(vo.getLbxProductID()).equalsIgnoreCase(""))
      {
        insertPrepStmtObject.addNull();
      }
      else insertPrepStmtObject.addString(vo.getLbxProductID().toUpperCase().trim());

      if (CommonFunction.checkNull(vo.getLbxScheme()).equalsIgnoreCase(""))
      {
        insertPrepStmtObject.addNull();
      }
      else insertPrepStmtObject.addString(vo.getLbxScheme().toUpperCase().trim());

      if (CommonFunction.checkNull(vo.getVehicleType()).equalsIgnoreCase(""))
      {
        insertPrepStmtObject.addNull();
      }
      else insertPrepStmtObject.addString(vo.getVehicleType().toUpperCase().trim());

      if (CommonFunction.checkNull(vo.getManufactId()).equalsIgnoreCase(""))
      {
        insertPrepStmtObject.addNull();
      }
      else insertPrepStmtObject.addString(vo.getManufactId().toUpperCase().trim());

      if (CommonFunction.checkNull(vo.getModelDescId()).equalsIgnoreCase(""))
      {
        insertPrepStmtObject.addNull();
      }
      else insertPrepStmtObject.addString(vo.getModelDescId().toUpperCase().trim());

      if (CommonFunction.checkNull(vo.getBranchAmt()).equalsIgnoreCase(""))
      {
        insertPrepStmtObject.addNull();
      }
      else insertPrepStmtObject.addString(vo.getBranchAmt().toUpperCase().trim());

      if (CommonFunction.checkNull(vo.getNationalAmt()).equalsIgnoreCase(""))
      {
        insertPrepStmtObject.addNull();
      }
      else insertPrepStmtObject.addString(vo.getNationalAmt().toUpperCase().trim());

      if (CommonFunction.checkNull(vo.getHoAmt()).equalsIgnoreCase(""))
      {
        insertPrepStmtObject.addNull();
      }
      else insertPrepStmtObject.addString(vo.getHoAmt().toUpperCase().trim());

      if (CommonFunction.checkNull("A").equalsIgnoreCase(""))
        insertPrepStmtObject.addNull();
      else {
        insertPrepStmtObject.addString("A");
      }
      if (CommonFunction.checkNull(vo.getMakerId()).equalsIgnoreCase(""))
      {
        insertPrepStmtObject.addNull();
      }
      else insertPrepStmtObject.addString(vo.getMakerId());

      if (CommonFunction.checkNull(vo.getMakerDate()).equalsIgnoreCase(""))
      {
        insertPrepStmtObject.addNull();
      }
      else insertPrepStmtObject.addString(vo.getMakerDate());

      insertPrepStmtObject.setSql(bufInsSql.toString());
      qryList.add(insertPrepStmtObject);

      logger.info(new StringBuilder().append("IN insertVehicleApprovalGrid() insert query1 ### ").append(insertPrepStmtObject.printQuery()).toString());
      status = ConnectionDAOforEJB.sqlInsUpdDeletePrepStmt(qryList);
      logger.info(new StringBuilder().append("In saveVehicleApprovalData...................... ").append(status).toString());

      String sqlquery1 = "select max(vehicle_approval_id) from com_vehicle_approval_grid ";
      logger.info(new StringBuilder().append("qry for vehicle_approval_id :: ").append(sqlquery1).toString());
      String veh_id = ConnectionDAO.singleReturn(sqlquery1);
      logger.info(new StringBuilder().append("vehicle_approval_id for old vehicle approval grid ").append(veh_id).toString());

      if (status)
      {
        for (int i = 0; i < vehicleApprovalBranchArr.length; i++)
        {
          bufInsSql = null;
          insertPrepStmtObject = null;
          logger.info("In insert Old Vehicle Approval Grid");
          bufInsSql = new StringBuffer();
          insertPrepStmtObject = new PrepStmtObject();
          bufInsSql.append("insert into COM_OLD_VEHICLE_APPROVAL_GRID(VEHICLE_APPROVAL_ID,MANUFACTURING_YEAR,GRID_BRANCH_AMT,GRID_NATIONAL_HEAD_AMT,GRID_HEAD_OFFICE_AMT,REC_STATUS,MAKER_ID,MAKER_DATE)");
          bufInsSql.append(" values ( ");
          bufInsSql.append(" ?,");
          bufInsSql.append(" ?,");
          bufInsSql.append(" ?,");
          bufInsSql.append(" ?,");
          bufInsSql.append(" ?,");
          bufInsSql.append(" ?,");
          bufInsSql.append(" ?,");
          bufInsSql.append(new StringBuilder().append(" DATE_ADD(STR_TO_DATE(?, '").append(this.dateFormatWithTime).append("'),INTERVAL CURTIME() HOUR_SECOND) )").toString());

          if (CommonFunction.checkNull(veh_id).equalsIgnoreCase(""))
          {
            insertPrepStmtObject.addNull();
          }
          else insertPrepStmtObject.addString(veh_id);

          if (CommonFunction.checkNull(vehicleApprovalYearArr[i]).equalsIgnoreCase(""))
            insertPrepStmtObject.addInt(0L);
          else {
            insertPrepStmtObject.addString(vehicleApprovalYearArr[i]);
          }
          if (CommonFunction.checkNull(vehicleApprovalBranchArr[i]).equalsIgnoreCase(""))
            insertPrepStmtObject.addInt(0L);
          else {
            insertPrepStmtObject.addString(vehicleApprovalBranchArr[i]);
          }
          if (CommonFunction.checkNull(vehicleApprovalNationalArr[i]).equalsIgnoreCase(""))
            insertPrepStmtObject.addInt(0L);
          else {
            insertPrepStmtObject.addString(vehicleApprovalNationalArr[i]);
          }
          if (CommonFunction.checkNull(vehicleApprovalHOArr[i]).equalsIgnoreCase(""))
            insertPrepStmtObject.addInt(0L);
          else {
            insertPrepStmtObject.addString(vehicleApprovalHOArr[i]);
          }
          if (CommonFunction.checkNull("A").equalsIgnoreCase(""))
            insertPrepStmtObject.addNull();
          else {
            insertPrepStmtObject.addString("A");
          }
          if (CommonFunction.checkNull(vo.getMakerId()).equalsIgnoreCase(""))
          {
            insertPrepStmtObject.addNull();
          }
          else insertPrepStmtObject.addString(vo.getMakerId());

          if (CommonFunction.checkNull(vo.getMakerDate()).equalsIgnoreCase(""))
          {
            insertPrepStmtObject.addNull();
          }
          else insertPrepStmtObject.addString(vo.getMakerDate());

          insertPrepStmtObject.setSql(bufInsSql.toString());
          qryList.add(insertPrepStmtObject);

          logger.info(new StringBuilder().append("IN insertVehicleApprovalGrid() insert query1 ### ").append(insertPrepStmtObject.printQuery()).toString());
          status = ConnectionDAOforEJB.sqlInsUpdDeletePrepStmt(qryList);
          logger.info(new StringBuilder().append("In saveVehicleApprovalData...................... ").append(status).toString());
        }
      }
    }
    catch (Exception e) {
      e.printStackTrace();
    }
    finally {
      qryList = null;
      insertPrepStmtObject = null;
    }

    return status;
  }

  public ArrayList getVehicleApprovalSearchGrid(Object ob)
  {
    int count = 0;
    int startRecordIndex = 0;
    int endRecordIndex = this.no;

    ArrayList searchlist = new ArrayList();
    VehicleApprovalGridVo vehicleApprovalGridVo = (VehicleApprovalGridVo)ob;
    ArrayList detailList = new ArrayList();
    try
    {
      logger.info("In getVehicleApprovalSearchGrid()..............inside ejb server file.......................Dao Impl");

      String lbxProductID = StringEscapeUtils.escapeSql(CommonFunction.checkNull(vehicleApprovalGridVo.getLbxProductID())).trim();
      String lbxScheme = StringEscapeUtils.escapeSql(CommonFunction.checkNull(vehicleApprovalGridVo.getLbxScheme())).trim();
      String manufactId = StringEscapeUtils.escapeSql(CommonFunction.checkNull(vehicleApprovalGridVo.getManufactId())).trim();
      String branchAmt = StringEscapeUtils.escapeSql(CommonFunction.checkNull(vehicleApprovalGridVo.getBranchAmt())).trim();
      String nationalAmt = StringEscapeUtils.escapeSql(CommonFunction.checkNull(vehicleApprovalGridVo.getNationalAmt())).trim();
      String hoAmt = StringEscapeUtils.escapeSql(CommonFunction.checkNull(vehicleApprovalGridVo.getHoAmt())).trim();

      StringBuffer bufInsSql = new StringBuffer();
      StringBuffer bufInsSqlTempCount = new StringBuffer();

      bufInsSql.append("SELECT DISTINCT VEHICLE_APPROVAL_ID,cvag.PRODUCT_ID,csm.SCHEME_DESC, cdm.DEALER_DESC as MANUFACTURER_DESC,BRANCH_AMT,NATIONAL_HEAD_AMT,HEAD_OFFICE_AMT FROM com_vehicle_approval_grid cvag LEFT JOIN cr_product_m cpm ON cpm.PRODUCT_ID= cvag.PRODUCT_ID LEFT JOIN cr_scheme_m csm ON csm.SCHEME_ID= cvag.SCHEME_ID LEFT JOIN cr_dsa_dealer_m cdm ON cdm.DEALER_ID= cvag.MANUFACTURER AND BP_TYPE='MF' WHERE 'a'='a' ");

      bufInsSqlTempCount.append("SELECT COUNT(DISTINCT VEHICLE_APPROVAL_ID) FROM com_vehicle_approval_grid cvag WHERE 'a'='a' ");

      if ((!lbxProductID.equals("")) && (!lbxScheme.equals(""))) {
        bufInsSql.append(new StringBuilder().append(" AND cvag.PRODUCT_ID = '").append(lbxProductID).append("' AND cvag.SCHEME_ID = '").append(lbxScheme).append("' ").toString());
        bufInsSqlTempCount.append(new StringBuilder().append(" AND cvag.PRODUCT_ID = '").append(lbxProductID).append("' AND cvag.SCHEME_ID = '").append(lbxScheme).append("' ").toString());
      }
      else if (!manufactId.equals("")) {
        bufInsSql.append(new StringBuilder().append(" AND MANUFACT_ID like '%").append(manufactId).append("%' ").toString());
        bufInsSqlTempCount.append(new StringBuilder().append(" AND MANUFACT_ID like '%").append(manufactId).append("%' ").toString());
      }
      else if (!branchAmt.equals("")) {
        bufInsSql.append(new StringBuilder().append(" AND BRANCH_AMT like '%").append(branchAmt).append("%' ").toString());
        bufInsSqlTempCount.append(new StringBuilder().append(" AND BRANCH_AMT like '%").append(branchAmt).append("%' ").toString());
      }
      else if (!nationalAmt.equals("")) {
        bufInsSql.append(new StringBuilder().append(" AND NATIONAL_HEAD_AMT like '%").append(nationalAmt).append("%' ").toString());
        bufInsSqlTempCount.append(new StringBuilder().append(" AND NATIONAL_HEAD_AMT like '%").append(nationalAmt).append("%' ").toString());
      }
      else if (!hoAmt.equals("")) {
        bufInsSql.append(new StringBuilder().append(" AND HEAD_OFFICE_AMT like '%").append(hoAmt).append("%' ").toString());
        bufInsSqlTempCount.append(new StringBuilder().append(" AND HEAD_OFFICE_AMT like '%").append(hoAmt).append("%' ").toString());
      }

      logger.info(new StringBuilder().append("search Query....").append(bufInsSql).toString());
      logger.info(new StringBuilder().append("bufInsSqlTempCount **************************** : ").append(bufInsSqlTempCount.toString()).toString());
      count = Integer.parseInt(ConnectionDAOforEJB.singleReturn(bufInsSqlTempCount.toString()));

      logger.info(new StringBuilder().append("current PAge Link no .................... ").append(vehicleApprovalGridVo.getCurrentPageLink()).toString());
      if (vehicleApprovalGridVo.getCurrentPageLink() > 1)
      {
        startRecordIndex = (vehicleApprovalGridVo.getCurrentPageLink() - 1) * this.no;
        endRecordIndex = this.no;
        logger.info(new StringBuilder().append("startRecordIndex .................... ").append(startRecordIndex).toString());
        logger.info(new StringBuilder().append("endRecordIndex .................... ").append(endRecordIndex).toString());
      }

      bufInsSql.append(new StringBuilder().append(" limit ").append(startRecordIndex).append(",").append(endRecordIndex).toString());
      logger.info(new StringBuilder().append("query : ").append(bufInsSql).toString());
      searchlist = ConnectionDAOforEJB.sqlSelect(bufInsSql.toString());

      logger.info(new StringBuilder().append("IN getVehicleApprovalSearchGrid() search query1 ### ").append(bufInsSql.toString()).toString());
      logger.info("In getVehicleApprovalSearchGrid.....................................Dao Impl");
      logger.info(new StringBuilder().append("getVehicleApprovalSearchGrid ").append(searchlist.size()).toString());

      for (int i = 0; i < searchlist.size(); i++) {
        ArrayList data = (ArrayList)searchlist.get(i);

        if (data.size() > 0) {
          VehicleApprovalGridVo vehiVO = new VehicleApprovalGridVo();
          vehiVO.setVehicleApprovalID(new StringBuilder().append("<a href=vehicleApprovalGridDispatchAction.do?method=selectVehicleApprovalGrid&vehicleApprovalId=").append(CommonFunction.checkNull(data.get(0)).toString()).append(">").append(CommonFunction.checkNull(data.get(0)).toString()).append("</a>").toString());

          vehiVO.setLbxProductID(CommonFunction.checkNull(data.get(1)).toString());
          vehiVO.setLbxScheme(CommonFunction.checkNull(data.get(2)).toString());
          vehiVO.setManufactId(CommonFunction.checkNull(data.get(3)).toString());
          vehiVO.setBranchAmt(CommonFunction.checkNull(data.get(4)).toString());
          vehiVO.setNationalAmt(CommonFunction.checkNull(data.get(5)).toString());
          vehiVO.setHoAmt(CommonFunction.checkNull(data.get(6)).toString());
          vehiVO.setTotalRecordSize(count);
          detailList.add(vehiVO);
        }
      }
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }

    return detailList;
  }

  public String getNoOfYearAtUsedVehicle()
  {
    StringBuilder query = new StringBuilder();
    String result = null;
    query.append("select PARAMETER_VALUE from parameter_mst where PARAMETER_KEY='USED_VEHICLE_NO_OF_YEAR'");
    try
    {
      result = ConnectionDAO.singleReturn(query.toString());
      query = null;
    }
    catch (Exception e) {
      e.printStackTrace();
    }return result;
  }

  public boolean updateVehicleApprovalGrid(Object ob)
  {
    VehicleApprovalGridVo vo = (VehicleApprovalGridVo)ob;
    boolean status = false;
    boolean status1 = false;

    logger.info(new StringBuilder().append("In update Vehicle Approval Grid..........").append(vo.getMakerId()).toString());
    PrepStmtObject insertPrepStmtObject = null;
    StringBuffer bufInsSql = null;

    String[] vehicleApprovalYearArr = vo.getVehicleApprovalYearArr();
    String[] vehicleApprovalBranchArr = vo.getVehicleApprovalBranchArr();
    String[] vehicleApprovalNationalArr = vo.getVehicleApprovalNationalArr();
    String[] vehicleApprovalHOArr = vo.getVehicleApprovalHOArr();
    ArrayList qryList = new ArrayList();
    ArrayList delQryList = new ArrayList();

    logger.info(new StringBuilder().append("makerId.......").append(vo.getMakerId()).toString());
    logger.info(new StringBuilder().append("makerDate.......").append(vo.getMakerDate()).toString());
    try
    {
      logger.info("In update Vehicle Approval Grid.........inside ejb server file...........Dao Impl");

      String query = new StringBuilder().append("select PRODUCT_ID from com_vehicle_approval_grid cvag LEFT JOIN com_old_vehicle_approval_grid covag ON cvag.VEHICLE_APPROVAL_ID=covag.VEHICLE_APPROVAL_ID where cvag.VEHICLE_APPROVAL_ID='").append(StringEscapeUtils.escapeSql(vo.getVehicleApprovalID().trim())).append("'").toString();
      logger.info(new StringBuilder().append("In insertVehicleApprovalGrid.......inside ejb server file..........Dao Impl ").append(query).toString());

      status1 = ConnectionDAOforEJB.checkStatus(query);
      logger.info(new StringBuilder().append("status1... ").append(status1).toString());

      bufInsSql = new StringBuffer();
      insertPrepStmtObject = new PrepStmtObject();

      bufInsSql.append("UPDATE com_vehicle_approval_grid set PRODUCT_ID=?, SCHEME_ID=?, VEHICLE_TYPE=?, MANUFACTURER=?, MODEL=?, BRANCH_AMT=?, NATIONAL_HEAD_AMT=?, HEAD_OFFICE_AMT=? where VEHICLE_APPROVAL_ID=? ");

      if (CommonFunction.checkNull(vo.getLbxProductID()).equalsIgnoreCase(""))
      {
        insertPrepStmtObject.addNull();
      }
      else insertPrepStmtObject.addString(vo.getLbxProductID().toUpperCase().trim());

      if (CommonFunction.checkNull(vo.getLbxScheme()).equalsIgnoreCase(""))
      {
        insertPrepStmtObject.addNull();
      }
      else insertPrepStmtObject.addString(vo.getLbxScheme().toUpperCase().trim());

      if (CommonFunction.checkNull(vo.getVehicleType()).equalsIgnoreCase(""))
      {
        insertPrepStmtObject.addNull();
      }
      else insertPrepStmtObject.addString(vo.getVehicleType().toUpperCase().trim());

      if (CommonFunction.checkNull(vo.getManufactId()).equalsIgnoreCase(""))
      {
        insertPrepStmtObject.addNull();
      }
      else insertPrepStmtObject.addString(vo.getManufactId().toUpperCase().trim());

      if (CommonFunction.checkNull(vo.getModelDescId()).equalsIgnoreCase(""))
      {
        insertPrepStmtObject.addNull();
      }
      else insertPrepStmtObject.addString(vo.getModelDescId().toUpperCase().trim());

      if (CommonFunction.checkNull(vo.getBranchAmt()).equalsIgnoreCase(""))
      {
        insertPrepStmtObject.addNull();
      }
      else insertPrepStmtObject.addString(vo.getBranchAmt().toUpperCase().trim());

      if (CommonFunction.checkNull(vo.getNationalAmt()).equalsIgnoreCase(""))
      {
        insertPrepStmtObject.addNull();
      }
      else insertPrepStmtObject.addString(vo.getNationalAmt().toUpperCase().trim());

      if (CommonFunction.checkNull(vo.getHoAmt()).equalsIgnoreCase(""))
      {
        insertPrepStmtObject.addNull();
      }
      else insertPrepStmtObject.addString(vo.getHoAmt().toUpperCase().trim());

      if (CommonFunction.checkNull(vo.getVehicleApprovalID()).equalsIgnoreCase(""))
      {
        insertPrepStmtObject.addNull();
      }
      else insertPrepStmtObject.addString(vo.getVehicleApprovalID().toUpperCase().trim());

      insertPrepStmtObject.setSql(bufInsSql.toString());
      qryList.add(insertPrepStmtObject);
      logger.info(new StringBuilder().append("IN updateVehicleApprovalGrid() query1 ### ").append(insertPrepStmtObject.printQuery()).toString());
      status = ConnectionDAOforEJB.sqlInsUpdDeletePrepStmt(qryList);
      logger.info(new StringBuilder().append("In updateVehicleApprovalGrid...................... ").append(status).toString());

      if (status)
      {
        bufInsSql = new StringBuffer();
        bufInsSql.append(new StringBuilder().append("delete from com_old_vehicle_approval_grid where VEHICLE_APPROVAL_ID='").append(StringEscapeUtils.escapeSql(vo.getVehicleApprovalID().trim())).append("'").toString());
        delQryList.add(bufInsSql.toString());
        status = ConnectionDAO.sqlInsUpdDelete(delQryList);
        logger.info(new StringBuilder().append("status for delete query :").append(status).toString());

        for (int i = 0; i < vehicleApprovalBranchArr.length; i++)
        {
          bufInsSql = null;
          insertPrepStmtObject = null;
          logger.info("In update Vehicle Approval Grid");
          insertPrepStmtObject = new PrepStmtObject();
          bufInsSql = new StringBuffer();
          bufInsSql.append("insert into COM_OLD_VEHICLE_APPROVAL_GRID(VEHICLE_APPROVAL_ID,MANUFACTURING_YEAR,GRID_BRANCH_AMT,GRID_NATIONAL_HEAD_AMT,GRID_HEAD_OFFICE_AMT,REC_STATUS,MAKER_ID,MAKER_DATE)");
          bufInsSql.append(" values ( ");
          bufInsSql.append(" ?,");
          bufInsSql.append(" ?,");
          bufInsSql.append(" ?,");
          bufInsSql.append(" ?,");
          bufInsSql.append(" ?,");
          bufInsSql.append(" ?,");
          bufInsSql.append(" ?,");
          bufInsSql.append(new StringBuilder().append(" DATE_ADD(STR_TO_DATE(?, '").append(this.dateFormatWithTime).append("'),INTERVAL CURTIME() HOUR_SECOND) )").toString());

          if (CommonFunction.checkNull(vo.getVehicleApprovalID()).equalsIgnoreCase(""))
          {
            insertPrepStmtObject.addNull();
          }
          else insertPrepStmtObject.addString(vo.getVehicleApprovalID().toUpperCase().trim());

          if (CommonFunction.checkNull(vehicleApprovalYearArr[i]).equalsIgnoreCase(""))
            insertPrepStmtObject.addInt(0L);
          else {
            insertPrepStmtObject.addString(vehicleApprovalYearArr[i]);
          }
          if (CommonFunction.checkNull(vehicleApprovalBranchArr[i]).equalsIgnoreCase(""))
            insertPrepStmtObject.addInt(0L);
          else {
            insertPrepStmtObject.addString(vehicleApprovalBranchArr[i]);
          }
          if (CommonFunction.checkNull(vehicleApprovalNationalArr[i]).equalsIgnoreCase(""))
            insertPrepStmtObject.addInt(0L);
          else {
            insertPrepStmtObject.addString(vehicleApprovalNationalArr[i]);
          }
          if (CommonFunction.checkNull(vehicleApprovalHOArr[i]).equalsIgnoreCase(""))
            insertPrepStmtObject.addInt(0L);
          else {
            insertPrepStmtObject.addString(vehicleApprovalHOArr[i]);
          }
          if (CommonFunction.checkNull("A").equalsIgnoreCase(""))
            insertPrepStmtObject.addNull();
          else {
            insertPrepStmtObject.addString("A");
          }
          if (CommonFunction.checkNull(vo.getMakerId()).equalsIgnoreCase(""))
            insertPrepStmtObject.addNull();
          else {
            insertPrepStmtObject.addString(vo.getMakerId());
          }
          if (CommonFunction.checkNull(vo.getMakerDate()).equalsIgnoreCase(""))
            insertPrepStmtObject.addNull();
          else {
            insertPrepStmtObject.addString(vo.getMakerDate());
          }
          insertPrepStmtObject.setSql(bufInsSql.toString());
          qryList.add(insertPrepStmtObject);
          logger.info(new StringBuilder().append("IN updateVehicleApprovalGrid() insert query1 ### ").append(insertPrepStmtObject.printQuery()).toString());
          status = ConnectionDAOforEJB.sqlInsUpdDeletePrepStmt(qryList);
          logger.info(new StringBuilder().append("In updateVehicleApprovalGrid...................... ").append(status).toString());
        }
      }
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
    finally {
      qryList = null;
      insertPrepStmtObject = null;
    }

    return status;
  }

  public ArrayList<VehicleApprovalGridVo> selectVehicleApprovalGrid(String vehicleApprovalID)
  {
    logger.info(new StringBuilder().append("In selectVehicleApprovalGrid On basis of Product_ID ").append(vehicleApprovalID).toString());
    ArrayList searchlist = new ArrayList();
    ArrayList list = new ArrayList();
    StringBuilder bufInsSql = new StringBuilder();
    VehicleApprovalGridVo dataVo = null;
    ArrayList data = null;
    try
    {
      bufInsSql.append(new StringBuilder().append("SELECT DISTINCT cvag.VEHICLE_APPROVAL_ID,cvag.PRODUCT_ID,cpm.PRODUCT_DESC,cvag.SCHEME_ID,csm.SCHEME_DESC,VEHICLE_TYPE,cvag.MANUFACTURER,cdm.DEALER_DESC as MANUFACTURER_DESC,cvag.MODEL,cmm.MODEL as MODEL_DESC ,BRANCH_AMT,NATIONAL_HEAD_AMT,HEAD_OFFICE_AMT,GRID_BRANCH_AMT,GRID_NATIONAL_HEAD_AMT,GRID_HEAD_OFFICE_AMT,MANUFACTURING_YEAR FROM com_vehicle_approval_grid cvag  LEFT JOIN cr_product_m cpm ON cpm.PRODUCT_ID= cvag.PRODUCT_ID LEFT JOIN cr_scheme_m csm ON csm.SCHEME_ID= cvag.SCHEME_ID LEFT JOIN cr_dsa_dealer_m cdm ON cdm.DEALER_ID= cvag.MANUFACTURER AND BP_TYPE='MF' LEFT JOIN CR_MAKE_MODEL_MASTER cmm ON cmm.MAKE_MODEL_ID= cvag.MODEL LEFT JOIN com_old_vehicle_approval_grid covag ON covag.VEHICLE_APPROVAL_ID= cvag.VEHICLE_APPROVAL_ID  where cvag.VEHICLE_APPROVAL_ID='").append(vehicleApprovalID).append("'").toString());

      logger.info(new StringBuilder().append("query for selectVehicleApprovalGrid On the basis of Product_ID  : ").append(bufInsSql.toString()).toString());

      searchlist = ConnectionDAOforEJB.sqlSelect(bufInsSql.toString());
      int size = searchlist.size();

      for (int i = 0; i < size; i++)
      {
        data = (ArrayList)searchlist.get(i);

        if ((data != null) && (data.size() > 0))
        {
          dataVo = new VehicleApprovalGridVo();

          dataVo.setVehicleApprovalID(CommonFunction.checkNull(data.get(0)).trim());
          dataVo.setLbxProductID(CommonFunction.checkNull(data.get(1)).trim());
          dataVo.setProduct(CommonFunction.checkNull(data.get(2)).trim());
          dataVo.setLbxScheme(CommonFunction.checkNull(data.get(3)).trim());
          dataVo.setScheme(CommonFunction.checkNull(data.get(4)).trim());
          dataVo.setVehicleType(CommonFunction.checkNull(data.get(5)).trim());
          dataVo.setManufactId(CommonFunction.checkNull(data.get(6)).trim());
          dataVo.setManufacturer(CommonFunction.checkNull(data.get(7)).trim());
          dataVo.setModelDescId(CommonFunction.checkNull(data.get(8)).trim());
          dataVo.setModelDesc(CommonFunction.checkNull(data.get(9)).trim());
          dataVo.setBranchAmt(CommonFunction.checkNull(data.get(10)).trim());
          dataVo.setNationalAmt(CommonFunction.checkNull(data.get(11)).trim());
          dataVo.setHoAmt(CommonFunction.checkNull(data.get(12)).trim());
          dataVo.setGridBranchAmt(CommonFunction.checkNull(data.get(13)).trim());
          dataVo.setGridNationalAmt(CommonFunction.checkNull(data.get(14)).trim());
          dataVo.setGridHoAmt(CommonFunction.checkNull(data.get(15)).trim());
          dataVo.setVehicleApprovalYear(CommonFunction.checkNull(data.get(16)).trim());
          list.add(dataVo);
        }
      }
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
    finally
    {
      searchlist.clear();
      searchlist = null;
      bufInsSql = null;
      dataVo = null;
      data = null;
    }

    return list;
  }
}