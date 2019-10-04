
import { CHANGE_INPUT, ADD_ITEM, DELETE_ITEM, GET_DATA } from './actionTypes'

//默认数据
const defaultStore = {
    inputValue: 'Write Something',
    list: []
}
//state: 指的是原始仓库里的状态。action: 指的是action新传递的状态。
//Reducer里只能接收state，不能改变state。）,所以我们声明了一个新变量，然后再次用return返回回去。
export default (state = defaultStore, action) => {

    //值改变
    if (action.type === CHANGE_INPUT) {
        let newState = JSON.parse(JSON.stringify(state)) //深度拷贝state
        newState.inputValue = action.value
        return newState
    }

    //获取list数据集
    else if (action.type === GET_DATA) {
        let newState = JSON.parse(JSON.stringify(state))
        newState.list = action.value.data.list 
        return newState
    }

    //state值只能传递，不能使用
    else if (action.type === ADD_ITEM) { //根据type值，编写业务逻辑
        let newState = JSON.parse(JSON.stringify(state))
        newState.list.push(state.inputValue)  //push新的内容到列表中去
        newState.inputValue = ''
        return newState
    }

    //删除
    else if (action.type === DELETE_ITEM) {
        let newState = JSON.parse(JSON.stringify(state))
        newState.list.splice(action.value, 1)  //删除数组中对应的值
        return newState
    }

    else {
        return state
    }
}