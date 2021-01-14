import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import ServiceOrder from './service-order';
import ServiceOrderDetail from './service-order-detail';
import ServiceOrderUpdate from './service-order-update';
import ServiceOrderDeleteDialog from './service-order-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={ServiceOrderUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={ServiceOrderUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={ServiceOrderDetail} />
      <ErrorBoundaryRoute path={match.url} component={ServiceOrder} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={ServiceOrderDeleteDialog} />
  </>
);

export default Routes;
