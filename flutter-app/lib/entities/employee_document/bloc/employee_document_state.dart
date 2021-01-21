part of 'employee_document_bloc.dart';

enum EmployeeDocumentStatusUI {init, loading, error, done}
enum EmployeeDocumentDeleteStatus {ok, ko, none}

class EmployeeDocumentState extends Equatable {
  final List<EmployeeDocument> employeeDocuments;
  final EmployeeDocument loadedEmployeeDocument;
  final bool editMode;
  final EmployeeDocumentDeleteStatus deleteStatus;
  final EmployeeDocumentStatusUI employeeDocumentStatusUI;

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

  
  EmployeeDocumentState(
IssuedDateInput issuedDate,ExpiryDateInput expiryDate,UploadedDateInput uploadedDate,CreatedDateInput createdDate,LastUpdatedDateInput lastUpdatedDate,{
    this.employeeDocuments = const [],
    this.employeeDocumentStatusUI = EmployeeDocumentStatusUI.init,
    this.loadedEmployeeDocument = const EmployeeDocument(0,'','',null,'',null,null,null,'',null,null,0,false,null,null,null,),
    this.editMode = false,
    this.formStatus = FormzStatus.pure,
    this.generalNotificationKey = '',
    this.deleteStatus = EmployeeDocumentDeleteStatus.none,
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

  EmployeeDocumentState copyWith({
    List<EmployeeDocument> employeeDocuments,
    EmployeeDocumentStatusUI employeeDocumentStatusUI,
    bool editMode,
    EmployeeDocumentDeleteStatus deleteStatus,
    EmployeeDocument loadedEmployeeDocument,
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
    return EmployeeDocumentState(
        issuedDate,
        expiryDate,
        uploadedDate,
        createdDate,
        lastUpdatedDate,
      employeeDocuments: employeeDocuments ?? this.employeeDocuments,
      employeeDocumentStatusUI: employeeDocumentStatusUI ?? this.employeeDocumentStatusUI,
      loadedEmployeeDocument: loadedEmployeeDocument ?? this.loadedEmployeeDocument,
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
  List<Object> get props => [employeeDocuments, employeeDocumentStatusUI,
     loadedEmployeeDocument, editMode, deleteStatus, formStatus, generalNotificationKey, 
documentName,documentNumber,documentStatus,note,issuedDate,expiryDate,uploadedDate,documentFileUrl,createdDate,lastUpdatedDate,clientId,hasExtraData,];

  @override
  bool get stringify => true;
}
