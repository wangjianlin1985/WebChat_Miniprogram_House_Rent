package com.client.controller;

import java.text.SimpleDateFormat;
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
import com.chengxusheji.po.OrderInfo;
import com.chengxusheji.po.Minsu;
import com.chengxusheji.po.UserInfo;
import com.chengxusheji.service.MinsuService;
import com.chengxusheji.service.OrderInfoService;
import com.client.service.AuthService;
import com.client.utils.JsonResult;
import com.client.utils.JsonResultBuilder;
import com.client.utils.ReturnCode;

@RestController
@RequestMapping("/api/orderInfo") 
public class ApiOrderInfoController {
	@Resource OrderInfoService orderInfoService;
	@Resource MinsuService minsuService;
	@Resource AuthService authService;

	@InitBinder("minsuObj")
	public void initBinderminsuObj(WebDataBinder binder) {
		binder.setFieldDefaultPrefix("minsuObj.");
	}
	@InitBinder("userObj")
	public void initBinderuserObj(WebDataBinder binder) {
		binder.setFieldDefaultPrefix("userObj.");
	}
	@InitBinder("orderInfo")
	public void initBinderOrderInfo(WebDataBinder binder) {
		binder.setFieldDefaultPrefix("orderInfo.");
	}

	/*客户端ajax方式添加订单信息*/
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public JsonResult add(@Validated OrderInfo orderInfo, BindingResult br, Model model, HttpServletRequest request,HttpServletResponse response) throws Exception {
		//通过accessToken获取到用户信息 
		String userName = authService.getUserName(request);
		if(userName == null) return JsonResultBuilder.error(ReturnCode.TOKEN_VALID_ERROR);
		if (br.hasErrors()) //验证输入参数
			return JsonResultBuilder.error(ReturnCode.INPUT_PARAM_ERROR); 
        orderInfoService.addOrderInfo(orderInfo); //添加到数据库
        return JsonResultBuilder.ok();
	}
	
	
	/*客户端ajax方式添加订单信息*/
	@RequestMapping(value = "/userAdd", method = RequestMethod.POST)
	public JsonResult userAdd(OrderInfo orderInfo, BindingResult br, Model model, HttpServletRequest request,HttpServletResponse response) throws Exception {
		//通过accessToken获取到用户信息 
		String userName = authService.getUserName(request);
		if(userName == null) return JsonResultBuilder.error(ReturnCode.TOKEN_VALID_ERROR);
		 
		UserInfo userObj = new UserInfo();
		userObj.setUser_name(userName);
		orderInfo.setUserObj(userObj);
		
		int minsuId = orderInfo.getMinsuObj().getMinsuId();
		float price = minsuService.getMinsu(minsuId).getPrice();
		orderInfo.setTotalPrice(price * orderInfo.getOrderDays());
		
		orderInfo.setOrderState("待处理");
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		orderInfo.setOrderTime(sdf.format(new java.util.Date()));
		
		orderInfoService.addOrderInfo(orderInfo); //添加到数据库
        return JsonResultBuilder.ok();
	}

	
	

	/*客户端ajax更新订单信息*/
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public JsonResult update(@Validated OrderInfo orderInfo, BindingResult br, Model model, HttpServletRequest request,HttpServletResponse response) throws Exception {
		//通过accessToken获取到用户信息 
		String userName = authService.getUserName(request);
		if(userName == null) return JsonResultBuilder.error(ReturnCode.TOKEN_VALID_ERROR);
		if (br.hasErrors())  //验证输入参数
			return JsonResultBuilder.error(ReturnCode.INPUT_PARAM_ERROR); 
        orderInfoService.updateOrderInfo(orderInfo);  //更新记录到数据库
        return JsonResultBuilder.ok(orderInfoService.getOrderInfo(orderInfo.getOrderId()));
	}

	/*ajax方式显示获取订单详细信息*/
	@RequestMapping(value="/get/{orderId}",method=RequestMethod.POST)
	public JsonResult getOrderInfo(@PathVariable int orderId,Model model,HttpServletRequest request,HttpServletResponse response) throws Exception {
        /*根据主键orderId获取OrderInfo对象*/
        OrderInfo orderInfo = orderInfoService.getOrderInfo(orderId); 
        return JsonResultBuilder.ok(orderInfo);
	}

	/*ajax方式删除订单记录*/
	@RequestMapping(value="/delete/{orderId}",method=RequestMethod.POST)
	public JsonResult deleteOrderInfo(@PathVariable int orderId,Model model,HttpServletRequest request,HttpServletResponse response) throws Exception {
		//通过accessToken获取到用户信息 
		String userName = authService.getUserName(request);
		if(userName == null) return JsonResultBuilder.error(ReturnCode.TOKEN_VALID_ERROR);
		try {
			orderInfoService.deleteOrderInfo(orderId);
			return JsonResultBuilder.ok();
		} catch (Exception ex) {
			return JsonResultBuilder.error(ReturnCode.FOREIGN_KEY_CONSTRAINT_ERROR);
		}
	}

	//客户端查询订单信息
	@RequestMapping(value="/list",method=RequestMethod.POST)
	public JsonResult list(@ModelAttribute("minsuObj") Minsu minsuObj,@ModelAttribute("userObj") UserInfo userObj,String liveDate,String orderState,String orderTime,Integer page,Integer rows, Model model, HttpServletRequest request,HttpServletResponse response) throws Exception {
		//通过accessToken获取到用户信息 
		String userName = authService.getUserName(request);
		if(userName == null) return JsonResultBuilder.error(ReturnCode.TOKEN_VALID_ERROR);
		
		if (page==null || page == 0) page = 1;
		if (liveDate == null) liveDate = "";
		if (orderState == null) orderState = "";
		if (orderTime == null) orderTime = "";
		if(rows != 0)orderInfoService.setRows(rows);
		userObj = new UserInfo();
		userObj.setUser_name(userName);
		List<OrderInfo> orderInfoList = orderInfoService.queryOrderInfo(minsuObj, userObj, liveDate, orderState, orderTime, page);
	    /*计算总的页数和总的记录数*/
	    orderInfoService.queryTotalPageAndRecordNumber(minsuObj, userObj, liveDate, orderState, orderTime);
	    /*获取到总的页码数目*/
	    int totalPage = orderInfoService.getTotalPage();
	    /*当前查询条件下总记录数*/
	    int recordNumber = orderInfoService.getRecordNumber();
	    HashMap<String, Object> resultMap = new HashMap<String, Object>();
	    resultMap.put("totalPage", totalPage);
	    resultMap.put("list", orderInfoList);
	    return JsonResultBuilder.ok(resultMap);
	}

	//客户端ajax获取所有订单
	@RequestMapping(value="/listAll",method=RequestMethod.POST)
	public JsonResult listAll() throws Exception{
		List<OrderInfo> orderInfoList = orderInfoService.queryAllOrderInfo(); 
		return JsonResultBuilder.ok(orderInfoList);
	}
}

