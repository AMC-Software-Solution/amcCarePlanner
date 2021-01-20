import 'package:datetime_picker_formfield/datetime_picker_formfield.dart';
import 'package:intl/intl.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:amcCarePlanner/entities/communication/bloc/communication_bloc.dart';
import 'package:amcCarePlanner/generated/l10n.dart';
import 'package:flutter/material.dart';
import 'package:formz/formz.dart';
import 'package:amcCarePlanner/shared/repository/http_utils.dart';
import 'package:amcCarePlanner/entities/communication/communication_model.dart';
import 'communication_route.dart';

class CommunicationUpdateScreen extends StatelessWidget {
  CommunicationUpdateScreen({Key key}) : super(key: CommunicationRoutes.editScreenKey);

  @override
  Widget build(BuildContext context) {
    return BlocListener<CommunicationBloc, CommunicationState>(
      listener: (context, state) {
        if(state.formStatus.isSubmissionSuccess){
          Navigator.pushNamed(context, CommunicationRoutes.list);
        }
      },
      child: Scaffold(
          appBar: AppBar(
            centerTitle: true,
            title: BlocBuilder<CommunicationBloc, CommunicationState>(
                buildWhen: (previous, current) => previous.editMode != current.editMode,
                builder: (context, state) {
                String title = state.editMode == true ?S.of(context).pageEntitiesCommunicationUpdateTitle:
S.of(context).pageEntitiesCommunicationCreateTitle;
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
          communicationTypeField(),
          noteField(),
          communicationDateField(),
          attachmentUrlField(),
          createdDateField(),
          lastUpdatedDateField(),
          clientIdField(),
          hasExtraDataField(),
        validationZone(),
        submit(context)
      ]),
    );
  }

      Widget communicationTypeField() {
        return BlocBuilder<CommunicationBloc,CommunicationState>(
            buildWhen: (previous, current) => previous.communicationType != current.communicationType,
            builder: (context, state) {
              return Padding(
                padding: const EdgeInsets.only(left: 3.0),
                child: Row(
                  mainAxisAlignment: MainAxisAlignment.spaceBetween,
                  children: <Widget>[
                    Text(S.of(context).pageEntitiesCommunicationCommunicationTypeField, style: Theme.of(context).textTheme.bodyText1,),
                    DropdownButton<CommunicationType>(
                        value: state.communicationType.value,
                        onChanged: (value) { context.bloc<CommunicationBloc>().add(CommunicationTypeChanged(communicationType: value)); },
                        items: createDropdownCommunicationTypeItems(CommunicationType.values)),
                  ],
                ),
              );
            });
      }
      Widget noteField() {
        return BlocBuilder<CommunicationBloc, CommunicationState>(
            buildWhen: (previous, current) => previous.note != current.note,
            builder: (context, state) {
              return TextFormField(
                  controller: context.bloc<CommunicationBloc>().noteController,
                  onChanged: (value) { context.bloc<CommunicationBloc>()
                    .add(NoteChanged(note:value)); },
                  keyboardType:TextInputType.text,                  decoration: InputDecoration(
                      labelText:S.of(context).pageEntitiesCommunicationNoteField));
            }
        );
      }
      Widget communicationDateField() {
        return BlocBuilder<CommunicationBloc, CommunicationState>(
            buildWhen: (previous, current) => previous.communicationDate != current.communicationDate,
            builder: (context, state) {
              return DateTimeField(
                controller: context.bloc<CommunicationBloc>().communicationDateController,
                onChanged: (value) { context.bloc<CommunicationBloc>().add(CommunicationDateChanged(communicationDate: value)); },
                format: DateFormat.yMMMMd(S.of(context).locale),
                keyboardType: TextInputType.datetime,
            decoration: InputDecoration(labelText:S.of(context).pageEntitiesCommunicationCommunicationDateField,),
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
      Widget attachmentUrlField() {
        return BlocBuilder<CommunicationBloc, CommunicationState>(
            buildWhen: (previous, current) => previous.attachmentUrl != current.attachmentUrl,
            builder: (context, state) {
              return TextFormField(
                  controller: context.bloc<CommunicationBloc>().attachmentUrlController,
                  onChanged: (value) { context.bloc<CommunicationBloc>()
                    .add(AttachmentUrlChanged(attachmentUrl:value)); },
                  keyboardType:TextInputType.text,                  decoration: InputDecoration(
                      labelText:S.of(context).pageEntitiesCommunicationAttachmentUrlField));
            }
        );
      }
      Widget createdDateField() {
        return BlocBuilder<CommunicationBloc, CommunicationState>(
            buildWhen: (previous, current) => previous.createdDate != current.createdDate,
            builder: (context, state) {
              return DateTimeField(
                controller: context.bloc<CommunicationBloc>().createdDateController,
                onChanged: (value) { context.bloc<CommunicationBloc>().add(CreatedDateChanged(createdDate: value)); },
                format: DateFormat.yMMMMd(S.of(context).locale),
                keyboardType: TextInputType.datetime,
            decoration: InputDecoration(labelText:S.of(context).pageEntitiesCommunicationCreatedDateField,),
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
        return BlocBuilder<CommunicationBloc, CommunicationState>(
            buildWhen: (previous, current) => previous.lastUpdatedDate != current.lastUpdatedDate,
            builder: (context, state) {
              return DateTimeField(
                controller: context.bloc<CommunicationBloc>().lastUpdatedDateController,
                onChanged: (value) { context.bloc<CommunicationBloc>().add(LastUpdatedDateChanged(lastUpdatedDate: value)); },
                format: DateFormat.yMMMMd(S.of(context).locale),
                keyboardType: TextInputType.datetime,
            decoration: InputDecoration(labelText:S.of(context).pageEntitiesCommunicationLastUpdatedDateField,),
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
        return BlocBuilder<CommunicationBloc, CommunicationState>(
            buildWhen: (previous, current) => previous.clientId != current.clientId,
            builder: (context, state) {
              return TextFormField(
                  controller: context.bloc<CommunicationBloc>().clientIdController,
                  onChanged: (value) { context.bloc<CommunicationBloc>()
                    .add(ClientIdChanged(clientId:int.parse(value))); },
                  keyboardType:TextInputType.number,                  decoration: InputDecoration(
                      labelText:S.of(context).pageEntitiesCommunicationClientIdField));
            }
        );
      }
        Widget hasExtraDataField() {
          return BlocBuilder<CommunicationBloc,CommunicationState>(
              buildWhen: (previous, current) => previous.hasExtraData != current.hasExtraData,
              builder: (context, state) {
                return Padding(
                  padding: const EdgeInsets.only(left: 3.0),
                  child: Row(
                    mainAxisAlignment: MainAxisAlignment.spaceBetween,
                    children: <Widget>[
                      Text(S.of(context).pageEntitiesCommunicationHasExtraDataField, style: Theme.of(context).textTheme.bodyText1,),
                      Switch(
                          value: state.hasExtraData.value,
                          onChanged: (value) { context.bloc<CommunicationBloc>().add(HasExtraDataChanged(hasExtraData: value)); },
                          activeColor: Theme.of(context).primaryColor,),
                    ],
                  ),
                );
              });
        }

      List<DropdownMenuItem<CommunicationType>> createDropdownCommunicationTypeItems(List<CommunicationType> communicationTypes) {
        List<DropdownMenuItem<CommunicationType>> communicationTypeDropDown = [];
    
        for (CommunicationType communicationType in communicationTypes) {
          DropdownMenuItem<CommunicationType> dropdown = DropdownMenuItem<CommunicationType>(
              value: communicationType, child: Text(communicationType.toString()));
              communicationTypeDropDown.add(dropdown);
        }
    
        return communicationTypeDropDown;
      }

  Widget validationZone() {
    return BlocBuilder<CommunicationBloc, CommunicationState>(
        buildWhen:(previous, current) => previous.formStatus != current.formStatus,
        builder: (context, state) {
          return Visibility(
              visible: state.formStatus.isSubmissionFailure ||  state.formStatus.isSubmissionSuccess,
              child: Center(
                child: generateNotificationText(state, context),
              ));
        });
  }

  Widget generateNotificationText(CommunicationState state, BuildContext context) {
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
    return BlocBuilder<CommunicationBloc, CommunicationState>(
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
            onPressed: state.formStatus.isValidated ? () => context.bloc<CommunicationBloc>().add(CommunicationFormSubmitted()) : null,
          );
        }
    );
  }
}
