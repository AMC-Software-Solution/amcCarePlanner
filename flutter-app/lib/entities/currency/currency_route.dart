
import 'package:flutter/widgets.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:amcCarePlanner/shared/models/entity_arguments.dart';

import 'bloc/currency_bloc.dart';
import 'currency_list_screen.dart';
import 'currency_repository.dart';
import 'currency_update_screen.dart';
import 'currency_view_screen.dart';

class CurrencyRoutes {
  static final list = '/entities/currency-list';
  static final create = '/entities/currency-create';
  static final edit = '/entities/currency-edit';
  static final view = '/entities/currency-view';

  static const listScreenKey = Key('__currencyListScreen__');
  static const createScreenKey = Key('__currencyCreateScreen__');
  static const editScreenKey = Key('__currencyEditScreen__');
  static const viewScreenKey = Key('__currencyViewScreen__');

  static final map = <String, WidgetBuilder>{
    list: (context) {
      return BlocProvider<CurrencyBloc>(
          create: (context) => CurrencyBloc(currencyRepository: CurrencyRepository())
            ..add(InitCurrencyList()),
          child: CurrencyListScreen());
    },
    create: (context) {
      return BlocProvider<CurrencyBloc>(
          create: (context) => CurrencyBloc(currencyRepository: CurrencyRepository()),
          child: CurrencyUpdateScreen());
    },
    edit: (context) {
      EntityArguments arguments = ModalRoute.of(context).settings.arguments;
      return BlocProvider<CurrencyBloc>(
          create: (context) => CurrencyBloc(currencyRepository: CurrencyRepository())
            ..add(LoadCurrencyByIdForEdit(id: arguments.id)),
          child: CurrencyUpdateScreen());
    },
    view: (context) {
      EntityArguments arguments = ModalRoute.of(context).settings.arguments;
      return BlocProvider<CurrencyBloc>(
          create: (context) => CurrencyBloc(currencyRepository: CurrencyRepository())
            ..add(LoadCurrencyByIdForView(id: arguments.id)),
          child: CurrencyViewScreen());
    },
  };
}
