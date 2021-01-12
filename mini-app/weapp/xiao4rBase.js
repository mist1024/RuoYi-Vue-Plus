"use strict";

Object.defineProperty(exports, "__esModule", {
  value: true
});
exports["default"] = void 0;

var _store = _interopRequireDefault(require('./store/index.js'));

function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { "default": obj }; }

function _classCallCheck(instance, Constructor) { if (!(instance instanceof Constructor)) { throw new TypeError("Cannot call a class as a function"); } }

function _defineProperties(target, props) { for (var i = 0; i < props.length; i++) { var descriptor = props[i]; descriptor.enumerable = descriptor.enumerable || false; descriptor.configurable = true; if ("value" in descriptor) descriptor.writable = true; Object.defineProperty(target, descriptor.key, descriptor); } }

function _createClass(Constructor, protoProps, staticProps) { if (protoProps) _defineProperties(Constructor.prototype, protoProps); if (staticProps) _defineProperties(Constructor, staticProps); return Constructor; }

var Xiao4rBase = /*#__PURE__*/function () {
  function Xiao4rBase() {
    _classCallCheck(this, Xiao4rBase);
  }

  _createClass(Xiao4rBase, [{
    key: "saveOpenid",
    value: function saveOpenid(openid) {
      wx.setStorageSync('openid', openid);

      _store["default"].dispatch('setOpenidAction', openid);
    }
  }, {
    key: "getOpenid",
    value: function getOpenid() {
      return wx.getStorageSync('openid');
    }
  }, {
    key: "navigateTo",
    value: function navigateTo(url) {
      wx.navigateTo({
        url: url
      });
    }
  }, {
    key: "showToast",
    value: function showToast(msg) {
      wx.showToast({
        title: msg,
        icon: 'none'
      });
    }
  }]);

  return Xiao4rBase;
}();

var _default = new Xiao4rBase();

exports["default"] = _default;