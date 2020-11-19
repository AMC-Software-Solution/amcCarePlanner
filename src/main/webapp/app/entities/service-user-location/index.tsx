import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import ServiceUserLocation from './service-user-location';
import ServiceUserLocationDetail from './service-user-location-detail';
import ServiceUserLocationUpdate from './service-user-location-update';
import ServiceUserLocationDeleteDialog from './service-user-location-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={ServiceUserLocationUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={ServiceUserLocationUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={ServiceUserLocationDetail} />
      <ErrorBoundaryRoute path={match.url} component={ServiceUserLocation} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={ServiceUserLocationDeleteDialog} />
  </>
);

export default Routes;
