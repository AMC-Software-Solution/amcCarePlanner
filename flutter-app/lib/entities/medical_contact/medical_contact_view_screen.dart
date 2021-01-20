import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:amcCarePlanner/entities/medical_contact/bloc/medical_contact_bloc.dart';
import 'package:amcCarePlanner/entities/medical_contact/medical_contact_model.dart';
import 'package:amcCarePlanner/generated/l10n.dart';
import 'package:flutter/material.dart';
import 'package:intl/intl.dart';
import 'package:amcCarePlanner/shared/widgets/loading_indicator_widget.dart';
import 'medical_contact_route.dart';

class MedicalContactViewScreen extends StatelessWidget {
  MedicalContactViewScreen({Key key}) : super(key: MedicalContactRoutes.createScreenKey);

  @override
  Widget build(BuildContext context) {
    return Scaffold(
          appBar: AppBar(
            centerTitle: true,
            title:Text(S.of(context).pageEntitiesMedicalContactViewTitle),
          ),
          body: SingleChildScrollView(
            padding: const EdgeInsets.all(15.0),
            child: BlocBuilder<MedicalContactBloc, MedicalContactState>(
              buildWhen: (previous, current) => previous.loadedMedicalContact != current.loadedMedicalContact,
              builder: (context, state) {
                return Visibility(
                  visible: state.medicalContactStatusUI == MedicalContactStatusUI.done,
                  replacement: LoadingIndicator(),
                  child: Column(children: <Widget>[
                    medicalContactCard(state.loadedMedicalContact, context)
                  ]),
                );
              }
            ),
          ),
        floatingActionButton: FloatingActionButton(
          onPressed: () => Navigator.pushNamed(context, MedicalContactRoutes.create),
          child: Icon(Icons.add, color: Theme.of(context).iconTheme.color,),
          backgroundColor: Theme.of(context).primaryColor,
        )
    );
  }

  Widget medicalContactCard(MedicalContact medicalContact, BuildContext context) {
    return Card(
      child: Container(
        padding: EdgeInsets.all(20),
        width: MediaQuery.of(context).size.width * 0.90,
        child: Column(
          mainAxisAlignment: MainAxisAlignment.spaceEvenly,
          crossAxisAlignment: CrossAxisAlignment.start,
          children: <Widget>[
            Text('Id : ' + medicalContact.id.toString(), style: Theme.of(context).textTheme.bodyText1,),
                Text('Doctor Name : ' + medicalContact.doctorName.toString(), style: Theme.of(context).textTheme.bodyText1,),
                Text('Doctor Surgery : ' + medicalContact.doctorSurgery.toString(), style: Theme.of(context).textTheme.bodyText1,),
                Text('Doctor Address : ' + medicalContact.doctorAddress.toString(), style: Theme.of(context).textTheme.bodyText1,),
                Text('Doctor Phone : ' + medicalContact.doctorPhone.toString(), style: Theme.of(context).textTheme.bodyText1,),
              Text('Last Visited Doctor : ' + (medicalContact?.lastVisitedDoctor != null ? DateFormat.yMMMMd(S.of(context).locale).format(medicalContact.lastVisitedDoctor) : ''), style: Theme.of(context).textTheme.bodyText1,),
                Text('District Nurse Name : ' + medicalContact.districtNurseName.toString(), style: Theme.of(context).textTheme.bodyText1,),
                Text('District Nurse Phone : ' + medicalContact.districtNursePhone.toString(), style: Theme.of(context).textTheme.bodyText1,),
                Text('Care Manager Name : ' + medicalContact.careManagerName.toString(), style: Theme.of(context).textTheme.bodyText1,),
                Text('Care Manager Phone : ' + medicalContact.careManagerPhone.toString(), style: Theme.of(context).textTheme.bodyText1,),
              Text('Created Date : ' + (medicalContact?.createdDate != null ? DateFormat.yMMMMd(S.of(context).locale).format(medicalContact.createdDate) : ''), style: Theme.of(context).textTheme.bodyText1,),
              Text('Last Updated Date : ' + (medicalContact?.lastUpdatedDate != null ? DateFormat.yMMMMd(S.of(context).locale).format(medicalContact.lastUpdatedDate) : ''), style: Theme.of(context).textTheme.bodyText1,),
                Text('Client Id : ' + medicalContact.clientId.toString(), style: Theme.of(context).textTheme.bodyText1,),
                Text('Has Extra Data : ' + medicalContact.hasExtraData.toString(), style: Theme.of(context).textTheme.bodyText1,),
          ],
        ),
      ),
    );
  }
}
