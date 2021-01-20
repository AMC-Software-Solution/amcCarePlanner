import 'package:datetime_picker_formfield/datetime_picker_formfield.dart';
import 'package:intl/intl.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:amcCarePlanner/entities/service_user/bloc/service_user_bloc.dart';
import 'package:amcCarePlanner/generated/l10n.dart';
import 'package:flutter/material.dart';
import 'package:formz/formz.dart';
import 'package:amcCarePlanner/shared/repository/http_utils.dart';
import 'package:amcCarePlanner/entities/service_user/service_user_model.dart';
import 'service_user_route.dart';

class ServiceUserUpdateScreen extends StatelessWidget {
  ServiceUserUpdateScreen({Key key}) : super(key: ServiceUserRoutes.editScreenKey);

  @override
  Widget build(BuildContext context) {
    return BlocListener<ServiceUserBloc, ServiceUserState>(
      listener: (context, state) {
        if(state.formStatus.isSubmissionSuccess){
          Navigator.pushNamed(context, ServiceUserRoutes.list);
        }
      },
      child: Scaffold(
          appBar: AppBar(
            centerTitle: true,
            title: BlocBuilder<ServiceUserBloc, ServiceUserState>(
                buildWhen: (previous, current) => previous.editMode != current.editMode,
                builder: (context, state) {
                String title = state.editMode == true ?S.of(context).pageEntitiesServiceUserUpdateTitle:
S.of(context).pageEntitiesServiceUserCreateTitle;
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
          titlleField(),
          firstNameField(),
          middleNameField(),
          lastNameField(),
          preferredNameField(),
          emailField(),
          serviceUserCodeField(),
          dateOfBirthField(),
          lastVisitDateField(),
          startDateField(),
          supportTypeField(),
          serviceUserCategoryField(),
          vulnerabilityField(),
          servicePriorityField(),
          sourceField(),
          statusField(),
          firstLanguageField(),
          interpreterRequiredField(),
          activatedDateField(),
          profilePhotoUrlField(),
          lastRecordedHeightField(),
          lastRecordedWeightField(),
          hasMedicalConditionField(),
          medicalConditionSummaryField(),
          createdDateField(),
          lastUpdatedDateField(),
          clientIdField(),
          hasExtraDataField(),
        validationZone(),
        submit(context)
      ]),
    );
  }

      Widget titlleField() {
        return BlocBuilder<ServiceUserBloc,ServiceUserState>(
            buildWhen: (previous, current) => previous.titlle != current.titlle,
            builder: (context, state) {
              return Padding(
                padding: const EdgeInsets.only(left: 3.0),
                child: Row(
                  mainAxisAlignment: MainAxisAlignment.spaceBetween,
                  children: <Widget>[
                    Text(S.of(context).pageEntitiesServiceUserTitlleField, style: Theme.of(context).textTheme.bodyText1,),
                    DropdownButton<Titlle>(
                        value: state.titlle.value,
                        onChanged: (value) { context.bloc<ServiceUserBloc>().add(TitlleChanged(titlle: value)); },
                        items: createDropdownTitlleItems(Titlle.values)),
                  ],
                ),
              );
            });
      }
      Widget firstNameField() {
        return BlocBuilder<ServiceUserBloc, ServiceUserState>(
            buildWhen: (previous, current) => previous.firstName != current.firstName,
            builder: (context, state) {
              return TextFormField(
                  controller: context.bloc<ServiceUserBloc>().firstNameController,
                  onChanged: (value) { context.bloc<ServiceUserBloc>()
                    .add(FirstNameChanged(firstName:value)); },
                  keyboardType:TextInputType.text,                  decoration: InputDecoration(
                      labelText:S.of(context).pageEntitiesServiceUserFirstNameField));
            }
        );
      }
      Widget middleNameField() {
        return BlocBuilder<ServiceUserBloc, ServiceUserState>(
            buildWhen: (previous, current) => previous.middleName != current.middleName,
            builder: (context, state) {
              return TextFormField(
                  controller: context.bloc<ServiceUserBloc>().middleNameController,
                  onChanged: (value) { context.bloc<ServiceUserBloc>()
                    .add(MiddleNameChanged(middleName:value)); },
                  keyboardType:TextInputType.text,                  decoration: InputDecoration(
                      labelText:S.of(context).pageEntitiesServiceUserMiddleNameField));
            }
        );
      }
      Widget lastNameField() {
        return BlocBuilder<ServiceUserBloc, ServiceUserState>(
            buildWhen: (previous, current) => previous.lastName != current.lastName,
            builder: (context, state) {
              return TextFormField(
                  controller: context.bloc<ServiceUserBloc>().lastNameController,
                  onChanged: (value) { context.bloc<ServiceUserBloc>()
                    .add(LastNameChanged(lastName:value)); },
                  keyboardType:TextInputType.text,                  decoration: InputDecoration(
                      labelText:S.of(context).pageEntitiesServiceUserLastNameField));
            }
        );
      }
      Widget preferredNameField() {
        return BlocBuilder<ServiceUserBloc, ServiceUserState>(
            buildWhen: (previous, current) => previous.preferredName != current.preferredName,
            builder: (context, state) {
              return TextFormField(
                  controller: context.bloc<ServiceUserBloc>().preferredNameController,
                  onChanged: (value) { context.bloc<ServiceUserBloc>()
                    .add(PreferredNameChanged(preferredName:value)); },
                  keyboardType:TextInputType.text,                  decoration: InputDecoration(
                      labelText:S.of(context).pageEntitiesServiceUserPreferredNameField));
            }
        );
      }
      Widget emailField() {
        return BlocBuilder<ServiceUserBloc, ServiceUserState>(
            buildWhen: (previous, current) => previous.email != current.email,
            builder: (context, state) {
              return TextFormField(
                  controller: context.bloc<ServiceUserBloc>().emailController,
                  onChanged: (value) { context.bloc<ServiceUserBloc>()
                    .add(EmailChanged(email:value)); },
                  keyboardType:TextInputType.text,                  decoration: InputDecoration(
                      labelText:S.of(context).pageEntitiesServiceUserEmailField));
            }
        );
      }
      Widget serviceUserCodeField() {
        return BlocBuilder<ServiceUserBloc, ServiceUserState>(
            buildWhen: (previous, current) => previous.serviceUserCode != current.serviceUserCode,
            builder: (context, state) {
              return TextFormField(
                  controller: context.bloc<ServiceUserBloc>().serviceUserCodeController,
                  onChanged: (value) { context.bloc<ServiceUserBloc>()
                    .add(ServiceUserCodeChanged(serviceUserCode:value)); },
                  keyboardType:TextInputType.text,                  decoration: InputDecoration(
                      labelText:S.of(context).pageEntitiesServiceUserServiceUserCodeField));
            }
        );
      }
      Widget dateOfBirthField() {
        return BlocBuilder<ServiceUserBloc, ServiceUserState>(
            buildWhen: (previous, current) => previous.dateOfBirth != current.dateOfBirth,
            builder: (context, state) {
              return DateTimeField(
                controller: context.bloc<ServiceUserBloc>().dateOfBirthController,
                onChanged: (value) { context.bloc<ServiceUserBloc>().add(DateOfBirthChanged(dateOfBirth: value)); },
                format: DateFormat.yMMMMd(S.of(context).locale),
                keyboardType: TextInputType.datetime,
            decoration: InputDecoration(labelText:S.of(context).pageEntitiesServiceUserDateOfBirthField,),
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
      Widget lastVisitDateField() {
        return BlocBuilder<ServiceUserBloc, ServiceUserState>(
            buildWhen: (previous, current) => previous.lastVisitDate != current.lastVisitDate,
            builder: (context, state) {
              return DateTimeField(
                controller: context.bloc<ServiceUserBloc>().lastVisitDateController,
                onChanged: (value) { context.bloc<ServiceUserBloc>().add(LastVisitDateChanged(lastVisitDate: value)); },
                format: DateFormat.yMMMMd(S.of(context).locale),
                keyboardType: TextInputType.datetime,
            decoration: InputDecoration(labelText:S.of(context).pageEntitiesServiceUserLastVisitDateField,),
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
      Widget startDateField() {
        return BlocBuilder<ServiceUserBloc, ServiceUserState>(
            buildWhen: (previous, current) => previous.startDate != current.startDate,
            builder: (context, state) {
              return DateTimeField(
                controller: context.bloc<ServiceUserBloc>().startDateController,
                onChanged: (value) { context.bloc<ServiceUserBloc>().add(StartDateChanged(startDate: value)); },
                format: DateFormat.yMMMMd(S.of(context).locale),
                keyboardType: TextInputType.datetime,
            decoration: InputDecoration(labelText:S.of(context).pageEntitiesServiceUserStartDateField,),
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
      Widget supportTypeField() {
        return BlocBuilder<ServiceUserBloc,ServiceUserState>(
            buildWhen: (previous, current) => previous.supportType != current.supportType,
            builder: (context, state) {
              return Padding(
                padding: const EdgeInsets.only(left: 3.0),
                child: Row(
                  mainAxisAlignment: MainAxisAlignment.spaceBetween,
                  children: <Widget>[
                    Text(S.of(context).pageEntitiesServiceUserSupportTypeField, style: Theme.of(context).textTheme.bodyText1,),
                    DropdownButton<SupportType>(
                        value: state.supportType.value,
                        onChanged: (value) { context.bloc<ServiceUserBloc>().add(SupportTypeChanged(supportType: value)); },
                        items: createDropdownSupportTypeItems(SupportType.values)),
                  ],
                ),
              );
            });
      }
      Widget serviceUserCategoryField() {
        return BlocBuilder<ServiceUserBloc,ServiceUserState>(
            buildWhen: (previous, current) => previous.serviceUserCategory != current.serviceUserCategory,
            builder: (context, state) {
              return Padding(
                padding: const EdgeInsets.only(left: 3.0),
                child: Row(
                  mainAxisAlignment: MainAxisAlignment.spaceBetween,
                  children: <Widget>[
                    Text(S.of(context).pageEntitiesServiceUserServiceUserCategoryField, style: Theme.of(context).textTheme.bodyText1,),
                    DropdownButton<ServiceUserCategory>(
                        value: state.serviceUserCategory.value,
                        onChanged: (value) { context.bloc<ServiceUserBloc>().add(ServiceUserCategoryChanged(serviceUserCategory: value)); },
                        items: createDropdownServiceUserCategoryItems(ServiceUserCategory.values)),
                  ],
                ),
              );
            });
      }
      Widget vulnerabilityField() {
        return BlocBuilder<ServiceUserBloc,ServiceUserState>(
            buildWhen: (previous, current) => previous.vulnerability != current.vulnerability,
            builder: (context, state) {
              return Padding(
                padding: const EdgeInsets.only(left: 3.0),
                child: Row(
                  mainAxisAlignment: MainAxisAlignment.spaceBetween,
                  children: <Widget>[
                    Text(S.of(context).pageEntitiesServiceUserVulnerabilityField, style: Theme.of(context).textTheme.bodyText1,),
                    DropdownButton<Vulnerability>(
                        value: state.vulnerability.value,
                        onChanged: (value) { context.bloc<ServiceUserBloc>().add(VulnerabilityChanged(vulnerability: value)); },
                        items: createDropdownVulnerabilityItems(Vulnerability.values)),
                  ],
                ),
              );
            });
      }
      Widget servicePriorityField() {
        return BlocBuilder<ServiceUserBloc,ServiceUserState>(
            buildWhen: (previous, current) => previous.servicePriority != current.servicePriority,
            builder: (context, state) {
              return Padding(
                padding: const EdgeInsets.only(left: 3.0),
                child: Row(
                  mainAxisAlignment: MainAxisAlignment.spaceBetween,
                  children: <Widget>[
                    Text(S.of(context).pageEntitiesServiceUserServicePriorityField, style: Theme.of(context).textTheme.bodyText1,),
                    DropdownButton<ServicePriority>(
                        value: state.servicePriority.value,
                        onChanged: (value) { context.bloc<ServiceUserBloc>().add(ServicePriorityChanged(servicePriority: value)); },
                        items: createDropdownServicePriorityItems(ServicePriority.values)),
                  ],
                ),
              );
            });
      }
      Widget sourceField() {
        return BlocBuilder<ServiceUserBloc,ServiceUserState>(
            buildWhen: (previous, current) => previous.source != current.source,
            builder: (context, state) {
              return Padding(
                padding: const EdgeInsets.only(left: 3.0),
                child: Row(
                  mainAxisAlignment: MainAxisAlignment.spaceBetween,
                  children: <Widget>[
                    Text(S.of(context).pageEntitiesServiceUserSourceField, style: Theme.of(context).textTheme.bodyText1,),
                    DropdownButton<Source>(
                        value: state.source.value,
                        onChanged: (value) { context.bloc<ServiceUserBloc>().add(SourceChanged(source: value)); },
                        items: createDropdownSourceItems(Source.values)),
                  ],
                ),
              );
            });
      }
      Widget statusField() {
        return BlocBuilder<ServiceUserBloc,ServiceUserState>(
            buildWhen: (previous, current) => previous.status != current.status,
            builder: (context, state) {
              return Padding(
                padding: const EdgeInsets.only(left: 3.0),
                child: Row(
                  mainAxisAlignment: MainAxisAlignment.spaceBetween,
                  children: <Widget>[
                    Text(S.of(context).pageEntitiesServiceUserStatusField, style: Theme.of(context).textTheme.bodyText1,),
                    DropdownButton<ServiceUserStatus>(
                        value: state.status.value,
                        onChanged: (value) { context.bloc<ServiceUserBloc>().add(StatusChanged(status: value)); },
                        items: createDropdownServiceUserStatusItems(ServiceUserStatus.values)),
                  ],
                ),
              );
            });
      }
      Widget firstLanguageField() {
        return BlocBuilder<ServiceUserBloc, ServiceUserState>(
            buildWhen: (previous, current) => previous.firstLanguage != current.firstLanguage,
            builder: (context, state) {
              return TextFormField(
                  controller: context.bloc<ServiceUserBloc>().firstLanguageController,
                  onChanged: (value) { context.bloc<ServiceUserBloc>()
                    .add(FirstLanguageChanged(firstLanguage:value)); },
                  keyboardType:TextInputType.text,                  decoration: InputDecoration(
                      labelText:S.of(context).pageEntitiesServiceUserFirstLanguageField));
            }
        );
      }
        Widget interpreterRequiredField() {
          return BlocBuilder<ServiceUserBloc,ServiceUserState>(
              buildWhen: (previous, current) => previous.interpreterRequired != current.interpreterRequired,
              builder: (context, state) {
                return Padding(
                  padding: const EdgeInsets.only(left: 3.0),
                  child: Row(
                    mainAxisAlignment: MainAxisAlignment.spaceBetween,
                    children: <Widget>[
                      Text(S.of(context).pageEntitiesServiceUserInterpreterRequiredField, style: Theme.of(context).textTheme.bodyText1,),
                      Switch(
                          value: state.interpreterRequired.value,
                          onChanged: (value) { context.bloc<ServiceUserBloc>().add(InterpreterRequiredChanged(interpreterRequired: value)); },
                          activeColor: Theme.of(context).primaryColor,),
                    ],
                  ),
                );
              });
        }
      Widget activatedDateField() {
        return BlocBuilder<ServiceUserBloc, ServiceUserState>(
            buildWhen: (previous, current) => previous.activatedDate != current.activatedDate,
            builder: (context, state) {
              return DateTimeField(
                controller: context.bloc<ServiceUserBloc>().activatedDateController,
                onChanged: (value) { context.bloc<ServiceUserBloc>().add(ActivatedDateChanged(activatedDate: value)); },
                format: DateFormat.yMMMMd(S.of(context).locale),
                keyboardType: TextInputType.datetime,
            decoration: InputDecoration(labelText:S.of(context).pageEntitiesServiceUserActivatedDateField,),
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
      Widget profilePhotoUrlField() {
        return BlocBuilder<ServiceUserBloc, ServiceUserState>(
            buildWhen: (previous, current) => previous.profilePhotoUrl != current.profilePhotoUrl,
            builder: (context, state) {
              return TextFormField(
                  controller: context.bloc<ServiceUserBloc>().profilePhotoUrlController,
                  onChanged: (value) { context.bloc<ServiceUserBloc>()
                    .add(ProfilePhotoUrlChanged(profilePhotoUrl:value)); },
                  keyboardType:TextInputType.text,                  decoration: InputDecoration(
                      labelText:S.of(context).pageEntitiesServiceUserProfilePhotoUrlField));
            }
        );
      }
      Widget lastRecordedHeightField() {
        return BlocBuilder<ServiceUserBloc, ServiceUserState>(
            buildWhen: (previous, current) => previous.lastRecordedHeight != current.lastRecordedHeight,
            builder: (context, state) {
              return TextFormField(
                  controller: context.bloc<ServiceUserBloc>().lastRecordedHeightController,
                  onChanged: (value) { context.bloc<ServiceUserBloc>()
                    .add(LastRecordedHeightChanged(lastRecordedHeight:value)); },
                  keyboardType:TextInputType.text,                  decoration: InputDecoration(
                      labelText:S.of(context).pageEntitiesServiceUserLastRecordedHeightField));
            }
        );
      }
      Widget lastRecordedWeightField() {
        return BlocBuilder<ServiceUserBloc, ServiceUserState>(
            buildWhen: (previous, current) => previous.lastRecordedWeight != current.lastRecordedWeight,
            builder: (context, state) {
              return TextFormField(
                  controller: context.bloc<ServiceUserBloc>().lastRecordedWeightController,
                  onChanged: (value) { context.bloc<ServiceUserBloc>()
                    .add(LastRecordedWeightChanged(lastRecordedWeight:value)); },
                  keyboardType:TextInputType.text,                  decoration: InputDecoration(
                      labelText:S.of(context).pageEntitiesServiceUserLastRecordedWeightField));
            }
        );
      }
        Widget hasMedicalConditionField() {
          return BlocBuilder<ServiceUserBloc,ServiceUserState>(
              buildWhen: (previous, current) => previous.hasMedicalCondition != current.hasMedicalCondition,
              builder: (context, state) {
                return Padding(
                  padding: const EdgeInsets.only(left: 3.0),
                  child: Row(
                    mainAxisAlignment: MainAxisAlignment.spaceBetween,
                    children: <Widget>[
                      Text(S.of(context).pageEntitiesServiceUserHasMedicalConditionField, style: Theme.of(context).textTheme.bodyText1,),
                      Switch(
                          value: state.hasMedicalCondition.value,
                          onChanged: (value) { context.bloc<ServiceUserBloc>().add(HasMedicalConditionChanged(hasMedicalCondition: value)); },
                          activeColor: Theme.of(context).primaryColor,),
                    ],
                  ),
                );
              });
        }
      Widget medicalConditionSummaryField() {
        return BlocBuilder<ServiceUserBloc, ServiceUserState>(
            buildWhen: (previous, current) => previous.medicalConditionSummary != current.medicalConditionSummary,
            builder: (context, state) {
              return TextFormField(
                  controller: context.bloc<ServiceUserBloc>().medicalConditionSummaryController,
                  onChanged: (value) { context.bloc<ServiceUserBloc>()
                    .add(MedicalConditionSummaryChanged(medicalConditionSummary:value)); },
                  keyboardType:TextInputType.text,                  decoration: InputDecoration(
                      labelText:S.of(context).pageEntitiesServiceUserMedicalConditionSummaryField));
            }
        );
      }
      Widget createdDateField() {
        return BlocBuilder<ServiceUserBloc, ServiceUserState>(
            buildWhen: (previous, current) => previous.createdDate != current.createdDate,
            builder: (context, state) {
              return DateTimeField(
                controller: context.bloc<ServiceUserBloc>().createdDateController,
                onChanged: (value) { context.bloc<ServiceUserBloc>().add(CreatedDateChanged(createdDate: value)); },
                format: DateFormat.yMMMMd(S.of(context).locale),
                keyboardType: TextInputType.datetime,
            decoration: InputDecoration(labelText:S.of(context).pageEntitiesServiceUserCreatedDateField,),
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
        return BlocBuilder<ServiceUserBloc, ServiceUserState>(
            buildWhen: (previous, current) => previous.lastUpdatedDate != current.lastUpdatedDate,
            builder: (context, state) {
              return DateTimeField(
                controller: context.bloc<ServiceUserBloc>().lastUpdatedDateController,
                onChanged: (value) { context.bloc<ServiceUserBloc>().add(LastUpdatedDateChanged(lastUpdatedDate: value)); },
                format: DateFormat.yMMMMd(S.of(context).locale),
                keyboardType: TextInputType.datetime,
            decoration: InputDecoration(labelText:S.of(context).pageEntitiesServiceUserLastUpdatedDateField,),
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
        return BlocBuilder<ServiceUserBloc, ServiceUserState>(
            buildWhen: (previous, current) => previous.clientId != current.clientId,
            builder: (context, state) {
              return TextFormField(
                  controller: context.bloc<ServiceUserBloc>().clientIdController,
                  onChanged: (value) { context.bloc<ServiceUserBloc>()
                    .add(ClientIdChanged(clientId:int.parse(value))); },
                  keyboardType:TextInputType.number,                  decoration: InputDecoration(
                      labelText:S.of(context).pageEntitiesServiceUserClientIdField));
            }
        );
      }
        Widget hasExtraDataField() {
          return BlocBuilder<ServiceUserBloc,ServiceUserState>(
              buildWhen: (previous, current) => previous.hasExtraData != current.hasExtraData,
              builder: (context, state) {
                return Padding(
                  padding: const EdgeInsets.only(left: 3.0),
                  child: Row(
                    mainAxisAlignment: MainAxisAlignment.spaceBetween,
                    children: <Widget>[
                      Text(S.of(context).pageEntitiesServiceUserHasExtraDataField, style: Theme.of(context).textTheme.bodyText1,),
                      Switch(
                          value: state.hasExtraData.value,
                          onChanged: (value) { context.bloc<ServiceUserBloc>().add(HasExtraDataChanged(hasExtraData: value)); },
                          activeColor: Theme.of(context).primaryColor,),
                    ],
                  ),
                );
              });
        }

      List<DropdownMenuItem<Titlle>> createDropdownTitlleItems(List<Titlle> titlles) {
        List<DropdownMenuItem<Titlle>> titlleDropDown = [];
    
        for (Titlle titlle in titlles) {
          DropdownMenuItem<Titlle> dropdown = DropdownMenuItem<Titlle>(
              value: titlle, child: Text(titlle.toString()));
              titlleDropDown.add(dropdown);
        }
    
        return titlleDropDown;
      }
      List<DropdownMenuItem<SupportType>> createDropdownSupportTypeItems(List<SupportType> supportTypes) {
        List<DropdownMenuItem<SupportType>> supportTypeDropDown = [];
    
        for (SupportType supportType in supportTypes) {
          DropdownMenuItem<SupportType> dropdown = DropdownMenuItem<SupportType>(
              value: supportType, child: Text(supportType.toString()));
              supportTypeDropDown.add(dropdown);
        }
    
        return supportTypeDropDown;
      }
      List<DropdownMenuItem<ServiceUserCategory>> createDropdownServiceUserCategoryItems(List<ServiceUserCategory> serviceUserCategorys) {
        List<DropdownMenuItem<ServiceUserCategory>> serviceUserCategoryDropDown = [];
    
        for (ServiceUserCategory serviceUserCategory in serviceUserCategorys) {
          DropdownMenuItem<ServiceUserCategory> dropdown = DropdownMenuItem<ServiceUserCategory>(
              value: serviceUserCategory, child: Text(serviceUserCategory.toString()));
              serviceUserCategoryDropDown.add(dropdown);
        }
    
        return serviceUserCategoryDropDown;
      }
      List<DropdownMenuItem<Vulnerability>> createDropdownVulnerabilityItems(List<Vulnerability> vulnerabilitys) {
        List<DropdownMenuItem<Vulnerability>> vulnerabilityDropDown = [];
    
        for (Vulnerability vulnerability in vulnerabilitys) {
          DropdownMenuItem<Vulnerability> dropdown = DropdownMenuItem<Vulnerability>(
              value: vulnerability, child: Text(vulnerability.toString()));
              vulnerabilityDropDown.add(dropdown);
        }
    
        return vulnerabilityDropDown;
      }
      List<DropdownMenuItem<ServicePriority>> createDropdownServicePriorityItems(List<ServicePriority> servicePrioritys) {
        List<DropdownMenuItem<ServicePriority>> servicePriorityDropDown = [];
    
        for (ServicePriority servicePriority in servicePrioritys) {
          DropdownMenuItem<ServicePriority> dropdown = DropdownMenuItem<ServicePriority>(
              value: servicePriority, child: Text(servicePriority.toString()));
              servicePriorityDropDown.add(dropdown);
        }
    
        return servicePriorityDropDown;
      }
      List<DropdownMenuItem<Source>> createDropdownSourceItems(List<Source> sources) {
        List<DropdownMenuItem<Source>> sourceDropDown = [];
    
        for (Source source in sources) {
          DropdownMenuItem<Source> dropdown = DropdownMenuItem<Source>(
              value: source, child: Text(source.toString()));
              sourceDropDown.add(dropdown);
        }
    
        return sourceDropDown;
      }
      List<DropdownMenuItem<ServiceUserStatus>> createDropdownServiceUserStatusItems(List<ServiceUserStatus> statuss) {
        List<DropdownMenuItem<ServiceUserStatus>> statusDropDown = [];
    
        for (ServiceUserStatus status in statuss) {
          DropdownMenuItem<ServiceUserStatus> dropdown = DropdownMenuItem<ServiceUserStatus>(
              value: status, child: Text(status.toString()));
              statusDropDown.add(dropdown);
        }
    
        return statusDropDown;
      }

  Widget validationZone() {
    return BlocBuilder<ServiceUserBloc, ServiceUserState>(
        buildWhen:(previous, current) => previous.formStatus != current.formStatus,
        builder: (context, state) {
          return Visibility(
              visible: state.formStatus.isSubmissionFailure ||  state.formStatus.isSubmissionSuccess,
              child: Center(
                child: generateNotificationText(state, context),
              ));
        });
  }

  Widget generateNotificationText(ServiceUserState state, BuildContext context) {
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
    return BlocBuilder<ServiceUserBloc, ServiceUserState>(
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
            onPressed: state.formStatus.isValidated ? () => context.bloc<ServiceUserBloc>().add(ServiceUserFormSubmitted()) : null,
          );
        }
    );
  }
}
