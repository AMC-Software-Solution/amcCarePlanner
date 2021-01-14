import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import EmployeeDesignation from './employee-designation';
import EmployeeDesignationDetail from './employee-designation-detail';
import EmployeeDesignationUpdate from './employee-designation-update';
import EmployeeDesignationDeleteDialog from './employee-designation-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={EmployeeDesignationUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={EmployeeDesignationUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={EmployeeDesignationDetail} />
      <ErrorBoundaryRoute path={match.url} component={EmployeeDesignation} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={EmployeeDesignationDeleteDialog} />
  </>
);

export default Routes;
