import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import EmployeeDocument from './employee-document';
import EmployeeDocumentDetail from './employee-document-detail';
import EmployeeDocumentUpdate from './employee-document-update';
import EmployeeDocumentDeleteDialog from './employee-document-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={EmployeeDocumentUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={EmployeeDocumentUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={EmployeeDocumentDetail} />
      <ErrorBoundaryRoute path={match.url} component={EmployeeDocument} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={EmployeeDocumentDeleteDialog} />
  </>
);

export default Routes;
