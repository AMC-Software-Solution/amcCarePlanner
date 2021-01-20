import 'package:formz/formz.dart';
import 'package:amcCarePlanner/entities/communication/communication_model.dart';

enum CommunicationTypeValidationError { invalid }
class CommunicationTypeInput extends FormzInput<CommunicationType, CommunicationTypeValidationError> {
  const CommunicationTypeInput.pure() : super.pure(CommunicationType.EMAIL);
  const CommunicationTypeInput.dirty([CommunicationType value]) : super.dirty(value);

  @override
  CommunicationTypeValidationError validator(CommunicationType value) {
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

enum CommunicationDateValidationError { invalid }
class CommunicationDateInput extends FormzInput<DateTime, CommunicationDateValidationError> {
  CommunicationDateInput.pure() : super.pure(DateTime.now());
  CommunicationDateInput.dirty([DateTime value]) : super.dirty(value);

  @override
  CommunicationDateValidationError validator(DateTime value) {
    return null;
  }
}

enum AttachmentUrlValidationError { invalid }
class AttachmentUrlInput extends FormzInput<String, AttachmentUrlValidationError> {
  const AttachmentUrlInput.pure() : super.pure('');
  const AttachmentUrlInput.dirty([String value = '']) : super.dirty(value);

  @override
  AttachmentUrlValidationError validator(String value) {
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

