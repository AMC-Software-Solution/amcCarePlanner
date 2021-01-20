import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:amcCarePlanner/account/login/login_repository.dart';
import 'package:amcCarePlanner/entities/service_user_contact/bloc/service_user_contact_bloc.dart';
import 'package:amcCarePlanner/entities/service_user_contact/service_user_contact_model.dart';
import 'package:amcCarePlanner/generated/l10n.dart';
import 'package:flutter/material.dart';
import 'package:amcCarePlanner/shared/widgets/drawer/bloc/drawer_bloc.dart';
import 'package:amcCarePlanner/shared/widgets/drawer/drawer_widget.dart';
import 'package:amcCarePlanner/shared/widgets/loading_indicator_widget.dart';
import 'package:amcCarePlanner/shared/models/entity_arguments.dart';
import 'service_user_contact_route.dart';

class ServiceUserContactListScreen extends StatelessWidget {
    ServiceUserContactListScreen({Key key}) : super(key: ServiceUserContactRoutes.listScreenKey);
    final GlobalKey<ScaffoldState> scaffoldKey = new GlobalKey<ScaffoldState>();

  @override
  Widget build(BuildContext context) {
    return  BlocListener<ServiceUserContactBloc, ServiceUserContactState>(
      listener: (context, state) {
        if(state.deleteStatus == ServiceUserContactDeleteStatus.ok) {
          Navigator.of(context).pop();
          scaffoldKey.currentState.showSnackBar(new SnackBar(
              content: new Text(S.of(context).pageEntitiesServiceUserContactDeleteOk)
          ));
        }
      },
      child: Scaffold(
          key: scaffoldKey,
          appBar: AppBar(
            centerTitle: true,
    title:Text(S.of(context).pageEntitiesServiceUserContactListTitle),
          ),
          body: SingleChildScrollView(
            padding: const EdgeInsets.all(15.0),
            child: BlocBuilder<ServiceUserContactBloc, ServiceUserContactState>(
              buildWhen: (previous, current) => previous.serviceUserContacts != current.serviceUserContacts,
              builder: (context, state) {
                return Visibility(
                  visible: state.serviceUserContactStatusUI == ServiceUserContactStatusUI.done,
                  replacement: LoadingIndicator(),
                  child: Column(children: <Widget>[
                    for (ServiceUserContact serviceUserContact in state.serviceUserContacts) serviceUserContactCard(serviceUserContact, context)
                  ]),
                );
              }
            ),
          ),
        drawer: BlocProvider<DrawerBloc>(
            create: (context) => DrawerBloc(loginRepository: LoginRepository()),
            child: AmcCarePlannerDrawer()),
        floatingActionButton: FloatingActionButton(
          onPressed: () => Navigator.pushNamed(context, ServiceUserContactRoutes.create),
          child: Icon(Icons.add, color: Theme.of(context).iconTheme.color,),
          backgroundColor: Theme.of(context).primaryColor,
        )
      ),
    );
  }

  Widget serviceUserContactCard(ServiceUserContact serviceUserContact, BuildContext context) {
    ServiceUserContactBloc serviceUserContactBloc = BlocProvider.of<ServiceUserContactBloc>(context);
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
                  title: Text('Address : ${serviceUserContact.address.toString()}'),
                  subtitle: Text('City Or Town : ${serviceUserContact.cityOrTown.toString()}'),
              trailing: DropdownButton(
                  icon: Icon(Icons.more_vert),
                  onChanged: (String newValue) {
                    switch (newValue) {
                      case "Edit":
                        Navigator.pushNamed(
                            context, ServiceUserContactRoutes.edit,
                            arguments: EntityArguments(serviceUserContact.id));
                        break;
                      case "Delete":
                        showDialog(
                          context: context,
                          builder: (BuildContext context) {
                            return deleteDialog(
                              serviceUserContactBloc, context, serviceUserContact.id);
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
                  context, ServiceUserContactRoutes.view,
                  arguments: EntityArguments(serviceUserContact.id)),
            ),
          ],
        ),
      ),
    );
  }

  Widget deleteDialog(ServiceUserContactBloc serviceUserContactBloc, BuildContext context, int id) {
    return BlocProvider<ServiceUserContactBloc>.value(
      value: serviceUserContactBloc,
      child: AlertDialog(
        title: new Text(S.of(context).pageEntitiesServiceUserContactDeletePopupTitle),
        content: new Text(S.of(context).entityActionConfirmDelete),
        actions: <Widget>[
          new FlatButton(
            child: new Text(S.of(context).entityActionConfirmDeleteYes),
            onPressed: () {
              serviceUserContactBloc.add(DeleteServiceUserContactById(id: id));
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
