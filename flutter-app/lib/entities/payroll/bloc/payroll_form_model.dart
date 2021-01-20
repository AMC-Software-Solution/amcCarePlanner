import 'package:formz/formz.dart';
import 'package:amcCarePlanner/entities/payroll/payroll_model.dart';

enum PaymentDateValidationError { invalid }
class PaymentDateInput extends FormzInput<DateTime, PaymentDateValidationError> {
  PaymentDateInput.pure() : super.pure(DateTime.now());
  PaymentDateInput.dirty([DateTime value]) : super.dirty(value);

  @override
  PaymentDateValidationError validator(DateTime value) {
    return null;
  }
}

enum PayPeriodValidationError { invalid }
class PayPeriodInput extends FormzInput<String, PayPeriodValidationError> {
  const PayPeriodInput.pure() : super.pure('');
  const PayPeriodInput.dirty([String value = '']) : super.dirty(value);

  @override
  PayPeriodValidationError validator(String value) {
    return null;
  }
}

enum TotalHoursWorkedValidationError { invalid }
class TotalHoursWorkedInput extends FormzInput<int, TotalHoursWorkedValidationError> {
  const TotalHoursWorkedInput.pure() : super.pure(0);
  const TotalHoursWorkedInput.dirty([int value = 0]) : super.dirty(value);

  @override
  TotalHoursWorkedValidationError validator(int value) {
    return null;
  }
}

enum GrossPayValidationError { invalid }
class GrossPayInput extends FormzInput<String, GrossPayValidationError> {
  const GrossPayInput.pure() : super.pure('');
  const GrossPayInput.dirty([String value = '']) : super.dirty(value);

  @override
  GrossPayValidationError validator(String value) {
    return null;
  }
}

enum NetPayValidationError { invalid }
class NetPayInput extends FormzInput<String, NetPayValidationError> {
  const NetPayInput.pure() : super.pure('');
  const NetPayInput.dirty([String value = '']) : super.dirty(value);

  @override
  NetPayValidationError validator(String value) {
    return null;
  }
}

enum TotalTaxValidationError { invalid }
class TotalTaxInput extends FormzInput<String, TotalTaxValidationError> {
  const TotalTaxInput.pure() : super.pure('');
  const TotalTaxInput.dirty([String value = '']) : super.dirty(value);

  @override
  TotalTaxValidationError validator(String value) {
    return null;
  }
}

enum PayrollStatusValidationError { invalid }
class PayrollStatusInput extends FormzInput<PayrollStatus, PayrollStatusValidationError> {
  const PayrollStatusInput.pure() : super.pure(PayrollStatus.CREATED);
  const PayrollStatusInput.dirty([PayrollStatus value]) : super.dirty(value);

  @override
  PayrollStatusValidationError validator(PayrollStatus value) {
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

