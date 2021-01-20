import 'package:datetime_picker_formfield/datetime_picker_formfield.dart';
import 'package:intl/intl.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:amcCarePlanner/entities/equality/bloc/equality_bloc.dart';
import 'package:amcCarePlanner/generated/l10n.dart';
import 'package:flutter/material.dart';
import 'package:formz/formz.dart';
import 'package:amcCarePlanner/shared/repository/http_utils.dart';
import 'package:amcCarePlanner/entities/equality/equality_model.dart';
import 'equality_route.dart';

class EqualityUpdateScreen extends StatelessWidget {
  EqualityUpdateScreen({Key key}) : super(key: EqualityRoutes.editScreenKey);

  @override
  Widget build(BuildContext context) {
    return BlocListener<EqualityBloc, EqualityState>(
      listener: (context, state) {
        if(state.formStatus.isSubmissionSuccess){
          Navigator.pushNamed(context, EqualityRoutes.list);
        }
      },
      child: Scaffold(
          appBar: AppBar(
            centerTitle: true,
            title: BlocBuilder<EqualityBloc, EqualityState>(
                buildWhen: (previous, current) => previous.editMode != current.editMode,
                builder: (context, state) {
                String title = state.editMode == true ?S.of(context).pageEntitiesEqualityUpdateTitle:
S.of(context).pageEntitiesEqualityCreateTitle;
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
          genderField(),
          maritalStatusField(),
          religionField(),
          createdDateField(),
          lastUpdatedDateField(),
          clientIdField(),
          hasExtraDataField(),
        validationZone(),
        submit(context)
      ]),
    );
  }

      Widget genderField() {
        return BlocBuilder<EqualityBloc,EqualityState>(
            buildWhen: (previous, current) => previous.gender != current.gender,
            builder: (context, state) {
              return Padding(
                padding: const EdgeInsets.only(left: 3.0),
                child: Row(
                  mainAxisAlignment: MainAxisAlignment.spaceBetween,
                  children: <Widget>[
                    Text(S.of(context).pageEntitiesEqualityGenderField, style: Theme.of(context).textTheme.bodyText1,),
                    DropdownButton<Gender>(
                        value: state.gender.value,
                        onChanged: (value) { context.bloc<EqualityBloc>().add(GenderChanged(gender: value)); },
                        items: createDropdownGenderItems(Gender.values)),
                  ],
                ),
              );
            });
      }
      Widget maritalStatusField() {
        return BlocBuilder<EqualityBloc,EqualityState>(
            buildWhen: (previous, current) => previous.maritalStatus != current.maritalStatus,
            builder: (context, state) {
              return Padding(
                padding: const EdgeInsets.only(left: 3.0),
                child: Row(
                  mainAxisAlignment: MainAxisAlignment.spaceBetween,
                  children: <Widget>[
                    Text(S.of(context).pageEntitiesEqualityMaritalStatusField, style: Theme.of(context).textTheme.bodyText1,),
                    DropdownButton<MaritalStatus>(
                        value: state.maritalStatus.value,
                        onChanged: (value) { context.bloc<EqualityBloc>().add(MaritalStatusChanged(maritalStatus: value)); },
                        items: createDropdownMaritalStatusItems(MaritalStatus.values)),
                  ],
                ),
              );
            });
      }
      Widget religionField() {
        return BlocBuilder<EqualityBloc,EqualityState>(
            buildWhen: (previous, current) => previous.religion != current.religion,
            builder: (context, state) {
              return Padding(
                padding: const EdgeInsets.only(left: 3.0),
                child: Row(
                  mainAxisAlignment: MainAxisAlignment.spaceBetween,
                  children: <Widget>[
                    Text(S.of(context).pageEntitiesEqualityReligionField, style: Theme.of(context).textTheme.bodyText1,),
                    DropdownButton<Religion>(
                        value: state.religion.value,
                        onChanged: (value) { context.bloc<EqualityBloc>().add(ReligionChanged(religion: value)); },
                        items: createDropdownReligionItems(Religion.values)),
                  ],
                ),
              );
            });
      }
      Widget createdDateField() {
        return BlocBuilder<EqualityBloc, EqualityState>(
            buildWhen: (previous, current) => previous.createdDate != current.createdDate,
            builder: (context, state) {
              return DateTimeField(
                controller: context.bloc<EqualityBloc>().createdDateController,
                onChanged: (value) { context.bloc<EqualityBloc>().add(CreatedDateChanged(createdDate: value)); },
                format: DateFormat.yMMMMd(S.of(context).locale),
                keyboardType: TextInputType.datetime,
            decoration: InputDecoration(labelText:S.of(context).pageEntitiesEqualityCreatedDateField,),
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
        return BlocBuilder<EqualityBloc, EqualityState>(
            buildWhen: (previous, current) => previous.lastUpdatedDate != current.lastUpdatedDate,
            builder: (context, state) {
              return DateTimeField(
                controller: context.bloc<EqualityBloc>().lastUpdatedDateController,
                onChanged: (value) { context.bloc<EqualityBloc>().add(LastUpdatedDateChanged(lastUpdatedDate: value)); },
                format: DateFormat.yMMMMd(S.of(context).locale),
                keyboardType: TextInputType.datetime,
            decoration: InputDecoration(labelText:S.of(context).pageEntitiesEqualityLastUpdatedDateField,),
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
        return BlocBuilder<EqualityBloc, EqualityState>(
            buildWhen: (previous, current) => previous.clientId != current.clientId,
            builder: (context, state) {
              return TextFormField(
                  controller: context.bloc<EqualityBloc>().clientIdController,
                  onChanged: (value) { context.bloc<EqualityBloc>()
                    .add(ClientIdChanged(clientId:int.parse(value))); },
                  keyboardType:TextInputType.number,                  decoration: InputDecoration(
                      labelText:S.of(context).pageEntitiesEqualityClientIdField));
            }
        );
      }
        Widget hasExtraDataField() {
          return BlocBuilder<EqualityBloc,EqualityState>(
              buildWhen: (previous, current) => previous.hasExtraData != current.hasExtraData,
              builder: (context, state) {
                return Padding(
                  padding: const EdgeInsets.only(left: 3.0),
                  child: Row(
                    mainAxisAlignment: MainAxisAlignment.spaceBetween,
                    children: <Widget>[
                      Text(S.of(context).pageEntitiesEqualityHasExtraDataField, style: Theme.of(context).textTheme.bodyText1,),
                      Switch(
                          value: state.hasExtraData.value,
                          onChanged: (value) { context.bloc<EqualityBloc>().add(HasExtraDataChanged(hasExtraData: value)); },
                          activeColor: Theme.of(context).primaryColor,),
                    ],
                  ),
                );
              });
        }

      List<DropdownMenuItem<Gender>> createDropdownGenderItems(List<Gender> genders) {
        List<DropdownMenuItem<Gender>> genderDropDown = [];
    
        for (Gender gender in genders) {
          DropdownMenuItem<Gender> dropdown = DropdownMenuItem<Gender>(
              value: gender, child: Text(gender.toString()));
              genderDropDown.add(dropdown);
        }
    
        return genderDropDown;
      }
      List<DropdownMenuItem<MaritalStatus>> createDropdownMaritalStatusItems(List<MaritalStatus> maritalStatuss) {
        List<DropdownMenuItem<MaritalStatus>> maritalStatusDropDown = [];
    
        for (MaritalStatus maritalStatus in maritalStatuss) {
          DropdownMenuItem<MaritalStatus> dropdown = DropdownMenuItem<MaritalStatus>(
              value: maritalStatus, child: Text(maritalStatus.toString()));
              maritalStatusDropDown.add(dropdown);
        }
    
        return maritalStatusDropDown;
      }
      List<DropdownMenuItem<Religion>> createDropdownReligionItems(List<Religion> religions) {
        List<DropdownMenuItem<Religion>> religionDropDown = [];
    
        for (Religion religion in religions) {
          DropdownMenuItem<Religion> dropdown = DropdownMenuItem<Religion>(
              value: religion, child: Text(religion.toString()));
              religionDropDown.add(dropdown);
        }
    
        return religionDropDown;
      }

  Widget validationZone() {
    return BlocBuilder<EqualityBloc, EqualityState>(
        buildWhen:(previous, current) => previous.formStatus != current.formStatus,
        builder: (context, state) {
          return Visibility(
              visible: state.formStatus.isSubmissionFailure ||  state.formStatus.isSubmissionSuccess,
              child: Center(
                child: generateNotificationText(state, context),
              ));
        });
  }

  Widget generateNotificationText(EqualityState state, BuildContext context) {
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
    return BlocBuilder<EqualityBloc, EqualityState>(
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
            onPressed: state.formStatus.isValidated ? () => context.bloc<EqualityBloc>().add(EqualityFormSubmitted()) : null,
          );
        }
    );
  }
}
