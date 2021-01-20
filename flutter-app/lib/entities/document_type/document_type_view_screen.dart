import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:amcCarePlanner/entities/document_type/bloc/document_type_bloc.dart';
import 'package:amcCarePlanner/entities/document_type/document_type_model.dart';
import 'package:amcCarePlanner/generated/l10n.dart';
import 'package:flutter/material.dart';
import 'package:intl/intl.dart';
import 'package:amcCarePlanner/shared/widgets/loading_indicator_widget.dart';
import 'document_type_route.dart';

class DocumentTypeViewScreen extends StatelessWidget {
  DocumentTypeViewScreen({Key key}) : super(key: DocumentTypeRoutes.createScreenKey);

  @override
  Widget build(BuildContext context) {
    return Scaffold(
          appBar: AppBar(
            centerTitle: true,
            title:Text(S.of(context).pageEntitiesDocumentTypeViewTitle),
          ),
          body: SingleChildScrollView(
            padding: const EdgeInsets.all(15.0),
            child: BlocBuilder<DocumentTypeBloc, DocumentTypeState>(
              buildWhen: (previous, current) => previous.loadedDocumentType != current.loadedDocumentType,
              builder: (context, state) {
                return Visibility(
                  visible: state.documentTypeStatusUI == DocumentTypeStatusUI.done,
                  replacement: LoadingIndicator(),
                  child: Column(children: <Widget>[
                    documentTypeCard(state.loadedDocumentType, context)
                  ]),
                );
              }
            ),
          ),
        floatingActionButton: FloatingActionButton(
          onPressed: () => Navigator.pushNamed(context, DocumentTypeRoutes.create),
          child: Icon(Icons.add, color: Theme.of(context).iconTheme.color,),
          backgroundColor: Theme.of(context).primaryColor,
        )
    );
  }

  Widget documentTypeCard(DocumentType documentType, BuildContext context) {
    return Card(
      child: Container(
        padding: EdgeInsets.all(20),
        width: MediaQuery.of(context).size.width * 0.90,
        child: Column(
          mainAxisAlignment: MainAxisAlignment.spaceEvenly,
          crossAxisAlignment: CrossAxisAlignment.start,
          children: <Widget>[
            Text('Id : ' + documentType.id.toString(), style: Theme.of(context).textTheme.bodyText1,),
                Text('Document Type Title : ' + documentType.documentTypeTitle.toString(), style: Theme.of(context).textTheme.bodyText1,),
                Text('Document Type Description : ' + documentType.documentTypeDescription.toString(), style: Theme.of(context).textTheme.bodyText1,),
              Text('Created Date : ' + (documentType?.createdDate != null ? DateFormat.yMMMMd(S.of(context).locale).format(documentType.createdDate) : ''), style: Theme.of(context).textTheme.bodyText1,),
              Text('Last Updated Date : ' + (documentType?.lastUpdatedDate != null ? DateFormat.yMMMMd(S.of(context).locale).format(documentType.lastUpdatedDate) : ''), style: Theme.of(context).textTheme.bodyText1,),
                Text('Has Extra Data : ' + documentType.hasExtraData.toString(), style: Theme.of(context).textTheme.bodyText1,),
          ],
        ),
      ),
    );
  }
}
