import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import RelationshipType from './relationship-type';
import RelationshipTypeDetail from './relationship-type-detail';
import RelationshipTypeUpdate from './relationship-type-update';
import RelationshipTypeDeleteDialog from './relationship-type-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={RelationshipTypeUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={RelationshipTypeUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={RelationshipTypeDetail} />
      <ErrorBoundaryRoute path={match.url} component={RelationshipType} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={RelationshipTypeDeleteDialog} />
  </>
);

export default Routes;
