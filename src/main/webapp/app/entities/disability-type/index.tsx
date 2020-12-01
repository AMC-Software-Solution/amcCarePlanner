import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import DisabilityType from './disability-type';
import DisabilityTypeDetail from './disability-type-detail';
import DisabilityTypeUpdate from './disability-type-update';
import DisabilityTypeDeleteDialog from './disability-type-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={DisabilityTypeUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={DisabilityTypeUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={DisabilityTypeDetail} />
      <ErrorBoundaryRoute path={match.url} component={DisabilityType} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={DisabilityTypeDeleteDialog} />
  </>
);

export default Routes;
