import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:amcCarePlanner/account/login/login_repository.dart';
import 'package:amcCarePlanner/entities/employee_document/bloc/employee_document_bloc.dart';
import 'package:amcCarePlanner/entities/employee_document/employee_document_model.dart';
import 'package:amcCarePlanner/generated/l10n.dart';
import 'package:flutter/material.dart';
import 'package:amcCarePlanner/shared/widgets/drawer/bloc/drawer_bloc.dart';
import 'package:amcCarePlanner/shared/widgets/drawer/drawer_widget.dart';
import 'package:amcCarePlanner/shared/widgets/loading_indicator_widget.dart';
import 'package:amcCarePlanner/shared/models/entity_arguments.dart';
import 'employee_document_route.dart';

class EmployeeDocumentListScreen extends StatelessWidget {
    EmployeeDocumentListScreen({Key key}) : super(key: EmployeeDocumentRoutes.listScreenKey);
    final GlobalKey<ScaffoldState> scaffoldKey = new GlobalKey<ScaffoldState>();

  @override
  Widget build(BuildContext context) {
    return  BlocListener<EmployeeDocumentBloc, EmployeeDocumentState>(
      listener: (context, state) {
        if(state.deleteStatus == EmployeeDocumentDeleteStatus.ok) {
          Navigator.of(context).pop();
          scaffoldKey.currentState.showSnackBar(new SnackBar(
              content: new Text(S.of(context).pageEntitiesEmployeeDocumentDeleteOk)
          ));
        }
      },
      child: Scaffold(
          key: scaffoldKey,
          appBar: AppBar(
            centerTitle: true,
    title:Text(S.of(context).pageEntitiesEmployeeDocumentListTitle),
          ),
          body: SingleChildScrollView(
            padding: const EdgeInsets.all(15.0),
            child: BlocBuilder<EmployeeDocumentBloc, EmployeeDocumentState>(
              buildWhen: (previous, current) => previous.employeeDocuments != current.employeeDocuments,
              builder: (context, state) {
                return Visibility(
                  visible: state.employeeDocumentStatusUI == EmployeeDocumentStatusUI.done,
                  replacement: LoadingIndicator(),
                  child: Column(children: <Widget>[
                    for (EmployeeDocument employeeDocument in state.employeeDocuments) employeeDocumentCard(employeeDocument, context)
                  ]),
                );
              }
            ),
          ),
        drawer: BlocProvider<DrawerBloc>(
            create: (context) => DrawerBloc(loginRepository: LoginRepository()),
            child: AmcCarePlannerDrawer()),
        floatingActionButton: FloatingActionButton(
          onPressed: () => Navigator.pushNamed(context, EmployeeDocumentRoutes.create),
          child: Icon(Icons.add, color: Theme.of(context).iconTheme.color,),
          backgroundColor: Theme.of(context).primaryColor,
        )
      ),
    );
  }

  Widget employeeDocumentCard(EmployeeDocument employeeDocument, BuildContext context) {
    EmployeeDocumentBloc employeeDocumentBloc = BlocProvider.of<EmployeeDocumentBloc>(context);
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
                  title: Text('Document Name : ${employeeDocument.documentName.toString()}'),
                  subtitle: Text('Document Number : ${employeeDocument.documentNumber.toString()}'),
              trailing: DropdownButton(
                  icon: Icon(Icons.more_vert),
                  onChanged: (String newValue) {
                    switch (newValue) {
                      case "Edit":
                        Navigator.pushNamed(
                            context, EmployeeDocumentRoutes.edit,
                            arguments: EntityArguments(employeeDocument.id));
                        break;
                      case "Delete":
                        showDialog(
                          context: context,
                          builder: (BuildContext context) {
                            return deleteDialog(
                              employeeDocumentBloc, context, employeeDocument.id);
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
                  context, EmployeeDocumentRoutes.view,
                  arguments: EntityArguments(employeeDocument.id)),
            ),
          ],
        ),
      ),
    );
  }

  Widget deleteDialog(EmployeeDocumentBloc employeeDocumentBloc, BuildContext context, int id) {
    return BlocProvider<EmployeeDocumentBloc>.value(
      value: employeeDocumentBloc,
      child: AlertDialog(
        title: new Text(S.of(context).pageEntitiesEmployeeDocumentDeletePopupTitle),
        content: new Text(S.of(context).entityActionConfirmDelete),
        actions: <Widget>[
          new FlatButton(
            child: new Text(S.of(context).entityActionConfirmDeleteYes),
            onPressed: () {
              employeeDocumentBloc.add(DeleteEmployeeDocumentById(id: id));
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
