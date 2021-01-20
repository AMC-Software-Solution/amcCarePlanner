
import 'package:flutter/widgets.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:amcCarePlanner/shared/models/entity_arguments.dart';

import 'bloc/branch_bloc.dart';
import 'branch_list_screen.dart';
import 'branch_repository.dart';
import 'branch_update_screen.dart';
import 'branch_view_screen.dart';

class BranchRoutes {
  static final list = '/entities/branch-list';
  static final create = '/entities/branch-create';
  static final edit = '/entities/branch-edit';
  static final view = '/entities/branch-view';

  static const listScreenKey = Key('__branchListScreen__');
  static const createScreenKey = Key('__branchCreateScreen__');
  static const editScreenKey = Key('__branchEditScreen__');
  static const viewScreenKey = Key('__branchViewScreen__');

  static final map = <String, WidgetBuilder>{
    list: (context) {
      return BlocProvider<BranchBloc>(
          create: (context) => BranchBloc(branchRepository: BranchRepository())
            ..add(InitBranchList()),
          child: BranchListScreen());
    },
    create: (context) {
      return BlocProvider<BranchBloc>(
          create: (context) => BranchBloc(branchRepository: BranchRepository()),
          child: BranchUpdateScreen());
    },
    edit: (context) {
      EntityArguments arguments = ModalRoute.of(context).settings.arguments;
      return BlocProvider<BranchBloc>(
          create: (context) => BranchBloc(branchRepository: BranchRepository())
            ..add(LoadBranchByIdForEdit(id: arguments.id)),
          child: BranchUpdateScreen());
    },
    view: (context) {
      EntityArguments arguments = ModalRoute.of(context).settings.arguments;
      return BlocProvider<BranchBloc>(
          create: (context) => BranchBloc(branchRepository: BranchRepository())
            ..add(LoadBranchByIdForView(id: arguments.id)),
          child: BranchViewScreen());
    },
  };
}
