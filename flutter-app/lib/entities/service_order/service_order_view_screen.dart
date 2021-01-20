import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:amcCarePlanner/entities/service_order/bloc/service_order_bloc.dart';
import 'package:amcCarePlanner/entities/service_order/service_order_model.dart';
import 'package:amcCarePlanner/generated/l10n.dart';
import 'package:flutter/material.dart';
import 'package:intl/intl.dart';
import 'package:amcCarePlanner/shared/widgets/loading_indicator_widget.dart';
import 'service_order_route.dart';

class ServiceOrderViewScreen extends StatelessWidget {
  ServiceOrderViewScreen({Key key}) : super(key: ServiceOrderRoutes.createScreenKey);

  @override
  Widget build(BuildContext context) {
    return Scaffold(
          appBar: AppBar(
            centerTitle: true,
            title:Text(S.of(context).pageEntitiesServiceOrderViewTitle),
          ),
          body: SingleChildScrollView(
            padding: const EdgeInsets.all(15.0),
            child: BlocBuilder<ServiceOrderBloc, ServiceOrderState>(
              buildWhen: (previous, current) => previous.loadedServiceOrder != current.loadedServiceOrder,
              builder: (context, state) {
                return Visibility(
                  visible: state.serviceOrderStatusUI == ServiceOrderStatusUI.done,
                  replacement: LoadingIndicator(),
                  child: Column(children: <Widget>[
                    serviceOrderCard(state.loadedServiceOrder, context)
                  ]),
                );
              }
            ),
          ),
        floatingActionButton: FloatingActionButton(
          onPressed: () => Navigator.pushNamed(context, ServiceOrderRoutes.create),
          child: Icon(Icons.add, color: Theme.of(context).iconTheme.color,),
          backgroundColor: Theme.of(context).primaryColor,
        )
    );
  }

  Widget serviceOrderCard(ServiceOrder serviceOrder, BuildContext context) {
    return Card(
      child: Container(
        padding: EdgeInsets.all(20),
        width: MediaQuery.of(context).size.width * 0.90,
        child: Column(
          mainAxisAlignment: MainAxisAlignment.spaceEvenly,
          crossAxisAlignment: CrossAxisAlignment.start,
          children: <Widget>[
            Text('Id : ' + serviceOrder.id.toString(), style: Theme.of(context).textTheme.bodyText1,),
                Text('Title : ' + serviceOrder.title.toString(), style: Theme.of(context).textTheme.bodyText1,),
                Text('Service Description : ' + serviceOrder.serviceDescription.toString(), style: Theme.of(context).textTheme.bodyText1,),
                Text('Service Hourly Rate : ' + serviceOrder.serviceHourlyRate.toString(), style: Theme.of(context).textTheme.bodyText1,),
                Text('Client Id : ' + serviceOrder.clientId.toString(), style: Theme.of(context).textTheme.bodyText1,),
              Text('Created Date : ' + (serviceOrder?.createdDate != null ? DateFormat.yMMMMd(S.of(context).locale).format(serviceOrder.createdDate) : ''), style: Theme.of(context).textTheme.bodyText1,),
              Text('Last Updated Date : ' + (serviceOrder?.lastUpdatedDate != null ? DateFormat.yMMMMd(S.of(context).locale).format(serviceOrder.lastUpdatedDate) : ''), style: Theme.of(context).textTheme.bodyText1,),
                Text('Has Extra Data : ' + serviceOrder.hasExtraData.toString(), style: Theme.of(context).textTheme.bodyText1,),
          ],
        ),
      ),
    );
  }
}
