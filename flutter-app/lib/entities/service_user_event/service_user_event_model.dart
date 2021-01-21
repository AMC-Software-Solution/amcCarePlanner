import 'package:dart_json_mapper/dart_json_mapper.dart';

import '../employee/employee_model.dart';
import '../service_user/service_user_model.dart';

@jsonSerializable
class ServiceUserEvent {

  @JsonProperty(name: 'id')
  final int id;

  @JsonProperty(name: 'eventTitle')
  final String eventTitle;

  @JsonProperty(name: 'description')
  final String description;

  @JsonProperty(name: 'serviceUserEventStatus', enumValues: ServiceUserEventStatus.values)
  final ServiceUserEventStatus serviceUserEventStatus;

  @JsonProperty(name: 'serviceUserEventType', enumValues: ServiceUserEventType.values)
  final ServiceUserEventType serviceUserEventType;

  @JsonProperty(name: 'priority', enumValues: Priority.values)
  final Priority priority;

  @JsonProperty(name: 'note')
  final String note;

  @JsonProperty(name: 'dateOfEvent', converterParams: {'format': 'yyyy-MM-dd\'T\'HH:mm:ss\'Z\''})
  final DateTime dateOfEvent;

  @JsonProperty(name: 'createdDate', converterParams: {'format': 'yyyy-MM-dd\'T\'HH:mm:ss\'Z\''})
  final DateTime createdDate;

  @JsonProperty(name: 'lastUpdatedDate', converterParams: {'format': 'yyyy-MM-dd\'T\'HH:mm:ss\'Z\''})
  final DateTime lastUpdatedDate;

  @JsonProperty(name: 'clientId')
  final int clientId;

  @JsonProperty(name: 'hasExtraData')
  final bool hasExtraData;

  @JsonProperty(name: 'reportedBy')
  final Employee reportedBy;

  @JsonProperty(name: 'assignedTo')
  final Employee assignedTo;

  @JsonProperty(name: 'serviceUser')
  final ServiceUser serviceUser;

 const ServiceUserEvent (
     this.id,
        this.eventTitle,
        this.description,
        this.serviceUserEventStatus,
        this.serviceUserEventType,
        this.priority,
        this.note,
        this.dateOfEvent,
        this.createdDate,
        this.lastUpdatedDate,
        this.clientId,
        this.hasExtraData,
        this.reportedBy,
        this.assignedTo,
        this.serviceUser,
    );

@override
String toString() {
    return 'ServiceUserEvent{'+
    'id: $id,' +
        'eventTitle: $eventTitle,' +
        'description: $description,' +
        'serviceUserEventStatus: $serviceUserEventStatus,' +
        'serviceUserEventType: $serviceUserEventType,' +
        'priority: $priority,' +
        'note: $note,' +
        'dateOfEvent: $dateOfEvent,' +
        'createdDate: $createdDate,' +
        'lastUpdatedDate: $lastUpdatedDate,' +
        'clientId: $clientId,' +
        'hasExtraData: $hasExtraData,' +
        'reportedBy: $reportedBy,' +
        'assignedTo: $assignedTo,' +
        'serviceUser: $serviceUser,' +
    '}';
   }

@override
bool operator == (Object other) =>
    identical(this, other) ||
    other is ServiceUserEvent &&
    id == other.id ;


@override
int get hashCode =>
    id.hashCode ;
}


@jsonSerializable
@Json(enumValues: ServiceUserEventStatus.values)
enum ServiceUserEventStatus {
    REPORTED ,
    UNDER_INVESTIGATION ,
    RESOLVED
}@jsonSerializable
@Json(enumValues: ServiceUserEventType.values)
enum ServiceUserEventType {
    ACCIDENT ,
    INCIDENT ,
    SAFEGUARDING ,
    MEDICAL_ERROR ,
    COMPLIMENT ,
    COMPLAINCE ,
    COMMENT ,
    MISSED_VISIT
}@jsonSerializable
@Json(enumValues: Priority.values)
enum Priority {
    HIGH ,
    LOW ,
    MEDIUM
}
