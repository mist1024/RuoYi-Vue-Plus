"use strict";

var _core = _interopRequireDefault(require('./../vendor.js')(0));

var _eventHub = _interopRequireDefault(require('./../common/eventHub.js'));

var _store = _interopRequireDefault(require('./../store/index.js'));

var _x = require('./../vendor.js')(2);

var _xiao4rBase = _interopRequireDefault(require('./../xiao4rBase.js'));

var _xiao4rApis = _interopRequireDefault(require('./../apis/xiao4rApis.js'));

var _validateUtils = _interopRequireDefault(require('./../js/utils/validateUtils.js'));

function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { "default": obj }; }

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
    mainPriceType: ['100以下', '100～199', '200～299', '300～399', '400～499', '500～599', '600～699', '700～799', '800～899', '900～999', '1000或以上'],
    salesModeType: ['自销', '渠道代理', '包销', '网销'],
    awardType: ['2017柏林葡萄酒大奖赛', '2017年第24届布鲁塞尔葡萄酒大赛', '第六届宁夏贺兰山东麓国际葡萄酒博览会', '2018柏林葡萄酒大奖赛', '2018第25届布鲁塞尔葡萄酒大赛', '2018Decanter（品醇客）世界葡萄酒大赛', '2019柏林葡萄酒大奖赛', '2019Decanter（品醇客）世界葡萄酒大赛', '2019第26届布鲁塞尔葡萄酒大赛', '第八届宁夏贺兰山东麓国际葡萄酒博览会', '2020柏林葡萄酒大奖赛', '2020第27届布鲁塞尔葡萄酒大赛', '第九届宁夏贺兰山东麓国际葡萄酒博览会']
  },
  computed: (0, _x.mapState)(['wineryForm']),
  watch: {},
  methods: {
    onChangeMainPrice: function onChangeMainPrice(e) {
      this.wineryForm.mainPrice = e.$wx.detail;
    },
    onChangeSalesMode: function onChangeSalesMode(e) {
      this.wineryForm.salesMode = e.$wx.detail;
    },
    onChangeAwards: function onChangeAwards(e) {
      console.log(e.$wx.detail);
      this.wineryForm.awards = e.$wx.detail;
    },
    onNext: function onNext() {
      if (!_validateUtils["default"].isIntOrDecimal(this.wineryForm.annualOutput)) {
        _xiao4rBase["default"].showToast('请输入正确的年产量!');

        return;
      }

      if (!_validateUtils["default"].isIntOrDecimal(this.wineryForm.stock)) {
        _xiao4rBase["default"].showToast('请输入正确的当前库存!');

        return;
      }

      if (!this.wineryForm.bucketCount) {
        _xiao4rBase["default"].showToast('请输入橡木桶数量!');

        return;
      }

      if (this.wineryForm.mainPrice.length < 1) {
        _xiao4rBase["default"].showToast('请选择主要产品定价!');

        return;
      }

      if (this.wineryForm.salesMode.length < 1) {
        _xiao4rBase["default"].showToast('请选择销售方式!');

        return;
      }

      _xiao4rBase["default"].navigateTo('form6');
    },
    onBack: function onBack() {
      wx.navigateBack();
    }
  },
  created: function created() {}
}, {info: {"components":{},"on":{}}, handlers: {'16-0': {"change": function proxy () {
  var $wx = arguments[arguments.length - 1].$wx;
  var $event = ($wx.detail && $wx.detail.arguments) ? $wx.detail.arguments[0] : arguments[arguments.length -1];
  var $args = $wx.detail && $wx.detail.arguments;
  var _vm=this;
  return (function () {
    _vm.onChangeMainPrice.apply(_vm, $args || [$event]);
  })();
}},'16-1': {"change": function proxy () {
  var $wx = arguments[arguments.length - 1].$wx;
  var $event = ($wx.detail && $wx.detail.arguments) ? $wx.detail.arguments[0] : arguments[arguments.length -1];
  var $args = $wx.detail && $wx.detail.arguments;
  var _vm=this;
  return (function () {
    _vm.onChangeSalesMode.apply(_vm, $args || [$event]);
  })();
}},'16-2': {"change": function proxy () {
  var $wx = arguments[arguments.length - 1].$wx;
  var $event = ($wx.detail && $wx.detail.arguments) ? $wx.detail.arguments[0] : arguments[arguments.length -1];
  var $args = $wx.detail && $wx.detail.arguments;
  var _vm=this;
  return (function () {
    _vm.onChangeAwards.apply(_vm, $args || [$event]);
  })();
}},'16-3': {"tap": function proxy () {
    var _vm=this;
  return (function () {
    _vm.onBack();
  })();
}},'16-4': {"tap": function proxy () {
    var _vm=this;
  return (function () {
    _vm.onNext();
  })();
}}}, models: {'14': {
      type: "input",
      expr: "wineryForm.annualOutput",
      handler: function set ($v) {
      var _vm=this;
        _vm.$set(_vm.wineryForm, "annualOutput", $v);
      
    }
    },'15': {
      type: "input",
      expr: "wineryForm.stock",
      handler: function set ($v) {
      var _vm=this;
        _vm.$set(_vm.wineryForm, "stock", $v);
      
    }
    },'16': {
      type: "input",
      expr: "wineryForm.bucketCount",
      handler: function set ($v) {
      var _vm=this;
        _vm.$set(_vm.wineryForm, "bucketCount", $v);
      
    }
    },'17': {
      type: "input",
      expr: "wineryForm.awardInformation",
      handler: function set ($v) {
      var _vm=this;
        _vm.$set(_vm.wineryForm, "awardInformation", $v);
      
    }
    }}, refs: undefined }, {info: {"components":{},"on":{}}, handlers: {'16-0': {"change": function proxy () {
  var $wx = arguments[arguments.length - 1].$wx;
  var $event = ($wx.detail && $wx.detail.arguments) ? $wx.detail.arguments[0] : arguments[arguments.length -1];
  var $args = $wx.detail && $wx.detail.arguments;
  var _vm=this;
  return (function () {
    _vm.onChangeMainPrice.apply(_vm, $args || [$event]);
  })();
}},'16-1': {"change": function proxy () {
  var $wx = arguments[arguments.length - 1].$wx;
  var $event = ($wx.detail && $wx.detail.arguments) ? $wx.detail.arguments[0] : arguments[arguments.length -1];
  var $args = $wx.detail && $wx.detail.arguments;
  var _vm=this;
  return (function () {
    _vm.onChangeSalesMode.apply(_vm, $args || [$event]);
  })();
}},'16-2': {"change": function proxy () {
  var $wx = arguments[arguments.length - 1].$wx;
  var $event = ($wx.detail && $wx.detail.arguments) ? $wx.detail.arguments[0] : arguments[arguments.length -1];
  var $args = $wx.detail && $wx.detail.arguments;
  var _vm=this;
  return (function () {
    _vm.onChangeAwards.apply(_vm, $args || [$event]);
  })();
}},'16-3': {"tap": function proxy () {
    var _vm=this;
  return (function () {
    _vm.onBack();
  })();
}},'16-4': {"tap": function proxy () {
    var _vm=this;
  return (function () {
    _vm.onNext();
  })();
}}}, models: {'14': {
      type: "input",
      expr: "wineryForm.annualOutput",
      handler: function set ($v) {
      var _vm=this;
        _vm.$set(_vm.wineryForm, "annualOutput", $v);
      
    }
    },'15': {
      type: "input",
      expr: "wineryForm.stock",
      handler: function set ($v) {
      var _vm=this;
        _vm.$set(_vm.wineryForm, "stock", $v);
      
    }
    },'16': {
      type: "input",
      expr: "wineryForm.bucketCount",
      handler: function set ($v) {
      var _vm=this;
        _vm.$set(_vm.wineryForm, "bucketCount", $v);
      
    }
    },'17': {
      type: "input",
      expr: "wineryForm.awardInformation",
      handler: function set ($v) {
      var _vm=this;
        _vm.$set(_vm.wineryForm, "awardInformation", $v);
      
    }
    }}, refs: undefined }, {info: {"components":{},"on":{}}, handlers: {'16-0': {"change": function proxy () {
  var $wx = arguments[arguments.length - 1].$wx;
  var $event = ($wx.detail && $wx.detail.arguments) ? $wx.detail.arguments[0] : arguments[arguments.length -1];
  var $args = $wx.detail && $wx.detail.arguments;
  var _vm=this;
  return (function () {
    _vm.onChangeMainPrice.apply(_vm, $args || [$event]);
  })();
}},'16-1': {"change": function proxy () {
  var $wx = arguments[arguments.length - 1].$wx;
  var $event = ($wx.detail && $wx.detail.arguments) ? $wx.detail.arguments[0] : arguments[arguments.length -1];
  var $args = $wx.detail && $wx.detail.arguments;
  var _vm=this;
  return (function () {
    _vm.onChangeSalesMode.apply(_vm, $args || [$event]);
  })();
}},'16-2': {"change": function proxy () {
  var $wx = arguments[arguments.length - 1].$wx;
  var $event = ($wx.detail && $wx.detail.arguments) ? $wx.detail.arguments[0] : arguments[arguments.length -1];
  var $args = $wx.detail && $wx.detail.arguments;
  var _vm=this;
  return (function () {
    _vm.onChangeAwards.apply(_vm, $args || [$event]);
  })();
}},'16-3': {"tap": function proxy () {
    var _vm=this;
  return (function () {
    _vm.onBack();
  })();
}},'16-4': {"tap": function proxy () {
    var _vm=this;
  return (function () {
    _vm.onNext();
  })();
}}}, models: {'14': {
      type: "input",
      expr: "wineryForm.annualOutput",
      handler: function set ($v) {
      var _vm=this;
        _vm.$set(_vm.wineryForm, "annualOutput", $v);
      
    }
    },'15': {
      type: "input",
      expr: "wineryForm.stock",
      handler: function set ($v) {
      var _vm=this;
        _vm.$set(_vm.wineryForm, "stock", $v);
      
    }
    },'16': {
      type: "input",
      expr: "wineryForm.bucketCount",
      handler: function set ($v) {
      var _vm=this;
        _vm.$set(_vm.wineryForm, "bucketCount", $v);
      
    }
    },'17': {
      type: "input",
      expr: "wineryForm.awardInformation",
      handler: function set ($v) {
      var _vm=this;
        _vm.$set(_vm.wineryForm, "awardInformation", $v);
      
    }
    }}, refs: undefined }, {info: {"components":{},"on":{}}, handlers: {'16-0': {"change": function proxy () {
  var $wx = arguments[arguments.length - 1].$wx;
  var $event = ($wx.detail && $wx.detail.arguments) ? $wx.detail.arguments[0] : arguments[arguments.length -1];
  var $args = $wx.detail && $wx.detail.arguments;
  var _vm=this;
  return (function () {
    _vm.onChangeMainPrice.apply(_vm, $args || [$event]);
  })();
}},'16-1': {"change": function proxy () {
  var $wx = arguments[arguments.length - 1].$wx;
  var $event = ($wx.detail && $wx.detail.arguments) ? $wx.detail.arguments[0] : arguments[arguments.length -1];
  var $args = $wx.detail && $wx.detail.arguments;
  var _vm=this;
  return (function () {
    _vm.onChangeSalesMode.apply(_vm, $args || [$event]);
  })();
}},'16-2': {"change": function proxy () {
  var $wx = arguments[arguments.length - 1].$wx;
  var $event = ($wx.detail && $wx.detail.arguments) ? $wx.detail.arguments[0] : arguments[arguments.length -1];
  var $args = $wx.detail && $wx.detail.arguments;
  var _vm=this;
  return (function () {
    _vm.onChangeAwards.apply(_vm, $args || [$event]);
  })();
}},'16-3': {"tap": function proxy () {
    var _vm=this;
  return (function () {
    _vm.onBack();
  })();
}},'16-4': {"tap": function proxy () {
    var _vm=this;
  return (function () {
    _vm.onNext();
  })();
}}}, models: {'14': {
      type: "input",
      expr: "wineryForm.annualOutput",
      handler: function set ($v) {
      var _vm=this;
        _vm.$set(_vm.wineryForm, "annualOutput", $v);
      
    }
    },'15': {
      type: "input",
      expr: "wineryForm.stock",
      handler: function set ($v) {
      var _vm=this;
        _vm.$set(_vm.wineryForm, "stock", $v);
      
    }
    },'16': {
      type: "input",
      expr: "wineryForm.bucketCount",
      handler: function set ($v) {
      var _vm=this;
        _vm.$set(_vm.wineryForm, "bucketCount", $v);
      
    }
    },'17': {
      type: "input",
      expr: "wineryForm.awardInformation",
      handler: function set ($v) {
      var _vm=this;
        _vm.$set(_vm.wineryForm, "awardInformation", $v);
      
    }
    }}, refs: undefined }, {info: {"components":{},"on":{}}, handlers: {'16-0': {"change": function proxy () {
  var $wx = arguments[arguments.length - 1].$wx;
  var $event = ($wx.detail && $wx.detail.arguments) ? $wx.detail.arguments[0] : arguments[arguments.length -1];
  var $args = $wx.detail && $wx.detail.arguments;
  var _vm=this;
  return (function () {
    _vm.onChangeMainPrice.apply(_vm, $args || [$event]);
  })();
}},'16-1': {"change": function proxy () {
  var $wx = arguments[arguments.length - 1].$wx;
  var $event = ($wx.detail && $wx.detail.arguments) ? $wx.detail.arguments[0] : arguments[arguments.length -1];
  var $args = $wx.detail && $wx.detail.arguments;
  var _vm=this;
  return (function () {
    _vm.onChangeSalesMode.apply(_vm, $args || [$event]);
  })();
}},'16-2': {"change": function proxy () {
  var $wx = arguments[arguments.length - 1].$wx;
  var $event = ($wx.detail && $wx.detail.arguments) ? $wx.detail.arguments[0] : arguments[arguments.length -1];
  var $args = $wx.detail && $wx.detail.arguments;
  var _vm=this;
  return (function () {
    _vm.onChangeAwards.apply(_vm, $args || [$event]);
  })();
}},'16-3': {"tap": function proxy () {
    var _vm=this;
  return (function () {
    _vm.onBack();
  })();
}},'16-4': {"tap": function proxy () {
    var _vm=this;
  return (function () {
    _vm.onNext();
  })();
}}}, models: {'14': {
      type: "input",
      expr: "wineryForm.annualOutput",
      handler: function set ($v) {
      var _vm=this;
        _vm.$set(_vm.wineryForm, "annualOutput", $v);
      
    }
    },'15': {
      type: "input",
      expr: "wineryForm.stock",
      handler: function set ($v) {
      var _vm=this;
        _vm.$set(_vm.wineryForm, "stock", $v);
      
    }
    },'16': {
      type: "input",
      expr: "wineryForm.bucketCount",
      handler: function set ($v) {
      var _vm=this;
        _vm.$set(_vm.wineryForm, "bucketCount", $v);
      
    }
    },'17': {
      type: "input",
      expr: "wineryForm.awardInformation",
      handler: function set ($v) {
      var _vm=this;
        _vm.$set(_vm.wineryForm, "awardInformation", $v);
      
    }
    }}, refs: undefined }, {info: {"components":{},"on":{}}, handlers: {'16-0': {"change": function proxy () {
  var $wx = arguments[arguments.length - 1].$wx;
  var $event = ($wx.detail && $wx.detail.arguments) ? $wx.detail.arguments[0] : arguments[arguments.length -1];
  var $args = $wx.detail && $wx.detail.arguments;
  var _vm=this;
  return (function () {
    _vm.onChangeMainPrice.apply(_vm, $args || [$event]);
  })();
}},'16-1': {"change": function proxy () {
  var $wx = arguments[arguments.length - 1].$wx;
  var $event = ($wx.detail && $wx.detail.arguments) ? $wx.detail.arguments[0] : arguments[arguments.length -1];
  var $args = $wx.detail && $wx.detail.arguments;
  var _vm=this;
  return (function () {
    _vm.onChangeSalesMode.apply(_vm, $args || [$event]);
  })();
}},'16-2': {"change": function proxy () {
  var $wx = arguments[arguments.length - 1].$wx;
  var $event = ($wx.detail && $wx.detail.arguments) ? $wx.detail.arguments[0] : arguments[arguments.length -1];
  var $args = $wx.detail && $wx.detail.arguments;
  var _vm=this;
  return (function () {
    _vm.onChangeAwards.apply(_vm, $args || [$event]);
  })();
}},'16-3': {"tap": function proxy () {
    var _vm=this;
  return (function () {
    _vm.onBack();
  })();
}},'16-4': {"tap": function proxy () {
    var _vm=this;
  return (function () {
    _vm.onNext();
  })();
}}}, models: {'14': {
      type: "input",
      expr: "wineryForm.annualOutput",
      handler: function set ($v) {
      var _vm=this;
        _vm.$set(_vm.wineryForm, "annualOutput", $v);
      
    }
    },'15': {
      type: "input",
      expr: "wineryForm.stock",
      handler: function set ($v) {
      var _vm=this;
        _vm.$set(_vm.wineryForm, "stock", $v);
      
    }
    },'16': {
      type: "input",
      expr: "wineryForm.bucketCount",
      handler: function set ($v) {
      var _vm=this;
        _vm.$set(_vm.wineryForm, "bucketCount", $v);
      
    }
    },'17': {
      type: "input",
      expr: "wineryForm.awardInformation",
      handler: function set ($v) {
      var _vm=this;
        _vm.$set(_vm.wineryForm, "awardInformation", $v);
      
    }
    }}, refs: undefined }, {info: {"components":{},"on":{}}, handlers: {'16-0': {"change": function proxy () {
  var $wx = arguments[arguments.length - 1].$wx;
  var $event = ($wx.detail && $wx.detail.arguments) ? $wx.detail.arguments[0] : arguments[arguments.length -1];
  var $args = $wx.detail && $wx.detail.arguments;
  var _vm=this;
  return (function () {
    _vm.onChangeMainPrice.apply(_vm, $args || [$event]);
  })();
}},'16-1': {"change": function proxy () {
  var $wx = arguments[arguments.length - 1].$wx;
  var $event = ($wx.detail && $wx.detail.arguments) ? $wx.detail.arguments[0] : arguments[arguments.length -1];
  var $args = $wx.detail && $wx.detail.arguments;
  var _vm=this;
  return (function () {
    _vm.onChangeSalesMode.apply(_vm, $args || [$event]);
  })();
}},'16-2': {"change": function proxy () {
  var $wx = arguments[arguments.length - 1].$wx;
  var $event = ($wx.detail && $wx.detail.arguments) ? $wx.detail.arguments[0] : arguments[arguments.length -1];
  var $args = $wx.detail && $wx.detail.arguments;
  var _vm=this;
  return (function () {
    _vm.onChangeAwards.apply(_vm, $args || [$event]);
  })();
}},'16-3': {"tap": function proxy () {
    var _vm=this;
  return (function () {
    _vm.onBack();
  })();
}},'16-4': {"tap": function proxy () {
    var _vm=this;
  return (function () {
    _vm.onNext();
  })();
}}}, models: {'14': {
      type: "input",
      expr: "wineryForm.annualOutput",
      handler: function set ($v) {
      var _vm=this;
        _vm.$set(_vm.wineryForm, "annualOutput", $v);
      
    }
    },'15': {
      type: "input",
      expr: "wineryForm.stock",
      handler: function set ($v) {
      var _vm=this;
        _vm.$set(_vm.wineryForm, "stock", $v);
      
    }
    },'16': {
      type: "input",
      expr: "wineryForm.bucketCount",
      handler: function set ($v) {
      var _vm=this;
        _vm.$set(_vm.wineryForm, "bucketCount", $v);
      
    }
    },'17': {
      type: "input",
      expr: "wineryForm.awardInformation",
      handler: function set ($v) {
      var _vm=this;
        _vm.$set(_vm.wineryForm, "awardInformation", $v);
      
    }
    }}, refs: undefined });