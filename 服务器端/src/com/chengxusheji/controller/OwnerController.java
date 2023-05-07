package com.chengxusheji.controller;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import com.chengxusheji.utils.ExportExcelUtil;
import com.chengxusheji.utils.UserException;
import com.chengxusheji.service.OwnerService;
import com.chengxusheji.po.Owner;

//Owner管理控制层
@Controller
@RequestMapping("/Owner")
public class OwnerController extends BaseController {

    /*业务层对象*/
    @Resource OwnerService ownerService;

	@InitBinder("owner")
	public void initBinderOwner(WebDataBinder binder) {
		binder.setFieldDefaultPrefix("owner.");
	}
	/*跳转到添加Owner视图*/
	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String add(Model model,HttpServletRequest request) throws Exception {
		model.addAttribute(new Owner());
		return "Owner_add";
	}

	/*客户端ajax方式提交添加民宿主人信息*/
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public void add(Owner owner, BindingResult br,
			Model model, HttpServletRequest request,HttpServletResponse response) throws Exception {
		String message = "";
		boolean success = false;
		 
		if(ownerService.getOwner(owner.getOwnerUserName()) != null) {
			message = "宿主用户名已经存在！";
			writeJsonResponse(response, success, message);
			return ;
		}
		try {
			owner.setOwnerPhoto(this.handlePhotoUpload(request, "ownerPhotoFile"));
		} catch(UserException ex) {
			message = "图片格式不正确！";
			writeJsonResponse(response, success, message);
			return ;
		} 
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		owner.setRegTime(sdf.format(new java.util.Date()));
		
		owner.setShzt("待审核");
		
        ownerService.addOwner(owner);
        message = "民宿主人添加成功!";
        success = true;
        writeJsonResponse(response, success, message);
	}
	/*ajax方式按照查询条件分页查询民宿主人信息*/
	@RequestMapping(value = { "/list" }, method = {RequestMethod.GET,RequestMethod.POST})
	public void list(String ownerUserName,String name,String birthDate,String telephone,String shzt,Integer page,Integer rows, Model model, HttpServletRequest request,HttpServletResponse response) throws Exception {
		if (page==null || page == 0) page = 1;
		if (ownerUserName == null) ownerUserName = "";
		if (name == null) name = "";
		if (birthDate == null) birthDate = "";
		if (telephone == null) telephone = "";
		if (shzt == null) shzt = "";
		if(rows != 0)ownerService.setRows(rows);
		List<Owner> ownerList = ownerService.queryOwner(ownerUserName, name, birthDate, telephone, shzt, page);
	    /*计算总的页数和总的记录数*/
	    ownerService.queryTotalPageAndRecordNumber(ownerUserName, name, birthDate, telephone, shzt);
	    /*获取到总的页码数目*/
	    int totalPage = ownerService.getTotalPage();
	    /*当前查询条件下总记录数*/
	    int recordNumber = ownerService.getRecordNumber();
        response.setContentType("text/json;charset=UTF-8");
		PrintWriter out = response.getWriter();
		//将要被返回到客户端的对象
		JSONObject jsonObj=new JSONObject();
		jsonObj.accumulate("total", recordNumber);
		JSONArray jsonArray = new JSONArray();
		for(Owner owner:ownerList) {
			JSONObject jsonOwner = owner.getJsonObject();
			jsonArray.put(jsonOwner);
		}
		jsonObj.accumulate("rows", jsonArray);
		out.println(jsonObj.toString());
		out.flush();
		out.close();
	}

	/*ajax方式按照查询条件分页查询民宿主人信息*/
	@RequestMapping(value = { "/listAll" }, method = {RequestMethod.GET,RequestMethod.POST})
	public void listAll(HttpServletResponse response) throws Exception {
		List<Owner> ownerList = ownerService.queryAllOwner();
        response.setContentType("text/json;charset=UTF-8"); 
		PrintWriter out = response.getWriter();
		JSONArray jsonArray = new JSONArray();
		for(Owner owner:ownerList) {
			JSONObject jsonOwner = new JSONObject();
			jsonOwner.accumulate("ownerUserName", owner.getOwnerUserName());
			jsonOwner.accumulate("name", owner.getName());
			jsonArray.put(jsonOwner);
		}
		out.println(jsonArray.toString());
		out.flush();
		out.close();
	}

	/*前台按照查询条件分页查询民宿主人信息*/
	@RequestMapping(value = { "/frontlist" }, method = {RequestMethod.GET,RequestMethod.POST})
	public String frontlist(String ownerUserName,String name,String birthDate,String telephone,String shzt,Integer currentPage, Model model, HttpServletRequest request) throws Exception  {
		if (currentPage==null || currentPage == 0) currentPage = 1;
		if (ownerUserName == null) ownerUserName = "";
		if (name == null) name = "";
		if (birthDate == null) birthDate = "";
		if (telephone == null) telephone = "";
		if (shzt == null) shzt = "";
		List<Owner> ownerList = ownerService.queryOwner(ownerUserName, name, birthDate, telephone, shzt, currentPage);
	    /*计算总的页数和总的记录数*/
	    ownerService.queryTotalPageAndRecordNumber(ownerUserName, name, birthDate, telephone, shzt);
	    /*获取到总的页码数目*/
	    int totalPage = ownerService.getTotalPage();
	    /*当前查询条件下总记录数*/
	    int recordNumber = ownerService.getRecordNumber();
	    request.setAttribute("ownerList",  ownerList);
	    request.setAttribute("totalPage", totalPage);
	    request.setAttribute("recordNumber", recordNumber);
	    request.setAttribute("currentPage", currentPage);
	    request.setAttribute("ownerUserName", ownerUserName);
	    request.setAttribute("name", name);
	    request.setAttribute("birthDate", birthDate);
	    request.setAttribute("telephone", telephone);
	    request.setAttribute("shzt", shzt);
		return "Owner/owner_frontquery_result"; 
	}

     /*前台查询Owner信息*/
	@RequestMapping(value="/{ownerUserName}/frontshow",method=RequestMethod.GET)
	public String frontshow(@PathVariable String ownerUserName,Model model,HttpServletRequest request) throws Exception {
		/*根据主键ownerUserName获取Owner对象*/
        Owner owner = ownerService.getOwner(ownerUserName);

        request.setAttribute("owner",  owner);
        return "Owner/owner_frontshow";
	}

	/*ajax方式显示民宿主人修改jsp视图页*/
	@RequestMapping(value="/{ownerUserName}/update",method=RequestMethod.GET)
	public void update(@PathVariable String ownerUserName,Model model,HttpServletRequest request,HttpServletResponse response) throws Exception {
        /*根据主键ownerUserName获取Owner对象*/
        Owner owner = ownerService.getOwner(ownerUserName);

        response.setContentType("text/json;charset=UTF-8");
        PrintWriter out = response.getWriter();
		//将要被返回到客户端的对象 
		JSONObject jsonOwner = owner.getJsonObject();
		out.println(jsonOwner.toString());
		out.flush();
		out.close();
	}

	/*ajax方式更新民宿主人信息*/
	@RequestMapping(value = "/{ownerUserName}/update", method = RequestMethod.POST)
	public void update(@Validated Owner owner, BindingResult br,
			Model model, HttpServletRequest request,HttpServletResponse response) throws Exception {
		String message = "";
    	boolean success = false;
		if (br.hasErrors()) { 
			message = "输入的信息有错误！";
			writeJsonResponse(response, success, message);
			return;
		}
		String ownerPhotoFileName = this.handlePhotoUpload(request, "ownerPhotoFile");
		if(!ownerPhotoFileName.equals("upload/NoImage.jpg"))owner.setOwnerPhoto(ownerPhotoFileName); 


		try {
			ownerService.updateOwner(owner);
			message = "民宿主人更新成功!";
			success = true;
			writeJsonResponse(response, success, message);
		} catch (Exception e) {
			e.printStackTrace();
			message = "民宿主人更新失败!";
			writeJsonResponse(response, success, message); 
		}
	}
    /*删除民宿主人信息*/
	@RequestMapping(value="/{ownerUserName}/delete",method=RequestMethod.GET)
	public String delete(@PathVariable String ownerUserName,HttpServletRequest request) throws UnsupportedEncodingException {
		  try {
			  ownerService.deleteOwner(ownerUserName);
	            request.setAttribute("message", "民宿主人删除成功!");
	            return "message";
	        } catch (Exception e) { 
	            e.printStackTrace();
	            request.setAttribute("error", "民宿主人删除失败!");
				return "error";

	        }

	}

	/*ajax方式删除多条民宿主人记录*/
	@RequestMapping(value="/deletes",method=RequestMethod.POST)
	public void delete(String ownerUserNames,HttpServletRequest request,HttpServletResponse response) throws IOException, JSONException {
		String message = "";
    	boolean success = false;
        try { 
        	int count = ownerService.deleteOwners(ownerUserNames);
        	success = true;
        	message = count + "条记录删除成功";
        	writeJsonResponse(response, success, message);
        } catch (Exception e) { 
            //e.printStackTrace();
            message = "有记录存在外键约束,删除失败";
            writeJsonResponse(response, success, message);
        }
	}

	/*按照查询条件导出民宿主人信息到Excel*/
	@RequestMapping(value = { "/OutToExcel" }, method = {RequestMethod.GET,RequestMethod.POST})
	public void OutToExcel(String ownerUserName,String name,String birthDate,String telephone,String shzt, Model model, HttpServletRequest request,HttpServletResponse response) throws Exception {
        if(ownerUserName == null) ownerUserName = "";
        if(name == null) name = "";
        if(birthDate == null) birthDate = "";
        if(telephone == null) telephone = "";
        if(shzt == null) shzt = "";
        List<Owner> ownerList = ownerService.queryOwner(ownerUserName,name,birthDate,telephone,shzt);
        ExportExcelUtil ex = new ExportExcelUtil();
        String _title = "Owner信息记录"; 
        String[] headers = { "宿主用户名","宿主姓名","性别","出生日期","宿主照片","联系电话","注册时间","审核状态"};
        List<String[]> dataset = new ArrayList<String[]>(); 
        for(int i=0;i<ownerList.size();i++) {
        	Owner owner = ownerList.get(i); 
        	dataset.add(new String[]{owner.getOwnerUserName(),owner.getName(),owner.getGender(),owner.getBirthDate(),owner.getOwnerPhoto(),owner.getTelephone(),owner.getRegTime(),owner.getShzt()});
        }
        /*
        OutputStream out = null;
		try {
			out = new FileOutputStream("C://output.xls");
			ex.exportExcel(title,headers, dataset, out);
		    out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		*/
		OutputStream out = null;//创建一个输出流对象 
		try { 
			out = response.getOutputStream();//
			response.setHeader("Content-disposition","attachment; filename="+"Owner.xls");//filename是下载的xls的名，建议最好用英文 
			response.setContentType("application/msexcel;charset=UTF-8");//设置类型 
			response.setHeader("Pragma","No-cache");//设置头 
			response.setHeader("Cache-Control","no-cache");//设置头 
			response.setDateHeader("Expires", 0);//设置日期头  
			String rootPath = request.getSession().getServletContext().getRealPath("/");
			ex.exportExcel(rootPath,_title,headers, dataset, out);
			out.flush();
		} catch (IOException e) { 
			e.printStackTrace(); 
		}finally{
			try{
				if(out!=null){ 
					out.close(); 
				}
			}catch(IOException e){ 
				e.printStackTrace(); 
			} 
		}
    }
}
