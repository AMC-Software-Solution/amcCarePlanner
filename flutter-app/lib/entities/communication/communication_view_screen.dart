import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:amcCarePlanner/entities/communication/bloc/communication_bloc.dart';
import 'package:amcCarePlanner/entities/communication/communication_model.dart';
import 'package:amcCarePlanner/generated/l10n.dart';
import 'package:flutter/material.dart';
import 'package:intl/intl.dart';
import 'package:amcCarePlanner/shared/widgets/loading_indicator_widget.dart';
import 'communication_route.dart';

class CommunicationViewScreen extends StatelessWidget {
  CommunicationViewScreen({Key key}) : super(key: CommunicationRoutes.createScreenKey);

  @override
  Widget build(BuildContext context) {
    return Scaffold(
          appBar: AppBar(
            centerTitle: true,
            title:Text(S.of(context).pageEntitiesCommunicationViewTitle),
          ),
          body: SingleChildScrollView(
            padding: const EdgeInsets.all(15.0),
            child: BlocBuilder<CommunicationBloc, CommunicationState>(
              buildWhen: (previous, current) => previous.loadedCommunication != current.loadedCommunication,
              builder: (context, state) {
                return Visibility(
                  visible: state.communicationStatusUI == CommunicationStatusUI.done,
                  replacement: LoadingIndicator(),
                  child: Column(children: <Widget>[
                    communicationCard(state.loadedCommunication, context)
                  ]),
                );
              }
            ),
          ),
        floatingActionButton: FloatingActionButton(
          onPressed: () => Navigator.pushNamed(context, CommunicationRoutes.create),
          child: Icon(Icons.add, color: Theme.of(context).iconTheme.color,),
          backgroundColor: Theme.of(context).primaryColor,
        )
    );
  }

  Widget communicationCard(Communication communication, BuildContext context) {
    return Card(
      child: Container(
        padding: EdgeInsets.all(20),
        width: MediaQuery.of(context).size.width * 0.90,
        child: Column(
          mainAxisAlignment: MainAxisAlignment.spaceEvenly,
          crossAxisAlignment: CrossAxisAlignment.start,
          children: <Widget>[
            Text('Id : ' + communication.id.toString(), style: Theme.of(context).textTheme.bodyText1,),
                Text('Communication Type : ' + communication.communicationType.toString(), style: Theme.of(context).textTheme.bodyText1,),
                Text('Note : ' + communication.note.toString(), style: Theme.of(context).textTheme.bodyText1,),
              Text('Communication Date : ' + (communication?.communicationDate != null ? DateFormat.yMMMMd(S.of(context).locale).format(communication.communicationDate) : ''), style: Theme.of(context).textTheme.bodyText1,),
                Text('Attachment Url : ' + communication.attachmentUrl.toString(), style: Theme.of(context).textTheme.bodyText1,),
              Text('Created Date : ' + (communication?.createdDate != null ? DateFormat.yMMMMd(S.of(context).locale).format(communication.createdDate) : ''), style: Theme.of(context).textTheme.bodyText1,),
              Text('Last Updated Date : ' + (communication?.lastUpdatedDate != null ? DateFormat.yMMMMd(S.of(context).locale).format(communication.lastUpdatedDate) : ''), style: Theme.of(context).textTheme.bodyText1,),
                Text('Client Id : ' + communication.clientId.toString(), style: Theme.of(context).textTheme.bodyText1,),
                Text('Has Extra Data : ' + communication.hasExtraData.toString(), style: Theme.of(context).textTheme.bodyText1,),
          ],
        ),
      ),
    );
  }
}
