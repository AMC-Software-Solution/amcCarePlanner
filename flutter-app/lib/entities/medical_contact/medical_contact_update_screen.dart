import 'package:datetime_picker_formfield/datetime_picker_formfield.dart';
import 'package:intl/intl.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:amcCarePlanner/entities/medical_contact/bloc/medical_contact_bloc.dart';
import 'package:amcCarePlanner/generated/l10n.dart';
import 'package:flutter/material.dart';
import 'package:formz/formz.dart';
import 'package:amcCarePlanner/shared/repository/http_utils.dart';
import 'package:amcCarePlanner/entities/medical_contact/medical_contact_model.dart';
import 'medical_contact_route.dart';

class MedicalContactUpdateScreen extends StatelessWidget {
  MedicalContactUpdateScreen({Key key}) : super(key: MedicalContactRoutes.editScreenKey);

  @override
  Widget build(BuildContext context) {
    return BlocListener<MedicalContactBloc, MedicalContactState>(
      listener: (context, state) {
        if(state.formStatus.isSubmissionSuccess){
          Navigator.pushNamed(context, MedicalContactRoutes.list);
        }
      },
      child: Scaffold(
          appBar: AppBar(
            centerTitle: true,
            title: BlocBuilder<MedicalContactBloc, MedicalContactState>(
                buildWhen: (previous, current) => previous.editMode != current.editMode,
                builder: (context, state) {
                String title = state.editMode == true ?S.of(context).pageEntitiesMedicalContactUpdateTitle:
S.of(context).pageEntitiesMedicalContactCreateTitle;
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
          doctorNameField(),
          doctorSurgeryField(),
          doctorAddressField(),
          doctorPhoneField(),
          lastVisitedDoctorField(),
          districtNurseNameField(),
          districtNursePhoneField(),
          careManagerNameField(),
          careManagerPhoneField(),
          createdDateField(),
          lastUpdatedDateField(),
          clientIdField(),
          hasExtraDataField(),
        validationZone(),
        submit(context)
      ]),
    );
  }

      Widget doctorNameField() {
        return BlocBuilder<MedicalContactBloc, MedicalContactState>(
            buildWhen: (previous, current) => previous.doctorName != current.doctorName,
            builder: (context, state) {
              return TextFormField(
                  controller: context.bloc<MedicalContactBloc>().doctorNameController,
                  onChanged: (value) { context.bloc<MedicalContactBloc>()
                    .add(DoctorNameChanged(doctorName:value)); },
                  keyboardType:TextInputType.text,                  decoration: InputDecoration(
                      labelText:S.of(context).pageEntitiesMedicalContactDoctorNameField));
            }
        );
      }
      Widget doctorSurgeryField() {
        return BlocBuilder<MedicalContactBloc, MedicalContactState>(
            buildWhen: (previous, current) => previous.doctorSurgery != current.doctorSurgery,
            builder: (context, state) {
              return TextFormField(
                  controller: context.bloc<MedicalContactBloc>().doctorSurgeryController,
                  onChanged: (value) { context.bloc<MedicalContactBloc>()
                    .add(DoctorSurgeryChanged(doctorSurgery:value)); },
                  keyboardType:TextInputType.text,                  decoration: InputDecoration(
                      labelText:S.of(context).pageEntitiesMedicalContactDoctorSurgeryField));
            }
        );
      }
      Widget doctorAddressField() {
        return BlocBuilder<MedicalContactBloc, MedicalContactState>(
            buildWhen: (previous, current) => previous.doctorAddress != current.doctorAddress,
            builder: (context, state) {
              return TextFormField(
                  controller: context.bloc<MedicalContactBloc>().doctorAddressController,
                  onChanged: (value) { context.bloc<MedicalContactBloc>()
                    .add(DoctorAddressChanged(doctorAddress:value)); },
                  keyboardType:TextInputType.text,                  decoration: InputDecoration(
                      labelText:S.of(context).pageEntitiesMedicalContactDoctorAddressField));
            }
        );
      }
      Widget doctorPhoneField() {
        return BlocBuilder<MedicalContactBloc, MedicalContactState>(
            buildWhen: (previous, current) => previous.doctorPhone != current.doctorPhone,
            builder: (context, state) {
              return TextFormField(
                  controller: context.bloc<MedicalContactBloc>().doctorPhoneController,
                  onChanged: (value) { context.bloc<MedicalContactBloc>()
                    .add(DoctorPhoneChanged(doctorPhone:value)); },
                  keyboardType:TextInputType.text,                  decoration: InputDecoration(
                      labelText:S.of(context).pageEntitiesMedicalContactDoctorPhoneField));
            }
        );
      }
      Widget lastVisitedDoctorField() {
        return BlocBuilder<MedicalContactBloc, MedicalContactState>(
            buildWhen: (previous, current) => previous.lastVisitedDoctor != current.lastVisitedDoctor,
            builder: (context, state) {
              return DateTimeField(
                controller: context.bloc<MedicalContactBloc>().lastVisitedDoctorController,
                onChanged: (value) { context.bloc<MedicalContactBloc>().add(LastVisitedDoctorChanged(lastVisitedDoctor: value)); },
                format: DateFormat.yMMMMd(S.of(context).locale),
                keyboardType: TextInputType.datetime,
            decoration: InputDecoration(labelText:S.of(context).pageEntitiesMedicalContactLastVisitedDoctorField,),
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
      Widget districtNurseNameField() {
        return BlocBuilder<MedicalContactBloc, MedicalContactState>(
            buildWhen: (previous, current) => previous.districtNurseName != current.districtNurseName,
            builder: (context, state) {
              return TextFormField(
                  controller: context.bloc<MedicalContactBloc>().districtNurseNameController,
                  onChanged: (value) { context.bloc<MedicalContactBloc>()
                    .add(DistrictNurseNameChanged(districtNurseName:value)); },
                  keyboardType:TextInputType.text,                  decoration: InputDecoration(
                      labelText:S.of(context).pageEntitiesMedicalContactDistrictNurseNameField));
            }
        );
      }
      Widget districtNursePhoneField() {
        return BlocBuilder<MedicalContactBloc, MedicalContactState>(
            buildWhen: (previous, current) => previous.districtNursePhone != current.districtNursePhone,
            builder: (context, state) {
              return TextFormField(
                  controller: context.bloc<MedicalContactBloc>().districtNursePhoneController,
                  onChanged: (value) { context.bloc<MedicalContactBloc>()
                    .add(DistrictNursePhoneChanged(districtNursePhone:value)); },
                  keyboardType:TextInputType.text,                  decoration: InputDecoration(
                      labelText:S.of(context).pageEntitiesMedicalContactDistrictNursePhoneField));
            }
        );
      }
      Widget careManagerNameField() {
        return BlocBuilder<MedicalContactBloc, MedicalContactState>(
            buildWhen: (previous, current) => previous.careManagerName != current.careManagerName,
            builder: (context, state) {
              return TextFormField(
                  controller: context.bloc<MedicalContactBloc>().careManagerNameController,
                  onChanged: (value) { context.bloc<MedicalContactBloc>()
                    .add(CareManagerNameChanged(careManagerName:value)); },
                  keyboardType:TextInputType.text,                  decoration: InputDecoration(
                      labelText:S.of(context).pageEntitiesMedicalContactCareManagerNameField));
            }
        );
      }
      Widget careManagerPhoneField() {
        return BlocBuilder<MedicalContactBloc, MedicalContactState>(
            buildWhen: (previous, current) => previous.careManagerPhone != current.careManagerPhone,
            builder: (context, state) {
              return TextFormField(
                  controller: context.bloc<MedicalContactBloc>().careManagerPhoneController,
                  onChanged: (value) { context.bloc<MedicalContactBloc>()
                    .add(CareManagerPhoneChanged(careManagerPhone:value)); },
                  keyboardType:TextInputType.text,                  decoration: InputDecoration(
                      labelText:S.of(context).pageEntitiesMedicalContactCareManagerPhoneField));
            }
        );
      }
      Widget createdDateField() {
        return BlocBuilder<MedicalContactBloc, MedicalContactState>(
            buildWhen: (previous, current) => previous.createdDate != current.createdDate,
            builder: (context, state) {
              return DateTimeField(
                controller: context.bloc<MedicalContactBloc>().createdDateController,
                onChanged: (value) { context.bloc<MedicalContactBloc>().add(CreatedDateChanged(createdDate: value)); },
                format: DateFormat.yMMMMd(S.of(context).locale),
                keyboardType: TextInputType.datetime,
            decoration: InputDecoration(labelText:S.of(context).pageEntitiesMedicalContactCreatedDateField,),
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
        return BlocBuilder<MedicalContactBloc, MedicalContactState>(
            buildWhen: (previous, current) => previous.lastUpdatedDate != current.lastUpdatedDate,
            builder: (context, state) {
              return DateTimeField(
                controller: context.bloc<MedicalContactBloc>().lastUpdatedDateController,
                onChanged: (value) { context.bloc<MedicalContactBloc>().add(LastUpdatedDateChanged(lastUpdatedDate: value)); },
                format: DateFormat.yMMMMd(S.of(context).locale),
                keyboardType: TextInputType.datetime,
            decoration: InputDecoration(labelText:S.of(context).pageEntitiesMedicalContactLastUpdatedDateField,),
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
        return BlocBuilder<MedicalContactBloc, MedicalContactState>(
            buildWhen: (previous, current) => previous.clientId != current.clientId,
            builder: (context, state) {
              return TextFormField(
                  controller: context.bloc<MedicalContactBloc>().clientIdController,
                  onChanged: (value) { context.bloc<MedicalContactBloc>()
                    .add(ClientIdChanged(clientId:int.parse(value))); },
                  keyboardType:TextInputType.number,                  decoration: InputDecoration(
                      labelText:S.of(context).pageEntitiesMedicalContactClientIdField));
            }
        );
      }
        Widget hasExtraDataField() {
          return BlocBuilder<MedicalContactBloc,MedicalContactState>(
              buildWhen: (previous, current) => previous.hasExtraData != current.hasExtraData,
              builder: (context, state) {
                return Padding(
                  padding: const EdgeInsets.only(left: 3.0),
                  child: Row(
                    mainAxisAlignment: MainAxisAlignment.spaceBetween,
                    children: <Widget>[
                      Text(S.of(context).pageEntitiesMedicalContactHasExtraDataField, style: Theme.of(context).textTheme.bodyText1,),
                      Switch(
                          value: state.hasExtraData.value,
                          onChanged: (value) { context.bloc<MedicalContactBloc>().add(HasExtraDataChanged(hasExtraData: value)); },
                          activeColor: Theme.of(context).primaryColor,),
                    ],
                  ),
                );
              });
        }


  Widget validationZone() {
    return BlocBuilder<MedicalContactBloc, MedicalContactState>(
        buildWhen:(previous, current) => previous.formStatus != current.formStatus,
        builder: (context, state) {
          return Visibility(
              visible: state.formStatus.isSubmissionFailure ||  state.formStatus.isSubmissionSuccess,
              child: Center(
                child: generateNotificationText(state, context),
              ));
        });
  }

  Widget generateNotificationText(MedicalContactState state, BuildContext context) {
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
    return BlocBuilder<MedicalContactBloc, MedicalContactState>(
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
            onPressed: state.formStatus.isValidated ? () => context.bloc<MedicalContactBloc>().add(MedicalContactFormSubmitted()) : null,
          );
        }
    );
  }
}
