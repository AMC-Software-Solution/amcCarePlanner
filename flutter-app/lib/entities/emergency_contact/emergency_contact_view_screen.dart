import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:amcCarePlanner/entities/emergency_contact/bloc/emergency_contact_bloc.dart';
import 'package:amcCarePlanner/entities/emergency_contact/emergency_contact_model.dart';
import 'package:amcCarePlanner/generated/l10n.dart';
import 'package:flutter/material.dart';
import 'package:intl/intl.dart';
import 'package:amcCarePlanner/shared/widgets/loading_indicator_widget.dart';
import 'emergency_contact_route.dart';

class EmergencyContactViewScreen extends StatelessWidget {
  EmergencyContactViewScreen({Key key}) : super(key: EmergencyContactRoutes.createScreenKey);

  @override
  Widget build(BuildContext context) {
    return Scaffold(
          appBar: AppBar(
            centerTitle: true,
            title:Text(S.of(context).pageEntitiesEmergencyContactViewTitle),
          ),
          body: SingleChildScrollView(
            padding: const EdgeInsets.all(15.0),
            child: BlocBuilder<EmergencyContactBloc, EmergencyContactState>(
              buildWhen: (previous, current) => previous.loadedEmergencyContact != current.loadedEmergencyContact,
              builder: (context, state) {
                return Visibility(
                  visible: state.emergencyContactStatusUI == EmergencyContactStatusUI.done,
                  replacement: LoadingIndicator(),
                  child: Column(children: <Widget>[
                    emergencyContactCard(state.loadedEmergencyContact, context)
                  ]),
                );
              }
            ),
          ),
        floatingActionButton: FloatingActionButton(
          onPressed: () => Navigator.pushNamed(context, EmergencyContactRoutes.create),
          child: Icon(Icons.add, color: Theme.of(context).iconTheme.color,),
          backgroundColor: Theme.of(context).primaryColor,
        )
    );
  }

  Widget emergencyContactCard(EmergencyContact emergencyContact, BuildContext context) {
    return Card(
      child: Container(
        padding: EdgeInsets.all(20),
        width: MediaQuery.of(context).size.width * 0.90,
        child: Column(
          mainAxisAlignment: MainAxisAlignment.spaceEvenly,
          crossAxisAlignment: CrossAxisAlignment.start,
          children: <Widget>[
            Text('Id : ' + emergencyContact.id.toString(), style: Theme.of(context).textTheme.bodyText1,),
                Text('Name : ' + emergencyContact.name.toString(), style: Theme.of(context).textTheme.bodyText1,),
                Text('Contact Relationship : ' + emergencyContact.contactRelationship.toString(), style: Theme.of(context).textTheme.bodyText1,),
                Text('Is Key Holder : ' + emergencyContact.isKeyHolder.toString(), style: Theme.of(context).textTheme.bodyText1,),
                Text('Info Sharing Consent Given : ' + emergencyContact.infoSharingConsentGiven.toString(), style: Theme.of(context).textTheme.bodyText1,),
                Text('Preferred Contact Number : ' + emergencyContact.preferredContactNumber.toString(), style: Theme.of(context).textTheme.bodyText1,),
                Text('Full Address : ' + emergencyContact.fullAddress.toString(), style: Theme.of(context).textTheme.bodyText1,),
              Text('Created Date : ' + (emergencyContact?.createdDate != null ? DateFormat.yMMMMd(S.of(context).locale).format(emergencyContact.createdDate) : ''), style: Theme.of(context).textTheme.bodyText1,),
              Text('Last Updated Date : ' + (emergencyContact?.lastUpdatedDate != null ? DateFormat.yMMMMd(S.of(context).locale).format(emergencyContact.lastUpdatedDate) : ''), style: Theme.of(context).textTheme.bodyText1,),
                Text('Client Id : ' + emergencyContact.clientId.toString(), style: Theme.of(context).textTheme.bodyText1,),
                Text('Has Extra Data : ' + emergencyContact.hasExtraData.toString(), style: Theme.of(context).textTheme.bodyText1,),
          ],
        ),
      ),
    );
  }
}
