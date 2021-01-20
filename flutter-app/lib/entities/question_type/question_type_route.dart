
import 'package:flutter/widgets.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:amcCarePlanner/shared/models/entity_arguments.dart';

import 'bloc/question_type_bloc.dart';
import 'question_type_list_screen.dart';
import 'question_type_repository.dart';
import 'question_type_update_screen.dart';
import 'question_type_view_screen.dart';

class QuestionTypeRoutes {
  static final list = '/entities/questionType-list';
  static final create = '/entities/questionType-create';
  static final edit = '/entities/questionType-edit';
  static final view = '/entities/questionType-view';

  static const listScreenKey = Key('__questionTypeListScreen__');
  static const createScreenKey = Key('__questionTypeCreateScreen__');
  static const editScreenKey = Key('__questionTypeEditScreen__');
  static const viewScreenKey = Key('__questionTypeViewScreen__');

  static final map = <String, WidgetBuilder>{
    list: (context) {
      return BlocProvider<QuestionTypeBloc>(
          create: (context) => QuestionTypeBloc(questionTypeRepository: QuestionTypeRepository())
            ..add(InitQuestionTypeList()),
          child: QuestionTypeListScreen());
    },
    create: (context) {
      return BlocProvider<QuestionTypeBloc>(
          create: (context) => QuestionTypeBloc(questionTypeRepository: QuestionTypeRepository()),
          child: QuestionTypeUpdateScreen());
    },
    edit: (context) {
      EntityArguments arguments = ModalRoute.of(context).settings.arguments;
      return BlocProvider<QuestionTypeBloc>(
          create: (context) => QuestionTypeBloc(questionTypeRepository: QuestionTypeRepository())
            ..add(LoadQuestionTypeByIdForEdit(id: arguments.id)),
          child: QuestionTypeUpdateScreen());
    },
    view: (context) {
      EntityArguments arguments = ModalRoute.of(context).settings.arguments;
      return BlocProvider<QuestionTypeBloc>(
          create: (context) => QuestionTypeBloc(questionTypeRepository: QuestionTypeRepository())
            ..add(LoadQuestionTypeByIdForView(id: arguments.id)),
          child: QuestionTypeViewScreen());
    },
  };
}
