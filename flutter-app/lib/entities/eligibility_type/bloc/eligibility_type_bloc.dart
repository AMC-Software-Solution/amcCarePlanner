import 'dart:async';
import 'package:bloc/bloc.dart';
import 'package:flutter/foundation.dart';
import 'package:equatable/equatable.dart';
import 'package:flutter/widgets.dart';
import 'package:formz/formz.dart';

import 'package:amcCarePlanner/entities/eligibility_type/eligibility_type_model.dart';
import 'package:amcCarePlanner/entities/eligibility_type/eligibility_type_repository.dart';
import 'package:amcCarePlanner/entities/eligibility_type/bloc/eligibility_type_form_model.dart';
import 'package:amcCarePlanner/generated/l10n.dart';
import 'package:amcCarePlanner/shared/repository/http_utils.dart';
import 'package:intl/intl.dart';

part 'eligibility_type_events.dart';
part 'eligibility_type_state.dart';

class EligibilityTypeBloc extends Bloc<EligibilityTypeEvent, EligibilityTypeState> {
  final EligibilityTypeRepository _eligibilityTypeRepository;

  final eligibilityTypeController = TextEditingController();
  final descriptionController = TextEditingController();

  EligibilityTypeBloc({@required EligibilityTypeRepository eligibilityTypeRepository}) : assert(eligibilityTypeRepository != null),
        _eligibilityTypeRepository = eligibilityTypeRepository, 
  super(EligibilityTypeState());

  @override
  void onTransition(Transition<EligibilityTypeEvent, EligibilityTypeState> transition) {
    super.onTransition(transition);
  }

  @override
  Stream<EligibilityTypeState> mapEventToState(EligibilityTypeEvent event) async* {
    if (event is InitEligibilityTypeList) {
      yield* onInitList(event);
    } else if (event is EligibilityTypeFormSubmitted) {
      yield* onSubmit();
    } else if (event is LoadEligibilityTypeByIdForEdit) {
      yield* onLoadEligibilityTypeIdForEdit(event);
    } else if (event is DeleteEligibilityTypeById) {
      yield* onDeleteEligibilityTypeId(event);
    } else if (event is LoadEligibilityTypeByIdForView) {
      yield* onLoadEligibilityTypeIdForView(event);
    }else if (event is EligibilityTypeChanged){
      yield* onEligibilityTypeChange(event);
    }else if (event is DescriptionChanged){
      yield* onDescriptionChange(event);
    }else if (event is HasExtraDataChanged){
      yield* onHasExtraDataChange(event);
    }  }

  Stream<EligibilityTypeState> onInitList(InitEligibilityTypeList event) async* {
    yield this.state.copyWith(eligibilityTypeStatusUI: EligibilityTypeStatusUI.loading);
    List<EligibilityType> eligibilityTypes = await _eligibilityTypeRepository.getAllEligibilityTypes();
    yield this.state.copyWith(eligibilityTypes: eligibilityTypes, eligibilityTypeStatusUI: EligibilityTypeStatusUI.done);
  }

  Stream<EligibilityTypeState> onSubmit() async* {
    if (this.state.formStatus.isValidated) {
      yield this.state.copyWith(formStatus: FormzStatus.submissionInProgress);
      try {
        EligibilityType result;
        if(this.state.editMode) {
          EligibilityType newEligibilityType = EligibilityType(state.loadedEligibilityType.id,
            this.state.eligibilityType.value,  
            this.state.description.value,  
            this.state.hasExtraData.value,  
          );

          result = await _eligibilityTypeRepository.update(newEligibilityType);
        } else {
          EligibilityType newEligibilityType = EligibilityType(null,
            this.state.eligibilityType.value,  
            this.state.description.value,  
            this.state.hasExtraData.value,  
          );

          result = await _eligibilityTypeRepository.create(newEligibilityType);
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

  Stream<EligibilityTypeState> onLoadEligibilityTypeIdForEdit(LoadEligibilityTypeByIdForEdit event) async* {
    yield this.state.copyWith(eligibilityTypeStatusUI: EligibilityTypeStatusUI.loading);
    EligibilityType loadedEligibilityType = await _eligibilityTypeRepository.getEligibilityType(event.id);

    final eligibilityType = EligibilityTypeInput.dirty(loadedEligibilityType?.eligibilityType != null ? loadedEligibilityType.eligibilityType: '');
    final description = DescriptionInput.dirty(loadedEligibilityType?.description != null ? loadedEligibilityType.description: '');
    final hasExtraData = HasExtraDataInput.dirty(loadedEligibilityType?.hasExtraData != null ? loadedEligibilityType.hasExtraData: false);

    yield this.state.copyWith(loadedEligibilityType: loadedEligibilityType, editMode: true,
      eligibilityType: eligibilityType,
      description: description,
      hasExtraData: hasExtraData,
    eligibilityTypeStatusUI: EligibilityTypeStatusUI.done);

    eligibilityTypeController.text = loadedEligibilityType.eligibilityType;
    descriptionController.text = loadedEligibilityType.description;
  }

  Stream<EligibilityTypeState> onDeleteEligibilityTypeId(DeleteEligibilityTypeById event) async* {
    try {
      await _eligibilityTypeRepository.delete(event.id);
      this.add(InitEligibilityTypeList());
      yield this.state.copyWith(deleteStatus: EligibilityTypeDeleteStatus.ok);
    } catch (e) {
      yield this.state.copyWith(deleteStatus: EligibilityTypeDeleteStatus.ko,
          generalNotificationKey: HttpUtils.errorServerKey);
    }
    yield this.state.copyWith(deleteStatus: EligibilityTypeDeleteStatus.none);
  }

  Stream<EligibilityTypeState> onLoadEligibilityTypeIdForView(LoadEligibilityTypeByIdForView event) async* {
    yield this.state.copyWith(eligibilityTypeStatusUI: EligibilityTypeStatusUI.loading);
    try {
      EligibilityType loadedEligibilityType = await _eligibilityTypeRepository.getEligibilityType(event.id);
      yield this.state.copyWith(loadedEligibilityType: loadedEligibilityType, eligibilityTypeStatusUI: EligibilityTypeStatusUI.done);
    } catch(e) {
      yield this.state.copyWith(loadedEligibilityType: null, eligibilityTypeStatusUI: EligibilityTypeStatusUI.error);
    }
  }


  Stream<EligibilityTypeState> onEligibilityTypeChange(EligibilityTypeChanged event) async* {
    final eligibilityType = EligibilityTypeInput.dirty(event.eligibilityType);
    yield this.state.copyWith(
      eligibilityType: eligibilityType,
      formStatus: Formz.validate([
        eligibilityType,
      this.state.description,
      this.state.hasExtraData,
      ]),
    );
  }
  Stream<EligibilityTypeState> onDescriptionChange(DescriptionChanged event) async* {
    final description = DescriptionInput.dirty(event.description);
    yield this.state.copyWith(
      description: description,
      formStatus: Formz.validate([
      this.state.eligibilityType,
        description,
      this.state.hasExtraData,
      ]),
    );
  }
  Stream<EligibilityTypeState> onHasExtraDataChange(HasExtraDataChanged event) async* {
    final hasExtraData = HasExtraDataInput.dirty(event.hasExtraData);
    yield this.state.copyWith(
      hasExtraData: hasExtraData,
      formStatus: Formz.validate([
      this.state.eligibilityType,
      this.state.description,
        hasExtraData,
      ]),
    );
  }

  @override
  Future<void> close() {
    eligibilityTypeController.dispose();
    descriptionController.dispose();
    return super.close();
  }

}