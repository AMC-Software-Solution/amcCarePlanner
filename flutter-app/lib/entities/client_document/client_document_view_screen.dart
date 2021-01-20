import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:amcCarePlanner/entities/client_document/bloc/client_document_bloc.dart';
import 'package:amcCarePlanner/entities/client_document/client_document_model.dart';
import 'package:amcCarePlanner/generated/l10n.dart';
import 'package:flutter/material.dart';
import 'package:intl/intl.dart';
import 'package:amcCarePlanner/shared/widgets/loading_indicator_widget.dart';
import 'client_document_route.dart';

class ClientDocumentViewScreen extends StatelessWidget {
  ClientDocumentViewScreen({Key key}) : super(key: ClientDocumentRoutes.createScreenKey);

  @override
  Widget build(BuildContext context) {
    return Scaffold(
          appBar: AppBar(
            centerTitle: true,
            title:Text(S.of(context).pageEntitiesClientDocumentViewTitle),
          ),
          body: SingleChildScrollView(
            padding: const EdgeInsets.all(15.0),
            child: BlocBuilder<ClientDocumentBloc, ClientDocumentState>(
              buildWhen: (previous, current) => previous.loadedClientDocument != current.loadedClientDocument,
              builder: (context, state) {
                return Visibility(
                  visible: state.clientDocumentStatusUI == ClientDocumentStatusUI.done,
                  replacement: LoadingIndicator(),
                  child: Column(children: <Widget>[
                    clientDocumentCard(state.loadedClientDocument, context)
                  ]),
                );
              }
            ),
          ),
        floatingActionButton: FloatingActionButton(
          onPressed: () => Navigator.pushNamed(context, ClientDocumentRoutes.create),
          child: Icon(Icons.add, color: Theme.of(context).iconTheme.color,),
          backgroundColor: Theme.of(context).primaryColor,
        )
    );
  }

  Widget clientDocumentCard(ClientDocument clientDocument, BuildContext context) {
    return Card(
      child: Container(
        padding: EdgeInsets.all(20),
        width: MediaQuery.of(context).size.width * 0.90,
        child: Column(
          mainAxisAlignment: MainAxisAlignment.spaceEvenly,
          crossAxisAlignment: CrossAxisAlignment.start,
          children: <Widget>[
            Text('Id : ' + clientDocument.id.toString(), style: Theme.of(context).textTheme.bodyText1,),
                Text('Document Name : ' + clientDocument.documentName.toString(), style: Theme.of(context).textTheme.bodyText1,),
                Text('Document Number : ' + clientDocument.documentNumber.toString(), style: Theme.of(context).textTheme.bodyText1,),
                Text('Document Type : ' + clientDocument.documentType.toString(), style: Theme.of(context).textTheme.bodyText1,),
                Text('Client Document Status : ' + clientDocument.clientDocumentStatus.toString(), style: Theme.of(context).textTheme.bodyText1,),
                Text('Note : ' + clientDocument.note.toString(), style: Theme.of(context).textTheme.bodyText1,),
              Text('Issued Date : ' + (clientDocument?.issuedDate != null ? DateFormat.yMMMMd(S.of(context).locale).format(clientDocument.issuedDate) : ''), style: Theme.of(context).textTheme.bodyText1,),
              Text('Expiry Date : ' + (clientDocument?.expiryDate != null ? DateFormat.yMMMMd(S.of(context).locale).format(clientDocument.expiryDate) : ''), style: Theme.of(context).textTheme.bodyText1,),
              Text('Uploaded Date : ' + (clientDocument?.uploadedDate != null ? DateFormat.yMMMMd(S.of(context).locale).format(clientDocument.uploadedDate) : ''), style: Theme.of(context).textTheme.bodyText1,),
                Text('Document File Url : ' + clientDocument.documentFileUrl.toString(), style: Theme.of(context).textTheme.bodyText1,),
              Text('Created Date : ' + (clientDocument?.createdDate != null ? DateFormat.yMMMMd(S.of(context).locale).format(clientDocument.createdDate) : ''), style: Theme.of(context).textTheme.bodyText1,),
              Text('Last Updated Date : ' + (clientDocument?.lastUpdatedDate != null ? DateFormat.yMMMMd(S.of(context).locale).format(clientDocument.lastUpdatedDate) : ''), style: Theme.of(context).textTheme.bodyText1,),
                Text('Client Id : ' + clientDocument.clientId.toString(), style: Theme.of(context).textTheme.bodyText1,),
                Text('Has Extra Data : ' + clientDocument.hasExtraData.toString(), style: Theme.of(context).textTheme.bodyText1,),
          ],
        ),
      ),
    );
  }
}
