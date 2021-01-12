"use strict";

var _core = _interopRequireDefault(require('./../vendor.js')(0));

var _eventHub = _interopRequireDefault(require('./../common/eventHub.js'));

var _store = _interopRequireDefault(require('./../store/index.js'));

var _x = require('./../vendor.js')(2);

var _xiao4rBase = _interopRequireDefault(require('./../xiao4rBase.js'));

var _xiao4rApis = _interopRequireDefault(require('./../apis/xiao4rApis.js'));

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
    fermentationProcessType: ['冷浸渍', '倒灌', '压帽', '二氧化碳浸渍', '整串发酵', '半二氧化碳浸渍', '酒泥陈酿'],
    containerType: ['不锈钢罐', '橡木桶'],
    clarificationMethodType: ['沉淀', '下胶', '过滤']
  },
  computed: (0, _x.mapState)(['wineryForm']),
  watch: {},
  methods: {
    onChangeFermentationProcess: function onChangeFermentationProcess(e) {
      this.wineryForm.fermentationProcess = e.$wx.detail;
    },
    onChangeContainer: function onChangeContainer(e) {
      this.wineryForm.container = e.$wx.detail;
    },
    onChangeClarificationMethod: function onChangeClarificationMethod(e) {
      this.wineryForm.clarificationMethod = e.$wx.detail;
    },
    onNext: function onNext() {
      if (this.wineryForm.fermentationProcess.length < 1) {
        _xiao4rBase["default"].showToast('请选择酒精发酵工艺!');

        return;
      }

      if (this.wineryForm.container.length < 1) {
        _xiao4rBase["default"].showToast('请选择热化容器!');

        return;
      }

      if (this.wineryForm.clarificationMethod.length < 1) {
        _xiao4rBase["default"].showToast('请选择澄清方式!');

        return;
      }

      _xiao4rBase["default"].navigateTo('form5');
    },
    onBack: function onBack() {
      wx.navigateBack();
    }
  },
  created: function created() {
    console.log(this.form);
  }
}, {info: {"components":{},"on":{}}, handlers: {'15-0': {"change": function proxy () {
  var $wx = arguments[arguments.length - 1].$wx;
  var $event = ($wx.detail && $wx.detail.arguments) ? $wx.detail.arguments[0] : arguments[arguments.length -1];
  var $args = $wx.detail && $wx.detail.arguments;
  var _vm=this;
  return (function () {
    _vm.onChangeFermentationProcess.apply(_vm, $args || [$event]);
  })();
}},'15-1': {"change": function proxy () {
  var $wx = arguments[arguments.length - 1].$wx;
  var $event = ($wx.detail && $wx.detail.arguments) ? $wx.detail.arguments[0] : arguments[arguments.length -1];
  var $args = $wx.detail && $wx.detail.arguments;
  var _vm=this;
  return (function () {
    _vm.onChangeContainer.apply(_vm, $args || [$event]);
  })();
}},'15-2': {"change": function proxy () {
  var $wx = arguments[arguments.length - 1].$wx;
  var $event = ($wx.detail && $wx.detail.arguments) ? $wx.detail.arguments[0] : arguments[arguments.length -1];
  var $args = $wx.detail && $wx.detail.arguments;
  var _vm=this;
  return (function () {
    _vm.onChangeClarificationMethod.apply(_vm, $args || [$event]);
  })();
}},'15-3': {"tap": function proxy () {
    var _vm=this;
  return (function () {
    _vm.onBack();
  })();
}},'15-4': {"tap": function proxy () {
    var _vm=this;
  return (function () {
    _vm.onNext();
  })();
}}}, models: {}, refs: undefined }, {info: {"components":{},"on":{}}, handlers: {'15-0': {"change": function proxy () {
  var $wx = arguments[arguments.length - 1].$wx;
  var $event = ($wx.detail && $wx.detail.arguments) ? $wx.detail.arguments[0] : arguments[arguments.length -1];
  var $args = $wx.detail && $wx.detail.arguments;
  var _vm=this;
  return (function () {
    _vm.onChangeFermentationProcess.apply(_vm, $args || [$event]);
  })();
}},'15-1': {"change": function proxy () {
  var $wx = arguments[arguments.length - 1].$wx;
  var $event = ($wx.detail && $wx.detail.arguments) ? $wx.detail.arguments[0] : arguments[arguments.length -1];
  var $args = $wx.detail && $wx.detail.arguments;
  var _vm=this;
  return (function () {
    _vm.onChangeContainer.apply(_vm, $args || [$event]);
  })();
}},'15-2': {"change": function proxy () {
  var $wx = arguments[arguments.length - 1].$wx;
  var $event = ($wx.detail && $wx.detail.arguments) ? $wx.detail.arguments[0] : arguments[arguments.length -1];
  var $args = $wx.detail && $wx.detail.arguments;
  var _vm=this;
  return (function () {
    _vm.onChangeClarificationMethod.apply(_vm, $args || [$event]);
  })();
}},'15-3': {"tap": function proxy () {
    var _vm=this;
  return (function () {
    _vm.onBack();
  })();
}},'15-4': {"tap": function proxy () {
    var _vm=this;
  return (function () {
    _vm.onNext();
  })();
}}}, models: {}, refs: undefined }, {info: {"components":{},"on":{}}, handlers: {'15-0': {"change": function proxy () {
  var $wx = arguments[arguments.length - 1].$wx;
  var $event = ($wx.detail && $wx.detail.arguments) ? $wx.detail.arguments[0] : arguments[arguments.length -1];
  var $args = $wx.detail && $wx.detail.arguments;
  var _vm=this;
  return (function () {
    _vm.onChangeFermentationProcess.apply(_vm, $args || [$event]);
  })();
}},'15-1': {"change": function proxy () {
  var $wx = arguments[arguments.length - 1].$wx;
  var $event = ($wx.detail && $wx.detail.arguments) ? $wx.detail.arguments[0] : arguments[arguments.length -1];
  var $args = $wx.detail && $wx.detail.arguments;
  var _vm=this;
  return (function () {
    _vm.onChangeContainer.apply(_vm, $args || [$event]);
  })();
}},'15-2': {"change": function proxy () {
  var $wx = arguments[arguments.length - 1].$wx;
  var $event = ($wx.detail && $wx.detail.arguments) ? $wx.detail.arguments[0] : arguments[arguments.length -1];
  var $args = $wx.detail && $wx.detail.arguments;
  var _vm=this;
  return (function () {
    _vm.onChangeClarificationMethod.apply(_vm, $args || [$event]);
  })();
}},'15-3': {"tap": function proxy () {
    var _vm=this;
  return (function () {
    _vm.onBack();
  })();
}},'15-4': {"tap": function proxy () {
    var _vm=this;
  return (function () {
    _vm.onNext();
  })();
}}}, models: {}, refs: undefined }, {info: {"components":{},"on":{}}, handlers: {'15-0': {"change": function proxy () {
  var $wx = arguments[arguments.length - 1].$wx;
  var $event = ($wx.detail && $wx.detail.arguments) ? $wx.detail.arguments[0] : arguments[arguments.length -1];
  var $args = $wx.detail && $wx.detail.arguments;
  var _vm=this;
  return (function () {
    _vm.onChangeFermentationProcess.apply(_vm, $args || [$event]);
  })();
}},'15-1': {"change": function proxy () {
  var $wx = arguments[arguments.length - 1].$wx;
  var $event = ($wx.detail && $wx.detail.arguments) ? $wx.detail.arguments[0] : arguments[arguments.length -1];
  var $args = $wx.detail && $wx.detail.arguments;
  var _vm=this;
  return (function () {
    _vm.onChangeContainer.apply(_vm, $args || [$event]);
  })();
}},'15-2': {"change": function proxy () {
  var $wx = arguments[arguments.length - 1].$wx;
  var $event = ($wx.detail && $wx.detail.arguments) ? $wx.detail.arguments[0] : arguments[arguments.length -1];
  var $args = $wx.detail && $wx.detail.arguments;
  var _vm=this;
  return (function () {
    _vm.onChangeClarificationMethod.apply(_vm, $args || [$event]);
  })();
}},'15-3': {"tap": function proxy () {
    var _vm=this;
  return (function () {
    _vm.onBack();
  })();
}},'15-4': {"tap": function proxy () {
    var _vm=this;
  return (function () {
    _vm.onNext();
  })();
}}}, models: {}, refs: undefined }, {info: {"components":{},"on":{}}, handlers: {'15-0': {"change": function proxy () {
  var $wx = arguments[arguments.length - 1].$wx;
  var $event = ($wx.detail && $wx.detail.arguments) ? $wx.detail.arguments[0] : arguments[arguments.length -1];
  var $args = $wx.detail && $wx.detail.arguments;
  var _vm=this;
  return (function () {
    _vm.onChangeFermentationProcess.apply(_vm, $args || [$event]);
  })();
}},'15-1': {"change": function proxy () {
  var $wx = arguments[arguments.length - 1].$wx;
  var $event = ($wx.detail && $wx.detail.arguments) ? $wx.detail.arguments[0] : arguments[arguments.length -1];
  var $args = $wx.detail && $wx.detail.arguments;
  var _vm=this;
  return (function () {
    _vm.onChangeContainer.apply(_vm, $args || [$event]);
  })();
}},'15-2': {"change": function proxy () {
  var $wx = arguments[arguments.length - 1].$wx;
  var $event = ($wx.detail && $wx.detail.arguments) ? $wx.detail.arguments[0] : arguments[arguments.length -1];
  var $args = $wx.detail && $wx.detail.arguments;
  var _vm=this;
  return (function () {
    _vm.onChangeClarificationMethod.apply(_vm, $args || [$event]);
  })();
}},'15-3': {"tap": function proxy () {
    var _vm=this;
  return (function () {
    _vm.onBack();
  })();
}},'15-4': {"tap": function proxy () {
    var _vm=this;
  return (function () {
    _vm.onNext();
  })();
}}}, models: {}, refs: undefined }, {info: {"components":{},"on":{}}, handlers: {'15-0': {"change": function proxy () {
  var $wx = arguments[arguments.length - 1].$wx;
  var $event = ($wx.detail && $wx.detail.arguments) ? $wx.detail.arguments[0] : arguments[arguments.length -1];
  var $args = $wx.detail && $wx.detail.arguments;
  var _vm=this;
  return (function () {
    _vm.onChangeFermentationProcess.apply(_vm, $args || [$event]);
  })();
}},'15-1': {"change": function proxy () {
  var $wx = arguments[arguments.length - 1].$wx;
  var $event = ($wx.detail && $wx.detail.arguments) ? $wx.detail.arguments[0] : arguments[arguments.length -1];
  var $args = $wx.detail && $wx.detail.arguments;
  var _vm=this;
  return (function () {
    _vm.onChangeContainer.apply(_vm, $args || [$event]);
  })();
}},'15-2': {"change": function proxy () {
  var $wx = arguments[arguments.length - 1].$wx;
  var $event = ($wx.detail && $wx.detail.arguments) ? $wx.detail.arguments[0] : arguments[arguments.length -1];
  var $args = $wx.detail && $wx.detail.arguments;
  var _vm=this;
  return (function () {
    _vm.onChangeClarificationMethod.apply(_vm, $args || [$event]);
  })();
}},'15-3': {"tap": function proxy () {
    var _vm=this;
  return (function () {
    _vm.onBack();
  })();
}},'15-4': {"tap": function proxy () {
    var _vm=this;
  return (function () {
    _vm.onNext();
  })();
}}}, models: {}, refs: undefined }, {info: {"components":{},"on":{}}, handlers: {'15-0': {"change": function proxy () {
  var $wx = arguments[arguments.length - 1].$wx;
  var $event = ($wx.detail && $wx.detail.arguments) ? $wx.detail.arguments[0] : arguments[arguments.length -1];
  var $args = $wx.detail && $wx.detail.arguments;
  var _vm=this;
  return (function () {
    _vm.onChangeFermentationProcess.apply(_vm, $args || [$event]);
  })();
}},'15-1': {"change": function proxy () {
  var $wx = arguments[arguments.length - 1].$wx;
  var $event = ($wx.detail && $wx.detail.arguments) ? $wx.detail.arguments[0] : arguments[arguments.length -1];
  var $args = $wx.detail && $wx.detail.arguments;
  var _vm=this;
  return (function () {
    _vm.onChangeContainer.apply(_vm, $args || [$event]);
  })();
}},'15-2': {"change": function proxy () {
  var $wx = arguments[arguments.length - 1].$wx;
  var $event = ($wx.detail && $wx.detail.arguments) ? $wx.detail.arguments[0] : arguments[arguments.length -1];
  var $args = $wx.detail && $wx.detail.arguments;
  var _vm=this;
  return (function () {
    _vm.onChangeClarificationMethod.apply(_vm, $args || [$event]);
  })();
}},'15-3': {"tap": function proxy () {
    var _vm=this;
  return (function () {
    _vm.onBack();
  })();
}},'15-4': {"tap": function proxy () {
    var _vm=this;
  return (function () {
    _vm.onNext();
  })();
}}}, models: {}, refs: undefined });