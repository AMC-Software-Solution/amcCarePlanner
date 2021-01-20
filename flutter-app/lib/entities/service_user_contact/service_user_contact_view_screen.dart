import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:amcCarePlanner/entities/service_user_contact/bloc/service_user_contact_bloc.dart';
import 'package:amcCarePlanner/entities/service_user_contact/service_user_contact_model.dart';
import 'package:amcCarePlanner/generated/l10n.dart';
import 'package:flutter/material.dart';
import 'package:intl/intl.dart';
import 'package:amcCarePlanner/shared/widgets/loading_indicator_widget.dart';
import 'service_user_contact_route.dart';

class ServiceUserContactViewScreen extends StatelessWidget {
  ServiceUserContactViewScreen({Key key}) : super(key: ServiceUserContactRoutes.createScreenKey);

  @override
  Widget build(BuildContext context) {
    return Scaffold(
          appBar: AppBar(
            centerTitle: true,
            title:Text(S.of(context).pageEntitiesServiceUserContactViewTitle),
          ),
          body: SingleChildScrollView(
            padding: const EdgeInsets.all(15.0),
            child: BlocBuilder<ServiceUserContactBloc, ServiceUserContactState>(
              buildWhen: (previous, current) => previous.loadedServiceUserContact != current.loadedServiceUserContact,
              builder: (context, state) {
                return Visibility(
                  visible: state.serviceUserContactStatusUI == ServiceUserContactStatusUI.done,
                  replacement: LoadingIndicator(),
                  child: Column(children: <Widget>[
                    serviceUserContactCard(state.loadedServiceUserContact, context)
                  ]),
                );
              }
            ),
          ),
        floatingActionButton: FloatingActionButton(
          onPressed: () => Navigator.pushNamed(context, ServiceUserContactRoutes.create),
          child: Icon(Icons.add, color: Theme.of(context).iconTheme.color,),
          backgroundColor: Theme.of(context).primaryColor,
        )
    );
  }

  Widget serviceUserContactCard(ServiceUserContact serviceUserContact, BuildContext context) {
    return Card(
      child: Container(
        padding: EdgeInsets.all(20),
        width: MediaQuery.of(context).size.width * 0.90,
        child: Column(
          mainAxisAlignment: MainAxisAlignment.spaceEvenly,
          crossAxisAlignment: CrossAxisAlignment.start,
          children: <Widget>[
            Text('Id : ' + serviceUserContact.id.toString(), style: Theme.of(context).textTheme.bodyText1,),
                Text('Address : ' + serviceUserContact.address.toString(), style: Theme.of(context).textTheme.bodyText1,),
                Text('City Or Town : ' + serviceUserContact.cityOrTown.toString(), style: Theme.of(context).textTheme.bodyText1,),
                Text('County : ' + serviceUserContact.county.toString(), style: Theme.of(context).textTheme.bodyText1,),
                Text('Post Code : ' + serviceUserContact.postCode.toString(), style: Theme.of(context).textTheme.bodyText1,),
                Text('Telephone : ' + serviceUserContact.telephone.toString(), style: Theme.of(context).textTheme.bodyText1,),
              Text('Created Date : ' + (serviceUserContact?.createdDate != null ? DateFormat.yMMMMd(S.of(context).locale).format(serviceUserContact.createdDate) : ''), style: Theme.of(context).textTheme.bodyText1,),
              Text('Last Updated Date : ' + (serviceUserContact?.lastUpdatedDate != null ? DateFormat.yMMMMd(S.of(context).locale).format(serviceUserContact.lastUpdatedDate) : ''), style: Theme.of(context).textTheme.bodyText1,),
                Text('Client Id : ' + serviceUserContact.clientId.toString(), style: Theme.of(context).textTheme.bodyText1,),
                Text('Has Extra Data : ' + serviceUserContact.hasExtraData.toString(), style: Theme.of(context).textTheme.bodyText1,),
          ],
        ),
      ),
    );
  }
}
