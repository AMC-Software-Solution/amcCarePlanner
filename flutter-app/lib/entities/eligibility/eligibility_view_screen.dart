import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:amcCarePlanner/entities/eligibility/bloc/eligibility_bloc.dart';
import 'package:amcCarePlanner/entities/eligibility/eligibility_model.dart';
import 'package:amcCarePlanner/generated/l10n.dart';
import 'package:flutter/material.dart';
import 'package:intl/intl.dart';
import 'package:amcCarePlanner/shared/widgets/loading_indicator_widget.dart';
import 'eligibility_route.dart';

class EligibilityViewScreen extends StatelessWidget {
  EligibilityViewScreen({Key key}) : super(key: EligibilityRoutes.createScreenKey);

  @override
  Widget build(BuildContext context) {
    return Scaffold(
          appBar: AppBar(
            centerTitle: true,
            title:Text(S.of(context).pageEntitiesEligibilityViewTitle),
          ),
          body: SingleChildScrollView(
            padding: const EdgeInsets.all(15.0),
            child: BlocBuilder<EligibilityBloc, EligibilityState>(
              buildWhen: (previous, current) => previous.loadedEligibility != current.loadedEligibility,
              builder: (context, state) {
                return Visibility(
                  visible: state.eligibilityStatusUI == EligibilityStatusUI.done,
                  replacement: LoadingIndicator(),
                  child: Column(children: <Widget>[
                    eligibilityCard(state.loadedEligibility, context)
                  ]),
                );
              }
            ),
          ),
        floatingActionButton: FloatingActionButton(
          onPressed: () => Navigator.pushNamed(context, EligibilityRoutes.create),
          child: Icon(Icons.add, color: Theme.of(context).iconTheme.color,),
          backgroundColor: Theme.of(context).primaryColor,
        )
    );
  }

  Widget eligibilityCard(Eligibility eligibility, BuildContext context) {
    return Card(
      child: Container(
        padding: EdgeInsets.all(20),
        width: MediaQuery.of(context).size.width * 0.90,
        child: Column(
          mainAxisAlignment: MainAxisAlignment.spaceEvenly,
          crossAxisAlignment: CrossAxisAlignment.start,
          children: <Widget>[
            Text('Id : ' + eligibility.id.toString(), style: Theme.of(context).textTheme.bodyText1,),
                Text('Is Eligible : ' + eligibility.isEligible.toString(), style: Theme.of(context).textTheme.bodyText1,),
                Text('Note : ' + eligibility.note.toString(), style: Theme.of(context).textTheme.bodyText1,),
              Text('Created Date : ' + (eligibility?.createdDate != null ? DateFormat.yMMMMd(S.of(context).locale).format(eligibility.createdDate) : ''), style: Theme.of(context).textTheme.bodyText1,),
              Text('Last Updated Date : ' + (eligibility?.lastUpdatedDate != null ? DateFormat.yMMMMd(S.of(context).locale).format(eligibility.lastUpdatedDate) : ''), style: Theme.of(context).textTheme.bodyText1,),
                Text('Client Id : ' + eligibility.clientId.toString(), style: Theme.of(context).textTheme.bodyText1,),
                Text('Has Extra Data : ' + eligibility.hasExtraData.toString(), style: Theme.of(context).textTheme.bodyText1,),
          ],
        ),
      ),
    );
  }
}
