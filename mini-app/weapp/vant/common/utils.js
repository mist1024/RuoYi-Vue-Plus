"use strict";

Object.defineProperty(exports, "__esModule", {
  value: true
});
exports.range = range;
exports.nextTick = nextTick;
exports.getSystemInfoSync = getSystemInfoSync;
exports.addUnit = addUnit;
exports.requestAnimationFrame = requestAnimationFrame;
exports.pickExclude = pickExclude;
exports.getRect = getRect;
exports.getAllRect = getAllRect;
exports.groupSetData = groupSetData;
exports.toPromise = toPromise;

var _validator = require('./validator.js');

var _version = require('./version.js');

function range(num, min, max) {
  return Math.min(Math.max(num, min), max);
}

function nextTick(cb) {
  if ((0, _version.canIUseNextTick)()) {
    wx.nextTick(cb);
  } else {
    setTimeout(function () {
      cb();
    }, 1000 / 30);
  }
}

var systemInfo;

function getSystemInfoSync() {
  if (systemInfo == null) {
    systemInfo = wx.getSystemInfoSync();
  }

  return systemInfo;
}

function addUnit(value) {
  if (!(0, _validator.isDef)(value)) {
    return undefined;
  }

  value = String(value);
  return (0, _validator.isNumber)(value) ? "".concat(value, "px") : value;
}

function requestAnimationFrame(cb) {
  var systemInfo = getSystemInfoSync();

  if (systemInfo.platform === 'devtools') {
    return setTimeout(function () {
      cb();
    }, 1000 / 30);
  }

  return wx.createSelectorQuery().selectViewport().boundingClientRect().exec(function () {
    cb();
  });
}

function pickExclude(obj, keys) {
  if (!(0, _validator.isPlainObject)(obj)) {
    return {};
  }

  return Object.keys(obj).reduce(function (prev, key) {
    if (!keys.includes(key)) {
      prev[key] = obj[key];
    }

    return prev;
  }, {});
}

function getRect(context, selector) {
  return new Promise(function (resolve) {
    wx.createSelectorQuery()["in"](context).select(selector).boundingClientRect().exec(function () {
      var rect = arguments.length > 0 && arguments[0] !== undefined ? arguments[0] : [];
      return resolve(rect[0]);
    });
  });
}

function getAllRect(context, selector) {
  return new Promise(function (resolve) {
    wx.createSelectorQuery()["in"](context).selectAll(selector).boundingClientRect().exec(function () {
      var rect = arguments.length > 0 && arguments[0] !== undefined ? arguments[0] : [];
      return resolve(rect[0]);
    });
  });
}

function groupSetData(context, cb) {
  if ((0, _version.canIUseGroupSetData)()) {
    context.groupSetData(cb);
  } else {
    cb();
  }
}

function toPromise(promiseLike) {
  if ((0, _validator.isPromise)(promiseLike)) {
    return promiseLike;
  }

  return Promise.resolve(promiseLike);
}