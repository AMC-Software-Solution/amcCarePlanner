import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import EligibilityType from './eligibility-type';
import EligibilityTypeDetail from './eligibility-type-detail';
import EligibilityTypeUpdate from './eligibility-type-update';
import EligibilityTypeDeleteDialog from './eligibility-type-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={EligibilityTypeUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={EligibilityTypeUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={EligibilityTypeDetail} />
      <ErrorBoundaryRoute path={match.url} component={EligibilityType} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={EligibilityTypeDeleteDialog} />
  </>
);

export default Routes;
