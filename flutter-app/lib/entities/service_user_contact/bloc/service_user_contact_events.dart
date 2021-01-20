part of 'service_user_contact_bloc.dart';

abstract class ServiceUserContactEvent extends Equatable {
  const ServiceUserContactEvent();

  @override
  List<Object> get props => [];

  @override
  bool get stringify => true;
}

class InitServiceUserContactList extends ServiceUserContactEvent {}

class AddressChanged extends ServiceUserContactEvent {
  final String address;
  
  const AddressChanged({@required this.address});
  
  @override
  List<Object> get props => [address];
}
class CityOrTownChanged extends ServiceUserContactEvent {
  final String cityOrTown;
  
  const CityOrTownChanged({@required this.cityOrTown});
  
  @override
  List<Object> get props => [cityOrTown];
}
class CountyChanged extends ServiceUserContactEvent {
  final String county;
  
  const CountyChanged({@required this.county});
  
  @override
  List<Object> get props => [county];
}
class PostCodeChanged extends ServiceUserContactEvent {
  final String postCode;
  
  const PostCodeChanged({@required this.postCode});
  
  @override
  List<Object> get props => [postCode];
}
class TelephoneChanged extends ServiceUserContactEvent {
  final String telephone;
  
  const TelephoneChanged({@required this.telephone});
  
  @override
  List<Object> get props => [telephone];
}
class CreatedDateChanged extends ServiceUserContactEvent {
  final DateTime createdDate;
  
  const CreatedDateChanged({@required this.createdDate});
  
  @override
  List<Object> get props => [createdDate];
}
class LastUpdatedDateChanged extends ServiceUserContactEvent {
  final DateTime lastUpdatedDate;
  
  const LastUpdatedDateChanged({@required this.lastUpdatedDate});
  
  @override
  List<Object> get props => [lastUpdatedDate];
}
class ClientIdChanged extends ServiceUserContactEvent {
  final int clientId;
  
  const ClientIdChanged({@required this.clientId});
  
  @override
  List<Object> get props => [clientId];
}
class HasExtraDataChanged extends ServiceUserContactEvent {
  final bool hasExtraData;
  
  const HasExtraDataChanged({@required this.hasExtraData});
  
  @override
  List<Object> get props => [hasExtraData];
}

class ServiceUserContactFormSubmitted extends ServiceUserContactEvent {}

class LoadServiceUserContactByIdForEdit extends ServiceUserContactEvent {
  final int id;

  const LoadServiceUserContactByIdForEdit({@required this.id});

  @override
  List<Object> get props => [id];
}

class DeleteServiceUserContactById extends ServiceUserContactEvent {
  final int id;

  const DeleteServiceUserContactById({@required this.id});

  @override
  List<Object> get props => [id];
}

class LoadServiceUserContactByIdForView extends ServiceUserContactEvent {
  final int id;

  const LoadServiceUserContactByIdForView({@required this.id});

  @override
  List<Object> get props => [id];
}
