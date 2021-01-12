"use strict";

var _regeneratorRuntime2 = _interopRequireDefault(require('./../vendor.js')(3));

var _core = _interopRequireDefault(require('./../vendor.js')(0));

var _eventHub = _interopRequireDefault(require('./../common/eventHub.js'));

var _store = _interopRequireDefault(require('./../store/index.js'));

var _x = require('./../vendor.js')(2);

var _xiao4rBase = _interopRequireDefault(require('./../xiao4rBase.js'));

var _userApis = _interopRequireDefault(require('./../apis/userApis.js'));

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
    form: _store["default"].state.wineryForm,
    wineryStatusType: ['已建成投产', '在建已投产', '在建未投产', '已停产']
  },
  computed: (0, _x.mapState)(['wineryForm']),
  methods: _objectSpread(_objectSpread({}, (0, _x.mapActions)(['setWineryFormAction'])), {}, {
    bindDateChange: function bindDateChange(event) {
      this.wineryForm.buildTime = event.$wx.detail.value;
    },
    bindRegionChange: function bindRegionChange(event) {
      this.wineryForm.region = event.$wx.detail.value;
    },
    onChangeWineryStatus: function onChangeWineryStatus(e) {
      this.wineryForm.wineryStatus = e.$wx.detail;
    },
    onGetMobile: function onGetMobile(e) {
      var _this = this;

      return _asyncToGenerator( /*#__PURE__*/_regeneratorRuntime2["default"].mark(function _callee() {
        var rsp;
        return _regeneratorRuntime2["default"].wrap(function _callee$(_context) {
          while (1) {
            switch (_context.prev = _context.next) {
              case 0:
                wx.showLoading({
                  title: '获取中...',
                  mask: true
                });
                _context.prev = 1;
                _context.next = 4;
                return _userApis["default"].sendMobile({
                  openid: _xiao4rBase["default"].getOpenid(),
                  detail: e.$wx.detail
                });

              case 4:
                rsp = _context.sent;

                if (rsp.code === 200) {
                  _this.wineryForm.mobile = rsp.data.mobile;
                }

                _xiao4rBase["default"].showToast(rsp.msg);

                _context.next = 12;
                break;

              case 9:
                _context.prev = 9;
                _context.t0 = _context["catch"](1);

                _xiao4rBase["default"].showToast('服务器连接异常,请联系管理员.');

              case 12:
                _context.prev = 12;
                wx.hideLoading();
                return _context.finish(12);

              case 15:
              case "end":
                return _context.stop();
            }
          }
        }, _callee, null, [[1, 9, 12, 15]]);
      }))();
    },
    onNext: function onNext() {
      console.log(this.form);

      if (!this.wineryForm.mobile) {
        _xiao4rBase["default"].showToast('请授权获取您的手机号');

        return;
      }

      if (!this.wineryForm.wineryName) {
        _xiao4rBase["default"].showToast('请输入您的酒庄名称');

        return;
      }

      if (!this.wineryForm.buildTime) {
        _xiao4rBase["default"].showToast('请输入您的酒庄成立时间');

        return;
      }

      if (!this.wineryForm.address) {
        _xiao4rBase["default"].showToast('请输入您的酒庄地址');

        return;
      }

      if (!this.wineryForm.wineryArea) {
        _xiao4rBase["default"].showToast('请输入您的酒庄总面积');

        return;
      }

      if (!this.wineryForm.buildArea) {
        _xiao4rBase["default"].showToast('请输入您的建筑总面积');

        return;
      } // this.setFormAction(this.form)


      _xiao4rBase["default"].navigateTo('form2');
    }
  }),
  created: function created() {
    var self = this;
  }
}, {info: {"components":{},"on":{}}, handlers: {'12-5': {"getphonenumber": function proxy () {
  var $wx = arguments[arguments.length - 1].$wx;
  var $event = ($wx.detail && $wx.detail.arguments) ? $wx.detail.arguments[0] : arguments[arguments.length -1];
  var $args = $wx.detail && $wx.detail.arguments;
  var _vm=this;
  return (function () {
    _vm.onGetMobile.apply(_vm, $args || [$event]);
  })();
}},'12-6': {"change": function proxy () {
  var $wx = arguments[arguments.length - 1].$wx;
  var $event = ($wx.detail && $wx.detail.arguments) ? $wx.detail.arguments[0] : arguments[arguments.length -1];
  var $args = $wx.detail && $wx.detail.arguments;
  var _vm=this;
  return (function () {
    _vm.bindDateChange.apply(_vm, $args || [$event]);
  })();
}},'12-7': {"change": function proxy () {
  var $wx = arguments[arguments.length - 1].$wx;
  var $event = ($wx.detail && $wx.detail.arguments) ? $wx.detail.arguments[0] : arguments[arguments.length -1];
  var $args = $wx.detail && $wx.detail.arguments;
  var _vm=this;
  return (function () {
    _vm.bindRegionChange.apply(_vm, $args || [$event]);
  })();
}},'12-8': {"change": function proxy () {
  var $wx = arguments[arguments.length - 1].$wx;
  var $event = ($wx.detail && $wx.detail.arguments) ? $wx.detail.arguments[0] : arguments[arguments.length -1];
  var $args = $wx.detail && $wx.detail.arguments;
  var _vm=this;
  return (function () {
    _vm.onChangeWineryStatus.apply(_vm, $args || [$event]);
  })();
}},'12-9': {"tap": function proxy () {
    var _vm=this;
  return (function () {
    _vm.onNext();
  })();
}}}, models: {'21': {
      type: "input",
      expr: "wineryForm.wineryName",
      handler: function set ($v) {
      var _vm=this;
        _vm.$set(_vm.wineryForm, "wineryName", $v);
      
    }
    },'22': {
      type: "change",
      expr: "wineryForm.date",
      handler: function set ($v) {
      var _vm=this;
        _vm.$set(_vm.wineryForm, "date", $v);
      
    }
    },'23': {
      type: "change",
      expr: "wineryForm.region",
      handler: function set ($v) {
      var _vm=this;
        _vm.$set(_vm.wineryForm, "region", $v);
      
    }
    },'24': {
      type: "input",
      expr: "wineryForm.address",
      handler: function set ($v) {
      var _vm=this;
        _vm.$set(_vm.wineryForm, "address", $v);
      
    }
    },'25': {
      type: "input",
      expr: "wineryForm.wineryArea",
      handler: function set ($v) {
      var _vm=this;
        _vm.$set(_vm.wineryForm, "wineryArea", $v);
      
    }
    },'26': {
      type: "input",
      expr: "wineryForm.buildArea",
      handler: function set ($v) {
      var _vm=this;
        _vm.$set(_vm.wineryForm, "buildArea", $v);
      
    }
    }}, refs: undefined });