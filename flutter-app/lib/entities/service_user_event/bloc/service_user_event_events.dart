part of 'service_user_event_bloc.dart';

abstract class ServiceUserEventEvent extends Equatable {
  const ServiceUserEventEvent();

  @override
  List<Object> get props => [];

  @override
  bool get stringify => true;
}

class InitServiceUserEventList extends ServiceUserEventEvent {}

class EventTitleChanged extends ServiceUserEventEvent {
  final String eventTitle;
  
  const EventTitleChanged({@required this.eventTitle});
  
  @override
  List<Object> get props => [eventTitle];
}
class DescriptionChanged extends ServiceUserEventEvent {
  final String description;
  
  const DescriptionChanged({@required this.description});
  
  @override
  List<Object> get props => [description];
}
class ServiceUserEventStatusChanged extends ServiceUserEventEvent {
  final ServiceUserEventStatus serviceUserEventStatus;
  
  const ServiceUserEventStatusChanged({@required this.serviceUserEventStatus});
  
  @override
  List<Object> get props => [serviceUserEventStatus];
}
class ServiceUserEventTypeChanged extends ServiceUserEventEvent {
  final ServiceUserEventType serviceUserEventType;
  
  const ServiceUserEventTypeChanged({@required this.serviceUserEventType});
  
  @override
  List<Object> get props => [serviceUserEventType];
}
class PriorityChanged extends ServiceUserEventEvent {
  final Priority priority;
  
  const PriorityChanged({@required this.priority});
  
  @override
  List<Object> get props => [priority];
}
class NoteChanged extends ServiceUserEventEvent {
  final String note;
  
  const NoteChanged({@required this.note});
  
  @override
  List<Object> get props => [note];
}
class DateOfEventChanged extends ServiceUserEventEvent {
  final DateTime dateOfEvent;
  
  const DateOfEventChanged({@required this.dateOfEvent});
  
  @override
  List<Object> get props => [dateOfEvent];
}
class CreatedDateChanged extends ServiceUserEventEvent {
  final DateTime createdDate;
  
  const CreatedDateChanged({@required this.createdDate});
  
  @override
  List<Object> get props => [createdDate];
}
class LastUpdatedDateChanged extends ServiceUserEventEvent {
  final DateTime lastUpdatedDate;
  
  const LastUpdatedDateChanged({@required this.lastUpdatedDate});
  
  @override
  List<Object> get props => [lastUpdatedDate];
}
class ClientIdChanged extends ServiceUserEventEvent {
  final int clientId;
  
  const ClientIdChanged({@required this.clientId});
  
  @override
  List<Object> get props => [clientId];
}
class HasExtraDataChanged extends ServiceUserEventEvent {
  final bool hasExtraData;
  
  const HasExtraDataChanged({@required this.hasExtraData});
  
  @override
  List<Object> get props => [hasExtraData];
}

class ServiceUserEventFormSubmitted extends ServiceUserEventEvent {}

class LoadServiceUserEventByIdForEdit extends ServiceUserEventEvent {
  final int id;

  const LoadServiceUserEventByIdForEdit({@required this.id});

  @override
  List<Object> get props => [id];
}

class DeleteServiceUserEventById extends ServiceUserEventEvent {
  final int id;

  const DeleteServiceUserEventById({@required this.id});

  @override
  List<Object> get props => [id];
}

class LoadServiceUserEventByIdForView extends ServiceUserEventEvent {
  final int id;

  const LoadServiceUserEventByIdForView({@required this.id});

  @override
  List<Object> get props => [id];
}
