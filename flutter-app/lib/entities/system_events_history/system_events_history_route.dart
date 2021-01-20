
import 'package:flutter/widgets.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:amcCarePlanner/shared/models/entity_arguments.dart';

import 'bloc/system_events_history_bloc.dart';
import 'system_events_history_list_screen.dart';
import 'system_events_history_repository.dart';
import 'system_events_history_update_screen.dart';
import 'system_events_history_view_screen.dart';

class SystemEventsHistoryRoutes {
  static final list = '/entities/systemEventsHistory-list';
  static final create = '/entities/systemEventsHistory-create';
  static final edit = '/entities/systemEventsHistory-edit';
  static final view = '/entities/systemEventsHistory-view';

  static const listScreenKey = Key('__systemEventsHistoryListScreen__');
  static const createScreenKey = Key('__systemEventsHistoryCreateScreen__');
  static const editScreenKey = Key('__systemEventsHistoryEditScreen__');
  static const viewScreenKey = Key('__systemEventsHistoryViewScreen__');

  static final map = <String, WidgetBuilder>{
    list: (context) {
      return BlocProvider<SystemEventsHistoryBloc>(
          create: (context) => SystemEventsHistoryBloc(systemEventsHistoryRepository: SystemEventsHistoryRepository())
            ..add(InitSystemEventsHistoryList()),
          child: SystemEventsHistoryListScreen());
    },
    create: (context) {
      return BlocProvider<SystemEventsHistoryBloc>(
          create: (context) => SystemEventsHistoryBloc(systemEventsHistoryRepository: SystemEventsHistoryRepository()),
          child: SystemEventsHistoryUpdateScreen());
    },
    edit: (context) {
      EntityArguments arguments = ModalRoute.of(context).settings.arguments;
      return BlocProvider<SystemEventsHistoryBloc>(
          create: (context) => SystemEventsHistoryBloc(systemEventsHistoryRepository: SystemEventsHistoryRepository())
            ..add(LoadSystemEventsHistoryByIdForEdit(id: arguments.id)),
          child: SystemEventsHistoryUpdateScreen());
    },
    view: (context) {
      EntityArguments arguments = ModalRoute.of(context).settings.arguments;
      return BlocProvider<SystemEventsHistoryBloc>(
          create: (context) => SystemEventsHistoryBloc(systemEventsHistoryRepository: SystemEventsHistoryRepository())
            ..add(LoadSystemEventsHistoryByIdForView(id: arguments.id)),
          child: SystemEventsHistoryViewScreen());
    },
  };
}
