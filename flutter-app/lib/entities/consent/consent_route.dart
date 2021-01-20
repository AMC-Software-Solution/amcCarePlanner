
import 'package:flutter/widgets.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:amcCarePlanner/shared/models/entity_arguments.dart';

import 'bloc/consent_bloc.dart';
import 'consent_list_screen.dart';
import 'consent_repository.dart';
import 'consent_update_screen.dart';
import 'consent_view_screen.dart';

class ConsentRoutes {
  static final list = '/entities/consent-list';
  static final create = '/entities/consent-create';
  static final edit = '/entities/consent-edit';
  static final view = '/entities/consent-view';

  static const listScreenKey = Key('__consentListScreen__');
  static const createScreenKey = Key('__consentCreateScreen__');
  static const editScreenKey = Key('__consentEditScreen__');
  static const viewScreenKey = Key('__consentViewScreen__');

  static final map = <String, WidgetBuilder>{
    list: (context) {
      return BlocProvider<ConsentBloc>(
          create: (context) => ConsentBloc(consentRepository: ConsentRepository())
            ..add(InitConsentList()),
          child: ConsentListScreen());
    },
    create: (context) {
      return BlocProvider<ConsentBloc>(
          create: (context) => ConsentBloc(consentRepository: ConsentRepository()),
          child: ConsentUpdateScreen());
    },
    edit: (context) {
      EntityArguments arguments = ModalRoute.of(context).settings.arguments;
      return BlocProvider<ConsentBloc>(
          create: (context) => ConsentBloc(consentRepository: ConsentRepository())
            ..add(LoadConsentByIdForEdit(id: arguments.id)),
          child: ConsentUpdateScreen());
    },
    view: (context) {
      EntityArguments arguments = ModalRoute.of(context).settings.arguments;
      return BlocProvider<ConsentBloc>(
          create: (context) => ConsentBloc(consentRepository: ConsentRepository())
            ..add(LoadConsentByIdForView(id: arguments.id)),
          child: ConsentViewScreen());
    },
  };
}
