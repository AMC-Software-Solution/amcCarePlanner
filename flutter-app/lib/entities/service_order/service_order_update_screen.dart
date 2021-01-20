import 'package:datetime_picker_formfield/datetime_picker_formfield.dart';
import 'package:intl/intl.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:amcCarePlanner/entities/service_order/bloc/service_order_bloc.dart';
import 'package:amcCarePlanner/generated/l10n.dart';
import 'package:flutter/material.dart';
import 'package:formz/formz.dart';
import 'package:amcCarePlanner/shared/repository/http_utils.dart';
import 'package:amcCarePlanner/entities/service_order/service_order_model.dart';
import 'service_order_route.dart';

class ServiceOrderUpdateScreen extends StatelessWidget {
  ServiceOrderUpdateScreen({Key key}) : super(key: ServiceOrderRoutes.editScreenKey);

  @override
  Widget build(BuildContext context) {
    return BlocListener<ServiceOrderBloc, ServiceOrderState>(
      listener: (context, state) {
        if(state.formStatus.isSubmissionSuccess){
          Navigator.pushNamed(context, ServiceOrderRoutes.list);
        }
      },
      child: Scaffold(
          appBar: AppBar(
            centerTitle: true,
            title: BlocBuilder<ServiceOrderBloc, ServiceOrderState>(
                buildWhen: (previous, current) => previous.editMode != current.editMode,
                builder: (context, state) {
                String title = state.editMode == true ?S.of(context).pageEntitiesServiceOrderUpdateTitle:
S.of(context).pageEntitiesServiceOrderCreateTitle;
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
          serviceDescriptionField(),
          serviceHourlyRateField(),
          clientIdField(),
          createdDateField(),
          lastUpdatedDateField(),
          hasExtraDataField(),
        validationZone(),
        submit(context)
      ]),
    );
  }

      Widget titleField() {
        return BlocBuilder<ServiceOrderBloc, ServiceOrderState>(
            buildWhen: (previous, current) => previous.title != current.title,
            builder: (context, state) {
              return TextFormField(
                  controller: context.bloc<ServiceOrderBloc>().titleController,
                  onChanged: (value) { context.bloc<ServiceOrderBloc>()
                    .add(TitleChanged(title:value)); },
                  keyboardType:TextInputType.text,                  decoration: InputDecoration(
                      labelText:S.of(context).pageEntitiesServiceOrderTitleField));
            }
        );
      }
      Widget serviceDescriptionField() {
        return BlocBuilder<ServiceOrderBloc, ServiceOrderState>(
            buildWhen: (previous, current) => previous.serviceDescription != current.serviceDescription,
            builder: (context, state) {
              return TextFormField(
                  controller: context.bloc<ServiceOrderBloc>().serviceDescriptionController,
                  onChanged: (value) { context.bloc<ServiceOrderBloc>()
                    .add(ServiceDescriptionChanged(serviceDescription:value)); },
                  keyboardType:TextInputType.text,                  decoration: InputDecoration(
                      labelText:S.of(context).pageEntitiesServiceOrderServiceDescriptionField));
            }
        );
      }
      Widget serviceHourlyRateField() {
        return BlocBuilder<ServiceOrderBloc, ServiceOrderState>(
            buildWhen: (previous, current) => previous.serviceHourlyRate != current.serviceHourlyRate,
            builder: (context, state) {
              return TextFormField(
                  controller: context.bloc<ServiceOrderBloc>().serviceHourlyRateController,
                  onChanged: (value) { context.bloc<ServiceOrderBloc>()
                    .add(ServiceHourlyRateChanged(serviceHourlyRate:value)); },
                  keyboardType:TextInputType.text,                  decoration: InputDecoration(
                      labelText:S.of(context).pageEntitiesServiceOrderServiceHourlyRateField));
            }
        );
      }
      Widget clientIdField() {
        return BlocBuilder<ServiceOrderBloc, ServiceOrderState>(
            buildWhen: (previous, current) => previous.clientId != current.clientId,
            builder: (context, state) {
              return TextFormField(
                  controller: context.bloc<ServiceOrderBloc>().clientIdController,
                  onChanged: (value) { context.bloc<ServiceOrderBloc>()
                    .add(ClientIdChanged(clientId:int.parse(value))); },
                  keyboardType:TextInputType.number,                  decoration: InputDecoration(
                      labelText:S.of(context).pageEntitiesServiceOrderClientIdField));
            }
        );
      }
      Widget createdDateField() {
        return BlocBuilder<ServiceOrderBloc, ServiceOrderState>(
            buildWhen: (previous, current) => previous.createdDate != current.createdDate,
            builder: (context, state) {
              return DateTimeField(
                controller: context.bloc<ServiceOrderBloc>().createdDateController,
                onChanged: (value) { context.bloc<ServiceOrderBloc>().add(CreatedDateChanged(createdDate: value)); },
                format: DateFormat.yMMMMd(S.of(context).locale),
                keyboardType: TextInputType.datetime,
            decoration: InputDecoration(labelText:S.of(context).pageEntitiesServiceOrderCreatedDateField,),
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
        return BlocBuilder<ServiceOrderBloc, ServiceOrderState>(
            buildWhen: (previous, current) => previous.lastUpdatedDate != current.lastUpdatedDate,
            builder: (context, state) {
              return DateTimeField(
                controller: context.bloc<ServiceOrderBloc>().lastUpdatedDateController,
                onChanged: (value) { context.bloc<ServiceOrderBloc>().add(LastUpdatedDateChanged(lastUpdatedDate: value)); },
                format: DateFormat.yMMMMd(S.of(context).locale),
                keyboardType: TextInputType.datetime,
            decoration: InputDecoration(labelText:S.of(context).pageEntitiesServiceOrderLastUpdatedDateField,),
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
        Widget hasExtraDataField() {
          return BlocBuilder<ServiceOrderBloc,ServiceOrderState>(
              buildWhen: (previous, current) => previous.hasExtraData != current.hasExtraData,
              builder: (context, state) {
                return Padding(
                  padding: const EdgeInsets.only(left: 3.0),
                  child: Row(
                    mainAxisAlignment: MainAxisAlignment.spaceBetween,
                    children: <Widget>[
                      Text(S.of(context).pageEntitiesServiceOrderHasExtraDataField, style: Theme.of(context).textTheme.bodyText1,),
                      Switch(
                          value: state.hasExtraData.value,
                          onChanged: (value) { context.bloc<ServiceOrderBloc>().add(HasExtraDataChanged(hasExtraData: value)); },
                          activeColor: Theme.of(context).primaryColor,),
                    ],
                  ),
                );
              });
        }


  Widget validationZone() {
    return BlocBuilder<ServiceOrderBloc, ServiceOrderState>(
        buildWhen:(previous, current) => previous.formStatus != current.formStatus,
        builder: (context, state) {
          return Visibility(
              visible: state.formStatus.isSubmissionFailure ||  state.formStatus.isSubmissionSuccess,
              child: Center(
                child: generateNotificationText(state, context),
              ));
        });
  }

  Widget generateNotificationText(ServiceOrderState state, BuildContext context) {
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
    return BlocBuilder<ServiceOrderBloc, ServiceOrderState>(
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
            onPressed: state.formStatus.isValidated ? () => context.bloc<ServiceOrderBloc>().add(ServiceOrderFormSubmitted()) : null,
          );
        }
    );
  }
}
