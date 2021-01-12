"use strict";

Object.defineProperty(exports, "__esModule", {
  value: true
});
exports["default"] = void 0;

var _x = _interopRequireDefault(require('./../vendor.js')(2));

var _wineryForm = require('./wineryForm.js');

function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { "default": obj }; }

var _default = new _x["default"].Store({
  state: {
    user: {
      openid: '',
      mobile: '',
      userInfo: {},
      token: ''
    },
    wineryForm: _wineryForm.wineryForm
  },
  mutations: {
    setUser: function setUser(state, user) {
      state.user = user;
    },
    setOpenid: function setOpenid(state, openid) {
      state.user.openid = openid;
      console.log('state.user.openid:', state.user.openid);
    },
    setMobile: function setMobile(state, mobile) {
      state.user.mobile = mobile;
    },
    setUserInfo: function setUserInfo(state, userInfo) {
      state.user.userInfo = userInfo;
    },
    setToken: function setToken(state, token) {
      state.user.token = token;
    },
    setWineryForm: function setWineryForm(state, form) {
      console.log('远程酒庄表单:', form);
      state.wineryForm = form;
      console.log('本地酒庄表单:', state.wineryForm);
    }
  },
  actions: {
    setUserAction: function setUserAction(_ref, user) {
      var commit = _ref.commit;
      commit('setUser', user);
    },
    setOpenidAction: function setOpenidAction(_ref2, openid) {
      var commit = _ref2.commit;
      commit('setOpenid', openid);
    },
    setMobileAction: function setMobileAction(_ref3, user) {
      var commit = _ref3.commit;
      commit('setMobile', user);
    },
    setUserInfoAction: function setUserInfoAction(_ref4, userInfo) {
      var commit = _ref4.commit;
      commit('setUserInfo', userInfo);
    },
    setTokenAction: function setTokenAction(_ref5, token) {
      var commit = _ref5.commit;
      commit('setToken', token);
    },
    setWineryFormAction: function setWineryFormAction(_ref6, form) {
      var commit = _ref6.commit;
      commit('setWineryForm', form);
    }
  }
});

exports["default"] = _default;