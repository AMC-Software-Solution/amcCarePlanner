
import 'package:flutter/widgets.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:amcCarePlanner/shared/models/entity_arguments.dart';

import 'bloc/payroll_bloc.dart';
import 'payroll_list_screen.dart';
import 'payroll_repository.dart';
import 'payroll_update_screen.dart';
import 'payroll_view_screen.dart';

class PayrollRoutes {
  static final list = '/entities/payroll-list';
  static final create = '/entities/payroll-create';
  static final edit = '/entities/payroll-edit';
  static final view = '/entities/payroll-view';

  static const listScreenKey = Key('__payrollListScreen__');
  static const createScreenKey = Key('__payrollCreateScreen__');
  static const editScreenKey = Key('__payrollEditScreen__');
  static const viewScreenKey = Key('__payrollViewScreen__');

  static final map = <String, WidgetBuilder>{
    list: (context) {
      return BlocProvider<PayrollBloc>(
          create: (context) => PayrollBloc(payrollRepository: PayrollRepository())
            ..add(InitPayrollList()),
          child: PayrollListScreen());
    },
    create: (context) {
      return BlocProvider<PayrollBloc>(
          create: (context) => PayrollBloc(payrollRepository: PayrollRepository()),
          child: PayrollUpdateScreen());
    },
    edit: (context) {
      EntityArguments arguments = ModalRoute.of(context).settings.arguments;
      return BlocProvider<PayrollBloc>(
          create: (context) => PayrollBloc(payrollRepository: PayrollRepository())
            ..add(LoadPayrollByIdForEdit(id: arguments.id)),
          child: PayrollUpdateScreen());
    },
    view: (context) {
      EntityArguments arguments = ModalRoute.of(context).settings.arguments;
      return BlocProvider<PayrollBloc>(
          create: (context) => PayrollBloc(payrollRepository: PayrollRepository())
            ..add(LoadPayrollByIdForView(id: arguments.id)),
          child: PayrollViewScreen());
    },
  };
}
