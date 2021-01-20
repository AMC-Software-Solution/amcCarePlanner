import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:amcCarePlanner/entities/eligibility_type/bloc/eligibility_type_bloc.dart';
import 'package:amcCarePlanner/entities/eligibility_type/eligibility_type_model.dart';
import 'package:amcCarePlanner/generated/l10n.dart';
import 'package:flutter/material.dart';
import 'package:amcCarePlanner/shared/widgets/loading_indicator_widget.dart';
import 'eligibility_type_route.dart';

class EligibilityTypeViewScreen extends StatelessWidget {
  EligibilityTypeViewScreen({Key key}) : super(key: EligibilityTypeRoutes.createScreenKey);

  @override
  Widget build(BuildContext context) {
    return Scaffold(
          appBar: AppBar(
            centerTitle: true,
            title:Text(S.of(context).pageEntitiesEligibilityTypeViewTitle),
          ),
          body: SingleChildScrollView(
            padding: const EdgeInsets.all(15.0),
            child: BlocBuilder<EligibilityTypeBloc, EligibilityTypeState>(
              buildWhen: (previous, current) => previous.loadedEligibilityType != current.loadedEligibilityType,
              builder: (context, state) {
                return Visibility(
                  visible: state.eligibilityTypeStatusUI == EligibilityTypeStatusUI.done,
                  replacement: LoadingIndicator(),
                  child: Column(children: <Widget>[
                    eligibilityTypeCard(state.loadedEligibilityType, context)
                  ]),
                );
              }
            ),
          ),
        floatingActionButton: FloatingActionButton(
          onPressed: () => Navigator.pushNamed(context, EligibilityTypeRoutes.create),
          child: Icon(Icons.add, color: Theme.of(context).iconTheme.color,),
          backgroundColor: Theme.of(context).primaryColor,
        )
    );
  }

  Widget eligibilityTypeCard(EligibilityType eligibilityType, BuildContext context) {
    return Card(
      child: Container(
        padding: EdgeInsets.all(20),
        width: MediaQuery.of(context).size.width * 0.90,
        child: Column(
          mainAxisAlignment: MainAxisAlignment.spaceEvenly,
          crossAxisAlignment: CrossAxisAlignment.start,
          children: <Widget>[
            Text('Id : ' + eligibilityType.id.toString(), style: Theme.of(context).textTheme.bodyText1,),
                Text('Eligibility Type : ' + eligibilityType.eligibilityType.toString(), style: Theme.of(context).textTheme.bodyText1,),
                Text('Description : ' + eligibilityType.description.toString(), style: Theme.of(context).textTheme.bodyText1,),
                Text('Has Extra Data : ' + eligibilityType.hasExtraData.toString(), style: Theme.of(context).textTheme.bodyText1,),
          ],
        ),
      ),
    );
  }
}
