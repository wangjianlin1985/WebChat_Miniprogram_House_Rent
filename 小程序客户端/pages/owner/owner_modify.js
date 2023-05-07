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
    ownerPhoto: "upload/NoImage.jpg", //宿主照片
    ownerPhotoUrl: "",
    ownerPhotoList: [],
    telephone: "", //联系电话
    address: "", //家庭地址
    regTime: "", //注册时间
    shzt: "", //审核状态
    loadingHide: true,
    loadingText: "网络操作中。。",
  },

  //选择出生日期事件
  bind_birthDate_change: function (e) {
    this.setData({
      birthDate: e.detail.value
    })
  },
  //清空出生日期事件
  clear_birthDate: function (e) {
    this.setData({
      birthDate: "",
    });
  },

  //选择注册时间事件
  bind_regTime_change: function (e) {
    this.setData({
      regTime: e.detail.value
    })
  },
  //清空注册时间事件
  clear_regTime: function (e) {
    this.setData({
      regTime: "",
    });
  },

  //选择宿主照片上传
  select_ownerPhoto: function (e) {
    var self = this
    wx.chooseImage({
      count: 1,   //可以上传一张图片
      sizeType: ['original', 'compressed'],
      sourceType: ['album', 'camera'],
      success: function (res) {
        var tempFilePaths = res.tempFilePaths
        self.setData({
          ownerPhotoList: tempFilePaths,
          loadingHide: false, 
        });

        utils.sendUploadImage(config.basePath + "upload/image", tempFilePaths[0], function (res) {
          wx.stopPullDownRefresh()
          setTimeout(function () {
            self.setData({
              ownerPhoto: res.data,
              loadingHide: true
            })
          }, 200)
        })
      }
    })
  },

  //提交服务器修改民宿主人信息
  formSubmit: function (e) {
    var self = this
    var formData = e.detail.value
    var url = config.basePath + "api/owner/update"
    utils.sendRequest(url, formData, function (res) {
      wx.stopPullDownRefresh();
      wx.showToast({
        title: '修改成功',
        icon: 'success',
        duration: 500
      })

      //服务器修改成功返回列表页更新显示为最新的数据
      var pages = getCurrentPages()
      var ownerlist_page = pages[pages.length - 2];
      var owners = ownerlist_page.data.owners
      for(var i=0;i<owners.length;i++) {
        if(owners[i].ownerUserName == res.data.ownerUserName) {
          owners[i] = res.data
          break
        }
      }
      ownerlist_page.setData({owners:owners})
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

