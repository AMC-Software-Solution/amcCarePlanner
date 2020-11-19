import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import TenantDocument from './tenant-document';
import TenantDocumentDetail from './tenant-document-detail';
import TenantDocumentUpdate from './tenant-document-update';
import TenantDocumentDeleteDialog from './tenant-document-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={TenantDocumentUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={TenantDocumentUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={TenantDocumentDetail} />
      <ErrorBoundaryRoute path={match.url} component={TenantDocument} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={TenantDocumentDeleteDialog} />
  </>
);

export default Routes;
