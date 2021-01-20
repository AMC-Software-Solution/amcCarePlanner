
import 'package:flutter/widgets.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:amcCarePlanner/shared/models/entity_arguments.dart';

import 'bloc/disability_bloc.dart';
import 'disability_list_screen.dart';
import 'disability_repository.dart';
import 'disability_update_screen.dart';
import 'disability_view_screen.dart';

class DisabilityRoutes {
  static final list = '/entities/disability-list';
  static final create = '/entities/disability-create';
  static final edit = '/entities/disability-edit';
  static final view = '/entities/disability-view';

  static const listScreenKey = Key('__disabilityListScreen__');
  static const createScreenKey = Key('__disabilityCreateScreen__');
  static const editScreenKey = Key('__disabilityEditScreen__');
  static const viewScreenKey = Key('__disabilityViewScreen__');

  static final map = <String, WidgetBuilder>{
    list: (context) {
      return BlocProvider<DisabilityBloc>(
          create: (context) => DisabilityBloc(disabilityRepository: DisabilityRepository())
            ..add(InitDisabilityList()),
          child: DisabilityListScreen());
    },
    create: (context) {
      return BlocProvider<DisabilityBloc>(
          create: (context) => DisabilityBloc(disabilityRepository: DisabilityRepository()),
          child: DisabilityUpdateScreen());
    },
    edit: (context) {
      EntityArguments arguments = ModalRoute.of(context).settings.arguments;
      return BlocProvider<DisabilityBloc>(
          create: (context) => DisabilityBloc(disabilityRepository: DisabilityRepository())
            ..add(LoadDisabilityByIdForEdit(id: arguments.id)),
          child: DisabilityUpdateScreen());
    },
    view: (context) {
      EntityArguments arguments = ModalRoute.of(context).settings.arguments;
      return BlocProvider<DisabilityBloc>(
          create: (context) => DisabilityBloc(disabilityRepository: DisabilityRepository())
            ..add(LoadDisabilityByIdForView(id: arguments.id)),
          child: DisabilityViewScreen());
    },
  };
}
