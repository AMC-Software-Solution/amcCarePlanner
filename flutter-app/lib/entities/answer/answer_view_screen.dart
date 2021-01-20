import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:amcCarePlanner/entities/answer/bloc/answer_bloc.dart';
import 'package:amcCarePlanner/entities/answer/answer_model.dart';
import 'package:amcCarePlanner/generated/l10n.dart';
import 'package:flutter/material.dart';
import 'package:intl/intl.dart';
import 'package:amcCarePlanner/shared/widgets/loading_indicator_widget.dart';
import 'answer_route.dart';

class AnswerViewScreen extends StatelessWidget {
  AnswerViewScreen({Key key}) : super(key: AnswerRoutes.createScreenKey);

  @override
  Widget build(BuildContext context) {
    return Scaffold(
          appBar: AppBar(
            centerTitle: true,
            title:Text(S.of(context).pageEntitiesAnswerViewTitle),
          ),
          body: SingleChildScrollView(
            padding: const EdgeInsets.all(15.0),
            child: BlocBuilder<AnswerBloc, AnswerState>(
              buildWhen: (previous, current) => previous.loadedAnswer != current.loadedAnswer,
              builder: (context, state) {
                return Visibility(
                  visible: state.answerStatusUI == AnswerStatusUI.done,
                  replacement: LoadingIndicator(),
                  child: Column(children: <Widget>[
                    answerCard(state.loadedAnswer, context)
                  ]),
                );
              }
            ),
          ),
        floatingActionButton: FloatingActionButton(
          onPressed: () => Navigator.pushNamed(context, AnswerRoutes.create),
          child: Icon(Icons.add, color: Theme.of(context).iconTheme.color,),
          backgroundColor: Theme.of(context).primaryColor,
        )
    );
  }

  Widget answerCard(Answer answer, BuildContext context) {
    return Card(
      child: Container(
        padding: EdgeInsets.all(20),
        width: MediaQuery.of(context).size.width * 0.90,
        child: Column(
          mainAxisAlignment: MainAxisAlignment.spaceEvenly,
          crossAxisAlignment: CrossAxisAlignment.start,
          children: <Widget>[
            Text('Id : ' + answer.id.toString(), style: Theme.of(context).textTheme.bodyText1,),
                Text('Answer : ' + answer.answer.toString(), style: Theme.of(context).textTheme.bodyText1,),
                Text('Description : ' + answer.description.toString(), style: Theme.of(context).textTheme.bodyText1,),
                Text('Attribute 1 : ' + answer.attribute1.toString(), style: Theme.of(context).textTheme.bodyText1,),
                Text('Attribute 2 : ' + answer.attribute2.toString(), style: Theme.of(context).textTheme.bodyText1,),
                Text('Attribute 3 : ' + answer.attribute3.toString(), style: Theme.of(context).textTheme.bodyText1,),
                Text('Attribute 4 : ' + answer.attribute4.toString(), style: Theme.of(context).textTheme.bodyText1,),
                Text('Attribute 5 : ' + answer.attribute5.toString(), style: Theme.of(context).textTheme.bodyText1,),
              Text('Created Date : ' + (answer?.createdDate != null ? DateFormat.yMMMMd(S.of(context).locale).format(answer.createdDate) : ''), style: Theme.of(context).textTheme.bodyText1,),
              Text('Last Updated Date : ' + (answer?.lastUpdatedDate != null ? DateFormat.yMMMMd(S.of(context).locale).format(answer.lastUpdatedDate) : ''), style: Theme.of(context).textTheme.bodyText1,),
                Text('Client Id : ' + answer.clientId.toString(), style: Theme.of(context).textTheme.bodyText1,),
                Text('Has Extra Data : ' + answer.hasExtraData.toString(), style: Theme.of(context).textTheme.bodyText1,),
          ],
        ),
      ),
    );
  }
}
