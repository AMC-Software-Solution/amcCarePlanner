import 'package:datetime_picker_formfield/datetime_picker_formfield.dart';
import 'package:intl/intl.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:amcCarePlanner/entities/employee_document/bloc/employee_document_bloc.dart';
import 'package:amcCarePlanner/generated/l10n.dart';
import 'package:flutter/material.dart';
import 'package:formz/formz.dart';
import 'package:amcCarePlanner/shared/repository/http_utils.dart';
import 'package:amcCarePlanner/entities/employee_document/employee_document_model.dart';
import 'employee_document_route.dart';

class EmployeeDocumentUpdateScreen extends StatelessWidget {
  EmployeeDocumentUpdateScreen({Key key}) : super(key: EmployeeDocumentRoutes.editScreenKey);

  @override
  Widget build(BuildContext context) {
    return BlocListener<EmployeeDocumentBloc, EmployeeDocumentState>(
      listener: (context, state) {
        if(state.formStatus.isSubmissionSuccess){
          Navigator.pushNamed(context, EmployeeDocumentRoutes.list);
        }
      },
      child: Scaffold(
          appBar: AppBar(
            centerTitle: true,
            title: BlocBuilder<EmployeeDocumentBloc, EmployeeDocumentState>(
                buildWhen: (previous, current) => previous.editMode != current.editMode,
                builder: (context, state) {
                String title = state.editMode == true ?S.of(context).pageEntitiesEmployeeDocumentUpdateTitle:
S.of(context).pageEntitiesEmployeeDocumentCreateTitle;
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
        return BlocBuilder<EmployeeDocumentBloc, EmployeeDocumentState>(
            buildWhen: (previous, current) => previous.documentName != current.documentName,
            builder: (context, state) {
              return TextFormField(
                  controller: context.bloc<EmployeeDocumentBloc>().documentNameController,
                  onChanged: (value) { context.bloc<EmployeeDocumentBloc>()
                    .add(DocumentNameChanged(documentName:value)); },
                  keyboardType:TextInputType.text,                  decoration: InputDecoration(
                      labelText:S.of(context).pageEntitiesEmployeeDocumentDocumentNameField));
            }
        );
      }
      Widget documentNumberField() {
        return BlocBuilder<EmployeeDocumentBloc, EmployeeDocumentState>(
            buildWhen: (previous, current) => previous.documentNumber != current.documentNumber,
            builder: (context, state) {
              return TextFormField(
                  controller: context.bloc<EmployeeDocumentBloc>().documentNumberController,
                  onChanged: (value) { context.bloc<EmployeeDocumentBloc>()
                    .add(DocumentNumberChanged(documentNumber:value)); },
                  keyboardType:TextInputType.text,                  decoration: InputDecoration(
                      labelText:S.of(context).pageEntitiesEmployeeDocumentDocumentNumberField));
            }
        );
      }
      Widget documentStatusField() {
        return BlocBuilder<EmployeeDocumentBloc,EmployeeDocumentState>(
            buildWhen: (previous, current) => previous.documentStatus != current.documentStatus,
            builder: (context, state) {
              return Padding(
                padding: const EdgeInsets.only(left: 3.0),
                child: Row(
                  mainAxisAlignment: MainAxisAlignment.spaceBetween,
                  children: <Widget>[
                    Text(S.of(context).pageEntitiesEmployeeDocumentDocumentStatusField, style: Theme.of(context).textTheme.bodyText1,),
                    DropdownButton<EmployeeDocumentStatus>(
                        value: state.documentStatus.value,
                        onChanged: (value) { context.bloc<EmployeeDocumentBloc>().add(DocumentStatusChanged(documentStatus: value)); },
                        items: createDropdownEmployeeDocumentStatusItems(EmployeeDocumentStatus.values)),
                  ],
                ),
              );
            });
      }
      Widget noteField() {
        return BlocBuilder<EmployeeDocumentBloc, EmployeeDocumentState>(
            buildWhen: (previous, current) => previous.note != current.note,
            builder: (context, state) {
              return TextFormField(
                  controller: context.bloc<EmployeeDocumentBloc>().noteController,
                  onChanged: (value) { context.bloc<EmployeeDocumentBloc>()
                    .add(NoteChanged(note:value)); },
                  keyboardType:TextInputType.text,                  decoration: InputDecoration(
                      labelText:S.of(context).pageEntitiesEmployeeDocumentNoteField));
            }
        );
      }
      Widget issuedDateField() {
        return BlocBuilder<EmployeeDocumentBloc, EmployeeDocumentState>(
            buildWhen: (previous, current) => previous.issuedDate != current.issuedDate,
            builder: (context, state) {
              return DateTimeField(
                controller: context.bloc<EmployeeDocumentBloc>().issuedDateController,
                onChanged: (value) { context.bloc<EmployeeDocumentBloc>().add(IssuedDateChanged(issuedDate: value)); },
                format: DateFormat.yMMMMd(S.of(context).locale),
                keyboardType: TextInputType.datetime,
            decoration: InputDecoration(labelText:S.of(context).pageEntitiesEmployeeDocumentIssuedDateField,),
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
        return BlocBuilder<EmployeeDocumentBloc, EmployeeDocumentState>(
            buildWhen: (previous, current) => previous.expiryDate != current.expiryDate,
            builder: (context, state) {
              return DateTimeField(
                controller: context.bloc<EmployeeDocumentBloc>().expiryDateController,
                onChanged: (value) { context.bloc<EmployeeDocumentBloc>().add(ExpiryDateChanged(expiryDate: value)); },
                format: DateFormat.yMMMMd(S.of(context).locale),
                keyboardType: TextInputType.datetime,
            decoration: InputDecoration(labelText:S.of(context).pageEntitiesEmployeeDocumentExpiryDateField,),
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
        return BlocBuilder<EmployeeDocumentBloc, EmployeeDocumentState>(
            buildWhen: (previous, current) => previous.uploadedDate != current.uploadedDate,
            builder: (context, state) {
              return DateTimeField(
                controller: context.bloc<EmployeeDocumentBloc>().uploadedDateController,
                onChanged: (value) { context.bloc<EmployeeDocumentBloc>().add(UploadedDateChanged(uploadedDate: value)); },
                format: DateFormat.yMMMMd(S.of(context).locale),
                keyboardType: TextInputType.datetime,
            decoration: InputDecoration(labelText:S.of(context).pageEntitiesEmployeeDocumentUploadedDateField,),
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
        return BlocBuilder<EmployeeDocumentBloc, EmployeeDocumentState>(
            buildWhen: (previous, current) => previous.documentFileUrl != current.documentFileUrl,
            builder: (context, state) {
              return TextFormField(
                  controller: context.bloc<EmployeeDocumentBloc>().documentFileUrlController,
                  onChanged: (value) { context.bloc<EmployeeDocumentBloc>()
                    .add(DocumentFileUrlChanged(documentFileUrl:value)); },
                  keyboardType:TextInputType.text,                  decoration: InputDecoration(
                      labelText:S.of(context).pageEntitiesEmployeeDocumentDocumentFileUrlField));
            }
        );
      }
      Widget createdDateField() {
        return BlocBuilder<EmployeeDocumentBloc, EmployeeDocumentState>(
            buildWhen: (previous, current) => previous.createdDate != current.createdDate,
            builder: (context, state) {
              return DateTimeField(
                controller: context.bloc<EmployeeDocumentBloc>().createdDateController,
                onChanged: (value) { context.bloc<EmployeeDocumentBloc>().add(CreatedDateChanged(createdDate: value)); },
                format: DateFormat.yMMMMd(S.of(context).locale),
                keyboardType: TextInputType.datetime,
            decoration: InputDecoration(labelText:S.of(context).pageEntitiesEmployeeDocumentCreatedDateField,),
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
        return BlocBuilder<EmployeeDocumentBloc, EmployeeDocumentState>(
            buildWhen: (previous, current) => previous.lastUpdatedDate != current.lastUpdatedDate,
            builder: (context, state) {
              return DateTimeField(
                controller: context.bloc<EmployeeDocumentBloc>().lastUpdatedDateController,
                onChanged: (value) { context.bloc<EmployeeDocumentBloc>().add(LastUpdatedDateChanged(lastUpdatedDate: value)); },
                format: DateFormat.yMMMMd(S.of(context).locale),
                keyboardType: TextInputType.datetime,
            decoration: InputDecoration(labelText:S.of(context).pageEntitiesEmployeeDocumentLastUpdatedDateField,),
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
        return BlocBuilder<EmployeeDocumentBloc, EmployeeDocumentState>(
            buildWhen: (previous, current) => previous.clientId != current.clientId,
            builder: (context, state) {
              return TextFormField(
                  controller: context.bloc<EmployeeDocumentBloc>().clientIdController,
                  onChanged: (value) { context.bloc<EmployeeDocumentBloc>()
                    .add(ClientIdChanged(clientId:int.parse(value))); },
                  keyboardType:TextInputType.number,                  decoration: InputDecoration(
                      labelText:S.of(context).pageEntitiesEmployeeDocumentClientIdField));
            }
        );
      }
        Widget hasExtraDataField() {
          return BlocBuilder<EmployeeDocumentBloc,EmployeeDocumentState>(
              buildWhen: (previous, current) => previous.hasExtraData != current.hasExtraData,
              builder: (context, state) {
                return Padding(
                  padding: const EdgeInsets.only(left: 3.0),
                  child: Row(
                    mainAxisAlignment: MainAxisAlignment.spaceBetween,
                    children: <Widget>[
                      Text(S.of(context).pageEntitiesEmployeeDocumentHasExtraDataField, style: Theme.of(context).textTheme.bodyText1,),
                      Switch(
                          value: state.hasExtraData.value,
                          onChanged: (value) { context.bloc<EmployeeDocumentBloc>().add(HasExtraDataChanged(hasExtraData: value)); },
                          activeColor: Theme.of(context).primaryColor,),
                    ],
                  ),
                );
              });
        }

      List<DropdownMenuItem<EmployeeDocumentStatus>> createDropdownEmployeeDocumentStatusItems(List<EmployeeDocumentStatus> documentStatuss) {
        List<DropdownMenuItem<EmployeeDocumentStatus>> documentStatusDropDown = [];
    
        for (EmployeeDocumentStatus documentStatus in documentStatuss) {
          DropdownMenuItem<EmployeeDocumentStatus> dropdown = DropdownMenuItem<EmployeeDocumentStatus>(
              value: documentStatus, child: Text(documentStatus.toString()));
              documentStatusDropDown.add(dropdown);
        }
    
        return documentStatusDropDown;
      }

  Widget validationZone() {
    return BlocBuilder<EmployeeDocumentBloc, EmployeeDocumentState>(
        buildWhen:(previous, current) => previous.formStatus != current.formStatus,
        builder: (context, state) {
          return Visibility(
              visible: state.formStatus.isSubmissionFailure ||  state.formStatus.isSubmissionSuccess,
              child: Center(
                child: generateNotificationText(state, context),
              ));
        });
  }

  Widget generateNotificationText(EmployeeDocumentState state, BuildContext context) {
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
    return BlocBuilder<EmployeeDocumentBloc, EmployeeDocumentState>(
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
            onPressed: state.formStatus.isValidated ? () => context.bloc<EmployeeDocumentBloc>().add(EmployeeDocumentFormSubmitted()) : null,
          );
        }
    );
  }
}
