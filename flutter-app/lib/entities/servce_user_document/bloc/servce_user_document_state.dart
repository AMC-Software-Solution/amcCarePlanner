part of 'servce_user_document_bloc.dart';

enum ServceUserDocumentStatusUI {init, loading, error, done}
enum ServceUserDocumentDeleteStatus {ok, ko, none}

class ServceUserDocumentState extends Equatable {
  final List<ServceUserDocument> servceUserDocuments;
  final ServceUserDocument loadedServceUserDocument;
  final bool editMode;
  final ServceUserDocumentDeleteStatus deleteStatus;
  final ServceUserDocumentStatusUI servceUserDocumentStatusUI;

  final FormzStatus formStatus;
  final String generalNotificationKey;

  final DocumentNameInput documentName;
  final DocumentNumberInput documentNumber;
  final DocumentStatusInput documentStatus;
  final NoteInput note;
  final IssuedDateInput issuedDate;
  final ExpiryDateInput expiryDate;
  final UploadedDateInput uploadedDate;
  final DocumentFileUrlInput documentFileUrl;
  final CreatedDateInput createdDate;
  final LastUpdatedDateInput lastUpdatedDate;
  final ClientIdInput clientId;
  final HasExtraDataInput hasExtraData;

  
  ServceUserDocumentState(
IssuedDateInput issuedDate,ExpiryDateInput expiryDate,UploadedDateInput uploadedDate,CreatedDateInput createdDate,LastUpdatedDateInput lastUpdatedDate,{
    this.servceUserDocuments = const [],
    this.servceUserDocumentStatusUI = ServceUserDocumentStatusUI.init,
    this.loadedServceUserDocument = const ServceUserDocument(0,'','',null,'',null,null,null,'',null,null,0,false,null,null,),
    this.editMode = false,
    this.formStatus = FormzStatus.pure,
    this.generalNotificationKey = '',
    this.deleteStatus = ServceUserDocumentDeleteStatus.none,
    this.documentName = const DocumentNameInput.pure(),
    this.documentNumber = const DocumentNumberInput.pure(),
    this.documentStatus = const DocumentStatusInput.pure(),
    this.note = const NoteInput.pure(),
    this.documentFileUrl = const DocumentFileUrlInput.pure(),
    this.clientId = const ClientIdInput.pure(),
    this.hasExtraData = const HasExtraDataInput.pure(),
  }):this.issuedDate = issuedDate ?? IssuedDateInput.pure(),
this.expiryDate = expiryDate ?? ExpiryDateInput.pure(),
this.uploadedDate = uploadedDate ?? UploadedDateInput.pure(),
this.createdDate = createdDate ?? CreatedDateInput.pure(),
this.lastUpdatedDate = lastUpdatedDate ?? LastUpdatedDateInput.pure()
;

  ServceUserDocumentState copyWith({
    List<ServceUserDocument> servceUserDocuments,
    ServceUserDocumentStatusUI servceUserDocumentStatusUI,
    bool editMode,
    ServceUserDocumentDeleteStatus deleteStatus,
    ServceUserDocument loadedServceUserDocument,
    FormzStatus formStatus,
    String generalNotificationKey,
    DocumentNameInput documentName,
    DocumentNumberInput documentNumber,
    DocumentStatusInput documentStatus,
    NoteInput note,
    IssuedDateInput issuedDate,
    ExpiryDateInput expiryDate,
    UploadedDateInput uploadedDate,
    DocumentFileUrlInput documentFileUrl,
    CreatedDateInput createdDate,
    LastUpdatedDateInput lastUpdatedDate,
    ClientIdInput clientId,
    HasExtraDataInput hasExtraData,
  }) {
    return ServceUserDocumentState(
        issuedDate,
        expiryDate,
        uploadedDate,
        createdDate,
        lastUpdatedDate,
      servceUserDocuments: servceUserDocuments ?? this.servceUserDocuments,
      servceUserDocumentStatusUI: servceUserDocumentStatusUI ?? this.servceUserDocumentStatusUI,
      loadedServceUserDocument: loadedServceUserDocument ?? this.loadedServceUserDocument,
      editMode: editMode ?? this.editMode,
      formStatus: formStatus ?? this.formStatus,
      generalNotificationKey: generalNotificationKey ?? this.generalNotificationKey,
      deleteStatus: deleteStatus ?? this.deleteStatus,
      documentName: documentName ?? this.documentName,
      documentNumber: documentNumber ?? this.documentNumber,
      documentStatus: documentStatus ?? this.documentStatus,
      note: note ?? this.note,
      documentFileUrl: documentFileUrl ?? this.documentFileUrl,
      clientId: clientId ?? this.clientId,
      hasExtraData: hasExtraData ?? this.hasExtraData,
    );
  }

  @override
  List<Object> get props => [servceUserDocuments, servceUserDocumentStatusUI,
     loadedServceUserDocument, editMode, deleteStatus, formStatus, generalNotificationKey, 
documentName,documentNumber,documentStatus,note,issuedDate,expiryDate,uploadedDate,documentFileUrl,createdDate,lastUpdatedDate,clientId,hasExtraData,];

  @override
  bool get stringify => true;
}
