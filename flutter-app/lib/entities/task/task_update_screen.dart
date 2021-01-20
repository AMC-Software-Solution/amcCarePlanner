import 'package:datetime_picker_formfield/datetime_picker_formfield.dart';
import 'package:intl/intl.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:amcCarePlanner/entities/task/bloc/task_bloc.dart';
import 'package:amcCarePlanner/generated/l10n.dart';
import 'package:flutter/material.dart';
import 'package:formz/formz.dart';
import 'package:amcCarePlanner/shared/repository/http_utils.dart';
import 'package:amcCarePlanner/entities/task/task_model.dart';
import 'task_route.dart';

class TaskUpdateScreen extends StatelessWidget {
  TaskUpdateScreen({Key key}) : super(key: TaskRoutes.editScreenKey);

  @override
  Widget build(BuildContext context) {
    return BlocListener<TaskBloc, TaskState>(
      listener: (context, state) {
        if(state.formStatus.isSubmissionSuccess){
          Navigator.pushNamed(context, TaskRoutes.list);
        }
      },
      child: Scaffold(
          appBar: AppBar(
            centerTitle: true,
            title: BlocBuilder<TaskBloc, TaskState>(
                buildWhen: (previous, current) => previous.editMode != current.editMode,
                builder: (context, state) {
                String title = state.editMode == true ?S.of(context).pageEntitiesTaskUpdateTitle:
S.of(context).pageEntitiesTaskCreateTitle;
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
          taskNameField(),
          descriptionField(),
          dateOfTaskField(),
          startTimeField(),
          endTimeField(),
          statusField(),
          createdDateField(),
          lastUpdatedDateField(),
          clientIdField(),
          hasExtraDataField(),
        validationZone(),
        submit(context)
      ]),
    );
  }

      Widget taskNameField() {
        return BlocBuilder<TaskBloc, TaskState>(
            buildWhen: (previous, current) => previous.taskName != current.taskName,
            builder: (context, state) {
              return TextFormField(
                  controller: context.bloc<TaskBloc>().taskNameController,
                  onChanged: (value) { context.bloc<TaskBloc>()
                    .add(TaskNameChanged(taskName:value)); },
                  keyboardType:TextInputType.text,                  decoration: InputDecoration(
                      labelText:S.of(context).pageEntitiesTaskTaskNameField));
            }
        );
      }
      Widget descriptionField() {
        return BlocBuilder<TaskBloc, TaskState>(
            buildWhen: (previous, current) => previous.description != current.description,
            builder: (context, state) {
              return TextFormField(
                  controller: context.bloc<TaskBloc>().descriptionController,
                  onChanged: (value) { context.bloc<TaskBloc>()
                    .add(DescriptionChanged(description:value)); },
                  keyboardType:TextInputType.text,                  decoration: InputDecoration(
                      labelText:S.of(context).pageEntitiesTaskDescriptionField));
            }
        );
      }
      Widget dateOfTaskField() {
        return BlocBuilder<TaskBloc, TaskState>(
            buildWhen: (previous, current) => previous.dateOfTask != current.dateOfTask,
            builder: (context, state) {
              return DateTimeField(
                controller: context.bloc<TaskBloc>().dateOfTaskController,
                onChanged: (value) { context.bloc<TaskBloc>().add(DateOfTaskChanged(dateOfTask: value)); },
                format: DateFormat.yMMMMd(S.of(context).locale),
                keyboardType: TextInputType.datetime,
            decoration: InputDecoration(labelText:S.of(context).pageEntitiesTaskDateOfTaskField,),
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
        return BlocBuilder<TaskBloc, TaskState>(
            buildWhen: (previous, current) => previous.startTime != current.startTime,
            builder: (context, state) {
              return TextFormField(
                  controller: context.bloc<TaskBloc>().startTimeController,
                  onChanged: (value) { context.bloc<TaskBloc>()
                    .add(StartTimeChanged(startTime:value)); },
                  keyboardType:TextInputType.text,                  decoration: InputDecoration(
                      labelText:S.of(context).pageEntitiesTaskStartTimeField));
            }
        );
      }
      Widget endTimeField() {
        return BlocBuilder<TaskBloc, TaskState>(
            buildWhen: (previous, current) => previous.endTime != current.endTime,
            builder: (context, state) {
              return TextFormField(
                  controller: context.bloc<TaskBloc>().endTimeController,
                  onChanged: (value) { context.bloc<TaskBloc>()
                    .add(EndTimeChanged(endTime:value)); },
                  keyboardType:TextInputType.text,                  decoration: InputDecoration(
                      labelText:S.of(context).pageEntitiesTaskEndTimeField));
            }
        );
      }
      Widget statusField() {
        return BlocBuilder<TaskBloc,TaskState>(
            buildWhen: (previous, current) => previous.status != current.status,
            builder: (context, state) {
              return Padding(
                padding: const EdgeInsets.only(left: 3.0),
                child: Row(
                  mainAxisAlignment: MainAxisAlignment.spaceBetween,
                  children: <Widget>[
                    Text(S.of(context).pageEntitiesTaskStatusField, style: Theme.of(context).textTheme.bodyText1,),
                    DropdownButton<TaskStatus>(
                        value: state.status.value,
                        onChanged: (value) { context.bloc<TaskBloc>().add(StatusChanged(status: value)); },
                        items: createDropdownTaskStatusItems(TaskStatus.values)),
                  ],
                ),
              );
            });
      }
      Widget createdDateField() {
        return BlocBuilder<TaskBloc, TaskState>(
            buildWhen: (previous, current) => previous.createdDate != current.createdDate,
            builder: (context, state) {
              return DateTimeField(
                controller: context.bloc<TaskBloc>().createdDateController,
                onChanged: (value) { context.bloc<TaskBloc>().add(CreatedDateChanged(createdDate: value)); },
                format: DateFormat.yMMMMd(S.of(context).locale),
                keyboardType: TextInputType.datetime,
            decoration: InputDecoration(labelText:S.of(context).pageEntitiesTaskCreatedDateField,),
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
        return BlocBuilder<TaskBloc, TaskState>(
            buildWhen: (previous, current) => previous.lastUpdatedDate != current.lastUpdatedDate,
            builder: (context, state) {
              return DateTimeField(
                controller: context.bloc<TaskBloc>().lastUpdatedDateController,
                onChanged: (value) { context.bloc<TaskBloc>().add(LastUpdatedDateChanged(lastUpdatedDate: value)); },
                format: DateFormat.yMMMMd(S.of(context).locale),
                keyboardType: TextInputType.datetime,
            decoration: InputDecoration(labelText:S.of(context).pageEntitiesTaskLastUpdatedDateField,),
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
        return BlocBuilder<TaskBloc, TaskState>(
            buildWhen: (previous, current) => previous.clientId != current.clientId,
            builder: (context, state) {
              return TextFormField(
                  controller: context.bloc<TaskBloc>().clientIdController,
                  onChanged: (value) { context.bloc<TaskBloc>()
                    .add(ClientIdChanged(clientId:int.parse(value))); },
                  keyboardType:TextInputType.number,                  decoration: InputDecoration(
                      labelText:S.of(context).pageEntitiesTaskClientIdField));
            }
        );
      }
        Widget hasExtraDataField() {
          return BlocBuilder<TaskBloc,TaskState>(
              buildWhen: (previous, current) => previous.hasExtraData != current.hasExtraData,
              builder: (context, state) {
                return Padding(
                  padding: const EdgeInsets.only(left: 3.0),
                  child: Row(
                    mainAxisAlignment: MainAxisAlignment.spaceBetween,
                    children: <Widget>[
                      Text(S.of(context).pageEntitiesTaskHasExtraDataField, style: Theme.of(context).textTheme.bodyText1,),
                      Switch(
                          value: state.hasExtraData.value,
                          onChanged: (value) { context.bloc<TaskBloc>().add(HasExtraDataChanged(hasExtraData: value)); },
                          activeColor: Theme.of(context).primaryColor,),
                    ],
                  ),
                );
              });
        }

      List<DropdownMenuItem<TaskStatus>> createDropdownTaskStatusItems(List<TaskStatus> statuss) {
        List<DropdownMenuItem<TaskStatus>> statusDropDown = [];
    
        for (TaskStatus status in statuss) {
          DropdownMenuItem<TaskStatus> dropdown = DropdownMenuItem<TaskStatus>(
              value: status, child: Text(status.toString()));
              statusDropDown.add(dropdown);
        }
    
        return statusDropDown;
      }

  Widget validationZone() {
    return BlocBuilder<TaskBloc, TaskState>(
        buildWhen:(previous, current) => previous.formStatus != current.formStatus,
        builder: (context, state) {
          return Visibility(
              visible: state.formStatus.isSubmissionFailure ||  state.formStatus.isSubmissionSuccess,
              child: Center(
                child: generateNotificationText(state, context),
              ));
        });
  }

  Widget generateNotificationText(TaskState state, BuildContext context) {
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
    return BlocBuilder<TaskBloc, TaskState>(
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
            onPressed: state.formStatus.isValidated ? () => context.bloc<TaskBloc>().add(TaskFormSubmitted()) : null,
          );
        }
    );
  }
}
