"use strict";

var _regeneratorRuntime2 = _interopRequireDefault(require('./../vendor.js')(3));

var _core = _interopRequireDefault(require('./../vendor.js')(0));

var _eventHub = _interopRequireDefault(require('./../common/eventHub.js'));

var _store = _interopRequireDefault(require('./../store/index.js'));

var _x = require('./../vendor.js')(2);

var _xiao4rBase = _interopRequireDefault(require('./../xiao4rBase.js'));

var _xiao4rApis = _interopRequireDefault(require('./../apis/xiao4rApis.js'));

var _validateUtils = _interopRequireDefault(require('./../js/utils/validateUtils.js'));

function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { "default": obj }; }

function asyncGeneratorStep(gen, resolve, reject, _next, _throw, key, arg) { try { var info = gen[key](arg); var value = info.value; } catch (error) { reject(error); return; } if (info.done) { resolve(value); } else { Promise.resolve(value).then(_next, _throw); } }

function _asyncToGenerator(fn) { return function () { var self = this, args = arguments; return new Promise(function (resolve, reject) { var gen = fn.apply(self, args); function _next(value) { asyncGeneratorStep(gen, resolve, reject, _next, _throw, "next", value); } function _throw(err) { asyncGeneratorStep(gen, resolve, reject, _next, _throw, "throw", err); } _next(undefined); }); }; }

_core["default"].page({
  store: _store["default"],
  hooks: {// Page 级别 hook, 只对当前 Page 的 setData 生效。
    // 'before-setData': function(dirty) {
    //   if (Math.random() < 0.2) {
    //     console.log('setData canceled')
    //     return false // Cancel setData
    //   }
    //   dirty.time = +new Date()
    //   return dirty
    // }
  },
  data: {
    form: {},
    sloganType: ['葡萄酒·贺兰山', '葡萄酒之乡·神奇贺兰山', '中国葡萄酒·当然贺兰山', '中国葡萄酒·要喝贺兰山', '其他'],
    slogan: ''
  },
  computed: (0, _x.mapState)(['wineryForm']),
  watch: {},
  methods: {
    onChangeSlogan: function onChangeSlogan(e) {
      this.wineryForm.slogan = e.$wx.detail;
    },
    onNext: function onNext() {
      var _this = this;

      return _asyncToGenerator( /*#__PURE__*/_regeneratorRuntime2["default"].mark(function _callee() {
        var form, rsp;
        return _regeneratorRuntime2["default"].wrap(function _callee$(_context) {
          while (1) {
            switch (_context.prev = _context.next) {
              case 0:
                if (_this.wineryForm.personName) {
                  _context.next = 3;
                  break;
                }

                _xiao4rBase["default"].showToast('请输入您的姓名!');

                return _context.abrupt("return");

              case 3:
                if (_validateUtils["default"].isEmail(_this.wineryForm.email)) {
                  _context.next = 6;
                  break;
                }

                _xiao4rBase["default"].showToast('请正确输入您的邮箱!');

                return _context.abrupt("return");

              case 6:
                form = _this.wineryForm;

                if (_this.wineryForm.slogan === '其他') {
                  form.slogan = _this.slogan;
                }

                form.openid = _xiao4rBase["default"].getOpenid();
                wx.showLoading({
                  title: '正在提交...',
                  mask: true
                });
                _context.prev = 10;
                _context.next = 13;
                return _xiao4rApis["default"].postForm(form);

              case 13:
                rsp = _context.sent;

                if (rsp.code === 200) {
                  _xiao4rBase["default"].showToast('提交成功!');

                  wx.reLaunch({
                    url: 'index'
                  });
                } else {
                  _xiao4rBase["default"].showToast('提交失败.' + rsp.msg);
                }

                _context.next = 20;
                break;

              case 17:
                _context.prev = 17;
                _context.t0 = _context["catch"](10);

                _xiao4rBase["default"].showToast('提交失败.', _context.t0);

              case 20:
                _context.prev = 20;
                wx.hideLoading();
                return _context.finish(20);

              case 23:
              case "end":
                return _context.stop();
            }
          }
        }, _callee, null, [[10, 17, 20, 23]]);
      }))();
    },
    onBack: function onBack() {
      wx.navigateBack();
    }
  },
  created: function created() {}
}, {info: {"components":{},"on":{}}, handlers: {'17-0': {"change": function proxy () {
  var $wx = arguments[arguments.length - 1].$wx;
  var $event = ($wx.detail && $wx.detail.arguments) ? $wx.detail.arguments[0] : arguments[arguments.length -1];
  var $args = $wx.detail && $wx.detail.arguments;
  var _vm=this;
  return (function () {
    _vm.onChangeSlogan.apply(_vm, $args || [$event]);
  })();
}},'17-1': {"tap": function proxy () {
    var _vm=this;
  return (function () {
    _vm.onBack();
  })();
}},'17-2': {"tap": function proxy () {
    var _vm=this;
  return (function () {
    _vm.onNext();
  })();
}}}, models: {'18': {
      type: "input",
      expr: "wineryForm.personName",
      handler: function set ($v) {
      var _vm=this;
        _vm.$set(_vm.wineryForm, "personName", $v);
      
    }
    },'19': {
      type: "input",
      expr: "wineryForm.email",
      handler: function set ($v) {
      var _vm=this;
        _vm.$set(_vm.wineryForm, "email", $v);
      
    }
    },'20': {
      type: "input",
      expr: "slogan",
      handler: function set ($v) {
      var _vm=this;
        _vm.slogan = $v;
      
    }
    }}, refs: undefined }, {info: {"components":{},"on":{}}, handlers: {'17-0': {"change": function proxy () {
  var $wx = arguments[arguments.length - 1].$wx;
  var $event = ($wx.detail && $wx.detail.arguments) ? $wx.detail.arguments[0] : arguments[arguments.length -1];
  var $args = $wx.detail && $wx.detail.arguments;
  var _vm=this;
  return (function () {
    _vm.onChangeSlogan.apply(_vm, $args || [$event]);
  })();
}},'17-1': {"tap": function proxy () {
    var _vm=this;
  return (function () {
    _vm.onBack();
  })();
}},'17-2': {"tap": function proxy () {
    var _vm=this;
  return (function () {
    _vm.onNext();
  })();
}}}, models: {'18': {
      type: "input",
      expr: "wineryForm.personName",
      handler: function set ($v) {
      var _vm=this;
        _vm.$set(_vm.wineryForm, "personName", $v);
      
    }
    },'19': {
      type: "input",
      expr: "wineryForm.email",
      handler: function set ($v) {
      var _vm=this;
        _vm.$set(_vm.wineryForm, "email", $v);
      
    }
    },'20': {
      type: "input",
      expr: "slogan",
      handler: function set ($v) {
      var _vm=this;
        _vm.slogan = $v;
      
    }
    }}, refs: undefined }, {info: {"components":{},"on":{}}, handlers: {'17-0': {"change": function proxy () {
  var $wx = arguments[arguments.length - 1].$wx;
  var $event = ($wx.detail && $wx.detail.arguments) ? $wx.detail.arguments[0] : arguments[arguments.length -1];
  var $args = $wx.detail && $wx.detail.arguments;
  var _vm=this;
  return (function () {
    _vm.onChangeSlogan.apply(_vm, $args || [$event]);
  })();
}},'17-1': {"tap": function proxy () {
    var _vm=this;
  return (function () {
    _vm.onBack();
  })();
}},'17-2': {"tap": function proxy () {
    var _vm=this;
  return (function () {
    _vm.onNext();
  })();
}}}, models: {'18': {
      type: "input",
      expr: "wineryForm.personName",
      handler: function set ($v) {
      var _vm=this;
        _vm.$set(_vm.wineryForm, "personName", $v);
      
    }
    },'19': {
      type: "input",
      expr: "wineryForm.email",
      handler: function set ($v) {
      var _vm=this;
        _vm.$set(_vm.wineryForm, "email", $v);
      
    }
    },'20': {
      type: "input",
      expr: "slogan",
      handler: function set ($v) {
      var _vm=this;
        _vm.slogan = $v;
      
    }
    }}, refs: undefined }, {info: {"components":{},"on":{}}, handlers: {'17-0': {"change": function proxy () {
  var $wx = arguments[arguments.length - 1].$wx;
  var $event = ($wx.detail && $wx.detail.arguments) ? $wx.detail.arguments[0] : arguments[arguments.length -1];
  var $args = $wx.detail && $wx.detail.arguments;
  var _vm=this;
  return (function () {
    _vm.onChangeSlogan.apply(_vm, $args || [$event]);
  })();
}},'17-1': {"tap": function proxy () {
    var _vm=this;
  return (function () {
    _vm.onBack();
  })();
}},'17-2': {"tap": function proxy () {
    var _vm=this;
  return (function () {
    _vm.onNext();
  })();
}}}, models: {'18': {
      type: "input",
      expr: "wineryForm.personName",
      handler: function set ($v) {
      var _vm=this;
        _vm.$set(_vm.wineryForm, "personName", $v);
      
    }
    },'19': {
      type: "input",
      expr: "wineryForm.email",
      handler: function set ($v) {
      var _vm=this;
        _vm.$set(_vm.wineryForm, "email", $v);
      
    }
    },'20': {
      type: "input",
      expr: "slogan",
      handler: function set ($v) {
      var _vm=this;
        _vm.slogan = $v;
      
    }
    }}, refs: undefined }, {info: {"components":{},"on":{}}, handlers: {'17-0': {"change": function proxy () {
  var $wx = arguments[arguments.length - 1].$wx;
  var $event = ($wx.detail && $wx.detail.arguments) ? $wx.detail.arguments[0] : arguments[arguments.length -1];
  var $args = $wx.detail && $wx.detail.arguments;
  var _vm=this;
  return (function () {
    _vm.onChangeSlogan.apply(_vm, $args || [$event]);
  })();
}},'17-1': {"tap": function proxy () {
    var _vm=this;
  return (function () {
    _vm.onBack();
  })();
}},'17-2': {"tap": function proxy () {
    var _vm=this;
  return (function () {
    _vm.onNext();
  })();
}}}, models: {'18': {
      type: "input",
      expr: "wineryForm.personName",
      handler: function set ($v) {
      var _vm=this;
        _vm.$set(_vm.wineryForm, "personName", $v);
      
    }
    },'19': {
      type: "input",
      expr: "wineryForm.email",
      handler: function set ($v) {
      var _vm=this;
        _vm.$set(_vm.wineryForm, "email", $v);
      
    }
    },'20': {
      type: "input",
      expr: "slogan",
      handler: function set ($v) {
      var _vm=this;
        _vm.slogan = $v;
      
    }
    }}, refs: undefined }, {info: {"components":{},"on":{}}, handlers: {'17-0': {"change": function proxy () {
  var $wx = arguments[arguments.length - 1].$wx;
  var $event = ($wx.detail && $wx.detail.arguments) ? $wx.detail.arguments[0] : arguments[arguments.length -1];
  var $args = $wx.detail && $wx.detail.arguments;
  var _vm=this;
  return (function () {
    _vm.onChangeSlogan.apply(_vm, $args || [$event]);
  })();
}},'17-1': {"tap": function proxy () {
    var _vm=this;
  return (function () {
    _vm.onBack();
  })();
}},'17-2': {"tap": function proxy () {
    var _vm=this;
  return (function () {
    _vm.onNext();
  })();
}}}, models: {'18': {
      type: "input",
      expr: "wineryForm.personName",
      handler: function set ($v) {
      var _vm=this;
        _vm.$set(_vm.wineryForm, "personName", $v);
      
    }
    },'19': {
      type: "input",
      expr: "wineryForm.email",
      handler: function set ($v) {
      var _vm=this;
        _vm.$set(_vm.wineryForm, "email", $v);
      
    }
    },'20': {
      type: "input",
      expr: "slogan",
      handler: function set ($v) {
      var _vm=this;
        _vm.slogan = $v;
      
    }
    }}, refs: undefined }, {info: {"components":{},"on":{}}, handlers: {'17-0': {"change": function proxy () {
  var $wx = arguments[arguments.length - 1].$wx;
  var $event = ($wx.detail && $wx.detail.arguments) ? $wx.detail.arguments[0] : arguments[arguments.length -1];
  var $args = $wx.detail && $wx.detail.arguments;
  var _vm=this;
  return (function () {
    _vm.onChangeSlogan.apply(_vm, $args || [$event]);
  })();
}},'17-1': {"tap": function proxy () {
    var _vm=this;
  return (function () {
    _vm.onBack();
  })();
}},'17-2': {"tap": function proxy () {
    var _vm=this;
  return (function () {
    _vm.onNext();
  })();
}}}, models: {'18': {
      type: "input",
      expr: "wineryForm.personName",
      handler: function set ($v) {
      var _vm=this;
        _vm.$set(_vm.wineryForm, "personName", $v);
      
    }
    },'19': {
      type: "input",
      expr: "wineryForm.email",
      handler: function set ($v) {
      var _vm=this;
        _vm.$set(_vm.wineryForm, "email", $v);
      
    }
    },'20': {
      type: "input",
      expr: "slogan",
      handler: function set ($v) {
      var _vm=this;
        _vm.slogan = $v;
      
    }
    }}, refs: undefined });