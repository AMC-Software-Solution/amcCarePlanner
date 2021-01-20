import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:amcCarePlanner/account/login/login_repository.dart';
import 'package:amcCarePlanner/entities/service_user_location/bloc/service_user_location_bloc.dart';
import 'package:amcCarePlanner/entities/service_user_location/service_user_location_model.dart';
import 'package:amcCarePlanner/generated/l10n.dart';
import 'package:flutter/material.dart';
import 'package:amcCarePlanner/shared/widgets/drawer/bloc/drawer_bloc.dart';
import 'package:amcCarePlanner/shared/widgets/drawer/drawer_widget.dart';
import 'package:amcCarePlanner/shared/widgets/loading_indicator_widget.dart';
import 'package:amcCarePlanner/shared/models/entity_arguments.dart';
import 'service_user_location_route.dart';

class ServiceUserLocationListScreen extends StatelessWidget {
    ServiceUserLocationListScreen({Key key}) : super(key: ServiceUserLocationRoutes.listScreenKey);
    final GlobalKey<ScaffoldState> scaffoldKey = new GlobalKey<ScaffoldState>();

  @override
  Widget build(BuildContext context) {
    return  BlocListener<ServiceUserLocationBloc, ServiceUserLocationState>(
      listener: (context, state) {
        if(state.deleteStatus == ServiceUserLocationDeleteStatus.ok) {
          Navigator.of(context).pop();
          scaffoldKey.currentState.showSnackBar(new SnackBar(
              content: new Text(S.of(context).pageEntitiesServiceUserLocationDeleteOk)
          ));
        }
      },
      child: Scaffold(
          key: scaffoldKey,
          appBar: AppBar(
            centerTitle: true,
    title:Text(S.of(context).pageEntitiesServiceUserLocationListTitle),
          ),
          body: SingleChildScrollView(
            padding: const EdgeInsets.all(15.0),
            child: BlocBuilder<ServiceUserLocationBloc, ServiceUserLocationState>(
              buildWhen: (previous, current) => previous.serviceUserLocations != current.serviceUserLocations,
              builder: (context, state) {
                return Visibility(
                  visible: state.serviceUserLocationStatusUI == ServiceUserLocationStatusUI.done,
                  replacement: LoadingIndicator(),
                  child: Column(children: <Widget>[
                    for (ServiceUserLocation serviceUserLocation in state.serviceUserLocations) serviceUserLocationCard(serviceUserLocation, context)
                  ]),
                );
              }
            ),
          ),
        drawer: BlocProvider<DrawerBloc>(
            create: (context) => DrawerBloc(loginRepository: LoginRepository()),
            child: AmcCarePlannerDrawer()),
        floatingActionButton: FloatingActionButton(
          onPressed: () => Navigator.pushNamed(context, ServiceUserLocationRoutes.create),
          child: Icon(Icons.add, color: Theme.of(context).iconTheme.color,),
          backgroundColor: Theme.of(context).primaryColor,
        )
      ),
    );
  }

  Widget serviceUserLocationCard(ServiceUserLocation serviceUserLocation, BuildContext context) {
    ServiceUserLocationBloc serviceUserLocationBloc = BlocProvider.of<ServiceUserLocationBloc>(context);
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
                  title: Text('Latitude : ${serviceUserLocation.latitude.toString()}'),
                  subtitle: Text('Longitude : ${serviceUserLocation.longitude.toString()}'),
              trailing: DropdownButton(
                  icon: Icon(Icons.more_vert),
                  onChanged: (String newValue) {
                    switch (newValue) {
                      case "Edit":
                        Navigator.pushNamed(
                            context, ServiceUserLocationRoutes.edit,
                            arguments: EntityArguments(serviceUserLocation.id));
                        break;
                      case "Delete":
                        showDialog(
                          context: context,
                          builder: (BuildContext context) {
                            return deleteDialog(
                              serviceUserLocationBloc, context, serviceUserLocation.id);
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
                  context, ServiceUserLocationRoutes.view,
                  arguments: EntityArguments(serviceUserLocation.id)),
            ),
          ],
        ),
      ),
    );
  }

  Widget deleteDialog(ServiceUserLocationBloc serviceUserLocationBloc, BuildContext context, int id) {
    return BlocProvider<ServiceUserLocationBloc>.value(
      value: serviceUserLocationBloc,
      child: AlertDialog(
        title: new Text(S.of(context).pageEntitiesServiceUserLocationDeletePopupTitle),
        content: new Text(S.of(context).entityActionConfirmDelete),
        actions: <Widget>[
          new FlatButton(
            child: new Text(S.of(context).entityActionConfirmDeleteYes),
            onPressed: () {
              serviceUserLocationBloc.add(DeleteServiceUserLocationById(id: id));
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
