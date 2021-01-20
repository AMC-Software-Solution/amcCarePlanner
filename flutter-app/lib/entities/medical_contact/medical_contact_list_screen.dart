import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:amcCarePlanner/account/login/login_repository.dart';
import 'package:amcCarePlanner/entities/medical_contact/bloc/medical_contact_bloc.dart';
import 'package:amcCarePlanner/entities/medical_contact/medical_contact_model.dart';
import 'package:amcCarePlanner/generated/l10n.dart';
import 'package:flutter/material.dart';
import 'package:amcCarePlanner/shared/widgets/drawer/bloc/drawer_bloc.dart';
import 'package:amcCarePlanner/shared/widgets/drawer/drawer_widget.dart';
import 'package:amcCarePlanner/shared/widgets/loading_indicator_widget.dart';
import 'package:amcCarePlanner/shared/models/entity_arguments.dart';
import 'medical_contact_route.dart';

class MedicalContactListScreen extends StatelessWidget {
    MedicalContactListScreen({Key key}) : super(key: MedicalContactRoutes.listScreenKey);
    final GlobalKey<ScaffoldState> scaffoldKey = new GlobalKey<ScaffoldState>();

  @override
  Widget build(BuildContext context) {
    return  BlocListener<MedicalContactBloc, MedicalContactState>(
      listener: (context, state) {
        if(state.deleteStatus == MedicalContactDeleteStatus.ok) {
          Navigator.of(context).pop();
          scaffoldKey.currentState.showSnackBar(new SnackBar(
              content: new Text(S.of(context).pageEntitiesMedicalContactDeleteOk)
          ));
        }
      },
      child: Scaffold(
          key: scaffoldKey,
          appBar: AppBar(
            centerTitle: true,
    title:Text(S.of(context).pageEntitiesMedicalContactListTitle),
          ),
          body: SingleChildScrollView(
            padding: const EdgeInsets.all(15.0),
            child: BlocBuilder<MedicalContactBloc, MedicalContactState>(
              buildWhen: (previous, current) => previous.medicalContacts != current.medicalContacts,
              builder: (context, state) {
                return Visibility(
                  visible: state.medicalContactStatusUI == MedicalContactStatusUI.done,
                  replacement: LoadingIndicator(),
                  child: Column(children: <Widget>[
                    for (MedicalContact medicalContact in state.medicalContacts) medicalContactCard(medicalContact, context)
                  ]),
                );
              }
            ),
          ),
        drawer: BlocProvider<DrawerBloc>(
            create: (context) => DrawerBloc(loginRepository: LoginRepository()),
            child: AmcCarePlannerDrawer()),
        floatingActionButton: FloatingActionButton(
          onPressed: () => Navigator.pushNamed(context, MedicalContactRoutes.create),
          child: Icon(Icons.add, color: Theme.of(context).iconTheme.color,),
          backgroundColor: Theme.of(context).primaryColor,
        )
      ),
    );
  }

  Widget medicalContactCard(MedicalContact medicalContact, BuildContext context) {
    MedicalContactBloc medicalContactBloc = BlocProvider.of<MedicalContactBloc>(context);
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
                  title: Text('Doctor Name : ${medicalContact.doctorName.toString()}'),
                  subtitle: Text('Doctor Surgery : ${medicalContact.doctorSurgery.toString()}'),
              trailing: DropdownButton(
                  icon: Icon(Icons.more_vert),
                  onChanged: (String newValue) {
                    switch (newValue) {
                      case "Edit":
                        Navigator.pushNamed(
                            context, MedicalContactRoutes.edit,
                            arguments: EntityArguments(medicalContact.id));
                        break;
                      case "Delete":
                        showDialog(
                          context: context,
                          builder: (BuildContext context) {
                            return deleteDialog(
                              medicalContactBloc, context, medicalContact.id);
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
                  context, MedicalContactRoutes.view,
                  arguments: EntityArguments(medicalContact.id)),
            ),
          ],
        ),
      ),
    );
  }

  Widget deleteDialog(MedicalContactBloc medicalContactBloc, BuildContext context, int id) {
    return BlocProvider<MedicalContactBloc>.value(
      value: medicalContactBloc,
      child: AlertDialog(
        title: new Text(S.of(context).pageEntitiesMedicalContactDeletePopupTitle),
        content: new Text(S.of(context).entityActionConfirmDelete),
        actions: <Widget>[
          new FlatButton(
            child: new Text(S.of(context).entityActionConfirmDeleteYes),
            onPressed: () {
              medicalContactBloc.add(DeleteMedicalContactById(id: id));
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
