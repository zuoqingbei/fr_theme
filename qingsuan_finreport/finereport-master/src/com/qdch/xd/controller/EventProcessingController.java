package com.qdch.xd.controller;

import com.fr.hailian.core.QdchUser;
import com.fr.hailian.util.JDBCUtil;
import com.fr.stable.StringUtils;

import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.Page;
import com.qdch.core.BaseController;
import com.qdch.util.ExportUtil;
import com.qdch.xd.model.*;


import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 
* @author doushuihai  
* @date 2018年3月30日下午1:07:26  
* @TODO 风险管理-风险事件处理
 */
public class EventProcessingController extends BaseController {
	private static RiskEventModel riskEventModelDao = RiskEventModel.dao;
	private static ExchangeInfoModel exchangeInfoModelDao = ExchangeInfoModel.dao;
	private static RiskTypeModel riskTypeModelDao = RiskTypeModel.dao;
	private static RiskEventHistoryModel riskEventHistoryModel  = RiskEventHistoryModel.dao;
	private static DictModel dictModelDao  = DictModel.dao;

	/**
	 * 
	* @author doushuihai  
	* @date 2018年3月30日下午1:08:45  
	* @TODO 风险事件页面
	 */
	public void index() {
//		renderJsp("xd/pages/riskSolve.jsp");
		//setAttr("type",getPara("type"));
		setAttr("user", getLoginUser());
		render("xd/pages/05_01fengxianshijianchuli.html");
	}

    /**
     *  交易所信息 /市场名称
     */
//	public void getExchageList(){
//	    List<ExchangeInfoModel> list = exchangeInfoModelDao.getList();
//        mRenderJson(list);
//
//    }
//	public
	public void getList(){
		int pageNum =Integer.parseInt(StringUtils.isBlank(getPara("pageNum"))||
				getPara("pageNum").equals("undefined")==true?
				"1":getPara("pageNum"));
		int pageSize =Integer.parseInt(StringUtils.isBlank(getPara("pageSize"))||
				getPara("pageSize").equals("undefined")
				==true?
				"10":getPara("pageSize"));
//		getPara(getRequest());
		getResponse().setCharacterEncoding("UTF-8");
		String checkstatus = ""; // 处理状态
		String role = "";
		if(role.equals("处理人")){
			checkstatus = " and clzt in ('未处理','驳回','已排除') ";
		}else if(role.equals("审核人")){
			checkstatus = " and clzt in ('已提交','已排查') ";
		}else if(role.equals("决策人")){
			checkstatus = " and clzt in ('已上报','已查阅''已确认') ";
		}
		Page<RiskEventModel> page = riskEventModelDao.getRiskEvent(checkstatus,pageNum,pageSize,getRequest());
		mRenderJson(page);
	}

//	public void getRiskType(){
//		mRenderJson(riskTypeModelDao.getByType("3")); //3--小贷 4--p2p
//	}

	/**
	 * 下拉框
	 */
	public void  getPullDown(){
		Map<String,Object> result  = new HashMap<String, Object>();
		List<RiskTypeModel> riskTypeList =  riskTypeModelDao.getTypeKind("3"); //风险类别
		List<RiskTypeModel> riskList =  riskTypeModelDao.getByType("3");
		List<ExchangeInfoModel> exchangeInfoModelList = exchangeInfoModelDao.getList(); //机构/市场

		result.put("type",riskTypeList);
		result.put("risk",riskList);
		result.put("exchange",exchangeInfoModelList);
		result.put("status",dictModelDao.getLabel(getPara("note")));
		mRenderJson(result);
	}

	public void exportExcel(){

		try {
			String[] tablename = new String[]{"风险事件ID","报警时间","风险类别","风险指标","风险指标值","阙值","超出额",
            "市场代码","市场名称","客户号","客户名称","业务菜单编码","业务菜单名称","业务类型","业务编码",
            "处理人","处理状态","处理时间"};
            List<RiskEventModel> riskEventModels = riskEventModelDao.getRiskEventList("",getRequest());
            String[][] content = new String[riskEventModels.size()][];
            List<String> list = new ArrayList<String>();
            for (int i = 0; i < riskEventModels.size(); i++) {
                content[i] = new String[tablename.length];
                RiskEventModel obj = riskEventModels.get(i);
                content[i][0] = toString(obj.get("fxsj_id"));
                content[i][1] = toString(obj.get("bjsj"));
                content[i][2] = toString(obj.get("fxlb"));
                content[i][3] = toString(obj.get("fxzb"));
                content[i][4] = toString(obj.get("fxzbz"));
                content[i][5] = toString(obj.get("yuzhi"));
                content[i][6] = toString(obj.get("cce"));
                content[i][7] = toString(obj.get("jgdm"));
                content[i][8] = toString(obj.get("jgmc"));
                content[i][9] = toString(obj.get("cust_id"));
                content[i][10] = toString(obj.get("khmc"));
                content[i][11] =toString(obj.get("ywcdbm"));
                content[i][12] = toString(obj.get("ywcdmc"));
                content[i][13] = toString(obj.get("ywlx"));
                content[i][14] = toString(obj.get("ywbm"));
                content[i][15] = toString(obj.get("shr"));
                content[i][16] = toString(obj.get("clzt"));
                content[i][17] = toString(obj.get("update_time"));

            }



            ExportUtil.toexcel(tablename,content,getResponse(),"风险事件");
		} catch (Exception e) {
			e.printStackTrace();
		}
		mRenderJson(null);
	}

	public String toString (Object object){
	    if(object==null){
	        return  "";
        }else{
	        return object.toString();
        }
    }

	/**
	 * 提交审核
	 */
	public void submitCheck(){
		try {
			RiskEventModel eventModel = riskEventModelDao.findById(getPara("id"));
			eventModel.set("clzt",getPara("status"));
			eventModel.set("shr","wf");
			eventModel.set("report_id","wf");
			eventModel.set("bz","wf");
			eventModel.set("update_time",new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(System.currentTimeMillis()));
			eventModel.dao.update();


//			RiskEventHistoryModel historyModel  = RiskEventHistoryModel.dao;
//			historyModel.set("fxsj_id","");
	//		historyModel.set("fxsj_id",eventModel.get("fxsj_id")+"");
	//		historyModel.set("shr",eventModel.get("shr")+"");
	//		historyModel.set("report_id",eventModel.get("report_id")+"");
	//		historyModel.set("bjsj",eventModel.get("bjsj")+"");
	//		historyModel.set("fxlb",eventModel.get("fxlb")+"");
	//		historyModel.set("fxzb",eventModel.get("fxzb")+"");
	//		historyModel.set("fxzbz",eventModel.get("yuzhi")+"");
	//		historyModel.set("yuzhi",eventModel.get("yuzhi")+"");
	//		historyModel.set("cce",eventModel.get("cce")+"");
	//		historyModel.set("jgdm",eventModel.get("jgdm")+"");
	//		historyModel.set("jgmc",eventModel.get("jgmc")+"");
	//		historyModel.set("cust_id",eventModel.get("cust_id")+"");
	//		historyModel.set("khmc",eventModel.get("khmc")+"");
	//		historyModel.set("ywcdbm",eventModel.get("ywcdbm")+"");
	//		historyModel.set("ywcdmc",eventModel.get("ywcdmc")+"");
	//		historyModel.set("ywlx",eventModel.get("ywlx")+"");
	//		historyModel.set("ywbm",eventModel.get("ywbm")+"");

	//		historyModel.set("update_time",new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(System.currentTimeMillis()));
	//		historyModel.save();
				StringBuffer sb = new StringBuffer();
				sb.append("update hub_fxsj ");
	//		sb.append("insert into hub_fxsj_audit_new(bjsj,fxlb,fxzb,fxzbz,yuzhi,cce," +
	//				"jgdm,jgmc,cust_id,khmc,ywcdbm,ywcdmc,ywlx,ywbm,shr,clzt,fxsm,bz,fxsj_id,unit," +
	//				"report_id,deal_id,update_time,jysfl,bq,yxcd,zbvalue)values(");
//				sb.append("insert into hub_fxsj_audit_new(shr,clzt,fxsj_id,update_time)values(");
	//		sb.append(eventModel.get("bjsj")+"");
	//		sb.append(eventModel.get("fxlb")+",");
	//		sb.append(eventModel.get("fxzb")+",");
	//		sb.append(eventModel.get("fxzbz")+",");
	//		sb.append(eventModel.get("yuzhi")+",");
	//		sb.append(eventModel.get("cce")+",");
	//		sb.append(eventModel.get("jgdm")+",");
	//		sb.append(eventModel.get("jgmc")+",");
	//		sb.append(eventModel.get("cust_id")+",");
	//		sb.append(eventModel.get("khmc")+",");
	//		sb.append(eventModel.get("ywcdbm")+",");
	//		sb.append(eventModel.get("ywcdmc")+",");
	//		sb.append(eventModel.get("ywlx")+",");
	//		sb.append(eventModel.get("ywbm")+",");
				sb.append(" set shr='").append(getLoginUser().getId());
				sb.append(", clzt='").append(eventModel.get("clzt")+"',");
	//		sb.append(eventModel.get("fxsm")+",");
			sb.append(",update_time'").append(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(System.currentTimeMillis()));
//				sb.append("'").append(eventModel.get("fxsj_id")+"',");
	//		sb.append(eventModel.get("unit")+",");
	//		sb.append(eventModel.get("report_id")+",");
	//		sb.append(eventModel.get("deal_id")+",");
//				sb.append("'").append(eventModel.get("update_time")+"'");
			sb.append(" where fxsj_id="+getPara("id")+"");
	//		sb.append(eventModel.get("jysfl")+",");
	//		sb.append(eventModel.get("bq")+",");
	//		sb.append(eventModel.get("yxcd")+",");
	//		sb.append(eventModel.get("zbvalue"));
//			sb.append(")");
			JDBCUtil.executeUpdate(sb.toString(),null);
			mRenderJson(true);
		} catch (Exception e) {
			e.printStackTrace();
			mRenderJson(false);
		}
	}




}
