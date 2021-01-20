import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:amcCarePlanner/entities/employee_designation/bloc/employee_designation_bloc.dart';
import 'package:amcCarePlanner/generated/l10n.dart';
import 'package:flutter/material.dart';
import 'package:formz/formz.dart';
import 'package:amcCarePlanner/shared/repository/http_utils.dart';
import 'package:amcCarePlanner/entities/employee_designation/employee_designation_model.dart';
import 'employee_designation_route.dart';

class EmployeeDesignationUpdateScreen extends StatelessWidget {
  EmployeeDesignationUpdateScreen({Key key}) : super(key: EmployeeDesignationRoutes.editScreenKey);

  @override
  Widget build(BuildContext context) {
    return BlocListener<EmployeeDesignationBloc, EmployeeDesignationState>(
      listener: (context, state) {
        if(state.formStatus.isSubmissionSuccess){
          Navigator.pushNamed(context, EmployeeDesignationRoutes.list);
        }
      },
      child: Scaffold(
          appBar: AppBar(
            centerTitle: true,
            title: BlocBuilder<EmployeeDesignationBloc, EmployeeDesignationState>(
                buildWhen: (previous, current) => previous.editMode != current.editMode,
                builder: (context, state) {
                String title = state.editMode == true ?S.of(context).pageEntitiesEmployeeDesignationUpdateTitle:
S.of(context).pageEntitiesEmployeeDesignationCreateTitle;
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
          designationField(),
          descriptionField(),
          designationDateField(),
          clientIdField(),
          hasExtraDataField(),
        validationZone(),
        submit(context)
      ]),
    );
  }

      Widget designationField() {
        return BlocBuilder<EmployeeDesignationBloc, EmployeeDesignationState>(
            buildWhen: (previous, current) => previous.designation != current.designation,
            builder: (context, state) {
              return TextFormField(
                  controller: context.bloc<EmployeeDesignationBloc>().designationController,
                  onChanged: (value) { context.bloc<EmployeeDesignationBloc>()
                    .add(DesignationChanged(designation:value)); },
                  keyboardType:TextInputType.text,                  decoration: InputDecoration(
                      labelText:S.of(context).pageEntitiesEmployeeDesignationDesignationField));
            }
        );
      }
      Widget descriptionField() {
        return BlocBuilder<EmployeeDesignationBloc, EmployeeDesignationState>(
            buildWhen: (previous, current) => previous.description != current.description,
            builder: (context, state) {
              return TextFormField(
                  controller: context.bloc<EmployeeDesignationBloc>().descriptionController,
                  onChanged: (value) { context.bloc<EmployeeDesignationBloc>()
                    .add(DescriptionChanged(description:value)); },
                  keyboardType:TextInputType.text,                  decoration: InputDecoration(
                      labelText:S.of(context).pageEntitiesEmployeeDesignationDescriptionField));
            }
        );
      }
      Widget designationDateField() {
        return BlocBuilder<EmployeeDesignationBloc, EmployeeDesignationState>(
            buildWhen: (previous, current) => previous.designationDate != current.designationDate,
            builder: (context, state) {
              return TextFormField(
                  controller: context.bloc<EmployeeDesignationBloc>().designationDateController,
                  onChanged: (value) { context.bloc<EmployeeDesignationBloc>()
                    .add(DesignationDateChanged(designationDate:value)); },
                  keyboardType:TextInputType.text,                  decoration: InputDecoration(
                      labelText:S.of(context).pageEntitiesEmployeeDesignationDesignationDateField));
            }
        );
      }
      Widget clientIdField() {
        return BlocBuilder<EmployeeDesignationBloc, EmployeeDesignationState>(
            buildWhen: (previous, current) => previous.clientId != current.clientId,
            builder: (context, state) {
              return TextFormField(
                  controller: context.bloc<EmployeeDesignationBloc>().clientIdController,
                  onChanged: (value) { context.bloc<EmployeeDesignationBloc>()
                    .add(ClientIdChanged(clientId:int.parse(value))); },
                  keyboardType:TextInputType.number,                  decoration: InputDecoration(
                      labelText:S.of(context).pageEntitiesEmployeeDesignationClientIdField));
            }
        );
      }
        Widget hasExtraDataField() {
          return BlocBuilder<EmployeeDesignationBloc,EmployeeDesignationState>(
              buildWhen: (previous, current) => previous.hasExtraData != current.hasExtraData,
              builder: (context, state) {
                return Padding(
                  padding: const EdgeInsets.only(left: 3.0),
                  child: Row(
                    mainAxisAlignment: MainAxisAlignment.spaceBetween,
                    children: <Widget>[
                      Text(S.of(context).pageEntitiesEmployeeDesignationHasExtraDataField, style: Theme.of(context).textTheme.bodyText1,),
                      Switch(
                          value: state.hasExtraData.value,
                          onChanged: (value) { context.bloc<EmployeeDesignationBloc>().add(HasExtraDataChanged(hasExtraData: value)); },
                          activeColor: Theme.of(context).primaryColor,),
                    ],
                  ),
                );
              });
        }


  Widget validationZone() {
    return BlocBuilder<EmployeeDesignationBloc, EmployeeDesignationState>(
        buildWhen:(previous, current) => previous.formStatus != current.formStatus,
        builder: (context, state) {
          return Visibility(
              visible: state.formStatus.isSubmissionFailure ||  state.formStatus.isSubmissionSuccess,
              child: Center(
                child: generateNotificationText(state, context),
              ));
        });
  }

  Widget generateNotificationText(EmployeeDesignationState state, BuildContext context) {
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
    return BlocBuilder<EmployeeDesignationBloc, EmployeeDesignationState>(
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
            onPressed: state.formStatus.isValidated ? () => context.bloc<EmployeeDesignationBloc>().add(EmployeeDesignationFormSubmitted()) : null,
          );
        }
    );
  }
}
