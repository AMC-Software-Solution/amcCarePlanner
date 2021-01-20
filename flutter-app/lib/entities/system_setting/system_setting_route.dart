
import 'package:flutter/widgets.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:amcCarePlanner/shared/models/entity_arguments.dart';

import 'bloc/system_setting_bloc.dart';
import 'system_setting_list_screen.dart';
import 'system_setting_repository.dart';
import 'system_setting_update_screen.dart';
import 'system_setting_view_screen.dart';

class SystemSettingRoutes {
  static final list = '/entities/systemSetting-list';
  static final create = '/entities/systemSetting-create';
  static final edit = '/entities/systemSetting-edit';
  static final view = '/entities/systemSetting-view';

  static const listScreenKey = Key('__systemSettingListScreen__');
  static const createScreenKey = Key('__systemSettingCreateScreen__');
  static const editScreenKey = Key('__systemSettingEditScreen__');
  static const viewScreenKey = Key('__systemSettingViewScreen__');

  static final map = <String, WidgetBuilder>{
    list: (context) {
      return BlocProvider<SystemSettingBloc>(
          create: (context) => SystemSettingBloc(systemSettingRepository: SystemSettingRepository())
            ..add(InitSystemSettingList()),
          child: SystemSettingListScreen());
    },
    create: (context) {
      return BlocProvider<SystemSettingBloc>(
          create: (context) => SystemSettingBloc(systemSettingRepository: SystemSettingRepository()),
          child: SystemSettingUpdateScreen());
    },
    edit: (context) {
      EntityArguments arguments = ModalRoute.of(context).settings.arguments;
      return BlocProvider<SystemSettingBloc>(
          create: (context) => SystemSettingBloc(systemSettingRepository: SystemSettingRepository())
            ..add(LoadSystemSettingByIdForEdit(id: arguments.id)),
          child: SystemSettingUpdateScreen());
    },
    view: (context) {
      EntityArguments arguments = ModalRoute.of(context).settings.arguments;
      return BlocProvider<SystemSettingBloc>(
          create: (context) => SystemSettingBloc(systemSettingRepository: SystemSettingRepository())
            ..add(LoadSystemSettingByIdForView(id: arguments.id)),
          child: SystemSettingViewScreen());
    },
  };
}
