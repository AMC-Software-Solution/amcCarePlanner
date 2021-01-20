import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:amcCarePlanner/account/login/login_repository.dart';
import 'package:amcCarePlanner/entities/document_type/bloc/document_type_bloc.dart';
import 'package:amcCarePlanner/entities/document_type/document_type_model.dart';
import 'package:amcCarePlanner/generated/l10n.dart';
import 'package:flutter/material.dart';
import 'package:amcCarePlanner/shared/widgets/drawer/bloc/drawer_bloc.dart';
import 'package:amcCarePlanner/shared/widgets/drawer/drawer_widget.dart';
import 'package:amcCarePlanner/shared/widgets/loading_indicator_widget.dart';
import 'package:amcCarePlanner/shared/models/entity_arguments.dart';
import 'document_type_route.dart';

class DocumentTypeListScreen extends StatelessWidget {
    DocumentTypeListScreen({Key key}) : super(key: DocumentTypeRoutes.listScreenKey);
    final GlobalKey<ScaffoldState> scaffoldKey = new GlobalKey<ScaffoldState>();

  @override
  Widget build(BuildContext context) {
    return  BlocListener<DocumentTypeBloc, DocumentTypeState>(
      listener: (context, state) {
        if(state.deleteStatus == DocumentTypeDeleteStatus.ok) {
          Navigator.of(context).pop();
          scaffoldKey.currentState.showSnackBar(new SnackBar(
              content: new Text(S.of(context).pageEntitiesDocumentTypeDeleteOk)
          ));
        }
      },
      child: Scaffold(
          key: scaffoldKey,
          appBar: AppBar(
            centerTitle: true,
    title:Text(S.of(context).pageEntitiesDocumentTypeListTitle),
          ),
          body: SingleChildScrollView(
            padding: const EdgeInsets.all(15.0),
            child: BlocBuilder<DocumentTypeBloc, DocumentTypeState>(
              buildWhen: (previous, current) => previous.documentTypes != current.documentTypes,
              builder: (context, state) {
                return Visibility(
                  visible: state.documentTypeStatusUI == DocumentTypeStatusUI.done,
                  replacement: LoadingIndicator(),
                  child: Column(children: <Widget>[
                    for (DocumentType documentType in state.documentTypes) documentTypeCard(documentType, context)
                  ]),
                );
              }
            ),
          ),
        drawer: BlocProvider<DrawerBloc>(
            create: (context) => DrawerBloc(loginRepository: LoginRepository()),
            child: AmcCarePlannerDrawer()),
        floatingActionButton: FloatingActionButton(
          onPressed: () => Navigator.pushNamed(context, DocumentTypeRoutes.create),
          child: Icon(Icons.add, color: Theme.of(context).iconTheme.color,),
          backgroundColor: Theme.of(context).primaryColor,
        )
      ),
    );
  }

  Widget documentTypeCard(DocumentType documentType, BuildContext context) {
    DocumentTypeBloc documentTypeBloc = BlocProvider.of<DocumentTypeBloc>(context);
    return Card(
      child: Container(
        width: MediaQuery.of(context).size.width * 0.90,
        child: Column(
          children: <Widget>[
            ListTile(
              leading: Icon(
                Icons.account_circle,
                size: 60.0,
                color: Theme.of(context).primaryColor,
              ),
                  title: Text('Document Type Title : ${documentType.documentTypeTitle.toString()}'),
                  subtitle: Text('Document Type Description : ${documentType.documentTypeDescription.toString()}'),
              trailing: DropdownButton(
                  icon: Icon(Icons.more_vert),
                  onChanged: (String newValue) {
                    switch (newValue) {
                      case "Edit":
                        Navigator.pushNamed(
                            context, DocumentTypeRoutes.edit,
                            arguments: EntityArguments(documentType.id));
                        break;
                      case "Delete":
                        showDialog(
                          context: context,
                          builder: (BuildContext context) {
                            return deleteDialog(
                              documentTypeBloc, context, documentType.id);
                          },
                        );
                    }
                  },
                  items: [
                    DropdownMenuItem<String>(
                      value: 'Edit',
                      child: Text('Edit'),
                    ),
                    DropdownMenuItem<String>(
                        value: 'Delete', child: Text('Delete'))
                  ]),
              selected: false,
              onTap: () => Navigator.pushNamed(
                  context, DocumentTypeRoutes.view,
                  arguments: EntityArguments(documentType.id)),
            ),
          ],
        ),
      ),
    );
  }

  Widget deleteDialog(DocumentTypeBloc documentTypeBloc, BuildContext context, int id) {
    return BlocProvider<DocumentTypeBloc>.value(
      value: documentTypeBloc,
      child: AlertDialog(
        title: new Text(S.of(context).pageEntitiesDocumentTypeDeletePopupTitle),
        content: new Text(S.of(context).entityActionConfirmDelete),
        actions: <Widget>[
          new FlatButton(
            child: new Text(S.of(context).entityActionConfirmDeleteYes),
            onPressed: () {
              documentTypeBloc.add(DeleteDocumentTypeById(id: id));
            },
          ),
          new FlatButton(
            child: new Text(S.of(context).entityActionConfirmDeleteNo),
            onPressed: () {
              Navigator.of(context).pop();
            },
          ),
        ],
      ),
    );
  }

}
