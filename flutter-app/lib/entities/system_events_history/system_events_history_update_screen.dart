import 'package:datetime_picker_formfield/datetime_picker_formfield.dart';
import 'package:intl/intl.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:amcCarePlanner/entities/system_events_history/bloc/system_events_history_bloc.dart';
import 'package:amcCarePlanner/generated/l10n.dart';
import 'package:flutter/material.dart';
import 'package:formz/formz.dart';
import 'package:amcCarePlanner/shared/repository/http_utils.dart';
import 'package:amcCarePlanner/entities/system_events_history/system_events_history_model.dart';
import 'system_events_history_route.dart';

class SystemEventsHistoryUpdateScreen extends StatelessWidget {
  SystemEventsHistoryUpdateScreen({Key key}) : super(key: SystemEventsHistoryRoutes.editScreenKey);

  @override
  Widget build(BuildContext context) {
    return BlocListener<SystemEventsHistoryBloc, SystemEventsHistoryState>(
      listener: (context, state) {
        if(state.formStatus.isSubmissionSuccess){
          Navigator.pushNamed(context, SystemEventsHistoryRoutes.list);
        }
      },
      child: Scaffold(
          appBar: AppBar(
            centerTitle: true,
            title: BlocBuilder<SystemEventsHistoryBloc, SystemEventsHistoryState>(
                buildWhen: (previous, current) => previous.editMode != current.editMode,
                builder: (context, state) {
                String title = state.editMode == true ?S.of(context).pageEntitiesSystemEventsHistoryUpdateTitle:
S.of(context).pageEntitiesSystemEventsHistoryCreateTitle;
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
          eventNameField(),
          eventDateField(),
          eventApiField(),
          ipAddressField(),
          eventNoteField(),
          eventEntityNameField(),
          eventEntityIdField(),
          isSuspeciousField(),
          callerEmailField(),
          callerIdField(),
          clientIdField(),
        validationZone(),
        submit(context)
      ]),
    );
  }

      Widget eventNameField() {
        return BlocBuilder<SystemEventsHistoryBloc, SystemEventsHistoryState>(
            buildWhen: (previous, current) => previous.eventName != current.eventName,
            builder: (context, state) {
              return TextFormField(
                  controller: context.bloc<SystemEventsHistoryBloc>().eventNameController,
                  onChanged: (value) { context.bloc<SystemEventsHistoryBloc>()
                    .add(EventNameChanged(eventName:value)); },
                  keyboardType:TextInputType.text,                  decoration: InputDecoration(
                      labelText:S.of(context).pageEntitiesSystemEventsHistoryEventNameField));
            }
        );
      }
      Widget eventDateField() {
        return BlocBuilder<SystemEventsHistoryBloc, SystemEventsHistoryState>(
            buildWhen: (previous, current) => previous.eventDate != current.eventDate,
            builder: (context, state) {
              return DateTimeField(
                controller: context.bloc<SystemEventsHistoryBloc>().eventDateController,
                onChanged: (value) { context.bloc<SystemEventsHistoryBloc>().add(EventDateChanged(eventDate: value)); },
                format: DateFormat.yMMMMd(S.of(context).locale),
                keyboardType: TextInputType.datetime,
            decoration: InputDecoration(labelText:S.of(context).pageEntitiesSystemEventsHistoryEventDateField,),
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
      Widget eventApiField() {
        return BlocBuilder<SystemEventsHistoryBloc, SystemEventsHistoryState>(
            buildWhen: (previous, current) => previous.eventApi != current.eventApi,
            builder: (context, state) {
              return TextFormField(
                  controller: context.bloc<SystemEventsHistoryBloc>().eventApiController,
                  onChanged: (value) { context.bloc<SystemEventsHistoryBloc>()
                    .add(EventApiChanged(eventApi:value)); },
                  keyboardType:TextInputType.text,                  decoration: InputDecoration(
                      labelText:S.of(context).pageEntitiesSystemEventsHistoryEventApiField));
            }
        );
      }
      Widget ipAddressField() {
        return BlocBuilder<SystemEventsHistoryBloc, SystemEventsHistoryState>(
            buildWhen: (previous, current) => previous.ipAddress != current.ipAddress,
            builder: (context, state) {
              return TextFormField(
                  controller: context.bloc<SystemEventsHistoryBloc>().ipAddressController,
                  onChanged: (value) { context.bloc<SystemEventsHistoryBloc>()
                    .add(IpAddressChanged(ipAddress:value)); },
                  keyboardType:TextInputType.text,                  decoration: InputDecoration(
                      labelText:S.of(context).pageEntitiesSystemEventsHistoryIpAddressField));
            }
        );
      }
      Widget eventNoteField() {
        return BlocBuilder<SystemEventsHistoryBloc, SystemEventsHistoryState>(
            buildWhen: (previous, current) => previous.eventNote != current.eventNote,
            builder: (context, state) {
              return TextFormField(
                  controller: context.bloc<SystemEventsHistoryBloc>().eventNoteController,
                  onChanged: (value) { context.bloc<SystemEventsHistoryBloc>()
                    .add(EventNoteChanged(eventNote:value)); },
                  keyboardType:TextInputType.text,                  decoration: InputDecoration(
                      labelText:S.of(context).pageEntitiesSystemEventsHistoryEventNoteField));
            }
        );
      }
      Widget eventEntityNameField() {
        return BlocBuilder<SystemEventsHistoryBloc, SystemEventsHistoryState>(
            buildWhen: (previous, current) => previous.eventEntityName != current.eventEntityName,
            builder: (context, state) {
              return TextFormField(
                  controller: context.bloc<SystemEventsHistoryBloc>().eventEntityNameController,
                  onChanged: (value) { context.bloc<SystemEventsHistoryBloc>()
                    .add(EventEntityNameChanged(eventEntityName:value)); },
                  keyboardType:TextInputType.text,                  decoration: InputDecoration(
                      labelText:S.of(context).pageEntitiesSystemEventsHistoryEventEntityNameField));
            }
        );
      }
      Widget eventEntityIdField() {
        return BlocBuilder<SystemEventsHistoryBloc, SystemEventsHistoryState>(
            buildWhen: (previous, current) => previous.eventEntityId != current.eventEntityId,
            builder: (context, state) {
              return TextFormField(
                  controller: context.bloc<SystemEventsHistoryBloc>().eventEntityIdController,
                  onChanged: (value) { context.bloc<SystemEventsHistoryBloc>()
                    .add(EventEntityIdChanged(eventEntityId:int.parse(value))); },
                  keyboardType:TextInputType.number,                  decoration: InputDecoration(
                      labelText:S.of(context).pageEntitiesSystemEventsHistoryEventEntityIdField));
            }
        );
      }
        Widget isSuspeciousField() {
          return BlocBuilder<SystemEventsHistoryBloc,SystemEventsHistoryState>(
              buildWhen: (previous, current) => previous.isSuspecious != current.isSuspecious,
              builder: (context, state) {
                return Padding(
                  padding: const EdgeInsets.only(left: 3.0),
                  child: Row(
                    mainAxisAlignment: MainAxisAlignment.spaceBetween,
                    children: <Widget>[
                      Text(S.of(context).pageEntitiesSystemEventsHistoryIsSuspeciousField, style: Theme.of(context).textTheme.bodyText1,),
                      Switch(
                          value: state.isSuspecious.value,
                          onChanged: (value) { context.bloc<SystemEventsHistoryBloc>().add(IsSuspeciousChanged(isSuspecious: value)); },
                          activeColor: Theme.of(context).primaryColor,),
                    ],
                  ),
                );
              });
        }
      Widget callerEmailField() {
        return BlocBuilder<SystemEventsHistoryBloc, SystemEventsHistoryState>(
            buildWhen: (previous, current) => previous.callerEmail != current.callerEmail,
            builder: (context, state) {
              return TextFormField(
                  controller: context.bloc<SystemEventsHistoryBloc>().callerEmailController,
                  onChanged: (value) { context.bloc<SystemEventsHistoryBloc>()
                    .add(CallerEmailChanged(callerEmail:value)); },
                  keyboardType:TextInputType.text,                  decoration: InputDecoration(
                      labelText:S.of(context).pageEntitiesSystemEventsHistoryCallerEmailField));
            }
        );
      }
      Widget callerIdField() {
        return BlocBuilder<SystemEventsHistoryBloc, SystemEventsHistoryState>(
            buildWhen: (previous, current) => previous.callerId != current.callerId,
            builder: (context, state) {
              return TextFormField(
                  controller: context.bloc<SystemEventsHistoryBloc>().callerIdController,
                  onChanged: (value) { context.bloc<SystemEventsHistoryBloc>()
                    .add(CallerIdChanged(callerId:int.parse(value))); },
                  keyboardType:TextInputType.number,                  decoration: InputDecoration(
                      labelText:S.of(context).pageEntitiesSystemEventsHistoryCallerIdField));
            }
        );
      }
      Widget clientIdField() {
        return BlocBuilder<SystemEventsHistoryBloc, SystemEventsHistoryState>(
            buildWhen: (previous, current) => previous.clientId != current.clientId,
            builder: (context, state) {
              return TextFormField(
                  controller: context.bloc<SystemEventsHistoryBloc>().clientIdController,
                  onChanged: (value) { context.bloc<SystemEventsHistoryBloc>()
                    .add(ClientIdChanged(clientId:int.parse(value))); },
                  keyboardType:TextInputType.number,                  decoration: InputDecoration(
                      labelText:S.of(context).pageEntitiesSystemEventsHistoryClientIdField));
            }
        );
      }


  Widget validationZone() {
    return BlocBuilder<SystemEventsHistoryBloc, SystemEventsHistoryState>(
        buildWhen:(previous, current) => previous.formStatus != current.formStatus,
        builder: (context, state) {
          return Visibility(
              visible: state.formStatus.isSubmissionFailure ||  state.formStatus.isSubmissionSuccess,
              child: Center(
                child: generateNotificationText(state, context),
              ));
        });
  }

  Widget generateNotificationText(SystemEventsHistoryState state, BuildContext context) {
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
    return BlocBuilder<SystemEventsHistoryBloc, SystemEventsHistoryState>(
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
            onPressed: state.formStatus.isValidated ? () => context.bloc<SystemEventsHistoryBloc>().add(SystemEventsHistoryFormSubmitted()) : null,
          );
        }
    );
  }
}
