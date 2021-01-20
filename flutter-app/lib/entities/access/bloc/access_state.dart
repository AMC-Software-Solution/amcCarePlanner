part of 'access_bloc.dart';

enum AccessStatusUI {init, loading, error, done}
enum AccessDeleteStatus {ok, ko, none}

class AccessState extends Equatable {
  final List<Access> accesses;
  final Access loadedAccess;
  final bool editMode;
  final AccessDeleteStatus deleteStatus;
  final AccessStatusUI accessStatusUI;

  final FormzStatus formStatus;
  final String generalNotificationKey;

  final KeySafeNumberInput keySafeNumber;
  final AccessDetailsInput accessDetails;
  final CreatedDateInput createdDate;
  final LastUpdatedDateInput lastUpdatedDate;
  final ClientIdInput clientId;
  final HasExtraDataInput hasExtraData;

  
  AccessState(
CreatedDateInput createdDate,LastUpdatedDateInput lastUpdatedDate,{
    this.accesses = const [],
    this.accessStatusUI = AccessStatusUI.init,
    this.loadedAccess = const Access(0,'','',null,null,0,false,null,),
    this.editMode = false,
    this.formStatus = FormzStatus.pure,
    this.generalNotificationKey = '',
    this.deleteStatus = AccessDeleteStatus.none,
    this.keySafeNumber = const KeySafeNumberInput.pure(),
    this.accessDetails = const AccessDetailsInput.pure(),
    this.clientId = const ClientIdInput.pure(),
    this.hasExtraData = const HasExtraDataInput.pure(),
  }):this.createdDate = createdDate ?? CreatedDateInput.pure(),
this.lastUpdatedDate = lastUpdatedDate ?? LastUpdatedDateInput.pure()
;

  AccessState copyWith({
    List<Access> accesses,
    AccessStatusUI accessStatusUI,
    bool editMode,
    AccessDeleteStatus deleteStatus,
    Access loadedAccess,
    FormzStatus formStatus,
    String generalNotificationKey,
    KeySafeNumberInput keySafeNumber,
    AccessDetailsInput accessDetails,
    CreatedDateInput createdDate,
    LastUpdatedDateInput lastUpdatedDate,
    ClientIdInput clientId,
    HasExtraDataInput hasExtraData,
  }) {
    return AccessState(
        createdDate,
        lastUpdatedDate,
      accesses: accesses ?? this.accesses,
      accessStatusUI: accessStatusUI ?? this.accessStatusUI,
      loadedAccess: loadedAccess ?? this.loadedAccess,
      editMode: editMode ?? this.editMode,
      formStatus: formStatus ?? this.formStatus,
      generalNotificationKey: generalNotificationKey ?? this.generalNotificationKey,
      deleteStatus: deleteStatus ?? this.deleteStatus,
      keySafeNumber: keySafeNumber ?? this.keySafeNumber,
      accessDetails: accessDetails ?? this.accessDetails,
      clientId: clientId ?? this.clientId,
      hasExtraData: hasExtraData ?? this.hasExtraData,
    );
  }

  @override
  List<Object> get props => [accesses, accessStatusUI,
     loadedAccess, editMode, deleteStatus, formStatus, generalNotificationKey, 
keySafeNumber,accessDetails,createdDate,lastUpdatedDate,clientId,hasExtraData,];

  @override
  bool get stringify => true;
}
