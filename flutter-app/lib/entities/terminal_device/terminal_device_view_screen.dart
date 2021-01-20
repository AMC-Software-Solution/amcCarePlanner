import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:amcCarePlanner/entities/terminal_device/bloc/terminal_device_bloc.dart';
import 'package:amcCarePlanner/entities/terminal_device/terminal_device_model.dart';
import 'package:amcCarePlanner/generated/l10n.dart';
import 'package:flutter/material.dart';
import 'package:intl/intl.dart';
import 'package:amcCarePlanner/shared/widgets/loading_indicator_widget.dart';
import 'terminal_device_route.dart';

class TerminalDeviceViewScreen extends StatelessWidget {
  TerminalDeviceViewScreen({Key key}) : super(key: TerminalDeviceRoutes.createScreenKey);

  @override
  Widget build(BuildContext context) {
    return Scaffold(
          appBar: AppBar(
            centerTitle: true,
            title:Text(S.of(context).pageEntitiesTerminalDeviceViewTitle),
          ),
          body: SingleChildScrollView(
            padding: const EdgeInsets.all(15.0),
            child: BlocBuilder<TerminalDeviceBloc, TerminalDeviceState>(
              buildWhen: (previous, current) => previous.loadedTerminalDevice != current.loadedTerminalDevice,
              builder: (context, state) {
                return Visibility(
                  visible: state.terminalDeviceStatusUI == TerminalDeviceStatusUI.done,
                  replacement: LoadingIndicator(),
                  child: Column(children: <Widget>[
                    terminalDeviceCard(state.loadedTerminalDevice, context)
                  ]),
                );
              }
            ),
          ),
        floatingActionButton: FloatingActionButton(
          onPressed: () => Navigator.pushNamed(context, TerminalDeviceRoutes.create),
          child: Icon(Icons.add, color: Theme.of(context).iconTheme.color,),
          backgroundColor: Theme.of(context).primaryColor,
        )
    );
  }

  Widget terminalDeviceCard(TerminalDevice terminalDevice, BuildContext context) {
    return Card(
      child: Container(
        padding: EdgeInsets.all(20),
        width: MediaQuery.of(context).size.width * 0.90,
        child: Column(
          mainAxisAlignment: MainAxisAlignment.spaceEvenly,
          crossAxisAlignment: CrossAxisAlignment.start,
          children: <Widget>[
            Text('Id : ' + terminalDevice.id.toString(), style: Theme.of(context).textTheme.bodyText1,),
                Text('Device Name : ' + terminalDevice.deviceName.toString(), style: Theme.of(context).textTheme.bodyText1,),
                Text('Device Model : ' + terminalDevice.deviceModel.toString(), style: Theme.of(context).textTheme.bodyText1,),
              Text('Registered Date : ' + (terminalDevice?.registeredDate != null ? DateFormat.yMMMMd(S.of(context).locale).format(terminalDevice.registeredDate) : ''), style: Theme.of(context).textTheme.bodyText1,),
                Text('Imei : ' + terminalDevice.imei.toString(), style: Theme.of(context).textTheme.bodyText1,),
                Text('Sim Number : ' + terminalDevice.simNumber.toString(), style: Theme.of(context).textTheme.bodyText1,),
              Text('User Started Using From : ' + (terminalDevice?.userStartedUsingFrom != null ? DateFormat.yMMMMd(S.of(context).locale).format(terminalDevice.userStartedUsingFrom) : ''), style: Theme.of(context).textTheme.bodyText1,),
              Text('Device On Location From : ' + (terminalDevice?.deviceOnLocationFrom != null ? DateFormat.yMMMMd(S.of(context).locale).format(terminalDevice.deviceOnLocationFrom) : ''), style: Theme.of(context).textTheme.bodyText1,),
                Text('Operating System : ' + terminalDevice.operatingSystem.toString(), style: Theme.of(context).textTheme.bodyText1,),
                Text('Note : ' + terminalDevice.note.toString(), style: Theme.of(context).textTheme.bodyText1,),
                Text('Owner Entity Id : ' + terminalDevice.ownerEntityId.toString(), style: Theme.of(context).textTheme.bodyText1,),
                Text('Owner Entity Name : ' + terminalDevice.ownerEntityName.toString(), style: Theme.of(context).textTheme.bodyText1,),
              Text('Created Date : ' + (terminalDevice?.createdDate != null ? DateFormat.yMMMMd(S.of(context).locale).format(terminalDevice.createdDate) : ''), style: Theme.of(context).textTheme.bodyText1,),
              Text('Last Updated Date : ' + (terminalDevice?.lastUpdatedDate != null ? DateFormat.yMMMMd(S.of(context).locale).format(terminalDevice.lastUpdatedDate) : ''), style: Theme.of(context).textTheme.bodyText1,),
                Text('Client Id : ' + terminalDevice.clientId.toString(), style: Theme.of(context).textTheme.bodyText1,),
                Text('Has Extra Data : ' + terminalDevice.hasExtraData.toString(), style: Theme.of(context).textTheme.bodyText1,),
          ],
        ),
      ),
    );
  }
}
