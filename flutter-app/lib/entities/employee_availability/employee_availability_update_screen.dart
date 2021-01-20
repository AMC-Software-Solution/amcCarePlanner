import 'package:datetime_picker_formfield/datetime_picker_formfield.dart';
import 'package:intl/intl.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:amcCarePlanner/entities/employee_availability/bloc/employee_availability_bloc.dart';
import 'package:amcCarePlanner/generated/l10n.dart';
import 'package:flutter/material.dart';
import 'package:formz/formz.dart';
import 'package:amcCarePlanner/shared/repository/http_utils.dart';
import 'package:amcCarePlanner/entities/employee_availability/employee_availability_model.dart';
import 'employee_availability_route.dart';

class EmployeeAvailabilityUpdateScreen extends StatelessWidget {
  EmployeeAvailabilityUpdateScreen({Key key}) : super(key: EmployeeAvailabilityRoutes.editScreenKey);

  @override
  Widget build(BuildContext context) {
    return BlocListener<EmployeeAvailabilityBloc, EmployeeAvailabilityState>(
      listener: (context, state) {
        if(state.formStatus.isSubmissionSuccess){
          Navigator.pushNamed(context, EmployeeAvailabilityRoutes.list);
        }
      },
      child: Scaffold(
          appBar: AppBar(
            centerTitle: true,
            title: BlocBuilder<EmployeeAvailabilityBloc, EmployeeAvailabilityState>(
                buildWhen: (previous, current) => previous.editMode != current.editMode,
                builder: (context, state) {
                String title = state.editMode == true ?S.of(context).pageEntitiesEmployeeAvailabilityUpdateTitle:
S.of(context).pageEntitiesEmployeeAvailabilityCreateTitle;
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
          availableForWorkField(),
          availableMondayField(),
          availableTuesdayField(),
          availableWednesdayField(),
          availableThursdayField(),
          availableFridayField(),
          availableSaturdayField(),
          availableSundayField(),
          preferredShiftField(),
          hasExtraDataField(),
          lastUpdatedDateField(),
          clientIdField(),
        validationZone(),
        submit(context)
      ]),
    );
  }

        Widget availableForWorkField() {
          return BlocBuilder<EmployeeAvailabilityBloc,EmployeeAvailabilityState>(
              buildWhen: (previous, current) => previous.availableForWork != current.availableForWork,
              builder: (context, state) {
                return Padding(
                  padding: const EdgeInsets.only(left: 3.0),
                  child: Row(
                    mainAxisAlignment: MainAxisAlignment.spaceBetween,
                    children: <Widget>[
                      Text(S.of(context).pageEntitiesEmployeeAvailabilityAvailableForWorkField, style: Theme.of(context).textTheme.bodyText1,),
                      Switch(
                          value: state.availableForWork.value,
                          onChanged: (value) { context.bloc<EmployeeAvailabilityBloc>().add(AvailableForWorkChanged(availableForWork: value)); },
                          activeColor: Theme.of(context).primaryColor,),
                    ],
                  ),
                );
              });
        }
        Widget availableMondayField() {
          return BlocBuilder<EmployeeAvailabilityBloc,EmployeeAvailabilityState>(
              buildWhen: (previous, current) => previous.availableMonday != current.availableMonday,
              builder: (context, state) {
                return Padding(
                  padding: const EdgeInsets.only(left: 3.0),
                  child: Row(
                    mainAxisAlignment: MainAxisAlignment.spaceBetween,
                    children: <Widget>[
                      Text(S.of(context).pageEntitiesEmployeeAvailabilityAvailableMondayField, style: Theme.of(context).textTheme.bodyText1,),
                      Switch(
                          value: state.availableMonday.value,
                          onChanged: (value) { context.bloc<EmployeeAvailabilityBloc>().add(AvailableMondayChanged(availableMonday: value)); },
                          activeColor: Theme.of(context).primaryColor,),
                    ],
                  ),
                );
              });
        }
        Widget availableTuesdayField() {
          return BlocBuilder<EmployeeAvailabilityBloc,EmployeeAvailabilityState>(
              buildWhen: (previous, current) => previous.availableTuesday != current.availableTuesday,
              builder: (context, state) {
                return Padding(
                  padding: const EdgeInsets.only(left: 3.0),
                  child: Row(
                    mainAxisAlignment: MainAxisAlignment.spaceBetween,
                    children: <Widget>[
                      Text(S.of(context).pageEntitiesEmployeeAvailabilityAvailableTuesdayField, style: Theme.of(context).textTheme.bodyText1,),
                      Switch(
                          value: state.availableTuesday.value,
                          onChanged: (value) { context.bloc<EmployeeAvailabilityBloc>().add(AvailableTuesdayChanged(availableTuesday: value)); },
                          activeColor: Theme.of(context).primaryColor,),
                    ],
                  ),
                );
              });
        }
        Widget availableWednesdayField() {
          return BlocBuilder<EmployeeAvailabilityBloc,EmployeeAvailabilityState>(
              buildWhen: (previous, current) => previous.availableWednesday != current.availableWednesday,
              builder: (context, state) {
                return Padding(
                  padding: const EdgeInsets.only(left: 3.0),
                  child: Row(
                    mainAxisAlignment: MainAxisAlignment.spaceBetween,
                    children: <Widget>[
                      Text(S.of(context).pageEntitiesEmployeeAvailabilityAvailableWednesdayField, style: Theme.of(context).textTheme.bodyText1,),
                      Switch(
                          value: state.availableWednesday.value,
                          onChanged: (value) { context.bloc<EmployeeAvailabilityBloc>().add(AvailableWednesdayChanged(availableWednesday: value)); },
                          activeColor: Theme.of(context).primaryColor,),
                    ],
                  ),
                );
              });
        }
        Widget availableThursdayField() {
          return BlocBuilder<EmployeeAvailabilityBloc,EmployeeAvailabilityState>(
              buildWhen: (previous, current) => previous.availableThursday != current.availableThursday,
              builder: (context, state) {
                return Padding(
                  padding: const EdgeInsets.only(left: 3.0),
                  child: Row(
                    mainAxisAlignment: MainAxisAlignment.spaceBetween,
                    children: <Widget>[
                      Text(S.of(context).pageEntitiesEmployeeAvailabilityAvailableThursdayField, style: Theme.of(context).textTheme.bodyText1,),
                      Switch(
                          value: state.availableThursday.value,
                          onChanged: (value) { context.bloc<EmployeeAvailabilityBloc>().add(AvailableThursdayChanged(availableThursday: value)); },
                          activeColor: Theme.of(context).primaryColor,),
                    ],
                  ),
                );
              });
        }
        Widget availableFridayField() {
          return BlocBuilder<EmployeeAvailabilityBloc,EmployeeAvailabilityState>(
              buildWhen: (previous, current) => previous.availableFriday != current.availableFriday,
              builder: (context, state) {
                return Padding(
                  padding: const EdgeInsets.only(left: 3.0),
                  child: Row(
                    mainAxisAlignment: MainAxisAlignment.spaceBetween,
                    children: <Widget>[
                      Text(S.of(context).pageEntitiesEmployeeAvailabilityAvailableFridayField, style: Theme.of(context).textTheme.bodyText1,),
                      Switch(
                          value: state.availableFriday.value,
                          onChanged: (value) { context.bloc<EmployeeAvailabilityBloc>().add(AvailableFridayChanged(availableFriday: value)); },
                          activeColor: Theme.of(context).primaryColor,),
                    ],
                  ),
                );
              });
        }
        Widget availableSaturdayField() {
          return BlocBuilder<EmployeeAvailabilityBloc,EmployeeAvailabilityState>(
              buildWhen: (previous, current) => previous.availableSaturday != current.availableSaturday,
              builder: (context, state) {
                return Padding(
                  padding: const EdgeInsets.only(left: 3.0),
                  child: Row(
                    mainAxisAlignment: MainAxisAlignment.spaceBetween,
                    children: <Widget>[
                      Text(S.of(context).pageEntitiesEmployeeAvailabilityAvailableSaturdayField, style: Theme.of(context).textTheme.bodyText1,),
                      Switch(
                          value: state.availableSaturday.value,
                          onChanged: (value) { context.bloc<EmployeeAvailabilityBloc>().add(AvailableSaturdayChanged(availableSaturday: value)); },
                          activeColor: Theme.of(context).primaryColor,),
                    ],
                  ),
                );
              });
        }
        Widget availableSundayField() {
          return BlocBuilder<EmployeeAvailabilityBloc,EmployeeAvailabilityState>(
              buildWhen: (previous, current) => previous.availableSunday != current.availableSunday,
              builder: (context, state) {
                return Padding(
                  padding: const EdgeInsets.only(left: 3.0),
                  child: Row(
                    mainAxisAlignment: MainAxisAlignment.spaceBetween,
                    children: <Widget>[
                      Text(S.of(context).pageEntitiesEmployeeAvailabilityAvailableSundayField, style: Theme.of(context).textTheme.bodyText1,),
                      Switch(
                          value: state.availableSunday.value,
                          onChanged: (value) { context.bloc<EmployeeAvailabilityBloc>().add(AvailableSundayChanged(availableSunday: value)); },
                          activeColor: Theme.of(context).primaryColor,),
                    ],
                  ),
                );
              });
        }
      Widget preferredShiftField() {
        return BlocBuilder<EmployeeAvailabilityBloc,EmployeeAvailabilityState>(
            buildWhen: (previous, current) => previous.preferredShift != current.preferredShift,
            builder: (context, state) {
              return Padding(
                padding: const EdgeInsets.only(left: 3.0),
                child: Row(
                  mainAxisAlignment: MainAxisAlignment.spaceBetween,
                  children: <Widget>[
                    Text(S.of(context).pageEntitiesEmployeeAvailabilityPreferredShiftField, style: Theme.of(context).textTheme.bodyText1,),
                    DropdownButton<Shift>(
                        value: state.preferredShift.value,
                        onChanged: (value) { context.bloc<EmployeeAvailabilityBloc>().add(PreferredShiftChanged(preferredShift: value)); },
                        items: createDropdownShiftItems(Shift.values)),
                  ],
                ),
              );
            });
      }
        Widget hasExtraDataField() {
          return BlocBuilder<EmployeeAvailabilityBloc,EmployeeAvailabilityState>(
              buildWhen: (previous, current) => previous.hasExtraData != current.hasExtraData,
              builder: (context, state) {
                return Padding(
                  padding: const EdgeInsets.only(left: 3.0),
                  child: Row(
                    mainAxisAlignment: MainAxisAlignment.spaceBetween,
                    children: <Widget>[
                      Text(S.of(context).pageEntitiesEmployeeAvailabilityHasExtraDataField, style: Theme.of(context).textTheme.bodyText1,),
                      Switch(
                          value: state.hasExtraData.value,
                          onChanged: (value) { context.bloc<EmployeeAvailabilityBloc>().add(HasExtraDataChanged(hasExtraData: value)); },
                          activeColor: Theme.of(context).primaryColor,),
                    ],
                  ),
                );
              });
        }
      Widget lastUpdatedDateField() {
        return BlocBuilder<EmployeeAvailabilityBloc, EmployeeAvailabilityState>(
            buildWhen: (previous, current) => previous.lastUpdatedDate != current.lastUpdatedDate,
            builder: (context, state) {
              return DateTimeField(
                controller: context.bloc<EmployeeAvailabilityBloc>().lastUpdatedDateController,
                onChanged: (value) { context.bloc<EmployeeAvailabilityBloc>().add(LastUpdatedDateChanged(lastUpdatedDate: value)); },
                format: DateFormat.yMMMMd(S.of(context).locale),
                keyboardType: TextInputType.datetime,
            decoration: InputDecoration(labelText:S.of(context).pageEntitiesEmployeeAvailabilityLastUpdatedDateField,),
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
        return BlocBuilder<EmployeeAvailabilityBloc, EmployeeAvailabilityState>(
            buildWhen: (previous, current) => previous.clientId != current.clientId,
            builder: (context, state) {
              return TextFormField(
                  controller: context.bloc<EmployeeAvailabilityBloc>().clientIdController,
                  onChanged: (value) { context.bloc<EmployeeAvailabilityBloc>()
                    .add(ClientIdChanged(clientId:int.parse(value))); },
                  keyboardType:TextInputType.number,                  decoration: InputDecoration(
                      labelText:S.of(context).pageEntitiesEmployeeAvailabilityClientIdField));
            }
        );
      }

      List<DropdownMenuItem<Shift>> createDropdownShiftItems(List<Shift> preferredShifts) {
        List<DropdownMenuItem<Shift>> preferredShiftDropDown = [];
    
        for (Shift preferredShift in preferredShifts) {
          DropdownMenuItem<Shift> dropdown = DropdownMenuItem<Shift>(
              value: preferredShift, child: Text(preferredShift.toString()));
              preferredShiftDropDown.add(dropdown);
        }
    
        return preferredShiftDropDown;
      }

  Widget validationZone() {
    return BlocBuilder<EmployeeAvailabilityBloc, EmployeeAvailabilityState>(
        buildWhen:(previous, current) => previous.formStatus != current.formStatus,
        builder: (context, state) {
          return Visibility(
              visible: state.formStatus.isSubmissionFailure ||  state.formStatus.isSubmissionSuccess,
              child: Center(
                child: generateNotificationText(state, context),
              ));
        });
  }

  Widget generateNotificationText(EmployeeAvailabilityState state, BuildContext context) {
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
    return BlocBuilder<EmployeeAvailabilityBloc, EmployeeAvailabilityState>(
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
            onPressed: state.formStatus.isValidated ? () => context.bloc<EmployeeAvailabilityBloc>().add(EmployeeAvailabilityFormSubmitted()) : null,
          );
        }
    );
  }
}
