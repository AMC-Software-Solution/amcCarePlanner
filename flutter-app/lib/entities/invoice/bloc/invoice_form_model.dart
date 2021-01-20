import 'package:formz/formz.dart';
import 'package:amcCarePlanner/entities/invoice/invoice_model.dart';

enum TotalAmountValidationError { invalid }
class TotalAmountInput extends FormzInput<String, TotalAmountValidationError> {
  const TotalAmountInput.pure() : super.pure('');
  const TotalAmountInput.dirty([String value = '']) : super.dirty(value);

  @override
  TotalAmountValidationError validator(String value) {
    return null;
  }
}

enum DescriptionValidationError { invalid }
class DescriptionInput extends FormzInput<String, DescriptionValidationError> {
  const DescriptionInput.pure() : super.pure('');
  const DescriptionInput.dirty([String value = '']) : super.dirty(value);

  @override
  DescriptionValidationError validator(String value) {
    return null;
  }
}

enum InvoiceNumberValidationError { invalid }
class InvoiceNumberInput extends FormzInput<String, InvoiceNumberValidationError> {
  const InvoiceNumberInput.pure() : super.pure('');
  const InvoiceNumberInput.dirty([String value = '']) : super.dirty(value);

  @override
  InvoiceNumberValidationError validator(String value) {
    return null;
  }
}

enum GeneratedDateValidationError { invalid }
class GeneratedDateInput extends FormzInput<DateTime, GeneratedDateValidationError> {
  GeneratedDateInput.pure() : super.pure(DateTime.now());
  GeneratedDateInput.dirty([DateTime value]) : super.dirty(value);

  @override
  GeneratedDateValidationError validator(DateTime value) {
    return null;
  }
}

enum DueDateValidationError { invalid }
class DueDateInput extends FormzInput<DateTime, DueDateValidationError> {
  DueDateInput.pure() : super.pure(DateTime.now());
  DueDateInput.dirty([DateTime value]) : super.dirty(value);

  @override
  DueDateValidationError validator(DateTime value) {
    return null;
  }
}

enum PaymentDateValidationError { invalid }
class PaymentDateInput extends FormzInput<DateTime, PaymentDateValidationError> {
  PaymentDateInput.pure() : super.pure(DateTime.now());
  PaymentDateInput.dirty([DateTime value]) : super.dirty(value);

  @override
  PaymentDateValidationError validator(DateTime value) {
    return null;
  }
}

enum InvoiceStatusValidationError { invalid }
class InvoiceStatusInput extends FormzInput<InvoiceStatus, InvoiceStatusValidationError> {
  const InvoiceStatusInput.pure() : super.pure(InvoiceStatus.CREATED);
  const InvoiceStatusInput.dirty([InvoiceStatus value]) : super.dirty(value);

  @override
  InvoiceStatusValidationError validator(InvoiceStatus value) {
    return null;
  }
}

enum TaxValidationError { invalid }
class TaxInput extends FormzInput<String, TaxValidationError> {
  const TaxInput.pure() : super.pure('');
  const TaxInput.dirty([String value = '']) : super.dirty(value);

  @override
  TaxValidationError validator(String value) {
    return null;
  }
}

enum Attribute1ValidationError { invalid }
class Attribute1Input extends FormzInput<String, Attribute1ValidationError> {
  const Attribute1Input.pure() : super.pure('');
  const Attribute1Input.dirty([String value = '']) : super.dirty(value);

  @override
  Attribute1ValidationError validator(String value) {
    return null;
  }
}

enum Attribute2ValidationError { invalid }
class Attribute2Input extends FormzInput<String, Attribute2ValidationError> {
  const Attribute2Input.pure() : super.pure('');
  const Attribute2Input.dirty([String value = '']) : super.dirty(value);

  @override
  Attribute2ValidationError validator(String value) {
    return null;
  }
}

enum Attribute3ValidationError { invalid }
class Attribute3Input extends FormzInput<String, Attribute3ValidationError> {
  const Attribute3Input.pure() : super.pure('');
  const Attribute3Input.dirty([String value = '']) : super.dirty(value);

  @override
  Attribute3ValidationError validator(String value) {
    return null;
  }
}

enum Attribute4ValidationError { invalid }
class Attribute4Input extends FormzInput<String, Attribute4ValidationError> {
  const Attribute4Input.pure() : super.pure('');
  const Attribute4Input.dirty([String value = '']) : super.dirty(value);

  @override
  Attribute4ValidationError validator(String value) {
    return null;
  }
}

enum Attribute5ValidationError { invalid }
class Attribute5Input extends FormzInput<String, Attribute5ValidationError> {
  const Attribute5Input.pure() : super.pure('');
  const Attribute5Input.dirty([String value = '']) : super.dirty(value);

  @override
  Attribute5ValidationError validator(String value) {
    return null;
  }
}

enum Attribute6ValidationError { invalid }
class Attribute6Input extends FormzInput<String, Attribute6ValidationError> {
  const Attribute6Input.pure() : super.pure('');
  const Attribute6Input.dirty([String value = '']) : super.dirty(value);

  @override
  Attribute6ValidationError validator(String value) {
    return null;
  }
}

enum Attribute7ValidationError { invalid }
class Attribute7Input extends FormzInput<String, Attribute7ValidationError> {
  const Attribute7Input.pure() : super.pure('');
  const Attribute7Input.dirty([String value = '']) : super.dirty(value);

  @override
  Attribute7ValidationError validator(String value) {
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

