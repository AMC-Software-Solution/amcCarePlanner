import 'package:datetime_picker_formfield/datetime_picker_formfield.dart';
import 'package:intl/intl.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:amcCarePlanner/entities/notifications/bloc/notifications_bloc.dart';
import 'package:amcCarePlanner/generated/l10n.dart';
import 'package:flutter/material.dart';
import 'package:formz/formz.dart';
import 'package:amcCarePlanner/shared/repository/http_utils.dart';
import 'package:amcCarePlanner/entities/notifications/notifications_model.dart';
import 'notifications_route.dart';

class NotificationsUpdateScreen extends StatelessWidget {
  NotificationsUpdateScreen({Key key}) : super(key: NotificationsRoutes.editScreenKey);

  @override
  Widget build(BuildContext context) {
    return BlocListener<NotificationsBloc, NotificationsState>(
      listener: (context, state) {
        if(state.formStatus.isSubmissionSuccess){
          Navigator.pushNamed(context, NotificationsRoutes.list);
        }
      },
      child: Scaffold(
          appBar: AppBar(
            centerTitle: true,
            title: BlocBuilder<NotificationsBloc, NotificationsState>(
                buildWhen: (previous, current) => previous.editMode != current.editMode,
                builder: (context, state) {
                String title = state.editMode == true ?S.of(context).pageEntitiesNotificationsUpdateTitle:
S.of(context).pageEntitiesNotificationsCreateTitle;
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
          bodyField(),
          notificationDateField(),
          imageUrlField(),
          senderIdField(),
          receiverIdField(),
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
        return BlocBuilder<NotificationsBloc, NotificationsState>(
            buildWhen: (previous, current) => previous.title != current.title,
            builder: (context, state) {
              return TextFormField(
                  controller: context.bloc<NotificationsBloc>().titleController,
                  onChanged: (value) { context.bloc<NotificationsBloc>()
                    .add(TitleChanged(title:value)); },
                  keyboardType:TextInputType.text,                  decoration: InputDecoration(
                      labelText:S.of(context).pageEntitiesNotificationsTitleField));
            }
        );
      }
      Widget bodyField() {
        return BlocBuilder<NotificationsBloc, NotificationsState>(
            buildWhen: (previous, current) => previous.body != current.body,
            builder: (context, state) {
              return TextFormField(
                  controller: context.bloc<NotificationsBloc>().bodyController,
                  onChanged: (value) { context.bloc<NotificationsBloc>()
                    .add(BodyChanged(body:value)); },
                  keyboardType:TextInputType.text,                  decoration: InputDecoration(
                      labelText:S.of(context).pageEntitiesNotificationsBodyField));
            }
        );
      }
      Widget notificationDateField() {
        return BlocBuilder<NotificationsBloc, NotificationsState>(
            buildWhen: (previous, current) => previous.notificationDate != current.notificationDate,
            builder: (context, state) {
              return DateTimeField(
                controller: context.bloc<NotificationsBloc>().notificationDateController,
                onChanged: (value) { context.bloc<NotificationsBloc>().add(NotificationDateChanged(notificationDate: value)); },
                format: DateFormat.yMMMMd(S.of(context).locale),
                keyboardType: TextInputType.datetime,
            decoration: InputDecoration(labelText:S.of(context).pageEntitiesNotificationsNotificationDateField,),
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
      Widget imageUrlField() {
        return BlocBuilder<NotificationsBloc, NotificationsState>(
            buildWhen: (previous, current) => previous.imageUrl != current.imageUrl,
            builder: (context, state) {
              return TextFormField(
                  controller: context.bloc<NotificationsBloc>().imageUrlController,
                  onChanged: (value) { context.bloc<NotificationsBloc>()
                    .add(ImageUrlChanged(imageUrl:value)); },
                  keyboardType:TextInputType.text,                  decoration: InputDecoration(
                      labelText:S.of(context).pageEntitiesNotificationsImageUrlField));
            }
        );
      }
      Widget senderIdField() {
        return BlocBuilder<NotificationsBloc, NotificationsState>(
            buildWhen: (previous, current) => previous.senderId != current.senderId,
            builder: (context, state) {
              return TextFormField(
                  controller: context.bloc<NotificationsBloc>().senderIdController,
                  onChanged: (value) { context.bloc<NotificationsBloc>()
                    .add(SenderIdChanged(senderId:int.parse(value))); },
                  keyboardType:TextInputType.number,                  decoration: InputDecoration(
                      labelText:S.of(context).pageEntitiesNotificationsSenderIdField));
            }
        );
      }
      Widget receiverIdField() {
        return BlocBuilder<NotificationsBloc, NotificationsState>(
            buildWhen: (previous, current) => previous.receiverId != current.receiverId,
            builder: (context, state) {
              return TextFormField(
                  controller: context.bloc<NotificationsBloc>().receiverIdController,
                  onChanged: (value) { context.bloc<NotificationsBloc>()
                    .add(ReceiverIdChanged(receiverId:int.parse(value))); },
                  keyboardType:TextInputType.number,                  decoration: InputDecoration(
                      labelText:S.of(context).pageEntitiesNotificationsReceiverIdField));
            }
        );
      }
      Widget createdDateField() {
        return BlocBuilder<NotificationsBloc, NotificationsState>(
            buildWhen: (previous, current) => previous.createdDate != current.createdDate,
            builder: (context, state) {
              return DateTimeField(
                controller: context.bloc<NotificationsBloc>().createdDateController,
                onChanged: (value) { context.bloc<NotificationsBloc>().add(CreatedDateChanged(createdDate: value)); },
                format: DateFormat.yMMMMd(S.of(context).locale),
                keyboardType: TextInputType.datetime,
            decoration: InputDecoration(labelText:S.of(context).pageEntitiesNotificationsCreatedDateField,),
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
        return BlocBuilder<NotificationsBloc, NotificationsState>(
            buildWhen: (previous, current) => previous.lastUpdatedDate != current.lastUpdatedDate,
            builder: (context, state) {
              return DateTimeField(
                controller: context.bloc<NotificationsBloc>().lastUpdatedDateController,
                onChanged: (value) { context.bloc<NotificationsBloc>().add(LastUpdatedDateChanged(lastUpdatedDate: value)); },
                format: DateFormat.yMMMMd(S.of(context).locale),
                keyboardType: TextInputType.datetime,
            decoration: InputDecoration(labelText:S.of(context).pageEntitiesNotificationsLastUpdatedDateField,),
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
        return BlocBuilder<NotificationsBloc, NotificationsState>(
            buildWhen: (previous, current) => previous.clientId != current.clientId,
            builder: (context, state) {
              return TextFormField(
                  controller: context.bloc<NotificationsBloc>().clientIdController,
                  onChanged: (value) { context.bloc<NotificationsBloc>()
                    .add(ClientIdChanged(clientId:int.parse(value))); },
                  keyboardType:TextInputType.number,                  decoration: InputDecoration(
                      labelText:S.of(context).pageEntitiesNotificationsClientIdField));
            }
        );
      }
        Widget hasExtraDataField() {
          return BlocBuilder<NotificationsBloc,NotificationsState>(
              buildWhen: (previous, current) => previous.hasExtraData != current.hasExtraData,
              builder: (context, state) {
                return Padding(
                  padding: const EdgeInsets.only(left: 3.0),
                  child: Row(
                    mainAxisAlignment: MainAxisAlignment.spaceBetween,
                    children: <Widget>[
                      Text(S.of(context).pageEntitiesNotificationsHasExtraDataField, style: Theme.of(context).textTheme.bodyText1,),
                      Switch(
                          value: state.hasExtraData.value,
                          onChanged: (value) { context.bloc<NotificationsBloc>().add(HasExtraDataChanged(hasExtraData: value)); },
                          activeColor: Theme.of(context).primaryColor,),
                    ],
                  ),
                );
              });
        }


  Widget validationZone() {
    return BlocBuilder<NotificationsBloc, NotificationsState>(
        buildWhen:(previous, current) => previous.formStatus != current.formStatus,
        builder: (context, state) {
          return Visibility(
              visible: state.formStatus.isSubmissionFailure ||  state.formStatus.isSubmissionSuccess,
              child: Center(
                child: generateNotificationText(state, context),
              ));
        });
  }

  Widget generateNotificationText(NotificationsState state, BuildContext context) {
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
    return BlocBuilder<NotificationsBloc, NotificationsState>(
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
            onPressed: state.formStatus.isValidated ? () => context.bloc<NotificationsBloc>().add(NotificationsFormSubmitted()) : null,
          );
        }
    );
  }
}
