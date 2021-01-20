import 'dart:async';
import 'package:bloc/bloc.dart';
import 'package:flutter/foundation.dart';
import 'package:equatable/equatable.dart';
import 'package:flutter/widgets.dart';
import 'package:formz/formz.dart';

import 'package:amcCarePlanner/entities/task/task_model.dart';
import 'package:amcCarePlanner/entities/task/task_repository.dart';
import 'package:amcCarePlanner/entities/task/bloc/task_form_model.dart';
import 'package:amcCarePlanner/generated/l10n.dart';
import 'package:amcCarePlanner/shared/repository/http_utils.dart';
import 'package:intl/intl.dart';

part 'task_events.dart';
part 'task_state.dart';

class TaskBloc extends Bloc<TaskEvent, TaskState> {
  final TaskRepository _taskRepository;

  final taskNameController = TextEditingController();
  final descriptionController = TextEditingController();
  final dateOfTaskController = TextEditingController();
  final startTimeController = TextEditingController();
  final endTimeController = TextEditingController();
  final createdDateController = TextEditingController();
  final lastUpdatedDateController = TextEditingController();
  final clientIdController = TextEditingController();

  TaskBloc({@required TaskRepository taskRepository}) : assert(taskRepository != null),
        _taskRepository = taskRepository, 
  super(TaskState(null,null,null,));

  @override
  void onTransition(Transition<TaskEvent, TaskState> transition) {
    super.onTransition(transition);
  }

  @override
  Stream<TaskState> mapEventToState(TaskEvent event) async* {
    if (event is InitTaskList) {
      yield* onInitList(event);
    } else if (event is TaskFormSubmitted) {
      yield* onSubmit();
    } else if (event is LoadTaskByIdForEdit) {
      yield* onLoadTaskIdForEdit(event);
    } else if (event is DeleteTaskById) {
      yield* onDeleteTaskId(event);
    } else if (event is LoadTaskByIdForView) {
      yield* onLoadTaskIdForView(event);
    }else if (event is TaskNameChanged){
      yield* onTaskNameChange(event);
    }else if (event is DescriptionChanged){
      yield* onDescriptionChange(event);
    }else if (event is DateOfTaskChanged){
      yield* onDateOfTaskChange(event);
    }else if (event is StartTimeChanged){
      yield* onStartTimeChange(event);
    }else if (event is EndTimeChanged){
      yield* onEndTimeChange(event);
    }else if (event is StatusChanged){
      yield* onStatusChange(event);
    }else if (event is CreatedDateChanged){
      yield* onCreatedDateChange(event);
    }else if (event is LastUpdatedDateChanged){
      yield* onLastUpdatedDateChange(event);
    }else if (event is ClientIdChanged){
      yield* onClientIdChange(event);
    }else if (event is HasExtraDataChanged){
      yield* onHasExtraDataChange(event);
    }  }

  Stream<TaskState> onInitList(InitTaskList event) async* {
    yield this.state.copyWith(taskStatusUI: TaskStatusUI.loading);
    List<Task> tasks = await _taskRepository.getAllTasks();
    yield this.state.copyWith(tasks: tasks, taskStatusUI: TaskStatusUI.done);
  }

  Stream<TaskState> onSubmit() async* {
    if (this.state.formStatus.isValidated) {
      yield this.state.copyWith(formStatus: FormzStatus.submissionInProgress);
      try {
        Task result;
        if(this.state.editMode) {
          Task newTask = Task(state.loadedTask.id,
            this.state.taskName.value,  
            this.state.description.value,  
            this.state.dateOfTask.value,  
            this.state.startTime.value,  
            this.state.endTime.value,  
            this.state.status.value,  
            this.state.createdDate.value,  
            this.state.lastUpdatedDate.value,  
            this.state.clientId.value,  
            this.state.hasExtraData.value,  
            null, 
            null, 
            null, 
            null, 
            null, 
          );

          result = await _taskRepository.update(newTask);
        } else {
          Task newTask = Task(null,
            this.state.taskName.value,  
            this.state.description.value,  
            this.state.dateOfTask.value,  
            this.state.startTime.value,  
            this.state.endTime.value,  
            this.state.status.value,  
            this.state.createdDate.value,  
            this.state.lastUpdatedDate.value,  
            this.state.clientId.value,  
            this.state.hasExtraData.value,  
            null, 
            null, 
            null, 
            null, 
            null, 
          );

          result = await _taskRepository.create(newTask);
        }

        if (result == null) {
          yield this.state.copyWith(formStatus: FormzStatus.submissionFailure,
              generalNotificationKey: HttpUtils.badRequestServerKey);
        } else {
          yield this.state.copyWith(formStatus: FormzStatus.submissionSuccess,
              generalNotificationKey: HttpUtils.successResult);
        }
      } catch (e) {
        yield this.state.copyWith(formStatus: FormzStatus.submissionFailure,
            generalNotificationKey: HttpUtils.errorServerKey);
      }
    }
  }

  Stream<TaskState> onLoadTaskIdForEdit(LoadTaskByIdForEdit event) async* {
    yield this.state.copyWith(taskStatusUI: TaskStatusUI.loading);
    Task loadedTask = await _taskRepository.getTask(event.id);

    final taskName = TaskNameInput.dirty(loadedTask?.taskName != null ? loadedTask.taskName: '');
    final description = DescriptionInput.dirty(loadedTask?.description != null ? loadedTask.description: '');
    final dateOfTask = DateOfTaskInput.dirty(loadedTask?.dateOfTask != null ? loadedTask.dateOfTask: null);
    final startTime = StartTimeInput.dirty(loadedTask?.startTime != null ? loadedTask.startTime: '');
    final endTime = EndTimeInput.dirty(loadedTask?.endTime != null ? loadedTask.endTime: '');
    final status = StatusInput.dirty(loadedTask?.status != null ? loadedTask.status: null);
    final createdDate = CreatedDateInput.dirty(loadedTask?.createdDate != null ? loadedTask.createdDate: null);
    final lastUpdatedDate = LastUpdatedDateInput.dirty(loadedTask?.lastUpdatedDate != null ? loadedTask.lastUpdatedDate: null);
    final clientId = ClientIdInput.dirty(loadedTask?.clientId != null ? loadedTask.clientId: 0);
    final hasExtraData = HasExtraDataInput.dirty(loadedTask?.hasExtraData != null ? loadedTask.hasExtraData: false);

    yield this.state.copyWith(loadedTask: loadedTask, editMode: true,
      taskName: taskName,
      description: description,
      dateOfTask: dateOfTask,
      startTime: startTime,
      endTime: endTime,
      status: status,
      createdDate: createdDate,
      lastUpdatedDate: lastUpdatedDate,
      clientId: clientId,
      hasExtraData: hasExtraData,
    taskStatusUI: TaskStatusUI.done);

    taskNameController.text = loadedTask.taskName;
    descriptionController.text = loadedTask.description;
    dateOfTaskController.text = DateFormat.yMMMMd(S.current.locale).format(loadedTask?.dateOfTask);
    startTimeController.text = loadedTask.startTime;
    endTimeController.text = loadedTask.endTime;
    createdDateController.text = DateFormat.yMMMMd(S.current.locale).format(loadedTask?.createdDate);
    lastUpdatedDateController.text = DateFormat.yMMMMd(S.current.locale).format(loadedTask?.lastUpdatedDate);
    clientIdController.text = loadedTask.clientId.toString();
  }

  Stream<TaskState> onDeleteTaskId(DeleteTaskById event) async* {
    try {
      await _taskRepository.delete(event.id);
      this.add(InitTaskList());
      yield this.state.copyWith(deleteStatus: TaskDeleteStatus.ok);
    } catch (e) {
      yield this.state.copyWith(deleteStatus: TaskDeleteStatus.ko,
          generalNotificationKey: HttpUtils.errorServerKey);
    }
    yield this.state.copyWith(deleteStatus: TaskDeleteStatus.none);
  }

  Stream<TaskState> onLoadTaskIdForView(LoadTaskByIdForView event) async* {
    yield this.state.copyWith(taskStatusUI: TaskStatusUI.loading);
    try {
      Task loadedTask = await _taskRepository.getTask(event.id);
      yield this.state.copyWith(loadedTask: loadedTask, taskStatusUI: TaskStatusUI.done);
    } catch(e) {
      yield this.state.copyWith(loadedTask: null, taskStatusUI: TaskStatusUI.error);
    }
  }


  Stream<TaskState> onTaskNameChange(TaskNameChanged event) async* {
    final taskName = TaskNameInput.dirty(event.taskName);
    yield this.state.copyWith(
      taskName: taskName,
      formStatus: Formz.validate([
        taskName,
      this.state.description,
      this.state.dateOfTask,
      this.state.startTime,
      this.state.endTime,
      this.state.status,
      this.state.createdDate,
      this.state.lastUpdatedDate,
      this.state.clientId,
      this.state.hasExtraData,
      ]),
    );
  }
  Stream<TaskState> onDescriptionChange(DescriptionChanged event) async* {
    final description = DescriptionInput.dirty(event.description);
    yield this.state.copyWith(
      description: description,
      formStatus: Formz.validate([
      this.state.taskName,
        description,
      this.state.dateOfTask,
      this.state.startTime,
      this.state.endTime,
      this.state.status,
      this.state.createdDate,
      this.state.lastUpdatedDate,
      this.state.clientId,
      this.state.hasExtraData,
      ]),
    );
  }
  Stream<TaskState> onDateOfTaskChange(DateOfTaskChanged event) async* {
    final dateOfTask = DateOfTaskInput.dirty(event.dateOfTask);
    yield this.state.copyWith(
      dateOfTask: dateOfTask,
      formStatus: Formz.validate([
      this.state.taskName,
      this.state.description,
        dateOfTask,
      this.state.startTime,
      this.state.endTime,
      this.state.status,
      this.state.createdDate,
      this.state.lastUpdatedDate,
      this.state.clientId,
      this.state.hasExtraData,
      ]),
    );
  }
  Stream<TaskState> onStartTimeChange(StartTimeChanged event) async* {
    final startTime = StartTimeInput.dirty(event.startTime);
    yield this.state.copyWith(
      startTime: startTime,
      formStatus: Formz.validate([
      this.state.taskName,
      this.state.description,
      this.state.dateOfTask,
        startTime,
      this.state.endTime,
      this.state.status,
      this.state.createdDate,
      this.state.lastUpdatedDate,
      this.state.clientId,
      this.state.hasExtraData,
      ]),
    );
  }
  Stream<TaskState> onEndTimeChange(EndTimeChanged event) async* {
    final endTime = EndTimeInput.dirty(event.endTime);
    yield this.state.copyWith(
      endTime: endTime,
      formStatus: Formz.validate([
      this.state.taskName,
      this.state.description,
      this.state.dateOfTask,
      this.state.startTime,
        endTime,
      this.state.status,
      this.state.createdDate,
      this.state.lastUpdatedDate,
      this.state.clientId,
      this.state.hasExtraData,
      ]),
    );
  }
  Stream<TaskState> onStatusChange(StatusChanged event) async* {
    final status = StatusInput.dirty(event.status);
    yield this.state.copyWith(
      status: status,
      formStatus: Formz.validate([
      this.state.taskName,
      this.state.description,
      this.state.dateOfTask,
      this.state.startTime,
      this.state.endTime,
        status,
      this.state.createdDate,
      this.state.lastUpdatedDate,
      this.state.clientId,
      this.state.hasExtraData,
      ]),
    );
  }
  Stream<TaskState> onCreatedDateChange(CreatedDateChanged event) async* {
    final createdDate = CreatedDateInput.dirty(event.createdDate);
    yield this.state.copyWith(
      createdDate: createdDate,
      formStatus: Formz.validate([
      this.state.taskName,
      this.state.description,
      this.state.dateOfTask,
      this.state.startTime,
      this.state.endTime,
      this.state.status,
        createdDate,
      this.state.lastUpdatedDate,
      this.state.clientId,
      this.state.hasExtraData,
      ]),
    );
  }
  Stream<TaskState> onLastUpdatedDateChange(LastUpdatedDateChanged event) async* {
    final lastUpdatedDate = LastUpdatedDateInput.dirty(event.lastUpdatedDate);
    yield this.state.copyWith(
      lastUpdatedDate: lastUpdatedDate,
      formStatus: Formz.validate([
      this.state.taskName,
      this.state.description,
      this.state.dateOfTask,
      this.state.startTime,
      this.state.endTime,
      this.state.status,
      this.state.createdDate,
        lastUpdatedDate,
      this.state.clientId,
      this.state.hasExtraData,
      ]),
    );
  }
  Stream<TaskState> onClientIdChange(ClientIdChanged event) async* {
    final clientId = ClientIdInput.dirty(event.clientId);
    yield this.state.copyWith(
      clientId: clientId,
      formStatus: Formz.validate([
      this.state.taskName,
      this.state.description,
      this.state.dateOfTask,
      this.state.startTime,
      this.state.endTime,
      this.state.status,
      this.state.createdDate,
      this.state.lastUpdatedDate,
        clientId,
      this.state.hasExtraData,
      ]),
    );
  }
  Stream<TaskState> onHasExtraDataChange(HasExtraDataChanged event) async* {
    final hasExtraData = HasExtraDataInput.dirty(event.hasExtraData);
    yield this.state.copyWith(
      hasExtraData: hasExtraData,
      formStatus: Formz.validate([
      this.state.taskName,
      this.state.description,
      this.state.dateOfTask,
      this.state.startTime,
      this.state.endTime,
      this.state.status,
      this.state.createdDate,
      this.state.lastUpdatedDate,
      this.state.clientId,
        hasExtraData,
      ]),
    );
  }

  @override
  Future<void> close() {
    taskNameController.dispose();
    descriptionController.dispose();
    dateOfTaskController.dispose();
    startTimeController.dispose();
    endTimeController.dispose();
    createdDateController.dispose();
    lastUpdatedDateController.dispose();
    clientIdController.dispose();
    return super.close();
  }

}