import React, { Component } from 'react';
import '../style/App.css';
import Footer from './Footer'
import Header from './Header'
import Main from './Main'
import Login from './Login'
import Register from './Register'
import Confirm from './Confirm'
import AddTest from './AddTest'
import UserPanel from './UserPanel'
import UserTestList from './UserTestList'
import TestView from './TestView'
import 'bootstrap/dist/css/bootstrap.css';

import { BrowserRouter, Route, Switch } from 'react-router-dom'

class App extends Component {
  render() {
    return (
      <BrowserRouter>
        <div className="App">
          <Header />
          <Switch>
            <Route exact path="/" component={Main} />
            <Route exact path="/login" component={Login} />
            <Route exact path="/register" component={Register} />
            <Route exact path="/userPanel" componets={UserPanel}/>
            <Route exact path="/confirm" component={Confirm}/>
            <Route exact path="/addTest" component ={AddTest} />
            <Route exact path="/testView" component ={TestView} />
            <Route exact path="/userTestList" component ={UserTestList} />
          </Switch>

        </div>
      </BrowserRouter>
    );
  }
}

export default App;
