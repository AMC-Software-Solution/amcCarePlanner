part of 'document_type_bloc.dart';

enum DocumentTypeStatusUI {init, loading, error, done}
enum DocumentTypeDeleteStatus {ok, ko, none}

class DocumentTypeState extends Equatable {
  final List<DocumentType> documentTypes;
  final DocumentType loadedDocumentType;
  final bool editMode;
  final DocumentTypeDeleteStatus deleteStatus;
  final DocumentTypeStatusUI documentTypeStatusUI;

  final FormzStatus formStatus;
  final String generalNotificationKey;

  final DocumentTypeTitleInput documentTypeTitle;
  final DocumentTypeDescriptionInput documentTypeDescription;
  final CreatedDateInput createdDate;
  final LastUpdatedDateInput lastUpdatedDate;
  final HasExtraDataInput hasExtraData;

  
  DocumentTypeState(
CreatedDateInput createdDate,LastUpdatedDateInput lastUpdatedDate,{
    this.documentTypes = const [],
    this.documentTypeStatusUI = DocumentTypeStatusUI.init,
    this.loadedDocumentType = const DocumentType(0,'','',null,null,false,),
    this.editMode = false,
    this.formStatus = FormzStatus.pure,
    this.generalNotificationKey = '',
    this.deleteStatus = DocumentTypeDeleteStatus.none,
    this.documentTypeTitle = const DocumentTypeTitleInput.pure(),
    this.documentTypeDescription = const DocumentTypeDescriptionInput.pure(),
    this.hasExtraData = const HasExtraDataInput.pure(),
  }):this.createdDate = createdDate ?? CreatedDateInput.pure(),
this.lastUpdatedDate = lastUpdatedDate ?? LastUpdatedDateInput.pure()
;

  DocumentTypeState copyWith({
    List<DocumentType> documentTypes,
    DocumentTypeStatusUI documentTypeStatusUI,
    bool editMode,
    DocumentTypeDeleteStatus deleteStatus,
    DocumentType loadedDocumentType,
    FormzStatus formStatus,
    String generalNotificationKey,
    DocumentTypeTitleInput documentTypeTitle,
    DocumentTypeDescriptionInput documentTypeDescription,
    CreatedDateInput createdDate,
    LastUpdatedDateInput lastUpdatedDate,
    HasExtraDataInput hasExtraData,
  }) {
    return DocumentTypeState(
        createdDate,
        lastUpdatedDate,
      documentTypes: documentTypes ?? this.documentTypes,
      documentTypeStatusUI: documentTypeStatusUI ?? this.documentTypeStatusUI,
      loadedDocumentType: loadedDocumentType ?? this.loadedDocumentType,
      editMode: editMode ?? this.editMode,
      formStatus: formStatus ?? this.formStatus,
      generalNotificationKey: generalNotificationKey ?? this.generalNotificationKey,
      deleteStatus: deleteStatus ?? this.deleteStatus,
      documentTypeTitle: documentTypeTitle ?? this.documentTypeTitle,
      documentTypeDescription: documentTypeDescription ?? this.documentTypeDescription,
      hasExtraData: hasExtraData ?? this.hasExtraData,
    );
  }

  @override
  List<Object> get props => [documentTypes, documentTypeStatusUI,
     loadedDocumentType, editMode, deleteStatus, formStatus, generalNotificationKey, 
documentTypeTitle,documentTypeDescription,createdDate,lastUpdatedDate,hasExtraData,];

  @override
  bool get stringify => true;
}
