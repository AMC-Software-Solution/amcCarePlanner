import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:amcCarePlanner/account/login/login_repository.dart';
import 'package:amcCarePlanner/entities/terminal_device/bloc/terminal_device_bloc.dart';
import 'package:amcCarePlanner/entities/terminal_device/terminal_device_model.dart';
import 'package:amcCarePlanner/generated/l10n.dart';
import 'package:flutter/material.dart';
import 'package:amcCarePlanner/shared/widgets/drawer/bloc/drawer_bloc.dart';
import 'package:amcCarePlanner/shared/widgets/drawer/drawer_widget.dart';
import 'package:amcCarePlanner/shared/widgets/loading_indicator_widget.dart';
import 'package:amcCarePlanner/shared/models/entity_arguments.dart';
import 'terminal_device_route.dart';

class TerminalDeviceListScreen extends StatelessWidget {
    TerminalDeviceListScreen({Key key}) : super(key: TerminalDeviceRoutes.listScreenKey);
    final GlobalKey<ScaffoldState> scaffoldKey = new GlobalKey<ScaffoldState>();

  @override
  Widget build(BuildContext context) {
    return  BlocListener<TerminalDeviceBloc, TerminalDeviceState>(
      listener: (context, state) {
        if(state.deleteStatus == TerminalDeviceDeleteStatus.ok) {
          Navigator.of(context).pop();
          scaffoldKey.currentState.showSnackBar(new SnackBar(
              content: new Text(S.of(context).pageEntitiesTerminalDeviceDeleteOk)
          ));
        }
      },
      child: Scaffold(
          key: scaffoldKey,
          appBar: AppBar(
            centerTitle: true,
    title:Text(S.of(context).pageEntitiesTerminalDeviceListTitle),
          ),
          body: SingleChildScrollView(
            padding: const EdgeInsets.all(15.0),
            child: BlocBuilder<TerminalDeviceBloc, TerminalDeviceState>(
              buildWhen: (previous, current) => previous.terminalDevices != current.terminalDevices,
              builder: (context, state) {
                return Visibility(
                  visible: state.terminalDeviceStatusUI == TerminalDeviceStatusUI.done,
                  replacement: LoadingIndicator(),
                  child: Column(children: <Widget>[
                    for (TerminalDevice terminalDevice in state.terminalDevices) terminalDeviceCard(terminalDevice, context)
                  ]),
                );
              }
            ),
          ),
        drawer: BlocProvider<DrawerBloc>(
            create: (context) => DrawerBloc(loginRepository: LoginRepository()),
            child: AmcCarePlannerDrawer()),
        floatingActionButton: FloatingActionButton(
          onPressed: () => Navigator.pushNamed(context, TerminalDeviceRoutes.create),
          child: Icon(Icons.add, color: Theme.of(context).iconTheme.color,),
          backgroundColor: Theme.of(context).primaryColor,
        )
      ),
    );
  }

  Widget terminalDeviceCard(TerminalDevice terminalDevice, BuildContext context) {
    TerminalDeviceBloc terminalDeviceBloc = BlocProvider.of<TerminalDeviceBloc>(context);
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
                  title: Text('Device Name : ${terminalDevice.deviceName.toString()}'),
                  subtitle: Text('Device Model : ${terminalDevice.deviceModel.toString()}'),
              trailing: DropdownButton(
                  icon: Icon(Icons.more_vert),
                  onChanged: (String newValue) {
                    switch (newValue) {
                      case "Edit":
                        Navigator.pushNamed(
                            context, TerminalDeviceRoutes.edit,
                            arguments: EntityArguments(terminalDevice.id));
                        break;
                      case "Delete":
                        showDialog(
                          context: context,
                          builder: (BuildContext context) {
                            return deleteDialog(
                              terminalDeviceBloc, context, terminalDevice.id);
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
                  context, TerminalDeviceRoutes.view,
                  arguments: EntityArguments(terminalDevice.id)),
            ),
          ],
        ),
      ),
    );
  }

  Widget deleteDialog(TerminalDeviceBloc terminalDeviceBloc, BuildContext context, int id) {
    return BlocProvider<TerminalDeviceBloc>.value(
      value: terminalDeviceBloc,
      child: AlertDialog(
        title: new Text(S.of(context).pageEntitiesTerminalDeviceDeletePopupTitle),
        content: new Text(S.of(context).entityActionConfirmDelete),
        actions: <Widget>[
          new FlatButton(
            child: new Text(S.of(context).entityActionConfirmDeleteYes),
            onPressed: () {
              terminalDeviceBloc.add(DeleteTerminalDeviceById(id: id));
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
