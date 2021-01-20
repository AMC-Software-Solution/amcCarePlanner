import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:amcCarePlanner/account/login/login_repository.dart';
import 'package:amcCarePlanner/entities/service_order/bloc/service_order_bloc.dart';
import 'package:amcCarePlanner/entities/service_order/service_order_model.dart';
import 'package:amcCarePlanner/generated/l10n.dart';
import 'package:flutter/material.dart';
import 'package:amcCarePlanner/shared/widgets/drawer/bloc/drawer_bloc.dart';
import 'package:amcCarePlanner/shared/widgets/drawer/drawer_widget.dart';
import 'package:amcCarePlanner/shared/widgets/loading_indicator_widget.dart';
import 'package:amcCarePlanner/shared/models/entity_arguments.dart';
import 'service_order_route.dart';

class ServiceOrderListScreen extends StatelessWidget {
    ServiceOrderListScreen({Key key}) : super(key: ServiceOrderRoutes.listScreenKey);
    final GlobalKey<ScaffoldState> scaffoldKey = new GlobalKey<ScaffoldState>();

  @override
  Widget build(BuildContext context) {
    return  BlocListener<ServiceOrderBloc, ServiceOrderState>(
      listener: (context, state) {
        if(state.deleteStatus == ServiceOrderDeleteStatus.ok) {
          Navigator.of(context).pop();
          scaffoldKey.currentState.showSnackBar(new SnackBar(
              content: new Text(S.of(context).pageEntitiesServiceOrderDeleteOk)
          ));
        }
      },
      child: Scaffold(
          key: scaffoldKey,
          appBar: AppBar(
            centerTitle: true,
    title:Text(S.of(context).pageEntitiesServiceOrderListTitle),
          ),
          body: SingleChildScrollView(
            padding: const EdgeInsets.all(15.0),
            child: BlocBuilder<ServiceOrderBloc, ServiceOrderState>(
              buildWhen: (previous, current) => previous.serviceOrders != current.serviceOrders,
              builder: (context, state) {
                return Visibility(
                  visible: state.serviceOrderStatusUI == ServiceOrderStatusUI.done,
                  replacement: LoadingIndicator(),
                  child: Column(children: <Widget>[
                    for (ServiceOrder serviceOrder in state.serviceOrders) serviceOrderCard(serviceOrder, context)
                  ]),
                );
              }
            ),
          ),
        drawer: BlocProvider<DrawerBloc>(
            create: (context) => DrawerBloc(loginRepository: LoginRepository()),
            child: AmcCarePlannerDrawer()),
        floatingActionButton: FloatingActionButton(
          onPressed: () => Navigator.pushNamed(context, ServiceOrderRoutes.create),
          child: Icon(Icons.add, color: Theme.of(context).iconTheme.color,),
          backgroundColor: Theme.of(context).primaryColor,
        )
      ),
    );
  }

  Widget serviceOrderCard(ServiceOrder serviceOrder, BuildContext context) {
    ServiceOrderBloc serviceOrderBloc = BlocProvider.of<ServiceOrderBloc>(context);
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
                  title: Text('Title : ${serviceOrder.title.toString()}'),
                  subtitle: Text('Service Description : ${serviceOrder.serviceDescription.toString()}'),
              trailing: DropdownButton(
                  icon: Icon(Icons.more_vert),
                  onChanged: (String newValue) {
                    switch (newValue) {
                      case "Edit":
                        Navigator.pushNamed(
                            context, ServiceOrderRoutes.edit,
                            arguments: EntityArguments(serviceOrder.id));
                        break;
                      case "Delete":
                        showDialog(
                          context: context,
                          builder: (BuildContext context) {
                            return deleteDialog(
                              serviceOrderBloc, context, serviceOrder.id);
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
                  context, ServiceOrderRoutes.view,
                  arguments: EntityArguments(serviceOrder.id)),
            ),
          ],
        ),
      ),
    );
  }

  Widget deleteDialog(ServiceOrderBloc serviceOrderBloc, BuildContext context, int id) {
    return BlocProvider<ServiceOrderBloc>.value(
      value: serviceOrderBloc,
      child: AlertDialog(
        title: new Text(S.of(context).pageEntitiesServiceOrderDeletePopupTitle),
        content: new Text(S.of(context).entityActionConfirmDelete),
        actions: <Widget>[
          new FlatButton(
            child: new Text(S.of(context).entityActionConfirmDeleteYes),
            onPressed: () {
              serviceOrderBloc.add(DeleteServiceOrderById(id: id));
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
