import 'package:dart_json_mapper/dart_json_mapper.dart';

import '../employee/employee_model.dart';

@jsonSerializable
class EmployeeAvailability {

  @JsonProperty(name: 'id')
  final int id;

  @JsonProperty(name: 'availableForWork')
  final bool availableForWork;

  @JsonProperty(name: 'availableMonday')
  final bool availableMonday;

  @JsonProperty(name: 'availableTuesday')
  final bool availableTuesday;

  @JsonProperty(name: 'availableWednesday')
  final bool availableWednesday;

  @JsonProperty(name: 'availableThursday')
  final bool availableThursday;

  @JsonProperty(name: 'availableFriday')
  final bool availableFriday;

  @JsonProperty(name: 'availableSaturday')
  final bool availableSaturday;

  @JsonProperty(name: 'availableSunday')
  final bool availableSunday;

  @JsonProperty(name: 'preferredShift', enumValues: Shift.values)
  final Shift preferredShift;

  @JsonProperty(name: 'hasExtraData')
  final bool hasExtraData;

  @JsonProperty(name: 'lastUpdatedDate', converterParams: {'format': 'yyyy-MM-dd\'T\'HH:mm:ss\'Z\''})
  final DateTime lastUpdatedDate;

  @JsonProperty(name: 'clientId')
  final int clientId;

  @JsonProperty(name: 'employee')
  final Employee employee;
        
 const EmployeeAvailability (
     this.id,
        this.availableForWork,
        this.availableMonday,
        this.availableTuesday,
        this.availableWednesday,
        this.availableThursday,
        this.availableFriday,
        this.availableSaturday,
        this.availableSunday,
        this.preferredShift,
        this.hasExtraData,
        this.lastUpdatedDate,
        this.clientId,
        this.employee,
    );

@override
String toString() {
    return 'EmployeeAvailability{'+
    'id: $id,' +
        'availableForWork: $availableForWork,' +
        'availableMonday: $availableMonday,' +
        'availableTuesday: $availableTuesday,' +
        'availableWednesday: $availableWednesday,' +
        'availableThursday: $availableThursday,' +
        'availableFriday: $availableFriday,' +
        'availableSaturday: $availableSaturday,' +
        'availableSunday: $availableSunday,' +
        'preferredShift: $preferredShift,' +
        'hasExtraData: $hasExtraData,' +
        'lastUpdatedDate: $lastUpdatedDate,' +
        'clientId: $clientId,' +
        'employee: $employee,' +
    '}';
   }

@override
bool operator == (Object other) => 
    identical(this, other) ||
    other is EmployeeAvailability &&
    id == other.id ;


@override
int get hashCode => 
    id.hashCode ;
}


@jsonSerializable
@Json(enumValues: Shift.values)
enum Shift {
    MORNING_SHIFT ,
    AFTERNOON_SHIFT ,
    EVENING_SHIFT ,
    NIGHTS_SHIFT ,
    AVAILABLE_ANY_SHIFT 
}