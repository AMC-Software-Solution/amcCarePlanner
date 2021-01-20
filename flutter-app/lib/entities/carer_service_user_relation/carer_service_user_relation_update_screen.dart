import 'package:datetime_picker_formfield/datetime_picker_formfield.dart';
import 'package:intl/intl.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:amcCarePlanner/entities/carer_service_user_relation/bloc/carer_service_user_relation_bloc.dart';
import 'package:amcCarePlanner/generated/l10n.dart';
import 'package:flutter/material.dart';
import 'package:formz/formz.dart';
import 'package:amcCarePlanner/shared/repository/http_utils.dart';
import 'package:amcCarePlanner/entities/carer_service_user_relation/carer_service_user_relation_model.dart';
import 'carer_service_user_relation_route.dart';

class CarerServiceUserRelationUpdateScreen extends StatelessWidget {
  CarerServiceUserRelationUpdateScreen({Key key}) : super(key: CarerServiceUserRelationRoutes.editScreenKey);

  @override
  Widget build(BuildContext context) {
    return BlocListener<CarerServiceUserRelationBloc, CarerServiceUserRelationState>(
      listener: (context, state) {
        if(state.formStatus.isSubmissionSuccess){
          Navigator.pushNamed(context, CarerServiceUserRelationRoutes.list);
        }
      },
      child: Scaffold(
          appBar: AppBar(
            centerTitle: true,
            title: BlocBuilder<CarerServiceUserRelationBloc, CarerServiceUserRelationState>(
                buildWhen: (previous, current) => previous.editMode != current.editMode,
                builder: (context, state) {
                String title = state.editMode == true ?S.of(context).pageEntitiesCarerServiceUserRelationUpdateTitle:
S.of(context).pageEntitiesCarerServiceUserRelationCreateTitle;
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
          reasonField(),
          countField(),
          createdDateField(),
          lastUpdatedDateField(),
          clientIdField(),
          hasExtraDataField(),
        validationZone(),
        submit(context)
      ]),
    );
  }

      Widget reasonField() {
        return BlocBuilder<CarerServiceUserRelationBloc, CarerServiceUserRelationState>(
            buildWhen: (previous, current) => previous.reason != current.reason,
            builder: (context, state) {
              return TextFormField(
                  controller: context.bloc<CarerServiceUserRelationBloc>().reasonController,
                  onChanged: (value) { context.bloc<CarerServiceUserRelationBloc>()
                    .add(ReasonChanged(reason:value)); },
                  keyboardType:TextInputType.text,                  decoration: InputDecoration(
                      labelText:S.of(context).pageEntitiesCarerServiceUserRelationReasonField));
            }
        );
      }
      Widget countField() {
        return BlocBuilder<CarerServiceUserRelationBloc, CarerServiceUserRelationState>(
            buildWhen: (previous, current) => previous.count != current.count,
            builder: (context, state) {
              return TextFormField(
                  controller: context.bloc<CarerServiceUserRelationBloc>().countController,
                  onChanged: (value) { context.bloc<CarerServiceUserRelationBloc>()
                    .add(CountChanged(count:int.parse(value))); },
                  keyboardType:TextInputType.number,                  decoration: InputDecoration(
                      labelText:S.of(context).pageEntitiesCarerServiceUserRelationCountField));
            }
        );
      }
      Widget createdDateField() {
        return BlocBuilder<CarerServiceUserRelationBloc, CarerServiceUserRelationState>(
            buildWhen: (previous, current) => previous.createdDate != current.createdDate,
            builder: (context, state) {
              return DateTimeField(
                controller: context.bloc<CarerServiceUserRelationBloc>().createdDateController,
                onChanged: (value) { context.bloc<CarerServiceUserRelationBloc>().add(CreatedDateChanged(createdDate: value)); },
                format: DateFormat.yMMMMd(S.of(context).locale),
                keyboardType: TextInputType.datetime,
            decoration: InputDecoration(labelText:S.of(context).pageEntitiesCarerServiceUserRelationCreatedDateField,),
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
        return BlocBuilder<CarerServiceUserRelationBloc, CarerServiceUserRelationState>(
            buildWhen: (previous, current) => previous.lastUpdatedDate != current.lastUpdatedDate,
            builder: (context, state) {
              return DateTimeField(
                controller: context.bloc<CarerServiceUserRelationBloc>().lastUpdatedDateController,
                onChanged: (value) { context.bloc<CarerServiceUserRelationBloc>().add(LastUpdatedDateChanged(lastUpdatedDate: value)); },
                format: DateFormat.yMMMMd(S.of(context).locale),
                keyboardType: TextInputType.datetime,
            decoration: InputDecoration(labelText:S.of(context).pageEntitiesCarerServiceUserRelationLastUpdatedDateField,),
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
        return BlocBuilder<CarerServiceUserRelationBloc, CarerServiceUserRelationState>(
            buildWhen: (previous, current) => previous.clientId != current.clientId,
            builder: (context, state) {
              return TextFormField(
                  controller: context.bloc<CarerServiceUserRelationBloc>().clientIdController,
                  onChanged: (value) { context.bloc<CarerServiceUserRelationBloc>()
                    .add(ClientIdChanged(clientId:int.parse(value))); },
                  keyboardType:TextInputType.number,                  decoration: InputDecoration(
                      labelText:S.of(context).pageEntitiesCarerServiceUserRelationClientIdField));
            }
        );
      }
        Widget hasExtraDataField() {
          return BlocBuilder<CarerServiceUserRelationBloc,CarerServiceUserRelationState>(
              buildWhen: (previous, current) => previous.hasExtraData != current.hasExtraData,
              builder: (context, state) {
                return Padding(
                  padding: const EdgeInsets.only(left: 3.0),
                  child: Row(
                    mainAxisAlignment: MainAxisAlignment.spaceBetween,
                    children: <Widget>[
                      Text(S.of(context).pageEntitiesCarerServiceUserRelationHasExtraDataField, style: Theme.of(context).textTheme.bodyText1,),
                      Switch(
                          value: state.hasExtraData.value,
                          onChanged: (value) { context.bloc<CarerServiceUserRelationBloc>().add(HasExtraDataChanged(hasExtraData: value)); },
                          activeColor: Theme.of(context).primaryColor,),
                    ],
                  ),
                );
              });
        }


  Widget validationZone() {
    return BlocBuilder<CarerServiceUserRelationBloc, CarerServiceUserRelationState>(
        buildWhen:(previous, current) => previous.formStatus != current.formStatus,
        builder: (context, state) {
          return Visibility(
              visible: state.formStatus.isSubmissionFailure ||  state.formStatus.isSubmissionSuccess,
              child: Center(
                child: generateNotificationText(state, context),
              ));
        });
  }

  Widget generateNotificationText(CarerServiceUserRelationState state, BuildContext context) {
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
    return BlocBuilder<CarerServiceUserRelationBloc, CarerServiceUserRelationState>(
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
            onPressed: state.formStatus.isValidated ? () => context.bloc<CarerServiceUserRelationBloc>().add(CarerServiceUserRelationFormSubmitted()) : null,
          );
        }
    );
  }
}
