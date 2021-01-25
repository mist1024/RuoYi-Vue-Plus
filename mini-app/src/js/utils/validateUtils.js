class ValidateUtils {
  isEmail(value) {

    const reg = /^([a-zA-Z]|[0-9])(\w|\-)+@[a-zA-Z0-9]+\.([a-zA-Z]{2,4})$/

    return reg.test(value)

  }

  /**
   * 校验数字或者小数
   * @param text
   * @returns {boolean}
   */
   isIntOrDecimal(text) {

     let pattern = /^[0-9]+([.]{1}[0-9]+){0,1}$/;
     return pattern.test(text)
   }

}

export default new ValidateUtils()
