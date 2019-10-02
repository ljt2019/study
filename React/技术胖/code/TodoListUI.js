import React from 'react'
import '../asserts/css/App.css'
import 'antd/dist/antd.css'
import { Input, Button, List } from 'antd'

export const TodoListUI = (props) => {
  return (
    <div style={{ margin: '10px', width: '300px' }}>
      <Input
        placeholder={props.inputValue}
        style={{ width: '250px', marginRight: '10px' }}
        onChange={props.changeInputValue}
        value={props.inputValue}
      />
      <Button
        type="primary"
        onClick={props.addClick}
      >增加</Button>
      <List
        bordered
        dataSource={props.list}
        renderItem={(item, index) => (
          <List.Item onClick={() => { props.delClick(index) }}>
            {item}
          </List.Item>
        )}
      />
    </div>
  )
}
