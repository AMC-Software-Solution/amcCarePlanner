import 'package:datetime_picker_formfield/datetime_picker_formfield.dart';
import 'package:intl/intl.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:amcCarePlanner/entities/extra_data/bloc/extra_data_bloc.dart';
import 'package:amcCarePlanner/generated/l10n.dart';
import 'package:flutter/material.dart';
import 'package:formz/formz.dart';
import 'package:amcCarePlanner/shared/repository/http_utils.dart';
import 'package:amcCarePlanner/entities/extra_data/extra_data_model.dart';
import 'extra_data_route.dart';

class ExtraDataUpdateScreen extends StatelessWidget {
  ExtraDataUpdateScreen({Key key}) : super(key: ExtraDataRoutes.editScreenKey);

  @override
  Widget build(BuildContext context) {
    return BlocListener<ExtraDataBloc, ExtraDataState>(
      listener: (context, state) {
        if(state.formStatus.isSubmissionSuccess){
          Navigator.pushNamed(context, ExtraDataRoutes.list);
        }
      },
      child: Scaffold(
          appBar: AppBar(
            centerTitle: true,
            title: BlocBuilder<ExtraDataBloc, ExtraDataState>(
                buildWhen: (previous, current) => previous.editMode != current.editMode,
                builder: (context, state) {
                String title = state.editMode == true ?S.of(context).pageEntitiesExtraDataUpdateTitle:
S.of(context).pageEntitiesExtraDataCreateTitle;
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
          entityNameField(),
          entityIdField(),
          extraDataKeyField(),
          extraDataValueField(),
          extraDataValueDataTypeField(),
          extraDataDescriptionField(),
          extraDataDateField(),
          hasExtraDataField(),
        validationZone(),
        submit(context)
      ]),
    );
  }

      Widget entityNameField() {
        return BlocBuilder<ExtraDataBloc, ExtraDataState>(
            buildWhen: (previous, current) => previous.entityName != current.entityName,
            builder: (context, state) {
              return TextFormField(
                  controller: context.bloc<ExtraDataBloc>().entityNameController,
                  onChanged: (value) { context.bloc<ExtraDataBloc>()
                    .add(EntityNameChanged(entityName:value)); },
                  keyboardType:TextInputType.text,                  decoration: InputDecoration(
                      labelText:S.of(context).pageEntitiesExtraDataEntityNameField));
            }
        );
      }
      Widget entityIdField() {
        return BlocBuilder<ExtraDataBloc, ExtraDataState>(
            buildWhen: (previous, current) => previous.entityId != current.entityId,
            builder: (context, state) {
              return TextFormField(
                  controller: context.bloc<ExtraDataBloc>().entityIdController,
                  onChanged: (value) { context.bloc<ExtraDataBloc>()
                    .add(EntityIdChanged(entityId:int.parse(value))); },
                  keyboardType:TextInputType.number,                  decoration: InputDecoration(
                      labelText:S.of(context).pageEntitiesExtraDataEntityIdField));
            }
        );
      }
      Widget extraDataKeyField() {
        return BlocBuilder<ExtraDataBloc, ExtraDataState>(
            buildWhen: (previous, current) => previous.extraDataKey != current.extraDataKey,
            builder: (context, state) {
              return TextFormField(
                  controller: context.bloc<ExtraDataBloc>().extraDataKeyController,
                  onChanged: (value) { context.bloc<ExtraDataBloc>()
                    .add(ExtraDataKeyChanged(extraDataKey:value)); },
                  keyboardType:TextInputType.text,                  decoration: InputDecoration(
                      labelText:S.of(context).pageEntitiesExtraDataExtraDataKeyField));
            }
        );
      }
      Widget extraDataValueField() {
        return BlocBuilder<ExtraDataBloc, ExtraDataState>(
            buildWhen: (previous, current) => previous.extraDataValue != current.extraDataValue,
            builder: (context, state) {
              return TextFormField(
                  controller: context.bloc<ExtraDataBloc>().extraDataValueController,
                  onChanged: (value) { context.bloc<ExtraDataBloc>()
                    .add(ExtraDataValueChanged(extraDataValue:value)); },
                  keyboardType:TextInputType.text,                  decoration: InputDecoration(
                      labelText:S.of(context).pageEntitiesExtraDataExtraDataValueField));
            }
        );
      }
      Widget extraDataValueDataTypeField() {
        return BlocBuilder<ExtraDataBloc,ExtraDataState>(
            buildWhen: (previous, current) => previous.extraDataValueDataType != current.extraDataValueDataType,
            builder: (context, state) {
              return Padding(
                padding: const EdgeInsets.only(left: 3.0),
                child: Row(
                  mainAxisAlignment: MainAxisAlignment.spaceBetween,
                  children: <Widget>[
                    Text(S.of(context).pageEntitiesExtraDataExtraDataValueDataTypeField, style: Theme.of(context).textTheme.bodyText1,),
                    DropdownButton<DataType>(
                        value: state.extraDataValueDataType.value,
                        onChanged: (value) { context.bloc<ExtraDataBloc>().add(ExtraDataValueDataTypeChanged(extraDataValueDataType: value)); },
                        items: createDropdownDataTypeItems(DataType.values)),
                  ],
                ),
              );
            });
      }
      Widget extraDataDescriptionField() {
        return BlocBuilder<ExtraDataBloc, ExtraDataState>(
            buildWhen: (previous, current) => previous.extraDataDescription != current.extraDataDescription,
            builder: (context, state) {
              return TextFormField(
                  controller: context.bloc<ExtraDataBloc>().extraDataDescriptionController,
                  onChanged: (value) { context.bloc<ExtraDataBloc>()
                    .add(ExtraDataDescriptionChanged(extraDataDescription:value)); },
                  keyboardType:TextInputType.text,                  decoration: InputDecoration(
                      labelText:S.of(context).pageEntitiesExtraDataExtraDataDescriptionField));
            }
        );
      }
      Widget extraDataDateField() {
        return BlocBuilder<ExtraDataBloc, ExtraDataState>(
            buildWhen: (previous, current) => previous.extraDataDate != current.extraDataDate,
            builder: (context, state) {
              return DateTimeField(
                controller: context.bloc<ExtraDataBloc>().extraDataDateController,
                onChanged: (value) { context.bloc<ExtraDataBloc>().add(ExtraDataDateChanged(extraDataDate: value)); },
                format: DateFormat.yMMMMd(S.of(context).locale),
                keyboardType: TextInputType.datetime,
            decoration: InputDecoration(labelText:S.of(context).pageEntitiesExtraDataExtraDataDateField,),
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
          return BlocBuilder<ExtraDataBloc,ExtraDataState>(
              buildWhen: (previous, current) => previous.hasExtraData != current.hasExtraData,
              builder: (context, state) {
                return Padding(
                  padding: const EdgeInsets.only(left: 3.0),
                  child: Row(
                    mainAxisAlignment: MainAxisAlignment.spaceBetween,
                    children: <Widget>[
                      Text(S.of(context).pageEntitiesExtraDataHasExtraDataField, style: Theme.of(context).textTheme.bodyText1,),
                      Switch(
                          value: state.hasExtraData.value,
                          onChanged: (value) { context.bloc<ExtraDataBloc>().add(HasExtraDataChanged(hasExtraData: value)); },
                          activeColor: Theme.of(context).primaryColor,),
                    ],
                  ),
                );
              });
        }

      List<DropdownMenuItem<DataType>> createDropdownDataTypeItems(List<DataType> extraDataValueDataTypes) {
        List<DropdownMenuItem<DataType>> extraDataValueDataTypeDropDown = [];
    
        for (DataType extraDataValueDataType in extraDataValueDataTypes) {
          DropdownMenuItem<DataType> dropdown = DropdownMenuItem<DataType>(
              value: extraDataValueDataType, child: Text(extraDataValueDataType.toString()));
              extraDataValueDataTypeDropDown.add(dropdown);
        }
    
        return extraDataValueDataTypeDropDown;
      }

  Widget validationZone() {
    return BlocBuilder<ExtraDataBloc, ExtraDataState>(
        buildWhen:(previous, current) => previous.formStatus != current.formStatus,
        builder: (context, state) {
          return Visibility(
              visible: state.formStatus.isSubmissionFailure ||  state.formStatus.isSubmissionSuccess,
              child: Center(
                child: generateNotificationText(state, context),
              ));
        });
  }

  Widget generateNotificationText(ExtraDataState state, BuildContext context) {
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
    return BlocBuilder<ExtraDataBloc, ExtraDataState>(
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
            onPressed: state.formStatus.isValidated ? () => context.bloc<ExtraDataBloc>().add(ExtraDataFormSubmitted()) : null,
          );
        }
    );
  }
}
