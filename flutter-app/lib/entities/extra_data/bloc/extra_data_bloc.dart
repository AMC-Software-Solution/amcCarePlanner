import 'dart:async';
import 'package:bloc/bloc.dart';
import 'package:flutter/foundation.dart';
import 'package:equatable/equatable.dart';
import 'package:flutter/widgets.dart';
import 'package:formz/formz.dart';

import 'package:amcCarePlanner/entities/extra_data/extra_data_model.dart';
import 'package:amcCarePlanner/entities/extra_data/extra_data_repository.dart';
import 'package:amcCarePlanner/entities/extra_data/bloc/extra_data_form_model.dart';
import 'package:amcCarePlanner/generated/l10n.dart';
import 'package:amcCarePlanner/shared/repository/http_utils.dart';
import 'package:intl/intl.dart';

part 'extra_data_events.dart';
part 'extra_data_state.dart';

class ExtraDataBloc extends Bloc<ExtraDataEvent, ExtraDataState> {
  final ExtraDataRepository _extraDataRepository;

  final entityNameController = TextEditingController();
  final entityIdController = TextEditingController();
  final extraDataKeyController = TextEditingController();
  final extraDataValueController = TextEditingController();
  final extraDataDescriptionController = TextEditingController();
  final extraDataDateController = TextEditingController();

  ExtraDataBloc({@required ExtraDataRepository extraDataRepository}) : assert(extraDataRepository != null),
        _extraDataRepository = extraDataRepository, 
  super(ExtraDataState(null,));

  @override
  void onTransition(Transition<ExtraDataEvent, ExtraDataState> transition) {
    super.onTransition(transition);
  }

  @override
  Stream<ExtraDataState> mapEventToState(ExtraDataEvent event) async* {
    if (event is InitExtraDataList) {
      yield* onInitList(event);
    } else if (event is ExtraDataFormSubmitted) {
      yield* onSubmit();
    } else if (event is LoadExtraDataByIdForEdit) {
      yield* onLoadExtraDataIdForEdit(event);
    } else if (event is DeleteExtraDataById) {
      yield* onDeleteExtraDataId(event);
    } else if (event is LoadExtraDataByIdForView) {
      yield* onLoadExtraDataIdForView(event);
    }else if (event is EntityNameChanged){
      yield* onEntityNameChange(event);
    }else if (event is EntityIdChanged){
      yield* onEntityIdChange(event);
    }else if (event is ExtraDataKeyChanged){
      yield* onExtraDataKeyChange(event);
    }else if (event is ExtraDataValueChanged){
      yield* onExtraDataValueChange(event);
    }else if (event is ExtraDataValueDataTypeChanged){
      yield* onExtraDataValueDataTypeChange(event);
    }else if (event is ExtraDataDescriptionChanged){
      yield* onExtraDataDescriptionChange(event);
    }else if (event is ExtraDataDateChanged){
      yield* onExtraDataDateChange(event);
    }else if (event is HasExtraDataChanged){
      yield* onHasExtraDataChange(event);
    }  }

  Stream<ExtraDataState> onInitList(InitExtraDataList event) async* {
    yield this.state.copyWith(extraDataStatusUI: ExtraDataStatusUI.loading);
    List<ExtraData> extraData = await _extraDataRepository.getAllExtraData();
    yield this.state.copyWith(extraData: extraData, extraDataStatusUI: ExtraDataStatusUI.done);
  }

  Stream<ExtraDataState> onSubmit() async* {
    if (this.state.formStatus.isValidated) {
      yield this.state.copyWith(formStatus: FormzStatus.submissionInProgress);
      try {
        ExtraData result;
        if(this.state.editMode) {
          ExtraData newExtraData = ExtraData(state.loadedExtraData.id,
            this.state.entityName.value,  
            this.state.entityId.value,  
            this.state.extraDataKey.value,  
            this.state.extraDataValue.value,  
            this.state.extraDataValueDataType.value,  
            this.state.extraDataDescription.value,  
            this.state.extraDataDate.value,  
            this.state.hasExtraData.value,  
          );

          result = await _extraDataRepository.update(newExtraData);
        } else {
          ExtraData newExtraData = ExtraData(null,
            this.state.entityName.value,  
            this.state.entityId.value,  
            this.state.extraDataKey.value,  
            this.state.extraDataValue.value,  
            this.state.extraDataValueDataType.value,  
            this.state.extraDataDescription.value,  
            this.state.extraDataDate.value,  
            this.state.hasExtraData.value,  
          );

          result = await _extraDataRepository.create(newExtraData);
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

  Stream<ExtraDataState> onLoadExtraDataIdForEdit(LoadExtraDataByIdForEdit event) async* {
    yield this.state.copyWith(extraDataStatusUI: ExtraDataStatusUI.loading);
    ExtraData loadedExtraData = await _extraDataRepository.getExtraData(event.id);

    final entityName = EntityNameInput.dirty(loadedExtraData?.entityName != null ? loadedExtraData.entityName: '');
    final entityId = EntityIdInput.dirty(loadedExtraData?.entityId != null ? loadedExtraData.entityId: 0);
    final extraDataKey = ExtraDataKeyInput.dirty(loadedExtraData?.extraDataKey != null ? loadedExtraData.extraDataKey: '');
    final extraDataValue = ExtraDataValueInput.dirty(loadedExtraData?.extraDataValue != null ? loadedExtraData.extraDataValue: '');
    final extraDataValueDataType = ExtraDataValueDataTypeInput.dirty(loadedExtraData?.extraDataValueDataType != null ? loadedExtraData.extraDataValueDataType: null);
    final extraDataDescription = ExtraDataDescriptionInput.dirty(loadedExtraData?.extraDataDescription != null ? loadedExtraData.extraDataDescription: '');
    final extraDataDate = ExtraDataDateInput.dirty(loadedExtraData?.extraDataDate != null ? loadedExtraData.extraDataDate: null);
    final hasExtraData = HasExtraDataInput.dirty(loadedExtraData?.hasExtraData != null ? loadedExtraData.hasExtraData: false);

    yield this.state.copyWith(loadedExtraData: loadedExtraData, editMode: true,
      entityName: entityName,
      entityId: entityId,
      extraDataKey: extraDataKey,
      extraDataValue: extraDataValue,
      extraDataValueDataType: extraDataValueDataType,
      extraDataDescription: extraDataDescription,
      extraDataDate: extraDataDate,
      hasExtraData: hasExtraData,
    extraDataStatusUI: ExtraDataStatusUI.done);

    entityNameController.text = loadedExtraData.entityName;
    entityIdController.text = loadedExtraData.entityId.toString();
    extraDataKeyController.text = loadedExtraData.extraDataKey;
    extraDataValueController.text = loadedExtraData.extraDataValue;
    extraDataDescriptionController.text = loadedExtraData.extraDataDescription;
    extraDataDateController.text = DateFormat.yMMMMd(S.current.locale).format(loadedExtraData?.extraDataDate);
  }

  Stream<ExtraDataState> onDeleteExtraDataId(DeleteExtraDataById event) async* {
    try {
      await _extraDataRepository.delete(event.id);
      this.add(InitExtraDataList());
      yield this.state.copyWith(deleteStatus: ExtraDataDeleteStatus.ok);
    } catch (e) {
      yield this.state.copyWith(deleteStatus: ExtraDataDeleteStatus.ko,
          generalNotificationKey: HttpUtils.errorServerKey);
    }
    yield this.state.copyWith(deleteStatus: ExtraDataDeleteStatus.none);
  }

  Stream<ExtraDataState> onLoadExtraDataIdForView(LoadExtraDataByIdForView event) async* {
    yield this.state.copyWith(extraDataStatusUI: ExtraDataStatusUI.loading);
    try {
      ExtraData loadedExtraData = await _extraDataRepository.getExtraData(event.id);
      yield this.state.copyWith(loadedExtraData: loadedExtraData, extraDataStatusUI: ExtraDataStatusUI.done);
    } catch(e) {
      yield this.state.copyWith(loadedExtraData: null, extraDataStatusUI: ExtraDataStatusUI.error);
    }
  }


  Stream<ExtraDataState> onEntityNameChange(EntityNameChanged event) async* {
    final entityName = EntityNameInput.dirty(event.entityName);
    yield this.state.copyWith(
      entityName: entityName,
      formStatus: Formz.validate([
        entityName,
      this.state.entityId,
      this.state.extraDataKey,
      this.state.extraDataValue,
      this.state.extraDataValueDataType,
      this.state.extraDataDescription,
      this.state.extraDataDate,
      this.state.hasExtraData,
      ]),
    );
  }
  Stream<ExtraDataState> onEntityIdChange(EntityIdChanged event) async* {
    final entityId = EntityIdInput.dirty(event.entityId);
    yield this.state.copyWith(
      entityId: entityId,
      formStatus: Formz.validate([
      this.state.entityName,
        entityId,
      this.state.extraDataKey,
      this.state.extraDataValue,
      this.state.extraDataValueDataType,
      this.state.extraDataDescription,
      this.state.extraDataDate,
      this.state.hasExtraData,
      ]),
    );
  }
  Stream<ExtraDataState> onExtraDataKeyChange(ExtraDataKeyChanged event) async* {
    final extraDataKey = ExtraDataKeyInput.dirty(event.extraDataKey);
    yield this.state.copyWith(
      extraDataKey: extraDataKey,
      formStatus: Formz.validate([
      this.state.entityName,
      this.state.entityId,
        extraDataKey,
      this.state.extraDataValue,
      this.state.extraDataValueDataType,
      this.state.extraDataDescription,
      this.state.extraDataDate,
      this.state.hasExtraData,
      ]),
    );
  }
  Stream<ExtraDataState> onExtraDataValueChange(ExtraDataValueChanged event) async* {
    final extraDataValue = ExtraDataValueInput.dirty(event.extraDataValue);
    yield this.state.copyWith(
      extraDataValue: extraDataValue,
      formStatus: Formz.validate([
      this.state.entityName,
      this.state.entityId,
      this.state.extraDataKey,
        extraDataValue,
      this.state.extraDataValueDataType,
      this.state.extraDataDescription,
      this.state.extraDataDate,
      this.state.hasExtraData,
      ]),
    );
  }
  Stream<ExtraDataState> onExtraDataValueDataTypeChange(ExtraDataValueDataTypeChanged event) async* {
    final extraDataValueDataType = ExtraDataValueDataTypeInput.dirty(event.extraDataValueDataType);
    yield this.state.copyWith(
      extraDataValueDataType: extraDataValueDataType,
      formStatus: Formz.validate([
      this.state.entityName,
      this.state.entityId,
      this.state.extraDataKey,
      this.state.extraDataValue,
        extraDataValueDataType,
      this.state.extraDataDescription,
      this.state.extraDataDate,
      this.state.hasExtraData,
      ]),
    );
  }
  Stream<ExtraDataState> onExtraDataDescriptionChange(ExtraDataDescriptionChanged event) async* {
    final extraDataDescription = ExtraDataDescriptionInput.dirty(event.extraDataDescription);
    yield this.state.copyWith(
      extraDataDescription: extraDataDescription,
      formStatus: Formz.validate([
      this.state.entityName,
      this.state.entityId,
      this.state.extraDataKey,
      this.state.extraDataValue,
      this.state.extraDataValueDataType,
        extraDataDescription,
      this.state.extraDataDate,
      this.state.hasExtraData,
      ]),
    );
  }
  Stream<ExtraDataState> onExtraDataDateChange(ExtraDataDateChanged event) async* {
    final extraDataDate = ExtraDataDateInput.dirty(event.extraDataDate);
    yield this.state.copyWith(
      extraDataDate: extraDataDate,
      formStatus: Formz.validate([
      this.state.entityName,
      this.state.entityId,
      this.state.extraDataKey,
      this.state.extraDataValue,
      this.state.extraDataValueDataType,
      this.state.extraDataDescription,
        extraDataDate,
      this.state.hasExtraData,
      ]),
    );
  }
  Stream<ExtraDataState> onHasExtraDataChange(HasExtraDataChanged event) async* {
    final hasExtraData = HasExtraDataInput.dirty(event.hasExtraData);
    yield this.state.copyWith(
      hasExtraData: hasExtraData,
      formStatus: Formz.validate([
      this.state.entityName,
      this.state.entityId,
      this.state.extraDataKey,
      this.state.extraDataValue,
      this.state.extraDataValueDataType,
      this.state.extraDataDescription,
      this.state.extraDataDate,
        hasExtraData,
      ]),
    );
  }

  @override
  Future<void> close() {
    entityNameController.dispose();
    entityIdController.dispose();
    extraDataKeyController.dispose();
    extraDataValueController.dispose();
    extraDataDescriptionController.dispose();
    extraDataDateController.dispose();
    return super.close();
  }

}