"use strict";

var _core = _interopRequireDefault(require('./../../vendor.js')(0));

var _store = _interopRequireDefault(require('./../../store/index.js'));

var _x = require('./../../vendor.js')(2);

function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { "default": obj }; }

function ownKeys(object, enumerableOnly) { var keys = Object.keys(object); if (Object.getOwnPropertySymbols) { var symbols = Object.getOwnPropertySymbols(object); if (enumerableOnly) symbols = symbols.filter(function (sym) { return Object.getOwnPropertyDescriptor(object, sym).enumerable; }); keys.push.apply(keys, symbols); } return keys; }

function _objectSpread(target) { for (var i = 1; i < arguments.length; i++) { var source = arguments[i] != null ? arguments[i] : {}; if (i % 2) { ownKeys(Object(source), true).forEach(function (key) { _defineProperty(target, key, source[key]); }); } else if (Object.getOwnPropertyDescriptors) { Object.defineProperties(target, Object.getOwnPropertyDescriptors(source)); } else { ownKeys(Object(source)).forEach(function (key) { Object.defineProperty(target, key, Object.getOwnPropertyDescriptor(source, key)); }); } } return target; }

function _defineProperty(obj, key, value) { if (key in obj) { Object.defineProperty(obj, key, { value: value, enumerable: true, configurable: true, writable: true }); } else { obj[key] = value; } return obj; }

_core["default"].page({
  store: _store["default"],
  hooks: {},
  data: {
    goodsImages: ['https://ss3.bdstatic.com/70cFv8Sh_Q1YnxGkpoWK1HF6hhy/it/u=751441608,3469454349&fm=15&gp=0.jpg', 'https://ss3.bdstatic.com/70cFv8Sh_Q1YnxGkpoWK1HF6hhy/it/u=751441608,3469454349&fm=15&gp=0.jpg', 'https://ss3.bdstatic.com/70cFv8Sh_Q1YnxGkpoWK1HF6hhy/it/u=751441608,3469454349&fm=15&gp=0.jpg'],
    detail: '<p style=\'color: #AC1630\'>富文本详情</p>',
    isShowSpec: false,
    count: 1
  },
  computed: {},
  methods: _objectSpread(_objectSpread({}, (0, _x.mapActions)(['setUserAction', 'setFormAction'])), {}, {
    handleViewTap: function handleViewTap() {
      console.log('handleVieTap clicked');
    },
    tap: function tap() {},
    onShowSpec: function onShowSpec() {
      this.isShowSpec = true;
    },
    onCloseSpec: function onCloseSpec() {
      this.isShowSpec = false;
    },
    onChangeCount: function onChangeCount(e) {
      console.log(e);
    },
    callAppLaunch: function callAppLaunch() {}
  }),
  ready: function ready() {}
}, {info: {"components":{"van-goods-action":{"path":"./../../vant/goods-action/index"},"van-goods-action-icon":{"path":"./../../vant/goods-action-icon/index"},"van-goods-action-button":{"path":"./../../vant/goods-action-button/index"}},"on":{"19-3":["tap"],"19-4":["tap"]}}, handlers: {'19-0': {"tap": function proxy () {
  var $wx = arguments[arguments.length - 1].$wx;
  var $event = ($wx.detail && $wx.detail.arguments) ? $wx.detail.arguments[0] : arguments[arguments.length -1];
  var $args = $wx.detail && $wx.detail.arguments;
  var _vm=this;
  return (function () {
    _vm.onShowSpec.apply(_vm, $args || [$event]);
  })();
}},'19-1': {"close": function proxy () {
  var $wx = arguments[arguments.length - 1].$wx;
  var $event = ($wx.detail && $wx.detail.arguments) ? $wx.detail.arguments[0] : arguments[arguments.length -1];
  var $args = $wx.detail && $wx.detail.arguments;
  var _vm=this;
  return (function () {
    _vm.onCloseSpec.apply(_vm, $args || [$event]);
  })();
}},'19-2': {"change": function proxy () {
  var $wx = arguments[arguments.length - 1].$wx;
  var $event = ($wx.detail && $wx.detail.arguments) ? $wx.detail.arguments[0] : arguments[arguments.length -1];
  var $args = $wx.detail && $wx.detail.arguments;
  var _vm=this;
  return (function () {
    _vm.onChangeCount.apply(_vm, $args || [$event]);
  })();
}},'19-3': {"tap": function proxy () {
  var $wx = arguments[arguments.length - 1].$wx;
  var $event = ($wx.detail && $wx.detail.arguments) ? $wx.detail.arguments[0] : arguments[arguments.length -1];
  var $args = $wx.detail && $wx.detail.arguments;
  var _vm=this;
  return (function () {
    _vm.onShowSpec.apply(_vm, $args || [$event]);
  })();
}},'19-4': {"tap": function proxy () {
  var $wx = arguments[arguments.length - 1].$wx;
  var $event = ($wx.detail && $wx.detail.arguments) ? $wx.detail.arguments[0] : arguments[arguments.length -1];
  var $args = $wx.detail && $wx.detail.arguments;
  var _vm=this;
  return (function () {
    _vm.onShowSpec.apply(_vm, $args || [$event]);
  })();
}}}, models: {}, refs: undefined }, {info: {"components":{"van-goods-action":{"path":"./../../vant/goods-action/index"},"van-goods-action-icon":{"path":"./../../vant/goods-action-icon/index"},"van-goods-action-button":{"path":"./../../vant/goods-action-button/index"}},"on":{"19-3":["tap"],"19-4":["tap"]}}, handlers: {'19-0': {"tap": function proxy () {
  var $wx = arguments[arguments.length - 1].$wx;
  var $event = ($wx.detail && $wx.detail.arguments) ? $wx.detail.arguments[0] : arguments[arguments.length -1];
  var $args = $wx.detail && $wx.detail.arguments;
  var _vm=this;
  return (function () {
    _vm.onShowSpec.apply(_vm, $args || [$event]);
  })();
}},'19-1': {"close": function proxy () {
  var $wx = arguments[arguments.length - 1].$wx;
  var $event = ($wx.detail && $wx.detail.arguments) ? $wx.detail.arguments[0] : arguments[arguments.length -1];
  var $args = $wx.detail && $wx.detail.arguments;
  var _vm=this;
  return (function () {
    _vm.onCloseSpec.apply(_vm, $args || [$event]);
  })();
}},'19-2': {"change": function proxy () {
  var $wx = arguments[arguments.length - 1].$wx;
  var $event = ($wx.detail && $wx.detail.arguments) ? $wx.detail.arguments[0] : arguments[arguments.length -1];
  var $args = $wx.detail && $wx.detail.arguments;
  var _vm=this;
  return (function () {
    _vm.onChangeCount.apply(_vm, $args || [$event]);
  })();
}},'19-3': {"tap": function proxy () {
  var $wx = arguments[arguments.length - 1].$wx;
  var $event = ($wx.detail && $wx.detail.arguments) ? $wx.detail.arguments[0] : arguments[arguments.length -1];
  var $args = $wx.detail && $wx.detail.arguments;
  var _vm=this;
  return (function () {
    _vm.onShowSpec.apply(_vm, $args || [$event]);
  })();
}},'19-4': {"tap": function proxy () {
  var $wx = arguments[arguments.length - 1].$wx;
  var $event = ($wx.detail && $wx.detail.arguments) ? $wx.detail.arguments[0] : arguments[arguments.length -1];
  var $args = $wx.detail && $wx.detail.arguments;
  var _vm=this;
  return (function () {
    _vm.onShowSpec.apply(_vm, $args || [$event]);
  })();
}}}, models: {}, refs: undefined }, {info: {"components":{"van-goods-action":{"path":"./../../vant/goods-action/index"},"van-goods-action-icon":{"path":"./../../vant/goods-action-icon/index"},"van-goods-action-button":{"path":"./../../vant/goods-action-button/index"}},"on":{"19-3":["tap"],"19-4":["tap"]}}, handlers: {'19-0': {"tap": function proxy () {
  var $wx = arguments[arguments.length - 1].$wx;
  var $event = ($wx.detail && $wx.detail.arguments) ? $wx.detail.arguments[0] : arguments[arguments.length -1];
  var $args = $wx.detail && $wx.detail.arguments;
  var _vm=this;
  return (function () {
    _vm.onShowSpec.apply(_vm, $args || [$event]);
  })();
}},'19-1': {"close": function proxy () {
  var $wx = arguments[arguments.length - 1].$wx;
  var $event = ($wx.detail && $wx.detail.arguments) ? $wx.detail.arguments[0] : arguments[arguments.length -1];
  var $args = $wx.detail && $wx.detail.arguments;
  var _vm=this;
  return (function () {
    _vm.onCloseSpec.apply(_vm, $args || [$event]);
  })();
}},'19-2': {"change": function proxy () {
  var $wx = arguments[arguments.length - 1].$wx;
  var $event = ($wx.detail && $wx.detail.arguments) ? $wx.detail.arguments[0] : arguments[arguments.length -1];
  var $args = $wx.detail && $wx.detail.arguments;
  var _vm=this;
  return (function () {
    _vm.onChangeCount.apply(_vm, $args || [$event]);
  })();
}},'19-3': {"tap": function proxy () {
  var $wx = arguments[arguments.length - 1].$wx;
  var $event = ($wx.detail && $wx.detail.arguments) ? $wx.detail.arguments[0] : arguments[arguments.length -1];
  var $args = $wx.detail && $wx.detail.arguments;
  var _vm=this;
  return (function () {
    _vm.onShowSpec.apply(_vm, $args || [$event]);
  })();
}},'19-4': {"tap": function proxy () {
  var $wx = arguments[arguments.length - 1].$wx;
  var $event = ($wx.detail && $wx.detail.arguments) ? $wx.detail.arguments[0] : arguments[arguments.length -1];
  var $args = $wx.detail && $wx.detail.arguments;
  var _vm=this;
  return (function () {
    _vm.onShowSpec.apply(_vm, $args || [$event]);
  })();
}}}, models: {}, refs: undefined }, {info: {"components":{"van-goods-action":{"path":"./../../vant/goods-action/index"},"van-goods-action-icon":{"path":"./../../vant/goods-action-icon/index"},"van-goods-action-button":{"path":"./../../vant/goods-action-button/index"}},"on":{"19-3":["tap"],"19-4":["tap"]}}, handlers: {'19-0': {"tap": function proxy () {
  var $wx = arguments[arguments.length - 1].$wx;
  var $event = ($wx.detail && $wx.detail.arguments) ? $wx.detail.arguments[0] : arguments[arguments.length -1];
  var $args = $wx.detail && $wx.detail.arguments;
  var _vm=this;
  return (function () {
    _vm.onShowSpec.apply(_vm, $args || [$event]);
  })();
}},'19-1': {"close": function proxy () {
  var $wx = arguments[arguments.length - 1].$wx;
  var $event = ($wx.detail && $wx.detail.arguments) ? $wx.detail.arguments[0] : arguments[arguments.length -1];
  var $args = $wx.detail && $wx.detail.arguments;
  var _vm=this;
  return (function () {
    _vm.onCloseSpec.apply(_vm, $args || [$event]);
  })();
}},'19-2': {"change": function proxy () {
  var $wx = arguments[arguments.length - 1].$wx;
  var $event = ($wx.detail && $wx.detail.arguments) ? $wx.detail.arguments[0] : arguments[arguments.length -1];
  var $args = $wx.detail && $wx.detail.arguments;
  var _vm=this;
  return (function () {
    _vm.onChangeCount.apply(_vm, $args || [$event]);
  })();
}},'19-3': {"tap": function proxy () {
  var $wx = arguments[arguments.length - 1].$wx;
  var $event = ($wx.detail && $wx.detail.arguments) ? $wx.detail.arguments[0] : arguments[arguments.length -1];
  var $args = $wx.detail && $wx.detail.arguments;
  var _vm=this;
  return (function () {
    _vm.onShowSpec.apply(_vm, $args || [$event]);
  })();
}},'19-4': {"tap": function proxy () {
  var $wx = arguments[arguments.length - 1].$wx;
  var $event = ($wx.detail && $wx.detail.arguments) ? $wx.detail.arguments[0] : arguments[arguments.length -1];
  var $args = $wx.detail && $wx.detail.arguments;
  var _vm=this;
  return (function () {
    _vm.onShowSpec.apply(_vm, $args || [$event]);
  })();
}}}, models: {}, refs: undefined }, {info: {"components":{"van-goods-action":{"path":"./../../vant/goods-action/index"},"van-goods-action-icon":{"path":"./../../vant/goods-action-icon/index"},"van-goods-action-button":{"path":"./../../vant/goods-action-button/index"}},"on":{"19-3":["tap"],"19-4":["tap"]}}, handlers: {'19-0': {"tap": function proxy () {
  var $wx = arguments[arguments.length - 1].$wx;
  var $event = ($wx.detail && $wx.detail.arguments) ? $wx.detail.arguments[0] : arguments[arguments.length -1];
  var $args = $wx.detail && $wx.detail.arguments;
  var _vm=this;
  return (function () {
    _vm.onShowSpec.apply(_vm, $args || [$event]);
  })();
}},'19-1': {"close": function proxy () {
  var $wx = arguments[arguments.length - 1].$wx;
  var $event = ($wx.detail && $wx.detail.arguments) ? $wx.detail.arguments[0] : arguments[arguments.length -1];
  var $args = $wx.detail && $wx.detail.arguments;
  var _vm=this;
  return (function () {
    _vm.onCloseSpec.apply(_vm, $args || [$event]);
  })();
}},'19-2': {"change": function proxy () {
  var $wx = arguments[arguments.length - 1].$wx;
  var $event = ($wx.detail && $wx.detail.arguments) ? $wx.detail.arguments[0] : arguments[arguments.length -1];
  var $args = $wx.detail && $wx.detail.arguments;
  var _vm=this;
  return (function () {
    _vm.onChangeCount.apply(_vm, $args || [$event]);
  })();
}},'19-3': {"tap": function proxy () {
  var $wx = arguments[arguments.length - 1].$wx;
  var $event = ($wx.detail && $wx.detail.arguments) ? $wx.detail.arguments[0] : arguments[arguments.length -1];
  var $args = $wx.detail && $wx.detail.arguments;
  var _vm=this;
  return (function () {
    _vm.onShowSpec.apply(_vm, $args || [$event]);
  })();
}},'19-4': {"tap": function proxy () {
  var $wx = arguments[arguments.length - 1].$wx;
  var $event = ($wx.detail && $wx.detail.arguments) ? $wx.detail.arguments[0] : arguments[arguments.length -1];
  var $args = $wx.detail && $wx.detail.arguments;
  var _vm=this;
  return (function () {
    _vm.onShowSpec.apply(_vm, $args || [$event]);
  })();
}}}, models: {}, refs: undefined }, {info: {"components":{"van-goods-action":{"path":"./../../vant/goods-action/index"},"van-goods-action-icon":{"path":"./../../vant/goods-action-icon/index"},"van-goods-action-button":{"path":"./../../vant/goods-action-button/index"}},"on":{"19-3":["tap"],"19-4":["tap"]}}, handlers: {'19-0': {"tap": function proxy () {
  var $wx = arguments[arguments.length - 1].$wx;
  var $event = ($wx.detail && $wx.detail.arguments) ? $wx.detail.arguments[0] : arguments[arguments.length -1];
  var $args = $wx.detail && $wx.detail.arguments;
  var _vm=this;
  return (function () {
    _vm.onShowSpec.apply(_vm, $args || [$event]);
  })();
}},'19-1': {"close": function proxy () {
  var $wx = arguments[arguments.length - 1].$wx;
  var $event = ($wx.detail && $wx.detail.arguments) ? $wx.detail.arguments[0] : arguments[arguments.length -1];
  var $args = $wx.detail && $wx.detail.arguments;
  var _vm=this;
  return (function () {
    _vm.onCloseSpec.apply(_vm, $args || [$event]);
  })();
}},'19-2': {"change": function proxy () {
  var $wx = arguments[arguments.length - 1].$wx;
  var $event = ($wx.detail && $wx.detail.arguments) ? $wx.detail.arguments[0] : arguments[arguments.length -1];
  var $args = $wx.detail && $wx.detail.arguments;
  var _vm=this;
  return (function () {
    _vm.onChangeCount.apply(_vm, $args || [$event]);
  })();
}},'19-3': {"tap": function proxy () {
  var $wx = arguments[arguments.length - 1].$wx;
  var $event = ($wx.detail && $wx.detail.arguments) ? $wx.detail.arguments[0] : arguments[arguments.length -1];
  var $args = $wx.detail && $wx.detail.arguments;
  var _vm=this;
  return (function () {
    _vm.onShowSpec.apply(_vm, $args || [$event]);
  })();
}},'19-4': {"tap": function proxy () {
  var $wx = arguments[arguments.length - 1].$wx;
  var $event = ($wx.detail && $wx.detail.arguments) ? $wx.detail.arguments[0] : arguments[arguments.length -1];
  var $args = $wx.detail && $wx.detail.arguments;
  var _vm=this;
  return (function () {
    _vm.onShowSpec.apply(_vm, $args || [$event]);
  })();
}}}, models: {}, refs: undefined }, {info: {"components":{"van-goods-action":{"path":"./../../vant/goods-action/index"},"van-goods-action-icon":{"path":"./../../vant/goods-action-icon/index"},"van-goods-action-button":{"path":"./../../vant/goods-action-button/index"}},"on":{"19-3":["tap"],"19-4":["tap"]}}, handlers: {'19-0': {"tap": function proxy () {
  var $wx = arguments[arguments.length - 1].$wx;
  var $event = ($wx.detail && $wx.detail.arguments) ? $wx.detail.arguments[0] : arguments[arguments.length -1];
  var $args = $wx.detail && $wx.detail.arguments;
  var _vm=this;
  return (function () {
    _vm.onShowSpec.apply(_vm, $args || [$event]);
  })();
}},'19-1': {"close": function proxy () {
  var $wx = arguments[arguments.length - 1].$wx;
  var $event = ($wx.detail && $wx.detail.arguments) ? $wx.detail.arguments[0] : arguments[arguments.length -1];
  var $args = $wx.detail && $wx.detail.arguments;
  var _vm=this;
  return (function () {
    _vm.onCloseSpec.apply(_vm, $args || [$event]);
  })();
}},'19-2': {"change": function proxy () {
  var $wx = arguments[arguments.length - 1].$wx;
  var $event = ($wx.detail && $wx.detail.arguments) ? $wx.detail.arguments[0] : arguments[arguments.length -1];
  var $args = $wx.detail && $wx.detail.arguments;
  var _vm=this;
  return (function () {
    _vm.onChangeCount.apply(_vm, $args || [$event]);
  })();
}},'19-3': {"tap": function proxy () {
  var $wx = arguments[arguments.length - 1].$wx;
  var $event = ($wx.detail && $wx.detail.arguments) ? $wx.detail.arguments[0] : arguments[arguments.length -1];
  var $args = $wx.detail && $wx.detail.arguments;
  var _vm=this;
  return (function () {
    _vm.onShowSpec.apply(_vm, $args || [$event]);
  })();
}},'19-4': {"tap": function proxy () {
  var $wx = arguments[arguments.length - 1].$wx;
  var $event = ($wx.detail && $wx.detail.arguments) ? $wx.detail.arguments[0] : arguments[arguments.length -1];
  var $args = $wx.detail && $wx.detail.arguments;
  var _vm=this;
  return (function () {
    _vm.onShowSpec.apply(_vm, $args || [$event]);
  })();
}}}, models: {}, refs: undefined });