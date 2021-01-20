import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:amcCarePlanner/entities/question_type/bloc/question_type_bloc.dart';
import 'package:amcCarePlanner/entities/question_type/question_type_model.dart';
import 'package:amcCarePlanner/generated/l10n.dart';
import 'package:flutter/material.dart';
import 'package:intl/intl.dart';
import 'package:amcCarePlanner/shared/widgets/loading_indicator_widget.dart';
import 'question_type_route.dart';

class QuestionTypeViewScreen extends StatelessWidget {
  QuestionTypeViewScreen({Key key}) : super(key: QuestionTypeRoutes.createScreenKey);

  @override
  Widget build(BuildContext context) {
    return Scaffold(
          appBar: AppBar(
            centerTitle: true,
            title:Text(S.of(context).pageEntitiesQuestionTypeViewTitle),
          ),
          body: SingleChildScrollView(
            padding: const EdgeInsets.all(15.0),
            child: BlocBuilder<QuestionTypeBloc, QuestionTypeState>(
              buildWhen: (previous, current) => previous.loadedQuestionType != current.loadedQuestionType,
              builder: (context, state) {
                return Visibility(
                  visible: state.questionTypeStatusUI == QuestionTypeStatusUI.done,
                  replacement: LoadingIndicator(),
                  child: Column(children: <Widget>[
                    questionTypeCard(state.loadedQuestionType, context)
                  ]),
                );
              }
            ),
          ),
        floatingActionButton: FloatingActionButton(
          onPressed: () => Navigator.pushNamed(context, QuestionTypeRoutes.create),
          child: Icon(Icons.add, color: Theme.of(context).iconTheme.color,),
          backgroundColor: Theme.of(context).primaryColor,
        )
    );
  }

  Widget questionTypeCard(QuestionType questionType, BuildContext context) {
    return Card(
      child: Container(
        padding: EdgeInsets.all(20),
        width: MediaQuery.of(context).size.width * 0.90,
        child: Column(
          mainAxisAlignment: MainAxisAlignment.spaceEvenly,
          crossAxisAlignment: CrossAxisAlignment.start,
          children: <Widget>[
            Text('Id : ' + questionType.id.toString(), style: Theme.of(context).textTheme.bodyText1,),
                Text('Question Type : ' + questionType.questionType.toString(), style: Theme.of(context).textTheme.bodyText1,),
              Text('Last Updated Date : ' + (questionType?.lastUpdatedDate != null ? DateFormat.yMMMMd(S.of(context).locale).format(questionType.lastUpdatedDate) : ''), style: Theme.of(context).textTheme.bodyText1,),
          ],
        ),
      ),
    );
  }
}
