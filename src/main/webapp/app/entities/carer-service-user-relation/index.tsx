import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import CarerServiceUserRelation from './carer-service-user-relation';
import CarerServiceUserRelationDetail from './carer-service-user-relation-detail';
import CarerServiceUserRelationUpdate from './carer-service-user-relation-update';
import CarerServiceUserRelationDeleteDialog from './carer-service-user-relation-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={CarerServiceUserRelationUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={CarerServiceUserRelationUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={CarerServiceUserRelationDetail} />
      <ErrorBoundaryRoute path={match.url} component={CarerServiceUserRelation} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={CarerServiceUserRelationDeleteDialog} />
  </>
);

export default Routes;
