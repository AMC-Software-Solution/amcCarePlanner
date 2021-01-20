part of 'extra_data_bloc.dart';

enum ExtraDataStatusUI {init, loading, error, done}
enum ExtraDataDeleteStatus {ok, ko, none}

class ExtraDataState extends Equatable {
  final List<ExtraData> extraData;
  final ExtraData loadedExtraData;
  final bool editMode;
  final ExtraDataDeleteStatus deleteStatus;
  final ExtraDataStatusUI extraDataStatusUI;

  final FormzStatus formStatus;
  final String generalNotificationKey;

  final EntityNameInput entityName;
  final EntityIdInput entityId;
  final ExtraDataKeyInput extraDataKey;
  final ExtraDataValueInput extraDataValue;
  final ExtraDataValueDataTypeInput extraDataValueDataType;
  final ExtraDataDescriptionInput extraDataDescription;
  final ExtraDataDateInput extraDataDate;
  final HasExtraDataInput hasExtraData;

  
  ExtraDataState(
ExtraDataDateInput extraDataDate,{
    this.extraData = const [],
    this.extraDataStatusUI = ExtraDataStatusUI.init,
    this.loadedExtraData = const ExtraData(0,'',0,'','',null,'',null,false,),
    this.editMode = false,
    this.formStatus = FormzStatus.pure,
    this.generalNotificationKey = '',
    this.deleteStatus = ExtraDataDeleteStatus.none,
    this.entityName = const EntityNameInput.pure(),
    this.entityId = const EntityIdInput.pure(),
    this.extraDataKey = const ExtraDataKeyInput.pure(),
    this.extraDataValue = const ExtraDataValueInput.pure(),
    this.extraDataValueDataType = const ExtraDataValueDataTypeInput.pure(),
    this.extraDataDescription = const ExtraDataDescriptionInput.pure(),
    this.hasExtraData = const HasExtraDataInput.pure(),
  }):this.extraDataDate = extraDataDate ?? ExtraDataDateInput.pure()
;

  ExtraDataState copyWith({
    List<ExtraData> extraData,
    ExtraDataStatusUI extraDataStatusUI,
    bool editMode,
    ExtraDataDeleteStatus deleteStatus,
    ExtraData loadedExtraData,
    FormzStatus formStatus,
    String generalNotificationKey,
    EntityNameInput entityName,
    EntityIdInput entityId,
    ExtraDataKeyInput extraDataKey,
    ExtraDataValueInput extraDataValue,
    ExtraDataValueDataTypeInput extraDataValueDataType,
    ExtraDataDescriptionInput extraDataDescription,
    ExtraDataDateInput extraDataDate,
    HasExtraDataInput hasExtraData,
  }) {
    return ExtraDataState(
        extraDataDate,
      extraData: extraData ?? this.extraData,
      extraDataStatusUI: extraDataStatusUI ?? this.extraDataStatusUI,
      loadedExtraData: loadedExtraData ?? this.loadedExtraData,
      editMode: editMode ?? this.editMode,
      formStatus: formStatus ?? this.formStatus,
      generalNotificationKey: generalNotificationKey ?? this.generalNotificationKey,
      deleteStatus: deleteStatus ?? this.deleteStatus,
      entityName: entityName ?? this.entityName,
      entityId: entityId ?? this.entityId,
      extraDataKey: extraDataKey ?? this.extraDataKey,
      extraDataValue: extraDataValue ?? this.extraDataValue,
      extraDataValueDataType: extraDataValueDataType ?? this.extraDataValueDataType,
      extraDataDescription: extraDataDescription ?? this.extraDataDescription,
      hasExtraData: hasExtraData ?? this.hasExtraData,
    );
  }

  @override
  List<Object> get props => [extraData, extraDataStatusUI,
     loadedExtraData, editMode, deleteStatus, formStatus, generalNotificationKey, 
entityName,entityId,extraDataKey,extraDataValue,extraDataValueDataType,extraDataDescription,extraDataDate,hasExtraData,];

  @override
  bool get stringify => true;
}
