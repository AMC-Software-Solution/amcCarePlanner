import 'dart:async';
import 'package:bloc/bloc.dart';
import 'package:flutter/foundation.dart';
import 'package:equatable/equatable.dart';
import 'package:flutter/widgets.dart';
import 'package:formz/formz.dart';

import 'package:amcCarePlanner/entities/disability_type/disability_type_model.dart';
import 'package:amcCarePlanner/entities/disability_type/disability_type_repository.dart';
import 'package:amcCarePlanner/entities/disability_type/bloc/disability_type_form_model.dart';
import 'package:amcCarePlanner/generated/l10n.dart';
import 'package:amcCarePlanner/shared/repository/http_utils.dart';
import 'package:intl/intl.dart';

part 'disability_type_events.dart';
part 'disability_type_state.dart';

class DisabilityTypeBloc extends Bloc<DisabilityTypeEvent, DisabilityTypeState> {
  final DisabilityTypeRepository _disabilityTypeRepository;

  final disabilityController = TextEditingController();
  final descriptionController = TextEditingController();

  DisabilityTypeBloc({@required DisabilityTypeRepository disabilityTypeRepository}) : assert(disabilityTypeRepository != null),
        _disabilityTypeRepository = disabilityTypeRepository, 
  super(DisabilityTypeState());

  @override
  void onTransition(Transition<DisabilityTypeEvent, DisabilityTypeState> transition) {
    super.onTransition(transition);
  }

  @override
  Stream<DisabilityTypeState> mapEventToState(DisabilityTypeEvent event) async* {
    if (event is InitDisabilityTypeList) {
      yield* onInitList(event);
    } else if (event is DisabilityTypeFormSubmitted) {
      yield* onSubmit();
    } else if (event is LoadDisabilityTypeByIdForEdit) {
      yield* onLoadDisabilityTypeIdForEdit(event);
    } else if (event is DeleteDisabilityTypeById) {
      yield* onDeleteDisabilityTypeId(event);
    } else if (event is LoadDisabilityTypeByIdForView) {
      yield* onLoadDisabilityTypeIdForView(event);
    }else if (event is DisabilityChanged){
      yield* onDisabilityChange(event);
    }else if (event is DescriptionChanged){
      yield* onDescriptionChange(event);
    }else if (event is HasExtraDataChanged){
      yield* onHasExtraDataChange(event);
    }  }

  Stream<DisabilityTypeState> onInitList(InitDisabilityTypeList event) async* {
    yield this.state.copyWith(disabilityTypeStatusUI: DisabilityTypeStatusUI.loading);
    List<DisabilityType> disabilityTypes = await _disabilityTypeRepository.getAllDisabilityTypes();
    yield this.state.copyWith(disabilityTypes: disabilityTypes, disabilityTypeStatusUI: DisabilityTypeStatusUI.done);
  }

  Stream<DisabilityTypeState> onSubmit() async* {
    if (this.state.formStatus.isValidated) {
      yield this.state.copyWith(formStatus: FormzStatus.submissionInProgress);
      try {
        DisabilityType result;
        if(this.state.editMode) {
          DisabilityType newDisabilityType = DisabilityType(state.loadedDisabilityType.id,
            this.state.disability.value,  
            this.state.description.value,  
            this.state.hasExtraData.value,  
          );

          result = await _disabilityTypeRepository.update(newDisabilityType);
        } else {
          DisabilityType newDisabilityType = DisabilityType(null,
            this.state.disability.value,  
            this.state.description.value,  
            this.state.hasExtraData.value,  
          );

          result = await _disabilityTypeRepository.create(newDisabilityType);
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

  Stream<DisabilityTypeState> onLoadDisabilityTypeIdForEdit(LoadDisabilityTypeByIdForEdit event) async* {
    yield this.state.copyWith(disabilityTypeStatusUI: DisabilityTypeStatusUI.loading);
    DisabilityType loadedDisabilityType = await _disabilityTypeRepository.getDisabilityType(event.id);

    final disability = DisabilityInput.dirty(loadedDisabilityType?.disability != null ? loadedDisabilityType.disability: '');
    final description = DescriptionInput.dirty(loadedDisabilityType?.description != null ? loadedDisabilityType.description: '');
    final hasExtraData = HasExtraDataInput.dirty(loadedDisabilityType?.hasExtraData != null ? loadedDisabilityType.hasExtraData: false);

    yield this.state.copyWith(loadedDisabilityType: loadedDisabilityType, editMode: true,
      disability: disability,
      description: description,
      hasExtraData: hasExtraData,
    disabilityTypeStatusUI: DisabilityTypeStatusUI.done);

    disabilityController.text = loadedDisabilityType.disability;
    descriptionController.text = loadedDisabilityType.description;
  }

  Stream<DisabilityTypeState> onDeleteDisabilityTypeId(DeleteDisabilityTypeById event) async* {
    try {
      await _disabilityTypeRepository.delete(event.id);
      this.add(InitDisabilityTypeList());
      yield this.state.copyWith(deleteStatus: DisabilityTypeDeleteStatus.ok);
    } catch (e) {
      yield this.state.copyWith(deleteStatus: DisabilityTypeDeleteStatus.ko,
          generalNotificationKey: HttpUtils.errorServerKey);
    }
    yield this.state.copyWith(deleteStatus: DisabilityTypeDeleteStatus.none);
  }

  Stream<DisabilityTypeState> onLoadDisabilityTypeIdForView(LoadDisabilityTypeByIdForView event) async* {
    yield this.state.copyWith(disabilityTypeStatusUI: DisabilityTypeStatusUI.loading);
    try {
      DisabilityType loadedDisabilityType = await _disabilityTypeRepository.getDisabilityType(event.id);
      yield this.state.copyWith(loadedDisabilityType: loadedDisabilityType, disabilityTypeStatusUI: DisabilityTypeStatusUI.done);
    } catch(e) {
      yield this.state.copyWith(loadedDisabilityType: null, disabilityTypeStatusUI: DisabilityTypeStatusUI.error);
    }
  }


  Stream<DisabilityTypeState> onDisabilityChange(DisabilityChanged event) async* {
    final disability = DisabilityInput.dirty(event.disability);
    yield this.state.copyWith(
      disability: disability,
      formStatus: Formz.validate([
        disability,
      this.state.description,
      this.state.hasExtraData,
      ]),
    );
  }
  Stream<DisabilityTypeState> onDescriptionChange(DescriptionChanged event) async* {
    final description = DescriptionInput.dirty(event.description);
    yield this.state.copyWith(
      description: description,
      formStatus: Formz.validate([
      this.state.disability,
        description,
      this.state.hasExtraData,
      ]),
    );
  }
  Stream<DisabilityTypeState> onHasExtraDataChange(HasExtraDataChanged event) async* {
    final hasExtraData = HasExtraDataInput.dirty(event.hasExtraData);
    yield this.state.copyWith(
      hasExtraData: hasExtraData,
      formStatus: Formz.validate([
      this.state.disability,
      this.state.description,
        hasExtraData,
      ]),
    );
  }

  @override
  Future<void> close() {
    disabilityController.dispose();
    descriptionController.dispose();
    return super.close();
  }

}