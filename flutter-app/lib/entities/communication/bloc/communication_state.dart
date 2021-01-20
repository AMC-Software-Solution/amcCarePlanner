part of 'communication_bloc.dart';

enum CommunicationStatusUI {init, loading, error, done}
enum CommunicationDeleteStatus {ok, ko, none}

class CommunicationState extends Equatable {
  final List<Communication> communications;
  final Communication loadedCommunication;
  final bool editMode;
  final CommunicationDeleteStatus deleteStatus;
  final CommunicationStatusUI communicationStatusUI;

  final FormzStatus formStatus;
  final String generalNotificationKey;

  final CommunicationTypeInput communicationType;
  final NoteInput note;
  final CommunicationDateInput communicationDate;
  final AttachmentUrlInput attachmentUrl;
  final CreatedDateInput createdDate;
  final LastUpdatedDateInput lastUpdatedDate;
  final ClientIdInput clientId;
  final HasExtraDataInput hasExtraData;

  
  CommunicationState(
CommunicationDateInput communicationDate,CreatedDateInput createdDate,LastUpdatedDateInput lastUpdatedDate,{
    this.communications = const [],
    this.communicationStatusUI = CommunicationStatusUI.init,
    this.loadedCommunication = const Communication(0,null,'',null,'',null,null,0,false,null,null,),
    this.editMode = false,
    this.formStatus = FormzStatus.pure,
    this.generalNotificationKey = '',
    this.deleteStatus = CommunicationDeleteStatus.none,
    this.communicationType = const CommunicationTypeInput.pure(),
    this.note = const NoteInput.pure(),
    this.attachmentUrl = const AttachmentUrlInput.pure(),
    this.clientId = const ClientIdInput.pure(),
    this.hasExtraData = const HasExtraDataInput.pure(),
  }):this.communicationDate = communicationDate ?? CommunicationDateInput.pure(),
this.createdDate = createdDate ?? CreatedDateInput.pure(),
this.lastUpdatedDate = lastUpdatedDate ?? LastUpdatedDateInput.pure()
;

  CommunicationState copyWith({
    List<Communication> communications,
    CommunicationStatusUI communicationStatusUI,
    bool editMode,
    CommunicationDeleteStatus deleteStatus,
    Communication loadedCommunication,
    FormzStatus formStatus,
    String generalNotificationKey,
    CommunicationTypeInput communicationType,
    NoteInput note,
    CommunicationDateInput communicationDate,
    AttachmentUrlInput attachmentUrl,
    CreatedDateInput createdDate,
    LastUpdatedDateInput lastUpdatedDate,
    ClientIdInput clientId,
    HasExtraDataInput hasExtraData,
  }) {
    return CommunicationState(
        communicationDate,
        createdDate,
        lastUpdatedDate,
      communications: communications ?? this.communications,
      communicationStatusUI: communicationStatusUI ?? this.communicationStatusUI,
      loadedCommunication: loadedCommunication ?? this.loadedCommunication,
      editMode: editMode ?? this.editMode,
      formStatus: formStatus ?? this.formStatus,
      generalNotificationKey: generalNotificationKey ?? this.generalNotificationKey,
      deleteStatus: deleteStatus ?? this.deleteStatus,
      communicationType: communicationType ?? this.communicationType,
      note: note ?? this.note,
      attachmentUrl: attachmentUrl ?? this.attachmentUrl,
      clientId: clientId ?? this.clientId,
      hasExtraData: hasExtraData ?? this.hasExtraData,
    );
  }

  @override
  List<Object> get props => [communications, communicationStatusUI,
     loadedCommunication, editMode, deleteStatus, formStatus, generalNotificationKey, 
communicationType,note,communicationDate,attachmentUrl,createdDate,lastUpdatedDate,clientId,hasExtraData,];

  @override
  bool get stringify => true;
}
