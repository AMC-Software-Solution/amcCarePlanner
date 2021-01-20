import 'dart:async';
import 'package:bloc/bloc.dart';
import 'package:flutter/foundation.dart';
import 'package:equatable/equatable.dart';
import 'package:flutter/widgets.dart';
import 'package:formz/formz.dart';

import 'package:amcCarePlanner/entities/system_setting/system_setting_model.dart';
import 'package:amcCarePlanner/entities/system_setting/system_setting_repository.dart';
import 'package:amcCarePlanner/entities/system_setting/bloc/system_setting_form_model.dart';
import 'package:amcCarePlanner/generated/l10n.dart';
import 'package:amcCarePlanner/shared/repository/http_utils.dart';
import 'package:intl/intl.dart';

part 'system_setting_events.dart';
part 'system_setting_state.dart';

class SystemSettingBloc extends Bloc<SystemSettingEvent, SystemSettingState> {
  final SystemSettingRepository _systemSettingRepository;

  final fieldNameController = TextEditingController();
  final fieldValueController = TextEditingController();
  final defaultValueController = TextEditingController();
  final createdDateController = TextEditingController();
  final updatedDateController = TextEditingController();
  final clientIdController = TextEditingController();

  SystemSettingBloc({@required SystemSettingRepository systemSettingRepository}) : assert(systemSettingRepository != null),
        _systemSettingRepository = systemSettingRepository, 
  super(SystemSettingState(null,null,));

  @override
  void onTransition(Transition<SystemSettingEvent, SystemSettingState> transition) {
    super.onTransition(transition);
  }

  @override
  Stream<SystemSettingState> mapEventToState(SystemSettingEvent event) async* {
    if (event is InitSystemSettingList) {
      yield* onInitList(event);
    } else if (event is SystemSettingFormSubmitted) {
      yield* onSubmit();
    } else if (event is LoadSystemSettingByIdForEdit) {
      yield* onLoadSystemSettingIdForEdit(event);
    } else if (event is DeleteSystemSettingById) {
      yield* onDeleteSystemSettingId(event);
    } else if (event is LoadSystemSettingByIdForView) {
      yield* onLoadSystemSettingIdForView(event);
    }else if (event is FieldNameChanged){
      yield* onFieldNameChange(event);
    }else if (event is FieldValueChanged){
      yield* onFieldValueChange(event);
    }else if (event is DefaultValueChanged){
      yield* onDefaultValueChange(event);
    }else if (event is SettingEnabledChanged){
      yield* onSettingEnabledChange(event);
    }else if (event is CreatedDateChanged){
      yield* onCreatedDateChange(event);
    }else if (event is UpdatedDateChanged){
      yield* onUpdatedDateChange(event);
    }else if (event is ClientIdChanged){
      yield* onClientIdChange(event);
    }else if (event is HasExtraDataChanged){
      yield* onHasExtraDataChange(event);
    }  }

  Stream<SystemSettingState> onInitList(InitSystemSettingList event) async* {
    yield this.state.copyWith(systemSettingStatusUI: SystemSettingStatusUI.loading);
    List<SystemSetting> systemSettings = await _systemSettingRepository.getAllSystemSettings();
    yield this.state.copyWith(systemSettings: systemSettings, systemSettingStatusUI: SystemSettingStatusUI.done);
  }

  Stream<SystemSettingState> onSubmit() async* {
    if (this.state.formStatus.isValidated) {
      yield this.state.copyWith(formStatus: FormzStatus.submissionInProgress);
      try {
        SystemSetting result;
        if(this.state.editMode) {
          SystemSetting newSystemSetting = SystemSetting(state.loadedSystemSetting.id,
            this.state.fieldName.value,  
            this.state.fieldValue.value,  
            this.state.defaultValue.value,  
            this.state.settingEnabled.value,  
            this.state.createdDate.value,  
            this.state.updatedDate.value,  
            this.state.clientId.value,  
            this.state.hasExtraData.value,  
          );

          result = await _systemSettingRepository.update(newSystemSetting);
        } else {
          SystemSetting newSystemSetting = SystemSetting(null,
            this.state.fieldName.value,  
            this.state.fieldValue.value,  
            this.state.defaultValue.value,  
            this.state.settingEnabled.value,  
            this.state.createdDate.value,  
            this.state.updatedDate.value,  
            this.state.clientId.value,  
            this.state.hasExtraData.value,  
          );

          result = await _systemSettingRepository.create(newSystemSetting);
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

  Stream<SystemSettingState> onLoadSystemSettingIdForEdit(LoadSystemSettingByIdForEdit event) async* {
    yield this.state.copyWith(systemSettingStatusUI: SystemSettingStatusUI.loading);
    SystemSetting loadedSystemSetting = await _systemSettingRepository.getSystemSetting(event.id);

    final fieldName = FieldNameInput.dirty(loadedSystemSetting?.fieldName != null ? loadedSystemSetting.fieldName: '');
    final fieldValue = FieldValueInput.dirty(loadedSystemSetting?.fieldValue != null ? loadedSystemSetting.fieldValue: '');
    final defaultValue = DefaultValueInput.dirty(loadedSystemSetting?.defaultValue != null ? loadedSystemSetting.defaultValue: '');
    final settingEnabled = SettingEnabledInput.dirty(loadedSystemSetting?.settingEnabled != null ? loadedSystemSetting.settingEnabled: false);
    final createdDate = CreatedDateInput.dirty(loadedSystemSetting?.createdDate != null ? loadedSystemSetting.createdDate: null);
    final updatedDate = UpdatedDateInput.dirty(loadedSystemSetting?.updatedDate != null ? loadedSystemSetting.updatedDate: null);
    final clientId = ClientIdInput.dirty(loadedSystemSetting?.clientId != null ? loadedSystemSetting.clientId: 0);
    final hasExtraData = HasExtraDataInput.dirty(loadedSystemSetting?.hasExtraData != null ? loadedSystemSetting.hasExtraData: false);

    yield this.state.copyWith(loadedSystemSetting: loadedSystemSetting, editMode: true,
      fieldName: fieldName,
      fieldValue: fieldValue,
      defaultValue: defaultValue,
      settingEnabled: settingEnabled,
      createdDate: createdDate,
      updatedDate: updatedDate,
      clientId: clientId,
      hasExtraData: hasExtraData,
    systemSettingStatusUI: SystemSettingStatusUI.done);

    fieldNameController.text = loadedSystemSetting.fieldName;
    fieldValueController.text = loadedSystemSetting.fieldValue;
    defaultValueController.text = loadedSystemSetting.defaultValue;
    createdDateController.text = DateFormat.yMMMMd(S.current.locale).format(loadedSystemSetting?.createdDate);
    updatedDateController.text = DateFormat.yMMMMd(S.current.locale).format(loadedSystemSetting?.updatedDate);
    clientIdController.text = loadedSystemSetting.clientId.toString();
  }

  Stream<SystemSettingState> onDeleteSystemSettingId(DeleteSystemSettingById event) async* {
    try {
      await _systemSettingRepository.delete(event.id);
      this.add(InitSystemSettingList());
      yield this.state.copyWith(deleteStatus: SystemSettingDeleteStatus.ok);
    } catch (e) {
      yield this.state.copyWith(deleteStatus: SystemSettingDeleteStatus.ko,
          generalNotificationKey: HttpUtils.errorServerKey);
    }
    yield this.state.copyWith(deleteStatus: SystemSettingDeleteStatus.none);
  }

  Stream<SystemSettingState> onLoadSystemSettingIdForView(LoadSystemSettingByIdForView event) async* {
    yield this.state.copyWith(systemSettingStatusUI: SystemSettingStatusUI.loading);
    try {
      SystemSetting loadedSystemSetting = await _systemSettingRepository.getSystemSetting(event.id);
      yield this.state.copyWith(loadedSystemSetting: loadedSystemSetting, systemSettingStatusUI: SystemSettingStatusUI.done);
    } catch(e) {
      yield this.state.copyWith(loadedSystemSetting: null, systemSettingStatusUI: SystemSettingStatusUI.error);
    }
  }


  Stream<SystemSettingState> onFieldNameChange(FieldNameChanged event) async* {
    final fieldName = FieldNameInput.dirty(event.fieldName);
    yield this.state.copyWith(
      fieldName: fieldName,
      formStatus: Formz.validate([
        fieldName,
      this.state.fieldValue,
      this.state.defaultValue,
      this.state.settingEnabled,
      this.state.createdDate,
      this.state.updatedDate,
      this.state.clientId,
      this.state.hasExtraData,
      ]),
    );
  }
  Stream<SystemSettingState> onFieldValueChange(FieldValueChanged event) async* {
    final fieldValue = FieldValueInput.dirty(event.fieldValue);
    yield this.state.copyWith(
      fieldValue: fieldValue,
      formStatus: Formz.validate([
      this.state.fieldName,
        fieldValue,
      this.state.defaultValue,
      this.state.settingEnabled,
      this.state.createdDate,
      this.state.updatedDate,
      this.state.clientId,
      this.state.hasExtraData,
      ]),
    );
  }
  Stream<SystemSettingState> onDefaultValueChange(DefaultValueChanged event) async* {
    final defaultValue = DefaultValueInput.dirty(event.defaultValue);
    yield this.state.copyWith(
      defaultValue: defaultValue,
      formStatus: Formz.validate([
      this.state.fieldName,
      this.state.fieldValue,
        defaultValue,
      this.state.settingEnabled,
      this.state.createdDate,
      this.state.updatedDate,
      this.state.clientId,
      this.state.hasExtraData,
      ]),
    );
  }
  Stream<SystemSettingState> onSettingEnabledChange(SettingEnabledChanged event) async* {
    final settingEnabled = SettingEnabledInput.dirty(event.settingEnabled);
    yield this.state.copyWith(
      settingEnabled: settingEnabled,
      formStatus: Formz.validate([
      this.state.fieldName,
      this.state.fieldValue,
      this.state.defaultValue,
        settingEnabled,
      this.state.createdDate,
      this.state.updatedDate,
      this.state.clientId,
      this.state.hasExtraData,
      ]),
    );
  }
  Stream<SystemSettingState> onCreatedDateChange(CreatedDateChanged event) async* {
    final createdDate = CreatedDateInput.dirty(event.createdDate);
    yield this.state.copyWith(
      createdDate: createdDate,
      formStatus: Formz.validate([
      this.state.fieldName,
      this.state.fieldValue,
      this.state.defaultValue,
      this.state.settingEnabled,
        createdDate,
      this.state.updatedDate,
      this.state.clientId,
      this.state.hasExtraData,
      ]),
    );
  }
  Stream<SystemSettingState> onUpdatedDateChange(UpdatedDateChanged event) async* {
    final updatedDate = UpdatedDateInput.dirty(event.updatedDate);
    yield this.state.copyWith(
      updatedDate: updatedDate,
      formStatus: Formz.validate([
      this.state.fieldName,
      this.state.fieldValue,
      this.state.defaultValue,
      this.state.settingEnabled,
      this.state.createdDate,
        updatedDate,
      this.state.clientId,
      this.state.hasExtraData,
      ]),
    );
  }
  Stream<SystemSettingState> onClientIdChange(ClientIdChanged event) async* {
    final clientId = ClientIdInput.dirty(event.clientId);
    yield this.state.copyWith(
      clientId: clientId,
      formStatus: Formz.validate([
      this.state.fieldName,
      this.state.fieldValue,
      this.state.defaultValue,
      this.state.settingEnabled,
      this.state.createdDate,
      this.state.updatedDate,
        clientId,
      this.state.hasExtraData,
      ]),
    );
  }
  Stream<SystemSettingState> onHasExtraDataChange(HasExtraDataChanged event) async* {
    final hasExtraData = HasExtraDataInput.dirty(event.hasExtraData);
    yield this.state.copyWith(
      hasExtraData: hasExtraData,
      formStatus: Formz.validate([
      this.state.fieldName,
      this.state.fieldValue,
      this.state.defaultValue,
      this.state.settingEnabled,
      this.state.createdDate,
      this.state.updatedDate,
      this.state.clientId,
        hasExtraData,
      ]),
    );
  }

  @override
  Future<void> close() {
    fieldNameController.dispose();
    fieldValueController.dispose();
    defaultValueController.dispose();
    createdDateController.dispose();
    updatedDateController.dispose();
    clientIdController.dispose();
    return super.close();
  }

}