import 'package:formz/formz.dart';
import 'package:amcCarePlanner/entities/relationship_type/relationship_type_model.dart';

enum RelationTypeValidationError { invalid }
class RelationTypeInput extends FormzInput<String, RelationTypeValidationError> {
  const RelationTypeInput.pure() : super.pure('');
  const RelationTypeInput.dirty([String value = '']) : super.dirty(value);

  @override
  RelationTypeValidationError validator(String value) {
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

