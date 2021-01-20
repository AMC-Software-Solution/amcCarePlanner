import 'package:datetime_picker_formfield/datetime_picker_formfield.dart';
import 'package:intl/intl.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:amcCarePlanner/entities/country/bloc/country_bloc.dart';
import 'package:amcCarePlanner/generated/l10n.dart';
import 'package:flutter/material.dart';
import 'package:formz/formz.dart';
import 'package:amcCarePlanner/shared/repository/http_utils.dart';
import 'package:amcCarePlanner/entities/country/country_model.dart';
import 'country_route.dart';

class CountryUpdateScreen extends StatelessWidget {
  CountryUpdateScreen({Key key}) : super(key: CountryRoutes.editScreenKey);

  @override
  Widget build(BuildContext context) {
    return BlocListener<CountryBloc, CountryState>(
      listener: (context, state) {
        if(state.formStatus.isSubmissionSuccess){
          Navigator.pushNamed(context, CountryRoutes.list);
        }
      },
      child: Scaffold(
          appBar: AppBar(
            centerTitle: true,
            title: BlocBuilder<CountryBloc, CountryState>(
                buildWhen: (previous, current) => previous.editMode != current.editMode,
                builder: (context, state) {
                String title = state.editMode == true ?S.of(context).pageEntitiesCountryUpdateTitle:
S.of(context).pageEntitiesCountryCreateTitle;
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
          countryNameField(),
          countryIsoCodeField(),
          countryFlagUrlField(),
          countryCallingCodeField(),
          countryTelDigitLengthField(),
          createdDateField(),
          lastUpdatedDateField(),
          hasExtraDataField(),
        validationZone(),
        submit(context)
      ]),
    );
  }

      Widget countryNameField() {
        return BlocBuilder<CountryBloc, CountryState>(
            buildWhen: (previous, current) => previous.countryName != current.countryName,
            builder: (context, state) {
              return TextFormField(
                  controller: context.bloc<CountryBloc>().countryNameController,
                  onChanged: (value) { context.bloc<CountryBloc>()
                    .add(CountryNameChanged(countryName:value)); },
                  keyboardType:TextInputType.text,                  decoration: InputDecoration(
                      labelText:S.of(context).pageEntitiesCountryCountryNameField));
            }
        );
      }
      Widget countryIsoCodeField() {
        return BlocBuilder<CountryBloc, CountryState>(
            buildWhen: (previous, current) => previous.countryIsoCode != current.countryIsoCode,
            builder: (context, state) {
              return TextFormField(
                  controller: context.bloc<CountryBloc>().countryIsoCodeController,
                  onChanged: (value) { context.bloc<CountryBloc>()
                    .add(CountryIsoCodeChanged(countryIsoCode:value)); },
                  keyboardType:TextInputType.text,                  decoration: InputDecoration(
                      labelText:S.of(context).pageEntitiesCountryCountryIsoCodeField));
            }
        );
      }
      Widget countryFlagUrlField() {
        return BlocBuilder<CountryBloc, CountryState>(
            buildWhen: (previous, current) => previous.countryFlagUrl != current.countryFlagUrl,
            builder: (context, state) {
              return TextFormField(
                  controller: context.bloc<CountryBloc>().countryFlagUrlController,
                  onChanged: (value) { context.bloc<CountryBloc>()
                    .add(CountryFlagUrlChanged(countryFlagUrl:value)); },
                  keyboardType:TextInputType.text,                  decoration: InputDecoration(
                      labelText:S.of(context).pageEntitiesCountryCountryFlagUrlField));
            }
        );
      }
      Widget countryCallingCodeField() {
        return BlocBuilder<CountryBloc, CountryState>(
            buildWhen: (previous, current) => previous.countryCallingCode != current.countryCallingCode,
            builder: (context, state) {
              return TextFormField(
                  controller: context.bloc<CountryBloc>().countryCallingCodeController,
                  onChanged: (value) { context.bloc<CountryBloc>()
                    .add(CountryCallingCodeChanged(countryCallingCode:value)); },
                  keyboardType:TextInputType.text,                  decoration: InputDecoration(
                      labelText:S.of(context).pageEntitiesCountryCountryCallingCodeField));
            }
        );
      }
      Widget countryTelDigitLengthField() {
        return BlocBuilder<CountryBloc, CountryState>(
            buildWhen: (previous, current) => previous.countryTelDigitLength != current.countryTelDigitLength,
            builder: (context, state) {
              return TextFormField(
                  controller: context.bloc<CountryBloc>().countryTelDigitLengthController,
                  onChanged: (value) { context.bloc<CountryBloc>()
                    .add(CountryTelDigitLengthChanged(countryTelDigitLength:int.parse(value))); },
                  keyboardType:TextInputType.number,                  decoration: InputDecoration(
                      labelText:S.of(context).pageEntitiesCountryCountryTelDigitLengthField));
            }
        );
      }
      Widget createdDateField() {
        return BlocBuilder<CountryBloc, CountryState>(
            buildWhen: (previous, current) => previous.createdDate != current.createdDate,
            builder: (context, state) {
              return DateTimeField(
                controller: context.bloc<CountryBloc>().createdDateController,
                onChanged: (value) { context.bloc<CountryBloc>().add(CreatedDateChanged(createdDate: value)); },
                format: DateFormat.yMMMMd(S.of(context).locale),
                keyboardType: TextInputType.datetime,
            decoration: InputDecoration(labelText:S.of(context).pageEntitiesCountryCreatedDateField,),
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
        return BlocBuilder<CountryBloc, CountryState>(
            buildWhen: (previous, current) => previous.lastUpdatedDate != current.lastUpdatedDate,
            builder: (context, state) {
              return DateTimeField(
                controller: context.bloc<CountryBloc>().lastUpdatedDateController,
                onChanged: (value) { context.bloc<CountryBloc>().add(LastUpdatedDateChanged(lastUpdatedDate: value)); },
                format: DateFormat.yMMMMd(S.of(context).locale),
                keyboardType: TextInputType.datetime,
            decoration: InputDecoration(labelText:S.of(context).pageEntitiesCountryLastUpdatedDateField,),
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
          return BlocBuilder<CountryBloc,CountryState>(
              buildWhen: (previous, current) => previous.hasExtraData != current.hasExtraData,
              builder: (context, state) {
                return Padding(
                  padding: const EdgeInsets.only(left: 3.0),
                  child: Row(
                    mainAxisAlignment: MainAxisAlignment.spaceBetween,
                    children: <Widget>[
                      Text(S.of(context).pageEntitiesCountryHasExtraDataField, style: Theme.of(context).textTheme.bodyText1,),
                      Switch(
                          value: state.hasExtraData.value,
                          onChanged: (value) { context.bloc<CountryBloc>().add(HasExtraDataChanged(hasExtraData: value)); },
                          activeColor: Theme.of(context).primaryColor,),
                    ],
                  ),
                );
              });
        }


  Widget validationZone() {
    return BlocBuilder<CountryBloc, CountryState>(
        buildWhen:(previous, current) => previous.formStatus != current.formStatus,
        builder: (context, state) {
          return Visibility(
              visible: state.formStatus.isSubmissionFailure ||  state.formStatus.isSubmissionSuccess,
              child: Center(
                child: generateNotificationText(state, context),
              ));
        });
  }

  Widget generateNotificationText(CountryState state, BuildContext context) {
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
    return BlocBuilder<CountryBloc, CountryState>(
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
            onPressed: state.formStatus.isValidated ? () => context.bloc<CountryBloc>().add(CountryFormSubmitted()) : null,
          );
        }
    );
  }
}
