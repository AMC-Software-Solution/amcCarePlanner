import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import SystemSetting from './system-setting';
import SystemSettingDetail from './system-setting-detail';
import SystemSettingUpdate from './system-setting-update';
import SystemSettingDeleteDialog from './system-setting-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={SystemSettingUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={SystemSettingUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={SystemSettingDetail} />
      <ErrorBoundaryRoute path={match.url} component={SystemSetting} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={SystemSettingDeleteDialog} />
  </>
);

export default Routes;
