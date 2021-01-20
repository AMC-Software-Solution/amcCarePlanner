
import 'package:flutter/widgets.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:amcCarePlanner/shared/models/entity_arguments.dart';

import 'bloc/service_user_location_bloc.dart';
import 'service_user_location_list_screen.dart';
import 'service_user_location_repository.dart';
import 'service_user_location_update_screen.dart';
import 'service_user_location_view_screen.dart';

class ServiceUserLocationRoutes {
  static final list = '/entities/serviceUserLocation-list';
  static final create = '/entities/serviceUserLocation-create';
  static final edit = '/entities/serviceUserLocation-edit';
  static final view = '/entities/serviceUserLocation-view';

  static const listScreenKey = Key('__serviceUserLocationListScreen__');
  static const createScreenKey = Key('__serviceUserLocationCreateScreen__');
  static const editScreenKey = Key('__serviceUserLocationEditScreen__');
  static const viewScreenKey = Key('__serviceUserLocationViewScreen__');

  static final map = <String, WidgetBuilder>{
    list: (context) {
      return BlocProvider<ServiceUserLocationBloc>(
          create: (context) => ServiceUserLocationBloc(serviceUserLocationRepository: ServiceUserLocationRepository())
            ..add(InitServiceUserLocationList()),
          child: ServiceUserLocationListScreen());
    },
    create: (context) {
      return BlocProvider<ServiceUserLocationBloc>(
          create: (context) => ServiceUserLocationBloc(serviceUserLocationRepository: ServiceUserLocationRepository()),
          child: ServiceUserLocationUpdateScreen());
    },
    edit: (context) {
      EntityArguments arguments = ModalRoute.of(context).settings.arguments;
      return BlocProvider<ServiceUserLocationBloc>(
          create: (context) => ServiceUserLocationBloc(serviceUserLocationRepository: ServiceUserLocationRepository())
            ..add(LoadServiceUserLocationByIdForEdit(id: arguments.id)),
          child: ServiceUserLocationUpdateScreen());
    },
    view: (context) {
      EntityArguments arguments = ModalRoute.of(context).settings.arguments;
      return BlocProvider<ServiceUserLocationBloc>(
          create: (context) => ServiceUserLocationBloc(serviceUserLocationRepository: ServiceUserLocationRepository())
            ..add(LoadServiceUserLocationByIdForView(id: arguments.id)),
          child: ServiceUserLocationViewScreen());
    },
  };
}
