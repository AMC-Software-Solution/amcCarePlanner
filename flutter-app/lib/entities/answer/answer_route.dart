
import 'package:flutter/widgets.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:amcCarePlanner/shared/models/entity_arguments.dart';

import 'bloc/answer_bloc.dart';
import 'answer_list_screen.dart';
import 'answer_repository.dart';
import 'answer_update_screen.dart';
import 'answer_view_screen.dart';

class AnswerRoutes {
  static final list = '/entities/answer-list';
  static final create = '/entities/answer-create';
  static final edit = '/entities/answer-edit';
  static final view = '/entities/answer-view';

  static const listScreenKey = Key('__answerListScreen__');
  static const createScreenKey = Key('__answerCreateScreen__');
  static const editScreenKey = Key('__answerEditScreen__');
  static const viewScreenKey = Key('__answerViewScreen__');

  static final map = <String, WidgetBuilder>{
    list: (context) {
      return BlocProvider<AnswerBloc>(
          create: (context) => AnswerBloc(answerRepository: AnswerRepository())
            ..add(InitAnswerList()),
          child: AnswerListScreen());
    },
    create: (context) {
      return BlocProvider<AnswerBloc>(
          create: (context) => AnswerBloc(answerRepository: AnswerRepository()),
          child: AnswerUpdateScreen());
    },
    edit: (context) {
      EntityArguments arguments = ModalRoute.of(context).settings.arguments;
      return BlocProvider<AnswerBloc>(
          create: (context) => AnswerBloc(answerRepository: AnswerRepository())
            ..add(LoadAnswerByIdForEdit(id: arguments.id)),
          child: AnswerUpdateScreen());
    },
    view: (context) {
      EntityArguments arguments = ModalRoute.of(context).settings.arguments;
      return BlocProvider<AnswerBloc>(
          create: (context) => AnswerBloc(answerRepository: AnswerRepository())
            ..add(LoadAnswerByIdForView(id: arguments.id)),
          child: AnswerViewScreen());
    },
  };
}
