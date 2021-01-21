part of 'employee_bloc.dart';

enum EmployeeStatusUI {init, loading, error, done}
enum EmployeeDeleteStatus {ok, ko, none}

class EmployeeState extends Equatable {
  final List<Employee> employees;
  final Employee loadedEmployee;
  final bool editMode;
  final EmployeeDeleteStatus deleteStatus;
  final EmployeeStatusUI employeeStatusUI;

  final FormzStatus formStatus;
  final String generalNotificationKey;

  final TitleInput title;
  final FirstNameInput firstName;
  final MiddleInitialInput middleInitial;
  final LastNameInput lastName;
  final PreferredNameInput preferredName;
  final GenderInput gender;
  final EmployeeCodeInput employeeCode;
  final PhoneInput phone;
  final EmailInput email;
  final NationalInsuranceNumberInput nationalInsuranceNumber;
  final EmployeeContractTypeInput employeeContractType;
  final PinCodeInput pinCode;
  final TransportModeInput transportMode;
  final AddressInput address;
  final CountyInput county;
  final PostCodeInput postCode;
  final DateOfBirthInput dateOfBirth;
  final PhotoUrlInput photoUrl;
  final StatusInput status;
  final EmployeeBioInput employeeBio;
  final AcruedHolidayHoursInput acruedHolidayHours;
  final CreatedDateInput createdDate;
  final LastUpdatedDateInput lastUpdatedDate;
  final ClientIdInput clientId;
  final HasExtraDataInput hasExtraData;

  
  EmployeeState(
DateOfBirthInput dateOfBirth,CreatedDateInput createdDate,LastUpdatedDateInput lastUpdatedDate,{
    this.employees = const [],
    this.employeeStatusUI = EmployeeStatusUI.init,
    this.loadedEmployee = const Employee(0,null,'','','','',null,'','','','',null,0,null,'','','',null,'',null,'',0,null,null,0,false,null,null,null,null,),
    this.editMode = false,
    this.formStatus = FormzStatus.pure,
    this.generalNotificationKey = '',
    this.deleteStatus = EmployeeDeleteStatus.none,
    this.title = const TitleInput.pure(),
    this.firstName = const FirstNameInput.pure(),
    this.middleInitial = const MiddleInitialInput.pure(),
    this.lastName = const LastNameInput.pure(),
    this.preferredName = const PreferredNameInput.pure(),
    this.gender = const GenderInput.pure(),
    this.employeeCode = const EmployeeCodeInput.pure(),
    this.phone = const PhoneInput.pure(),
    this.email = const EmailInput.pure(),
    this.nationalInsuranceNumber = const NationalInsuranceNumberInput.pure(),
    this.employeeContractType = const EmployeeContractTypeInput.pure(),
    this.pinCode = const PinCodeInput.pure(),
    this.transportMode = const TransportModeInput.pure(),
    this.address = const AddressInput.pure(),
    this.county = const CountyInput.pure(),
    this.postCode = const PostCodeInput.pure(),
    this.photoUrl = const PhotoUrlInput.pure(),
    this.status = const StatusInput.pure(),
    this.employeeBio = const EmployeeBioInput.pure(),
    this.acruedHolidayHours = const AcruedHolidayHoursInput.pure(),
    this.clientId = const ClientIdInput.pure(),
    this.hasExtraData = const HasExtraDataInput.pure(),
  }):this.dateOfBirth = dateOfBirth ?? DateOfBirthInput.pure(),
this.createdDate = createdDate ?? CreatedDateInput.pure(),
this.lastUpdatedDate = lastUpdatedDate ?? LastUpdatedDateInput.pure()
;

  EmployeeState copyWith({
    List<Employee> employees,
    EmployeeStatusUI employeeStatusUI,
    bool editMode,
    EmployeeDeleteStatus deleteStatus,
    Employee loadedEmployee,
    FormzStatus formStatus,
    String generalNotificationKey,
    TitleInput title,
    FirstNameInput firstName,
    MiddleInitialInput middleInitial,
    LastNameInput lastName,
    PreferredNameInput preferredName,
    GenderInput gender,
    EmployeeCodeInput employeeCode,
    PhoneInput phone,
    EmailInput email,
    NationalInsuranceNumberInput nationalInsuranceNumber,
    EmployeeContractTypeInput employeeContractType,
    PinCodeInput pinCode,
    TransportModeInput transportMode,
    AddressInput address,
    CountyInput county,
    PostCodeInput postCode,
    DateOfBirthInput dateOfBirth,
    PhotoUrlInput photoUrl,
    StatusInput status,
    EmployeeBioInput employeeBio,
    AcruedHolidayHoursInput acruedHolidayHours,
    CreatedDateInput createdDate,
    LastUpdatedDateInput lastUpdatedDate,
    ClientIdInput clientId,
    HasExtraDataInput hasExtraData,
  }) {
    return EmployeeState(
        dateOfBirth,
        createdDate,
        lastUpdatedDate,
      employees: employees ?? this.employees,
      employeeStatusUI: employeeStatusUI ?? this.employeeStatusUI,
      loadedEmployee: loadedEmployee ?? this.loadedEmployee,
      editMode: editMode ?? this.editMode,
      formStatus: formStatus ?? this.formStatus,
      generalNotificationKey: generalNotificationKey ?? this.generalNotificationKey,
      deleteStatus: deleteStatus ?? this.deleteStatus,
      title: title ?? this.title,
      firstName: firstName ?? this.firstName,
      middleInitial: middleInitial ?? this.middleInitial,
      lastName: lastName ?? this.lastName,
      preferredName: preferredName ?? this.preferredName,
      gender: gender ?? this.gender,
      employeeCode: employeeCode ?? this.employeeCode,
      phone: phone ?? this.phone,
      email: email ?? this.email,
      nationalInsuranceNumber: nationalInsuranceNumber ?? this.nationalInsuranceNumber,
      employeeContractType: employeeContractType ?? this.employeeContractType,
      pinCode: pinCode ?? this.pinCode,
      transportMode: transportMode ?? this.transportMode,
      address: address ?? this.address,
      county: county ?? this.county,
      postCode: postCode ?? this.postCode,
      photoUrl: photoUrl ?? this.photoUrl,
      status: status ?? this.status,
      employeeBio: employeeBio ?? this.employeeBio,
      acruedHolidayHours: acruedHolidayHours ?? this.acruedHolidayHours,
      clientId: clientId ?? this.clientId,
      hasExtraData: hasExtraData ?? this.hasExtraData,
    );
  }

  @override
  List<Object> get props => [employees, employeeStatusUI,
     loadedEmployee, editMode, deleteStatus, formStatus, generalNotificationKey, 
title,firstName,middleInitial,lastName,preferredName,gender,employeeCode,phone,email,nationalInsuranceNumber,employeeContractType,pinCode,transportMode,address,county,postCode,dateOfBirth,photoUrl,status,employeeBio,acruedHolidayHours,createdDate,lastUpdatedDate,clientId,hasExtraData,];

  @override
  bool get stringify => true;
}
