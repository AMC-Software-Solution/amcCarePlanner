part of 'eligibility_bloc.dart';

enum EligibilityStatusUI {init, loading, error, done}
enum EligibilityDeleteStatus {ok, ko, none}

class EligibilityState extends Equatable {
  final List<Eligibility> eligibilities;
  final Eligibility loadedEligibility;
  final bool editMode;
  final EligibilityDeleteStatus deleteStatus;
  final EligibilityStatusUI eligibilityStatusUI;

  final FormzStatus formStatus;
  final String generalNotificationKey;

  final IsEligibleInput isEligible;
  final NoteInput note;
  final CreatedDateInput createdDate;
  final LastUpdatedDateInput lastUpdatedDate;
  final ClientIdInput clientId;
  final HasExtraDataInput hasExtraData;

  
  EligibilityState(
CreatedDateInput createdDate,LastUpdatedDateInput lastUpdatedDate,{
    this.eligibilities = const [],
    this.eligibilityStatusUI = EligibilityStatusUI.init,
    this.loadedEligibility = const Eligibility(0,false,'',null,null,0,false,null,null,),
    this.editMode = false,
    this.formStatus = FormzStatus.pure,
    this.generalNotificationKey = '',
    this.deleteStatus = EligibilityDeleteStatus.none,
    this.isEligible = const IsEligibleInput.pure(),
    this.note = const NoteInput.pure(),
    this.clientId = const ClientIdInput.pure(),
    this.hasExtraData = const HasExtraDataInput.pure(),
  }):this.createdDate = createdDate ?? CreatedDateInput.pure(),
this.lastUpdatedDate = lastUpdatedDate ?? LastUpdatedDateInput.pure()
;

  EligibilityState copyWith({
    List<Eligibility> eligibilities,
    EligibilityStatusUI eligibilityStatusUI,
    bool editMode,
    EligibilityDeleteStatus deleteStatus,
    Eligibility loadedEligibility,
    FormzStatus formStatus,
    String generalNotificationKey,
    IsEligibleInput isEligible,
    NoteInput note,
    CreatedDateInput createdDate,
    LastUpdatedDateInput lastUpdatedDate,
    ClientIdInput clientId,
    HasExtraDataInput hasExtraData,
  }) {
    return EligibilityState(
        createdDate,
        lastUpdatedDate,
      eligibilities: eligibilities ?? this.eligibilities,
      eligibilityStatusUI: eligibilityStatusUI ?? this.eligibilityStatusUI,
      loadedEligibility: loadedEligibility ?? this.loadedEligibility,
      editMode: editMode ?? this.editMode,
      formStatus: formStatus ?? this.formStatus,
      generalNotificationKey: generalNotificationKey ?? this.generalNotificationKey,
      deleteStatus: deleteStatus ?? this.deleteStatus,
      isEligible: isEligible ?? this.isEligible,
      note: note ?? this.note,
      clientId: clientId ?? this.clientId,
      hasExtraData: hasExtraData ?? this.hasExtraData,
    );
  }

  @override
  List<Object> get props => [eligibilities, eligibilityStatusUI,
     loadedEligibility, editMode, deleteStatus, formStatus, generalNotificationKey, 
isEligible,note,createdDate,lastUpdatedDate,clientId,hasExtraData,];

  @override
  bool get stringify => true;
}
