import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import TerminalDevice from './terminal-device';
import TerminalDeviceDetail from './terminal-device-detail';
import TerminalDeviceUpdate from './terminal-device-update';
import TerminalDeviceDeleteDialog from './terminal-device-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={TerminalDeviceUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={TerminalDeviceUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={TerminalDeviceDetail} />
      <ErrorBoundaryRoute path={match.url} component={TerminalDevice} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={TerminalDeviceDeleteDialog} />
  </>
);

export default Routes;
