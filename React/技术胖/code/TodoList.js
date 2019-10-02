import React, { Component } from 'react'
import store from '../store/store'
import { CHANGE_INPUT, ADD_ITEM, DELETE_ITEM } from '../store/actionTypes'
import { commonAction } from '../store/actionCreatores'
import { TodoListUI } from './TodoListUI'
import axios from "axios";

class TodoList extends Component {
  constructor(props) {
    super(props)
    //从数据仓库中获得数据，复制给state
    this.state = store.getState()
    //订阅Redux的状态
    store.subscribe(() => {
      this.setState(store.getState())
    })
  }

  //改变输入框的值
  changeInputValue = (e) => {
    store.dispatch(commonAction(e.target.value, null, CHANGE_INPUT))
  }
  //添加列
  addClick = () => {
    store.dispatch(commonAction(null, null, ADD_ITEM))
  }
  //删除列
  delClick = (index) => {
    store.dispatch(commonAction(null, index, DELETE_ITEM))
  }

  render() {
    return (
      <TodoListUI
        inputValue={this.state.inputValue}
        changeInputValue={this.changeInputValue}
        list={this.state.list}
        addClick={this.addClick}
        delClick={this.delClick}
      />
    )
  }
}
export default TodoList;
