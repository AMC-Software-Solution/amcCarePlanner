import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:amcCarePlanner/entities/power_of_attorney/bloc/power_of_attorney_bloc.dart';
import 'package:amcCarePlanner/entities/power_of_attorney/power_of_attorney_model.dart';
import 'package:amcCarePlanner/generated/l10n.dart';
import 'package:flutter/material.dart';
import 'package:intl/intl.dart';
import 'package:amcCarePlanner/shared/widgets/loading_indicator_widget.dart';
import 'power_of_attorney_route.dart';

class PowerOfAttorneyViewScreen extends StatelessWidget {
  PowerOfAttorneyViewScreen({Key key}) : super(key: PowerOfAttorneyRoutes.createScreenKey);

  @override
  Widget build(BuildContext context) {
    return Scaffold(
          appBar: AppBar(
            centerTitle: true,
            title:Text(S.of(context).pageEntitiesPowerOfAttorneyViewTitle),
          ),
          body: SingleChildScrollView(
            padding: const EdgeInsets.all(15.0),
            child: BlocBuilder<PowerOfAttorneyBloc, PowerOfAttorneyState>(
              buildWhen: (previous, current) => previous.loadedPowerOfAttorney != current.loadedPowerOfAttorney,
              builder: (context, state) {
                return Visibility(
                  visible: state.powerOfAttorneyStatusUI == PowerOfAttorneyStatusUI.done,
                  replacement: LoadingIndicator(),
                  child: Column(children: <Widget>[
                    powerOfAttorneyCard(state.loadedPowerOfAttorney, context)
                  ]),
                );
              }
            ),
          ),
        floatingActionButton: FloatingActionButton(
          onPressed: () => Navigator.pushNamed(context, PowerOfAttorneyRoutes.create),
          child: Icon(Icons.add, color: Theme.of(context).iconTheme.color,),
          backgroundColor: Theme.of(context).primaryColor,
        )
    );
  }

  Widget powerOfAttorneyCard(PowerOfAttorney powerOfAttorney, BuildContext context) {
    return Card(
      child: Container(
        padding: EdgeInsets.all(20),
        width: MediaQuery.of(context).size.width * 0.90,
        child: Column(
          mainAxisAlignment: MainAxisAlignment.spaceEvenly,
          crossAxisAlignment: CrossAxisAlignment.start,
          children: <Widget>[
            Text('Id : ' + powerOfAttorney.id.toString(), style: Theme.of(context).textTheme.bodyText1,),
                Text('Power Of Attorney Consent : ' + powerOfAttorney.powerOfAttorneyConsent.toString(), style: Theme.of(context).textTheme.bodyText1,),
                Text('Health And Welfare : ' + powerOfAttorney.healthAndWelfare.toString(), style: Theme.of(context).textTheme.bodyText1,),
                Text('Health And Welfare Name : ' + powerOfAttorney.healthAndWelfareName.toString(), style: Theme.of(context).textTheme.bodyText1,),
                Text('Property And Fin Affairs : ' + powerOfAttorney.propertyAndFinAffairs.toString(), style: Theme.of(context).textTheme.bodyText1,),
                Text('Property And Fin Affairs Name : ' + powerOfAttorney.propertyAndFinAffairsName.toString(), style: Theme.of(context).textTheme.bodyText1,),
              Text('Created Date : ' + (powerOfAttorney?.createdDate != null ? DateFormat.yMMMMd(S.of(context).locale).format(powerOfAttorney.createdDate) : ''), style: Theme.of(context).textTheme.bodyText1,),
              Text('Last Updated Date : ' + (powerOfAttorney?.lastUpdatedDate != null ? DateFormat.yMMMMd(S.of(context).locale).format(powerOfAttorney.lastUpdatedDate) : ''), style: Theme.of(context).textTheme.bodyText1,),
                Text('Client Id : ' + powerOfAttorney.clientId.toString(), style: Theme.of(context).textTheme.bodyText1,),
                Text('Has Extra Data : ' + powerOfAttorney.hasExtraData.toString(), style: Theme.of(context).textTheme.bodyText1,),
          ],
        ),
      ),
    );
  }
}
