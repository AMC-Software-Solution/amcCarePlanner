import 'package:datetime_picker_formfield/datetime_picker_formfield.dart';
import 'package:intl/intl.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:amcCarePlanner/entities/timesheet/bloc/timesheet_bloc.dart';
import 'package:amcCarePlanner/generated/l10n.dart';
import 'package:flutter/material.dart';
import 'package:formz/formz.dart';
import 'package:amcCarePlanner/shared/repository/http_utils.dart';
import 'package:amcCarePlanner/entities/timesheet/timesheet_model.dart';
import 'timesheet_route.dart';

class TimesheetUpdateScreen extends StatelessWidget {
  TimesheetUpdateScreen({Key key}) : super(key: TimesheetRoutes.editScreenKey);

  @override
  Widget build(BuildContext context) {
    return BlocListener<TimesheetBloc, TimesheetState>(
      listener: (context, state) {
        if(state.formStatus.isSubmissionSuccess){
          Navigator.pushNamed(context, TimesheetRoutes.list);
        }
      },
      child: Scaffold(
          appBar: AppBar(
            centerTitle: true,
            title: BlocBuilder<TimesheetBloc, TimesheetState>(
                buildWhen: (previous, current) => previous.editMode != current.editMode,
                builder: (context, state) {
                String title = state.editMode == true ?S.of(context).pageEntitiesTimesheetUpdateTitle:
S.of(context).pageEntitiesTimesheetCreateTitle;
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
          descriptionField(),
          timesheetDateField(),
          startTimeField(),
          endTimeField(),
          hoursWorkedField(),
          breakStartTimeField(),
          breakEndTimeField(),
          noteField(),
          createdDateField(),
          lastUpdatedDateField(),
          clientIdField(),
          hasExtraDataField(),
        validationZone(),
        submit(context)
      ]),
    );
  }

      Widget descriptionField() {
        return BlocBuilder<TimesheetBloc, TimesheetState>(
            buildWhen: (previous, current) => previous.description != current.description,
            builder: (context, state) {
              return TextFormField(
                  controller: context.bloc<TimesheetBloc>().descriptionController,
                  onChanged: (value) { context.bloc<TimesheetBloc>()
                    .add(DescriptionChanged(description:value)); },
                  keyboardType:TextInputType.text,                  decoration: InputDecoration(
                      labelText:S.of(context).pageEntitiesTimesheetDescriptionField));
            }
        );
      }
      Widget timesheetDateField() {
        return BlocBuilder<TimesheetBloc, TimesheetState>(
            buildWhen: (previous, current) => previous.timesheetDate != current.timesheetDate,
            builder: (context, state) {
              return DateTimeField(
                controller: context.bloc<TimesheetBloc>().timesheetDateController,
                onChanged: (value) { context.bloc<TimesheetBloc>().add(TimesheetDateChanged(timesheetDate: value)); },
                format: DateFormat.yMMMMd(S.of(context).locale),
                keyboardType: TextInputType.datetime,
            decoration: InputDecoration(labelText:S.of(context).pageEntitiesTimesheetTimesheetDateField,),
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
      Widget startTimeField() {
        return BlocBuilder<TimesheetBloc, TimesheetState>(
            buildWhen: (previous, current) => previous.startTime != current.startTime,
            builder: (context, state) {
              return TextFormField(
                  controller: context.bloc<TimesheetBloc>().startTimeController,
                  onChanged: (value) { context.bloc<TimesheetBloc>()
                    .add(StartTimeChanged(startTime:value)); },
                  keyboardType:TextInputType.text,                  decoration: InputDecoration(
                      labelText:S.of(context).pageEntitiesTimesheetStartTimeField));
            }
        );
      }
      Widget endTimeField() {
        return BlocBuilder<TimesheetBloc, TimesheetState>(
            buildWhen: (previous, current) => previous.endTime != current.endTime,
            builder: (context, state) {
              return TextFormField(
                  controller: context.bloc<TimesheetBloc>().endTimeController,
                  onChanged: (value) { context.bloc<TimesheetBloc>()
                    .add(EndTimeChanged(endTime:value)); },
                  keyboardType:TextInputType.text,                  decoration: InputDecoration(
                      labelText:S.of(context).pageEntitiesTimesheetEndTimeField));
            }
        );
      }
      Widget hoursWorkedField() {
        return BlocBuilder<TimesheetBloc, TimesheetState>(
            buildWhen: (previous, current) => previous.hoursWorked != current.hoursWorked,
            builder: (context, state) {
              return TextFormField(
                  controller: context.bloc<TimesheetBloc>().hoursWorkedController,
                  onChanged: (value) { context.bloc<TimesheetBloc>()
                    .add(HoursWorkedChanged(hoursWorked:int.parse(value))); },
                  keyboardType:TextInputType.number,                  decoration: InputDecoration(
                      labelText:S.of(context).pageEntitiesTimesheetHoursWorkedField));
            }
        );
      }
      Widget breakStartTimeField() {
        return BlocBuilder<TimesheetBloc, TimesheetState>(
            buildWhen: (previous, current) => previous.breakStartTime != current.breakStartTime,
            builder: (context, state) {
              return TextFormField(
                  controller: context.bloc<TimesheetBloc>().breakStartTimeController,
                  onChanged: (value) { context.bloc<TimesheetBloc>()
                    .add(BreakStartTimeChanged(breakStartTime:value)); },
                  keyboardType:TextInputType.text,                  decoration: InputDecoration(
                      labelText:S.of(context).pageEntitiesTimesheetBreakStartTimeField));
            }
        );
      }
      Widget breakEndTimeField() {
        return BlocBuilder<TimesheetBloc, TimesheetState>(
            buildWhen: (previous, current) => previous.breakEndTime != current.breakEndTime,
            builder: (context, state) {
              return TextFormField(
                  controller: context.bloc<TimesheetBloc>().breakEndTimeController,
                  onChanged: (value) { context.bloc<TimesheetBloc>()
                    .add(BreakEndTimeChanged(breakEndTime:value)); },
                  keyboardType:TextInputType.text,                  decoration: InputDecoration(
                      labelText:S.of(context).pageEntitiesTimesheetBreakEndTimeField));
            }
        );
      }
      Widget noteField() {
        return BlocBuilder<TimesheetBloc, TimesheetState>(
            buildWhen: (previous, current) => previous.note != current.note,
            builder: (context, state) {
              return TextFormField(
                  controller: context.bloc<TimesheetBloc>().noteController,
                  onChanged: (value) { context.bloc<TimesheetBloc>()
                    .add(NoteChanged(note:value)); },
                  keyboardType:TextInputType.text,                  decoration: InputDecoration(
                      labelText:S.of(context).pageEntitiesTimesheetNoteField));
            }
        );
      }
      Widget createdDateField() {
        return BlocBuilder<TimesheetBloc, TimesheetState>(
            buildWhen: (previous, current) => previous.createdDate != current.createdDate,
            builder: (context, state) {
              return DateTimeField(
                controller: context.bloc<TimesheetBloc>().createdDateController,
                onChanged: (value) { context.bloc<TimesheetBloc>().add(CreatedDateChanged(createdDate: value)); },
                format: DateFormat.yMMMMd(S.of(context).locale),
                keyboardType: TextInputType.datetime,
            decoration: InputDecoration(labelText:S.of(context).pageEntitiesTimesheetCreatedDateField,),
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
        return BlocBuilder<TimesheetBloc, TimesheetState>(
            buildWhen: (previous, current) => previous.lastUpdatedDate != current.lastUpdatedDate,
            builder: (context, state) {
              return DateTimeField(
                controller: context.bloc<TimesheetBloc>().lastUpdatedDateController,
                onChanged: (value) { context.bloc<TimesheetBloc>().add(LastUpdatedDateChanged(lastUpdatedDate: value)); },
                format: DateFormat.yMMMMd(S.of(context).locale),
                keyboardType: TextInputType.datetime,
            decoration: InputDecoration(labelText:S.of(context).pageEntitiesTimesheetLastUpdatedDateField,),
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
        return BlocBuilder<TimesheetBloc, TimesheetState>(
            buildWhen: (previous, current) => previous.clientId != current.clientId,
            builder: (context, state) {
              return TextFormField(
                  controller: context.bloc<TimesheetBloc>().clientIdController,
                  onChanged: (value) { context.bloc<TimesheetBloc>()
                    .add(ClientIdChanged(clientId:int.parse(value))); },
                  keyboardType:TextInputType.number,                  decoration: InputDecoration(
                      labelText:S.of(context).pageEntitiesTimesheetClientIdField));
            }
        );
      }
        Widget hasExtraDataField() {
          return BlocBuilder<TimesheetBloc,TimesheetState>(
              buildWhen: (previous, current) => previous.hasExtraData != current.hasExtraData,
              builder: (context, state) {
                return Padding(
                  padding: const EdgeInsets.only(left: 3.0),
                  child: Row(
                    mainAxisAlignment: MainAxisAlignment.spaceBetween,
                    children: <Widget>[
                      Text(S.of(context).pageEntitiesTimesheetHasExtraDataField, style: Theme.of(context).textTheme.bodyText1,),
                      Switch(
                          value: state.hasExtraData.value,
                          onChanged: (value) { context.bloc<TimesheetBloc>().add(HasExtraDataChanged(hasExtraData: value)); },
                          activeColor: Theme.of(context).primaryColor,),
                    ],
                  ),
                );
              });
        }


  Widget validationZone() {
    return BlocBuilder<TimesheetBloc, TimesheetState>(
        buildWhen:(previous, current) => previous.formStatus != current.formStatus,
        builder: (context, state) {
          return Visibility(
              visible: state.formStatus.isSubmissionFailure ||  state.formStatus.isSubmissionSuccess,
              child: Center(
                child: generateNotificationText(state, context),
              ));
        });
  }

  Widget generateNotificationText(TimesheetState state, BuildContext context) {
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
    return BlocBuilder<TimesheetBloc, TimesheetState>(
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
            onPressed: state.formStatus.isValidated ? () => context.bloc<TimesheetBloc>().add(TimesheetFormSubmitted()) : null,
          );
        }
    );
  }
}
