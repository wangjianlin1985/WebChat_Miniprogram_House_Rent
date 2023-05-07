package com.client.controller;

import java.util.HashMap;
import java.util.List;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.ui.Model;

import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;

import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;

import org.springframework.web.bind.annotation.ModelAttribute;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.chengxusheji.po.Minsu;
import com.chengxusheji.po.Area;
import com.chengxusheji.po.Owner;
import com.chengxusheji.service.MinsuService;
import com.client.service.AuthService;
import com.client.utils.JsonResult;
import com.client.utils.JsonResultBuilder;
import com.client.utils.ReturnCode;

@RestController
@RequestMapping("/api/minsu") 
public class ApiMinsuController {
	@Resource MinsuService minsuService;
	@Resource AuthService authService;

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

	/*客户端ajax方式添加民宿信息*/
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public JsonResult add(@Validated Minsu minsu, BindingResult br, Model model, HttpServletRequest request,HttpServletResponse response) throws Exception {
		//通过accessToken获取到用户信息 
		String userName = authService.getUserName(request);
		if(userName == null) return JsonResultBuilder.error(ReturnCode.TOKEN_VALID_ERROR);
		if (br.hasErrors()) //验证输入参数
			return JsonResultBuilder.error(ReturnCode.INPUT_PARAM_ERROR);
        minsuService.addMinsu(minsu); //添加到数据库
        return JsonResultBuilder.ok();
	}

	/*客户端ajax更新民宿信息*/
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public JsonResult update(@Validated Minsu minsu, BindingResult br, Model model, HttpServletRequest request,HttpServletResponse response) throws Exception {
		//通过accessToken获取到用户信息 
		String userName = authService.getUserName(request);
		if(userName == null) return JsonResultBuilder.error(ReturnCode.TOKEN_VALID_ERROR);
		if (br.hasErrors())  //验证输入参数
			return JsonResultBuilder.error(ReturnCode.INPUT_PARAM_ERROR); 
        minsuService.updateMinsu(minsu);  //更新记录到数据库
        return JsonResultBuilder.ok(minsuService.getMinsu(minsu.getMinsuId()));
	}

	/*ajax方式显示获取民宿详细信息*/
	@RequestMapping(value="/get/{minsuId}",method=RequestMethod.POST)
	public JsonResult getMinsu(@PathVariable int minsuId,Model model,HttpServletRequest request,HttpServletResponse response) throws Exception {
        /*根据主键minsuId获取Minsu对象*/
        Minsu minsu = minsuService.getMinsu(minsuId); 
        return JsonResultBuilder.ok(minsu);
	}

	/*ajax方式删除民宿记录*/
	@RequestMapping(value="/delete/{minsuId}",method=RequestMethod.POST)
	public JsonResult deleteMinsu(@PathVariable int minsuId,Model model,HttpServletRequest request,HttpServletResponse response) throws Exception {
		//通过accessToken获取到用户信息 
		String userName = authService.getUserName(request);
		if(userName == null) return JsonResultBuilder.error(ReturnCode.TOKEN_VALID_ERROR);
		try {
			minsuService.deleteMinsu(minsuId);
			return JsonResultBuilder.ok();
		} catch (Exception ex) {
			return JsonResultBuilder.error(ReturnCode.FOREIGN_KEY_CONSTRAINT_ERROR);
		}
	}

	//客户端查询民宿信息
	@RequestMapping(value="/list",method=RequestMethod.POST)
	public JsonResult list(@ModelAttribute("ownerObj") Owner ownerObj,String addTime,@ModelAttribute("areaObj") Area areaObj,String minsuName,Integer page,Integer rows, Model model, HttpServletRequest request,HttpServletResponse response) throws Exception {
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
	    HashMap<String, Object> resultMap = new HashMap<String, Object>();
	    resultMap.put("totalPage", totalPage);
	    resultMap.put("list", minsuList);
	    return JsonResultBuilder.ok(resultMap);
	}
	
	
	//客户端查询民宿信息
	@RequestMapping(value="/zxList",method=RequestMethod.POST)
	public JsonResult zxList(Model model, HttpServletRequest request,HttpServletResponse response) throws Exception {
		 
		List<Minsu> minsuList = minsuService.queryLatestMinsu();
	   
	    
	    HashMap<String, Object> resultMap = new HashMap<String, Object>();
	    
	    resultMap.put("list", minsuList);
	    return JsonResultBuilder.ok(resultMap);
	}
	
	

	//客户端ajax获取所有民宿
	@RequestMapping(value="/listAll",method=RequestMethod.POST)
	public JsonResult listAll() throws Exception{
		List<Minsu> minsuList = minsuService.queryAllMinsu(); 
		return JsonResultBuilder.ok(minsuList);
	}
}

