part of 'system_setting_bloc.dart';

abstract class SystemSettingEvent extends Equatable {
  const SystemSettingEvent();

  @override
  List<Object> get props => [];

  @override
  bool get stringify => true;
}

class InitSystemSettingList extends SystemSettingEvent {}

class FieldNameChanged extends SystemSettingEvent {
  final String fieldName;
  
  const FieldNameChanged({@required this.fieldName});
  
  @override
  List<Object> get props => [fieldName];
}
class FieldValueChanged extends SystemSettingEvent {
  final String fieldValue;
  
  const FieldValueChanged({@required this.fieldValue});
  
  @override
  List<Object> get props => [fieldValue];
}
class DefaultValueChanged extends SystemSettingEvent {
  final String defaultValue;
  
  const DefaultValueChanged({@required this.defaultValue});
  
  @override
  List<Object> get props => [defaultValue];
}
class SettingEnabledChanged extends SystemSettingEvent {
  final bool settingEnabled;
  
  const SettingEnabledChanged({@required this.settingEnabled});
  
  @override
  List<Object> get props => [settingEnabled];
}
class CreatedDateChanged extends SystemSettingEvent {
  final DateTime createdDate;
  
  const CreatedDateChanged({@required this.createdDate});
  
  @override
  List<Object> get props => [createdDate];
}
class UpdatedDateChanged extends SystemSettingEvent {
  final DateTime updatedDate;
  
  const UpdatedDateChanged({@required this.updatedDate});
  
  @override
  List<Object> get props => [updatedDate];
}
class ClientIdChanged extends SystemSettingEvent {
  final int clientId;
  
  const ClientIdChanged({@required this.clientId});
  
  @override
  List<Object> get props => [clientId];
}
class HasExtraDataChanged extends SystemSettingEvent {
  final bool hasExtraData;
  
  const HasExtraDataChanged({@required this.hasExtraData});
  
  @override
  List<Object> get props => [hasExtraData];
}

class SystemSettingFormSubmitted extends SystemSettingEvent {}

class LoadSystemSettingByIdForEdit extends SystemSettingEvent {
  final int id;

  const LoadSystemSettingByIdForEdit({@required this.id});

  @override
  List<Object> get props => [id];
}

class DeleteSystemSettingById extends SystemSettingEvent {
  final int id;

  const DeleteSystemSettingById({@required this.id});

  @override
  List<Object> get props => [id];
}

class LoadSystemSettingByIdForView extends SystemSettingEvent {
  final int id;

  const LoadSystemSettingByIdForView({@required this.id});

  @override
  List<Object> get props => [id];
}
