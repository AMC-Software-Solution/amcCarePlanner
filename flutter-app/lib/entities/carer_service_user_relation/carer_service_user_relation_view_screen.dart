import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:amcCarePlanner/entities/carer_service_user_relation/bloc/carer_service_user_relation_bloc.dart';
import 'package:amcCarePlanner/entities/carer_service_user_relation/carer_service_user_relation_model.dart';
import 'package:amcCarePlanner/generated/l10n.dart';
import 'package:flutter/material.dart';
import 'package:intl/intl.dart';
import 'package:amcCarePlanner/shared/widgets/loading_indicator_widget.dart';
import 'carer_service_user_relation_route.dart';

class CarerServiceUserRelationViewScreen extends StatelessWidget {
  CarerServiceUserRelationViewScreen({Key key}) : super(key: CarerServiceUserRelationRoutes.createScreenKey);

  @override
  Widget build(BuildContext context) {
    return Scaffold(
          appBar: AppBar(
            centerTitle: true,
            title:Text(S.of(context).pageEntitiesCarerServiceUserRelationViewTitle),
          ),
          body: SingleChildScrollView(
            padding: const EdgeInsets.all(15.0),
            child: BlocBuilder<CarerServiceUserRelationBloc, CarerServiceUserRelationState>(
              buildWhen: (previous, current) => previous.loadedCarerServiceUserRelation != current.loadedCarerServiceUserRelation,
              builder: (context, state) {
                return Visibility(
                  visible: state.carerServiceUserRelationStatusUI == CarerServiceUserRelationStatusUI.done,
                  replacement: LoadingIndicator(),
                  child: Column(children: <Widget>[
                    carerServiceUserRelationCard(state.loadedCarerServiceUserRelation, context)
                  ]),
                );
              }
            ),
          ),
        floatingActionButton: FloatingActionButton(
          onPressed: () => Navigator.pushNamed(context, CarerServiceUserRelationRoutes.create),
          child: Icon(Icons.add, color: Theme.of(context).iconTheme.color,),
          backgroundColor: Theme.of(context).primaryColor,
        )
    );
  }

  Widget carerServiceUserRelationCard(CarerServiceUserRelation carerServiceUserRelation, BuildContext context) {
    return Card(
      child: Container(
        padding: EdgeInsets.all(20),
        width: MediaQuery.of(context).size.width * 0.90,
        child: Column(
          mainAxisAlignment: MainAxisAlignment.spaceEvenly,
          crossAxisAlignment: CrossAxisAlignment.start,
          children: <Widget>[
            Text('Id : ' + carerServiceUserRelation.id.toString(), style: Theme.of(context).textTheme.bodyText1,),
                Text('Reason : ' + carerServiceUserRelation.reason.toString(), style: Theme.of(context).textTheme.bodyText1,),
                Text('Count : ' + carerServiceUserRelation.count.toString(), style: Theme.of(context).textTheme.bodyText1,),
              Text('Created Date : ' + (carerServiceUserRelation?.createdDate != null ? DateFormat.yMMMMd(S.of(context).locale).format(carerServiceUserRelation.createdDate) : ''), style: Theme.of(context).textTheme.bodyText1,),
              Text('Last Updated Date : ' + (carerServiceUserRelation?.lastUpdatedDate != null ? DateFormat.yMMMMd(S.of(context).locale).format(carerServiceUserRelation.lastUpdatedDate) : ''), style: Theme.of(context).textTheme.bodyText1,),
                Text('Client Id : ' + carerServiceUserRelation.clientId.toString(), style: Theme.of(context).textTheme.bodyText1,),
                Text('Has Extra Data : ' + carerServiceUserRelation.hasExtraData.toString(), style: Theme.of(context).textTheme.bodyText1,),
          ],
        ),
      ),
    );
  }
}
