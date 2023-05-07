var utils = require("../../utils/common.js")
var config = require("../../utils/config.js")

Page({
  /**
   * 页面的初始数据
   */
  data: {
    commentId: 0, //评论id
    minsuObj_Index: "0", //被评民宿
    minsus: [],
    content: "", //评论内容
    userObj_Index: "0", //评论用户
    userInfos: [],
    commentTime: "", //评论时间
    loadingHide: true,
    loadingText: "网络操作中。。",
  },

  //被评民宿修改事件
  bind_minsuObj_change: function (e) {
    this.setData({
      minsuObj_Index: e.detail.value
    })
  },

  //评论用户修改事件
  bind_userObj_change: function (e) {
    this.setData({
      userObj_Index: e.detail.value
    })
  },

  //提交服务器修改评论信息
  formSubmit: function (e) {
    var self = this
    var formData = e.detail.value
    var url = config.basePath + "api/comment/update"
    utils.sendRequest(url, formData, function (res) {
      wx.stopPullDownRefresh();
      wx.showToast({
        title: '修改成功',
        icon: 'success',
        duration: 500
      })

      //服务器修改成功返回列表页更新显示为最新的数据
      var pages = getCurrentPages()
      var commentlist_page = pages[pages.length - 2];
      var comments = commentlist_page.data.comments
      for(var i=0;i<comments.length;i++) {
        if(comments[i].commentId == res.data.commentId) {
          comments[i] = res.data
          break
        }
      }
      commentlist_page.setData({comments:comments})
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
    var commentId = params.commentId
    var url = config.basePath + "api/comment/get/" + commentId
    utils.sendRequest(url, {}, function (commentRes) {
      wx.stopPullDownRefresh()
      self.setData({
        commentId: commentRes.data.commentId,
        minsuObj_Index: 1,
        content: commentRes.data.content,
        userObj_Index: 1,
        commentTime: commentRes.data.commentTime,
      })

      var minsuUrl = config.basePath + "api/minsu/listAll" 
      utils.sendRequest(minsuUrl, null, function (res) {
        wx.stopPullDownRefresh()
        self.setData({
          minsus: res.data,
        })
        for (var i = 0; i < self.data.minsus.length; i++) {
          if (commentRes.data.minsuObj.minsuId == self.data.minsus[i].minsuId) {
            self.setData({
              minsuObj_Index: i,
            });
            break;
          }
        }
      })
      var userInfoUrl = config.basePath + "api/userInfo/listAll" 
      utils.sendRequest(userInfoUrl, null, function (res) {
        wx.stopPullDownRefresh()
        self.setData({
          userInfos: res.data,
        })
        for (var i = 0; i < self.data.userInfos.length; i++) {
          if (commentRes.data.userObj.user_name == self.data.userInfos[i].user_name) {
            self.setData({
              userObj_Index: i,
            });
            break;
          }
        }
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

