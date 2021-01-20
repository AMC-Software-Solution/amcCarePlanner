
import 'package:flutter/widgets.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:amcCarePlanner/shared/models/entity_arguments.dart';

import 'bloc/service_user_bloc.dart';
import 'service_user_list_screen.dart';
import 'service_user_repository.dart';
import 'service_user_update_screen.dart';
import 'service_user_view_screen.dart';

class ServiceUserRoutes {
  static final list = '/entities/serviceUser-list';
  static final create = '/entities/serviceUser-create';
  static final edit = '/entities/serviceUser-edit';
  static final view = '/entities/serviceUser-view';

  static const listScreenKey = Key('__serviceUserListScreen__');
  static const createScreenKey = Key('__serviceUserCreateScreen__');
  static const editScreenKey = Key('__serviceUserEditScreen__');
  static const viewScreenKey = Key('__serviceUserViewScreen__');

  static final map = <String, WidgetBuilder>{
    list: (context) {
      return BlocProvider<ServiceUserBloc>(
          create: (context) => ServiceUserBloc(serviceUserRepository: ServiceUserRepository())
            ..add(InitServiceUserList()),
          child: ServiceUserListScreen());
    },
    create: (context) {
      return BlocProvider<ServiceUserBloc>(
          create: (context) => ServiceUserBloc(serviceUserRepository: ServiceUserRepository()),
          child: ServiceUserUpdateScreen());
    },
    edit: (context) {
      EntityArguments arguments = ModalRoute.of(context).settings.arguments;
      return BlocProvider<ServiceUserBloc>(
          create: (context) => ServiceUserBloc(serviceUserRepository: ServiceUserRepository())
            ..add(LoadServiceUserByIdForEdit(id: arguments.id)),
          child: ServiceUserUpdateScreen());
    },
    view: (context) {
      EntityArguments arguments = ModalRoute.of(context).settings.arguments;
      return BlocProvider<ServiceUserBloc>(
          create: (context) => ServiceUserBloc(serviceUserRepository: ServiceUserRepository())
            ..add(LoadServiceUserByIdForView(id: arguments.id)),
          child: ServiceUserViewScreen());
    },
  };
}
