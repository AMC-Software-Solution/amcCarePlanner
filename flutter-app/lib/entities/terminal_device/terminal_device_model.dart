import 'package:dart_json_mapper/dart_json_mapper.dart';

import '../employee/employee_model.dart';

@jsonSerializable
class TerminalDevice {

  @JsonProperty(name: 'id')
  final int id;

  @JsonProperty(name: 'deviceName')
  final String deviceName;

  @JsonProperty(name: 'deviceModel')
  final String deviceModel;

  @JsonProperty(name: 'registeredDate', converterParams: {'format': 'yyyy-MM-dd\'T\'HH:mm:ss\'Z\''})
  final DateTime registeredDate;

  @JsonProperty(name: 'imei')
  final String imei;

  @JsonProperty(name: 'simNumber')
  final String simNumber;

  @JsonProperty(name: 'userStartedUsingFrom', converterParams: {'format': 'yyyy-MM-dd\'T\'HH:mm:ss\'Z\''})
  final DateTime userStartedUsingFrom;

  @JsonProperty(name: 'deviceOnLocationFrom', converterParams: {'format': 'yyyy-MM-dd\'T\'HH:mm:ss\'Z\''})
  final DateTime deviceOnLocationFrom;

  @JsonProperty(name: 'operatingSystem')
  final String operatingSystem;

  @JsonProperty(name: 'note')
  final String note;

  @JsonProperty(name: 'ownerEntityId')
  final int ownerEntityId;

  @JsonProperty(name: 'ownerEntityName')
  final String ownerEntityName;

  @JsonProperty(name: 'createdDate', converterParams: {'format': 'yyyy-MM-dd\'T\'HH:mm:ss\'Z\''})
  final DateTime createdDate;

  @JsonProperty(name: 'lastUpdatedDate', converterParams: {'format': 'yyyy-MM-dd\'T\'HH:mm:ss\'Z\''})
  final DateTime lastUpdatedDate;

  @JsonProperty(name: 'clientId')
  final int clientId;

  @JsonProperty(name: 'hasExtraData')
  final bool hasExtraData;

  @JsonProperty(name: 'employee')
  final Employee employee;
        
 const TerminalDevice (
     this.id,
        this.deviceName,
        this.deviceModel,
        this.registeredDate,
        this.imei,
        this.simNumber,
        this.userStartedUsingFrom,
        this.deviceOnLocationFrom,
        this.operatingSystem,
        this.note,
        this.ownerEntityId,
        this.ownerEntityName,
        this.createdDate,
        this.lastUpdatedDate,
        this.clientId,
        this.hasExtraData,
        this.employee,
    );

@override
String toString() {
    return 'TerminalDevice{'+
    'id: $id,' +
        'deviceName: $deviceName,' +
        'deviceModel: $deviceModel,' +
        'registeredDate: $registeredDate,' +
        'imei: $imei,' +
        'simNumber: $simNumber,' +
        'userStartedUsingFrom: $userStartedUsingFrom,' +
        'deviceOnLocationFrom: $deviceOnLocationFrom,' +
        'operatingSystem: $operatingSystem,' +
        'note: $note,' +
        'ownerEntityId: $ownerEntityId,' +
        'ownerEntityName: $ownerEntityName,' +
        'createdDate: $createdDate,' +
        'lastUpdatedDate: $lastUpdatedDate,' +
        'clientId: $clientId,' +
        'hasExtraData: $hasExtraData,' +
        'employee: $employee,' +
    '}';
   }

@override
bool operator == (Object other) => 
    identical(this, other) ||
    other is TerminalDevice &&
    id == other.id ;


@override
int get hashCode => 
    id.hashCode ;
}


