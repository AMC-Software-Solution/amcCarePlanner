
import 'package:flutter/widgets.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:amcCarePlanner/shared/models/entity_arguments.dart';

import 'bloc/timesheet_bloc.dart';
import 'timesheet_list_screen.dart';
import 'timesheet_repository.dart';
import 'timesheet_update_screen.dart';
import 'timesheet_view_screen.dart';

class TimesheetRoutes {
  static final list = '/entities/timesheet-list';
  static final create = '/entities/timesheet-create';
  static final edit = '/entities/timesheet-edit';
  static final view = '/entities/timesheet-view';

  static const listScreenKey = Key('__timesheetListScreen__');
  static const createScreenKey = Key('__timesheetCreateScreen__');
  static const editScreenKey = Key('__timesheetEditScreen__');
  static const viewScreenKey = Key('__timesheetViewScreen__');

  static final map = <String, WidgetBuilder>{
    list: (context) {
      return BlocProvider<TimesheetBloc>(
          create: (context) => TimesheetBloc(timesheetRepository: TimesheetRepository())
            ..add(InitTimesheetList()),
          child: TimesheetListScreen());
    },
    create: (context) {
      return BlocProvider<TimesheetBloc>(
          create: (context) => TimesheetBloc(timesheetRepository: TimesheetRepository()),
          child: TimesheetUpdateScreen());
    },
    edit: (context) {
      EntityArguments arguments = ModalRoute.of(context).settings.arguments;
      return BlocProvider<TimesheetBloc>(
          create: (context) => TimesheetBloc(timesheetRepository: TimesheetRepository())
            ..add(LoadTimesheetByIdForEdit(id: arguments.id)),
          child: TimesheetUpdateScreen());
    },
    view: (context) {
      EntityArguments arguments = ModalRoute.of(context).settings.arguments;
      return BlocProvider<TimesheetBloc>(
          create: (context) => TimesheetBloc(timesheetRepository: TimesheetRepository())
            ..add(LoadTimesheetByIdForView(id: arguments.id)),
          child: TimesheetViewScreen());
    },
  };
}
