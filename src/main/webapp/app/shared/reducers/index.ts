import { combineReducers } from 'redux';
import { loadingBarReducer as loadingBar } from 'react-redux-loading-bar';

import locale, { LocaleState } from './locale';
import authentication, { AuthenticationState } from './authentication';
import applicationProfile, { ApplicationProfileState } from './application-profile';

import administration, { AdministrationState } from 'app/modules/administration/administration.reducer';
import userManagement, { UserManagementState } from 'app/modules/administration/user-management/user-management.reducer';
import register, { RegisterState } from 'app/modules/account/register/register.reducer';
import activate, { ActivateState } from 'app/modules/account/activate/activate.reducer';
import password, { PasswordState } from 'app/modules/account/password/password.reducer';
import settings, { SettingsState } from 'app/modules/account/settings/settings.reducer';
import passwordReset, { PasswordResetState } from 'app/modules/account/password-reset/password-reset.reducer';
// prettier-ignore
import client, {
  ClientState
} from 'app/entities/client/client.reducer';
// prettier-ignore
import branch, {
  BranchState
} from 'app/entities/branch/branch.reducer';
// prettier-ignore
import serviceUser, {
  ServiceUserState
} from 'app/entities/service-user/service-user.reducer';
// prettier-ignore
import medicalContact, {
  MedicalContactState
} from 'app/entities/medical-contact/medical-contact.reducer';
// prettier-ignore
import serviceUserContact, {
  ServiceUserContactState
} from 'app/entities/service-user-contact/service-user-contact.reducer';
// prettier-ignore
import emergencyContact, {
  EmergencyContactState
} from 'app/entities/emergency-contact/emergency-contact.reducer';
// prettier-ignore
import serviceUserLocation, {
  ServiceUserLocationState
} from 'app/entities/service-user-location/service-user-location.reducer';
// prettier-ignore
import powerOfAttorney, {
  PowerOfAttorneyState
} from 'app/entities/power-of-attorney/power-of-attorney.reducer';
// prettier-ignore
import consent, {
  ConsentState
} from 'app/entities/consent/consent.reducer';
// prettier-ignore
import access, {
  AccessState
} from 'app/entities/access/access.reducer';
// prettier-ignore
import equality, {
  EqualityState
} from 'app/entities/equality/equality.reducer';
// prettier-ignore
import questionType, {
  QuestionTypeState
} from 'app/entities/question-type/question-type.reducer';
// prettier-ignore
import question, {
  QuestionState
} from 'app/entities/question/question.reducer';
// prettier-ignore
import answer, {
  AnswerState
} from 'app/entities/answer/answer.reducer';
// prettier-ignore
import employeeDesignation, {
  EmployeeDesignationState
} from 'app/entities/employee-designation/employee-designation.reducer';
// prettier-ignore
import employee, {
  EmployeeState
} from 'app/entities/employee/employee.reducer';
// prettier-ignore
import employeeLocation, {
  EmployeeLocationState
} from 'app/entities/employee-location/employee-location.reducer';
// prettier-ignore
import employeeAvailability, {
  EmployeeAvailabilityState
} from 'app/entities/employee-availability/employee-availability.reducer';
// prettier-ignore
import relationshipType, {
  RelationshipTypeState
} from 'app/entities/relationship-type/relationship-type.reducer';
// prettier-ignore
import carerServiceUserRelation, {
  CarerServiceUserRelationState
} from 'app/entities/carer-service-user-relation/carer-service-user-relation.reducer';
// prettier-ignore
import disabilityType, {
  DisabilityTypeState
} from 'app/entities/disability-type/disability-type.reducer';
// prettier-ignore
import disability, {
  DisabilityState
} from 'app/entities/disability/disability.reducer';
// prettier-ignore
import eligibilityType, {
  EligibilityTypeState
} from 'app/entities/eligibility-type/eligibility-type.reducer';
// prettier-ignore
import eligibility, {
  EligibilityState
} from 'app/entities/eligibility/eligibility.reducer';
// prettier-ignore
import travel, {
  TravelState
} from 'app/entities/travel/travel.reducer';
// prettier-ignore
import payroll, {
  PayrollState
} from 'app/entities/payroll/payroll.reducer';
// prettier-ignore
import employeeHoliday, {
  EmployeeHolidayState
} from 'app/entities/employee-holiday/employee-holiday.reducer';
// prettier-ignore
import servceUserDocument, {
  ServceUserDocumentState
} from 'app/entities/servce-user-document/servce-user-document.reducer';
// prettier-ignore
import terminalDevice, {
  TerminalDeviceState
} from 'app/entities/terminal-device/terminal-device.reducer';
// prettier-ignore
import employeeDocument, {
  EmployeeDocumentState
} from 'app/entities/employee-document/employee-document.reducer';
// prettier-ignore
import communication, {
  CommunicationState
} from 'app/entities/communication/communication.reducer';
// prettier-ignore
import documentType, {
  DocumentTypeState
} from 'app/entities/document-type/document-type.reducer';
// prettier-ignore
import notification, {
  NotificationState
} from 'app/entities/notification/notification.reducer';
// prettier-ignore
import country, {
  CountryState
} from 'app/entities/country/country.reducer';
// prettier-ignore
import task, {
  TaskState
} from 'app/entities/task/task.reducer';
// prettier-ignore
import serviceUserEvent, {
  ServiceUserEventState
} from 'app/entities/service-user-event/service-user-event.reducer';
// prettier-ignore
import currency, {
  CurrencyState
} from 'app/entities/currency/currency.reducer';
// prettier-ignore
import serviceOrder, {
  ServiceOrderState
} from 'app/entities/service-order/service-order.reducer';
// prettier-ignore
import invoice, {
  InvoiceState
} from 'app/entities/invoice/invoice.reducer';
// prettier-ignore
import timesheet, {
  TimesheetState
} from 'app/entities/timesheet/timesheet.reducer';
// prettier-ignore
import clientDocument, {
  ClientDocumentState
} from 'app/entities/client-document/client-document.reducer';
// prettier-ignore
import systemEventsHistory, {
  SystemEventsHistoryState
} from 'app/entities/system-events-history/system-events-history.reducer';
// prettier-ignore
import systemSetting, {
  SystemSettingState
} from 'app/entities/system-setting/system-setting.reducer';
// prettier-ignore
import extraData, {
  ExtraDataState
} from 'app/entities/extra-data/extra-data.reducer';
// prettier-ignore
import notifications, {
  NotificationsState
} from 'app/entities/notifications/notifications.reducer';
/* jhipster-needle-add-reducer-import - JHipster will add reducer here */

export interface IRootState {
  readonly authentication: AuthenticationState;
  readonly locale: LocaleState;
  readonly applicationProfile: ApplicationProfileState;
  readonly administration: AdministrationState;
  readonly userManagement: UserManagementState;
  readonly register: RegisterState;
  readonly activate: ActivateState;
  readonly passwordReset: PasswordResetState;
  readonly password: PasswordState;
  readonly settings: SettingsState;
  readonly client: ClientState;
  readonly branch: BranchState;
  readonly serviceUser: ServiceUserState;
  readonly medicalContact: MedicalContactState;
  readonly serviceUserContact: ServiceUserContactState;
  readonly emergencyContact: EmergencyContactState;
  readonly serviceUserLocation: ServiceUserLocationState;
  readonly powerOfAttorney: PowerOfAttorneyState;
  readonly consent: ConsentState;
  readonly access: AccessState;
  readonly equality: EqualityState;
  readonly questionType: QuestionTypeState;
  readonly question: QuestionState;
  readonly answer: AnswerState;
  readonly employeeDesignation: EmployeeDesignationState;
  readonly employee: EmployeeState;
  readonly employeeLocation: EmployeeLocationState;
  readonly employeeAvailability: EmployeeAvailabilityState;
  readonly relationshipType: RelationshipTypeState;
  readonly carerServiceUserRelation: CarerServiceUserRelationState;
  readonly disabilityType: DisabilityTypeState;
  readonly disability: DisabilityState;
  readonly eligibilityType: EligibilityTypeState;
  readonly eligibility: EligibilityState;
  readonly travel: TravelState;
  readonly payroll: PayrollState;
  readonly employeeHoliday: EmployeeHolidayState;
  readonly servceUserDocument: ServceUserDocumentState;
  readonly terminalDevice: TerminalDeviceState;
  readonly employeeDocument: EmployeeDocumentState;
  readonly communication: CommunicationState;
  readonly documentType: DocumentTypeState;
  readonly notification: NotificationState;
  readonly country: CountryState;
  readonly task: TaskState;
  readonly serviceUserEvent: ServiceUserEventState;
  readonly currency: CurrencyState;
  readonly serviceOrder: ServiceOrderState;
  readonly invoice: InvoiceState;
  readonly timesheet: TimesheetState;
  readonly clientDocument: ClientDocumentState;
  readonly systemEventsHistory: SystemEventsHistoryState;
  readonly systemSetting: SystemSettingState;
  readonly extraData: ExtraDataState;
  readonly notifications: NotificationsState;
  /* jhipster-needle-add-reducer-type - JHipster will add reducer type here */
  readonly loadingBar: any;
}

const rootReducer = combineReducers<IRootState>({
  authentication,
  locale,
  applicationProfile,
  administration,
  userManagement,
  register,
  activate,
  passwordReset,
  password,
  settings,
  client,
  branch,
  serviceUser,
  medicalContact,
  serviceUserContact,
  emergencyContact,
  serviceUserLocation,
  powerOfAttorney,
  consent,
  access,
  equality,
  questionType,
  question,
  answer,
  employeeDesignation,
  employee,
  employeeLocation,
  employeeAvailability,
  relationshipType,
  carerServiceUserRelation,
  disabilityType,
  disability,
  eligibilityType,
  eligibility,
  travel,
  payroll,
  employeeHoliday,
  servceUserDocument,
  terminalDevice,
  employeeDocument,
  communication,
  documentType,
  notification,
  country,
  task,
  serviceUserEvent,
  currency,
  serviceOrder,
  invoice,
  timesheet,
  clientDocument,
  systemEventsHistory,
  systemSetting,
  extraData,
  notifications,
  /* jhipster-needle-add-reducer-combine - JHipster will add reducer here */
  loadingBar,
});

export default rootReducer;
