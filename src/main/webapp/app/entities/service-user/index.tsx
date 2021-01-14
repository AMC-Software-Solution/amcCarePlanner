import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import ServiceUser from './service-user';
import ServiceUserDetail from './service-user-detail';
import ServiceUserUpdate from './service-user-update';
import ServiceUserDeleteDialog from './service-user-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={ServiceUserUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={ServiceUserUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={ServiceUserDetail} />
      <ErrorBoundaryRoute path={match.url} component={ServiceUser} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={ServiceUserDeleteDialog} />
  </>
);

export default Routes;
