part of 'system_events_history_bloc.dart';

abstract class SystemEventsHistoryEvent extends Equatable {
  const SystemEventsHistoryEvent();

  @override
  List<Object> get props => [];

  @override
  bool get stringify => true;
}

class InitSystemEventsHistoryList extends SystemEventsHistoryEvent {}

class EventNameChanged extends SystemEventsHistoryEvent {
  final String eventName;
  
  const EventNameChanged({@required this.eventName});
  
  @override
  List<Object> get props => [eventName];
}
class EventDateChanged extends SystemEventsHistoryEvent {
  final DateTime eventDate;
  
  const EventDateChanged({@required this.eventDate});
  
  @override
  List<Object> get props => [eventDate];
}
class EventApiChanged extends SystemEventsHistoryEvent {
  final String eventApi;
  
  const EventApiChanged({@required this.eventApi});
  
  @override
  List<Object> get props => [eventApi];
}
class IpAddressChanged extends SystemEventsHistoryEvent {
  final String ipAddress;
  
  const IpAddressChanged({@required this.ipAddress});
  
  @override
  List<Object> get props => [ipAddress];
}
class EventNoteChanged extends SystemEventsHistoryEvent {
  final String eventNote;
  
  const EventNoteChanged({@required this.eventNote});
  
  @override
  List<Object> get props => [eventNote];
}
class EventEntityNameChanged extends SystemEventsHistoryEvent {
  final String eventEntityName;
  
  const EventEntityNameChanged({@required this.eventEntityName});
  
  @override
  List<Object> get props => [eventEntityName];
}
class EventEntityIdChanged extends SystemEventsHistoryEvent {
  final int eventEntityId;
  
  const EventEntityIdChanged({@required this.eventEntityId});
  
  @override
  List<Object> get props => [eventEntityId];
}
class IsSuspeciousChanged extends SystemEventsHistoryEvent {
  final bool isSuspecious;
  
  const IsSuspeciousChanged({@required this.isSuspecious});
  
  @override
  List<Object> get props => [isSuspecious];
}
class CallerEmailChanged extends SystemEventsHistoryEvent {
  final String callerEmail;
  
  const CallerEmailChanged({@required this.callerEmail});
  
  @override
  List<Object> get props => [callerEmail];
}
class CallerIdChanged extends SystemEventsHistoryEvent {
  final int callerId;
  
  const CallerIdChanged({@required this.callerId});
  
  @override
  List<Object> get props => [callerId];
}
class ClientIdChanged extends SystemEventsHistoryEvent {
  final int clientId;
  
  const ClientIdChanged({@required this.clientId});
  
  @override
  List<Object> get props => [clientId];
}

class SystemEventsHistoryFormSubmitted extends SystemEventsHistoryEvent {}

class LoadSystemEventsHistoryByIdForEdit extends SystemEventsHistoryEvent {
  final int id;

  const LoadSystemEventsHistoryByIdForEdit({@required this.id});

  @override
  List<Object> get props => [id];
}

class DeleteSystemEventsHistoryById extends SystemEventsHistoryEvent {
  final int id;

  const DeleteSystemEventsHistoryById({@required this.id});

  @override
  List<Object> get props => [id];
}

class LoadSystemEventsHistoryByIdForView extends SystemEventsHistoryEvent {
  final int id;

  const LoadSystemEventsHistoryByIdForView({@required this.id});

  @override
  List<Object> get props => [id];
}
