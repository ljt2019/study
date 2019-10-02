
/**
 * 公共action
 * @param {值} value 
 * @param {索引} index 
 * @param {reducer中的类型} type 
 */
export const commonAction = (value, index, type) => ({
    type: type,
    value: value,
    index: index
})
