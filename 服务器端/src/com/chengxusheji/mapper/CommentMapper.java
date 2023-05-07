package com.chengxusheji.mapper;

import java.util.ArrayList;
import org.apache.ibatis.annotations.Param;
import com.chengxusheji.po.Comment;

public interface CommentMapper {
	/*添加评论信息*/
	public void addComment(Comment comment) throws Exception;

	/*按照查询条件分页查询评论记录*/
	public ArrayList<Comment> queryComment(@Param("where") String where,@Param("startIndex") int startIndex,@Param("pageSize") int pageSize) throws Exception;

	/*按照查询条件查询所有评论记录*/
	public ArrayList<Comment> queryCommentList(@Param("where") String where) throws Exception;

	/*按照查询条件的评论记录数*/
	public int queryCommentCount(@Param("where") String where) throws Exception; 

	/*根据主键查询某条评论记录*/
	public Comment getComment(int commentId) throws Exception;

	/*更新评论记录*/
	public void updateComment(Comment comment) throws Exception;

	/*删除评论记录*/
	public void deleteComment(int commentId) throws Exception;

}
