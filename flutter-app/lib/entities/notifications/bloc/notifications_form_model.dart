import 'package:formz/formz.dart';
import 'package:amcCarePlanner/entities/notifications/notifications_model.dart';

enum TitleValidationError { invalid }
class TitleInput extends FormzInput<String, TitleValidationError> {
  const TitleInput.pure() : super.pure('');
  const TitleInput.dirty([String value = '']) : super.dirty(value);

  @override
  TitleValidationError validator(String value) {
    return null;
  }
}

enum BodyValidationError { invalid }
class BodyInput extends FormzInput<String, BodyValidationError> {
  const BodyInput.pure() : super.pure('');
  const BodyInput.dirty([String value = '']) : super.dirty(value);

  @override
  BodyValidationError validator(String value) {
    return null;
  }
}

enum NotificationDateValidationError { invalid }
class NotificationDateInput extends FormzInput<DateTime, NotificationDateValidationError> {
  NotificationDateInput.pure() : super.pure(DateTime.now());
  NotificationDateInput.dirty([DateTime value]) : super.dirty(value);

  @override
  NotificationDateValidationError validator(DateTime value) {
    return null;
  }
}

enum ImageUrlValidationError { invalid }
class ImageUrlInput extends FormzInput<String, ImageUrlValidationError> {
  const ImageUrlInput.pure() : super.pure('');
  const ImageUrlInput.dirty([String value = '']) : super.dirty(value);

  @override
  ImageUrlValidationError validator(String value) {
    return null;
  }
}

enum SenderIdValidationError { invalid }
class SenderIdInput extends FormzInput<int, SenderIdValidationError> {
  const SenderIdInput.pure() : super.pure(0);
  const SenderIdInput.dirty([int value = 0]) : super.dirty(value);

  @override
  SenderIdValidationError validator(int value) {
    return null;
  }
}

enum ReceiverIdValidationError { invalid }
class ReceiverIdInput extends FormzInput<int, ReceiverIdValidationError> {
  const ReceiverIdInput.pure() : super.pure(0);
  const ReceiverIdInput.dirty([int value = 0]) : super.dirty(value);

  @override
  ReceiverIdValidationError validator(int value) {
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

