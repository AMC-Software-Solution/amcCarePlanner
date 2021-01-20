import 'package:formz/formz.dart';
import 'package:amcCarePlanner/entities/document_type/document_type_model.dart';

enum DocumentTypeTitleValidationError { invalid }
class DocumentTypeTitleInput extends FormzInput<String, DocumentTypeTitleValidationError> {
  const DocumentTypeTitleInput.pure() : super.pure('');
  const DocumentTypeTitleInput.dirty([String value = '']) : super.dirty(value);

  @override
  DocumentTypeTitleValidationError validator(String value) {
    return null;
  }
}

enum DocumentTypeDescriptionValidationError { invalid }
class DocumentTypeDescriptionInput extends FormzInput<String, DocumentTypeDescriptionValidationError> {
  const DocumentTypeDescriptionInput.pure() : super.pure('');
  const DocumentTypeDescriptionInput.dirty([String value = '']) : super.dirty(value);

  @override
  DocumentTypeDescriptionValidationError validator(String value) {
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

enum HasExtraDataValidationError { invalid }
class HasExtraDataInput extends FormzInput<bool, HasExtraDataValidationError> {
  const HasExtraDataInput.pure() : super.pure(false);
  const HasExtraDataInput.dirty([bool value = false]) : super.dirty(value);

  @override
  HasExtraDataValidationError validator(bool value) {
    return null;
  }
}

