import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:amcCarePlanner/entities/service_user_location/bloc/service_user_location_bloc.dart';
import 'package:amcCarePlanner/entities/service_user_location/service_user_location_model.dart';
import 'package:amcCarePlanner/generated/l10n.dart';
import 'package:flutter/material.dart';
import 'package:intl/intl.dart';
import 'package:amcCarePlanner/shared/widgets/loading_indicator_widget.dart';
import 'service_user_location_route.dart';

class ServiceUserLocationViewScreen extends StatelessWidget {
  ServiceUserLocationViewScreen({Key key}) : super(key: ServiceUserLocationRoutes.createScreenKey);

  @override
  Widget build(BuildContext context) {
    return Scaffold(
          appBar: AppBar(
            centerTitle: true,
            title:Text(S.of(context).pageEntitiesServiceUserLocationViewTitle),
          ),
          body: SingleChildScrollView(
            padding: const EdgeInsets.all(15.0),
            child: BlocBuilder<ServiceUserLocationBloc, ServiceUserLocationState>(
              buildWhen: (previous, current) => previous.loadedServiceUserLocation != current.loadedServiceUserLocation,
              builder: (context, state) {
                return Visibility(
                  visible: state.serviceUserLocationStatusUI == ServiceUserLocationStatusUI.done,
                  replacement: LoadingIndicator(),
                  child: Column(children: <Widget>[
                    serviceUserLocationCard(state.loadedServiceUserLocation, context)
                  ]),
                );
              }
            ),
          ),
        floatingActionButton: FloatingActionButton(
          onPressed: () => Navigator.pushNamed(context, ServiceUserLocationRoutes.create),
          child: Icon(Icons.add, color: Theme.of(context).iconTheme.color,),
          backgroundColor: Theme.of(context).primaryColor,
        )
    );
  }

  Widget serviceUserLocationCard(ServiceUserLocation serviceUserLocation, BuildContext context) {
    return Card(
      child: Container(
        padding: EdgeInsets.all(20),
        width: MediaQuery.of(context).size.width * 0.90,
        child: Column(
          mainAxisAlignment: MainAxisAlignment.spaceEvenly,
          crossAxisAlignment: CrossAxisAlignment.start,
          children: <Widget>[
            Text('Id : ' + serviceUserLocation.id.toString(), style: Theme.of(context).textTheme.bodyText1,),
                Text('Latitude : ' + serviceUserLocation.latitude.toString(), style: Theme.of(context).textTheme.bodyText1,),
                Text('Longitude : ' + serviceUserLocation.longitude.toString(), style: Theme.of(context).textTheme.bodyText1,),
              Text('Created Date : ' + (serviceUserLocation?.createdDate != null ? DateFormat.yMMMMd(S.of(context).locale).format(serviceUserLocation.createdDate) : ''), style: Theme.of(context).textTheme.bodyText1,),
              Text('Last Updated Date : ' + (serviceUserLocation?.lastUpdatedDate != null ? DateFormat.yMMMMd(S.of(context).locale).format(serviceUserLocation.lastUpdatedDate) : ''), style: Theme.of(context).textTheme.bodyText1,),
                Text('Client Id : ' + serviceUserLocation.clientId.toString(), style: Theme.of(context).textTheme.bodyText1,),
                Text('Has Extra Data : ' + serviceUserLocation.hasExtraData.toString(), style: Theme.of(context).textTheme.bodyText1,),
          ],
        ),
      ),
    );
  }
}
