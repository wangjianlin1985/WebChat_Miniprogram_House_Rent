package com.chengxusheji.service;

import java.util.ArrayList;
import javax.annotation.Resource; 
import org.springframework.stereotype.Service;
import com.chengxusheji.po.Area;
import com.chengxusheji.po.Owner;
import com.chengxusheji.po.Minsu;

import com.chengxusheji.mapper.MinsuMapper;
@Service
public class MinsuService {

	@Resource MinsuMapper minsuMapper;
    /*每页显示记录数目*/
    private int rows = 10;;
    public int getRows() {
		return rows;
	}
	public void setRows(int rows) {
		this.rows = rows;
	}

    /*保存查询后总的页数*/
    private int totalPage;
    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }
    public int getTotalPage() {
        return totalPage;
    }

    /*保存查询到的总记录数*/
    private int recordNumber;
    public void setRecordNumber(int recordNumber) {
        this.recordNumber = recordNumber;
    }
    public int getRecordNumber() {
        return recordNumber;
    }

    /*添加民宿记录*/
    public void addMinsu(Minsu minsu) throws Exception {
    	minsuMapper.addMinsu(minsu);
    }

    /*按照查询条件分页查询民宿记录*/
    public ArrayList<Minsu> queryMinsu(Owner ownerObj,String addTime,Area areaObj,String minsuName,int currentPage) throws Exception { 
     	String where = "where 1=1";
    	if(null != ownerObj &&  ownerObj.getOwnerUserName() != null  && !ownerObj.getOwnerUserName().equals(""))  where += " and t_minsu.ownerObj='" + ownerObj.getOwnerUserName() + "'";
    	if(!addTime.equals("")) where = where + " and t_minsu.addTime like '%" + addTime + "%'";
    	if(null != areaObj && areaObj.getAreaId()!= null && areaObj.getAreaId()!= 0)  where += " and t_minsu.areaObj=" + areaObj.getAreaId();
    	if(!minsuName.equals("")) where = where + " and t_minsu.minsuName like '%" + minsuName + "%'";
    	int startIndex = (currentPage-1) * this.rows;
    	return minsuMapper.queryMinsu(where, startIndex, this.rows);
    }
    
    
    /*查询最新民宿信息*/
    public ArrayList<Minsu> queryLatestMinsu() throws Exception { 
      
    	String where = "where 1=1";
    	return minsuMapper.queryLatestMinsu(where);
    }

    
    

    /*按照查询条件查询所有记录*/
    public ArrayList<Minsu> queryMinsu(Owner ownerObj,String addTime,Area areaObj,String minsuName) throws Exception  { 
     	String where = "where 1=1";
    	if(null != ownerObj &&  ownerObj.getOwnerUserName() != null && !ownerObj.getOwnerUserName().equals(""))  where += " and t_minsu.ownerObj='" + ownerObj.getOwnerUserName() + "'";
    	if(!addTime.equals("")) where = where + " and t_minsu.addTime like '%" + addTime + "%'";
    	if(null != areaObj && areaObj.getAreaId()!= null && areaObj.getAreaId()!= 0)  where += " and t_minsu.areaObj=" + areaObj.getAreaId();
    	if(!minsuName.equals("")) where = where + " and t_minsu.minsuName like '%" + minsuName + "%'";
    	return minsuMapper.queryMinsuList(where);
    }

    /*查询所有民宿记录*/
    public ArrayList<Minsu> queryAllMinsu()  throws Exception {
        return minsuMapper.queryMinsuList("where 1=1");
    }
    
    /*查询所有民宿记录*/
    public ArrayList<Minsu> queryAllMinsu(String ownerUserName)  throws Exception {
        return minsuMapper.queryMinsuList("where t_minsu.ownerObj='" + ownerUserName + "'");
    }
    

    /*当前查询条件下计算总的页数和记录数*/
    public void queryTotalPageAndRecordNumber(Owner ownerObj,String addTime,Area areaObj,String minsuName) throws Exception {
     	String where = "where 1=1";
    	if(null != ownerObj &&  ownerObj.getOwnerUserName() != null && !ownerObj.getOwnerUserName().equals(""))  where += " and t_minsu.ownerObj='" + ownerObj.getOwnerUserName() + "'";
    	if(!addTime.equals("")) where = where + " and t_minsu.addTime like '%" + addTime + "%'";
    	if(null != areaObj && areaObj.getAreaId()!= null && areaObj.getAreaId()!= 0)  where += " and t_minsu.areaObj=" + areaObj.getAreaId();
    	if(!minsuName.equals("")) where = where + " and t_minsu.minsuName like '%" + minsuName + "%'";
        recordNumber = minsuMapper.queryMinsuCount(where);
        int mod = recordNumber % this.rows;
        totalPage = recordNumber / this.rows;
        if(mod != 0) totalPage++;
    }

    /*根据主键获取民宿记录*/
    public Minsu getMinsu(int minsuId) throws Exception  {
        Minsu minsu = minsuMapper.getMinsu(minsuId);
        return minsu;
    }

    /*更新民宿记录*/
    public void updateMinsu(Minsu minsu) throws Exception {
        minsuMapper.updateMinsu(minsu);
    }

    /*删除一条民宿记录*/
    public void deleteMinsu (int minsuId) throws Exception {
        minsuMapper.deleteMinsu(minsuId);
    }

    /*删除多条民宿信息*/
    public int deleteMinsus (String minsuIds) throws Exception {
    	String _minsuIds[] = minsuIds.split(",");
    	for(String _minsuId: _minsuIds) {
    		minsuMapper.deleteMinsu(Integer.parseInt(_minsuId));
    	}
    	return _minsuIds.length;
    }
}
