part of 'client_bloc.dart';

enum ClientStatusUI {init, loading, error, done}
enum ClientDeleteStatus {ok, ko, none}

class ClientState extends Equatable {
  final List<Client> clients;
  final Client loadedClient;
  final bool editMode;
  final ClientDeleteStatus deleteStatus;
  final ClientStatusUI clientStatusUI;

  final FormzStatus formStatus;
  final String generalNotificationKey;

  final ClientNameInput clientName;
  final ClientDescriptionInput clientDescription;
  final ClientLogoUrlInput clientLogoUrl;
  final ClientContactNameInput clientContactName;
  final ClientPhoneInput clientPhone;
  final ClientEmailInput clientEmail;
  final CreatedDateInput createdDate;
  final EnabledInput enabled;
  final ReasonInput reason;
  final LastUpdatedDateInput lastUpdatedDate;
  final HasExtraDataInput hasExtraData;

  
  ClientState(
CreatedDateInput createdDate,LastUpdatedDateInput lastUpdatedDate,{
    this.clients = const [],
    this.clientStatusUI = ClientStatusUI.init,
    this.loadedClient = const Client(0,'','','','','','',null,false,'',null,false,),
    this.editMode = false,
    this.formStatus = FormzStatus.pure,
    this.generalNotificationKey = '',
    this.deleteStatus = ClientDeleteStatus.none,
    this.clientName = const ClientNameInput.pure(),
    this.clientDescription = const ClientDescriptionInput.pure(),
    this.clientLogoUrl = const ClientLogoUrlInput.pure(),
    this.clientContactName = const ClientContactNameInput.pure(),
    this.clientPhone = const ClientPhoneInput.pure(),
    this.clientEmail = const ClientEmailInput.pure(),
    this.enabled = const EnabledInput.pure(),
    this.reason = const ReasonInput.pure(),
    this.hasExtraData = const HasExtraDataInput.pure(),
  }):this.createdDate = createdDate ?? CreatedDateInput.pure(),
this.lastUpdatedDate = lastUpdatedDate ?? LastUpdatedDateInput.pure()
;

  ClientState copyWith({
    List<Client> clients,
    ClientStatusUI clientStatusUI,
    bool editMode,
    ClientDeleteStatus deleteStatus,
    Client loadedClient,
    FormzStatus formStatus,
    String generalNotificationKey,
    ClientNameInput clientName,
    ClientDescriptionInput clientDescription,
    ClientLogoUrlInput clientLogoUrl,
    ClientContactNameInput clientContactName,
    ClientPhoneInput clientPhone,
    ClientEmailInput clientEmail,
    CreatedDateInput createdDate,
    EnabledInput enabled,
    ReasonInput reason,
    LastUpdatedDateInput lastUpdatedDate,
    HasExtraDataInput hasExtraData,
  }) {
    return ClientState(
        createdDate,
        lastUpdatedDate,
      clients: clients ?? this.clients,
      clientStatusUI: clientStatusUI ?? this.clientStatusUI,
      loadedClient: loadedClient ?? this.loadedClient,
      editMode: editMode ?? this.editMode,
      formStatus: formStatus ?? this.formStatus,
      generalNotificationKey: generalNotificationKey ?? this.generalNotificationKey,
      deleteStatus: deleteStatus ?? this.deleteStatus,
      clientName: clientName ?? this.clientName,
      clientDescription: clientDescription ?? this.clientDescription,
      clientLogoUrl: clientLogoUrl ?? this.clientLogoUrl,
      clientContactName: clientContactName ?? this.clientContactName,
      clientPhone: clientPhone ?? this.clientPhone,
      clientEmail: clientEmail ?? this.clientEmail,
      enabled: enabled ?? this.enabled,
      reason: reason ?? this.reason,
      hasExtraData: hasExtraData ?? this.hasExtraData,
    );
  }

  @override
  List<Object> get props => [clients, clientStatusUI,
     loadedClient, editMode, deleteStatus, formStatus, generalNotificationKey, 
clientName,clientDescription,clientLogoUrl,clientContactName,clientPhone,clientEmail,createdDate,enabled,reason,lastUpdatedDate,hasExtraData,];

  @override
  bool get stringify => true;
}
