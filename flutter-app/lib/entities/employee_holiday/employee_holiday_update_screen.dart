import 'package:datetime_picker_formfield/datetime_picker_formfield.dart';
import 'package:intl/intl.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:amcCarePlanner/entities/employee_holiday/bloc/employee_holiday_bloc.dart';
import 'package:amcCarePlanner/generated/l10n.dart';
import 'package:flutter/material.dart';
import 'package:formz/formz.dart';
import 'package:amcCarePlanner/shared/repository/http_utils.dart';
import 'package:amcCarePlanner/entities/employee_holiday/employee_holiday_model.dart';
import 'employee_holiday_route.dart';

class EmployeeHolidayUpdateScreen extends StatelessWidget {
  EmployeeHolidayUpdateScreen({Key key}) : super(key: EmployeeHolidayRoutes.editScreenKey);

  @override
  Widget build(BuildContext context) {
    return BlocListener<EmployeeHolidayBloc, EmployeeHolidayState>(
      listener: (context, state) {
        if(state.formStatus.isSubmissionSuccess){
          Navigator.pushNamed(context, EmployeeHolidayRoutes.list);
        }
      },
      child: Scaffold(
          appBar: AppBar(
            centerTitle: true,
            title: BlocBuilder<EmployeeHolidayBloc, EmployeeHolidayState>(
                buildWhen: (previous, current) => previous.editMode != current.editMode,
                builder: (context, state) {
                String title = state.editMode == true ?S.of(context).pageEntitiesEmployeeHolidayUpdateTitle:
S.of(context).pageEntitiesEmployeeHolidayCreateTitle;
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
          startDateField(),
          endDateField(),
          employeeHolidayTypeField(),
          approvedDateField(),
          requestedDateField(),
          holidayStatusField(),
          noteField(),
          rejectionReasonField(),
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
        return BlocBuilder<EmployeeHolidayBloc, EmployeeHolidayState>(
            buildWhen: (previous, current) => previous.description != current.description,
            builder: (context, state) {
              return TextFormField(
                  controller: context.bloc<EmployeeHolidayBloc>().descriptionController,
                  onChanged: (value) { context.bloc<EmployeeHolidayBloc>()
                    .add(DescriptionChanged(description:value)); },
                  keyboardType:TextInputType.text,                  decoration: InputDecoration(
                      labelText:S.of(context).pageEntitiesEmployeeHolidayDescriptionField));
            }
        );
      }
      Widget startDateField() {
        return BlocBuilder<EmployeeHolidayBloc, EmployeeHolidayState>(
            buildWhen: (previous, current) => previous.startDate != current.startDate,
            builder: (context, state) {
              return DateTimeField(
                controller: context.bloc<EmployeeHolidayBloc>().startDateController,
                onChanged: (value) { context.bloc<EmployeeHolidayBloc>().add(StartDateChanged(startDate: value)); },
                format: DateFormat.yMMMMd(S.of(context).locale),
                keyboardType: TextInputType.datetime,
            decoration: InputDecoration(labelText:S.of(context).pageEntitiesEmployeeHolidayStartDateField,),
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
      Widget endDateField() {
        return BlocBuilder<EmployeeHolidayBloc, EmployeeHolidayState>(
            buildWhen: (previous, current) => previous.endDate != current.endDate,
            builder: (context, state) {
              return DateTimeField(
                controller: context.bloc<EmployeeHolidayBloc>().endDateController,
                onChanged: (value) { context.bloc<EmployeeHolidayBloc>().add(EndDateChanged(endDate: value)); },
                format: DateFormat.yMMMMd(S.of(context).locale),
                keyboardType: TextInputType.datetime,
            decoration: InputDecoration(labelText:S.of(context).pageEntitiesEmployeeHolidayEndDateField,),
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
      Widget employeeHolidayTypeField() {
        return BlocBuilder<EmployeeHolidayBloc,EmployeeHolidayState>(
            buildWhen: (previous, current) => previous.employeeHolidayType != current.employeeHolidayType,
            builder: (context, state) {
              return Padding(
                padding: const EdgeInsets.only(left: 3.0),
                child: Row(
                  mainAxisAlignment: MainAxisAlignment.spaceBetween,
                  children: <Widget>[
                    Text(S.of(context).pageEntitiesEmployeeHolidayEmployeeHolidayTypeField, style: Theme.of(context).textTheme.bodyText1,),
                    DropdownButton<EmployeeHolidayType>(
                        value: state.employeeHolidayType.value,
                        onChanged: (value) { context.bloc<EmployeeHolidayBloc>().add(EmployeeHolidayTypeChanged(employeeHolidayType: value)); },
                        items: createDropdownEmployeeHolidayTypeItems(EmployeeHolidayType.values)),
                  ],
                ),
              );
            });
      }
      Widget approvedDateField() {
        return BlocBuilder<EmployeeHolidayBloc, EmployeeHolidayState>(
            buildWhen: (previous, current) => previous.approvedDate != current.approvedDate,
            builder: (context, state) {
              return DateTimeField(
                controller: context.bloc<EmployeeHolidayBloc>().approvedDateController,
                onChanged: (value) { context.bloc<EmployeeHolidayBloc>().add(ApprovedDateChanged(approvedDate: value)); },
                format: DateFormat.yMMMMd(S.of(context).locale),
                keyboardType: TextInputType.datetime,
            decoration: InputDecoration(labelText:S.of(context).pageEntitiesEmployeeHolidayApprovedDateField,),
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
      Widget requestedDateField() {
        return BlocBuilder<EmployeeHolidayBloc, EmployeeHolidayState>(
            buildWhen: (previous, current) => previous.requestedDate != current.requestedDate,
            builder: (context, state) {
              return DateTimeField(
                controller: context.bloc<EmployeeHolidayBloc>().requestedDateController,
                onChanged: (value) { context.bloc<EmployeeHolidayBloc>().add(RequestedDateChanged(requestedDate: value)); },
                format: DateFormat.yMMMMd(S.of(context).locale),
                keyboardType: TextInputType.datetime,
            decoration: InputDecoration(labelText:S.of(context).pageEntitiesEmployeeHolidayRequestedDateField,),
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
      Widget holidayStatusField() {
        return BlocBuilder<EmployeeHolidayBloc,EmployeeHolidayState>(
            buildWhen: (previous, current) => previous.holidayStatus != current.holidayStatus,
            builder: (context, state) {
              return Padding(
                padding: const EdgeInsets.only(left: 3.0),
                child: Row(
                  mainAxisAlignment: MainAxisAlignment.spaceBetween,
                  children: <Widget>[
                    Text(S.of(context).pageEntitiesEmployeeHolidayHolidayStatusField, style: Theme.of(context).textTheme.bodyText1,),
                    DropdownButton<HolidayStatus>(
                        value: state.holidayStatus.value,
                        onChanged: (value) { context.bloc<EmployeeHolidayBloc>().add(HolidayStatusChanged(holidayStatus: value)); },
                        items: createDropdownHolidayStatusItems(HolidayStatus.values)),
                  ],
                ),
              );
            });
      }
      Widget noteField() {
        return BlocBuilder<EmployeeHolidayBloc, EmployeeHolidayState>(
            buildWhen: (previous, current) => previous.note != current.note,
            builder: (context, state) {
              return TextFormField(
                  controller: context.bloc<EmployeeHolidayBloc>().noteController,
                  onChanged: (value) { context.bloc<EmployeeHolidayBloc>()
                    .add(NoteChanged(note:value)); },
                  keyboardType:TextInputType.text,                  decoration: InputDecoration(
                      labelText:S.of(context).pageEntitiesEmployeeHolidayNoteField));
            }
        );
      }
      Widget rejectionReasonField() {
        return BlocBuilder<EmployeeHolidayBloc, EmployeeHolidayState>(
            buildWhen: (previous, current) => previous.rejectionReason != current.rejectionReason,
            builder: (context, state) {
              return TextFormField(
                  controller: context.bloc<EmployeeHolidayBloc>().rejectionReasonController,
                  onChanged: (value) { context.bloc<EmployeeHolidayBloc>()
                    .add(RejectionReasonChanged(rejectionReason:value)); },
                  keyboardType:TextInputType.text,                  decoration: InputDecoration(
                      labelText:S.of(context).pageEntitiesEmployeeHolidayRejectionReasonField));
            }
        );
      }
      Widget createdDateField() {
        return BlocBuilder<EmployeeHolidayBloc, EmployeeHolidayState>(
            buildWhen: (previous, current) => previous.createdDate != current.createdDate,
            builder: (context, state) {
              return DateTimeField(
                controller: context.bloc<EmployeeHolidayBloc>().createdDateController,
                onChanged: (value) { context.bloc<EmployeeHolidayBloc>().add(CreatedDateChanged(createdDate: value)); },
                format: DateFormat.yMMMMd(S.of(context).locale),
                keyboardType: TextInputType.datetime,
            decoration: InputDecoration(labelText:S.of(context).pageEntitiesEmployeeHolidayCreatedDateField,),
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
        return BlocBuilder<EmployeeHolidayBloc, EmployeeHolidayState>(
            buildWhen: (previous, current) => previous.lastUpdatedDate != current.lastUpdatedDate,
            builder: (context, state) {
              return DateTimeField(
                controller: context.bloc<EmployeeHolidayBloc>().lastUpdatedDateController,
                onChanged: (value) { context.bloc<EmployeeHolidayBloc>().add(LastUpdatedDateChanged(lastUpdatedDate: value)); },
                format: DateFormat.yMMMMd(S.of(context).locale),
                keyboardType: TextInputType.datetime,
            decoration: InputDecoration(labelText:S.of(context).pageEntitiesEmployeeHolidayLastUpdatedDateField,),
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
        return BlocBuilder<EmployeeHolidayBloc, EmployeeHolidayState>(
            buildWhen: (previous, current) => previous.clientId != current.clientId,
            builder: (context, state) {
              return TextFormField(
                  controller: context.bloc<EmployeeHolidayBloc>().clientIdController,
                  onChanged: (value) { context.bloc<EmployeeHolidayBloc>()
                    .add(ClientIdChanged(clientId:int.parse(value))); },
                  keyboardType:TextInputType.number,                  decoration: InputDecoration(
                      labelText:S.of(context).pageEntitiesEmployeeHolidayClientIdField));
            }
        );
      }
        Widget hasExtraDataField() {
          return BlocBuilder<EmployeeHolidayBloc,EmployeeHolidayState>(
              buildWhen: (previous, current) => previous.hasExtraData != current.hasExtraData,
              builder: (context, state) {
                return Padding(
                  padding: const EdgeInsets.only(left: 3.0),
                  child: Row(
                    mainAxisAlignment: MainAxisAlignment.spaceBetween,
                    children: <Widget>[
                      Text(S.of(context).pageEntitiesEmployeeHolidayHasExtraDataField, style: Theme.of(context).textTheme.bodyText1,),
                      Switch(
                          value: state.hasExtraData.value,
                          onChanged: (value) { context.bloc<EmployeeHolidayBloc>().add(HasExtraDataChanged(hasExtraData: value)); },
                          activeColor: Theme.of(context).primaryColor,),
                    ],
                  ),
                );
              });
        }

      List<DropdownMenuItem<EmployeeHolidayType>> createDropdownEmployeeHolidayTypeItems(List<EmployeeHolidayType> employeeHolidayTypes) {
        List<DropdownMenuItem<EmployeeHolidayType>> employeeHolidayTypeDropDown = [];
    
        for (EmployeeHolidayType employeeHolidayType in employeeHolidayTypes) {
          DropdownMenuItem<EmployeeHolidayType> dropdown = DropdownMenuItem<EmployeeHolidayType>(
              value: employeeHolidayType, child: Text(employeeHolidayType.toString()));
              employeeHolidayTypeDropDown.add(dropdown);
        }
    
        return employeeHolidayTypeDropDown;
      }
      List<DropdownMenuItem<HolidayStatus>> createDropdownHolidayStatusItems(List<HolidayStatus> holidayStatuss) {
        List<DropdownMenuItem<HolidayStatus>> holidayStatusDropDown = [];
    
        for (HolidayStatus holidayStatus in holidayStatuss) {
          DropdownMenuItem<HolidayStatus> dropdown = DropdownMenuItem<HolidayStatus>(
              value: holidayStatus, child: Text(holidayStatus.toString()));
              holidayStatusDropDown.add(dropdown);
        }
    
        return holidayStatusDropDown;
      }

  Widget validationZone() {
    return BlocBuilder<EmployeeHolidayBloc, EmployeeHolidayState>(
        buildWhen:(previous, current) => previous.formStatus != current.formStatus,
        builder: (context, state) {
          return Visibility(
              visible: state.formStatus.isSubmissionFailure ||  state.formStatus.isSubmissionSuccess,
              child: Center(
                child: generateNotificationText(state, context),
              ));
        });
  }

  Widget generateNotificationText(EmployeeHolidayState state, BuildContext context) {
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
    return BlocBuilder<EmployeeHolidayBloc, EmployeeHolidayState>(
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
            onPressed: state.formStatus.isValidated ? () => context.bloc<EmployeeHolidayBloc>().add(EmployeeHolidayFormSubmitted()) : null,
          );
        }
    );
  }
}
