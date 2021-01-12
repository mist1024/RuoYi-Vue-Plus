"use strict";

var _regeneratorRuntime2 = _interopRequireDefault(require('./../vendor.js')(3));

var _core = _interopRequireDefault(require('./../vendor.js')(0));

var _eventHub = _interopRequireDefault(require('./../common/eventHub.js'));

var _store = _interopRequireDefault(require('./../store/index.js'));

var _x = require('./../vendor.js')(2);

var _xiao4rBase = _interopRequireDefault(require('./../xiao4rBase.js'));

var _xiao4rApis = _interopRequireDefault(require('./../apis/xiao4rApis.js'));

var _userApis = _interopRequireDefault(require('./../apis/userApis.js'));

var _request = _interopRequireDefault(require('./../js/request.js'));

function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { "default": obj }; }

function asyncGeneratorStep(gen, resolve, reject, _next, _throw, key, arg) { try { var info = gen[key](arg); var value = info.value; } catch (error) { reject(error); return; } if (info.done) { resolve(value); } else { Promise.resolve(value).then(_next, _throw); } }

function _asyncToGenerator(fn) { return function () { var self = this, args = arguments; return new Promise(function (resolve, reject) { var gen = fn.apply(self, args); function _next(value) { asyncGeneratorStep(gen, resolve, reject, _next, _throw, "next", value); } function _throw(err) { asyncGeneratorStep(gen, resolve, reject, _next, _throw, "throw", err); } _next(undefined); }); }; }

function ownKeys(object, enumerableOnly) { var keys = Object.keys(object); if (Object.getOwnPropertySymbols) { var symbols = Object.getOwnPropertySymbols(object); if (enumerableOnly) symbols = symbols.filter(function (sym) { return Object.getOwnPropertyDescriptor(object, sym).enumerable; }); keys.push.apply(keys, symbols); } return keys; }

function _objectSpread(target) { for (var i = 1; i < arguments.length; i++) { var source = arguments[i] != null ? arguments[i] : {}; if (i % 2) { ownKeys(Object(source), true).forEach(function (key) { _defineProperty(target, key, source[key]); }); } else if (Object.getOwnPropertyDescriptors) { Object.defineProperties(target, Object.getOwnPropertyDescriptors(source)); } else { ownKeys(Object(source)).forEach(function (key) { Object.defineProperty(target, key, Object.getOwnPropertyDescriptor(source, key)); }); } } return target; }

function _defineProperty(obj, key, value) { if (key in obj) { Object.defineProperty(obj, key, { value: value, enumerable: true, configurable: true, writable: true }); } else { obj[key] = value; } return obj; }

_core["default"].page({
  store: _store["default"],
  hooks: {
    // Page 级别 hook, 只对当前 Page 的 setData 生效。
    'before-setData': function beforeSetData(dirty) {
      if (Math.random() < 0.2) {
        console.log('setData canceled');
        return false; // Cancel setData
      }

      dirty.time = +new Date();
      return dirty;
    }
  },
  data: {
    user: _store["default"].state.user
  },
  computed: {
    testcomputed: function testcomputed() {
      return 'computed - ';
    },
    openid: function openid() {
      return _store["default"].state.user.openid;
    }
  },
  methods: _objectSpread(_objectSpread({}, (0, _x.mapActions)(['setUserAction', 'setWineryFormAction'])), {}, {
    handleViewTap: function handleViewTap() {
      console.log('handleVieTap clicked');
    },
    tap: function tap() {},
    callAppLaunch: function callAppLaunch() {
      _eventHub["default"].$emit('app-launch', {
        a: 1
      }, {
        b: 2
      });
    },
    onNext: function onNext() {
      _xiao4rBase["default"].navigateTo('form1'); // xiao4rApis.getAuthTest({ openid: store.state.user.openid }).then(r => {
      //   wx.navigateToMiniProgram({
      //     appId: 'wx88736d7d39e2eda6',
      //     path: 'pages/oauth/authindex',
      //     extraData: r.data
      //   })
      // })

    },
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
        var self;
        return _regeneratorRuntime2["default"].wrap(function _callee$(_context) {
          while (1) {
            switch (_context.prev = _context.next) {
              case 0:
                self = _this;

                _xiao4rBase["default"].saveOpenid(openid);

                wx.getUserInfo({
                  success: function success(res) {
                    self.setUserAction({
                      openid: _xiao4rBase["default"].getOpenid(),
                      userInfo: res.userInfo
                    });
                  }
                });
                _context.next = 5;
                return _this.getForm();

              case 5:
                wx.hideLoading();

              case 6:
              case "end":
                return _context.stop();
            }
          }
        }, _callee);
      }))();
    },
    getForm: function getForm() {
      var _this2 = this;

      return _asyncToGenerator( /*#__PURE__*/_regeneratorRuntime2["default"].mark(function _callee2() {
        var rsp, temp;
        return _regeneratorRuntime2["default"].wrap(function _callee2$(_context2) {
          while (1) {
            switch (_context2.prev = _context2.next) {
              case 0:
                _context2.prev = 0;
                _context2.next = 3;
                return _xiao4rApis["default"].getForm({
                  openid: _xiao4rBase["default"].getOpenid()
                });

              case 3:
                rsp = _context2.sent;

                if (rsp.code === 200) {
                  temp = rsp.data;
                  temp.region = temp.region.split(',');
                  temp.fermentationProcess = temp.fermentationProcess.split(',');
                  temp.clarificationMethod = temp.clarificationMethod.split(',');
                  temp.mainPrice = temp.mainPrice.split(',');
                  temp.salesMode = temp.salesMode.split(',');
                  temp.container = temp.container.split(',');
                  temp.awards = temp.awards.split(',');
                  temp.redVariety = JSON.parse(temp.redVariety);
                  temp.whiteVariety = JSON.parse(temp.whiteVariety);

                  _this2.setWineryFormAction(temp);
                }

                _context2.next = 10;
                break;

              case 7:
                _context2.prev = 7;
                _context2.t0 = _context2["catch"](0);

                _xiao4rBase["default"].showToast('获取用户信息失败.' + _context2.t0);

              case 10:
              case "end":
                return _context2.stop();
            }
          }
        }, _callee2, null, [[0, 7]]);
      }))();
    }
  }),
  ready: function ready() {
    this.login();
  }
}, {info: {"components":{},"on":{}}, handlers: {'11-1': {"tap": function proxy () {
    var _vm=this;
  return (function () {
    _vm.onNext();
  })();
}}}, models: {}, refs: undefined });