import 'package:dart_json_mapper/dart_json_mapper.dart';

import '../employee/employee_model.dart';

@jsonSerializable
class ServiceUserLocation {

  @JsonProperty(name: 'id')
  final int id;

  @JsonProperty(name: 'latitude')
  final String latitude;

  @JsonProperty(name: 'longitude')
  final String longitude;

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
        
 const ServiceUserLocation (
     this.id,
        this.latitude,
        this.longitude,
        this.createdDate,
        this.lastUpdatedDate,
        this.clientId,
        this.hasExtraData,
        this.employee,
    );

@override
String toString() {
    return 'ServiceUserLocation{'+
    'id: $id,' +
        'latitude: $latitude,' +
        'longitude: $longitude,' +
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
    other is ServiceUserLocation &&
    id == other.id ;


@override
int get hashCode => 
    id.hashCode ;
}


