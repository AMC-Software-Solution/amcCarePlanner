import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:amcCarePlanner/account/login/login_repository.dart';
import 'package:amcCarePlanner/entities/eligibility_type/bloc/eligibility_type_bloc.dart';
import 'package:amcCarePlanner/entities/eligibility_type/eligibility_type_model.dart';
import 'package:amcCarePlanner/generated/l10n.dart';
import 'package:flutter/material.dart';
import 'package:amcCarePlanner/shared/widgets/drawer/bloc/drawer_bloc.dart';
import 'package:amcCarePlanner/shared/widgets/drawer/drawer_widget.dart';
import 'package:amcCarePlanner/shared/widgets/loading_indicator_widget.dart';
import 'package:amcCarePlanner/shared/models/entity_arguments.dart';
import 'eligibility_type_route.dart';

class EligibilityTypeListScreen extends StatelessWidget {
    EligibilityTypeListScreen({Key key}) : super(key: EligibilityTypeRoutes.listScreenKey);
    final GlobalKey<ScaffoldState> scaffoldKey = new GlobalKey<ScaffoldState>();

  @override
  Widget build(BuildContext context) {
    return  BlocListener<EligibilityTypeBloc, EligibilityTypeState>(
      listener: (context, state) {
        if(state.deleteStatus == EligibilityTypeDeleteStatus.ok) {
          Navigator.of(context).pop();
          scaffoldKey.currentState.showSnackBar(new SnackBar(
              content: new Text(S.of(context).pageEntitiesEligibilityTypeDeleteOk)
          ));
        }
      },
      child: Scaffold(
          key: scaffoldKey,
          appBar: AppBar(
            centerTitle: true,
    title:Text(S.of(context).pageEntitiesEligibilityTypeListTitle),
          ),
          body: SingleChildScrollView(
            padding: const EdgeInsets.all(15.0),
            child: BlocBuilder<EligibilityTypeBloc, EligibilityTypeState>(
              buildWhen: (previous, current) => previous.eligibilityTypes != current.eligibilityTypes,
              builder: (context, state) {
                return Visibility(
                  visible: state.eligibilityTypeStatusUI == EligibilityTypeStatusUI.done,
                  replacement: LoadingIndicator(),
                  child: Column(children: <Widget>[
                    for (EligibilityType eligibilityType in state.eligibilityTypes) eligibilityTypeCard(eligibilityType, context)
                  ]),
                );
              }
            ),
          ),
        drawer: BlocProvider<DrawerBloc>(
            create: (context) => DrawerBloc(loginRepository: LoginRepository()),
            child: AmcCarePlannerDrawer()),
        floatingActionButton: FloatingActionButton(
          onPressed: () => Navigator.pushNamed(context, EligibilityTypeRoutes.create),
          child: Icon(Icons.add, color: Theme.of(context).iconTheme.color,),
          backgroundColor: Theme.of(context).primaryColor,
        )
      ),
    );
  }

  Widget eligibilityTypeCard(EligibilityType eligibilityType, BuildContext context) {
    EligibilityTypeBloc eligibilityTypeBloc = BlocProvider.of<EligibilityTypeBloc>(context);
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
                  title: Text('Eligibility Type : ${eligibilityType.eligibilityType.toString()}'),
                  subtitle: Text('Description : ${eligibilityType.description.toString()}'),
              trailing: DropdownButton(
                  icon: Icon(Icons.more_vert),
                  onChanged: (String newValue) {
                    switch (newValue) {
                      case "Edit":
                        Navigator.pushNamed(
                            context, EligibilityTypeRoutes.edit,
                            arguments: EntityArguments(eligibilityType.id));
                        break;
                      case "Delete":
                        showDialog(
                          context: context,
                          builder: (BuildContext context) {
                            return deleteDialog(
                              eligibilityTypeBloc, context, eligibilityType.id);
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
                  context, EligibilityTypeRoutes.view,
                  arguments: EntityArguments(eligibilityType.id)),
            ),
          ],
        ),
      ),
    );
  }

  Widget deleteDialog(EligibilityTypeBloc eligibilityTypeBloc, BuildContext context, int id) {
    return BlocProvider<EligibilityTypeBloc>.value(
      value: eligibilityTypeBloc,
      child: AlertDialog(
        title: new Text(S.of(context).pageEntitiesEligibilityTypeDeletePopupTitle),
        content: new Text(S.of(context).entityActionConfirmDelete),
        actions: <Widget>[
          new FlatButton(
            child: new Text(S.of(context).entityActionConfirmDeleteYes),
            onPressed: () {
              eligibilityTypeBloc.add(DeleteEligibilityTypeById(id: id));
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
