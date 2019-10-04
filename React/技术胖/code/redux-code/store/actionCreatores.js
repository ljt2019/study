
import axios from "axios";
import { GET_DATA } from './actionTypes'

/**
 * 返回公共action对象
 * @param {传过来的值} value 
 * @param {reducer中的类型} type 
 */
export const commonAction = (value, type) => ({
    value: value,
    type: type
})

//使用 thunk 后可以返回一个方法
export const getTodoList = () => {
    return (dispatch) => {
        axios.get('https://www.easy-mock.com/mock/5cfcce489dc7c36bd6da2c99/xiaojiejie/getList').then((res) => {
            const data = res.data
            const action = commonAction(data, GET_DATA)
            dispatch(action)
        })
    }
}
