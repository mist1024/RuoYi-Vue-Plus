"use strict";

var _core = _interopRequireDefault(require('./../vendor.js')(0));

var _eventHub = _interopRequireDefault(require('./../common/eventHub.js'));

var _store = _interopRequireDefault(require('./../store/index.js'));

var _x = require('./../vendor.js')(2);

var _xiao4rBase = _interopRequireDefault(require('./../xiao4rBase.js'));

var _xiao4rApis = _interopRequireDefault(require('./../apis/xiao4rApis.js'));

function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { "default": obj }; }

function ownKeys(object, enumerableOnly) { var keys = Object.keys(object); if (Object.getOwnPropertySymbols) { var symbols = Object.getOwnPropertySymbols(object); if (enumerableOnly) symbols = symbols.filter(function (sym) { return Object.getOwnPropertyDescriptor(object, sym).enumerable; }); keys.push.apply(keys, symbols); } return keys; }

function _objectSpread(target) { for (var i = 1; i < arguments.length; i++) { var source = arguments[i] != null ? arguments[i] : {}; if (i % 2) { ownKeys(Object(source), true).forEach(function (key) { _defineProperty(target, key, source[key]); }); } else if (Object.getOwnPropertyDescriptors) { Object.defineProperties(target, Object.getOwnPropertyDescriptors(source)); } else { ownKeys(Object(source)).forEach(function (key) { Object.defineProperty(target, key, Object.getOwnPropertyDescriptor(source, key)); }); } } return target; }

function _defineProperty(obj, key, value) { if (key in obj) { Object.defineProperty(obj, key, { value: value, enumerable: true, configurable: true, writable: true }); } else { obj[key] = value; } return obj; }

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
    soilTypes: ['沙石', '砾石', '石灰岩', '混合'],
    irrigationTypes: ['漫灌', '喷灌', '滴灌'],
    redVarieties: ['赤霞珠', '梅洛', '品丽珠', '黑皮诺', '西拉', '歌海娜', '桑娇维塞', '内比奥罗', '丹魄', '马尔贝克', '其他'],
    whiteVarieties: ['霞多丽', '长相思', '雷司令', '灰皮诺', '赛美蓉', '琼瑶浆', '其他']
  },
  computed: (0, _x.mapState)(['wineryForm']),
  watch: {},
  methods: _objectSpread(_objectSpread({}, (0, _x.mapActions)(['setFormAction'])), {}, {
    onChangeSoilType: function onChangeSoilType(e) {
      this.wineryForm.soilType = e.$wx.detail;
    },
    onChangeIrrigationType: function onChangeIrrigationType(e) {
      this.wineryForm.irrigationType = e.$wx.detail;
    },
    onChangeRedVariety: function onChangeRedVariety(e) {
      this.wineryForm.redVariety.checked = e.$wx.detail;
      this.changeItem(this.wineryForm.redVariety.checked, this.wineryForm.redVariety.params);
    },
    onChangeWhiteVariety: function onChangeWhiteVariety(e) {
      console.log(e);
      this.wineryForm.whiteVariety.checked = e.$wx.detail;
      this.changeItem(this.wineryForm.whiteVariety.checked, this.wineryForm.whiteVariety.params);
    },
    changeItem: function changeItem(types, items) {
      for (var i = 0; i < items.length; i++) {
        var isChecked = false;

        for (var j = 0; j < types.length; j++) {
          // console.log(types[j])
          // console.log(items[i].name)
          if (types[j] === items[i].name) {
            isChecked = true;
            break;
          }
        }

        items[i].isChecked = isChecked;

        if (!items[i].isChecked) {
          items[i].msg = {
            area: '',
            treeOld: '',
            treeMaxOld: ''
          };
        }
      }
    },
    initVariety: function initVariety(types, fromList) {
      for (var i = 0; i < types.length; i++) {
        fromList.push({
          name: types[i],
          isChecked: false,
          msg: {
            area: '',
            treeOld: '',
            treeMaxOld: ''
          }
        });
      }
    },
    onNext: function onNext() {
      if (!this.wineryForm.plantArea) {
        _xiao4rBase["default"].showToast('请输入种植面积！');

        return;
      }

      if (!this.wineryForm.plantWeight) {
        _xiao4rBase["default"].showToast('请输入产量！');

        return;
      }

      var error = this.checkVariety(this.wineryForm.redVariety);

      if (error) {
        _xiao4rBase["default"].showToast('请检查红葡萄信息:' + error);

        return;
      }

      error = this.checkVariety(this.wineryForm.whiteVariety);

      if (error) {
        _xiao4rBase["default"].showToast('请检查白葡萄信息:' + error);

        return;
      } // this.setFormAction(this.form)


      _xiao4rBase["default"].navigateTo('form4');
    },
    onBack: function onBack() {
      // this.setFormAction(this.form)
      wx.navigateBack();
    },
    checkVariety: function checkVariety(variety) {
      var errorMsg = '';
      var isChecked = false;

      for (var i = 0; i < variety.params.length; i++) {
        var item = variety.params[i];

        if (item.isChecked) {
          isChecked = true;

          if (!item.msg.area || !item.msg.treeOld || !item.msg.treeMaxOld) {
            errorMsg = '请完善' + item.name + '品种信息!';
          }
        }
      }

      if (!isChecked) {
        errorMsg = '请选择葡萄品种!';
      }

      return errorMsg;
    }
  }),
  created: function created() {
    if (this.wineryForm.redVariety.params.length < 1) {
      this.initVariety(this.redVarieties, this.wineryForm.redVariety.params);
    }

    if (this.wineryForm.whiteVariety.params.length < 1) {
      this.initVariety(this.whiteVarieties, this.wineryForm.whiteVariety.params);
    }
  }
}, {info: {"components":{},"on":{}}, handlers: {'14-0': {"change": function proxy () {
  var $wx = arguments[arguments.length - 1].$wx;
  var $event = ($wx.detail && $wx.detail.arguments) ? $wx.detail.arguments[0] : arguments[arguments.length -1];
  var $args = $wx.detail && $wx.detail.arguments;
  var _vm=this;
  return (function () {
    _vm.onChangeSoilType.apply(_vm, $args || [$event]);
  })();
}},'14-1': {"change": function proxy () {
  var $wx = arguments[arguments.length - 1].$wx;
  var $event = ($wx.detail && $wx.detail.arguments) ? $wx.detail.arguments[0] : arguments[arguments.length -1];
  var $args = $wx.detail && $wx.detail.arguments;
  var _vm=this;
  return (function () {
    _vm.onChangeRedVariety.apply(_vm, $args || [$event]);
  })();
}},'14-2': {"change": function proxy () {
  var $wx = arguments[arguments.length - 1].$wx;
  var $event = ($wx.detail && $wx.detail.arguments) ? $wx.detail.arguments[0] : arguments[arguments.length -1];
  var $args = $wx.detail && $wx.detail.arguments;
  var _vm=this;
  return (function () {
    _vm.onChangeWhiteVariety.apply(_vm, $args || [$event]);
  })();
}},'14-3': {"change": function proxy () {
  var $wx = arguments[arguments.length - 1].$wx;
  var $event = ($wx.detail && $wx.detail.arguments) ? $wx.detail.arguments[0] : arguments[arguments.length -1];
  var $args = $wx.detail && $wx.detail.arguments;
  var _vm=this;
  return (function () {
    _vm.onChangeIrrigationType.apply(_vm, $args || [$event]);
  })();
}},'14-4': {"tap": function proxy () {
    var _vm=this;
  return (function () {
    _vm.onBack();
  })();
}},'14-5': {"tap": function proxy () {
    var _vm=this;
  return (function () {
    _vm.onNext();
  })();
}}}, models: {'6': {
      type: "input",
      expr: "wineryForm.plantArea",
      handler: function set ($v) {
      var _vm=this;
        _vm.$set(_vm.wineryForm, "plantArea", $v);
      
    }
    },'7': {
      type: "input",
      expr: "wineryForm.plantWeight",
      handler: function set ($v) {
      var _vm=this;
        _vm.$set(_vm.wineryForm, "plantWeight", $v);
      
    }
    },'8': {
      type: "input",
      expr: "item.msg.area",
      handler: function set ($v, $p) {
    var _vm=this;
      function fn1(item) {
          _vm.$set(item.msg, "area", $v);
        }
      return fn1((_vm.wineryForm.redVariety.params)[$p[0]]);
    
  }
    },'9': {
      type: "input",
      expr: "item.msg.treeOld",
      handler: function set ($v, $p) {
    var _vm=this;
      function fn1(item) {
          _vm.$set(item.msg, "treeOld", $v);
        }
      return fn1((_vm.wineryForm.redVariety.params)[$p[0]]);
    
  }
    },'10': {
      type: "input",
      expr: "item.msg.treeMaxOld",
      handler: function set ($v, $p) {
    var _vm=this;
      function fn1(item) {
          _vm.$set(item.msg, "treeMaxOld", $v);
        }
      return fn1((_vm.wineryForm.redVariety.params)[$p[0]]);
    
  }
    },'11': {
      type: "input",
      expr: "item.msg.area",
      handler: function set ($v, $p) {
    var _vm=this;
      function fn1(item) {
          _vm.$set(item.msg, "area", $v);
        }
      return fn1((_vm.wineryForm.whiteVariety.params)[$p[0]]);
    
  }
    },'12': {
      type: "input",
      expr: "item.msg.treeOld",
      handler: function set ($v, $p) {
    var _vm=this;
      function fn1(item) {
          _vm.$set(item.msg, "treeOld", $v);
        }
      return fn1((_vm.wineryForm.whiteVariety.params)[$p[0]]);
    
  }
    },'13': {
      type: "input",
      expr: "item.msg.treeMaxOld",
      handler: function set ($v, $p) {
    var _vm=this;
      function fn1(item) {
          _vm.$set(item.msg, "treeMaxOld", $v);
        }
      return fn1((_vm.wineryForm.whiteVariety.params)[$p[0]]);
    
  }
    }}, refs: undefined }, {info: {"components":{},"on":{}}, handlers: {'14-0': {"change": function proxy () {
  var $wx = arguments[arguments.length - 1].$wx;
  var $event = ($wx.detail && $wx.detail.arguments) ? $wx.detail.arguments[0] : arguments[arguments.length -1];
  var $args = $wx.detail && $wx.detail.arguments;
  var _vm=this;
  return (function () {
    _vm.onChangeSoilType.apply(_vm, $args || [$event]);
  })();
}},'14-1': {"change": function proxy () {
  var $wx = arguments[arguments.length - 1].$wx;
  var $event = ($wx.detail && $wx.detail.arguments) ? $wx.detail.arguments[0] : arguments[arguments.length -1];
  var $args = $wx.detail && $wx.detail.arguments;
  var _vm=this;
  return (function () {
    _vm.onChangeRedVariety.apply(_vm, $args || [$event]);
  })();
}},'14-2': {"change": function proxy () {
  var $wx = arguments[arguments.length - 1].$wx;
  var $event = ($wx.detail && $wx.detail.arguments) ? $wx.detail.arguments[0] : arguments[arguments.length -1];
  var $args = $wx.detail && $wx.detail.arguments;
  var _vm=this;
  return (function () {
    _vm.onChangeWhiteVariety.apply(_vm, $args || [$event]);
  })();
}},'14-3': {"change": function proxy () {
  var $wx = arguments[arguments.length - 1].$wx;
  var $event = ($wx.detail && $wx.detail.arguments) ? $wx.detail.arguments[0] : arguments[arguments.length -1];
  var $args = $wx.detail && $wx.detail.arguments;
  var _vm=this;
  return (function () {
    _vm.onChangeIrrigationType.apply(_vm, $args || [$event]);
  })();
}},'14-4': {"tap": function proxy () {
    var _vm=this;
  return (function () {
    _vm.onBack();
  })();
}},'14-5': {"tap": function proxy () {
    var _vm=this;
  return (function () {
    _vm.onNext();
  })();
}}}, models: {'6': {
      type: "input",
      expr: "wineryForm.plantArea",
      handler: function set ($v) {
      var _vm=this;
        _vm.$set(_vm.wineryForm, "plantArea", $v);
      
    }
    },'7': {
      type: "input",
      expr: "wineryForm.plantWeight",
      handler: function set ($v) {
      var _vm=this;
        _vm.$set(_vm.wineryForm, "plantWeight", $v);
      
    }
    },'8': {
      type: "input",
      expr: "item.msg.area",
      handler: function set ($v, $p) {
    var _vm=this;
      function fn1(item) {
          _vm.$set(item.msg, "area", $v);
        }
      return fn1((_vm.wineryForm.redVariety.params)[$p[0]]);
    
  }
    },'9': {
      type: "input",
      expr: "item.msg.treeOld",
      handler: function set ($v, $p) {
    var _vm=this;
      function fn1(item) {
          _vm.$set(item.msg, "treeOld", $v);
        }
      return fn1((_vm.wineryForm.redVariety.params)[$p[0]]);
    
  }
    },'10': {
      type: "input",
      expr: "item.msg.treeMaxOld",
      handler: function set ($v, $p) {
    var _vm=this;
      function fn1(item) {
          _vm.$set(item.msg, "treeMaxOld", $v);
        }
      return fn1((_vm.wineryForm.redVariety.params)[$p[0]]);
    
  }
    },'11': {
      type: "input",
      expr: "item.msg.area",
      handler: function set ($v, $p) {
    var _vm=this;
      function fn1(item) {
          _vm.$set(item.msg, "area", $v);
        }
      return fn1((_vm.wineryForm.whiteVariety.params)[$p[0]]);
    
  }
    },'12': {
      type: "input",
      expr: "item.msg.treeOld",
      handler: function set ($v, $p) {
    var _vm=this;
      function fn1(item) {
          _vm.$set(item.msg, "treeOld", $v);
        }
      return fn1((_vm.wineryForm.whiteVariety.params)[$p[0]]);
    
  }
    },'13': {
      type: "input",
      expr: "item.msg.treeMaxOld",
      handler: function set ($v, $p) {
    var _vm=this;
      function fn1(item) {
          _vm.$set(item.msg, "treeMaxOld", $v);
        }
      return fn1((_vm.wineryForm.whiteVariety.params)[$p[0]]);
    
  }
    }}, refs: undefined }, {info: {"components":{},"on":{}}, handlers: {'14-0': {"change": function proxy () {
  var $wx = arguments[arguments.length - 1].$wx;
  var $event = ($wx.detail && $wx.detail.arguments) ? $wx.detail.arguments[0] : arguments[arguments.length -1];
  var $args = $wx.detail && $wx.detail.arguments;
  var _vm=this;
  return (function () {
    _vm.onChangeSoilType.apply(_vm, $args || [$event]);
  })();
}},'14-1': {"change": function proxy () {
  var $wx = arguments[arguments.length - 1].$wx;
  var $event = ($wx.detail && $wx.detail.arguments) ? $wx.detail.arguments[0] : arguments[arguments.length -1];
  var $args = $wx.detail && $wx.detail.arguments;
  var _vm=this;
  return (function () {
    _vm.onChangeRedVariety.apply(_vm, $args || [$event]);
  })();
}},'14-2': {"change": function proxy () {
  var $wx = arguments[arguments.length - 1].$wx;
  var $event = ($wx.detail && $wx.detail.arguments) ? $wx.detail.arguments[0] : arguments[arguments.length -1];
  var $args = $wx.detail && $wx.detail.arguments;
  var _vm=this;
  return (function () {
    _vm.onChangeWhiteVariety.apply(_vm, $args || [$event]);
  })();
}},'14-3': {"change": function proxy () {
  var $wx = arguments[arguments.length - 1].$wx;
  var $event = ($wx.detail && $wx.detail.arguments) ? $wx.detail.arguments[0] : arguments[arguments.length -1];
  var $args = $wx.detail && $wx.detail.arguments;
  var _vm=this;
  return (function () {
    _vm.onChangeIrrigationType.apply(_vm, $args || [$event]);
  })();
}},'14-4': {"tap": function proxy () {
    var _vm=this;
  return (function () {
    _vm.onBack();
  })();
}},'14-5': {"tap": function proxy () {
    var _vm=this;
  return (function () {
    _vm.onNext();
  })();
}}}, models: {'6': {
      type: "input",
      expr: "wineryForm.plantArea",
      handler: function set ($v) {
      var _vm=this;
        _vm.$set(_vm.wineryForm, "plantArea", $v);
      
    }
    },'7': {
      type: "input",
      expr: "wineryForm.plantWeight",
      handler: function set ($v) {
      var _vm=this;
        _vm.$set(_vm.wineryForm, "plantWeight", $v);
      
    }
    },'8': {
      type: "input",
      expr: "item.msg.area",
      handler: function set ($v, $p) {
    var _vm=this;
      function fn1(item) {
          _vm.$set(item.msg, "area", $v);
        }
      return fn1((_vm.wineryForm.redVariety.params)[$p[0]]);
    
  }
    },'9': {
      type: "input",
      expr: "item.msg.treeOld",
      handler: function set ($v, $p) {
    var _vm=this;
      function fn1(item) {
          _vm.$set(item.msg, "treeOld", $v);
        }
      return fn1((_vm.wineryForm.redVariety.params)[$p[0]]);
    
  }
    },'10': {
      type: "input",
      expr: "item.msg.treeMaxOld",
      handler: function set ($v, $p) {
    var _vm=this;
      function fn1(item) {
          _vm.$set(item.msg, "treeMaxOld", $v);
        }
      return fn1((_vm.wineryForm.redVariety.params)[$p[0]]);
    
  }
    },'11': {
      type: "input",
      expr: "item.msg.area",
      handler: function set ($v, $p) {
    var _vm=this;
      function fn1(item) {
          _vm.$set(item.msg, "area", $v);
        }
      return fn1((_vm.wineryForm.whiteVariety.params)[$p[0]]);
    
  }
    },'12': {
      type: "input",
      expr: "item.msg.treeOld",
      handler: function set ($v, $p) {
    var _vm=this;
      function fn1(item) {
          _vm.$set(item.msg, "treeOld", $v);
        }
      return fn1((_vm.wineryForm.whiteVariety.params)[$p[0]]);
    
  }
    },'13': {
      type: "input",
      expr: "item.msg.treeMaxOld",
      handler: function set ($v, $p) {
    var _vm=this;
      function fn1(item) {
          _vm.$set(item.msg, "treeMaxOld", $v);
        }
      return fn1((_vm.wineryForm.whiteVariety.params)[$p[0]]);
    
  }
    }}, refs: undefined }, {info: {"components":{},"on":{}}, handlers: {'14-0': {"change": function proxy () {
  var $wx = arguments[arguments.length - 1].$wx;
  var $event = ($wx.detail && $wx.detail.arguments) ? $wx.detail.arguments[0] : arguments[arguments.length -1];
  var $args = $wx.detail && $wx.detail.arguments;
  var _vm=this;
  return (function () {
    _vm.onChangeSoilType.apply(_vm, $args || [$event]);
  })();
}},'14-1': {"change": function proxy () {
  var $wx = arguments[arguments.length - 1].$wx;
  var $event = ($wx.detail && $wx.detail.arguments) ? $wx.detail.arguments[0] : arguments[arguments.length -1];
  var $args = $wx.detail && $wx.detail.arguments;
  var _vm=this;
  return (function () {
    _vm.onChangeRedVariety.apply(_vm, $args || [$event]);
  })();
}},'14-2': {"change": function proxy () {
  var $wx = arguments[arguments.length - 1].$wx;
  var $event = ($wx.detail && $wx.detail.arguments) ? $wx.detail.arguments[0] : arguments[arguments.length -1];
  var $args = $wx.detail && $wx.detail.arguments;
  var _vm=this;
  return (function () {
    _vm.onChangeWhiteVariety.apply(_vm, $args || [$event]);
  })();
}},'14-3': {"change": function proxy () {
  var $wx = arguments[arguments.length - 1].$wx;
  var $event = ($wx.detail && $wx.detail.arguments) ? $wx.detail.arguments[0] : arguments[arguments.length -1];
  var $args = $wx.detail && $wx.detail.arguments;
  var _vm=this;
  return (function () {
    _vm.onChangeIrrigationType.apply(_vm, $args || [$event]);
  })();
}},'14-4': {"tap": function proxy () {
    var _vm=this;
  return (function () {
    _vm.onBack();
  })();
}},'14-5': {"tap": function proxy () {
    var _vm=this;
  return (function () {
    _vm.onNext();
  })();
}}}, models: {'6': {
      type: "input",
      expr: "wineryForm.plantArea",
      handler: function set ($v) {
      var _vm=this;
        _vm.$set(_vm.wineryForm, "plantArea", $v);
      
    }
    },'7': {
      type: "input",
      expr: "wineryForm.plantWeight",
      handler: function set ($v) {
      var _vm=this;
        _vm.$set(_vm.wineryForm, "plantWeight", $v);
      
    }
    },'8': {
      type: "input",
      expr: "item.msg.area",
      handler: function set ($v, $p) {
    var _vm=this;
      function fn1(item) {
          _vm.$set(item.msg, "area", $v);
        }
      return fn1((_vm.wineryForm.redVariety.params)[$p[0]]);
    
  }
    },'9': {
      type: "input",
      expr: "item.msg.treeOld",
      handler: function set ($v, $p) {
    var _vm=this;
      function fn1(item) {
          _vm.$set(item.msg, "treeOld", $v);
        }
      return fn1((_vm.wineryForm.redVariety.params)[$p[0]]);
    
  }
    },'10': {
      type: "input",
      expr: "item.msg.treeMaxOld",
      handler: function set ($v, $p) {
    var _vm=this;
      function fn1(item) {
          _vm.$set(item.msg, "treeMaxOld", $v);
        }
      return fn1((_vm.wineryForm.redVariety.params)[$p[0]]);
    
  }
    },'11': {
      type: "input",
      expr: "item.msg.area",
      handler: function set ($v, $p) {
    var _vm=this;
      function fn1(item) {
          _vm.$set(item.msg, "area", $v);
        }
      return fn1((_vm.wineryForm.whiteVariety.params)[$p[0]]);
    
  }
    },'12': {
      type: "input",
      expr: "item.msg.treeOld",
      handler: function set ($v, $p) {
    var _vm=this;
      function fn1(item) {
          _vm.$set(item.msg, "treeOld", $v);
        }
      return fn1((_vm.wineryForm.whiteVariety.params)[$p[0]]);
    
  }
    },'13': {
      type: "input",
      expr: "item.msg.treeMaxOld",
      handler: function set ($v, $p) {
    var _vm=this;
      function fn1(item) {
          _vm.$set(item.msg, "treeMaxOld", $v);
        }
      return fn1((_vm.wineryForm.whiteVariety.params)[$p[0]]);
    
  }
    }}, refs: undefined }, {info: {"components":{},"on":{}}, handlers: {'14-0': {"change": function proxy () {
  var $wx = arguments[arguments.length - 1].$wx;
  var $event = ($wx.detail && $wx.detail.arguments) ? $wx.detail.arguments[0] : arguments[arguments.length -1];
  var $args = $wx.detail && $wx.detail.arguments;
  var _vm=this;
  return (function () {
    _vm.onChangeSoilType.apply(_vm, $args || [$event]);
  })();
}},'14-1': {"change": function proxy () {
  var $wx = arguments[arguments.length - 1].$wx;
  var $event = ($wx.detail && $wx.detail.arguments) ? $wx.detail.arguments[0] : arguments[arguments.length -1];
  var $args = $wx.detail && $wx.detail.arguments;
  var _vm=this;
  return (function () {
    _vm.onChangeRedVariety.apply(_vm, $args || [$event]);
  })();
}},'14-2': {"change": function proxy () {
  var $wx = arguments[arguments.length - 1].$wx;
  var $event = ($wx.detail && $wx.detail.arguments) ? $wx.detail.arguments[0] : arguments[arguments.length -1];
  var $args = $wx.detail && $wx.detail.arguments;
  var _vm=this;
  return (function () {
    _vm.onChangeWhiteVariety.apply(_vm, $args || [$event]);
  })();
}},'14-3': {"change": function proxy () {
  var $wx = arguments[arguments.length - 1].$wx;
  var $event = ($wx.detail && $wx.detail.arguments) ? $wx.detail.arguments[0] : arguments[arguments.length -1];
  var $args = $wx.detail && $wx.detail.arguments;
  var _vm=this;
  return (function () {
    _vm.onChangeIrrigationType.apply(_vm, $args || [$event]);
  })();
}},'14-4': {"tap": function proxy () {
    var _vm=this;
  return (function () {
    _vm.onBack();
  })();
}},'14-5': {"tap": function proxy () {
    var _vm=this;
  return (function () {
    _vm.onNext();
  })();
}}}, models: {'6': {
      type: "input",
      expr: "wineryForm.plantArea",
      handler: function set ($v) {
      var _vm=this;
        _vm.$set(_vm.wineryForm, "plantArea", $v);
      
    }
    },'7': {
      type: "input",
      expr: "wineryForm.plantWeight",
      handler: function set ($v) {
      var _vm=this;
        _vm.$set(_vm.wineryForm, "plantWeight", $v);
      
    }
    },'8': {
      type: "input",
      expr: "item.msg.area",
      handler: function set ($v, $p) {
    var _vm=this;
      function fn1(item) {
          _vm.$set(item.msg, "area", $v);
        }
      return fn1((_vm.wineryForm.redVariety.params)[$p[0]]);
    
  }
    },'9': {
      type: "input",
      expr: "item.msg.treeOld",
      handler: function set ($v, $p) {
    var _vm=this;
      function fn1(item) {
          _vm.$set(item.msg, "treeOld", $v);
        }
      return fn1((_vm.wineryForm.redVariety.params)[$p[0]]);
    
  }
    },'10': {
      type: "input",
      expr: "item.msg.treeMaxOld",
      handler: function set ($v, $p) {
    var _vm=this;
      function fn1(item) {
          _vm.$set(item.msg, "treeMaxOld", $v);
        }
      return fn1((_vm.wineryForm.redVariety.params)[$p[0]]);
    
  }
    },'11': {
      type: "input",
      expr: "item.msg.area",
      handler: function set ($v, $p) {
    var _vm=this;
      function fn1(item) {
          _vm.$set(item.msg, "area", $v);
        }
      return fn1((_vm.wineryForm.whiteVariety.params)[$p[0]]);
    
  }
    },'12': {
      type: "input",
      expr: "item.msg.treeOld",
      handler: function set ($v, $p) {
    var _vm=this;
      function fn1(item) {
          _vm.$set(item.msg, "treeOld", $v);
        }
      return fn1((_vm.wineryForm.whiteVariety.params)[$p[0]]);
    
  }
    },'13': {
      type: "input",
      expr: "item.msg.treeMaxOld",
      handler: function set ($v, $p) {
    var _vm=this;
      function fn1(item) {
          _vm.$set(item.msg, "treeMaxOld", $v);
        }
      return fn1((_vm.wineryForm.whiteVariety.params)[$p[0]]);
    
  }
    }}, refs: undefined }, {info: {"components":{},"on":{}}, handlers: {'14-0': {"change": function proxy () {
  var $wx = arguments[arguments.length - 1].$wx;
  var $event = ($wx.detail && $wx.detail.arguments) ? $wx.detail.arguments[0] : arguments[arguments.length -1];
  var $args = $wx.detail && $wx.detail.arguments;
  var _vm=this;
  return (function () {
    _vm.onChangeSoilType.apply(_vm, $args || [$event]);
  })();
}},'14-1': {"change": function proxy () {
  var $wx = arguments[arguments.length - 1].$wx;
  var $event = ($wx.detail && $wx.detail.arguments) ? $wx.detail.arguments[0] : arguments[arguments.length -1];
  var $args = $wx.detail && $wx.detail.arguments;
  var _vm=this;
  return (function () {
    _vm.onChangeRedVariety.apply(_vm, $args || [$event]);
  })();
}},'14-2': {"change": function proxy () {
  var $wx = arguments[arguments.length - 1].$wx;
  var $event = ($wx.detail && $wx.detail.arguments) ? $wx.detail.arguments[0] : arguments[arguments.length -1];
  var $args = $wx.detail && $wx.detail.arguments;
  var _vm=this;
  return (function () {
    _vm.onChangeWhiteVariety.apply(_vm, $args || [$event]);
  })();
}},'14-3': {"change": function proxy () {
  var $wx = arguments[arguments.length - 1].$wx;
  var $event = ($wx.detail && $wx.detail.arguments) ? $wx.detail.arguments[0] : arguments[arguments.length -1];
  var $args = $wx.detail && $wx.detail.arguments;
  var _vm=this;
  return (function () {
    _vm.onChangeIrrigationType.apply(_vm, $args || [$event]);
  })();
}},'14-4': {"tap": function proxy () {
    var _vm=this;
  return (function () {
    _vm.onBack();
  })();
}},'14-5': {"tap": function proxy () {
    var _vm=this;
  return (function () {
    _vm.onNext();
  })();
}}}, models: {'6': {
      type: "input",
      expr: "wineryForm.plantArea",
      handler: function set ($v) {
      var _vm=this;
        _vm.$set(_vm.wineryForm, "plantArea", $v);
      
    }
    },'7': {
      type: "input",
      expr: "wineryForm.plantWeight",
      handler: function set ($v) {
      var _vm=this;
        _vm.$set(_vm.wineryForm, "plantWeight", $v);
      
    }
    },'8': {
      type: "input",
      expr: "item.msg.area",
      handler: function set ($v, $p) {
    var _vm=this;
      function fn1(item) {
          _vm.$set(item.msg, "area", $v);
        }
      return fn1((_vm.wineryForm.redVariety.params)[$p[0]]);
    
  }
    },'9': {
      type: "input",
      expr: "item.msg.treeOld",
      handler: function set ($v, $p) {
    var _vm=this;
      function fn1(item) {
          _vm.$set(item.msg, "treeOld", $v);
        }
      return fn1((_vm.wineryForm.redVariety.params)[$p[0]]);
    
  }
    },'10': {
      type: "input",
      expr: "item.msg.treeMaxOld",
      handler: function set ($v, $p) {
    var _vm=this;
      function fn1(item) {
          _vm.$set(item.msg, "treeMaxOld", $v);
        }
      return fn1((_vm.wineryForm.redVariety.params)[$p[0]]);
    
  }
    },'11': {
      type: "input",
      expr: "item.msg.area",
      handler: function set ($v, $p) {
    var _vm=this;
      function fn1(item) {
          _vm.$set(item.msg, "area", $v);
        }
      return fn1((_vm.wineryForm.whiteVariety.params)[$p[0]]);
    
  }
    },'12': {
      type: "input",
      expr: "item.msg.treeOld",
      handler: function set ($v, $p) {
    var _vm=this;
      function fn1(item) {
          _vm.$set(item.msg, "treeOld", $v);
        }
      return fn1((_vm.wineryForm.whiteVariety.params)[$p[0]]);
    
  }
    },'13': {
      type: "input",
      expr: "item.msg.treeMaxOld",
      handler: function set ($v, $p) {
    var _vm=this;
      function fn1(item) {
          _vm.$set(item.msg, "treeMaxOld", $v);
        }
      return fn1((_vm.wineryForm.whiteVariety.params)[$p[0]]);
    
  }
    }}, refs: undefined }, {info: {"components":{},"on":{}}, handlers: {'14-0': {"change": function proxy () {
  var $wx = arguments[arguments.length - 1].$wx;
  var $event = ($wx.detail && $wx.detail.arguments) ? $wx.detail.arguments[0] : arguments[arguments.length -1];
  var $args = $wx.detail && $wx.detail.arguments;
  var _vm=this;
  return (function () {
    _vm.onChangeSoilType.apply(_vm, $args || [$event]);
  })();
}},'14-1': {"change": function proxy () {
  var $wx = arguments[arguments.length - 1].$wx;
  var $event = ($wx.detail && $wx.detail.arguments) ? $wx.detail.arguments[0] : arguments[arguments.length -1];
  var $args = $wx.detail && $wx.detail.arguments;
  var _vm=this;
  return (function () {
    _vm.onChangeRedVariety.apply(_vm, $args || [$event]);
  })();
}},'14-2': {"change": function proxy () {
  var $wx = arguments[arguments.length - 1].$wx;
  var $event = ($wx.detail && $wx.detail.arguments) ? $wx.detail.arguments[0] : arguments[arguments.length -1];
  var $args = $wx.detail && $wx.detail.arguments;
  var _vm=this;
  return (function () {
    _vm.onChangeWhiteVariety.apply(_vm, $args || [$event]);
  })();
}},'14-3': {"change": function proxy () {
  var $wx = arguments[arguments.length - 1].$wx;
  var $event = ($wx.detail && $wx.detail.arguments) ? $wx.detail.arguments[0] : arguments[arguments.length -1];
  var $args = $wx.detail && $wx.detail.arguments;
  var _vm=this;
  return (function () {
    _vm.onChangeIrrigationType.apply(_vm, $args || [$event]);
  })();
}},'14-4': {"tap": function proxy () {
    var _vm=this;
  return (function () {
    _vm.onBack();
  })();
}},'14-5': {"tap": function proxy () {
    var _vm=this;
  return (function () {
    _vm.onNext();
  })();
}}}, models: {'6': {
      type: "input",
      expr: "wineryForm.plantArea",
      handler: function set ($v) {
      var _vm=this;
        _vm.$set(_vm.wineryForm, "plantArea", $v);
      
    }
    },'7': {
      type: "input",
      expr: "wineryForm.plantWeight",
      handler: function set ($v) {
      var _vm=this;
        _vm.$set(_vm.wineryForm, "plantWeight", $v);
      
    }
    },'8': {
      type: "input",
      expr: "item.msg.area",
      handler: function set ($v, $p) {
    var _vm=this;
      function fn1(item) {
          _vm.$set(item.msg, "area", $v);
        }
      return fn1((_vm.wineryForm.redVariety.params)[$p[0]]);
    
  }
    },'9': {
      type: "input",
      expr: "item.msg.treeOld",
      handler: function set ($v, $p) {
    var _vm=this;
      function fn1(item) {
          _vm.$set(item.msg, "treeOld", $v);
        }
      return fn1((_vm.wineryForm.redVariety.params)[$p[0]]);
    
  }
    },'10': {
      type: "input",
      expr: "item.msg.treeMaxOld",
      handler: function set ($v, $p) {
    var _vm=this;
      function fn1(item) {
          _vm.$set(item.msg, "treeMaxOld", $v);
        }
      return fn1((_vm.wineryForm.redVariety.params)[$p[0]]);
    
  }
    },'11': {
      type: "input",
      expr: "item.msg.area",
      handler: function set ($v, $p) {
    var _vm=this;
      function fn1(item) {
          _vm.$set(item.msg, "area", $v);
        }
      return fn1((_vm.wineryForm.whiteVariety.params)[$p[0]]);
    
  }
    },'12': {
      type: "input",
      expr: "item.msg.treeOld",
      handler: function set ($v, $p) {
    var _vm=this;
      function fn1(item) {
          _vm.$set(item.msg, "treeOld", $v);
        }
      return fn1((_vm.wineryForm.whiteVariety.params)[$p[0]]);
    
  }
    },'13': {
      type: "input",
      expr: "item.msg.treeMaxOld",
      handler: function set ($v, $p) {
    var _vm=this;
      function fn1(item) {
          _vm.$set(item.msg, "treeMaxOld", $v);
        }
      return fn1((_vm.wineryForm.whiteVariety.params)[$p[0]]);
    
  }
    }}, refs: undefined });