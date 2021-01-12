"use strict";

var _core = _interopRequireDefault(require('./../../vendor.js')(0));

var _store = _interopRequireDefault(require('./../../store/index.js'));

var _x = require('./../../vendor.js')(2);

var _xiao4rBase = _interopRequireDefault(require('./../../xiao4rBase.js'));

function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { "default": obj }; }

_core["default"].component({
  store: _store["default"],
  hooks: {},
  data: {
    active: 0,
    list: [1, 2, 3, 4]
  },
  computed: {},
  methods: {
    onItem: function onItem(item) {
      console.log(item);

      _xiao4rBase["default"].navigateTo("goods-detail?id=".concat(item));
    }
  },
  ready: function ready() {}
}, {info: {"components":{},"on":{}}, handlers: {'83-0': {"tap": function proxy (item) {
    var _vm=this;
  return (function () {
    _vm.onItem(item);
  })();
}}}, models: {}, refs: undefined }, {info: {"components":{},"on":{}}, handlers: {'83-0': {"tap": function proxy (item) {
    var _vm=this;
  return (function () {
    _vm.onItem(item);
  })();
}}}, models: {}, refs: undefined }, {info: {"components":{},"on":{}}, handlers: {'83-0': {"tap": function proxy (item) {
    var _vm=this;
  return (function () {
    _vm.onItem(item);
  })();
}}}, models: {}, refs: undefined }, {info: {"components":{},"on":{}}, handlers: {'83-0': {"tap": function proxy (item) {
    var _vm=this;
  return (function () {
    _vm.onItem(item);
  })();
}}}, models: {}, refs: undefined }, {info: {"components":{},"on":{}}, handlers: {'83-0': {"tap": function proxy (item) {
    var _vm=this;
  return (function () {
    _vm.onItem(item);
  })();
}}}, models: {}, refs: undefined }, {info: {"components":{},"on":{}}, handlers: {'83-0': {"tap": function proxy (item) {
    var _vm=this;
  return (function () {
    _vm.onItem(item);
  })();
}}}, models: {}, refs: undefined }, {info: {"components":{},"on":{}}, handlers: {'83-0': {"tap": function proxy (item) {
    var _vm=this;
  return (function () {
    _vm.onItem(item);
  })();
}}}, models: {}, refs: undefined }, {info: {"components":{},"on":{}}, handlers: {'83-0': {"tap": function proxy (item) {
    var _vm=this;
  return (function () {
    _vm.onItem(item);
  })();
}}}, models: {}, refs: undefined }, {info: {"components":{},"on":{}}, handlers: {'83-0': {"tap": function proxy (item) {
    var _vm=this;
  return (function () {
    _vm.onItem(item);
  })();
}}}, models: {}, refs: undefined });