part of 'task_bloc.dart';

abstract class TaskEvent extends Equatable {
  const TaskEvent();

  @override
  List<Object> get props => [];

  @override
  bool get stringify => true;
}

class InitTaskList extends TaskEvent {}

class TaskNameChanged extends TaskEvent {
  final String taskName;
  
  const TaskNameChanged({@required this.taskName});
  
  @override
  List<Object> get props => [taskName];
}
class DescriptionChanged extends TaskEvent {
  final String description;
  
  const DescriptionChanged({@required this.description});
  
  @override
  List<Object> get props => [description];
}
class DateOfTaskChanged extends TaskEvent {
  final DateTime dateOfTask;
  
  const DateOfTaskChanged({@required this.dateOfTask});
  
  @override
  List<Object> get props => [dateOfTask];
}
class StartTimeChanged extends TaskEvent {
  final String startTime;
  
  const StartTimeChanged({@required this.startTime});
  
  @override
  List<Object> get props => [startTime];
}
class EndTimeChanged extends TaskEvent {
  final String endTime;
  
  const EndTimeChanged({@required this.endTime});
  
  @override
  List<Object> get props => [endTime];
}
class StatusChanged extends TaskEvent {
  final TaskStatus status;
  
  const StatusChanged({@required this.status});
  
  @override
  List<Object> get props => [status];
}
class CreatedDateChanged extends TaskEvent {
  final DateTime createdDate;
  
  const CreatedDateChanged({@required this.createdDate});
  
  @override
  List<Object> get props => [createdDate];
}
class LastUpdatedDateChanged extends TaskEvent {
  final DateTime lastUpdatedDate;
  
  const LastUpdatedDateChanged({@required this.lastUpdatedDate});
  
  @override
  List<Object> get props => [lastUpdatedDate];
}
class ClientIdChanged extends TaskEvent {
  final int clientId;
  
  const ClientIdChanged({@required this.clientId});
  
  @override
  List<Object> get props => [clientId];
}
class HasExtraDataChanged extends TaskEvent {
  final bool hasExtraData;
  
  const HasExtraDataChanged({@required this.hasExtraData});
  
  @override
  List<Object> get props => [hasExtraData];
}

class TaskFormSubmitted extends TaskEvent {}

class LoadTaskByIdForEdit extends TaskEvent {
  final int id;

  const LoadTaskByIdForEdit({@required this.id});

  @override
  List<Object> get props => [id];
}

class DeleteTaskById extends TaskEvent {
  final int id;

  const DeleteTaskById({@required this.id});

  @override
  List<Object> get props => [id];
}

class LoadTaskByIdForView extends TaskEvent {
  final int id;

  const LoadTaskByIdForView({@required this.id});

  @override
  List<Object> get props => [id];
}
