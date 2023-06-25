import CryptoJS from "crypto-js";
const key = CryptoJS.enc.Utf8.parse("abcdefghijklmnop")

// 加密
export function encryptAES(data) {
  var encrypted = CryptoJS.AES.encrypt(data, key, {
    "mode": CryptoJS.mode.ECB,
    "padding": CryptoJS.pad.Pkcs7
  });
  return encrypted.toString();
}

// 解密
export function decryptAES(ciphertext) {
  var decrypted = CryptoJS.AES.decrypt(ciphertext, key, {
    "mode": CryptoJS.mode.ECB,
    "padding": CryptoJS.pad.Pkcs7
  });
  return decrypted.toString(CryptoJS.enc.Utf8);
}
