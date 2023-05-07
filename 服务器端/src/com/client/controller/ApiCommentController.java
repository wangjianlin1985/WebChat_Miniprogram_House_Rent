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
import com.chengxusheji.po.Comment;
import com.chengxusheji.po.Minsu;
import com.chengxusheji.po.UserInfo;
import com.chengxusheji.service.CommentService;
import com.client.service.AuthService;
import com.client.utils.JsonResult;
import com.client.utils.JsonResultBuilder;
import com.client.utils.ReturnCode;

@RestController
@RequestMapping("/api/comment") 
public class ApiCommentController {
	@Resource CommentService commentService;
	@Resource AuthService authService;

	@InitBinder("minsuObj")
	public void initBinderminsuObj(WebDataBinder binder) {
		binder.setFieldDefaultPrefix("minsuObj.");
	}
	@InitBinder("userObj")
	public void initBinderuserObj(WebDataBinder binder) {
		binder.setFieldDefaultPrefix("userObj.");
	}
	@InitBinder("comment")
	public void initBinderComment(WebDataBinder binder) {
		binder.setFieldDefaultPrefix("comment.");
	}

	/*客户端ajax方式添加评论信息*/
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public JsonResult add(@Validated Comment comment, BindingResult br, Model model, HttpServletRequest request,HttpServletResponse response) throws Exception {
		//通过accessToken获取到用户信息 
		String userName = authService.getUserName(request);
		if(userName == null) return JsonResultBuilder.error(ReturnCode.TOKEN_VALID_ERROR);
		if (br.hasErrors()) //验证输入参数
			return JsonResultBuilder.error(ReturnCode.INPUT_PARAM_ERROR);
        commentService.addComment(comment); //添加到数据库
        return JsonResultBuilder.ok();
	}
	
	
	/*客户端ajax方式添加评论信息*/
	@RequestMapping(value = "/userAdd", method = RequestMethod.POST)
	public JsonResult userAdd(Comment comment, BindingResult br, Model model, HttpServletRequest request,HttpServletResponse response) throws Exception {
		//通过accessToken获取到用户信息 
		String userName = authService.getUserName(request);
		if(userName == null) return JsonResultBuilder.error(ReturnCode.TOKEN_VALID_ERROR);
		UserInfo userObj = new UserInfo();
		userObj.setUser_name(userName);
		comment.setUserObj(userObj);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		comment.setCommentTime(sdf.format(new java.util.Date()));  
        commentService.addComment(comment); //添加到数据库
        return JsonResultBuilder.ok();
	}

	

	/*客户端ajax更新评论信息*/
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public JsonResult update(@Validated Comment comment, BindingResult br, Model model, HttpServletRequest request,HttpServletResponse response) throws Exception {
		//通过accessToken获取到用户信息 
		String userName = authService.getUserName(request);
		if(userName == null) return JsonResultBuilder.error(ReturnCode.TOKEN_VALID_ERROR);
		if (br.hasErrors())  //验证输入参数
			return JsonResultBuilder.error(ReturnCode.INPUT_PARAM_ERROR); 
        commentService.updateComment(comment);  //更新记录到数据库
        return JsonResultBuilder.ok(commentService.getComment(comment.getCommentId()));
	}

	/*ajax方式显示获取评论详细信息*/
	@RequestMapping(value="/get/{commentId}",method=RequestMethod.POST)
	public JsonResult getComment(@PathVariable int commentId,Model model,HttpServletRequest request,HttpServletResponse response) throws Exception {
        /*根据主键commentId获取Comment对象*/
        Comment comment = commentService.getComment(commentId); 
        return JsonResultBuilder.ok(comment);
	}

	/*ajax方式删除评论记录*/
	@RequestMapping(value="/delete/{commentId}",method=RequestMethod.POST)
	public JsonResult deleteComment(@PathVariable int commentId,Model model,HttpServletRequest request,HttpServletResponse response) throws Exception {
		//通过accessToken获取到用户信息 
		String userName = authService.getUserName(request);
		if(userName == null) return JsonResultBuilder.error(ReturnCode.TOKEN_VALID_ERROR);
		try {
			commentService.deleteComment(commentId);
			return JsonResultBuilder.ok();
		} catch (Exception ex) {
			return JsonResultBuilder.error(ReturnCode.FOREIGN_KEY_CONSTRAINT_ERROR);
		}
	}

	//客户端查询评论信息
	@RequestMapping(value="/list",method=RequestMethod.POST)
	public JsonResult list(@ModelAttribute("minsuObj") Minsu minsuObj,@ModelAttribute("userObj") UserInfo userObj,String commentTime,Integer page,Integer rows, Model model, HttpServletRequest request,HttpServletResponse response) throws Exception {
		if (page==null || page == 0) page = 1;
		if (commentTime == null) commentTime = "";
		if(rows != 0)commentService.setRows(rows);
		List<Comment> commentList = commentService.queryComment(minsuObj, userObj, commentTime, page);
	    /*计算总的页数和总的记录数*/
	    commentService.queryTotalPageAndRecordNumber(minsuObj, userObj, commentTime);
	    /*获取到总的页码数目*/
	    int totalPage = commentService.getTotalPage();
	    /*当前查询条件下总记录数*/
	    int recordNumber = commentService.getRecordNumber();
	    HashMap<String, Object> resultMap = new HashMap<String, Object>();
	    resultMap.put("totalPage", totalPage);
	    resultMap.put("list", commentList);
	    return JsonResultBuilder.ok(resultMap);
	}
	
	
	//客户端查询评论信息
	@RequestMapping(value="/userList",method=RequestMethod.POST)
	public JsonResult userList(@ModelAttribute("minsuObj") Minsu minsuObj,@ModelAttribute("userObj") UserInfo userObj,String commentTime,Integer page,Integer rows, Model model, HttpServletRequest request,HttpServletResponse response) throws Exception {
		if (page==null || page == 0) page = 1;
		if (commentTime == null) commentTime = "";
		if(rows != 0)commentService.setRows(rows);
		//通过accessToken获取到用户信息 
		String userName = authService.getUserName(request);
		if(userName == null) return JsonResultBuilder.error(ReturnCode.TOKEN_VALID_ERROR);
		userObj = new UserInfo();
		userObj.setUser_name(userName);
		List<Comment> commentList = commentService.queryComment(minsuObj, userObj, commentTime, page);
	    /*计算总的页数和总的记录数*/
	    commentService.queryTotalPageAndRecordNumber(minsuObj, userObj, commentTime);
	    /*获取到总的页码数目*/
	    int totalPage = commentService.getTotalPage();
	    /*当前查询条件下总记录数*/
	    int recordNumber = commentService.getRecordNumber();
	    HashMap<String, Object> resultMap = new HashMap<String, Object>();
	    resultMap.put("totalPage", totalPage);
	    resultMap.put("list", commentList);
	    return JsonResultBuilder.ok(resultMap);
	}
	

	//客户端ajax获取所有评论
	@RequestMapping(value="/listAll",method=RequestMethod.POST)
	public JsonResult listAll() throws Exception{
		List<Comment> commentList = commentService.queryAllComment(); 
		return JsonResultBuilder.ok(commentList);
	}
}

