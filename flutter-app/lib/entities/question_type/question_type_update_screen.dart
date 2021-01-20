import 'package:datetime_picker_formfield/datetime_picker_formfield.dart';
import 'package:intl/intl.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:amcCarePlanner/entities/question_type/bloc/question_type_bloc.dart';
import 'package:amcCarePlanner/generated/l10n.dart';
import 'package:flutter/material.dart';
import 'package:formz/formz.dart';
import 'package:amcCarePlanner/shared/repository/http_utils.dart';
import 'package:amcCarePlanner/entities/question_type/question_type_model.dart';
import 'question_type_route.dart';

class QuestionTypeUpdateScreen extends StatelessWidget {
  QuestionTypeUpdateScreen({Key key}) : super(key: QuestionTypeRoutes.editScreenKey);

  @override
  Widget build(BuildContext context) {
    return BlocListener<QuestionTypeBloc, QuestionTypeState>(
      listener: (context, state) {
        if(state.formStatus.isSubmissionSuccess){
          Navigator.pushNamed(context, QuestionTypeRoutes.list);
        }
      },
      child: Scaffold(
          appBar: AppBar(
            centerTitle: true,
            title: BlocBuilder<QuestionTypeBloc, QuestionTypeState>(
                buildWhen: (previous, current) => previous.editMode != current.editMode,
                builder: (context, state) {
                String title = state.editMode == true ?S.of(context).pageEntitiesQuestionTypeUpdateTitle:
S.of(context).pageEntitiesQuestionTypeCreateTitle;
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
          questionTypeField(),
          lastUpdatedDateField(),
        validationZone(),
        submit(context)
      ]),
    );
  }

      Widget questionTypeField() {
        return BlocBuilder<QuestionTypeBloc, QuestionTypeState>(
            buildWhen: (previous, current) => previous.questionType != current.questionType,
            builder: (context, state) {
              return TextFormField(
                  controller: context.bloc<QuestionTypeBloc>().questionTypeController,
                  onChanged: (value) { context.bloc<QuestionTypeBloc>()
                    .add(QuestionTypeChanged(questionType:value)); },
                  keyboardType:TextInputType.text,                  decoration: InputDecoration(
                      labelText:S.of(context).pageEntitiesQuestionTypeQuestionTypeField));
            }
        );
      }
      Widget lastUpdatedDateField() {
        return BlocBuilder<QuestionTypeBloc, QuestionTypeState>(
            buildWhen: (previous, current) => previous.lastUpdatedDate != current.lastUpdatedDate,
            builder: (context, state) {
              return DateTimeField(
                controller: context.bloc<QuestionTypeBloc>().lastUpdatedDateController,
                onChanged: (value) { context.bloc<QuestionTypeBloc>().add(LastUpdatedDateChanged(lastUpdatedDate: value)); },
                format: DateFormat.yMMMMd(S.of(context).locale),
                keyboardType: TextInputType.datetime,
            decoration: InputDecoration(labelText:S.of(context).pageEntitiesQuestionTypeLastUpdatedDateField,),
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


  Widget validationZone() {
    return BlocBuilder<QuestionTypeBloc, QuestionTypeState>(
        buildWhen:(previous, current) => previous.formStatus != current.formStatus,
        builder: (context, state) {
          return Visibility(
              visible: state.formStatus.isSubmissionFailure ||  state.formStatus.isSubmissionSuccess,
              child: Center(
                child: generateNotificationText(state, context),
              ));
        });
  }

  Widget generateNotificationText(QuestionTypeState state, BuildContext context) {
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
    return BlocBuilder<QuestionTypeBloc, QuestionTypeState>(
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
            onPressed: state.formStatus.isValidated ? () => context.bloc<QuestionTypeBloc>().add(QuestionTypeFormSubmitted()) : null,
          );
        }
    );
  }
}
