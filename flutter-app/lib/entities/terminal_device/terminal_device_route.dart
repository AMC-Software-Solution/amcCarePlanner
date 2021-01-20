
import 'package:flutter/widgets.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:amcCarePlanner/shared/models/entity_arguments.dart';

import 'bloc/terminal_device_bloc.dart';
import 'terminal_device_list_screen.dart';
import 'terminal_device_repository.dart';
import 'terminal_device_update_screen.dart';
import 'terminal_device_view_screen.dart';

class TerminalDeviceRoutes {
  static final list = '/entities/terminalDevice-list';
  static final create = '/entities/terminalDevice-create';
  static final edit = '/entities/terminalDevice-edit';
  static final view = '/entities/terminalDevice-view';

  static const listScreenKey = Key('__terminalDeviceListScreen__');
  static const createScreenKey = Key('__terminalDeviceCreateScreen__');
  static const editScreenKey = Key('__terminalDeviceEditScreen__');
  static const viewScreenKey = Key('__terminalDeviceViewScreen__');

  static final map = <String, WidgetBuilder>{
    list: (context) {
      return BlocProvider<TerminalDeviceBloc>(
          create: (context) => TerminalDeviceBloc(terminalDeviceRepository: TerminalDeviceRepository())
            ..add(InitTerminalDeviceList()),
          child: TerminalDeviceListScreen());
    },
    create: (context) {
      return BlocProvider<TerminalDeviceBloc>(
          create: (context) => TerminalDeviceBloc(terminalDeviceRepository: TerminalDeviceRepository()),
          child: TerminalDeviceUpdateScreen());
    },
    edit: (context) {
      EntityArguments arguments = ModalRoute.of(context).settings.arguments;
      return BlocProvider<TerminalDeviceBloc>(
          create: (context) => TerminalDeviceBloc(terminalDeviceRepository: TerminalDeviceRepository())
            ..add(LoadTerminalDeviceByIdForEdit(id: arguments.id)),
          child: TerminalDeviceUpdateScreen());
    },
    view: (context) {
      EntityArguments arguments = ModalRoute.of(context).settings.arguments;
      return BlocProvider<TerminalDeviceBloc>(
          create: (context) => TerminalDeviceBloc(terminalDeviceRepository: TerminalDeviceRepository())
            ..add(LoadTerminalDeviceByIdForView(id: arguments.id)),
          child: TerminalDeviceViewScreen());
    },
  };
}
