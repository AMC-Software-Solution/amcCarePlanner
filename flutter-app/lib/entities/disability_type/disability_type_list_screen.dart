import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:amcCarePlanner/account/login/login_repository.dart';
import 'package:amcCarePlanner/entities/disability_type/bloc/disability_type_bloc.dart';
import 'package:amcCarePlanner/entities/disability_type/disability_type_model.dart';
import 'package:amcCarePlanner/generated/l10n.dart';
import 'package:flutter/material.dart';
import 'package:amcCarePlanner/shared/widgets/drawer/bloc/drawer_bloc.dart';
import 'package:amcCarePlanner/shared/widgets/drawer/drawer_widget.dart';
import 'package:amcCarePlanner/shared/widgets/loading_indicator_widget.dart';
import 'package:amcCarePlanner/shared/models/entity_arguments.dart';
import 'disability_type_route.dart';

class DisabilityTypeListScreen extends StatelessWidget {
    DisabilityTypeListScreen({Key key}) : super(key: DisabilityTypeRoutes.listScreenKey);
    final GlobalKey<ScaffoldState> scaffoldKey = new GlobalKey<ScaffoldState>();

  @override
  Widget build(BuildContext context) {
    return  BlocListener<DisabilityTypeBloc, DisabilityTypeState>(
      listener: (context, state) {
        if(state.deleteStatus == DisabilityTypeDeleteStatus.ok) {
          Navigator.of(context).pop();
          scaffoldKey.currentState.showSnackBar(new SnackBar(
              content: new Text(S.of(context).pageEntitiesDisabilityTypeDeleteOk)
          ));
        }
      },
      child: Scaffold(
          key: scaffoldKey,
          appBar: AppBar(
            centerTitle: true,
    title:Text(S.of(context).pageEntitiesDisabilityTypeListTitle),
          ),
          body: SingleChildScrollView(
            padding: const EdgeInsets.all(15.0),
            child: BlocBuilder<DisabilityTypeBloc, DisabilityTypeState>(
              buildWhen: (previous, current) => previous.disabilityTypes != current.disabilityTypes,
              builder: (context, state) {
                return Visibility(
                  visible: state.disabilityTypeStatusUI == DisabilityTypeStatusUI.done,
                  replacement: LoadingIndicator(),
                  child: Column(children: <Widget>[
                    for (DisabilityType disabilityType in state.disabilityTypes) disabilityTypeCard(disabilityType, context)
                  ]),
                );
              }
            ),
          ),
        drawer: BlocProvider<DrawerBloc>(
            create: (context) => DrawerBloc(loginRepository: LoginRepository()),
            child: AmcCarePlannerDrawer()),
        floatingActionButton: FloatingActionButton(
          onPressed: () => Navigator.pushNamed(context, DisabilityTypeRoutes.create),
          child: Icon(Icons.add, color: Theme.of(context).iconTheme.color,),
          backgroundColor: Theme.of(context).primaryColor,
        )
      ),
    );
  }

  Widget disabilityTypeCard(DisabilityType disabilityType, BuildContext context) {
    DisabilityTypeBloc disabilityTypeBloc = BlocProvider.of<DisabilityTypeBloc>(context);
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
                  title: Text('Disability : ${disabilityType.disability.toString()}'),
                  subtitle: Text('Description : ${disabilityType.description.toString()}'),
              trailing: DropdownButton(
                  icon: Icon(Icons.more_vert),
                  onChanged: (String newValue) {
                    switch (newValue) {
                      case "Edit":
                        Navigator.pushNamed(
                            context, DisabilityTypeRoutes.edit,
                            arguments: EntityArguments(disabilityType.id));
                        break;
                      case "Delete":
                        showDialog(
                          context: context,
                          builder: (BuildContext context) {
                            return deleteDialog(
                              disabilityTypeBloc, context, disabilityType.id);
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
                  context, DisabilityTypeRoutes.view,
                  arguments: EntityArguments(disabilityType.id)),
            ),
          ],
        ),
      ),
    );
  }

  Widget deleteDialog(DisabilityTypeBloc disabilityTypeBloc, BuildContext context, int id) {
    return BlocProvider<DisabilityTypeBloc>.value(
      value: disabilityTypeBloc,
      child: AlertDialog(
        title: new Text(S.of(context).pageEntitiesDisabilityTypeDeletePopupTitle),
        content: new Text(S.of(context).entityActionConfirmDelete),
        actions: <Widget>[
          new FlatButton(
            child: new Text(S.of(context).entityActionConfirmDeleteYes),
            onPressed: () {
              disabilityTypeBloc.add(DeleteDisabilityTypeById(id: id));
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
