import 'dart:async';
import 'package:bloc/bloc.dart';
import 'package:flutter/foundation.dart';
import 'package:equatable/equatable.dart';
import 'package:flutter/widgets.dart';
import 'package:formz/formz.dart';

import 'package:amcCarePlanner/entities/employee/employee_model.dart';
import 'package:amcCarePlanner/entities/employee/employee_repository.dart';
import 'package:amcCarePlanner/entities/employee/bloc/employee_form_model.dart';
import 'package:amcCarePlanner/generated/l10n.dart';
import 'package:amcCarePlanner/shared/repository/http_utils.dart';
import 'package:intl/intl.dart';

part 'employee_events.dart';
part 'employee_state.dart';

class EmployeeBloc extends Bloc<EmployeeEvent, EmployeeState> {
  final EmployeeRepository _employeeRepository;

  final firstNameController = TextEditingController();
  final middleInitialController = TextEditingController();
  final lastNameController = TextEditingController();
  final preferredNameController = TextEditingController();
  final employeeCodeController = TextEditingController();
  final phoneController = TextEditingController();
  final emailController = TextEditingController();
  final nationalInsuranceNumberController = TextEditingController();
  final pinCodeController = TextEditingController();
  final addressController = TextEditingController();
  final countyController = TextEditingController();
  final postCodeController = TextEditingController();
  final dateOfBirthController = TextEditingController();
  final photoUrlController = TextEditingController();
  final employeeBioController = TextEditingController();
  final acruedHolidayHoursController = TextEditingController();
  final createdDateController = TextEditingController();
  final lastUpdatedDateController = TextEditingController();
  final clientIdController = TextEditingController();

  EmployeeBloc({@required EmployeeRepository employeeRepository}) : assert(employeeRepository != null),
        _employeeRepository = employeeRepository, 
  super(EmployeeState(null,null,null,));

  @override
  void onTransition(Transition<EmployeeEvent, EmployeeState> transition) {
    super.onTransition(transition);
  }

  @override
  Stream<EmployeeState> mapEventToState(EmployeeEvent event) async* {
    if (event is InitEmployeeList) {
      yield* onInitList(event);
    } else if (event is EmployeeFormSubmitted) {
      yield* onSubmit();
    } else if (event is LoadEmployeeByIdForEdit) {
      yield* onLoadEmployeeIdForEdit(event);
    } else if (event is DeleteEmployeeById) {
      yield* onDeleteEmployeeId(event);
    } else if (event is LoadEmployeeByIdForView) {
      yield* onLoadEmployeeIdForView(event);
    }else if (event is TittleChanged){
      yield* onTittleChange(event);
    }else if (event is FirstNameChanged){
      yield* onFirstNameChange(event);
    }else if (event is MiddleInitialChanged){
      yield* onMiddleInitialChange(event);
    }else if (event is LastNameChanged){
      yield* onLastNameChange(event);
    }else if (event is PreferredNameChanged){
      yield* onPreferredNameChange(event);
    }else if (event is GendderChanged){
      yield* onGendderChange(event);
    }else if (event is EmployeeCodeChanged){
      yield* onEmployeeCodeChange(event);
    }else if (event is PhoneChanged){
      yield* onPhoneChange(event);
    }else if (event is EmailChanged){
      yield* onEmailChange(event);
    }else if (event is NationalInsuranceNumberChanged){
      yield* onNationalInsuranceNumberChange(event);
    }else if (event is EmployeeContractTypeChanged){
      yield* onEmployeeContractTypeChange(event);
    }else if (event is PinCodeChanged){
      yield* onPinCodeChange(event);
    }else if (event is EmployeeTransportModeChanged){
      yield* onEmployeeTransportModeChange(event);
    }else if (event is AddressChanged){
      yield* onAddressChange(event);
    }else if (event is CountyChanged){
      yield* onCountyChange(event);
    }else if (event is PostCodeChanged){
      yield* onPostCodeChange(event);
    }else if (event is DateOfBirthChanged){
      yield* onDateOfBirthChange(event);
    }else if (event is PhotoUrlChanged){
      yield* onPhotoUrlChange(event);
    }else if (event is StatusChanged){
      yield* onStatusChange(event);
    }else if (event is EmployeeBioChanged){
      yield* onEmployeeBioChange(event);
    }else if (event is AcruedHolidayHoursChanged){
      yield* onAcruedHolidayHoursChange(event);
    }else if (event is CreatedDateChanged){
      yield* onCreatedDateChange(event);
    }else if (event is LastUpdatedDateChanged){
      yield* onLastUpdatedDateChange(event);
    }else if (event is ClientIdChanged){
      yield* onClientIdChange(event);
    }else if (event is HasExtraDataChanged){
      yield* onHasExtraDataChange(event);
    }  }

  Stream<EmployeeState> onInitList(InitEmployeeList event) async* {
    yield this.state.copyWith(employeeStatusUI: EmployeeStatusUI.loading);
    List<Employee> employees = await _employeeRepository.getAllEmployees();
    yield this.state.copyWith(employees: employees, employeeStatusUI: EmployeeStatusUI.done);
  }

  Stream<EmployeeState> onSubmit() async* {
    if (this.state.formStatus.isValidated) {
      yield this.state.copyWith(formStatus: FormzStatus.submissionInProgress);
      try {
        Employee result;
        if(this.state.editMode) {
          Employee newEmployee = Employee(state.loadedEmployee.id,
            this.state.tittle.value,  
            this.state.firstName.value,  
            this.state.middleInitial.value,  
            this.state.lastName.value,  
            this.state.preferredName.value,  
            this.state.gendder.value,  
            this.state.employeeCode.value,  
            this.state.phone.value,  
            this.state.email.value,  
            this.state.nationalInsuranceNumber.value,  
            this.state.employeeContractType.value,  
            this.state.pinCode.value,  
            this.state.employeeTransportMode.value,  
            this.state.address.value,  
            this.state.county.value,  
            this.state.postCode.value,  
            this.state.dateOfBirth.value,  
            this.state.photoUrl.value,  
            this.state.status.value,  
            this.state.employeeBio.value,  
            this.state.acruedHolidayHours.value,  
            this.state.createdDate.value,  
            this.state.lastUpdatedDate.value,  
            this.state.clientId.value,  
            this.state.hasExtraData.value,  
            null, 
            null, 
            null, 
            null, 
          );

          result = await _employeeRepository.update(newEmployee);
        } else {
          Employee newEmployee = Employee(null,
            this.state.tittle.value,  
            this.state.firstName.value,  
            this.state.middleInitial.value,  
            this.state.lastName.value,  
            this.state.preferredName.value,  
            this.state.gendder.value,  
            this.state.employeeCode.value,  
            this.state.phone.value,  
            this.state.email.value,  
            this.state.nationalInsuranceNumber.value,  
            this.state.employeeContractType.value,  
            this.state.pinCode.value,  
            this.state.employeeTransportMode.value,  
            this.state.address.value,  
            this.state.county.value,  
            this.state.postCode.value,  
            this.state.dateOfBirth.value,  
            this.state.photoUrl.value,  
            this.state.status.value,  
            this.state.employeeBio.value,  
            this.state.acruedHolidayHours.value,  
            this.state.createdDate.value,  
            this.state.lastUpdatedDate.value,  
            this.state.clientId.value,  
            this.state.hasExtraData.value,  
            null, 
            null, 
            null, 
            null, 
          );

          result = await _employeeRepository.create(newEmployee);
        }

        if (result == null) {
          yield this.state.copyWith(formStatus: FormzStatus.submissionFailure,
              generalNotificationKey: HttpUtils.badRequestServerKey);
        } else {
          yield this.state.copyWith(formStatus: FormzStatus.submissionSuccess,
              generalNotificationKey: HttpUtils.successResult);
        }
      } catch (e) {
        yield this.state.copyWith(formStatus: FormzStatus.submissionFailure,
            generalNotificationKey: HttpUtils.errorServerKey);
      }
    }
  }

  Stream<EmployeeState> onLoadEmployeeIdForEdit(LoadEmployeeByIdForEdit event) async* {
    yield this.state.copyWith(employeeStatusUI: EmployeeStatusUI.loading);
    Employee loadedEmployee = await _employeeRepository.getEmployee(event.id);

    final tittle = TittleInput.dirty(loadedEmployee?.tittle != null ? loadedEmployee.tittle: null);
    final firstName = FirstNameInput.dirty(loadedEmployee?.firstName != null ? loadedEmployee.firstName: '');
    final middleInitial = MiddleInitialInput.dirty(loadedEmployee?.middleInitial != null ? loadedEmployee.middleInitial: '');
    final lastName = LastNameInput.dirty(loadedEmployee?.lastName != null ? loadedEmployee.lastName: '');
    final preferredName = PreferredNameInput.dirty(loadedEmployee?.preferredName != null ? loadedEmployee.preferredName: '');
    final gendder = GendderInput.dirty(loadedEmployee?.gendder != null ? loadedEmployee.gendder: null);
    final employeeCode = EmployeeCodeInput.dirty(loadedEmployee?.employeeCode != null ? loadedEmployee.employeeCode: '');
    final phone = PhoneInput.dirty(loadedEmployee?.phone != null ? loadedEmployee.phone: '');
    final email = EmailInput.dirty(loadedEmployee?.email != null ? loadedEmployee.email: '');
    final nationalInsuranceNumber = NationalInsuranceNumberInput.dirty(loadedEmployee?.nationalInsuranceNumber != null ? loadedEmployee.nationalInsuranceNumber: '');
    final employeeContractType = EmployeeContractTypeInput.dirty(loadedEmployee?.employeeContractType != null ? loadedEmployee.employeeContractType: null);
    final pinCode = PinCodeInput.dirty(loadedEmployee?.pinCode != null ? loadedEmployee.pinCode: 0);
    final employeeTransportMode = EmployeeTransportModeInput.dirty(loadedEmployee?.employeeTransportMode != null ? loadedEmployee.employeeTransportMode: null);
    final address = AddressInput.dirty(loadedEmployee?.address != null ? loadedEmployee.address: '');
    final county = CountyInput.dirty(loadedEmployee?.county != null ? loadedEmployee.county: '');
    final postCode = PostCodeInput.dirty(loadedEmployee?.postCode != null ? loadedEmployee.postCode: '');
    final dateOfBirth = DateOfBirthInput.dirty(loadedEmployee?.dateOfBirth != null ? loadedEmployee.dateOfBirth: null);
    final photoUrl = PhotoUrlInput.dirty(loadedEmployee?.photoUrl != null ? loadedEmployee.photoUrl: '');
    final status = StatusInput.dirty(loadedEmployee?.status != null ? loadedEmployee.status: null);
    final employeeBio = EmployeeBioInput.dirty(loadedEmployee?.employeeBio != null ? loadedEmployee.employeeBio: '');
    final acruedHolidayHours = AcruedHolidayHoursInput.dirty(loadedEmployee?.acruedHolidayHours != null ? loadedEmployee.acruedHolidayHours: 0);
    final createdDate = CreatedDateInput.dirty(loadedEmployee?.createdDate != null ? loadedEmployee.createdDate: null);
    final lastUpdatedDate = LastUpdatedDateInput.dirty(loadedEmployee?.lastUpdatedDate != null ? loadedEmployee.lastUpdatedDate: null);
    final clientId = ClientIdInput.dirty(loadedEmployee?.clientId != null ? loadedEmployee.clientId: 0);
    final hasExtraData = HasExtraDataInput.dirty(loadedEmployee?.hasExtraData != null ? loadedEmployee.hasExtraData: false);

    yield this.state.copyWith(loadedEmployee: loadedEmployee, editMode: true,
      tittle: tittle,
      firstName: firstName,
      middleInitial: middleInitial,
      lastName: lastName,
      preferredName: preferredName,
      gendder: gendder,
      employeeCode: employeeCode,
      phone: phone,
      email: email,
      nationalInsuranceNumber: nationalInsuranceNumber,
      employeeContractType: employeeContractType,
      pinCode: pinCode,
      employeeTransportMode: employeeTransportMode,
      address: address,
      county: county,
      postCode: postCode,
      dateOfBirth: dateOfBirth,
      photoUrl: photoUrl,
      status: status,
      employeeBio: employeeBio,
      acruedHolidayHours: acruedHolidayHours,
      createdDate: createdDate,
      lastUpdatedDate: lastUpdatedDate,
      clientId: clientId,
      hasExtraData: hasExtraData,
    employeeStatusUI: EmployeeStatusUI.done);

    firstNameController.text = loadedEmployee.firstName;
    middleInitialController.text = loadedEmployee.middleInitial;
    lastNameController.text = loadedEmployee.lastName;
    preferredNameController.text = loadedEmployee.preferredName;
    employeeCodeController.text = loadedEmployee.employeeCode;
    phoneController.text = loadedEmployee.phone;
    emailController.text = loadedEmployee.email;
    nationalInsuranceNumberController.text = loadedEmployee.nationalInsuranceNumber;
    pinCodeController.text = loadedEmployee.pinCode.toString();
    addressController.text = loadedEmployee.address;
    countyController.text = loadedEmployee.county;
    postCodeController.text = loadedEmployee.postCode;
    dateOfBirthController.text = DateFormat.yMMMMd(S.current.locale).format(loadedEmployee?.dateOfBirth);
    photoUrlController.text = loadedEmployee.photoUrl;
    employeeBioController.text = loadedEmployee.employeeBio;
    acruedHolidayHoursController.text = loadedEmployee.acruedHolidayHours.toString();
    createdDateController.text = DateFormat.yMMMMd(S.current.locale).format(loadedEmployee?.createdDate);
    lastUpdatedDateController.text = DateFormat.yMMMMd(S.current.locale).format(loadedEmployee?.lastUpdatedDate);
    clientIdController.text = loadedEmployee.clientId.toString();
  }

  Stream<EmployeeState> onDeleteEmployeeId(DeleteEmployeeById event) async* {
    try {
      await _employeeRepository.delete(event.id);
      this.add(InitEmployeeList());
      yield this.state.copyWith(deleteStatus: EmployeeDeleteStatus.ok);
    } catch (e) {
      yield this.state.copyWith(deleteStatus: EmployeeDeleteStatus.ko,
          generalNotificationKey: HttpUtils.errorServerKey);
    }
    yield this.state.copyWith(deleteStatus: EmployeeDeleteStatus.none);
  }

  Stream<EmployeeState> onLoadEmployeeIdForView(LoadEmployeeByIdForView event) async* {
    yield this.state.copyWith(employeeStatusUI: EmployeeStatusUI.loading);
    try {
      Employee loadedEmployee = await _employeeRepository.getEmployee(event.id);
      yield this.state.copyWith(loadedEmployee: loadedEmployee, employeeStatusUI: EmployeeStatusUI.done);
    } catch(e) {
      yield this.state.copyWith(loadedEmployee: null, employeeStatusUI: EmployeeStatusUI.error);
    }
  }


  Stream<EmployeeState> onTittleChange(TittleChanged event) async* {
    final tittle = TittleInput.dirty(event.tittle);
    yield this.state.copyWith(
      tittle: tittle,
      formStatus: Formz.validate([
        tittle,
      this.state.firstName,
      this.state.middleInitial,
      this.state.lastName,
      this.state.preferredName,
      this.state.gendder,
      this.state.employeeCode,
      this.state.phone,
      this.state.email,
      this.state.nationalInsuranceNumber,
      this.state.employeeContractType,
      this.state.pinCode,
      this.state.employeeTransportMode,
      this.state.address,
      this.state.county,
      this.state.postCode,
      this.state.dateOfBirth,
      this.state.photoUrl,
      this.state.status,
      this.state.employeeBio,
      this.state.acruedHolidayHours,
      this.state.createdDate,
      this.state.lastUpdatedDate,
      this.state.clientId,
      this.state.hasExtraData,
      ]),
    );
  }
  Stream<EmployeeState> onFirstNameChange(FirstNameChanged event) async* {
    final firstName = FirstNameInput.dirty(event.firstName);
    yield this.state.copyWith(
      firstName: firstName,
      formStatus: Formz.validate([
      this.state.tittle,
        firstName,
      this.state.middleInitial,
      this.state.lastName,
      this.state.preferredName,
      this.state.gendder,
      this.state.employeeCode,
      this.state.phone,
      this.state.email,
      this.state.nationalInsuranceNumber,
      this.state.employeeContractType,
      this.state.pinCode,
      this.state.employeeTransportMode,
      this.state.address,
      this.state.county,
      this.state.postCode,
      this.state.dateOfBirth,
      this.state.photoUrl,
      this.state.status,
      this.state.employeeBio,
      this.state.acruedHolidayHours,
      this.state.createdDate,
      this.state.lastUpdatedDate,
      this.state.clientId,
      this.state.hasExtraData,
      ]),
    );
  }
  Stream<EmployeeState> onMiddleInitialChange(MiddleInitialChanged event) async* {
    final middleInitial = MiddleInitialInput.dirty(event.middleInitial);
    yield this.state.copyWith(
      middleInitial: middleInitial,
      formStatus: Formz.validate([
      this.state.tittle,
      this.state.firstName,
        middleInitial,
      this.state.lastName,
      this.state.preferredName,
      this.state.gendder,
      this.state.employeeCode,
      this.state.phone,
      this.state.email,
      this.state.nationalInsuranceNumber,
      this.state.employeeContractType,
      this.state.pinCode,
      this.state.employeeTransportMode,
      this.state.address,
      this.state.county,
      this.state.postCode,
      this.state.dateOfBirth,
      this.state.photoUrl,
      this.state.status,
      this.state.employeeBio,
      this.state.acruedHolidayHours,
      this.state.createdDate,
      this.state.lastUpdatedDate,
      this.state.clientId,
      this.state.hasExtraData,
      ]),
    );
  }
  Stream<EmployeeState> onLastNameChange(LastNameChanged event) async* {
    final lastName = LastNameInput.dirty(event.lastName);
    yield this.state.copyWith(
      lastName: lastName,
      formStatus: Formz.validate([
      this.state.tittle,
      this.state.firstName,
      this.state.middleInitial,
        lastName,
      this.state.preferredName,
      this.state.gendder,
      this.state.employeeCode,
      this.state.phone,
      this.state.email,
      this.state.nationalInsuranceNumber,
      this.state.employeeContractType,
      this.state.pinCode,
      this.state.employeeTransportMode,
      this.state.address,
      this.state.county,
      this.state.postCode,
      this.state.dateOfBirth,
      this.state.photoUrl,
      this.state.status,
      this.state.employeeBio,
      this.state.acruedHolidayHours,
      this.state.createdDate,
      this.state.lastUpdatedDate,
      this.state.clientId,
      this.state.hasExtraData,
      ]),
    );
  }
  Stream<EmployeeState> onPreferredNameChange(PreferredNameChanged event) async* {
    final preferredName = PreferredNameInput.dirty(event.preferredName);
    yield this.state.copyWith(
      preferredName: preferredName,
      formStatus: Formz.validate([
      this.state.tittle,
      this.state.firstName,
      this.state.middleInitial,
      this.state.lastName,
        preferredName,
      this.state.gendder,
      this.state.employeeCode,
      this.state.phone,
      this.state.email,
      this.state.nationalInsuranceNumber,
      this.state.employeeContractType,
      this.state.pinCode,
      this.state.employeeTransportMode,
      this.state.address,
      this.state.county,
      this.state.postCode,
      this.state.dateOfBirth,
      this.state.photoUrl,
      this.state.status,
      this.state.employeeBio,
      this.state.acruedHolidayHours,
      this.state.createdDate,
      this.state.lastUpdatedDate,
      this.state.clientId,
      this.state.hasExtraData,
      ]),
    );
  }
  Stream<EmployeeState> onGendderChange(GendderChanged event) async* {
    final gendder = GendderInput.dirty(event.gendder);
    yield this.state.copyWith(
      gendder: gendder,
      formStatus: Formz.validate([
      this.state.tittle,
      this.state.firstName,
      this.state.middleInitial,
      this.state.lastName,
      this.state.preferredName,
        gendder,
      this.state.employeeCode,
      this.state.phone,
      this.state.email,
      this.state.nationalInsuranceNumber,
      this.state.employeeContractType,
      this.state.pinCode,
      this.state.employeeTransportMode,
      this.state.address,
      this.state.county,
      this.state.postCode,
      this.state.dateOfBirth,
      this.state.photoUrl,
      this.state.status,
      this.state.employeeBio,
      this.state.acruedHolidayHours,
      this.state.createdDate,
      this.state.lastUpdatedDate,
      this.state.clientId,
      this.state.hasExtraData,
      ]),
    );
  }
  Stream<EmployeeState> onEmployeeCodeChange(EmployeeCodeChanged event) async* {
    final employeeCode = EmployeeCodeInput.dirty(event.employeeCode);
    yield this.state.copyWith(
      employeeCode: employeeCode,
      formStatus: Formz.validate([
      this.state.tittle,
      this.state.firstName,
      this.state.middleInitial,
      this.state.lastName,
      this.state.preferredName,
      this.state.gendder,
        employeeCode,
      this.state.phone,
      this.state.email,
      this.state.nationalInsuranceNumber,
      this.state.employeeContractType,
      this.state.pinCode,
      this.state.employeeTransportMode,
      this.state.address,
      this.state.county,
      this.state.postCode,
      this.state.dateOfBirth,
      this.state.photoUrl,
      this.state.status,
      this.state.employeeBio,
      this.state.acruedHolidayHours,
      this.state.createdDate,
      this.state.lastUpdatedDate,
      this.state.clientId,
      this.state.hasExtraData,
      ]),
    );
  }
  Stream<EmployeeState> onPhoneChange(PhoneChanged event) async* {
    final phone = PhoneInput.dirty(event.phone);
    yield this.state.copyWith(
      phone: phone,
      formStatus: Formz.validate([
      this.state.tittle,
      this.state.firstName,
      this.state.middleInitial,
      this.state.lastName,
      this.state.preferredName,
      this.state.gendder,
      this.state.employeeCode,
        phone,
      this.state.email,
      this.state.nationalInsuranceNumber,
      this.state.employeeContractType,
      this.state.pinCode,
      this.state.employeeTransportMode,
      this.state.address,
      this.state.county,
      this.state.postCode,
      this.state.dateOfBirth,
      this.state.photoUrl,
      this.state.status,
      this.state.employeeBio,
      this.state.acruedHolidayHours,
      this.state.createdDate,
      this.state.lastUpdatedDate,
      this.state.clientId,
      this.state.hasExtraData,
      ]),
    );
  }
  Stream<EmployeeState> onEmailChange(EmailChanged event) async* {
    final email = EmailInput.dirty(event.email);
    yield this.state.copyWith(
      email: email,
      formStatus: Formz.validate([
      this.state.tittle,
      this.state.firstName,
      this.state.middleInitial,
      this.state.lastName,
      this.state.preferredName,
      this.state.gendder,
      this.state.employeeCode,
      this.state.phone,
        email,
      this.state.nationalInsuranceNumber,
      this.state.employeeContractType,
      this.state.pinCode,
      this.state.employeeTransportMode,
      this.state.address,
      this.state.county,
      this.state.postCode,
      this.state.dateOfBirth,
      this.state.photoUrl,
      this.state.status,
      this.state.employeeBio,
      this.state.acruedHolidayHours,
      this.state.createdDate,
      this.state.lastUpdatedDate,
      this.state.clientId,
      this.state.hasExtraData,
      ]),
    );
  }
  Stream<EmployeeState> onNationalInsuranceNumberChange(NationalInsuranceNumberChanged event) async* {
    final nationalInsuranceNumber = NationalInsuranceNumberInput.dirty(event.nationalInsuranceNumber);
    yield this.state.copyWith(
      nationalInsuranceNumber: nationalInsuranceNumber,
      formStatus: Formz.validate([
      this.state.tittle,
      this.state.firstName,
      this.state.middleInitial,
      this.state.lastName,
      this.state.preferredName,
      this.state.gendder,
      this.state.employeeCode,
      this.state.phone,
      this.state.email,
        nationalInsuranceNumber,
      this.state.employeeContractType,
      this.state.pinCode,
      this.state.employeeTransportMode,
      this.state.address,
      this.state.county,
      this.state.postCode,
      this.state.dateOfBirth,
      this.state.photoUrl,
      this.state.status,
      this.state.employeeBio,
      this.state.acruedHolidayHours,
      this.state.createdDate,
      this.state.lastUpdatedDate,
      this.state.clientId,
      this.state.hasExtraData,
      ]),
    );
  }
  Stream<EmployeeState> onEmployeeContractTypeChange(EmployeeContractTypeChanged event) async* {
    final employeeContractType = EmployeeContractTypeInput.dirty(event.employeeContractType);
    yield this.state.copyWith(
      employeeContractType: employeeContractType,
      formStatus: Formz.validate([
      this.state.tittle,
      this.state.firstName,
      this.state.middleInitial,
      this.state.lastName,
      this.state.preferredName,
      this.state.gendder,
      this.state.employeeCode,
      this.state.phone,
      this.state.email,
      this.state.nationalInsuranceNumber,
        employeeContractType,
      this.state.pinCode,
      this.state.employeeTransportMode,
      this.state.address,
      this.state.county,
      this.state.postCode,
      this.state.dateOfBirth,
      this.state.photoUrl,
      this.state.status,
      this.state.employeeBio,
      this.state.acruedHolidayHours,
      this.state.createdDate,
      this.state.lastUpdatedDate,
      this.state.clientId,
      this.state.hasExtraData,
      ]),
    );
  }
  Stream<EmployeeState> onPinCodeChange(PinCodeChanged event) async* {
    final pinCode = PinCodeInput.dirty(event.pinCode);
    yield this.state.copyWith(
      pinCode: pinCode,
      formStatus: Formz.validate([
      this.state.tittle,
      this.state.firstName,
      this.state.middleInitial,
      this.state.lastName,
      this.state.preferredName,
      this.state.gendder,
      this.state.employeeCode,
      this.state.phone,
      this.state.email,
      this.state.nationalInsuranceNumber,
      this.state.employeeContractType,
        pinCode,
      this.state.employeeTransportMode,
      this.state.address,
      this.state.county,
      this.state.postCode,
      this.state.dateOfBirth,
      this.state.photoUrl,
      this.state.status,
      this.state.employeeBio,
      this.state.acruedHolidayHours,
      this.state.createdDate,
      this.state.lastUpdatedDate,
      this.state.clientId,
      this.state.hasExtraData,
      ]),
    );
  }
  Stream<EmployeeState> onEmployeeTransportModeChange(EmployeeTransportModeChanged event) async* {
    final employeeTransportMode = EmployeeTransportModeInput.dirty(event.employeeTransportMode);
    yield this.state.copyWith(
      employeeTransportMode: employeeTransportMode,
      formStatus: Formz.validate([
      this.state.tittle,
      this.state.firstName,
      this.state.middleInitial,
      this.state.lastName,
      this.state.preferredName,
      this.state.gendder,
      this.state.employeeCode,
      this.state.phone,
      this.state.email,
      this.state.nationalInsuranceNumber,
      this.state.employeeContractType,
      this.state.pinCode,
        employeeTransportMode,
      this.state.address,
      this.state.county,
      this.state.postCode,
      this.state.dateOfBirth,
      this.state.photoUrl,
      this.state.status,
      this.state.employeeBio,
      this.state.acruedHolidayHours,
      this.state.createdDate,
      this.state.lastUpdatedDate,
      this.state.clientId,
      this.state.hasExtraData,
      ]),
    );
  }
  Stream<EmployeeState> onAddressChange(AddressChanged event) async* {
    final address = AddressInput.dirty(event.address);
    yield this.state.copyWith(
      address: address,
      formStatus: Formz.validate([
      this.state.tittle,
      this.state.firstName,
      this.state.middleInitial,
      this.state.lastName,
      this.state.preferredName,
      this.state.gendder,
      this.state.employeeCode,
      this.state.phone,
      this.state.email,
      this.state.nationalInsuranceNumber,
      this.state.employeeContractType,
      this.state.pinCode,
      this.state.employeeTransportMode,
        address,
      this.state.county,
      this.state.postCode,
      this.state.dateOfBirth,
      this.state.photoUrl,
      this.state.status,
      this.state.employeeBio,
      this.state.acruedHolidayHours,
      this.state.createdDate,
      this.state.lastUpdatedDate,
      this.state.clientId,
      this.state.hasExtraData,
      ]),
    );
  }
  Stream<EmployeeState> onCountyChange(CountyChanged event) async* {
    final county = CountyInput.dirty(event.county);
    yield this.state.copyWith(
      county: county,
      formStatus: Formz.validate([
      this.state.tittle,
      this.state.firstName,
      this.state.middleInitial,
      this.state.lastName,
      this.state.preferredName,
      this.state.gendder,
      this.state.employeeCode,
      this.state.phone,
      this.state.email,
      this.state.nationalInsuranceNumber,
      this.state.employeeContractType,
      this.state.pinCode,
      this.state.employeeTransportMode,
      this.state.address,
        county,
      this.state.postCode,
      this.state.dateOfBirth,
      this.state.photoUrl,
      this.state.status,
      this.state.employeeBio,
      this.state.acruedHolidayHours,
      this.state.createdDate,
      this.state.lastUpdatedDate,
      this.state.clientId,
      this.state.hasExtraData,
      ]),
    );
  }
  Stream<EmployeeState> onPostCodeChange(PostCodeChanged event) async* {
    final postCode = PostCodeInput.dirty(event.postCode);
    yield this.state.copyWith(
      postCode: postCode,
      formStatus: Formz.validate([
      this.state.tittle,
      this.state.firstName,
      this.state.middleInitial,
      this.state.lastName,
      this.state.preferredName,
      this.state.gendder,
      this.state.employeeCode,
      this.state.phone,
      this.state.email,
      this.state.nationalInsuranceNumber,
      this.state.employeeContractType,
      this.state.pinCode,
      this.state.employeeTransportMode,
      this.state.address,
      this.state.county,
        postCode,
      this.state.dateOfBirth,
      this.state.photoUrl,
      this.state.status,
      this.state.employeeBio,
      this.state.acruedHolidayHours,
      this.state.createdDate,
      this.state.lastUpdatedDate,
      this.state.clientId,
      this.state.hasExtraData,
      ]),
    );
  }
  Stream<EmployeeState> onDateOfBirthChange(DateOfBirthChanged event) async* {
    final dateOfBirth = DateOfBirthInput.dirty(event.dateOfBirth);
    yield this.state.copyWith(
      dateOfBirth: dateOfBirth,
      formStatus: Formz.validate([
      this.state.tittle,
      this.state.firstName,
      this.state.middleInitial,
      this.state.lastName,
      this.state.preferredName,
      this.state.gendder,
      this.state.employeeCode,
      this.state.phone,
      this.state.email,
      this.state.nationalInsuranceNumber,
      this.state.employeeContractType,
      this.state.pinCode,
      this.state.employeeTransportMode,
      this.state.address,
      this.state.county,
      this.state.postCode,
        dateOfBirth,
      this.state.photoUrl,
      this.state.status,
      this.state.employeeBio,
      this.state.acruedHolidayHours,
      this.state.createdDate,
      this.state.lastUpdatedDate,
      this.state.clientId,
      this.state.hasExtraData,
      ]),
    );
  }
  Stream<EmployeeState> onPhotoUrlChange(PhotoUrlChanged event) async* {
    final photoUrl = PhotoUrlInput.dirty(event.photoUrl);
    yield this.state.copyWith(
      photoUrl: photoUrl,
      formStatus: Formz.validate([
      this.state.tittle,
      this.state.firstName,
      this.state.middleInitial,
      this.state.lastName,
      this.state.preferredName,
      this.state.gendder,
      this.state.employeeCode,
      this.state.phone,
      this.state.email,
      this.state.nationalInsuranceNumber,
      this.state.employeeContractType,
      this.state.pinCode,
      this.state.employeeTransportMode,
      this.state.address,
      this.state.county,
      this.state.postCode,
      this.state.dateOfBirth,
        photoUrl,
      this.state.status,
      this.state.employeeBio,
      this.state.acruedHolidayHours,
      this.state.createdDate,
      this.state.lastUpdatedDate,
      this.state.clientId,
      this.state.hasExtraData,
      ]),
    );
  }
  Stream<EmployeeState> onStatusChange(StatusChanged event) async* {
    final status = StatusInput.dirty(event.status);
    yield this.state.copyWith(
      status: status,
      formStatus: Formz.validate([
      this.state.tittle,
      this.state.firstName,
      this.state.middleInitial,
      this.state.lastName,
      this.state.preferredName,
      this.state.gendder,
      this.state.employeeCode,
      this.state.phone,
      this.state.email,
      this.state.nationalInsuranceNumber,
      this.state.employeeContractType,
      this.state.pinCode,
      this.state.employeeTransportMode,
      this.state.address,
      this.state.county,
      this.state.postCode,
      this.state.dateOfBirth,
      this.state.photoUrl,
        status,
      this.state.employeeBio,
      this.state.acruedHolidayHours,
      this.state.createdDate,
      this.state.lastUpdatedDate,
      this.state.clientId,
      this.state.hasExtraData,
      ]),
    );
  }
  Stream<EmployeeState> onEmployeeBioChange(EmployeeBioChanged event) async* {
    final employeeBio = EmployeeBioInput.dirty(event.employeeBio);
    yield this.state.copyWith(
      employeeBio: employeeBio,
      formStatus: Formz.validate([
      this.state.tittle,
      this.state.firstName,
      this.state.middleInitial,
      this.state.lastName,
      this.state.preferredName,
      this.state.gendder,
      this.state.employeeCode,
      this.state.phone,
      this.state.email,
      this.state.nationalInsuranceNumber,
      this.state.employeeContractType,
      this.state.pinCode,
      this.state.employeeTransportMode,
      this.state.address,
      this.state.county,
      this.state.postCode,
      this.state.dateOfBirth,
      this.state.photoUrl,
      this.state.status,
        employeeBio,
      this.state.acruedHolidayHours,
      this.state.createdDate,
      this.state.lastUpdatedDate,
      this.state.clientId,
      this.state.hasExtraData,
      ]),
    );
  }
  Stream<EmployeeState> onAcruedHolidayHoursChange(AcruedHolidayHoursChanged event) async* {
    final acruedHolidayHours = AcruedHolidayHoursInput.dirty(event.acruedHolidayHours);
    yield this.state.copyWith(
      acruedHolidayHours: acruedHolidayHours,
      formStatus: Formz.validate([
      this.state.tittle,
      this.state.firstName,
      this.state.middleInitial,
      this.state.lastName,
      this.state.preferredName,
      this.state.gendder,
      this.state.employeeCode,
      this.state.phone,
      this.state.email,
      this.state.nationalInsuranceNumber,
      this.state.employeeContractType,
      this.state.pinCode,
      this.state.employeeTransportMode,
      this.state.address,
      this.state.county,
      this.state.postCode,
      this.state.dateOfBirth,
      this.state.photoUrl,
      this.state.status,
      this.state.employeeBio,
        acruedHolidayHours,
      this.state.createdDate,
      this.state.lastUpdatedDate,
      this.state.clientId,
      this.state.hasExtraData,
      ]),
    );
  }
  Stream<EmployeeState> onCreatedDateChange(CreatedDateChanged event) async* {
    final createdDate = CreatedDateInput.dirty(event.createdDate);
    yield this.state.copyWith(
      createdDate: createdDate,
      formStatus: Formz.validate([
      this.state.tittle,
      this.state.firstName,
      this.state.middleInitial,
      this.state.lastName,
      this.state.preferredName,
      this.state.gendder,
      this.state.employeeCode,
      this.state.phone,
      this.state.email,
      this.state.nationalInsuranceNumber,
      this.state.employeeContractType,
      this.state.pinCode,
      this.state.employeeTransportMode,
      this.state.address,
      this.state.county,
      this.state.postCode,
      this.state.dateOfBirth,
      this.state.photoUrl,
      this.state.status,
      this.state.employeeBio,
      this.state.acruedHolidayHours,
        createdDate,
      this.state.lastUpdatedDate,
      this.state.clientId,
      this.state.hasExtraData,
      ]),
    );
  }
  Stream<EmployeeState> onLastUpdatedDateChange(LastUpdatedDateChanged event) async* {
    final lastUpdatedDate = LastUpdatedDateInput.dirty(event.lastUpdatedDate);
    yield this.state.copyWith(
      lastUpdatedDate: lastUpdatedDate,
      formStatus: Formz.validate([
      this.state.tittle,
      this.state.firstName,
      this.state.middleInitial,
      this.state.lastName,
      this.state.preferredName,
      this.state.gendder,
      this.state.employeeCode,
      this.state.phone,
      this.state.email,
      this.state.nationalInsuranceNumber,
      this.state.employeeContractType,
      this.state.pinCode,
      this.state.employeeTransportMode,
      this.state.address,
      this.state.county,
      this.state.postCode,
      this.state.dateOfBirth,
      this.state.photoUrl,
      this.state.status,
      this.state.employeeBio,
      this.state.acruedHolidayHours,
      this.state.createdDate,
        lastUpdatedDate,
      this.state.clientId,
      this.state.hasExtraData,
      ]),
    );
  }
  Stream<EmployeeState> onClientIdChange(ClientIdChanged event) async* {
    final clientId = ClientIdInput.dirty(event.clientId);
    yield this.state.copyWith(
      clientId: clientId,
      formStatus: Formz.validate([
      this.state.tittle,
      this.state.firstName,
      this.state.middleInitial,
      this.state.lastName,
      this.state.preferredName,
      this.state.gendder,
      this.state.employeeCode,
      this.state.phone,
      this.state.email,
      this.state.nationalInsuranceNumber,
      this.state.employeeContractType,
      this.state.pinCode,
      this.state.employeeTransportMode,
      this.state.address,
      this.state.county,
      this.state.postCode,
      this.state.dateOfBirth,
      this.state.photoUrl,
      this.state.status,
      this.state.employeeBio,
      this.state.acruedHolidayHours,
      this.state.createdDate,
      this.state.lastUpdatedDate,
        clientId,
      this.state.hasExtraData,
      ]),
    );
  }
  Stream<EmployeeState> onHasExtraDataChange(HasExtraDataChanged event) async* {
    final hasExtraData = HasExtraDataInput.dirty(event.hasExtraData);
    yield this.state.copyWith(
      hasExtraData: hasExtraData,
      formStatus: Formz.validate([
      this.state.tittle,
      this.state.firstName,
      this.state.middleInitial,
      this.state.lastName,
      this.state.preferredName,
      this.state.gendder,
      this.state.employeeCode,
      this.state.phone,
      this.state.email,
      this.state.nationalInsuranceNumber,
      this.state.employeeContractType,
      this.state.pinCode,
      this.state.employeeTransportMode,
      this.state.address,
      this.state.county,
      this.state.postCode,
      this.state.dateOfBirth,
      this.state.photoUrl,
      this.state.status,
      this.state.employeeBio,
      this.state.acruedHolidayHours,
      this.state.createdDate,
      this.state.lastUpdatedDate,
      this.state.clientId,
        hasExtraData,
      ]),
    );
  }

  @override
  Future<void> close() {
    firstNameController.dispose();
    middleInitialController.dispose();
    lastNameController.dispose();
    preferredNameController.dispose();
    employeeCodeController.dispose();
    phoneController.dispose();
    emailController.dispose();
    nationalInsuranceNumberController.dispose();
    pinCodeController.dispose();
    addressController.dispose();
    countyController.dispose();
    postCodeController.dispose();
    dateOfBirthController.dispose();
    photoUrlController.dispose();
    employeeBioController.dispose();
    acruedHolidayHoursController.dispose();
    createdDateController.dispose();
    lastUpdatedDateController.dispose();
    clientIdController.dispose();
    return super.close();
  }

}