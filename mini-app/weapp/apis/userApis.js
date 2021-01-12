"use strict";

Object.defineProperty(exports, "__esModule", {
  value: true
});
exports["default"] = void 0;

var _request = _interopRequireDefault(require('./../js/request.js'));

var _xiao4rApis = require('./xiao4rApis.js');

var _config = require('./../config.js');

function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { "default": obj }; }

function _classCallCheck(instance, Constructor) { if (!(instance instanceof Constructor)) { throw new TypeError("Cannot call a class as a function"); } }

function _defineProperties(target, props) { for (var i = 0; i < props.length; i++) { var descriptor = props[i]; descriptor.enumerable = descriptor.enumerable || false; descriptor.configurable = true; if ("value" in descriptor) descriptor.writable = true; Object.defineProperty(target, descriptor.key, descriptor); } }

function _createClass(Constructor, protoProps, staticProps) { if (protoProps) _defineProperties(Constructor.prototype, protoProps); if (staticProps) _defineProperties(Constructor, staticProps); return Constructor; }

/**
 * 用户相关接口
 */
var UserApis = /*#__PURE__*/function () {
  function UserApis() {
    _classCallCheck(this, UserApis);
  }

  _createClass(UserApis, [{
    key: "registrationByMini",

    /**
     * 注册
     * @param data
     * @returns {Promise实例对象}
     */
    value: function registrationByMini(data) {
      data.deptId = _config.MINI_DEPTID;
      return _request["default"].post({
        url: _xiao4rApis.baseUrl + 'mini/user/registrationByMini',
        data: data
      });
    }
    /**
     * 登录
     * @param data
     * @returns {Promise实例对象}
     */

  }, {
    key: "loginByMini",
    value: function loginByMini(data) {
      data.deptId = _config.MINI_DEPTID;
      return _request["default"].post({
        url: _xiao4rApis.baseUrl + 'mini/user/loginByMini',
        data: data
      });
    }
  }, {
    key: "getSession",
    value: function getSession(code) {
      return _request["default"].get({
        url: _xiao4rApis.baseUrl + 'mini/user/getSession',
        header: _xiao4rApis.formHeader,
        data: {
          'code': code,
          'deptId': _config.MINI_DEPTID
        }
      });
    }
  }, {
    key: "sendMobile",
    value: function sendMobile(data) {
      data.deptId = _config.MINI_DEPTID;
      return _request["default"].post({
        url: _xiao4rApis.baseUrl + 'mini/user/sendMobile',
        header: _xiao4rApis.jsonHeader,
        data: data
      });
    }
  }, {
    key: "getAuthTest",
    value: function getAuthTest(data) {
      return _request["default"].get({
        url: _xiao4rApis.baseUrl + 'mini/user/test',
        header: _xiao4rApis.formHeader,
        data: data
      });
    }
  }]);

  return UserApis;
}();

var _default = new UserApis();

exports["default"] = _default;