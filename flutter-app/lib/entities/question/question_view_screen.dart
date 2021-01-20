import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:amcCarePlanner/entities/question/bloc/question_bloc.dart';
import 'package:amcCarePlanner/entities/question/question_model.dart';
import 'package:amcCarePlanner/generated/l10n.dart';
import 'package:flutter/material.dart';
import 'package:intl/intl.dart';
import 'package:amcCarePlanner/shared/widgets/loading_indicator_widget.dart';
import 'question_route.dart';

class QuestionViewScreen extends StatelessWidget {
  QuestionViewScreen({Key key}) : super(key: QuestionRoutes.createScreenKey);

  @override
  Widget build(BuildContext context) {
    return Scaffold(
          appBar: AppBar(
            centerTitle: true,
            title:Text(S.of(context).pageEntitiesQuestionViewTitle),
          ),
          body: SingleChildScrollView(
            padding: const EdgeInsets.all(15.0),
            child: BlocBuilder<QuestionBloc, QuestionState>(
              buildWhen: (previous, current) => previous.loadedQuestion != current.loadedQuestion,
              builder: (context, state) {
                return Visibility(
                  visible: state.questionStatusUI == QuestionStatusUI.done,
                  replacement: LoadingIndicator(),
                  child: Column(children: <Widget>[
                    questionCard(state.loadedQuestion, context)
                  ]),
                );
              }
            ),
          ),
        floatingActionButton: FloatingActionButton(
          onPressed: () => Navigator.pushNamed(context, QuestionRoutes.create),
          child: Icon(Icons.add, color: Theme.of(context).iconTheme.color,),
          backgroundColor: Theme.of(context).primaryColor,
        )
    );
  }

  Widget questionCard(Question question, BuildContext context) {
    return Card(
      child: Container(
        padding: EdgeInsets.all(20),
        width: MediaQuery.of(context).size.width * 0.90,
        child: Column(
          mainAxisAlignment: MainAxisAlignment.spaceEvenly,
          crossAxisAlignment: CrossAxisAlignment.start,
          children: <Widget>[
            Text('Id : ' + question.id.toString(), style: Theme.of(context).textTheme.bodyText1,),
                Text('Question : ' + question.question.toString(), style: Theme.of(context).textTheme.bodyText1,),
                Text('Description : ' + question.description.toString(), style: Theme.of(context).textTheme.bodyText1,),
                Text('Attribute 1 : ' + question.attribute1.toString(), style: Theme.of(context).textTheme.bodyText1,),
                Text('Attribute 2 : ' + question.attribute2.toString(), style: Theme.of(context).textTheme.bodyText1,),
                Text('Attribute 3 : ' + question.attribute3.toString(), style: Theme.of(context).textTheme.bodyText1,),
                Text('Attribute 4 : ' + question.attribute4.toString(), style: Theme.of(context).textTheme.bodyText1,),
                Text('Attribute 5 : ' + question.attribute5.toString(), style: Theme.of(context).textTheme.bodyText1,),
                Text('Optional : ' + question.optional.toString(), style: Theme.of(context).textTheme.bodyText1,),
              Text('Created Date : ' + (question?.createdDate != null ? DateFormat.yMMMMd(S.of(context).locale).format(question.createdDate) : ''), style: Theme.of(context).textTheme.bodyText1,),
              Text('Last Updated Date : ' + (question?.lastUpdatedDate != null ? DateFormat.yMMMMd(S.of(context).locale).format(question.lastUpdatedDate) : ''), style: Theme.of(context).textTheme.bodyText1,),
                Text('Client Id : ' + question.clientId.toString(), style: Theme.of(context).textTheme.bodyText1,),
                Text('Has Extra Data : ' + question.hasExtraData.toString(), style: Theme.of(context).textTheme.bodyText1,),
          ],
        ),
      ),
    );
  }
}
