import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:amcCarePlanner/account/login/login_repository.dart';
import 'package:amcCarePlanner/entities/system_events_history/bloc/system_events_history_bloc.dart';
import 'package:amcCarePlanner/entities/system_events_history/system_events_history_model.dart';
import 'package:amcCarePlanner/generated/l10n.dart';
import 'package:flutter/material.dart';
import 'package:amcCarePlanner/shared/widgets/drawer/bloc/drawer_bloc.dart';
import 'package:amcCarePlanner/shared/widgets/drawer/drawer_widget.dart';
import 'package:amcCarePlanner/shared/widgets/loading_indicator_widget.dart';
import 'package:amcCarePlanner/shared/models/entity_arguments.dart';
import 'system_events_history_route.dart';

class SystemEventsHistoryListScreen extends StatelessWidget {
    SystemEventsHistoryListScreen({Key key}) : super(key: SystemEventsHistoryRoutes.listScreenKey);
    final GlobalKey<ScaffoldState> scaffoldKey = new GlobalKey<ScaffoldState>();

  @override
  Widget build(BuildContext context) {
    return  BlocListener<SystemEventsHistoryBloc, SystemEventsHistoryState>(
      listener: (context, state) {
        if(state.deleteStatus == SystemEventsHistoryDeleteStatus.ok) {
          Navigator.of(context).pop();
          scaffoldKey.currentState.showSnackBar(new SnackBar(
              content: new Text(S.of(context).pageEntitiesSystemEventsHistoryDeleteOk)
          ));
        }
      },
      child: Scaffold(
          key: scaffoldKey,
          appBar: AppBar(
            centerTitle: true,
    title:Text(S.of(context).pageEntitiesSystemEventsHistoryListTitle),
          ),
          body: SingleChildScrollView(
            padding: const EdgeInsets.all(15.0),
            child: BlocBuilder<SystemEventsHistoryBloc, SystemEventsHistoryState>(
              buildWhen: (previous, current) => previous.systemEventsHistories != current.systemEventsHistories,
              builder: (context, state) {
                return Visibility(
                  visible: state.systemEventsHistoryStatusUI == SystemEventsHistoryStatusUI.done,
                  replacement: LoadingIndicator(),
                  child: Column(children: <Widget>[
                    for (SystemEventsHistory systemEventsHistory in state.systemEventsHistories) systemEventsHistoryCard(systemEventsHistory, context)
                  ]),
                );
              }
            ),
          ),
        drawer: BlocProvider<DrawerBloc>(
            create: (context) => DrawerBloc(loginRepository: LoginRepository()),
            child: AmcCarePlannerDrawer()),
        floatingActionButton: FloatingActionButton(
          onPressed: () => Navigator.pushNamed(context, SystemEventsHistoryRoutes.create),
          child: Icon(Icons.add, color: Theme.of(context).iconTheme.color,),
          backgroundColor: Theme.of(context).primaryColor,
        )
      ),
    );
  }

  Widget systemEventsHistoryCard(SystemEventsHistory systemEventsHistory, BuildContext context) {
    SystemEventsHistoryBloc systemEventsHistoryBloc = BlocProvider.of<SystemEventsHistoryBloc>(context);
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
                  title: Text('Event Name : ${systemEventsHistory.eventName.toString()}'),
                  subtitle: Text('Event Date : ${systemEventsHistory.eventDate.toString()}'),
              trailing: DropdownButton(
                  icon: Icon(Icons.more_vert),
                  onChanged: (String newValue) {
                    switch (newValue) {
                      case "Edit":
                        Navigator.pushNamed(
                            context, SystemEventsHistoryRoutes.edit,
                            arguments: EntityArguments(systemEventsHistory.id));
                        break;
                      case "Delete":
                        showDialog(
                          context: context,
                          builder: (BuildContext context) {
                            return deleteDialog(
                              systemEventsHistoryBloc, context, systemEventsHistory.id);
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
                  context, SystemEventsHistoryRoutes.view,
                  arguments: EntityArguments(systemEventsHistory.id)),
            ),
          ],
        ),
      ),
    );
  }

  Widget deleteDialog(SystemEventsHistoryBloc systemEventsHistoryBloc, BuildContext context, int id) {
    return BlocProvider<SystemEventsHistoryBloc>.value(
      value: systemEventsHistoryBloc,
      child: AlertDialog(
        title: new Text(S.of(context).pageEntitiesSystemEventsHistoryDeletePopupTitle),
        content: new Text(S.of(context).entityActionConfirmDelete),
        actions: <Widget>[
          new FlatButton(
            child: new Text(S.of(context).entityActionConfirmDeleteYes),
            onPressed: () {
              systemEventsHistoryBloc.add(DeleteSystemEventsHistoryById(id: id));
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
