package com.chengxusheji.service;

import java.util.ArrayList;
import javax.annotation.Resource; 
import org.springframework.stereotype.Service;

import com.chengxusheji.po.Admin;
import com.chengxusheji.po.Owner;

import com.chengxusheji.mapper.OwnerMapper;
@Service
public class OwnerService {

	@Resource OwnerMapper ownerMapper;
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

    /*添加民宿主人记录*/
    public void addOwner(Owner owner) throws Exception {
    	ownerMapper.addOwner(owner);
    }

    /*按照查询条件分页查询民宿主人记录*/
    public ArrayList<Owner> queryOwner(String ownerUserName,String name,String birthDate,String telephone,String shzt,int currentPage) throws Exception { 
     	String where = "where 1=1";
    	if(!ownerUserName.equals("")) where = where + " and t_owner.ownerUserName like '%" + ownerUserName + "%'";
    	if(!name.equals("")) where = where + " and t_owner.name like '%" + name + "%'";
    	if(!birthDate.equals("")) where = where + " and t_owner.birthDate like '%" + birthDate + "%'";
    	if(!telephone.equals("")) where = where + " and t_owner.telephone like '%" + telephone + "%'";
    	if(!shzt.equals("")) where = where + " and t_owner.shzt like '%" + shzt + "%'";
    	int startIndex = (currentPage-1) * this.rows;
    	return ownerMapper.queryOwner(where, startIndex, this.rows);
    }

    /*按照查询条件查询所有记录*/
    public ArrayList<Owner> queryOwner(String ownerUserName,String name,String birthDate,String telephone,String shzt) throws Exception  { 
     	String where = "where 1=1";
    	if(!ownerUserName.equals("")) where = where + " and t_owner.ownerUserName like '%" + ownerUserName + "%'";
    	if(!name.equals("")) where = where + " and t_owner.name like '%" + name + "%'";
    	if(!birthDate.equals("")) where = where + " and t_owner.birthDate like '%" + birthDate + "%'";
    	if(!telephone.equals("")) where = where + " and t_owner.telephone like '%" + telephone + "%'";
    	if(!shzt.equals("")) where = where + " and t_owner.shzt like '%" + shzt + "%'";
    	return ownerMapper.queryOwnerList(where);
    }

    /*查询所有民宿主人记录*/
    public ArrayList<Owner> queryAllOwner()  throws Exception {
        return ownerMapper.queryOwnerList("where 1=1");
    }

    /*当前查询条件下计算总的页数和记录数*/
    public void queryTotalPageAndRecordNumber(String ownerUserName,String name,String birthDate,String telephone,String shzt) throws Exception {
     	String where = "where 1=1";
    	if(!ownerUserName.equals("")) where = where + " and t_owner.ownerUserName like '%" + ownerUserName + "%'";
    	if(!name.equals("")) where = where + " and t_owner.name like '%" + name + "%'";
    	if(!birthDate.equals("")) where = where + " and t_owner.birthDate like '%" + birthDate + "%'";
    	if(!telephone.equals("")) where = where + " and t_owner.telephone like '%" + telephone + "%'";
    	if(!shzt.equals("")) where = where + " and t_owner.shzt like '%" + shzt + "%'";
        recordNumber = ownerMapper.queryOwnerCount(where);
        int mod = recordNumber % this.rows;
        totalPage = recordNumber / this.rows;
        if(mod != 0) totalPage++;
    }

    /*根据主键获取民宿主人记录*/
    public Owner getOwner(String ownerUserName) throws Exception  {
        Owner owner = ownerMapper.getOwner(ownerUserName);
        return owner;
    }

    /*更新民宿主人记录*/
    public void updateOwner(Owner owner) throws Exception {
        ownerMapper.updateOwner(owner);
    }

    /*删除一条民宿主人记录*/
    public void deleteOwner (String ownerUserName) throws Exception {
        ownerMapper.deleteOwner(ownerUserName);
    }

    /*删除多条民宿主人信息*/
    public int deleteOwners (String ownerUserNames) throws Exception {
    	String _ownerUserNames[] = ownerUserNames.split(",");
    	for(String _ownerUserName: _ownerUserNames) {
    		ownerMapper.deleteOwner(_ownerUserName);
    	}
    	return _ownerUserNames.length;
    }
	 
	
	/*保存业务逻辑错误信息字段*/
	private String errMessage;
	public String getErrMessage() { return this.errMessage; }
	
	/*验证用户登录*/
	public boolean checkLogin(String userName, String password) throws Exception { 
		Owner db_owner = (Owner) ownerMapper.getOwner(userName);
		if(db_owner == null) { 
			this.errMessage = " 账号不存在 ";
			System.out.print(this.errMessage);
			return false;
		}else if( !db_owner.getPassword().equals(password)) {
			this.errMessage = " 密码不正确! ";
			System.out.print(this.errMessage);
			return false;
		}else if(!db_owner.getShzt().equals("审核通过")) {
			this.errMessage = "账号未审核! ";
			System.out.print(this.errMessage);
			return false;
		}
		
		return true;
	}
}
