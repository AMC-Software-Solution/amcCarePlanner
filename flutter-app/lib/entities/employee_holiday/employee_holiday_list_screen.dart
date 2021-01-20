import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:amcCarePlanner/account/login/login_repository.dart';
import 'package:amcCarePlanner/entities/employee_holiday/bloc/employee_holiday_bloc.dart';
import 'package:amcCarePlanner/entities/employee_holiday/employee_holiday_model.dart';
import 'package:amcCarePlanner/generated/l10n.dart';
import 'package:flutter/material.dart';
import 'package:amcCarePlanner/shared/widgets/drawer/bloc/drawer_bloc.dart';
import 'package:amcCarePlanner/shared/widgets/drawer/drawer_widget.dart';
import 'package:amcCarePlanner/shared/widgets/loading_indicator_widget.dart';
import 'package:amcCarePlanner/shared/models/entity_arguments.dart';
import 'employee_holiday_route.dart';

class EmployeeHolidayListScreen extends StatelessWidget {
    EmployeeHolidayListScreen({Key key}) : super(key: EmployeeHolidayRoutes.listScreenKey);
    final GlobalKey<ScaffoldState> scaffoldKey = new GlobalKey<ScaffoldState>();

  @override
  Widget build(BuildContext context) {
    return  BlocListener<EmployeeHolidayBloc, EmployeeHolidayState>(
      listener: (context, state) {
        if(state.deleteStatus == EmployeeHolidayDeleteStatus.ok) {
          Navigator.of(context).pop();
          scaffoldKey.currentState.showSnackBar(new SnackBar(
              content: new Text(S.of(context).pageEntitiesEmployeeHolidayDeleteOk)
          ));
        }
      },
      child: Scaffold(
          key: scaffoldKey,
          appBar: AppBar(
            centerTitle: true,
    title:Text(S.of(context).pageEntitiesEmployeeHolidayListTitle),
          ),
          body: SingleChildScrollView(
            padding: const EdgeInsets.all(15.0),
            child: BlocBuilder<EmployeeHolidayBloc, EmployeeHolidayState>(
              buildWhen: (previous, current) => previous.employeeHolidays != current.employeeHolidays,
              builder: (context, state) {
                return Visibility(
                  visible: state.employeeHolidayStatusUI == EmployeeHolidayStatusUI.done,
                  replacement: LoadingIndicator(),
                  child: Column(children: <Widget>[
                    for (EmployeeHoliday employeeHoliday in state.employeeHolidays) employeeHolidayCard(employeeHoliday, context)
                  ]),
                );
              }
            ),
          ),
        drawer: BlocProvider<DrawerBloc>(
            create: (context) => DrawerBloc(loginRepository: LoginRepository()),
            child: AmcCarePlannerDrawer()),
        floatingActionButton: FloatingActionButton(
          onPressed: () => Navigator.pushNamed(context, EmployeeHolidayRoutes.create),
          child: Icon(Icons.add, color: Theme.of(context).iconTheme.color,),
          backgroundColor: Theme.of(context).primaryColor,
        )
      ),
    );
  }

  Widget employeeHolidayCard(EmployeeHoliday employeeHoliday, BuildContext context) {
    EmployeeHolidayBloc employeeHolidayBloc = BlocProvider.of<EmployeeHolidayBloc>(context);
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
                  title: Text('Description : ${employeeHoliday.description.toString()}'),
                  subtitle: Text('Start Date : ${employeeHoliday.startDate.toString()}'),
              trailing: DropdownButton(
                  icon: Icon(Icons.more_vert),
                  onChanged: (String newValue) {
                    switch (newValue) {
                      case "Edit":
                        Navigator.pushNamed(
                            context, EmployeeHolidayRoutes.edit,
                            arguments: EntityArguments(employeeHoliday.id));
                        break;
                      case "Delete":
                        showDialog(
                          context: context,
                          builder: (BuildContext context) {
                            return deleteDialog(
                              employeeHolidayBloc, context, employeeHoliday.id);
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
                  context, EmployeeHolidayRoutes.view,
                  arguments: EntityArguments(employeeHoliday.id)),
            ),
          ],
        ),
      ),
    );
  }

  Widget deleteDialog(EmployeeHolidayBloc employeeHolidayBloc, BuildContext context, int id) {
    return BlocProvider<EmployeeHolidayBloc>.value(
      value: employeeHolidayBloc,
      child: AlertDialog(
        title: new Text(S.of(context).pageEntitiesEmployeeHolidayDeletePopupTitle),
        content: new Text(S.of(context).entityActionConfirmDelete),
        actions: <Widget>[
          new FlatButton(
            child: new Text(S.of(context).entityActionConfirmDeleteYes),
            onPressed: () {
              employeeHolidayBloc.add(DeleteEmployeeHolidayById(id: id));
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
