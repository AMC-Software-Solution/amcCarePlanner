
import 'package:flutter/widgets.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:amcCarePlanner/shared/models/entity_arguments.dart';

import 'bloc/disability_type_bloc.dart';
import 'disability_type_list_screen.dart';
import 'disability_type_repository.dart';
import 'disability_type_update_screen.dart';
import 'disability_type_view_screen.dart';

class DisabilityTypeRoutes {
  static final list = '/entities/disabilityType-list';
  static final create = '/entities/disabilityType-create';
  static final edit = '/entities/disabilityType-edit';
  static final view = '/entities/disabilityType-view';

  static const listScreenKey = Key('__disabilityTypeListScreen__');
  static const createScreenKey = Key('__disabilityTypeCreateScreen__');
  static const editScreenKey = Key('__disabilityTypeEditScreen__');
  static const viewScreenKey = Key('__disabilityTypeViewScreen__');

  static final map = <String, WidgetBuilder>{
    list: (context) {
      return BlocProvider<DisabilityTypeBloc>(
          create: (context) => DisabilityTypeBloc(disabilityTypeRepository: DisabilityTypeRepository())
            ..add(InitDisabilityTypeList()),
          child: DisabilityTypeListScreen());
    },
    create: (context) {
      return BlocProvider<DisabilityTypeBloc>(
          create: (context) => DisabilityTypeBloc(disabilityTypeRepository: DisabilityTypeRepository()),
          child: DisabilityTypeUpdateScreen());
    },
    edit: (context) {
      EntityArguments arguments = ModalRoute.of(context).settings.arguments;
      return BlocProvider<DisabilityTypeBloc>(
          create: (context) => DisabilityTypeBloc(disabilityTypeRepository: DisabilityTypeRepository())
            ..add(LoadDisabilityTypeByIdForEdit(id: arguments.id)),
          child: DisabilityTypeUpdateScreen());
    },
    view: (context) {
      EntityArguments arguments = ModalRoute.of(context).settings.arguments;
      return BlocProvider<DisabilityTypeBloc>(
          create: (context) => DisabilityTypeBloc(disabilityTypeRepository: DisabilityTypeRepository())
            ..add(LoadDisabilityTypeByIdForView(id: arguments.id)),
          child: DisabilityTypeViewScreen());
    },
  };
}
