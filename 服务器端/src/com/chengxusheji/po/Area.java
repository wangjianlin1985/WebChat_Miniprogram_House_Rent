package com.chengxusheji.po;

import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.NotEmpty;
import org.json.JSONException;
import org.json.JSONObject;
import com.client.utils.JsonUtils;
import com.client.utils.SessionConsts;
import com.fasterxml.jackson.annotation.JsonIgnore;

public class Area {
    /*地区id*/
    private Integer areaId;
    public Integer getAreaId(){
        return areaId;
    }
    public void setAreaId(Integer areaId){
        this.areaId = areaId;
    }

    /*地区名称*/
    @NotEmpty(message="地区名称不能为空")
    private String areanName;
    public String getAreanName() {
        return areanName;
    }
    public void setAreanName(String areanName) {
        this.areanName = areanName;
    }

    @JsonIgnore
    public JSONObject getJsonObject() throws JSONException {
    	JSONObject jsonArea=new JSONObject(); 
		jsonArea.accumulate("areaId", this.getAreaId());
		jsonArea.accumulate("areanName", this.getAreanName());
		return jsonArea;
    }

    @Override
	public String toString() {
		return JsonUtils.toJson(this);
	}
}