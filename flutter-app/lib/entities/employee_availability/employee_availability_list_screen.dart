import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:amcCarePlanner/account/login/login_repository.dart';
import 'package:amcCarePlanner/entities/employee_availability/bloc/employee_availability_bloc.dart';
import 'package:amcCarePlanner/entities/employee_availability/employee_availability_model.dart';
import 'package:amcCarePlanner/generated/l10n.dart';
import 'package:flutter/material.dart';
import 'package:amcCarePlanner/shared/widgets/drawer/bloc/drawer_bloc.dart';
import 'package:amcCarePlanner/shared/widgets/drawer/drawer_widget.dart';
import 'package:amcCarePlanner/shared/widgets/loading_indicator_widget.dart';
import 'package:amcCarePlanner/shared/models/entity_arguments.dart';
import 'employee_availability_route.dart';

class EmployeeAvailabilityListScreen extends StatelessWidget {
    EmployeeAvailabilityListScreen({Key key}) : super(key: EmployeeAvailabilityRoutes.listScreenKey);
    final GlobalKey<ScaffoldState> scaffoldKey = new GlobalKey<ScaffoldState>();

  @override
  Widget build(BuildContext context) {
    return  BlocListener<EmployeeAvailabilityBloc, EmployeeAvailabilityState>(
      listener: (context, state) {
        if(state.deleteStatus == EmployeeAvailabilityDeleteStatus.ok) {
          Navigator.of(context).pop();
          scaffoldKey.currentState.showSnackBar(new SnackBar(
              content: new Text(S.of(context).pageEntitiesEmployeeAvailabilityDeleteOk)
          ));
        }
      },
      child: Scaffold(
          key: scaffoldKey,
          appBar: AppBar(
            centerTitle: true,
    title:Text(S.of(context).pageEntitiesEmployeeAvailabilityListTitle),
          ),
          body: SingleChildScrollView(
            padding: const EdgeInsets.all(15.0),
            child: BlocBuilder<EmployeeAvailabilityBloc, EmployeeAvailabilityState>(
              buildWhen: (previous, current) => previous.employeeAvailabilities != current.employeeAvailabilities,
              builder: (context, state) {
                return Visibility(
                  visible: state.employeeAvailabilityStatusUI == EmployeeAvailabilityStatusUI.done,
                  replacement: LoadingIndicator(),
                  child: Column(children: <Widget>[
                    for (EmployeeAvailability employeeAvailability in state.employeeAvailabilities) employeeAvailabilityCard(employeeAvailability, context)
                  ]),
                );
              }
            ),
          ),
        drawer: BlocProvider<DrawerBloc>(
            create: (context) => DrawerBloc(loginRepository: LoginRepository()),
            child: AmcCarePlannerDrawer()),
        floatingActionButton: FloatingActionButton(
          onPressed: () => Navigator.pushNamed(context, EmployeeAvailabilityRoutes.create),
          child: Icon(Icons.add, color: Theme.of(context).iconTheme.color,),
          backgroundColor: Theme.of(context).primaryColor,
        )
      ),
    );
  }

  Widget employeeAvailabilityCard(EmployeeAvailability employeeAvailability, BuildContext context) {
    EmployeeAvailabilityBloc employeeAvailabilityBloc = BlocProvider.of<EmployeeAvailabilityBloc>(context);
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
                  title: Text('Available For Work : ${employeeAvailability.availableForWork.toString()}'),
                  subtitle: Text('Available Monday : ${employeeAvailability.availableMonday.toString()}'),
              trailing: DropdownButton(
                  icon: Icon(Icons.more_vert),
                  onChanged: (String newValue) {
                    switch (newValue) {
                      case "Edit":
                        Navigator.pushNamed(
                            context, EmployeeAvailabilityRoutes.edit,
                            arguments: EntityArguments(employeeAvailability.id));
                        break;
                      case "Delete":
                        showDialog(
                          context: context,
                          builder: (BuildContext context) {
                            return deleteDialog(
                              employeeAvailabilityBloc, context, employeeAvailability.id);
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
                  context, EmployeeAvailabilityRoutes.view,
                  arguments: EntityArguments(employeeAvailability.id)),
            ),
          ],
        ),
      ),
    );
  }

  Widget deleteDialog(EmployeeAvailabilityBloc employeeAvailabilityBloc, BuildContext context, int id) {
    return BlocProvider<EmployeeAvailabilityBloc>.value(
      value: employeeAvailabilityBloc,
      child: AlertDialog(
        title: new Text(S.of(context).pageEntitiesEmployeeAvailabilityDeletePopupTitle),
        content: new Text(S.of(context).entityActionConfirmDelete),
        actions: <Widget>[
          new FlatButton(
            child: new Text(S.of(context).entityActionConfirmDeleteYes),
            onPressed: () {
              employeeAvailabilityBloc.add(DeleteEmployeeAvailabilityById(id: id));
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
