
import 'package:flutter/widgets.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:amcCarePlanner/shared/models/entity_arguments.dart';

import 'bloc/document_type_bloc.dart';
import 'document_type_list_screen.dart';
import 'document_type_repository.dart';
import 'document_type_update_screen.dart';
import 'document_type_view_screen.dart';

class DocumentTypeRoutes {
  static final list = '/entities/documentType-list';
  static final create = '/entities/documentType-create';
  static final edit = '/entities/documentType-edit';
  static final view = '/entities/documentType-view';

  static const listScreenKey = Key('__documentTypeListScreen__');
  static const createScreenKey = Key('__documentTypeCreateScreen__');
  static const editScreenKey = Key('__documentTypeEditScreen__');
  static const viewScreenKey = Key('__documentTypeViewScreen__');

  static final map = <String, WidgetBuilder>{
    list: (context) {
      return BlocProvider<DocumentTypeBloc>(
          create: (context) => DocumentTypeBloc(documentTypeRepository: DocumentTypeRepository())
            ..add(InitDocumentTypeList()),
          child: DocumentTypeListScreen());
    },
    create: (context) {
      return BlocProvider<DocumentTypeBloc>(
          create: (context) => DocumentTypeBloc(documentTypeRepository: DocumentTypeRepository()),
          child: DocumentTypeUpdateScreen());
    },
    edit: (context) {
      EntityArguments arguments = ModalRoute.of(context).settings.arguments;
      return BlocProvider<DocumentTypeBloc>(
          create: (context) => DocumentTypeBloc(documentTypeRepository: DocumentTypeRepository())
            ..add(LoadDocumentTypeByIdForEdit(id: arguments.id)),
          child: DocumentTypeUpdateScreen());
    },
    view: (context) {
      EntityArguments arguments = ModalRoute.of(context).settings.arguments;
      return BlocProvider<DocumentTypeBloc>(
          create: (context) => DocumentTypeBloc(documentTypeRepository: DocumentTypeRepository())
            ..add(LoadDocumentTypeByIdForView(id: arguments.id)),
          child: DocumentTypeViewScreen());
    },
  };
}
