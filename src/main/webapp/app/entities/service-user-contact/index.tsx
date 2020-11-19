import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import ServiceUserContact from './service-user-contact';
import ServiceUserContactDetail from './service-user-contact-detail';
import ServiceUserContactUpdate from './service-user-contact-update';
import ServiceUserContactDeleteDialog from './service-user-contact-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={ServiceUserContactUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={ServiceUserContactUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={ServiceUserContactDetail} />
      <ErrorBoundaryRoute path={match.url} component={ServiceUserContact} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={ServiceUserContactDeleteDialog} />
  </>
);

export default Routes;
