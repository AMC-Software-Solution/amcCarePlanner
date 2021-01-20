import 'package:datetime_picker_formfield/datetime_picker_formfield.dart';
import 'package:intl/intl.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:amcCarePlanner/entities/consent/bloc/consent_bloc.dart';
import 'package:amcCarePlanner/generated/l10n.dart';
import 'package:flutter/material.dart';
import 'package:formz/formz.dart';
import 'package:amcCarePlanner/shared/repository/http_utils.dart';
import 'package:amcCarePlanner/entities/consent/consent_model.dart';
import 'consent_route.dart';

class ConsentUpdateScreen extends StatelessWidget {
  ConsentUpdateScreen({Key key}) : super(key: ConsentRoutes.editScreenKey);

  @override
  Widget build(BuildContext context) {
    return BlocListener<ConsentBloc, ConsentState>(
      listener: (context, state) {
        if(state.formStatus.isSubmissionSuccess){
          Navigator.pushNamed(context, ConsentRoutes.list);
        }
      },
      child: Scaffold(
          appBar: AppBar(
            centerTitle: true,
            title: BlocBuilder<ConsentBloc, ConsentState>(
                buildWhen: (previous, current) => previous.editMode != current.editMode,
                builder: (context, state) {
                String title = state.editMode == true ?S.of(context).pageEntitiesConsentUpdateTitle:
S.of(context).pageEntitiesConsentCreateTitle;
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
          titleField(),
          descriptionField(),
          giveConsentField(),
          arrangementsField(),
          serviceUserSignatureField(),
          signatureImageUrlField(),
          consentDateField(),
          createdDateField(),
          lastUpdatedDateField(),
          clientIdField(),
          hasExtraDataField(),
        validationZone(),
        submit(context)
      ]),
    );
  }

      Widget titleField() {
        return BlocBuilder<ConsentBloc, ConsentState>(
            buildWhen: (previous, current) => previous.title != current.title,
            builder: (context, state) {
              return TextFormField(
                  controller: context.bloc<ConsentBloc>().titleController,
                  onChanged: (value) { context.bloc<ConsentBloc>()
                    .add(TitleChanged(title:value)); },
                  keyboardType:TextInputType.text,                  decoration: InputDecoration(
                      labelText:S.of(context).pageEntitiesConsentTitleField));
            }
        );
      }
      Widget descriptionField() {
        return BlocBuilder<ConsentBloc, ConsentState>(
            buildWhen: (previous, current) => previous.description != current.description,
            builder: (context, state) {
              return TextFormField(
                  controller: context.bloc<ConsentBloc>().descriptionController,
                  onChanged: (value) { context.bloc<ConsentBloc>()
                    .add(DescriptionChanged(description:value)); },
                  keyboardType:TextInputType.text,                  decoration: InputDecoration(
                      labelText:S.of(context).pageEntitiesConsentDescriptionField));
            }
        );
      }
        Widget giveConsentField() {
          return BlocBuilder<ConsentBloc,ConsentState>(
              buildWhen: (previous, current) => previous.giveConsent != current.giveConsent,
              builder: (context, state) {
                return Padding(
                  padding: const EdgeInsets.only(left: 3.0),
                  child: Row(
                    mainAxisAlignment: MainAxisAlignment.spaceBetween,
                    children: <Widget>[
                      Text(S.of(context).pageEntitiesConsentGiveConsentField, style: Theme.of(context).textTheme.bodyText1,),
                      Switch(
                          value: state.giveConsent.value,
                          onChanged: (value) { context.bloc<ConsentBloc>().add(GiveConsentChanged(giveConsent: value)); },
                          activeColor: Theme.of(context).primaryColor,),
                    ],
                  ),
                );
              });
        }
      Widget arrangementsField() {
        return BlocBuilder<ConsentBloc, ConsentState>(
            buildWhen: (previous, current) => previous.arrangements != current.arrangements,
            builder: (context, state) {
              return TextFormField(
                  controller: context.bloc<ConsentBloc>().arrangementsController,
                  onChanged: (value) { context.bloc<ConsentBloc>()
                    .add(ArrangementsChanged(arrangements:value)); },
                  keyboardType:TextInputType.text,                  decoration: InputDecoration(
                      labelText:S.of(context).pageEntitiesConsentArrangementsField));
            }
        );
      }
      Widget serviceUserSignatureField() {
        return BlocBuilder<ConsentBloc, ConsentState>(
            buildWhen: (previous, current) => previous.serviceUserSignature != current.serviceUserSignature,
            builder: (context, state) {
              return TextFormField(
                  controller: context.bloc<ConsentBloc>().serviceUserSignatureController,
                  onChanged: (value) { context.bloc<ConsentBloc>()
                    .add(ServiceUserSignatureChanged(serviceUserSignature:value)); },
                  keyboardType:TextInputType.text,                  decoration: InputDecoration(
                      labelText:S.of(context).pageEntitiesConsentServiceUserSignatureField));
            }
        );
      }
      Widget signatureImageUrlField() {
        return BlocBuilder<ConsentBloc, ConsentState>(
            buildWhen: (previous, current) => previous.signatureImageUrl != current.signatureImageUrl,
            builder: (context, state) {
              return TextFormField(
                  controller: context.bloc<ConsentBloc>().signatureImageUrlController,
                  onChanged: (value) { context.bloc<ConsentBloc>()
                    .add(SignatureImageUrlChanged(signatureImageUrl:value)); },
                  keyboardType:TextInputType.text,                  decoration: InputDecoration(
                      labelText:S.of(context).pageEntitiesConsentSignatureImageUrlField));
            }
        );
      }
      Widget consentDateField() {
        return BlocBuilder<ConsentBloc, ConsentState>(
            buildWhen: (previous, current) => previous.consentDate != current.consentDate,
            builder: (context, state) {
              return DateTimeField(
                controller: context.bloc<ConsentBloc>().consentDateController,
                onChanged: (value) { context.bloc<ConsentBloc>().add(ConsentDateChanged(consentDate: value)); },
                format: DateFormat.yMMMMd(S.of(context).locale),
                keyboardType: TextInputType.datetime,
            decoration: InputDecoration(labelText:S.of(context).pageEntitiesConsentConsentDateField,),
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
      Widget createdDateField() {
        return BlocBuilder<ConsentBloc, ConsentState>(
            buildWhen: (previous, current) => previous.createdDate != current.createdDate,
            builder: (context, state) {
              return DateTimeField(
                controller: context.bloc<ConsentBloc>().createdDateController,
                onChanged: (value) { context.bloc<ConsentBloc>().add(CreatedDateChanged(createdDate: value)); },
                format: DateFormat.yMMMMd(S.of(context).locale),
                keyboardType: TextInputType.datetime,
            decoration: InputDecoration(labelText:S.of(context).pageEntitiesConsentCreatedDateField,),
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
        return BlocBuilder<ConsentBloc, ConsentState>(
            buildWhen: (previous, current) => previous.lastUpdatedDate != current.lastUpdatedDate,
            builder: (context, state) {
              return DateTimeField(
                controller: context.bloc<ConsentBloc>().lastUpdatedDateController,
                onChanged: (value) { context.bloc<ConsentBloc>().add(LastUpdatedDateChanged(lastUpdatedDate: value)); },
                format: DateFormat.yMMMMd(S.of(context).locale),
                keyboardType: TextInputType.datetime,
            decoration: InputDecoration(labelText:S.of(context).pageEntitiesConsentLastUpdatedDateField,),
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
        return BlocBuilder<ConsentBloc, ConsentState>(
            buildWhen: (previous, current) => previous.clientId != current.clientId,
            builder: (context, state) {
              return TextFormField(
                  controller: context.bloc<ConsentBloc>().clientIdController,
                  onChanged: (value) { context.bloc<ConsentBloc>()
                    .add(ClientIdChanged(clientId:int.parse(value))); },
                  keyboardType:TextInputType.number,                  decoration: InputDecoration(
                      labelText:S.of(context).pageEntitiesConsentClientIdField));
            }
        );
      }
        Widget hasExtraDataField() {
          return BlocBuilder<ConsentBloc,ConsentState>(
              buildWhen: (previous, current) => previous.hasExtraData != current.hasExtraData,
              builder: (context, state) {
                return Padding(
                  padding: const EdgeInsets.only(left: 3.0),
                  child: Row(
                    mainAxisAlignment: MainAxisAlignment.spaceBetween,
                    children: <Widget>[
                      Text(S.of(context).pageEntitiesConsentHasExtraDataField, style: Theme.of(context).textTheme.bodyText1,),
                      Switch(
                          value: state.hasExtraData.value,
                          onChanged: (value) { context.bloc<ConsentBloc>().add(HasExtraDataChanged(hasExtraData: value)); },
                          activeColor: Theme.of(context).primaryColor,),
                    ],
                  ),
                );
              });
        }


  Widget validationZone() {
    return BlocBuilder<ConsentBloc, ConsentState>(
        buildWhen:(previous, current) => previous.formStatus != current.formStatus,
        builder: (context, state) {
          return Visibility(
              visible: state.formStatus.isSubmissionFailure ||  state.formStatus.isSubmissionSuccess,
              child: Center(
                child: generateNotificationText(state, context),
              ));
        });
  }

  Widget generateNotificationText(ConsentState state, BuildContext context) {
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
    return BlocBuilder<ConsentBloc, ConsentState>(
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
            onPressed: state.formStatus.isValidated ? () => context.bloc<ConsentBloc>().add(ConsentFormSubmitted()) : null,
          );
        }
    );
  }
}
