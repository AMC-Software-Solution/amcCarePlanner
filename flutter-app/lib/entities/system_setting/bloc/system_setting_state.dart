part of 'system_setting_bloc.dart';

enum SystemSettingStatusUI {init, loading, error, done}
enum SystemSettingDeleteStatus {ok, ko, none}

class SystemSettingState extends Equatable {
  final List<SystemSetting> systemSettings;
  final SystemSetting loadedSystemSetting;
  final bool editMode;
  final SystemSettingDeleteStatus deleteStatus;
  final SystemSettingStatusUI systemSettingStatusUI;

  final FormzStatus formStatus;
  final String generalNotificationKey;

  final FieldNameInput fieldName;
  final FieldValueInput fieldValue;
  final DefaultValueInput defaultValue;
  final SettingEnabledInput settingEnabled;
  final CreatedDateInput createdDate;
  final UpdatedDateInput updatedDate;
  final ClientIdInput clientId;
  final HasExtraDataInput hasExtraData;

  
  SystemSettingState(
CreatedDateInput createdDate,UpdatedDateInput updatedDate,{
    this.systemSettings = const [],
    this.systemSettingStatusUI = SystemSettingStatusUI.init,
    this.loadedSystemSetting = const SystemSetting(0,'','','',false,null,null,0,false,),
    this.editMode = false,
    this.formStatus = FormzStatus.pure,
    this.generalNotificationKey = '',
    this.deleteStatus = SystemSettingDeleteStatus.none,
    this.fieldName = const FieldNameInput.pure(),
    this.fieldValue = const FieldValueInput.pure(),
    this.defaultValue = const DefaultValueInput.pure(),
    this.settingEnabled = const SettingEnabledInput.pure(),
    this.clientId = const ClientIdInput.pure(),
    this.hasExtraData = const HasExtraDataInput.pure(),
  }):this.createdDate = createdDate ?? CreatedDateInput.pure(),
this.updatedDate = updatedDate ?? UpdatedDateInput.pure()
;

  SystemSettingState copyWith({
    List<SystemSetting> systemSettings,
    SystemSettingStatusUI systemSettingStatusUI,
    bool editMode,
    SystemSettingDeleteStatus deleteStatus,
    SystemSetting loadedSystemSetting,
    FormzStatus formStatus,
    String generalNotificationKey,
    FieldNameInput fieldName,
    FieldValueInput fieldValue,
    DefaultValueInput defaultValue,
    SettingEnabledInput settingEnabled,
    CreatedDateInput createdDate,
    UpdatedDateInput updatedDate,
    ClientIdInput clientId,
    HasExtraDataInput hasExtraData,
  }) {
    return SystemSettingState(
        createdDate,
        updatedDate,
      systemSettings: systemSettings ?? this.systemSettings,
      systemSettingStatusUI: systemSettingStatusUI ?? this.systemSettingStatusUI,
      loadedSystemSetting: loadedSystemSetting ?? this.loadedSystemSetting,
      editMode: editMode ?? this.editMode,
      formStatus: formStatus ?? this.formStatus,
      generalNotificationKey: generalNotificationKey ?? this.generalNotificationKey,
      deleteStatus: deleteStatus ?? this.deleteStatus,
      fieldName: fieldName ?? this.fieldName,
      fieldValue: fieldValue ?? this.fieldValue,
      defaultValue: defaultValue ?? this.defaultValue,
      settingEnabled: settingEnabled ?? this.settingEnabled,
      clientId: clientId ?? this.clientId,
      hasExtraData: hasExtraData ?? this.hasExtraData,
    );
  }

  @override
  List<Object> get props => [systemSettings, systemSettingStatusUI,
     loadedSystemSetting, editMode, deleteStatus, formStatus, generalNotificationKey, 
fieldName,fieldValue,defaultValue,settingEnabled,createdDate,updatedDate,clientId,hasExtraData,];

  @override
  bool get stringify => true;
}
