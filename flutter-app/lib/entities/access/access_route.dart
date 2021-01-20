
import 'package:flutter/widgets.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:amcCarePlanner/shared/models/entity_arguments.dart';

import 'bloc/access_bloc.dart';
import 'access_list_screen.dart';
import 'access_repository.dart';
import 'access_update_screen.dart';
import 'access_view_screen.dart';

class AccessRoutes {
  static final list = '/entities/access-list';
  static final create = '/entities/access-create';
  static final edit = '/entities/access-edit';
  static final view = '/entities/access-view';

  static const listScreenKey = Key('__accessListScreen__');
  static const createScreenKey = Key('__accessCreateScreen__');
  static const editScreenKey = Key('__accessEditScreen__');
  static const viewScreenKey = Key('__accessViewScreen__');

  static final map = <String, WidgetBuilder>{
    list: (context) {
      return BlocProvider<AccessBloc>(
          create: (context) => AccessBloc(accessRepository: AccessRepository())
            ..add(InitAccessList()),
          child: AccessListScreen());
    },
    create: (context) {
      return BlocProvider<AccessBloc>(
          create: (context) => AccessBloc(accessRepository: AccessRepository()),
          child: AccessUpdateScreen());
    },
    edit: (context) {
      EntityArguments arguments = ModalRoute.of(context).settings.arguments;
      return BlocProvider<AccessBloc>(
          create: (context) => AccessBloc(accessRepository: AccessRepository())
            ..add(LoadAccessByIdForEdit(id: arguments.id)),
          child: AccessUpdateScreen());
    },
    view: (context) {
      EntityArguments arguments = ModalRoute.of(context).settings.arguments;
      return BlocProvider<AccessBloc>(
          create: (context) => AccessBloc(accessRepository: AccessRepository())
            ..add(LoadAccessByIdForView(id: arguments.id)),
          child: AccessViewScreen());
    },
  };
}
