part of 'disability_bloc.dart';

enum DisabilityStatusUI {init, loading, error, done}
enum DisabilityDeleteStatus {ok, ko, none}

class DisabilityState extends Equatable {
  final List<Disability> disabilities;
  final Disability loadedDisability;
  final bool editMode;
  final DisabilityDeleteStatus deleteStatus;
  final DisabilityStatusUI disabilityStatusUI;

  final FormzStatus formStatus;
  final String generalNotificationKey;

  final IsDisabledInput isDisabled;
  final NoteInput note;
  final CreatedDateInput createdDate;
  final LastUpdatedDateInput lastUpdatedDate;
  final ClientIdInput clientId;
  final HasExtraDataInput hasExtraData;

  
  DisabilityState(
CreatedDateInput createdDate,LastUpdatedDateInput lastUpdatedDate,{
    this.disabilities = const [],
    this.disabilityStatusUI = DisabilityStatusUI.init,
    this.loadedDisability = const Disability(0,false,'',null,null,0,false,null,null,),
    this.editMode = false,
    this.formStatus = FormzStatus.pure,
    this.generalNotificationKey = '',
    this.deleteStatus = DisabilityDeleteStatus.none,
    this.isDisabled = const IsDisabledInput.pure(),
    this.note = const NoteInput.pure(),
    this.clientId = const ClientIdInput.pure(),
    this.hasExtraData = const HasExtraDataInput.pure(),
  }):this.createdDate = createdDate ?? CreatedDateInput.pure(),
this.lastUpdatedDate = lastUpdatedDate ?? LastUpdatedDateInput.pure()
;

  DisabilityState copyWith({
    List<Disability> disabilities,
    DisabilityStatusUI disabilityStatusUI,
    bool editMode,
    DisabilityDeleteStatus deleteStatus,
    Disability loadedDisability,
    FormzStatus formStatus,
    String generalNotificationKey,
    IsDisabledInput isDisabled,
    NoteInput note,
    CreatedDateInput createdDate,
    LastUpdatedDateInput lastUpdatedDate,
    ClientIdInput clientId,
    HasExtraDataInput hasExtraData,
  }) {
    return DisabilityState(
        createdDate,
        lastUpdatedDate,
      disabilities: disabilities ?? this.disabilities,
      disabilityStatusUI: disabilityStatusUI ?? this.disabilityStatusUI,
      loadedDisability: loadedDisability ?? this.loadedDisability,
      editMode: editMode ?? this.editMode,
      formStatus: formStatus ?? this.formStatus,
      generalNotificationKey: generalNotificationKey ?? this.generalNotificationKey,
      deleteStatus: deleteStatus ?? this.deleteStatus,
      isDisabled: isDisabled ?? this.isDisabled,
      note: note ?? this.note,
      clientId: clientId ?? this.clientId,
      hasExtraData: hasExtraData ?? this.hasExtraData,
    );
  }

  @override
  List<Object> get props => [disabilities, disabilityStatusUI,
     loadedDisability, editMode, deleteStatus, formStatus, generalNotificationKey, 
isDisabled,note,createdDate,lastUpdatedDate,clientId,hasExtraData,];

  @override
  bool get stringify => true;
}
