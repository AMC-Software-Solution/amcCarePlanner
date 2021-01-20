import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:amcCarePlanner/account/login/login_repository.dart';
import 'package:amcCarePlanner/entities/system_setting/bloc/system_setting_bloc.dart';
import 'package:amcCarePlanner/entities/system_setting/system_setting_model.dart';
import 'package:amcCarePlanner/generated/l10n.dart';
import 'package:flutter/material.dart';
import 'package:amcCarePlanner/shared/widgets/drawer/bloc/drawer_bloc.dart';
import 'package:amcCarePlanner/shared/widgets/drawer/drawer_widget.dart';
import 'package:amcCarePlanner/shared/widgets/loading_indicator_widget.dart';
import 'package:amcCarePlanner/shared/models/entity_arguments.dart';
import 'system_setting_route.dart';

class SystemSettingListScreen extends StatelessWidget {
    SystemSettingListScreen({Key key}) : super(key: SystemSettingRoutes.listScreenKey);
    final GlobalKey<ScaffoldState> scaffoldKey = new GlobalKey<ScaffoldState>();

  @override
  Widget build(BuildContext context) {
    return  BlocListener<SystemSettingBloc, SystemSettingState>(
      listener: (context, state) {
        if(state.deleteStatus == SystemSettingDeleteStatus.ok) {
          Navigator.of(context).pop();
          scaffoldKey.currentState.showSnackBar(new SnackBar(
              content: new Text(S.of(context).pageEntitiesSystemSettingDeleteOk)
          ));
        }
      },
      child: Scaffold(
          key: scaffoldKey,
          appBar: AppBar(
            centerTitle: true,
    title:Text(S.of(context).pageEntitiesSystemSettingListTitle),
          ),
          body: SingleChildScrollView(
            padding: const EdgeInsets.all(15.0),
            child: BlocBuilder<SystemSettingBloc, SystemSettingState>(
              buildWhen: (previous, current) => previous.systemSettings != current.systemSettings,
              builder: (context, state) {
                return Visibility(
                  visible: state.systemSettingStatusUI == SystemSettingStatusUI.done,
                  replacement: LoadingIndicator(),
                  child: Column(children: <Widget>[
                    for (SystemSetting systemSetting in state.systemSettings) systemSettingCard(systemSetting, context)
                  ]),
                );
              }
            ),
          ),
        drawer: BlocProvider<DrawerBloc>(
            create: (context) => DrawerBloc(loginRepository: LoginRepository()),
            child: AmcCarePlannerDrawer()),
        floatingActionButton: FloatingActionButton(
          onPressed: () => Navigator.pushNamed(context, SystemSettingRoutes.create),
          child: Icon(Icons.add, color: Theme.of(context).iconTheme.color,),
          backgroundColor: Theme.of(context).primaryColor,
        )
      ),
    );
  }

  Widget systemSettingCard(SystemSetting systemSetting, BuildContext context) {
    SystemSettingBloc systemSettingBloc = BlocProvider.of<SystemSettingBloc>(context);
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
                  title: Text('Field Name : ${systemSetting.fieldName.toString()}'),
                  subtitle: Text('Field Value : ${systemSetting.fieldValue.toString()}'),
              trailing: DropdownButton(
                  icon: Icon(Icons.more_vert),
                  onChanged: (String newValue) {
                    switch (newValue) {
                      case "Edit":
                        Navigator.pushNamed(
                            context, SystemSettingRoutes.edit,
                            arguments: EntityArguments(systemSetting.id));
                        break;
                      case "Delete":
                        showDialog(
                          context: context,
                          builder: (BuildContext context) {
                            return deleteDialog(
                              systemSettingBloc, context, systemSetting.id);
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
                  context, SystemSettingRoutes.view,
                  arguments: EntityArguments(systemSetting.id)),
            ),
          ],
        ),
      ),
    );
  }

  Widget deleteDialog(SystemSettingBloc systemSettingBloc, BuildContext context, int id) {
    return BlocProvider<SystemSettingBloc>.value(
      value: systemSettingBloc,
      child: AlertDialog(
        title: new Text(S.of(context).pageEntitiesSystemSettingDeletePopupTitle),
        content: new Text(S.of(context).entityActionConfirmDelete),
        actions: <Widget>[
          new FlatButton(
            child: new Text(S.of(context).entityActionConfirmDeleteYes),
            onPressed: () {
              systemSettingBloc.add(DeleteSystemSettingById(id: id));
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
