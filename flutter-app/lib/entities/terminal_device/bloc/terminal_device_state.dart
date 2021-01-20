part of 'terminal_device_bloc.dart';

enum TerminalDeviceStatusUI {init, loading, error, done}
enum TerminalDeviceDeleteStatus {ok, ko, none}

class TerminalDeviceState extends Equatable {
  final List<TerminalDevice> terminalDevices;
  final TerminalDevice loadedTerminalDevice;
  final bool editMode;
  final TerminalDeviceDeleteStatus deleteStatus;
  final TerminalDeviceStatusUI terminalDeviceStatusUI;

  final FormzStatus formStatus;
  final String generalNotificationKey;

  final DeviceNameInput deviceName;
  final DeviceModelInput deviceModel;
  final RegisteredDateInput registeredDate;
  final ImeiInput imei;
  final SimNumberInput simNumber;
  final UserStartedUsingFromInput userStartedUsingFrom;
  final DeviceOnLocationFromInput deviceOnLocationFrom;
  final OperatingSystemInput operatingSystem;
  final NoteInput note;
  final OwnerEntityIdInput ownerEntityId;
  final OwnerEntityNameInput ownerEntityName;
  final CreatedDateInput createdDate;
  final LastUpdatedDateInput lastUpdatedDate;
  final ClientIdInput clientId;
  final HasExtraDataInput hasExtraData;

  
  TerminalDeviceState(
RegisteredDateInput registeredDate,UserStartedUsingFromInput userStartedUsingFrom,DeviceOnLocationFromInput deviceOnLocationFrom,CreatedDateInput createdDate,LastUpdatedDateInput lastUpdatedDate,{
    this.terminalDevices = const [],
    this.terminalDeviceStatusUI = TerminalDeviceStatusUI.init,
    this.loadedTerminalDevice = const TerminalDevice(0,'','',null,'','',null,null,'','',0,'',null,null,0,false,null,),
    this.editMode = false,
    this.formStatus = FormzStatus.pure,
    this.generalNotificationKey = '',
    this.deleteStatus = TerminalDeviceDeleteStatus.none,
    this.deviceName = const DeviceNameInput.pure(),
    this.deviceModel = const DeviceModelInput.pure(),
    this.imei = const ImeiInput.pure(),
    this.simNumber = const SimNumberInput.pure(),
    this.operatingSystem = const OperatingSystemInput.pure(),
    this.note = const NoteInput.pure(),
    this.ownerEntityId = const OwnerEntityIdInput.pure(),
    this.ownerEntityName = const OwnerEntityNameInput.pure(),
    this.clientId = const ClientIdInput.pure(),
    this.hasExtraData = const HasExtraDataInput.pure(),
  }):this.registeredDate = registeredDate ?? RegisteredDateInput.pure(),
this.userStartedUsingFrom = userStartedUsingFrom ?? UserStartedUsingFromInput.pure(),
this.deviceOnLocationFrom = deviceOnLocationFrom ?? DeviceOnLocationFromInput.pure(),
this.createdDate = createdDate ?? CreatedDateInput.pure(),
this.lastUpdatedDate = lastUpdatedDate ?? LastUpdatedDateInput.pure()
;

  TerminalDeviceState copyWith({
    List<TerminalDevice> terminalDevices,
    TerminalDeviceStatusUI terminalDeviceStatusUI,
    bool editMode,
    TerminalDeviceDeleteStatus deleteStatus,
    TerminalDevice loadedTerminalDevice,
    FormzStatus formStatus,
    String generalNotificationKey,
    DeviceNameInput deviceName,
    DeviceModelInput deviceModel,
    RegisteredDateInput registeredDate,
    ImeiInput imei,
    SimNumberInput simNumber,
    UserStartedUsingFromInput userStartedUsingFrom,
    DeviceOnLocationFromInput deviceOnLocationFrom,
    OperatingSystemInput operatingSystem,
    NoteInput note,
    OwnerEntityIdInput ownerEntityId,
    OwnerEntityNameInput ownerEntityName,
    CreatedDateInput createdDate,
    LastUpdatedDateInput lastUpdatedDate,
    ClientIdInput clientId,
    HasExtraDataInput hasExtraData,
  }) {
    return TerminalDeviceState(
        registeredDate,
        userStartedUsingFrom,
        deviceOnLocationFrom,
        createdDate,
        lastUpdatedDate,
      terminalDevices: terminalDevices ?? this.terminalDevices,
      terminalDeviceStatusUI: terminalDeviceStatusUI ?? this.terminalDeviceStatusUI,
      loadedTerminalDevice: loadedTerminalDevice ?? this.loadedTerminalDevice,
      editMode: editMode ?? this.editMode,
      formStatus: formStatus ?? this.formStatus,
      generalNotificationKey: generalNotificationKey ?? this.generalNotificationKey,
      deleteStatus: deleteStatus ?? this.deleteStatus,
      deviceName: deviceName ?? this.deviceName,
      deviceModel: deviceModel ?? this.deviceModel,
      imei: imei ?? this.imei,
      simNumber: simNumber ?? this.simNumber,
      operatingSystem: operatingSystem ?? this.operatingSystem,
      note: note ?? this.note,
      ownerEntityId: ownerEntityId ?? this.ownerEntityId,
      ownerEntityName: ownerEntityName ?? this.ownerEntityName,
      clientId: clientId ?? this.clientId,
      hasExtraData: hasExtraData ?? this.hasExtraData,
    );
  }

  @override
  List<Object> get props => [terminalDevices, terminalDeviceStatusUI,
     loadedTerminalDevice, editMode, deleteStatus, formStatus, generalNotificationKey, 
deviceName,deviceModel,registeredDate,imei,simNumber,userStartedUsingFrom,deviceOnLocationFrom,operatingSystem,note,ownerEntityId,ownerEntityName,createdDate,lastUpdatedDate,clientId,hasExtraData,];

  @override
  bool get stringify => true;
}
