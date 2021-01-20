
import 'package:flutter/widgets.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:amcCarePlanner/shared/models/entity_arguments.dart';

import 'bloc/eligibility_bloc.dart';
import 'eligibility_list_screen.dart';
import 'eligibility_repository.dart';
import 'eligibility_update_screen.dart';
import 'eligibility_view_screen.dart';

class EligibilityRoutes {
  static final list = '/entities/eligibility-list';
  static final create = '/entities/eligibility-create';
  static final edit = '/entities/eligibility-edit';
  static final view = '/entities/eligibility-view';

  static const listScreenKey = Key('__eligibilityListScreen__');
  static const createScreenKey = Key('__eligibilityCreateScreen__');
  static const editScreenKey = Key('__eligibilityEditScreen__');
  static const viewScreenKey = Key('__eligibilityViewScreen__');

  static final map = <String, WidgetBuilder>{
    list: (context) {
      return BlocProvider<EligibilityBloc>(
          create: (context) => EligibilityBloc(eligibilityRepository: EligibilityRepository())
            ..add(InitEligibilityList()),
          child: EligibilityListScreen());
    },
    create: (context) {
      return BlocProvider<EligibilityBloc>(
          create: (context) => EligibilityBloc(eligibilityRepository: EligibilityRepository()),
          child: EligibilityUpdateScreen());
    },
    edit: (context) {
      EntityArguments arguments = ModalRoute.of(context).settings.arguments;
      return BlocProvider<EligibilityBloc>(
          create: (context) => EligibilityBloc(eligibilityRepository: EligibilityRepository())
            ..add(LoadEligibilityByIdForEdit(id: arguments.id)),
          child: EligibilityUpdateScreen());
    },
    view: (context) {
      EntityArguments arguments = ModalRoute.of(context).settings.arguments;
      return BlocProvider<EligibilityBloc>(
          create: (context) => EligibilityBloc(eligibilityRepository: EligibilityRepository())
            ..add(LoadEligibilityByIdForView(id: arguments.id)),
          child: EligibilityViewScreen());
    },
  };
}
