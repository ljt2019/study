
// import {createStore, applyMiddleware} from 'redux'
// import reducer from './reducer'
// // 创建数据存储仓库
// const store = createStore(reducer,applyMiddleware(thunk)) //推荐使用下面那中，这种使用不了Redux Dev Tools
// const store = createStore(reducer,window.__REDUX_DEVTOOLS_EXTENSION__ && window.__REDUX_DEVTOOLS_EXTENSION__()) 
// //将仓库暴露出去
// export default store

/******************* 引入Redux-thunk的写法  *******************/
import { createStore, applyMiddleware, compose } from 'redux'
import reducer from './reducer'
import thunk from 'redux-thunk'
//增强函数
const composeEnhancers = window.__REDUX_DEVTOOLS_EXTENSION_COMPOSE__ ? window.__REDUX_DEVTOOLS_EXTENSION_COMPOSE__({}) : compose
const enhancer = composeEnhancers(applyMiddleware(thunk))
// 创建数据存储仓库
const store = createStore(reducer, enhancer)
//将仓库暴露出去
export default store   