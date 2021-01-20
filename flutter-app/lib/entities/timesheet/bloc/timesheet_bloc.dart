import 'dart:async';
import 'package:bloc/bloc.dart';
import 'package:flutter/foundation.dart';
import 'package:equatable/equatable.dart';
import 'package:flutter/widgets.dart';
import 'package:formz/formz.dart';

import 'package:amcCarePlanner/entities/timesheet/timesheet_model.dart';
import 'package:amcCarePlanner/entities/timesheet/timesheet_repository.dart';
import 'package:amcCarePlanner/entities/timesheet/bloc/timesheet_form_model.dart';
import 'package:amcCarePlanner/generated/l10n.dart';
import 'package:amcCarePlanner/shared/repository/http_utils.dart';
import 'package:intl/intl.dart';

part 'timesheet_events.dart';
part 'timesheet_state.dart';

class TimesheetBloc extends Bloc<TimesheetEvent, TimesheetState> {
  final TimesheetRepository _timesheetRepository;

  final descriptionController = TextEditingController();
  final timesheetDateController = TextEditingController();
  final startTimeController = TextEditingController();
  final endTimeController = TextEditingController();
  final hoursWorkedController = TextEditingController();
  final breakStartTimeController = TextEditingController();
  final breakEndTimeController = TextEditingController();
  final noteController = TextEditingController();
  final createdDateController = TextEditingController();
  final lastUpdatedDateController = TextEditingController();
  final clientIdController = TextEditingController();

  TimesheetBloc({@required TimesheetRepository timesheetRepository}) : assert(timesheetRepository != null),
        _timesheetRepository = timesheetRepository, 
  super(TimesheetState(null,null,null,));

  @override
  void onTransition(Transition<TimesheetEvent, TimesheetState> transition) {
    super.onTransition(transition);
  }

  @override
  Stream<TimesheetState> mapEventToState(TimesheetEvent event) async* {
    if (event is InitTimesheetList) {
      yield* onInitList(event);
    } else if (event is TimesheetFormSubmitted) {
      yield* onSubmit();
    } else if (event is LoadTimesheetByIdForEdit) {
      yield* onLoadTimesheetIdForEdit(event);
    } else if (event is DeleteTimesheetById) {
      yield* onDeleteTimesheetId(event);
    } else if (event is LoadTimesheetByIdForView) {
      yield* onLoadTimesheetIdForView(event);
    }else if (event is DescriptionChanged){
      yield* onDescriptionChange(event);
    }else if (event is TimesheetDateChanged){
      yield* onTimesheetDateChange(event);
    }else if (event is StartTimeChanged){
      yield* onStartTimeChange(event);
    }else if (event is EndTimeChanged){
      yield* onEndTimeChange(event);
    }else if (event is HoursWorkedChanged){
      yield* onHoursWorkedChange(event);
    }else if (event is BreakStartTimeChanged){
      yield* onBreakStartTimeChange(event);
    }else if (event is BreakEndTimeChanged){
      yield* onBreakEndTimeChange(event);
    }else if (event is NoteChanged){
      yield* onNoteChange(event);
    }else if (event is CreatedDateChanged){
      yield* onCreatedDateChange(event);
    }else if (event is LastUpdatedDateChanged){
      yield* onLastUpdatedDateChange(event);
    }else if (event is ClientIdChanged){
      yield* onClientIdChange(event);
    }else if (event is HasExtraDataChanged){
      yield* onHasExtraDataChange(event);
    }  }

  Stream<TimesheetState> onInitList(InitTimesheetList event) async* {
    yield this.state.copyWith(timesheetStatusUI: TimesheetStatusUI.loading);
    List<Timesheet> timesheets = await _timesheetRepository.getAllTimesheets();
    yield this.state.copyWith(timesheets: timesheets, timesheetStatusUI: TimesheetStatusUI.done);
  }

  Stream<TimesheetState> onSubmit() async* {
    if (this.state.formStatus.isValidated) {
      yield this.state.copyWith(formStatus: FormzStatus.submissionInProgress);
      try {
        Timesheet result;
        if(this.state.editMode) {
          Timesheet newTimesheet = Timesheet(state.loadedTimesheet.id,
            this.state.description.value,  
            this.state.timesheetDate.value,  
            this.state.startTime.value,  
            this.state.endTime.value,  
            this.state.hoursWorked.value,  
            this.state.breakStartTime.value,  
            this.state.breakEndTime.value,  
            this.state.note.value,  
            this.state.createdDate.value,  
            this.state.lastUpdatedDate.value,  
            this.state.clientId.value,  
            this.state.hasExtraData.value,  
            null, 
            null, 
            null, 
          );

          result = await _timesheetRepository.update(newTimesheet);
        } else {
          Timesheet newTimesheet = Timesheet(null,
            this.state.description.value,  
            this.state.timesheetDate.value,  
            this.state.startTime.value,  
            this.state.endTime.value,  
            this.state.hoursWorked.value,  
            this.state.breakStartTime.value,  
            this.state.breakEndTime.value,  
            this.state.note.value,  
            this.state.createdDate.value,  
            this.state.lastUpdatedDate.value,  
            this.state.clientId.value,  
            this.state.hasExtraData.value,  
            null, 
            null, 
            null, 
          );

          result = await _timesheetRepository.create(newTimesheet);
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

  Stream<TimesheetState> onLoadTimesheetIdForEdit(LoadTimesheetByIdForEdit event) async* {
    yield this.state.copyWith(timesheetStatusUI: TimesheetStatusUI.loading);
    Timesheet loadedTimesheet = await _timesheetRepository.getTimesheet(event.id);

    final description = DescriptionInput.dirty(loadedTimesheet?.description != null ? loadedTimesheet.description: '');
    final timesheetDate = TimesheetDateInput.dirty(loadedTimesheet?.timesheetDate != null ? loadedTimesheet.timesheetDate: null);
    final startTime = StartTimeInput.dirty(loadedTimesheet?.startTime != null ? loadedTimesheet.startTime: '');
    final endTime = EndTimeInput.dirty(loadedTimesheet?.endTime != null ? loadedTimesheet.endTime: '');
    final hoursWorked = HoursWorkedInput.dirty(loadedTimesheet?.hoursWorked != null ? loadedTimesheet.hoursWorked: 0);
    final breakStartTime = BreakStartTimeInput.dirty(loadedTimesheet?.breakStartTime != null ? loadedTimesheet.breakStartTime: '');
    final breakEndTime = BreakEndTimeInput.dirty(loadedTimesheet?.breakEndTime != null ? loadedTimesheet.breakEndTime: '');
    final note = NoteInput.dirty(loadedTimesheet?.note != null ? loadedTimesheet.note: '');
    final createdDate = CreatedDateInput.dirty(loadedTimesheet?.createdDate != null ? loadedTimesheet.createdDate: null);
    final lastUpdatedDate = LastUpdatedDateInput.dirty(loadedTimesheet?.lastUpdatedDate != null ? loadedTimesheet.lastUpdatedDate: null);
    final clientId = ClientIdInput.dirty(loadedTimesheet?.clientId != null ? loadedTimesheet.clientId: 0);
    final hasExtraData = HasExtraDataInput.dirty(loadedTimesheet?.hasExtraData != null ? loadedTimesheet.hasExtraData: false);

    yield this.state.copyWith(loadedTimesheet: loadedTimesheet, editMode: true,
      description: description,
      timesheetDate: timesheetDate,
      startTime: startTime,
      endTime: endTime,
      hoursWorked: hoursWorked,
      breakStartTime: breakStartTime,
      breakEndTime: breakEndTime,
      note: note,
      createdDate: createdDate,
      lastUpdatedDate: lastUpdatedDate,
      clientId: clientId,
      hasExtraData: hasExtraData,
    timesheetStatusUI: TimesheetStatusUI.done);

    descriptionController.text = loadedTimesheet.description;
    timesheetDateController.text = DateFormat.yMMMMd(S.current.locale).format(loadedTimesheet?.timesheetDate);
    startTimeController.text = loadedTimesheet.startTime;
    endTimeController.text = loadedTimesheet.endTime;
    hoursWorkedController.text = loadedTimesheet.hoursWorked.toString();
    breakStartTimeController.text = loadedTimesheet.breakStartTime;
    breakEndTimeController.text = loadedTimesheet.breakEndTime;
    noteController.text = loadedTimesheet.note;
    createdDateController.text = DateFormat.yMMMMd(S.current.locale).format(loadedTimesheet?.createdDate);
    lastUpdatedDateController.text = DateFormat.yMMMMd(S.current.locale).format(loadedTimesheet?.lastUpdatedDate);
    clientIdController.text = loadedTimesheet.clientId.toString();
  }

  Stream<TimesheetState> onDeleteTimesheetId(DeleteTimesheetById event) async* {
    try {
      await _timesheetRepository.delete(event.id);
      this.add(InitTimesheetList());
      yield this.state.copyWith(deleteStatus: TimesheetDeleteStatus.ok);
    } catch (e) {
      yield this.state.copyWith(deleteStatus: TimesheetDeleteStatus.ko,
          generalNotificationKey: HttpUtils.errorServerKey);
    }
    yield this.state.copyWith(deleteStatus: TimesheetDeleteStatus.none);
  }

  Stream<TimesheetState> onLoadTimesheetIdForView(LoadTimesheetByIdForView event) async* {
    yield this.state.copyWith(timesheetStatusUI: TimesheetStatusUI.loading);
    try {
      Timesheet loadedTimesheet = await _timesheetRepository.getTimesheet(event.id);
      yield this.state.copyWith(loadedTimesheet: loadedTimesheet, timesheetStatusUI: TimesheetStatusUI.done);
    } catch(e) {
      yield this.state.copyWith(loadedTimesheet: null, timesheetStatusUI: TimesheetStatusUI.error);
    }
  }


  Stream<TimesheetState> onDescriptionChange(DescriptionChanged event) async* {
    final description = DescriptionInput.dirty(event.description);
    yield this.state.copyWith(
      description: description,
      formStatus: Formz.validate([
        description,
      this.state.timesheetDate,
      this.state.startTime,
      this.state.endTime,
      this.state.hoursWorked,
      this.state.breakStartTime,
      this.state.breakEndTime,
      this.state.note,
      this.state.createdDate,
      this.state.lastUpdatedDate,
      this.state.clientId,
      this.state.hasExtraData,
      ]),
    );
  }
  Stream<TimesheetState> onTimesheetDateChange(TimesheetDateChanged event) async* {
    final timesheetDate = TimesheetDateInput.dirty(event.timesheetDate);
    yield this.state.copyWith(
      timesheetDate: timesheetDate,
      formStatus: Formz.validate([
      this.state.description,
        timesheetDate,
      this.state.startTime,
      this.state.endTime,
      this.state.hoursWorked,
      this.state.breakStartTime,
      this.state.breakEndTime,
      this.state.note,
      this.state.createdDate,
      this.state.lastUpdatedDate,
      this.state.clientId,
      this.state.hasExtraData,
      ]),
    );
  }
  Stream<TimesheetState> onStartTimeChange(StartTimeChanged event) async* {
    final startTime = StartTimeInput.dirty(event.startTime);
    yield this.state.copyWith(
      startTime: startTime,
      formStatus: Formz.validate([
      this.state.description,
      this.state.timesheetDate,
        startTime,
      this.state.endTime,
      this.state.hoursWorked,
      this.state.breakStartTime,
      this.state.breakEndTime,
      this.state.note,
      this.state.createdDate,
      this.state.lastUpdatedDate,
      this.state.clientId,
      this.state.hasExtraData,
      ]),
    );
  }
  Stream<TimesheetState> onEndTimeChange(EndTimeChanged event) async* {
    final endTime = EndTimeInput.dirty(event.endTime);
    yield this.state.copyWith(
      endTime: endTime,
      formStatus: Formz.validate([
      this.state.description,
      this.state.timesheetDate,
      this.state.startTime,
        endTime,
      this.state.hoursWorked,
      this.state.breakStartTime,
      this.state.breakEndTime,
      this.state.note,
      this.state.createdDate,
      this.state.lastUpdatedDate,
      this.state.clientId,
      this.state.hasExtraData,
      ]),
    );
  }
  Stream<TimesheetState> onHoursWorkedChange(HoursWorkedChanged event) async* {
    final hoursWorked = HoursWorkedInput.dirty(event.hoursWorked);
    yield this.state.copyWith(
      hoursWorked: hoursWorked,
      formStatus: Formz.validate([
      this.state.description,
      this.state.timesheetDate,
      this.state.startTime,
      this.state.endTime,
        hoursWorked,
      this.state.breakStartTime,
      this.state.breakEndTime,
      this.state.note,
      this.state.createdDate,
      this.state.lastUpdatedDate,
      this.state.clientId,
      this.state.hasExtraData,
      ]),
    );
  }
  Stream<TimesheetState> onBreakStartTimeChange(BreakStartTimeChanged event) async* {
    final breakStartTime = BreakStartTimeInput.dirty(event.breakStartTime);
    yield this.state.copyWith(
      breakStartTime: breakStartTime,
      formStatus: Formz.validate([
      this.state.description,
      this.state.timesheetDate,
      this.state.startTime,
      this.state.endTime,
      this.state.hoursWorked,
        breakStartTime,
      this.state.breakEndTime,
      this.state.note,
      this.state.createdDate,
      this.state.lastUpdatedDate,
      this.state.clientId,
      this.state.hasExtraData,
      ]),
    );
  }
  Stream<TimesheetState> onBreakEndTimeChange(BreakEndTimeChanged event) async* {
    final breakEndTime = BreakEndTimeInput.dirty(event.breakEndTime);
    yield this.state.copyWith(
      breakEndTime: breakEndTime,
      formStatus: Formz.validate([
      this.state.description,
      this.state.timesheetDate,
      this.state.startTime,
      this.state.endTime,
      this.state.hoursWorked,
      this.state.breakStartTime,
        breakEndTime,
      this.state.note,
      this.state.createdDate,
      this.state.lastUpdatedDate,
      this.state.clientId,
      this.state.hasExtraData,
      ]),
    );
  }
  Stream<TimesheetState> onNoteChange(NoteChanged event) async* {
    final note = NoteInput.dirty(event.note);
    yield this.state.copyWith(
      note: note,
      formStatus: Formz.validate([
      this.state.description,
      this.state.timesheetDate,
      this.state.startTime,
      this.state.endTime,
      this.state.hoursWorked,
      this.state.breakStartTime,
      this.state.breakEndTime,
        note,
      this.state.createdDate,
      this.state.lastUpdatedDate,
      this.state.clientId,
      this.state.hasExtraData,
      ]),
    );
  }
  Stream<TimesheetState> onCreatedDateChange(CreatedDateChanged event) async* {
    final createdDate = CreatedDateInput.dirty(event.createdDate);
    yield this.state.copyWith(
      createdDate: createdDate,
      formStatus: Formz.validate([
      this.state.description,
      this.state.timesheetDate,
      this.state.startTime,
      this.state.endTime,
      this.state.hoursWorked,
      this.state.breakStartTime,
      this.state.breakEndTime,
      this.state.note,
        createdDate,
      this.state.lastUpdatedDate,
      this.state.clientId,
      this.state.hasExtraData,
      ]),
    );
  }
  Stream<TimesheetState> onLastUpdatedDateChange(LastUpdatedDateChanged event) async* {
    final lastUpdatedDate = LastUpdatedDateInput.dirty(event.lastUpdatedDate);
    yield this.state.copyWith(
      lastUpdatedDate: lastUpdatedDate,
      formStatus: Formz.validate([
      this.state.description,
      this.state.timesheetDate,
      this.state.startTime,
      this.state.endTime,
      this.state.hoursWorked,
      this.state.breakStartTime,
      this.state.breakEndTime,
      this.state.note,
      this.state.createdDate,
        lastUpdatedDate,
      this.state.clientId,
      this.state.hasExtraData,
      ]),
    );
  }
  Stream<TimesheetState> onClientIdChange(ClientIdChanged event) async* {
    final clientId = ClientIdInput.dirty(event.clientId);
    yield this.state.copyWith(
      clientId: clientId,
      formStatus: Formz.validate([
      this.state.description,
      this.state.timesheetDate,
      this.state.startTime,
      this.state.endTime,
      this.state.hoursWorked,
      this.state.breakStartTime,
      this.state.breakEndTime,
      this.state.note,
      this.state.createdDate,
      this.state.lastUpdatedDate,
        clientId,
      this.state.hasExtraData,
      ]),
    );
  }
  Stream<TimesheetState> onHasExtraDataChange(HasExtraDataChanged event) async* {
    final hasExtraData = HasExtraDataInput.dirty(event.hasExtraData);
    yield this.state.copyWith(
      hasExtraData: hasExtraData,
      formStatus: Formz.validate([
      this.state.description,
      this.state.timesheetDate,
      this.state.startTime,
      this.state.endTime,
      this.state.hoursWorked,
      this.state.breakStartTime,
      this.state.breakEndTime,
      this.state.note,
      this.state.createdDate,
      this.state.lastUpdatedDate,
      this.state.clientId,
        hasExtraData,
      ]),
    );
  }

  @override
  Future<void> close() {
    descriptionController.dispose();
    timesheetDateController.dispose();
    startTimeController.dispose();
    endTimeController.dispose();
    hoursWorkedController.dispose();
    breakStartTimeController.dispose();
    breakEndTimeController.dispose();
    noteController.dispose();
    createdDateController.dispose();
    lastUpdatedDateController.dispose();
    clientIdController.dispose();
    return super.close();
  }

}