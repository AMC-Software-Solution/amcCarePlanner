import 'package:formz/formz.dart';
import 'package:amcCarePlanner/entities/servce_user_document/servce_user_document_model.dart';

enum DocumentNameValidationError { invalid }
class DocumentNameInput extends FormzInput<String, DocumentNameValidationError> {
  const DocumentNameInput.pure() : super.pure('');
  const DocumentNameInput.dirty([String value = '']) : super.dirty(value);

  @override
  DocumentNameValidationError validator(String value) {
    return null;
  }
}

enum DocumentNumberValidationError { invalid }
class DocumentNumberInput extends FormzInput<String, DocumentNumberValidationError> {
  const DocumentNumberInput.pure() : super.pure('');
  const DocumentNumberInput.dirty([String value = '']) : super.dirty(value);

  @override
  DocumentNumberValidationError validator(String value) {
    return null;
  }
}

enum DocumentStatusValidationError { invalid }
class DocumentStatusInput extends FormzInput<DocumentStatus, DocumentStatusValidationError> {
  const DocumentStatusInput.pure() : super.pure(DocumentStatus.EXPIRED);
  const DocumentStatusInput.dirty([DocumentStatus value]) : super.dirty(value);

  @override
  DocumentStatusValidationError validator(DocumentStatus value) {
    return null;
  }
}

enum NoteValidationError { invalid }
class NoteInput extends FormzInput<String, NoteValidationError> {
  const NoteInput.pure() : super.pure('');
  const NoteInput.dirty([String value = '']) : super.dirty(value);

  @override
  NoteValidationError validator(String value) {
    return null;
  }
}

enum IssuedDateValidationError { invalid }
class IssuedDateInput extends FormzInput<DateTime, IssuedDateValidationError> {
  IssuedDateInput.pure() : super.pure(DateTime.now());
  IssuedDateInput.dirty([DateTime value]) : super.dirty(value);

  @override
  IssuedDateValidationError validator(DateTime value) {
    return null;
  }
}

enum ExpiryDateValidationError { invalid }
class ExpiryDateInput extends FormzInput<DateTime, ExpiryDateValidationError> {
  ExpiryDateInput.pure() : super.pure(DateTime.now());
  ExpiryDateInput.dirty([DateTime value]) : super.dirty(value);

  @override
  ExpiryDateValidationError validator(DateTime value) {
    return null;
  }
}

enum UploadedDateValidationError { invalid }
class UploadedDateInput extends FormzInput<DateTime, UploadedDateValidationError> {
  UploadedDateInput.pure() : super.pure(DateTime.now());
  UploadedDateInput.dirty([DateTime value]) : super.dirty(value);

  @override
  UploadedDateValidationError validator(DateTime value) {
    return null;
  }
}

enum DocumentFileUrlValidationError { invalid }
class DocumentFileUrlInput extends FormzInput<String, DocumentFileUrlValidationError> {
  const DocumentFileUrlInput.pure() : super.pure('');
  const DocumentFileUrlInput.dirty([String value = '']) : super.dirty(value);

  @override
  DocumentFileUrlValidationError validator(String value) {
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

