var utils = require("../../utils/common.js")
var config = require("../../utils/config.js")

Page({
  /**
   * 页面的初始数据
   */
  data: {
    ownerUserName: "", //宿主用户名
    password: "", //登录密码
    name: "", //宿主姓名
    gender: "", //性别
    birthDate: "", //出生日期
    ownerPhotoUrl: "", //宿主照片
    telephone: "", //联系电话
    address: "", //家庭地址
    regTime: "", //注册时间
    shzt: "", //审核状态
    loadingHide: true,
    loadingText: "网络操作中。。",
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (params) {
    var self = this
    var ownerUserName = params.ownerUserName
    var url = config.basePath + "api/owner/get/" + ownerUserName
    utils.sendRequest(url, {}, function (ownerRes) {
      wx.stopPullDownRefresh()
      self.setData({
        ownerUserName: ownerRes.data.ownerUserName,
        password: ownerRes.data.password,
        name: ownerRes.data.name,
        gender: ownerRes.data.gender,
        birthDate: ownerRes.data.birthDate,
        ownerPhoto: ownerRes.data.ownerPhoto,
        ownerPhotoUrl: ownerRes.data.ownerPhotoUrl,
        telephone: ownerRes.data.telephone,
        address: ownerRes.data.address,
        regTime: ownerRes.data.regTime,
        shzt: ownerRes.data.shzt,
      })
    })
  },

  /**
   * 生命周期函数--监听页面显示
   */
  onShow: function () {
  },

  /**
   * 页面上拉触底事件的处理函数
   */
  onReachBottom: function () {
  }

})

