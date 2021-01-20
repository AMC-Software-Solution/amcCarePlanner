
import 'package:flutter/widgets.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:amcCarePlanner/shared/models/entity_arguments.dart';

import 'bloc/question_bloc.dart';
import 'question_list_screen.dart';
import 'question_repository.dart';
import 'question_update_screen.dart';
import 'question_view_screen.dart';

class QuestionRoutes {
  static final list = '/entities/question-list';
  static final create = '/entities/question-create';
  static final edit = '/entities/question-edit';
  static final view = '/entities/question-view';

  static const listScreenKey = Key('__questionListScreen__');
  static const createScreenKey = Key('__questionCreateScreen__');
  static const editScreenKey = Key('__questionEditScreen__');
  static const viewScreenKey = Key('__questionViewScreen__');

  static final map = <String, WidgetBuilder>{
    list: (context) {
      return BlocProvider<QuestionBloc>(
          create: (context) => QuestionBloc(questionRepository: QuestionRepository())
            ..add(InitQuestionList()),
          child: QuestionListScreen());
    },
    create: (context) {
      return BlocProvider<QuestionBloc>(
          create: (context) => QuestionBloc(questionRepository: QuestionRepository()),
          child: QuestionUpdateScreen());
    },
    edit: (context) {
      EntityArguments arguments = ModalRoute.of(context).settings.arguments;
      return BlocProvider<QuestionBloc>(
          create: (context) => QuestionBloc(questionRepository: QuestionRepository())
            ..add(LoadQuestionByIdForEdit(id: arguments.id)),
          child: QuestionUpdateScreen());
    },
    view: (context) {
      EntityArguments arguments = ModalRoute.of(context).settings.arguments;
      return BlocProvider<QuestionBloc>(
          create: (context) => QuestionBloc(questionRepository: QuestionRepository())
            ..add(LoadQuestionByIdForView(id: arguments.id)),
          child: QuestionViewScreen());
    },
  };
}
