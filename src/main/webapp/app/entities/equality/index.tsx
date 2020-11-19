import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Equality from './equality';
import EqualityDetail from './equality-detail';
import EqualityUpdate from './equality-update';
import EqualityDeleteDialog from './equality-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={EqualityUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={EqualityUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={EqualityDetail} />
      <ErrorBoundaryRoute path={match.url} component={Equality} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={EqualityDeleteDialog} />
  </>
);

export default Routes;
