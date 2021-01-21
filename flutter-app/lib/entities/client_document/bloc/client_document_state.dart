part of 'client_document_bloc.dart';

enum ClientDocumentStatusUI {init, loading, error, done}
enum ClientDocumentDeleteStatus {ok, ko, none}

class ClientDocumentState extends Equatable {
  final List<ClientDocument> clientDocuments;
  final ClientDocument loadedClientDocument;
  final bool editMode;
  final ClientDocumentDeleteStatus deleteStatus;
  final ClientDocumentStatusUI clientDocumentStatusUI;

  final FormzStatus formStatus;
  final String generalNotificationKey;

  final DocumentNameInput documentName;
  final DocumentNumberInput documentNumber;
  final DocumentTypeInput documentType;
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

  
  ClientDocumentState(
IssuedDateInput issuedDate,ExpiryDateInput expiryDate,UploadedDateInput uploadedDate,CreatedDateInput createdDate,LastUpdatedDateInput lastUpdatedDate,{
    this.clientDocuments = const [],
    this.clientDocumentStatusUI = ClientDocumentStatusUI.init,
    this.loadedClientDocument = const ClientDocument(0,'','',null,null,'',null,null,null,'',null,null,0,false,null,null,),
    this.editMode = false,
    this.formStatus = FormzStatus.pure,
    this.generalNotificationKey = '',
    this.deleteStatus = ClientDocumentDeleteStatus.none,
    this.documentName = const DocumentNameInput.pure(),
    this.documentNumber = const DocumentNumberInput.pure(),
    this.documentType = const DocumentTypeInput.pure(),
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

  ClientDocumentState copyWith({
    List<ClientDocument> clientDocuments,
    ClientDocumentStatusUI clientDocumentStatusUI,
    bool editMode,
    ClientDocumentDeleteStatus deleteStatus,
    ClientDocument loadedClientDocument,
    FormzStatus formStatus,
    String generalNotificationKey,
    DocumentNameInput documentName,
    DocumentNumberInput documentNumber,
    DocumentTypeInput documentType,
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
    return ClientDocumentState(
        issuedDate,
        expiryDate,
        uploadedDate,
        createdDate,
        lastUpdatedDate,
      clientDocuments: clientDocuments ?? this.clientDocuments,
      clientDocumentStatusUI: clientDocumentStatusUI ?? this.clientDocumentStatusUI,
      loadedClientDocument: loadedClientDocument ?? this.loadedClientDocument,
      editMode: editMode ?? this.editMode,
      formStatus: formStatus ?? this.formStatus,
      generalNotificationKey: generalNotificationKey ?? this.generalNotificationKey,
      deleteStatus: deleteStatus ?? this.deleteStatus,
      documentName: documentName ?? this.documentName,
      documentNumber: documentNumber ?? this.documentNumber,
      documentType: documentType ?? this.documentType,
      documentStatus: documentStatus ?? this.documentStatus,
      note: note ?? this.note,
      documentFileUrl: documentFileUrl ?? this.documentFileUrl,
      clientId: clientId ?? this.clientId,
      hasExtraData: hasExtraData ?? this.hasExtraData,
    );
  }

  @override
  List<Object> get props => [clientDocuments, clientDocumentStatusUI,
     loadedClientDocument, editMode, deleteStatus, formStatus, generalNotificationKey, 
documentName,documentNumber,documentType,documentStatus,note,issuedDate,expiryDate,uploadedDate,documentFileUrl,createdDate,lastUpdatedDate,clientId,hasExtraData,];

  @override
  bool get stringify => true;
}
