import 'dart:async';
import 'package:bloc/bloc.dart';
import 'package:flutter/foundation.dart';
import 'package:equatable/equatable.dart';
import 'package:flutter/widgets.dart';
import 'package:formz/formz.dart';

import 'package:amcCarePlanner/entities/employee_holiday/employee_holiday_model.dart';
import 'package:amcCarePlanner/entities/employee_holiday/employee_holiday_repository.dart';
import 'package:amcCarePlanner/entities/employee_holiday/bloc/employee_holiday_form_model.dart';
import 'package:amcCarePlanner/generated/l10n.dart';
import 'package:amcCarePlanner/shared/repository/http_utils.dart';
import 'package:intl/intl.dart';

part 'employee_holiday_events.dart';
part 'employee_holiday_state.dart';

class EmployeeHolidayBloc extends Bloc<EmployeeHolidayEvent, EmployeeHolidayState> {
  final EmployeeHolidayRepository _employeeHolidayRepository;

  final descriptionController = TextEditingController();
  final startDateController = TextEditingController();
  final endDateController = TextEditingController();
  final approvedDateController = TextEditingController();
  final requestedDateController = TextEditingController();
  final noteController = TextEditingController();
  final rejectionReasonController = TextEditingController();
  final createdDateController = TextEditingController();
  final lastUpdatedDateController = TextEditingController();
  final clientIdController = TextEditingController();

  EmployeeHolidayBloc({@required EmployeeHolidayRepository employeeHolidayRepository}) : assert(employeeHolidayRepository != null),
        _employeeHolidayRepository = employeeHolidayRepository, 
  super(EmployeeHolidayState(null,null,null,null,null,null,));

  @override
  void onTransition(Transition<EmployeeHolidayEvent, EmployeeHolidayState> transition) {
    super.onTransition(transition);
  }

  @override
  Stream<EmployeeHolidayState> mapEventToState(EmployeeHolidayEvent event) async* {
    if (event is InitEmployeeHolidayList) {
      yield* onInitList(event);
    } else if (event is EmployeeHolidayFormSubmitted) {
      yield* onSubmit();
    } else if (event is LoadEmployeeHolidayByIdForEdit) {
      yield* onLoadEmployeeHolidayIdForEdit(event);
    } else if (event is DeleteEmployeeHolidayById) {
      yield* onDeleteEmployeeHolidayId(event);
    } else if (event is LoadEmployeeHolidayByIdForView) {
      yield* onLoadEmployeeHolidayIdForView(event);
    }else if (event is DescriptionChanged){
      yield* onDescriptionChange(event);
    }else if (event is StartDateChanged){
      yield* onStartDateChange(event);
    }else if (event is EndDateChanged){
      yield* onEndDateChange(event);
    }else if (event is EmployeeHolidayTypeChanged){
      yield* onEmployeeHolidayTypeChange(event);
    }else if (event is ApprovedDateChanged){
      yield* onApprovedDateChange(event);
    }else if (event is RequestedDateChanged){
      yield* onRequestedDateChange(event);
    }else if (event is HolidayStatusChanged){
      yield* onHolidayStatusChange(event);
    }else if (event is NoteChanged){
      yield* onNoteChange(event);
    }else if (event is RejectionReasonChanged){
      yield* onRejectionReasonChange(event);
    }else if (event is CreatedDateChanged){
      yield* onCreatedDateChange(event);
    }else if (event is LastUpdatedDateChanged){
      yield* onLastUpdatedDateChange(event);
    }else if (event is ClientIdChanged){
      yield* onClientIdChange(event);
    }else if (event is HasExtraDataChanged){
      yield* onHasExtraDataChange(event);
    }  }

  Stream<EmployeeHolidayState> onInitList(InitEmployeeHolidayList event) async* {
    yield this.state.copyWith(employeeHolidayStatusUI: EmployeeHolidayStatusUI.loading);
    List<EmployeeHoliday> employeeHolidays = await _employeeHolidayRepository.getAllEmployeeHolidays();
    yield this.state.copyWith(employeeHolidays: employeeHolidays, employeeHolidayStatusUI: EmployeeHolidayStatusUI.done);
  }

  Stream<EmployeeHolidayState> onSubmit() async* {
    if (this.state.formStatus.isValidated) {
      yield this.state.copyWith(formStatus: FormzStatus.submissionInProgress);
      try {
        EmployeeHoliday result;
        if(this.state.editMode) {
          EmployeeHoliday newEmployeeHoliday = EmployeeHoliday(state.loadedEmployeeHoliday.id,
            this.state.description.value,  
            this.state.startDate.value,  
            this.state.endDate.value,  
            this.state.employeeHolidayType.value,  
            this.state.approvedDate.value,  
            this.state.requestedDate.value,  
            this.state.holidayStatus.value,  
            this.state.note.value,  
            this.state.rejectionReason.value,  
            this.state.createdDate.value,  
            this.state.lastUpdatedDate.value,  
            this.state.clientId.value,  
            this.state.hasExtraData.value,  
            null, 
            null, 
          );

          result = await _employeeHolidayRepository.update(newEmployeeHoliday);
        } else {
          EmployeeHoliday newEmployeeHoliday = EmployeeHoliday(null,
            this.state.description.value,  
            this.state.startDate.value,  
            this.state.endDate.value,  
            this.state.employeeHolidayType.value,  
            this.state.approvedDate.value,  
            this.state.requestedDate.value,  
            this.state.holidayStatus.value,  
            this.state.note.value,  
            this.state.rejectionReason.value,  
            this.state.createdDate.value,  
            this.state.lastUpdatedDate.value,  
            this.state.clientId.value,  
            this.state.hasExtraData.value,  
            null, 
            null, 
          );

          result = await _employeeHolidayRepository.create(newEmployeeHoliday);
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

  Stream<EmployeeHolidayState> onLoadEmployeeHolidayIdForEdit(LoadEmployeeHolidayByIdForEdit event) async* {
    yield this.state.copyWith(employeeHolidayStatusUI: EmployeeHolidayStatusUI.loading);
    EmployeeHoliday loadedEmployeeHoliday = await _employeeHolidayRepository.getEmployeeHoliday(event.id);

    final description = DescriptionInput.dirty(loadedEmployeeHoliday?.description != null ? loadedEmployeeHoliday.description: '');
    final startDate = StartDateInput.dirty(loadedEmployeeHoliday?.startDate != null ? loadedEmployeeHoliday.startDate: null);
    final endDate = EndDateInput.dirty(loadedEmployeeHoliday?.endDate != null ? loadedEmployeeHoliday.endDate: null);
    final employeeHolidayType = EmployeeHolidayTypeInput.dirty(loadedEmployeeHoliday?.employeeHolidayType != null ? loadedEmployeeHoliday.employeeHolidayType: null);
    final approvedDate = ApprovedDateInput.dirty(loadedEmployeeHoliday?.approvedDate != null ? loadedEmployeeHoliday.approvedDate: null);
    final requestedDate = RequestedDateInput.dirty(loadedEmployeeHoliday?.requestedDate != null ? loadedEmployeeHoliday.requestedDate: null);
    final holidayStatus = HolidayStatusInput.dirty(loadedEmployeeHoliday?.holidayStatus != null ? loadedEmployeeHoliday.holidayStatus: null);
    final note = NoteInput.dirty(loadedEmployeeHoliday?.note != null ? loadedEmployeeHoliday.note: '');
    final rejectionReason = RejectionReasonInput.dirty(loadedEmployeeHoliday?.rejectionReason != null ? loadedEmployeeHoliday.rejectionReason: '');
    final createdDate = CreatedDateInput.dirty(loadedEmployeeHoliday?.createdDate != null ? loadedEmployeeHoliday.createdDate: null);
    final lastUpdatedDate = LastUpdatedDateInput.dirty(loadedEmployeeHoliday?.lastUpdatedDate != null ? loadedEmployeeHoliday.lastUpdatedDate: null);
    final clientId = ClientIdInput.dirty(loadedEmployeeHoliday?.clientId != null ? loadedEmployeeHoliday.clientId: 0);
    final hasExtraData = HasExtraDataInput.dirty(loadedEmployeeHoliday?.hasExtraData != null ? loadedEmployeeHoliday.hasExtraData: false);

    yield this.state.copyWith(loadedEmployeeHoliday: loadedEmployeeHoliday, editMode: true,
      description: description,
      startDate: startDate,
      endDate: endDate,
      employeeHolidayType: employeeHolidayType,
      approvedDate: approvedDate,
      requestedDate: requestedDate,
      holidayStatus: holidayStatus,
      note: note,
      rejectionReason: rejectionReason,
      createdDate: createdDate,
      lastUpdatedDate: lastUpdatedDate,
      clientId: clientId,
      hasExtraData: hasExtraData,
    employeeHolidayStatusUI: EmployeeHolidayStatusUI.done);

    descriptionController.text = loadedEmployeeHoliday.description;
    startDateController.text = DateFormat.yMMMMd(S.current.locale).format(loadedEmployeeHoliday?.startDate);
    endDateController.text = DateFormat.yMMMMd(S.current.locale).format(loadedEmployeeHoliday?.endDate);
    approvedDateController.text = DateFormat.yMMMMd(S.current.locale).format(loadedEmployeeHoliday?.approvedDate);
    requestedDateController.text = DateFormat.yMMMMd(S.current.locale).format(loadedEmployeeHoliday?.requestedDate);
    noteController.text = loadedEmployeeHoliday.note;
    rejectionReasonController.text = loadedEmployeeHoliday.rejectionReason;
    createdDateController.text = DateFormat.yMMMMd(S.current.locale).format(loadedEmployeeHoliday?.createdDate);
    lastUpdatedDateController.text = DateFormat.yMMMMd(S.current.locale).format(loadedEmployeeHoliday?.lastUpdatedDate);
    clientIdController.text = loadedEmployeeHoliday.clientId.toString();
  }

  Stream<EmployeeHolidayState> onDeleteEmployeeHolidayId(DeleteEmployeeHolidayById event) async* {
    try {
      await _employeeHolidayRepository.delete(event.id);
      this.add(InitEmployeeHolidayList());
      yield this.state.copyWith(deleteStatus: EmployeeHolidayDeleteStatus.ok);
    } catch (e) {
      yield this.state.copyWith(deleteStatus: EmployeeHolidayDeleteStatus.ko,
          generalNotificationKey: HttpUtils.errorServerKey);
    }
    yield this.state.copyWith(deleteStatus: EmployeeHolidayDeleteStatus.none);
  }

  Stream<EmployeeHolidayState> onLoadEmployeeHolidayIdForView(LoadEmployeeHolidayByIdForView event) async* {
    yield this.state.copyWith(employeeHolidayStatusUI: EmployeeHolidayStatusUI.loading);
    try {
      EmployeeHoliday loadedEmployeeHoliday = await _employeeHolidayRepository.getEmployeeHoliday(event.id);
      yield this.state.copyWith(loadedEmployeeHoliday: loadedEmployeeHoliday, employeeHolidayStatusUI: EmployeeHolidayStatusUI.done);
    } catch(e) {
      yield this.state.copyWith(loadedEmployeeHoliday: null, employeeHolidayStatusUI: EmployeeHolidayStatusUI.error);
    }
  }


  Stream<EmployeeHolidayState> onDescriptionChange(DescriptionChanged event) async* {
    final description = DescriptionInput.dirty(event.description);
    yield this.state.copyWith(
      description: description,
      formStatus: Formz.validate([
        description,
      this.state.startDate,
      this.state.endDate,
      this.state.employeeHolidayType,
      this.state.approvedDate,
      this.state.requestedDate,
      this.state.holidayStatus,
      this.state.note,
      this.state.rejectionReason,
      this.state.createdDate,
      this.state.lastUpdatedDate,
      this.state.clientId,
      this.state.hasExtraData,
      ]),
    );
  }
  Stream<EmployeeHolidayState> onStartDateChange(StartDateChanged event) async* {
    final startDate = StartDateInput.dirty(event.startDate);
    yield this.state.copyWith(
      startDate: startDate,
      formStatus: Formz.validate([
      this.state.description,
        startDate,
      this.state.endDate,
      this.state.employeeHolidayType,
      this.state.approvedDate,
      this.state.requestedDate,
      this.state.holidayStatus,
      this.state.note,
      this.state.rejectionReason,
      this.state.createdDate,
      this.state.lastUpdatedDate,
      this.state.clientId,
      this.state.hasExtraData,
      ]),
    );
  }
  Stream<EmployeeHolidayState> onEndDateChange(EndDateChanged event) async* {
    final endDate = EndDateInput.dirty(event.endDate);
    yield this.state.copyWith(
      endDate: endDate,
      formStatus: Formz.validate([
      this.state.description,
      this.state.startDate,
        endDate,
      this.state.employeeHolidayType,
      this.state.approvedDate,
      this.state.requestedDate,
      this.state.holidayStatus,
      this.state.note,
      this.state.rejectionReason,
      this.state.createdDate,
      this.state.lastUpdatedDate,
      this.state.clientId,
      this.state.hasExtraData,
      ]),
    );
  }
  Stream<EmployeeHolidayState> onEmployeeHolidayTypeChange(EmployeeHolidayTypeChanged event) async* {
    final employeeHolidayType = EmployeeHolidayTypeInput.dirty(event.employeeHolidayType);
    yield this.state.copyWith(
      employeeHolidayType: employeeHolidayType,
      formStatus: Formz.validate([
      this.state.description,
      this.state.startDate,
      this.state.endDate,
        employeeHolidayType,
      this.state.approvedDate,
      this.state.requestedDate,
      this.state.holidayStatus,
      this.state.note,
      this.state.rejectionReason,
      this.state.createdDate,
      this.state.lastUpdatedDate,
      this.state.clientId,
      this.state.hasExtraData,
      ]),
    );
  }
  Stream<EmployeeHolidayState> onApprovedDateChange(ApprovedDateChanged event) async* {
    final approvedDate = ApprovedDateInput.dirty(event.approvedDate);
    yield this.state.copyWith(
      approvedDate: approvedDate,
      formStatus: Formz.validate([
      this.state.description,
      this.state.startDate,
      this.state.endDate,
      this.state.employeeHolidayType,
        approvedDate,
      this.state.requestedDate,
      this.state.holidayStatus,
      this.state.note,
      this.state.rejectionReason,
      this.state.createdDate,
      this.state.lastUpdatedDate,
      this.state.clientId,
      this.state.hasExtraData,
      ]),
    );
  }
  Stream<EmployeeHolidayState> onRequestedDateChange(RequestedDateChanged event) async* {
    final requestedDate = RequestedDateInput.dirty(event.requestedDate);
    yield this.state.copyWith(
      requestedDate: requestedDate,
      formStatus: Formz.validate([
      this.state.description,
      this.state.startDate,
      this.state.endDate,
      this.state.employeeHolidayType,
      this.state.approvedDate,
        requestedDate,
      this.state.holidayStatus,
      this.state.note,
      this.state.rejectionReason,
      this.state.createdDate,
      this.state.lastUpdatedDate,
      this.state.clientId,
      this.state.hasExtraData,
      ]),
    );
  }
  Stream<EmployeeHolidayState> onHolidayStatusChange(HolidayStatusChanged event) async* {
    final holidayStatus = HolidayStatusInput.dirty(event.holidayStatus);
    yield this.state.copyWith(
      holidayStatus: holidayStatus,
      formStatus: Formz.validate([
      this.state.description,
      this.state.startDate,
      this.state.endDate,
      this.state.employeeHolidayType,
      this.state.approvedDate,
      this.state.requestedDate,
        holidayStatus,
      this.state.note,
      this.state.rejectionReason,
      this.state.createdDate,
      this.state.lastUpdatedDate,
      this.state.clientId,
      this.state.hasExtraData,
      ]),
    );
  }
  Stream<EmployeeHolidayState> onNoteChange(NoteChanged event) async* {
    final note = NoteInput.dirty(event.note);
    yield this.state.copyWith(
      note: note,
      formStatus: Formz.validate([
      this.state.description,
      this.state.startDate,
      this.state.endDate,
      this.state.employeeHolidayType,
      this.state.approvedDate,
      this.state.requestedDate,
      this.state.holidayStatus,
        note,
      this.state.rejectionReason,
      this.state.createdDate,
      this.state.lastUpdatedDate,
      this.state.clientId,
      this.state.hasExtraData,
      ]),
    );
  }
  Stream<EmployeeHolidayState> onRejectionReasonChange(RejectionReasonChanged event) async* {
    final rejectionReason = RejectionReasonInput.dirty(event.rejectionReason);
    yield this.state.copyWith(
      rejectionReason: rejectionReason,
      formStatus: Formz.validate([
      this.state.description,
      this.state.startDate,
      this.state.endDate,
      this.state.employeeHolidayType,
      this.state.approvedDate,
      this.state.requestedDate,
      this.state.holidayStatus,
      this.state.note,
        rejectionReason,
      this.state.createdDate,
      this.state.lastUpdatedDate,
      this.state.clientId,
      this.state.hasExtraData,
      ]),
    );
  }
  Stream<EmployeeHolidayState> onCreatedDateChange(CreatedDateChanged event) async* {
    final createdDate = CreatedDateInput.dirty(event.createdDate);
    yield this.state.copyWith(
      createdDate: createdDate,
      formStatus: Formz.validate([
      this.state.description,
      this.state.startDate,
      this.state.endDate,
      this.state.employeeHolidayType,
      this.state.approvedDate,
      this.state.requestedDate,
      this.state.holidayStatus,
      this.state.note,
      this.state.rejectionReason,
        createdDate,
      this.state.lastUpdatedDate,
      this.state.clientId,
      this.state.hasExtraData,
      ]),
    );
  }
  Stream<EmployeeHolidayState> onLastUpdatedDateChange(LastUpdatedDateChanged event) async* {
    final lastUpdatedDate = LastUpdatedDateInput.dirty(event.lastUpdatedDate);
    yield this.state.copyWith(
      lastUpdatedDate: lastUpdatedDate,
      formStatus: Formz.validate([
      this.state.description,
      this.state.startDate,
      this.state.endDate,
      this.state.employeeHolidayType,
      this.state.approvedDate,
      this.state.requestedDate,
      this.state.holidayStatus,
      this.state.note,
      this.state.rejectionReason,
      this.state.createdDate,
        lastUpdatedDate,
      this.state.clientId,
      this.state.hasExtraData,
      ]),
    );
  }
  Stream<EmployeeHolidayState> onClientIdChange(ClientIdChanged event) async* {
    final clientId = ClientIdInput.dirty(event.clientId);
    yield this.state.copyWith(
      clientId: clientId,
      formStatus: Formz.validate([
      this.state.description,
      this.state.startDate,
      this.state.endDate,
      this.state.employeeHolidayType,
      this.state.approvedDate,
      this.state.requestedDate,
      this.state.holidayStatus,
      this.state.note,
      this.state.rejectionReason,
      this.state.createdDate,
      this.state.lastUpdatedDate,
        clientId,
      this.state.hasExtraData,
      ]),
    );
  }
  Stream<EmployeeHolidayState> onHasExtraDataChange(HasExtraDataChanged event) async* {
    final hasExtraData = HasExtraDataInput.dirty(event.hasExtraData);
    yield this.state.copyWith(
      hasExtraData: hasExtraData,
      formStatus: Formz.validate([
      this.state.description,
      this.state.startDate,
      this.state.endDate,
      this.state.employeeHolidayType,
      this.state.approvedDate,
      this.state.requestedDate,
      this.state.holidayStatus,
      this.state.note,
      this.state.rejectionReason,
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
    startDateController.dispose();
    endDateController.dispose();
    approvedDateController.dispose();
    requestedDateController.dispose();
    noteController.dispose();
    rejectionReasonController.dispose();
    createdDateController.dispose();
    lastUpdatedDateController.dispose();
    clientIdController.dispose();
    return super.close();
  }

}