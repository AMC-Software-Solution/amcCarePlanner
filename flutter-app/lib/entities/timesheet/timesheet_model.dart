import 'package:dart_json_mapper/dart_json_mapper.dart';

import '../task/task_model.dart';
import '../service_user/service_user_model.dart';
import '../employee/employee_model.dart';

@jsonSerializable
class Timesheet {

  @JsonProperty(name: 'id')
  final int id;

  @JsonProperty(name: 'description')
  final String description;

  @JsonProperty(name: 'timesheetDate', converterParams: {'format': 'yyyy-MM-dd\'T\'HH:mm:ss\'Z\''})
  final DateTime timesheetDate;

  @JsonProperty(name: 'startTime')
  final String startTime;

  @JsonProperty(name: 'endTime')
  final String endTime;

  @JsonProperty(name: 'hoursWorked')
  final int hoursWorked;

  @JsonProperty(name: 'breakStartTime')
  final String breakStartTime;

  @JsonProperty(name: 'breakEndTime')
  final String breakEndTime;

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

  @JsonProperty(name: 'task')
  final Task task;

  @JsonProperty(name: 'serviceUser')
  final ServiceUser serviceUser;

  @JsonProperty(name: 'careProvider')
  final Employee careProvider;

 const Timesheet (
     this.id,
        this.description,
        this.timesheetDate,
        this.startTime,
        this.endTime,
        this.hoursWorked,
        this.breakStartTime,
        this.breakEndTime,
        this.note,
        this.createdDate,
        this.lastUpdatedDate,
        this.clientId,
        this.hasExtraData,
        this.task,
        this.serviceUser,
        this.careProvider,
    );

@override
String toString() {
    return 'Timesheet{'+
    'id: $id,' +
        'description: $description,' +
        'timesheetDate: $timesheetDate,' +
        'startTime: $startTime,' +
        'endTime: $endTime,' +
        'hoursWorked: $hoursWorked,' +
        'breakStartTime: $breakStartTime,' +
        'breakEndTime: $breakEndTime,' +
        'note: $note,' +
        'createdDate: $createdDate,' +
        'lastUpdatedDate: $lastUpdatedDate,' +
        'clientId: $clientId,' +
        'hasExtraData: $hasExtraData,' +
        'task: $task,' +
        'serviceUser: $serviceUser,' +
        'careProvider: $careProvider,' +
    '}';
   }

@override
bool operator == (Object other) =>
    identical(this, other) ||
    other is Timesheet &&
    id == other.id ;


@override
int get hashCode =>
    id.hashCode ;
}


