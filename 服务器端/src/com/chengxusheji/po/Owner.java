package com.chengxusheji.po;

import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.NotEmpty;
import org.json.JSONException;
import org.json.JSONObject;
import com.client.utils.JsonUtils;
import com.client.utils.SessionConsts;
import com.fasterxml.jackson.annotation.JsonIgnore;

public class Owner {
    /*宿主用户名*/
    @NotEmpty(message="宿主用户名不能为空")
    private String ownerUserName;
    public String getOwnerUserName(){
        return ownerUserName;
    }
    public void setOwnerUserName(String ownerUserName){
        this.ownerUserName = ownerUserName;
    }

    /*登录密码*/
    @NotEmpty(message="登录密码不能为空")
    private String password;
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    /*宿主姓名*/
    @NotEmpty(message="宿主姓名不能为空")
    private String name;
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    /*性别*/
    @NotEmpty(message="性别不能为空")
    private String gender;
    public String getGender() {
        return gender;
    }
    public void setGender(String gender) {
        this.gender = gender;
    }

    /*出生日期*/
    @NotEmpty(message="出生日期不能为空")
    private String birthDate;
    public String getBirthDate() {
        return birthDate;
    }
    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    /*宿主照片*/
    private String ownerPhoto;
    public String getOwnerPhoto() {
        return ownerPhoto;
    }
    public void setOwnerPhoto(String ownerPhoto) {
        this.ownerPhoto = ownerPhoto;
    }

    private String ownerPhotoUrl;
    public void setOwnerPhotoUrl(String ownerPhotoUrl) {
		this.ownerPhotoUrl = ownerPhotoUrl;
	}
	public String getOwnerPhotoUrl() {
		return  SessionConsts.BASE_URL + ownerPhoto;
	}
    /*联系电话*/
    @NotEmpty(message="联系电话不能为空")
    private String telephone;
    public String getTelephone() {
        return telephone;
    }
    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    /*家庭地址*/
    private String address;
    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }

    /*注册时间*/
    private String regTime;
    public String getRegTime() {
        return regTime;
    }
    public void setRegTime(String regTime) {
        this.regTime = regTime;
    }

    /*审核状态*/
    @NotEmpty(message="审核状态不能为空")
    private String shzt;
    public String getShzt() {
        return shzt;
    }
    public void setShzt(String shzt) {
        this.shzt = shzt;
    }

    @JsonIgnore
    public JSONObject getJsonObject() throws JSONException {
    	JSONObject jsonOwner=new JSONObject(); 
		jsonOwner.accumulate("ownerUserName", this.getOwnerUserName());
		jsonOwner.accumulate("password", this.getPassword());
		jsonOwner.accumulate("name", this.getName());
		jsonOwner.accumulate("gender", this.getGender());
		jsonOwner.accumulate("birthDate", this.getBirthDate().length()>19?this.getBirthDate().substring(0,19):this.getBirthDate());
		jsonOwner.accumulate("ownerPhoto", this.getOwnerPhoto());
		jsonOwner.accumulate("telephone", this.getTelephone());
		jsonOwner.accumulate("address", this.getAddress());
		jsonOwner.accumulate("regTime", this.getRegTime().length()>19?this.getRegTime().substring(0,19):this.getRegTime());
		jsonOwner.accumulate("shzt", this.getShzt());
		return jsonOwner;
    }

    @Override
	public String toString() {
		return JsonUtils.toJson(this);
	}
}