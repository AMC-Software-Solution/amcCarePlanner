
import 'package:flutter/widgets.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:amcCarePlanner/shared/models/entity_arguments.dart';

import 'bloc/power_of_attorney_bloc.dart';
import 'power_of_attorney_list_screen.dart';
import 'power_of_attorney_repository.dart';
import 'power_of_attorney_update_screen.dart';
import 'power_of_attorney_view_screen.dart';

class PowerOfAttorneyRoutes {
  static final list = '/entities/powerOfAttorney-list';
  static final create = '/entities/powerOfAttorney-create';
  static final edit = '/entities/powerOfAttorney-edit';
  static final view = '/entities/powerOfAttorney-view';

  static const listScreenKey = Key('__powerOfAttorneyListScreen__');
  static const createScreenKey = Key('__powerOfAttorneyCreateScreen__');
  static const editScreenKey = Key('__powerOfAttorneyEditScreen__');
  static const viewScreenKey = Key('__powerOfAttorneyViewScreen__');

  static final map = <String, WidgetBuilder>{
    list: (context) {
      return BlocProvider<PowerOfAttorneyBloc>(
          create: (context) => PowerOfAttorneyBloc(powerOfAttorneyRepository: PowerOfAttorneyRepository())
            ..add(InitPowerOfAttorneyList()),
          child: PowerOfAttorneyListScreen());
    },
    create: (context) {
      return BlocProvider<PowerOfAttorneyBloc>(
          create: (context) => PowerOfAttorneyBloc(powerOfAttorneyRepository: PowerOfAttorneyRepository()),
          child: PowerOfAttorneyUpdateScreen());
    },
    edit: (context) {
      EntityArguments arguments = ModalRoute.of(context).settings.arguments;
      return BlocProvider<PowerOfAttorneyBloc>(
          create: (context) => PowerOfAttorneyBloc(powerOfAttorneyRepository: PowerOfAttorneyRepository())
            ..add(LoadPowerOfAttorneyByIdForEdit(id: arguments.id)),
          child: PowerOfAttorneyUpdateScreen());
    },
    view: (context) {
      EntityArguments arguments = ModalRoute.of(context).settings.arguments;
      return BlocProvider<PowerOfAttorneyBloc>(
          create: (context) => PowerOfAttorneyBloc(powerOfAttorneyRepository: PowerOfAttorneyRepository())
            ..add(LoadPowerOfAttorneyByIdForView(id: arguments.id)),
          child: PowerOfAttorneyViewScreen());
    },
  };
}
