import 'package:datetime_picker_formfield/datetime_picker_formfield.dart';
import 'package:intl/intl.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:amcCarePlanner/entities/employee/bloc/employee_bloc.dart';
import 'package:amcCarePlanner/generated/l10n.dart';
import 'package:flutter/material.dart';
import 'package:formz/formz.dart';
import 'package:amcCarePlanner/shared/repository/http_utils.dart';
import 'package:amcCarePlanner/entities/employee/employee_model.dart';
import 'employee_route.dart';

class EmployeeUpdateScreen extends StatelessWidget {
  EmployeeUpdateScreen({Key key}) : super(key: EmployeeRoutes.editScreenKey);

  @override
  Widget build(BuildContext context) {
    return BlocListener<EmployeeBloc, EmployeeState>(
      listener: (context, state) {
        if(state.formStatus.isSubmissionSuccess){
          Navigator.pushNamed(context, EmployeeRoutes.list);
        }
      },
      child: Scaffold(
          appBar: AppBar(
            centerTitle: true,
            title: BlocBuilder<EmployeeBloc, EmployeeState>(
                buildWhen: (previous, current) => previous.editMode != current.editMode,
                builder: (context, state) {
                String title = state.editMode == true ?S.of(context).pageEntitiesEmployeeUpdateTitle:
S.of(context).pageEntitiesEmployeeCreateTitle;
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
          tittleField(),
          firstNameField(),
          middleInitialField(),
          lastNameField(),
          preferredNameField(),
          gendderField(),
          employeeCodeField(),
          phoneField(),
          emailField(),
          nationalInsuranceNumberField(),
          employeeContractTypeField(),
          pinCodeField(),
          employeeTransportModeField(),
          addressField(),
          countyField(),
          postCodeField(),
          dateOfBirthField(),
          photoUrlField(),
          statusField(),
          employeeBioField(),
          acruedHolidayHoursField(),
          createdDateField(),
          lastUpdatedDateField(),
          clientIdField(),
          hasExtraDataField(),
        validationZone(),
        submit(context)
      ]),
    );
  }

      Widget tittleField() {
        return BlocBuilder<EmployeeBloc,EmployeeState>(
            buildWhen: (previous, current) => previous.tittle != current.tittle,
            builder: (context, state) {
              return Padding(
                padding: const EdgeInsets.only(left: 3.0),
                child: Row(
                  mainAxisAlignment: MainAxisAlignment.spaceBetween,
                  children: <Widget>[
                    Text(S.of(context).pageEntitiesEmployeeTittleField, style: Theme.of(context).textTheme.bodyText1,),
                    DropdownButton<Tittle>(
                        value: state.tittle.value,
                        onChanged: (value) { context.bloc<EmployeeBloc>().add(TittleChanged(tittle: value)); },
                        items: createDropdownTittleItems(Tittle.values)),
                  ],
                ),
              );
            });
      }
      Widget firstNameField() {
        return BlocBuilder<EmployeeBloc, EmployeeState>(
            buildWhen: (previous, current) => previous.firstName != current.firstName,
            builder: (context, state) {
              return TextFormField(
                  controller: context.bloc<EmployeeBloc>().firstNameController,
                  onChanged: (value) { context.bloc<EmployeeBloc>()
                    .add(FirstNameChanged(firstName:value)); },
                  keyboardType:TextInputType.text,                  decoration: InputDecoration(
                      labelText:S.of(context).pageEntitiesEmployeeFirstNameField));
            }
        );
      }
      Widget middleInitialField() {
        return BlocBuilder<EmployeeBloc, EmployeeState>(
            buildWhen: (previous, current) => previous.middleInitial != current.middleInitial,
            builder: (context, state) {
              return TextFormField(
                  controller: context.bloc<EmployeeBloc>().middleInitialController,
                  onChanged: (value) { context.bloc<EmployeeBloc>()
                    .add(MiddleInitialChanged(middleInitial:value)); },
                  keyboardType:TextInputType.text,                  decoration: InputDecoration(
                      labelText:S.of(context).pageEntitiesEmployeeMiddleInitialField));
            }
        );
      }
      Widget lastNameField() {
        return BlocBuilder<EmployeeBloc, EmployeeState>(
            buildWhen: (previous, current) => previous.lastName != current.lastName,
            builder: (context, state) {
              return TextFormField(
                  controller: context.bloc<EmployeeBloc>().lastNameController,
                  onChanged: (value) { context.bloc<EmployeeBloc>()
                    .add(LastNameChanged(lastName:value)); },
                  keyboardType:TextInputType.text,                  decoration: InputDecoration(
                      labelText:S.of(context).pageEntitiesEmployeeLastNameField));
            }
        );
      }
      Widget preferredNameField() {
        return BlocBuilder<EmployeeBloc, EmployeeState>(
            buildWhen: (previous, current) => previous.preferredName != current.preferredName,
            builder: (context, state) {
              return TextFormField(
                  controller: context.bloc<EmployeeBloc>().preferredNameController,
                  onChanged: (value) { context.bloc<EmployeeBloc>()
                    .add(PreferredNameChanged(preferredName:value)); },
                  keyboardType:TextInputType.text,                  decoration: InputDecoration(
                      labelText:S.of(context).pageEntitiesEmployeePreferredNameField));
            }
        );
      }
      Widget gendderField() {
        return BlocBuilder<EmployeeBloc,EmployeeState>(
            buildWhen: (previous, current) => previous.gendder != current.gendder,
            builder: (context, state) {
              return Padding(
                padding: const EdgeInsets.only(left: 3.0),
                child: Row(
                  mainAxisAlignment: MainAxisAlignment.spaceBetween,
                  children: <Widget>[
                    Text(S.of(context).pageEntitiesEmployeeGendderField, style: Theme.of(context).textTheme.bodyText1,),
                    DropdownButton<Gendder>(
                        value: state.gendder.value,
                        onChanged: (value) { context.bloc<EmployeeBloc>().add(GendderChanged(gendder: value)); },
                        items: createDropdownGendderItems(Gendder.values)),
                  ],
                ),
              );
            });
      }
      Widget employeeCodeField() {
        return BlocBuilder<EmployeeBloc, EmployeeState>(
            buildWhen: (previous, current) => previous.employeeCode != current.employeeCode,
            builder: (context, state) {
              return TextFormField(
                  controller: context.bloc<EmployeeBloc>().employeeCodeController,
                  onChanged: (value) { context.bloc<EmployeeBloc>()
                    .add(EmployeeCodeChanged(employeeCode:value)); },
                  keyboardType:TextInputType.text,                  decoration: InputDecoration(
                      labelText:S.of(context).pageEntitiesEmployeeEmployeeCodeField));
            }
        );
      }
      Widget phoneField() {
        return BlocBuilder<EmployeeBloc, EmployeeState>(
            buildWhen: (previous, current) => previous.phone != current.phone,
            builder: (context, state) {
              return TextFormField(
                  controller: context.bloc<EmployeeBloc>().phoneController,
                  onChanged: (value) { context.bloc<EmployeeBloc>()
                    .add(PhoneChanged(phone:value)); },
                  keyboardType:TextInputType.text,                  decoration: InputDecoration(
                      labelText:S.of(context).pageEntitiesEmployeePhoneField));
            }
        );
      }
      Widget emailField() {
        return BlocBuilder<EmployeeBloc, EmployeeState>(
            buildWhen: (previous, current) => previous.email != current.email,
            builder: (context, state) {
              return TextFormField(
                  controller: context.bloc<EmployeeBloc>().emailController,
                  onChanged: (value) { context.bloc<EmployeeBloc>()
                    .add(EmailChanged(email:value)); },
                  keyboardType:TextInputType.text,                  decoration: InputDecoration(
                      labelText:S.of(context).pageEntitiesEmployeeEmailField));
            }
        );
      }
      Widget nationalInsuranceNumberField() {
        return BlocBuilder<EmployeeBloc, EmployeeState>(
            buildWhen: (previous, current) => previous.nationalInsuranceNumber != current.nationalInsuranceNumber,
            builder: (context, state) {
              return TextFormField(
                  controller: context.bloc<EmployeeBloc>().nationalInsuranceNumberController,
                  onChanged: (value) { context.bloc<EmployeeBloc>()
                    .add(NationalInsuranceNumberChanged(nationalInsuranceNumber:value)); },
                  keyboardType:TextInputType.text,                  decoration: InputDecoration(
                      labelText:S.of(context).pageEntitiesEmployeeNationalInsuranceNumberField));
            }
        );
      }
      Widget employeeContractTypeField() {
        return BlocBuilder<EmployeeBloc,EmployeeState>(
            buildWhen: (previous, current) => previous.employeeContractType != current.employeeContractType,
            builder: (context, state) {
              return Padding(
                padding: const EdgeInsets.only(left: 3.0),
                child: Row(
                  mainAxisAlignment: MainAxisAlignment.spaceBetween,
                  children: <Widget>[
                    Text(S.of(context).pageEntitiesEmployeeEmployeeContractTypeField, style: Theme.of(context).textTheme.bodyText1,),
                    DropdownButton<EmployeeContractType>(
                        value: state.employeeContractType.value,
                        onChanged: (value) { context.bloc<EmployeeBloc>().add(EmployeeContractTypeChanged(employeeContractType: value)); },
                        items: createDropdownEmployeeContractTypeItems(EmployeeContractType.values)),
                  ],
                ),
              );
            });
      }
      Widget pinCodeField() {
        return BlocBuilder<EmployeeBloc, EmployeeState>(
            buildWhen: (previous, current) => previous.pinCode != current.pinCode,
            builder: (context, state) {
              return TextFormField(
                  controller: context.bloc<EmployeeBloc>().pinCodeController,
                  onChanged: (value) { context.bloc<EmployeeBloc>()
                    .add(PinCodeChanged(pinCode:int.parse(value))); },
                  keyboardType:TextInputType.number,                  decoration: InputDecoration(
                      labelText:S.of(context).pageEntitiesEmployeePinCodeField));
            }
        );
      }
      Widget employeeTransportModeField() {
        return BlocBuilder<EmployeeBloc,EmployeeState>(
            buildWhen: (previous, current) => previous.employeeTransportMode != current.employeeTransportMode,
            builder: (context, state) {
              return Padding(
                padding: const EdgeInsets.only(left: 3.0),
                child: Row(
                  mainAxisAlignment: MainAxisAlignment.spaceBetween,
                  children: <Widget>[
                    Text(S.of(context).pageEntitiesEmployeeEmployeeTransportModeField, style: Theme.of(context).textTheme.bodyText1,),
                    DropdownButton<EmployeeTravelMode>(
                        value: state.employeeTransportMode.value,
                        onChanged: (value) { context.bloc<EmployeeBloc>().add(EmployeeTransportModeChanged(employeeTransportMode: value)); },
                        items: createDropdownEmployeeTravelModeItems(EmployeeTravelMode.values)),
                  ],
                ),
              );
            });
      }
      Widget addressField() {
        return BlocBuilder<EmployeeBloc, EmployeeState>(
            buildWhen: (previous, current) => previous.address != current.address,
            builder: (context, state) {
              return TextFormField(
                  controller: context.bloc<EmployeeBloc>().addressController,
                  onChanged: (value) { context.bloc<EmployeeBloc>()
                    .add(AddressChanged(address:value)); },
                  keyboardType:TextInputType.text,                  decoration: InputDecoration(
                      labelText:S.of(context).pageEntitiesEmployeeAddressField));
            }
        );
      }
      Widget countyField() {
        return BlocBuilder<EmployeeBloc, EmployeeState>(
            buildWhen: (previous, current) => previous.county != current.county,
            builder: (context, state) {
              return TextFormField(
                  controller: context.bloc<EmployeeBloc>().countyController,
                  onChanged: (value) { context.bloc<EmployeeBloc>()
                    .add(CountyChanged(county:value)); },
                  keyboardType:TextInputType.text,                  decoration: InputDecoration(
                      labelText:S.of(context).pageEntitiesEmployeeCountyField));
            }
        );
      }
      Widget postCodeField() {
        return BlocBuilder<EmployeeBloc, EmployeeState>(
            buildWhen: (previous, current) => previous.postCode != current.postCode,
            builder: (context, state) {
              return TextFormField(
                  controller: context.bloc<EmployeeBloc>().postCodeController,
                  onChanged: (value) { context.bloc<EmployeeBloc>()
                    .add(PostCodeChanged(postCode:value)); },
                  keyboardType:TextInputType.text,                  decoration: InputDecoration(
                      labelText:S.of(context).pageEntitiesEmployeePostCodeField));
            }
        );
      }
      Widget dateOfBirthField() {
        return BlocBuilder<EmployeeBloc, EmployeeState>(
            buildWhen: (previous, current) => previous.dateOfBirth != current.dateOfBirth,
            builder: (context, state) {
              return DateTimeField(
                controller: context.bloc<EmployeeBloc>().dateOfBirthController,
                onChanged: (value) { context.bloc<EmployeeBloc>().add(DateOfBirthChanged(dateOfBirth: value)); },
                format: DateFormat.yMMMMd(S.of(context).locale),
                keyboardType: TextInputType.datetime,
            decoration: InputDecoration(labelText:S.of(context).pageEntitiesEmployeeDateOfBirthField,),
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
      Widget photoUrlField() {
        return BlocBuilder<EmployeeBloc, EmployeeState>(
            buildWhen: (previous, current) => previous.photoUrl != current.photoUrl,
            builder: (context, state) {
              return TextFormField(
                  controller: context.bloc<EmployeeBloc>().photoUrlController,
                  onChanged: (value) { context.bloc<EmployeeBloc>()
                    .add(PhotoUrlChanged(photoUrl:value)); },
                  keyboardType:TextInputType.text,                  decoration: InputDecoration(
                      labelText:S.of(context).pageEntitiesEmployeePhotoUrlField));
            }
        );
      }
      Widget statusField() {
        return BlocBuilder<EmployeeBloc,EmployeeState>(
            buildWhen: (previous, current) => previous.status != current.status,
            builder: (context, state) {
              return Padding(
                padding: const EdgeInsets.only(left: 3.0),
                child: Row(
                  mainAxisAlignment: MainAxisAlignment.spaceBetween,
                  children: <Widget>[
                    Text(S.of(context).pageEntitiesEmployeeStatusField, style: Theme.of(context).textTheme.bodyText1,),
                    DropdownButton<EmployeeStatus>(
                        value: state.status.value,
                        onChanged: (value) { context.bloc<EmployeeBloc>().add(StatusChanged(status: value)); },
                        items: createDropdownEmployeeStatusItems(EmployeeStatus.values)),
                  ],
                ),
              );
            });
      }
      Widget employeeBioField() {
        return BlocBuilder<EmployeeBloc, EmployeeState>(
            buildWhen: (previous, current) => previous.employeeBio != current.employeeBio,
            builder: (context, state) {
              return TextFormField(
                  controller: context.bloc<EmployeeBloc>().employeeBioController,
                  onChanged: (value) { context.bloc<EmployeeBloc>()
                    .add(EmployeeBioChanged(employeeBio:value)); },
                  keyboardType:TextInputType.text,                  decoration: InputDecoration(
                      labelText:S.of(context).pageEntitiesEmployeeEmployeeBioField));
            }
        );
      }
      Widget acruedHolidayHoursField() {
        return BlocBuilder<EmployeeBloc, EmployeeState>(
            buildWhen: (previous, current) => previous.acruedHolidayHours != current.acruedHolidayHours,
            builder: (context, state) {
              return TextFormField(
                  controller: context.bloc<EmployeeBloc>().acruedHolidayHoursController,
                  onChanged: (value) { context.bloc<EmployeeBloc>()
                    .add(AcruedHolidayHoursChanged(acruedHolidayHours:int.parse(value))); },
                  keyboardType:TextInputType.number,                  decoration: InputDecoration(
                      labelText:S.of(context).pageEntitiesEmployeeAcruedHolidayHoursField));
            }
        );
      }
      Widget createdDateField() {
        return BlocBuilder<EmployeeBloc, EmployeeState>(
            buildWhen: (previous, current) => previous.createdDate != current.createdDate,
            builder: (context, state) {
              return DateTimeField(
                controller: context.bloc<EmployeeBloc>().createdDateController,
                onChanged: (value) { context.bloc<EmployeeBloc>().add(CreatedDateChanged(createdDate: value)); },
                format: DateFormat.yMMMMd(S.of(context).locale),
                keyboardType: TextInputType.datetime,
            decoration: InputDecoration(labelText:S.of(context).pageEntitiesEmployeeCreatedDateField,),
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
        return BlocBuilder<EmployeeBloc, EmployeeState>(
            buildWhen: (previous, current) => previous.lastUpdatedDate != current.lastUpdatedDate,
            builder: (context, state) {
              return DateTimeField(
                controller: context.bloc<EmployeeBloc>().lastUpdatedDateController,
                onChanged: (value) { context.bloc<EmployeeBloc>().add(LastUpdatedDateChanged(lastUpdatedDate: value)); },
                format: DateFormat.yMMMMd(S.of(context).locale),
                keyboardType: TextInputType.datetime,
            decoration: InputDecoration(labelText:S.of(context).pageEntitiesEmployeeLastUpdatedDateField,),
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
        return BlocBuilder<EmployeeBloc, EmployeeState>(
            buildWhen: (previous, current) => previous.clientId != current.clientId,
            builder: (context, state) {
              return TextFormField(
                  controller: context.bloc<EmployeeBloc>().clientIdController,
                  onChanged: (value) { context.bloc<EmployeeBloc>()
                    .add(ClientIdChanged(clientId:int.parse(value))); },
                  keyboardType:TextInputType.number,                  decoration: InputDecoration(
                      labelText:S.of(context).pageEntitiesEmployeeClientIdField));
            }
        );
      }
        Widget hasExtraDataField() {
          return BlocBuilder<EmployeeBloc,EmployeeState>(
              buildWhen: (previous, current) => previous.hasExtraData != current.hasExtraData,
              builder: (context, state) {
                return Padding(
                  padding: const EdgeInsets.only(left: 3.0),
                  child: Row(
                    mainAxisAlignment: MainAxisAlignment.spaceBetween,
                    children: <Widget>[
                      Text(S.of(context).pageEntitiesEmployeeHasExtraDataField, style: Theme.of(context).textTheme.bodyText1,),
                      Switch(
                          value: state.hasExtraData.value,
                          onChanged: (value) { context.bloc<EmployeeBloc>().add(HasExtraDataChanged(hasExtraData: value)); },
                          activeColor: Theme.of(context).primaryColor,),
                    ],
                  ),
                );
              });
        }

      List<DropdownMenuItem<Tittle>> createDropdownTittleItems(List<Tittle> tittles) {
        List<DropdownMenuItem<Tittle>> tittleDropDown = [];
    
        for (Tittle tittle in tittles) {
          DropdownMenuItem<Tittle> dropdown = DropdownMenuItem<Tittle>(
              value: tittle, child: Text(tittle.toString()));
              tittleDropDown.add(dropdown);
        }
    
        return tittleDropDown;
      }
      List<DropdownMenuItem<Gendder>> createDropdownGendderItems(List<Gendder> gendders) {
        List<DropdownMenuItem<Gendder>> gendderDropDown = [];
    
        for (Gendder gendder in gendders) {
          DropdownMenuItem<Gendder> dropdown = DropdownMenuItem<Gendder>(
              value: gendder, child: Text(gendder.toString()));
              gendderDropDown.add(dropdown);
        }
    
        return gendderDropDown;
      }
      List<DropdownMenuItem<EmployeeContractType>> createDropdownEmployeeContractTypeItems(List<EmployeeContractType> employeeContractTypes) {
        List<DropdownMenuItem<EmployeeContractType>> employeeContractTypeDropDown = [];
    
        for (EmployeeContractType employeeContractType in employeeContractTypes) {
          DropdownMenuItem<EmployeeContractType> dropdown = DropdownMenuItem<EmployeeContractType>(
              value: employeeContractType, child: Text(employeeContractType.toString()));
              employeeContractTypeDropDown.add(dropdown);
        }
    
        return employeeContractTypeDropDown;
      }
      List<DropdownMenuItem<EmployeeTravelMode>> createDropdownEmployeeTravelModeItems(List<EmployeeTravelMode> employeeTransportModes) {
        List<DropdownMenuItem<EmployeeTravelMode>> employeeTransportModeDropDown = [];
    
        for (EmployeeTravelMode employeeTransportMode in employeeTransportModes) {
          DropdownMenuItem<EmployeeTravelMode> dropdown = DropdownMenuItem<EmployeeTravelMode>(
              value: employeeTransportMode, child: Text(employeeTransportMode.toString()));
              employeeTransportModeDropDown.add(dropdown);
        }
    
        return employeeTransportModeDropDown;
      }
      List<DropdownMenuItem<EmployeeStatus>> createDropdownEmployeeStatusItems(List<EmployeeStatus> statuss) {
        List<DropdownMenuItem<EmployeeStatus>> statusDropDown = [];
    
        for (EmployeeStatus status in statuss) {
          DropdownMenuItem<EmployeeStatus> dropdown = DropdownMenuItem<EmployeeStatus>(
              value: status, child: Text(status.toString()));
              statusDropDown.add(dropdown);
        }
    
        return statusDropDown;
      }

  Widget validationZone() {
    return BlocBuilder<EmployeeBloc, EmployeeState>(
        buildWhen:(previous, current) => previous.formStatus != current.formStatus,
        builder: (context, state) {
          return Visibility(
              visible: state.formStatus.isSubmissionFailure ||  state.formStatus.isSubmissionSuccess,
              child: Center(
                child: generateNotificationText(state, context),
              ));
        });
  }

  Widget generateNotificationText(EmployeeState state, BuildContext context) {
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
    return BlocBuilder<EmployeeBloc, EmployeeState>(
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
            onPressed: state.formStatus.isValidated ? () => context.bloc<EmployeeBloc>().add(EmployeeFormSubmitted()) : null,
          );
        }
    );
  }
}
