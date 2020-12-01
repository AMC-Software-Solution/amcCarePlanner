import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import CarerClientRelation from './carer-client-relation';
import CarerClientRelationDetail from './carer-client-relation-detail';
import CarerClientRelationUpdate from './carer-client-relation-update';
import CarerClientRelationDeleteDialog from './carer-client-relation-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={CarerClientRelationUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={CarerClientRelationUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={CarerClientRelationDetail} />
      <ErrorBoundaryRoute path={match.url} component={CarerClientRelation} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={CarerClientRelationDeleteDialog} />
  </>
);

export default Routes;
