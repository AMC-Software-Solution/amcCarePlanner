import 'package:dart_json_mapper/dart_json_mapper.dart';

import '../../shared/models/user.dart';

@jsonSerializable
class SystemEventsHistory {

  @JsonProperty(name: 'id')
  final int id;

  @JsonProperty(name: 'eventName')
  final String eventName;

  @JsonProperty(name: 'eventDate', converterParams: {'format': 'yyyy-MM-dd\'T\'HH:mm:ss\'Z\''})
  final DateTime eventDate;

  @JsonProperty(name: 'eventApi')
  final String eventApi;

  @JsonProperty(name: 'ipAddress')
  final String ipAddress;

  @JsonProperty(name: 'eventNote')
  final String eventNote;

  @JsonProperty(name: 'eventEntityName')
  final String eventEntityName;

  @JsonProperty(name: 'eventEntityId')
  final int eventEntityId;

  @JsonProperty(name: 'isSuspecious')
  final bool isSuspecious;

  @JsonProperty(name: 'callerEmail')
  final String callerEmail;

  @JsonProperty(name: 'callerId')
  final int callerId;

  @JsonProperty(name: 'clientId')
  final int clientId;

  @JsonProperty(name: 'triggedBy')
  final User triggedBy;

 const SystemEventsHistory (
     this.id,
        this.eventName,
        this.eventDate,
        this.eventApi,
        this.ipAddress,
        this.eventNote,
        this.eventEntityName,
        this.eventEntityId,
        this.isSuspecious,
        this.callerEmail,
        this.callerId,
        this.clientId,
        this.triggedBy,
    );

@override
String toString() {
    return 'SystemEventsHistory{'+
    'id: $id,' +
        'eventName: $eventName,' +
        'eventDate: $eventDate,' +
        'eventApi: $eventApi,' +
        'ipAddress: $ipAddress,' +
        'eventNote: $eventNote,' +
        'eventEntityName: $eventEntityName,' +
        'eventEntityId: $eventEntityId,' +
        'isSuspecious: $isSuspecious,' +
        'callerEmail: $callerEmail,' +
        'callerId: $callerId,' +
        'clientId: $clientId,' +
        'triggedBy: $triggedBy,' +
    '}';
   }

@override
bool operator == (Object other) =>
    identical(this, other) ||
    other is SystemEventsHistory &&
    id == other.id ;


@override
int get hashCode =>
    id.hashCode ;
}


