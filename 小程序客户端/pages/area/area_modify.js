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

  //提交服务器修改地区信息
  formSubmit: function (e) {
    var self = this
    var formData = e.detail.value
    var url = config.basePath + "api/area/update"
    utils.sendRequest(url, formData, function (res) {
      wx.stopPullDownRefresh();
      wx.showToast({
        title: '修改成功',
        icon: 'success',
        duration: 500
      })

      //服务器修改成功返回列表页更新显示为最新的数据
      var pages = getCurrentPages()
      var arealist_page = pages[pages.length - 2];
      var areas = arealist_page.data.areas
      for(var i=0;i<areas.length;i++) {
        if(areas[i].areaId == res.data.areaId) {
          areas[i] = res.data
          break
        }
      }
      arealist_page.setData({areas:areas})
      setTimeout(function () {
        wx.navigateBack({})
      }, 500) 
    })
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
   * 页面相关事件处理函数--监听用户下拉动作
   */
  onPullDownRefresh: function () {
  },

  /**
   * 页面上拉触底事件的处理函数
   */
  onReachBottom: function () {
  },

})

