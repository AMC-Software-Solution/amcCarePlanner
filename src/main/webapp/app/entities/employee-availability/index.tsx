import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import EmployeeAvailability from './employee-availability';
import EmployeeAvailabilityDetail from './employee-availability-detail';
import EmployeeAvailabilityUpdate from './employee-availability-update';
import EmployeeAvailabilityDeleteDialog from './employee-availability-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={EmployeeAvailabilityUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={EmployeeAvailabilityUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={EmployeeAvailabilityDetail} />
      <ErrorBoundaryRoute path={match.url} component={EmployeeAvailability} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={EmployeeAvailabilityDeleteDialog} />
  </>
);

export default Routes;
