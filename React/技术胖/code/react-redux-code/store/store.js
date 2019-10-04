
import {createStore} from 'redux'
import reducer from './reducer'
// 创建数据存储仓库
const store = createStore(reducer,window.__REDUX_DEVTOOLS_EXTENSION__ && window.__REDUX_DEVTOOLS_EXTENSION__()) 
//将仓库暴露出去
export default store