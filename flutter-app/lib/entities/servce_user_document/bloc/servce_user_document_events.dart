part of 'servce_user_document_bloc.dart';

abstract class ServceUserDocumentEvent extends Equatable {
  const ServceUserDocumentEvent();

  @override
  List<Object> get props => [];

  @override
  bool get stringify => true;
}

class InitServceUserDocumentList extends ServceUserDocumentEvent {}

class DocumentNameChanged extends ServceUserDocumentEvent {
  final String documentName;
  
  const DocumentNameChanged({@required this.documentName});
  
  @override
  List<Object> get props => [documentName];
}
class DocumentNumberChanged extends ServceUserDocumentEvent {
  final String documentNumber;
  
  const DocumentNumberChanged({@required this.documentNumber});
  
  @override
  List<Object> get props => [documentNumber];
}
class DocumentStatusChanged extends ServceUserDocumentEvent {
  final ServiceUserDocumentStatus documentStatus;
  
  const DocumentStatusChanged({@required this.documentStatus});
  
  @override
  List<Object> get props => [documentStatus];
}
class NoteChanged extends ServceUserDocumentEvent {
  final String note;
  
  const NoteChanged({@required this.note});
  
  @override
  List<Object> get props => [note];
}
class IssuedDateChanged extends ServceUserDocumentEvent {
  final DateTime issuedDate;
  
  const IssuedDateChanged({@required this.issuedDate});
  
  @override
  List<Object> get props => [issuedDate];
}
class ExpiryDateChanged extends ServceUserDocumentEvent {
  final DateTime expiryDate;
  
  const ExpiryDateChanged({@required this.expiryDate});
  
  @override
  List<Object> get props => [expiryDate];
}
class UploadedDateChanged extends ServceUserDocumentEvent {
  final DateTime uploadedDate;
  
  const UploadedDateChanged({@required this.uploadedDate});
  
  @override
  List<Object> get props => [uploadedDate];
}
class DocumentFileUrlChanged extends ServceUserDocumentEvent {
  final String documentFileUrl;
  
  const DocumentFileUrlChanged({@required this.documentFileUrl});
  
  @override
  List<Object> get props => [documentFileUrl];
}
class CreatedDateChanged extends ServceUserDocumentEvent {
  final DateTime createdDate;
  
  const CreatedDateChanged({@required this.createdDate});
  
  @override
  List<Object> get props => [createdDate];
}
class LastUpdatedDateChanged extends ServceUserDocumentEvent {
  final DateTime lastUpdatedDate;
  
  const LastUpdatedDateChanged({@required this.lastUpdatedDate});
  
  @override
  List<Object> get props => [lastUpdatedDate];
}
class ClientIdChanged extends ServceUserDocumentEvent {
  final int clientId;
  
  const ClientIdChanged({@required this.clientId});
  
  @override
  List<Object> get props => [clientId];
}
class HasExtraDataChanged extends ServceUserDocumentEvent {
  final bool hasExtraData;
  
  const HasExtraDataChanged({@required this.hasExtraData});
  
  @override
  List<Object> get props => [hasExtraData];
}

class ServceUserDocumentFormSubmitted extends ServceUserDocumentEvent {}

class LoadServceUserDocumentByIdForEdit extends ServceUserDocumentEvent {
  final int id;

  const LoadServceUserDocumentByIdForEdit({@required this.id});

  @override
  List<Object> get props => [id];
}

class DeleteServceUserDocumentById extends ServceUserDocumentEvent {
  final int id;

  const DeleteServceUserDocumentById({@required this.id});

  @override
  List<Object> get props => [id];
}

class LoadServceUserDocumentByIdForView extends ServceUserDocumentEvent {
  final int id;

  const LoadServceUserDocumentByIdForView({@required this.id});

  @override
  List<Object> get props => [id];
}
