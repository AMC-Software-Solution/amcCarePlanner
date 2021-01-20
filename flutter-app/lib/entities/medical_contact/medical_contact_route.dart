
import 'package:flutter/widgets.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:amcCarePlanner/shared/models/entity_arguments.dart';

import 'bloc/medical_contact_bloc.dart';
import 'medical_contact_list_screen.dart';
import 'medical_contact_repository.dart';
import 'medical_contact_update_screen.dart';
import 'medical_contact_view_screen.dart';

class MedicalContactRoutes {
  static final list = '/entities/medicalContact-list';
  static final create = '/entities/medicalContact-create';
  static final edit = '/entities/medicalContact-edit';
  static final view = '/entities/medicalContact-view';

  static const listScreenKey = Key('__medicalContactListScreen__');
  static const createScreenKey = Key('__medicalContactCreateScreen__');
  static const editScreenKey = Key('__medicalContactEditScreen__');
  static const viewScreenKey = Key('__medicalContactViewScreen__');

  static final map = <String, WidgetBuilder>{
    list: (context) {
      return BlocProvider<MedicalContactBloc>(
          create: (context) => MedicalContactBloc(medicalContactRepository: MedicalContactRepository())
            ..add(InitMedicalContactList()),
          child: MedicalContactListScreen());
    },
    create: (context) {
      return BlocProvider<MedicalContactBloc>(
          create: (context) => MedicalContactBloc(medicalContactRepository: MedicalContactRepository()),
          child: MedicalContactUpdateScreen());
    },
    edit: (context) {
      EntityArguments arguments = ModalRoute.of(context).settings.arguments;
      return BlocProvider<MedicalContactBloc>(
          create: (context) => MedicalContactBloc(medicalContactRepository: MedicalContactRepository())
            ..add(LoadMedicalContactByIdForEdit(id: arguments.id)),
          child: MedicalContactUpdateScreen());
    },
    view: (context) {
      EntityArguments arguments = ModalRoute.of(context).settings.arguments;
      return BlocProvider<MedicalContactBloc>(
          create: (context) => MedicalContactBloc(medicalContactRepository: MedicalContactRepository())
            ..add(LoadMedicalContactByIdForView(id: arguments.id)),
          child: MedicalContactViewScreen());
    },
  };
}
