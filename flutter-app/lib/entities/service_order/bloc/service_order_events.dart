part of 'service_order_bloc.dart';

abstract class ServiceOrderEvent extends Equatable {
  const ServiceOrderEvent();

  @override
  List<Object> get props => [];

  @override
  bool get stringify => true;
}

class InitServiceOrderList extends ServiceOrderEvent {}

class TitleChanged extends ServiceOrderEvent {
  final String title;
  
  const TitleChanged({@required this.title});
  
  @override
  List<Object> get props => [title];
}
class ServiceDescriptionChanged extends ServiceOrderEvent {
  final String serviceDescription;
  
  const ServiceDescriptionChanged({@required this.serviceDescription});
  
  @override
  List<Object> get props => [serviceDescription];
}
class ServiceHourlyRateChanged extends ServiceOrderEvent {
  final String serviceHourlyRate;
  
  const ServiceHourlyRateChanged({@required this.serviceHourlyRate});
  
  @override
  List<Object> get props => [serviceHourlyRate];
}
class ClientIdChanged extends ServiceOrderEvent {
  final int clientId;
  
  const ClientIdChanged({@required this.clientId});
  
  @override
  List<Object> get props => [clientId];
}
class CreatedDateChanged extends ServiceOrderEvent {
  final DateTime createdDate;
  
  const CreatedDateChanged({@required this.createdDate});
  
  @override
  List<Object> get props => [createdDate];
}
class LastUpdatedDateChanged extends ServiceOrderEvent {
  final DateTime lastUpdatedDate;
  
  const LastUpdatedDateChanged({@required this.lastUpdatedDate});
  
  @override
  List<Object> get props => [lastUpdatedDate];
}
class HasExtraDataChanged extends ServiceOrderEvent {
  final bool hasExtraData;
  
  const HasExtraDataChanged({@required this.hasExtraData});
  
  @override
  List<Object> get props => [hasExtraData];
}

class ServiceOrderFormSubmitted extends ServiceOrderEvent {}

class LoadServiceOrderByIdForEdit extends ServiceOrderEvent {
  final int id;

  const LoadServiceOrderByIdForEdit({@required this.id});

  @override
  List<Object> get props => [id];
}

class DeleteServiceOrderById extends ServiceOrderEvent {
  final int id;

  const DeleteServiceOrderById({@required this.id});

  @override
  List<Object> get props => [id];
}

class LoadServiceOrderByIdForView extends ServiceOrderEvent {
  final int id;

  const LoadServiceOrderByIdForView({@required this.id});

  @override
  List<Object> get props => [id];
}
