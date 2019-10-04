import React from 'react'
import '../asserts/css/App.css'
import 'antd/dist/antd.css'
import { Input, Button, List } from 'antd'

export const TodoListUI = (props) => {
  let { inputValue, changeInputValue, list, addClick, delClick } = props;
  return (
    <div style={{ margin: '10px', width: '300px' }}>
      <Input
        placeholder={inputValue}
        style={{ width: '250px', marginRight: '10px' }}
        onChange={changeInputValue}
        value={inputValue}
      />
      <Button
        type="primary"
        onClick={addClick}
      >增加</Button>
      <List
        bordered
        dataSource={list}
        renderItem={(item, index) => (
          <List.Item onClick={() => { delClick(index) }}>
            {item}
          </List.Item>
        )}
      />
    </div>
  )
}
