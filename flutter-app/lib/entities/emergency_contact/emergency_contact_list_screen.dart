import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:amcCarePlanner/account/login/login_repository.dart';
import 'package:amcCarePlanner/entities/emergency_contact/bloc/emergency_contact_bloc.dart';
import 'package:amcCarePlanner/entities/emergency_contact/emergency_contact_model.dart';
import 'package:amcCarePlanner/generated/l10n.dart';
import 'package:flutter/material.dart';
import 'package:amcCarePlanner/shared/widgets/drawer/bloc/drawer_bloc.dart';
import 'package:amcCarePlanner/shared/widgets/drawer/drawer_widget.dart';
import 'package:amcCarePlanner/shared/widgets/loading_indicator_widget.dart';
import 'package:amcCarePlanner/shared/models/entity_arguments.dart';
import 'emergency_contact_route.dart';

class EmergencyContactListScreen extends StatelessWidget {
    EmergencyContactListScreen({Key key}) : super(key: EmergencyContactRoutes.listScreenKey);
    final GlobalKey<ScaffoldState> scaffoldKey = new GlobalKey<ScaffoldState>();

  @override
  Widget build(BuildContext context) {
    return  BlocListener<EmergencyContactBloc, EmergencyContactState>(
      listener: (context, state) {
        if(state.deleteStatus == EmergencyContactDeleteStatus.ok) {
          Navigator.of(context).pop();
          scaffoldKey.currentState.showSnackBar(new SnackBar(
              content: new Text(S.of(context).pageEntitiesEmergencyContactDeleteOk)
          ));
        }
      },
      child: Scaffold(
          key: scaffoldKey,
          appBar: AppBar(
            centerTitle: true,
    title:Text(S.of(context).pageEntitiesEmergencyContactListTitle),
          ),
          body: SingleChildScrollView(
            padding: const EdgeInsets.all(15.0),
            child: BlocBuilder<EmergencyContactBloc, EmergencyContactState>(
              buildWhen: (previous, current) => previous.emergencyContacts != current.emergencyContacts,
              builder: (context, state) {
                return Visibility(
                  visible: state.emergencyContactStatusUI == EmergencyContactStatusUI.done,
                  replacement: LoadingIndicator(),
                  child: Column(children: <Widget>[
                    for (EmergencyContact emergencyContact in state.emergencyContacts) emergencyContactCard(emergencyContact, context)
                  ]),
                );
              }
            ),
          ),
        drawer: BlocProvider<DrawerBloc>(
            create: (context) => DrawerBloc(loginRepository: LoginRepository()),
            child: AmcCarePlannerDrawer()),
        floatingActionButton: FloatingActionButton(
          onPressed: () => Navigator.pushNamed(context, EmergencyContactRoutes.create),
          child: Icon(Icons.add, color: Theme.of(context).iconTheme.color,),
          backgroundColor: Theme.of(context).primaryColor,
        )
      ),
    );
  }

  Widget emergencyContactCard(EmergencyContact emergencyContact, BuildContext context) {
    EmergencyContactBloc emergencyContactBloc = BlocProvider.of<EmergencyContactBloc>(context);
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
                  title: Text('Name : ${emergencyContact.name.toString()}'),
                  subtitle: Text('Contact Relationship : ${emergencyContact.contactRelationship.toString()}'),
              trailing: DropdownButton(
                  icon: Icon(Icons.more_vert),
                  onChanged: (String newValue) {
                    switch (newValue) {
                      case "Edit":
                        Navigator.pushNamed(
                            context, EmergencyContactRoutes.edit,
                            arguments: EntityArguments(emergencyContact.id));
                        break;
                      case "Delete":
                        showDialog(
                          context: context,
                          builder: (BuildContext context) {
                            return deleteDialog(
                              emergencyContactBloc, context, emergencyContact.id);
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
                  context, EmergencyContactRoutes.view,
                  arguments: EntityArguments(emergencyContact.id)),
            ),
          ],
        ),
      ),
    );
  }

  Widget deleteDialog(EmergencyContactBloc emergencyContactBloc, BuildContext context, int id) {
    return BlocProvider<EmergencyContactBloc>.value(
      value: emergencyContactBloc,
      child: AlertDialog(
        title: new Text(S.of(context).pageEntitiesEmergencyContactDeletePopupTitle),
        content: new Text(S.of(context).entityActionConfirmDelete),
        actions: <Widget>[
          new FlatButton(
            child: new Text(S.of(context).entityActionConfirmDeleteYes),
            onPressed: () {
              emergencyContactBloc.add(DeleteEmergencyContactById(id: id));
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
