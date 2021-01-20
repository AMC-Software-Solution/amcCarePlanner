
import 'package:flutter/widgets.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:amcCarePlanner/shared/models/entity_arguments.dart';

import 'bloc/service_user_event_bloc.dart';
import 'service_user_event_list_screen.dart';
import 'service_user_event_repository.dart';
import 'service_user_event_update_screen.dart';
import 'service_user_event_view_screen.dart';

class ServiceUserEventRoutes {
  static final list = '/entities/serviceUserEvent-list';
  static final create = '/entities/serviceUserEvent-create';
  static final edit = '/entities/serviceUserEvent-edit';
  static final view = '/entities/serviceUserEvent-view';

  static const listScreenKey = Key('__serviceUserEventListScreen__');
  static const createScreenKey = Key('__serviceUserEventCreateScreen__');
  static const editScreenKey = Key('__serviceUserEventEditScreen__');
  static const viewScreenKey = Key('__serviceUserEventViewScreen__');

  static final map = <String, WidgetBuilder>{
    list: (context) {
      return BlocProvider<ServiceUserEventBloc>(
          create: (context) => ServiceUserEventBloc(serviceUserEventRepository: ServiceUserEventRepository())
            ..add(InitServiceUserEventList()),
          child: ServiceUserEventListScreen());
    },
    create: (context) {
      return BlocProvider<ServiceUserEventBloc>(
          create: (context) => ServiceUserEventBloc(serviceUserEventRepository: ServiceUserEventRepository()),
          child: ServiceUserEventUpdateScreen());
    },
    edit: (context) {
      EntityArguments arguments = ModalRoute.of(context).settings.arguments;
      return BlocProvider<ServiceUserEventBloc>(
          create: (context) => ServiceUserEventBloc(serviceUserEventRepository: ServiceUserEventRepository())
            ..add(LoadServiceUserEventByIdForEdit(id: arguments.id)),
          child: ServiceUserEventUpdateScreen());
    },
    view: (context) {
      EntityArguments arguments = ModalRoute.of(context).settings.arguments;
      return BlocProvider<ServiceUserEventBloc>(
          create: (context) => ServiceUserEventBloc(serviceUserEventRepository: ServiceUserEventRepository())
            ..add(LoadServiceUserEventByIdForView(id: arguments.id)),
          child: ServiceUserEventViewScreen());
    },
  };
}
