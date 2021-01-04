/**
 * 根据字典值获取名称
 * @param value
 * @param options
 * @returns {string}
 */
export function getDictName(value,options) {
  let actions = [];
  Object.keys(options).some((key) => {
    if (options[key].dictValue == ('' + value)) {
      actions.push(options[key].dictLabel);
      return true;
    }
  })
  return actions.join('');
}
