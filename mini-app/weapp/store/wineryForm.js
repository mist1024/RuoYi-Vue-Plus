"use strict";

Object.defineProperty(exports, "__esModule", {
  value: true
});
exports.wineryForm = void 0;
var wineryForm = {
  openid: '',
  email: '',
  personName: '',
  mobile: '',
  wineryName: '',
  // 酒庄名称（具体）
  buildTime: '',
  // 建立时间（具体）
  region: ['宁夏回族自治区', '银川市', '西夏区'],
  // 酒庄地址（区）
  address: '',
  // 酒庄地址（具体）
  wineryArea: '',
  // 总面积
  buildArea: '',
  // 建筑面积
  wineryStatus: '已建成投产',
  // 酒庄现状
  plantArea: '',
  // 面积（亩）
  plantWeight: '',
  // 产量（斤）
  soilType: '沙粒',
  // 土壤类型
  redVariety: {
    params: [],
    checked: []
  },
  // 红葡萄品种
  whiteVariety: {
    params: [],
    checked: []
  },
  // 白葡萄品种
  irrigationType: '漫灌',
  // 灌溉方式
  fermentationProcess: [],
  // 发酵工艺
  container: [],
  // 热化容器
  clarificationMethod: [],
  // 澄清方式
  annualOutput: '',
  // 年产量
  stock: '',
  // 库存
  bucketCount: '',
  // 酒桶数量
  mainPrice: [],
  // 主要产品定价
  salesMode: [],
  // 销售方式
  awards: [],
  // 获奖信息
  awardInformation: '',
  // 获奖信息
  slogan: '葡萄酒·贺兰山' // 征集口号

};
exports.wineryForm = wineryForm;