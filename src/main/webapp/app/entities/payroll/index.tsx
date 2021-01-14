import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Payroll from './payroll';
import PayrollDetail from './payroll-detail';
import PayrollUpdate from './payroll-update';
import PayrollDeleteDialog from './payroll-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={PayrollUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={PayrollUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={PayrollDetail} />
      <ErrorBoundaryRoute path={match.url} component={Payroll} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={PayrollDeleteDialog} />
  </>
);

export default Routes;
