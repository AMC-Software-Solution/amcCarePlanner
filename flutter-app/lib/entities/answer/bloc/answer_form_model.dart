import 'package:formz/formz.dart';
import 'package:amcCarePlanner/entities/answer/answer_model.dart';

enum AnswerValidationError { invalid }
class AnswerInput extends FormzInput<String, AnswerValidationError> {
  const AnswerInput.pure() : super.pure('');
  const AnswerInput.dirty([String value = '']) : super.dirty(value);

  @override
  AnswerValidationError validator(String value) {
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

