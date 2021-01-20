import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:amcCarePlanner/entities/disability/bloc/disability_bloc.dart';
import 'package:amcCarePlanner/entities/disability/disability_model.dart';
import 'package:amcCarePlanner/generated/l10n.dart';
import 'package:flutter/material.dart';
import 'package:intl/intl.dart';
import 'package:amcCarePlanner/shared/widgets/loading_indicator_widget.dart';
import 'disability_route.dart';

class DisabilityViewScreen extends StatelessWidget {
  DisabilityViewScreen({Key key}) : super(key: DisabilityRoutes.createScreenKey);

  @override
  Widget build(BuildContext context) {
    return Scaffold(
          appBar: AppBar(
            centerTitle: true,
            title:Text(S.of(context).pageEntitiesDisabilityViewTitle),
          ),
          body: SingleChildScrollView(
            padding: const EdgeInsets.all(15.0),
            child: BlocBuilder<DisabilityBloc, DisabilityState>(
              buildWhen: (previous, current) => previous.loadedDisability != current.loadedDisability,
              builder: (context, state) {
                return Visibility(
                  visible: state.disabilityStatusUI == DisabilityStatusUI.done,
                  replacement: LoadingIndicator(),
                  child: Column(children: <Widget>[
                    disabilityCard(state.loadedDisability, context)
                  ]),
                );
              }
            ),
          ),
        floatingActionButton: FloatingActionButton(
          onPressed: () => Navigator.pushNamed(context, DisabilityRoutes.create),
          child: Icon(Icons.add, color: Theme.of(context).iconTheme.color,),
          backgroundColor: Theme.of(context).primaryColor,
        )
    );
  }

  Widget disabilityCard(Disability disability, BuildContext context) {
    return Card(
      child: Container(
        padding: EdgeInsets.all(20),
        width: MediaQuery.of(context).size.width * 0.90,
        child: Column(
          mainAxisAlignment: MainAxisAlignment.spaceEvenly,
          crossAxisAlignment: CrossAxisAlignment.start,
          children: <Widget>[
            Text('Id : ' + disability.id.toString(), style: Theme.of(context).textTheme.bodyText1,),
                Text('Is Disabled : ' + disability.isDisabled.toString(), style: Theme.of(context).textTheme.bodyText1,),
                Text('Note : ' + disability.note.toString(), style: Theme.of(context).textTheme.bodyText1,),
              Text('Created Date : ' + (disability?.createdDate != null ? DateFormat.yMMMMd(S.of(context).locale).format(disability.createdDate) : ''), style: Theme.of(context).textTheme.bodyText1,),
              Text('Last Updated Date : ' + (disability?.lastUpdatedDate != null ? DateFormat.yMMMMd(S.of(context).locale).format(disability.lastUpdatedDate) : ''), style: Theme.of(context).textTheme.bodyText1,),
                Text('Client Id : ' + disability.clientId.toString(), style: Theme.of(context).textTheme.bodyText1,),
                Text('Has Extra Data : ' + disability.hasExtraData.toString(), style: Theme.of(context).textTheme.bodyText1,),
          ],
        ),
      ),
    );
  }
}
