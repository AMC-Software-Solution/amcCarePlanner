part of 'travel_bloc.dart';

abstract class TravelEvent extends Equatable {
  const TravelEvent();

  @override
  List<Object> get props => [];

  @override
  bool get stringify => true;
}

class InitTravelList extends TravelEvent {}

class TravelModeChanged extends TravelEvent {
  final TravelMode travelMode;
  
  const TravelModeChanged({@required this.travelMode});
  
  @override
  List<Object> get props => [travelMode];
}
class DistanceToDestinationChanged extends TravelEvent {
  final int distanceToDestination;
  
  const DistanceToDestinationChanged({@required this.distanceToDestination});
  
  @override
  List<Object> get props => [distanceToDestination];
}
class TimeToDestinationChanged extends TravelEvent {
  final int timeToDestination;
  
  const TimeToDestinationChanged({@required this.timeToDestination});
  
  @override
  List<Object> get props => [timeToDestination];
}
class ActualDistanceRequiredChanged extends TravelEvent {
  final int actualDistanceRequired;
  
  const ActualDistanceRequiredChanged({@required this.actualDistanceRequired});
  
  @override
  List<Object> get props => [actualDistanceRequired];
}
class ActualTimeRequiredChanged extends TravelEvent {
  final int actualTimeRequired;
  
  const ActualTimeRequiredChanged({@required this.actualTimeRequired});
  
  @override
  List<Object> get props => [actualTimeRequired];
}
class TravelStatusChanged extends TravelEvent {
  final TravelStatus travelStatus;
  
  const TravelStatusChanged({@required this.travelStatus});
  
  @override
  List<Object> get props => [travelStatus];
}
class CreatedDateChanged extends TravelEvent {
  final DateTime createdDate;
  
  const CreatedDateChanged({@required this.createdDate});
  
  @override
  List<Object> get props => [createdDate];
}
class LastUpdatedDateChanged extends TravelEvent {
  final DateTime lastUpdatedDate;
  
  const LastUpdatedDateChanged({@required this.lastUpdatedDate});
  
  @override
  List<Object> get props => [lastUpdatedDate];
}
class ClientIdChanged extends TravelEvent {
  final int clientId;
  
  const ClientIdChanged({@required this.clientId});
  
  @override
  List<Object> get props => [clientId];
}
class HasExtraDataChanged extends TravelEvent {
  final bool hasExtraData;
  
  const HasExtraDataChanged({@required this.hasExtraData});
  
  @override
  List<Object> get props => [hasExtraData];
}

class TravelFormSubmitted extends TravelEvent {}

class LoadTravelByIdForEdit extends TravelEvent {
  final int id;

  const LoadTravelByIdForEdit({@required this.id});

  @override
  List<Object> get props => [id];
}

class DeleteTravelById extends TravelEvent {
  final int id;

  const DeleteTravelById({@required this.id});

  @override
  List<Object> get props => [id];
}

class LoadTravelByIdForView extends TravelEvent {
  final int id;

  const LoadTravelByIdForView({@required this.id});

  @override
  List<Object> get props => [id];
}
