"use strict";

var _component = require('./../common/component.js');

var _button = require('./../mixins/button.js');

var _openType = require('./../mixins/open-type.js');

var _color = require('./../common/color.js');

var _utils = require('./../common/utils.js');

(0, _component.VantComponent)({
  mixins: [_button.button, _openType.openType],
  props: {
    show: {
      type: Boolean,
      observer: function observer(show) {
        !show && this.stopLoading();
      }
    },
    title: String,
    message: String,
    theme: {
      type: String,
      value: 'default'
    },
    useSlot: Boolean,
    className: String,
    customStyle: String,
    asyncClose: Boolean,
    messageAlign: String,
    beforeClose: null,
    overlayStyle: String,
    useTitleSlot: Boolean,
    showCancelButton: Boolean,
    closeOnClickOverlay: Boolean,
    confirmButtonOpenType: String,
    width: null,
    zIndex: {
      type: Number,
      value: 2000
    },
    confirmButtonText: {
      type: String,
      value: '确认'
    },
    cancelButtonText: {
      type: String,
      value: '取消'
    },
    confirmButtonColor: {
      type: String,
      value: _color.RED
    },
    cancelButtonColor: {
      type: String,
      value: _color.GRAY
    },
    showConfirmButton: {
      type: Boolean,
      value: true
    },
    overlay: {
      type: Boolean,
      value: true
    },
    transition: {
      type: String,
      value: 'scale'
    }
  },
  data: {
    loading: {
      confirm: false,
      cancel: false
    }
  },
  methods: {
    onConfirm: function onConfirm() {
      this.handleAction('confirm');
    },
    onCancel: function onCancel() {
      this.handleAction('cancel');
    },
    onClickOverlay: function onClickOverlay() {
      this.onClose('overlay');
    },
    close: function close(action) {
      var _this = this;

      this.setData({
        show: false
      });
      wx.nextTick(function () {
        _this.$emit('close', action);

        var callback = _this.data.callback;

        if (callback) {
          callback(action, _this);
        }
      });
    },
    stopLoading: function stopLoading() {
      this.setData({
        loading: {
          confirm: false,
          cancel: false
        }
      });
    },
    handleAction: function handleAction(action) {
      var _this2 = this;

      this.$emit(action, {
        dialog: this
      });
      var _this$data = this.data,
          asyncClose = _this$data.asyncClose,
          beforeClose = _this$data.beforeClose;

      if (!asyncClose && !beforeClose) {
        this.close(action);
        return;
      } // this.setData({
      //   [`loading.${action}`]: true,
      // });


      if (beforeClose) {
        (0, _utils.toPromise)(beforeClose(action)).then(function (value) {
          if (value) {
            _this2.close(action);
          } else {
            _this2.stopLoading();
          }
        });
      }
    }
  }
});