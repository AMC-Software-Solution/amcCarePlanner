import 'dart:async';
import 'package:bloc/bloc.dart';
import 'package:flutter/foundation.dart';
import 'package:equatable/equatable.dart';
import 'package:flutter/widgets.dart';
import 'package:formz/formz.dart';

import 'package:amcCarePlanner/entities/eligibility/eligibility_model.dart';
import 'package:amcCarePlanner/entities/eligibility/eligibility_repository.dart';
import 'package:amcCarePlanner/entities/eligibility/bloc/eligibility_form_model.dart';
import 'package:amcCarePlanner/generated/l10n.dart';
import 'package:amcCarePlanner/shared/repository/http_utils.dart';
import 'package:intl/intl.dart';

part 'eligibility_events.dart';
part 'eligibility_state.dart';

class EligibilityBloc extends Bloc<EligibilityEvent, EligibilityState> {
  final EligibilityRepository _eligibilityRepository;

  final noteController = TextEditingController();
  final createdDateController = TextEditingController();
  final lastUpdatedDateController = TextEditingController();
  final clientIdController = TextEditingController();

  EligibilityBloc({@required EligibilityRepository eligibilityRepository}) : assert(eligibilityRepository != null),
        _eligibilityRepository = eligibilityRepository, 
  super(EligibilityState(null,null,));

  @override
  void onTransition(Transition<EligibilityEvent, EligibilityState> transition) {
    super.onTransition(transition);
  }

  @override
  Stream<EligibilityState> mapEventToState(EligibilityEvent event) async* {
    if (event is InitEligibilityList) {
      yield* onInitList(event);
    } else if (event is EligibilityFormSubmitted) {
      yield* onSubmit();
    } else if (event is LoadEligibilityByIdForEdit) {
      yield* onLoadEligibilityIdForEdit(event);
    } else if (event is DeleteEligibilityById) {
      yield* onDeleteEligibilityId(event);
    } else if (event is LoadEligibilityByIdForView) {
      yield* onLoadEligibilityIdForView(event);
    }else if (event is IsEligibleChanged){
      yield* onIsEligibleChange(event);
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

  Stream<EligibilityState> onInitList(InitEligibilityList event) async* {
    yield this.state.copyWith(eligibilityStatusUI: EligibilityStatusUI.loading);
    List<Eligibility> eligibilities = await _eligibilityRepository.getAllEligibilities();
    yield this.state.copyWith(eligibilities: eligibilities, eligibilityStatusUI: EligibilityStatusUI.done);
  }

  Stream<EligibilityState> onSubmit() async* {
    if (this.state.formStatus.isValidated) {
      yield this.state.copyWith(formStatus: FormzStatus.submissionInProgress);
      try {
        Eligibility result;
        if(this.state.editMode) {
          Eligibility newEligibility = Eligibility(state.loadedEligibility.id,
            this.state.isEligible.value,  
            this.state.note.value,  
            this.state.createdDate.value,  
            this.state.lastUpdatedDate.value,  
            this.state.clientId.value,  
            this.state.hasExtraData.value,  
            null, 
            null, 
          );

          result = await _eligibilityRepository.update(newEligibility);
        } else {
          Eligibility newEligibility = Eligibility(null,
            this.state.isEligible.value,  
            this.state.note.value,  
            this.state.createdDate.value,  
            this.state.lastUpdatedDate.value,  
            this.state.clientId.value,  
            this.state.hasExtraData.value,  
            null, 
            null, 
          );

          result = await _eligibilityRepository.create(newEligibility);
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

  Stream<EligibilityState> onLoadEligibilityIdForEdit(LoadEligibilityByIdForEdit event) async* {
    yield this.state.copyWith(eligibilityStatusUI: EligibilityStatusUI.loading);
    Eligibility loadedEligibility = await _eligibilityRepository.getEligibility(event.id);

    final isEligible = IsEligibleInput.dirty(loadedEligibility?.isEligible != null ? loadedEligibility.isEligible: false);
    final note = NoteInput.dirty(loadedEligibility?.note != null ? loadedEligibility.note: '');
    final createdDate = CreatedDateInput.dirty(loadedEligibility?.createdDate != null ? loadedEligibility.createdDate: null);
    final lastUpdatedDate = LastUpdatedDateInput.dirty(loadedEligibility?.lastUpdatedDate != null ? loadedEligibility.lastUpdatedDate: null);
    final clientId = ClientIdInput.dirty(loadedEligibility?.clientId != null ? loadedEligibility.clientId: 0);
    final hasExtraData = HasExtraDataInput.dirty(loadedEligibility?.hasExtraData != null ? loadedEligibility.hasExtraData: false);

    yield this.state.copyWith(loadedEligibility: loadedEligibility, editMode: true,
      isEligible: isEligible,
      note: note,
      createdDate: createdDate,
      lastUpdatedDate: lastUpdatedDate,
      clientId: clientId,
      hasExtraData: hasExtraData,
    eligibilityStatusUI: EligibilityStatusUI.done);

    noteController.text = loadedEligibility.note;
    createdDateController.text = DateFormat.yMMMMd(S.current.locale).format(loadedEligibility?.createdDate);
    lastUpdatedDateController.text = DateFormat.yMMMMd(S.current.locale).format(loadedEligibility?.lastUpdatedDate);
    clientIdController.text = loadedEligibility.clientId.toString();
  }

  Stream<EligibilityState> onDeleteEligibilityId(DeleteEligibilityById event) async* {
    try {
      await _eligibilityRepository.delete(event.id);
      this.add(InitEligibilityList());
      yield this.state.copyWith(deleteStatus: EligibilityDeleteStatus.ok);
    } catch (e) {
      yield this.state.copyWith(deleteStatus: EligibilityDeleteStatus.ko,
          generalNotificationKey: HttpUtils.errorServerKey);
    }
    yield this.state.copyWith(deleteStatus: EligibilityDeleteStatus.none);
  }

  Stream<EligibilityState> onLoadEligibilityIdForView(LoadEligibilityByIdForView event) async* {
    yield this.state.copyWith(eligibilityStatusUI: EligibilityStatusUI.loading);
    try {
      Eligibility loadedEligibility = await _eligibilityRepository.getEligibility(event.id);
      yield this.state.copyWith(loadedEligibility: loadedEligibility, eligibilityStatusUI: EligibilityStatusUI.done);
    } catch(e) {
      yield this.state.copyWith(loadedEligibility: null, eligibilityStatusUI: EligibilityStatusUI.error);
    }
  }


  Stream<EligibilityState> onIsEligibleChange(IsEligibleChanged event) async* {
    final isEligible = IsEligibleInput.dirty(event.isEligible);
    yield this.state.copyWith(
      isEligible: isEligible,
      formStatus: Formz.validate([
        isEligible,
      this.state.note,
      this.state.createdDate,
      this.state.lastUpdatedDate,
      this.state.clientId,
      this.state.hasExtraData,
      ]),
    );
  }
  Stream<EligibilityState> onNoteChange(NoteChanged event) async* {
    final note = NoteInput.dirty(event.note);
    yield this.state.copyWith(
      note: note,
      formStatus: Formz.validate([
      this.state.isEligible,
        note,
      this.state.createdDate,
      this.state.lastUpdatedDate,
      this.state.clientId,
      this.state.hasExtraData,
      ]),
    );
  }
  Stream<EligibilityState> onCreatedDateChange(CreatedDateChanged event) async* {
    final createdDate = CreatedDateInput.dirty(event.createdDate);
    yield this.state.copyWith(
      createdDate: createdDate,
      formStatus: Formz.validate([
      this.state.isEligible,
      this.state.note,
        createdDate,
      this.state.lastUpdatedDate,
      this.state.clientId,
      this.state.hasExtraData,
      ]),
    );
  }
  Stream<EligibilityState> onLastUpdatedDateChange(LastUpdatedDateChanged event) async* {
    final lastUpdatedDate = LastUpdatedDateInput.dirty(event.lastUpdatedDate);
    yield this.state.copyWith(
      lastUpdatedDate: lastUpdatedDate,
      formStatus: Formz.validate([
      this.state.isEligible,
      this.state.note,
      this.state.createdDate,
        lastUpdatedDate,
      this.state.clientId,
      this.state.hasExtraData,
      ]),
    );
  }
  Stream<EligibilityState> onClientIdChange(ClientIdChanged event) async* {
    final clientId = ClientIdInput.dirty(event.clientId);
    yield this.state.copyWith(
      clientId: clientId,
      formStatus: Formz.validate([
      this.state.isEligible,
      this.state.note,
      this.state.createdDate,
      this.state.lastUpdatedDate,
        clientId,
      this.state.hasExtraData,
      ]),
    );
  }
  Stream<EligibilityState> onHasExtraDataChange(HasExtraDataChanged event) async* {
    final hasExtraData = HasExtraDataInput.dirty(event.hasExtraData);
    yield this.state.copyWith(
      hasExtraData: hasExtraData,
      formStatus: Formz.validate([
      this.state.isEligible,
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