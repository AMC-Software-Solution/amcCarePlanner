import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:amcCarePlanner/account/login/login_repository.dart';
import 'package:amcCarePlanner/entities/relationship_type/bloc/relationship_type_bloc.dart';
import 'package:amcCarePlanner/entities/relationship_type/relationship_type_model.dart';
import 'package:amcCarePlanner/generated/l10n.dart';
import 'package:flutter/material.dart';
import 'package:amcCarePlanner/shared/widgets/drawer/bloc/drawer_bloc.dart';
import 'package:amcCarePlanner/shared/widgets/drawer/drawer_widget.dart';
import 'package:amcCarePlanner/shared/widgets/loading_indicator_widget.dart';
import 'package:amcCarePlanner/shared/models/entity_arguments.dart';
import 'relationship_type_route.dart';

class RelationshipTypeListScreen extends StatelessWidget {
    RelationshipTypeListScreen({Key key}) : super(key: RelationshipTypeRoutes.listScreenKey);
    final GlobalKey<ScaffoldState> scaffoldKey = new GlobalKey<ScaffoldState>();

  @override
  Widget build(BuildContext context) {
    return  BlocListener<RelationshipTypeBloc, RelationshipTypeState>(
      listener: (context, state) {
        if(state.deleteStatus == RelationshipTypeDeleteStatus.ok) {
          Navigator.of(context).pop();
          scaffoldKey.currentState.showSnackBar(new SnackBar(
              content: new Text(S.of(context).pageEntitiesRelationshipTypeDeleteOk)
          ));
        }
      },
      child: Scaffold(
          key: scaffoldKey,
          appBar: AppBar(
            centerTitle: true,
    title:Text(S.of(context).pageEntitiesRelationshipTypeListTitle),
          ),
          body: SingleChildScrollView(
            padding: const EdgeInsets.all(15.0),
            child: BlocBuilder<RelationshipTypeBloc, RelationshipTypeState>(
              buildWhen: (previous, current) => previous.relationshipTypes != current.relationshipTypes,
              builder: (context, state) {
                return Visibility(
                  visible: state.relationshipTypeStatusUI == RelationshipTypeStatusUI.done,
                  replacement: LoadingIndicator(),
                  child: Column(children: <Widget>[
                    for (RelationshipType relationshipType in state.relationshipTypes) relationshipTypeCard(relationshipType, context)
                  ]),
                );
              }
            ),
          ),
        drawer: BlocProvider<DrawerBloc>(
            create: (context) => DrawerBloc(loginRepository: LoginRepository()),
            child: AmcCarePlannerDrawer()),
        floatingActionButton: FloatingActionButton(
          onPressed: () => Navigator.pushNamed(context, RelationshipTypeRoutes.create),
          child: Icon(Icons.add, color: Theme.of(context).iconTheme.color,),
          backgroundColor: Theme.of(context).primaryColor,
        )
      ),
    );
  }

  Widget relationshipTypeCard(RelationshipType relationshipType, BuildContext context) {
    RelationshipTypeBloc relationshipTypeBloc = BlocProvider.of<RelationshipTypeBloc>(context);
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
                  title: Text('Relation Type : ${relationshipType.relationType.toString()}'),
                  subtitle: Text('Description : ${relationshipType.description.toString()}'),
              trailing: DropdownButton(
                  icon: Icon(Icons.more_vert),
                  onChanged: (String newValue) {
                    switch (newValue) {
                      case "Edit":
                        Navigator.pushNamed(
                            context, RelationshipTypeRoutes.edit,
                            arguments: EntityArguments(relationshipType.id));
                        break;
                      case "Delete":
                        showDialog(
                          context: context,
                          builder: (BuildContext context) {
                            return deleteDialog(
                              relationshipTypeBloc, context, relationshipType.id);
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
                  context, RelationshipTypeRoutes.view,
                  arguments: EntityArguments(relationshipType.id)),
            ),
          ],
        ),
      ),
    );
  }

  Widget deleteDialog(RelationshipTypeBloc relationshipTypeBloc, BuildContext context, int id) {
    return BlocProvider<RelationshipTypeBloc>.value(
      value: relationshipTypeBloc,
      child: AlertDialog(
        title: new Text(S.of(context).pageEntitiesRelationshipTypeDeletePopupTitle),
        content: new Text(S.of(context).entityActionConfirmDelete),
        actions: <Widget>[
          new FlatButton(
            child: new Text(S.of(context).entityActionConfirmDeleteYes),
            onPressed: () {
              relationshipTypeBloc.add(DeleteRelationshipTypeById(id: id));
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
