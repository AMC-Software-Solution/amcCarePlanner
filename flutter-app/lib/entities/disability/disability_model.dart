import 'package:dart_json_mapper/dart_json_mapper.dart';

import '../disability_type/disability_type_model.dart';
import '../employee/employee_model.dart';

@jsonSerializable
class Disability {

  @JsonProperty(name: 'id')
  final int id;

  @JsonProperty(name: 'isDisabled')
  final bool isDisabled;

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

  @JsonProperty(name: 'disabilityType')
  final DisabilityType disabilityType;

  @JsonProperty(name: 'employee')
  final Employee employee;

 const Disability (
     this.id,
        this.isDisabled,
        this.note,
        this.createdDate,
        this.lastUpdatedDate,
        this.clientId,
        this.hasExtraData,
        this.disabilityType,
        this.employee,
    );

@override
String toString() {
    return 'Disability{'+
    'id: $id,' +
        'isDisabled: $isDisabled,' +
        'note: $note,' +
        'createdDate: $createdDate,' +
        'lastUpdatedDate: $lastUpdatedDate,' +
        'clientId: $clientId,' +
        'hasExtraData: $hasExtraData,' +
        'disabilityType: $disabilityType,' +
        'employee: $employee,' +
    '}';
   }

@override
bool operator == (Object other) =>
    identical(this, other) ||
    other is Disability &&
    id == other.id ;


@override
int get hashCode =>
    id.hashCode ;
}


