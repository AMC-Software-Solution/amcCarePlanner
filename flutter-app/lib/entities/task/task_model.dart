import 'package:dart_json_mapper/dart_json_mapper.dart';

import '../service_user/service_user_model.dart';
import '../employee/employee_model.dart';
import '../service_order/service_order_model.dart';
import '../employee/employee_model.dart';
import '../employee/employee_model.dart';

@jsonSerializable
class Task {

  @JsonProperty(name: 'id')
  final int id;

  @JsonProperty(name: 'taskName')
  final String taskName;

  @JsonProperty(name: 'description')
  final String description;

  @JsonProperty(name: 'dateOfTask', converterParams: {'format': 'yyyy-MM-dd\'T\'HH:mm:ss\'Z\''})
  final DateTime dateOfTask;

  @JsonProperty(name: 'startTime')
  final String startTime;

  @JsonProperty(name: 'endTime')
  final String endTime;

  @JsonProperty(name: 'status', enumValues: TaskStatus.values)
  final TaskStatus status;

  @JsonProperty(name: 'createdDate', converterParams: {'format': 'yyyy-MM-dd\'T\'HH:mm:ss\'Z\''})
  final DateTime createdDate;

  @JsonProperty(name: 'lastUpdatedDate', converterParams: {'format': 'yyyy-MM-dd\'T\'HH:mm:ss\'Z\''})
  final DateTime lastUpdatedDate;

  @JsonProperty(name: 'clientId')
  final int clientId;

  @JsonProperty(name: 'hasExtraData')
  final bool hasExtraData;

  @JsonProperty(name: 'serviceUser')
  final ServiceUser serviceUser;

  @JsonProperty(name: 'assignedTo')
  final Employee assignedTo;

  @JsonProperty(name: 'serviceOrder')
  final ServiceOrder serviceOrder;

  @JsonProperty(name: 'createdBy')
  final Employee createdBy;

  @JsonProperty(name: 'allocatedBy')
  final Employee allocatedBy;

 const Task (
     this.id,
        this.taskName,
        this.description,
        this.dateOfTask,
        this.startTime,
        this.endTime,
        this.status,
        this.createdDate,
        this.lastUpdatedDate,
        this.clientId,
        this.hasExtraData,
        this.serviceUser,
        this.assignedTo,
        this.serviceOrder,
        this.createdBy,
        this.allocatedBy,
    );

@override
String toString() {
    return 'Task{'+
    'id: $id,' +
        'taskName: $taskName,' +
        'description: $description,' +
        'dateOfTask: $dateOfTask,' +
        'startTime: $startTime,' +
        'endTime: $endTime,' +
        'status: $status,' +
        'createdDate: $createdDate,' +
        'lastUpdatedDate: $lastUpdatedDate,' +
        'clientId: $clientId,' +
        'hasExtraData: $hasExtraData,' +
        'serviceUser: $serviceUser,' +
        'assignedTo: $assignedTo,' +
        'serviceOrder: $serviceOrder,' +
        'createdBy: $createdBy,' +
        'allocatedBy: $allocatedBy,' +
    '}';
   }

@override
bool operator == (Object other) =>
    identical(this, other) ||
    other is Task &&
    id == other.id ;


@override
int get hashCode =>
    id.hashCode ;
}


@jsonSerializable
@Json(enumValues: TaskStatus.values)
enum TaskStatus {
    ASSSIGNED ,
    INPROGRESS ,
    CANCELLED_BY_CLIENT ,
    CANCELLED_BY_EMPLOYEE ,
    MISSED ,
    COMPLETED ,
    REJECTED ,
    AVAILABLE
}
