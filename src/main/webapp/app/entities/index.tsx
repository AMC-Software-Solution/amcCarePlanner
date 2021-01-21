import React from 'react';
import { Switch } from 'react-router-dom';

// eslint-disable-next-line @typescript-eslint/no-unused-vars
import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Client from './client';
import Branch from './branch';
import ServiceUser from './service-user';
import MedicalContact from './medical-contact';
import ServiceUserContact from './service-user-contact';
import EmergencyContact from './emergency-contact';
import ServiceUserLocation from './service-user-location';
import PowerOfAttorney from './power-of-attorney';
import Consent from './consent';
import Access from './access';
import Equality from './equality';
import QuestionType from './question-type';
import Question from './question';
import Answer from './answer';
import EmployeeDesignation from './employee-designation';
import Employee from './employee';
import EmployeeLocation from './employee-location';
import EmployeeAvailability from './employee-availability';
import RelationshipType from './relationship-type';
import CarerServiceUserRelation from './carer-service-user-relation';
import DisabilityType from './disability-type';
import Disability from './disability';
import EligibilityType from './eligibility-type';
import Eligibility from './eligibility';
import Travel from './travel';
import Payroll from './payroll';
import EmployeeHoliday from './employee-holiday';
import ServceUserDocument from './servce-user-document';
import TerminalDevice from './terminal-device';
import EmployeeDocument from './employee-document';
import Communication from './communication';
import DocumentType from './document-type';
import Notification from './notification';
import Country from './country';
import Task from './task';
import ServiceUserEvent from './service-user-event';
import Currency from './currency';
import ServiceOrder from './service-order';
import Invoice from './invoice';
import Timesheet from './timesheet';
import ClientDocument from './client-document';
import SystemEventsHistory from './system-events-history';
import SystemSetting from './system-setting';
import ExtraData from './extra-data';
import Notifications from './notifications';
/* jhipster-needle-add-route-import - JHipster will add routes here */

const Routes = ({ match }) => (
  <div>
    <Switch>
      {/* prettier-ignore */}
      <ErrorBoundaryRoute path={`${match.url}client`} component={Client} />
      <ErrorBoundaryRoute path={`${match.url}branch`} component={Branch} />
      <ErrorBoundaryRoute path={`${match.url}service-user`} component={ServiceUser} />
      <ErrorBoundaryRoute path={`${match.url}medical-contact`} component={MedicalContact} />
      <ErrorBoundaryRoute path={`${match.url}service-user-contact`} component={ServiceUserContact} />
      <ErrorBoundaryRoute path={`${match.url}emergency-contact`} component={EmergencyContact} />
      <ErrorBoundaryRoute path={`${match.url}service-user-location`} component={ServiceUserLocation} />
      <ErrorBoundaryRoute path={`${match.url}power-of-attorney`} component={PowerOfAttorney} />
      <ErrorBoundaryRoute path={`${match.url}consent`} component={Consent} />
      <ErrorBoundaryRoute path={`${match.url}access`} component={Access} />
      <ErrorBoundaryRoute path={`${match.url}equality`} component={Equality} />
      <ErrorBoundaryRoute path={`${match.url}question-type`} component={QuestionType} />
      <ErrorBoundaryRoute path={`${match.url}question`} component={Question} />
      <ErrorBoundaryRoute path={`${match.url}answer`} component={Answer} />
      <ErrorBoundaryRoute path={`${match.url}employee-designation`} component={EmployeeDesignation} />
      <ErrorBoundaryRoute path={`${match.url}employee`} component={Employee} />
      <ErrorBoundaryRoute path={`${match.url}employee-location`} component={EmployeeLocation} />
      <ErrorBoundaryRoute path={`${match.url}employee-availability`} component={EmployeeAvailability} />
      <ErrorBoundaryRoute path={`${match.url}relationship-type`} component={RelationshipType} />
      <ErrorBoundaryRoute path={`${match.url}carer-service-user-relation`} component={CarerServiceUserRelation} />
      <ErrorBoundaryRoute path={`${match.url}disability-type`} component={DisabilityType} />
      <ErrorBoundaryRoute path={`${match.url}disability`} component={Disability} />
      <ErrorBoundaryRoute path={`${match.url}eligibility-type`} component={EligibilityType} />
      <ErrorBoundaryRoute path={`${match.url}eligibility`} component={Eligibility} />
      <ErrorBoundaryRoute path={`${match.url}travel`} component={Travel} />
      <ErrorBoundaryRoute path={`${match.url}payroll`} component={Payroll} />
      <ErrorBoundaryRoute path={`${match.url}employee-holiday`} component={EmployeeHoliday} />
      <ErrorBoundaryRoute path={`${match.url}servce-user-document`} component={ServceUserDocument} />
      <ErrorBoundaryRoute path={`${match.url}terminal-device`} component={TerminalDevice} />
      <ErrorBoundaryRoute path={`${match.url}employee-document`} component={EmployeeDocument} />
      <ErrorBoundaryRoute path={`${match.url}communication`} component={Communication} />
      <ErrorBoundaryRoute path={`${match.url}document-type`} component={DocumentType} />
      <ErrorBoundaryRoute path={`${match.url}notification`} component={Notification} />
      <ErrorBoundaryRoute path={`${match.url}country`} component={Country} />
      <ErrorBoundaryRoute path={`${match.url}task`} component={Task} />
      <ErrorBoundaryRoute path={`${match.url}service-user-event`} component={ServiceUserEvent} />
      <ErrorBoundaryRoute path={`${match.url}currency`} component={Currency} />
      <ErrorBoundaryRoute path={`${match.url}service-order`} component={ServiceOrder} />
      <ErrorBoundaryRoute path={`${match.url}invoice`} component={Invoice} />
      <ErrorBoundaryRoute path={`${match.url}timesheet`} component={Timesheet} />
      <ErrorBoundaryRoute path={`${match.url}client-document`} component={ClientDocument} />
      <ErrorBoundaryRoute path={`${match.url}system-events-history`} component={SystemEventsHistory} />
      <ErrorBoundaryRoute path={`${match.url}system-setting`} component={SystemSetting} />
      <ErrorBoundaryRoute path={`${match.url}extra-data`} component={ExtraData} />
      <ErrorBoundaryRoute path={`${match.url}notifications`} component={Notifications} />
      {/* jhipster-needle-add-route-path - JHipster will add routes here */}
    </Switch>
  </div>
);

export default Routes;
