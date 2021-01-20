
import 'package:flutter/widgets.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:amcCarePlanner/shared/models/entity_arguments.dart';

import 'bloc/eligibility_type_bloc.dart';
import 'eligibility_type_list_screen.dart';
import 'eligibility_type_repository.dart';
import 'eligibility_type_update_screen.dart';
import 'eligibility_type_view_screen.dart';

class EligibilityTypeRoutes {
  static final list = '/entities/eligibilityType-list';
  static final create = '/entities/eligibilityType-create';
  static final edit = '/entities/eligibilityType-edit';
  static final view = '/entities/eligibilityType-view';

  static const listScreenKey = Key('__eligibilityTypeListScreen__');
  static const createScreenKey = Key('__eligibilityTypeCreateScreen__');
  static const editScreenKey = Key('__eligibilityTypeEditScreen__');
  static const viewScreenKey = Key('__eligibilityTypeViewScreen__');

  static final map = <String, WidgetBuilder>{
    list: (context) {
      return BlocProvider<EligibilityTypeBloc>(
          create: (context) => EligibilityTypeBloc(eligibilityTypeRepository: EligibilityTypeRepository())
            ..add(InitEligibilityTypeList()),
          child: EligibilityTypeListScreen());
    },
    create: (context) {
      return BlocProvider<EligibilityTypeBloc>(
          create: (context) => EligibilityTypeBloc(eligibilityTypeRepository: EligibilityTypeRepository()),
          child: EligibilityTypeUpdateScreen());
    },
    edit: (context) {
      EntityArguments arguments = ModalRoute.of(context).settings.arguments;
      return BlocProvider<EligibilityTypeBloc>(
          create: (context) => EligibilityTypeBloc(eligibilityTypeRepository: EligibilityTypeRepository())
            ..add(LoadEligibilityTypeByIdForEdit(id: arguments.id)),
          child: EligibilityTypeUpdateScreen());
    },
    view: (context) {
      EntityArguments arguments = ModalRoute.of(context).settings.arguments;
      return BlocProvider<EligibilityTypeBloc>(
          create: (context) => EligibilityTypeBloc(eligibilityTypeRepository: EligibilityTypeRepository())
            ..add(LoadEligibilityTypeByIdForView(id: arguments.id)),
          child: EligibilityTypeViewScreen());
    },
  };
}
