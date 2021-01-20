
import 'package:flutter/widgets.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:amcCarePlanner/shared/models/entity_arguments.dart';

import 'bloc/carer_service_user_relation_bloc.dart';
import 'carer_service_user_relation_list_screen.dart';
import 'carer_service_user_relation_repository.dart';
import 'carer_service_user_relation_update_screen.dart';
import 'carer_service_user_relation_view_screen.dart';

class CarerServiceUserRelationRoutes {
  static final list = '/entities/carerServiceUserRelation-list';
  static final create = '/entities/carerServiceUserRelation-create';
  static final edit = '/entities/carerServiceUserRelation-edit';
  static final view = '/entities/carerServiceUserRelation-view';

  static const listScreenKey = Key('__carerServiceUserRelationListScreen__');
  static const createScreenKey = Key('__carerServiceUserRelationCreateScreen__');
  static const editScreenKey = Key('__carerServiceUserRelationEditScreen__');
  static const viewScreenKey = Key('__carerServiceUserRelationViewScreen__');

  static final map = <String, WidgetBuilder>{
    list: (context) {
      return BlocProvider<CarerServiceUserRelationBloc>(
          create: (context) => CarerServiceUserRelationBloc(carerServiceUserRelationRepository: CarerServiceUserRelationRepository())
            ..add(InitCarerServiceUserRelationList()),
          child: CarerServiceUserRelationListScreen());
    },
    create: (context) {
      return BlocProvider<CarerServiceUserRelationBloc>(
          create: (context) => CarerServiceUserRelationBloc(carerServiceUserRelationRepository: CarerServiceUserRelationRepository()),
          child: CarerServiceUserRelationUpdateScreen());
    },
    edit: (context) {
      EntityArguments arguments = ModalRoute.of(context).settings.arguments;
      return BlocProvider<CarerServiceUserRelationBloc>(
          create: (context) => CarerServiceUserRelationBloc(carerServiceUserRelationRepository: CarerServiceUserRelationRepository())
            ..add(LoadCarerServiceUserRelationByIdForEdit(id: arguments.id)),
          child: CarerServiceUserRelationUpdateScreen());
    },
    view: (context) {
      EntityArguments arguments = ModalRoute.of(context).settings.arguments;
      return BlocProvider<CarerServiceUserRelationBloc>(
          create: (context) => CarerServiceUserRelationBloc(carerServiceUserRelationRepository: CarerServiceUserRelationRepository())
            ..add(LoadCarerServiceUserRelationByIdForView(id: arguments.id)),
          child: CarerServiceUserRelationViewScreen());
    },
  };
}
