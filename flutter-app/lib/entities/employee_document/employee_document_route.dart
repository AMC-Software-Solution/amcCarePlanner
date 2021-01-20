
import 'package:flutter/widgets.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:amcCarePlanner/shared/models/entity_arguments.dart';

import 'bloc/employee_document_bloc.dart';
import 'employee_document_list_screen.dart';
import 'employee_document_repository.dart';
import 'employee_document_update_screen.dart';
import 'employee_document_view_screen.dart';

class EmployeeDocumentRoutes {
  static final list = '/entities/employeeDocument-list';
  static final create = '/entities/employeeDocument-create';
  static final edit = '/entities/employeeDocument-edit';
  static final view = '/entities/employeeDocument-view';

  static const listScreenKey = Key('__employeeDocumentListScreen__');
  static const createScreenKey = Key('__employeeDocumentCreateScreen__');
  static const editScreenKey = Key('__employeeDocumentEditScreen__');
  static const viewScreenKey = Key('__employeeDocumentViewScreen__');

  static final map = <String, WidgetBuilder>{
    list: (context) {
      return BlocProvider<EmployeeDocumentBloc>(
          create: (context) => EmployeeDocumentBloc(employeeDocumentRepository: EmployeeDocumentRepository())
            ..add(InitEmployeeDocumentList()),
          child: EmployeeDocumentListScreen());
    },
    create: (context) {
      return BlocProvider<EmployeeDocumentBloc>(
          create: (context) => EmployeeDocumentBloc(employeeDocumentRepository: EmployeeDocumentRepository()),
          child: EmployeeDocumentUpdateScreen());
    },
    edit: (context) {
      EntityArguments arguments = ModalRoute.of(context).settings.arguments;
      return BlocProvider<EmployeeDocumentBloc>(
          create: (context) => EmployeeDocumentBloc(employeeDocumentRepository: EmployeeDocumentRepository())
            ..add(LoadEmployeeDocumentByIdForEdit(id: arguments.id)),
          child: EmployeeDocumentUpdateScreen());
    },
    view: (context) {
      EntityArguments arguments = ModalRoute.of(context).settings.arguments;
      return BlocProvider<EmployeeDocumentBloc>(
          create: (context) => EmployeeDocumentBloc(employeeDocumentRepository: EmployeeDocumentRepository())
            ..add(LoadEmployeeDocumentByIdForView(id: arguments.id)),
          child: EmployeeDocumentViewScreen());
    },
  };
}
