
import 'package:flutter/widgets.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:amcCarePlanner/shared/models/entity_arguments.dart';

import 'bloc/extra_data_bloc.dart';
import 'extra_data_list_screen.dart';
import 'extra_data_repository.dart';
import 'extra_data_update_screen.dart';
import 'extra_data_view_screen.dart';

class ExtraDataRoutes {
  static final list = '/entities/extraData-list';
  static final create = '/entities/extraData-create';
  static final edit = '/entities/extraData-edit';
  static final view = '/entities/extraData-view';

  static const listScreenKey = Key('__extraDataListScreen__');
  static const createScreenKey = Key('__extraDataCreateScreen__');
  static const editScreenKey = Key('__extraDataEditScreen__');
  static const viewScreenKey = Key('__extraDataViewScreen__');

  static final map = <String, WidgetBuilder>{
    list: (context) {
      return BlocProvider<ExtraDataBloc>(
          create: (context) => ExtraDataBloc(extraDataRepository: ExtraDataRepository())
            ..add(InitExtraDataList()),
          child: ExtraDataListScreen());
    },
    create: (context) {
      return BlocProvider<ExtraDataBloc>(
          create: (context) => ExtraDataBloc(extraDataRepository: ExtraDataRepository()),
          child: ExtraDataUpdateScreen());
    },
    edit: (context) {
      EntityArguments arguments = ModalRoute.of(context).settings.arguments;
      return BlocProvider<ExtraDataBloc>(
          create: (context) => ExtraDataBloc(extraDataRepository: ExtraDataRepository())
            ..add(LoadExtraDataByIdForEdit(id: arguments.id)),
          child: ExtraDataUpdateScreen());
    },
    view: (context) {
      EntityArguments arguments = ModalRoute.of(context).settings.arguments;
      return BlocProvider<ExtraDataBloc>(
          create: (context) => ExtraDataBloc(extraDataRepository: ExtraDataRepository())
            ..add(LoadExtraDataByIdForView(id: arguments.id)),
          child: ExtraDataViewScreen());
    },
  };
}
