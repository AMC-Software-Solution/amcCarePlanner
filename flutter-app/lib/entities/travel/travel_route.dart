
import 'package:flutter/widgets.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:amcCarePlanner/shared/models/entity_arguments.dart';

import 'bloc/travel_bloc.dart';
import 'travel_list_screen.dart';
import 'travel_repository.dart';
import 'travel_update_screen.dart';
import 'travel_view_screen.dart';

class TravelRoutes {
  static final list = '/entities/travel-list';
  static final create = '/entities/travel-create';
  static final edit = '/entities/travel-edit';
  static final view = '/entities/travel-view';

  static const listScreenKey = Key('__travelListScreen__');
  static const createScreenKey = Key('__travelCreateScreen__');
  static const editScreenKey = Key('__travelEditScreen__');
  static const viewScreenKey = Key('__travelViewScreen__');

  static final map = <String, WidgetBuilder>{
    list: (context) {
      return BlocProvider<TravelBloc>(
          create: (context) => TravelBloc(travelRepository: TravelRepository())
            ..add(InitTravelList()),
          child: TravelListScreen());
    },
    create: (context) {
      return BlocProvider<TravelBloc>(
          create: (context) => TravelBloc(travelRepository: TravelRepository()),
          child: TravelUpdateScreen());
    },
    edit: (context) {
      EntityArguments arguments = ModalRoute.of(context).settings.arguments;
      return BlocProvider<TravelBloc>(
          create: (context) => TravelBloc(travelRepository: TravelRepository())
            ..add(LoadTravelByIdForEdit(id: arguments.id)),
          child: TravelUpdateScreen());
    },
    view: (context) {
      EntityArguments arguments = ModalRoute.of(context).settings.arguments;
      return BlocProvider<TravelBloc>(
          create: (context) => TravelBloc(travelRepository: TravelRepository())
            ..add(LoadTravelByIdForView(id: arguments.id)),
          child: TravelViewScreen());
    },
  };
}
