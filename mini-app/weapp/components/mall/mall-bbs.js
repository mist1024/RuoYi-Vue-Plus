"use strict";

var _core = _interopRequireDefault(require('./../../vendor.js')(0));

var _store = _interopRequireDefault(require('./../../store/index.js'));

var _x = require('./../../vendor.js')(2);

function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { "default": obj }; }

_core["default"].component({
  store: _store["default"],
  hooks: {},
  data: {
    user: _store["default"].state.user,
    list: [],
    isShowInput: false
  },
  computed: {},
  methods: {
    onReply: function onReply(item) {
      console.log(item);
      this.isShowInput = true;
    },
    onCloseInput: function onCloseInput() {
      this.isShowInput = false;
    },
    onConfirmInput: function onConfirmInput() {
      this.isShowInput = false;
    }
  },
  ready: function ready() {
    console.log('user:', this.user);

    for (var i = 0; i < 10; i++) {
      this.list.push({
        title: '标题',
        user: '用户1',
        context: '长篇大论长篇大论长篇大论长篇大论长篇大论长篇大论长篇大论长篇大论长篇大论长篇大论'
      });
    }
  }
}, {info: {"components":{},"on":{}}, handlers: {'84-0': {"tap": function proxy (item) {
    var _vm=this;
  return (function () {
    _vm.onReply(item);
  })();
}},'84-1': {"close": function proxy () {
  var $wx = arguments[arguments.length - 1].$wx;
  var $event = ($wx.detail && $wx.detail.arguments) ? $wx.detail.arguments[0] : arguments[arguments.length -1];
  var $args = $wx.detail && $wx.detail.arguments;
  var _vm=this;
  return (function () {
    _vm.onCloseInput.apply(_vm, $args || [$event]);
  })();
}, "confirm": function proxy () {
  var $wx = arguments[arguments.length - 1].$wx;
  var $event = ($wx.detail && $wx.detail.arguments) ? $wx.detail.arguments[0] : arguments[arguments.length -1];
  var $args = $wx.detail && $wx.detail.arguments;
  var _vm=this;
  return (function () {
    _vm.onConfirmInput.apply(_vm, $args || [$event]);
  })();
}}}, models: {}, refs: undefined }, {info: {"components":{},"on":{}}, handlers: {'84-0': {"tap": function proxy (item) {
    var _vm=this;
  return (function () {
    _vm.onReply(item);
  })();
}},'84-1': {"close": function proxy () {
  var $wx = arguments[arguments.length - 1].$wx;
  var $event = ($wx.detail && $wx.detail.arguments) ? $wx.detail.arguments[0] : arguments[arguments.length -1];
  var $args = $wx.detail && $wx.detail.arguments;
  var _vm=this;
  return (function () {
    _vm.onCloseInput.apply(_vm, $args || [$event]);
  })();
}, "confirm": function proxy () {
  var $wx = arguments[arguments.length - 1].$wx;
  var $event = ($wx.detail && $wx.detail.arguments) ? $wx.detail.arguments[0] : arguments[arguments.length -1];
  var $args = $wx.detail && $wx.detail.arguments;
  var _vm=this;
  return (function () {
    _vm.onConfirmInput.apply(_vm, $args || [$event]);
  })();
}}}, models: {}, refs: undefined }, {info: {"components":{},"on":{}}, handlers: {'84-0': {"tap": function proxy (item) {
    var _vm=this;
  return (function () {
    _vm.onReply(item);
  })();
}},'84-1': {"close": function proxy () {
  var $wx = arguments[arguments.length - 1].$wx;
  var $event = ($wx.detail && $wx.detail.arguments) ? $wx.detail.arguments[0] : arguments[arguments.length -1];
  var $args = $wx.detail && $wx.detail.arguments;
  var _vm=this;
  return (function () {
    _vm.onCloseInput.apply(_vm, $args || [$event]);
  })();
}, "confirm": function proxy () {
  var $wx = arguments[arguments.length - 1].$wx;
  var $event = ($wx.detail && $wx.detail.arguments) ? $wx.detail.arguments[0] : arguments[arguments.length -1];
  var $args = $wx.detail && $wx.detail.arguments;
  var _vm=this;
  return (function () {
    _vm.onConfirmInput.apply(_vm, $args || [$event]);
  })();
}}}, models: {}, refs: undefined }, {info: {"components":{},"on":{}}, handlers: {'84-0': {"tap": function proxy (item) {
    var _vm=this;
  return (function () {
    _vm.onReply(item);
  })();
}},'84-1': {"close": function proxy () {
  var $wx = arguments[arguments.length - 1].$wx;
  var $event = ($wx.detail && $wx.detail.arguments) ? $wx.detail.arguments[0] : arguments[arguments.length -1];
  var $args = $wx.detail && $wx.detail.arguments;
  var _vm=this;
  return (function () {
    _vm.onCloseInput.apply(_vm, $args || [$event]);
  })();
}, "confirm": function proxy () {
  var $wx = arguments[arguments.length - 1].$wx;
  var $event = ($wx.detail && $wx.detail.arguments) ? $wx.detail.arguments[0] : arguments[arguments.length -1];
  var $args = $wx.detail && $wx.detail.arguments;
  var _vm=this;
  return (function () {
    _vm.onConfirmInput.apply(_vm, $args || [$event]);
  })();
}}}, models: {}, refs: undefined }, {info: {"components":{},"on":{}}, handlers: {'84-0': {"tap": function proxy (item) {
    var _vm=this;
  return (function () {
    _vm.onReply(item);
  })();
}},'84-1': {"close": function proxy () {
  var $wx = arguments[arguments.length - 1].$wx;
  var $event = ($wx.detail && $wx.detail.arguments) ? $wx.detail.arguments[0] : arguments[arguments.length -1];
  var $args = $wx.detail && $wx.detail.arguments;
  var _vm=this;
  return (function () {
    _vm.onCloseInput.apply(_vm, $args || [$event]);
  })();
}, "confirm": function proxy () {
  var $wx = arguments[arguments.length - 1].$wx;
  var $event = ($wx.detail && $wx.detail.arguments) ? $wx.detail.arguments[0] : arguments[arguments.length -1];
  var $args = $wx.detail && $wx.detail.arguments;
  var _vm=this;
  return (function () {
    _vm.onConfirmInput.apply(_vm, $args || [$event]);
  })();
}}}, models: {}, refs: undefined }, {info: {"components":{},"on":{}}, handlers: {'84-0': {"tap": function proxy (item) {
    var _vm=this;
  return (function () {
    _vm.onReply(item);
  })();
}},'84-1': {"close": function proxy () {
  var $wx = arguments[arguments.length - 1].$wx;
  var $event = ($wx.detail && $wx.detail.arguments) ? $wx.detail.arguments[0] : arguments[arguments.length -1];
  var $args = $wx.detail && $wx.detail.arguments;
  var _vm=this;
  return (function () {
    _vm.onCloseInput.apply(_vm, $args || [$event]);
  })();
}, "confirm": function proxy () {
  var $wx = arguments[arguments.length - 1].$wx;
  var $event = ($wx.detail && $wx.detail.arguments) ? $wx.detail.arguments[0] : arguments[arguments.length -1];
  var $args = $wx.detail && $wx.detail.arguments;
  var _vm=this;
  return (function () {
    _vm.onConfirmInput.apply(_vm, $args || [$event]);
  })();
}}}, models: {}, refs: undefined }, {info: {"components":{},"on":{}}, handlers: {'84-0': {"tap": function proxy (item) {
    var _vm=this;
  return (function () {
    _vm.onReply(item);
  })();
}},'84-1': {"close": function proxy () {
  var $wx = arguments[arguments.length - 1].$wx;
  var $event = ($wx.detail && $wx.detail.arguments) ? $wx.detail.arguments[0] : arguments[arguments.length -1];
  var $args = $wx.detail && $wx.detail.arguments;
  var _vm=this;
  return (function () {
    _vm.onCloseInput.apply(_vm, $args || [$event]);
  })();
}, "confirm": function proxy () {
  var $wx = arguments[arguments.length - 1].$wx;
  var $event = ($wx.detail && $wx.detail.arguments) ? $wx.detail.arguments[0] : arguments[arguments.length -1];
  var $args = $wx.detail && $wx.detail.arguments;
  var _vm=this;
  return (function () {
    _vm.onConfirmInput.apply(_vm, $args || [$event]);
  })();
}}}, models: {}, refs: undefined }, {info: {"components":{},"on":{}}, handlers: {'84-0': {"tap": function proxy (item) {
    var _vm=this;
  return (function () {
    _vm.onReply(item);
  })();
}},'84-1': {"close": function proxy () {
  var $wx = arguments[arguments.length - 1].$wx;
  var $event = ($wx.detail && $wx.detail.arguments) ? $wx.detail.arguments[0] : arguments[arguments.length -1];
  var $args = $wx.detail && $wx.detail.arguments;
  var _vm=this;
  return (function () {
    _vm.onCloseInput.apply(_vm, $args || [$event]);
  })();
}, "confirm": function proxy () {
  var $wx = arguments[arguments.length - 1].$wx;
  var $event = ($wx.detail && $wx.detail.arguments) ? $wx.detail.arguments[0] : arguments[arguments.length -1];
  var $args = $wx.detail && $wx.detail.arguments;
  var _vm=this;
  return (function () {
    _vm.onConfirmInput.apply(_vm, $args || [$event]);
  })();
}}}, models: {}, refs: undefined }, {info: {"components":{},"on":{}}, handlers: {'84-0': {"tap": function proxy (item) {
    var _vm=this;
  return (function () {
    _vm.onReply(item);
  })();
}},'84-1': {"close": function proxy () {
  var $wx = arguments[arguments.length - 1].$wx;
  var $event = ($wx.detail && $wx.detail.arguments) ? $wx.detail.arguments[0] : arguments[arguments.length -1];
  var $args = $wx.detail && $wx.detail.arguments;
  var _vm=this;
  return (function () {
    _vm.onCloseInput.apply(_vm, $args || [$event]);
  })();
}, "confirm": function proxy () {
  var $wx = arguments[arguments.length - 1].$wx;
  var $event = ($wx.detail && $wx.detail.arguments) ? $wx.detail.arguments[0] : arguments[arguments.length -1];
  var $args = $wx.detail && $wx.detail.arguments;
  var _vm=this;
  return (function () {
    _vm.onConfirmInput.apply(_vm, $args || [$event]);
  })();
}}}, models: {}, refs: undefined });