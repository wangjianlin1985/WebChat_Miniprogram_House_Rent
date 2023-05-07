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
import com.chengxusheji.po.Owner;
import com.chengxusheji.service.OwnerService;
import com.client.service.AuthService;
import com.client.utils.JsonResult;
import com.client.utils.JsonResultBuilder;
import com.client.utils.ReturnCode;

@RestController
@RequestMapping("/api/owner") 
public class ApiOwnerController {
	@Resource OwnerService ownerService;
	@Resource AuthService authService;

	@InitBinder("owner")
	public void initBinderOwner(WebDataBinder binder) {
		binder.setFieldDefaultPrefix("owner.");
	}

	/*客户端ajax方式添加民宿主人信息*/
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public JsonResult add(@Validated Owner owner, BindingResult br, Model model, HttpServletRequest request,HttpServletResponse response) throws Exception {
		//通过accessToken获取到用户信息 
		String userName = authService.getUserName(request);
		if(userName == null) return JsonResultBuilder.error(ReturnCode.TOKEN_VALID_ERROR);
		if (br.hasErrors()) //验证输入参数
			return JsonResultBuilder.error(ReturnCode.INPUT_PARAM_ERROR);
		if(ownerService.getOwner(owner.getOwnerUserName()) != null) //验证主键是否重复
			return JsonResultBuilder.error(ReturnCode.PRIMARY_EXIST_ERROR);
        ownerService.addOwner(owner); //添加到数据库
        return JsonResultBuilder.ok();
	}

	/*客户端ajax更新民宿主人信息*/
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public JsonResult update(@Validated Owner owner, BindingResult br, Model model, HttpServletRequest request,HttpServletResponse response) throws Exception {
		//通过accessToken获取到用户信息 
		String userName = authService.getUserName(request);
		if(userName == null) return JsonResultBuilder.error(ReturnCode.TOKEN_VALID_ERROR);
		if (br.hasErrors())  //验证输入参数
			return JsonResultBuilder.error(ReturnCode.INPUT_PARAM_ERROR); 
        ownerService.updateOwner(owner);  //更新记录到数据库
        return JsonResultBuilder.ok(ownerService.getOwner(owner.getOwnerUserName()));
	}

	/*ajax方式显示获取民宿主人详细信息*/
	@RequestMapping(value="/get/{ownerUserName}",method=RequestMethod.POST)
	public JsonResult getOwner(@PathVariable String ownerUserName,Model model,HttpServletRequest request,HttpServletResponse response) throws Exception {
        /*根据主键ownerUserName获取Owner对象*/
        Owner owner = ownerService.getOwner(ownerUserName); 
        return JsonResultBuilder.ok(owner);
	}

	/*ajax方式删除民宿主人记录*/
	@RequestMapping(value="/delete/{ownerUserName}",method=RequestMethod.POST)
	public JsonResult deleteOwner(@PathVariable String ownerUserName,Model model,HttpServletRequest request,HttpServletResponse response) throws Exception {
		//通过accessToken获取到用户信息 
		String userName = authService.getUserName(request);
		if(userName == null) return JsonResultBuilder.error(ReturnCode.TOKEN_VALID_ERROR);
		try {
			ownerService.deleteOwner(ownerUserName);
			return JsonResultBuilder.ok();
		} catch (Exception ex) {
			return JsonResultBuilder.error(ReturnCode.FOREIGN_KEY_CONSTRAINT_ERROR);
		}
	}

	//客户端查询民宿主人信息
	@RequestMapping(value="/list",method=RequestMethod.POST)
	public JsonResult list(String ownerUserName,String name,String birthDate,String telephone,String shzt,Integer page,Integer rows, Model model, HttpServletRequest request,HttpServletResponse response) throws Exception {
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
	    HashMap<String, Object> resultMap = new HashMap<String, Object>();
	    resultMap.put("totalPage", totalPage);
	    resultMap.put("list", ownerList);
	    return JsonResultBuilder.ok(resultMap);
	}

	//客户端ajax获取所有民宿主人
	@RequestMapping(value="/listAll",method=RequestMethod.POST)
	public JsonResult listAll() throws Exception{
		List<Owner> ownerList = ownerService.queryAllOwner(); 
		return JsonResultBuilder.ok(ownerList);
	}
}

