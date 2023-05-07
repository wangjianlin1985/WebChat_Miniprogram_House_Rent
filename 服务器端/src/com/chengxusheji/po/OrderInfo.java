package com.chengxusheji.po;

import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.NotEmpty;
import org.json.JSONException;
import org.json.JSONObject;
import com.client.utils.JsonUtils;
import com.client.utils.SessionConsts;
import com.fasterxml.jackson.annotation.JsonIgnore;

public class OrderInfo {
    /*订单id*/
    private Integer orderId;
    public Integer getOrderId(){
        return orderId;
    }
    public void setOrderId(Integer orderId){
        this.orderId = orderId;
    }

    /*预订民宿*/
    private Minsu minsuObj;
    public Minsu getMinsuObj() {
        return minsuObj;
    }
    public void setMinsuObj(Minsu minsuObj) {
        this.minsuObj = minsuObj;
    }

    /*预订用户*/
    private UserInfo userObj;
    public UserInfo getUserObj() {
        return userObj;
    }
    public void setUserObj(UserInfo userObj) {
        this.userObj = userObj;
    }

    /*入住日期*/
    @NotEmpty(message="入住日期不能为空")
    private String liveDate;
    public String getLiveDate() {
        return liveDate;
    }
    public void setLiveDate(String liveDate) {
        this.liveDate = liveDate;
    }

    /*入住天数*/
    @NotNull(message="必须输入入住天数")
    private Integer orderDays;
    public Integer getOrderDays() {
        return orderDays;
    }
    public void setOrderDays(Integer orderDays) {
        this.orderDays = orderDays;
    }

    /*订单总价*/
    @NotNull(message="必须输入订单总价")
    private Float totalPrice;
    public Float getTotalPrice() {
        return totalPrice;
    }
    public void setTotalPrice(Float totalPrice) {
        this.totalPrice = totalPrice;
    }

    /*订单备注*/
    private String orderMemo;
    public String getOrderMemo() {
        return orderMemo;
    }
    public void setOrderMemo(String orderMemo) {
        this.orderMemo = orderMemo;
    }

    /*订单状态*/
    @NotEmpty(message="订单状态不能为空")
    private String orderState;
    public String getOrderState() {
        return orderState;
    }
    public void setOrderState(String orderState) {
        this.orderState = orderState;
    }

    /*预订时间*/
    @NotEmpty(message="预订时间不能为空")
    private String orderTime;
    public String getOrderTime() {
        return orderTime;
    }
    public void setOrderTime(String orderTime) {
        this.orderTime = orderTime;
    }

    @JsonIgnore
    public JSONObject getJsonObject() throws JSONException {
    	JSONObject jsonOrderInfo=new JSONObject(); 
		jsonOrderInfo.accumulate("orderId", this.getOrderId());
		jsonOrderInfo.accumulate("minsuObj", this.getMinsuObj().getMinsuName());
		jsonOrderInfo.accumulate("minsuObjPri", this.getMinsuObj().getMinsuId());
		jsonOrderInfo.accumulate("userObj", this.getUserObj().getName());
		jsonOrderInfo.accumulate("userObjPri", this.getUserObj().getUser_name());
		jsonOrderInfo.accumulate("liveDate", this.getLiveDate().length()>19?this.getLiveDate().substring(0,19):this.getLiveDate());
		jsonOrderInfo.accumulate("orderDays", this.getOrderDays());
		jsonOrderInfo.accumulate("totalPrice", this.getTotalPrice());
		jsonOrderInfo.accumulate("orderMemo", this.getOrderMemo());
		jsonOrderInfo.accumulate("orderState", this.getOrderState());
		jsonOrderInfo.accumulate("orderTime", this.getOrderTime().length()>19?this.getOrderTime().substring(0,19):this.getOrderTime());
		return jsonOrderInfo;
    }

    @Override
	public String toString() {
		return JsonUtils.toJson(this);
	}
}