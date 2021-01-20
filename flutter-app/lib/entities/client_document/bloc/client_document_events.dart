part of 'client_document_bloc.dart';

abstract class ClientDocumentEvent extends Equatable {
  const ClientDocumentEvent();

  @override
  List<Object> get props => [];

  @override
  bool get stringify => true;
}

class InitClientDocumentList extends ClientDocumentEvent {}

class DocumentNameChanged extends ClientDocumentEvent {
  final String documentName;
  
  const DocumentNameChanged({@required this.documentName});
  
  @override
  List<Object> get props => [documentName];
}
class DocumentNumberChanged extends ClientDocumentEvent {
  final String documentNumber;
  
  const DocumentNumberChanged({@required this.documentNumber});
  
  @override
  List<Object> get props => [documentNumber];
}
class DocumentTypeChanged extends ClientDocumentEvent {
  final ClientDocumentType documentType;
  
  const DocumentTypeChanged({@required this.documentType});
  
  @override
  List<Object> get props => [documentType];
}
class ClientDocumentStatusChanged extends ClientDocumentEvent {
  final ClientDocumentStatus clientDocumentStatus;
  
  const ClientDocumentStatusChanged({@required this.clientDocumentStatus});
  
  @override
  List<Object> get props => [clientDocumentStatus];
}
class NoteChanged extends ClientDocumentEvent {
  final String note;
  
  const NoteChanged({@required this.note});
  
  @override
  List<Object> get props => [note];
}
class IssuedDateChanged extends ClientDocumentEvent {
  final DateTime issuedDate;
  
  const IssuedDateChanged({@required this.issuedDate});
  
  @override
  List<Object> get props => [issuedDate];
}
class ExpiryDateChanged extends ClientDocumentEvent {
  final DateTime expiryDate;
  
  const ExpiryDateChanged({@required this.expiryDate});
  
  @override
  List<Object> get props => [expiryDate];
}
class UploadedDateChanged extends ClientDocumentEvent {
  final DateTime uploadedDate;
  
  const UploadedDateChanged({@required this.uploadedDate});
  
  @override
  List<Object> get props => [uploadedDate];
}
class DocumentFileUrlChanged extends ClientDocumentEvent {
  final String documentFileUrl;
  
  const DocumentFileUrlChanged({@required this.documentFileUrl});
  
  @override
  List<Object> get props => [documentFileUrl];
}
class CreatedDateChanged extends ClientDocumentEvent {
  final DateTime createdDate;
  
  const CreatedDateChanged({@required this.createdDate});
  
  @override
  List<Object> get props => [createdDate];
}
class LastUpdatedDateChanged extends ClientDocumentEvent {
  final DateTime lastUpdatedDate;
  
  const LastUpdatedDateChanged({@required this.lastUpdatedDate});
  
  @override
  List<Object> get props => [lastUpdatedDate];
}
class ClientIdChanged extends ClientDocumentEvent {
  final int clientId;
  
  const ClientIdChanged({@required this.clientId});
  
  @override
  List<Object> get props => [clientId];
}
class HasExtraDataChanged extends ClientDocumentEvent {
  final bool hasExtraData;
  
  const HasExtraDataChanged({@required this.hasExtraData});
  
  @override
  List<Object> get props => [hasExtraData];
}

class ClientDocumentFormSubmitted extends ClientDocumentEvent {}

class LoadClientDocumentByIdForEdit extends ClientDocumentEvent {
  final int id;

  const LoadClientDocumentByIdForEdit({@required this.id});

  @override
  List<Object> get props => [id];
}

class DeleteClientDocumentById extends ClientDocumentEvent {
  final int id;

  const DeleteClientDocumentById({@required this.id});

  @override
  List<Object> get props => [id];
}

class LoadClientDocumentByIdForView extends ClientDocumentEvent {
  final int id;

  const LoadClientDocumentByIdForView({@required this.id});

  @override
  List<Object> get props => [id];
}
