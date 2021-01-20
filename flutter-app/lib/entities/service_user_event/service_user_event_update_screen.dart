import 'package:datetime_picker_formfield/datetime_picker_formfield.dart';
import 'package:intl/intl.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:amcCarePlanner/entities/service_user_event/bloc/service_user_event_bloc.dart';
import 'package:amcCarePlanner/generated/l10n.dart';
import 'package:flutter/material.dart';
import 'package:formz/formz.dart';
import 'package:amcCarePlanner/shared/repository/http_utils.dart';
import 'package:amcCarePlanner/entities/service_user_event/service_user_event_model.dart';
import 'service_user_event_route.dart';

class ServiceUserEventUpdateScreen extends StatelessWidget {
  ServiceUserEventUpdateScreen({Key key}) : super(key: ServiceUserEventRoutes.editScreenKey);

  @override
  Widget build(BuildContext context) {
    return BlocListener<ServiceUserEventBloc, ServiceUserEventState>(
      listener: (context, state) {
        if(state.formStatus.isSubmissionSuccess){
          Navigator.pushNamed(context, ServiceUserEventRoutes.list);
        }
      },
      child: Scaffold(
          appBar: AppBar(
            centerTitle: true,
            title: BlocBuilder<ServiceUserEventBloc, ServiceUserEventState>(
                buildWhen: (previous, current) => previous.editMode != current.editMode,
                builder: (context, state) {
                String title = state.editMode == true ?S.of(context).pageEntitiesServiceUserEventUpdateTitle:
S.of(context).pageEntitiesServiceUserEventCreateTitle;
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
          eventTitleField(),
          descriptionField(),
          serviceUserEventStatusField(),
          serviceUserEventTypeField(),
          priorityField(),
          noteField(),
          dateOfEventField(),
          createdDateField(),
          lastUpdatedDateField(),
          clientIdField(),
          hasExtraDataField(),
        validationZone(),
        submit(context)
      ]),
    );
  }

      Widget eventTitleField() {
        return BlocBuilder<ServiceUserEventBloc, ServiceUserEventState>(
            buildWhen: (previous, current) => previous.eventTitle != current.eventTitle,
            builder: (context, state) {
              return TextFormField(
                  controller: context.bloc<ServiceUserEventBloc>().eventTitleController,
                  onChanged: (value) { context.bloc<ServiceUserEventBloc>()
                    .add(EventTitleChanged(eventTitle:value)); },
                  keyboardType:TextInputType.text,                  decoration: InputDecoration(
                      labelText:S.of(context).pageEntitiesServiceUserEventEventTitleField));
            }
        );
      }
      Widget descriptionField() {
        return BlocBuilder<ServiceUserEventBloc, ServiceUserEventState>(
            buildWhen: (previous, current) => previous.description != current.description,
            builder: (context, state) {
              return TextFormField(
                  controller: context.bloc<ServiceUserEventBloc>().descriptionController,
                  onChanged: (value) { context.bloc<ServiceUserEventBloc>()
                    .add(DescriptionChanged(description:value)); },
                  keyboardType:TextInputType.text,                  decoration: InputDecoration(
                      labelText:S.of(context).pageEntitiesServiceUserEventDescriptionField));
            }
        );
      }
      Widget serviceUserEventStatusField() {
        return BlocBuilder<ServiceUserEventBloc,ServiceUserEventState>(
            buildWhen: (previous, current) => previous.serviceUserEventStatus != current.serviceUserEventStatus,
            builder: (context, state) {
              return Padding(
                padding: const EdgeInsets.only(left: 3.0),
                child: Row(
                  mainAxisAlignment: MainAxisAlignment.spaceBetween,
                  children: <Widget>[
                    Text(S.of(context).pageEntitiesServiceUserEventServiceUserEventStatusField, style: Theme.of(context).textTheme.bodyText1,),
                    DropdownButton<ServiceUserEventStatus>(
                        value: state.serviceUserEventStatus.value,
                        onChanged: (value) { context.bloc<ServiceUserEventBloc>().add(ServiceUserEventStatusChanged(serviceUserEventStatus: value)); },
                        items: createDropdownServiceUserEventStatusItems(ServiceUserEventStatus.values)),
                  ],
                ),
              );
            });
      }
      Widget serviceUserEventTypeField() {
        return BlocBuilder<ServiceUserEventBloc,ServiceUserEventState>(
            buildWhen: (previous, current) => previous.serviceUserEventType != current.serviceUserEventType,
            builder: (context, state) {
              return Padding(
                padding: const EdgeInsets.only(left: 3.0),
                child: Row(
                  mainAxisAlignment: MainAxisAlignment.spaceBetween,
                  children: <Widget>[
                    Text(S.of(context).pageEntitiesServiceUserEventServiceUserEventTypeField, style: Theme.of(context).textTheme.bodyText1,),
                    DropdownButton<ServiceUserEventType>(
                        value: state.serviceUserEventType.value,
                        onChanged: (value) { context.bloc<ServiceUserEventBloc>().add(ServiceUserEventTypeChanged(serviceUserEventType: value)); },
                        items: createDropdownServiceUserEventTypeItems(ServiceUserEventType.values)),
                  ],
                ),
              );
            });
      }
      Widget priorityField() {
        return BlocBuilder<ServiceUserEventBloc,ServiceUserEventState>(
            buildWhen: (previous, current) => previous.priority != current.priority,
            builder: (context, state) {
              return Padding(
                padding: const EdgeInsets.only(left: 3.0),
                child: Row(
                  mainAxisAlignment: MainAxisAlignment.spaceBetween,
                  children: <Widget>[
                    Text(S.of(context).pageEntitiesServiceUserEventPriorityField, style: Theme.of(context).textTheme.bodyText1,),
                    DropdownButton<Priority>(
                        value: state.priority.value,
                        onChanged: (value) { context.bloc<ServiceUserEventBloc>().add(PriorityChanged(priority: value)); },
                        items: createDropdownPriorityItems(Priority.values)),
                  ],
                ),
              );
            });
      }
      Widget noteField() {
        return BlocBuilder<ServiceUserEventBloc, ServiceUserEventState>(
            buildWhen: (previous, current) => previous.note != current.note,
            builder: (context, state) {
              return TextFormField(
                  controller: context.bloc<ServiceUserEventBloc>().noteController,
                  onChanged: (value) { context.bloc<ServiceUserEventBloc>()
                    .add(NoteChanged(note:value)); },
                  keyboardType:TextInputType.text,                  decoration: InputDecoration(
                      labelText:S.of(context).pageEntitiesServiceUserEventNoteField));
            }
        );
      }
      Widget dateOfEventField() {
        return BlocBuilder<ServiceUserEventBloc, ServiceUserEventState>(
            buildWhen: (previous, current) => previous.dateOfEvent != current.dateOfEvent,
            builder: (context, state) {
              return DateTimeField(
                controller: context.bloc<ServiceUserEventBloc>().dateOfEventController,
                onChanged: (value) { context.bloc<ServiceUserEventBloc>().add(DateOfEventChanged(dateOfEvent: value)); },
                format: DateFormat.yMMMMd(S.of(context).locale),
                keyboardType: TextInputType.datetime,
            decoration: InputDecoration(labelText:S.of(context).pageEntitiesServiceUserEventDateOfEventField,),
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
        return BlocBuilder<ServiceUserEventBloc, ServiceUserEventState>(
            buildWhen: (previous, current) => previous.createdDate != current.createdDate,
            builder: (context, state) {
              return DateTimeField(
                controller: context.bloc<ServiceUserEventBloc>().createdDateController,
                onChanged: (value) { context.bloc<ServiceUserEventBloc>().add(CreatedDateChanged(createdDate: value)); },
                format: DateFormat.yMMMMd(S.of(context).locale),
                keyboardType: TextInputType.datetime,
            decoration: InputDecoration(labelText:S.of(context).pageEntitiesServiceUserEventCreatedDateField,),
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
        return BlocBuilder<ServiceUserEventBloc, ServiceUserEventState>(
            buildWhen: (previous, current) => previous.lastUpdatedDate != current.lastUpdatedDate,
            builder: (context, state) {
              return DateTimeField(
                controller: context.bloc<ServiceUserEventBloc>().lastUpdatedDateController,
                onChanged: (value) { context.bloc<ServiceUserEventBloc>().add(LastUpdatedDateChanged(lastUpdatedDate: value)); },
                format: DateFormat.yMMMMd(S.of(context).locale),
                keyboardType: TextInputType.datetime,
            decoration: InputDecoration(labelText:S.of(context).pageEntitiesServiceUserEventLastUpdatedDateField,),
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
        return BlocBuilder<ServiceUserEventBloc, ServiceUserEventState>(
            buildWhen: (previous, current) => previous.clientId != current.clientId,
            builder: (context, state) {
              return TextFormField(
                  controller: context.bloc<ServiceUserEventBloc>().clientIdController,
                  onChanged: (value) { context.bloc<ServiceUserEventBloc>()
                    .add(ClientIdChanged(clientId:int.parse(value))); },
                  keyboardType:TextInputType.number,                  decoration: InputDecoration(
                      labelText:S.of(context).pageEntitiesServiceUserEventClientIdField));
            }
        );
      }
        Widget hasExtraDataField() {
          return BlocBuilder<ServiceUserEventBloc,ServiceUserEventState>(
              buildWhen: (previous, current) => previous.hasExtraData != current.hasExtraData,
              builder: (context, state) {
                return Padding(
                  padding: const EdgeInsets.only(left: 3.0),
                  child: Row(
                    mainAxisAlignment: MainAxisAlignment.spaceBetween,
                    children: <Widget>[
                      Text(S.of(context).pageEntitiesServiceUserEventHasExtraDataField, style: Theme.of(context).textTheme.bodyText1,),
                      Switch(
                          value: state.hasExtraData.value,
                          onChanged: (value) { context.bloc<ServiceUserEventBloc>().add(HasExtraDataChanged(hasExtraData: value)); },
                          activeColor: Theme.of(context).primaryColor,),
                    ],
                  ),
                );
              });
        }

      List<DropdownMenuItem<ServiceUserEventStatus>> createDropdownServiceUserEventStatusItems(List<ServiceUserEventStatus> serviceUserEventStatuss) {
        List<DropdownMenuItem<ServiceUserEventStatus>> serviceUserEventStatusDropDown = [];
    
        for (ServiceUserEventStatus serviceUserEventStatus in serviceUserEventStatuss) {
          DropdownMenuItem<ServiceUserEventStatus> dropdown = DropdownMenuItem<ServiceUserEventStatus>(
              value: serviceUserEventStatus, child: Text(serviceUserEventStatus.toString()));
              serviceUserEventStatusDropDown.add(dropdown);
        }
    
        return serviceUserEventStatusDropDown;
      }
      List<DropdownMenuItem<ServiceUserEventType>> createDropdownServiceUserEventTypeItems(List<ServiceUserEventType> serviceUserEventTypes) {
        List<DropdownMenuItem<ServiceUserEventType>> serviceUserEventTypeDropDown = [];
    
        for (ServiceUserEventType serviceUserEventType in serviceUserEventTypes) {
          DropdownMenuItem<ServiceUserEventType> dropdown = DropdownMenuItem<ServiceUserEventType>(
              value: serviceUserEventType, child: Text(serviceUserEventType.toString()));
              serviceUserEventTypeDropDown.add(dropdown);
        }
    
        return serviceUserEventTypeDropDown;
      }
      List<DropdownMenuItem<Priority>> createDropdownPriorityItems(List<Priority> prioritys) {
        List<DropdownMenuItem<Priority>> priorityDropDown = [];
    
        for (Priority priority in prioritys) {
          DropdownMenuItem<Priority> dropdown = DropdownMenuItem<Priority>(
              value: priority, child: Text(priority.toString()));
              priorityDropDown.add(dropdown);
        }
    
        return priorityDropDown;
      }

  Widget validationZone() {
    return BlocBuilder<ServiceUserEventBloc, ServiceUserEventState>(
        buildWhen:(previous, current) => previous.formStatus != current.formStatus,
        builder: (context, state) {
          return Visibility(
              visible: state.formStatus.isSubmissionFailure ||  state.formStatus.isSubmissionSuccess,
              child: Center(
                child: generateNotificationText(state, context),
              ));
        });
  }

  Widget generateNotificationText(ServiceUserEventState state, BuildContext context) {
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
    return BlocBuilder<ServiceUserEventBloc, ServiceUserEventState>(
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
            onPressed: state.formStatus.isValidated ? () => context.bloc<ServiceUserEventBloc>().add(ServiceUserEventFormSubmitted()) : null,
          );
        }
    );
  }
}
