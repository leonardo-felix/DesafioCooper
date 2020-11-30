import React from 'react';

import { BrowserRouter, Switch, Route, Redirect } from 'react-router-dom';

import Login from './pages/login/Login';
import Home from './pages/home/Home';
import TorcedorForm from './pages/torcedor/TorcedorForm';

export default function Routes() {
  return (
    <BrowserRouter>
      <Switch>
        <Route path="/" exact>
          <Redirect to="/login" />
        </Route>
        <Route path="/home" component={Home} />
        <Route path="/login" component={Login} />
        <Route path="/torcedor/new" component={TorcedorForm} />
        <Route path="/torcedor/edit/:id" component={TorcedorForm} />
        <Route path="/torcedor/view/:id" component={TorcedorForm} />
      </Switch>
    </BrowserRouter>
  );
}
