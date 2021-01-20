part of 'eligibility_type_bloc.dart';

enum EligibilityTypeStatusUI {init, loading, error, done}
enum EligibilityTypeDeleteStatus {ok, ko, none}

class EligibilityTypeState extends Equatable {
  final List<EligibilityType> eligibilityTypes;
  final EligibilityType loadedEligibilityType;
  final bool editMode;
  final EligibilityTypeDeleteStatus deleteStatus;
  final EligibilityTypeStatusUI eligibilityTypeStatusUI;

  final FormzStatus formStatus;
  final String generalNotificationKey;

  final EligibilityTypeInput eligibilityType;
  final DescriptionInput description;
  final HasExtraDataInput hasExtraData;

  
  EligibilityTypeState(
{
    this.eligibilityTypes = const [],
    this.eligibilityTypeStatusUI = EligibilityTypeStatusUI.init,
    this.loadedEligibilityType = const EligibilityType(0,'','',false,),
    this.editMode = false,
    this.formStatus = FormzStatus.pure,
    this.generalNotificationKey = '',
    this.deleteStatus = EligibilityTypeDeleteStatus.none,
    this.eligibilityType = const EligibilityTypeInput.pure(),
    this.description = const DescriptionInput.pure(),
    this.hasExtraData = const HasExtraDataInput.pure(),
  });

  EligibilityTypeState copyWith({
    List<EligibilityType> eligibilityTypes,
    EligibilityTypeStatusUI eligibilityTypeStatusUI,
    bool editMode,
    EligibilityTypeDeleteStatus deleteStatus,
    EligibilityType loadedEligibilityType,
    FormzStatus formStatus,
    String generalNotificationKey,
    EligibilityTypeInput eligibilityType,
    DescriptionInput description,
    HasExtraDataInput hasExtraData,
  }) {
    return EligibilityTypeState(
      eligibilityTypes: eligibilityTypes ?? this.eligibilityTypes,
      eligibilityTypeStatusUI: eligibilityTypeStatusUI ?? this.eligibilityTypeStatusUI,
      loadedEligibilityType: loadedEligibilityType ?? this.loadedEligibilityType,
      editMode: editMode ?? this.editMode,
      formStatus: formStatus ?? this.formStatus,
      generalNotificationKey: generalNotificationKey ?? this.generalNotificationKey,
      deleteStatus: deleteStatus ?? this.deleteStatus,
      eligibilityType: eligibilityType ?? this.eligibilityType,
      description: description ?? this.description,
      hasExtraData: hasExtraData ?? this.hasExtraData,
    );
  }

  @override
  List<Object> get props => [eligibilityTypes, eligibilityTypeStatusUI,
     loadedEligibilityType, editMode, deleteStatus, formStatus, generalNotificationKey, 
eligibilityType,description,hasExtraData,];

  @override
  bool get stringify => true;
}
