import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:amcCarePlanner/entities/disability_type/bloc/disability_type_bloc.dart';
import 'package:amcCarePlanner/entities/disability_type/disability_type_model.dart';
import 'package:amcCarePlanner/generated/l10n.dart';
import 'package:flutter/material.dart';
import 'package:amcCarePlanner/shared/widgets/loading_indicator_widget.dart';
import 'disability_type_route.dart';

class DisabilityTypeViewScreen extends StatelessWidget {
  DisabilityTypeViewScreen({Key key}) : super(key: DisabilityTypeRoutes.createScreenKey);

  @override
  Widget build(BuildContext context) {
    return Scaffold(
          appBar: AppBar(
            centerTitle: true,
            title:Text(S.of(context).pageEntitiesDisabilityTypeViewTitle),
          ),
          body: SingleChildScrollView(
            padding: const EdgeInsets.all(15.0),
            child: BlocBuilder<DisabilityTypeBloc, DisabilityTypeState>(
              buildWhen: (previous, current) => previous.loadedDisabilityType != current.loadedDisabilityType,
              builder: (context, state) {
                return Visibility(
                  visible: state.disabilityTypeStatusUI == DisabilityTypeStatusUI.done,
                  replacement: LoadingIndicator(),
                  child: Column(children: <Widget>[
                    disabilityTypeCard(state.loadedDisabilityType, context)
                  ]),
                );
              }
            ),
          ),
        floatingActionButton: FloatingActionButton(
          onPressed: () => Navigator.pushNamed(context, DisabilityTypeRoutes.create),
          child: Icon(Icons.add, color: Theme.of(context).iconTheme.color,),
          backgroundColor: Theme.of(context).primaryColor,
        )
    );
  }

  Widget disabilityTypeCard(DisabilityType disabilityType, BuildContext context) {
    return Card(
      child: Container(
        padding: EdgeInsets.all(20),
        width: MediaQuery.of(context).size.width * 0.90,
        child: Column(
          mainAxisAlignment: MainAxisAlignment.spaceEvenly,
          crossAxisAlignment: CrossAxisAlignment.start,
          children: <Widget>[
            Text('Id : ' + disabilityType.id.toString(), style: Theme.of(context).textTheme.bodyText1,),
                Text('Disability : ' + disabilityType.disability.toString(), style: Theme.of(context).textTheme.bodyText1,),
                Text('Description : ' + disabilityType.description.toString(), style: Theme.of(context).textTheme.bodyText1,),
                Text('Has Extra Data : ' + disabilityType.hasExtraData.toString(), style: Theme.of(context).textTheme.bodyText1,),
          ],
        ),
      ),
    );
  }
}
