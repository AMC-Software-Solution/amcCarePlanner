part of 'carer_service_user_relation_bloc.dart';

enum CarerServiceUserRelationStatusUI {init, loading, error, done}
enum CarerServiceUserRelationDeleteStatus {ok, ko, none}

class CarerServiceUserRelationState extends Equatable {
  final List<CarerServiceUserRelation> carerServiceUserRelations;
  final CarerServiceUserRelation loadedCarerServiceUserRelation;
  final bool editMode;
  final CarerServiceUserRelationDeleteStatus deleteStatus;
  final CarerServiceUserRelationStatusUI carerServiceUserRelationStatusUI;

  final FormzStatus formStatus;
  final String generalNotificationKey;

  final ReasonInput reason;
  final CountInput count;
  final CreatedDateInput createdDate;
  final LastUpdatedDateInput lastUpdatedDate;
  final ClientIdInput clientId;
  final HasExtraDataInput hasExtraData;

  
  CarerServiceUserRelationState(
CreatedDateInput createdDate,LastUpdatedDateInput lastUpdatedDate,{
    this.carerServiceUserRelations = const [],
    this.carerServiceUserRelationStatusUI = CarerServiceUserRelationStatusUI.init,
    this.loadedCarerServiceUserRelation = const CarerServiceUserRelation(0,'',0,null,null,0,false,null,null,null,),
    this.editMode = false,
    this.formStatus = FormzStatus.pure,
    this.generalNotificationKey = '',
    this.deleteStatus = CarerServiceUserRelationDeleteStatus.none,
    this.reason = const ReasonInput.pure(),
    this.count = const CountInput.pure(),
    this.clientId = const ClientIdInput.pure(),
    this.hasExtraData = const HasExtraDataInput.pure(),
  }):this.createdDate = createdDate ?? CreatedDateInput.pure(),
this.lastUpdatedDate = lastUpdatedDate ?? LastUpdatedDateInput.pure()
;

  CarerServiceUserRelationState copyWith({
    List<CarerServiceUserRelation> carerServiceUserRelations,
    CarerServiceUserRelationStatusUI carerServiceUserRelationStatusUI,
    bool editMode,
    CarerServiceUserRelationDeleteStatus deleteStatus,
    CarerServiceUserRelation loadedCarerServiceUserRelation,
    FormzStatus formStatus,
    String generalNotificationKey,
    ReasonInput reason,
    CountInput count,
    CreatedDateInput createdDate,
    LastUpdatedDateInput lastUpdatedDate,
    ClientIdInput clientId,
    HasExtraDataInput hasExtraData,
  }) {
    return CarerServiceUserRelationState(
        createdDate,
        lastUpdatedDate,
      carerServiceUserRelations: carerServiceUserRelations ?? this.carerServiceUserRelations,
      carerServiceUserRelationStatusUI: carerServiceUserRelationStatusUI ?? this.carerServiceUserRelationStatusUI,
      loadedCarerServiceUserRelation: loadedCarerServiceUserRelation ?? this.loadedCarerServiceUserRelation,
      editMode: editMode ?? this.editMode,
      formStatus: formStatus ?? this.formStatus,
      generalNotificationKey: generalNotificationKey ?? this.generalNotificationKey,
      deleteStatus: deleteStatus ?? this.deleteStatus,
      reason: reason ?? this.reason,
      count: count ?? this.count,
      clientId: clientId ?? this.clientId,
      hasExtraData: hasExtraData ?? this.hasExtraData,
    );
  }

  @override
  List<Object> get props => [carerServiceUserRelations, carerServiceUserRelationStatusUI,
     loadedCarerServiceUserRelation, editMode, deleteStatus, formStatus, generalNotificationKey, 
reason,count,createdDate,lastUpdatedDate,clientId,hasExtraData,];

  @override
  bool get stringify => true;
}
