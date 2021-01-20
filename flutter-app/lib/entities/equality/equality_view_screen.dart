import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:amcCarePlanner/entities/equality/bloc/equality_bloc.dart';
import 'package:amcCarePlanner/entities/equality/equality_model.dart';
import 'package:amcCarePlanner/generated/l10n.dart';
import 'package:flutter/material.dart';
import 'package:intl/intl.dart';
import 'package:amcCarePlanner/shared/widgets/loading_indicator_widget.dart';
import 'equality_route.dart';

class EqualityViewScreen extends StatelessWidget {
  EqualityViewScreen({Key key}) : super(key: EqualityRoutes.createScreenKey);

  @override
  Widget build(BuildContext context) {
    return Scaffold(
          appBar: AppBar(
            centerTitle: true,
            title:Text(S.of(context).pageEntitiesEqualityViewTitle),
          ),
          body: SingleChildScrollView(
            padding: const EdgeInsets.all(15.0),
            child: BlocBuilder<EqualityBloc, EqualityState>(
              buildWhen: (previous, current) => previous.loadedEquality != current.loadedEquality,
              builder: (context, state) {
                return Visibility(
                  visible: state.equalityStatusUI == EqualityStatusUI.done,
                  replacement: LoadingIndicator(),
                  child: Column(children: <Widget>[
                    equalityCard(state.loadedEquality, context)
                  ]),
                );
              }
            ),
          ),
        floatingActionButton: FloatingActionButton(
          onPressed: () => Navigator.pushNamed(context, EqualityRoutes.create),
          child: Icon(Icons.add, color: Theme.of(context).iconTheme.color,),
          backgroundColor: Theme.of(context).primaryColor,
        )
    );
  }

  Widget equalityCard(Equality equality, BuildContext context) {
    return Card(
      child: Container(
        padding: EdgeInsets.all(20),
        width: MediaQuery.of(context).size.width * 0.90,
        child: Column(
          mainAxisAlignment: MainAxisAlignment.spaceEvenly,
          crossAxisAlignment: CrossAxisAlignment.start,
          children: <Widget>[
            Text('Id : ' + equality.id.toString(), style: Theme.of(context).textTheme.bodyText1,),
                Text('Gender : ' + equality.gender.toString(), style: Theme.of(context).textTheme.bodyText1,),
                Text('Marital Status : ' + equality.maritalStatus.toString(), style: Theme.of(context).textTheme.bodyText1,),
                Text('Religion : ' + equality.religion.toString(), style: Theme.of(context).textTheme.bodyText1,),
              Text('Created Date : ' + (equality?.createdDate != null ? DateFormat.yMMMMd(S.of(context).locale).format(equality.createdDate) : ''), style: Theme.of(context).textTheme.bodyText1,),
              Text('Last Updated Date : ' + (equality?.lastUpdatedDate != null ? DateFormat.yMMMMd(S.of(context).locale).format(equality.lastUpdatedDate) : ''), style: Theme.of(context).textTheme.bodyText1,),
                Text('Client Id : ' + equality.clientId.toString(), style: Theme.of(context).textTheme.bodyText1,),
                Text('Has Extra Data : ' + equality.hasExtraData.toString(), style: Theme.of(context).textTheme.bodyText1,),
          ],
        ),
      ),
    );
  }
}
