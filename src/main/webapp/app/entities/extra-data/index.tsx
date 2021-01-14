import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import ExtraData from './extra-data';
import ExtraDataDetail from './extra-data-detail';
import ExtraDataUpdate from './extra-data-update';
import ExtraDataDeleteDialog from './extra-data-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={ExtraDataUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={ExtraDataUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={ExtraDataDetail} />
      <ErrorBoundaryRoute path={match.url} component={ExtraData} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={ExtraDataDeleteDialog} />
  </>
);

export default Routes;
