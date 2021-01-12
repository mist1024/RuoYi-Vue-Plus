"use strict";

var _component = require('./../common/component.js');

var _utils = require('./../common/utils.js');

(0, _component.VantComponent)({
  classes: ['title-class'],
  props: {
    title: String,
    fixed: {
      type: Boolean,
      observer: 'setHeight'
    },
    placeholder: {
      type: Boolean,
      observer: 'setHeight'
    },
    leftText: String,
    rightText: String,
    customStyle: String,
    leftArrow: Boolean,
    border: {
      type: Boolean,
      value: true
    },
    zIndex: {
      type: Number,
      value: 1
    },
    safeAreaInsetTop: {
      type: Boolean,
      value: true
    }
  },
  data: {
    height: 46
  },
  created: function created() {
    var _getSystemInfoSync = (0, _utils.getSystemInfoSync)(),
        statusBarHeight = _getSystemInfoSync.statusBarHeight;

    this.setData({
      statusBarHeight: statusBarHeight,
      height: 46 + statusBarHeight
    });
  },
  mounted: function mounted() {
    this.setHeight();
  },
  methods: {
    onClickLeft: function onClickLeft() {
      this.$emit('click-left');
    },
    onClickRight: function onClickRight() {
      this.$emit('click-right');
    },
    setHeight: function setHeight() {
      var _this = this;

      if (!this.data.fixed || !this.data.placeholder) {
        return;
      }

      wx.nextTick(function () {
        (0, _utils.getRect)(_this, '.van-nav-bar').then(function (res) {
          if (res && 'height' in res) {
            _this.setData({
              height: res.height
            });
          }
        });
      });
    }
  }
});