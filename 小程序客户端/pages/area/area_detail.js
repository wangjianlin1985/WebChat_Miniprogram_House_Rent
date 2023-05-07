var utils = require("../../utils/common.js")
var config = require("../../utils/config.js")

Page({
  /**
   * 页面的初始数据
   */
  data: {
    areaId: 0, //地区id
    areanName: "", //地区名称
    loadingHide: true,
    loadingText: "网络操作中。。",
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (params) {
    var self = this
    var areaId = params.areaId
    var url = config.basePath + "api/area/get/" + areaId
    utils.sendRequest(url, {}, function (areaRes) {
      wx.stopPullDownRefresh()
      self.setData({
        areaId: areaRes.data.areaId,
        areanName: areaRes.data.areanName,
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

