import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:amcCarePlanner/account/login/login_repository.dart';
import 'package:amcCarePlanner/entities/question_type/bloc/question_type_bloc.dart';
import 'package:amcCarePlanner/entities/question_type/question_type_model.dart';
import 'package:amcCarePlanner/generated/l10n.dart';
import 'package:flutter/material.dart';
import 'package:amcCarePlanner/shared/widgets/drawer/bloc/drawer_bloc.dart';
import 'package:amcCarePlanner/shared/widgets/drawer/drawer_widget.dart';
import 'package:amcCarePlanner/shared/widgets/loading_indicator_widget.dart';
import 'package:amcCarePlanner/shared/models/entity_arguments.dart';
import 'question_type_route.dart';

class QuestionTypeListScreen extends StatelessWidget {
    QuestionTypeListScreen({Key key}) : super(key: QuestionTypeRoutes.listScreenKey);
    final GlobalKey<ScaffoldState> scaffoldKey = new GlobalKey<ScaffoldState>();

  @override
  Widget build(BuildContext context) {
    return  BlocListener<QuestionTypeBloc, QuestionTypeState>(
      listener: (context, state) {
        if(state.deleteStatus == QuestionTypeDeleteStatus.ok) {
          Navigator.of(context).pop();
          scaffoldKey.currentState.showSnackBar(new SnackBar(
              content: new Text(S.of(context).pageEntitiesQuestionTypeDeleteOk)
          ));
        }
      },
      child: Scaffold(
          key: scaffoldKey,
          appBar: AppBar(
            centerTitle: true,
    title:Text(S.of(context).pageEntitiesQuestionTypeListTitle),
          ),
          body: SingleChildScrollView(
            padding: const EdgeInsets.all(15.0),
            child: BlocBuilder<QuestionTypeBloc, QuestionTypeState>(
              buildWhen: (previous, current) => previous.questionTypes != current.questionTypes,
              builder: (context, state) {
                return Visibility(
                  visible: state.questionTypeStatusUI == QuestionTypeStatusUI.done,
                  replacement: LoadingIndicator(),
                  child: Column(children: <Widget>[
                    for (QuestionType questionType in state.questionTypes) questionTypeCard(questionType, context)
                  ]),
                );
              }
            ),
          ),
        drawer: BlocProvider<DrawerBloc>(
            create: (context) => DrawerBloc(loginRepository: LoginRepository()),
            child: AmcCarePlannerDrawer()),
        floatingActionButton: FloatingActionButton(
          onPressed: () => Navigator.pushNamed(context, QuestionTypeRoutes.create),
          child: Icon(Icons.add, color: Theme.of(context).iconTheme.color,),
          backgroundColor: Theme.of(context).primaryColor,
        )
      ),
    );
  }

  Widget questionTypeCard(QuestionType questionType, BuildContext context) {
    QuestionTypeBloc questionTypeBloc = BlocProvider.of<QuestionTypeBloc>(context);
    return Card(
      child: Container(
        width: MediaQuery.of(context).size.width * 0.90,
        child: Column(
          children: <Widget>[
            ListTile(
              leading: Icon(
                Icons.account_circle,
                size: 60.0,
                color: Theme.of(context).primaryColor,
              ),
                  title: Text('Question Type : ${questionType.questionType.toString()}'),
                  subtitle: Text('Last Updated Date : ${questionType.lastUpdatedDate.toString()}'),
              trailing: DropdownButton(
                  icon: Icon(Icons.more_vert),
                  onChanged: (String newValue) {
                    switch (newValue) {
                      case "Edit":
                        Navigator.pushNamed(
                            context, QuestionTypeRoutes.edit,
                            arguments: EntityArguments(questionType.id));
                        break;
                      case "Delete":
                        showDialog(
                          context: context,
                          builder: (BuildContext context) {
                            return deleteDialog(
                              questionTypeBloc, context, questionType.id);
                          },
                        );
                    }
                  },
                  items: [
                    DropdownMenuItem<String>(
                      value: 'Edit',
                      child: Text('Edit'),
                    ),
                    DropdownMenuItem<String>(
                        value: 'Delete', child: Text('Delete'))
                  ]),
              selected: false,
              onTap: () => Navigator.pushNamed(
                  context, QuestionTypeRoutes.view,
                  arguments: EntityArguments(questionType.id)),
            ),
          ],
        ),
      ),
    );
  }

  Widget deleteDialog(QuestionTypeBloc questionTypeBloc, BuildContext context, int id) {
    return BlocProvider<QuestionTypeBloc>.value(
      value: questionTypeBloc,
      child: AlertDialog(
        title: new Text(S.of(context).pageEntitiesQuestionTypeDeletePopupTitle),
        content: new Text(S.of(context).entityActionConfirmDelete),
        actions: <Widget>[
          new FlatButton(
            child: new Text(S.of(context).entityActionConfirmDeleteYes),
            onPressed: () {
              questionTypeBloc.add(DeleteQuestionTypeById(id: id));
            },
          ),
          new FlatButton(
            child: new Text(S.of(context).entityActionConfirmDeleteNo),
            onPressed: () {
              Navigator.of(context).pop();
            },
          ),
        ],
      ),
    );
  }

}
