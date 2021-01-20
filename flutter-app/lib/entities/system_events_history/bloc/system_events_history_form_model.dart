import 'package:formz/formz.dart';
import 'package:amcCarePlanner/entities/system_events_history/system_events_history_model.dart';

enum EventNameValidationError { invalid }
class EventNameInput extends FormzInput<String, EventNameValidationError> {
  const EventNameInput.pure() : super.pure('');
  const EventNameInput.dirty([String value = '']) : super.dirty(value);

  @override
  EventNameValidationError validator(String value) {
    return null;
  }
}

enum EventDateValidationError { invalid }
class EventDateInput extends FormzInput<DateTime, EventDateValidationError> {
  EventDateInput.pure() : super.pure(DateTime.now());
  EventDateInput.dirty([DateTime value]) : super.dirty(value);

  @override
  EventDateValidationError validator(DateTime value) {
    return null;
  }
}

enum EventApiValidationError { invalid }
class EventApiInput extends FormzInput<String, EventApiValidationError> {
  const EventApiInput.pure() : super.pure('');
  const EventApiInput.dirty([String value = '']) : super.dirty(value);

  @override
  EventApiValidationError validator(String value) {
    return null;
  }
}

enum IpAddressValidationError { invalid }
class IpAddressInput extends FormzInput<String, IpAddressValidationError> {
  const IpAddressInput.pure() : super.pure('');
  const IpAddressInput.dirty([String value = '']) : super.dirty(value);

  @override
  IpAddressValidationError validator(String value) {
    return null;
  }
}

enum EventNoteValidationError { invalid }
class EventNoteInput extends FormzInput<String, EventNoteValidationError> {
  const EventNoteInput.pure() : super.pure('');
  const EventNoteInput.dirty([String value = '']) : super.dirty(value);

  @override
  EventNoteValidationError validator(String value) {
    return null;
  }
}

enum EventEntityNameValidationError { invalid }
class EventEntityNameInput extends FormzInput<String, EventEntityNameValidationError> {
  const EventEntityNameInput.pure() : super.pure('');
  const EventEntityNameInput.dirty([String value = '']) : super.dirty(value);

  @override
  EventEntityNameValidationError validator(String value) {
    return null;
  }
}

enum EventEntityIdValidationError { invalid }
class EventEntityIdInput extends FormzInput<int, EventEntityIdValidationError> {
  const EventEntityIdInput.pure() : super.pure(0);
  const EventEntityIdInput.dirty([int value = 0]) : super.dirty(value);

  @override
  EventEntityIdValidationError validator(int value) {
    return null;
  }
}

enum IsSuspeciousValidationError { invalid }
class IsSuspeciousInput extends FormzInput<bool, IsSuspeciousValidationError> {
  const IsSuspeciousInput.pure() : super.pure(false);
  const IsSuspeciousInput.dirty([bool value = false]) : super.dirty(value);

  @override
  IsSuspeciousValidationError validator(bool value) {
    return null;
  }
}

enum CallerEmailValidationError { invalid }
class CallerEmailInput extends FormzInput<String, CallerEmailValidationError> {
  const CallerEmailInput.pure() : super.pure('');
  const CallerEmailInput.dirty([String value = '']) : super.dirty(value);

  @override
  CallerEmailValidationError validator(String value) {
    return null;
  }
}

enum CallerIdValidationError { invalid }
class CallerIdInput extends FormzInput<int, CallerIdValidationError> {
  const CallerIdInput.pure() : super.pure(0);
  const CallerIdInput.dirty([int value = 0]) : super.dirty(value);

  @override
  CallerIdValidationError validator(int value) {
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

