
import 'package:flutter/widgets.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:amcCarePlanner/shared/models/entity_arguments.dart';

import 'bloc/employee_designation_bloc.dart';
import 'employee_designation_list_screen.dart';
import 'employee_designation_repository.dart';
import 'employee_designation_update_screen.dart';
import 'employee_designation_view_screen.dart';

class EmployeeDesignationRoutes {
  static final list = '/entities/employeeDesignation-list';
  static final create = '/entities/employeeDesignation-create';
  static final edit = '/entities/employeeDesignation-edit';
  static final view = '/entities/employeeDesignation-view';

  static const listScreenKey = Key('__employeeDesignationListScreen__');
  static const createScreenKey = Key('__employeeDesignationCreateScreen__');
  static const editScreenKey = Key('__employeeDesignationEditScreen__');
  static const viewScreenKey = Key('__employeeDesignationViewScreen__');

  static final map = <String, WidgetBuilder>{
    list: (context) {
      return BlocProvider<EmployeeDesignationBloc>(
          create: (context) => EmployeeDesignationBloc(employeeDesignationRepository: EmployeeDesignationRepository())
            ..add(InitEmployeeDesignationList()),
          child: EmployeeDesignationListScreen());
    },
    create: (context) {
      return BlocProvider<EmployeeDesignationBloc>(
          create: (context) => EmployeeDesignationBloc(employeeDesignationRepository: EmployeeDesignationRepository()),
          child: EmployeeDesignationUpdateScreen());
    },
    edit: (context) {
      EntityArguments arguments = ModalRoute.of(context).settings.arguments;
      return BlocProvider<EmployeeDesignationBloc>(
          create: (context) => EmployeeDesignationBloc(employeeDesignationRepository: EmployeeDesignationRepository())
            ..add(LoadEmployeeDesignationByIdForEdit(id: arguments.id)),
          child: EmployeeDesignationUpdateScreen());
    },
    view: (context) {
      EntityArguments arguments = ModalRoute.of(context).settings.arguments;
      return BlocProvider<EmployeeDesignationBloc>(
          create: (context) => EmployeeDesignationBloc(employeeDesignationRepository: EmployeeDesignationRepository())
            ..add(LoadEmployeeDesignationByIdForView(id: arguments.id)),
          child: EmployeeDesignationViewScreen());
    },
  };
}
