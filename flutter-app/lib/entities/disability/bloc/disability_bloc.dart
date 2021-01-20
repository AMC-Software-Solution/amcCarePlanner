import 'dart:async';
import 'package:bloc/bloc.dart';
import 'package:flutter/foundation.dart';
import 'package:equatable/equatable.dart';
import 'package:flutter/widgets.dart';
import 'package:formz/formz.dart';

import 'package:amcCarePlanner/entities/disability/disability_model.dart';
import 'package:amcCarePlanner/entities/disability/disability_repository.dart';
import 'package:amcCarePlanner/entities/disability/bloc/disability_form_model.dart';
import 'package:amcCarePlanner/generated/l10n.dart';
import 'package:amcCarePlanner/shared/repository/http_utils.dart';
import 'package:intl/intl.dart';

part 'disability_events.dart';
part 'disability_state.dart';

class DisabilityBloc extends Bloc<DisabilityEvent, DisabilityState> {
  final DisabilityRepository _disabilityRepository;

  final noteController = TextEditingController();
  final createdDateController = TextEditingController();
  final lastUpdatedDateController = TextEditingController();
  final clientIdController = TextEditingController();

  DisabilityBloc({@required DisabilityRepository disabilityRepository}) : assert(disabilityRepository != null),
        _disabilityRepository = disabilityRepository, 
  super(DisabilityState(null,null,));

  @override
  void onTransition(Transition<DisabilityEvent, DisabilityState> transition) {
    super.onTransition(transition);
  }

  @override
  Stream<DisabilityState> mapEventToState(DisabilityEvent event) async* {
    if (event is InitDisabilityList) {
      yield* onInitList(event);
    } else if (event is DisabilityFormSubmitted) {
      yield* onSubmit();
    } else if (event is LoadDisabilityByIdForEdit) {
      yield* onLoadDisabilityIdForEdit(event);
    } else if (event is DeleteDisabilityById) {
      yield* onDeleteDisabilityId(event);
    } else if (event is LoadDisabilityByIdForView) {
      yield* onLoadDisabilityIdForView(event);
    }else if (event is IsDisabledChanged){
      yield* onIsDisabledChange(event);
    }else if (event is NoteChanged){
      yield* onNoteChange(event);
    }else if (event is CreatedDateChanged){
      yield* onCreatedDateChange(event);
    }else if (event is LastUpdatedDateChanged){
      yield* onLastUpdatedDateChange(event);
    }else if (event is ClientIdChanged){
      yield* onClientIdChange(event);
    }else if (event is HasExtraDataChanged){
      yield* onHasExtraDataChange(event);
    }  }

  Stream<DisabilityState> onInitList(InitDisabilityList event) async* {
    yield this.state.copyWith(disabilityStatusUI: DisabilityStatusUI.loading);
    List<Disability> disabilities = await _disabilityRepository.getAllDisabilities();
    yield this.state.copyWith(disabilities: disabilities, disabilityStatusUI: DisabilityStatusUI.done);
  }

  Stream<DisabilityState> onSubmit() async* {
    if (this.state.formStatus.isValidated) {
      yield this.state.copyWith(formStatus: FormzStatus.submissionInProgress);
      try {
        Disability result;
        if(this.state.editMode) {
          Disability newDisability = Disability(state.loadedDisability.id,
            this.state.isDisabled.value,  
            this.state.note.value,  
            this.state.createdDate.value,  
            this.state.lastUpdatedDate.value,  
            this.state.clientId.value,  
            this.state.hasExtraData.value,  
            null, 
            null, 
          );

          result = await _disabilityRepository.update(newDisability);
        } else {
          Disability newDisability = Disability(null,
            this.state.isDisabled.value,  
            this.state.note.value,  
            this.state.createdDate.value,  
            this.state.lastUpdatedDate.value,  
            this.state.clientId.value,  
            this.state.hasExtraData.value,  
            null, 
            null, 
          );

          result = await _disabilityRepository.create(newDisability);
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

  Stream<DisabilityState> onLoadDisabilityIdForEdit(LoadDisabilityByIdForEdit event) async* {
    yield this.state.copyWith(disabilityStatusUI: DisabilityStatusUI.loading);
    Disability loadedDisability = await _disabilityRepository.getDisability(event.id);

    final isDisabled = IsDisabledInput.dirty(loadedDisability?.isDisabled != null ? loadedDisability.isDisabled: false);
    final note = NoteInput.dirty(loadedDisability?.note != null ? loadedDisability.note: '');
    final createdDate = CreatedDateInput.dirty(loadedDisability?.createdDate != null ? loadedDisability.createdDate: null);
    final lastUpdatedDate = LastUpdatedDateInput.dirty(loadedDisability?.lastUpdatedDate != null ? loadedDisability.lastUpdatedDate: null);
    final clientId = ClientIdInput.dirty(loadedDisability?.clientId != null ? loadedDisability.clientId: 0);
    final hasExtraData = HasExtraDataInput.dirty(loadedDisability?.hasExtraData != null ? loadedDisability.hasExtraData: false);

    yield this.state.copyWith(loadedDisability: loadedDisability, editMode: true,
      isDisabled: isDisabled,
      note: note,
      createdDate: createdDate,
      lastUpdatedDate: lastUpdatedDate,
      clientId: clientId,
      hasExtraData: hasExtraData,
    disabilityStatusUI: DisabilityStatusUI.done);

    noteController.text = loadedDisability.note;
    createdDateController.text = DateFormat.yMMMMd(S.current.locale).format(loadedDisability?.createdDate);
    lastUpdatedDateController.text = DateFormat.yMMMMd(S.current.locale).format(loadedDisability?.lastUpdatedDate);
    clientIdController.text = loadedDisability.clientId.toString();
  }

  Stream<DisabilityState> onDeleteDisabilityId(DeleteDisabilityById event) async* {
    try {
      await _disabilityRepository.delete(event.id);
      this.add(InitDisabilityList());
      yield this.state.copyWith(deleteStatus: DisabilityDeleteStatus.ok);
    } catch (e) {
      yield this.state.copyWith(deleteStatus: DisabilityDeleteStatus.ko,
          generalNotificationKey: HttpUtils.errorServerKey);
    }
    yield this.state.copyWith(deleteStatus: DisabilityDeleteStatus.none);
  }

  Stream<DisabilityState> onLoadDisabilityIdForView(LoadDisabilityByIdForView event) async* {
    yield this.state.copyWith(disabilityStatusUI: DisabilityStatusUI.loading);
    try {
      Disability loadedDisability = await _disabilityRepository.getDisability(event.id);
      yield this.state.copyWith(loadedDisability: loadedDisability, disabilityStatusUI: DisabilityStatusUI.done);
    } catch(e) {
      yield this.state.copyWith(loadedDisability: null, disabilityStatusUI: DisabilityStatusUI.error);
    }
  }


  Stream<DisabilityState> onIsDisabledChange(IsDisabledChanged event) async* {
    final isDisabled = IsDisabledInput.dirty(event.isDisabled);
    yield this.state.copyWith(
      isDisabled: isDisabled,
      formStatus: Formz.validate([
        isDisabled,
      this.state.note,
      this.state.createdDate,
      this.state.lastUpdatedDate,
      this.state.clientId,
      this.state.hasExtraData,
      ]),
    );
  }
  Stream<DisabilityState> onNoteChange(NoteChanged event) async* {
    final note = NoteInput.dirty(event.note);
    yield this.state.copyWith(
      note: note,
      formStatus: Formz.validate([
      this.state.isDisabled,
        note,
      this.state.createdDate,
      this.state.lastUpdatedDate,
      this.state.clientId,
      this.state.hasExtraData,
      ]),
    );
  }
  Stream<DisabilityState> onCreatedDateChange(CreatedDateChanged event) async* {
    final createdDate = CreatedDateInput.dirty(event.createdDate);
    yield this.state.copyWith(
      createdDate: createdDate,
      formStatus: Formz.validate([
      this.state.isDisabled,
      this.state.note,
        createdDate,
      this.state.lastUpdatedDate,
      this.state.clientId,
      this.state.hasExtraData,
      ]),
    );
  }
  Stream<DisabilityState> onLastUpdatedDateChange(LastUpdatedDateChanged event) async* {
    final lastUpdatedDate = LastUpdatedDateInput.dirty(event.lastUpdatedDate);
    yield this.state.copyWith(
      lastUpdatedDate: lastUpdatedDate,
      formStatus: Formz.validate([
      this.state.isDisabled,
      this.state.note,
      this.state.createdDate,
        lastUpdatedDate,
      this.state.clientId,
      this.state.hasExtraData,
      ]),
    );
  }
  Stream<DisabilityState> onClientIdChange(ClientIdChanged event) async* {
    final clientId = ClientIdInput.dirty(event.clientId);
    yield this.state.copyWith(
      clientId: clientId,
      formStatus: Formz.validate([
      this.state.isDisabled,
      this.state.note,
      this.state.createdDate,
      this.state.lastUpdatedDate,
        clientId,
      this.state.hasExtraData,
      ]),
    );
  }
  Stream<DisabilityState> onHasExtraDataChange(HasExtraDataChanged event) async* {
    final hasExtraData = HasExtraDataInput.dirty(event.hasExtraData);
    yield this.state.copyWith(
      hasExtraData: hasExtraData,
      formStatus: Formz.validate([
      this.state.isDisabled,
      this.state.note,
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
    createdDateController.dispose();
    lastUpdatedDateController.dispose();
    clientIdController.dispose();
    return super.close();
  }

}