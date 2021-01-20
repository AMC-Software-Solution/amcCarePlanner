import 'package:datetime_picker_formfield/datetime_picker_formfield.dart';
import 'package:intl/intl.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:amcCarePlanner/entities/power_of_attorney/bloc/power_of_attorney_bloc.dart';
import 'package:amcCarePlanner/generated/l10n.dart';
import 'package:flutter/material.dart';
import 'package:formz/formz.dart';
import 'package:amcCarePlanner/shared/repository/http_utils.dart';
import 'package:amcCarePlanner/entities/power_of_attorney/power_of_attorney_model.dart';
import 'power_of_attorney_route.dart';

class PowerOfAttorneyUpdateScreen extends StatelessWidget {
  PowerOfAttorneyUpdateScreen({Key key}) : super(key: PowerOfAttorneyRoutes.editScreenKey);

  @override
  Widget build(BuildContext context) {
    return BlocListener<PowerOfAttorneyBloc, PowerOfAttorneyState>(
      listener: (context, state) {
        if(state.formStatus.isSubmissionSuccess){
          Navigator.pushNamed(context, PowerOfAttorneyRoutes.list);
        }
      },
      child: Scaffold(
          appBar: AppBar(
            centerTitle: true,
            title: BlocBuilder<PowerOfAttorneyBloc, PowerOfAttorneyState>(
                buildWhen: (previous, current) => previous.editMode != current.editMode,
                builder: (context, state) {
                String title = state.editMode == true ?S.of(context).pageEntitiesPowerOfAttorneyUpdateTitle:
S.of(context).pageEntitiesPowerOfAttorneyCreateTitle;
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
          powerOfAttorneyConsentField(),
          healthAndWelfareField(),
          healthAndWelfareNameField(),
          propertyAndFinAffairsField(),
          propertyAndFinAffairsNameField(),
          createdDateField(),
          lastUpdatedDateField(),
          clientIdField(),
          hasExtraDataField(),
        validationZone(),
        submit(context)
      ]),
    );
  }

        Widget powerOfAttorneyConsentField() {
          return BlocBuilder<PowerOfAttorneyBloc,PowerOfAttorneyState>(
              buildWhen: (previous, current) => previous.powerOfAttorneyConsent != current.powerOfAttorneyConsent,
              builder: (context, state) {
                return Padding(
                  padding: const EdgeInsets.only(left: 3.0),
                  child: Row(
                    mainAxisAlignment: MainAxisAlignment.spaceBetween,
                    children: <Widget>[
                      Text(S.of(context).pageEntitiesPowerOfAttorneyPowerOfAttorneyConsentField, style: Theme.of(context).textTheme.bodyText1,),
                      Switch(
                          value: state.powerOfAttorneyConsent.value,
                          onChanged: (value) { context.bloc<PowerOfAttorneyBloc>().add(PowerOfAttorneyConsentChanged(powerOfAttorneyConsent: value)); },
                          activeColor: Theme.of(context).primaryColor,),
                    ],
                  ),
                );
              });
        }
        Widget healthAndWelfareField() {
          return BlocBuilder<PowerOfAttorneyBloc,PowerOfAttorneyState>(
              buildWhen: (previous, current) => previous.healthAndWelfare != current.healthAndWelfare,
              builder: (context, state) {
                return Padding(
                  padding: const EdgeInsets.only(left: 3.0),
                  child: Row(
                    mainAxisAlignment: MainAxisAlignment.spaceBetween,
                    children: <Widget>[
                      Text(S.of(context).pageEntitiesPowerOfAttorneyHealthAndWelfareField, style: Theme.of(context).textTheme.bodyText1,),
                      Switch(
                          value: state.healthAndWelfare.value,
                          onChanged: (value) { context.bloc<PowerOfAttorneyBloc>().add(HealthAndWelfareChanged(healthAndWelfare: value)); },
                          activeColor: Theme.of(context).primaryColor,),
                    ],
                  ),
                );
              });
        }
      Widget healthAndWelfareNameField() {
        return BlocBuilder<PowerOfAttorneyBloc, PowerOfAttorneyState>(
            buildWhen: (previous, current) => previous.healthAndWelfareName != current.healthAndWelfareName,
            builder: (context, state) {
              return TextFormField(
                  controller: context.bloc<PowerOfAttorneyBloc>().healthAndWelfareNameController,
                  onChanged: (value) { context.bloc<PowerOfAttorneyBloc>()
                    .add(HealthAndWelfareNameChanged(healthAndWelfareName:value)); },
                  keyboardType:TextInputType.text,                  decoration: InputDecoration(
                      labelText:S.of(context).pageEntitiesPowerOfAttorneyHealthAndWelfareNameField));
            }
        );
      }
        Widget propertyAndFinAffairsField() {
          return BlocBuilder<PowerOfAttorneyBloc,PowerOfAttorneyState>(
              buildWhen: (previous, current) => previous.propertyAndFinAffairs != current.propertyAndFinAffairs,
              builder: (context, state) {
                return Padding(
                  padding: const EdgeInsets.only(left: 3.0),
                  child: Row(
                    mainAxisAlignment: MainAxisAlignment.spaceBetween,
                    children: <Widget>[
                      Text(S.of(context).pageEntitiesPowerOfAttorneyPropertyAndFinAffairsField, style: Theme.of(context).textTheme.bodyText1,),
                      Switch(
                          value: state.propertyAndFinAffairs.value,
                          onChanged: (value) { context.bloc<PowerOfAttorneyBloc>().add(PropertyAndFinAffairsChanged(propertyAndFinAffairs: value)); },
                          activeColor: Theme.of(context).primaryColor,),
                    ],
                  ),
                );
              });
        }
      Widget propertyAndFinAffairsNameField() {
        return BlocBuilder<PowerOfAttorneyBloc, PowerOfAttorneyState>(
            buildWhen: (previous, current) => previous.propertyAndFinAffairsName != current.propertyAndFinAffairsName,
            builder: (context, state) {
              return TextFormField(
                  controller: context.bloc<PowerOfAttorneyBloc>().propertyAndFinAffairsNameController,
                  onChanged: (value) { context.bloc<PowerOfAttorneyBloc>()
                    .add(PropertyAndFinAffairsNameChanged(propertyAndFinAffairsName:value)); },
                  keyboardType:TextInputType.text,                  decoration: InputDecoration(
                      labelText:S.of(context).pageEntitiesPowerOfAttorneyPropertyAndFinAffairsNameField));
            }
        );
      }
      Widget createdDateField() {
        return BlocBuilder<PowerOfAttorneyBloc, PowerOfAttorneyState>(
            buildWhen: (previous, current) => previous.createdDate != current.createdDate,
            builder: (context, state) {
              return DateTimeField(
                controller: context.bloc<PowerOfAttorneyBloc>().createdDateController,
                onChanged: (value) { context.bloc<PowerOfAttorneyBloc>().add(CreatedDateChanged(createdDate: value)); },
                format: DateFormat.yMMMMd(S.of(context).locale),
                keyboardType: TextInputType.datetime,
            decoration: InputDecoration(labelText:S.of(context).pageEntitiesPowerOfAttorneyCreatedDateField,),
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
        return BlocBuilder<PowerOfAttorneyBloc, PowerOfAttorneyState>(
            buildWhen: (previous, current) => previous.lastUpdatedDate != current.lastUpdatedDate,
            builder: (context, state) {
              return DateTimeField(
                controller: context.bloc<PowerOfAttorneyBloc>().lastUpdatedDateController,
                onChanged: (value) { context.bloc<PowerOfAttorneyBloc>().add(LastUpdatedDateChanged(lastUpdatedDate: value)); },
                format: DateFormat.yMMMMd(S.of(context).locale),
                keyboardType: TextInputType.datetime,
            decoration: InputDecoration(labelText:S.of(context).pageEntitiesPowerOfAttorneyLastUpdatedDateField,),
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
        return BlocBuilder<PowerOfAttorneyBloc, PowerOfAttorneyState>(
            buildWhen: (previous, current) => previous.clientId != current.clientId,
            builder: (context, state) {
              return TextFormField(
                  controller: context.bloc<PowerOfAttorneyBloc>().clientIdController,
                  onChanged: (value) { context.bloc<PowerOfAttorneyBloc>()
                    .add(ClientIdChanged(clientId:int.parse(value))); },
                  keyboardType:TextInputType.number,                  decoration: InputDecoration(
                      labelText:S.of(context).pageEntitiesPowerOfAttorneyClientIdField));
            }
        );
      }
        Widget hasExtraDataField() {
          return BlocBuilder<PowerOfAttorneyBloc,PowerOfAttorneyState>(
              buildWhen: (previous, current) => previous.hasExtraData != current.hasExtraData,
              builder: (context, state) {
                return Padding(
                  padding: const EdgeInsets.only(left: 3.0),
                  child: Row(
                    mainAxisAlignment: MainAxisAlignment.spaceBetween,
                    children: <Widget>[
                      Text(S.of(context).pageEntitiesPowerOfAttorneyHasExtraDataField, style: Theme.of(context).textTheme.bodyText1,),
                      Switch(
                          value: state.hasExtraData.value,
                          onChanged: (value) { context.bloc<PowerOfAttorneyBloc>().add(HasExtraDataChanged(hasExtraData: value)); },
                          activeColor: Theme.of(context).primaryColor,),
                    ],
                  ),
                );
              });
        }


  Widget validationZone() {
    return BlocBuilder<PowerOfAttorneyBloc, PowerOfAttorneyState>(
        buildWhen:(previous, current) => previous.formStatus != current.formStatus,
        builder: (context, state) {
          return Visibility(
              visible: state.formStatus.isSubmissionFailure ||  state.formStatus.isSubmissionSuccess,
              child: Center(
                child: generateNotificationText(state, context),
              ));
        });
  }

  Widget generateNotificationText(PowerOfAttorneyState state, BuildContext context) {
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
    return BlocBuilder<PowerOfAttorneyBloc, PowerOfAttorneyState>(
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
            onPressed: state.formStatus.isValidated ? () => context.bloc<PowerOfAttorneyBloc>().add(PowerOfAttorneyFormSubmitted()) : null,
          );
        }
    );
  }
}
