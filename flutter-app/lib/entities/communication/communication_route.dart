
import 'package:flutter/widgets.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:amcCarePlanner/shared/models/entity_arguments.dart';

import 'bloc/communication_bloc.dart';
import 'communication_list_screen.dart';
import 'communication_repository.dart';
import 'communication_update_screen.dart';
import 'communication_view_screen.dart';

class CommunicationRoutes {
  static final list = '/entities/communication-list';
  static final create = '/entities/communication-create';
  static final edit = '/entities/communication-edit';
  static final view = '/entities/communication-view';

  static const listScreenKey = Key('__communicationListScreen__');
  static const createScreenKey = Key('__communicationCreateScreen__');
  static const editScreenKey = Key('__communicationEditScreen__');
  static const viewScreenKey = Key('__communicationViewScreen__');

  static final map = <String, WidgetBuilder>{
    list: (context) {
      return BlocProvider<CommunicationBloc>(
          create: (context) => CommunicationBloc(communicationRepository: CommunicationRepository())
            ..add(InitCommunicationList()),
          child: CommunicationListScreen());
    },
    create: (context) {
      return BlocProvider<CommunicationBloc>(
          create: (context) => CommunicationBloc(communicationRepository: CommunicationRepository()),
          child: CommunicationUpdateScreen());
    },
    edit: (context) {
      EntityArguments arguments = ModalRoute.of(context).settings.arguments;
      return BlocProvider<CommunicationBloc>(
          create: (context) => CommunicationBloc(communicationRepository: CommunicationRepository())
            ..add(LoadCommunicationByIdForEdit(id: arguments.id)),
          child: CommunicationUpdateScreen());
    },
    view: (context) {
      EntityArguments arguments = ModalRoute.of(context).settings.arguments;
      return BlocProvider<CommunicationBloc>(
          create: (context) => CommunicationBloc(communicationRepository: CommunicationRepository())
            ..add(LoadCommunicationByIdForView(id: arguments.id)),
          child: CommunicationViewScreen());
    },
  };
}
