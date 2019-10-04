import React from 'react'
import { CHANGE_INPUT, ADD_ITEM, DELETE_ITEM } from '../store/actionTypes'
import { commonAction } from '../store/actionCreatores'
import { TodoListUI } from './TodoListUI'
import { connect } from "react-redux";

const TodoList = (props) => {

  let { inputValue, changeInputValue, list, addClick, delClick } = props;

  return (
    <TodoListUI
      inputValue={inputValue}
      changeInputValue={changeInputValue}
      list={list}
      addClick={addClick}
      delClick={delClick}
    />
  )
}

//映射state成props
const stateToProps = (state) => {
  return {
    inputValue: state.inputValue,
    list: state.list
  }
}

//映射dispatch成屬性
const dispatchToProps = (dispatch) => {
  return {
    //改变输入框的值
    changeInputValue(e) {
      dispatch(commonAction(e.target.value, CHANGE_INPUT))
    },
    //添加列
    addClick() {
      dispatch(commonAction(null, ADD_ITEM))
    },
    //删除列
    delClick(e) {
      dispatch(commonAction(e, DELETE_ITEM))
    },
  }
}

//连接
export default connect(stateToProps, dispatchToProps)(TodoList);
