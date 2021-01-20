part of 'disability_type_bloc.dart';

enum DisabilityTypeStatusUI {init, loading, error, done}
enum DisabilityTypeDeleteStatus {ok, ko, none}

class DisabilityTypeState extends Equatable {
  final List<DisabilityType> disabilityTypes;
  final DisabilityType loadedDisabilityType;
  final bool editMode;
  final DisabilityTypeDeleteStatus deleteStatus;
  final DisabilityTypeStatusUI disabilityTypeStatusUI;

  final FormzStatus formStatus;
  final String generalNotificationKey;

  final DisabilityInput disability;
  final DescriptionInput description;
  final HasExtraDataInput hasExtraData;

  
  DisabilityTypeState(
{
    this.disabilityTypes = const [],
    this.disabilityTypeStatusUI = DisabilityTypeStatusUI.init,
    this.loadedDisabilityType = const DisabilityType(0,'','',false,),
    this.editMode = false,
    this.formStatus = FormzStatus.pure,
    this.generalNotificationKey = '',
    this.deleteStatus = DisabilityTypeDeleteStatus.none,
    this.disability = const DisabilityInput.pure(),
    this.description = const DescriptionInput.pure(),
    this.hasExtraData = const HasExtraDataInput.pure(),
  });

  DisabilityTypeState copyWith({
    List<DisabilityType> disabilityTypes,
    DisabilityTypeStatusUI disabilityTypeStatusUI,
    bool editMode,
    DisabilityTypeDeleteStatus deleteStatus,
    DisabilityType loadedDisabilityType,
    FormzStatus formStatus,
    String generalNotificationKey,
    DisabilityInput disability,
    DescriptionInput description,
    HasExtraDataInput hasExtraData,
  }) {
    return DisabilityTypeState(
      disabilityTypes: disabilityTypes ?? this.disabilityTypes,
      disabilityTypeStatusUI: disabilityTypeStatusUI ?? this.disabilityTypeStatusUI,
      loadedDisabilityType: loadedDisabilityType ?? this.loadedDisabilityType,
      editMode: editMode ?? this.editMode,
      formStatus: formStatus ?? this.formStatus,
      generalNotificationKey: generalNotificationKey ?? this.generalNotificationKey,
      deleteStatus: deleteStatus ?? this.deleteStatus,
      disability: disability ?? this.disability,
      description: description ?? this.description,
      hasExtraData: hasExtraData ?? this.hasExtraData,
    );
  }

  @override
  List<Object> get props => [disabilityTypes, disabilityTypeStatusUI,
     loadedDisabilityType, editMode, deleteStatus, formStatus, generalNotificationKey, 
disability,description,hasExtraData,];

  @override
  bool get stringify => true;
}
