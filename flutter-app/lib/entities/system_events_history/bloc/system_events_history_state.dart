part of 'system_events_history_bloc.dart';

enum SystemEventsHistoryStatusUI {init, loading, error, done}
enum SystemEventsHistoryDeleteStatus {ok, ko, none}

class SystemEventsHistoryState extends Equatable {
  final List<SystemEventsHistory> systemEventsHistories;
  final SystemEventsHistory loadedSystemEventsHistory;
  final bool editMode;
  final SystemEventsHistoryDeleteStatus deleteStatus;
  final SystemEventsHistoryStatusUI systemEventsHistoryStatusUI;

  final FormzStatus formStatus;
  final String generalNotificationKey;

  final EventNameInput eventName;
  final EventDateInput eventDate;
  final EventApiInput eventApi;
  final IpAddressInput ipAddress;
  final EventNoteInput eventNote;
  final EventEntityNameInput eventEntityName;
  final EventEntityIdInput eventEntityId;
  final IsSuspeciousInput isSuspecious;
  final CallerEmailInput callerEmail;
  final CallerIdInput callerId;
  final ClientIdInput clientId;

  
  SystemEventsHistoryState(
EventDateInput eventDate,{
    this.systemEventsHistories = const [],
    this.systemEventsHistoryStatusUI = SystemEventsHistoryStatusUI.init,
    this.loadedSystemEventsHistory = const SystemEventsHistory(0,'',null,'','','','',0,false,'',0,0,null,),
    this.editMode = false,
    this.formStatus = FormzStatus.pure,
    this.generalNotificationKey = '',
    this.deleteStatus = SystemEventsHistoryDeleteStatus.none,
    this.eventName = const EventNameInput.pure(),
    this.eventApi = const EventApiInput.pure(),
    this.ipAddress = const IpAddressInput.pure(),
    this.eventNote = const EventNoteInput.pure(),
    this.eventEntityName = const EventEntityNameInput.pure(),
    this.eventEntityId = const EventEntityIdInput.pure(),
    this.isSuspecious = const IsSuspeciousInput.pure(),
    this.callerEmail = const CallerEmailInput.pure(),
    this.callerId = const CallerIdInput.pure(),
    this.clientId = const ClientIdInput.pure(),
  }):this.eventDate = eventDate ?? EventDateInput.pure()
;

  SystemEventsHistoryState copyWith({
    List<SystemEventsHistory> systemEventsHistories,
    SystemEventsHistoryStatusUI systemEventsHistoryStatusUI,
    bool editMode,
    SystemEventsHistoryDeleteStatus deleteStatus,
    SystemEventsHistory loadedSystemEventsHistory,
    FormzStatus formStatus,
    String generalNotificationKey,
    EventNameInput eventName,
    EventDateInput eventDate,
    EventApiInput eventApi,
    IpAddressInput ipAddress,
    EventNoteInput eventNote,
    EventEntityNameInput eventEntityName,
    EventEntityIdInput eventEntityId,
    IsSuspeciousInput isSuspecious,
    CallerEmailInput callerEmail,
    CallerIdInput callerId,
    ClientIdInput clientId,
  }) {
    return SystemEventsHistoryState(
        eventDate,
      systemEventsHistories: systemEventsHistories ?? this.systemEventsHistories,
      systemEventsHistoryStatusUI: systemEventsHistoryStatusUI ?? this.systemEventsHistoryStatusUI,
      loadedSystemEventsHistory: loadedSystemEventsHistory ?? this.loadedSystemEventsHistory,
      editMode: editMode ?? this.editMode,
      formStatus: formStatus ?? this.formStatus,
      generalNotificationKey: generalNotificationKey ?? this.generalNotificationKey,
      deleteStatus: deleteStatus ?? this.deleteStatus,
      eventName: eventName ?? this.eventName,
      eventApi: eventApi ?? this.eventApi,
      ipAddress: ipAddress ?? this.ipAddress,
      eventNote: eventNote ?? this.eventNote,
      eventEntityName: eventEntityName ?? this.eventEntityName,
      eventEntityId: eventEntityId ?? this.eventEntityId,
      isSuspecious: isSuspecious ?? this.isSuspecious,
      callerEmail: callerEmail ?? this.callerEmail,
      callerId: callerId ?? this.callerId,
      clientId: clientId ?? this.clientId,
    );
  }

  @override
  List<Object> get props => [systemEventsHistories, systemEventsHistoryStatusUI,
     loadedSystemEventsHistory, editMode, deleteStatus, formStatus, generalNotificationKey, 
eventName,eventDate,eventApi,ipAddress,eventNote,eventEntityName,eventEntityId,isSuspecious,callerEmail,callerId,clientId,];

  @override
  bool get stringify => true;
}
