import 'package:dart_json_mapper/dart_json_mapper.dart';

import '../employee/employee_model.dart';
import '../employee/employee_model.dart';

@jsonSerializable
class EmployeeHoliday {

  @JsonProperty(name: 'id')
  final int id;

  @JsonProperty(name: 'description')
  final String description;

  @JsonProperty(name: 'startDate', converterParams: {'format': 'yyyy-MM-dd\'T\'HH:mm:ss\'Z\''})
  final DateTime startDate;

  @JsonProperty(name: 'endDate', converterParams: {'format': 'yyyy-MM-dd\'T\'HH:mm:ss\'Z\''})
  final DateTime endDate;

  @JsonProperty(name: 'employeeHolidayType', enumValues: EmployeeHolidayType.values)
  final EmployeeHolidayType employeeHolidayType;

  @JsonProperty(name: 'approvedDate', converterParams: {'format': 'yyyy-MM-dd\'T\'HH:mm:ss\'Z\''})
  final DateTime approvedDate;

  @JsonProperty(name: 'requestedDate', converterParams: {'format': 'yyyy-MM-dd\'T\'HH:mm:ss\'Z\''})
  final DateTime requestedDate;

  @JsonProperty(name: 'holidayStatus', enumValues: HolidayStatus.values)
  final HolidayStatus holidayStatus;

  @JsonProperty(name: 'note')
  final String note;

  @JsonProperty(name: 'rejectionReason')
  final String rejectionReason;

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

  @JsonProperty(name: 'approvedBy')
  final Employee approvedBy;
        
 const EmployeeHoliday (
     this.id,
        this.description,
        this.startDate,
        this.endDate,
        this.employeeHolidayType,
        this.approvedDate,
        this.requestedDate,
        this.holidayStatus,
        this.note,
        this.rejectionReason,
        this.createdDate,
        this.lastUpdatedDate,
        this.clientId,
        this.hasExtraData,
        this.employee,
        this.approvedBy,
    );

@override
String toString() {
    return 'EmployeeHoliday{'+
    'id: $id,' +
        'description: $description,' +
        'startDate: $startDate,' +
        'endDate: $endDate,' +
        'employeeHolidayType: $employeeHolidayType,' +
        'approvedDate: $approvedDate,' +
        'requestedDate: $requestedDate,' +
        'holidayStatus: $holidayStatus,' +
        'note: $note,' +
        'rejectionReason: $rejectionReason,' +
        'createdDate: $createdDate,' +
        'lastUpdatedDate: $lastUpdatedDate,' +
        'clientId: $clientId,' +
        'hasExtraData: $hasExtraData,' +
        'employee: $employee,' +
        'approvedBy: $approvedBy,' +
    '}';
   }

@override
bool operator == (Object other) => 
    identical(this, other) ||
    other is EmployeeHoliday &&
    id == other.id ;


@override
int get hashCode => 
    id.hashCode ;
}


@jsonSerializable
@Json(enumValues: EmployeeHolidayType.values)
enum EmployeeHolidayType {
    ANNUAL_HOLIDAY ,
    PUBLIC_HOLIDAY ,
    PARENTAL_LEAVE ,
    SICKNESS_ABSENCE ,
    UNPAID_HOLIDAY ,
    OTHER 
}@jsonSerializable
@Json(enumValues: HolidayStatus.values)
enum HolidayStatus {
    REQUESTED ,
    APPROVED ,
    REJECTED 
}