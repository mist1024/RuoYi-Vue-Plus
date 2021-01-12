"use strict";

var _regeneratorRuntime2 = _interopRequireDefault(require('./../../vendor.js')(3));

var _core = _interopRequireDefault(require('./../../vendor.js')(0));

var _store = _interopRequireDefault(require('./../../store/index.js'));

var _x = require('./../../vendor.js')(2);

var _eventHub = _interopRequireDefault(require('./../../common/eventHub.js'));

var _xiao4rBase = _interopRequireDefault(require('./../../xiao4rBase.js'));

var _userApis = _interopRequireDefault(require('./../../apis/userApis.js'));

function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { "default": obj }; }

function asyncGeneratorStep(gen, resolve, reject, _next, _throw, key, arg) { try { var info = gen[key](arg); var value = info.value; } catch (error) { reject(error); return; } if (info.done) { resolve(value); } else { Promise.resolve(value).then(_next, _throw); } }

function _asyncToGenerator(fn) { return function () { var self = this, args = arguments; return new Promise(function (resolve, reject) { var gen = fn.apply(self, args); function _next(value) { asyncGeneratorStep(gen, resolve, reject, _next, _throw, "next", value); } function _throw(err) { asyncGeneratorStep(gen, resolve, reject, _next, _throw, "throw", err); } _next(undefined); }); }; }

function ownKeys(object, enumerableOnly) { var keys = Object.keys(object); if (Object.getOwnPropertySymbols) { var symbols = Object.getOwnPropertySymbols(object); if (enumerableOnly) symbols = symbols.filter(function (sym) { return Object.getOwnPropertyDescriptor(object, sym).enumerable; }); keys.push.apply(keys, symbols); } return keys; }

function _objectSpread(target) { for (var i = 1; i < arguments.length; i++) { var source = arguments[i] != null ? arguments[i] : {}; if (i % 2) { ownKeys(Object(source), true).forEach(function (key) { _defineProperty(target, key, source[key]); }); } else if (Object.getOwnPropertyDescriptors) { Object.defineProperties(target, Object.getOwnPropertyDescriptors(source)); } else { ownKeys(Object(source)).forEach(function (key) { Object.defineProperty(target, key, Object.getOwnPropertyDescriptor(source, key)); }); } } return target; }

function _defineProperty(obj, key, value) { if (key in obj) { Object.defineProperty(obj, key, { value: value, enumerable: true, configurable: true, writable: true }); } else { obj[key] = value; } return obj; }

_core["default"].component({
  store: _store["default"],
  props: {},
  hooks: {},
  data: {
    that: void 0,
    isShow: false,
    model: {},
    isChecked: false
  },
  computed: (0, _x.mapState)(['user']),
  events: {},
  methods: _objectSpread(_objectSpread({}, (0, _x.mapActions)(['setUserAction', 'setUserInfoAction', 'setMobileAction', 'setTokenAction'])), {}, {
    getUserInfo: function getUserInfo(event) {
      console.log('getUserInfo:', event.$wx.detail);
      this.setUserInfoAction(event.$wx.detail.userInfo);
      console.log('userInfo:', _store["default"].state.user.userInfo);

      if (!this.isChecked) {
        _xiao4rBase["default"].showToast('请确认xxxx协议.');

        return false;
      }

      if (!this.user.mobile) {
        _xiao4rBase["default"].showToast('请授权获取手机号码.');

        return false;
      }

      if (!this.user.userInfo) {
        _xiao4rBase["default"].showToast('请授权获取用户昵称.');

        return false;
      }

      var self = this;

      _userApis["default"].registrationByMini(this.user).then(function (r) {
        if (r.code === 200) {
          _xiao4rBase["default"].showToast('注册成功!');

          self.isShow = false;
          self.setTokenAction(r.token);
        } else {
          _xiao4rBase["default"].showToast(r.msg);
        }
      })["catch"](function (e) {
        _xiao4rBase["default"].showToast('注册失败!');
      });
    },
    onGetMobile: function onGetMobile(e) {
      var _this = this;

      return _asyncToGenerator( /*#__PURE__*/_regeneratorRuntime2["default"].mark(function _callee() {
        var rsp;
        return _regeneratorRuntime2["default"].wrap(function _callee$(_context) {
          while (1) {
            switch (_context.prev = _context.next) {
              case 0:
                console.log(e.$wx.detail);
                wx.showLoading({
                  title: '获取中...',
                  mask: true
                });
                _context.prev = 2;
                _context.next = 5;
                return _userApis["default"].sendMobile({
                  openid: _xiao4rBase["default"].getOpenid(),
                  detail: e.$wx.detail
                });

              case 5:
                rsp = _context.sent;
                console.log(rsp);

                if (rsp.code === 200) {
                  _this.setMobileAction(rsp.data.mobile);
                } else {
                  _xiao4rBase["default"].showToast('服务器连接异常.');
                }

                _context.next = 13;
                break;

              case 10:
                _context.prev = 10;
                _context.t0 = _context["catch"](2);

                _xiao4rBase["default"].showToast('服务器连接异常.');

              case 13:
                _context.prev = 13;
                wx.hideLoading();
                return _context.finish(13);

              case 16:
              case "end":
                return _context.stop();
            }
          }
        }, _callee, null, [[2, 10, 13, 16]]);
      }))();
    },
    onChangeCheak: function onChangeCheak(e) {
      this.isChecked = e.$wx.detail;
    },
    onClose: function onClose() {
      this.isShow = false;
    }
  }),
  ready: function ready() {
    var _this2 = this;

    // 获取系统信息
    var self = this;
    wx.getSystemInfo({
      success: function success(res) {
        self.model = res.model;
      }
    });
    console.log(this);
    this.page = this;

    _eventHub["default"].$on('onShowDialogUserInfo', function () {
      _this2.isShow = true;
    });
  }
}, {info: {"components":{},"on":{}}, handlers: {'87-42': {"close": function proxy () {
  var $wx = arguments[arguments.length - 1].$wx;
  var $event = ($wx.detail && $wx.detail.arguments) ? $wx.detail.arguments[0] : arguments[arguments.length -1];
  var $args = $wx.detail && $wx.detail.arguments;
  var _vm=this;
  return (function () {
    _vm.onClose.apply(_vm, $args || [$event]);
  })();
}, "cancel": function proxy () {
  var $wx = arguments[arguments.length - 1].$wx;
  var $event = ($wx.detail && $wx.detail.arguments) ? $wx.detail.arguments[0] : arguments[arguments.length -1];
  var $args = $wx.detail && $wx.detail.arguments;
  var _vm=this;
  return (function () {
    _vm.onClose.apply(_vm, $args || [$event]);
  })();
}, "getuserinfo": function proxy () {
  var $wx = arguments[arguments.length - 1].$wx;
  var $event = ($wx.detail && $wx.detail.arguments) ? $wx.detail.arguments[0] : arguments[arguments.length -1];
  var $args = $wx.detail && $wx.detail.arguments;
  var _vm=this;
  return (function () {
    _vm.getUserInfo.apply(_vm, $args || [$event]);
  })();
}},'87-45': {"getphonenumber": function proxy () {
  var $wx = arguments[arguments.length - 1].$wx;
  var $event = ($wx.detail && $wx.detail.arguments) ? $wx.detail.arguments[0] : arguments[arguments.length -1];
  var $args = $wx.detail && $wx.detail.arguments;
  var _vm=this;
  return (function () {
    _vm.onGetMobile.apply(_vm, $args || [$event]);
  })();
}},'87-46': {"change": function proxy () {
  var $wx = arguments[arguments.length - 1].$wx;
  var $event = ($wx.detail && $wx.detail.arguments) ? $wx.detail.arguments[0] : arguments[arguments.length -1];
  var $args = $wx.detail && $wx.detail.arguments;
  var _vm=this;
  return (function () {
    _vm.onChangeCheak.apply(_vm, $args || [$event]);
  })();
}}}, models: {}, refs: undefined });