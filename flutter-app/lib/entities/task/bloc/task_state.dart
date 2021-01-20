part of 'task_bloc.dart';

enum TaskStatusUI {init, loading, error, done}
enum TaskDeleteStatus {ok, ko, none}

class TaskState extends Equatable {
  final List<Task> tasks;
  final Task loadedTask;
  final bool editMode;
  final TaskDeleteStatus deleteStatus;
  final TaskStatusUI taskStatusUI;

  final FormzStatus formStatus;
  final String generalNotificationKey;

  final TaskNameInput taskName;
  final DescriptionInput description;
  final DateOfTaskInput dateOfTask;
  final StartTimeInput startTime;
  final EndTimeInput endTime;
  final StatusInput status;
  final CreatedDateInput createdDate;
  final LastUpdatedDateInput lastUpdatedDate;
  final ClientIdInput clientId;
  final HasExtraDataInput hasExtraData;

  
  TaskState(
DateOfTaskInput dateOfTask,CreatedDateInput createdDate,LastUpdatedDateInput lastUpdatedDate,{
    this.tasks = const [],
    this.taskStatusUI = TaskStatusUI.init,
    this.loadedTask = const Task(0,'','',null,'','',null,null,null,0,false,null,null,null,null,null,),
    this.editMode = false,
    this.formStatus = FormzStatus.pure,
    this.generalNotificationKey = '',
    this.deleteStatus = TaskDeleteStatus.none,
    this.taskName = const TaskNameInput.pure(),
    this.description = const DescriptionInput.pure(),
    this.startTime = const StartTimeInput.pure(),
    this.endTime = const EndTimeInput.pure(),
    this.status = const StatusInput.pure(),
    this.clientId = const ClientIdInput.pure(),
    this.hasExtraData = const HasExtraDataInput.pure(),
  }):this.dateOfTask = dateOfTask ?? DateOfTaskInput.pure(),
this.createdDate = createdDate ?? CreatedDateInput.pure(),
this.lastUpdatedDate = lastUpdatedDate ?? LastUpdatedDateInput.pure()
;

  TaskState copyWith({
    List<Task> tasks,
    TaskStatusUI taskStatusUI,
    bool editMode,
    TaskDeleteStatus deleteStatus,
    Task loadedTask,
    FormzStatus formStatus,
    String generalNotificationKey,
    TaskNameInput taskName,
    DescriptionInput description,
    DateOfTaskInput dateOfTask,
    StartTimeInput startTime,
    EndTimeInput endTime,
    StatusInput status,
    CreatedDateInput createdDate,
    LastUpdatedDateInput lastUpdatedDate,
    ClientIdInput clientId,
    HasExtraDataInput hasExtraData,
  }) {
    return TaskState(
        dateOfTask,
        createdDate,
        lastUpdatedDate,
      tasks: tasks ?? this.tasks,
      taskStatusUI: taskStatusUI ?? this.taskStatusUI,
      loadedTask: loadedTask ?? this.loadedTask,
      editMode: editMode ?? this.editMode,
      formStatus: formStatus ?? this.formStatus,
      generalNotificationKey: generalNotificationKey ?? this.generalNotificationKey,
      deleteStatus: deleteStatus ?? this.deleteStatus,
      taskName: taskName ?? this.taskName,
      description: description ?? this.description,
      startTime: startTime ?? this.startTime,
      endTime: endTime ?? this.endTime,
      status: status ?? this.status,
      clientId: clientId ?? this.clientId,
      hasExtraData: hasExtraData ?? this.hasExtraData,
    );
  }

  @override
  List<Object> get props => [tasks, taskStatusUI,
     loadedTask, editMode, deleteStatus, formStatus, generalNotificationKey, 
taskName,description,dateOfTask,startTime,endTime,status,createdDate,lastUpdatedDate,clientId,hasExtraData,];

  @override
  bool get stringify => true;
}
