
import 'package:flutter/widgets.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:amcCarePlanner/shared/models/entity_arguments.dart';

import 'bloc/relationship_type_bloc.dart';
import 'relationship_type_list_screen.dart';
import 'relationship_type_repository.dart';
import 'relationship_type_update_screen.dart';
import 'relationship_type_view_screen.dart';

class RelationshipTypeRoutes {
  static final list = '/entities/relationshipType-list';
  static final create = '/entities/relationshipType-create';
  static final edit = '/entities/relationshipType-edit';
  static final view = '/entities/relationshipType-view';

  static const listScreenKey = Key('__relationshipTypeListScreen__');
  static const createScreenKey = Key('__relationshipTypeCreateScreen__');
  static const editScreenKey = Key('__relationshipTypeEditScreen__');
  static const viewScreenKey = Key('__relationshipTypeViewScreen__');

  static final map = <String, WidgetBuilder>{
    list: (context) {
      return BlocProvider<RelationshipTypeBloc>(
          create: (context) => RelationshipTypeBloc(relationshipTypeRepository: RelationshipTypeRepository())
            ..add(InitRelationshipTypeList()),
          child: RelationshipTypeListScreen());
    },
    create: (context) {
      return BlocProvider<RelationshipTypeBloc>(
          create: (context) => RelationshipTypeBloc(relationshipTypeRepository: RelationshipTypeRepository()),
          child: RelationshipTypeUpdateScreen());
    },
    edit: (context) {
      EntityArguments arguments = ModalRoute.of(context).settings.arguments;
      return BlocProvider<RelationshipTypeBloc>(
          create: (context) => RelationshipTypeBloc(relationshipTypeRepository: RelationshipTypeRepository())
            ..add(LoadRelationshipTypeByIdForEdit(id: arguments.id)),
          child: RelationshipTypeUpdateScreen());
    },
    view: (context) {
      EntityArguments arguments = ModalRoute.of(context).settings.arguments;
      return BlocProvider<RelationshipTypeBloc>(
          create: (context) => RelationshipTypeBloc(relationshipTypeRepository: RelationshipTypeRepository())
            ..add(LoadRelationshipTypeByIdForView(id: arguments.id)),
          child: RelationshipTypeViewScreen());
    },
  };
}
