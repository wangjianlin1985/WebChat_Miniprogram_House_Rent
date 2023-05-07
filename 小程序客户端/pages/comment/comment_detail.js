var utils = require("../../utils/common.js")
var config = require("../../utils/config.js")

Page({
  /**
   * 页面的初始数据
   */
  data: {
    commentId: 0, //评论id
    minsuObj: "", //被评民宿
    content: "", //评论内容
    userObj: "", //评论用户
    commentTime: "", //评论时间
    loadingHide: true,
    loadingText: "网络操作中。。",
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
        minsuObj: commentRes.data.minsuObj.minsuName,
        content: commentRes.data.content,
        userObj: commentRes.data.userObj.name,
        commentTime: commentRes.data.commentTime,
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

