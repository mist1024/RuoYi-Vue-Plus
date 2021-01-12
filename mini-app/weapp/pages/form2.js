"use strict";

var _core = _interopRequireDefault(require('./../vendor.js')(0));

var _eventHub = _interopRequireDefault(require('./../common/eventHub.js'));

var _store = _interopRequireDefault(require('./../store/index.js'));

var _x = require('./../vendor.js')(2);

var _xiao4rBase = _interopRequireDefault(require('./../xiao4rBase.js'));

function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { "default": obj }; }

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
  data: {},
  computed: (0, _x.mapState)(['wineryForm']),
  methods: {
    onNext: function onNext() {
      _xiao4rBase["default"].navigateTo('form3');
    }
  },
  created: function created() {}
}, {info: {"components":{},"on":{}}, handlers: {'13-0': {"tap": function proxy () {
    var _vm=this;
  return (function () {
    _vm.onNext();
  })();
}}}, models: {}, refs: undefined }, {info: {"components":{},"on":{}}, handlers: {'13-0': {"tap": function proxy () {
    var _vm=this;
  return (function () {
    _vm.onNext();
  })();
}}}, models: {}, refs: undefined }, {info: {"components":{},"on":{}}, handlers: {'13-0': {"tap": function proxy () {
    var _vm=this;
  return (function () {
    _vm.onNext();
  })();
}}}, models: {}, refs: undefined }, {info: {"components":{},"on":{}}, handlers: {'13-0': {"tap": function proxy () {
    var _vm=this;
  return (function () {
    _vm.onNext();
  })();
}}}, models: {}, refs: undefined }, {info: {"components":{},"on":{}}, handlers: {'13-0': {"tap": function proxy () {
    var _vm=this;
  return (function () {
    _vm.onNext();
  })();
}}}, models: {}, refs: undefined }, {info: {"components":{},"on":{}}, handlers: {'13-0': {"tap": function proxy () {
    var _vm=this;
  return (function () {
    _vm.onNext();
  })();
}}}, models: {}, refs: undefined }, {info: {"components":{},"on":{}}, handlers: {'13-0': {"tap": function proxy () {
    var _vm=this;
  return (function () {
    _vm.onNext();
  })();
}}}, models: {}, refs: undefined });