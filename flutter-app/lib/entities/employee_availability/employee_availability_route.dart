
import 'package:flutter/widgets.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:amcCarePlanner/shared/models/entity_arguments.dart';

import 'bloc/employee_availability_bloc.dart';
import 'employee_availability_list_screen.dart';
import 'employee_availability_repository.dart';
import 'employee_availability_update_screen.dart';
import 'employee_availability_view_screen.dart';

class EmployeeAvailabilityRoutes {
  static final list = '/entities/employeeAvailability-list';
  static final create = '/entities/employeeAvailability-create';
  static final edit = '/entities/employeeAvailability-edit';
  static final view = '/entities/employeeAvailability-view';

  static const listScreenKey = Key('__employeeAvailabilityListScreen__');
  static const createScreenKey = Key('__employeeAvailabilityCreateScreen__');
  static const editScreenKey = Key('__employeeAvailabilityEditScreen__');
  static const viewScreenKey = Key('__employeeAvailabilityViewScreen__');

  static final map = <String, WidgetBuilder>{
    list: (context) {
      return BlocProvider<EmployeeAvailabilityBloc>(
          create: (context) => EmployeeAvailabilityBloc(employeeAvailabilityRepository: EmployeeAvailabilityRepository())
            ..add(InitEmployeeAvailabilityList()),
          child: EmployeeAvailabilityListScreen());
    },
    create: (context) {
      return BlocProvider<EmployeeAvailabilityBloc>(
          create: (context) => EmployeeAvailabilityBloc(employeeAvailabilityRepository: EmployeeAvailabilityRepository()),
          child: EmployeeAvailabilityUpdateScreen());
    },
    edit: (context) {
      EntityArguments arguments = ModalRoute.of(context).settings.arguments;
      return BlocProvider<EmployeeAvailabilityBloc>(
          create: (context) => EmployeeAvailabilityBloc(employeeAvailabilityRepository: EmployeeAvailabilityRepository())
            ..add(LoadEmployeeAvailabilityByIdForEdit(id: arguments.id)),
          child: EmployeeAvailabilityUpdateScreen());
    },
    view: (context) {
      EntityArguments arguments = ModalRoute.of(context).settings.arguments;
      return BlocProvider<EmployeeAvailabilityBloc>(
          create: (context) => EmployeeAvailabilityBloc(employeeAvailabilityRepository: EmployeeAvailabilityRepository())
            ..add(LoadEmployeeAvailabilityByIdForView(id: arguments.id)),
          child: EmployeeAvailabilityViewScreen());
    },
  };
}
