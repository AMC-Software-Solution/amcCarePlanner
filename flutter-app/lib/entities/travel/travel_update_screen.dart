import 'package:datetime_picker_formfield/datetime_picker_formfield.dart';
import 'package:intl/intl.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:amcCarePlanner/entities/travel/bloc/travel_bloc.dart';
import 'package:amcCarePlanner/generated/l10n.dart';
import 'package:flutter/material.dart';
import 'package:formz/formz.dart';
import 'package:amcCarePlanner/shared/repository/http_utils.dart';
import 'package:amcCarePlanner/entities/travel/travel_model.dart';
import 'travel_route.dart';

class TravelUpdateScreen extends StatelessWidget {
  TravelUpdateScreen({Key key}) : super(key: TravelRoutes.editScreenKey);

  @override
  Widget build(BuildContext context) {
    return BlocListener<TravelBloc, TravelState>(
      listener: (context, state) {
        if(state.formStatus.isSubmissionSuccess){
          Navigator.pushNamed(context, TravelRoutes.list);
        }
      },
      child: Scaffold(
          appBar: AppBar(
            centerTitle: true,
            title: BlocBuilder<TravelBloc, TravelState>(
                buildWhen: (previous, current) => previous.editMode != current.editMode,
                builder: (context, state) {
                String title = state.editMode == true ?S.of(context).pageEntitiesTravelUpdateTitle:
S.of(context).pageEntitiesTravelCreateTitle;
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
          travelModeField(),
          distanceToDestinationField(),
          timeToDestinationField(),
          actualDistanceRequiredField(),
          actualTimeRequiredField(),
          travelStatusField(),
          createdDateField(),
          lastUpdatedDateField(),
          clientIdField(),
          hasExtraDataField(),
        validationZone(),
        submit(context)
      ]),
    );
  }

      Widget travelModeField() {
        return BlocBuilder<TravelBloc,TravelState>(
            buildWhen: (previous, current) => previous.travelMode != current.travelMode,
            builder: (context, state) {
              return Padding(
                padding: const EdgeInsets.only(left: 3.0),
                child: Row(
                  mainAxisAlignment: MainAxisAlignment.spaceBetween,
                  children: <Widget>[
                    Text(S.of(context).pageEntitiesTravelTravelModeField, style: Theme.of(context).textTheme.bodyText1,),
                    DropdownButton<TravelMode>(
                        value: state.travelMode.value,
                        onChanged: (value) { context.bloc<TravelBloc>().add(TravelModeChanged(travelMode: value)); },
                        items: createDropdownTravelModeItems(TravelMode.values)),
                  ],
                ),
              );
            });
      }
      Widget distanceToDestinationField() {
        return BlocBuilder<TravelBloc, TravelState>(
            buildWhen: (previous, current) => previous.distanceToDestination != current.distanceToDestination,
            builder: (context, state) {
              return TextFormField(
                  controller: context.bloc<TravelBloc>().distanceToDestinationController,
                  onChanged: (value) { context.bloc<TravelBloc>()
                    .add(DistanceToDestinationChanged(distanceToDestination:int.parse(value))); },
                  keyboardType:TextInputType.number,                  decoration: InputDecoration(
                      labelText:S.of(context).pageEntitiesTravelDistanceToDestinationField));
            }
        );
      }
      Widget timeToDestinationField() {
        return BlocBuilder<TravelBloc, TravelState>(
            buildWhen: (previous, current) => previous.timeToDestination != current.timeToDestination,
            builder: (context, state) {
              return TextFormField(
                  controller: context.bloc<TravelBloc>().timeToDestinationController,
                  onChanged: (value) { context.bloc<TravelBloc>()
                    .add(TimeToDestinationChanged(timeToDestination:int.parse(value))); },
                  keyboardType:TextInputType.number,                  decoration: InputDecoration(
                      labelText:S.of(context).pageEntitiesTravelTimeToDestinationField));
            }
        );
      }
      Widget actualDistanceRequiredField() {
        return BlocBuilder<TravelBloc, TravelState>(
            buildWhen: (previous, current) => previous.actualDistanceRequired != current.actualDistanceRequired,
            builder: (context, state) {
              return TextFormField(
                  controller: context.bloc<TravelBloc>().actualDistanceRequiredController,
                  onChanged: (value) { context.bloc<TravelBloc>()
                    .add(ActualDistanceRequiredChanged(actualDistanceRequired:int.parse(value))); },
                  keyboardType:TextInputType.number,                  decoration: InputDecoration(
                      labelText:S.of(context).pageEntitiesTravelActualDistanceRequiredField));
            }
        );
      }
      Widget actualTimeRequiredField() {
        return BlocBuilder<TravelBloc, TravelState>(
            buildWhen: (previous, current) => previous.actualTimeRequired != current.actualTimeRequired,
            builder: (context, state) {
              return TextFormField(
                  controller: context.bloc<TravelBloc>().actualTimeRequiredController,
                  onChanged: (value) { context.bloc<TravelBloc>()
                    .add(ActualTimeRequiredChanged(actualTimeRequired:int.parse(value))); },
                  keyboardType:TextInputType.number,                  decoration: InputDecoration(
                      labelText:S.of(context).pageEntitiesTravelActualTimeRequiredField));
            }
        );
      }
      Widget travelStatusField() {
        return BlocBuilder<TravelBloc,TravelState>(
            buildWhen: (previous, current) => previous.travelStatus != current.travelStatus,
            builder: (context, state) {
              return Padding(
                padding: const EdgeInsets.only(left: 3.0),
                child: Row(
                  mainAxisAlignment: MainAxisAlignment.spaceBetween,
                  children: <Widget>[
                    Text(S.of(context).pageEntitiesTravelTravelStatusField, style: Theme.of(context).textTheme.bodyText1,),
                    DropdownButton<TravelStatus>(
                        value: state.travelStatus.value,
                        onChanged: (value) { context.bloc<TravelBloc>().add(TravelStatusChanged(travelStatus: value)); },
                        items: createDropdownTravelStatusItems(TravelStatus.values)),
                  ],
                ),
              );
            });
      }
      Widget createdDateField() {
        return BlocBuilder<TravelBloc, TravelState>(
            buildWhen: (previous, current) => previous.createdDate != current.createdDate,
            builder: (context, state) {
              return DateTimeField(
                controller: context.bloc<TravelBloc>().createdDateController,
                onChanged: (value) { context.bloc<TravelBloc>().add(CreatedDateChanged(createdDate: value)); },
                format: DateFormat.yMMMMd(S.of(context).locale),
                keyboardType: TextInputType.datetime,
            decoration: InputDecoration(labelText:S.of(context).pageEntitiesTravelCreatedDateField,),
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
        return BlocBuilder<TravelBloc, TravelState>(
            buildWhen: (previous, current) => previous.lastUpdatedDate != current.lastUpdatedDate,
            builder: (context, state) {
              return DateTimeField(
                controller: context.bloc<TravelBloc>().lastUpdatedDateController,
                onChanged: (value) { context.bloc<TravelBloc>().add(LastUpdatedDateChanged(lastUpdatedDate: value)); },
                format: DateFormat.yMMMMd(S.of(context).locale),
                keyboardType: TextInputType.datetime,
            decoration: InputDecoration(labelText:S.of(context).pageEntitiesTravelLastUpdatedDateField,),
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
        return BlocBuilder<TravelBloc, TravelState>(
            buildWhen: (previous, current) => previous.clientId != current.clientId,
            builder: (context, state) {
              return TextFormField(
                  controller: context.bloc<TravelBloc>().clientIdController,
                  onChanged: (value) { context.bloc<TravelBloc>()
                    .add(ClientIdChanged(clientId:int.parse(value))); },
                  keyboardType:TextInputType.number,                  decoration: InputDecoration(
                      labelText:S.of(context).pageEntitiesTravelClientIdField));
            }
        );
      }
        Widget hasExtraDataField() {
          return BlocBuilder<TravelBloc,TravelState>(
              buildWhen: (previous, current) => previous.hasExtraData != current.hasExtraData,
              builder: (context, state) {
                return Padding(
                  padding: const EdgeInsets.only(left: 3.0),
                  child: Row(
                    mainAxisAlignment: MainAxisAlignment.spaceBetween,
                    children: <Widget>[
                      Text(S.of(context).pageEntitiesTravelHasExtraDataField, style: Theme.of(context).textTheme.bodyText1,),
                      Switch(
                          value: state.hasExtraData.value,
                          onChanged: (value) { context.bloc<TravelBloc>().add(HasExtraDataChanged(hasExtraData: value)); },
                          activeColor: Theme.of(context).primaryColor,),
                    ],
                  ),
                );
              });
        }

      List<DropdownMenuItem<TravelMode>> createDropdownTravelModeItems(List<TravelMode> travelModes) {
        List<DropdownMenuItem<TravelMode>> travelModeDropDown = [];
    
        for (TravelMode travelMode in travelModes) {
          DropdownMenuItem<TravelMode> dropdown = DropdownMenuItem<TravelMode>(
              value: travelMode, child: Text(travelMode.toString()));
              travelModeDropDown.add(dropdown);
        }
    
        return travelModeDropDown;
      }
      List<DropdownMenuItem<TravelStatus>> createDropdownTravelStatusItems(List<TravelStatus> travelStatuss) {
        List<DropdownMenuItem<TravelStatus>> travelStatusDropDown = [];
    
        for (TravelStatus travelStatus in travelStatuss) {
          DropdownMenuItem<TravelStatus> dropdown = DropdownMenuItem<TravelStatus>(
              value: travelStatus, child: Text(travelStatus.toString()));
              travelStatusDropDown.add(dropdown);
        }
    
        return travelStatusDropDown;
      }

  Widget validationZone() {
    return BlocBuilder<TravelBloc, TravelState>(
        buildWhen:(previous, current) => previous.formStatus != current.formStatus,
        builder: (context, state) {
          return Visibility(
              visible: state.formStatus.isSubmissionFailure ||  state.formStatus.isSubmissionSuccess,
              child: Center(
                child: generateNotificationText(state, context),
              ));
        });
  }

  Widget generateNotificationText(TravelState state, BuildContext context) {
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
    return BlocBuilder<TravelBloc, TravelState>(
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
            onPressed: state.formStatus.isValidated ? () => context.bloc<TravelBloc>().add(TravelFormSubmitted()) : null,
          );
        }
    );
  }
}
