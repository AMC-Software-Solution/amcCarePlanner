import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:amcCarePlanner/entities/service_user/bloc/service_user_bloc.dart';
import 'package:amcCarePlanner/entities/service_user/service_user_model.dart';
import 'package:amcCarePlanner/generated/l10n.dart';
import 'package:flutter/material.dart';
import 'package:intl/intl.dart';
import 'package:amcCarePlanner/shared/widgets/loading_indicator_widget.dart';
import 'service_user_route.dart';

class ServiceUserViewScreen extends StatelessWidget {
  ServiceUserViewScreen({Key key}) : super(key: ServiceUserRoutes.createScreenKey);

  @override
  Widget build(BuildContext context) {
    return Scaffold(
          appBar: AppBar(
            centerTitle: true,
            title:Text(S.of(context).pageEntitiesServiceUserViewTitle),
          ),
          body: SingleChildScrollView(
            padding: const EdgeInsets.all(15.0),
            child: BlocBuilder<ServiceUserBloc, ServiceUserState>(
              buildWhen: (previous, current) => previous.loadedServiceUser != current.loadedServiceUser,
              builder: (context, state) {
                return Visibility(
                  visible: state.serviceUserStatusUI == ServiceUserStatusUI.done,
                  replacement: LoadingIndicator(),
                  child: Column(children: <Widget>[
                    serviceUserCard(state.loadedServiceUser, context)
                  ]),
                );
              }
            ),
          ),
        floatingActionButton: FloatingActionButton(
          onPressed: () => Navigator.pushNamed(context, ServiceUserRoutes.create),
          child: Icon(Icons.add, color: Theme.of(context).iconTheme.color,),
          backgroundColor: Theme.of(context).primaryColor,
        )
    );
  }

  Widget serviceUserCard(ServiceUser serviceUser, BuildContext context) {
    return Card(
      child: Container(
        padding: EdgeInsets.all(20),
        width: MediaQuery.of(context).size.width * 0.90,
        child: Column(
          mainAxisAlignment: MainAxisAlignment.spaceEvenly,
          crossAxisAlignment: CrossAxisAlignment.start,
          children: <Widget>[
            Text('Id : ' + serviceUser.id.toString(), style: Theme.of(context).textTheme.bodyText1,),
                Text('Titlle : ' + serviceUser.titlle.toString(), style: Theme.of(context).textTheme.bodyText1,),
                Text('First Name : ' + serviceUser.firstName.toString(), style: Theme.of(context).textTheme.bodyText1,),
                Text('Middle Name : ' + serviceUser.middleName.toString(), style: Theme.of(context).textTheme.bodyText1,),
                Text('Last Name : ' + serviceUser.lastName.toString(), style: Theme.of(context).textTheme.bodyText1,),
                Text('Preferred Name : ' + serviceUser.preferredName.toString(), style: Theme.of(context).textTheme.bodyText1,),
                Text('Email : ' + serviceUser.email.toString(), style: Theme.of(context).textTheme.bodyText1,),
                Text('Service User Code : ' + serviceUser.serviceUserCode.toString(), style: Theme.of(context).textTheme.bodyText1,),
              Text('Date Of Birth : ' + (serviceUser?.dateOfBirth != null ? DateFormat.yMMMMd(S.of(context).locale).format(serviceUser.dateOfBirth) : ''), style: Theme.of(context).textTheme.bodyText1,),
              Text('Last Visit Date : ' + (serviceUser?.lastVisitDate != null ? DateFormat.yMMMMd(S.of(context).locale).format(serviceUser.lastVisitDate) : ''), style: Theme.of(context).textTheme.bodyText1,),
              Text('Start Date : ' + (serviceUser?.startDate != null ? DateFormat.yMMMMd(S.of(context).locale).format(serviceUser.startDate) : ''), style: Theme.of(context).textTheme.bodyText1,),
                Text('Support Type : ' + serviceUser.supportType.toString(), style: Theme.of(context).textTheme.bodyText1,),
                Text('Service User Category : ' + serviceUser.serviceUserCategory.toString(), style: Theme.of(context).textTheme.bodyText1,),
                Text('Vulnerability : ' + serviceUser.vulnerability.toString(), style: Theme.of(context).textTheme.bodyText1,),
                Text('Service Priority : ' + serviceUser.servicePriority.toString(), style: Theme.of(context).textTheme.bodyText1,),
                Text('Source : ' + serviceUser.source.toString(), style: Theme.of(context).textTheme.bodyText1,),
                Text('Status : ' + serviceUser.status.toString(), style: Theme.of(context).textTheme.bodyText1,),
                Text('First Language : ' + serviceUser.firstLanguage.toString(), style: Theme.of(context).textTheme.bodyText1,),
                Text('Interpreter Required : ' + serviceUser.interpreterRequired.toString(), style: Theme.of(context).textTheme.bodyText1,),
              Text('Activated Date : ' + (serviceUser?.activatedDate != null ? DateFormat.yMMMMd(S.of(context).locale).format(serviceUser.activatedDate) : ''), style: Theme.of(context).textTheme.bodyText1,),
                Text('Profile Photo Url : ' + serviceUser.profilePhotoUrl.toString(), style: Theme.of(context).textTheme.bodyText1,),
                Text('Last Recorded Height : ' + serviceUser.lastRecordedHeight.toString(), style: Theme.of(context).textTheme.bodyText1,),
                Text('Last Recorded Weight : ' + serviceUser.lastRecordedWeight.toString(), style: Theme.of(context).textTheme.bodyText1,),
                Text('Has Medical Condition : ' + serviceUser.hasMedicalCondition.toString(), style: Theme.of(context).textTheme.bodyText1,),
                Text('Medical Condition Summary : ' + serviceUser.medicalConditionSummary.toString(), style: Theme.of(context).textTheme.bodyText1,),
              Text('Created Date : ' + (serviceUser?.createdDate != null ? DateFormat.yMMMMd(S.of(context).locale).format(serviceUser.createdDate) : ''), style: Theme.of(context).textTheme.bodyText1,),
              Text('Last Updated Date : ' + (serviceUser?.lastUpdatedDate != null ? DateFormat.yMMMMd(S.of(context).locale).format(serviceUser.lastUpdatedDate) : ''), style: Theme.of(context).textTheme.bodyText1,),
                Text('Client Id : ' + serviceUser.clientId.toString(), style: Theme.of(context).textTheme.bodyText1,),
                Text('Has Extra Data : ' + serviceUser.hasExtraData.toString(), style: Theme.of(context).textTheme.bodyText1,),
          ],
        ),
      ),
    );
  }
}
