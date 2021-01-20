import 'dart:async';
import 'package:bloc/bloc.dart';
import 'package:flutter/foundation.dart';
import 'package:equatable/equatable.dart';
import 'package:flutter/widgets.dart';
import 'package:formz/formz.dart';

import 'package:amcCarePlanner/entities/communication/communication_model.dart';
import 'package:amcCarePlanner/entities/communication/communication_repository.dart';
import 'package:amcCarePlanner/entities/communication/bloc/communication_form_model.dart';
import 'package:amcCarePlanner/generated/l10n.dart';
import 'package:amcCarePlanner/shared/repository/http_utils.dart';
import 'package:intl/intl.dart';

part 'communication_events.dart';
part 'communication_state.dart';

class CommunicationBloc extends Bloc<CommunicationEvent, CommunicationState> {
  final CommunicationRepository _communicationRepository;

  final noteController = TextEditingController();
  final communicationDateController = TextEditingController();
  final attachmentUrlController = TextEditingController();
  final createdDateController = TextEditingController();
  final lastUpdatedDateController = TextEditingController();
  final clientIdController = TextEditingController();

  CommunicationBloc({@required CommunicationRepository communicationRepository}) : assert(communicationRepository != null),
        _communicationRepository = communicationRepository, 
  super(CommunicationState(null,null,null,));

  @override
  void onTransition(Transition<CommunicationEvent, CommunicationState> transition) {
    super.onTransition(transition);
  }

  @override
  Stream<CommunicationState> mapEventToState(CommunicationEvent event) async* {
    if (event is InitCommunicationList) {
      yield* onInitList(event);
    } else if (event is CommunicationFormSubmitted) {
      yield* onSubmit();
    } else if (event is LoadCommunicationByIdForEdit) {
      yield* onLoadCommunicationIdForEdit(event);
    } else if (event is DeleteCommunicationById) {
      yield* onDeleteCommunicationId(event);
    } else if (event is LoadCommunicationByIdForView) {
      yield* onLoadCommunicationIdForView(event);
    }else if (event is CommunicationTypeChanged){
      yield* onCommunicationTypeChange(event);
    }else if (event is NoteChanged){
      yield* onNoteChange(event);
    }else if (event is CommunicationDateChanged){
      yield* onCommunicationDateChange(event);
    }else if (event is AttachmentUrlChanged){
      yield* onAttachmentUrlChange(event);
    }else if (event is CreatedDateChanged){
      yield* onCreatedDateChange(event);
    }else if (event is LastUpdatedDateChanged){
      yield* onLastUpdatedDateChange(event);
    }else if (event is ClientIdChanged){
      yield* onClientIdChange(event);
    }else if (event is HasExtraDataChanged){
      yield* onHasExtraDataChange(event);
    }  }

  Stream<CommunicationState> onInitList(InitCommunicationList event) async* {
    yield this.state.copyWith(communicationStatusUI: CommunicationStatusUI.loading);
    List<Communication> communications = await _communicationRepository.getAllCommunications();
    yield this.state.copyWith(communications: communications, communicationStatusUI: CommunicationStatusUI.done);
  }

  Stream<CommunicationState> onSubmit() async* {
    if (this.state.formStatus.isValidated) {
      yield this.state.copyWith(formStatus: FormzStatus.submissionInProgress);
      try {
        Communication result;
        if(this.state.editMode) {
          Communication newCommunication = Communication(state.loadedCommunication.id,
            this.state.communicationType.value,  
            this.state.note.value,  
            this.state.communicationDate.value,  
            this.state.attachmentUrl.value,  
            this.state.createdDate.value,  
            this.state.lastUpdatedDate.value,  
            this.state.clientId.value,  
            this.state.hasExtraData.value,  
            null, 
            null, 
          );

          result = await _communicationRepository.update(newCommunication);
        } else {
          Communication newCommunication = Communication(null,
            this.state.communicationType.value,  
            this.state.note.value,  
            this.state.communicationDate.value,  
            this.state.attachmentUrl.value,  
            this.state.createdDate.value,  
            this.state.lastUpdatedDate.value,  
            this.state.clientId.value,  
            this.state.hasExtraData.value,  
            null, 
            null, 
          );

          result = await _communicationRepository.create(newCommunication);
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

  Stream<CommunicationState> onLoadCommunicationIdForEdit(LoadCommunicationByIdForEdit event) async* {
    yield this.state.copyWith(communicationStatusUI: CommunicationStatusUI.loading);
    Communication loadedCommunication = await _communicationRepository.getCommunication(event.id);

    final communicationType = CommunicationTypeInput.dirty(loadedCommunication?.communicationType != null ? loadedCommunication.communicationType: null);
    final note = NoteInput.dirty(loadedCommunication?.note != null ? loadedCommunication.note: '');
    final communicationDate = CommunicationDateInput.dirty(loadedCommunication?.communicationDate != null ? loadedCommunication.communicationDate: null);
    final attachmentUrl = AttachmentUrlInput.dirty(loadedCommunication?.attachmentUrl != null ? loadedCommunication.attachmentUrl: '');
    final createdDate = CreatedDateInput.dirty(loadedCommunication?.createdDate != null ? loadedCommunication.createdDate: null);
    final lastUpdatedDate = LastUpdatedDateInput.dirty(loadedCommunication?.lastUpdatedDate != null ? loadedCommunication.lastUpdatedDate: null);
    final clientId = ClientIdInput.dirty(loadedCommunication?.clientId != null ? loadedCommunication.clientId: 0);
    final hasExtraData = HasExtraDataInput.dirty(loadedCommunication?.hasExtraData != null ? loadedCommunication.hasExtraData: false);

    yield this.state.copyWith(loadedCommunication: loadedCommunication, editMode: true,
      communicationType: communicationType,
      note: note,
      communicationDate: communicationDate,
      attachmentUrl: attachmentUrl,
      createdDate: createdDate,
      lastUpdatedDate: lastUpdatedDate,
      clientId: clientId,
      hasExtraData: hasExtraData,
    communicationStatusUI: CommunicationStatusUI.done);

    noteController.text = loadedCommunication.note;
    communicationDateController.text = DateFormat.yMMMMd(S.current.locale).format(loadedCommunication?.communicationDate);
    attachmentUrlController.text = loadedCommunication.attachmentUrl;
    createdDateController.text = DateFormat.yMMMMd(S.current.locale).format(loadedCommunication?.createdDate);
    lastUpdatedDateController.text = DateFormat.yMMMMd(S.current.locale).format(loadedCommunication?.lastUpdatedDate);
    clientIdController.text = loadedCommunication.clientId.toString();
  }

  Stream<CommunicationState> onDeleteCommunicationId(DeleteCommunicationById event) async* {
    try {
      await _communicationRepository.delete(event.id);
      this.add(InitCommunicationList());
      yield this.state.copyWith(deleteStatus: CommunicationDeleteStatus.ok);
    } catch (e) {
      yield this.state.copyWith(deleteStatus: CommunicationDeleteStatus.ko,
          generalNotificationKey: HttpUtils.errorServerKey);
    }
    yield this.state.copyWith(deleteStatus: CommunicationDeleteStatus.none);
  }

  Stream<CommunicationState> onLoadCommunicationIdForView(LoadCommunicationByIdForView event) async* {
    yield this.state.copyWith(communicationStatusUI: CommunicationStatusUI.loading);
    try {
      Communication loadedCommunication = await _communicationRepository.getCommunication(event.id);
      yield this.state.copyWith(loadedCommunication: loadedCommunication, communicationStatusUI: CommunicationStatusUI.done);
    } catch(e) {
      yield this.state.copyWith(loadedCommunication: null, communicationStatusUI: CommunicationStatusUI.error);
    }
  }


  Stream<CommunicationState> onCommunicationTypeChange(CommunicationTypeChanged event) async* {
    final communicationType = CommunicationTypeInput.dirty(event.communicationType);
    yield this.state.copyWith(
      communicationType: communicationType,
      formStatus: Formz.validate([
        communicationType,
      this.state.note,
      this.state.communicationDate,
      this.state.attachmentUrl,
      this.state.createdDate,
      this.state.lastUpdatedDate,
      this.state.clientId,
      this.state.hasExtraData,
      ]),
    );
  }
  Stream<CommunicationState> onNoteChange(NoteChanged event) async* {
    final note = NoteInput.dirty(event.note);
    yield this.state.copyWith(
      note: note,
      formStatus: Formz.validate([
      this.state.communicationType,
        note,
      this.state.communicationDate,
      this.state.attachmentUrl,
      this.state.createdDate,
      this.state.lastUpdatedDate,
      this.state.clientId,
      this.state.hasExtraData,
      ]),
    );
  }
  Stream<CommunicationState> onCommunicationDateChange(CommunicationDateChanged event) async* {
    final communicationDate = CommunicationDateInput.dirty(event.communicationDate);
    yield this.state.copyWith(
      communicationDate: communicationDate,
      formStatus: Formz.validate([
      this.state.communicationType,
      this.state.note,
        communicationDate,
      this.state.attachmentUrl,
      this.state.createdDate,
      this.state.lastUpdatedDate,
      this.state.clientId,
      this.state.hasExtraData,
      ]),
    );
  }
  Stream<CommunicationState> onAttachmentUrlChange(AttachmentUrlChanged event) async* {
    final attachmentUrl = AttachmentUrlInput.dirty(event.attachmentUrl);
    yield this.state.copyWith(
      attachmentUrl: attachmentUrl,
      formStatus: Formz.validate([
      this.state.communicationType,
      this.state.note,
      this.state.communicationDate,
        attachmentUrl,
      this.state.createdDate,
      this.state.lastUpdatedDate,
      this.state.clientId,
      this.state.hasExtraData,
      ]),
    );
  }
  Stream<CommunicationState> onCreatedDateChange(CreatedDateChanged event) async* {
    final createdDate = CreatedDateInput.dirty(event.createdDate);
    yield this.state.copyWith(
      createdDate: createdDate,
      formStatus: Formz.validate([
      this.state.communicationType,
      this.state.note,
      this.state.communicationDate,
      this.state.attachmentUrl,
        createdDate,
      this.state.lastUpdatedDate,
      this.state.clientId,
      this.state.hasExtraData,
      ]),
    );
  }
  Stream<CommunicationState> onLastUpdatedDateChange(LastUpdatedDateChanged event) async* {
    final lastUpdatedDate = LastUpdatedDateInput.dirty(event.lastUpdatedDate);
    yield this.state.copyWith(
      lastUpdatedDate: lastUpdatedDate,
      formStatus: Formz.validate([
      this.state.communicationType,
      this.state.note,
      this.state.communicationDate,
      this.state.attachmentUrl,
      this.state.createdDate,
        lastUpdatedDate,
      this.state.clientId,
      this.state.hasExtraData,
      ]),
    );
  }
  Stream<CommunicationState> onClientIdChange(ClientIdChanged event) async* {
    final clientId = ClientIdInput.dirty(event.clientId);
    yield this.state.copyWith(
      clientId: clientId,
      formStatus: Formz.validate([
      this.state.communicationType,
      this.state.note,
      this.state.communicationDate,
      this.state.attachmentUrl,
      this.state.createdDate,
      this.state.lastUpdatedDate,
        clientId,
      this.state.hasExtraData,
      ]),
    );
  }
  Stream<CommunicationState> onHasExtraDataChange(HasExtraDataChanged event) async* {
    final hasExtraData = HasExtraDataInput.dirty(event.hasExtraData);
    yield this.state.copyWith(
      hasExtraData: hasExtraData,
      formStatus: Formz.validate([
      this.state.communicationType,
      this.state.note,
      this.state.communicationDate,
      this.state.attachmentUrl,
      this.state.createdDate,
      this.state.lastUpdatedDate,
      this.state.clientId,
        hasExtraData,
      ]),
    );
  }

  @override
  Future<void> close() {
    noteController.dispose();
    communicationDateController.dispose();
    attachmentUrlController.dispose();
    createdDateController.dispose();
    lastUpdatedDateController.dispose();
    clientIdController.dispose();
    return super.close();
  }

}