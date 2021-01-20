import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:amcCarePlanner/account/login/login_repository.dart';
import 'package:amcCarePlanner/entities/employee_location/bloc/employee_location_bloc.dart';
import 'package:amcCarePlanner/entities/employee_location/employee_location_model.dart';
import 'package:amcCarePlanner/generated/l10n.dart';
import 'package:flutter/material.dart';
import 'package:amcCarePlanner/shared/widgets/drawer/bloc/drawer_bloc.dart';
import 'package:amcCarePlanner/shared/widgets/drawer/drawer_widget.dart';
import 'package:amcCarePlanner/shared/widgets/loading_indicator_widget.dart';
import 'package:amcCarePlanner/shared/models/entity_arguments.dart';
import 'employee_location_route.dart';

class EmployeeLocationListScreen extends StatelessWidget {
    EmployeeLocationListScreen({Key key}) : super(key: EmployeeLocationRoutes.listScreenKey);
    final GlobalKey<ScaffoldState> scaffoldKey = new GlobalKey<ScaffoldState>();

  @override
  Widget build(BuildContext context) {
    return  BlocListener<EmployeeLocationBloc, EmployeeLocationState>(
      listener: (context, state) {
        if(state.deleteStatus == EmployeeLocationDeleteStatus.ok) {
          Navigator.of(context).pop();
          scaffoldKey.currentState.showSnackBar(new SnackBar(
              content: new Text(S.of(context).pageEntitiesEmployeeLocationDeleteOk)
          ));
        }
      },
      child: Scaffold(
          key: scaffoldKey,
          appBar: AppBar(
            centerTitle: true,
    title:Text(S.of(context).pageEntitiesEmployeeLocationListTitle),
          ),
          body: SingleChildScrollView(
            padding: const EdgeInsets.all(15.0),
            child: BlocBuilder<EmployeeLocationBloc, EmployeeLocationState>(
              buildWhen: (previous, current) => previous.employeeLocations != current.employeeLocations,
              builder: (context, state) {
                return Visibility(
                  visible: state.employeeLocationStatusUI == EmployeeLocationStatusUI.done,
                  replacement: LoadingIndicator(),
                  child: Column(children: <Widget>[
                    for (EmployeeLocation employeeLocation in state.employeeLocations) employeeLocationCard(employeeLocation, context)
                  ]),
                );
              }
            ),
          ),
        drawer: BlocProvider<DrawerBloc>(
            create: (context) => DrawerBloc(loginRepository: LoginRepository()),
            child: AmcCarePlannerDrawer()),
        floatingActionButton: FloatingActionButton(
          onPressed: () => Navigator.pushNamed(context, EmployeeLocationRoutes.create),
          child: Icon(Icons.add, color: Theme.of(context).iconTheme.color,),
          backgroundColor: Theme.of(context).primaryColor,
        )
      ),
    );
  }

  Widget employeeLocationCard(EmployeeLocation employeeLocation, BuildContext context) {
    EmployeeLocationBloc employeeLocationBloc = BlocProvider.of<EmployeeLocationBloc>(context);
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
                  title: Text('Latitude : ${employeeLocation.latitude.toString()}'),
                  subtitle: Text('Longitude : ${employeeLocation.longitude.toString()}'),
              trailing: DropdownButton(
                  icon: Icon(Icons.more_vert),
                  onChanged: (String newValue) {
                    switch (newValue) {
                      case "Edit":
                        Navigator.pushNamed(
                            context, EmployeeLocationRoutes.edit,
                            arguments: EntityArguments(employeeLocation.id));
                        break;
                      case "Delete":
                        showDialog(
                          context: context,
                          builder: (BuildContext context) {
                            return deleteDialog(
                              employeeLocationBloc, context, employeeLocation.id);
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
                  context, EmployeeLocationRoutes.view,
                  arguments: EntityArguments(employeeLocation.id)),
            ),
          ],
        ),
      ),
    );
  }

  Widget deleteDialog(EmployeeLocationBloc employeeLocationBloc, BuildContext context, int id) {
    return BlocProvider<EmployeeLocationBloc>.value(
      value: employeeLocationBloc,
      child: AlertDialog(
        title: new Text(S.of(context).pageEntitiesEmployeeLocationDeletePopupTitle),
        content: new Text(S.of(context).entityActionConfirmDelete),
        actions: <Widget>[
          new FlatButton(
            child: new Text(S.of(context).entityActionConfirmDeleteYes),
            onPressed: () {
              employeeLocationBloc.add(DeleteEmployeeLocationById(id: id));
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
