import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:amcCarePlanner/account/login/login_repository.dart';
import 'package:amcCarePlanner/entities/answer/bloc/answer_bloc.dart';
import 'package:amcCarePlanner/entities/answer/answer_model.dart';
import 'package:amcCarePlanner/generated/l10n.dart';
import 'package:flutter/material.dart';
import 'package:amcCarePlanner/shared/widgets/drawer/bloc/drawer_bloc.dart';
import 'package:amcCarePlanner/shared/widgets/drawer/drawer_widget.dart';
import 'package:amcCarePlanner/shared/widgets/loading_indicator_widget.dart';
import 'package:amcCarePlanner/shared/models/entity_arguments.dart';
import 'answer_route.dart';

class AnswerListScreen extends StatelessWidget {
    AnswerListScreen({Key key}) : super(key: AnswerRoutes.listScreenKey);
    final GlobalKey<ScaffoldState> scaffoldKey = new GlobalKey<ScaffoldState>();

  @override
  Widget build(BuildContext context) {
    return  BlocListener<AnswerBloc, AnswerState>(
      listener: (context, state) {
        if(state.deleteStatus == AnswerDeleteStatus.ok) {
          Navigator.of(context).pop();
          scaffoldKey.currentState.showSnackBar(new SnackBar(
              content: new Text(S.of(context).pageEntitiesAnswerDeleteOk)
          ));
        }
      },
      child: Scaffold(
          key: scaffoldKey,
          appBar: AppBar(
            centerTitle: true,
    title:Text(S.of(context).pageEntitiesAnswerListTitle),
          ),
          body: SingleChildScrollView(
            padding: const EdgeInsets.all(15.0),
            child: BlocBuilder<AnswerBloc, AnswerState>(
              buildWhen: (previous, current) => previous.answers != current.answers,
              builder: (context, state) {
                return Visibility(
                  visible: state.answerStatusUI == AnswerStatusUI.done,
                  replacement: LoadingIndicator(),
                  child: Column(children: <Widget>[
                    for (Answer answer in state.answers) answerCard(answer, context)
                  ]),
                );
              }
            ),
          ),
        drawer: BlocProvider<DrawerBloc>(
            create: (context) => DrawerBloc(loginRepository: LoginRepository()),
            child: AmcCarePlannerDrawer()),
        floatingActionButton: FloatingActionButton(
          onPressed: () => Navigator.pushNamed(context, AnswerRoutes.create),
          child: Icon(Icons.add, color: Theme.of(context).iconTheme.color,),
          backgroundColor: Theme.of(context).primaryColor,
        )
      ),
    );
  }

  Widget answerCard(Answer answer, BuildContext context) {
    AnswerBloc answerBloc = BlocProvider.of<AnswerBloc>(context);
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
                  title: Text('Answer : ${answer.answer.toString()}'),
                  subtitle: Text('Description : ${answer.description.toString()}'),
              trailing: DropdownButton(
                  icon: Icon(Icons.more_vert),
                  onChanged: (String newValue) {
                    switch (newValue) {
                      case "Edit":
                        Navigator.pushNamed(
                            context, AnswerRoutes.edit,
                            arguments: EntityArguments(answer.id));
                        break;
                      case "Delete":
                        showDialog(
                          context: context,
                          builder: (BuildContext context) {
                            return deleteDialog(
                              answerBloc, context, answer.id);
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
                  context, AnswerRoutes.view,
                  arguments: EntityArguments(answer.id)),
            ),
          ],
        ),
      ),
    );
  }

  Widget deleteDialog(AnswerBloc answerBloc, BuildContext context, int id) {
    return BlocProvider<AnswerBloc>.value(
      value: answerBloc,
      child: AlertDialog(
        title: new Text(S.of(context).pageEntitiesAnswerDeletePopupTitle),
        content: new Text(S.of(context).entityActionConfirmDelete),
        actions: <Widget>[
          new FlatButton(
            child: new Text(S.of(context).entityActionConfirmDeleteYes),
            onPressed: () {
              answerBloc.add(DeleteAnswerById(id: id));
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
