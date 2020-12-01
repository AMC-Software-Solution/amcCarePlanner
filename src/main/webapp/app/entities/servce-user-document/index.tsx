import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import ServceUserDocument from './servce-user-document';
import ServceUserDocumentDetail from './servce-user-document-detail';
import ServceUserDocumentUpdate from './servce-user-document-update';
import ServceUserDocumentDeleteDialog from './servce-user-document-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={ServceUserDocumentUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={ServceUserDocumentUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={ServceUserDocumentDetail} />
      <ErrorBoundaryRoute path={match.url} component={ServceUserDocument} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={ServceUserDocumentDeleteDialog} />
  </>
);

export default Routes;
