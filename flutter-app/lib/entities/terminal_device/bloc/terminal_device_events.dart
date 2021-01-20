part of 'terminal_device_bloc.dart';

abstract class TerminalDeviceEvent extends Equatable {
  const TerminalDeviceEvent();

  @override
  List<Object> get props => [];

  @override
  bool get stringify => true;
}

class InitTerminalDeviceList extends TerminalDeviceEvent {}

class DeviceNameChanged extends TerminalDeviceEvent {
  final String deviceName;
  
  const DeviceNameChanged({@required this.deviceName});
  
  @override
  List<Object> get props => [deviceName];
}
class DeviceModelChanged extends TerminalDeviceEvent {
  final String deviceModel;
  
  const DeviceModelChanged({@required this.deviceModel});
  
  @override
  List<Object> get props => [deviceModel];
}
class RegisteredDateChanged extends TerminalDeviceEvent {
  final DateTime registeredDate;
  
  const RegisteredDateChanged({@required this.registeredDate});
  
  @override
  List<Object> get props => [registeredDate];
}
class ImeiChanged extends TerminalDeviceEvent {
  final String imei;
  
  const ImeiChanged({@required this.imei});
  
  @override
  List<Object> get props => [imei];
}
class SimNumberChanged extends TerminalDeviceEvent {
  final String simNumber;
  
  const SimNumberChanged({@required this.simNumber});
  
  @override
  List<Object> get props => [simNumber];
}
class UserStartedUsingFromChanged extends TerminalDeviceEvent {
  final DateTime userStartedUsingFrom;
  
  const UserStartedUsingFromChanged({@required this.userStartedUsingFrom});
  
  @override
  List<Object> get props => [userStartedUsingFrom];
}
class DeviceOnLocationFromChanged extends TerminalDeviceEvent {
  final DateTime deviceOnLocationFrom;
  
  const DeviceOnLocationFromChanged({@required this.deviceOnLocationFrom});
  
  @override
  List<Object> get props => [deviceOnLocationFrom];
}
class OperatingSystemChanged extends TerminalDeviceEvent {
  final String operatingSystem;
  
  const OperatingSystemChanged({@required this.operatingSystem});
  
  @override
  List<Object> get props => [operatingSystem];
}
class NoteChanged extends TerminalDeviceEvent {
  final String note;
  
  const NoteChanged({@required this.note});
  
  @override
  List<Object> get props => [note];
}
class OwnerEntityIdChanged extends TerminalDeviceEvent {
  final int ownerEntityId;
  
  const OwnerEntityIdChanged({@required this.ownerEntityId});
  
  @override
  List<Object> get props => [ownerEntityId];
}
class OwnerEntityNameChanged extends TerminalDeviceEvent {
  final String ownerEntityName;
  
  const OwnerEntityNameChanged({@required this.ownerEntityName});
  
  @override
  List<Object> get props => [ownerEntityName];
}
class CreatedDateChanged extends TerminalDeviceEvent {
  final DateTime createdDate;
  
  const CreatedDateChanged({@required this.createdDate});
  
  @override
  List<Object> get props => [createdDate];
}
class LastUpdatedDateChanged extends TerminalDeviceEvent {
  final DateTime lastUpdatedDate;
  
  const LastUpdatedDateChanged({@required this.lastUpdatedDate});
  
  @override
  List<Object> get props => [lastUpdatedDate];
}
class ClientIdChanged extends TerminalDeviceEvent {
  final int clientId;
  
  const ClientIdChanged({@required this.clientId});
  
  @override
  List<Object> get props => [clientId];
}
class HasExtraDataChanged extends TerminalDeviceEvent {
  final bool hasExtraData;
  
  const HasExtraDataChanged({@required this.hasExtraData});
  
  @override
  List<Object> get props => [hasExtraData];
}

class TerminalDeviceFormSubmitted extends TerminalDeviceEvent {}

class LoadTerminalDeviceByIdForEdit extends TerminalDeviceEvent {
  final int id;

  const LoadTerminalDeviceByIdForEdit({@required this.id});

  @override
  List<Object> get props => [id];
}

class DeleteTerminalDeviceById extends TerminalDeviceEvent {
  final int id;

  const DeleteTerminalDeviceById({@required this.id});

  @override
  List<Object> get props => [id];
}

class LoadTerminalDeviceByIdForView extends TerminalDeviceEvent {
  final int id;

  const LoadTerminalDeviceByIdForView({@required this.id});

  @override
  List<Object> get props => [id];
}
