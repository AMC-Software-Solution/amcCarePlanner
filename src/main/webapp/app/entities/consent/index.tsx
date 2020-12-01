import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Consent from './consent';
import ConsentDetail from './consent-detail';
import ConsentUpdate from './consent-update';
import ConsentDeleteDialog from './consent-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={ConsentUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={ConsentUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={ConsentDetail} />
      <ErrorBoundaryRoute path={match.url} component={Consent} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={ConsentDeleteDialog} />
  </>
);

export default Routes;
