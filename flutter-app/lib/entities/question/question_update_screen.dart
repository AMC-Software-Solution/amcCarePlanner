import 'package:datetime_picker_formfield/datetime_picker_formfield.dart';
import 'package:intl/intl.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:amcCarePlanner/entities/question/bloc/question_bloc.dart';
import 'package:amcCarePlanner/generated/l10n.dart';
import 'package:flutter/material.dart';
import 'package:formz/formz.dart';
import 'package:amcCarePlanner/shared/repository/http_utils.dart';
import 'package:amcCarePlanner/entities/question/question_model.dart';
import 'question_route.dart';

class QuestionUpdateScreen extends StatelessWidget {
  QuestionUpdateScreen({Key key}) : super(key: QuestionRoutes.editScreenKey);

  @override
  Widget build(BuildContext context) {
    return BlocListener<QuestionBloc, QuestionState>(
      listener: (context, state) {
        if(state.formStatus.isSubmissionSuccess){
          Navigator.pushNamed(context, QuestionRoutes.list);
        }
      },
      child: Scaffold(
          appBar: AppBar(
            centerTitle: true,
            title: BlocBuilder<QuestionBloc, QuestionState>(
                buildWhen: (previous, current) => previous.editMode != current.editMode,
                builder: (context, state) {
                String title = state.editMode == true ?S.of(context).pageEntitiesQuestionUpdateTitle:
S.of(context).pageEntitiesQuestionCreateTitle;
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
          questionField(),
          descriptionField(),
          attribute1Field(),
          attribute2Field(),
          attribute3Field(),
          attribute4Field(),
          attribute5Field(),
          optionalField(),
          createdDateField(),
          lastUpdatedDateField(),
          clientIdField(),
          hasExtraDataField(),
        validationZone(),
        submit(context)
      ]),
    );
  }

      Widget questionField() {
        return BlocBuilder<QuestionBloc, QuestionState>(
            buildWhen: (previous, current) => previous.question != current.question,
            builder: (context, state) {
              return TextFormField(
                  controller: context.bloc<QuestionBloc>().questionController,
                  onChanged: (value) { context.bloc<QuestionBloc>()
                    .add(QuestionChanged(question:value)); },
                  keyboardType:TextInputType.text,                  decoration: InputDecoration(
                      labelText:S.of(context).pageEntitiesQuestionQuestionField));
            }
        );
      }
      Widget descriptionField() {
        return BlocBuilder<QuestionBloc, QuestionState>(
            buildWhen: (previous, current) => previous.description != current.description,
            builder: (context, state) {
              return TextFormField(
                  controller: context.bloc<QuestionBloc>().descriptionController,
                  onChanged: (value) { context.bloc<QuestionBloc>()
                    .add(DescriptionChanged(description:value)); },
                  keyboardType:TextInputType.text,                  decoration: InputDecoration(
                      labelText:S.of(context).pageEntitiesQuestionDescriptionField));
            }
        );
      }
      Widget attribute1Field() {
        return BlocBuilder<QuestionBloc, QuestionState>(
            buildWhen: (previous, current) => previous.attribute1 != current.attribute1,
            builder: (context, state) {
              return TextFormField(
                  controller: context.bloc<QuestionBloc>().attribute1Controller,
                  onChanged: (value) { context.bloc<QuestionBloc>()
                    .add(Attribute1Changed(attribute1:value)); },
                  keyboardType:TextInputType.text,                  decoration: InputDecoration(
                      labelText:S.of(context).pageEntitiesQuestionAttribute1Field));
            }
        );
      }
      Widget attribute2Field() {
        return BlocBuilder<QuestionBloc, QuestionState>(
            buildWhen: (previous, current) => previous.attribute2 != current.attribute2,
            builder: (context, state) {
              return TextFormField(
                  controller: context.bloc<QuestionBloc>().attribute2Controller,
                  onChanged: (value) { context.bloc<QuestionBloc>()
                    .add(Attribute2Changed(attribute2:value)); },
                  keyboardType:TextInputType.text,                  decoration: InputDecoration(
                      labelText:S.of(context).pageEntitiesQuestionAttribute2Field));
            }
        );
      }
      Widget attribute3Field() {
        return BlocBuilder<QuestionBloc, QuestionState>(
            buildWhen: (previous, current) => previous.attribute3 != current.attribute3,
            builder: (context, state) {
              return TextFormField(
                  controller: context.bloc<QuestionBloc>().attribute3Controller,
                  onChanged: (value) { context.bloc<QuestionBloc>()
                    .add(Attribute3Changed(attribute3:value)); },
                  keyboardType:TextInputType.text,                  decoration: InputDecoration(
                      labelText:S.of(context).pageEntitiesQuestionAttribute3Field));
            }
        );
      }
      Widget attribute4Field() {
        return BlocBuilder<QuestionBloc, QuestionState>(
            buildWhen: (previous, current) => previous.attribute4 != current.attribute4,
            builder: (context, state) {
              return TextFormField(
                  controller: context.bloc<QuestionBloc>().attribute4Controller,
                  onChanged: (value) { context.bloc<QuestionBloc>()
                    .add(Attribute4Changed(attribute4:value)); },
                  keyboardType:TextInputType.text,                  decoration: InputDecoration(
                      labelText:S.of(context).pageEntitiesQuestionAttribute4Field));
            }
        );
      }
      Widget attribute5Field() {
        return BlocBuilder<QuestionBloc, QuestionState>(
            buildWhen: (previous, current) => previous.attribute5 != current.attribute5,
            builder: (context, state) {
              return TextFormField(
                  controller: context.bloc<QuestionBloc>().attribute5Controller,
                  onChanged: (value) { context.bloc<QuestionBloc>()
                    .add(Attribute5Changed(attribute5:value)); },
                  keyboardType:TextInputType.text,                  decoration: InputDecoration(
                      labelText:S.of(context).pageEntitiesQuestionAttribute5Field));
            }
        );
      }
        Widget optionalField() {
          return BlocBuilder<QuestionBloc,QuestionState>(
              buildWhen: (previous, current) => previous.optional != current.optional,
              builder: (context, state) {
                return Padding(
                  padding: const EdgeInsets.only(left: 3.0),
                  child: Row(
                    mainAxisAlignment: MainAxisAlignment.spaceBetween,
                    children: <Widget>[
                      Text(S.of(context).pageEntitiesQuestionOptionalField, style: Theme.of(context).textTheme.bodyText1,),
                      Switch(
                          value: state.optional.value,
                          onChanged: (value) { context.bloc<QuestionBloc>().add(OptionalChanged(optional: value)); },
                          activeColor: Theme.of(context).primaryColor,),
                    ],
                  ),
                );
              });
        }
      Widget createdDateField() {
        return BlocBuilder<QuestionBloc, QuestionState>(
            buildWhen: (previous, current) => previous.createdDate != current.createdDate,
            builder: (context, state) {
              return DateTimeField(
                controller: context.bloc<QuestionBloc>().createdDateController,
                onChanged: (value) { context.bloc<QuestionBloc>().add(CreatedDateChanged(createdDate: value)); },
                format: DateFormat.yMMMMd(S.of(context).locale),
                keyboardType: TextInputType.datetime,
            decoration: InputDecoration(labelText:S.of(context).pageEntitiesQuestionCreatedDateField,),
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
        return BlocBuilder<QuestionBloc, QuestionState>(
            buildWhen: (previous, current) => previous.lastUpdatedDate != current.lastUpdatedDate,
            builder: (context, state) {
              return DateTimeField(
                controller: context.bloc<QuestionBloc>().lastUpdatedDateController,
                onChanged: (value) { context.bloc<QuestionBloc>().add(LastUpdatedDateChanged(lastUpdatedDate: value)); },
                format: DateFormat.yMMMMd(S.of(context).locale),
                keyboardType: TextInputType.datetime,
            decoration: InputDecoration(labelText:S.of(context).pageEntitiesQuestionLastUpdatedDateField,),
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
        return BlocBuilder<QuestionBloc, QuestionState>(
            buildWhen: (previous, current) => previous.clientId != current.clientId,
            builder: (context, state) {
              return TextFormField(
                  controller: context.bloc<QuestionBloc>().clientIdController,
                  onChanged: (value) { context.bloc<QuestionBloc>()
                    .add(ClientIdChanged(clientId:int.parse(value))); },
                  keyboardType:TextInputType.number,                  decoration: InputDecoration(
                      labelText:S.of(context).pageEntitiesQuestionClientIdField));
            }
        );
      }
        Widget hasExtraDataField() {
          return BlocBuilder<QuestionBloc,QuestionState>(
              buildWhen: (previous, current) => previous.hasExtraData != current.hasExtraData,
              builder: (context, state) {
                return Padding(
                  padding: const EdgeInsets.only(left: 3.0),
                  child: Row(
                    mainAxisAlignment: MainAxisAlignment.spaceBetween,
                    children: <Widget>[
                      Text(S.of(context).pageEntitiesQuestionHasExtraDataField, style: Theme.of(context).textTheme.bodyText1,),
                      Switch(
                          value: state.hasExtraData.value,
                          onChanged: (value) { context.bloc<QuestionBloc>().add(HasExtraDataChanged(hasExtraData: value)); },
                          activeColor: Theme.of(context).primaryColor,),
                    ],
                  ),
                );
              });
        }


  Widget validationZone() {
    return BlocBuilder<QuestionBloc, QuestionState>(
        buildWhen:(previous, current) => previous.formStatus != current.formStatus,
        builder: (context, state) {
          return Visibility(
              visible: state.formStatus.isSubmissionFailure ||  state.formStatus.isSubmissionSuccess,
              child: Center(
                child: generateNotificationText(state, context),
              ));
        });
  }

  Widget generateNotificationText(QuestionState state, BuildContext context) {
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
    return BlocBuilder<QuestionBloc, QuestionState>(
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
            onPressed: state.formStatus.isValidated ? () => context.bloc<QuestionBloc>().add(QuestionFormSubmitted()) : null,
          );
        }
    );
  }
}
