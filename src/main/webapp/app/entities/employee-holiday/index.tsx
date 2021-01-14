import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import EmployeeHoliday from './employee-holiday';
import EmployeeHolidayDetail from './employee-holiday-detail';
import EmployeeHolidayUpdate from './employee-holiday-update';
import EmployeeHolidayDeleteDialog from './employee-holiday-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={EmployeeHolidayUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={EmployeeHolidayUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={EmployeeHolidayDetail} />
      <ErrorBoundaryRoute path={match.url} component={EmployeeHoliday} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={EmployeeHolidayDeleteDialog} />
  </>
);

export default Routes;
