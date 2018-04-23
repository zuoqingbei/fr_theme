package com.fr.hailian.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fr.hailian.core.BaseServlet;
import com.fr.hailian.service.NotReadAllMsgService;
import com.fr.hailian.service.UserDataFromRoleService;
import com.fr.hailian.util.KeyUtil;
import com.fr.hailian.util.RoleUtil;
import com.fr.json.JSONObject;

/***
 * 所有消息的未读数
 * @author Tom
 *
 */
public class NotReadAllMsgServlet extends BaseServlet{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public void destroy() {
		super.destroy(); // Just puts "destroy" string in log
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		getNotReadAllMsg(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		getNotReadAllMsg(request, response);
	}
	
	private void getNotReadAllMsg(HttpServletRequest request, HttpServletResponse response) {
		HttpServletRequest hrequest = (HttpServletRequest) request;//web资源
		JSONObject r = new JSONObject();
		NotReadAllMsgService notReadAllMsg = new NotReadAllMsgService();
		UserDataFromRoleService user = new UserDataFromRoleService();
		//NotReadMsgService notReadMsg = new NotReadMsgService();
		long userId = RoleUtil.getCurrentUser(hrequest).getId();
		String userName = RoleUtil.getCurrentUser(hrequest).getUsername();
		String type ="";
		//String userName = "Test";
		try {
			//name = java.net.URLDecoder.decode(hrequest.getParameter("username"), "UTF-8");
			//System.out.println("name:" + name);
			type = java.net.URLDecoder.decode(hrequest.getParameter("type"), "UTF-8");
			String roleName = user.getRoleNameByUserId(userId+"");
			String jysStr=hrequest.getParameter("jysStr");
			//String roleName = KeyUtil.getKeyValue("DZ");
			//System.out.println("roleName:" + roleName);
			//System.out.println("type:" + type);
			//获取角色具有查看信息的交易所
			String jys=UserDataFromRoleService.getDepartMenByUserName(userName);
			jys = "("+jys+")";
			System.out.println(jys);
			long count = 0L;
			//判断当前用户为何种角色，通过角色去寻找当前的用户
			//如果是处理人，总的未读数应该加上风险事件数
			if(roleName.contains(KeyUtil.getKeyValue("DZCLR"))){
				count = notReadAllMsg.getUnReadAllMsgCount("DEAL",type,jys);
				//count += notReadMsg.getUnReadMsgCount(userName,type);
			}else if(roleName.contains(KeyUtil.getKeyValue("QYCLR"))){
				count = notReadAllMsg.getUnReadAllMsgCount("DEAL",type,jys);
				//count += notReadMsg.getUnReadMsgCount(userName,type);
			}else if(roleName.contains(KeyUtil.getKeyValue("DZSHR"))){
				count = notReadAllMsg.getUnReadAllMsgCount("AUDIT",type,jys);
			}else if(roleName.contains(KeyUtil.getKeyValue("QYSHR"))){
				count = notReadAllMsg.getUnReadAllMsgCount("AUDIT",type,jys);
			}else if(roleName.contains(KeyUtil.getKeyValue("DZJCR"))){
				count = notReadAllMsg.getUnReadAllMsgCount("JUDGE",type,jys);
			}else if(roleName.contains(KeyUtil.getKeyValue("QYJCR"))){
				count = notReadAllMsg.getUnReadAllMsgCount("JUDGE",type,jys);
			}
			//P2P 小贷
			else if(roleName.contains(KeyUtil.getKeyValue("P2PCLR"))){//P2P处理人
				count = notReadAllMsg.getUnReadAllMsgCount("DEAL",type,jysStr);
			}else if(roleName.contains(KeyUtil.getKeyValue("XDCLR"))){//小贷处理人
				count = notReadAllMsg.getUnReadAllMsgCount("DEAL",type,jysStr);
			}else if(roleName.contains(KeyUtil.getKeyValue("P2PSHR"))){//审核人
				count = notReadAllMsg.getUnReadAllMsgCount("AUDIT",type,jysStr);
			}else if(roleName.contains(KeyUtil.getKeyValue("XDSHR"))){
				count = notReadAllMsg.getUnReadAllMsgCount("AUDIT",type,jysStr);
			}else if(roleName.contains(KeyUtil.getKeyValue("P2PJCR"))){//政府
				count = notReadAllMsg.getUnReadAllMsgCount("JUDGE",type,jysStr);
			}else if(roleName.contains(KeyUtil.getKeyValue("P2PJCR"))){
				count = notReadAllMsg.getUnReadAllMsgCount("JUDGE",type,jysStr);
			}
			System.out.println(count);
			//先获取消息总数
			r.put("unReadAllCount", count);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if("3".equals(type)||"4".equals(type)){
			response.setCharacterEncoding("UTF-8");
			response.setContentType("application/json; charset=utf-8");
			PrintWriter out = null;
			try {
				out = response.getWriter();
				out.append(com.alibaba.fastjson.JSONObject.toJSONString(user));
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				if (out != null) {
					out.close();
				}
			}
		}else{
			responseOutWithJson(response, r);
		}
	}

}
