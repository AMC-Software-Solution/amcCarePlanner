import 'package:dart_json_mapper/dart_json_mapper.dart';

import '../eligibility_type/eligibility_type_model.dart';
import '../employee/employee_model.dart';

@jsonSerializable
class Eligibility {

  @JsonProperty(name: 'id')
  final int id;

  @JsonProperty(name: 'isEligible')
  final bool isEligible;

  @JsonProperty(name: 'note')
  final String note;

  @JsonProperty(name: 'createdDate', converterParams: {'format': 'yyyy-MM-dd\'T\'HH:mm:ss\'Z\''})
  final DateTime createdDate;

  @JsonProperty(name: 'lastUpdatedDate', converterParams: {'format': 'yyyy-MM-dd\'T\'HH:mm:ss\'Z\''})
  final DateTime lastUpdatedDate;

  @JsonProperty(name: 'clientId')
  final int clientId;

  @JsonProperty(name: 'hasExtraData')
  final bool hasExtraData;

  @JsonProperty(name: 'eligibilityType')
  final EligibilityType eligibilityType;

  @JsonProperty(name: 'employee')
  final Employee employee;

 const Eligibility (
     this.id,
        this.isEligible,
        this.note,
        this.createdDate,
        this.lastUpdatedDate,
        this.clientId,
        this.hasExtraData,
        this.eligibilityType,
        this.employee,
    );

@override
String toString() {
    return 'Eligibility{'+
    'id: $id,' +
        'isEligible: $isEligible,' +
        'note: $note,' +
        'createdDate: $createdDate,' +
        'lastUpdatedDate: $lastUpdatedDate,' +
        'clientId: $clientId,' +
        'hasExtraData: $hasExtraData,' +
        'eligibilityType: $eligibilityType,' +
        'employee: $employee,' +
    '}';
   }

@override
bool operator == (Object other) =>
    identical(this, other) ||
    other is Eligibility &&
    id == other.id ;


@override
int get hashCode =>
    id.hashCode ;
}


