part of 'client_bloc.dart';

abstract class ClientEvent extends Equatable {
  const ClientEvent();

  @override
  List<Object> get props => [];

  @override
  bool get stringify => true;
}

class InitClientList extends ClientEvent {}

class ClientNameChanged extends ClientEvent {
  final String clientName;
  
  const ClientNameChanged({@required this.clientName});
  
  @override
  List<Object> get props => [clientName];
}
class ClientDescriptionChanged extends ClientEvent {
  final String clientDescription;
  
  const ClientDescriptionChanged({@required this.clientDescription});
  
  @override
  List<Object> get props => [clientDescription];
}
class ClientLogoUrlChanged extends ClientEvent {
  final String clientLogoUrl;
  
  const ClientLogoUrlChanged({@required this.clientLogoUrl});
  
  @override
  List<Object> get props => [clientLogoUrl];
}
class ClientContactNameChanged extends ClientEvent {
  final String clientContactName;
  
  const ClientContactNameChanged({@required this.clientContactName});
  
  @override
  List<Object> get props => [clientContactName];
}
class ClientPhoneChanged extends ClientEvent {
  final String clientPhone;
  
  const ClientPhoneChanged({@required this.clientPhone});
  
  @override
  List<Object> get props => [clientPhone];
}
class ClientEmailChanged extends ClientEvent {
  final String clientEmail;
  
  const ClientEmailChanged({@required this.clientEmail});
  
  @override
  List<Object> get props => [clientEmail];
}
class CreatedDateChanged extends ClientEvent {
  final DateTime createdDate;
  
  const CreatedDateChanged({@required this.createdDate});
  
  @override
  List<Object> get props => [createdDate];
}
class EnabledChanged extends ClientEvent {
  final bool enabled;
  
  const EnabledChanged({@required this.enabled});
  
  @override
  List<Object> get props => [enabled];
}
class ReasonChanged extends ClientEvent {
  final String reason;
  
  const ReasonChanged({@required this.reason});
  
  @override
  List<Object> get props => [reason];
}
class LastUpdatedDateChanged extends ClientEvent {
  final DateTime lastUpdatedDate;
  
  const LastUpdatedDateChanged({@required this.lastUpdatedDate});
  
  @override
  List<Object> get props => [lastUpdatedDate];
}
class HasExtraDataChanged extends ClientEvent {
  final bool hasExtraData;
  
  const HasExtraDataChanged({@required this.hasExtraData});
  
  @override
  List<Object> get props => [hasExtraData];
}

class ClientFormSubmitted extends ClientEvent {}

class LoadClientByIdForEdit extends ClientEvent {
  final int id;

  const LoadClientByIdForEdit({@required this.id});

  @override
  List<Object> get props => [id];
}

class DeleteClientById extends ClientEvent {
  final int id;

  const DeleteClientById({@required this.id});

  @override
  List<Object> get props => [id];
}

class LoadClientByIdForView extends ClientEvent {
  final int id;

  const LoadClientByIdForView({@required this.id});

  @override
  List<Object> get props => [id];
}
