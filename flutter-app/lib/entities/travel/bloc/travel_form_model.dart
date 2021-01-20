import 'package:formz/formz.dart';
import 'package:amcCarePlanner/entities/travel/travel_model.dart';

enum TravelModeValidationError { invalid }
class TravelModeInput extends FormzInput<TravelMode, TravelModeValidationError> {
  const TravelModeInput.pure() : super.pure(TravelMode.CAR);
  const TravelModeInput.dirty([TravelMode value]) : super.dirty(value);

  @override
  TravelModeValidationError validator(TravelMode value) {
    return null;
  }
}

enum DistanceToDestinationValidationError { invalid }
class DistanceToDestinationInput extends FormzInput<int, DistanceToDestinationValidationError> {
  const DistanceToDestinationInput.pure() : super.pure(0);
  const DistanceToDestinationInput.dirty([int value = 0]) : super.dirty(value);

  @override
  DistanceToDestinationValidationError validator(int value) {
    return null;
  }
}

enum TimeToDestinationValidationError { invalid }
class TimeToDestinationInput extends FormzInput<int, TimeToDestinationValidationError> {
  const TimeToDestinationInput.pure() : super.pure(0);
  const TimeToDestinationInput.dirty([int value = 0]) : super.dirty(value);

  @override
  TimeToDestinationValidationError validator(int value) {
    return null;
  }
}

enum ActualDistanceRequiredValidationError { invalid }
class ActualDistanceRequiredInput extends FormzInput<int, ActualDistanceRequiredValidationError> {
  const ActualDistanceRequiredInput.pure() : super.pure(0);
  const ActualDistanceRequiredInput.dirty([int value = 0]) : super.dirty(value);

  @override
  ActualDistanceRequiredValidationError validator(int value) {
    return null;
  }
}

enum ActualTimeRequiredValidationError { invalid }
class ActualTimeRequiredInput extends FormzInput<int, ActualTimeRequiredValidationError> {
  const ActualTimeRequiredInput.pure() : super.pure(0);
  const ActualTimeRequiredInput.dirty([int value = 0]) : super.dirty(value);

  @override
  ActualTimeRequiredValidationError validator(int value) {
    return null;
  }
}

enum TravelStatusValidationError { invalid }
class TravelStatusInput extends FormzInput<TravelStatus, TravelStatusValidationError> {
  const TravelStatusInput.pure() : super.pure(TravelStatus.BOOKED);
  const TravelStatusInput.dirty([TravelStatus value]) : super.dirty(value);

  @override
  TravelStatusValidationError validator(TravelStatus value) {
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

