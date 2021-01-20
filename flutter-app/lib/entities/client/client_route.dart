
import 'package:flutter/widgets.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:amcCarePlanner/shared/models/entity_arguments.dart';

import 'bloc/client_bloc.dart';
import 'client_list_screen.dart';
import 'client_repository.dart';
import 'client_update_screen.dart';
import 'client_view_screen.dart';

class ClientRoutes {
  static final list = '/entities/client-list';
  static final create = '/entities/client-create';
  static final edit = '/entities/client-edit';
  static final view = '/entities/client-view';

  static const listScreenKey = Key('__clientListScreen__');
  static const createScreenKey = Key('__clientCreateScreen__');
  static const editScreenKey = Key('__clientEditScreen__');
  static const viewScreenKey = Key('__clientViewScreen__');

  static final map = <String, WidgetBuilder>{
    list: (context) {
      return BlocProvider<ClientBloc>(
          create: (context) => ClientBloc(clientRepository: ClientRepository())
            ..add(InitClientList()),
          child: ClientListScreen());
    },
    create: (context) {
      return BlocProvider<ClientBloc>(
          create: (context) => ClientBloc(clientRepository: ClientRepository()),
          child: ClientUpdateScreen());
    },
    edit: (context) {
      EntityArguments arguments = ModalRoute.of(context).settings.arguments;
      return BlocProvider<ClientBloc>(
          create: (context) => ClientBloc(clientRepository: ClientRepository())
            ..add(LoadClientByIdForEdit(id: arguments.id)),
          child: ClientUpdateScreen());
    },
    view: (context) {
      EntityArguments arguments = ModalRoute.of(context).settings.arguments;
      return BlocProvider<ClientBloc>(
          create: (context) => ClientBloc(clientRepository: ClientRepository())
            ..add(LoadClientByIdForView(id: arguments.id)),
          child: ClientViewScreen());
    },
  };
}
