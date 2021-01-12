"use strict";

Object.defineProperty(exports, "__esModule", {
  value: true
});
exports["default"] = exports.formHeader = exports.jsonHeader = exports.baseUrl = void 0;

var _request = _interopRequireDefault(require('./../js/request.js'));

function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { "default": obj }; }

function _classCallCheck(instance, Constructor) { if (!(instance instanceof Constructor)) { throw new TypeError("Cannot call a class as a function"); } }

function _defineProperties(target, props) { for (var i = 0; i < props.length; i++) { var descriptor = props[i]; descriptor.enumerable = descriptor.enumerable || false; descriptor.configurable = true; if ("value" in descriptor) descriptor.writable = true; Object.defineProperty(target, descriptor.key, descriptor); } }

function _createClass(Constructor, protoProps, staticProps) { if (protoProps) _defineProperties(Constructor.prototype, protoProps); if (staticProps) _defineProperties(Constructor, staticProps); return Constructor; }

var baseUrl = 'http://127.0.0.1:18989/winery/'; // export const baseUrl = 'http://36.1.50.18:18989/winery/'
// export const baseUrl = 'http://62.234.123.172:18989/api/'
// export const baseUrl = 'https://www.xiao4r.com/wine/winery/'

exports.baseUrl = baseUrl;
var jsonHeader = {
  'Content-Type': 'application/json'
};
exports.jsonHeader = jsonHeader;
var formHeader = {
  'Content-Type': 'application/x-www-form-urlencoded'
};
/**
 * 接口
 */

exports.formHeader = formHeader;

var Xiao4rApis = /*#__PURE__*/function () {
  function Xiao4rApis() {
    _classCallCheck(this, Xiao4rApis);
  }

  _createClass(Xiao4rApis, [{
    key: "postForm",
    value: function postForm(data) {
      return _request["default"].post({
        url: baseUrl + 'mini/postForm',
        header: jsonHeader,
        data: data
      });
    }
  }, {
    key: "getForm",
    value: function getForm(data) {
      return _request["default"].get({
        url: baseUrl + 'mini/getForm',
        header: formHeader,
        data: data
      });
    }
  }]);

  return Xiao4rApis;
}();

var _default = new Xiao4rApis();

exports["default"] = _default;