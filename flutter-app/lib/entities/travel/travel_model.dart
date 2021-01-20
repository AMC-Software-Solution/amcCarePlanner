import 'package:dart_json_mapper/dart_json_mapper.dart';

import '../task/task_model.dart';

@jsonSerializable
class Travel {

  @JsonProperty(name: 'id')
  final int id;

  @JsonProperty(name: 'travelMode', enumValues: TravelMode.values)
  final TravelMode travelMode;

  @JsonProperty(name: 'distanceToDestination')
  final int distanceToDestination;

  @JsonProperty(name: 'timeToDestination')
  final int timeToDestination;

  @JsonProperty(name: 'actualDistanceRequired')
  final int actualDistanceRequired;

  @JsonProperty(name: 'actualTimeRequired')
  final int actualTimeRequired;

  @JsonProperty(name: 'travelStatus', enumValues: TravelStatus.values)
  final TravelStatus travelStatus;

  @JsonProperty(name: 'createdDate', converterParams: {'format': 'yyyy-MM-dd\'T\'HH:mm:ss\'Z\''})
  final DateTime createdDate;

  @JsonProperty(name: 'lastUpdatedDate', converterParams: {'format': 'yyyy-MM-dd\'T\'HH:mm:ss\'Z\''})
  final DateTime lastUpdatedDate;

  @JsonProperty(name: 'clientId')
  final int clientId;

  @JsonProperty(name: 'hasExtraData')
  final bool hasExtraData;

  @JsonProperty(name: 'task')
  final Task task;
        
 const Travel (
     this.id,
        this.travelMode,
        this.distanceToDestination,
        this.timeToDestination,
        this.actualDistanceRequired,
        this.actualTimeRequired,
        this.travelStatus,
        this.createdDate,
        this.lastUpdatedDate,
        this.clientId,
        this.hasExtraData,
        this.task,
    );

@override
String toString() {
    return 'Travel{'+
    'id: $id,' +
        'travelMode: $travelMode,' +
        'distanceToDestination: $distanceToDestination,' +
        'timeToDestination: $timeToDestination,' +
        'actualDistanceRequired: $actualDistanceRequired,' +
        'actualTimeRequired: $actualTimeRequired,' +
        'travelStatus: $travelStatus,' +
        'createdDate: $createdDate,' +
        'lastUpdatedDate: $lastUpdatedDate,' +
        'clientId: $clientId,' +
        'hasExtraData: $hasExtraData,' +
        'task: $task,' +
    '}';
   }

@override
bool operator == (Object other) => 
    identical(this, other) ||
    other is Travel &&
    id == other.id ;


@override
int get hashCode => 
    id.hashCode ;
}


@jsonSerializable
@Json(enumValues: TravelMode.values)
enum TravelMode {
    CAR ,
    BUS ,
    TRAIN ,
    PLANE ,
    SHIP ,
    WALK ,
    OTHER 
}@jsonSerializable
@Json(enumValues: TravelStatus.values)
enum TravelStatus {
    BOOKED ,
    ENROUTE ,
    ARRIVED ,
    CANCELLED 
}