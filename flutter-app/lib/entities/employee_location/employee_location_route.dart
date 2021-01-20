
import 'package:flutter/widgets.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:amcCarePlanner/shared/models/entity_arguments.dart';

import 'bloc/employee_location_bloc.dart';
import 'employee_location_list_screen.dart';
import 'employee_location_repository.dart';
import 'employee_location_update_screen.dart';
import 'employee_location_view_screen.dart';

class EmployeeLocationRoutes {
  static final list = '/entities/employeeLocation-list';
  static final create = '/entities/employeeLocation-create';
  static final edit = '/entities/employeeLocation-edit';
  static final view = '/entities/employeeLocation-view';

  static const listScreenKey = Key('__employeeLocationListScreen__');
  static const createScreenKey = Key('__employeeLocationCreateScreen__');
  static const editScreenKey = Key('__employeeLocationEditScreen__');
  static const viewScreenKey = Key('__employeeLocationViewScreen__');

  static final map = <String, WidgetBuilder>{
    list: (context) {
      return BlocProvider<EmployeeLocationBloc>(
          create: (context) => EmployeeLocationBloc(employeeLocationRepository: EmployeeLocationRepository())
            ..add(InitEmployeeLocationList()),
          child: EmployeeLocationListScreen());
    },
    create: (context) {
      return BlocProvider<EmployeeLocationBloc>(
          create: (context) => EmployeeLocationBloc(employeeLocationRepository: EmployeeLocationRepository()),
          child: EmployeeLocationUpdateScreen());
    },
    edit: (context) {
      EntityArguments arguments = ModalRoute.of(context).settings.arguments;
      return BlocProvider<EmployeeLocationBloc>(
          create: (context) => EmployeeLocationBloc(employeeLocationRepository: EmployeeLocationRepository())
            ..add(LoadEmployeeLocationByIdForEdit(id: arguments.id)),
          child: EmployeeLocationUpdateScreen());
    },
    view: (context) {
      EntityArguments arguments = ModalRoute.of(context).settings.arguments;
      return BlocProvider<EmployeeLocationBloc>(
          create: (context) => EmployeeLocationBloc(employeeLocationRepository: EmployeeLocationRepository())
            ..add(LoadEmployeeLocationByIdForView(id: arguments.id)),
          child: EmployeeLocationViewScreen());
    },
  };
}
