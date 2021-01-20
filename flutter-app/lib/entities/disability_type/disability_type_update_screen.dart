import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:amcCarePlanner/entities/disability_type/bloc/disability_type_bloc.dart';
import 'package:amcCarePlanner/generated/l10n.dart';
import 'package:flutter/material.dart';
import 'package:formz/formz.dart';
import 'package:amcCarePlanner/shared/repository/http_utils.dart';
import 'package:amcCarePlanner/entities/disability_type/disability_type_model.dart';
import 'disability_type_route.dart';

class DisabilityTypeUpdateScreen extends StatelessWidget {
  DisabilityTypeUpdateScreen({Key key}) : super(key: DisabilityTypeRoutes.editScreenKey);

  @override
  Widget build(BuildContext context) {
    return BlocListener<DisabilityTypeBloc, DisabilityTypeState>(
      listener: (context, state) {
        if(state.formStatus.isSubmissionSuccess){
          Navigator.pushNamed(context, DisabilityTypeRoutes.list);
        }
      },
      child: Scaffold(
          appBar: AppBar(
            centerTitle: true,
            title: BlocBuilder<DisabilityTypeBloc, DisabilityTypeState>(
                buildWhen: (previous, current) => previous.editMode != current.editMode,
                builder: (context, state) {
                String title = state.editMode == true ?S.of(context).pageEntitiesDisabilityTypeUpdateTitle:
S.of(context).pageEntitiesDisabilityTypeCreateTitle;
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
          disabilityField(),
          descriptionField(),
          hasExtraDataField(),
        validationZone(),
        submit(context)
      ]),
    );
  }

      Widget disabilityField() {
        return BlocBuilder<DisabilityTypeBloc, DisabilityTypeState>(
            buildWhen: (previous, current) => previous.disability != current.disability,
            builder: (context, state) {
              return TextFormField(
                  controller: context.bloc<DisabilityTypeBloc>().disabilityController,
                  onChanged: (value) { context.bloc<DisabilityTypeBloc>()
                    .add(DisabilityChanged(disability:value)); },
                  keyboardType:TextInputType.text,                  decoration: InputDecoration(
                      labelText:S.of(context).pageEntitiesDisabilityTypeDisabilityField));
            }
        );
      }
      Widget descriptionField() {
        return BlocBuilder<DisabilityTypeBloc, DisabilityTypeState>(
            buildWhen: (previous, current) => previous.description != current.description,
            builder: (context, state) {
              return TextFormField(
                  controller: context.bloc<DisabilityTypeBloc>().descriptionController,
                  onChanged: (value) { context.bloc<DisabilityTypeBloc>()
                    .add(DescriptionChanged(description:value)); },
                  keyboardType:TextInputType.text,                  decoration: InputDecoration(
                      labelText:S.of(context).pageEntitiesDisabilityTypeDescriptionField));
            }
        );
      }
        Widget hasExtraDataField() {
          return BlocBuilder<DisabilityTypeBloc,DisabilityTypeState>(
              buildWhen: (previous, current) => previous.hasExtraData != current.hasExtraData,
              builder: (context, state) {
                return Padding(
                  padding: const EdgeInsets.only(left: 3.0),
                  child: Row(
                    mainAxisAlignment: MainAxisAlignment.spaceBetween,
                    children: <Widget>[
                      Text(S.of(context).pageEntitiesDisabilityTypeHasExtraDataField, style: Theme.of(context).textTheme.bodyText1,),
                      Switch(
                          value: state.hasExtraData.value,
                          onChanged: (value) { context.bloc<DisabilityTypeBloc>().add(HasExtraDataChanged(hasExtraData: value)); },
                          activeColor: Theme.of(context).primaryColor,),
                    ],
                  ),
                );
              });
        }


  Widget validationZone() {
    return BlocBuilder<DisabilityTypeBloc, DisabilityTypeState>(
        buildWhen:(previous, current) => previous.formStatus != current.formStatus,
        builder: (context, state) {
          return Visibility(
              visible: state.formStatus.isSubmissionFailure ||  state.formStatus.isSubmissionSuccess,
              child: Center(
                child: generateNotificationText(state, context),
              ));
        });
  }

  Widget generateNotificationText(DisabilityTypeState state, BuildContext context) {
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
    return BlocBuilder<DisabilityTypeBloc, DisabilityTypeState>(
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
            onPressed: state.formStatus.isValidated ? () => context.bloc<DisabilityTypeBloc>().add(DisabilityTypeFormSubmitted()) : null,
          );
        }
    );
  }
}
