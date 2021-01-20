part of 'relationship_type_bloc.dart';

enum RelationshipTypeStatusUI {init, loading, error, done}
enum RelationshipTypeDeleteStatus {ok, ko, none}

class RelationshipTypeState extends Equatable {
  final List<RelationshipType> relationshipTypes;
  final RelationshipType loadedRelationshipType;
  final bool editMode;
  final RelationshipTypeDeleteStatus deleteStatus;
  final RelationshipTypeStatusUI relationshipTypeStatusUI;

  final FormzStatus formStatus;
  final String generalNotificationKey;

  final RelationTypeInput relationType;
  final DescriptionInput description;
  final ClientIdInput clientId;
  final HasExtraDataInput hasExtraData;

  
  RelationshipTypeState(
{
    this.relationshipTypes = const [],
    this.relationshipTypeStatusUI = RelationshipTypeStatusUI.init,
    this.loadedRelationshipType = const RelationshipType(0,'','',0,false,),
    this.editMode = false,
    this.formStatus = FormzStatus.pure,
    this.generalNotificationKey = '',
    this.deleteStatus = RelationshipTypeDeleteStatus.none,
    this.relationType = const RelationTypeInput.pure(),
    this.description = const DescriptionInput.pure(),
    this.clientId = const ClientIdInput.pure(),
    this.hasExtraData = const HasExtraDataInput.pure(),
  });

  RelationshipTypeState copyWith({
    List<RelationshipType> relationshipTypes,
    RelationshipTypeStatusUI relationshipTypeStatusUI,
    bool editMode,
    RelationshipTypeDeleteStatus deleteStatus,
    RelationshipType loadedRelationshipType,
    FormzStatus formStatus,
    String generalNotificationKey,
    RelationTypeInput relationType,
    DescriptionInput description,
    ClientIdInput clientId,
    HasExtraDataInput hasExtraData,
  }) {
    return RelationshipTypeState(
      relationshipTypes: relationshipTypes ?? this.relationshipTypes,
      relationshipTypeStatusUI: relationshipTypeStatusUI ?? this.relationshipTypeStatusUI,
      loadedRelationshipType: loadedRelationshipType ?? this.loadedRelationshipType,
      editMode: editMode ?? this.editMode,
      formStatus: formStatus ?? this.formStatus,
      generalNotificationKey: generalNotificationKey ?? this.generalNotificationKey,
      deleteStatus: deleteStatus ?? this.deleteStatus,
      relationType: relationType ?? this.relationType,
      description: description ?? this.description,
      clientId: clientId ?? this.clientId,
      hasExtraData: hasExtraData ?? this.hasExtraData,
    );
  }

  @override
  List<Object> get props => [relationshipTypes, relationshipTypeStatusUI,
     loadedRelationshipType, editMode, deleteStatus, formStatus, generalNotificationKey, 
relationType,description,clientId,hasExtraData,];

  @override
  bool get stringify => true;
}
