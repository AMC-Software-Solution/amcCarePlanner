import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Disability from './disability';
import DisabilityDetail from './disability-detail';
import DisabilityUpdate from './disability-update';
import DisabilityDeleteDialog from './disability-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={DisabilityUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={DisabilityUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={DisabilityDetail} />
      <ErrorBoundaryRoute path={match.url} component={Disability} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={DisabilityDeleteDialog} />
  </>
);

export default Routes;
