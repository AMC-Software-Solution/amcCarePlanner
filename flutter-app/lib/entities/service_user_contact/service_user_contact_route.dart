
import 'package:flutter/widgets.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:amcCarePlanner/shared/models/entity_arguments.dart';

import 'bloc/service_user_contact_bloc.dart';
import 'service_user_contact_list_screen.dart';
import 'service_user_contact_repository.dart';
import 'service_user_contact_update_screen.dart';
import 'service_user_contact_view_screen.dart';

class ServiceUserContactRoutes {
  static final list = '/entities/serviceUserContact-list';
  static final create = '/entities/serviceUserContact-create';
  static final edit = '/entities/serviceUserContact-edit';
  static final view = '/entities/serviceUserContact-view';

  static const listScreenKey = Key('__serviceUserContactListScreen__');
  static const createScreenKey = Key('__serviceUserContactCreateScreen__');
  static const editScreenKey = Key('__serviceUserContactEditScreen__');
  static const viewScreenKey = Key('__serviceUserContactViewScreen__');

  static final map = <String, WidgetBuilder>{
    list: (context) {
      return BlocProvider<ServiceUserContactBloc>(
          create: (context) => ServiceUserContactBloc(serviceUserContactRepository: ServiceUserContactRepository())
            ..add(InitServiceUserContactList()),
          child: ServiceUserContactListScreen());
    },
    create: (context) {
      return BlocProvider<ServiceUserContactBloc>(
          create: (context) => ServiceUserContactBloc(serviceUserContactRepository: ServiceUserContactRepository()),
          child: ServiceUserContactUpdateScreen());
    },
    edit: (context) {
      EntityArguments arguments = ModalRoute.of(context).settings.arguments;
      return BlocProvider<ServiceUserContactBloc>(
          create: (context) => ServiceUserContactBloc(serviceUserContactRepository: ServiceUserContactRepository())
            ..add(LoadServiceUserContactByIdForEdit(id: arguments.id)),
          child: ServiceUserContactUpdateScreen());
    },
    view: (context) {
      EntityArguments arguments = ModalRoute.of(context).settings.arguments;
      return BlocProvider<ServiceUserContactBloc>(
          create: (context) => ServiceUserContactBloc(serviceUserContactRepository: ServiceUserContactRepository())
            ..add(LoadServiceUserContactByIdForView(id: arguments.id)),
          child: ServiceUserContactViewScreen());
    },
  };
}
