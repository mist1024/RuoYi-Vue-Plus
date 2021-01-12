"use strict";

var _core = _interopRequireDefault(require('./../../vendor.js')(0));

var _store = _interopRequireDefault(require('./../../store/index.js'));

var _x = require('./../../vendor.js')(2);

function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { "default": obj }; }

_core["default"].component({
  store: _store["default"],
  hooks: {},
  data: {
    active: 0,
    checked: false,
    list: [],
    selectedList: [],
    isAllSelect: false,
    price: 10000
  },
  computed: {},
  methods: {
    onChangeSelect: function onChangeSelect(e) {
      this.selectedList = e.$wx.detail;
      this.isAllSelect = this.selectedList.length === this.list.length;
    },
    onAllSelect: function onAllSelect(e) {
      this.isAllSelect = e.$wx.detail;

      if (this.isAllSelect) {
        this.selectedList = this.list.map(function (x) {
          return x.id;
        });
      } else {
        this.selectedList = [];
      }
    },
    onSubmit: function onSubmit() {// 订单提交
    }
  },
  ready: function ready() {
    for (var i = 0; i < 15; i++) {
      this.list.push({
        id: i + '',
        name: '商品',
        price: 599
      });
    }
  }
}, {info: {"components":{},"on":{}}, handlers: {'85-0': {"change": function proxy () {
  var $wx = arguments[arguments.length - 1].$wx;
  var $event = ($wx.detail && $wx.detail.arguments) ? $wx.detail.arguments[0] : arguments[arguments.length -1];
  var $args = $wx.detail && $wx.detail.arguments;
  var _vm=this;
  return (function () {
    _vm.onChangeSelect.apply(_vm, $args || [$event]);
  })();
}},'85-1': {"submit": function proxy () {
  var $wx = arguments[arguments.length - 1].$wx;
  var $event = ($wx.detail && $wx.detail.arguments) ? $wx.detail.arguments[0] : arguments[arguments.length -1];
  var $args = $wx.detail && $wx.detail.arguments;
  var _vm=this;
  return (function () {
    _vm.onSubmit.apply(_vm, $args || [$event]);
  })();
}},'85-2': {"change": function proxy () {
  var $wx = arguments[arguments.length - 1].$wx;
  var $event = ($wx.detail && $wx.detail.arguments) ? $wx.detail.arguments[0] : arguments[arguments.length -1];
  var $args = $wx.detail && $wx.detail.arguments;
  var _vm=this;
  return (function () {
    _vm.onAllSelect.apply(_vm, $args || [$event]);
  })();
}}}, models: {}, refs: undefined }, {info: {"components":{},"on":{}}, handlers: {'85-0': {"change": function proxy () {
  var $wx = arguments[arguments.length - 1].$wx;
  var $event = ($wx.detail && $wx.detail.arguments) ? $wx.detail.arguments[0] : arguments[arguments.length -1];
  var $args = $wx.detail && $wx.detail.arguments;
  var _vm=this;
  return (function () {
    _vm.onChangeSelect.apply(_vm, $args || [$event]);
  })();
}},'85-1': {"submit": function proxy () {
  var $wx = arguments[arguments.length - 1].$wx;
  var $event = ($wx.detail && $wx.detail.arguments) ? $wx.detail.arguments[0] : arguments[arguments.length -1];
  var $args = $wx.detail && $wx.detail.arguments;
  var _vm=this;
  return (function () {
    _vm.onSubmit.apply(_vm, $args || [$event]);
  })();
}},'85-2': {"change": function proxy () {
  var $wx = arguments[arguments.length - 1].$wx;
  var $event = ($wx.detail && $wx.detail.arguments) ? $wx.detail.arguments[0] : arguments[arguments.length -1];
  var $args = $wx.detail && $wx.detail.arguments;
  var _vm=this;
  return (function () {
    _vm.onAllSelect.apply(_vm, $args || [$event]);
  })();
}}}, models: {}, refs: undefined }, {info: {"components":{},"on":{}}, handlers: {'85-0': {"change": function proxy () {
  var $wx = arguments[arguments.length - 1].$wx;
  var $event = ($wx.detail && $wx.detail.arguments) ? $wx.detail.arguments[0] : arguments[arguments.length -1];
  var $args = $wx.detail && $wx.detail.arguments;
  var _vm=this;
  return (function () {
    _vm.onChangeSelect.apply(_vm, $args || [$event]);
  })();
}},'85-1': {"submit": function proxy () {
  var $wx = arguments[arguments.length - 1].$wx;
  var $event = ($wx.detail && $wx.detail.arguments) ? $wx.detail.arguments[0] : arguments[arguments.length -1];
  var $args = $wx.detail && $wx.detail.arguments;
  var _vm=this;
  return (function () {
    _vm.onSubmit.apply(_vm, $args || [$event]);
  })();
}},'85-2': {"change": function proxy () {
  var $wx = arguments[arguments.length - 1].$wx;
  var $event = ($wx.detail && $wx.detail.arguments) ? $wx.detail.arguments[0] : arguments[arguments.length -1];
  var $args = $wx.detail && $wx.detail.arguments;
  var _vm=this;
  return (function () {
    _vm.onAllSelect.apply(_vm, $args || [$event]);
  })();
}}}, models: {}, refs: undefined }, {info: {"components":{},"on":{}}, handlers: {'85-0': {"change": function proxy () {
  var $wx = arguments[arguments.length - 1].$wx;
  var $event = ($wx.detail && $wx.detail.arguments) ? $wx.detail.arguments[0] : arguments[arguments.length -1];
  var $args = $wx.detail && $wx.detail.arguments;
  var _vm=this;
  return (function () {
    _vm.onChangeSelect.apply(_vm, $args || [$event]);
  })();
}},'85-1': {"submit": function proxy () {
  var $wx = arguments[arguments.length - 1].$wx;
  var $event = ($wx.detail && $wx.detail.arguments) ? $wx.detail.arguments[0] : arguments[arguments.length -1];
  var $args = $wx.detail && $wx.detail.arguments;
  var _vm=this;
  return (function () {
    _vm.onSubmit.apply(_vm, $args || [$event]);
  })();
}},'85-2': {"change": function proxy () {
  var $wx = arguments[arguments.length - 1].$wx;
  var $event = ($wx.detail && $wx.detail.arguments) ? $wx.detail.arguments[0] : arguments[arguments.length -1];
  var $args = $wx.detail && $wx.detail.arguments;
  var _vm=this;
  return (function () {
    _vm.onAllSelect.apply(_vm, $args || [$event]);
  })();
}}}, models: {}, refs: undefined }, {info: {"components":{},"on":{}}, handlers: {'85-0': {"change": function proxy () {
  var $wx = arguments[arguments.length - 1].$wx;
  var $event = ($wx.detail && $wx.detail.arguments) ? $wx.detail.arguments[0] : arguments[arguments.length -1];
  var $args = $wx.detail && $wx.detail.arguments;
  var _vm=this;
  return (function () {
    _vm.onChangeSelect.apply(_vm, $args || [$event]);
  })();
}},'85-1': {"submit": function proxy () {
  var $wx = arguments[arguments.length - 1].$wx;
  var $event = ($wx.detail && $wx.detail.arguments) ? $wx.detail.arguments[0] : arguments[arguments.length -1];
  var $args = $wx.detail && $wx.detail.arguments;
  var _vm=this;
  return (function () {
    _vm.onSubmit.apply(_vm, $args || [$event]);
  })();
}},'85-2': {"change": function proxy () {
  var $wx = arguments[arguments.length - 1].$wx;
  var $event = ($wx.detail && $wx.detail.arguments) ? $wx.detail.arguments[0] : arguments[arguments.length -1];
  var $args = $wx.detail && $wx.detail.arguments;
  var _vm=this;
  return (function () {
    _vm.onAllSelect.apply(_vm, $args || [$event]);
  })();
}}}, models: {}, refs: undefined }, {info: {"components":{},"on":{}}, handlers: {'85-0': {"change": function proxy () {
  var $wx = arguments[arguments.length - 1].$wx;
  var $event = ($wx.detail && $wx.detail.arguments) ? $wx.detail.arguments[0] : arguments[arguments.length -1];
  var $args = $wx.detail && $wx.detail.arguments;
  var _vm=this;
  return (function () {
    _vm.onChangeSelect.apply(_vm, $args || [$event]);
  })();
}},'85-1': {"submit": function proxy () {
  var $wx = arguments[arguments.length - 1].$wx;
  var $event = ($wx.detail && $wx.detail.arguments) ? $wx.detail.arguments[0] : arguments[arguments.length -1];
  var $args = $wx.detail && $wx.detail.arguments;
  var _vm=this;
  return (function () {
    _vm.onSubmit.apply(_vm, $args || [$event]);
  })();
}},'85-2': {"change": function proxy () {
  var $wx = arguments[arguments.length - 1].$wx;
  var $event = ($wx.detail && $wx.detail.arguments) ? $wx.detail.arguments[0] : arguments[arguments.length -1];
  var $args = $wx.detail && $wx.detail.arguments;
  var _vm=this;
  return (function () {
    _vm.onAllSelect.apply(_vm, $args || [$event]);
  })();
}}}, models: {}, refs: undefined }, {info: {"components":{},"on":{}}, handlers: {'85-0': {"change": function proxy () {
  var $wx = arguments[arguments.length - 1].$wx;
  var $event = ($wx.detail && $wx.detail.arguments) ? $wx.detail.arguments[0] : arguments[arguments.length -1];
  var $args = $wx.detail && $wx.detail.arguments;
  var _vm=this;
  return (function () {
    _vm.onChangeSelect.apply(_vm, $args || [$event]);
  })();
}},'85-1': {"submit": function proxy () {
  var $wx = arguments[arguments.length - 1].$wx;
  var $event = ($wx.detail && $wx.detail.arguments) ? $wx.detail.arguments[0] : arguments[arguments.length -1];
  var $args = $wx.detail && $wx.detail.arguments;
  var _vm=this;
  return (function () {
    _vm.onSubmit.apply(_vm, $args || [$event]);
  })();
}},'85-2': {"change": function proxy () {
  var $wx = arguments[arguments.length - 1].$wx;
  var $event = ($wx.detail && $wx.detail.arguments) ? $wx.detail.arguments[0] : arguments[arguments.length -1];
  var $args = $wx.detail && $wx.detail.arguments;
  var _vm=this;
  return (function () {
    _vm.onAllSelect.apply(_vm, $args || [$event]);
  })();
}}}, models: {}, refs: undefined }, {info: {"components":{},"on":{}}, handlers: {'85-0': {"change": function proxy () {
  var $wx = arguments[arguments.length - 1].$wx;
  var $event = ($wx.detail && $wx.detail.arguments) ? $wx.detail.arguments[0] : arguments[arguments.length -1];
  var $args = $wx.detail && $wx.detail.arguments;
  var _vm=this;
  return (function () {
    _vm.onChangeSelect.apply(_vm, $args || [$event]);
  })();
}},'85-1': {"submit": function proxy () {
  var $wx = arguments[arguments.length - 1].$wx;
  var $event = ($wx.detail && $wx.detail.arguments) ? $wx.detail.arguments[0] : arguments[arguments.length -1];
  var $args = $wx.detail && $wx.detail.arguments;
  var _vm=this;
  return (function () {
    _vm.onSubmit.apply(_vm, $args || [$event]);
  })();
}},'85-2': {"change": function proxy () {
  var $wx = arguments[arguments.length - 1].$wx;
  var $event = ($wx.detail && $wx.detail.arguments) ? $wx.detail.arguments[0] : arguments[arguments.length -1];
  var $args = $wx.detail && $wx.detail.arguments;
  var _vm=this;
  return (function () {
    _vm.onAllSelect.apply(_vm, $args || [$event]);
  })();
}}}, models: {}, refs: undefined }, {info: {"components":{},"on":{}}, handlers: {'85-0': {"change": function proxy () {
  var $wx = arguments[arguments.length - 1].$wx;
  var $event = ($wx.detail && $wx.detail.arguments) ? $wx.detail.arguments[0] : arguments[arguments.length -1];
  var $args = $wx.detail && $wx.detail.arguments;
  var _vm=this;
  return (function () {
    _vm.onChangeSelect.apply(_vm, $args || [$event]);
  })();
}},'85-1': {"submit": function proxy () {
  var $wx = arguments[arguments.length - 1].$wx;
  var $event = ($wx.detail && $wx.detail.arguments) ? $wx.detail.arguments[0] : arguments[arguments.length -1];
  var $args = $wx.detail && $wx.detail.arguments;
  var _vm=this;
  return (function () {
    _vm.onSubmit.apply(_vm, $args || [$event]);
  })();
}},'85-2': {"change": function proxy () {
  var $wx = arguments[arguments.length - 1].$wx;
  var $event = ($wx.detail && $wx.detail.arguments) ? $wx.detail.arguments[0] : arguments[arguments.length -1];
  var $args = $wx.detail && $wx.detail.arguments;
  var _vm=this;
  return (function () {
    _vm.onAllSelect.apply(_vm, $args || [$event]);
  })();
}}}, models: {}, refs: undefined });