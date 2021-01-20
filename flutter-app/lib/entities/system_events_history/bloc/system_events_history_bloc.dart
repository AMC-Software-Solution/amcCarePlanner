import 'dart:async';
import 'package:bloc/bloc.dart';
import 'package:flutter/foundation.dart';
import 'package:equatable/equatable.dart';
import 'package:flutter/widgets.dart';
import 'package:formz/formz.dart';

import 'package:amcCarePlanner/entities/system_events_history/system_events_history_model.dart';
import 'package:amcCarePlanner/entities/system_events_history/system_events_history_repository.dart';
import 'package:amcCarePlanner/entities/system_events_history/bloc/system_events_history_form_model.dart';
import 'package:amcCarePlanner/generated/l10n.dart';
import 'package:amcCarePlanner/shared/repository/http_utils.dart';
import 'package:intl/intl.dart';

part 'system_events_history_events.dart';
part 'system_events_history_state.dart';

class SystemEventsHistoryBloc extends Bloc<SystemEventsHistoryEvent, SystemEventsHistoryState> {
  final SystemEventsHistoryRepository _systemEventsHistoryRepository;

  final eventNameController = TextEditingController();
  final eventDateController = TextEditingController();
  final eventApiController = TextEditingController();
  final ipAddressController = TextEditingController();
  final eventNoteController = TextEditingController();
  final eventEntityNameController = TextEditingController();
  final eventEntityIdController = TextEditingController();
  final callerEmailController = TextEditingController();
  final callerIdController = TextEditingController();
  final clientIdController = TextEditingController();

  SystemEventsHistoryBloc({@required SystemEventsHistoryRepository systemEventsHistoryRepository}) : assert(systemEventsHistoryRepository != null),
        _systemEventsHistoryRepository = systemEventsHistoryRepository, 
  super(SystemEventsHistoryState(null,));

  @override
  void onTransition(Transition<SystemEventsHistoryEvent, SystemEventsHistoryState> transition) {
    super.onTransition(transition);
  }

  @override
  Stream<SystemEventsHistoryState> mapEventToState(SystemEventsHistoryEvent event) async* {
    if (event is InitSystemEventsHistoryList) {
      yield* onInitList(event);
    } else if (event is SystemEventsHistoryFormSubmitted) {
      yield* onSubmit();
    } else if (event is LoadSystemEventsHistoryByIdForEdit) {
      yield* onLoadSystemEventsHistoryIdForEdit(event);
    } else if (event is DeleteSystemEventsHistoryById) {
      yield* onDeleteSystemEventsHistoryId(event);
    } else if (event is LoadSystemEventsHistoryByIdForView) {
      yield* onLoadSystemEventsHistoryIdForView(event);
    }else if (event is EventNameChanged){
      yield* onEventNameChange(event);
    }else if (event is EventDateChanged){
      yield* onEventDateChange(event);
    }else if (event is EventApiChanged){
      yield* onEventApiChange(event);
    }else if (event is IpAddressChanged){
      yield* onIpAddressChange(event);
    }else if (event is EventNoteChanged){
      yield* onEventNoteChange(event);
    }else if (event is EventEntityNameChanged){
      yield* onEventEntityNameChange(event);
    }else if (event is EventEntityIdChanged){
      yield* onEventEntityIdChange(event);
    }else if (event is IsSuspeciousChanged){
      yield* onIsSuspeciousChange(event);
    }else if (event is CallerEmailChanged){
      yield* onCallerEmailChange(event);
    }else if (event is CallerIdChanged){
      yield* onCallerIdChange(event);
    }else if (event is ClientIdChanged){
      yield* onClientIdChange(event);
    }  }

  Stream<SystemEventsHistoryState> onInitList(InitSystemEventsHistoryList event) async* {
    yield this.state.copyWith(systemEventsHistoryStatusUI: SystemEventsHistoryStatusUI.loading);
    List<SystemEventsHistory> systemEventsHistories = await _systemEventsHistoryRepository.getAllSystemEventsHistories();
    yield this.state.copyWith(systemEventsHistories: systemEventsHistories, systemEventsHistoryStatusUI: SystemEventsHistoryStatusUI.done);
  }

  Stream<SystemEventsHistoryState> onSubmit() async* {
    if (this.state.formStatus.isValidated) {
      yield this.state.copyWith(formStatus: FormzStatus.submissionInProgress);
      try {
        SystemEventsHistory result;
        if(this.state.editMode) {
          SystemEventsHistory newSystemEventsHistory = SystemEventsHistory(state.loadedSystemEventsHistory.id,
            this.state.eventName.value,  
            this.state.eventDate.value,  
            this.state.eventApi.value,  
            this.state.ipAddress.value,  
            this.state.eventNote.value,  
            this.state.eventEntityName.value,  
            this.state.eventEntityId.value,  
            this.state.isSuspecious.value,  
            this.state.callerEmail.value,  
            this.state.callerId.value,  
            this.state.clientId.value,  
            null, 
          );

          result = await _systemEventsHistoryRepository.update(newSystemEventsHistory);
        } else {
          SystemEventsHistory newSystemEventsHistory = SystemEventsHistory(null,
            this.state.eventName.value,  
            this.state.eventDate.value,  
            this.state.eventApi.value,  
            this.state.ipAddress.value,  
            this.state.eventNote.value,  
            this.state.eventEntityName.value,  
            this.state.eventEntityId.value,  
            this.state.isSuspecious.value,  
            this.state.callerEmail.value,  
            this.state.callerId.value,  
            this.state.clientId.value,  
            null, 
          );

          result = await _systemEventsHistoryRepository.create(newSystemEventsHistory);
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

  Stream<SystemEventsHistoryState> onLoadSystemEventsHistoryIdForEdit(LoadSystemEventsHistoryByIdForEdit event) async* {
    yield this.state.copyWith(systemEventsHistoryStatusUI: SystemEventsHistoryStatusUI.loading);
    SystemEventsHistory loadedSystemEventsHistory = await _systemEventsHistoryRepository.getSystemEventsHistory(event.id);

    final eventName = EventNameInput.dirty(loadedSystemEventsHistory?.eventName != null ? loadedSystemEventsHistory.eventName: '');
    final eventDate = EventDateInput.dirty(loadedSystemEventsHistory?.eventDate != null ? loadedSystemEventsHistory.eventDate: null);
    final eventApi = EventApiInput.dirty(loadedSystemEventsHistory?.eventApi != null ? loadedSystemEventsHistory.eventApi: '');
    final ipAddress = IpAddressInput.dirty(loadedSystemEventsHistory?.ipAddress != null ? loadedSystemEventsHistory.ipAddress: '');
    final eventNote = EventNoteInput.dirty(loadedSystemEventsHistory?.eventNote != null ? loadedSystemEventsHistory.eventNote: '');
    final eventEntityName = EventEntityNameInput.dirty(loadedSystemEventsHistory?.eventEntityName != null ? loadedSystemEventsHistory.eventEntityName: '');
    final eventEntityId = EventEntityIdInput.dirty(loadedSystemEventsHistory?.eventEntityId != null ? loadedSystemEventsHistory.eventEntityId: 0);
    final isSuspecious = IsSuspeciousInput.dirty(loadedSystemEventsHistory?.isSuspecious != null ? loadedSystemEventsHistory.isSuspecious: false);
    final callerEmail = CallerEmailInput.dirty(loadedSystemEventsHistory?.callerEmail != null ? loadedSystemEventsHistory.callerEmail: '');
    final callerId = CallerIdInput.dirty(loadedSystemEventsHistory?.callerId != null ? loadedSystemEventsHistory.callerId: 0);
    final clientId = ClientIdInput.dirty(loadedSystemEventsHistory?.clientId != null ? loadedSystemEventsHistory.clientId: 0);

    yield this.state.copyWith(loadedSystemEventsHistory: loadedSystemEventsHistory, editMode: true,
      eventName: eventName,
      eventDate: eventDate,
      eventApi: eventApi,
      ipAddress: ipAddress,
      eventNote: eventNote,
      eventEntityName: eventEntityName,
      eventEntityId: eventEntityId,
      isSuspecious: isSuspecious,
      callerEmail: callerEmail,
      callerId: callerId,
      clientId: clientId,
    systemEventsHistoryStatusUI: SystemEventsHistoryStatusUI.done);

    eventNameController.text = loadedSystemEventsHistory.eventName;
    eventDateController.text = DateFormat.yMMMMd(S.current.locale).format(loadedSystemEventsHistory?.eventDate);
    eventApiController.text = loadedSystemEventsHistory.eventApi;
    ipAddressController.text = loadedSystemEventsHistory.ipAddress;
    eventNoteController.text = loadedSystemEventsHistory.eventNote;
    eventEntityNameController.text = loadedSystemEventsHistory.eventEntityName;
    eventEntityIdController.text = loadedSystemEventsHistory.eventEntityId.toString();
    callerEmailController.text = loadedSystemEventsHistory.callerEmail;
    callerIdController.text = loadedSystemEventsHistory.callerId.toString();
    clientIdController.text = loadedSystemEventsHistory.clientId.toString();
  }

  Stream<SystemEventsHistoryState> onDeleteSystemEventsHistoryId(DeleteSystemEventsHistoryById event) async* {
    try {
      await _systemEventsHistoryRepository.delete(event.id);
      this.add(InitSystemEventsHistoryList());
      yield this.state.copyWith(deleteStatus: SystemEventsHistoryDeleteStatus.ok);
    } catch (e) {
      yield this.state.copyWith(deleteStatus: SystemEventsHistoryDeleteStatus.ko,
          generalNotificationKey: HttpUtils.errorServerKey);
    }
    yield this.state.copyWith(deleteStatus: SystemEventsHistoryDeleteStatus.none);
  }

  Stream<SystemEventsHistoryState> onLoadSystemEventsHistoryIdForView(LoadSystemEventsHistoryByIdForView event) async* {
    yield this.state.copyWith(systemEventsHistoryStatusUI: SystemEventsHistoryStatusUI.loading);
    try {
      SystemEventsHistory loadedSystemEventsHistory = await _systemEventsHistoryRepository.getSystemEventsHistory(event.id);
      yield this.state.copyWith(loadedSystemEventsHistory: loadedSystemEventsHistory, systemEventsHistoryStatusUI: SystemEventsHistoryStatusUI.done);
    } catch(e) {
      yield this.state.copyWith(loadedSystemEventsHistory: null, systemEventsHistoryStatusUI: SystemEventsHistoryStatusUI.error);
    }
  }


  Stream<SystemEventsHistoryState> onEventNameChange(EventNameChanged event) async* {
    final eventName = EventNameInput.dirty(event.eventName);
    yield this.state.copyWith(
      eventName: eventName,
      formStatus: Formz.validate([
        eventName,
      this.state.eventDate,
      this.state.eventApi,
      this.state.ipAddress,
      this.state.eventNote,
      this.state.eventEntityName,
      this.state.eventEntityId,
      this.state.isSuspecious,
      this.state.callerEmail,
      this.state.callerId,
      this.state.clientId,
      ]),
    );
  }
  Stream<SystemEventsHistoryState> onEventDateChange(EventDateChanged event) async* {
    final eventDate = EventDateInput.dirty(event.eventDate);
    yield this.state.copyWith(
      eventDate: eventDate,
      formStatus: Formz.validate([
      this.state.eventName,
        eventDate,
      this.state.eventApi,
      this.state.ipAddress,
      this.state.eventNote,
      this.state.eventEntityName,
      this.state.eventEntityId,
      this.state.isSuspecious,
      this.state.callerEmail,
      this.state.callerId,
      this.state.clientId,
      ]),
    );
  }
  Stream<SystemEventsHistoryState> onEventApiChange(EventApiChanged event) async* {
    final eventApi = EventApiInput.dirty(event.eventApi);
    yield this.state.copyWith(
      eventApi: eventApi,
      formStatus: Formz.validate([
      this.state.eventName,
      this.state.eventDate,
        eventApi,
      this.state.ipAddress,
      this.state.eventNote,
      this.state.eventEntityName,
      this.state.eventEntityId,
      this.state.isSuspecious,
      this.state.callerEmail,
      this.state.callerId,
      this.state.clientId,
      ]),
    );
  }
  Stream<SystemEventsHistoryState> onIpAddressChange(IpAddressChanged event) async* {
    final ipAddress = IpAddressInput.dirty(event.ipAddress);
    yield this.state.copyWith(
      ipAddress: ipAddress,
      formStatus: Formz.validate([
      this.state.eventName,
      this.state.eventDate,
      this.state.eventApi,
        ipAddress,
      this.state.eventNote,
      this.state.eventEntityName,
      this.state.eventEntityId,
      this.state.isSuspecious,
      this.state.callerEmail,
      this.state.callerId,
      this.state.clientId,
      ]),
    );
  }
  Stream<SystemEventsHistoryState> onEventNoteChange(EventNoteChanged event) async* {
    final eventNote = EventNoteInput.dirty(event.eventNote);
    yield this.state.copyWith(
      eventNote: eventNote,
      formStatus: Formz.validate([
      this.state.eventName,
      this.state.eventDate,
      this.state.eventApi,
      this.state.ipAddress,
        eventNote,
      this.state.eventEntityName,
      this.state.eventEntityId,
      this.state.isSuspecious,
      this.state.callerEmail,
      this.state.callerId,
      this.state.clientId,
      ]),
    );
  }
  Stream<SystemEventsHistoryState> onEventEntityNameChange(EventEntityNameChanged event) async* {
    final eventEntityName = EventEntityNameInput.dirty(event.eventEntityName);
    yield this.state.copyWith(
      eventEntityName: eventEntityName,
      formStatus: Formz.validate([
      this.state.eventName,
      this.state.eventDate,
      this.state.eventApi,
      this.state.ipAddress,
      this.state.eventNote,
        eventEntityName,
      this.state.eventEntityId,
      this.state.isSuspecious,
      this.state.callerEmail,
      this.state.callerId,
      this.state.clientId,
      ]),
    );
  }
  Stream<SystemEventsHistoryState> onEventEntityIdChange(EventEntityIdChanged event) async* {
    final eventEntityId = EventEntityIdInput.dirty(event.eventEntityId);
    yield this.state.copyWith(
      eventEntityId: eventEntityId,
      formStatus: Formz.validate([
      this.state.eventName,
      this.state.eventDate,
      this.state.eventApi,
      this.state.ipAddress,
      this.state.eventNote,
      this.state.eventEntityName,
        eventEntityId,
      this.state.isSuspecious,
      this.state.callerEmail,
      this.state.callerId,
      this.state.clientId,
      ]),
    );
  }
  Stream<SystemEventsHistoryState> onIsSuspeciousChange(IsSuspeciousChanged event) async* {
    final isSuspecious = IsSuspeciousInput.dirty(event.isSuspecious);
    yield this.state.copyWith(
      isSuspecious: isSuspecious,
      formStatus: Formz.validate([
      this.state.eventName,
      this.state.eventDate,
      this.state.eventApi,
      this.state.ipAddress,
      this.state.eventNote,
      this.state.eventEntityName,
      this.state.eventEntityId,
        isSuspecious,
      this.state.callerEmail,
      this.state.callerId,
      this.state.clientId,
      ]),
    );
  }
  Stream<SystemEventsHistoryState> onCallerEmailChange(CallerEmailChanged event) async* {
    final callerEmail = CallerEmailInput.dirty(event.callerEmail);
    yield this.state.copyWith(
      callerEmail: callerEmail,
      formStatus: Formz.validate([
      this.state.eventName,
      this.state.eventDate,
      this.state.eventApi,
      this.state.ipAddress,
      this.state.eventNote,
      this.state.eventEntityName,
      this.state.eventEntityId,
      this.state.isSuspecious,
        callerEmail,
      this.state.callerId,
      this.state.clientId,
      ]),
    );
  }
  Stream<SystemEventsHistoryState> onCallerIdChange(CallerIdChanged event) async* {
    final callerId = CallerIdInput.dirty(event.callerId);
    yield this.state.copyWith(
      callerId: callerId,
      formStatus: Formz.validate([
      this.state.eventName,
      this.state.eventDate,
      this.state.eventApi,
      this.state.ipAddress,
      this.state.eventNote,
      this.state.eventEntityName,
      this.state.eventEntityId,
      this.state.isSuspecious,
      this.state.callerEmail,
        callerId,
      this.state.clientId,
      ]),
    );
  }
  Stream<SystemEventsHistoryState> onClientIdChange(ClientIdChanged event) async* {
    final clientId = ClientIdInput.dirty(event.clientId);
    yield this.state.copyWith(
      clientId: clientId,
      formStatus: Formz.validate([
      this.state.eventName,
      this.state.eventDate,
      this.state.eventApi,
      this.state.ipAddress,
      this.state.eventNote,
      this.state.eventEntityName,
      this.state.eventEntityId,
      this.state.isSuspecious,
      this.state.callerEmail,
      this.state.callerId,
        clientId,
      ]),
    );
  }

  @override
  Future<void> close() {
    eventNameController.dispose();
    eventDateController.dispose();
    eventApiController.dispose();
    ipAddressController.dispose();
    eventNoteController.dispose();
    eventEntityNameController.dispose();
    eventEntityIdController.dispose();
    callerEmailController.dispose();
    callerIdController.dispose();
    clientIdController.dispose();
    return super.close();
  }

}