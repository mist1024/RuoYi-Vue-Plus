"use strict";

Object.defineProperty(exports, "__esModule", {
  value: true
});
exports.link = void 0;
var link = Behavior({
  properties: {
    url: String,
    linkType: {
      type: String,
      value: 'navigateTo'
    }
  },
  methods: {
    jumpLink: function jumpLink() {
      var urlKey = arguments.length > 0 && arguments[0] !== undefined ? arguments[0] : 'url';
      var url = this.data[urlKey];

      if (url) {
        if (this.data.linkType === 'navigateTo' && getCurrentPages().length > 9) {
          wx.redirectTo({
            url: url
          });
        } else {
          wx[this.data.linkType]({
            url: url
          });
        }
      }
    }
  }
});
exports.link = link;