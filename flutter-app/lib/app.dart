import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:amcCarePlanner/account/login/bloc/login_bloc.dart';
import 'package:amcCarePlanner/account/login/login_repository.dart';
import 'package:amcCarePlanner/account/register/bloc/register_bloc.dart';
import 'package:amcCarePlanner/account/settings/settings_screen.dart';
import 'package:amcCarePlanner/main/bloc/main_bloc.dart';
import 'package:amcCarePlanner/routes.dart';
import 'package:amcCarePlanner/main/main_screen.dart';
import 'package:flutter/material.dart';
import 'package:amcCarePlanner/shared/repository/account_repository.dart';
import 'package:amcCarePlanner/themes.dart';
import 'account/settings/bloc/settings_bloc.dart';

import 'account/login/login_screen.dart';
import 'account/register/register_screen.dart';

import 'package:flutter_localizations/flutter_localizations.dart';
import 'generated/l10n.dart';

import 'entities/access/access_route.dart';
import 'entities/answer/answer_route.dart';
import 'entities/branch/branch_route.dart';
import 'entities/carer_service_user_relation/carer_service_user_relation_route.dart';
import 'entities/client/client_route.dart';
import 'entities/client_document/client_document_route.dart';
import 'entities/communication/communication_route.dart';
import 'entities/consent/consent_route.dart';
import 'entities/country/country_route.dart';
import 'entities/currency/currency_route.dart';
import 'entities/disability/disability_route.dart';
import 'entities/disability_type/disability_type_route.dart';
import 'entities/document_type/document_type_route.dart';
import 'entities/eligibility/eligibility_route.dart';
import 'entities/eligibility_type/eligibility_type_route.dart';
import 'entities/emergency_contact/emergency_contact_route.dart';
import 'entities/employee/employee_route.dart';
import 'entities/employee_availability/employee_availability_route.dart';
import 'entities/employee_designation/employee_designation_route.dart';
import 'entities/employee_document/employee_document_route.dart';
import 'entities/employee_holiday/employee_holiday_route.dart';
import 'entities/employee_location/employee_location_route.dart';
import 'entities/equality/equality_route.dart';
import 'entities/extra_data/extra_data_route.dart';
import 'entities/invoice/invoice_route.dart';
import 'entities/medical_contact/medical_contact_route.dart';
import 'entities/notifications/notifications_route.dart';
import 'entities/payroll/payroll_route.dart';
import 'entities/power_of_attorney/power_of_attorney_route.dart';
import 'entities/question/question_route.dart';
import 'entities/question_type/question_type_route.dart';
import 'entities/relationship_type/relationship_type_route.dart';
import 'entities/servce_user_document/servce_user_document_route.dart';
import 'entities/service_order/service_order_route.dart';
import 'entities/service_user/service_user_route.dart';
import 'entities/service_user_contact/service_user_contact_route.dart';
import 'entities/service_user_event/service_user_event_route.dart';
import 'entities/service_user_location/service_user_location_route.dart';
import 'entities/system_events_history/system_events_history_route.dart';
import 'entities/system_setting/system_setting_route.dart';
import 'entities/task/task_route.dart';
import 'entities/terminal_device/terminal_device_route.dart';
import 'entities/timesheet/timesheet_route.dart';
import 'entities/travel/travel_route.dart';
// jhipster-merlin-needle-import-add - JHipster will add new imports here

class AmcCarePlannerApp extends StatelessWidget {
  const AmcCarePlannerApp({Key key}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'AmcCarePlanner app',
      theme: Themes.jhLight,
      routes: {
        AmcCarePlannerRoutes.login: (context) {
          return BlocProvider<LoginBloc>(
            create: (context) => LoginBloc(loginRepository: LoginRepository()),
            child: LoginScreen());
        },
        AmcCarePlannerRoutes.register: (context) {
          return BlocProvider<RegisterBloc>(
            create: (context) => RegisterBloc(accountRepository: AccountRepository()),
            child: RegisterScreen());
        },
        AmcCarePlannerRoutes.main: (context) {
          return BlocProvider<MainBloc>(
            create: (context) => MainBloc(accountRepository: AccountRepository())
              ..add(Init()),
            child: MainScreen());
        },
      AmcCarePlannerRoutes.settings: (context) {
        return BlocProvider<SettingsBloc>(
          create: (context) => SettingsBloc(accountRepository: AccountRepository())
            ..add(LoadCurrentUser()),
          child: SettingsScreen());
        },
        ...AccessRoutes.map,
        ...AnswerRoutes.map,
        ...BranchRoutes.map,
        ...CarerServiceUserRelationRoutes.map,
        ...ClientRoutes.map,
        ...ClientDocumentRoutes.map,
        ...CommunicationRoutes.map,
        ...ConsentRoutes.map,
        ...CountryRoutes.map,
        ...CurrencyRoutes.map,
        ...DisabilityRoutes.map,
        ...DisabilityTypeRoutes.map,
        ...DocumentTypeRoutes.map,
        ...EligibilityRoutes.map,
        ...EligibilityTypeRoutes.map,
        ...EmergencyContactRoutes.map,
        ...EmployeeRoutes.map,
        ...EmployeeAvailabilityRoutes.map,
        ...EmployeeDesignationRoutes.map,
        ...EmployeeDocumentRoutes.map,
        ...EmployeeHolidayRoutes.map,
        ...EmployeeLocationRoutes.map,
        ...EqualityRoutes.map,
        ...ExtraDataRoutes.map,
        ...InvoiceRoutes.map,
        ...MedicalContactRoutes.map,
        ...NotificationsRoutes.map,
        ...PayrollRoutes.map,
        ...PowerOfAttorneyRoutes.map,
        ...QuestionRoutes.map,
        ...QuestionTypeRoutes.map,
        ...RelationshipTypeRoutes.map,
        ...ServceUserDocumentRoutes.map,
        ...ServiceOrderRoutes.map,
        ...ServiceUserRoutes.map,
        ...ServiceUserContactRoutes.map,
        ...ServiceUserEventRoutes.map,
        ...ServiceUserLocationRoutes.map,
        ...SystemEventsHistoryRoutes.map,
        ...SystemSettingRoutes.map,
        ...TaskRoutes.map,
        ...TerminalDeviceRoutes.map,
        ...TimesheetRoutes.map,
        ...TravelRoutes.map,
        // jhipster-merlin-needle-route-add - JHipster will add new routes here
      },
        localizationsDelegates: [
          S.delegate,
          GlobalMaterialLocalizations.delegate,
          GlobalWidgetsLocalizations.delegate,
          GlobalCupertinoLocalizations.delegate,
        ],
        supportedLocales: S.delegate.supportedLocales
    );
  }
}
