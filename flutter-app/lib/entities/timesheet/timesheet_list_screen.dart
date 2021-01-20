import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:amcCarePlanner/account/login/login_repository.dart';
import 'package:amcCarePlanner/entities/timesheet/bloc/timesheet_bloc.dart';
import 'package:amcCarePlanner/entities/timesheet/timesheet_model.dart';
import 'package:amcCarePlanner/generated/l10n.dart';
import 'package:flutter/material.dart';
import 'package:amcCarePlanner/shared/widgets/drawer/bloc/drawer_bloc.dart';
import 'package:amcCarePlanner/shared/widgets/drawer/drawer_widget.dart';
import 'package:amcCarePlanner/shared/widgets/loading_indicator_widget.dart';
import 'package:amcCarePlanner/shared/models/entity_arguments.dart';
import 'timesheet_route.dart';

class TimesheetListScreen extends StatelessWidget {
    TimesheetListScreen({Key key}) : super(key: TimesheetRoutes.listScreenKey);
    final GlobalKey<ScaffoldState> scaffoldKey = new GlobalKey<ScaffoldState>();

  @override
  Widget build(BuildContext context) {
    return  BlocListener<TimesheetBloc, TimesheetState>(
      listener: (context, state) {
        if(state.deleteStatus == TimesheetDeleteStatus.ok) {
          Navigator.of(context).pop();
          scaffoldKey.currentState.showSnackBar(new SnackBar(
              content: new Text(S.of(context).pageEntitiesTimesheetDeleteOk)
          ));
        }
      },
      child: Scaffold(
          key: scaffoldKey,
          appBar: AppBar(
            centerTitle: true,
    title:Text(S.of(context).pageEntitiesTimesheetListTitle),
          ),
          body: SingleChildScrollView(
            padding: const EdgeInsets.all(15.0),
            child: BlocBuilder<TimesheetBloc, TimesheetState>(
              buildWhen: (previous, current) => previous.timesheets != current.timesheets,
              builder: (context, state) {
                return Visibility(
                  visible: state.timesheetStatusUI == TimesheetStatusUI.done,
                  replacement: LoadingIndicator(),
                  child: Column(children: <Widget>[
                    for (Timesheet timesheet in state.timesheets) timesheetCard(timesheet, context)
                  ]),
                );
              }
            ),
          ),
        drawer: BlocProvider<DrawerBloc>(
            create: (context) => DrawerBloc(loginRepository: LoginRepository()),
            child: AmcCarePlannerDrawer()),
        floatingActionButton: FloatingActionButton(
          onPressed: () => Navigator.pushNamed(context, TimesheetRoutes.create),
          child: Icon(Icons.add, color: Theme.of(context).iconTheme.color,),
          backgroundColor: Theme.of(context).primaryColor,
        )
      ),
    );
  }

  Widget timesheetCard(Timesheet timesheet, BuildContext context) {
    TimesheetBloc timesheetBloc = BlocProvider.of<TimesheetBloc>(context);
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
                  title: Text('Description : ${timesheet.description.toString()}'),
                  subtitle: Text('Timesheet Date : ${timesheet.timesheetDate.toString()}'),
              trailing: DropdownButton(
                  icon: Icon(Icons.more_vert),
                  onChanged: (String newValue) {
                    switch (newValue) {
                      case "Edit":
                        Navigator.pushNamed(
                            context, TimesheetRoutes.edit,
                            arguments: EntityArguments(timesheet.id));
                        break;
                      case "Delete":
                        showDialog(
                          context: context,
                          builder: (BuildContext context) {
                            return deleteDialog(
                              timesheetBloc, context, timesheet.id);
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
                  context, TimesheetRoutes.view,
                  arguments: EntityArguments(timesheet.id)),
            ),
          ],
        ),
      ),
    );
  }

  Widget deleteDialog(TimesheetBloc timesheetBloc, BuildContext context, int id) {
    return BlocProvider<TimesheetBloc>.value(
      value: timesheetBloc,
      child: AlertDialog(
        title: new Text(S.of(context).pageEntitiesTimesheetDeletePopupTitle),
        content: new Text(S.of(context).entityActionConfirmDelete),
        actions: <Widget>[
          new FlatButton(
            child: new Text(S.of(context).entityActionConfirmDeleteYes),
            onPressed: () {
              timesheetBloc.add(DeleteTimesheetById(id: id));
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
