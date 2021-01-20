import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:amcCarePlanner/account/login/login_repository.dart';
import 'package:amcCarePlanner/entities/power_of_attorney/bloc/power_of_attorney_bloc.dart';
import 'package:amcCarePlanner/entities/power_of_attorney/power_of_attorney_model.dart';
import 'package:amcCarePlanner/generated/l10n.dart';
import 'package:flutter/material.dart';
import 'package:amcCarePlanner/shared/widgets/drawer/bloc/drawer_bloc.dart';
import 'package:amcCarePlanner/shared/widgets/drawer/drawer_widget.dart';
import 'package:amcCarePlanner/shared/widgets/loading_indicator_widget.dart';
import 'package:amcCarePlanner/shared/models/entity_arguments.dart';
import 'power_of_attorney_route.dart';

class PowerOfAttorneyListScreen extends StatelessWidget {
    PowerOfAttorneyListScreen({Key key}) : super(key: PowerOfAttorneyRoutes.listScreenKey);
    final GlobalKey<ScaffoldState> scaffoldKey = new GlobalKey<ScaffoldState>();

  @override
  Widget build(BuildContext context) {
    return  BlocListener<PowerOfAttorneyBloc, PowerOfAttorneyState>(
      listener: (context, state) {
        if(state.deleteStatus == PowerOfAttorneyDeleteStatus.ok) {
          Navigator.of(context).pop();
          scaffoldKey.currentState.showSnackBar(new SnackBar(
              content: new Text(S.of(context).pageEntitiesPowerOfAttorneyDeleteOk)
          ));
        }
      },
      child: Scaffold(
          key: scaffoldKey,
          appBar: AppBar(
            centerTitle: true,
    title:Text(S.of(context).pageEntitiesPowerOfAttorneyListTitle),
          ),
          body: SingleChildScrollView(
            padding: const EdgeInsets.all(15.0),
            child: BlocBuilder<PowerOfAttorneyBloc, PowerOfAttorneyState>(
              buildWhen: (previous, current) => previous.powerOfAttorneys != current.powerOfAttorneys,
              builder: (context, state) {
                return Visibility(
                  visible: state.powerOfAttorneyStatusUI == PowerOfAttorneyStatusUI.done,
                  replacement: LoadingIndicator(),
                  child: Column(children: <Widget>[
                    for (PowerOfAttorney powerOfAttorney in state.powerOfAttorneys) powerOfAttorneyCard(powerOfAttorney, context)
                  ]),
                );
              }
            ),
          ),
        drawer: BlocProvider<DrawerBloc>(
            create: (context) => DrawerBloc(loginRepository: LoginRepository()),
            child: AmcCarePlannerDrawer()),
        floatingActionButton: FloatingActionButton(
          onPressed: () => Navigator.pushNamed(context, PowerOfAttorneyRoutes.create),
          child: Icon(Icons.add, color: Theme.of(context).iconTheme.color,),
          backgroundColor: Theme.of(context).primaryColor,
        )
      ),
    );
  }

  Widget powerOfAttorneyCard(PowerOfAttorney powerOfAttorney, BuildContext context) {
    PowerOfAttorneyBloc powerOfAttorneyBloc = BlocProvider.of<PowerOfAttorneyBloc>(context);
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
                  title: Text('Power Of Attorney Consent : ${powerOfAttorney.powerOfAttorneyConsent.toString()}'),
                  subtitle: Text('Health And Welfare : ${powerOfAttorney.healthAndWelfare.toString()}'),
              trailing: DropdownButton(
                  icon: Icon(Icons.more_vert),
                  onChanged: (String newValue) {
                    switch (newValue) {
                      case "Edit":
                        Navigator.pushNamed(
                            context, PowerOfAttorneyRoutes.edit,
                            arguments: EntityArguments(powerOfAttorney.id));
                        break;
                      case "Delete":
                        showDialog(
                          context: context,
                          builder: (BuildContext context) {
                            return deleteDialog(
                              powerOfAttorneyBloc, context, powerOfAttorney.id);
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
                  context, PowerOfAttorneyRoutes.view,
                  arguments: EntityArguments(powerOfAttorney.id)),
            ),
          ],
        ),
      ),
    );
  }

  Widget deleteDialog(PowerOfAttorneyBloc powerOfAttorneyBloc, BuildContext context, int id) {
    return BlocProvider<PowerOfAttorneyBloc>.value(
      value: powerOfAttorneyBloc,
      child: AlertDialog(
        title: new Text(S.of(context).pageEntitiesPowerOfAttorneyDeletePopupTitle),
        content: new Text(S.of(context).entityActionConfirmDelete),
        actions: <Widget>[
          new FlatButton(
            child: new Text(S.of(context).entityActionConfirmDeleteYes),
            onPressed: () {
              powerOfAttorneyBloc.add(DeletePowerOfAttorneyById(id: id));
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
