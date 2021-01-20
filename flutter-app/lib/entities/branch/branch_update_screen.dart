import 'package:datetime_picker_formfield/datetime_picker_formfield.dart';
import 'package:intl/intl.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:amcCarePlanner/entities/branch/bloc/branch_bloc.dart';
import 'package:amcCarePlanner/generated/l10n.dart';
import 'package:flutter/material.dart';
import 'package:formz/formz.dart';
import 'package:amcCarePlanner/shared/repository/http_utils.dart';
import 'package:amcCarePlanner/entities/branch/branch_model.dart';
import 'branch_route.dart';

class BranchUpdateScreen extends StatelessWidget {
  BranchUpdateScreen({Key key}) : super(key: BranchRoutes.editScreenKey);

  @override
  Widget build(BuildContext context) {
    return BlocListener<BranchBloc, BranchState>(
      listener: (context, state) {
        if(state.formStatus.isSubmissionSuccess){
          Navigator.pushNamed(context, BranchRoutes.list);
        }
      },
      child: Scaffold(
          appBar: AppBar(
            centerTitle: true,
            title: BlocBuilder<BranchBloc, BranchState>(
                buildWhen: (previous, current) => previous.editMode != current.editMode,
                builder: (context, state) {
                String title = state.editMode == true ?S.of(context).pageEntitiesBranchUpdateTitle:
S.of(context).pageEntitiesBranchCreateTitle;
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
          nameField(),
          addressField(),
          telephoneField(),
          contactNameField(),
          branchEmailField(),
          photoUrlField(),
          createdDateField(),
          lastUpdatedDateField(),
          hasExtraDataField(),
        validationZone(),
        submit(context)
      ]),
    );
  }

      Widget nameField() {
        return BlocBuilder<BranchBloc, BranchState>(
            buildWhen: (previous, current) => previous.name != current.name,
            builder: (context, state) {
              return TextFormField(
                  controller: context.bloc<BranchBloc>().nameController,
                  onChanged: (value) { context.bloc<BranchBloc>()
                    .add(NameChanged(name:value)); },
                  keyboardType:TextInputType.text,                  decoration: InputDecoration(
                      labelText:S.of(context).pageEntitiesBranchNameField));
            }
        );
      }
      Widget addressField() {
        return BlocBuilder<BranchBloc, BranchState>(
            buildWhen: (previous, current) => previous.address != current.address,
            builder: (context, state) {
              return TextFormField(
                  controller: context.bloc<BranchBloc>().addressController,
                  onChanged: (value) { context.bloc<BranchBloc>()
                    .add(AddressChanged(address:value)); },
                  keyboardType:TextInputType.text,                  decoration: InputDecoration(
                      labelText:S.of(context).pageEntitiesBranchAddressField));
            }
        );
      }
      Widget telephoneField() {
        return BlocBuilder<BranchBloc, BranchState>(
            buildWhen: (previous, current) => previous.telephone != current.telephone,
            builder: (context, state) {
              return TextFormField(
                  controller: context.bloc<BranchBloc>().telephoneController,
                  onChanged: (value) { context.bloc<BranchBloc>()
                    .add(TelephoneChanged(telephone:value)); },
                  keyboardType:TextInputType.text,                  decoration: InputDecoration(
                      labelText:S.of(context).pageEntitiesBranchTelephoneField));
            }
        );
      }
      Widget contactNameField() {
        return BlocBuilder<BranchBloc, BranchState>(
            buildWhen: (previous, current) => previous.contactName != current.contactName,
            builder: (context, state) {
              return TextFormField(
                  controller: context.bloc<BranchBloc>().contactNameController,
                  onChanged: (value) { context.bloc<BranchBloc>()
                    .add(ContactNameChanged(contactName:value)); },
                  keyboardType:TextInputType.text,                  decoration: InputDecoration(
                      labelText:S.of(context).pageEntitiesBranchContactNameField));
            }
        );
      }
      Widget branchEmailField() {
        return BlocBuilder<BranchBloc, BranchState>(
            buildWhen: (previous, current) => previous.branchEmail != current.branchEmail,
            builder: (context, state) {
              return TextFormField(
                  controller: context.bloc<BranchBloc>().branchEmailController,
                  onChanged: (value) { context.bloc<BranchBloc>()
                    .add(BranchEmailChanged(branchEmail:value)); },
                  keyboardType:TextInputType.text,                  decoration: InputDecoration(
                      labelText:S.of(context).pageEntitiesBranchBranchEmailField));
            }
        );
      }
      Widget photoUrlField() {
        return BlocBuilder<BranchBloc, BranchState>(
            buildWhen: (previous, current) => previous.photoUrl != current.photoUrl,
            builder: (context, state) {
              return TextFormField(
                  controller: context.bloc<BranchBloc>().photoUrlController,
                  onChanged: (value) { context.bloc<BranchBloc>()
                    .add(PhotoUrlChanged(photoUrl:value)); },
                  keyboardType:TextInputType.text,                  decoration: InputDecoration(
                      labelText:S.of(context).pageEntitiesBranchPhotoUrlField));
            }
        );
      }
      Widget createdDateField() {
        return BlocBuilder<BranchBloc, BranchState>(
            buildWhen: (previous, current) => previous.createdDate != current.createdDate,
            builder: (context, state) {
              return DateTimeField(
                controller: context.bloc<BranchBloc>().createdDateController,
                onChanged: (value) { context.bloc<BranchBloc>().add(CreatedDateChanged(createdDate: value)); },
                format: DateFormat.yMMMMd(S.of(context).locale),
                keyboardType: TextInputType.datetime,
            decoration: InputDecoration(labelText:S.of(context).pageEntitiesBranchCreatedDateField,),
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
        return BlocBuilder<BranchBloc, BranchState>(
            buildWhen: (previous, current) => previous.lastUpdatedDate != current.lastUpdatedDate,
            builder: (context, state) {
              return DateTimeField(
                controller: context.bloc<BranchBloc>().lastUpdatedDateController,
                onChanged: (value) { context.bloc<BranchBloc>().add(LastUpdatedDateChanged(lastUpdatedDate: value)); },
                format: DateFormat.yMMMMd(S.of(context).locale),
                keyboardType: TextInputType.datetime,
            decoration: InputDecoration(labelText:S.of(context).pageEntitiesBranchLastUpdatedDateField,),
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
          return BlocBuilder<BranchBloc,BranchState>(
              buildWhen: (previous, current) => previous.hasExtraData != current.hasExtraData,
              builder: (context, state) {
                return Padding(
                  padding: const EdgeInsets.only(left: 3.0),
                  child: Row(
                    mainAxisAlignment: MainAxisAlignment.spaceBetween,
                    children: <Widget>[
                      Text(S.of(context).pageEntitiesBranchHasExtraDataField, style: Theme.of(context).textTheme.bodyText1,),
                      Switch(
                          value: state.hasExtraData.value,
                          onChanged: (value) { context.bloc<BranchBloc>().add(HasExtraDataChanged(hasExtraData: value)); },
                          activeColor: Theme.of(context).primaryColor,),
                    ],
                  ),
                );
              });
        }


  Widget validationZone() {
    return BlocBuilder<BranchBloc, BranchState>(
        buildWhen:(previous, current) => previous.formStatus != current.formStatus,
        builder: (context, state) {
          return Visibility(
              visible: state.formStatus.isSubmissionFailure ||  state.formStatus.isSubmissionSuccess,
              child: Center(
                child: generateNotificationText(state, context),
              ));
        });
  }

  Widget generateNotificationText(BranchState state, BuildContext context) {
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
    return BlocBuilder<BranchBloc, BranchState>(
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
            onPressed: state.formStatus.isValidated ? () => context.bloc<BranchBloc>().add(BranchFormSubmitted()) : null,
          );
        }
    );
  }
}
