import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:amcCarePlanner/entities/servce_user_document/bloc/servce_user_document_bloc.dart';
import 'package:amcCarePlanner/entities/servce_user_document/servce_user_document_model.dart';
import 'package:amcCarePlanner/generated/l10n.dart';
import 'package:flutter/material.dart';
import 'package:intl/intl.dart';
import 'package:amcCarePlanner/shared/widgets/loading_indicator_widget.dart';
import 'servce_user_document_route.dart';

class ServceUserDocumentViewScreen extends StatelessWidget {
  ServceUserDocumentViewScreen({Key key}) : super(key: ServceUserDocumentRoutes.createScreenKey);

  @override
  Widget build(BuildContext context) {
    return Scaffold(
          appBar: AppBar(
            centerTitle: true,
            title:Text(S.of(context).pageEntitiesServceUserDocumentViewTitle),
          ),
          body: SingleChildScrollView(
            padding: const EdgeInsets.all(15.0),
            child: BlocBuilder<ServceUserDocumentBloc, ServceUserDocumentState>(
              buildWhen: (previous, current) => previous.loadedServceUserDocument != current.loadedServceUserDocument,
              builder: (context, state) {
                return Visibility(
                  visible: state.servceUserDocumentStatusUI == ServceUserDocumentStatusUI.done,
                  replacement: LoadingIndicator(),
                  child: Column(children: <Widget>[
                    servceUserDocumentCard(state.loadedServceUserDocument, context)
                  ]),
                );
              }
            ),
          ),
        floatingActionButton: FloatingActionButton(
          onPressed: () => Navigator.pushNamed(context, ServceUserDocumentRoutes.create),
          child: Icon(Icons.add, color: Theme.of(context).iconTheme.color,),
          backgroundColor: Theme.of(context).primaryColor,
        )
    );
  }

  Widget servceUserDocumentCard(ServceUserDocument servceUserDocument, BuildContext context) {
    return Card(
      child: Container(
        padding: EdgeInsets.all(20),
        width: MediaQuery.of(context).size.width * 0.90,
        child: Column(
          mainAxisAlignment: MainAxisAlignment.spaceEvenly,
          crossAxisAlignment: CrossAxisAlignment.start,
          children: <Widget>[
            Text('Id : ' + servceUserDocument.id.toString(), style: Theme.of(context).textTheme.bodyText1,),
                Text('Document Name : ' + servceUserDocument.documentName.toString(), style: Theme.of(context).textTheme.bodyText1,),
                Text('Document Number : ' + servceUserDocument.documentNumber.toString(), style: Theme.of(context).textTheme.bodyText1,),
                Text('Document Status : ' + servceUserDocument.documentStatus.toString(), style: Theme.of(context).textTheme.bodyText1,),
                Text('Note : ' + servceUserDocument.note.toString(), style: Theme.of(context).textTheme.bodyText1,),
              Text('Issued Date : ' + (servceUserDocument?.issuedDate != null ? DateFormat.yMMMMd(S.of(context).locale).format(servceUserDocument.issuedDate) : ''), style: Theme.of(context).textTheme.bodyText1,),
              Text('Expiry Date : ' + (servceUserDocument?.expiryDate != null ? DateFormat.yMMMMd(S.of(context).locale).format(servceUserDocument.expiryDate) : ''), style: Theme.of(context).textTheme.bodyText1,),
              Text('Uploaded Date : ' + (servceUserDocument?.uploadedDate != null ? DateFormat.yMMMMd(S.of(context).locale).format(servceUserDocument.uploadedDate) : ''), style: Theme.of(context).textTheme.bodyText1,),
                Text('Document File Url : ' + servceUserDocument.documentFileUrl.toString(), style: Theme.of(context).textTheme.bodyText1,),
              Text('Created Date : ' + (servceUserDocument?.createdDate != null ? DateFormat.yMMMMd(S.of(context).locale).format(servceUserDocument.createdDate) : ''), style: Theme.of(context).textTheme.bodyText1,),
              Text('Last Updated Date : ' + (servceUserDocument?.lastUpdatedDate != null ? DateFormat.yMMMMd(S.of(context).locale).format(servceUserDocument.lastUpdatedDate) : ''), style: Theme.of(context).textTheme.bodyText1,),
                Text('Client Id : ' + servceUserDocument.clientId.toString(), style: Theme.of(context).textTheme.bodyText1,),
                Text('Has Extra Data : ' + servceUserDocument.hasExtraData.toString(), style: Theme.of(context).textTheme.bodyText1,),
          ],
        ),
      ),
    );
  }
}
