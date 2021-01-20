import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:amcCarePlanner/entities/system_setting/bloc/system_setting_bloc.dart';
import 'package:amcCarePlanner/entities/system_setting/system_setting_model.dart';
import 'package:amcCarePlanner/generated/l10n.dart';
import 'package:flutter/material.dart';
import 'package:intl/intl.dart';
import 'package:amcCarePlanner/shared/widgets/loading_indicator_widget.dart';
import 'system_setting_route.dart';

class SystemSettingViewScreen extends StatelessWidget {
  SystemSettingViewScreen({Key key}) : super(key: SystemSettingRoutes.createScreenKey);

  @override
  Widget build(BuildContext context) {
    return Scaffold(
          appBar: AppBar(
            centerTitle: true,
            title:Text(S.of(context).pageEntitiesSystemSettingViewTitle),
          ),
          body: SingleChildScrollView(
            padding: const EdgeInsets.all(15.0),
            child: BlocBuilder<SystemSettingBloc, SystemSettingState>(
              buildWhen: (previous, current) => previous.loadedSystemSetting != current.loadedSystemSetting,
              builder: (context, state) {
                return Visibility(
                  visible: state.systemSettingStatusUI == SystemSettingStatusUI.done,
                  replacement: LoadingIndicator(),
                  child: Column(children: <Widget>[
                    systemSettingCard(state.loadedSystemSetting, context)
                  ]),
                );
              }
            ),
          ),
        floatingActionButton: FloatingActionButton(
          onPressed: () => Navigator.pushNamed(context, SystemSettingRoutes.create),
          child: Icon(Icons.add, color: Theme.of(context).iconTheme.color,),
          backgroundColor: Theme.of(context).primaryColor,
        )
    );
  }

  Widget systemSettingCard(SystemSetting systemSetting, BuildContext context) {
    return Card(
      child: Container(
        padding: EdgeInsets.all(20),
        width: MediaQuery.of(context).size.width * 0.90,
        child: Column(
          mainAxisAlignment: MainAxisAlignment.spaceEvenly,
          crossAxisAlignment: CrossAxisAlignment.start,
          children: <Widget>[
            Text('Id : ' + systemSetting.id.toString(), style: Theme.of(context).textTheme.bodyText1,),
                Text('Field Name : ' + systemSetting.fieldName.toString(), style: Theme.of(context).textTheme.bodyText1,),
                Text('Field Value : ' + systemSetting.fieldValue.toString(), style: Theme.of(context).textTheme.bodyText1,),
                Text('Default Value : ' + systemSetting.defaultValue.toString(), style: Theme.of(context).textTheme.bodyText1,),
                Text('Setting Enabled : ' + systemSetting.settingEnabled.toString(), style: Theme.of(context).textTheme.bodyText1,),
              Text('Created Date : ' + (systemSetting?.createdDate != null ? DateFormat.yMMMMd(S.of(context).locale).format(systemSetting.createdDate) : ''), style: Theme.of(context).textTheme.bodyText1,),
              Text('Updated Date : ' + (systemSetting?.updatedDate != null ? DateFormat.yMMMMd(S.of(context).locale).format(systemSetting.updatedDate) : ''), style: Theme.of(context).textTheme.bodyText1,),
                Text('Client Id : ' + systemSetting.clientId.toString(), style: Theme.of(context).textTheme.bodyText1,),
                Text('Has Extra Data : ' + systemSetting.hasExtraData.toString(), style: Theme.of(context).textTheme.bodyText1,),
          ],
        ),
      ),
    );
  }
}
