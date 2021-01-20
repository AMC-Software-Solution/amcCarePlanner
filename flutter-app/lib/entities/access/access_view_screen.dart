import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:amcCarePlanner/entities/access/bloc/access_bloc.dart';
import 'package:amcCarePlanner/entities/access/access_model.dart';
import 'package:amcCarePlanner/generated/l10n.dart';
import 'package:flutter/material.dart';
import 'package:intl/intl.dart';
import 'package:amcCarePlanner/shared/widgets/loading_indicator_widget.dart';
import 'access_route.dart';

class AccessViewScreen extends StatelessWidget {
  AccessViewScreen({Key key}) : super(key: AccessRoutes.createScreenKey);

  @override
  Widget build(BuildContext context) {
    return Scaffold(
          appBar: AppBar(
            centerTitle: true,
            title:Text(S.of(context).pageEntitiesAccessViewTitle),
          ),
          body: SingleChildScrollView(
            padding: const EdgeInsets.all(15.0),
            child: BlocBuilder<AccessBloc, AccessState>(
              buildWhen: (previous, current) => previous.loadedAccess != current.loadedAccess,
              builder: (context, state) {
                return Visibility(
                  visible: state.accessStatusUI == AccessStatusUI.done,
                  replacement: LoadingIndicator(),
                  child: Column(children: <Widget>[
                    accessCard(state.loadedAccess, context)
                  ]),
                );
              }
            ),
          ),
        floatingActionButton: FloatingActionButton(
          onPressed: () => Navigator.pushNamed(context, AccessRoutes.create),
          child: Icon(Icons.add, color: Theme.of(context).iconTheme.color,),
          backgroundColor: Theme.of(context).primaryColor,
        )
    );
  }

  Widget accessCard(Access access, BuildContext context) {
    return Card(
      child: Container(
        padding: EdgeInsets.all(20),
        width: MediaQuery.of(context).size.width * 0.90,
        child: Column(
          mainAxisAlignment: MainAxisAlignment.spaceEvenly,
          crossAxisAlignment: CrossAxisAlignment.start,
          children: <Widget>[
            Text('Id : ' + access.id.toString(), style: Theme.of(context).textTheme.bodyText1,),
                Text('Key Safe Number : ' + access.keySafeNumber.toString(), style: Theme.of(context).textTheme.bodyText1,),
                Text('Access Details : ' + access.accessDetails.toString(), style: Theme.of(context).textTheme.bodyText1,),
              Text('Created Date : ' + (access?.createdDate != null ? DateFormat.yMMMMd(S.of(context).locale).format(access.createdDate) : ''), style: Theme.of(context).textTheme.bodyText1,),
              Text('Last Updated Date : ' + (access?.lastUpdatedDate != null ? DateFormat.yMMMMd(S.of(context).locale).format(access.lastUpdatedDate) : ''), style: Theme.of(context).textTheme.bodyText1,),
                Text('Client Id : ' + access.clientId.toString(), style: Theme.of(context).textTheme.bodyText1,),
                Text('Has Extra Data : ' + access.hasExtraData.toString(), style: Theme.of(context).textTheme.bodyText1,),
          ],
        ),
      ),
    );
  }
}
