part of 'employee_document_bloc.dart';

abstract class EmployeeDocumentEvent extends Equatable {
  const EmployeeDocumentEvent();

  @override
  List<Object> get props => [];

  @override
  bool get stringify => true;
}

class InitEmployeeDocumentList extends EmployeeDocumentEvent {}

class DocumentNameChanged extends EmployeeDocumentEvent {
  final String documentName;
  
  const DocumentNameChanged({@required this.documentName});
  
  @override
  List<Object> get props => [documentName];
}
class DocumentNumberChanged extends EmployeeDocumentEvent {
  final String documentNumber;
  
  const DocumentNumberChanged({@required this.documentNumber});
  
  @override
  List<Object> get props => [documentNumber];
}
class EmployeeDocumentStatusChanged extends EmployeeDocumentEvent {
  final EmployeeDocumentStatus employeeDocumentStatus;
  
  const EmployeeDocumentStatusChanged({@required this.employeeDocumentStatus});
  
  @override
  List<Object> get props => [employeeDocumentStatus];
}
class NoteChanged extends EmployeeDocumentEvent {
  final String note;
  
  const NoteChanged({@required this.note});
  
  @override
  List<Object> get props => [note];
}
class IssuedDateChanged extends EmployeeDocumentEvent {
  final DateTime issuedDate;
  
  const IssuedDateChanged({@required this.issuedDate});
  
  @override
  List<Object> get props => [issuedDate];
}
class ExpiryDateChanged extends EmployeeDocumentEvent {
  final DateTime expiryDate;
  
  const ExpiryDateChanged({@required this.expiryDate});
  
  @override
  List<Object> get props => [expiryDate];
}
class UploadedDateChanged extends EmployeeDocumentEvent {
  final DateTime uploadedDate;
  
  const UploadedDateChanged({@required this.uploadedDate});
  
  @override
  List<Object> get props => [uploadedDate];
}
class DocumentFileUrlChanged extends EmployeeDocumentEvent {
  final String documentFileUrl;
  
  const DocumentFileUrlChanged({@required this.documentFileUrl});
  
  @override
  List<Object> get props => [documentFileUrl];
}
class CreatedDateChanged extends EmployeeDocumentEvent {
  final DateTime createdDate;
  
  const CreatedDateChanged({@required this.createdDate});
  
  @override
  List<Object> get props => [createdDate];
}
class LastUpdatedDateChanged extends EmployeeDocumentEvent {
  final DateTime lastUpdatedDate;
  
  const LastUpdatedDateChanged({@required this.lastUpdatedDate});
  
  @override
  List<Object> get props => [lastUpdatedDate];
}
class ClientIdChanged extends EmployeeDocumentEvent {
  final int clientId;
  
  const ClientIdChanged({@required this.clientId});
  
  @override
  List<Object> get props => [clientId];
}
class HasExtraDataChanged extends EmployeeDocumentEvent {
  final bool hasExtraData;
  
  const HasExtraDataChanged({@required this.hasExtraData});
  
  @override
  List<Object> get props => [hasExtraData];
}

class EmployeeDocumentFormSubmitted extends EmployeeDocumentEvent {}

class LoadEmployeeDocumentByIdForEdit extends EmployeeDocumentEvent {
  final int id;

  const LoadEmployeeDocumentByIdForEdit({@required this.id});

  @override
  List<Object> get props => [id];
}

class DeleteEmployeeDocumentById extends EmployeeDocumentEvent {
  final int id;

  const DeleteEmployeeDocumentById({@required this.id});

  @override
  List<Object> get props => [id];
}

class LoadEmployeeDocumentByIdForView extends EmployeeDocumentEvent {
  final int id;

  const LoadEmployeeDocumentByIdForView({@required this.id});

  @override
  List<Object> get props => [id];
}
