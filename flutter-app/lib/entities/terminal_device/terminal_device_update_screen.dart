import 'package:datetime_picker_formfield/datetime_picker_formfield.dart';
import 'package:intl/intl.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:amcCarePlanner/entities/terminal_device/bloc/terminal_device_bloc.dart';
import 'package:amcCarePlanner/generated/l10n.dart';
import 'package:flutter/material.dart';
import 'package:formz/formz.dart';
import 'package:amcCarePlanner/shared/repository/http_utils.dart';
import 'package:amcCarePlanner/entities/terminal_device/terminal_device_model.dart';
import 'terminal_device_route.dart';

class TerminalDeviceUpdateScreen extends StatelessWidget {
  TerminalDeviceUpdateScreen({Key key}) : super(key: TerminalDeviceRoutes.editScreenKey);

  @override
  Widget build(BuildContext context) {
    return BlocListener<TerminalDeviceBloc, TerminalDeviceState>(
      listener: (context, state) {
        if(state.formStatus.isSubmissionSuccess){
          Navigator.pushNamed(context, TerminalDeviceRoutes.list);
        }
      },
      child: Scaffold(
          appBar: AppBar(
            centerTitle: true,
            title: BlocBuilder<TerminalDeviceBloc, TerminalDeviceState>(
                buildWhen: (previous, current) => previous.editMode != current.editMode,
                builder: (context, state) {
                String title = state.editMode == true ?S.of(context).pageEntitiesTerminalDeviceUpdateTitle:
S.of(context).pageEntitiesTerminalDeviceCreateTitle;
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
          deviceNameField(),
          deviceModelField(),
          registeredDateField(),
          imeiField(),
          simNumberField(),
          userStartedUsingFromField(),
          deviceOnLocationFromField(),
          operatingSystemField(),
          noteField(),
          ownerEntityIdField(),
          ownerEntityNameField(),
          createdDateField(),
          lastUpdatedDateField(),
          clientIdField(),
          hasExtraDataField(),
        validationZone(),
        submit(context)
      ]),
    );
  }

      Widget deviceNameField() {
        return BlocBuilder<TerminalDeviceBloc, TerminalDeviceState>(
            buildWhen: (previous, current) => previous.deviceName != current.deviceName,
            builder: (context, state) {
              return TextFormField(
                  controller: context.bloc<TerminalDeviceBloc>().deviceNameController,
                  onChanged: (value) { context.bloc<TerminalDeviceBloc>()
                    .add(DeviceNameChanged(deviceName:value)); },
                  keyboardType:TextInputType.text,                  decoration: InputDecoration(
                      labelText:S.of(context).pageEntitiesTerminalDeviceDeviceNameField));
            }
        );
      }
      Widget deviceModelField() {
        return BlocBuilder<TerminalDeviceBloc, TerminalDeviceState>(
            buildWhen: (previous, current) => previous.deviceModel != current.deviceModel,
            builder: (context, state) {
              return TextFormField(
                  controller: context.bloc<TerminalDeviceBloc>().deviceModelController,
                  onChanged: (value) { context.bloc<TerminalDeviceBloc>()
                    .add(DeviceModelChanged(deviceModel:value)); },
                  keyboardType:TextInputType.text,                  decoration: InputDecoration(
                      labelText:S.of(context).pageEntitiesTerminalDeviceDeviceModelField));
            }
        );
      }
      Widget registeredDateField() {
        return BlocBuilder<TerminalDeviceBloc, TerminalDeviceState>(
            buildWhen: (previous, current) => previous.registeredDate != current.registeredDate,
            builder: (context, state) {
              return DateTimeField(
                controller: context.bloc<TerminalDeviceBloc>().registeredDateController,
                onChanged: (value) { context.bloc<TerminalDeviceBloc>().add(RegisteredDateChanged(registeredDate: value)); },
                format: DateFormat.yMMMMd(S.of(context).locale),
                keyboardType: TextInputType.datetime,
            decoration: InputDecoration(labelText:S.of(context).pageEntitiesTerminalDeviceRegisteredDateField,),
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
      Widget imeiField() {
        return BlocBuilder<TerminalDeviceBloc, TerminalDeviceState>(
            buildWhen: (previous, current) => previous.imei != current.imei,
            builder: (context, state) {
              return TextFormField(
                  controller: context.bloc<TerminalDeviceBloc>().imeiController,
                  onChanged: (value) { context.bloc<TerminalDeviceBloc>()
                    .add(ImeiChanged(imei:value)); },
                  keyboardType:TextInputType.text,                  decoration: InputDecoration(
                      labelText:S.of(context).pageEntitiesTerminalDeviceImeiField));
            }
        );
      }
      Widget simNumberField() {
        return BlocBuilder<TerminalDeviceBloc, TerminalDeviceState>(
            buildWhen: (previous, current) => previous.simNumber != current.simNumber,
            builder: (context, state) {
              return TextFormField(
                  controller: context.bloc<TerminalDeviceBloc>().simNumberController,
                  onChanged: (value) { context.bloc<TerminalDeviceBloc>()
                    .add(SimNumberChanged(simNumber:value)); },
                  keyboardType:TextInputType.text,                  decoration: InputDecoration(
                      labelText:S.of(context).pageEntitiesTerminalDeviceSimNumberField));
            }
        );
      }
      Widget userStartedUsingFromField() {
        return BlocBuilder<TerminalDeviceBloc, TerminalDeviceState>(
            buildWhen: (previous, current) => previous.userStartedUsingFrom != current.userStartedUsingFrom,
            builder: (context, state) {
              return DateTimeField(
                controller: context.bloc<TerminalDeviceBloc>().userStartedUsingFromController,
                onChanged: (value) { context.bloc<TerminalDeviceBloc>().add(UserStartedUsingFromChanged(userStartedUsingFrom: value)); },
                format: DateFormat.yMMMMd(S.of(context).locale),
                keyboardType: TextInputType.datetime,
            decoration: InputDecoration(labelText:S.of(context).pageEntitiesTerminalDeviceUserStartedUsingFromField,),
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
      Widget deviceOnLocationFromField() {
        return BlocBuilder<TerminalDeviceBloc, TerminalDeviceState>(
            buildWhen: (previous, current) => previous.deviceOnLocationFrom != current.deviceOnLocationFrom,
            builder: (context, state) {
              return DateTimeField(
                controller: context.bloc<TerminalDeviceBloc>().deviceOnLocationFromController,
                onChanged: (value) { context.bloc<TerminalDeviceBloc>().add(DeviceOnLocationFromChanged(deviceOnLocationFrom: value)); },
                format: DateFormat.yMMMMd(S.of(context).locale),
                keyboardType: TextInputType.datetime,
            decoration: InputDecoration(labelText:S.of(context).pageEntitiesTerminalDeviceDeviceOnLocationFromField,),
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
      Widget operatingSystemField() {
        return BlocBuilder<TerminalDeviceBloc, TerminalDeviceState>(
            buildWhen: (previous, current) => previous.operatingSystem != current.operatingSystem,
            builder: (context, state) {
              return TextFormField(
                  controller: context.bloc<TerminalDeviceBloc>().operatingSystemController,
                  onChanged: (value) { context.bloc<TerminalDeviceBloc>()
                    .add(OperatingSystemChanged(operatingSystem:value)); },
                  keyboardType:TextInputType.text,                  decoration: InputDecoration(
                      labelText:S.of(context).pageEntitiesTerminalDeviceOperatingSystemField));
            }
        );
      }
      Widget noteField() {
        return BlocBuilder<TerminalDeviceBloc, TerminalDeviceState>(
            buildWhen: (previous, current) => previous.note != current.note,
            builder: (context, state) {
              return TextFormField(
                  controller: context.bloc<TerminalDeviceBloc>().noteController,
                  onChanged: (value) { context.bloc<TerminalDeviceBloc>()
                    .add(NoteChanged(note:value)); },
                  keyboardType:TextInputType.text,                  decoration: InputDecoration(
                      labelText:S.of(context).pageEntitiesTerminalDeviceNoteField));
            }
        );
      }
      Widget ownerEntityIdField() {
        return BlocBuilder<TerminalDeviceBloc, TerminalDeviceState>(
            buildWhen: (previous, current) => previous.ownerEntityId != current.ownerEntityId,
            builder: (context, state) {
              return TextFormField(
                  controller: context.bloc<TerminalDeviceBloc>().ownerEntityIdController,
                  onChanged: (value) { context.bloc<TerminalDeviceBloc>()
                    .add(OwnerEntityIdChanged(ownerEntityId:int.parse(value))); },
                  keyboardType:TextInputType.number,                  decoration: InputDecoration(
                      labelText:S.of(context).pageEntitiesTerminalDeviceOwnerEntityIdField));
            }
        );
      }
      Widget ownerEntityNameField() {
        return BlocBuilder<TerminalDeviceBloc, TerminalDeviceState>(
            buildWhen: (previous, current) => previous.ownerEntityName != current.ownerEntityName,
            builder: (context, state) {
              return TextFormField(
                  controller: context.bloc<TerminalDeviceBloc>().ownerEntityNameController,
                  onChanged: (value) { context.bloc<TerminalDeviceBloc>()
                    .add(OwnerEntityNameChanged(ownerEntityName:value)); },
                  keyboardType:TextInputType.text,                  decoration: InputDecoration(
                      labelText:S.of(context).pageEntitiesTerminalDeviceOwnerEntityNameField));
            }
        );
      }
      Widget createdDateField() {
        return BlocBuilder<TerminalDeviceBloc, TerminalDeviceState>(
            buildWhen: (previous, current) => previous.createdDate != current.createdDate,
            builder: (context, state) {
              return DateTimeField(
                controller: context.bloc<TerminalDeviceBloc>().createdDateController,
                onChanged: (value) { context.bloc<TerminalDeviceBloc>().add(CreatedDateChanged(createdDate: value)); },
                format: DateFormat.yMMMMd(S.of(context).locale),
                keyboardType: TextInputType.datetime,
            decoration: InputDecoration(labelText:S.of(context).pageEntitiesTerminalDeviceCreatedDateField,),
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
      Widget lastUpdatedDateField() {
        return BlocBuilder<TerminalDeviceBloc, TerminalDeviceState>(
            buildWhen: (previous, current) => previous.lastUpdatedDate != current.lastUpdatedDate,
            builder: (context, state) {
              return DateTimeField(
                controller: context.bloc<TerminalDeviceBloc>().lastUpdatedDateController,
                onChanged: (value) { context.bloc<TerminalDeviceBloc>().add(LastUpdatedDateChanged(lastUpdatedDate: value)); },
                format: DateFormat.yMMMMd(S.of(context).locale),
                keyboardType: TextInputType.datetime,
            decoration: InputDecoration(labelText:S.of(context).pageEntitiesTerminalDeviceLastUpdatedDateField,),
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
        return BlocBuilder<TerminalDeviceBloc, TerminalDeviceState>(
            buildWhen: (previous, current) => previous.clientId != current.clientId,
            builder: (context, state) {
              return TextFormField(
                  controller: context.bloc<TerminalDeviceBloc>().clientIdController,
                  onChanged: (value) { context.bloc<TerminalDeviceBloc>()
                    .add(ClientIdChanged(clientId:int.parse(value))); },
                  keyboardType:TextInputType.number,                  decoration: InputDecoration(
                      labelText:S.of(context).pageEntitiesTerminalDeviceClientIdField));
            }
        );
      }
        Widget hasExtraDataField() {
          return BlocBuilder<TerminalDeviceBloc,TerminalDeviceState>(
              buildWhen: (previous, current) => previous.hasExtraData != current.hasExtraData,
              builder: (context, state) {
                return Padding(
                  padding: const EdgeInsets.only(left: 3.0),
                  child: Row(
                    mainAxisAlignment: MainAxisAlignment.spaceBetween,
                    children: <Widget>[
                      Text(S.of(context).pageEntitiesTerminalDeviceHasExtraDataField, style: Theme.of(context).textTheme.bodyText1,),
                      Switch(
                          value: state.hasExtraData.value,
                          onChanged: (value) { context.bloc<TerminalDeviceBloc>().add(HasExtraDataChanged(hasExtraData: value)); },
                          activeColor: Theme.of(context).primaryColor,),
                    ],
                  ),
                );
              });
        }


  Widget validationZone() {
    return BlocBuilder<TerminalDeviceBloc, TerminalDeviceState>(
        buildWhen:(previous, current) => previous.formStatus != current.formStatus,
        builder: (context, state) {
          return Visibility(
              visible: state.formStatus.isSubmissionFailure ||  state.formStatus.isSubmissionSuccess,
              child: Center(
                child: generateNotificationText(state, context),
              ));
        });
  }

  Widget generateNotificationText(TerminalDeviceState state, BuildContext context) {
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
    return BlocBuilder<TerminalDeviceBloc, TerminalDeviceState>(
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
            onPressed: state.formStatus.isValidated ? () => context.bloc<TerminalDeviceBloc>().add(TerminalDeviceFormSubmitted()) : null,
          );
        }
    );
  }
}
