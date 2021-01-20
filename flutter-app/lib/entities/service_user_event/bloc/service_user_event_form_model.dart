import 'package:formz/formz.dart';
import 'package:amcCarePlanner/entities/service_user_event/service_user_event_model.dart';

enum EventTitleValidationError { invalid }
class EventTitleInput extends FormzInput<String, EventTitleValidationError> {
  const EventTitleInput.pure() : super.pure('');
  const EventTitleInput.dirty([String value = '']) : super.dirty(value);

  @override
  EventTitleValidationError validator(String value) {
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

enum ServiceUserEventStatusValidationError { invalid }
class ServiceUserEventStatusInput extends FormzInput<ServiceUserEventStatus, ServiceUserEventStatusValidationError> {
  const ServiceUserEventStatusInput.pure() : super.pure(ServiceUserEventStatus.REPORTED);
  const ServiceUserEventStatusInput.dirty([ServiceUserEventStatus value]) : super.dirty(value);

  @override
  ServiceUserEventStatusValidationError validator(ServiceUserEventStatus value) {
    return null;
  }
}

enum ServiceUserEventTypeValidationError { invalid }
class ServiceUserEventTypeInput extends FormzInput<ServiceUserEventType, ServiceUserEventTypeValidationError> {
  const ServiceUserEventTypeInput.pure() : super.pure(ServiceUserEventType.ACCIDENT);
  const ServiceUserEventTypeInput.dirty([ServiceUserEventType value]) : super.dirty(value);

  @override
  ServiceUserEventTypeValidationError validator(ServiceUserEventType value) {
    return null;
  }
}

enum PriorityValidationError { invalid }
class PriorityInput extends FormzInput<Priority, PriorityValidationError> {
  const PriorityInput.pure() : super.pure(Priority.HIGH);
  const PriorityInput.dirty([Priority value]) : super.dirty(value);

  @override
  PriorityValidationError validator(Priority value) {
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

enum DateOfEventValidationError { invalid }
class DateOfEventInput extends FormzInput<DateTime, DateOfEventValidationError> {
  DateOfEventInput.pure() : super.pure(DateTime.now());
  DateOfEventInput.dirty([DateTime value]) : super.dirty(value);

  @override
  DateOfEventValidationError validator(DateTime value) {
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

