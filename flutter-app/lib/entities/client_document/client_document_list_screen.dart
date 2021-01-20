import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:amcCarePlanner/account/login/login_repository.dart';
import 'package:amcCarePlanner/entities/client_document/bloc/client_document_bloc.dart';
import 'package:amcCarePlanner/entities/client_document/client_document_model.dart';
import 'package:amcCarePlanner/generated/l10n.dart';
import 'package:flutter/material.dart';
import 'package:amcCarePlanner/shared/widgets/drawer/bloc/drawer_bloc.dart';
import 'package:amcCarePlanner/shared/widgets/drawer/drawer_widget.dart';
import 'package:amcCarePlanner/shared/widgets/loading_indicator_widget.dart';
import 'package:amcCarePlanner/shared/models/entity_arguments.dart';
import 'client_document_route.dart';

class ClientDocumentListScreen extends StatelessWidget {
    ClientDocumentListScreen({Key key}) : super(key: ClientDocumentRoutes.listScreenKey);
    final GlobalKey<ScaffoldState> scaffoldKey = new GlobalKey<ScaffoldState>();

  @override
  Widget build(BuildContext context) {
    return  BlocListener<ClientDocumentBloc, ClientDocumentState>(
      listener: (context, state) {
        if(state.deleteStatus == ClientDocumentDeleteStatus.ok) {
          Navigator.of(context).pop();
          scaffoldKey.currentState.showSnackBar(new SnackBar(
              content: new Text(S.of(context).pageEntitiesClientDocumentDeleteOk)
          ));
        }
      },
      child: Scaffold(
          key: scaffoldKey,
          appBar: AppBar(
            centerTitle: true,
    title:Text(S.of(context).pageEntitiesClientDocumentListTitle),
          ),
          body: SingleChildScrollView(
            padding: const EdgeInsets.all(15.0),
            child: BlocBuilder<ClientDocumentBloc, ClientDocumentState>(
              buildWhen: (previous, current) => previous.clientDocuments != current.clientDocuments,
              builder: (context, state) {
                return Visibility(
                  visible: state.clientDocumentStatusUI == ClientDocumentStatusUI.done,
                  replacement: LoadingIndicator(),
                  child: Column(children: <Widget>[
                    for (ClientDocument clientDocument in state.clientDocuments) clientDocumentCard(clientDocument, context)
                  ]),
                );
              }
            ),
          ),
        drawer: BlocProvider<DrawerBloc>(
            create: (context) => DrawerBloc(loginRepository: LoginRepository()),
            child: AmcCarePlannerDrawer()),
        floatingActionButton: FloatingActionButton(
          onPressed: () => Navigator.pushNamed(context, ClientDocumentRoutes.create),
          child: Icon(Icons.add, color: Theme.of(context).iconTheme.color,),
          backgroundColor: Theme.of(context).primaryColor,
        )
      ),
    );
  }

  Widget clientDocumentCard(ClientDocument clientDocument, BuildContext context) {
    ClientDocumentBloc clientDocumentBloc = BlocProvider.of<ClientDocumentBloc>(context);
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
                  title: Text('Document Name : ${clientDocument.documentName.toString()}'),
                  subtitle: Text('Document Number : ${clientDocument.documentNumber.toString()}'),
              trailing: DropdownButton(
                  icon: Icon(Icons.more_vert),
                  onChanged: (String newValue) {
                    switch (newValue) {
                      case "Edit":
                        Navigator.pushNamed(
                            context, ClientDocumentRoutes.edit,
                            arguments: EntityArguments(clientDocument.id));
                        break;
                      case "Delete":
                        showDialog(
                          context: context,
                          builder: (BuildContext context) {
                            return deleteDialog(
                              clientDocumentBloc, context, clientDocument.id);
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
                  context, ClientDocumentRoutes.view,
                  arguments: EntityArguments(clientDocument.id)),
            ),
          ],
        ),
      ),
    );
  }

  Widget deleteDialog(ClientDocumentBloc clientDocumentBloc, BuildContext context, int id) {
    return BlocProvider<ClientDocumentBloc>.value(
      value: clientDocumentBloc,
      child: AlertDialog(
        title: new Text(S.of(context).pageEntitiesClientDocumentDeletePopupTitle),
        content: new Text(S.of(context).entityActionConfirmDelete),
        actions: <Widget>[
          new FlatButton(
            child: new Text(S.of(context).entityActionConfirmDeleteYes),
            onPressed: () {
              clientDocumentBloc.add(DeleteClientDocumentById(id: id));
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
