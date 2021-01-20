import 'package:datetime_picker_formfield/datetime_picker_formfield.dart';
import 'package:intl/intl.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:amcCarePlanner/entities/service_user_contact/bloc/service_user_contact_bloc.dart';
import 'package:amcCarePlanner/generated/l10n.dart';
import 'package:flutter/material.dart';
import 'package:formz/formz.dart';
import 'package:amcCarePlanner/shared/repository/http_utils.dart';
import 'package:amcCarePlanner/entities/service_user_contact/service_user_contact_model.dart';
import 'service_user_contact_route.dart';

class ServiceUserContactUpdateScreen extends StatelessWidget {
  ServiceUserContactUpdateScreen({Key key}) : super(key: ServiceUserContactRoutes.editScreenKey);

  @override
  Widget build(BuildContext context) {
    return BlocListener<ServiceUserContactBloc, ServiceUserContactState>(
      listener: (context, state) {
        if(state.formStatus.isSubmissionSuccess){
          Navigator.pushNamed(context, ServiceUserContactRoutes.list);
        }
      },
      child: Scaffold(
          appBar: AppBar(
            centerTitle: true,
            title: BlocBuilder<ServiceUserContactBloc, ServiceUserContactState>(
                buildWhen: (previous, current) => previous.editMode != current.editMode,
                builder: (context, state) {
                String title = state.editMode == true ?S.of(context).pageEntitiesServiceUserContactUpdateTitle:
S.of(context).pageEntitiesServiceUserContactCreateTitle;
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
          addressField(),
          cityOrTownField(),
          countyField(),
          postCodeField(),
          telephoneField(),
          createdDateField(),
          lastUpdatedDateField(),
          clientIdField(),
          hasExtraDataField(),
        validationZone(),
        submit(context)
      ]),
    );
  }

      Widget addressField() {
        return BlocBuilder<ServiceUserContactBloc, ServiceUserContactState>(
            buildWhen: (previous, current) => previous.address != current.address,
            builder: (context, state) {
              return TextFormField(
                  controller: context.bloc<ServiceUserContactBloc>().addressController,
                  onChanged: (value) { context.bloc<ServiceUserContactBloc>()
                    .add(AddressChanged(address:value)); },
                  keyboardType:TextInputType.text,                  decoration: InputDecoration(
                      labelText:S.of(context).pageEntitiesServiceUserContactAddressField));
            }
        );
      }
      Widget cityOrTownField() {
        return BlocBuilder<ServiceUserContactBloc, ServiceUserContactState>(
            buildWhen: (previous, current) => previous.cityOrTown != current.cityOrTown,
            builder: (context, state) {
              return TextFormField(
                  controller: context.bloc<ServiceUserContactBloc>().cityOrTownController,
                  onChanged: (value) { context.bloc<ServiceUserContactBloc>()
                    .add(CityOrTownChanged(cityOrTown:value)); },
                  keyboardType:TextInputType.text,                  decoration: InputDecoration(
                      labelText:S.of(context).pageEntitiesServiceUserContactCityOrTownField));
            }
        );
      }
      Widget countyField() {
        return BlocBuilder<ServiceUserContactBloc, ServiceUserContactState>(
            buildWhen: (previous, current) => previous.county != current.county,
            builder: (context, state) {
              return TextFormField(
                  controller: context.bloc<ServiceUserContactBloc>().countyController,
                  onChanged: (value) { context.bloc<ServiceUserContactBloc>()
                    .add(CountyChanged(county:value)); },
                  keyboardType:TextInputType.text,                  decoration: InputDecoration(
                      labelText:S.of(context).pageEntitiesServiceUserContactCountyField));
            }
        );
      }
      Widget postCodeField() {
        return BlocBuilder<ServiceUserContactBloc, ServiceUserContactState>(
            buildWhen: (previous, current) => previous.postCode != current.postCode,
            builder: (context, state) {
              return TextFormField(
                  controller: context.bloc<ServiceUserContactBloc>().postCodeController,
                  onChanged: (value) { context.bloc<ServiceUserContactBloc>()
                    .add(PostCodeChanged(postCode:value)); },
                  keyboardType:TextInputType.text,                  decoration: InputDecoration(
                      labelText:S.of(context).pageEntitiesServiceUserContactPostCodeField));
            }
        );
      }
      Widget telephoneField() {
        return BlocBuilder<ServiceUserContactBloc, ServiceUserContactState>(
            buildWhen: (previous, current) => previous.telephone != current.telephone,
            builder: (context, state) {
              return TextFormField(
                  controller: context.bloc<ServiceUserContactBloc>().telephoneController,
                  onChanged: (value) { context.bloc<ServiceUserContactBloc>()
                    .add(TelephoneChanged(telephone:value)); },
                  keyboardType:TextInputType.text,                  decoration: InputDecoration(
                      labelText:S.of(context).pageEntitiesServiceUserContactTelephoneField));
            }
        );
      }
      Widget createdDateField() {
        return BlocBuilder<ServiceUserContactBloc, ServiceUserContactState>(
            buildWhen: (previous, current) => previous.createdDate != current.createdDate,
            builder: (context, state) {
              return DateTimeField(
                controller: context.bloc<ServiceUserContactBloc>().createdDateController,
                onChanged: (value) { context.bloc<ServiceUserContactBloc>().add(CreatedDateChanged(createdDate: value)); },
                format: DateFormat.yMMMMd(S.of(context).locale),
                keyboardType: TextInputType.datetime,
            decoration: InputDecoration(labelText:S.of(context).pageEntitiesServiceUserContactCreatedDateField,),
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
        return BlocBuilder<ServiceUserContactBloc, ServiceUserContactState>(
            buildWhen: (previous, current) => previous.lastUpdatedDate != current.lastUpdatedDate,
            builder: (context, state) {
              return DateTimeField(
                controller: context.bloc<ServiceUserContactBloc>().lastUpdatedDateController,
                onChanged: (value) { context.bloc<ServiceUserContactBloc>().add(LastUpdatedDateChanged(lastUpdatedDate: value)); },
                format: DateFormat.yMMMMd(S.of(context).locale),
                keyboardType: TextInputType.datetime,
            decoration: InputDecoration(labelText:S.of(context).pageEntitiesServiceUserContactLastUpdatedDateField,),
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
        return BlocBuilder<ServiceUserContactBloc, ServiceUserContactState>(
            buildWhen: (previous, current) => previous.clientId != current.clientId,
            builder: (context, state) {
              return TextFormField(
                  controller: context.bloc<ServiceUserContactBloc>().clientIdController,
                  onChanged: (value) { context.bloc<ServiceUserContactBloc>()
                    .add(ClientIdChanged(clientId:int.parse(value))); },
                  keyboardType:TextInputType.number,                  decoration: InputDecoration(
                      labelText:S.of(context).pageEntitiesServiceUserContactClientIdField));
            }
        );
      }
        Widget hasExtraDataField() {
          return BlocBuilder<ServiceUserContactBloc,ServiceUserContactState>(
              buildWhen: (previous, current) => previous.hasExtraData != current.hasExtraData,
              builder: (context, state) {
                return Padding(
                  padding: const EdgeInsets.only(left: 3.0),
                  child: Row(
                    mainAxisAlignment: MainAxisAlignment.spaceBetween,
                    children: <Widget>[
                      Text(S.of(context).pageEntitiesServiceUserContactHasExtraDataField, style: Theme.of(context).textTheme.bodyText1,),
                      Switch(
                          value: state.hasExtraData.value,
                          onChanged: (value) { context.bloc<ServiceUserContactBloc>().add(HasExtraDataChanged(hasExtraData: value)); },
                          activeColor: Theme.of(context).primaryColor,),
                    ],
                  ),
                );
              });
        }


  Widget validationZone() {
    return BlocBuilder<ServiceUserContactBloc, ServiceUserContactState>(
        buildWhen:(previous, current) => previous.formStatus != current.formStatus,
        builder: (context, state) {
          return Visibility(
              visible: state.formStatus.isSubmissionFailure ||  state.formStatus.isSubmissionSuccess,
              child: Center(
                child: generateNotificationText(state, context),
              ));
        });
  }

  Widget generateNotificationText(ServiceUserContactState state, BuildContext context) {
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
    return BlocBuilder<ServiceUserContactBloc, ServiceUserContactState>(
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
            onPressed: state.formStatus.isValidated ? () => context.bloc<ServiceUserContactBloc>().add(ServiceUserContactFormSubmitted()) : null,
          );
        }
    );
  }
}
