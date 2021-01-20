import 'package:datetime_picker_formfield/datetime_picker_formfield.dart';
import 'package:intl/intl.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:amcCarePlanner/entities/system_setting/bloc/system_setting_bloc.dart';
import 'package:amcCarePlanner/generated/l10n.dart';
import 'package:flutter/material.dart';
import 'package:formz/formz.dart';
import 'package:amcCarePlanner/shared/repository/http_utils.dart';
import 'package:amcCarePlanner/entities/system_setting/system_setting_model.dart';
import 'system_setting_route.dart';

class SystemSettingUpdateScreen extends StatelessWidget {
  SystemSettingUpdateScreen({Key key}) : super(key: SystemSettingRoutes.editScreenKey);

  @override
  Widget build(BuildContext context) {
    return BlocListener<SystemSettingBloc, SystemSettingState>(
      listener: (context, state) {
        if(state.formStatus.isSubmissionSuccess){
          Navigator.pushNamed(context, SystemSettingRoutes.list);
        }
      },
      child: Scaffold(
          appBar: AppBar(
            centerTitle: true,
            title: BlocBuilder<SystemSettingBloc, SystemSettingState>(
                buildWhen: (previous, current) => previous.editMode != current.editMode,
                builder: (context, state) {
                String title = state.editMode == true ?S.of(context).pageEntitiesSystemSettingUpdateTitle:
S.of(context).pageEntitiesSystemSettingCreateTitle;
                 return Text(title);
                }
            ),
          ),
          body: SingleChildScrollView(
            padding: const EdgeInsets.all(15.0),
            child: Column(children: <Widget>[settingsForm(context)]),
          ),
      ),
    );
  }

  Widget settingsForm(BuildContext context) {
    return Form(
      child: Wrap(runSpacing: 15, children: <Widget>[
          fieldNameField(),
          fieldValueField(),
          defaultValueField(),
          settingEnabledField(),
          createdDateField(),
          updatedDateField(),
          clientIdField(),
          hasExtraDataField(),
        validationZone(),
        submit(context)
      ]),
    );
  }

      Widget fieldNameField() {
        return BlocBuilder<SystemSettingBloc, SystemSettingState>(
            buildWhen: (previous, current) => previous.fieldName != current.fieldName,
            builder: (context, state) {
              return TextFormField(
                  controller: context.bloc<SystemSettingBloc>().fieldNameController,
                  onChanged: (value) { context.bloc<SystemSettingBloc>()
                    .add(FieldNameChanged(fieldName:value)); },
                  keyboardType:TextInputType.text,                  decoration: InputDecoration(
                      labelText:S.of(context).pageEntitiesSystemSettingFieldNameField));
            }
        );
      }
      Widget fieldValueField() {
        return BlocBuilder<SystemSettingBloc, SystemSettingState>(
            buildWhen: (previous, current) => previous.fieldValue != current.fieldValue,
            builder: (context, state) {
              return TextFormField(
                  controller: context.bloc<SystemSettingBloc>().fieldValueController,
                  onChanged: (value) { context.bloc<SystemSettingBloc>()
                    .add(FieldValueChanged(fieldValue:value)); },
                  keyboardType:TextInputType.text,                  decoration: InputDecoration(
                      labelText:S.of(context).pageEntitiesSystemSettingFieldValueField));
            }
        );
      }
      Widget defaultValueField() {
        return BlocBuilder<SystemSettingBloc, SystemSettingState>(
            buildWhen: (previous, current) => previous.defaultValue != current.defaultValue,
            builder: (context, state) {
              return TextFormField(
                  controller: context.bloc<SystemSettingBloc>().defaultValueController,
                  onChanged: (value) { context.bloc<SystemSettingBloc>()
                    .add(DefaultValueChanged(defaultValue:value)); },
                  keyboardType:TextInputType.text,                  decoration: InputDecoration(
                      labelText:S.of(context).pageEntitiesSystemSettingDefaultValueField));
            }
        );
      }
        Widget settingEnabledField() {
          return BlocBuilder<SystemSettingBloc,SystemSettingState>(
              buildWhen: (previous, current) => previous.settingEnabled != current.settingEnabled,
              builder: (context, state) {
                return Padding(
                  padding: const EdgeInsets.only(left: 3.0),
                  child: Row(
                    mainAxisAlignment: MainAxisAlignment.spaceBetween,
                    children: <Widget>[
                      Text(S.of(context).pageEntitiesSystemSettingSettingEnabledField, style: Theme.of(context).textTheme.bodyText1,),
                      Switch(
                          value: state.settingEnabled.value,
                          onChanged: (value) { context.bloc<SystemSettingBloc>().add(SettingEnabledChanged(settingEnabled: value)); },
                          activeColor: Theme.of(context).primaryColor,),
                    ],
                  ),
                );
              });
        }
      Widget createdDateField() {
        return BlocBuilder<SystemSettingBloc, SystemSettingState>(
            buildWhen: (previous, current) => previous.createdDate != current.createdDate,
            builder: (context, state) {
              return DateTimeField(
                controller: context.bloc<SystemSettingBloc>().createdDateController,
                onChanged: (value) { context.bloc<SystemSettingBloc>().add(CreatedDateChanged(createdDate: value)); },
                format: DateFormat.yMMMMd(S.of(context).locale),
                keyboardType: TextInputType.datetime,
            decoration: InputDecoration(labelText:S.of(context).pageEntitiesSystemSettingCreatedDateField,),
                onShowPicker: (context, currentValue) {
                  return showDatePicker(
                      locale: Locale(S.of(context).locale),
                      context: context,
                      firstDate: DateTime(1950),
                      initialDate: currentValue ?? DateTime.now(),
                      lastDate: DateTime(2050));
                },
              );
            }
        );
      }
      Widget updatedDateField() {
        return BlocBuilder<SystemSettingBloc, SystemSettingState>(
            buildWhen: (previous, current) => previous.updatedDate != current.updatedDate,
            builder: (context, state) {
              return DateTimeField(
                controller: context.bloc<SystemSettingBloc>().updatedDateController,
                onChanged: (value) { context.bloc<SystemSettingBloc>().add(UpdatedDateChanged(updatedDate: value)); },
                format: DateFormat.yMMMMd(S.of(context).locale),
                keyboardType: TextInputType.datetime,
            decoration: InputDecoration(labelText:S.of(context).pageEntitiesSystemSettingUpdatedDateField,),
                onShowPicker: (context, currentValue) {
                  return showDatePicker(
                      locale: Locale(S.of(context).locale),
                      context: context,
                      firstDate: DateTime(1950),
                      initialDate: currentValue ?? DateTime.now(),
                      lastDate: DateTime(2050));
                },
              );
            }
        );
      }
      Widget clientIdField() {
        return BlocBuilder<SystemSettingBloc, SystemSettingState>(
            buildWhen: (previous, current) => previous.clientId != current.clientId,
            builder: (context, state) {
              return TextFormField(
                  controller: context.bloc<SystemSettingBloc>().clientIdController,
                  onChanged: (value) { context.bloc<SystemSettingBloc>()
                    .add(ClientIdChanged(clientId:int.parse(value))); },
                  keyboardType:TextInputType.number,                  decoration: InputDecoration(
                      labelText:S.of(context).pageEntitiesSystemSettingClientIdField));
            }
        );
      }
        Widget hasExtraDataField() {
          return BlocBuilder<SystemSettingBloc,SystemSettingState>(
              buildWhen: (previous, current) => previous.hasExtraData != current.hasExtraData,
              builder: (context, state) {
                return Padding(
                  padding: const EdgeInsets.only(left: 3.0),
                  child: Row(
                    mainAxisAlignment: MainAxisAlignment.spaceBetween,
                    children: <Widget>[
                      Text(S.of(context).pageEntitiesSystemSettingHasExtraDataField, style: Theme.of(context).textTheme.bodyText1,),
                      Switch(
                          value: state.hasExtraData.value,
                          onChanged: (value) { context.bloc<SystemSettingBloc>().add(HasExtraDataChanged(hasExtraData: value)); },
                          activeColor: Theme.of(context).primaryColor,),
                    ],
                  ),
                );
              });
        }


  Widget validationZone() {
    return BlocBuilder<SystemSettingBloc, SystemSettingState>(
        buildWhen:(previous, current) => previous.formStatus != current.formStatus,
        builder: (context, state) {
          return Visibility(
              visible: state.formStatus.isSubmissionFailure ||  state.formStatus.isSubmissionSuccess,
              child: Center(
                child: generateNotificationText(state, context),
              ));
        });
  }

  Widget generateNotificationText(SystemSettingState state, BuildContext context) {
    String notificationTranslated = '';
    MaterialColor notificationColors;

    if (state.generalNotificationKey.toString().compareTo(HttpUtils.errorServerKey) == 0) {
      notificationTranslated =S.of(context).genericErrorServer;
      notificationColors = Theme.of(context).errorColor;
    } else if (state.generalNotificationKey.toString().compareTo(HttpUtils.badRequestServerKey) == 0) {
      notificationTranslated =S.of(context).genericErrorBadRequest;
      notificationColors = Theme.of(context).errorColor;
    }

    return Text(
      notificationTranslated,
      textAlign: TextAlign.center,
      style: TextStyle(fontSize: Theme.of(context).textTheme.bodyText1.fontSize,
          color: notificationColors),
    );
  }

  submit(BuildContext context) {
    return BlocBuilder<SystemSettingBloc, SystemSettingState>(
        buildWhen: (previous, current) => previous.formStatus != current.formStatus,
        builder: (context, state) {
          String buttonLabel = state.editMode == true ?
S.of(context).entityActionEdit.toUpperCase():
S.of(context).entityActionCreate.toUpperCase();
          return RaisedButton(
            child: Container(
                width: MediaQuery.of(context).size.width,
                child: Center(
                  child: Visibility(
                    replacement: CircularProgressIndicator(value: null),
                    visible: !state.formStatus.isSubmissionInProgress,
                    child: Text(buttonLabel),
                  ),
                )),
            onPressed: state.formStatus.isValidated ? () => context.bloc<SystemSettingBloc>().add(SystemSettingFormSubmitted()) : null,
          );
        }
    );
  }
}
