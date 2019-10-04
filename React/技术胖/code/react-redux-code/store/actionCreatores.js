
/**
 * 返回公共action对象
 * @param {传过来的值} value 
 * @param {reducer中的类型} type 
 */
export const commonAction = (value, type) => ({
    value: value,
    type: type
})
