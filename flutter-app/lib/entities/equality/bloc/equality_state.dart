part of 'equality_bloc.dart';

enum EqualityStatusUI {init, loading, error, done}
enum EqualityDeleteStatus {ok, ko, none}

class EqualityState extends Equatable {
  final List<Equality> equalities;
  final Equality loadedEquality;
  final bool editMode;
  final EqualityDeleteStatus deleteStatus;
  final EqualityStatusUI equalityStatusUI;

  final FormzStatus formStatus;
  final String generalNotificationKey;

  final GenderInput gender;
  final MaritalStatusInput maritalStatus;
  final ReligionInput religion;
  final CreatedDateInput createdDate;
  final LastUpdatedDateInput lastUpdatedDate;
  final ClientIdInput clientId;
  final HasExtraDataInput hasExtraData;

  
  EqualityState(
CreatedDateInput createdDate,LastUpdatedDateInput lastUpdatedDate,{
    this.equalities = const [],
    this.equalityStatusUI = EqualityStatusUI.init,
    this.loadedEquality = const Equality(0,null,null,null,null,null,0,false,null,null,),
    this.editMode = false,
    this.formStatus = FormzStatus.pure,
    this.generalNotificationKey = '',
    this.deleteStatus = EqualityDeleteStatus.none,
    this.gender = const GenderInput.pure(),
    this.maritalStatus = const MaritalStatusInput.pure(),
    this.religion = const ReligionInput.pure(),
    this.clientId = const ClientIdInput.pure(),
    this.hasExtraData = const HasExtraDataInput.pure(),
  }):this.createdDate = createdDate ?? CreatedDateInput.pure(),
this.lastUpdatedDate = lastUpdatedDate ?? LastUpdatedDateInput.pure()
;

  EqualityState copyWith({
    List<Equality> equalities,
    EqualityStatusUI equalityStatusUI,
    bool editMode,
    EqualityDeleteStatus deleteStatus,
    Equality loadedEquality,
    FormzStatus formStatus,
    String generalNotificationKey,
    GenderInput gender,
    MaritalStatusInput maritalStatus,
    ReligionInput religion,
    CreatedDateInput createdDate,
    LastUpdatedDateInput lastUpdatedDate,
    ClientIdInput clientId,
    HasExtraDataInput hasExtraData,
  }) {
    return EqualityState(
        createdDate,
        lastUpdatedDate,
      equalities: equalities ?? this.equalities,
      equalityStatusUI: equalityStatusUI ?? this.equalityStatusUI,
      loadedEquality: loadedEquality ?? this.loadedEquality,
      editMode: editMode ?? this.editMode,
      formStatus: formStatus ?? this.formStatus,
      generalNotificationKey: generalNotificationKey ?? this.generalNotificationKey,
      deleteStatus: deleteStatus ?? this.deleteStatus,
      gender: gender ?? this.gender,
      maritalStatus: maritalStatus ?? this.maritalStatus,
      religion: religion ?? this.religion,
      clientId: clientId ?? this.clientId,
      hasExtraData: hasExtraData ?? this.hasExtraData,
    );
  }

  @override
  List<Object> get props => [equalities, equalityStatusUI,
     loadedEquality, editMode, deleteStatus, formStatus, generalNotificationKey, 
gender,maritalStatus,religion,createdDate,lastUpdatedDate,clientId,hasExtraData,];

  @override
  bool get stringify => true;
}
