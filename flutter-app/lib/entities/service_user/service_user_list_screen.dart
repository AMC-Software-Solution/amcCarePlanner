import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:amcCarePlanner/account/login/login_repository.dart';
import 'package:amcCarePlanner/entities/service_user/bloc/service_user_bloc.dart';
import 'package:amcCarePlanner/entities/service_user/service_user_model.dart';
import 'package:amcCarePlanner/generated/l10n.dart';
import 'package:flutter/material.dart';
import 'package:amcCarePlanner/shared/widgets/drawer/bloc/drawer_bloc.dart';
import 'package:amcCarePlanner/shared/widgets/drawer/drawer_widget.dart';
import 'package:amcCarePlanner/shared/widgets/loading_indicator_widget.dart';
import 'package:amcCarePlanner/shared/models/entity_arguments.dart';
import 'service_user_route.dart';

class ServiceUserListScreen extends StatelessWidget {
    ServiceUserListScreen({Key key}) : super(key: ServiceUserRoutes.listScreenKey);
    final GlobalKey<ScaffoldState> scaffoldKey = new GlobalKey<ScaffoldState>();

  @override
  Widget build(BuildContext context) {
    return  BlocListener<ServiceUserBloc, ServiceUserState>(
      listener: (context, state) {
        if(state.deleteStatus == ServiceUserDeleteStatus.ok) {
          Navigator.of(context).pop();
          scaffoldKey.currentState.showSnackBar(new SnackBar(
              content: new Text(S.of(context).pageEntitiesServiceUserDeleteOk)
          ));
        }
      },
      child: Scaffold(
          key: scaffoldKey,
          appBar: AppBar(
            centerTitle: true,
    title:Text(S.of(context).pageEntitiesServiceUserListTitle),
          ),
          body: SingleChildScrollView(
            padding: const EdgeInsets.all(15.0),
            child: BlocBuilder<ServiceUserBloc, ServiceUserState>(
              buildWhen: (previous, current) => previous.serviceUsers != current.serviceUsers,
              builder: (context, state) {
                return Visibility(
                  visible: state.serviceUserStatusUI == ServiceUserStatusUI.done,
                  replacement: LoadingIndicator(),
                  child: Column(children: <Widget>[
                    for (ServiceUser serviceUser in state.serviceUsers) serviceUserCard(serviceUser, context)
                  ]),
                );
              }
            ),
          ),
        drawer: BlocProvider<DrawerBloc>(
            create: (context) => DrawerBloc(loginRepository: LoginRepository()),
            child: AmcCarePlannerDrawer()),
        floatingActionButton: FloatingActionButton(
          onPressed: () => Navigator.pushNamed(context, ServiceUserRoutes.create),
          child: Icon(Icons.add, color: Theme.of(context).iconTheme.color,),
          backgroundColor: Theme.of(context).primaryColor,
        )
      ),
    );
  }

  Widget serviceUserCard(ServiceUser serviceUser, BuildContext context) {
    ServiceUserBloc serviceUserBloc = BlocProvider.of<ServiceUserBloc>(context);
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
                  title: Text('Titlle : ${serviceUser.titlle.toString()}'),
                  subtitle: Text('First Name : ${serviceUser.firstName.toString()}'),
              trailing: DropdownButton(
                  icon: Icon(Icons.more_vert),
                  onChanged: (String newValue) {
                    switch (newValue) {
                      case "Edit":
                        Navigator.pushNamed(
                            context, ServiceUserRoutes.edit,
                            arguments: EntityArguments(serviceUser.id));
                        break;
                      case "Delete":
                        showDialog(
                          context: context,
                          builder: (BuildContext context) {
                            return deleteDialog(
                              serviceUserBloc, context, serviceUser.id);
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
                  context, ServiceUserRoutes.view,
                  arguments: EntityArguments(serviceUser.id)),
            ),
          ],
        ),
      ),
    );
  }

  Widget deleteDialog(ServiceUserBloc serviceUserBloc, BuildContext context, int id) {
    return BlocProvider<ServiceUserBloc>.value(
      value: serviceUserBloc,
      child: AlertDialog(
        title: new Text(S.of(context).pageEntitiesServiceUserDeletePopupTitle),
        content: new Text(S.of(context).entityActionConfirmDelete),
        actions: <Widget>[
          new FlatButton(
            child: new Text(S.of(context).entityActionConfirmDeleteYes),
            onPressed: () {
              serviceUserBloc.add(DeleteServiceUserById(id: id));
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
