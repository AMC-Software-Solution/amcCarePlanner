
import 'package:flutter/widgets.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:amcCarePlanner/shared/models/entity_arguments.dart';

import 'bloc/invoice_bloc.dart';
import 'invoice_list_screen.dart';
import 'invoice_repository.dart';
import 'invoice_update_screen.dart';
import 'invoice_view_screen.dart';

class InvoiceRoutes {
  static final list = '/entities/invoice-list';
  static final create = '/entities/invoice-create';
  static final edit = '/entities/invoice-edit';
  static final view = '/entities/invoice-view';

  static const listScreenKey = Key('__invoiceListScreen__');
  static const createScreenKey = Key('__invoiceCreateScreen__');
  static const editScreenKey = Key('__invoiceEditScreen__');
  static const viewScreenKey = Key('__invoiceViewScreen__');

  static final map = <String, WidgetBuilder>{
    list: (context) {
      return BlocProvider<InvoiceBloc>(
          create: (context) => InvoiceBloc(invoiceRepository: InvoiceRepository())
            ..add(InitInvoiceList()),
          child: InvoiceListScreen());
    },
    create: (context) {
      return BlocProvider<InvoiceBloc>(
          create: (context) => InvoiceBloc(invoiceRepository: InvoiceRepository()),
          child: InvoiceUpdateScreen());
    },
    edit: (context) {
      EntityArguments arguments = ModalRoute.of(context).settings.arguments;
      return BlocProvider<InvoiceBloc>(
          create: (context) => InvoiceBloc(invoiceRepository: InvoiceRepository())
            ..add(LoadInvoiceByIdForEdit(id: arguments.id)),
          child: InvoiceUpdateScreen());
    },
    view: (context) {
      EntityArguments arguments = ModalRoute.of(context).settings.arguments;
      return BlocProvider<InvoiceBloc>(
          create: (context) => InvoiceBloc(invoiceRepository: InvoiceRepository())
            ..add(LoadInvoiceByIdForView(id: arguments.id)),
          child: InvoiceViewScreen());
    },
  };
}
