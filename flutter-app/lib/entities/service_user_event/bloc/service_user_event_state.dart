part of 'service_user_event_bloc.dart';

enum ServiceUserEventStatusUI {init, loading, error, done}
enum ServiceUserEventDeleteStatus {ok, ko, none}

class ServiceUserEventState extends Equatable {
  final List<ServiceUserEvent> serviceUserEvents;
  final ServiceUserEvent loadedServiceUserEvent;
  final bool editMode;
  final ServiceUserEventDeleteStatus deleteStatus;
  final ServiceUserEventStatusUI serviceUserEventStatusUI;

  final FormzStatus formStatus;
  final String generalNotificationKey;

  final EventTitleInput eventTitle;
  final DescriptionInput description;
  final ServiceUserEventStatusInput serviceUserEventStatus;
  final ServiceUserEventTypeInput serviceUserEventType;
  final PriorityInput priority;
  final NoteInput note;
  final DateOfEventInput dateOfEvent;
  final CreatedDateInput createdDate;
  final LastUpdatedDateInput lastUpdatedDate;
  final ClientIdInput clientId;
  final HasExtraDataInput hasExtraData;

  
  ServiceUserEventState(
DateOfEventInput dateOfEvent,CreatedDateInput createdDate,LastUpdatedDateInput lastUpdatedDate,{
    this.serviceUserEvents = const [],
    this.serviceUserEventStatusUI = ServiceUserEventStatusUI.init,
    this.loadedServiceUserEvent = const ServiceUserEvent(0,'','',null,null,null,'',null,null,null,0,false,null,null,null,),
    this.editMode = false,
    this.formStatus = FormzStatus.pure,
    this.generalNotificationKey = '',
    this.deleteStatus = ServiceUserEventDeleteStatus.none,
    this.eventTitle = const EventTitleInput.pure(),
    this.description = const DescriptionInput.pure(),
    this.serviceUserEventStatus = const ServiceUserEventStatusInput.pure(),
    this.serviceUserEventType = const ServiceUserEventTypeInput.pure(),
    this.priority = const PriorityInput.pure(),
    this.note = const NoteInput.pure(),
    this.clientId = const ClientIdInput.pure(),
    this.hasExtraData = const HasExtraDataInput.pure(),
  }):this.dateOfEvent = dateOfEvent ?? DateOfEventInput.pure(),
this.createdDate = createdDate ?? CreatedDateInput.pure(),
this.lastUpdatedDate = lastUpdatedDate ?? LastUpdatedDateInput.pure()
;

  ServiceUserEventState copyWith({
    List<ServiceUserEvent> serviceUserEvents,
    ServiceUserEventStatusUI serviceUserEventStatusUI,
    bool editMode,
    ServiceUserEventDeleteStatus deleteStatus,
    ServiceUserEvent loadedServiceUserEvent,
    FormzStatus formStatus,
    String generalNotificationKey,
    EventTitleInput eventTitle,
    DescriptionInput description,
    ServiceUserEventStatusInput serviceUserEventStatus,
    ServiceUserEventTypeInput serviceUserEventType,
    PriorityInput priority,
    NoteInput note,
    DateOfEventInput dateOfEvent,
    CreatedDateInput createdDate,
    LastUpdatedDateInput lastUpdatedDate,
    ClientIdInput clientId,
    HasExtraDataInput hasExtraData,
  }) {
    return ServiceUserEventState(
        dateOfEvent,
        createdDate,
        lastUpdatedDate,
      serviceUserEvents: serviceUserEvents ?? this.serviceUserEvents,
      serviceUserEventStatusUI: serviceUserEventStatusUI ?? this.serviceUserEventStatusUI,
      loadedServiceUserEvent: loadedServiceUserEvent ?? this.loadedServiceUserEvent,
      editMode: editMode ?? this.editMode,
      formStatus: formStatus ?? this.formStatus,
      generalNotificationKey: generalNotificationKey ?? this.generalNotificationKey,
      deleteStatus: deleteStatus ?? this.deleteStatus,
      eventTitle: eventTitle ?? this.eventTitle,
      description: description ?? this.description,
      serviceUserEventStatus: serviceUserEventStatus ?? this.serviceUserEventStatus,
      serviceUserEventType: serviceUserEventType ?? this.serviceUserEventType,
      priority: priority ?? this.priority,
      note: note ?? this.note,
      clientId: clientId ?? this.clientId,
      hasExtraData: hasExtraData ?? this.hasExtraData,
    );
  }

  @override
  List<Object> get props => [serviceUserEvents, serviceUserEventStatusUI,
     loadedServiceUserEvent, editMode, deleteStatus, formStatus, generalNotificationKey, 
eventTitle,description,serviceUserEventStatus,serviceUserEventType,priority,note,dateOfEvent,createdDate,lastUpdatedDate,clientId,hasExtraData,];

  @override
  bool get stringify => true;
}
