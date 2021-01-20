part of 'service_user_location_bloc.dart';

abstract class ServiceUserLocationEvent extends Equatable {
  const ServiceUserLocationEvent();

  @override
  List<Object> get props => [];

  @override
  bool get stringify => true;
}

class InitServiceUserLocationList extends ServiceUserLocationEvent {}

class LatitudeChanged extends ServiceUserLocationEvent {
  final String latitude;
  
  const LatitudeChanged({@required this.latitude});
  
  @override
  List<Object> get props => [latitude];
}
class LongitudeChanged extends ServiceUserLocationEvent {
  final String longitude;
  
  const LongitudeChanged({@required this.longitude});
  
  @override
  List<Object> get props => [longitude];
}
class CreatedDateChanged extends ServiceUserLocationEvent {
  final DateTime createdDate;
  
  const CreatedDateChanged({@required this.createdDate});
  
  @override
  List<Object> get props => [createdDate];
}
class LastUpdatedDateChanged extends ServiceUserLocationEvent {
  final DateTime lastUpdatedDate;
  
  const LastUpdatedDateChanged({@required this.lastUpdatedDate});
  
  @override
  List<Object> get props => [lastUpdatedDate];
}
class ClientIdChanged extends ServiceUserLocationEvent {
  final int clientId;
  
  const ClientIdChanged({@required this.clientId});
  
  @override
  List<Object> get props => [clientId];
}
class HasExtraDataChanged extends ServiceUserLocationEvent {
  final bool hasExtraData;
  
  const HasExtraDataChanged({@required this.hasExtraData});
  
  @override
  List<Object> get props => [hasExtraData];
}

class ServiceUserLocationFormSubmitted extends ServiceUserLocationEvent {}

class LoadServiceUserLocationByIdForEdit extends ServiceUserLocationEvent {
  final int id;

  const LoadServiceUserLocationByIdForEdit({@required this.id});

  @override
  List<Object> get props => [id];
}

class DeleteServiceUserLocationById extends ServiceUserLocationEvent {
  final int id;

  const DeleteServiceUserLocationById({@required this.id});

  @override
  List<Object> get props => [id];
}

class LoadServiceUserLocationByIdForView extends ServiceUserLocationEvent {
  final int id;

  const LoadServiceUserLocationByIdForView({@required this.id});

  @override
  List<Object> get props => [id];
}
