part of 'communication_bloc.dart';

abstract class CommunicationEvent extends Equatable {
  const CommunicationEvent();

  @override
  List<Object> get props => [];

  @override
  bool get stringify => true;
}

class InitCommunicationList extends CommunicationEvent {}

class CommunicationTypeChanged extends CommunicationEvent {
  final CommunicationType communicationType;
  
  const CommunicationTypeChanged({@required this.communicationType});
  
  @override
  List<Object> get props => [communicationType];
}
class NoteChanged extends CommunicationEvent {
  final String note;
  
  const NoteChanged({@required this.note});
  
  @override
  List<Object> get props => [note];
}
class CommunicationDateChanged extends CommunicationEvent {
  final DateTime communicationDate;
  
  const CommunicationDateChanged({@required this.communicationDate});
  
  @override
  List<Object> get props => [communicationDate];
}
class AttachmentUrlChanged extends CommunicationEvent {
  final String attachmentUrl;
  
  const AttachmentUrlChanged({@required this.attachmentUrl});
  
  @override
  List<Object> get props => [attachmentUrl];
}
class CreatedDateChanged extends CommunicationEvent {
  final DateTime createdDate;
  
  const CreatedDateChanged({@required this.createdDate});
  
  @override
  List<Object> get props => [createdDate];
}
class LastUpdatedDateChanged extends CommunicationEvent {
  final DateTime lastUpdatedDate;
  
  const LastUpdatedDateChanged({@required this.lastUpdatedDate});
  
  @override
  List<Object> get props => [lastUpdatedDate];
}
class ClientIdChanged extends CommunicationEvent {
  final int clientId;
  
  const ClientIdChanged({@required this.clientId});
  
  @override
  List<Object> get props => [clientId];
}
class HasExtraDataChanged extends CommunicationEvent {
  final bool hasExtraData;
  
  const HasExtraDataChanged({@required this.hasExtraData});
  
  @override
  List<Object> get props => [hasExtraData];
}

class CommunicationFormSubmitted extends CommunicationEvent {}

class LoadCommunicationByIdForEdit extends CommunicationEvent {
  final int id;

  const LoadCommunicationByIdForEdit({@required this.id});

  @override
  List<Object> get props => [id];
}

class DeleteCommunicationById extends CommunicationEvent {
  final int id;

  const DeleteCommunicationById({@required this.id});

  @override
  List<Object> get props => [id];
}

class LoadCommunicationByIdForView extends CommunicationEvent {
  final int id;

  const LoadCommunicationByIdForView({@required this.id});

  @override
  List<Object> get props => [id];
}
