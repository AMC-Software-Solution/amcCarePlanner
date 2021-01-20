import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:amcCarePlanner/account/login/login_repository.dart';
import 'package:amcCarePlanner/entities/extra_data/bloc/extra_data_bloc.dart';
import 'package:amcCarePlanner/entities/extra_data/extra_data_model.dart';
import 'package:amcCarePlanner/generated/l10n.dart';
import 'package:flutter/material.dart';
import 'package:amcCarePlanner/shared/widgets/drawer/bloc/drawer_bloc.dart';
import 'package:amcCarePlanner/shared/widgets/drawer/drawer_widget.dart';
import 'package:amcCarePlanner/shared/widgets/loading_indicator_widget.dart';
import 'package:amcCarePlanner/shared/models/entity_arguments.dart';
import 'extra_data_route.dart';

class ExtraDataListScreen extends StatelessWidget {
    ExtraDataListScreen({Key key}) : super(key: ExtraDataRoutes.listScreenKey);
    final GlobalKey<ScaffoldState> scaffoldKey = new GlobalKey<ScaffoldState>();

  @override
  Widget build(BuildContext context) {
    return  BlocListener<ExtraDataBloc, ExtraDataState>(
      listener: (context, state) {
        if(state.deleteStatus == ExtraDataDeleteStatus.ok) {
          Navigator.of(context).pop();
          scaffoldKey.currentState.showSnackBar(new SnackBar(
              content: new Text(S.of(context).pageEntitiesExtraDataDeleteOk)
          ));
        }
      },
      child: Scaffold(
          key: scaffoldKey,
          appBar: AppBar(
            centerTitle: true,
    title:Text(S.of(context).pageEntitiesExtraDataListTitle),
          ),
          body: SingleChildScrollView(
            padding: const EdgeInsets.all(15.0),
            child: BlocBuilder<ExtraDataBloc, ExtraDataState>(
              buildWhen: (previous, current) => previous.extraData != current.extraData,
              builder: (context, state) {
                return Visibility(
                  visible: state.extraDataStatusUI == ExtraDataStatusUI.done,
                  replacement: LoadingIndicator(),
                  child: Column(children: <Widget>[
                    for (ExtraData extraData in state.extraData) extraDataCard(extraData, context)
                  ]),
                );
              }
            ),
          ),
        drawer: BlocProvider<DrawerBloc>(
            create: (context) => DrawerBloc(loginRepository: LoginRepository()),
            child: AmcCarePlannerDrawer()),
        floatingActionButton: FloatingActionButton(
          onPressed: () => Navigator.pushNamed(context, ExtraDataRoutes.create),
          child: Icon(Icons.add, color: Theme.of(context).iconTheme.color,),
          backgroundColor: Theme.of(context).primaryColor,
        )
      ),
    );
  }

  Widget extraDataCard(ExtraData extraData, BuildContext context) {
    ExtraDataBloc extraDataBloc = BlocProvider.of<ExtraDataBloc>(context);
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
                  title: Text('Entity Name : ${extraData.entityName.toString()}'),
                  subtitle: Text('Entity Id : ${extraData.entityId.toString()}'),
              trailing: DropdownButton(
                  icon: Icon(Icons.more_vert),
                  onChanged: (String newValue) {
                    switch (newValue) {
                      case "Edit":
                        Navigator.pushNamed(
                            context, ExtraDataRoutes.edit,
                            arguments: EntityArguments(extraData.id));
                        break;
                      case "Delete":
                        showDialog(
                          context: context,
                          builder: (BuildContext context) {
                            return deleteDialog(
                              extraDataBloc, context, extraData.id);
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
                  context, ExtraDataRoutes.view,
                  arguments: EntityArguments(extraData.id)),
            ),
          ],
        ),
      ),
    );
  }

  Widget deleteDialog(ExtraDataBloc extraDataBloc, BuildContext context, int id) {
    return BlocProvider<ExtraDataBloc>.value(
      value: extraDataBloc,
      child: AlertDialog(
        title: new Text(S.of(context).pageEntitiesExtraDataDeletePopupTitle),
        content: new Text(S.of(context).entityActionConfirmDelete),
        actions: <Widget>[
          new FlatButton(
            child: new Text(S.of(context).entityActionConfirmDeleteYes),
            onPressed: () {
              extraDataBloc.add(DeleteExtraDataById(id: id));
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
