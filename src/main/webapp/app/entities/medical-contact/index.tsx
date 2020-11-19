import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import MedicalContact from './medical-contact';
import MedicalContactDetail from './medical-contact-detail';
import MedicalContactUpdate from './medical-contact-update';
import MedicalContactDeleteDialog from './medical-contact-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={MedicalContactUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={MedicalContactUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={MedicalContactDetail} />
      <ErrorBoundaryRoute path={match.url} component={MedicalContact} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={MedicalContactDeleteDialog} />
  </>
);

export default Routes;
