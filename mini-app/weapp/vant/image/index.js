"use strict";

var _component = require('./../common/component.js');

var _button = require('./../mixins/button.js');

var _openType = require('./../mixins/open-type.js');

(0, _component.VantComponent)({
  mixins: [_button.button, _openType.openType],
  classes: ['custom-class', 'loading-class', 'error-class', 'image-class'],
  props: {
    src: {
      type: String,
      observer: function observer() {
        this.setData({
          error: false,
          loading: true
        });
      }
    },
    round: Boolean,
    width: null,
    height: null,
    radius: null,
    lazyLoad: Boolean,
    useErrorSlot: Boolean,
    useLoadingSlot: Boolean,
    showMenuByLongpress: Boolean,
    fit: {
      type: String,
      value: 'fill'
    },
    showError: {
      type: Boolean,
      value: true
    },
    showLoading: {
      type: Boolean,
      value: true
    }
  },
  data: {
    error: false,
    loading: true,
    viewStyle: ''
  },
  methods: {
    onLoad: function onLoad(event) {
      this.setData({
        loading: false
      });
      this.$emit('load', event.detail);
    },
    onError: function onError(event) {
      this.setData({
        loading: false,
        error: true
      });
      this.$emit('error', event.detail);
    },
    onClick: function onClick(event) {
      this.$emit('click', event.detail);
    }
  }
});