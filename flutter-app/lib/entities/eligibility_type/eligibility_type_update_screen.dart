import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:amcCarePlanner/entities/eligibility_type/bloc/eligibility_type_bloc.dart';
import 'package:amcCarePlanner/generated/l10n.dart';
import 'package:flutter/material.dart';
import 'package:formz/formz.dart';
import 'package:amcCarePlanner/shared/repository/http_utils.dart';
import 'package:amcCarePlanner/entities/eligibility_type/eligibility_type_model.dart';
import 'eligibility_type_route.dart';

class EligibilityTypeUpdateScreen extends StatelessWidget {
  EligibilityTypeUpdateScreen({Key key}) : super(key: EligibilityTypeRoutes.editScreenKey);

  @override
  Widget build(BuildContext context) {
    return BlocListener<EligibilityTypeBloc, EligibilityTypeState>(
      listener: (context, state) {
        if(state.formStatus.isSubmissionSuccess){
          Navigator.pushNamed(context, EligibilityTypeRoutes.list);
        }
      },
      child: Scaffold(
          appBar: AppBar(
            centerTitle: true,
            title: BlocBuilder<EligibilityTypeBloc, EligibilityTypeState>(
                buildWhen: (previous, current) => previous.editMode != current.editMode,
                builder: (context, state) {
                String title = state.editMode == true ?S.of(context).pageEntitiesEligibilityTypeUpdateTitle:
S.of(context).pageEntitiesEligibilityTypeCreateTitle;
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
          eligibilityTypeField(),
          descriptionField(),
          hasExtraDataField(),
        validationZone(),
        submit(context)
      ]),
    );
  }

      Widget eligibilityTypeField() {
        return BlocBuilder<EligibilityTypeBloc, EligibilityTypeState>(
            buildWhen: (previous, current) => previous.eligibilityType != current.eligibilityType,
            builder: (context, state) {
              return TextFormField(
                  controller: context.bloc<EligibilityTypeBloc>().eligibilityTypeController,
                  onChanged: (value) { context.bloc<EligibilityTypeBloc>()
                    .add(EligibilityTypeChanged(eligibilityType:value)); },
                  keyboardType:TextInputType.text,                  decoration: InputDecoration(
                      labelText:S.of(context).pageEntitiesEligibilityTypeEligibilityTypeField));
            }
        );
      }
      Widget descriptionField() {
        return BlocBuilder<EligibilityTypeBloc, EligibilityTypeState>(
            buildWhen: (previous, current) => previous.description != current.description,
            builder: (context, state) {
              return TextFormField(
                  controller: context.bloc<EligibilityTypeBloc>().descriptionController,
                  onChanged: (value) { context.bloc<EligibilityTypeBloc>()
                    .add(DescriptionChanged(description:value)); },
                  keyboardType:TextInputType.text,                  decoration: InputDecoration(
                      labelText:S.of(context).pageEntitiesEligibilityTypeDescriptionField));
            }
        );
      }
        Widget hasExtraDataField() {
          return BlocBuilder<EligibilityTypeBloc,EligibilityTypeState>(
              buildWhen: (previous, current) => previous.hasExtraData != current.hasExtraData,
              builder: (context, state) {
                return Padding(
                  padding: const EdgeInsets.only(left: 3.0),
                  child: Row(
                    mainAxisAlignment: MainAxisAlignment.spaceBetween,
                    children: <Widget>[
                      Text(S.of(context).pageEntitiesEligibilityTypeHasExtraDataField, style: Theme.of(context).textTheme.bodyText1,),
                      Switch(
                          value: state.hasExtraData.value,
                          onChanged: (value) { context.bloc<EligibilityTypeBloc>().add(HasExtraDataChanged(hasExtraData: value)); },
                          activeColor: Theme.of(context).primaryColor,),
                    ],
                  ),
                );
              });
        }


  Widget validationZone() {
    return BlocBuilder<EligibilityTypeBloc, EligibilityTypeState>(
        buildWhen:(previous, current) => previous.formStatus != current.formStatus,
        builder: (context, state) {
          return Visibility(
              visible: state.formStatus.isSubmissionFailure ||  state.formStatus.isSubmissionSuccess,
              child: Center(
                child: generateNotificationText(state, context),
              ));
        });
  }

  Widget generateNotificationText(EligibilityTypeState state, BuildContext context) {
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
    return BlocBuilder<EligibilityTypeBloc, EligibilityTypeState>(
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
            onPressed: state.formStatus.isValidated ? () => context.bloc<EligibilityTypeBloc>().add(EligibilityTypeFormSubmitted()) : null,
          );
        }
    );
  }
}
