"use strict";

Object.defineProperty(exports, "__esModule", {
  value: true
});
exports["default"] = void 0;

function _classCallCheck(instance, Constructor) { if (!(instance instanceof Constructor)) { throw new TypeError("Cannot call a class as a function"); } }

function _defineProperties(target, props) { for (var i = 0; i < props.length; i++) { var descriptor = props[i]; descriptor.enumerable = descriptor.enumerable || false; descriptor.configurable = true; if ("value" in descriptor) descriptor.writable = true; Object.defineProperty(target, descriptor.key, descriptor); } }

function _createClass(Constructor, protoProps, staticProps) { if (protoProps) _defineProperties(Constructor.prototype, protoProps); if (staticProps) _defineProperties(Constructor, staticProps); return Constructor; }

var ValidateUtils = /*#__PURE__*/function () {
  function ValidateUtils() {
    _classCallCheck(this, ValidateUtils);
  }

  _createClass(ValidateUtils, [{
    key: "isEmail",
    value: function isEmail(value) {
      var reg = /^([a-zA-Z]|[0-9])(\w|\-)+@[a-zA-Z0-9]+\.([a-zA-Z]{2,4})$/;
      return reg.test(value);
    }
    /**
     * 校验数字或者小数
     * @param text
     * @returns {boolean}
     */

  }, {
    key: "isIntOrDecimal",
    value: function isIntOrDecimal(text) {
      var pattern = /^[0-9]+([.]{1}[0-9]+){0,1}$/;
      return pattern.test(text);
    }
  }]);

  return ValidateUtils;
}();

var _default = new ValidateUtils();

exports["default"] = _default;