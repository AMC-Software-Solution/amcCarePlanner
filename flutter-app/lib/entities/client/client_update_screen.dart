import 'package:datetime_picker_formfield/datetime_picker_formfield.dart';
import 'package:intl/intl.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:amcCarePlanner/entities/client/bloc/client_bloc.dart';
import 'package:amcCarePlanner/generated/l10n.dart';
import 'package:flutter/material.dart';
import 'package:formz/formz.dart';
import 'package:amcCarePlanner/shared/repository/http_utils.dart';
import 'package:amcCarePlanner/entities/client/client_model.dart';
import 'client_route.dart';

class ClientUpdateScreen extends StatelessWidget {
  ClientUpdateScreen({Key key}) : super(key: ClientRoutes.editScreenKey);

  @override
  Widget build(BuildContext context) {
    return BlocListener<ClientBloc, ClientState>(
      listener: (context, state) {
        if(state.formStatus.isSubmissionSuccess){
          Navigator.pushNamed(context, ClientRoutes.list);
        }
      },
      child: Scaffold(
          appBar: AppBar(
            centerTitle: true,
            title: BlocBuilder<ClientBloc, ClientState>(
                buildWhen: (previous, current) => previous.editMode != current.editMode,
                builder: (context, state) {
                String title = state.editMode == true ?S.of(context).pageEntitiesClientUpdateTitle:
S.of(context).pageEntitiesClientCreateTitle;
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
          clientNameField(),
          clientDescriptionField(),
          clientLogoUrlField(),
          clientContactNameField(),
          clientPhoneField(),
          clientEmailField(),
          createdDateField(),
          enabledField(),
          reasonField(),
          lastUpdatedDateField(),
          hasExtraDataField(),
        validationZone(),
        submit(context)
      ]),
    );
  }

      Widget clientNameField() {
        return BlocBuilder<ClientBloc, ClientState>(
            buildWhen: (previous, current) => previous.clientName != current.clientName,
            builder: (context, state) {
              return TextFormField(
                  controller: context.bloc<ClientBloc>().clientNameController,
                  onChanged: (value) { context.bloc<ClientBloc>()
                    .add(ClientNameChanged(clientName:value)); },
                  keyboardType:TextInputType.text,                  decoration: InputDecoration(
                      labelText:S.of(context).pageEntitiesClientClientNameField));
            }
        );
      }
      Widget clientDescriptionField() {
        return BlocBuilder<ClientBloc, ClientState>(
            buildWhen: (previous, current) => previous.clientDescription != current.clientDescription,
            builder: (context, state) {
              return TextFormField(
                  controller: context.bloc<ClientBloc>().clientDescriptionController,
                  onChanged: (value) { context.bloc<ClientBloc>()
                    .add(ClientDescriptionChanged(clientDescription:value)); },
                  keyboardType:TextInputType.text,                  decoration: InputDecoration(
                      labelText:S.of(context).pageEntitiesClientClientDescriptionField));
            }
        );
      }
      Widget clientLogoUrlField() {
        return BlocBuilder<ClientBloc, ClientState>(
            buildWhen: (previous, current) => previous.clientLogoUrl != current.clientLogoUrl,
            builder: (context, state) {
              return TextFormField(
                  controller: context.bloc<ClientBloc>().clientLogoUrlController,
                  onChanged: (value) { context.bloc<ClientBloc>()
                    .add(ClientLogoUrlChanged(clientLogoUrl:value)); },
                  keyboardType:TextInputType.text,                  decoration: InputDecoration(
                      labelText:S.of(context).pageEntitiesClientClientLogoUrlField));
            }
        );
      }
      Widget clientContactNameField() {
        return BlocBuilder<ClientBloc, ClientState>(
            buildWhen: (previous, current) => previous.clientContactName != current.clientContactName,
            builder: (context, state) {
              return TextFormField(
                  controller: context.bloc<ClientBloc>().clientContactNameController,
                  onChanged: (value) { context.bloc<ClientBloc>()
                    .add(ClientContactNameChanged(clientContactName:value)); },
                  keyboardType:TextInputType.text,                  decoration: InputDecoration(
                      labelText:S.of(context).pageEntitiesClientClientContactNameField));
            }
        );
      }
      Widget clientPhoneField() {
        return BlocBuilder<ClientBloc, ClientState>(
            buildWhen: (previous, current) => previous.clientPhone != current.clientPhone,
            builder: (context, state) {
              return TextFormField(
                  controller: context.bloc<ClientBloc>().clientPhoneController,
                  onChanged: (value) { context.bloc<ClientBloc>()
                    .add(ClientPhoneChanged(clientPhone:value)); },
                  keyboardType:TextInputType.text,                  decoration: InputDecoration(
                      labelText:S.of(context).pageEntitiesClientClientPhoneField));
            }
        );
      }
      Widget clientEmailField() {
        return BlocBuilder<ClientBloc, ClientState>(
            buildWhen: (previous, current) => previous.clientEmail != current.clientEmail,
            builder: (context, state) {
              return TextFormField(
                  controller: context.bloc<ClientBloc>().clientEmailController,
                  onChanged: (value) { context.bloc<ClientBloc>()
                    .add(ClientEmailChanged(clientEmail:value)); },
                  keyboardType:TextInputType.text,                  decoration: InputDecoration(
                      labelText:S.of(context).pageEntitiesClientClientEmailField));
            }
        );
      }
      Widget createdDateField() {
        return BlocBuilder<ClientBloc, ClientState>(
            buildWhen: (previous, current) => previous.createdDate != current.createdDate,
            builder: (context, state) {
              return DateTimeField(
                controller: context.bloc<ClientBloc>().createdDateController,
                onChanged: (value) { context.bloc<ClientBloc>().add(CreatedDateChanged(createdDate: value)); },
                format: DateFormat.yMMMMd(S.of(context).locale),
                keyboardType: TextInputType.datetime,
            decoration: InputDecoration(labelText:S.of(context).pageEntitiesClientCreatedDateField,),
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
        Widget enabledField() {
          return BlocBuilder<ClientBloc,ClientState>(
              buildWhen: (previous, current) => previous.enabled != current.enabled,
              builder: (context, state) {
                return Padding(
                  padding: const EdgeInsets.only(left: 3.0),
                  child: Row(
                    mainAxisAlignment: MainAxisAlignment.spaceBetween,
                    children: <Widget>[
                      Text(S.of(context).pageEntitiesClientEnabledField, style: Theme.of(context).textTheme.bodyText1,),
                      Switch(
                          value: state.enabled.value,
                          onChanged: (value) { context.bloc<ClientBloc>().add(EnabledChanged(enabled: value)); },
                          activeColor: Theme.of(context).primaryColor,),
                    ],
                  ),
                );
              });
        }
      Widget reasonField() {
        return BlocBuilder<ClientBloc, ClientState>(
            buildWhen: (previous, current) => previous.reason != current.reason,
            builder: (context, state) {
              return TextFormField(
                  controller: context.bloc<ClientBloc>().reasonController,
                  onChanged: (value) { context.bloc<ClientBloc>()
                    .add(ReasonChanged(reason:value)); },
                  keyboardType:TextInputType.text,                  decoration: InputDecoration(
                      labelText:S.of(context).pageEntitiesClientReasonField));
            }
        );
      }
      Widget lastUpdatedDateField() {
        return BlocBuilder<ClientBloc, ClientState>(
            buildWhen: (previous, current) => previous.lastUpdatedDate != current.lastUpdatedDate,
            builder: (context, state) {
              return DateTimeField(
                controller: context.bloc<ClientBloc>().lastUpdatedDateController,
                onChanged: (value) { context.bloc<ClientBloc>().add(LastUpdatedDateChanged(lastUpdatedDate: value)); },
                format: DateFormat.yMMMMd(S.of(context).locale),
                keyboardType: TextInputType.datetime,
            decoration: InputDecoration(labelText:S.of(context).pageEntitiesClientLastUpdatedDateField,),
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
        Widget hasExtraDataField() {
          return BlocBuilder<ClientBloc,ClientState>(
              buildWhen: (previous, current) => previous.hasExtraData != current.hasExtraData,
              builder: (context, state) {
                return Padding(
                  padding: const EdgeInsets.only(left: 3.0),
                  child: Row(
                    mainAxisAlignment: MainAxisAlignment.spaceBetween,
                    children: <Widget>[
                      Text(S.of(context).pageEntitiesClientHasExtraDataField, style: Theme.of(context).textTheme.bodyText1,),
                      Switch(
                          value: state.hasExtraData.value,
                          onChanged: (value) { context.bloc<ClientBloc>().add(HasExtraDataChanged(hasExtraData: value)); },
                          activeColor: Theme.of(context).primaryColor,),
                    ],
                  ),
                );
              });
        }


  Widget validationZone() {
    return BlocBuilder<ClientBloc, ClientState>(
        buildWhen:(previous, current) => previous.formStatus != current.formStatus,
        builder: (context, state) {
          return Visibility(
              visible: state.formStatus.isSubmissionFailure ||  state.formStatus.isSubmissionSuccess,
              child: Center(
                child: generateNotificationText(state, context),
              ));
        });
  }

  Widget generateNotificationText(ClientState state, BuildContext context) {
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
    return BlocBuilder<ClientBloc, ClientState>(
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
            onPressed: state.formStatus.isValidated ? () => context.bloc<ClientBloc>().add(ClientFormSubmitted()) : null,
          );
        }
    );
  }
}
