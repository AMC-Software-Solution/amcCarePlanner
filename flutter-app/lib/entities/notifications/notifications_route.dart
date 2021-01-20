
import 'package:flutter/widgets.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:amcCarePlanner/shared/models/entity_arguments.dart';

import 'bloc/notifications_bloc.dart';
import 'notifications_list_screen.dart';
import 'notifications_repository.dart';
import 'notifications_update_screen.dart';
import 'notifications_view_screen.dart';

class NotificationsRoutes {
  static final list = '/entities/notifications-list';
  static final create = '/entities/notifications-create';
  static final edit = '/entities/notifications-edit';
  static final view = '/entities/notifications-view';

  static const listScreenKey = Key('__notificationsListScreen__');
  static const createScreenKey = Key('__notificationsCreateScreen__');
  static const editScreenKey = Key('__notificationsEditScreen__');
  static const viewScreenKey = Key('__notificationsViewScreen__');

  static final map = <String, WidgetBuilder>{
    list: (context) {
      return BlocProvider<NotificationsBloc>(
          create: (context) => NotificationsBloc(notificationsRepository: NotificationsRepository())
            ..add(InitNotificationsList()),
          child: NotificationsListScreen());
    },
    create: (context) {
      return BlocProvider<NotificationsBloc>(
          create: (context) => NotificationsBloc(notificationsRepository: NotificationsRepository()),
          child: NotificationsUpdateScreen());
    },
    edit: (context) {
      EntityArguments arguments = ModalRoute.of(context).settings.arguments;
      return BlocProvider<NotificationsBloc>(
          create: (context) => NotificationsBloc(notificationsRepository: NotificationsRepository())
            ..add(LoadNotificationsByIdForEdit(id: arguments.id)),
          child: NotificationsUpdateScreen());
    },
    view: (context) {
      EntityArguments arguments = ModalRoute.of(context).settings.arguments;
      return BlocProvider<NotificationsBloc>(
          create: (context) => NotificationsBloc(notificationsRepository: NotificationsRepository())
            ..add(LoadNotificationsByIdForView(id: arguments.id)),
          child: NotificationsViewScreen());
    },
  };
}
