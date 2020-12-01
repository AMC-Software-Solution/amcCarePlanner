import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import PowerOfAttorney from './power-of-attorney';
import PowerOfAttorneyDetail from './power-of-attorney-detail';
import PowerOfAttorneyUpdate from './power-of-attorney-update';
import PowerOfAttorneyDeleteDialog from './power-of-attorney-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={PowerOfAttorneyUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={PowerOfAttorneyUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={PowerOfAttorneyDetail} />
      <ErrorBoundaryRoute path={match.url} component={PowerOfAttorney} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={PowerOfAttorneyDeleteDialog} />
  </>
);

export default Routes;
