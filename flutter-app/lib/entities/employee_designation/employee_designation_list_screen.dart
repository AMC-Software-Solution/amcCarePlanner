import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:amcCarePlanner/account/login/login_repository.dart';
import 'package:amcCarePlanner/entities/employee_designation/bloc/employee_designation_bloc.dart';
import 'package:amcCarePlanner/entities/employee_designation/employee_designation_model.dart';
import 'package:amcCarePlanner/generated/l10n.dart';
import 'package:flutter/material.dart';
import 'package:amcCarePlanner/shared/widgets/drawer/bloc/drawer_bloc.dart';
import 'package:amcCarePlanner/shared/widgets/drawer/drawer_widget.dart';
import 'package:amcCarePlanner/shared/widgets/loading_indicator_widget.dart';
import 'package:amcCarePlanner/shared/models/entity_arguments.dart';
import 'employee_designation_route.dart';

class EmployeeDesignationListScreen extends StatelessWidget {
    EmployeeDesignationListScreen({Key key}) : super(key: EmployeeDesignationRoutes.listScreenKey);
    final GlobalKey<ScaffoldState> scaffoldKey = new GlobalKey<ScaffoldState>();

  @override
  Widget build(BuildContext context) {
    return  BlocListener<EmployeeDesignationBloc, EmployeeDesignationState>(
      listener: (context, state) {
        if(state.deleteStatus == EmployeeDesignationDeleteStatus.ok) {
          Navigator.of(context).pop();
          scaffoldKey.currentState.showSnackBar(new SnackBar(
              content: new Text(S.of(context).pageEntitiesEmployeeDesignationDeleteOk)
          ));
        }
      },
      child: Scaffold(
          key: scaffoldKey,
          appBar: AppBar(
            centerTitle: true,
    title:Text(S.of(context).pageEntitiesEmployeeDesignationListTitle),
          ),
          body: SingleChildScrollView(
            padding: const EdgeInsets.all(15.0),
            child: BlocBuilder<EmployeeDesignationBloc, EmployeeDesignationState>(
              buildWhen: (previous, current) => previous.employeeDesignations != current.employeeDesignations,
              builder: (context, state) {
                return Visibility(
                  visible: state.employeeDesignationStatusUI == EmployeeDesignationStatusUI.done,
                  replacement: LoadingIndicator(),
                  child: Column(children: <Widget>[
                    for (EmployeeDesignation employeeDesignation in state.employeeDesignations) employeeDesignationCard(employeeDesignation, context)
                  ]),
                );
              }
            ),
          ),
        drawer: BlocProvider<DrawerBloc>(
            create: (context) => DrawerBloc(loginRepository: LoginRepository()),
            child: AmcCarePlannerDrawer()),
        floatingActionButton: FloatingActionButton(
          onPressed: () => Navigator.pushNamed(context, EmployeeDesignationRoutes.create),
          child: Icon(Icons.add, color: Theme.of(context).iconTheme.color,),
          backgroundColor: Theme.of(context).primaryColor,
        )
      ),
    );
  }

  Widget employeeDesignationCard(EmployeeDesignation employeeDesignation, BuildContext context) {
    EmployeeDesignationBloc employeeDesignationBloc = BlocProvider.of<EmployeeDesignationBloc>(context);
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
                  title: Text('Designation : ${employeeDesignation.designation.toString()}'),
                  subtitle: Text('Description : ${employeeDesignation.description.toString()}'),
              trailing: DropdownButton(
                  icon: Icon(Icons.more_vert),
                  onChanged: (String newValue) {
                    switch (newValue) {
                      case "Edit":
                        Navigator.pushNamed(
                            context, EmployeeDesignationRoutes.edit,
                            arguments: EntityArguments(employeeDesignation.id));
                        break;
                      case "Delete":
                        showDialog(
                          context: context,
                          builder: (BuildContext context) {
                            return deleteDialog(
                              employeeDesignationBloc, context, employeeDesignation.id);
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
                  context, EmployeeDesignationRoutes.view,
                  arguments: EntityArguments(employeeDesignation.id)),
            ),
          ],
        ),
      ),
    );
  }

  Widget deleteDialog(EmployeeDesignationBloc employeeDesignationBloc, BuildContext context, int id) {
    return BlocProvider<EmployeeDesignationBloc>.value(
      value: employeeDesignationBloc,
      child: AlertDialog(
        title: new Text(S.of(context).pageEntitiesEmployeeDesignationDeletePopupTitle),
        content: new Text(S.of(context).entityActionConfirmDelete),
        actions: <Widget>[
          new FlatButton(
            child: new Text(S.of(context).entityActionConfirmDeleteYes),
            onPressed: () {
              employeeDesignationBloc.add(DeleteEmployeeDesignationById(id: id));
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
