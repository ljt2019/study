import React from 'react';
import ReactDOM from 'react-dom';
import './asserts/css/index.css';
// import App from './component/App';
import TodoList from './component/TodoList';
import { Provider } from 'react-redux';
import store from './store/store'

const App = (
    //被 Provider 包裹的组件，可以使用store中的数据
    <Provider store={store}>
        <TodoList />
    </Provider>
)
ReactDOM.render(App, document.getElementById('root'));

// ReactDOM.render(<TodoList/>, document.getElementById('root'));


