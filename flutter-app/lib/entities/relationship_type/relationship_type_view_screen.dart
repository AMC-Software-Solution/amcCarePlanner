import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:amcCarePlanner/entities/relationship_type/bloc/relationship_type_bloc.dart';
import 'package:amcCarePlanner/entities/relationship_type/relationship_type_model.dart';
import 'package:amcCarePlanner/generated/l10n.dart';
import 'package:flutter/material.dart';
import 'package:amcCarePlanner/shared/widgets/loading_indicator_widget.dart';
import 'relationship_type_route.dart';

class RelationshipTypeViewScreen extends StatelessWidget {
  RelationshipTypeViewScreen({Key key}) : super(key: RelationshipTypeRoutes.createScreenKey);

  @override
  Widget build(BuildContext context) {
    return Scaffold(
          appBar: AppBar(
            centerTitle: true,
            title:Text(S.of(context).pageEntitiesRelationshipTypeViewTitle),
          ),
          body: SingleChildScrollView(
            padding: const EdgeInsets.all(15.0),
            child: BlocBuilder<RelationshipTypeBloc, RelationshipTypeState>(
              buildWhen: (previous, current) => previous.loadedRelationshipType != current.loadedRelationshipType,
              builder: (context, state) {
                return Visibility(
                  visible: state.relationshipTypeStatusUI == RelationshipTypeStatusUI.done,
                  replacement: LoadingIndicator(),
                  child: Column(children: <Widget>[
                    relationshipTypeCard(state.loadedRelationshipType, context)
                  ]),
                );
              }
            ),
          ),
        floatingActionButton: FloatingActionButton(
          onPressed: () => Navigator.pushNamed(context, RelationshipTypeRoutes.create),
          child: Icon(Icons.add, color: Theme.of(context).iconTheme.color,),
          backgroundColor: Theme.of(context).primaryColor,
        )
    );
  }

  Widget relationshipTypeCard(RelationshipType relationshipType, BuildContext context) {
    return Card(
      child: Container(
        padding: EdgeInsets.all(20),
        width: MediaQuery.of(context).size.width * 0.90,
        child: Column(
          mainAxisAlignment: MainAxisAlignment.spaceEvenly,
          crossAxisAlignment: CrossAxisAlignment.start,
          children: <Widget>[
            Text('Id : ' + relationshipType.id.toString(), style: Theme.of(context).textTheme.bodyText1,),
                Text('Relation Type : ' + relationshipType.relationType.toString(), style: Theme.of(context).textTheme.bodyText1,),
                Text('Description : ' + relationshipType.description.toString(), style: Theme.of(context).textTheme.bodyText1,),
                Text('Client Id : ' + relationshipType.clientId.toString(), style: Theme.of(context).textTheme.bodyText1,),
                Text('Has Extra Data : ' + relationshipType.hasExtraData.toString(), style: Theme.of(context).textTheme.bodyText1,),
          ],
        ),
      ),
    );
  }
}
