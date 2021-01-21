import 'package:datetime_picker_formfield/datetime_picker_formfield.dart';
import 'package:intl/intl.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:amcCarePlanner/entities/client_document/bloc/client_document_bloc.dart';
import 'package:amcCarePlanner/generated/l10n.dart';
import 'package:flutter/material.dart';
import 'package:formz/formz.dart';
import 'package:amcCarePlanner/shared/repository/http_utils.dart';
import 'package:amcCarePlanner/entities/client_document/client_document_model.dart';
import 'client_document_route.dart';

class ClientDocumentUpdateScreen extends StatelessWidget {
  ClientDocumentUpdateScreen({Key key}) : super(key: ClientDocumentRoutes.editScreenKey);

  @override
  Widget build(BuildContext context) {
    return BlocListener<ClientDocumentBloc, ClientDocumentState>(
      listener: (context, state) {
        if(state.formStatus.isSubmissionSuccess){
          Navigator.pushNamed(context, ClientDocumentRoutes.list);
        }
      },
      child: Scaffold(
          appBar: AppBar(
            centerTitle: true,
            title: BlocBuilder<ClientDocumentBloc, ClientDocumentState>(
                buildWhen: (previous, current) => previous.editMode != current.editMode,
                builder: (context, state) {
                String title = state.editMode == true ?S.of(context).pageEntitiesClientDocumentUpdateTitle:
S.of(context).pageEntitiesClientDocumentCreateTitle;
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
          documentNameField(),
          documentNumberField(),
          documentTypeField(),
          documentStatusField(),
          noteField(),
          issuedDateField(),
          expiryDateField(),
          uploadedDateField(),
          documentFileUrlField(),
          createdDateField(),
          lastUpdatedDateField(),
          clientIdField(),
          hasExtraDataField(),
        validationZone(),
        submit(context)
      ]),
    );
  }

      Widget documentNameField() {
        return BlocBuilder<ClientDocumentBloc, ClientDocumentState>(
            buildWhen: (previous, current) => previous.documentName != current.documentName,
            builder: (context, state) {
              return TextFormField(
                  controller: context.bloc<ClientDocumentBloc>().documentNameController,
                  onChanged: (value) { context.bloc<ClientDocumentBloc>()
                    .add(DocumentNameChanged(documentName:value)); },
                  keyboardType:TextInputType.text,                  decoration: InputDecoration(
                      labelText:S.of(context).pageEntitiesClientDocumentDocumentNameField));
            }
        );
      }
      Widget documentNumberField() {
        return BlocBuilder<ClientDocumentBloc, ClientDocumentState>(
            buildWhen: (previous, current) => previous.documentNumber != current.documentNumber,
            builder: (context, state) {
              return TextFormField(
                  controller: context.bloc<ClientDocumentBloc>().documentNumberController,
                  onChanged: (value) { context.bloc<ClientDocumentBloc>()
                    .add(DocumentNumberChanged(documentNumber:value)); },
                  keyboardType:TextInputType.text,                  decoration: InputDecoration(
                      labelText:S.of(context).pageEntitiesClientDocumentDocumentNumberField));
            }
        );
      }
      Widget documentTypeField() {
        return BlocBuilder<ClientDocumentBloc,ClientDocumentState>(
            buildWhen: (previous, current) => previous.documentType != current.documentType,
            builder: (context, state) {
              return Padding(
                padding: const EdgeInsets.only(left: 3.0),
                child: Row(
                  mainAxisAlignment: MainAxisAlignment.spaceBetween,
                  children: <Widget>[
                    Text(S.of(context).pageEntitiesClientDocumentDocumentTypeField, style: Theme.of(context).textTheme.bodyText1,),
                    DropdownButton<ClientDocumentType>(
                        value: state.documentType.value,
                        onChanged: (value) { context.bloc<ClientDocumentBloc>().add(DocumentTypeChanged(documentType: value)); },
                        items: createDropdownClientDocumentTypeItems(ClientDocumentType.values)),
                  ],
                ),
              );
            });
      }
      Widget documentStatusField() {
        return BlocBuilder<ClientDocumentBloc,ClientDocumentState>(
            buildWhen: (previous, current) => previous.documentStatus != current.documentStatus,
            builder: (context, state) {
              return Padding(
                padding: const EdgeInsets.only(left: 3.0),
                child: Row(
                  mainAxisAlignment: MainAxisAlignment.spaceBetween,
                  children: <Widget>[
                    Text(S.of(context).pageEntitiesClientDocumentDocumentStatusField, style: Theme.of(context).textTheme.bodyText1,),
                    DropdownButton<DocumentStatus>(
                        value: state.documentStatus.value,
                        onChanged: (value) { context.bloc<ClientDocumentBloc>().add(DocumentStatusChanged(documentStatus: value)); },
                        items: createDropdownDocumentStatusItems(DocumentStatus.values)),
                  ],
                ),
              );
            });
      }
      Widget noteField() {
        return BlocBuilder<ClientDocumentBloc, ClientDocumentState>(
            buildWhen: (previous, current) => previous.note != current.note,
            builder: (context, state) {
              return TextFormField(
                  controller: context.bloc<ClientDocumentBloc>().noteController,
                  onChanged: (value) { context.bloc<ClientDocumentBloc>()
                    .add(NoteChanged(note:value)); },
                  keyboardType:TextInputType.text,                  decoration: InputDecoration(
                      labelText:S.of(context).pageEntitiesClientDocumentNoteField));
            }
        );
      }
      Widget issuedDateField() {
        return BlocBuilder<ClientDocumentBloc, ClientDocumentState>(
            buildWhen: (previous, current) => previous.issuedDate != current.issuedDate,
            builder: (context, state) {
              return DateTimeField(
                controller: context.bloc<ClientDocumentBloc>().issuedDateController,
                onChanged: (value) { context.bloc<ClientDocumentBloc>().add(IssuedDateChanged(issuedDate: value)); },
                format: DateFormat.yMMMMd(S.of(context).locale),
                keyboardType: TextInputType.datetime,
            decoration: InputDecoration(labelText:S.of(context).pageEntitiesClientDocumentIssuedDateField,),
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
      Widget expiryDateField() {
        return BlocBuilder<ClientDocumentBloc, ClientDocumentState>(
            buildWhen: (previous, current) => previous.expiryDate != current.expiryDate,
            builder: (context, state) {
              return DateTimeField(
                controller: context.bloc<ClientDocumentBloc>().expiryDateController,
                onChanged: (value) { context.bloc<ClientDocumentBloc>().add(ExpiryDateChanged(expiryDate: value)); },
                format: DateFormat.yMMMMd(S.of(context).locale),
                keyboardType: TextInputType.datetime,
            decoration: InputDecoration(labelText:S.of(context).pageEntitiesClientDocumentExpiryDateField,),
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
      Widget uploadedDateField() {
        return BlocBuilder<ClientDocumentBloc, ClientDocumentState>(
            buildWhen: (previous, current) => previous.uploadedDate != current.uploadedDate,
            builder: (context, state) {
              return DateTimeField(
                controller: context.bloc<ClientDocumentBloc>().uploadedDateController,
                onChanged: (value) { context.bloc<ClientDocumentBloc>().add(UploadedDateChanged(uploadedDate: value)); },
                format: DateFormat.yMMMMd(S.of(context).locale),
                keyboardType: TextInputType.datetime,
            decoration: InputDecoration(labelText:S.of(context).pageEntitiesClientDocumentUploadedDateField,),
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
      Widget documentFileUrlField() {
        return BlocBuilder<ClientDocumentBloc, ClientDocumentState>(
            buildWhen: (previous, current) => previous.documentFileUrl != current.documentFileUrl,
            builder: (context, state) {
              return TextFormField(
                  controller: context.bloc<ClientDocumentBloc>().documentFileUrlController,
                  onChanged: (value) { context.bloc<ClientDocumentBloc>()
                    .add(DocumentFileUrlChanged(documentFileUrl:value)); },
                  keyboardType:TextInputType.text,                  decoration: InputDecoration(
                      labelText:S.of(context).pageEntitiesClientDocumentDocumentFileUrlField));
            }
        );
      }
      Widget createdDateField() {
        return BlocBuilder<ClientDocumentBloc, ClientDocumentState>(
            buildWhen: (previous, current) => previous.createdDate != current.createdDate,
            builder: (context, state) {
              return DateTimeField(
                controller: context.bloc<ClientDocumentBloc>().createdDateController,
                onChanged: (value) { context.bloc<ClientDocumentBloc>().add(CreatedDateChanged(createdDate: value)); },
                format: DateFormat.yMMMMd(S.of(context).locale),
                keyboardType: TextInputType.datetime,
            decoration: InputDecoration(labelText:S.of(context).pageEntitiesClientDocumentCreatedDateField,),
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
        return BlocBuilder<ClientDocumentBloc, ClientDocumentState>(
            buildWhen: (previous, current) => previous.lastUpdatedDate != current.lastUpdatedDate,
            builder: (context, state) {
              return DateTimeField(
                controller: context.bloc<ClientDocumentBloc>().lastUpdatedDateController,
                onChanged: (value) { context.bloc<ClientDocumentBloc>().add(LastUpdatedDateChanged(lastUpdatedDate: value)); },
                format: DateFormat.yMMMMd(S.of(context).locale),
                keyboardType: TextInputType.datetime,
            decoration: InputDecoration(labelText:S.of(context).pageEntitiesClientDocumentLastUpdatedDateField,),
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
        return BlocBuilder<ClientDocumentBloc, ClientDocumentState>(
            buildWhen: (previous, current) => previous.clientId != current.clientId,
            builder: (context, state) {
              return TextFormField(
                  controller: context.bloc<ClientDocumentBloc>().clientIdController,
                  onChanged: (value) { context.bloc<ClientDocumentBloc>()
                    .add(ClientIdChanged(clientId:int.parse(value))); },
                  keyboardType:TextInputType.number,                  decoration: InputDecoration(
                      labelText:S.of(context).pageEntitiesClientDocumentClientIdField));
            }
        );
      }
        Widget hasExtraDataField() {
          return BlocBuilder<ClientDocumentBloc,ClientDocumentState>(
              buildWhen: (previous, current) => previous.hasExtraData != current.hasExtraData,
              builder: (context, state) {
                return Padding(
                  padding: const EdgeInsets.only(left: 3.0),
                  child: Row(
                    mainAxisAlignment: MainAxisAlignment.spaceBetween,
                    children: <Widget>[
                      Text(S.of(context).pageEntitiesClientDocumentHasExtraDataField, style: Theme.of(context).textTheme.bodyText1,),
                      Switch(
                          value: state.hasExtraData.value,
                          onChanged: (value) { context.bloc<ClientDocumentBloc>().add(HasExtraDataChanged(hasExtraData: value)); },
                          activeColor: Theme.of(context).primaryColor,),
                    ],
                  ),
                );
              });
        }

      List<DropdownMenuItem<ClientDocumentType>> createDropdownClientDocumentTypeItems(List<ClientDocumentType> documentTypes) {
        List<DropdownMenuItem<ClientDocumentType>> documentTypeDropDown = [];
    
        for (ClientDocumentType documentType in documentTypes) {
          DropdownMenuItem<ClientDocumentType> dropdown = DropdownMenuItem<ClientDocumentType>(
              value: documentType, child: Text(documentType.toString()));
              documentTypeDropDown.add(dropdown);
        }
    
        return documentTypeDropDown;
      }
      List<DropdownMenuItem<DocumentStatus>> createDropdownDocumentStatusItems(List<DocumentStatus> documentStatuss) {
        List<DropdownMenuItem<DocumentStatus>> documentStatusDropDown = [];
    
        for (DocumentStatus documentStatus in documentStatuss) {
          DropdownMenuItem<DocumentStatus> dropdown = DropdownMenuItem<DocumentStatus>(
              value: documentStatus, child: Text(documentStatus.toString()));
              documentStatusDropDown.add(dropdown);
        }
    
        return documentStatusDropDown;
      }

  Widget validationZone() {
    return BlocBuilder<ClientDocumentBloc, ClientDocumentState>(
        buildWhen:(previous, current) => previous.formStatus != current.formStatus,
        builder: (context, state) {
          return Visibility(
              visible: state.formStatus.isSubmissionFailure ||  state.formStatus.isSubmissionSuccess,
              child: Center(
                child: generateNotificationText(state, context),
              ));
        });
  }

  Widget generateNotificationText(ClientDocumentState state, BuildContext context) {
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
    return BlocBuilder<ClientDocumentBloc, ClientDocumentState>(
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
            onPressed: state.formStatus.isValidated ? () => context.bloc<ClientDocumentBloc>().add(ClientDocumentFormSubmitted()) : null,
          );
        }
    );
  }
}
