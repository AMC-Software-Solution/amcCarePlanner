
import 'package:flutter/widgets.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:amcCarePlanner/shared/models/entity_arguments.dart';

import 'bloc/equality_bloc.dart';
import 'equality_list_screen.dart';
import 'equality_repository.dart';
import 'equality_update_screen.dart';
import 'equality_view_screen.dart';

class EqualityRoutes {
  static final list = '/entities/equality-list';
  static final create = '/entities/equality-create';
  static final edit = '/entities/equality-edit';
  static final view = '/entities/equality-view';

  static const listScreenKey = Key('__equalityListScreen__');
  static const createScreenKey = Key('__equalityCreateScreen__');
  static const editScreenKey = Key('__equalityEditScreen__');
  static const viewScreenKey = Key('__equalityViewScreen__');

  static final map = <String, WidgetBuilder>{
    list: (context) {
      return BlocProvider<EqualityBloc>(
          create: (context) => EqualityBloc(equalityRepository: EqualityRepository())
            ..add(InitEqualityList()),
          child: EqualityListScreen());
    },
    create: (context) {
      return BlocProvider<EqualityBloc>(
          create: (context) => EqualityBloc(equalityRepository: EqualityRepository()),
          child: EqualityUpdateScreen());
    },
    edit: (context) {
      EntityArguments arguments = ModalRoute.of(context).settings.arguments;
      return BlocProvider<EqualityBloc>(
          create: (context) => EqualityBloc(equalityRepository: EqualityRepository())
            ..add(LoadEqualityByIdForEdit(id: arguments.id)),
          child: EqualityUpdateScreen());
    },
    view: (context) {
      EntityArguments arguments = ModalRoute.of(context).settings.arguments;
      return BlocProvider<EqualityBloc>(
          create: (context) => EqualityBloc(equalityRepository: EqualityRepository())
            ..add(LoadEqualityByIdForView(id: arguments.id)),
          child: EqualityViewScreen());
    },
  };
}
