import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:amcCarePlanner/entities/client/bloc/client_bloc.dart';
import 'package:amcCarePlanner/entities/client/client_model.dart';
import 'package:amcCarePlanner/generated/l10n.dart';
import 'package:flutter/material.dart';
import 'package:intl/intl.dart';
import 'package:amcCarePlanner/shared/widgets/loading_indicator_widget.dart';
import 'client_route.dart';

class ClientViewScreen extends StatelessWidget {
  ClientViewScreen({Key key}) : super(key: ClientRoutes.createScreenKey);

  @override
  Widget build(BuildContext context) {
    return Scaffold(
          appBar: AppBar(
            centerTitle: true,
            title:Text(S.of(context).pageEntitiesClientViewTitle),
          ),
          body: SingleChildScrollView(
            padding: const EdgeInsets.all(15.0),
            child: BlocBuilder<ClientBloc, ClientState>(
              buildWhen: (previous, current) => previous.loadedClient != current.loadedClient,
              builder: (context, state) {
                return Visibility(
                  visible: state.clientStatusUI == ClientStatusUI.done,
                  replacement: LoadingIndicator(),
                  child: Column(children: <Widget>[
                    clientCard(state.loadedClient, context)
                  ]),
                );
              }
            ),
          ),
        floatingActionButton: FloatingActionButton(
          onPressed: () => Navigator.pushNamed(context, ClientRoutes.create),
          child: Icon(Icons.add, color: Theme.of(context).iconTheme.color,),
          backgroundColor: Theme.of(context).primaryColor,
        )
    );
  }

  Widget clientCard(Client client, BuildContext context) {
    return Card(
      child: Container(
        padding: EdgeInsets.all(20),
        width: MediaQuery.of(context).size.width * 0.90,
        child: Column(
          mainAxisAlignment: MainAxisAlignment.spaceEvenly,
          crossAxisAlignment: CrossAxisAlignment.start,
          children: <Widget>[
            Text('Id : ' + client.id.toString(), style: Theme.of(context).textTheme.bodyText1,),
                Text('Client Name : ' + client.clientName.toString(), style: Theme.of(context).textTheme.bodyText1,),
                Text('Client Description : ' + client.clientDescription.toString(), style: Theme.of(context).textTheme.bodyText1,),
                Text('Client Logo Url : ' + client.clientLogoUrl.toString(), style: Theme.of(context).textTheme.bodyText1,),
                Text('Client Contact Name : ' + client.clientContactName.toString(), style: Theme.of(context).textTheme.bodyText1,),
                Text('Client Phone : ' + client.clientPhone.toString(), style: Theme.of(context).textTheme.bodyText1,),
                Text('Client Email : ' + client.clientEmail.toString(), style: Theme.of(context).textTheme.bodyText1,),
              Text('Created Date : ' + (client?.createdDate != null ? DateFormat.yMMMMd(S.of(context).locale).format(client.createdDate) : ''), style: Theme.of(context).textTheme.bodyText1,),
                Text('Enabled : ' + client.enabled.toString(), style: Theme.of(context).textTheme.bodyText1,),
                Text('Reason : ' + client.reason.toString(), style: Theme.of(context).textTheme.bodyText1,),
              Text('Last Updated Date : ' + (client?.lastUpdatedDate != null ? DateFormat.yMMMMd(S.of(context).locale).format(client.lastUpdatedDate) : ''), style: Theme.of(context).textTheme.bodyText1,),
                Text('Has Extra Data : ' + client.hasExtraData.toString(), style: Theme.of(context).textTheme.bodyText1,),
          ],
        ),
      ),
    );
  }
}
