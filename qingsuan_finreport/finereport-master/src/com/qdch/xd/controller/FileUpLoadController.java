package com.qdch.xd.controller;


import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.servlet.http.HttpServletRequest;



import javax.servlet.http.HttpServletResponse;

import com.fr.hailian.util.JDBCUtil;
import com.jfinal.upload.UploadFile;
import com.qdch.core.BaseController;
import com.qdch.xd.model.MonthlyReportModel;


/*
* 文件上传
* @author ljm  
* @date 2018年4月16日
* 
*/
public class FileUpLoadController extends BaseController {
	public void index(){
		setAttr("jyslist", MonthlyReportModel.dao.getJys(getDataScopeByUserName()));//在进入页面时就获取权限内表为条件下拉框做准备
		render("xd/pages/06_02yuebaoshangchuan.html");
	}
	//文件上传
	public void upload(){  
        //存储路径  
        String path ="D:/apache-tomcat-7.0.78/webapps/WebReport/pdf-files";  
        UploadFile file = getFile("file");  
        String fileName = "";  
        String type = file.getFileName().substring(file.getFileName().lastIndexOf(".")); // 获取文件的后缀  
        if(!type.equals(".pdf")) {  //只能上传pdf格式文件
           return; 
        }else{  
            //上传文件  
            fileName = file.getFileName();
            String dest = path + "/" + fileName;  
            file.getFile().renameTo(new File(dest));  
        }  
        String fname = file.getFileName();
        String title = fname;
        fname = title;

        String jys = getPara("jys");
        //获取交易所分类
        int jysfl = 0;
        List<MonthlyReportModel> list = MonthlyReportModel.dao.getJyscfl(jys);
        for (MonthlyReportModel m : list) {
        	jysfl=Integer.parseInt(m.getStr("jysfl"));
		}
        String bglx = getPara("bglx");
        
        String date = new SimpleDateFormat("yyyy-MM-dd").format(System.currentTimeMillis());
        String loginUser = getLoginUser().getUsername();
        
        
		String sql="insert into insight_regulatory_report "
				+ "(order_number,title,upload_time,upload_user,jys,type,file_name,jysfl) "
				+ "values(?,?,?,?,?,?,?,?)";
		
		Object[] params ={null,title,date,loginUser,jys,bglx,fname,jysfl};
		
		JDBCUtil.executeUpdate(sql, params, "insight");
		
		renderJson();  
		
    }  
	
	//文件下载

	
	public void downLoad(){ 
		String fileName = getPara("fileName");
        String downPath = "D:/apache-tomcat-7.0.78/webapps/WebReport/pdf-files";  
        File file=new File(downPath+"/"+fileName);  
        if(file.exists()){  
        	renderFile(file);
        }  
        else{  
            renderJson();  
        }  
    } 
	
	//文件预览
	public void fileView() {
		try {
			HttpServletResponse response = getResponse();
			boolean boo = true;
			
			String fileName = getPara("fileName");
			String filePath = "D:/apache-tomcat-7.0.78/webapps/WebReport/pdf-files"+"/"+fileName;
			
			File f = new File(filePath);
			if (!f.exists()) {
				response.sendError(404, "File not found!");
				return;
			}
			BufferedInputStream br = new BufferedInputStream(new FileInputStream(f));
			byte[] buf = new byte[1024];
			int len = 0;
			response.reset(); // 非常重要
			if (boo) { // 在线打开（预览）
				URL u = new URL("file:///" + filePath);
				response.setContentType(u.openConnection().getContentType());
				response.setHeader("Content-Disposition", "inline; fileName=" + f.getName());
			} 
			OutputStream out = response.getOutputStream();
			while ((len = br.read(buf)) > 0)
				out.write(buf, 0, len);
			br.close();
			out.close();
			renderNull();
		} catch (Exception e) {
			e.printStackTrace();
		} 
	}
	

	
	
	
	
}
	
	

