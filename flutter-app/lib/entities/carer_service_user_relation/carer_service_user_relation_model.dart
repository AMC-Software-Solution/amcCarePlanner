import 'package:dart_json_mapper/dart_json_mapper.dart';

import '../relationship_type/relationship_type_model.dart';
import '../employee/employee_model.dart';
import '../service_user/service_user_model.dart';

@jsonSerializable
class CarerServiceUserRelation {

  @JsonProperty(name: 'id')
  final int id;

  @JsonProperty(name: 'reason')
  final String reason;

  @JsonProperty(name: 'count')
  final int count;

  @JsonProperty(name: 'createdDate', converterParams: {'format': 'yyyy-MM-dd\'T\'HH:mm:ss\'Z\''})
  final DateTime createdDate;

  @JsonProperty(name: 'lastUpdatedDate', converterParams: {'format': 'yyyy-MM-dd\'T\'HH:mm:ss\'Z\''})
  final DateTime lastUpdatedDate;

  @JsonProperty(name: 'clientId')
  final int clientId;

  @JsonProperty(name: 'hasExtraData')
  final bool hasExtraData;

  @JsonProperty(name: 'relationType')
  final RelationshipType relationType;

  @JsonProperty(name: 'employee')
  final Employee employee;

  @JsonProperty(name: 'serviceUser')
  final ServiceUser serviceUser;

 const CarerServiceUserRelation (
     this.id,
        this.reason,
        this.count,
        this.createdDate,
        this.lastUpdatedDate,
        this.clientId,
        this.hasExtraData,
        this.relationType,
        this.employee,
        this.serviceUser,
    );

@override
String toString() {
    return 'CarerServiceUserRelation{'+
    'id: $id,' +
        'reason: $reason,' +
        'count: $count,' +
        'createdDate: $createdDate,' +
        'lastUpdatedDate: $lastUpdatedDate,' +
        'clientId: $clientId,' +
        'hasExtraData: $hasExtraData,' +
        'relationType: $relationType,' +
        'employee: $employee,' +
        'serviceUser: $serviceUser,' +
    '}';
   }

@override
bool operator == (Object other) =>
    identical(this, other) ||
    other is CarerServiceUserRelation &&
    id == other.id ;


@override
int get hashCode =>
    id.hashCode ;
}


