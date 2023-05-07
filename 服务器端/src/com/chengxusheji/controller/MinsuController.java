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
import com.chengxusheji.service.MinsuService;
import com.chengxusheji.po.Minsu;
import com.chengxusheji.service.AreaService;
import com.chengxusheji.po.Area;
import com.chengxusheji.service.OwnerService;
import com.chengxusheji.po.Owner;

//Minsu管理控制层
@Controller
@RequestMapping("/Minsu")
public class MinsuController extends BaseController {

    /*业务层对象*/
    @Resource MinsuService minsuService;

    @Resource AreaService areaService;
    @Resource OwnerService ownerService;
	@InitBinder("areaObj")
	public void initBinderareaObj(WebDataBinder binder) {
		binder.setFieldDefaultPrefix("areaObj.");
	}
	@InitBinder("ownerObj")
	public void initBinderownerObj(WebDataBinder binder) {
		binder.setFieldDefaultPrefix("ownerObj.");
	}
	@InitBinder("minsu")
	public void initBinderMinsu(WebDataBinder binder) {
		binder.setFieldDefaultPrefix("minsu.");
	}
	/*跳转到添加Minsu视图*/
	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String add(Model model,HttpServletRequest request) throws Exception {
		model.addAttribute(new Minsu());
		/*查询所有的Area信息*/
		List<Area> areaList = areaService.queryAllArea();
		request.setAttribute("areaList", areaList);
		/*查询所有的Owner信息*/
		List<Owner> ownerList = ownerService.queryAllOwner();
		request.setAttribute("ownerList", ownerList);
		return "Minsu_add";
	}

	/*客户端ajax方式提交添加民宿信息*/
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public void add(@Validated Minsu minsu, BindingResult br,
			Model model, HttpServletRequest request,HttpServletResponse response) throws Exception {
		String message = "";
		boolean success = false;
		if (br.hasErrors()) {
			message = "输入信息不符合要求！";
			writeJsonResponse(response, success, message);
			return ;
		}
		try {
			minsu.setMinsuPhoto(this.handlePhotoUpload(request, "minsuPhotoFile"));
		} catch(UserException ex) {
			message = "图片格式不正确！";
			writeJsonResponse(response, success, message);
			return ;
		}
        minsuService.addMinsu(minsu);
        message = "民宿添加成功!";
        success = true;
        writeJsonResponse(response, success, message);
	}
	
	
	/*客户端ajax方式提交添加民宿信息*/
	@RequestMapping(value = "/szAdd", method = RequestMethod.POST)
	public void szAdd(Minsu minsu, BindingResult br,
			Model model, HttpServletRequest request,HttpServletResponse response,HttpSession session) throws Exception {
		String message = "";
		boolean success = false;
		String user_name = (String) session.getAttribute("user_name");
		Owner ownerObj = new Owner();
		ownerObj.setOwnerUserName(user_name);
		
		minsu.setOwnerObj(ownerObj);
		
		try {
			minsu.setMinsuPhoto(this.handlePhotoUpload(request, "minsuPhotoFile"));
		} catch(UserException ex) {
			message = "图片格式不正确！";
			writeJsonResponse(response, success, message);
			return ;
		}
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		minsu.setAddTime(sdf.format(new java.util.Date()));

        minsuService.addMinsu(minsu);
        message = "民宿发布成功!";
        success = true;
        writeJsonResponse(response, success, message);
	}
	
	
	
	/*ajax方式按照查询条件分页查询民宿信息*/
	@RequestMapping(value = { "/list" }, method = {RequestMethod.GET,RequestMethod.POST})
	public void list(@ModelAttribute("ownerObj") Owner ownerObj,String addTime,@ModelAttribute("areaObj") Area areaObj,String minsuName,Integer page,Integer rows, Model model, HttpServletRequest request,HttpServletResponse response) throws Exception {
		if (page==null || page == 0) page = 1;
		if (addTime == null) addTime = "";
		if (minsuName == null) minsuName = "";
		if(rows != 0)minsuService.setRows(rows);
		List<Minsu> minsuList = minsuService.queryMinsu(ownerObj, addTime, areaObj, minsuName, page);
	    /*计算总的页数和总的记录数*/
	    minsuService.queryTotalPageAndRecordNumber(ownerObj, addTime, areaObj, minsuName);
	    /*获取到总的页码数目*/
	    int totalPage = minsuService.getTotalPage();
	    /*当前查询条件下总记录数*/
	    int recordNumber = minsuService.getRecordNumber();
        response.setContentType("text/json;charset=UTF-8");
		PrintWriter out = response.getWriter();
		//将要被返回到客户端的对象
		JSONObject jsonObj=new JSONObject();
		jsonObj.accumulate("total", recordNumber);
		JSONArray jsonArray = new JSONArray();
		for(Minsu minsu:minsuList) {
			JSONObject jsonMinsu = minsu.getJsonObject();
			jsonArray.put(jsonMinsu);
		}
		jsonObj.accumulate("rows", jsonArray);
		out.println(jsonObj.toString());
		out.flush();
		out.close();
	}

	/*ajax方式按照查询条件分页查询民宿信息*/
	@RequestMapping(value = { "/listAll" }, method = {RequestMethod.GET,RequestMethod.POST})
	public void listAll(HttpServletResponse response) throws Exception {
		List<Minsu> minsuList = minsuService.queryAllMinsu();
        response.setContentType("text/json;charset=UTF-8"); 
		PrintWriter out = response.getWriter();
		JSONArray jsonArray = new JSONArray();
		for(Minsu minsu:minsuList) {
			JSONObject jsonMinsu = new JSONObject();
			jsonMinsu.accumulate("minsuId", minsu.getMinsuId());
			jsonMinsu.accumulate("minsuName", minsu.getMinsuName());
			jsonArray.put(jsonMinsu);
		}
		out.println(jsonArray.toString());
		out.flush();
		out.close();
	}
	
	/*ajax方式按照查询条件分页查询民宿信息*/
	@RequestMapping(value = { "/szListAll" }, method = {RequestMethod.GET,RequestMethod.POST})
	public void szListAll(HttpServletResponse response,HttpSession session) throws Exception {
		String ownerUserName = (String)session.getAttribute("user_name");
		List<Minsu> minsuList = minsuService.queryAllMinsu(ownerUserName);
        response.setContentType("text/json;charset=UTF-8"); 
		PrintWriter out = response.getWriter();
		JSONArray jsonArray = new JSONArray();
		for(Minsu minsu:minsuList) {
			JSONObject jsonMinsu = new JSONObject();
			jsonMinsu.accumulate("minsuId", minsu.getMinsuId());
			jsonMinsu.accumulate("minsuName", minsu.getMinsuName());
			jsonArray.put(jsonMinsu);
		}
		out.println(jsonArray.toString());
		out.flush();
		out.close();
	}
	

	/*前台按照查询条件分页查询民宿信息*/
	@RequestMapping(value = { "/frontlist" }, method = {RequestMethod.GET,RequestMethod.POST})
	public String frontlist(@ModelAttribute("ownerObj") Owner ownerObj,String addTime,@ModelAttribute("areaObj") Area areaObj,String minsuName,Integer currentPage, Model model, HttpServletRequest request) throws Exception  {
		if (currentPage==null || currentPage == 0) currentPage = 1;
		if (addTime == null) addTime = "";
		if (minsuName == null) minsuName = "";
		List<Minsu> minsuList = minsuService.queryMinsu(ownerObj, addTime, areaObj, minsuName, currentPage);
	    /*计算总的页数和总的记录数*/
	    minsuService.queryTotalPageAndRecordNumber(ownerObj, addTime, areaObj, minsuName);
	    /*获取到总的页码数目*/
	    int totalPage = minsuService.getTotalPage();
	    /*当前查询条件下总记录数*/
	    int recordNumber = minsuService.getRecordNumber();
	    request.setAttribute("minsuList",  minsuList);
	    request.setAttribute("totalPage", totalPage);
	    request.setAttribute("recordNumber", recordNumber);
	    request.setAttribute("currentPage", currentPage);
	    request.setAttribute("ownerObj", ownerObj);
	    request.setAttribute("addTime", addTime);
	    request.setAttribute("areaObj", areaObj);
	    request.setAttribute("minsuName", minsuName);
	    List<Area> areaList = areaService.queryAllArea();
	    request.setAttribute("areaList", areaList);
	    List<Owner> ownerList = ownerService.queryAllOwner();
	    request.setAttribute("ownerList", ownerList);
		return "Minsu/minsu_frontquery_result"; 
	}
	
	
	/*前台按照查询条件分页查询民宿信息*/
	@RequestMapping(value = { "/szFrontlist" }, method = {RequestMethod.GET,RequestMethod.POST})
	public String szFrontlist(@ModelAttribute("ownerObj") Owner ownerObj,String addTime,@ModelAttribute("areaObj") Area areaObj,String minsuName,Integer currentPage, Model model, HttpServletRequest request, HttpSession session) throws Exception  {
		if (currentPage==null || currentPage == 0) currentPage = 1;
		if (addTime == null) addTime = "";
		if (minsuName == null) minsuName = "";
		ownerObj = new Owner();
		ownerObj.setOwnerUserName(session.getAttribute("user_name").toString());
		List<Minsu> minsuList = minsuService.queryMinsu(ownerObj, addTime, areaObj, minsuName, currentPage);
	    /*计算总的页数和总的记录数*/
	    minsuService.queryTotalPageAndRecordNumber(ownerObj, addTime, areaObj, minsuName);
	    /*获取到总的页码数目*/
	    int totalPage = minsuService.getTotalPage();
	    /*当前查询条件下总记录数*/
	    int recordNumber = minsuService.getRecordNumber();
	    request.setAttribute("minsuList",  minsuList);
	    request.setAttribute("totalPage", totalPage);
	    request.setAttribute("recordNumber", recordNumber);
	    request.setAttribute("currentPage", currentPage);
	    request.setAttribute("ownerObj", ownerObj);
	    request.setAttribute("addTime", addTime);
	    request.setAttribute("areaObj", areaObj);
	    request.setAttribute("minsuName", minsuName);
	    List<Area> areaList = areaService.queryAllArea();
	    request.setAttribute("areaList", areaList);
	    List<Owner> ownerList = ownerService.queryAllOwner();
	    request.setAttribute("ownerList", ownerList);
		return "Minsu/minsu_szFrontquery_result"; 
	}

     /*前台查询Minsu信息*/
	@RequestMapping(value="/{minsuId}/frontshow",method=RequestMethod.GET)
	public String frontshow(@PathVariable Integer minsuId,Model model,HttpServletRequest request) throws Exception {
		/*根据主键minsuId获取Minsu对象*/
        Minsu minsu = minsuService.getMinsu(minsuId);

        List<Area> areaList = areaService.queryAllArea();
        request.setAttribute("areaList", areaList);
        List<Owner> ownerList = ownerService.queryAllOwner();
        request.setAttribute("ownerList", ownerList);
        request.setAttribute("minsu",  minsu);
        return "Minsu/minsu_frontshow";
	}

	/*ajax方式显示民宿修改jsp视图页*/
	@RequestMapping(value="/{minsuId}/update",method=RequestMethod.GET)
	public void update(@PathVariable Integer minsuId,Model model,HttpServletRequest request,HttpServletResponse response) throws Exception {
        /*根据主键minsuId获取Minsu对象*/
        Minsu minsu = minsuService.getMinsu(minsuId);

        response.setContentType("text/json;charset=UTF-8");
        PrintWriter out = response.getWriter();
		//将要被返回到客户端的对象 
		JSONObject jsonMinsu = minsu.getJsonObject();
		out.println(jsonMinsu.toString());
		out.flush();
		out.close();
	}

	/*ajax方式更新民宿信息*/
	@RequestMapping(value = "/{minsuId}/update", method = RequestMethod.POST)
	public void update(@Validated Minsu minsu, BindingResult br,
			Model model, HttpServletRequest request,HttpServletResponse response) throws Exception {
		String message = "";
    	boolean success = false;
		if (br.hasErrors()) { 
			message = "输入的信息有错误！";
			writeJsonResponse(response, success, message);
			return;
		}
		String minsuPhotoFileName = this.handlePhotoUpload(request, "minsuPhotoFile");
		if(!minsuPhotoFileName.equals("upload/NoImage.jpg"))minsu.setMinsuPhoto(minsuPhotoFileName); 


		try {
			minsuService.updateMinsu(minsu);
			message = "民宿更新成功!";
			success = true;
			writeJsonResponse(response, success, message);
		} catch (Exception e) {
			e.printStackTrace();
			message = "民宿更新失败!";
			writeJsonResponse(response, success, message); 
		}
	}
    /*删除民宿信息*/
	@RequestMapping(value="/{minsuId}/delete",method=RequestMethod.GET)
	public String delete(@PathVariable Integer minsuId,HttpServletRequest request) throws UnsupportedEncodingException {
		  try {
			  minsuService.deleteMinsu(minsuId);
	            request.setAttribute("message", "民宿删除成功!");
	            return "message";
	        } catch (Exception e) { 
	            e.printStackTrace();
	            request.setAttribute("error", "民宿删除失败!");
				return "error";

	        }

	}

	/*ajax方式删除多条民宿记录*/
	@RequestMapping(value="/deletes",method=RequestMethod.POST)
	public void delete(String minsuIds,HttpServletRequest request,HttpServletResponse response) throws IOException, JSONException {
		String message = "";
    	boolean success = false;
        try { 
        	int count = minsuService.deleteMinsus(minsuIds);
        	success = true;
        	message = count + "条记录删除成功";
        	writeJsonResponse(response, success, message);
        } catch (Exception e) { 
            //e.printStackTrace();
            message = "有记录存在外键约束,删除失败";
            writeJsonResponse(response, success, message);
        }
	}

	/*按照查询条件导出民宿信息到Excel*/
	@RequestMapping(value = { "/OutToExcel" }, method = {RequestMethod.GET,RequestMethod.POST})
	public void OutToExcel(@ModelAttribute("ownerObj") Owner ownerObj,String addTime,@ModelAttribute("areaObj") Area areaObj,String minsuName, Model model, HttpServletRequest request,HttpServletResponse response) throws Exception {
        if(addTime == null) addTime = "";
        if(minsuName == null) minsuName = "";
        List<Minsu> minsuList = minsuService.queryMinsu(ownerObj,addTime,areaObj,minsuName);
        ExportExcelUtil ex = new ExportExcelUtil();
        String _title = "Minsu信息记录"; 
        String[] headers = { "民宿id","所在地区","民宿名称","民宿照片","每日价格","民宿主人","发布时间"};
        List<String[]> dataset = new ArrayList<String[]>(); 
        for(int i=0;i<minsuList.size();i++) {
        	Minsu minsu = minsuList.get(i); 
        	dataset.add(new String[]{minsu.getMinsuId() + "",minsu.getAreaObj().getAreanName(),minsu.getMinsuName(),minsu.getMinsuPhoto(),minsu.getPrice() + "",minsu.getOwnerObj().getName(),minsu.getAddTime()});
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
			response.setHeader("Content-disposition","attachment; filename="+"Minsu.xls");//filename是下载的xls的名，建议最好用英文 
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
