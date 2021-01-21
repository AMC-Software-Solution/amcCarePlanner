import 'package:formz/formz.dart';
import 'package:amcCarePlanner/entities/employee/employee_model.dart';

enum TitleValidationError { invalid }
class TitleInput extends FormzInput<EmployeeTitle, TitleValidationError> {
  const TitleInput.pure() : super.pure(EmployeeTitle.MR);
  const TitleInput.dirty([EmployeeTitle value]) : super.dirty(value);

  @override
  TitleValidationError validator(EmployeeTitle value) {
    return null;
  }
}

enum FirstNameValidationError { invalid }
class FirstNameInput extends FormzInput<String, FirstNameValidationError> {
  const FirstNameInput.pure() : super.pure('');
  const FirstNameInput.dirty([String value = '']) : super.dirty(value);

  @override
  FirstNameValidationError validator(String value) {
    return null;
  }
}

enum MiddleInitialValidationError { invalid }
class MiddleInitialInput extends FormzInput<String, MiddleInitialValidationError> {
  const MiddleInitialInput.pure() : super.pure('');
  const MiddleInitialInput.dirty([String value = '']) : super.dirty(value);

  @override
  MiddleInitialValidationError validator(String value) {
    return null;
  }
}

enum LastNameValidationError { invalid }
class LastNameInput extends FormzInput<String, LastNameValidationError> {
  const LastNameInput.pure() : super.pure('');
  const LastNameInput.dirty([String value = '']) : super.dirty(value);

  @override
  LastNameValidationError validator(String value) {
    return null;
  }
}

enum PreferredNameValidationError { invalid }
class PreferredNameInput extends FormzInput<String, PreferredNameValidationError> {
  const PreferredNameInput.pure() : super.pure('');
  const PreferredNameInput.dirty([String value = '']) : super.dirty(value);

  @override
  PreferredNameValidationError validator(String value) {
    return null;
  }
}

enum GenderValidationError { invalid }
class GenderInput extends FormzInput<EmployeeGender, GenderValidationError> {
  const GenderInput.pure() : super.pure(EmployeeGender.MALE);
  const GenderInput.dirty([EmployeeGender value]) : super.dirty(value);

  @override
  GenderValidationError validator(EmployeeGender value) {
    return null;
  }
}

enum EmployeeCodeValidationError { invalid }
class EmployeeCodeInput extends FormzInput<String, EmployeeCodeValidationError> {
  const EmployeeCodeInput.pure() : super.pure('');
  const EmployeeCodeInput.dirty([String value = '']) : super.dirty(value);

  @override
  EmployeeCodeValidationError validator(String value) {
    return null;
  }
}

enum PhoneValidationError { invalid }
class PhoneInput extends FormzInput<String, PhoneValidationError> {
  const PhoneInput.pure() : super.pure('');
  const PhoneInput.dirty([String value = '']) : super.dirty(value);

  @override
  PhoneValidationError validator(String value) {
    return null;
  }
}

enum EmailValidationError { invalid }
class EmailInput extends FormzInput<String, EmailValidationError> {
  const EmailInput.pure() : super.pure('');
  const EmailInput.dirty([String value = '']) : super.dirty(value);

  @override
  EmailValidationError validator(String value) {
    return null;
  }
}

enum NationalInsuranceNumberValidationError { invalid }
class NationalInsuranceNumberInput extends FormzInput<String, NationalInsuranceNumberValidationError> {
  const NationalInsuranceNumberInput.pure() : super.pure('');
  const NationalInsuranceNumberInput.dirty([String value = '']) : super.dirty(value);

  @override
  NationalInsuranceNumberValidationError validator(String value) {
    return null;
  }
}

enum EmployeeContractTypeValidationError { invalid }
class EmployeeContractTypeInput extends FormzInput<EmployeeContractType, EmployeeContractTypeValidationError> {
  const EmployeeContractTypeInput.pure() : super.pure(EmployeeContractType.ZERO_HOURS_CONTRACT);
  const EmployeeContractTypeInput.dirty([EmployeeContractType value]) : super.dirty(value);

  @override
  EmployeeContractTypeValidationError validator(EmployeeContractType value) {
    return null;
  }
}

enum PinCodeValidationError { invalid }
class PinCodeInput extends FormzInput<int, PinCodeValidationError> {
  const PinCodeInput.pure() : super.pure(0);
  const PinCodeInput.dirty([int value = 0]) : super.dirty(value);

  @override
  PinCodeValidationError validator(int value) {
    return null;
  }
}

enum TransportModeValidationError { invalid }
class TransportModeInput extends FormzInput<EmployeeTravelMode, TransportModeValidationError> {
  const TransportModeInput.pure() : super.pure(EmployeeTravelMode.CAR);
  const TransportModeInput.dirty([EmployeeTravelMode value]) : super.dirty(value);

  @override
  TransportModeValidationError validator(EmployeeTravelMode value) {
    return null;
  }
}

enum AddressValidationError { invalid }
class AddressInput extends FormzInput<String, AddressValidationError> {
  const AddressInput.pure() : super.pure('');
  const AddressInput.dirty([String value = '']) : super.dirty(value);

  @override
  AddressValidationError validator(String value) {
    return null;
  }
}

enum CountyValidationError { invalid }
class CountyInput extends FormzInput<String, CountyValidationError> {
  const CountyInput.pure() : super.pure('');
  const CountyInput.dirty([String value = '']) : super.dirty(value);

  @override
  CountyValidationError validator(String value) {
    return null;
  }
}

enum PostCodeValidationError { invalid }
class PostCodeInput extends FormzInput<String, PostCodeValidationError> {
  const PostCodeInput.pure() : super.pure('');
  const PostCodeInput.dirty([String value = '']) : super.dirty(value);

  @override
  PostCodeValidationError validator(String value) {
    return null;
  }
}

enum DateOfBirthValidationError { invalid }
class DateOfBirthInput extends FormzInput<DateTime, DateOfBirthValidationError> {
  DateOfBirthInput.pure() : super.pure(DateTime.now());
  DateOfBirthInput.dirty([DateTime value]) : super.dirty(value);

  @override
  DateOfBirthValidationError validator(DateTime value) {
    return null;
  }
}

enum PhotoUrlValidationError { invalid }
class PhotoUrlInput extends FormzInput<String, PhotoUrlValidationError> {
  const PhotoUrlInput.pure() : super.pure('');
  const PhotoUrlInput.dirty([String value = '']) : super.dirty(value);

  @override
  PhotoUrlValidationError validator(String value) {
    return null;
  }
}

enum StatusValidationError { invalid }
class StatusInput extends FormzInput<EmployeeStatus, StatusValidationError> {
  const StatusInput.pure() : super.pure(EmployeeStatus.ACTIVE);
  const StatusInput.dirty([EmployeeStatus value]) : super.dirty(value);

  @override
  StatusValidationError validator(EmployeeStatus value) {
    return null;
  }
}

enum EmployeeBioValidationError { invalid }
class EmployeeBioInput extends FormzInput<String, EmployeeBioValidationError> {
  const EmployeeBioInput.pure() : super.pure('');
  const EmployeeBioInput.dirty([String value = '']) : super.dirty(value);

  @override
  EmployeeBioValidationError validator(String value) {
    return null;
  }
}

enum AcruedHolidayHoursValidationError { invalid }
class AcruedHolidayHoursInput extends FormzInput<int, AcruedHolidayHoursValidationError> {
  const AcruedHolidayHoursInput.pure() : super.pure(0);
  const AcruedHolidayHoursInput.dirty([int value = 0]) : super.dirty(value);

  @override
  AcruedHolidayHoursValidationError validator(int value) {
    return null;
  }
}

enum CreatedDateValidationError { invalid }
class CreatedDateInput extends FormzInput<DateTime, CreatedDateValidationError> {
  CreatedDateInput.pure() : super.pure(DateTime.now());
  CreatedDateInput.dirty([DateTime value]) : super.dirty(value);

  @override
  CreatedDateValidationError validator(DateTime value) {
    return null;
  }
}

enum LastUpdatedDateValidationError { invalid }
class LastUpdatedDateInput extends FormzInput<DateTime, LastUpdatedDateValidationError> {
  LastUpdatedDateInput.pure() : super.pure(DateTime.now());
  LastUpdatedDateInput.dirty([DateTime value]) : super.dirty(value);

  @override
  LastUpdatedDateValidationError validator(DateTime value) {
    return null;
  }
}

enum ClientIdValidationError { invalid }
class ClientIdInput extends FormzInput<int, ClientIdValidationError> {
  const ClientIdInput.pure() : super.pure(0);
  const ClientIdInput.dirty([int value = 0]) : super.dirty(value);

  @override
  ClientIdValidationError validator(int value) {
    return null;
  }
}

enum HasExtraDataValidationError { invalid }
class HasExtraDataInput extends FormzInput<bool, HasExtraDataValidationError> {
  const HasExtraDataInput.pure() : super.pure(false);
  const HasExtraDataInput.dirty([bool value = false]) : super.dirty(value);

  @override
  HasExtraDataValidationError validator(bool value) {
    return null;
  }
}

