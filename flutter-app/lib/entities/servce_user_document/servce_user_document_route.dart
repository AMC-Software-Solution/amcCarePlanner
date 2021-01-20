
import 'package:flutter/widgets.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:amcCarePlanner/shared/models/entity_arguments.dart';

import 'bloc/servce_user_document_bloc.dart';
import 'servce_user_document_list_screen.dart';
import 'servce_user_document_repository.dart';
import 'servce_user_document_update_screen.dart';
import 'servce_user_document_view_screen.dart';

class ServceUserDocumentRoutes {
  static final list = '/entities/servceUserDocument-list';
  static final create = '/entities/servceUserDocument-create';
  static final edit = '/entities/servceUserDocument-edit';
  static final view = '/entities/servceUserDocument-view';

  static const listScreenKey = Key('__servceUserDocumentListScreen__');
  static const createScreenKey = Key('__servceUserDocumentCreateScreen__');
  static const editScreenKey = Key('__servceUserDocumentEditScreen__');
  static const viewScreenKey = Key('__servceUserDocumentViewScreen__');

  static final map = <String, WidgetBuilder>{
    list: (context) {
      return BlocProvider<ServceUserDocumentBloc>(
          create: (context) => ServceUserDocumentBloc(servceUserDocumentRepository: ServceUserDocumentRepository())
            ..add(InitServceUserDocumentList()),
          child: ServceUserDocumentListScreen());
    },
    create: (context) {
      return BlocProvider<ServceUserDocumentBloc>(
          create: (context) => ServceUserDocumentBloc(servceUserDocumentRepository: ServceUserDocumentRepository()),
          child: ServceUserDocumentUpdateScreen());
    },
    edit: (context) {
      EntityArguments arguments = ModalRoute.of(context).settings.arguments;
      return BlocProvider<ServceUserDocumentBloc>(
          create: (context) => ServceUserDocumentBloc(servceUserDocumentRepository: ServceUserDocumentRepository())
            ..add(LoadServceUserDocumentByIdForEdit(id: arguments.id)),
          child: ServceUserDocumentUpdateScreen());
    },
    view: (context) {
      EntityArguments arguments = ModalRoute.of(context).settings.arguments;
      return BlocProvider<ServceUserDocumentBloc>(
          create: (context) => ServceUserDocumentBloc(servceUserDocumentRepository: ServceUserDocumentRepository())
            ..add(LoadServceUserDocumentByIdForView(id: arguments.id)),
          child: ServceUserDocumentViewScreen());
    },
  };
}
