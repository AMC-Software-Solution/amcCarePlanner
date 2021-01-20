part of 'document_type_bloc.dart';

abstract class DocumentTypeEvent extends Equatable {
  const DocumentTypeEvent();

  @override
  List<Object> get props => [];

  @override
  bool get stringify => true;
}

class InitDocumentTypeList extends DocumentTypeEvent {}

class DocumentTypeTitleChanged extends DocumentTypeEvent {
  final String documentTypeTitle;
  
  const DocumentTypeTitleChanged({@required this.documentTypeTitle});
  
  @override
  List<Object> get props => [documentTypeTitle];
}
class DocumentTypeDescriptionChanged extends DocumentTypeEvent {
  final String documentTypeDescription;
  
  const DocumentTypeDescriptionChanged({@required this.documentTypeDescription});
  
  @override
  List<Object> get props => [documentTypeDescription];
}
class CreatedDateChanged extends DocumentTypeEvent {
  final DateTime createdDate;
  
  const CreatedDateChanged({@required this.createdDate});
  
  @override
  List<Object> get props => [createdDate];
}
class LastUpdatedDateChanged extends DocumentTypeEvent {
  final DateTime lastUpdatedDate;
  
  const LastUpdatedDateChanged({@required this.lastUpdatedDate});
  
  @override
  List<Object> get props => [lastUpdatedDate];
}
class HasExtraDataChanged extends DocumentTypeEvent {
  final bool hasExtraData;
  
  const HasExtraDataChanged({@required this.hasExtraData});
  
  @override
  List<Object> get props => [hasExtraData];
}

class DocumentTypeFormSubmitted extends DocumentTypeEvent {}

class LoadDocumentTypeByIdForEdit extends DocumentTypeEvent {
  final int id;

  const LoadDocumentTypeByIdForEdit({@required this.id});

  @override
  List<Object> get props => [id];
}

class DeleteDocumentTypeById extends DocumentTypeEvent {
  final int id;

  const DeleteDocumentTypeById({@required this.id});

  @override
  List<Object> get props => [id];
}

class LoadDocumentTypeByIdForView extends DocumentTypeEvent {
  final int id;

  const LoadDocumentTypeByIdForView({@required this.id});

  @override
  List<Object> get props => [id];
}
