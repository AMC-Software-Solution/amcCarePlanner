
import 'package:flutter/widgets.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:amcCarePlanner/shared/models/entity_arguments.dart';

import 'bloc/client_document_bloc.dart';
import 'client_document_list_screen.dart';
import 'client_document_repository.dart';
import 'client_document_update_screen.dart';
import 'client_document_view_screen.dart';

class ClientDocumentRoutes {
  static final list = '/entities/clientDocument-list';
  static final create = '/entities/clientDocument-create';
  static final edit = '/entities/clientDocument-edit';
  static final view = '/entities/clientDocument-view';

  static const listScreenKey = Key('__clientDocumentListScreen__');
  static const createScreenKey = Key('__clientDocumentCreateScreen__');
  static const editScreenKey = Key('__clientDocumentEditScreen__');
  static const viewScreenKey = Key('__clientDocumentViewScreen__');

  static final map = <String, WidgetBuilder>{
    list: (context) {
      return BlocProvider<ClientDocumentBloc>(
          create: (context) => ClientDocumentBloc(clientDocumentRepository: ClientDocumentRepository())
            ..add(InitClientDocumentList()),
          child: ClientDocumentListScreen());
    },
    create: (context) {
      return BlocProvider<ClientDocumentBloc>(
          create: (context) => ClientDocumentBloc(clientDocumentRepository: ClientDocumentRepository()),
          child: ClientDocumentUpdateScreen());
    },
    edit: (context) {
      EntityArguments arguments = ModalRoute.of(context).settings.arguments;
      return BlocProvider<ClientDocumentBloc>(
          create: (context) => ClientDocumentBloc(clientDocumentRepository: ClientDocumentRepository())
            ..add(LoadClientDocumentByIdForEdit(id: arguments.id)),
          child: ClientDocumentUpdateScreen());
    },
    view: (context) {
      EntityArguments arguments = ModalRoute.of(context).settings.arguments;
      return BlocProvider<ClientDocumentBloc>(
          create: (context) => ClientDocumentBloc(clientDocumentRepository: ClientDocumentRepository())
            ..add(LoadClientDocumentByIdForView(id: arguments.id)),
          child: ClientDocumentViewScreen());
    },
  };
}
