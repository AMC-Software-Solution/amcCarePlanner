import 'package:datetime_picker_formfield/datetime_picker_formfield.dart';
import 'package:intl/intl.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:amcCarePlanner/entities/answer/bloc/answer_bloc.dart';
import 'package:amcCarePlanner/generated/l10n.dart';
import 'package:flutter/material.dart';
import 'package:formz/formz.dart';
import 'package:amcCarePlanner/shared/repository/http_utils.dart';
import 'package:amcCarePlanner/entities/answer/answer_model.dart';
import 'answer_route.dart';

class AnswerUpdateScreen extends StatelessWidget {
  AnswerUpdateScreen({Key key}) : super(key: AnswerRoutes.editScreenKey);

  @override
  Widget build(BuildContext context) {
    return BlocListener<AnswerBloc, AnswerState>(
      listener: (context, state) {
        if(state.formStatus.isSubmissionSuccess){
          Navigator.pushNamed(context, AnswerRoutes.list);
        }
      },
      child: Scaffold(
          appBar: AppBar(
            centerTitle: true,
            title: BlocBuilder<AnswerBloc, AnswerState>(
                buildWhen: (previous, current) => previous.editMode != current.editMode,
                builder: (context, state) {
                String title = state.editMode == true ?S.of(context).pageEntitiesAnswerUpdateTitle:
S.of(context).pageEntitiesAnswerCreateTitle;
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
          answerField(),
          descriptionField(),
          attribute1Field(),
          attribute2Field(),
          attribute3Field(),
          attribute4Field(),
          attribute5Field(),
          createdDateField(),
          lastUpdatedDateField(),
          clientIdField(),
          hasExtraDataField(),
        validationZone(),
        submit(context)
      ]),
    );
  }

      Widget answerField() {
        return BlocBuilder<AnswerBloc, AnswerState>(
            buildWhen: (previous, current) => previous.answer != current.answer,
            builder: (context, state) {
              return TextFormField(
                  controller: context.bloc<AnswerBloc>().answerController,
                  onChanged: (value) { context.bloc<AnswerBloc>()
                    .add(AnswerChanged(answer:value)); },
                  keyboardType:TextInputType.text,                  decoration: InputDecoration(
                      labelText:S.of(context).pageEntitiesAnswerAnswerField));
            }
        );
      }
      Widget descriptionField() {
        return BlocBuilder<AnswerBloc, AnswerState>(
            buildWhen: (previous, current) => previous.description != current.description,
            builder: (context, state) {
              return TextFormField(
                  controller: context.bloc<AnswerBloc>().descriptionController,
                  onChanged: (value) { context.bloc<AnswerBloc>()
                    .add(DescriptionChanged(description:value)); },
                  keyboardType:TextInputType.text,                  decoration: InputDecoration(
                      labelText:S.of(context).pageEntitiesAnswerDescriptionField));
            }
        );
      }
      Widget attribute1Field() {
        return BlocBuilder<AnswerBloc, AnswerState>(
            buildWhen: (previous, current) => previous.attribute1 != current.attribute1,
            builder: (context, state) {
              return TextFormField(
                  controller: context.bloc<AnswerBloc>().attribute1Controller,
                  onChanged: (value) { context.bloc<AnswerBloc>()
                    .add(Attribute1Changed(attribute1:value)); },
                  keyboardType:TextInputType.text,                  decoration: InputDecoration(
                      labelText:S.of(context).pageEntitiesAnswerAttribute1Field));
            }
        );
      }
      Widget attribute2Field() {
        return BlocBuilder<AnswerBloc, AnswerState>(
            buildWhen: (previous, current) => previous.attribute2 != current.attribute2,
            builder: (context, state) {
              return TextFormField(
                  controller: context.bloc<AnswerBloc>().attribute2Controller,
                  onChanged: (value) { context.bloc<AnswerBloc>()
                    .add(Attribute2Changed(attribute2:value)); },
                  keyboardType:TextInputType.text,                  decoration: InputDecoration(
                      labelText:S.of(context).pageEntitiesAnswerAttribute2Field));
            }
        );
      }
      Widget attribute3Field() {
        return BlocBuilder<AnswerBloc, AnswerState>(
            buildWhen: (previous, current) => previous.attribute3 != current.attribute3,
            builder: (context, state) {
              return TextFormField(
                  controller: context.bloc<AnswerBloc>().attribute3Controller,
                  onChanged: (value) { context.bloc<AnswerBloc>()
                    .add(Attribute3Changed(attribute3:value)); },
                  keyboardType:TextInputType.text,                  decoration: InputDecoration(
                      labelText:S.of(context).pageEntitiesAnswerAttribute3Field));
            }
        );
      }
      Widget attribute4Field() {
        return BlocBuilder<AnswerBloc, AnswerState>(
            buildWhen: (previous, current) => previous.attribute4 != current.attribute4,
            builder: (context, state) {
              return TextFormField(
                  controller: context.bloc<AnswerBloc>().attribute4Controller,
                  onChanged: (value) { context.bloc<AnswerBloc>()
                    .add(Attribute4Changed(attribute4:value)); },
                  keyboardType:TextInputType.text,                  decoration: InputDecoration(
                      labelText:S.of(context).pageEntitiesAnswerAttribute4Field));
            }
        );
      }
      Widget attribute5Field() {
        return BlocBuilder<AnswerBloc, AnswerState>(
            buildWhen: (previous, current) => previous.attribute5 != current.attribute5,
            builder: (context, state) {
              return TextFormField(
                  controller: context.bloc<AnswerBloc>().attribute5Controller,
                  onChanged: (value) { context.bloc<AnswerBloc>()
                    .add(Attribute5Changed(attribute5:value)); },
                  keyboardType:TextInputType.text,                  decoration: InputDecoration(
                      labelText:S.of(context).pageEntitiesAnswerAttribute5Field));
            }
        );
      }
      Widget createdDateField() {
        return BlocBuilder<AnswerBloc, AnswerState>(
            buildWhen: (previous, current) => previous.createdDate != current.createdDate,
            builder: (context, state) {
              return DateTimeField(
                controller: context.bloc<AnswerBloc>().createdDateController,
                onChanged: (value) { context.bloc<AnswerBloc>().add(CreatedDateChanged(createdDate: value)); },
                format: DateFormat.yMMMMd(S.of(context).locale),
                keyboardType: TextInputType.datetime,
            decoration: InputDecoration(labelText:S.of(context).pageEntitiesAnswerCreatedDateField,),
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
        return BlocBuilder<AnswerBloc, AnswerState>(
            buildWhen: (previous, current) => previous.lastUpdatedDate != current.lastUpdatedDate,
            builder: (context, state) {
              return DateTimeField(
                controller: context.bloc<AnswerBloc>().lastUpdatedDateController,
                onChanged: (value) { context.bloc<AnswerBloc>().add(LastUpdatedDateChanged(lastUpdatedDate: value)); },
                format: DateFormat.yMMMMd(S.of(context).locale),
                keyboardType: TextInputType.datetime,
            decoration: InputDecoration(labelText:S.of(context).pageEntitiesAnswerLastUpdatedDateField,),
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
        return BlocBuilder<AnswerBloc, AnswerState>(
            buildWhen: (previous, current) => previous.clientId != current.clientId,
            builder: (context, state) {
              return TextFormField(
                  controller: context.bloc<AnswerBloc>().clientIdController,
                  onChanged: (value) { context.bloc<AnswerBloc>()
                    .add(ClientIdChanged(clientId:int.parse(value))); },
                  keyboardType:TextInputType.number,                  decoration: InputDecoration(
                      labelText:S.of(context).pageEntitiesAnswerClientIdField));
            }
        );
      }
        Widget hasExtraDataField() {
          return BlocBuilder<AnswerBloc,AnswerState>(
              buildWhen: (previous, current) => previous.hasExtraData != current.hasExtraData,
              builder: (context, state) {
                return Padding(
                  padding: const EdgeInsets.only(left: 3.0),
                  child: Row(
                    mainAxisAlignment: MainAxisAlignment.spaceBetween,
                    children: <Widget>[
                      Text(S.of(context).pageEntitiesAnswerHasExtraDataField, style: Theme.of(context).textTheme.bodyText1,),
                      Switch(
                          value: state.hasExtraData.value,
                          onChanged: (value) { context.bloc<AnswerBloc>().add(HasExtraDataChanged(hasExtraData: value)); },
                          activeColor: Theme.of(context).primaryColor,),
                    ],
                  ),
                );
              });
        }


  Widget validationZone() {
    return BlocBuilder<AnswerBloc, AnswerState>(
        buildWhen:(previous, current) => previous.formStatus != current.formStatus,
        builder: (context, state) {
          return Visibility(
              visible: state.formStatus.isSubmissionFailure ||  state.formStatus.isSubmissionSuccess,
              child: Center(
                child: generateNotificationText(state, context),
              ));
        });
  }

  Widget generateNotificationText(AnswerState state, BuildContext context) {
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
    return BlocBuilder<AnswerBloc, AnswerState>(
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
            onPressed: state.formStatus.isValidated ? () => context.bloc<AnswerBloc>().add(AnswerFormSubmitted()) : null,
          );
        }
    );
  }
}
