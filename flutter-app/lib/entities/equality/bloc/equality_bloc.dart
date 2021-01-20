import 'dart:async';
import 'package:bloc/bloc.dart';
import 'package:flutter/foundation.dart';
import 'package:equatable/equatable.dart';
import 'package:flutter/widgets.dart';
import 'package:formz/formz.dart';

import 'package:amcCarePlanner/entities/equality/equality_model.dart';
import 'package:amcCarePlanner/entities/equality/equality_repository.dart';
import 'package:amcCarePlanner/entities/equality/bloc/equality_form_model.dart';
import 'package:amcCarePlanner/generated/l10n.dart';
import 'package:amcCarePlanner/shared/repository/http_utils.dart';
import 'package:intl/intl.dart';

part 'equality_events.dart';
part 'equality_state.dart';

class EqualityBloc extends Bloc<EqualityEvent, EqualityState> {
  final EqualityRepository _equalityRepository;

  final createdDateController = TextEditingController();
  final lastUpdatedDateController = TextEditingController();
  final clientIdController = TextEditingController();

  EqualityBloc({@required EqualityRepository equalityRepository}) : assert(equalityRepository != null),
        _equalityRepository = equalityRepository, 
  super(EqualityState(null,null,));

  @override
  void onTransition(Transition<EqualityEvent, EqualityState> transition) {
    super.onTransition(transition);
  }

  @override
  Stream<EqualityState> mapEventToState(EqualityEvent event) async* {
    if (event is InitEqualityList) {
      yield* onInitList(event);
    } else if (event is EqualityFormSubmitted) {
      yield* onSubmit();
    } else if (event is LoadEqualityByIdForEdit) {
      yield* onLoadEqualityIdForEdit(event);
    } else if (event is DeleteEqualityById) {
      yield* onDeleteEqualityId(event);
    } else if (event is LoadEqualityByIdForView) {
      yield* onLoadEqualityIdForView(event);
    }else if (event is GenderChanged){
      yield* onGenderChange(event);
    }else if (event is MaritalStatusChanged){
      yield* onMaritalStatusChange(event);
    }else if (event is ReligionChanged){
      yield* onReligionChange(event);
    }else if (event is CreatedDateChanged){
      yield* onCreatedDateChange(event);
    }else if (event is LastUpdatedDateChanged){
      yield* onLastUpdatedDateChange(event);
    }else if (event is ClientIdChanged){
      yield* onClientIdChange(event);
    }else if (event is HasExtraDataChanged){
      yield* onHasExtraDataChange(event);
    }  }

  Stream<EqualityState> onInitList(InitEqualityList event) async* {
    yield this.state.copyWith(equalityStatusUI: EqualityStatusUI.loading);
    List<Equality> equalities = await _equalityRepository.getAllEqualities();
    yield this.state.copyWith(equalities: equalities, equalityStatusUI: EqualityStatusUI.done);
  }

  Stream<EqualityState> onSubmit() async* {
    if (this.state.formStatus.isValidated) {
      yield this.state.copyWith(formStatus: FormzStatus.submissionInProgress);
      try {
        Equality result;
        if(this.state.editMode) {
          Equality newEquality = Equality(state.loadedEquality.id,
            this.state.gender.value,  
            this.state.maritalStatus.value,  
            this.state.religion.value,  
            this.state.createdDate.value,  
            this.state.lastUpdatedDate.value,  
            this.state.clientId.value,  
            this.state.hasExtraData.value,  
            null, 
            null, 
          );

          result = await _equalityRepository.update(newEquality);
        } else {
          Equality newEquality = Equality(null,
            this.state.gender.value,  
            this.state.maritalStatus.value,  
            this.state.religion.value,  
            this.state.createdDate.value,  
            this.state.lastUpdatedDate.value,  
            this.state.clientId.value,  
            this.state.hasExtraData.value,  
            null, 
            null, 
          );

          result = await _equalityRepository.create(newEquality);
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

  Stream<EqualityState> onLoadEqualityIdForEdit(LoadEqualityByIdForEdit event) async* {
    yield this.state.copyWith(equalityStatusUI: EqualityStatusUI.loading);
    Equality loadedEquality = await _equalityRepository.getEquality(event.id);

    final gender = GenderInput.dirty(loadedEquality?.gender != null ? loadedEquality.gender: null);
    final maritalStatus = MaritalStatusInput.dirty(loadedEquality?.maritalStatus != null ? loadedEquality.maritalStatus: null);
    final religion = ReligionInput.dirty(loadedEquality?.religion != null ? loadedEquality.religion: null);
    final createdDate = CreatedDateInput.dirty(loadedEquality?.createdDate != null ? loadedEquality.createdDate: null);
    final lastUpdatedDate = LastUpdatedDateInput.dirty(loadedEquality?.lastUpdatedDate != null ? loadedEquality.lastUpdatedDate: null);
    final clientId = ClientIdInput.dirty(loadedEquality?.clientId != null ? loadedEquality.clientId: 0);
    final hasExtraData = HasExtraDataInput.dirty(loadedEquality?.hasExtraData != null ? loadedEquality.hasExtraData: false);

    yield this.state.copyWith(loadedEquality: loadedEquality, editMode: true,
      gender: gender,
      maritalStatus: maritalStatus,
      religion: religion,
      createdDate: createdDate,
      lastUpdatedDate: lastUpdatedDate,
      clientId: clientId,
      hasExtraData: hasExtraData,
    equalityStatusUI: EqualityStatusUI.done);

    createdDateController.text = DateFormat.yMMMMd(S.current.locale).format(loadedEquality?.createdDate);
    lastUpdatedDateController.text = DateFormat.yMMMMd(S.current.locale).format(loadedEquality?.lastUpdatedDate);
    clientIdController.text = loadedEquality.clientId.toString();
  }

  Stream<EqualityState> onDeleteEqualityId(DeleteEqualityById event) async* {
    try {
      await _equalityRepository.delete(event.id);
      this.add(InitEqualityList());
      yield this.state.copyWith(deleteStatus: EqualityDeleteStatus.ok);
    } catch (e) {
      yield this.state.copyWith(deleteStatus: EqualityDeleteStatus.ko,
          generalNotificationKey: HttpUtils.errorServerKey);
    }
    yield this.state.copyWith(deleteStatus: EqualityDeleteStatus.none);
  }

  Stream<EqualityState> onLoadEqualityIdForView(LoadEqualityByIdForView event) async* {
    yield this.state.copyWith(equalityStatusUI: EqualityStatusUI.loading);
    try {
      Equality loadedEquality = await _equalityRepository.getEquality(event.id);
      yield this.state.copyWith(loadedEquality: loadedEquality, equalityStatusUI: EqualityStatusUI.done);
    } catch(e) {
      yield this.state.copyWith(loadedEquality: null, equalityStatusUI: EqualityStatusUI.error);
    }
  }


  Stream<EqualityState> onGenderChange(GenderChanged event) async* {
    final gender = GenderInput.dirty(event.gender);
    yield this.state.copyWith(
      gender: gender,
      formStatus: Formz.validate([
        gender,
      this.state.maritalStatus,
      this.state.religion,
      this.state.createdDate,
      this.state.lastUpdatedDate,
      this.state.clientId,
      this.state.hasExtraData,
      ]),
    );
  }
  Stream<EqualityState> onMaritalStatusChange(MaritalStatusChanged event) async* {
    final maritalStatus = MaritalStatusInput.dirty(event.maritalStatus);
    yield this.state.copyWith(
      maritalStatus: maritalStatus,
      formStatus: Formz.validate([
      this.state.gender,
        maritalStatus,
      this.state.religion,
      this.state.createdDate,
      this.state.lastUpdatedDate,
      this.state.clientId,
      this.state.hasExtraData,
      ]),
    );
  }
  Stream<EqualityState> onReligionChange(ReligionChanged event) async* {
    final religion = ReligionInput.dirty(event.religion);
    yield this.state.copyWith(
      religion: religion,
      formStatus: Formz.validate([
      this.state.gender,
      this.state.maritalStatus,
        religion,
      this.state.createdDate,
      this.state.lastUpdatedDate,
      this.state.clientId,
      this.state.hasExtraData,
      ]),
    );
  }
  Stream<EqualityState> onCreatedDateChange(CreatedDateChanged event) async* {
    final createdDate = CreatedDateInput.dirty(event.createdDate);
    yield this.state.copyWith(
      createdDate: createdDate,
      formStatus: Formz.validate([
      this.state.gender,
      this.state.maritalStatus,
      this.state.religion,
        createdDate,
      this.state.lastUpdatedDate,
      this.state.clientId,
      this.state.hasExtraData,
      ]),
    );
  }
  Stream<EqualityState> onLastUpdatedDateChange(LastUpdatedDateChanged event) async* {
    final lastUpdatedDate = LastUpdatedDateInput.dirty(event.lastUpdatedDate);
    yield this.state.copyWith(
      lastUpdatedDate: lastUpdatedDate,
      formStatus: Formz.validate([
      this.state.gender,
      this.state.maritalStatus,
      this.state.religion,
      this.state.createdDate,
        lastUpdatedDate,
      this.state.clientId,
      this.state.hasExtraData,
      ]),
    );
  }
  Stream<EqualityState> onClientIdChange(ClientIdChanged event) async* {
    final clientId = ClientIdInput.dirty(event.clientId);
    yield this.state.copyWith(
      clientId: clientId,
      formStatus: Formz.validate([
      this.state.gender,
      this.state.maritalStatus,
      this.state.religion,
      this.state.createdDate,
      this.state.lastUpdatedDate,
        clientId,
      this.state.hasExtraData,
      ]),
    );
  }
  Stream<EqualityState> onHasExtraDataChange(HasExtraDataChanged event) async* {
    final hasExtraData = HasExtraDataInput.dirty(event.hasExtraData);
    yield this.state.copyWith(
      hasExtraData: hasExtraData,
      formStatus: Formz.validate([
      this.state.gender,
      this.state.maritalStatus,
      this.state.religion,
      this.state.createdDate,
      this.state.lastUpdatedDate,
      this.state.clientId,
        hasExtraData,
      ]),
    );
  }

  @override
  Future<void> close() {
    createdDateController.dispose();
    lastUpdatedDateController.dispose();
    clientIdController.dispose();
    return super.close();
  }

}