import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:amcCarePlanner/entities/travel/bloc/travel_bloc.dart';
import 'package:amcCarePlanner/entities/travel/travel_model.dart';
import 'package:amcCarePlanner/generated/l10n.dart';
import 'package:flutter/material.dart';
import 'package:intl/intl.dart';
import 'package:amcCarePlanner/shared/widgets/loading_indicator_widget.dart';
import 'travel_route.dart';

class TravelViewScreen extends StatelessWidget {
  TravelViewScreen({Key key}) : super(key: TravelRoutes.createScreenKey);

  @override
  Widget build(BuildContext context) {
    return Scaffold(
          appBar: AppBar(
            centerTitle: true,
            title:Text(S.of(context).pageEntitiesTravelViewTitle),
          ),
          body: SingleChildScrollView(
            padding: const EdgeInsets.all(15.0),
            child: BlocBuilder<TravelBloc, TravelState>(
              buildWhen: (previous, current) => previous.loadedTravel != current.loadedTravel,
              builder: (context, state) {
                return Visibility(
                  visible: state.travelStatusUI == TravelStatusUI.done,
                  replacement: LoadingIndicator(),
                  child: Column(children: <Widget>[
                    travelCard(state.loadedTravel, context)
                  ]),
                );
              }
            ),
          ),
        floatingActionButton: FloatingActionButton(
          onPressed: () => Navigator.pushNamed(context, TravelRoutes.create),
          child: Icon(Icons.add, color: Theme.of(context).iconTheme.color,),
          backgroundColor: Theme.of(context).primaryColor,
        )
    );
  }

  Widget travelCard(Travel travel, BuildContext context) {
    return Card(
      child: Container(
        padding: EdgeInsets.all(20),
        width: MediaQuery.of(context).size.width * 0.90,
        child: Column(
          mainAxisAlignment: MainAxisAlignment.spaceEvenly,
          crossAxisAlignment: CrossAxisAlignment.start,
          children: <Widget>[
            Text('Id : ' + travel.id.toString(), style: Theme.of(context).textTheme.bodyText1,),
                Text('Travel Mode : ' + travel.travelMode.toString(), style: Theme.of(context).textTheme.bodyText1,),
                Text('Distance To Destination : ' + travel.distanceToDestination.toString(), style: Theme.of(context).textTheme.bodyText1,),
                Text('Time To Destination : ' + travel.timeToDestination.toString(), style: Theme.of(context).textTheme.bodyText1,),
                Text('Actual Distance Required : ' + travel.actualDistanceRequired.toString(), style: Theme.of(context).textTheme.bodyText1,),
                Text('Actual Time Required : ' + travel.actualTimeRequired.toString(), style: Theme.of(context).textTheme.bodyText1,),
                Text('Travel Status : ' + travel.travelStatus.toString(), style: Theme.of(context).textTheme.bodyText1,),
              Text('Created Date : ' + (travel?.createdDate != null ? DateFormat.yMMMMd(S.of(context).locale).format(travel.createdDate) : ''), style: Theme.of(context).textTheme.bodyText1,),
              Text('Last Updated Date : ' + (travel?.lastUpdatedDate != null ? DateFormat.yMMMMd(S.of(context).locale).format(travel.lastUpdatedDate) : ''), style: Theme.of(context).textTheme.bodyText1,),
                Text('Client Id : ' + travel.clientId.toString(), style: Theme.of(context).textTheme.bodyText1,),
                Text('Has Extra Data : ' + travel.hasExtraData.toString(), style: Theme.of(context).textTheme.bodyText1,),
          ],
        ),
      ),
    );
  }
}
