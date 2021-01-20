import 'dart:async';
import 'package:bloc/bloc.dart';
import 'package:flutter/foundation.dart';
import 'package:equatable/equatable.dart';
import 'package:flutter/widgets.dart';
import 'package:formz/formz.dart';

import 'package:amcCarePlanner/entities/service_user_event/service_user_event_model.dart';
import 'package:amcCarePlanner/entities/service_user_event/service_user_event_repository.dart';
import 'package:amcCarePlanner/entities/service_user_event/bloc/service_user_event_form_model.dart';
import 'package:amcCarePlanner/generated/l10n.dart';
import 'package:amcCarePlanner/shared/repository/http_utils.dart';
import 'package:intl/intl.dart';

part 'service_user_event_events.dart';
part 'service_user_event_state.dart';

class ServiceUserEventBloc extends Bloc<ServiceUserEventEvent, ServiceUserEventState> {
  final ServiceUserEventRepository _serviceUserEventRepository;

  final eventTitleController = TextEditingController();
  final descriptionController = TextEditingController();
  final noteController = TextEditingController();
  final dateOfEventController = TextEditingController();
  final createdDateController = TextEditingController();
  final lastUpdatedDateController = TextEditingController();
  final clientIdController = TextEditingController();

  ServiceUserEventBloc({@required ServiceUserEventRepository serviceUserEventRepository}) : assert(serviceUserEventRepository != null),
        _serviceUserEventRepository = serviceUserEventRepository, 
  super(ServiceUserEventState(null,null,null,));

  @override
  void onTransition(Transition<ServiceUserEventEvent, ServiceUserEventState> transition) {
    super.onTransition(transition);
  }

  @override
  Stream<ServiceUserEventState> mapEventToState(ServiceUserEventEvent event) async* {
    if (event is InitServiceUserEventList) {
      yield* onInitList(event);
    } else if (event is ServiceUserEventFormSubmitted) {
      yield* onSubmit();
    } else if (event is LoadServiceUserEventByIdForEdit) {
      yield* onLoadServiceUserEventIdForEdit(event);
    } else if (event is DeleteServiceUserEventById) {
      yield* onDeleteServiceUserEventId(event);
    } else if (event is LoadServiceUserEventByIdForView) {
      yield* onLoadServiceUserEventIdForView(event);
    }else if (event is EventTitleChanged){
      yield* onEventTitleChange(event);
    }else if (event is DescriptionChanged){
      yield* onDescriptionChange(event);
    }else if (event is ServiceUserEventStatusChanged){
      yield* onServiceUserEventStatusChange(event);
    }else if (event is ServiceUserEventTypeChanged){
      yield* onServiceUserEventTypeChange(event);
    }else if (event is PriorityChanged){
      yield* onPriorityChange(event);
    }else if (event is NoteChanged){
      yield* onNoteChange(event);
    }else if (event is DateOfEventChanged){
      yield* onDateOfEventChange(event);
    }else if (event is CreatedDateChanged){
      yield* onCreatedDateChange(event);
    }else if (event is LastUpdatedDateChanged){
      yield* onLastUpdatedDateChange(event);
    }else if (event is ClientIdChanged){
      yield* onClientIdChange(event);
    }else if (event is HasExtraDataChanged){
      yield* onHasExtraDataChange(event);
    }  }

  Stream<ServiceUserEventState> onInitList(InitServiceUserEventList event) async* {
    yield this.state.copyWith(serviceUserEventStatusUI: ServiceUserEventStatusUI.loading);
    List<ServiceUserEvent> serviceUserEvents = await _serviceUserEventRepository.getAllServiceUserEvents();
    yield this.state.copyWith(serviceUserEvents: serviceUserEvents, serviceUserEventStatusUI: ServiceUserEventStatusUI.done);
  }

  Stream<ServiceUserEventState> onSubmit() async* {
    if (this.state.formStatus.isValidated) {
      yield this.state.copyWith(formStatus: FormzStatus.submissionInProgress);
      try {
        ServiceUserEvent result;
        if(this.state.editMode) {
          ServiceUserEvent newServiceUserEvent = ServiceUserEvent(state.loadedServiceUserEvent.id,
            this.state.eventTitle.value,  
            this.state.description.value,  
            this.state.serviceUserEventStatus.value,  
            this.state.serviceUserEventType.value,  
            this.state.priority.value,  
            this.state.note.value,  
            this.state.dateOfEvent.value,  
            this.state.createdDate.value,  
            this.state.lastUpdatedDate.value,  
            this.state.clientId.value,  
            this.state.hasExtraData.value,  
            null, 
            null, 
            null, 
          );

          result = await _serviceUserEventRepository.update(newServiceUserEvent);
        } else {
          ServiceUserEvent newServiceUserEvent = ServiceUserEvent(null,
            this.state.eventTitle.value,  
            this.state.description.value,  
            this.state.serviceUserEventStatus.value,  
            this.state.serviceUserEventType.value,  
            this.state.priority.value,  
            this.state.note.value,  
            this.state.dateOfEvent.value,  
            this.state.createdDate.value,  
            this.state.lastUpdatedDate.value,  
            this.state.clientId.value,  
            this.state.hasExtraData.value,  
            null, 
            null, 
            null, 
          );

          result = await _serviceUserEventRepository.create(newServiceUserEvent);
        }

        if (result == null) {
          yield this.state.copyWith(formStatus: FormzStatus.submissionFailure,
              generalNotificationKey: HttpUtils.badRequestServerKey);
        } else {
          yield this.state.copyWith(formStatus: FormzStatus.submissionSuccess,
              generalNotificationKey: HttpUtils.successResult);
        }
      } catch (e) {
        yield this.state.copyWith(formStatus: FormzStatus.submissionFailure,
            generalNotificationKey: HttpUtils.errorServerKey);
      }
    }
  }

  Stream<ServiceUserEventState> onLoadServiceUserEventIdForEdit(LoadServiceUserEventByIdForEdit event) async* {
    yield this.state.copyWith(serviceUserEventStatusUI: ServiceUserEventStatusUI.loading);
    ServiceUserEvent loadedServiceUserEvent = await _serviceUserEventRepository.getServiceUserEvent(event.id);

    final eventTitle = EventTitleInput.dirty(loadedServiceUserEvent?.eventTitle != null ? loadedServiceUserEvent.eventTitle: '');
    final description = DescriptionInput.dirty(loadedServiceUserEvent?.description != null ? loadedServiceUserEvent.description: '');
    final serviceUserEventStatus = ServiceUserEventStatusInput.dirty(loadedServiceUserEvent?.serviceUserEventStatus != null ? loadedServiceUserEvent.serviceUserEventStatus: null);
    final serviceUserEventType = ServiceUserEventTypeInput.dirty(loadedServiceUserEvent?.serviceUserEventType != null ? loadedServiceUserEvent.serviceUserEventType: null);
    final priority = PriorityInput.dirty(loadedServiceUserEvent?.priority != null ? loadedServiceUserEvent.priority: null);
    final note = NoteInput.dirty(loadedServiceUserEvent?.note != null ? loadedServiceUserEvent.note: '');
    final dateOfEvent = DateOfEventInput.dirty(loadedServiceUserEvent?.dateOfEvent != null ? loadedServiceUserEvent.dateOfEvent: null);
    final createdDate = CreatedDateInput.dirty(loadedServiceUserEvent?.createdDate != null ? loadedServiceUserEvent.createdDate: null);
    final lastUpdatedDate = LastUpdatedDateInput.dirty(loadedServiceUserEvent?.lastUpdatedDate != null ? loadedServiceUserEvent.lastUpdatedDate: null);
    final clientId = ClientIdInput.dirty(loadedServiceUserEvent?.clientId != null ? loadedServiceUserEvent.clientId: 0);
    final hasExtraData = HasExtraDataInput.dirty(loadedServiceUserEvent?.hasExtraData != null ? loadedServiceUserEvent.hasExtraData: false);

    yield this.state.copyWith(loadedServiceUserEvent: loadedServiceUserEvent, editMode: true,
      eventTitle: eventTitle,
      description: description,
      serviceUserEventStatus: serviceUserEventStatus,
      serviceUserEventType: serviceUserEventType,
      priority: priority,
      note: note,
      dateOfEvent: dateOfEvent,
      createdDate: createdDate,
      lastUpdatedDate: lastUpdatedDate,
      clientId: clientId,
      hasExtraData: hasExtraData,
    serviceUserEventStatusUI: ServiceUserEventStatusUI.done);

    eventTitleController.text = loadedServiceUserEvent.eventTitle;
    descriptionController.text = loadedServiceUserEvent.description;
    noteController.text = loadedServiceUserEvent.note;
    dateOfEventController.text = DateFormat.yMMMMd(S.current.locale).format(loadedServiceUserEvent?.dateOfEvent);
    createdDateController.text = DateFormat.yMMMMd(S.current.locale).format(loadedServiceUserEvent?.createdDate);
    lastUpdatedDateController.text = DateFormat.yMMMMd(S.current.locale).format(loadedServiceUserEvent?.lastUpdatedDate);
    clientIdController.text = loadedServiceUserEvent.clientId.toString();
  }

  Stream<ServiceUserEventState> onDeleteServiceUserEventId(DeleteServiceUserEventById event) async* {
    try {
      await _serviceUserEventRepository.delete(event.id);
      this.add(InitServiceUserEventList());
      yield this.state.copyWith(deleteStatus: ServiceUserEventDeleteStatus.ok);
    } catch (e) {
      yield this.state.copyWith(deleteStatus: ServiceUserEventDeleteStatus.ko,
          generalNotificationKey: HttpUtils.errorServerKey);
    }
    yield this.state.copyWith(deleteStatus: ServiceUserEventDeleteStatus.none);
  }

  Stream<ServiceUserEventState> onLoadServiceUserEventIdForView(LoadServiceUserEventByIdForView event) async* {
    yield this.state.copyWith(serviceUserEventStatusUI: ServiceUserEventStatusUI.loading);
    try {
      ServiceUserEvent loadedServiceUserEvent = await _serviceUserEventRepository.getServiceUserEvent(event.id);
      yield this.state.copyWith(loadedServiceUserEvent: loadedServiceUserEvent, serviceUserEventStatusUI: ServiceUserEventStatusUI.done);
    } catch(e) {
      yield this.state.copyWith(loadedServiceUserEvent: null, serviceUserEventStatusUI: ServiceUserEventStatusUI.error);
    }
  }


  Stream<ServiceUserEventState> onEventTitleChange(EventTitleChanged event) async* {
    final eventTitle = EventTitleInput.dirty(event.eventTitle);
    yield this.state.copyWith(
      eventTitle: eventTitle,
      formStatus: Formz.validate([
        eventTitle,
      this.state.description,
      this.state.serviceUserEventStatus,
      this.state.serviceUserEventType,
      this.state.priority,
      this.state.note,
      this.state.dateOfEvent,
      this.state.createdDate,
      this.state.lastUpdatedDate,
      this.state.clientId,
      this.state.hasExtraData,
      ]),
    );
  }
  Stream<ServiceUserEventState> onDescriptionChange(DescriptionChanged event) async* {
    final description = DescriptionInput.dirty(event.description);
    yield this.state.copyWith(
      description: description,
      formStatus: Formz.validate([
      this.state.eventTitle,
        description,
      this.state.serviceUserEventStatus,
      this.state.serviceUserEventType,
      this.state.priority,
      this.state.note,
      this.state.dateOfEvent,
      this.state.createdDate,
      this.state.lastUpdatedDate,
      this.state.clientId,
      this.state.hasExtraData,
      ]),
    );
  }
  Stream<ServiceUserEventState> onServiceUserEventStatusChange(ServiceUserEventStatusChanged event) async* {
    final serviceUserEventStatus = ServiceUserEventStatusInput.dirty(event.serviceUserEventStatus);
    yield this.state.copyWith(
      serviceUserEventStatus: serviceUserEventStatus,
      formStatus: Formz.validate([
      this.state.eventTitle,
      this.state.description,
        serviceUserEventStatus,
      this.state.serviceUserEventType,
      this.state.priority,
      this.state.note,
      this.state.dateOfEvent,
      this.state.createdDate,
      this.state.lastUpdatedDate,
      this.state.clientId,
      this.state.hasExtraData,
      ]),
    );
  }
  Stream<ServiceUserEventState> onServiceUserEventTypeChange(ServiceUserEventTypeChanged event) async* {
    final serviceUserEventType = ServiceUserEventTypeInput.dirty(event.serviceUserEventType);
    yield this.state.copyWith(
      serviceUserEventType: serviceUserEventType,
      formStatus: Formz.validate([
      this.state.eventTitle,
      this.state.description,
      this.state.serviceUserEventStatus,
        serviceUserEventType,
      this.state.priority,
      this.state.note,
      this.state.dateOfEvent,
      this.state.createdDate,
      this.state.lastUpdatedDate,
      this.state.clientId,
      this.state.hasExtraData,
      ]),
    );
  }
  Stream<ServiceUserEventState> onPriorityChange(PriorityChanged event) async* {
    final priority = PriorityInput.dirty(event.priority);
    yield this.state.copyWith(
      priority: priority,
      formStatus: Formz.validate([
      this.state.eventTitle,
      this.state.description,
      this.state.serviceUserEventStatus,
      this.state.serviceUserEventType,
        priority,
      this.state.note,
      this.state.dateOfEvent,
      this.state.createdDate,
      this.state.lastUpdatedDate,
      this.state.clientId,
      this.state.hasExtraData,
      ]),
    );
  }
  Stream<ServiceUserEventState> onNoteChange(NoteChanged event) async* {
    final note = NoteInput.dirty(event.note);
    yield this.state.copyWith(
      note: note,
      formStatus: Formz.validate([
      this.state.eventTitle,
      this.state.description,
      this.state.serviceUserEventStatus,
      this.state.serviceUserEventType,
      this.state.priority,
        note,
      this.state.dateOfEvent,
      this.state.createdDate,
      this.state.lastUpdatedDate,
      this.state.clientId,
      this.state.hasExtraData,
      ]),
    );
  }
  Stream<ServiceUserEventState> onDateOfEventChange(DateOfEventChanged event) async* {
    final dateOfEvent = DateOfEventInput.dirty(event.dateOfEvent);
    yield this.state.copyWith(
      dateOfEvent: dateOfEvent,
      formStatus: Formz.validate([
      this.state.eventTitle,
      this.state.description,
      this.state.serviceUserEventStatus,
      this.state.serviceUserEventType,
      this.state.priority,
      this.state.note,
        dateOfEvent,
      this.state.createdDate,
      this.state.lastUpdatedDate,
      this.state.clientId,
      this.state.hasExtraData,
      ]),
    );
  }
  Stream<ServiceUserEventState> onCreatedDateChange(CreatedDateChanged event) async* {
    final createdDate = CreatedDateInput.dirty(event.createdDate);
    yield this.state.copyWith(
      createdDate: createdDate,
      formStatus: Formz.validate([
      this.state.eventTitle,
      this.state.description,
      this.state.serviceUserEventStatus,
      this.state.serviceUserEventType,
      this.state.priority,
      this.state.note,
      this.state.dateOfEvent,
        createdDate,
      this.state.lastUpdatedDate,
      this.state.clientId,
      this.state.hasExtraData,
      ]),
    );
  }
  Stream<ServiceUserEventState> onLastUpdatedDateChange(LastUpdatedDateChanged event) async* {
    final lastUpdatedDate = LastUpdatedDateInput.dirty(event.lastUpdatedDate);
    yield this.state.copyWith(
      lastUpdatedDate: lastUpdatedDate,
      formStatus: Formz.validate([
      this.state.eventTitle,
      this.state.description,
      this.state.serviceUserEventStatus,
      this.state.serviceUserEventType,
      this.state.priority,
      this.state.note,
      this.state.dateOfEvent,
      this.state.createdDate,
        lastUpdatedDate,
      this.state.clientId,
      this.state.hasExtraData,
      ]),
    );
  }
  Stream<ServiceUserEventState> onClientIdChange(ClientIdChanged event) async* {
    final clientId = ClientIdInput.dirty(event.clientId);
    yield this.state.copyWith(
      clientId: clientId,
      formStatus: Formz.validate([
      this.state.eventTitle,
      this.state.description,
      this.state.serviceUserEventStatus,
      this.state.serviceUserEventType,
      this.state.priority,
      this.state.note,
      this.state.dateOfEvent,
      this.state.createdDate,
      this.state.lastUpdatedDate,
        clientId,
      this.state.hasExtraData,
      ]),
    );
  }
  Stream<ServiceUserEventState> onHasExtraDataChange(HasExtraDataChanged event) async* {
    final hasExtraData = HasExtraDataInput.dirty(event.hasExtraData);
    yield this.state.copyWith(
      hasExtraData: hasExtraData,
      formStatus: Formz.validate([
      this.state.eventTitle,
      this.state.description,
      this.state.serviceUserEventStatus,
      this.state.serviceUserEventType,
      this.state.priority,
      this.state.note,
      this.state.dateOfEvent,
      this.state.createdDate,
      this.state.lastUpdatedDate,
      this.state.clientId,
        hasExtraData,
      ]),
    );
  }

  @override
  Future<void> close() {
    eventTitleController.dispose();
    descriptionController.dispose();
    noteController.dispose();
    dateOfEventController.dispose();
    createdDateController.dispose();
    lastUpdatedDateController.dispose();
    clientIdController.dispose();
    return super.close();
  }

}