import 'package:datetime_picker_formfield/datetime_picker_formfield.dart';
import 'package:intl/intl.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:amcCarePlanner/entities/servce_user_document/bloc/servce_user_document_bloc.dart';
import 'package:amcCarePlanner/generated/l10n.dart';
import 'package:flutter/material.dart';
import 'package:formz/formz.dart';
import 'package:amcCarePlanner/shared/repository/http_utils.dart';
import 'package:amcCarePlanner/entities/servce_user_document/servce_user_document_model.dart';
import 'servce_user_document_route.dart';

class ServceUserDocumentUpdateScreen extends StatelessWidget {
  ServceUserDocumentUpdateScreen({Key key}) : super(key: ServceUserDocumentRoutes.editScreenKey);

  @override
  Widget build(BuildContext context) {
    return BlocListener<ServceUserDocumentBloc, ServceUserDocumentState>(
      listener: (context, state) {
        if(state.formStatus.isSubmissionSuccess){
          Navigator.pushNamed(context, ServceUserDocumentRoutes.list);
        }
      },
      child: Scaffold(
          appBar: AppBar(
            centerTitle: true,
            title: BlocBuilder<ServceUserDocumentBloc, ServceUserDocumentState>(
                buildWhen: (previous, current) => previous.editMode != current.editMode,
                builder: (context, state) {
                String title = state.editMode == true ?S.of(context).pageEntitiesServceUserDocumentUpdateTitle:
S.of(context).pageEntitiesServceUserDocumentCreateTitle;
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
        return BlocBuilder<ServceUserDocumentBloc, ServceUserDocumentState>(
            buildWhen: (previous, current) => previous.documentName != current.documentName,
            builder: (context, state) {
              return TextFormField(
                  controller: context.bloc<ServceUserDocumentBloc>().documentNameController,
                  onChanged: (value) { context.bloc<ServceUserDocumentBloc>()
                    .add(DocumentNameChanged(documentName:value)); },
                  keyboardType:TextInputType.text,                  decoration: InputDecoration(
                      labelText:S.of(context).pageEntitiesServceUserDocumentDocumentNameField));
            }
        );
      }
      Widget documentNumberField() {
        return BlocBuilder<ServceUserDocumentBloc, ServceUserDocumentState>(
            buildWhen: (previous, current) => previous.documentNumber != current.documentNumber,
            builder: (context, state) {
              return TextFormField(
                  controller: context.bloc<ServceUserDocumentBloc>().documentNumberController,
                  onChanged: (value) { context.bloc<ServceUserDocumentBloc>()
                    .add(DocumentNumberChanged(documentNumber:value)); },
                  keyboardType:TextInputType.text,                  decoration: InputDecoration(
                      labelText:S.of(context).pageEntitiesServceUserDocumentDocumentNumberField));
            }
        );
      }
      Widget documentStatusField() {
        return BlocBuilder<ServceUserDocumentBloc,ServceUserDocumentState>(
            buildWhen: (previous, current) => previous.documentStatus != current.documentStatus,
            builder: (context, state) {
              return Padding(
                padding: const EdgeInsets.only(left: 3.0),
                child: Row(
                  mainAxisAlignment: MainAxisAlignment.spaceBetween,
                  children: <Widget>[
                    Text(S.of(context).pageEntitiesServceUserDocumentDocumentStatusField, style: Theme.of(context).textTheme.bodyText1,),
                    DropdownButton<DocumentStatus>(
                        value: state.documentStatus.value,
                        onChanged: (value) { context.bloc<ServceUserDocumentBloc>().add(DocumentStatusChanged(documentStatus: value)); },
                        items: createDropdownDocumentStatusItems(DocumentStatus.values)),
                  ],
                ),
              );
            });
      }
      Widget noteField() {
        return BlocBuilder<ServceUserDocumentBloc, ServceUserDocumentState>(
            buildWhen: (previous, current) => previous.note != current.note,
            builder: (context, state) {
              return TextFormField(
                  controller: context.bloc<ServceUserDocumentBloc>().noteController,
                  onChanged: (value) { context.bloc<ServceUserDocumentBloc>()
                    .add(NoteChanged(note:value)); },
                  keyboardType:TextInputType.text,                  decoration: InputDecoration(
                      labelText:S.of(context).pageEntitiesServceUserDocumentNoteField));
            }
        );
      }
      Widget issuedDateField() {
        return BlocBuilder<ServceUserDocumentBloc, ServceUserDocumentState>(
            buildWhen: (previous, current) => previous.issuedDate != current.issuedDate,
            builder: (context, state) {
              return DateTimeField(
                controller: context.bloc<ServceUserDocumentBloc>().issuedDateController,
                onChanged: (value) { context.bloc<ServceUserDocumentBloc>().add(IssuedDateChanged(issuedDate: value)); },
                format: DateFormat.yMMMMd(S.of(context).locale),
                keyboardType: TextInputType.datetime,
            decoration: InputDecoration(labelText:S.of(context).pageEntitiesServceUserDocumentIssuedDateField,),
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
        return BlocBuilder<ServceUserDocumentBloc, ServceUserDocumentState>(
            buildWhen: (previous, current) => previous.expiryDate != current.expiryDate,
            builder: (context, state) {
              return DateTimeField(
                controller: context.bloc<ServceUserDocumentBloc>().expiryDateController,
                onChanged: (value) { context.bloc<ServceUserDocumentBloc>().add(ExpiryDateChanged(expiryDate: value)); },
                format: DateFormat.yMMMMd(S.of(context).locale),
                keyboardType: TextInputType.datetime,
            decoration: InputDecoration(labelText:S.of(context).pageEntitiesServceUserDocumentExpiryDateField,),
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
        return BlocBuilder<ServceUserDocumentBloc, ServceUserDocumentState>(
            buildWhen: (previous, current) => previous.uploadedDate != current.uploadedDate,
            builder: (context, state) {
              return DateTimeField(
                controller: context.bloc<ServceUserDocumentBloc>().uploadedDateController,
                onChanged: (value) { context.bloc<ServceUserDocumentBloc>().add(UploadedDateChanged(uploadedDate: value)); },
                format: DateFormat.yMMMMd(S.of(context).locale),
                keyboardType: TextInputType.datetime,
            decoration: InputDecoration(labelText:S.of(context).pageEntitiesServceUserDocumentUploadedDateField,),
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
        return BlocBuilder<ServceUserDocumentBloc, ServceUserDocumentState>(
            buildWhen: (previous, current) => previous.documentFileUrl != current.documentFileUrl,
            builder: (context, state) {
              return TextFormField(
                  controller: context.bloc<ServceUserDocumentBloc>().documentFileUrlController,
                  onChanged: (value) { context.bloc<ServceUserDocumentBloc>()
                    .add(DocumentFileUrlChanged(documentFileUrl:value)); },
                  keyboardType:TextInputType.text,                  decoration: InputDecoration(
                      labelText:S.of(context).pageEntitiesServceUserDocumentDocumentFileUrlField));
            }
        );
      }
      Widget createdDateField() {
        return BlocBuilder<ServceUserDocumentBloc, ServceUserDocumentState>(
            buildWhen: (previous, current) => previous.createdDate != current.createdDate,
            builder: (context, state) {
              return DateTimeField(
                controller: context.bloc<ServceUserDocumentBloc>().createdDateController,
                onChanged: (value) { context.bloc<ServceUserDocumentBloc>().add(CreatedDateChanged(createdDate: value)); },
                format: DateFormat.yMMMMd(S.of(context).locale),
                keyboardType: TextInputType.datetime,
            decoration: InputDecoration(labelText:S.of(context).pageEntitiesServceUserDocumentCreatedDateField,),
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
        return BlocBuilder<ServceUserDocumentBloc, ServceUserDocumentState>(
            buildWhen: (previous, current) => previous.lastUpdatedDate != current.lastUpdatedDate,
            builder: (context, state) {
              return DateTimeField(
                controller: context.bloc<ServceUserDocumentBloc>().lastUpdatedDateController,
                onChanged: (value) { context.bloc<ServceUserDocumentBloc>().add(LastUpdatedDateChanged(lastUpdatedDate: value)); },
                format: DateFormat.yMMMMd(S.of(context).locale),
                keyboardType: TextInputType.datetime,
            decoration: InputDecoration(labelText:S.of(context).pageEntitiesServceUserDocumentLastUpdatedDateField,),
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
        return BlocBuilder<ServceUserDocumentBloc, ServceUserDocumentState>(
            buildWhen: (previous, current) => previous.clientId != current.clientId,
            builder: (context, state) {
              return TextFormField(
                  controller: context.bloc<ServceUserDocumentBloc>().clientIdController,
                  onChanged: (value) { context.bloc<ServceUserDocumentBloc>()
                    .add(ClientIdChanged(clientId:int.parse(value))); },
                  keyboardType:TextInputType.number,                  decoration: InputDecoration(
                      labelText:S.of(context).pageEntitiesServceUserDocumentClientIdField));
            }
        );
      }
        Widget hasExtraDataField() {
          return BlocBuilder<ServceUserDocumentBloc,ServceUserDocumentState>(
              buildWhen: (previous, current) => previous.hasExtraData != current.hasExtraData,
              builder: (context, state) {
                return Padding(
                  padding: const EdgeInsets.only(left: 3.0),
                  child: Row(
                    mainAxisAlignment: MainAxisAlignment.spaceBetween,
                    children: <Widget>[
                      Text(S.of(context).pageEntitiesServceUserDocumentHasExtraDataField, style: Theme.of(context).textTheme.bodyText1,),
                      Switch(
                          value: state.hasExtraData.value,
                          onChanged: (value) { context.bloc<ServceUserDocumentBloc>().add(HasExtraDataChanged(hasExtraData: value)); },
                          activeColor: Theme.of(context).primaryColor,),
                    ],
                  ),
                );
              });
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
    return BlocBuilder<ServceUserDocumentBloc, ServceUserDocumentState>(
        buildWhen:(previous, current) => previous.formStatus != current.formStatus,
        builder: (context, state) {
          return Visibility(
              visible: state.formStatus.isSubmissionFailure ||  state.formStatus.isSubmissionSuccess,
              child: Center(
                child: generateNotificationText(state, context),
              ));
        });
  }

  Widget generateNotificationText(ServceUserDocumentState state, BuildContext context) {
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
    return BlocBuilder<ServceUserDocumentBloc, ServceUserDocumentState>(
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
            onPressed: state.formStatus.isValidated ? () => context.bloc<ServceUserDocumentBloc>().add(ServceUserDocumentFormSubmitted()) : null,
          );
        }
    );
  }
}
