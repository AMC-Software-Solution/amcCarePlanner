import 'package:datetime_picker_formfield/datetime_picker_formfield.dart';
import 'package:intl/intl.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:amcCarePlanner/entities/service_user_location/bloc/service_user_location_bloc.dart';
import 'package:amcCarePlanner/generated/l10n.dart';
import 'package:flutter/material.dart';
import 'package:formz/formz.dart';
import 'package:amcCarePlanner/shared/repository/http_utils.dart';
import 'package:amcCarePlanner/entities/service_user_location/service_user_location_model.dart';
import 'service_user_location_route.dart';

class ServiceUserLocationUpdateScreen extends StatelessWidget {
  ServiceUserLocationUpdateScreen({Key key}) : super(key: ServiceUserLocationRoutes.editScreenKey);

  @override
  Widget build(BuildContext context) {
    return BlocListener<ServiceUserLocationBloc, ServiceUserLocationState>(
      listener: (context, state) {
        if(state.formStatus.isSubmissionSuccess){
          Navigator.pushNamed(context, ServiceUserLocationRoutes.list);
        }
      },
      child: Scaffold(
          appBar: AppBar(
            centerTitle: true,
            title: BlocBuilder<ServiceUserLocationBloc, ServiceUserLocationState>(
                buildWhen: (previous, current) => previous.editMode != current.editMode,
                builder: (context, state) {
                String title = state.editMode == true ?S.of(context).pageEntitiesServiceUserLocationUpdateTitle:
S.of(context).pageEntitiesServiceUserLocationCreateTitle;
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
          latitudeField(),
          longitudeField(),
          createdDateField(),
          lastUpdatedDateField(),
          clientIdField(),
          hasExtraDataField(),
        validationZone(),
        submit(context)
      ]),
    );
  }

      Widget latitudeField() {
        return BlocBuilder<ServiceUserLocationBloc, ServiceUserLocationState>(
            buildWhen: (previous, current) => previous.latitude != current.latitude,
            builder: (context, state) {
              return TextFormField(
                  controller: context.bloc<ServiceUserLocationBloc>().latitudeController,
                  onChanged: (value) { context.bloc<ServiceUserLocationBloc>()
                    .add(LatitudeChanged(latitude:value)); },
                  keyboardType:TextInputType.text,                  decoration: InputDecoration(
                      labelText:S.of(context).pageEntitiesServiceUserLocationLatitudeField));
            }
        );
      }
      Widget longitudeField() {
        return BlocBuilder<ServiceUserLocationBloc, ServiceUserLocationState>(
            buildWhen: (previous, current) => previous.longitude != current.longitude,
            builder: (context, state) {
              return TextFormField(
                  controller: context.bloc<ServiceUserLocationBloc>().longitudeController,
                  onChanged: (value) { context.bloc<ServiceUserLocationBloc>()
                    .add(LongitudeChanged(longitude:value)); },
                  keyboardType:TextInputType.text,                  decoration: InputDecoration(
                      labelText:S.of(context).pageEntitiesServiceUserLocationLongitudeField));
            }
        );
      }
      Widget createdDateField() {
        return BlocBuilder<ServiceUserLocationBloc, ServiceUserLocationState>(
            buildWhen: (previous, current) => previous.createdDate != current.createdDate,
            builder: (context, state) {
              return DateTimeField(
                controller: context.bloc<ServiceUserLocationBloc>().createdDateController,
                onChanged: (value) { context.bloc<ServiceUserLocationBloc>().add(CreatedDateChanged(createdDate: value)); },
                format: DateFormat.yMMMMd(S.of(context).locale),
                keyboardType: TextInputType.datetime,
            decoration: InputDecoration(labelText:S.of(context).pageEntitiesServiceUserLocationCreatedDateField,),
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
        return BlocBuilder<ServiceUserLocationBloc, ServiceUserLocationState>(
            buildWhen: (previous, current) => previous.lastUpdatedDate != current.lastUpdatedDate,
            builder: (context, state) {
              return DateTimeField(
                controller: context.bloc<ServiceUserLocationBloc>().lastUpdatedDateController,
                onChanged: (value) { context.bloc<ServiceUserLocationBloc>().add(LastUpdatedDateChanged(lastUpdatedDate: value)); },
                format: DateFormat.yMMMMd(S.of(context).locale),
                keyboardType: TextInputType.datetime,
            decoration: InputDecoration(labelText:S.of(context).pageEntitiesServiceUserLocationLastUpdatedDateField,),
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
        return BlocBuilder<ServiceUserLocationBloc, ServiceUserLocationState>(
            buildWhen: (previous, current) => previous.clientId != current.clientId,
            builder: (context, state) {
              return TextFormField(
                  controller: context.bloc<ServiceUserLocationBloc>().clientIdController,
                  onChanged: (value) { context.bloc<ServiceUserLocationBloc>()
                    .add(ClientIdChanged(clientId:int.parse(value))); },
                  keyboardType:TextInputType.number,                  decoration: InputDecoration(
                      labelText:S.of(context).pageEntitiesServiceUserLocationClientIdField));
            }
        );
      }
        Widget hasExtraDataField() {
          return BlocBuilder<ServiceUserLocationBloc,ServiceUserLocationState>(
              buildWhen: (previous, current) => previous.hasExtraData != current.hasExtraData,
              builder: (context, state) {
                return Padding(
                  padding: const EdgeInsets.only(left: 3.0),
                  child: Row(
                    mainAxisAlignment: MainAxisAlignment.spaceBetween,
                    children: <Widget>[
                      Text(S.of(context).pageEntitiesServiceUserLocationHasExtraDataField, style: Theme.of(context).textTheme.bodyText1,),
                      Switch(
                          value: state.hasExtraData.value,
                          onChanged: (value) { context.bloc<ServiceUserLocationBloc>().add(HasExtraDataChanged(hasExtraData: value)); },
                          activeColor: Theme.of(context).primaryColor,),
                    ],
                  ),
                );
              });
        }


  Widget validationZone() {
    return BlocBuilder<ServiceUserLocationBloc, ServiceUserLocationState>(
        buildWhen:(previous, current) => previous.formStatus != current.formStatus,
        builder: (context, state) {
          return Visibility(
              visible: state.formStatus.isSubmissionFailure ||  state.formStatus.isSubmissionSuccess,
              child: Center(
                child: generateNotificationText(state, context),
              ));
        });
  }

  Widget generateNotificationText(ServiceUserLocationState state, BuildContext context) {
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
    return BlocBuilder<ServiceUserLocationBloc, ServiceUserLocationState>(
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
            onPressed: state.formStatus.isValidated ? () => context.bloc<ServiceUserLocationBloc>().add(ServiceUserLocationFormSubmitted()) : null,
          );
        }
    );
  }
}
