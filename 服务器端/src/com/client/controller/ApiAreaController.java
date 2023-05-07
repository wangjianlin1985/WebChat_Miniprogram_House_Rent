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
import com.chengxusheji.po.Area;
import com.chengxusheji.service.AreaService;
import com.client.service.AuthService;
import com.client.utils.JsonResult;
import com.client.utils.JsonResultBuilder;
import com.client.utils.ReturnCode;

@RestController
@RequestMapping("/api/area") 
public class ApiAreaController {
	@Resource AreaService areaService;
	@Resource AuthService authService;

	@InitBinder("area")
	public void initBinderArea(WebDataBinder binder) {
		binder.setFieldDefaultPrefix("area.");
	}

	/*客户端ajax方式添加地区信息*/
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public JsonResult add(@Validated Area area, BindingResult br, Model model, HttpServletRequest request,HttpServletResponse response) throws Exception {
		//通过accessToken获取到用户信息 
		String userName = authService.getUserName(request);
		if(userName == null) return JsonResultBuilder.error(ReturnCode.TOKEN_VALID_ERROR);
		if (br.hasErrors()) //验证输入参数
			return JsonResultBuilder.error(ReturnCode.INPUT_PARAM_ERROR);
        areaService.addArea(area); //添加到数据库
        return JsonResultBuilder.ok();
	}

	/*客户端ajax更新地区信息*/
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public JsonResult update(@Validated Area area, BindingResult br, Model model, HttpServletRequest request,HttpServletResponse response) throws Exception {
		//通过accessToken获取到用户信息 
		String userName = authService.getUserName(request);
		if(userName == null) return JsonResultBuilder.error(ReturnCode.TOKEN_VALID_ERROR);
		if (br.hasErrors())  //验证输入参数
			return JsonResultBuilder.error(ReturnCode.INPUT_PARAM_ERROR); 
        areaService.updateArea(area);  //更新记录到数据库
        return JsonResultBuilder.ok(areaService.getArea(area.getAreaId()));
	}

	/*ajax方式显示获取地区详细信息*/
	@RequestMapping(value="/get/{areaId}",method=RequestMethod.POST)
	public JsonResult getArea(@PathVariable int areaId,Model model,HttpServletRequest request,HttpServletResponse response) throws Exception {
        /*根据主键areaId获取Area对象*/
        Area area = areaService.getArea(areaId); 
        return JsonResultBuilder.ok(area);
	}

	/*ajax方式删除地区记录*/
	@RequestMapping(value="/delete/{areaId}",method=RequestMethod.POST)
	public JsonResult deleteArea(@PathVariable int areaId,Model model,HttpServletRequest request,HttpServletResponse response) throws Exception {
		//通过accessToken获取到用户信息 
		String userName = authService.getUserName(request);
		if(userName == null) return JsonResultBuilder.error(ReturnCode.TOKEN_VALID_ERROR);
		try {
			areaService.deleteArea(areaId);
			return JsonResultBuilder.ok();
		} catch (Exception ex) {
			return JsonResultBuilder.error(ReturnCode.FOREIGN_KEY_CONSTRAINT_ERROR);
		}
	}

	//客户端查询地区信息
	@RequestMapping(value="/list",method=RequestMethod.POST)
	public JsonResult list(Integer page,Integer rows, Model model, HttpServletRequest request,HttpServletResponse response) throws Exception {
		if (page==null || page == 0) page = 1;
		if(rows != 0)areaService.setRows(rows);
		List<Area> areaList = areaService.queryArea(page);
	    /*计算总的页数和总的记录数*/
	    areaService.queryTotalPageAndRecordNumber();
	    /*获取到总的页码数目*/
	    int totalPage = areaService.getTotalPage();
	    /*当前查询条件下总记录数*/
	    int recordNumber = areaService.getRecordNumber();
	    HashMap<String, Object> resultMap = new HashMap<String, Object>();
	    resultMap.put("totalPage", totalPage);
	    resultMap.put("list", areaList);
	    return JsonResultBuilder.ok(resultMap);
	}

	//客户端ajax获取所有地区
	@RequestMapping(value="/listAll",method=RequestMethod.POST)
	public JsonResult listAll() throws Exception{
		List<Area> areaList = areaService.queryAllArea(); 
		return JsonResultBuilder.ok(areaList);
	}
}

