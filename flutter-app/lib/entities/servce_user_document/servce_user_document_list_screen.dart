import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:amcCarePlanner/account/login/login_repository.dart';
import 'package:amcCarePlanner/entities/servce_user_document/bloc/servce_user_document_bloc.dart';
import 'package:amcCarePlanner/entities/servce_user_document/servce_user_document_model.dart';
import 'package:amcCarePlanner/generated/l10n.dart';
import 'package:flutter/material.dart';
import 'package:amcCarePlanner/shared/widgets/drawer/bloc/drawer_bloc.dart';
import 'package:amcCarePlanner/shared/widgets/drawer/drawer_widget.dart';
import 'package:amcCarePlanner/shared/widgets/loading_indicator_widget.dart';
import 'package:amcCarePlanner/shared/models/entity_arguments.dart';
import 'servce_user_document_route.dart';

class ServceUserDocumentListScreen extends StatelessWidget {
    ServceUserDocumentListScreen({Key key}) : super(key: ServceUserDocumentRoutes.listScreenKey);
    final GlobalKey<ScaffoldState> scaffoldKey = new GlobalKey<ScaffoldState>();

  @override
  Widget build(BuildContext context) {
    return  BlocListener<ServceUserDocumentBloc, ServceUserDocumentState>(
      listener: (context, state) {
        if(state.deleteStatus == ServceUserDocumentDeleteStatus.ok) {
          Navigator.of(context).pop();
          scaffoldKey.currentState.showSnackBar(new SnackBar(
              content: new Text(S.of(context).pageEntitiesServceUserDocumentDeleteOk)
          ));
        }
      },
      child: Scaffold(
          key: scaffoldKey,
          appBar: AppBar(
            centerTitle: true,
    title:Text(S.of(context).pageEntitiesServceUserDocumentListTitle),
          ),
          body: SingleChildScrollView(
            padding: const EdgeInsets.all(15.0),
            child: BlocBuilder<ServceUserDocumentBloc, ServceUserDocumentState>(
              buildWhen: (previous, current) => previous.servceUserDocuments != current.servceUserDocuments,
              builder: (context, state) {
                return Visibility(
                  visible: state.servceUserDocumentStatusUI == ServceUserDocumentStatusUI.done,
                  replacement: LoadingIndicator(),
                  child: Column(children: <Widget>[
                    for (ServceUserDocument servceUserDocument in state.servceUserDocuments) servceUserDocumentCard(servceUserDocument, context)
                  ]),
                );
              }
            ),
          ),
        drawer: BlocProvider<DrawerBloc>(
            create: (context) => DrawerBloc(loginRepository: LoginRepository()),
            child: AmcCarePlannerDrawer()),
        floatingActionButton: FloatingActionButton(
          onPressed: () => Navigator.pushNamed(context, ServceUserDocumentRoutes.create),
          child: Icon(Icons.add, color: Theme.of(context).iconTheme.color,),
          backgroundColor: Theme.of(context).primaryColor,
        )
      ),
    );
  }

  Widget servceUserDocumentCard(ServceUserDocument servceUserDocument, BuildContext context) {
    ServceUserDocumentBloc servceUserDocumentBloc = BlocProvider.of<ServceUserDocumentBloc>(context);
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
                  title: Text('Document Name : ${servceUserDocument.documentName.toString()}'),
                  subtitle: Text('Document Number : ${servceUserDocument.documentNumber.toString()}'),
              trailing: DropdownButton(
                  icon: Icon(Icons.more_vert),
                  onChanged: (String newValue) {
                    switch (newValue) {
                      case "Edit":
                        Navigator.pushNamed(
                            context, ServceUserDocumentRoutes.edit,
                            arguments: EntityArguments(servceUserDocument.id));
                        break;
                      case "Delete":
                        showDialog(
                          context: context,
                          builder: (BuildContext context) {
                            return deleteDialog(
                              servceUserDocumentBloc, context, servceUserDocument.id);
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
                  context, ServceUserDocumentRoutes.view,
                  arguments: EntityArguments(servceUserDocument.id)),
            ),
          ],
        ),
      ),
    );
  }

  Widget deleteDialog(ServceUserDocumentBloc servceUserDocumentBloc, BuildContext context, int id) {
    return BlocProvider<ServceUserDocumentBloc>.value(
      value: servceUserDocumentBloc,
      child: AlertDialog(
        title: new Text(S.of(context).pageEntitiesServceUserDocumentDeletePopupTitle),
        content: new Text(S.of(context).entityActionConfirmDelete),
        actions: <Widget>[
          new FlatButton(
            child: new Text(S.of(context).entityActionConfirmDeleteYes),
            onPressed: () {
              servceUserDocumentBloc.add(DeleteServceUserDocumentById(id: id));
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
