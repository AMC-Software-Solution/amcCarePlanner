part of 'travel_bloc.dart';

enum TravelStatusUI {init, loading, error, done}
enum TravelDeleteStatus {ok, ko, none}

class TravelState extends Equatable {
  final List<Travel> travels;
  final Travel loadedTravel;
  final bool editMode;
  final TravelDeleteStatus deleteStatus;
  final TravelStatusUI travelStatusUI;

  final FormzStatus formStatus;
  final String generalNotificationKey;

  final TravelModeInput travelMode;
  final DistanceToDestinationInput distanceToDestination;
  final TimeToDestinationInput timeToDestination;
  final ActualDistanceRequiredInput actualDistanceRequired;
  final ActualTimeRequiredInput actualTimeRequired;
  final TravelStatusInput travelStatus;
  final CreatedDateInput createdDate;
  final LastUpdatedDateInput lastUpdatedDate;
  final ClientIdInput clientId;
  final HasExtraDataInput hasExtraData;

  
  TravelState(
CreatedDateInput createdDate,LastUpdatedDateInput lastUpdatedDate,{
    this.travels = const [],
    this.travelStatusUI = TravelStatusUI.init,
    this.loadedTravel = const Travel(0,null,0,0,0,0,null,null,null,0,false,null,),
    this.editMode = false,
    this.formStatus = FormzStatus.pure,
    this.generalNotificationKey = '',
    this.deleteStatus = TravelDeleteStatus.none,
    this.travelMode = const TravelModeInput.pure(),
    this.distanceToDestination = const DistanceToDestinationInput.pure(),
    this.timeToDestination = const TimeToDestinationInput.pure(),
    this.actualDistanceRequired = const ActualDistanceRequiredInput.pure(),
    this.actualTimeRequired = const ActualTimeRequiredInput.pure(),
    this.travelStatus = const TravelStatusInput.pure(),
    this.clientId = const ClientIdInput.pure(),
    this.hasExtraData = const HasExtraDataInput.pure(),
  }):this.createdDate = createdDate ?? CreatedDateInput.pure(),
this.lastUpdatedDate = lastUpdatedDate ?? LastUpdatedDateInput.pure()
;

  TravelState copyWith({
    List<Travel> travels,
    TravelStatusUI travelStatusUI,
    bool editMode,
    TravelDeleteStatus deleteStatus,
    Travel loadedTravel,
    FormzStatus formStatus,
    String generalNotificationKey,
    TravelModeInput travelMode,
    DistanceToDestinationInput distanceToDestination,
    TimeToDestinationInput timeToDestination,
    ActualDistanceRequiredInput actualDistanceRequired,
    ActualTimeRequiredInput actualTimeRequired,
    TravelStatusInput travelStatus,
    CreatedDateInput createdDate,
    LastUpdatedDateInput lastUpdatedDate,
    ClientIdInput clientId,
    HasExtraDataInput hasExtraData,
  }) {
    return TravelState(
        createdDate,
        lastUpdatedDate,
      travels: travels ?? this.travels,
      travelStatusUI: travelStatusUI ?? this.travelStatusUI,
      loadedTravel: loadedTravel ?? this.loadedTravel,
      editMode: editMode ?? this.editMode,
      formStatus: formStatus ?? this.formStatus,
      generalNotificationKey: generalNotificationKey ?? this.generalNotificationKey,
      deleteStatus: deleteStatus ?? this.deleteStatus,
      travelMode: travelMode ?? this.travelMode,
      distanceToDestination: distanceToDestination ?? this.distanceToDestination,
      timeToDestination: timeToDestination ?? this.timeToDestination,
      actualDistanceRequired: actualDistanceRequired ?? this.actualDistanceRequired,
      actualTimeRequired: actualTimeRequired ?? this.actualTimeRequired,
      travelStatus: travelStatus ?? this.travelStatus,
      clientId: clientId ?? this.clientId,
      hasExtraData: hasExtraData ?? this.hasExtraData,
    );
  }

  @override
  List<Object> get props => [travels, travelStatusUI,
     loadedTravel, editMode, deleteStatus, formStatus, generalNotificationKey, 
travelMode,distanceToDestination,timeToDestination,actualDistanceRequired,actualTimeRequired,travelStatus,createdDate,lastUpdatedDate,clientId,hasExtraData,];

  @override
  bool get stringify => true;
}
