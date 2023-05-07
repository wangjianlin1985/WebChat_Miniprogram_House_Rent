var utils = require("../../utils/common.js");
var config = require("../../utils/config.js");

Page({
  /**
   * 页面的初始数据
   */
  data: {
    queryViewHidden: true, //是否隐藏查询条件界面
    minsuObj_Index: "0", //被评民宿查询条件
    minsus: [{ "minsuId": 0, "minsuName": "不限制" }],
    minsuId: 0, //要查询的民宿
    userObj_Index: "0", //评论用户查询条件
    userInfos: [{ "user_name": "", "name": "不限制" }],
    commentTime: "", //评论时间查询关键字
    comments: [], //界面显示的评论列表数据
    page_size: 8, //每页显示几条数据
    page: 1,  //当前要显示第几页
    totalPage: null, //总的页码数
    loading_hide: true, //是否隐藏加载动画
    nodata_hide: true, //是否隐藏没有数据记录提示
  },

  // 加载评论列表
  listComment: function () {
    var self = this
    var url = config.basePath + "api/comment/list"
    //如果要显示的页码超过总页码不操作
    if (self.data.totalPage != null && self.data.page > self.data.totalPage) return
    self.setData({
      loading_hide: false,  //显示加载动画
    })
    //提交查询参数到服务器查询数据列表
    utils.sendRequest(url, {
      "minsuObj.minsuId": self.data.minsuId, //self.data.minsus[self.data.minsuObj_Index].minsuId,
      "userObj.user_name": self.data.userInfos[self.data.userObj_Index].user_name,
      "commentTime": self.data.commentTime,
      "page": self.data.page,
      "rows": self.data.page_size,
    }, function (res) {
      wx.stopPullDownRefresh()
      setTimeout(function () {
        self.setData({
          comments: self.data.comments.concat(res.data.list),
          totalPage: res.data.totalPage,
          loading_hide: true
        })
      }, 500)
      //如果当前显示的是最后一页，则显示没数据提示
      if (self.data.page == self.data.totalPage) {
        self.setData({
          nodata_hide: false,
        })
      }
    })
  },

  //显示或隐藏查询视图切换
  toggleQueryViewHide: function () {
    this.setData({
      queryViewHidden: !this.data.queryViewHidden,
    })
  },

  //点击查询按钮的事件
  queryComment: function (e) {
    var self = this
    self.setData({
      page: 1,
      totalPage: null,
      comments: [],
      loading_hide: true, //隐藏加载动画
      nodata_hide: true, //隐藏没有数据记录提示 
      queryViewHidden: true, //隐藏查询视图
    })
    self.listComment()
  },

  //绑定查询参数输入事件
  searchValueInput: function (e) {
    var id = e.target.dataset.id
    if (id == "commentTime") {
      this.setData({
        "commentTime": e.detail.value,
      })
    }

  },

  //查询参数被评民宿选择事件
  bind_minsuObj_change: function (e) {
    this.setData({
      minsuObj_Index: e.detail.value
    })
  },

  //查询参数评论用户选择事件
  bind_userObj_change: function (e) {
    this.setData({
      userObj_Index: e.detail.value
    })
  },

  init_query_params: function () {
    var self = this
    var url = null
    //初始化被评民宿下拉框
    url = config.basePath + "api/minsu/listAll"
    utils.sendRequest(url, null, function (res) {
      wx.stopPullDownRefresh();
      self.setData({
        minsus: self.data.minsus.concat(res.data),
      })
    })
    //初始化评论用户下拉框
    url = config.basePath + "api/userInfo/listAll"
    utils.sendRequest(url, null, function (res) {
      wx.stopPullDownRefresh();
      self.setData({
        userInfos: self.data.userInfos.concat(res.data),
      })
    })
  },

  //删除评论记录
  removeComment: function (e) {
    var self = this
    var commentId = e.currentTarget.dataset.commentid
    wx.showModal({
      title: '提示',
      content: '确定要删除commentId=' + commentId + '的记录吗？',
      success: function (sm) {
        if (sm.confirm) {
          // 用户点击了确定 可以调用删除方法了
          var url = config.basePath + "api/comment/delete/" + commentId
          utils.sendRequest(url, null, function (res) {
            wx.stopPullDownRefresh();
            wx.showToast({
              title: '删除成功',
              icon: 'success',
              duration: 500
            })
            //删除评论后客户端同步删除数据
            var comments = self.data.comments;
            for (var i = 0; i < comments.length; i++) {
              if (comments[i].commentId == commentId) {
                comments.splice(i, 1)
                break
              }
            }
            self.setData({ comments: comments })
          })
        } else if (sm.cancel) {
          console.log('用户点击取消')
        }
      }
    })
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    this.setData({
      "minsuId": options.minsuId
    })
    this.listComment()
    this.init_query_params()
  },

  /**
   * 页面相关事件处理函数--监听用户下拉动作
   */
  onPullDownRefresh: function () {
    var self = this
    self.setData({
      page: 1,  //显示最新的第1页结果
      comments: [], //清空评论数据
      nodata_hide: true, //隐藏没数据提示
    })
    self.listComment()
  },

  /**
   * 页面上拉触底事件的处理函数
   */
  onReachBottom: function () {
    var self = this
    if (self.data.page < self.data.totalPage) {
      self.setData({
        page: self.data.page + 1,
      })
      self.listComment()
    }
  },

  /**
   * 生命周期函数--监听页面显示
   */
  onShow: function () {

  },

  /**
   * 用户点击右上角分享
   */
  onShareAppMessage: function () {

  }

})

