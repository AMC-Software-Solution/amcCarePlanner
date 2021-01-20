import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:amcCarePlanner/account/login/login_repository.dart';
import 'package:amcCarePlanner/entities/service_user_event/bloc/service_user_event_bloc.dart';
import 'package:amcCarePlanner/entities/service_user_event/service_user_event_model.dart';
import 'package:amcCarePlanner/generated/l10n.dart';
import 'package:flutter/material.dart';
import 'package:amcCarePlanner/shared/widgets/drawer/bloc/drawer_bloc.dart';
import 'package:amcCarePlanner/shared/widgets/drawer/drawer_widget.dart';
import 'package:amcCarePlanner/shared/widgets/loading_indicator_widget.dart';
import 'package:amcCarePlanner/shared/models/entity_arguments.dart';
import 'service_user_event_route.dart';

class ServiceUserEventListScreen extends StatelessWidget {
    ServiceUserEventListScreen({Key key}) : super(key: ServiceUserEventRoutes.listScreenKey);
    final GlobalKey<ScaffoldState> scaffoldKey = new GlobalKey<ScaffoldState>();

  @override
  Widget build(BuildContext context) {
    return  BlocListener<ServiceUserEventBloc, ServiceUserEventState>(
      listener: (context, state) {
        if(state.deleteStatus == ServiceUserEventDeleteStatus.ok) {
          Navigator.of(context).pop();
          scaffoldKey.currentState.showSnackBar(new SnackBar(
              content: new Text(S.of(context).pageEntitiesServiceUserEventDeleteOk)
          ));
        }
      },
      child: Scaffold(
          key: scaffoldKey,
          appBar: AppBar(
            centerTitle: true,
    title:Text(S.of(context).pageEntitiesServiceUserEventListTitle),
          ),
          body: SingleChildScrollView(
            padding: const EdgeInsets.all(15.0),
            child: BlocBuilder<ServiceUserEventBloc, ServiceUserEventState>(
              buildWhen: (previous, current) => previous.serviceUserEvents != current.serviceUserEvents,
              builder: (context, state) {
                return Visibility(
                  visible: state.serviceUserEventStatusUI == ServiceUserEventStatusUI.done,
                  replacement: LoadingIndicator(),
                  child: Column(children: <Widget>[
                    for (ServiceUserEvent serviceUserEvent in state.serviceUserEvents) serviceUserEventCard(serviceUserEvent, context)
                  ]),
                );
              }
            ),
          ),
        drawer: BlocProvider<DrawerBloc>(
            create: (context) => DrawerBloc(loginRepository: LoginRepository()),
            child: AmcCarePlannerDrawer()),
        floatingActionButton: FloatingActionButton(
          onPressed: () => Navigator.pushNamed(context, ServiceUserEventRoutes.create),
          child: Icon(Icons.add, color: Theme.of(context).iconTheme.color,),
          backgroundColor: Theme.of(context).primaryColor,
        )
      ),
    );
  }

  Widget serviceUserEventCard(ServiceUserEvent serviceUserEvent, BuildContext context) {
    ServiceUserEventBloc serviceUserEventBloc = BlocProvider.of<ServiceUserEventBloc>(context);
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
                  title: Text('Event Title : ${serviceUserEvent.eventTitle.toString()}'),
                  subtitle: Text('Description : ${serviceUserEvent.description.toString()}'),
              trailing: DropdownButton(
                  icon: Icon(Icons.more_vert),
                  onChanged: (String newValue) {
                    switch (newValue) {
                      case "Edit":
                        Navigator.pushNamed(
                            context, ServiceUserEventRoutes.edit,
                            arguments: EntityArguments(serviceUserEvent.id));
                        break;
                      case "Delete":
                        showDialog(
                          context: context,
                          builder: (BuildContext context) {
                            return deleteDialog(
                              serviceUserEventBloc, context, serviceUserEvent.id);
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
                  context, ServiceUserEventRoutes.view,
                  arguments: EntityArguments(serviceUserEvent.id)),
            ),
          ],
        ),
      ),
    );
  }

  Widget deleteDialog(ServiceUserEventBloc serviceUserEventBloc, BuildContext context, int id) {
    return BlocProvider<ServiceUserEventBloc>.value(
      value: serviceUserEventBloc,
      child: AlertDialog(
        title: new Text(S.of(context).pageEntitiesServiceUserEventDeletePopupTitle),
        content: new Text(S.of(context).entityActionConfirmDelete),
        actions: <Widget>[
          new FlatButton(
            child: new Text(S.of(context).entityActionConfirmDeleteYes),
            onPressed: () {
              serviceUserEventBloc.add(DeleteServiceUserEventById(id: id));
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
