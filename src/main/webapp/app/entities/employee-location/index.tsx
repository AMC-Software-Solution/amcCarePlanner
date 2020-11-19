import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import EmployeeLocation from './employee-location';
import EmployeeLocationDetail from './employee-location-detail';
import EmployeeLocationUpdate from './employee-location-update';
import EmployeeLocationDeleteDialog from './employee-location-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={EmployeeLocationUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={EmployeeLocationUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={EmployeeLocationDetail} />
      <ErrorBoundaryRoute path={match.url} component={EmployeeLocation} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={EmployeeLocationDeleteDialog} />
  </>
);

export default Routes;
