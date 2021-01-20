import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:amcCarePlanner/generated/l10n.dart';
import 'package:amcCarePlanner/routes.dart';
import 'package:amcCarePlanner/shared/widgets/drawer/bloc/drawer_bloc.dart';
import 'package:flutter/material.dart';

import 'package:amcCarePlanner/entities/access/access_route.dart';
import 'package:amcCarePlanner/entities/answer/answer_route.dart';
import 'package:amcCarePlanner/entities/branch/branch_route.dart';
import 'package:amcCarePlanner/entities/carer_service_user_relation/carer_service_user_relation_route.dart';
import 'package:amcCarePlanner/entities/client/client_route.dart';
import 'package:amcCarePlanner/entities/client_document/client_document_route.dart';
import 'package:amcCarePlanner/entities/communication/communication_route.dart';
import 'package:amcCarePlanner/entities/consent/consent_route.dart';
import 'package:amcCarePlanner/entities/country/country_route.dart';
import 'package:amcCarePlanner/entities/currency/currency_route.dart';
import 'package:amcCarePlanner/entities/disability/disability_route.dart';
import 'package:amcCarePlanner/entities/disability_type/disability_type_route.dart';
import 'package:amcCarePlanner/entities/document_type/document_type_route.dart';
import 'package:amcCarePlanner/entities/eligibility/eligibility_route.dart';
import 'package:amcCarePlanner/entities/eligibility_type/eligibility_type_route.dart';
import 'package:amcCarePlanner/entities/emergency_contact/emergency_contact_route.dart';
import 'package:amcCarePlanner/entities/employee/employee_route.dart';
import 'package:amcCarePlanner/entities/employee_availability/employee_availability_route.dart';
import 'package:amcCarePlanner/entities/employee_designation/employee_designation_route.dart';
import 'package:amcCarePlanner/entities/employee_document/employee_document_route.dart';
import 'package:amcCarePlanner/entities/employee_holiday/employee_holiday_route.dart';
import 'package:amcCarePlanner/entities/employee_location/employee_location_route.dart';
import 'package:amcCarePlanner/entities/equality/equality_route.dart';
import 'package:amcCarePlanner/entities/extra_data/extra_data_route.dart';
import 'package:amcCarePlanner/entities/invoice/invoice_route.dart';
import 'package:amcCarePlanner/entities/medical_contact/medical_contact_route.dart';
import 'package:amcCarePlanner/entities/notifications/notifications_route.dart';
import 'package:amcCarePlanner/entities/payroll/payroll_route.dart';
import 'package:amcCarePlanner/entities/power_of_attorney/power_of_attorney_route.dart';
import 'package:amcCarePlanner/entities/question/question_route.dart';
import 'package:amcCarePlanner/entities/question_type/question_type_route.dart';
import 'package:amcCarePlanner/entities/relationship_type/relationship_type_route.dart';
import 'package:amcCarePlanner/entities/servce_user_document/servce_user_document_route.dart';
import 'package:amcCarePlanner/entities/service_order/service_order_route.dart';
import 'package:amcCarePlanner/entities/service_user/service_user_route.dart';
import 'package:amcCarePlanner/entities/service_user_contact/service_user_contact_route.dart';
import 'package:amcCarePlanner/entities/service_user_event/service_user_event_route.dart';
import 'package:amcCarePlanner/entities/service_user_location/service_user_location_route.dart';
import 'package:amcCarePlanner/entities/system_events_history/system_events_history_route.dart';
import 'package:amcCarePlanner/entities/system_setting/system_setting_route.dart';
import 'package:amcCarePlanner/entities/task/task_route.dart';
import 'package:amcCarePlanner/entities/terminal_device/terminal_device_route.dart';
import 'package:amcCarePlanner/entities/timesheet/timesheet_route.dart';
import 'package:amcCarePlanner/entities/travel/travel_route.dart';
// jhipster-merlin-needle-menu-import-entry-add

class AmcCarePlannerDrawer extends StatelessWidget {
   AmcCarePlannerDrawer({Key key}) : super(key: key);

   static final double iconSize = 30;

  @override
  Widget build(BuildContext context) {
    return BlocListener<DrawerBloc, DrawerState>(
      listener: (context, state) {
        if(state.isLogout) {
          Navigator.popUntil(context, ModalRoute.withName(AmcCarePlannerRoutes.login));
          Navigator.pushNamed(context, AmcCarePlannerRoutes.login);
        }
      },
      child: Drawer(
        child: ListView(
          padding: EdgeInsets.zero,
          children: <Widget>[
            header(context),
            ListTile(
              leading: Icon(Icons.home, size: iconSize,),
              title: Text(S.of(context).drawerMenuMain),
              onTap: () => Navigator.pushNamed(context, AmcCarePlannerRoutes.main),
            ),
            ListTile(
              leading: Icon(Icons.settings, size: iconSize,),
              title: Text(S.of(context).drawerSettingsTitle),
              onTap: () => Navigator.pushNamed(context, AmcCarePlannerRoutes.settings),
            ),
            ListTile(
              leading: Icon(Icons.exit_to_app, size: iconSize,),
          title: Text(S.of(context).drawerLogoutTitle),
              onTap: () => context.bloc<DrawerBloc>().add(Logout())
            ),
            Divider(thickness: 2),
            ListTile(
                leading: Icon(Icons.label, size: iconSize,),
                title: Text('Accesses'),
                onTap: () => Navigator.pushNamed(context, AccessRoutes.list),
            ),
            ListTile(
                leading: Icon(Icons.label, size: iconSize,),
                title: Text('Answers'),
                onTap: () => Navigator.pushNamed(context, AnswerRoutes.list),
            ),
            ListTile(
                leading: Icon(Icons.label, size: iconSize,),
                title: Text('Branches'),
                onTap: () => Navigator.pushNamed(context, BranchRoutes.list),
            ),
            ListTile(
                leading: Icon(Icons.label, size: iconSize,),
                title: Text('CarerServiceUserRelations'),
                onTap: () => Navigator.pushNamed(context, CarerServiceUserRelationRoutes.list),
            ),
            ListTile(
                leading: Icon(Icons.label, size: iconSize,),
                title: Text('Clients'),
                onTap: () => Navigator.pushNamed(context, ClientRoutes.list),
            ),
            ListTile(
                leading: Icon(Icons.label, size: iconSize,),
                title: Text('ClientDocuments'),
                onTap: () => Navigator.pushNamed(context, ClientDocumentRoutes.list),
            ),
            ListTile(
                leading: Icon(Icons.label, size: iconSize,),
                title: Text('Communications'),
                onTap: () => Navigator.pushNamed(context, CommunicationRoutes.list),
            ),
            ListTile(
                leading: Icon(Icons.label, size: iconSize,),
                title: Text('Consents'),
                onTap: () => Navigator.pushNamed(context, ConsentRoutes.list),
            ),
            ListTile(
                leading: Icon(Icons.label, size: iconSize,),
                title: Text('Countries'),
                onTap: () => Navigator.pushNamed(context, CountryRoutes.list),
            ),
            ListTile(
                leading: Icon(Icons.label, size: iconSize,),
                title: Text('Currencies'),
                onTap: () => Navigator.pushNamed(context, CurrencyRoutes.list),
            ),
            ListTile(
                leading: Icon(Icons.label, size: iconSize,),
                title: Text('Disabilities'),
                onTap: () => Navigator.pushNamed(context, DisabilityRoutes.list),
            ),
            ListTile(
                leading: Icon(Icons.label, size: iconSize,),
                title: Text('DisabilityTypes'),
                onTap: () => Navigator.pushNamed(context, DisabilityTypeRoutes.list),
            ),
            ListTile(
                leading: Icon(Icons.label, size: iconSize,),
                title: Text('DocumentTypes'),
                onTap: () => Navigator.pushNamed(context, DocumentTypeRoutes.list),
            ),
            ListTile(
                leading: Icon(Icons.label, size: iconSize,),
                title: Text('Eligibilities'),
                onTap: () => Navigator.pushNamed(context, EligibilityRoutes.list),
            ),
            ListTile(
                leading: Icon(Icons.label, size: iconSize,),
                title: Text('EligibilityTypes'),
                onTap: () => Navigator.pushNamed(context, EligibilityTypeRoutes.list),
            ),
            ListTile(
                leading: Icon(Icons.label, size: iconSize,),
                title: Text('EmergencyContacts'),
                onTap: () => Navigator.pushNamed(context, EmergencyContactRoutes.list),
            ),
            ListTile(
                leading: Icon(Icons.label, size: iconSize,),
                title: Text('Employees'),
                onTap: () => Navigator.pushNamed(context, EmployeeRoutes.list),
            ),
            ListTile(
                leading: Icon(Icons.label, size: iconSize,),
                title: Text('EmployeeAvailabilities'),
                onTap: () => Navigator.pushNamed(context, EmployeeAvailabilityRoutes.list),
            ),
            ListTile(
                leading: Icon(Icons.label, size: iconSize,),
                title: Text('EmployeeDesignations'),
                onTap: () => Navigator.pushNamed(context, EmployeeDesignationRoutes.list),
            ),
            ListTile(
                leading: Icon(Icons.label, size: iconSize,),
                title: Text('EmployeeDocuments'),
                onTap: () => Navigator.pushNamed(context, EmployeeDocumentRoutes.list),
            ),
            ListTile(
                leading: Icon(Icons.label, size: iconSize,),
                title: Text('EmployeeHolidays'),
                onTap: () => Navigator.pushNamed(context, EmployeeHolidayRoutes.list),
            ),
            ListTile(
                leading: Icon(Icons.label, size: iconSize,),
                title: Text('EmployeeLocations'),
                onTap: () => Navigator.pushNamed(context, EmployeeLocationRoutes.list),
            ),
            ListTile(
                leading: Icon(Icons.label, size: iconSize,),
                title: Text('Equalities'),
                onTap: () => Navigator.pushNamed(context, EqualityRoutes.list),
            ),
            ListTile(
                leading: Icon(Icons.label, size: iconSize,),
                title: Text('ExtraData'),
                onTap: () => Navigator.pushNamed(context, ExtraDataRoutes.list),
            ),
            ListTile(
                leading: Icon(Icons.label, size: iconSize,),
                title: Text('Invoices'),
                onTap: () => Navigator.pushNamed(context, InvoiceRoutes.list),
            ),
            ListTile(
                leading: Icon(Icons.label, size: iconSize,),
                title: Text('MedicalContacts'),
                onTap: () => Navigator.pushNamed(context, MedicalContactRoutes.list),
            ),
            ListTile(
                leading: Icon(Icons.label, size: iconSize,),
                title: Text('Notifications'),
                onTap: () => Navigator.pushNamed(context, NotificationsRoutes.list),
            ),
            ListTile(
                leading: Icon(Icons.label, size: iconSize,),
                title: Text('Payrolls'),
                onTap: () => Navigator.pushNamed(context, PayrollRoutes.list),
            ),
            ListTile(
                leading: Icon(Icons.label, size: iconSize,),
                title: Text('PowerOfAttorneys'),
                onTap: () => Navigator.pushNamed(context, PowerOfAttorneyRoutes.list),
            ),
            ListTile(
                leading: Icon(Icons.label, size: iconSize,),
                title: Text('Questions'),
                onTap: () => Navigator.pushNamed(context, QuestionRoutes.list),
            ),
            ListTile(
                leading: Icon(Icons.label, size: iconSize,),
                title: Text('QuestionTypes'),
                onTap: () => Navigator.pushNamed(context, QuestionTypeRoutes.list),
            ),
            ListTile(
                leading: Icon(Icons.label, size: iconSize,),
                title: Text('RelationshipTypes'),
                onTap: () => Navigator.pushNamed(context, RelationshipTypeRoutes.list),
            ),
            ListTile(
                leading: Icon(Icons.label, size: iconSize,),
                title: Text('ServceUserDocuments'),
                onTap: () => Navigator.pushNamed(context, ServceUserDocumentRoutes.list),
            ),
            ListTile(
                leading: Icon(Icons.label, size: iconSize,),
                title: Text('ServiceOrders'),
                onTap: () => Navigator.pushNamed(context, ServiceOrderRoutes.list),
            ),
            ListTile(
                leading: Icon(Icons.label, size: iconSize,),
                title: Text('ServiceUsers'),
                onTap: () => Navigator.pushNamed(context, ServiceUserRoutes.list),
            ),
            ListTile(
                leading: Icon(Icons.label, size: iconSize,),
                title: Text('ServiceUserContacts'),
                onTap: () => Navigator.pushNamed(context, ServiceUserContactRoutes.list),
            ),
            ListTile(
                leading: Icon(Icons.label, size: iconSize,),
                title: Text('ServiceUserEvents'),
                onTap: () => Navigator.pushNamed(context, ServiceUserEventRoutes.list),
            ),
            ListTile(
                leading: Icon(Icons.label, size: iconSize,),
                title: Text('ServiceUserLocations'),
                onTap: () => Navigator.pushNamed(context, ServiceUserLocationRoutes.list),
            ),
            ListTile(
                leading: Icon(Icons.label, size: iconSize,),
                title: Text('SystemEventsHistories'),
                onTap: () => Navigator.pushNamed(context, SystemEventsHistoryRoutes.list),
            ),
            ListTile(
                leading: Icon(Icons.label, size: iconSize,),
                title: Text('SystemSettings'),
                onTap: () => Navigator.pushNamed(context, SystemSettingRoutes.list),
            ),
            ListTile(
                leading: Icon(Icons.label, size: iconSize,),
                title: Text('Tasks'),
                onTap: () => Navigator.pushNamed(context, TaskRoutes.list),
            ),
            ListTile(
                leading: Icon(Icons.label, size: iconSize,),
                title: Text('TerminalDevices'),
                onTap: () => Navigator.pushNamed(context, TerminalDeviceRoutes.list),
            ),
            ListTile(
                leading: Icon(Icons.label, size: iconSize,),
                title: Text('Timesheets'),
                onTap: () => Navigator.pushNamed(context, TimesheetRoutes.list),
            ),
            ListTile(
                leading: Icon(Icons.label, size: iconSize,),
                title: Text('Travels'),
                onTap: () => Navigator.pushNamed(context, TravelRoutes.list),
            ),
            // jhipster-merlin-needle-menu-entry-add
          ],
        ),
      ),
    );
  }

  Widget header(BuildContext context) {
    return DrawerHeader(
      decoration: BoxDecoration(
        color: Theme.of(context).primaryColor,
      ),
      child: Text(S.of(context).drawerMenuTitle,
        textAlign: TextAlign.center,
        style: Theme.of(context).textTheme.headline2,
      ),
    );
  }
}
