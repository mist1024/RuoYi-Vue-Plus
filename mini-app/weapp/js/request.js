"use strict";

Object.defineProperty(exports, "__esModule", {
  value: true
});
exports["default"] = void 0;

var _xiao4rBase = _interopRequireDefault(require('./../xiao4rBase.js'));

var _uuid = require('./utils/uuid.js');

var _store = _interopRequireDefault(require('./../store/index.js'));

function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { "default": obj }; }

function _classCallCheck(instance, Constructor) { if (!(instance instanceof Constructor)) { throw new TypeError("Cannot call a class as a function"); } }

function _defineProperties(target, props) { for (var i = 0; i < props.length; i++) { var descriptor = props[i]; descriptor.enumerable = descriptor.enumerable || false; descriptor.configurable = true; if ("value" in descriptor) descriptor.writable = true; Object.defineProperty(target, descriptor.key, descriptor); } }

function _createClass(Constructor, protoProps, staticProps) { if (protoProps) _defineProperties(Constructor.prototype, protoProps); if (staticProps) _defineProperties(Constructor, staticProps); return Constructor; }

function _defineProperty(obj, key, value) { if (key in obj) { Object.defineProperty(obj, key, { value: value, enumerable: true, configurable: true, writable: true }); } else { obj[key] = value; } return obj; }

var Xiao4rRequest = /*#__PURE__*/function () {
  function Xiao4rRequest() {
    _classCallCheck(this, Xiao4rRequest);

    _defineProperty(this, "requestTaskMap", new Map());
  }

  _createClass(Xiao4rRequest, [{
    key: "requestPromise",

    /**
     * requestPromise用于将wx.request改写成Promise方式
     * @param：{string} myUrl 接口地址
     * @return: Promise实例对象
     */
    value: function requestPromise(req) {
      var _this = this;

      var id = (0, _uuid.uuid)();
      var self = this;

      if (_store["default"].state.user.token) {
        if (req.header) {
          req.header['Authorization'] = _store["default"].state.user.token;
        } else {
          req.header = {
            Authorization: _store["default"].state.user.token
          };
        }
      }

      var promise = new Promise(function (resolve, reject) {
        var task = wx.request({
          url: req.url,
          method: req.method,
          header: req.header,
          data: req.data,
          success: function success(rsp) {
            return resolve(rsp.data);
          },
          fail: function fail(error) {
            _xiao4rBase["default"].showToast('网络连接异常.');

            reject(error);
          },
          complete: function complete() {
            _this.requestTaskMap["delete"](id);
          }
        });
        self.requestTaskMap.set(id, task);
      }); // 返回一个Promise实例对象

      promise.taskId = id;
      return promise;
    }
  }, {
    key: "post",
    value: function post(req) {
      req.method = 'POST';
      return this.requestPromise(req);
    }
  }, {
    key: "get",
    value: function get(req) {
      req.method = 'GET';
      return this.requestPromise(req);
    }
  }, {
    key: "put",
    value: function put(req) {
      req.method = 'PUT';
      return this.requestPromise(req);
    }
  }, {
    key: "del",
    value: function del(req) {
      req.method = 'DEL';
      return this.requestPromise(req);
    }
  }]);

  return Xiao4rRequest;
}();

var _default = new Xiao4rRequest();

exports["default"] = _default;