import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:amcCarePlanner/entities/system_events_history/bloc/system_events_history_bloc.dart';
import 'package:amcCarePlanner/entities/system_events_history/system_events_history_model.dart';
import 'package:amcCarePlanner/generated/l10n.dart';
import 'package:flutter/material.dart';
import 'package:intl/intl.dart';
import 'package:amcCarePlanner/shared/widgets/loading_indicator_widget.dart';
import 'system_events_history_route.dart';

class SystemEventsHistoryViewScreen extends StatelessWidget {
  SystemEventsHistoryViewScreen({Key key}) : super(key: SystemEventsHistoryRoutes.createScreenKey);

  @override
  Widget build(BuildContext context) {
    return Scaffold(
          appBar: AppBar(
            centerTitle: true,
            title:Text(S.of(context).pageEntitiesSystemEventsHistoryViewTitle),
          ),
          body: SingleChildScrollView(
            padding: const EdgeInsets.all(15.0),
            child: BlocBuilder<SystemEventsHistoryBloc, SystemEventsHistoryState>(
              buildWhen: (previous, current) => previous.loadedSystemEventsHistory != current.loadedSystemEventsHistory,
              builder: (context, state) {
                return Visibility(
                  visible: state.systemEventsHistoryStatusUI == SystemEventsHistoryStatusUI.done,
                  replacement: LoadingIndicator(),
                  child: Column(children: <Widget>[
                    systemEventsHistoryCard(state.loadedSystemEventsHistory, context)
                  ]),
                );
              }
            ),
          ),
        floatingActionButton: FloatingActionButton(
          onPressed: () => Navigator.pushNamed(context, SystemEventsHistoryRoutes.create),
          child: Icon(Icons.add, color: Theme.of(context).iconTheme.color,),
          backgroundColor: Theme.of(context).primaryColor,
        )
    );
  }

  Widget systemEventsHistoryCard(SystemEventsHistory systemEventsHistory, BuildContext context) {
    return Card(
      child: Container(
        padding: EdgeInsets.all(20),
        width: MediaQuery.of(context).size.width * 0.90,
        child: Column(
          mainAxisAlignment: MainAxisAlignment.spaceEvenly,
          crossAxisAlignment: CrossAxisAlignment.start,
          children: <Widget>[
            Text('Id : ' + systemEventsHistory.id.toString(), style: Theme.of(context).textTheme.bodyText1,),
                Text('Event Name : ' + systemEventsHistory.eventName.toString(), style: Theme.of(context).textTheme.bodyText1,),
              Text('Event Date : ' + (systemEventsHistory?.eventDate != null ? DateFormat.yMMMMd(S.of(context).locale).format(systemEventsHistory.eventDate) : ''), style: Theme.of(context).textTheme.bodyText1,),
                Text('Event Api : ' + systemEventsHistory.eventApi.toString(), style: Theme.of(context).textTheme.bodyText1,),
                Text('Ip Address : ' + systemEventsHistory.ipAddress.toString(), style: Theme.of(context).textTheme.bodyText1,),
                Text('Event Note : ' + systemEventsHistory.eventNote.toString(), style: Theme.of(context).textTheme.bodyText1,),
                Text('Event Entity Name : ' + systemEventsHistory.eventEntityName.toString(), style: Theme.of(context).textTheme.bodyText1,),
                Text('Event Entity Id : ' + systemEventsHistory.eventEntityId.toString(), style: Theme.of(context).textTheme.bodyText1,),
                Text('Is Suspecious : ' + systemEventsHistory.isSuspecious.toString(), style: Theme.of(context).textTheme.bodyText1,),
                Text('Caller Email : ' + systemEventsHistory.callerEmail.toString(), style: Theme.of(context).textTheme.bodyText1,),
                Text('Caller Id : ' + systemEventsHistory.callerId.toString(), style: Theme.of(context).textTheme.bodyText1,),
                Text('Client Id : ' + systemEventsHistory.clientId.toString(), style: Theme.of(context).textTheme.bodyText1,),
          ],
        ),
      ),
    );
  }
}
