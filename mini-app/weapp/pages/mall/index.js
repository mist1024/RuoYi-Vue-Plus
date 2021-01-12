"use strict";

var _regeneratorRuntime2 = _interopRequireDefault(require('./../../vendor.js')(3));

var _core = _interopRequireDefault(require('./../../vendor.js')(0));

var _store = _interopRequireDefault(require('./../../store/index.js'));

var _x = require('./../../vendor.js')(2);

var _userApis = _interopRequireDefault(require('./../../apis/userApis.js'));

var _xiao4rBase = _interopRequireDefault(require('./../../xiao4rBase.js'));

var _eventHub = _interopRequireDefault(require('./../../common/eventHub.js'));

function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { "default": obj }; }

function asyncGeneratorStep(gen, resolve, reject, _next, _throw, key, arg) { try { var info = gen[key](arg); var value = info.value; } catch (error) { reject(error); return; } if (info.done) { resolve(value); } else { Promise.resolve(value).then(_next, _throw); } }

function _asyncToGenerator(fn) { return function () { var self = this, args = arguments; return new Promise(function (resolve, reject) { var gen = fn.apply(self, args); function _next(value) { asyncGeneratorStep(gen, resolve, reject, _next, _throw, "next", value); } function _throw(err) { asyncGeneratorStep(gen, resolve, reject, _next, _throw, "throw", err); } _next(undefined); }); }; }

function ownKeys(object, enumerableOnly) { var keys = Object.keys(object); if (Object.getOwnPropertySymbols) { var symbols = Object.getOwnPropertySymbols(object); if (enumerableOnly) symbols = symbols.filter(function (sym) { return Object.getOwnPropertyDescriptor(object, sym).enumerable; }); keys.push.apply(keys, symbols); } return keys; }

function _objectSpread(target) { for (var i = 1; i < arguments.length; i++) { var source = arguments[i] != null ? arguments[i] : {}; if (i % 2) { ownKeys(Object(source), true).forEach(function (key) { _defineProperty(target, key, source[key]); }); } else if (Object.getOwnPropertyDescriptors) { Object.defineProperties(target, Object.getOwnPropertyDescriptors(source)); } else { ownKeys(Object(source)).forEach(function (key) { Object.defineProperty(target, key, Object.getOwnPropertyDescriptor(source, key)); }); } } return target; }

function _defineProperty(obj, key, value) { if (key in obj) { Object.defineProperty(obj, key, { value: value, enumerable: true, configurable: true, writable: true }); } else { obj[key] = value; } return obj; }

_core["default"].page({
  store: _store["default"],
  hooks: {},
  data: {
    pageIndex: 0
  },
  computed: {},
  methods: _objectSpread(_objectSpread({}, (0, _x.mapActions)(['setUserAction', 'setTokenAction', 'setOpenidAction'])), {}, {
    onTest: function onTest() {
      _eventHub["default"].$emit('onShowDialogUserInfo');
    },
    handleViewTap: function handleViewTap() {
      console.log('handleVieTap clicked');
    },
    tap: function tap() {},
    onChange: function onChange(event) {
      this.pageIndex = event.$wx.detail;
    },
    callAppLaunch: function callAppLaunch() {},
    login: function login() {
      var self = this;
      wx.showLoading({
        title: '正在连接...',
        mask: true
      });
      wx.login({
        success: function success(res) {
          var req = _userApis["default"].getSession(res.code);

          req.then(function (rsp) {
            if (rsp.data.openid) {
              self.init(rsp.data.openid);
            } else {
              _xiao4rBase["default"].showToast('登录失败！' + res.errMsg);

              wx.hideLoading();
            }
          })["catch"](function (e) {
            _xiao4rBase["default"].showToast('登录失败！' + res.errMsg);
          }); // request.requestTaskMap.get(req.taskId).abort()
          // console.log('中断请求,req:', req.taskId)
        },
        fail: function fail(res) {
          _xiao4rBase["default"].showToast('登录失败,正在重试.');

          wx.hideLoading();
          self.login();
        }
      });
    },
    init: function init(openid) {
      var _this = this;

      return _asyncToGenerator( /*#__PURE__*/_regeneratorRuntime2["default"].mark(function _callee() {
        var rsp;
        return _regeneratorRuntime2["default"].wrap(function _callee$(_context) {
          while (1) {
            switch (_context.prev = _context.next) {
              case 0:
                _xiao4rBase["default"].saveOpenid(openid);

                _context.next = 3;
                return _userApis["default"].loginByMini({
                  openid: openid
                });

              case 3:
                rsp = _context.sent;

                if (rsp.token) {
                  _this.setTokenAction(rsp.token);
                }

                wx.hideLoading();

              case 6:
              case "end":
                return _context.stop();
            }
          }
        }, _callee);
      }))();
    }
  }),
  ready: function ready() {
    this.login();
  },
  onShow: function onShow() {}
}, {info: {"components":{"mall-home":{"path":"./../../components/mall/mall-home"},"mall-shopping":{"path":"./../../components/mall/mall-shopping"},"mall-bbs":{"path":"./../../components/mall/mall-bbs"},"mall-car":{"path":"./../../components/mall/mall-car"},"mall-my":{"path":"./../../components/mall/mall-my"},"dialog-registration":{"path":"./../../components/user/dialog-registration"}},"on":{}}, handlers: {'18-6': {"change": function proxy () {
  var $wx = arguments[arguments.length - 1].$wx;
  var $event = ($wx.detail && $wx.detail.arguments) ? $wx.detail.arguments[0] : arguments[arguments.length -1];
  var $args = $wx.detail && $wx.detail.arguments;
  var _vm=this;
  return (function () {
    _vm.onChange.apply(_vm, $args || [$event]);
  })();
}},'18-7': {"tap": function proxy () {
  var $wx = arguments[arguments.length - 1].$wx;
  var $event = ($wx.detail && $wx.detail.arguments) ? $wx.detail.arguments[0] : arguments[arguments.length -1];
  var $args = $wx.detail && $wx.detail.arguments;
  var _vm=this;
  return (function () {
    _vm.onTest.apply(_vm, $args || [$event]);
  })();
}}}, models: {}, refs: undefined });