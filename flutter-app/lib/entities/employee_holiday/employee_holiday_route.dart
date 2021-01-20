
import 'package:flutter/widgets.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:amcCarePlanner/shared/models/entity_arguments.dart';

import 'bloc/employee_holiday_bloc.dart';
import 'employee_holiday_list_screen.dart';
import 'employee_holiday_repository.dart';
import 'employee_holiday_update_screen.dart';
import 'employee_holiday_view_screen.dart';

class EmployeeHolidayRoutes {
  static final list = '/entities/employeeHoliday-list';
  static final create = '/entities/employeeHoliday-create';
  static final edit = '/entities/employeeHoliday-edit';
  static final view = '/entities/employeeHoliday-view';

  static const listScreenKey = Key('__employeeHolidayListScreen__');
  static const createScreenKey = Key('__employeeHolidayCreateScreen__');
  static const editScreenKey = Key('__employeeHolidayEditScreen__');
  static const viewScreenKey = Key('__employeeHolidayViewScreen__');

  static final map = <String, WidgetBuilder>{
    list: (context) {
      return BlocProvider<EmployeeHolidayBloc>(
          create: (context) => EmployeeHolidayBloc(employeeHolidayRepository: EmployeeHolidayRepository())
            ..add(InitEmployeeHolidayList()),
          child: EmployeeHolidayListScreen());
    },
    create: (context) {
      return BlocProvider<EmployeeHolidayBloc>(
          create: (context) => EmployeeHolidayBloc(employeeHolidayRepository: EmployeeHolidayRepository()),
          child: EmployeeHolidayUpdateScreen());
    },
    edit: (context) {
      EntityArguments arguments = ModalRoute.of(context).settings.arguments;
      return BlocProvider<EmployeeHolidayBloc>(
          create: (context) => EmployeeHolidayBloc(employeeHolidayRepository: EmployeeHolidayRepository())
            ..add(LoadEmployeeHolidayByIdForEdit(id: arguments.id)),
          child: EmployeeHolidayUpdateScreen());
    },
    view: (context) {
      EntityArguments arguments = ModalRoute.of(context).settings.arguments;
      return BlocProvider<EmployeeHolidayBloc>(
          create: (context) => EmployeeHolidayBloc(employeeHolidayRepository: EmployeeHolidayRepository())
            ..add(LoadEmployeeHolidayByIdForView(id: arguments.id)),
          child: EmployeeHolidayViewScreen());
    },
  };
}
