
import 'package:flutter/widgets.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:amcCarePlanner/shared/models/entity_arguments.dart';

import 'bloc/service_order_bloc.dart';
import 'service_order_list_screen.dart';
import 'service_order_repository.dart';
import 'service_order_update_screen.dart';
import 'service_order_view_screen.dart';

class ServiceOrderRoutes {
  static final list = '/entities/serviceOrder-list';
  static final create = '/entities/serviceOrder-create';
  static final edit = '/entities/serviceOrder-edit';
  static final view = '/entities/serviceOrder-view';

  static const listScreenKey = Key('__serviceOrderListScreen__');
  static const createScreenKey = Key('__serviceOrderCreateScreen__');
  static const editScreenKey = Key('__serviceOrderEditScreen__');
  static const viewScreenKey = Key('__serviceOrderViewScreen__');

  static final map = <String, WidgetBuilder>{
    list: (context) {
      return BlocProvider<ServiceOrderBloc>(
          create: (context) => ServiceOrderBloc(serviceOrderRepository: ServiceOrderRepository())
            ..add(InitServiceOrderList()),
          child: ServiceOrderListScreen());
    },
    create: (context) {
      return BlocProvider<ServiceOrderBloc>(
          create: (context) => ServiceOrderBloc(serviceOrderRepository: ServiceOrderRepository()),
          child: ServiceOrderUpdateScreen());
    },
    edit: (context) {
      EntityArguments arguments = ModalRoute.of(context).settings.arguments;
      return BlocProvider<ServiceOrderBloc>(
          create: (context) => ServiceOrderBloc(serviceOrderRepository: ServiceOrderRepository())
            ..add(LoadServiceOrderByIdForEdit(id: arguments.id)),
          child: ServiceOrderUpdateScreen());
    },
    view: (context) {
      EntityArguments arguments = ModalRoute.of(context).settings.arguments;
      return BlocProvider<ServiceOrderBloc>(
          create: (context) => ServiceOrderBloc(serviceOrderRepository: ServiceOrderRepository())
            ..add(LoadServiceOrderByIdForView(id: arguments.id)),
          child: ServiceOrderViewScreen());
    },
  };
}
