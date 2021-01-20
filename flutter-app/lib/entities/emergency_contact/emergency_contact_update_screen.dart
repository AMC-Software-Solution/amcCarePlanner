import 'package:datetime_picker_formfield/datetime_picker_formfield.dart';
import 'package:intl/intl.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:amcCarePlanner/entities/emergency_contact/bloc/emergency_contact_bloc.dart';
import 'package:amcCarePlanner/generated/l10n.dart';
import 'package:flutter/material.dart';
import 'package:formz/formz.dart';
import 'package:amcCarePlanner/shared/repository/http_utils.dart';
import 'package:amcCarePlanner/entities/emergency_contact/emergency_contact_model.dart';
import 'emergency_contact_route.dart';

class EmergencyContactUpdateScreen extends StatelessWidget {
  EmergencyContactUpdateScreen({Key key}) : super(key: EmergencyContactRoutes.editScreenKey);

  @override
  Widget build(BuildContext context) {
    return BlocListener<EmergencyContactBloc, EmergencyContactState>(
      listener: (context, state) {
        if(state.formStatus.isSubmissionSuccess){
          Navigator.pushNamed(context, EmergencyContactRoutes.list);
        }
      },
      child: Scaffold(
          appBar: AppBar(
            centerTitle: true,
            title: BlocBuilder<EmergencyContactBloc, EmergencyContactState>(
                buildWhen: (previous, current) => previous.editMode != current.editMode,
                builder: (context, state) {
                String title = state.editMode == true ?S.of(context).pageEntitiesEmergencyContactUpdateTitle:
S.of(context).pageEntitiesEmergencyContactCreateTitle;
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
          nameField(),
          contactRelationshipField(),
          isKeyHolderField(),
          infoSharingConsentGivenField(),
          preferredContactNumberField(),
          fullAddressField(),
          createdDateField(),
          lastUpdatedDateField(),
          clientIdField(),
          hasExtraDataField(),
        validationZone(),
        submit(context)
      ]),
    );
  }

      Widget nameField() {
        return BlocBuilder<EmergencyContactBloc, EmergencyContactState>(
            buildWhen: (previous, current) => previous.name != current.name,
            builder: (context, state) {
              return TextFormField(
                  controller: context.bloc<EmergencyContactBloc>().nameController,
                  onChanged: (value) { context.bloc<EmergencyContactBloc>()
                    .add(NameChanged(name:value)); },
                  keyboardType:TextInputType.text,                  decoration: InputDecoration(
                      labelText:S.of(context).pageEntitiesEmergencyContactNameField));
            }
        );
      }
      Widget contactRelationshipField() {
        return BlocBuilder<EmergencyContactBloc, EmergencyContactState>(
            buildWhen: (previous, current) => previous.contactRelationship != current.contactRelationship,
            builder: (context, state) {
              return TextFormField(
                  controller: context.bloc<EmergencyContactBloc>().contactRelationshipController,
                  onChanged: (value) { context.bloc<EmergencyContactBloc>()
                    .add(ContactRelationshipChanged(contactRelationship:value)); },
                  keyboardType:TextInputType.text,                  decoration: InputDecoration(
                      labelText:S.of(context).pageEntitiesEmergencyContactContactRelationshipField));
            }
        );
      }
        Widget isKeyHolderField() {
          return BlocBuilder<EmergencyContactBloc,EmergencyContactState>(
              buildWhen: (previous, current) => previous.isKeyHolder != current.isKeyHolder,
              builder: (context, state) {
                return Padding(
                  padding: const EdgeInsets.only(left: 3.0),
                  child: Row(
                    mainAxisAlignment: MainAxisAlignment.spaceBetween,
                    children: <Widget>[
                      Text(S.of(context).pageEntitiesEmergencyContactIsKeyHolderField, style: Theme.of(context).textTheme.bodyText1,),
                      Switch(
                          value: state.isKeyHolder.value,
                          onChanged: (value) { context.bloc<EmergencyContactBloc>().add(IsKeyHolderChanged(isKeyHolder: value)); },
                          activeColor: Theme.of(context).primaryColor,),
                    ],
                  ),
                );
              });
        }
        Widget infoSharingConsentGivenField() {
          return BlocBuilder<EmergencyContactBloc,EmergencyContactState>(
              buildWhen: (previous, current) => previous.infoSharingConsentGiven != current.infoSharingConsentGiven,
              builder: (context, state) {
                return Padding(
                  padding: const EdgeInsets.only(left: 3.0),
                  child: Row(
                    mainAxisAlignment: MainAxisAlignment.spaceBetween,
                    children: <Widget>[
                      Text(S.of(context).pageEntitiesEmergencyContactInfoSharingConsentGivenField, style: Theme.of(context).textTheme.bodyText1,),
                      Switch(
                          value: state.infoSharingConsentGiven.value,
                          onChanged: (value) { context.bloc<EmergencyContactBloc>().add(InfoSharingConsentGivenChanged(infoSharingConsentGiven: value)); },
                          activeColor: Theme.of(context).primaryColor,),
                    ],
                  ),
                );
              });
        }
      Widget preferredContactNumberField() {
        return BlocBuilder<EmergencyContactBloc, EmergencyContactState>(
            buildWhen: (previous, current) => previous.preferredContactNumber != current.preferredContactNumber,
            builder: (context, state) {
              return TextFormField(
                  controller: context.bloc<EmergencyContactBloc>().preferredContactNumberController,
                  onChanged: (value) { context.bloc<EmergencyContactBloc>()
                    .add(PreferredContactNumberChanged(preferredContactNumber:value)); },
                  keyboardType:TextInputType.text,                  decoration: InputDecoration(
                      labelText:S.of(context).pageEntitiesEmergencyContactPreferredContactNumberField));
            }
        );
      }
      Widget fullAddressField() {
        return BlocBuilder<EmergencyContactBloc, EmergencyContactState>(
            buildWhen: (previous, current) => previous.fullAddress != current.fullAddress,
            builder: (context, state) {
              return TextFormField(
                  controller: context.bloc<EmergencyContactBloc>().fullAddressController,
                  onChanged: (value) { context.bloc<EmergencyContactBloc>()
                    .add(FullAddressChanged(fullAddress:value)); },
                  keyboardType:TextInputType.text,                  decoration: InputDecoration(
                      labelText:S.of(context).pageEntitiesEmergencyContactFullAddressField));
            }
        );
      }
      Widget createdDateField() {
        return BlocBuilder<EmergencyContactBloc, EmergencyContactState>(
            buildWhen: (previous, current) => previous.createdDate != current.createdDate,
            builder: (context, state) {
              return DateTimeField(
                controller: context.bloc<EmergencyContactBloc>().createdDateController,
                onChanged: (value) { context.bloc<EmergencyContactBloc>().add(CreatedDateChanged(createdDate: value)); },
                format: DateFormat.yMMMMd(S.of(context).locale),
                keyboardType: TextInputType.datetime,
            decoration: InputDecoration(labelText:S.of(context).pageEntitiesEmergencyContactCreatedDateField,),
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
        return BlocBuilder<EmergencyContactBloc, EmergencyContactState>(
            buildWhen: (previous, current) => previous.lastUpdatedDate != current.lastUpdatedDate,
            builder: (context, state) {
              return DateTimeField(
                controller: context.bloc<EmergencyContactBloc>().lastUpdatedDateController,
                onChanged: (value) { context.bloc<EmergencyContactBloc>().add(LastUpdatedDateChanged(lastUpdatedDate: value)); },
                format: DateFormat.yMMMMd(S.of(context).locale),
                keyboardType: TextInputType.datetime,
            decoration: InputDecoration(labelText:S.of(context).pageEntitiesEmergencyContactLastUpdatedDateField,),
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
        return BlocBuilder<EmergencyContactBloc, EmergencyContactState>(
            buildWhen: (previous, current) => previous.clientId != current.clientId,
            builder: (context, state) {
              return TextFormField(
                  controller: context.bloc<EmergencyContactBloc>().clientIdController,
                  onChanged: (value) { context.bloc<EmergencyContactBloc>()
                    .add(ClientIdChanged(clientId:int.parse(value))); },
                  keyboardType:TextInputType.number,                  decoration: InputDecoration(
                      labelText:S.of(context).pageEntitiesEmergencyContactClientIdField));
            }
        );
      }
        Widget hasExtraDataField() {
          return BlocBuilder<EmergencyContactBloc,EmergencyContactState>(
              buildWhen: (previous, current) => previous.hasExtraData != current.hasExtraData,
              builder: (context, state) {
                return Padding(
                  padding: const EdgeInsets.only(left: 3.0),
                  child: Row(
                    mainAxisAlignment: MainAxisAlignment.spaceBetween,
                    children: <Widget>[
                      Text(S.of(context).pageEntitiesEmergencyContactHasExtraDataField, style: Theme.of(context).textTheme.bodyText1,),
                      Switch(
                          value: state.hasExtraData.value,
                          onChanged: (value) { context.bloc<EmergencyContactBloc>().add(HasExtraDataChanged(hasExtraData: value)); },
                          activeColor: Theme.of(context).primaryColor,),
                    ],
                  ),
                );
              });
        }


  Widget validationZone() {
    return BlocBuilder<EmergencyContactBloc, EmergencyContactState>(
        buildWhen:(previous, current) => previous.formStatus != current.formStatus,
        builder: (context, state) {
          return Visibility(
              visible: state.formStatus.isSubmissionFailure ||  state.formStatus.isSubmissionSuccess,
              child: Center(
                child: generateNotificationText(state, context),
              ));
        });
  }

  Widget generateNotificationText(EmergencyContactState state, BuildContext context) {
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
    return BlocBuilder<EmergencyContactBloc, EmergencyContactState>(
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
            onPressed: state.formStatus.isValidated ? () => context.bloc<EmergencyContactBloc>().add(EmergencyContactFormSubmitted()) : null,
          );
        }
    );
  }
}
