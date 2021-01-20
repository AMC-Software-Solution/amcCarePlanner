import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:amcCarePlanner/entities/service_user_event/bloc/service_user_event_bloc.dart';
import 'package:amcCarePlanner/entities/service_user_event/service_user_event_model.dart';
import 'package:amcCarePlanner/generated/l10n.dart';
import 'package:flutter/material.dart';
import 'package:intl/intl.dart';
import 'package:amcCarePlanner/shared/widgets/loading_indicator_widget.dart';
import 'service_user_event_route.dart';

class ServiceUserEventViewScreen extends StatelessWidget {
  ServiceUserEventViewScreen({Key key}) : super(key: ServiceUserEventRoutes.createScreenKey);

  @override
  Widget build(BuildContext context) {
    return Scaffold(
          appBar: AppBar(
            centerTitle: true,
            title:Text(S.of(context).pageEntitiesServiceUserEventViewTitle),
          ),
          body: SingleChildScrollView(
            padding: const EdgeInsets.all(15.0),
            child: BlocBuilder<ServiceUserEventBloc, ServiceUserEventState>(
              buildWhen: (previous, current) => previous.loadedServiceUserEvent != current.loadedServiceUserEvent,
              builder: (context, state) {
                return Visibility(
                  visible: state.serviceUserEventStatusUI == ServiceUserEventStatusUI.done,
                  replacement: LoadingIndicator(),
                  child: Column(children: <Widget>[
                    serviceUserEventCard(state.loadedServiceUserEvent, context)
                  ]),
                );
              }
            ),
          ),
        floatingActionButton: FloatingActionButton(
          onPressed: () => Navigator.pushNamed(context, ServiceUserEventRoutes.create),
          child: Icon(Icons.add, color: Theme.of(context).iconTheme.color,),
          backgroundColor: Theme.of(context).primaryColor,
        )
    );
  }

  Widget serviceUserEventCard(ServiceUserEvent serviceUserEvent, BuildContext context) {
    return Card(
      child: Container(
        padding: EdgeInsets.all(20),
        width: MediaQuery.of(context).size.width * 0.90,
        child: Column(
          mainAxisAlignment: MainAxisAlignment.spaceEvenly,
          crossAxisAlignment: CrossAxisAlignment.start,
          children: <Widget>[
            Text('Id : ' + serviceUserEvent.id.toString(), style: Theme.of(context).textTheme.bodyText1,),
                Text('Event Title : ' + serviceUserEvent.eventTitle.toString(), style: Theme.of(context).textTheme.bodyText1,),
                Text('Description : ' + serviceUserEvent.description.toString(), style: Theme.of(context).textTheme.bodyText1,),
                Text('Service User Event Status : ' + serviceUserEvent.serviceUserEventStatus.toString(), style: Theme.of(context).textTheme.bodyText1,),
                Text('Service User Event Type : ' + serviceUserEvent.serviceUserEventType.toString(), style: Theme.of(context).textTheme.bodyText1,),
                Text('Priority : ' + serviceUserEvent.priority.toString(), style: Theme.of(context).textTheme.bodyText1,),
                Text('Note : ' + serviceUserEvent.note.toString(), style: Theme.of(context).textTheme.bodyText1,),
              Text('Date Of Event : ' + (serviceUserEvent?.dateOfEvent != null ? DateFormat.yMMMMd(S.of(context).locale).format(serviceUserEvent.dateOfEvent) : ''), style: Theme.of(context).textTheme.bodyText1,),
              Text('Created Date : ' + (serviceUserEvent?.createdDate != null ? DateFormat.yMMMMd(S.of(context).locale).format(serviceUserEvent.createdDate) : ''), style: Theme.of(context).textTheme.bodyText1,),
              Text('Last Updated Date : ' + (serviceUserEvent?.lastUpdatedDate != null ? DateFormat.yMMMMd(S.of(context).locale).format(serviceUserEvent.lastUpdatedDate) : ''), style: Theme.of(context).textTheme.bodyText1,),
                Text('Client Id : ' + serviceUserEvent.clientId.toString(), style: Theme.of(context).textTheme.bodyText1,),
                Text('Has Extra Data : ' + serviceUserEvent.hasExtraData.toString(), style: Theme.of(context).textTheme.bodyText1,),
          ],
        ),
      ),
    );
  }
}
