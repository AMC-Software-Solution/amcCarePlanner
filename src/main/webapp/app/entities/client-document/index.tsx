import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import ClientDocument from './client-document';
import ClientDocumentDetail from './client-document-detail';
import ClientDocumentUpdate from './client-document-update';
import ClientDocumentDeleteDialog from './client-document-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={ClientDocumentUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={ClientDocumentUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={ClientDocumentDetail} />
      <ErrorBoundaryRoute path={match.url} component={ClientDocument} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={ClientDocumentDeleteDialog} />
  </>
);

export default Routes;
