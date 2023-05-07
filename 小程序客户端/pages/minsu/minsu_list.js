var utils = require("../../utils/common.js");
var config = require("../../utils/config.js");

Page({
  /**
   * 页面的初始数据
   */
  data: {
    queryViewHidden: true, //是否隐藏查询条件界面
    ownerObj_Index:"0", //民宿主人查询条件
    owners: [{"ownerUserName":"","name":"不限制"}],
    addTime: "", //发布时间查询关键字
    areaObj_Index:"0", //所在地区查询条件
    areas: [{"areaId":0,"areanName":"不限制"}],
    minsuName: "", //民宿名称查询关键字
    minsus: [], //界面显示的民宿列表数据
    page_size: 8, //每页显示几条数据
    page: 1,  //当前要显示第几页
    totalPage: null, //总的页码数
    loading_hide: true, //是否隐藏加载动画
    nodata_hide: true, //是否隐藏没有数据记录提示
  },

  // 加载民宿列表
  listMinsu: function () {
    var self = this
    var url = config.basePath + "api/minsu/list"
    //如果要显示的页码超过总页码不操作
    if(self.data.totalPage != null && self.data.page > self.data.totalPage) return
    self.setData({
      loading_hide: false,  //显示加载动画
    })
    //提交查询参数到服务器查询数据列表
    utils.sendRequest(url, {
      "ownerObj.ownerUserName": self.data.owners[self.data.ownerObj_Index].ownerUserName,
      "addTime": self.data.addTime,
      "areaObj.areaId": self.data.areas[self.data.areaObj_Index].areaId,
      "minsuName": self.data.minsuName,
      "page": self.data.page,
      "rows": self.data.page_size,
    }, function (res) { 
      wx.stopPullDownRefresh()
      setTimeout(function () {  
        self.setData({
          minsus: self.data.minsus.concat(res.data.list),
          totalPage: res.data.totalPage,
          loading_hide: true
        })
      }, 500)
      //如果当前显示的是最后一页，则显示没数据提示
      if(self.data.page == self.data.totalPage) {
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
  queryMinsu: function(e) {
    var self = this
    self.setData({
      page: 1,
      totalPage: null,
      minsus: [],
      loading_hide: true, //隐藏加载动画
      nodata_hide: true, //隐藏没有数据记录提示 
      queryViewHidden: true, //隐藏查询视图
    })
    self.listMinsu()
  },

  //查询参数发布时间选择事件
  bind_addTime_change: function (e) {
    this.setData({
      addTime: e.detail.value
    })
  },
  //清空查询参数发布时间
  clear_addTime: function (e) {
    this.setData({
      addTime: "",
    })
  },

  //绑定查询参数输入事件
  searchValueInput: function (e) {
    var id = e.target.dataset.id
    if (id == "minsuName") {
      this.setData({
        "minsuName": e.detail.value,
      })
    }

  },

  //查询参数民宿主人选择事件
  bind_ownerObj_change: function(e) {
    this.setData({
      ownerObj_Index: e.detail.value
    })
  },

  //查询参数所在地区选择事件
  bind_areaObj_change: function(e) {
    this.setData({
      areaObj_Index: e.detail.value
    })
  },

  init_query_params: function() { 
    var self = this
    var url = null
    //初始化民宿主人下拉框
    url = config.basePath + "api/owner/listAll"
    utils.sendRequest(url,null,function(res){
      wx.stopPullDownRefresh();
      self.setData({
        owners: self.data.owners.concat(res.data),
      })
    })
    //初始化所在地区下拉框
    url = config.basePath + "api/area/listAll"
    utils.sendRequest(url,null,function(res){
      wx.stopPullDownRefresh();
      self.setData({
        areas: self.data.areas.concat(res.data),
      })
    })
  },

  //删除民宿记录
  removeMinsu: function (e) {
    var self = this
    var minsuId = e.currentTarget.dataset.minsuid
    wx.showModal({
      title: '提示',
      content: '确定要删除minsuId=' + minsuId + '的记录吗？',
      success: function (sm) {
        if (sm.confirm) {
          // 用户点击了确定 可以调用删除方法了
          var url = config.basePath + "api/minsu/delete/" + minsuId
          utils.sendRequest(url, null, function (res) {
            wx.stopPullDownRefresh();
            wx.showToast({
              title: '删除成功',
              icon: 'success',
              duration: 500
            })
            //删除民宿后客户端同步删除数据
            var minsus = self.data.minsus;
            for (var i = 0; i < minsus.length; i++) {
              if (minsus[i].minsuId == minsuId) {
                minsus.splice(i, 1)
                break
              }
            }
            self.setData({ minsus: minsus })
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
    this.listMinsu()
    this.init_query_params()
  },

  /**
   * 页面相关事件处理函数--监听用户下拉动作
   */
  onPullDownRefresh: function () {
    var self = this
    self.setData({
      page: 1,  //显示最新的第1页结果
      minsus: [], //清空民宿数据
      nodata_hide: true, //隐藏没数据提示
    })
    self.listMinsu()
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
      self.listMinsu()
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

