
import 'package:flutter/widgets.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:amcCarePlanner/shared/models/entity_arguments.dart';

import 'bloc/emergency_contact_bloc.dart';
import 'emergency_contact_list_screen.dart';
import 'emergency_contact_repository.dart';
import 'emergency_contact_update_screen.dart';
import 'emergency_contact_view_screen.dart';

class EmergencyContactRoutes {
  static final list = '/entities/emergencyContact-list';
  static final create = '/entities/emergencyContact-create';
  static final edit = '/entities/emergencyContact-edit';
  static final view = '/entities/emergencyContact-view';

  static const listScreenKey = Key('__emergencyContactListScreen__');
  static const createScreenKey = Key('__emergencyContactCreateScreen__');
  static const editScreenKey = Key('__emergencyContactEditScreen__');
  static const viewScreenKey = Key('__emergencyContactViewScreen__');

  static final map = <String, WidgetBuilder>{
    list: (context) {
      return BlocProvider<EmergencyContactBloc>(
          create: (context) => EmergencyContactBloc(emergencyContactRepository: EmergencyContactRepository())
            ..add(InitEmergencyContactList()),
          child: EmergencyContactListScreen());
    },
    create: (context) {
      return BlocProvider<EmergencyContactBloc>(
          create: (context) => EmergencyContactBloc(emergencyContactRepository: EmergencyContactRepository()),
          child: EmergencyContactUpdateScreen());
    },
    edit: (context) {
      EntityArguments arguments = ModalRoute.of(context).settings.arguments;
      return BlocProvider<EmergencyContactBloc>(
          create: (context) => EmergencyContactBloc(emergencyContactRepository: EmergencyContactRepository())
            ..add(LoadEmergencyContactByIdForEdit(id: arguments.id)),
          child: EmergencyContactUpdateScreen());
    },
    view: (context) {
      EntityArguments arguments = ModalRoute.of(context).settings.arguments;
      return BlocProvider<EmergencyContactBloc>(
          create: (context) => EmergencyContactBloc(emergencyContactRepository: EmergencyContactRepository())
            ..add(LoadEmergencyContactByIdForView(id: arguments.id)),
          child: EmergencyContactViewScreen());
    },
  };
}
