import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import ServiceUserEvent from './service-user-event';
import ServiceUserEventDetail from './service-user-event-detail';
import ServiceUserEventUpdate from './service-user-event-update';
import ServiceUserEventDeleteDialog from './service-user-event-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={ServiceUserEventUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={ServiceUserEventUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={ServiceUserEventDetail} />
      <ErrorBoundaryRoute path={match.url} component={ServiceUserEvent} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={ServiceUserEventDeleteDialog} />
  </>
);

export default Routes;
